package ReplicaManager;

import java.util.logging.Logger;

import Models.Enums;
import Replica.ReplicaListner;
import StaticContent.StaticContent;
import Utilities.CLogger;

/*
 * Reason of this class:
 * 1) Receive UDP requests from Front End (Result is incorrect, replica is down).
 * 2) If replica is down, restart the respective replica and re-initiate the restore process i.e. logs.) (tIMEoUT PERIOD should be kept in mind)
 * 3) If results are incorrect, it increment the error counter and if the counter > 3, restart the replica as mentioned in step 2.
 * 4) Receive heart beat signals from respective replica (after 30 seconds each).
 * 5) Create instance of its replica but communicate through UDP ONLY.
 */

public class ReplicaManagerMain {
	private static CLogger clogger;
	private final static Logger LOGGER = Logger.getLogger(ReplicaManagerMain.class.getName());

	public static void main(String[] args) {
		String msg = "";
		try {
			// initialize logger
			clogger = new CLogger(LOGGER, "ReplicaManager/ReplicaManager.log");
			msg = "Replica is UP!";
			clogger.log(msg);
			System.out.println(msg);

			// Start UDP Server
			ReplicaListner server = new ReplicaListner(clogger, StaticContent.REPLICA_ULAN_lISTENING_PORT,
					Enums.UDPSender.ReplicaUlan);
			server.start();
			// server.executeTestMessage();
			server.join();

		} catch (Exception e) {
			System.out.println("Sequencer Exception: " + e.getMessage());
		}
	}
}
