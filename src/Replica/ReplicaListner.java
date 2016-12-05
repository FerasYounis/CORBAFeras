package Replica;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import DFRSApp.DFRSServer;
import Models.Enums;
import Models.UDPMessage;
import StaticContent.StaticContent;
import Utilities.CLogger;
import Utilities.Serializer;

/*
 * Reason of this class: 
 * 1) Listen to all incoming messages from sequencer and respective RM. - DONE
 * 3) Parse the incoming UDP request and call it in the actual server object. - DONE
 * 4) Replica always reply to FrontEnd port that is in UDP Object. - DONE
 * 5) Any request that comes from Sequencer should be saved in a file as backup so that it can be restored when the replica is started again.
 */

public class ReplicaListner implements Runnable {
	private CLogger clogger;
	private DatagramSocket serverSocket;
	private Thread t = null;
	private boolean continueUDP = true;
	private long sequencerNumber = 0;
	int port = 0;
	Enums.UDPSender machineName;

	public ReplicaListner(CLogger clogger, int port, Enums.UDPSender machineName) {
		this.clogger = clogger;
		this.port = port;
		this.machineName = machineName;
	}

	@Override
	public void run() {
		try {
			serverSocket = new DatagramSocket(port);
			byte[] receiveData = new byte[StaticContent.UDP_REQUEST_BUFFER_SIZE];
			// byte[] sendData = new byte[SIZE_BUFFER_REQUEST];
			String msg = machineName.toString() + " UDP Server Is UP!";

			System.out.println(msg);
			clogger.log(msg);
			while (continueUDP) {

				// Read request
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);

				byte[] message = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
				UDPMessage udpMessage = Serializer.deserialize(message);
				UDPMessage ackMessage = null;
				UDPMessage replyMessage = null;
				// Clear received buffer
				receiveData = new byte[StaticContent.UDP_REQUEST_BUFFER_SIZE];
				boolean receivedStatus = false;
				switch (udpMessage.getSender()) {
				case Sequencer:
					receivedStatus = true;
					// Perform Operations.
					msg = "Executing Opernation : " + udpMessage.getOpernation() + ", on Server :"
							+ udpMessage.getServerName();
					System.out.println(msg);
					clogger.log(msg);
					ackMessage = new UDPMessage(this.machineName, udpMessage.getSequencerNumber(),
							udpMessage.getServerName(), udpMessage.getOpernation(), Enums.UDPMessageType.Reply);

					replyMessage = new UDPMessage(this.machineName, udpMessage.getSequencerNumber(),
							udpMessage.getServerName(), udpMessage.getOpernation(), Enums.UDPMessageType.Reply);

					//FlightServer obj = ReplicaMain.servers.get(udpMessage.getServerName());
					DFRSServer obj = ReplicaMain.servers.get(udpMessage.getServerName());
					String res = "";
					switch (udpMessage.getOpernation()) {

					case bookFlight:
						res = obj.bookFlight(udpMessage.getParamters().get("firstName"),
								udpMessage.getParamters().get("lastName"), udpMessage.getParamters().get("address"),
								udpMessage.getParamters().get("phone"), udpMessage.getParamters().get("destination"),
								udpMessage.getParamters().get("date"), udpMessage.getParamters().get("classFlight"));

						replyMessage.setReplyMsg(res);

						break;

					case getBookedFlightCount:
						res = obj.getBookedFlightCount(udpMessage.getParamters().get("recordType"));

						replyMessage.setReplyMsg(res);

						break;

					case editFlightRecord:

						res = obj.editFlightRecord(udpMessage.getParamters().get("recordID"),
								udpMessage.getParamters().get("fieldName"), udpMessage.getParamters().get("newValue"));
						replyMessage.setReplyMsg(res);
						break;

					case transferReservation:
						res = obj.transferReservation(udpMessage.getParamters().get("passengerID"),
								udpMessage.getParamters().get("currentCity"),
								udpMessage.getParamters().get("otherCity"));
						replyMessage.setReplyMsg(res);
						break;

					}
					break;

				case RMUlan:
				case RMSajjad:
				case RMUmer:
				case RMFeras:
					receivedStatus = true;
					msg = udpMessage.getSender().toString() + " Server contacted for HeartBeat.";
					System.out.println(msg);
					clogger.log(msg);
					ackMessage = new UDPMessage(this.machineName, udpMessage.getSequencerNumber(),
							udpMessage.getServerName(), udpMessage.getOpernation(), Enums.UDPMessageType.Reply);
					break;

				default:
					msg = "Unknow Sender : " + udpMessage.getSender();
					System.out.println(msg);
					clogger.log(msg);
					break;
				}

				if (receivedStatus) {
					// Send Acknowledge.
					ackMessage.setStatus(true);
					byte[] sendData = Serializer.serialize(ackMessage);
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
							receivePacket.getAddress(), receivePacket.getPort());
					serverSocket.send(sendPacket);

					// Clear Send buffer
					sendData = new byte[StaticContent.UDP_REQUEST_BUFFER_SIZE];

					// Send reply to FE
					replyMessage.setStatus(true);
					sendData = Serializer.serialize(replyMessage);
					InetAddress IPAddress = InetAddress.getByName(StaticContent.FRONT_END_IP_ADDRESS);
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
							StaticContent.FRONT_END_lISTENING_PORT);
					serverSocket.send(sendPacket);
				}
			}
		} catch (Exception ex) {
			clogger.logException("on starting UDP Server", ex);
			ex.printStackTrace();
		}

	}

	/**
	 * Start the server thread
	 */
	public void start() {
		t = new Thread(this);
		t.start();
	}

	/**
	 * Execute a join on the thread
	 * 
	 * @throws InterruptedException
	 */
	public void join() throws InterruptedException {
		if (t == null)
			return;

		t.join();
	}

	/**
	 * Stop the server thread
	 */
	public void stop() {
		continueUDP = false;
	}

}
