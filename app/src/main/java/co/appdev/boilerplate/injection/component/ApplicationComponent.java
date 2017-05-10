package co.appdev.boilerplate.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.appdev.boilerplate.data.DataManager;
import co.appdev.boilerplate.data.local.DatabaseRealm;
import co.appdev.boilerplate.data.local.PreferencesHelper;
import co.appdev.boilerplate.data.remote.RibotsService;
import co.appdev.boilerplate.injection.ApplicationContext;
import co.appdev.boilerplate.injection.module.ApplicationModule;
import co.appdev.boilerplate.util.RxEventBus;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    RxEventBus eventBus();
    DatabaseRealm databaseRealm();

}
