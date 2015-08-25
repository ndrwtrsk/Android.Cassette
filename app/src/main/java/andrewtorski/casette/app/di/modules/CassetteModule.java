package andrewtorski.casette.app.di.modules;

import javax.inject.Named;

import andrewtorski.casette.app.di.PerActivity;
import andrewtorski.cassette.domain.usecase.ListCassettesUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides Cassette related collaborators.
 */
@Module
public class CassetteModule {

    private int cassetteId = -1;

    public CassetteModule() {
    }

    public CassetteModule(int userId) {
        this.cassetteId = userId;
    }

    @Provides
    @PerActivity
    @Named("listCassettes")
    ListCassettesUseCase provideGetUserListUseCase(ListCassettesUseCase useCase) {
        return useCase;
    }

    /*@Provides
    @PerActivity
    @Named("detailsCassette") UseCase provideGetUserDetailsUseCase(
            UserRepository userRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetUserDetails(cassetteId, userRepository, threadExecutor, postExecutionThread);
    }*/

}
