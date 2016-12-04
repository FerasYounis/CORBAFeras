package DFRSApp;

import java.io.IOException;
import java.util.logging.*;

public class Client implements SystemUser {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private String id;
    private City city;

    public Client(String id, City city) {
        this.id = id;
        this.city = city;
        try {
            FileHandler fileHandler = new FileHandler(getId() + "_client_logger.log", false);
            fileHandler.setFormatter(new SimpleFormatter());
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (SecurityException | IOException e) {
            //do nothing
        }
    }


    public String getId() {
        return id;
    }

    public String getFullId() {
        return id;
    }

    @Override
    public SystemUserType getUserType() {
        return SystemUserType.CLIENT;
    }

    @Override
    public City getLocation() {
        return city;
    }

    @Override
    public void logUserOperation(String msg) {
        LOGGER.log(Level.INFO, "user performed operation : " + msg);
    }
}
