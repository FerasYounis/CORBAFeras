package DFRSApp;

import java.io.Serializable;
import java.util.UUID;

public class Passenger implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4575375139164741007L;
	private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private City destination;
    private String date;
    private SeatType flightClass;
    private String recordId;


    public Passenger(String firstName, String lastName, String address, String phone, City destination, String date, SeatType flightClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.destination = destination;
        this.date = date;
        this.flightClass = flightClass;
        recordId = UUID.randomUUID().toString();

    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public City getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public SeatType getFlightClass() {
        return flightClass;
    }

    public String getRecordId() {
        return recordId;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", flightClass='" + flightClass + '\'' +
                ", recordId='" + recordId + '\'' +
                '}';
    }

}
