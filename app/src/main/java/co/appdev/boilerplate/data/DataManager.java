package co.appdev.boilerplate.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.data.local.DatabaseRealm;
import co.appdev.boilerplate.data.local.PreferencesHelper;
import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.data.remote.RibotsService;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@Singleton
public class DataManager {

    private final RibotsService mRibotsService;
    private final PreferencesHelper mPreferencesHelper;
    private final DatabaseRealm mDatabaseRealm;

    @Inject
    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,DatabaseRealm databaseRealm) {
        mRibotsService = ribotsService;
        mPreferencesHelper = preferencesHelper;
        this.mDatabaseRealm=databaseRealm;

    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public RibotsService getRibotsService(){return  mRibotsService;}

    public DatabaseRealm getmDatabaseRealm(){
        return mDatabaseRealm;
    }

    public Observable<Users> syncUsers() {
        return mRibotsService.getUsers()
                .concatMap(new Function<List<Users>, ObservableSource<? extends Users>>() {
                    @Override
                    public ObservableSource<? extends Users> apply( List<Users> users)
                            throws Exception {
                        return mDatabaseRealm.setUsers(users);
                    }
                });
    }

    public Observable<List<Users>> getUsers() {
        return mDatabaseRealm.getUsers();
    }

}
