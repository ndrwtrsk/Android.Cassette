package andrewtorski.cassette.data.repository.datasource;

import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * Contract for a data store which allows for data operation surrounding.
 */
public interface CassetteDataStore {
    /**
     * Persists provided CassetteEntity and then returns it with it's identifier field updated.
     */
    CassetteEntity createCassette(CassetteEntity cassetteEntity);

    /**
     * Retrieves a Cassette using the provided id.
     *
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found.
     */
    CassetteEntity getCassetteEntityDetails(final int cassetteId);
}
