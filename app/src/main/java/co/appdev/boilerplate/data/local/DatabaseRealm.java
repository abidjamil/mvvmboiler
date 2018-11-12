package co.appdev.boilerplate.data.local;

import android.content.Context;
import android.os.HandlerThread;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.injection.ApplicationContext;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.reactivex.Observable;

/**
 * Created by ahsan on 4/24/17.
 */
@Singleton
public class DatabaseRealm {

    private Context mContext;
    private RealmConfiguration realmConfiguration;

    @Inject
    public DatabaseRealm(@ApplicationContext Context context) {
        this.mContext = context;
        setup();
    }

    private void setup() {
        if (realmConfiguration == null) {
            Realm.init(mContext);
            // create your Realm configuration
            realmConfiguration = new RealmConfiguration.
                    Builder().
                    // schemaVersion(2).
                            deleteRealmIfMigrationNeeded().
                            build();
            Realm.setDefaultConfiguration(realmConfiguration);

        } else {
            throw new IllegalStateException("database already configured");
        }
    }

    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

}
