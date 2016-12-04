package DFRSApp;

import java.io.Serializable;

public enum City implements Serializable  {
	
    MONTREAL("Montreal", "MTL"),
    WASHINGTON("Washington", "WDC"),
    NEW_DELHI("NewDelhi", "ND");

    private String cityName;


    private String cityApprviation;

    City(String cityName, String cityApprviation) {
        this.cityName = cityName;
        this.cityApprviation = cityApprviation;
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getCityApprviation() {
        return cityApprviation;
    }
}
