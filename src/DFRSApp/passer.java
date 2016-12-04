package DFRSApp;

public class passer {

	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
		String citySeq = null;
		City city;
		String flightClassSeq = null;
		SeatType flightClass;
	
	
	if (citySeq.equals("Montreal")) {
       city = City.valueOf("MONTREAL");
    } else if (citySeq.equals("WASHINGTON")) {
    	city = City.valueOf("WASHINGTON");
	} else {
		city = City.valueOf("NEW_DELHI");
	}
	if (flightClassSeq.equals("Economy")) {
		flightClass = SeatType.valueOf("ECONOMY");
	    } else if (flightClassSeq.equals("Business")) {
	    	flightClass = SeatType.valueOf("BUSINESS");
		} else {
			flightClass = SeatType.valueOf("FIT");
	
	}
}
}
	

