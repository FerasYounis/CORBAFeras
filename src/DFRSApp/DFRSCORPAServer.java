package DFRSApp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;



public class DFRSCORPAServer implements Runnable {

	
	
	
	public static void main(String[] args) throws ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive {
		// TODO Auto-generated method stub
	    final DFRSServer montrealServer = new DFRSServer(City.MONTREAL);
	    final DFRSServer nyServer = new DFRSServer(City.WASHINGTON);
	    final DFRSServer ndServer = new DFRSServer(City.NEW_DELHI);
	      	Thread t = new Thread((DFRSServer)montrealServer);
	        t.start();
	        
	        Thread t2 = new Thread((DFRSServer)nyServer);
	        t2.start();
	        
	        
	        Thread t3 = new Thread((DFRSServer)ndServer);
	        t3.start();
	        //register the servers on the CORBA
			try {
				
			ORB orb = ORB.init(new String[0],null);
			POA rootPOA;
			
			rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			
			
			byte[] idMontreal = rootPOA.activate_object(montrealServer);
			byte[] idNy = rootPOA.activate_object(nyServer);
			byte[] idNd = rootPOA.activate_object(ndServer);

			convertObjectToFile(idMontreal, "montreal.txt", orb, rootPOA);
			convertObjectToFile(idNy, "ny.txt", orb, rootPOA);
			convertObjectToFile(idNd, "nd.txt", orb, rootPOA);
			rootPOA.the_POAManager().activate();
			System.out.println("THis is the SERVER running");
			orb.run();
					
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
	    

	}

private static void convertObjectToFile(byte[] id,String filename,ORB orb,POA rootPOA) throws FileNotFoundException, ObjectNotActive, WrongPolicy{
	org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
	String ior = orb.object_to_string(ref);
	
	PrintWriter file = new PrintWriter(filename);
	file.print(ior);
	file.close();
}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

