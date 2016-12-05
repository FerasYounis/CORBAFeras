package DFRSApp;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DFRSmain {

	public void initServer(ORB orb, String[] args, String serverName) throws InvalidName, AdapterInactive,   ServantNotActive, WrongPolicy, org.omg.CosNaming.NamingContextPackage.InvalidName, NotFound, CannotProceed {   
		  POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));  
		  // activate the POAManager  
		  rootpoa.the_POAManager().activate();  this.setORB(orb);  
		  // get object reference from the servant  
		  org.omg.CORBA.Object ref = rootpoa.servant_to_reference(this);  
		  FlightServerInterface href = FlightServerInterfaceHelper.narrow(ref);  
		  org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");  
		  NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);  
		  NameComponent path[] = ncRef.to_name(serverName);  ncRef.rebind(path, href);  
		  this.serverLocation = serverName;  System.out.println("Corba: '" + serverLocation + "' server is up and running"); 
		  }
	
    static void threadMessage(String message) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }

    private SystemUser user;

    
    
 

    private static SystemUser getUserFromStr(String userStr){
        if (userStr.equals("maMTL")){
        	return new Manager("111", City.MONTREAL);
        }else if (userStr.equals("maWDC" )){
        	return new Manager("222", City.WASHINGTON);
        }else {
        	return new Manager("333", City.NEW_DELHI);
        }
    }
}


