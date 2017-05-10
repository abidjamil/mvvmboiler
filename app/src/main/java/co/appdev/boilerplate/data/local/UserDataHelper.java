package co.appdev.boilerplate.data.local;

public interface UserDataHelper {

    public void clearAllPref();

    public void storeClientId(String client_id);

    public String getClientId();

}
