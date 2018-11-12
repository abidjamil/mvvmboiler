package co.appdev.boilerplate.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.data.local.DatabaseRealm;
import co.appdev.boilerplate.data.local.SharedPrefHelper;
import co.appdev.boilerplate.data.remote.ApiService;

@Singleton
public class DataManager {

    private ApiService mApiService;
    private DatabaseRealm databaseRealm;
    private SharedPrefHelper sharedPrefHelper;

    @Inject
    public DataManager(ApiService apiService, DatabaseRealm databaseRealm, SharedPrefHelper sharedPrefHelper) {
        this.mApiService = apiService;
        this.databaseRealm = databaseRealm;
        this.sharedPrefHelper = sharedPrefHelper;
    }

    public ApiService getmApiService() {
        return mApiService;
    }

    public DatabaseRealm getDatabaseRealm() {
        return databaseRealm;
    }

    public SharedPrefHelper getSharedPrefHelper() {
        return sharedPrefHelper;
    }
}
