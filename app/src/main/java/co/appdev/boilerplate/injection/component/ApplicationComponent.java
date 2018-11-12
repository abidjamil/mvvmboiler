package co.appdev.boilerplate.injection.component;

import javax.inject.Singleton;

import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.data.local.DatabaseRealm;
import co.appdev.boilerplate.data.local.SharedPrefHelper;
import co.appdev.boilerplate.injection.ApplicationContext;
import co.appdev.boilerplate.injection.module.ApplicationModule;
import co.appdev.boilerplate.injection.module.NetworkModule;
import co.appdev.boilerplate.injection.module.SharedPreferencesModule;
import co.appdev.boilerplate.services.SyncService;
import co.appdev.boilerplate.ui.base.BaseActivity;
import co.appdev.boilerplate.ui.base.BaseFragment;
import co.appdev.boilerplate.ui.splash.SplashActivity;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,NetworkModule.class, SharedPreferencesModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    DataManager dataManager();
    SharedPrefHelper sharedPrefHelper();
    DatabaseRealm databaseRealm();

    void inject(SyncService syncService);
    void inject(BaseFragment baseFragment);
    void inject(BaseActivity baseActivity);
    void inject(SplashActivity splashActivity);
}
