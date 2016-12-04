package DFRSApp;

public class FlightRoute {

    private City source;
    private City destination;


    private int maxFlights;


    private int currentFlightsCounts;

    public FlightRoute(int maxFlights, City source, City destination) {
        this.maxFlights = maxFlights;
        this.source = source;
        this.destination = destination;
    }

    public City getSource() {
        return source;
    }

    public City getDestination() {
        return destination;
    }

    public int getMaxFlights() {
        return maxFlights;
    }

    public void setMaxFlights(int maxFlights) {
        this.maxFlights = maxFlights;
    }

    public int getCurrentFlightsCounts() {
        return currentFlightsCounts;
    }

    public void setCurrentFlightsCounts(int currentFlightsCounts) {
        this.currentFlightsCounts = currentFlightsCounts;
    }

    public void incrementCurrentFlightsCounts() {
        this.currentFlightsCounts++;
    }

}
