package andrewtorski.casette.app;

import android.app.Application;

import andrewtorski.casette.app.di.components.ApplicationComponent;
import andrewtorski.casette.app.di.components.DaggerApplicationComponent;
import andrewtorski.casette.app.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
