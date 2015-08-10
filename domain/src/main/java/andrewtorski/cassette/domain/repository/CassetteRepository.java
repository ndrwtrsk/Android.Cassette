package andrewtorski.cassette.domain.repository;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Cassettes.
 * Serves as an interface for communicating with Data Access Layer.
 */
public interface CassetteRepository {

    /**
     * Persists provided Cassette..
     *
     * @param cassette Cassette to be persisted.
     */
    Cassette create(Cassette cassette);

    /**
     * Retrieves a Cassette using the provided id.
     *
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found.
     */
    Cassette get(final int cassetteId);

    /**
     * Returns a list of all Cassettes present.
     * These Cassettes do not include their associated Recordings.
     * Should be used for listing purposes exclusively.
     *
     * @return List of Cassettes.
     */
    List<Cassette> getAll();

    /**
     * Returns all CassetteEntities which were created between provided date span.
     * List is sorted descendingly.
     * Date span is expressed as two UNIX time values.
     *
     * @param fromDate UNIX time representing from-date.
     * @param ToDate   UNIX time representing to-date.
     * @return List of CassetteEntities.
     */
    List<Cassette> getAllBetweenDatesDescending(Date fromDate, Date ToDate);

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
