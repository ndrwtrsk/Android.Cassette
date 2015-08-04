package andrewtorski.cassette.data.repository.datasource;

import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * Contract for a data store which allows for data operation surrounding.
 */
public interface CassetteDataStore {
    /** Creates a Cassette using title and description.
     * @param title Title of the Cassette.
     * @param description Description of the Cassette. */
    CassetteEntity createCassette(String title, String description);

    /** Retrieves a Cassette using the provided id.
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found. */
    CassetteEntity getCassetteEntityDetails(final int cassetteId);
}
