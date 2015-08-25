package andrewtorski.casette.app.di.components;

import andrewtorski.casette.app.di.PerActivity;
import andrewtorski.casette.app.di.modules.ActivityModule;
import andrewtorski.casette.app.di.modules.CassetteModule;
import andrewtorski.casette.app.view.fragment.ListCassettesFragment;
import dagger.Component;

/**
 * A scope {@link andrewtorski.casette.app.di.PerActivity} component.
 * Injects Cassette specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CassetteModule.class})
public interface CassetteComponent extends ActivityComponent {
    void inject(ListCassettesFragment userListFragment);
}
