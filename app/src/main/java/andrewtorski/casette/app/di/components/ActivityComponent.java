package andrewtorski.casette.app.di.components;

import android.app.Activity;

import andrewtorski.casette.app.di.PerActivity;
import andrewtorski.casette.app.di.modules.ActivityModule;
import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p/>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link andrewtorski.casette.app.di.PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
