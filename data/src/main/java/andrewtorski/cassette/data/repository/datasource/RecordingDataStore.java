package andrewtorski.cassette.data.repository.datasource;

import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;

/**
 * Contract for a data store which allows for data operations surrounding Recordings.
 */
public interface RecordingDataStore {

    /**
     * Persists provided RecordingEntity and returns
     *
     * @param recordingEntity
     * @return
     */
    RecordingEntity createRecording(RecordingEntity recordingEntity);

    RecordingEntity getRecordingDetails(final long recordingId);

    List<RecordingEntity> getAllRecordings();

    List<RecordingEntity> getAllRecordingsWhichBelongToCassette(final long cassetteId);

    List<RecordingEntity> getAllRecordingsBetweenDatesDescending(long fromDate, long toDate);

    List<RecordingEntity> getAllRecordingsBetweenDatesWhichBelongToCassetteDescending(long cassetteId, long fromDate, long toDate);

    List<RecordingEntity> getAllRecordingsWhichContainTitleOrDescriptionSimilarTo(String searchClause);

    boolean updateRecording(long id, String title, String description);

    boolean deleteRecording(long id);
}
