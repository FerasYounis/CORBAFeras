package DFRSApp;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DFRSmain {


	
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


