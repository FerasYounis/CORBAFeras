package Replica;

import java.util.HashMap;
import java.util.logging.Logger;

import DFRSApp.DFRSCORPAServer;
import DFRSApp.DFRSServer;
import Models.Enums;
import StaticContent.StaticContent;
import Utilities.CLogger;

public class ReplicaMain {
	private static CLogger clogger;
	private final static Logger LOGGER = Logger.getLogger(ReplicaMain.class.getName());
	public static HashMap<String, DFRSServer> servers = new HashMap<String, DFRSServer>();

	/*
	 * Reason of this class: 
	 * 1) Create MTL, WSL, NDH server objects using ORB.
	 * 2) Listen to all incoming messages from sequencer and respective RM. 
	 * 3) Parse the incoming UDP request and call it in the actual server object.
	 * 4) Replica always reply to FrontEnd port that is in UDP Object. 
	 * 5) Any request that comes from Sequencer should be saved in a file as backup so that it can be restored when the replica is started again.
	 */

	public ReplicaMain()
	{
		String msg = "";
		
		String[] corbaArgs = new String[4];
		corbaArgs[0] = "-ORBInitialPort";
		corbaArgs[1] = "1050";
		corbaArgs[2] = "-ORBInitialHost";
		corbaArgs[3] = "localhost";
		
		try {
			// initialize logger
			clogger = new CLogger(LOGGER, "Replica/Replica.log");
			msg = "Replica is UP!";
			clogger.log(msg);
			System.out.println(msg);

			// Start UDP Server
			ReplicaListner server = new ReplicaListner(clogger, StaticContent.RM4_lISTENING_PORT,
					Enums.UDPSender.ReplicaFeras);
			server.start();

			createServerObjects(corbaArgs);
			
			// server.executeTestMessage();
			// server.join();

		} catch (Exception e) {
			System.out.println("Sequencer Exception: " + e.getMessage());
		}
	}


	private void createServerObjects(String[] orbArgs) {
//		FlightServer montreal = new FlightServer();
//		FlightServer washington = new FlightServer();
//		FlightServer newDelhi = new FlightServer();
//
//		montreal.startServer(Enums.FlightCities.Montreal.toString(), "", orbArgs);
//		washington.startServer(Enums.FlightCities.Washington.toString(), "", orbArgs);
//		newDelhi.startServer(Enums.FlightCities.NewDelhi.toString(), "", orbArgs);
//
//		servers.put(Enums.FlightCities.Montreal.toString(), montreal);
//		servers.put(Enums.FlightCities.Washington.toString(), washington);
//		servers.put(Enums.FlightCities.NewDelhi.toString(), newDelhi);
	}
	
	public static void main(String[] args) {
	}
}
