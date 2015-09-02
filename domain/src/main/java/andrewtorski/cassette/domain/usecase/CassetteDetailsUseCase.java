package andrewtorski.cassette.domain.usecase;

import javax.inject.Inject;

import andrewtorski.cassette.domain.RepositoryFacade;
import andrewtorski.cassette.domain.entity.Cassette;

public class CassetteDetailsUseCase extends AbstractUseCase {

    private Cassette cassette;

    @Inject
    public CassetteDetailsUseCase(RepositoryFacade facade) {
        super(facade);
    }

    public Cassette cassetteWithRecordings(long cassetteId) {
        cassette = this.getFacade().get(cassetteId, true);
        return cassette;
    }
}
