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

    public static void main(String args[])
            throws InterruptedException, FileNotFoundException {

        // Delay, in milliseconds before
        // we interrupt MessageLoop
        // thread (default one hour).
        long patience = 1000 * 60 * 60;

        // If command line argument
        // present, gives patience
        // in seconds.
        if (args.length > 0) {
            try {
                patience = Long.parseLong(args[0]) * 1000;
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }

        threadMessage("Starting DFRSmain thread");
        long startTime = System.currentTimeMillis();
        DFRSServerProxy proxy = new DFRSServerProxy();

        
        //proxy.addFlight(new Manager("4444", City.NEW_DELHI), 10, 10, 10, City.WASHINGTON, "11/14");
       // proxy.addFlight(new Manager("4444", City.MONTREAL), 10, 10, 10, City.WASHINGTON, "11/14");
        /*
        */
        //proxy.addFlight(new Manager("5555", City.NEW_DELHI), 10, 10, 10, City.MONTREAL, "10/10");
       // proxy.addFlight(new Manager("5555", City.NEW_DELHI), 10, 10, 10, City.WASHINGTON, "10/10");
        // proxy.addFlight(new Manager("6666", City.MONTREAL), 10, 10, 10, City.WASHINGTON, "10/10");
        //proxy.addFlight(new Manager("6666", City.MONTREAL), 10, 10, 10, City.NEW_DELHI, "10/10");
/*
        Client c = new Client("123",City.MONTREAL);
        Thread t = new Thread(new ClientThread(c,proxy));
        t.start();
        Thread t2 = new Thread(new ClientThread(new Client("124",City.NEW_DELHI),proxy));
        t2.start();
        Thread t3 = new Thread(new ClientThread(new Client("125",City.WASHINGTON),proxy));
        t3.start();
        Thread t7 = new Thread(new ClientThread(new Client("126",City.MONTREAL),proxy));
        t7.start();
        Thread t4 = new Thread(new ClientThread(new Manager("222",City.MONTREAL),proxy));
        t4.start();
        Thread t5 = new Thread(new ClientThread(new Manager("333",City.WASHINGTON),proxy));
        t5.start();
        Thread t6 = new Thread(new ClientThread(new Manager("444",City.NEW_DELHI),proxy));
        t6.start();
        threadMessage("Waiting for MessageLoop thread to finish");
        // loop until MessageLoop
        // thread exits
        while (t.isAlive()) {
            threadMessage("Still waiting...");
            // Wait maximum of 1 second
            // for MessageLoop thread
            // to finish.
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > patience)
                    && t.isAlive()) {
                threadMessage("Tired of waiting!");
                t.interrupt();
                // Shouldn't be long now
                // -- wait indefinitely
                t.join();
            }
        }
        threadMessage("Finally!");

        */

        boolean exit = false;
        Manager maMTL = new Manager("111", City.MONTREAL);
        Manager maWDC = new Manager("222", City.WASHINGTON);
        Manager maNDH = new Manager("333", City.NEW_DELHI);
        Client cMTL = new Client("199", City.MONTREAL);
        Client cWDC = new Client("299", City.WASHINGTON);
        Client cNDH = new Client("399", City.NEW_DELHI);
        File file = new File("answers.txt");
        //String flightid = proxy.bookFlight(cMTL,"first","first","addr","514",City.NEW_DELHI,"10/10",SeatType.ECONOMY);
        //proxy.bookFlight(cWDC,"second","second","addr2","450",City.MONTREAL,"10/10",SeatType.ECONOMY);
//proxy.transferReservation(cMTL,flightid,"MONTREAL","WASHINGTON");
        Scanner sd=null;
		sd = new Scanner(System.in);
		//sd = new Scanner(file);
        do {
            System.out.println("1.AddFlight");
            System.out.println("2.BookFlight");
            System.out.println("3.GetCount");
            System.out.println("4.Transfer");
            System.out.println("5.ResetCount");
            System.out.println("choose one!");
            

            System.out.println("enter your choice");
            int num = sd.nextInt();
            switch (num) {
                case 1:
                    //Scanner sc = new Scanner(System.in);
                    System.out.println("Hello manager please get started with adding" +
                            "a new flight please answer the following question?\n");

                    //Scanner sc1 = new Scanner(System.in);

                    //Scanner sc2 = new Scanner(System.in);

                    //Scanner sc3 = new Scanner(System.in);
                    //Scanner sc01 = new Scanner(System.in);
                    
                    System.out.println("WHO are You?\n maMTL\n maWDC\n maNDH");
                    String managerStr = sd.next();
                   
                    System.out.println("How Many Economy seats?\n");
                    int econ = sd.nextInt();
                    System.out.println("How Many Business seats?\n");
                    int bus = sd.nextInt();
                    System.out.println("How Many First seats?\n");
                    int fit = sd.nextInt();

                    //Scanner sc4 = new Scanner(System.in);
                    System.out
                            .println("Enter Destination: MONTREAL \n WASHINGTON \n NEW_DELHI \n");
                    String citystr = sd.next();
                    City destination = City.valueOf(citystr);

                    //Scanner sc5 = new Scanner(System.in);
                    System.out.println("Choose a date?\n");
                    String dat = sd.next();
                    String flightId = "";
                    if (managerStr.equals("maMTL")) {
                        flightId =proxy.addFlight(maMTL, econ, bus, fit, destination, dat);
                    } else if (managerStr.equals("maNDH")) {
                        flightId =proxy.addFlight(maNDH, econ, bus, fit, destination, dat);
                    } else {
                        flightId= proxy.addFlight(maWDC, econ, bus, fit, destination, dat);

                    }
                    System.out.println("Flight id is :"+flightId);
                    break;

                case 2:

                    System.out.println("Hello Client  please book your flight by answering thses questions");
                    System.out.println("\n");
                    //Scanner sc06 = new Scanner(System.in);
                    //Scanner sc6 = new Scanner(System.in);
                    System.out.println("Which Client are you?\n cWDC\n cMTL\n cNDH ");
                    String CleintCity = sd.next();
                    System.out.println("what is your first name\n");
                    String first1 = sd.next();
                    System.out.println("what is your last name\n");
                    String last1 = sd.next();
                    System.out.println("what is address \n");
                    String address = sd.next();
                    System.out.println("what is phone \n");
                    String phone = sd.next();
                    System.out.println("what is the date\n");
                    String dat1 = sd.next();
                    System.out.println("what a destibation \n");
                    String citystr2 = sd.next();
                    City destination2 = City.valueOf(citystr2);
                    System.out.println("what a Seat Type \n");
                    String flightClassStr = sd.next();
                    SeatType flightClass = SeatType.valueOf(flightClassStr);
                    String bookingId = "";
                    if (CleintCity.equals("cWDC")) {
                        bookingId=proxy.bookFlight(cWDC, first1, last1, address, phone, destination2, dat1, flightClass);
                    } else if (CleintCity.equals("cMTL")) {
                        bookingId=proxy.bookFlight(cMTL, first1, last1, address, phone, destination2, dat1, flightClass);

                    } else {
                        bookingId=proxy.bookFlight(cNDH, first1, last1, address, phone, destination2, dat1, flightClass);

                    }
                    System.out.println("Flight booking id is :"+bookingId);
                    break;

                case 3:
                    //Scanner sc7 = new Scanner(System.in);
                    System.out.println("Hello Manager1 \n GetCount of all flight\n");
                    System.out.println("\n");
                    System.out.println("what is the Seat Type \n");
                    String flightClassStr4 = sd.next();
                    System.out.println("WHO are You?\n maMTL\n maWDC\n maNDW");
                    String managerCity = sd.next();
                    SeatType flightClass4 = SeatType.valueOf(flightClassStr4);
                    if (managerCity.equals("maMTL")){
                    	String flightCount = proxy.getBookedFlightCount(maMTL, flightClass4);
                    	System.out.println("total flight count for class :" + flightClassStr4 + " :" + flightCount);
                    }else if (managerCity.equals("maWDC" )){
                    	String flightCount = proxy.getBookedFlightCount(maWDC, flightClass4);
                        System.out.println("total flight count for class :" + flightClassStr4 + " :" + flightCount);
                    }else {
                    	String flightCount = proxy.getBookedFlightCount(maNDH, flightClass4);
                        System.out.println("total flight count for class :" + flightClassStr4 + " :" + flightCount);
                    }
                    break;

                case 4:
                    //Scanner sc8 = new Scanner(System.in);
                    System.out.println("please answer the following questions to transfer a flight");
                    System.out.println("you is the flight record id ?\n");
                    String recordId = sd.next();
                    System.out.println("what is the current CIty \n");
                    String Currentstr = sd.next();
                    System.out.println("what is the other CIty \n");
                    String otherstr = sd.next();


                    proxy.transferReservation(cMTL, recordId, Currentstr,
                            otherstr);
                    break;

                case 5:
                    System.out.println("This is will reset all count");
                    System.out.println("WHO are You?\n maMTL\n maWDC\n maNDW");
                    String managerCity2 = sd.next();
                    proxy.resetCount(getUserFromStr(managerCity2));
                    
                    break;
            }
        } while (!exit
        		//&& sd.hasNext()
        		);
    }

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


