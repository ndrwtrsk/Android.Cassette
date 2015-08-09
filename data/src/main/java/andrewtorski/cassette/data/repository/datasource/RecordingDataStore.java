package andrewtorski.cassette.data.repository.datasource;

import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;

/**
 * Contract for a data store which allows for data operations surrounding Recordings.
 */
public interface RecordingDataStore {

    /**
     * Persists provided RecordingEntity and returns it with it's id updated.
     * If insertion didn't succeed, RecordingEntity will have it's id set to -1.
     * @param recordingEntity RecordingEntity to insert.
     * @return Same RecordingEntity as provided in the parameter, but with it's id field updated.
     */
    RecordingEntity createRecording(RecordingEntity recordingEntity);

    /**
     * Returns a RecordingEntity of specified identifier.
     *
     * @param recordingId Identifier of the searched RecordingEntity.
     * @return Reference to RecordingEntity object if found or null if not.
     */
    RecordingEntity getRecordingDetails(final long recordingId);

    /**
     * Returns all possible, existent RecordingEntities.
     * @return List of RecordingEntities.
     */
    List<RecordingEntity> getAllRecordings();

    /**
     * Returns all RecordingEntities which belong to Cassette of specified identifier.
     * @param cassetteId Id of the Cassette.
     * @return List of RecordingEntities.
     */
    List<RecordingEntity> getAllRecordingsWhichBelongToCassette(final long cassetteId);

    /**
     * Returns all RecordingEntities which were recorded in the provide date span expressed as two
     * epoch time values.
     * @param fromDate From date, epoch time.
     * @param toDate To date, epoch time.
     * @return List of RecordingEntities.
     */
    List<RecordingEntity> getAllRecordingsBetweenDatesDescending(long fromDate, long toDate);

    /**
     * Returns all RecordingEntities which were recorded in the provide date span expressed as two
     * epoch time values and which belong to Cassette of the specified identifier.
     * @param fromDate From date, epoch time.
     * @param toDate To date, epoch time.
     * @param cassetteId Id of the Cassette.
     * @return List of RecordingEntities.
     */
    List<RecordingEntity> getAllRecordingsBetweenDatesWhichBelongToCassetteDescending(long cassetteId, long fromDate, long toDate);

    /**
     * Returns all Recordings which title or description match in anyway the specified searchClause.
     * @param searchClause Searched phrase.
     * @return List of RecordingEntities.
     */
    List<RecordingEntity> getAllRecordingsWhichContainTitleOrDescriptionSimilarTo(String searchClause);

    /**
     * Updates the provided RecordingEntity with the data provider.
     *
     * @param recordingEntity RecordingEntity to update.
     * @return Was update successful.
     */
    boolean updateRecording(RecordingEntity recordingEntity);

    /**
     * Deletes the provided RecordingEntity with the date provider.
     * @param id Identifier of the RecordingEntity to delete.
     * @return Was deletion successful.
     */
    boolean deleteRecording(long id);
}
