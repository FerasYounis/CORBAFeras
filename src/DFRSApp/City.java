package DFRSApp;

import java.io.Serializable;

public enum City implements Serializable  {
	
    MONTREAL("Montreal", "MTL"),
    WASHINGTON("Washington", "WDC"),
    NEW_DELHI("NewDelhi", "NDL");
    
    

    private String cityName;


    private String cityApprviation;

    City(String cityName, String cityApprviation) {
        this.cityName = cityName;
        this.cityApprviation = cityApprviation;
    }

    public String getCityName() {
    	int x;
        return this.cityName;
    }

    public String getCityApprviation() {
        return cityApprviation;
    }
}
