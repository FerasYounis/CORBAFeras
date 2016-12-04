package DFRSApp;

public interface SystemUser {
    public String getFullId();

    public SystemUserType getUserType();

    public City getLocation();

    public void logUserOperation(String msg);
}
