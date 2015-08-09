package andrewtorski.cassette.domain.repository;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;

/**
 * Defines contract for all implementing classes to realize in order to become a repository
 * for Recordings.
 */
public interface RecordingRepository {

    /**
     * Persists a Recording and the returns it with it's id updated.
     *
     * @param recording Recording to persist.
     * @return Persisted recording.
     */
    Recording create(Recording recording);

    /**
     * Updates the provided Recording.
     *
     * @param recording Recoridng to update.
     * @return Was update successful.
     */
    boolean update(Recording recording);

    /**
     * Deletes the provided Recording.
     *
     * @param recording Recording to delete.
     * @return Was deletion successful.
     */
    boolean delete(Recording recording);

    /**
     * Deletes the Recording of provided id.
     *
     * @param id Id ofRecording to delete.
     * @return Was deletion successful.
     */
    boolean delete(long id);

    /**
     * Returns a collection of Recordings which are associated with the specified Cassette.
     *
     * @param cassette Cassette for which a search for Recordings is performed.
     * @return List of Recordings.
     */
    List<Recording> getAllForCassette(Cassette cassette);

    /**
     * Returns all Recordings which were recorded in the provided date span.
     *
     * @param fromDate From date.
     * @param toDate   To date
     * @return List of Recordings.
     */
    List<Recording> getAllBetweenDatesForCassette(Date fromDate, Date toDate);

    /**
     * Returns all Recordings which were recorded  in the provided date span and which belong to the
     * provided Cassette.
     *
     * @param cassette Cassette for which a search for Recordings is performed.
     * @param fromDate From date.
     * @param toDate   To date
     * @return List of Recordings.
     */
    List<Recording> getAllBetweenDatesForCassette(Cassette cassette, Date fromDate, Date toDate);

    /**
     * Returns all Recordings which title or description are like the provided search clause.
     *
     * @param searchClause The search clause.
     * @return List of Recordings.
     */
    List<Recording> getAllWhichTitleOrDescriptionIsLike(String searchClause);
}
