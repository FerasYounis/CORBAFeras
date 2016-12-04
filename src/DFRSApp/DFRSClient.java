package DFRSApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.omg.CORBA.ORB;



public class DFRSClient {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ORB orb = ORB.init(args, null);
		
		BufferedReader br = new BufferedReader( new FileReader("ior.txt"));
		String ior = br.readLine();
		br.close();
		
		org.omg.CORBA.Object o = orb.string_to_object(ior);
		
		DFRS aServer = DFRSHelper.narrow(o);
}
}
