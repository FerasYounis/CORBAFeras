package DFRSApp;


public class ClientThread implements Runnable{
    private SystemUser user;
    private DFRSServerProxy proxy;
    public ClientThread(SystemUser user,DFRSServerProxy proxy){
        this.user = user;
        this.proxy = proxy;
    }
    public void run() {

        try {
            while(true) {
                // Pause for 4 seconds
                Thread.sleep((int)(Math.random() * 1000));
                // Print a message
                if(user.getUserType() == SystemUserType.MANAGER){
                    proxy.getBookedFlightCount(user,SeatType.ECONOMY);

                }
                else{
                    if(user.getLocation() != City.MONTREAL) {
                        String recordId = proxy.bookFlight(user, "first", "last", "address", "phone", City.MONTREAL, "10/10", SeatType.ECONOMY);
                    }
                    else {
                        String recordId2 = proxy.bookFlight(user, "first2", "last2", "address2", "phone2", City.NEW_DELHI, "10/10", SeatType.ECONOMY);
                    }

                    //proxy.transferReservation(user, recordId, user.getLocation().toString(), City.NEW_DELHI.toString());

                }
            }
        } catch (InterruptedException e) {

        }
    }
}
