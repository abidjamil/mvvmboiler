package co.appdev.boilerplate.data.local;

public interface UserDataHelper {

    void storeIsLogin(boolean is_login);

    boolean getIsLogin();

    void clearAllPref();
}
