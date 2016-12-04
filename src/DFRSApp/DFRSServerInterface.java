package DFRSApp;


import java.rmi.Remote;
import java.rmi.RemoteException;
public interface DFRSServerInterface extends Remote  {

	public String bookFlight(String firstName, String lastName, String address, String phone, City destination, String date,
			SeatType flightClass) throws RemoteException;

	public String getBookedFlightCount(SeatType recordType) throws RemoteException;

	public void editFlightRecord(String recordID, String fieldName, String newValue) throws RemoteException;;
 
	public String addFlight(int economySeatCount, int businessSeatCount, int fitClassSeatCount, City destination, String date) throws RemoteException;

}