package andrewtorski.cassette.domain.usecase;

import andrewtorski.cassette.domain.RepositoryFacade;

/**
 * Class which serves as base class for all classes which perform use cases. All business logic is
 * accessed through this classes.
 */
public abstract class AbstractUseCase {

    /**
     * Provides access to data layer.
     */
    private RepositoryFacade facade;

    protected AbstractUseCase(RepositoryFacade facade) {
        this.facade = facade;
    }

    protected AbstractUseCase() {

    }

    protected RepositoryFacade getFacade() {
        return facade;
    }
}
