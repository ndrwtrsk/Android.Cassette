package andrewtorski.cassette.domain.repository;

import java.util.Date;

import andrewtorski.cassette.domain.entity.Cassette;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Cassettes.
 */
public interface CassetteRepository {

    /** Creates a Cassette using title and description.
     * @param title Title of the Cassette.
     * @param description Description of the Cassette. */
    void createCassette(String title, String description);

    /** Retrieves a Cassette using the provided id.
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found. */
    Cassette getCassette(final int cassetteId);

}
