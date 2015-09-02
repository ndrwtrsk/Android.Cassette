package andrewtorski.casette.app.di.modules;

import javax.inject.Named;

import andrewtorski.casette.app.di.PerActivity;
import andrewtorski.cassette.domain.usecase.CassetteDetailsUseCase;
import andrewtorski.cassette.domain.usecase.ListCassettesUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides Cassette related collaborators.
 */
@Module
public class CassetteModule {

    private long cassetteId = -1;

    public CassetteModule() {
    }

    public CassetteModule(long cassetteId) {
        this.cassetteId = cassetteId;
    }

    @Provides
    @PerActivity
    @Named("listCassettes")
    ListCassettesUseCase provideListCassetteUseCase(ListCassettesUseCase useCase) {
        return useCase;
    }

    @Provides
    @PerActivity
    @Named("cassetteDetails")
    CassetteDetailsUseCase provideCassetteDetailsUseCase(CassetteDetailsUseCase useCase) {
        return useCase;
    }

}
