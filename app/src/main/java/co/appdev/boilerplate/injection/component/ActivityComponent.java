package co.appdev.boilerplate.injection.component;

import co.appdev.boilerplate.injection.PerActivity;
import co.appdev.boilerplate.injection.module.ActivityModule;
import co.appdev.boilerplate.ui.main.MainActivity;
import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
