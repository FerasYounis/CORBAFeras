package DFRSApp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.omg.CORBA.ORB;

public class DFRSServerProxy {

	public DFRSServerProxy() {

	}

	public String bookFlight(SystemUser user, String firstName,
			String lastName, String address, String phone, City destination,
			String date, SeatType flightClass) {
		DFRS server = getServerBasedOnUser(user);
		logUserOperation(user, "bookFlight(" + firstName + "," + lastName + ","
				+ address + "," + phone + "," + destination + "," + date + ","
				+ flightClass + ")");
		String result = null;

		result = server.bookFlight(firstName, lastName, address, phone,
				destination.toString(), date, flightClass.toString());

		logUserOperation(user, "bookFlight result: " + result);
		return result;
	}

	public String getBookedFlightCount(SystemUser user, SeatType recordType) {
		DFRS server = getServerBasedOnUser(user);
		logUserOperation(user, "getBookedFlightCount(" + recordType + ")");
		String result = null;
		result = server.getBookedFlightCount(recordType.toString());

		logUserOperation(user, "getBookedFlightCount result: " + result);
		return result;
	}

	public void editFlightRecord(SystemUser user, String recordID,
			String fieldName, String newValue) {
		DFRS server = getServerBasedOnUser(user);
		logUserOperation(user, "editFlightRecord(" + recordID + "," + fieldName
				+ "," + newValue + ")");

		server.editFlightRecord(recordID, fieldName, newValue);

	}

	public String addFlight(SystemUser user, int economySeatCount,
			int businessSeatCount, int fitClassSeatCount, City destination,
			String date) {
		DFRS server = getServerBasedOnUser(user);
		logUserOperation(user, "addFlight(" + economySeatCount + ","
				+ businessSeatCount + "," + fitClassSeatCount + ","
				+ destination + "," + date + ")");
		String result = null;

		result = server.addFlight(economySeatCount, businessSeatCount,
				fitClassSeatCount, destination.toString(), date);

		logUserOperation(user, "addFlight result: " + result);
		return result;

	}

	public void transferReservation(SystemUser user, String recordId,
			String CurrentCityStr, String OtherCityStr) {
		logUserOperation(user,"ENTER transferReservation");
		City currentCity = City.valueOf(CurrentCityStr);
		if (user.getUserType() == SystemUserType.CLIENT) {
			logUserOperation(user, "transferReservation(" + recordId + ","
					+ CurrentCityStr + "," + OtherCityStr + ")");
			DFRS server = getServerBasedOnCity(currentCity);
			server.transferReservation(recordId, CurrentCityStr, OtherCityStr);
			logUserOperation(user, "transferReservation(Finished )");
		}

	}
	
	public void resetCount(SystemUser user){
		DFRS server = getServerBasedOnUser(user);
		logUserOperation(user, "resetCount("+user.getLocation()+ ")");
		server.resetCount();
	}

	private DFRS getServerBasedOnUser(SystemUser user) {
		try {

			if (user.getLocation() == City.MONTREAL) {
				DFRS stub = getServerFromCorba("montreal.txt");
				return stub;
			} else if (user.getLocation() == City.WASHINGTON) {
				DFRS stub = getServerFromCorba("ny.txt");
				return stub;
			} else {
				DFRS stub = getServerFromCorba("nd.txt");
				return stub;
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return null;

	}

	private DFRS getServerBasedOnCity(City city) {
		try {

			if (city == City.MONTREAL) {
				DFRS stub = getServerFromCorba("montreal.txt");
				return stub;
			} else if (city == City.WASHINGTON) {
				DFRS stub = getServerFromCorba("ny.txt");
				return stub;
			} else {
				DFRS stub = getServerFromCorba("nd.txt");
				return stub;
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return null;

	}

	private void logUserOperation(SystemUser user, String msg) {
		user.logUserOperation(msg);
	}

	private DFRS getServerFromCorba(String filename) {

		ORB orb = ORB.init(new String[0], null);

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));

			String ior = br.readLine();
			br.close();

			org.omg.CORBA.Object o = orb.string_to_object(ior);

			DFRS server = DFRSHelper.narrow(o);
			return server;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
