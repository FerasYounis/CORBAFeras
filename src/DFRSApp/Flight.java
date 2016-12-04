package DFRSApp;

import java.util.UUID;

public class Flight {

    private String recordId;
    private int economySeatCount;
    private int businessSeatCount;
    private int fitClassSeatCount;
    private int economySeatReserved;
    private int businessSeatReserved;
    private int fitClassSeatReserved;
    private City source;
    private City destination;
    private String date;


    public Flight(int economySeatCount, int businessSeatCount, int fitClassSeatCount,
                  City source, City destination, String date) {
        this.economySeatCount = economySeatCount;
        this.businessSeatCount = businessSeatCount;
        this.fitClassSeatCount = fitClassSeatCount;
        this.source = source;
        this.destination = destination;
        this.date = date;
        recordId = UUID.randomUUID().toString();
    }

    public Flight() {
        recordId = UUID.randomUUID().toString();
    }

    public String getRecordId() {
        return recordId;
    }

    public int getEconomySeatCount() {
        return economySeatCount;
    }

    public void setEconomySeatCount(int economySeatCount) {
        this.economySeatCount = economySeatCount;
    }

    public int getBusinessSeatCount() {
        return businessSeatCount;
    }

    public void setBusinessSeatCount(int businessSeatCount) {
        this.businessSeatCount = businessSeatCount;
    }

    public int getFitClassSeatCount() {
        return fitClassSeatCount;
    }

    public void setFitClassSeatCount(int fitClassSeatCount) {
        this.fitClassSeatCount = fitClassSeatCount;
    }

    public City getSource() {
        return source;
    }

    public void setSource(City source) {
        this.source = source;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public synchronized int incrementReservedCount(SeatType seatType) {
        if (seatType == SeatType.BUSINESS) {
            this.businessSeatReserved++;
            return businessSeatReserved;
        } else if (seatType == SeatType.ECONOMY) {
            this.economySeatReserved++;
            return economySeatReserved;
        } else if (seatType == SeatType.FIT) {
            this.fitClassSeatReserved++;
            return fitClassSeatReserved;
        }
        return 0;
    }
    public synchronized int decrementReservedCount(SeatType seatType) {
        if (seatType == SeatType.BUSINESS) {
            if(businessSeatReserved >1) {
                this.businessSeatReserved--;
            }
            else {
                businessSeatReserved = 0;
            }
            return businessSeatReserved;
        } else if (seatType == SeatType.ECONOMY) {
            if(economySeatReserved >1) {
                this.economySeatReserved--;
            }
            else {
                economySeatReserved = 0;
            }
            return economySeatReserved;
        } else if (seatType == SeatType.FIT) {
            if(fitClassSeatReserved >1) {
                this.fitClassSeatReserved--;
            }
            else {
                fitClassSeatReserved = 0;
            }
            return fitClassSeatReserved;
        }
        return 0;
    }
    public int getReservedCount(SeatType seatType) {
        if (seatType == SeatType.BUSINESS) {
            return businessSeatReserved;
        } else if (seatType == SeatType.ECONOMY) {
            return economySeatReserved;
        } else if (seatType == SeatType.FIT) {
            return fitClassSeatReserved;
        }
        return 0;
    }
    public synchronized boolean canReserved(SeatType seatType) {
        if (seatType == SeatType.BUSINESS) {
            return businessSeatReserved < businessSeatCount;
        } else if (seatType == SeatType.ECONOMY) {
            return economySeatReserved < economySeatCount;
        } else if (seatType == SeatType.FIT) {
            return fitClassSeatReserved < fitClassSeatCount;
        }
        return false;
    }
}
