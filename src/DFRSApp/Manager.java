package DFRSApp;

import java.io.IOException;
import java.util.logging.*;

public class Manager implements SystemUser {
    private static final Logger LOGGER = Logger.getLogger(Manager.class.getName());
    private City city;
    private String id;

    public Manager(String id,City city) {
        this.city = city;
        this.id = id;
        try {
            FileHandler fileHandler = new FileHandler(getFullId() + "_manager_logger.log", false);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(consoleHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (SecurityException | IOException e) {
            //do nothing
        }
    }

    public String getFullId() {
        return city.getCityApprviation() + id;
    }

    @Override
    public SystemUserType getUserType() {
        return SystemUserType.MANAGER;
    }

    @Override
    public City getLocation() {
        return city;
    }


    public String getId() {
        return id;
    }

    @Override
    public void logUserOperation(String msg) {
        LOGGER.log(Level.INFO, "Manager performed operation : " + msg);
    }
}
