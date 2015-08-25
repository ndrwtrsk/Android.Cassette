package andrewtorski.cassette.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import andrewtorski.cassette.domain.RepositoryFacade;
import andrewtorski.cassette.domain.entity.Cassette;

/**
 * Exposes functionality of listing all present Cassettes.
 */
public class ListCassettesUseCase extends AbstractUseCase {

    @Inject
    protected ListCassettesUseCase(RepositoryFacade facade) {
        super(facade);
    }

    public List<Cassette> cassettes() {
        return this.getFacade().getAll();
    }
}
