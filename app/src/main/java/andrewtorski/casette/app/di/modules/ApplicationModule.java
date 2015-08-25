package andrewtorski.casette.app.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import andrewtorski.casette.app.AndroidApplication;
import andrewtorski.casette.app.navigation.Navigator;
import andrewtorski.cassette.data.repository.test.CassetteTestRepository;
import andrewtorski.cassette.domain.RepositoryFacade;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    CassetteRepository provideTestCassetteRepository(CassetteTestRepository cassetteTestRepository) {
        return cassetteTestRepository;
    }

    @Provides
    @Singleton
    RepositoryFacade provideRepositoryFacade() {
        return new RepositoryFacade(new CassetteTestRepository(), null);
    }
}
