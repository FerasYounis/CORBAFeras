package DFRSApp;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.*;
import java.net.*;
import java.io.*;

public class DFRSServer extends DFRSPOA implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(DFRSServer.class
			.getName());
	private Map<String, CopyOnWriteArrayList<Passenger>> database = new HashMap<>();
	private List<Flight> flightsList = new ArrayList<>();
	private City serverCity;
	private DatagramSocket aSocket = null;
	private InetAddress aHost = null;
	int serverPort = 6789;
	ORB orb = null;
	Thread udpServerThread = null;

	public DFRSServer(City serverCity) {
		this.serverCity = serverCity;

		serverPort += serverCity.ordinal();

		try {
			FileHandler fileHandler = new FileHandler(
					serverCity.getCityApprviation() + "_server_logger.log",
					false);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

			System.err.println(serverCity.getCityApprviation()
					+ " Server ready");

			aHost = InetAddress.getLocalHost();
		} catch (SecurityException | IOException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public String bookFlight(String firstName, String lastName, String address,
			String phone, String destinationStr, String date,
			String flightClassStr) {
		City destination;
		if (destinationStr.equals("Montreal")){
			destination = City.MONTREAL;}
		if(destinationStr.equals("Washington")){
			destination = City.WASHINGTON;}
		else {
			destination = City.NEW_DELHI;}
		
		SeatType flightClass;
			if (flightClassStr.toUpperCase().equals(SeatType.BUSINESS)){
				flightClass = SeatType.BUSINESS;}
			if (flightClassStr.toUpperCase().equals(SeatType.ECONOMY)){
				flightClass = SeatType.ECONOMY;}
			else {flightClass = SeatType.FIRST;}
			
		for (Flight flight : flightsList) {

			if (flight.getDate().equals(date)
					&& flight.getDestination() == destination
					&& flight.canReserved(flightClass)) {
				Passenger passenger = new Passenger(firstName, lastName,
						address, phone, destination, date, flightClass);
				// lock.lock();
				// try {
				if (database.get(lastName.substring(0, 1).toUpperCase()) == null) {
					CopyOnWriteArrayList passengerList = new CopyOnWriteArrayList<Passenger>();
					passengerList.add(passenger);
					database.put(lastName.substring(0, 1).toUpperCase(),
							passengerList);
				}
				/*
				 * finally { lock.unlock();
				 */
				else {
					List passengerList = database.get(lastName.substring(0, 1)
							.toUpperCase());
					passengerList.add(passenger);
				}
				flight.incrementReservedCount(flightClass);
				LOGGER.log(Level.INFO, "bookFlight : " + passenger);
				return passenger.getRecordId();
			}
		}
		LOGGER.log(Level.INFO,
				"tried to book a flight that didnt match any flight data "
						+ date + " or destination " + destination);
		return null;

	}

	public synchronized String getBookedFlightCountLoical(SeatType recordType) {
		int totalcount = 0;
		for (Flight flight : flightsList) {
			totalcount = totalcount + flight.getReservedCount(recordType);
		}
		return serverCity.getCityApprviation() + " " + totalcount;
	}

	@Override
	public synchronized String getBookedFlightCount(String recordTypeStr) {
		SeatType recordType = SeatType.valueOf(recordTypeStr);

		int totalcount = 0;
		for (Flight flight : flightsList) {
			totalcount = totalcount + flight.getReservedCount(recordType);
		}
		StringBuffer answer = new StringBuffer();
		answer.append(serverCity.getCityApprviation() + " " + totalcount);

		
			
		
		for (City city : City.values()) {
			System.out.println("SOCKET Working on City:"+city);
			try {
				int sendport = 6789;
				if (city != serverCity) {
					sendport = 6789 + city.ordinal();
					int classValue = recordType.ordinal();

					DatagramSocket rSocket = new DatagramSocket();
					byte[] m = ("getBookedFlightCount" + classValue).getBytes();
					InetAddress aHost = InetAddress.getLocalHost();

					DatagramPacket request = new DatagramPacket(m, m.length,
							aHost, sendport);
					rSocket.send(request);
					byte[] buffer = new byte[1000];
					
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length);
					rSocket.receive(reply);
					String rcvAnswer = new String(reply.getData());

					System.out.println("SOCKET ANSWER RECIEVE on " + serverCity
							+ " from " + city + ": " + rcvAnswer.trim());
					answer.append(" + " +" " + rcvAnswer.trim());
					rSocket.close();
					
				}

			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			}
		
		}
		// answer = answer + serverCity.getCityApprviation() + "111 " +
		// totalcount;
		
		System.out.println("FINAL  ANSWER RECIEVE on " + serverCity + ": "
				+ answer.toString());

		return answer.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DFRSServerInterface#editFlightRecord(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized String editFlightRecord(String recordID,
			String fieldName, String newValue) {
		for (Flight flight : flightsList) {
			if (flight.getRecordId().equals(recordID)) {
				switch (fieldName) {
				case "economySeatCount":
					flight.setEconomySeatCount(Integer.getInteger(newValue));
					break;
				case "businessSeatCount":
					flight.setBusinessSeatCount(Integer.getInteger(newValue));
					break;
				case "fitClassSeatCount":
					flight.setFitClassSeatCount(Integer.getInteger(newValue));
					break;
				case "source":
					flight.setSource(City.valueOf(newValue));
					break;
				case "destination":
					flight.setDestination(City.valueOf(newValue));
					break;
				case "date":
					flight.setDate(newValue);
					break;
				case"deleteFlight":
					flight.equals(null);
					
				}
				break;
			}
		}
		return "success";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DFRSServerInterface#addFlight(int, int, int, City, java.lang.String)
	 */
	@Override
	public String addFlight(int economySeatCount, int businessSeatCount,
			int fitClassSeatCount, String destinationStr, String date) {
		City destination = City.valueOf(destinationStr);
		Flight flight = new Flight(economySeatCount, businessSeatCount,
				fitClassSeatCount, serverCity, destination, date);
		flightsList.add(flight);
		return flight.getRecordId();
	}
	
	public void resetCount(){
		database = new HashMap<>();
		flightsList = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				aSocket = new DatagramSocket(serverPort);
				byte[] buffer = new byte[10000];
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				aSocket.receive(request);

				String msgRecieved = new String(buffer);
				System.out.println("SOCKET RECIEVE on " + serverCity + ": "
						+ msgRecieved);
				String answer = null;
				if (msgRecieved.startsWith("getBookedFlightCount")) {
					String recordTypeStr = msgRecieved.substring(
							"getBookedFlightCount".length(),
							"getBookedFlightCount".length() + 1);
					if (recordTypeStr.equals("0")) {
						answer = this
								.getBookedFlightCountLoical(SeatType.ECONOMY);
					} else if (recordTypeStr.equals("1")) {
						answer = this
								.getBookedFlightCountLoical(SeatType.BUSINESS);
					} else {
						answer = this.getBookedFlightCountLoical(SeatType.FIRST);
					}
					DatagramPacket reply = new DatagramPacket(
							answer.getBytes(), answer.length(),
							request.getAddress(), request.getPort());
					aSocket.send(reply);
				}
				else if(msgRecieved.startsWith("TransferPassenger:")){
					String passengerStr = new String(removeStringFromArray("TransferPassenger:",buffer));
					////////
					System.out.println("TransferPassenger Object is :"+passengerStr);
					ByteArrayInputStream bis = new ByteArrayInputStream(removeStringFromArray("TransferPassenger:",buffer));
					ObjectInput in = null;
					try {
					  in = new ObjectInputStream(bis);
					  Passenger passenger = (Passenger) in.readObject(); 
					  String recordId = this.bookFlight(passenger.getFirstName(), passenger.getLastName(), passenger.getAddress(), passenger.getPhone(), passenger.getDestination().toString(), passenger.getDate(), passenger.getFlightClass().toString());
					  if(recordId != null ){
						  answer = "success";
						  DatagramPacket reply = new DatagramPacket(
									answer.getBytes(), answer.length(),
									request.getAddress(), request.getPort());
							aSocket.send(reply);
					  }
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					  try {
					    if (in != null) {
					      in.close();
					    }
					  } catch (IOException ex) {
					    // ignore close exception
					  }
					}
					////////
					
					
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				aSocket.close();
			} finally {
				if (aSocket != null)
					aSocket.close();
			}

		}

	}

	@Override
	public synchronized String transferReservation(String recordId, String CurrentCityStr,
			String OtherCityStr) {
		City currentCity = City.valueOf(CurrentCityStr);
		City otherCity = City.valueOf(OtherCityStr);
		// 1- search for the passenger in the database of this city
		Passenger passenger = searchForPassenger(recordId);

		if (passenger != null) {
			// 2- ask the other server to register the passenger
			boolean booked = bookFlightOnAnotherServerUsingUDP(passenger,otherCity);
			// 3- remove the user from our server
			if (booked) {
				removePassenger(recordId);
			}
		}
		return "success";

	}

	private Passenger searchForPassenger(String recordId) {
		for (CopyOnWriteArrayList<Passenger> passengers : database.values()) {
			for (Passenger passsenger : passengers) {
				if (passsenger.getRecordId().equalsIgnoreCase(recordId)) {
					return passsenger;
				}

			}
			
		}
		return null;
	}

	private synchronized void removePassenger(String recordId) {
		for (CopyOnWriteArrayList<Passenger> passengers : database.values()) {
			for (Passenger passsenger : passengers) {
				if (passsenger.getRecordId().equalsIgnoreCase(recordId)) {
					passengers.remove(passsenger);
					for (Flight flight : flightsList) {

						if (flight.getDate().equals(passsenger.getDate())
								&& flight.getDestination() == passsenger.getDestination()
								&& flight.canReserved(passsenger.getFlightClass())) {
							flight.decrementReservedCount(passsenger.getFlightClass());
							return;
						}
					}
					return;
				}

			}
			
		}

	}
	
	private synchronized boolean bookFlightOnAnotherServerUsingUDP(Passenger passenger,City city){
		try {
			String rcvAnswer = null;
				int sendport = 6789 + city.ordinal();

				DatagramSocket rSocket = new DatagramSocket();

				byte[] m = combineArrays("TransferPassenger:".getBytes(),serializeObject(passenger));
				InetAddress aHost = InetAddress.getLocalHost();

				DatagramPacket request = new DatagramPacket(m, m.length,
						aHost, sendport);
				rSocket.send(request);
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer,
						buffer.length);
				rSocket.receive(reply);
				rcvAnswer = new String(reply.getData());

				System.out.println("SOCKET ANSWER RECIEVE on " + serverCity
						+ " from " + city + ": " + rcvAnswer);
				rSocket.close();
				if(rcvAnswer.startsWith("success")){
					return true;
				}
				
			

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		return false;
	}
	

private byte[] serializeObject(Passenger passenger){
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	byte[] bytes = null; 
	ObjectOutput out = null;
	try {
	  out = new ObjectOutputStream(bos);   
	  out.writeObject(passenger);
	  out.flush();
	  bytes = bos.toByteArray();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	  try {
	    bos.close();
	  } catch (IOException ex) {
	    // ignore close exception
	  }
	}
	return bytes;

}

private byte[] combineArrays(byte[] one, byte[] two){

	byte[] combined = new byte[one.length + two.length];

	for (int i = 0; i < combined.length; ++i)
	{
	    combined[i] = i < one.length ? one[i] : two[i - one.length];
	}
	return combined;
}
	private byte[] removeStringFromArray(String input,byte[] bArray){
		int length = input.getBytes().length;
		byte[] newArray = new byte[bArray.length -length ];
		int j =0;
		for (int i = length;i<bArray.length;i++){
			newArray[j] = bArray[i];
			j++;

		}
		return newArray;
	}


	public void startServer(String serverName, String UDPPort, String[] orbArgs)  {
		orb = ORB.init(orbArgs, null);
		DFRSServer server = null ;
		try {
			server= initServer(orb,orbArgs,serverName);
		}
		catch (InvalidName | AdapterInactive | ServantNotActive | WrongPolicy | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		udpServerThread = new Thread(server);
		udpServerThread.start();
		Thread orbRunThread = new Thread(new Runnable() {
				   public void run() {
					   orb.run();
						   }
					  });

	}
	public DFRSServer initServer(ORB orb, String[] args, String serverName) throws InvalidName, AdapterInactive,   ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {
		DFRSServer server = null;
		if(serverName.equalsIgnoreCase("montreal")){
			server = new DFRSServer(City.MONTREAL);
			System.out.println("CORBA ' "+serverName+"' is init:) :) :) This is not the RM!!");
		}
		else if(serverName.equalsIgnoreCase("washington")){
			server = new DFRSServer(City.WASHINGTON);
			System.out.println("CORBA ' "+serverName+"' is init:) :) :) This is not the RM!!");
		}
		else{
			server = new DFRSServer(City.NEW_DELHI);
			System.out.println("CORBA ' "+serverName+"' is init:) :) :) This is not the RM!!");
		}
		try{
			POA rootPOA;

			rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

			byte[] idMontreal = rootPOA.activate_object(server);
			convertObjectToFile(idMontreal, serverName, orb, rootPOA);
			rootPOA.the_POAManager().activate();
			System.out.println("THis is the SERVER running");
			System.out.println("Corba: '" + serverName + "' server is up and running");


		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServantAlreadyActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPolicy e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdapterInactive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectNotActive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;

	}
	private static void convertObjectToFile(byte[] id,String filename,ORB orb,POA rootPOA) throws FileNotFoundException, ObjectNotActive, WrongPolicy{
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
		String ior = orb.object_to_string(ref);

		PrintWriter file = new PrintWriter(filename);
		file.print(ior);
		file.close();
	}
	public void shutDownServer()
	{  if(udpServerThread != null)
	{
		aSocket.close();
		udpServerThread.stop();
	}  if(orb != null)
	{
		orb.shutdown(false);
	}
	}
}
