package DFRSApp;

/**
 * Interface definition: DFRS.
 * 
 * @author OpenORB Compiler
 */
public interface DFRSOperations
{
    /**
     * Operation bookFlight
     */
    public String bookFlight(String firstName, String lastName, String address, String phone, String destination, String date, String flightClass);

    /**
     * Operation getBookedFlightCount
     */
    public String getBookedFlightCount(String recordType);

    /**
     * Operation editFlightRecord
     */
    public void editFlightRecord(String recordID, String fieldName, String newValue);

    /**
     * Operation addFlight
     */
    public String addFlight(int economySeatCount, int businessSeatCount, int fitClassSeatCount, String destination, String date);

    /**
     * Operation transferReservation
     */
    public void transferReservation(String recordId, String CurrentCity, String OtherCity);

    /**
     * Operation resetCount
     */
    public void resetCount();

}
