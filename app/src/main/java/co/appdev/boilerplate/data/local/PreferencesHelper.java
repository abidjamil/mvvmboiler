package co.appdev.boilerplate.data.local;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.injection.ApplicationContext;


@Singleton
public class PreferencesHelper implements UserDataHelper{

    private Context context;
    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        this.context=context;
    }


    @Override
    public void clearAllPref() {
        PreferencesDataHelper.clearPref(context);
    }

    @Override
    public void storeClientId(String client_id) {
        PreferencesDataHelper.store(context, PreferencesDataHelper.PersistenceKey.CLIENT_ID, client_id);
    }

    @Override
    public String getClientId() {
        return PreferencesDataHelper.retrieve(context, PreferencesDataHelper.PersistenceKey.CLIENT_ID);
    }
}
