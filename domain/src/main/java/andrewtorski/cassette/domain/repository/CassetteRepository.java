package andrewtorski.cassette.domain.repository;

import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Cassettes.
 * Serves as an interface for communicating with Data Access Layer.
 */
public interface CassetteRepository {

    /** Creates a Cassette using title and description.
     * @param cassette Cassette to be persisted.
     */
    Cassette createCassette(Cassette cassette);

    /** Retrieves a Cassette using the provided id.
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found. */
    Cassette getCassette(final int cassetteId);

    /**
     * Returns a list of all Cassettes present.
     *
     * @return List of Cassettes.
     */
    List<Cassette> getAll();

    /**
     * Updates Cassette.
     *
     * @param cassette Cassette to be updated.
     * @return Was update successful.
     */
    boolean update(Cassette cassette);

    /**
     * Deletes Cassette.
     *
     * @param cassette Cassette to be deleted.
     * @return Was deletion successful.
     */
    boolean delete(Cassette cassette);

    /**
     * Deletes Cassette of specified Id.
     *
     * @param id Id of the Cassette to be deleted.
     * @return Was deletion successful.
     */
    boolean delete(long id);

}
