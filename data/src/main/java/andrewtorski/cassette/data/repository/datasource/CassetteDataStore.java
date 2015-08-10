package andrewtorski.cassette.data.repository.datasource;

import java.util.List;

import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * Contract for a data store which allows for data operation surrounding Cassettes.
 */
public interface CassetteDataStore {
    /**
     * Persists provided CassetteEntity and then returns it with it's identifier field updated.
     */
    CassetteEntity create(CassetteEntity cassetteEntity);

    /**
     * Retrieves a Cassette using the provided id.
     *
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found.
     */
    CassetteEntity get(final long cassetteId);

    /**
     * Returns a list of all CassetteEntities in the database.
     *
     * @return List of CassetteEntities.
     */
    List<CassetteEntity> getAll();

    /**
     * Returns all CassetteEntities which were created between provided date span.
     * List is sorted descendingly.
     * Date span is expressed as two UNIX time values.
     *
     * @param fromDate UNIX time representing from-date.
     * @param ToDate   UNIX time representing to-date.
     * @return List of CassetteEntities.
     */
    List<CassetteEntity> getAllBetweenDates(long fromDate, long ToDate);

    /**
     * Updates existing cassette of cassetteEntity's id with cassetteEntity data.
     *
     * @param cassetteEntity CassetteEntity to be updated.
     * @return Was this operation successful.
     */
    boolean update(CassetteEntity cassetteEntity);

    /**
     * Deletes provided CassetteEntity.
     *
     * @param cassetteEntity CassetteEntity to be deleted.
     * @return Was this operation successful.
     */
    boolean delete(CassetteEntity cassetteEntity);

    /**
     * Deletes provided CassetteEntity of provided identifier.
     *
     * @param id Identifier of CassetteEntity to be deleted.
     * @return Was this operation successful.
     */
    boolean delete(long id);

    /**
     * Returns the number of entities present in the data store.
     */
    int count();
}
