package DFRSApp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;



public class DFRSCORPAServer implements Runnable {

   


	public void startServer(String serverName, String UDPPort, String[] orbArgs)  {
		ORB orb = ORB.init(orbArgs, null); 
		DFRSServer server = null ;
		try {
			server= initServer(orb,orbArgs,serverName);
		} 
		catch (InvalidName | AdapterInactive | ServantNotActive | WrongPolicy    | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | CannotProceed e) {   
		// TODO Auto-generated catch block  
		e.printStackTrace();  
		} 
		
		new Thread(server).start();
		orb.run();
		
	}
	
	public DFRSServer initServer(ORB orb, String[] args, String serverName) throws InvalidName, AdapterInactive,   ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {   
		DFRSServer server = null;
		if(serverName.equalsIgnoreCase("montreal")){
			server = new DFRSServer(City.MONTREAL);
		}
		else if(serverName.equalsIgnoreCase("washington")){
			server = new DFRSServer(City.WASHINGTON);
		}
		else{
			server = new DFRSServer(City.NEW_DELHI);
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

