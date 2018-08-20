package co.appdev.boilerplate.data.local;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.appdev.boilerplate.data.model.Users;
import co.appdev.boilerplate.injection.ApplicationContext;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

    RealmConfiguration realmConfiguration;

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

    public <T extends RealmObject> T add(T model) {
        Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.copyToRealm(model);
        realm.commitTransaction();
        return model;
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        return getRealmInstance().where(clazz).findAll();
    }

    public void close() {
        getRealmInstance().close();
    }

    public Observable<Users> setUsers(final List<Users> users) {
        return Observable.create(new ObservableOnSubscribe<Users>() {
            @Override
            public void subscribe(ObservableEmitter<Users> e) throws Exception {
                if(e.isDisposed()) return;
                Realm realm = getRealmInstance();
                realm.beginTransaction();
                for (Users user : users) {
                    realm.copyToRealm(user);
                }
                realm.commitTransaction();
            }
        });
    }

    public Observable<List<Users>> getUsers() {
        return Observable.just(getRealmInstance().copyFromRealm(getRealmInstance().where(Users.class).findAll()));

    }
}
