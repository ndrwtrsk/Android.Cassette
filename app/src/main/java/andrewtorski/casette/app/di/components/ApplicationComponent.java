package andrewtorski.casette.app.di.components;

import android.content.Context;

import javax.inject.Singleton;

import andrewtorski.casette.app.di.modules.ApplicationModule;
import andrewtorski.casette.app.view.activity.BaseActivity;
import andrewtorski.cassette.domain.RepositoryFacade;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    //Exposed to sub-graphs.
    Context context();

    //Repos
    CassetteRepository cassetteRepository();

    RepositoryFacade repositoryFacade();
}
