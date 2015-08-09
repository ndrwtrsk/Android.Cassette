package andrewtorski.cassette.data.repository.datasource;

import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.db.RecordingDataDbAdapter;
import andrewtorski.cassette.data.entity.RecordingEntity;

/**
 * {@link andrewtorski.cassette.data.repository.datasource.RecordingDataStore} implementation based on SQLite database.
 */
public class DbRecordingDataStore implements RecordingDataStore {

    private RecordingDataDbAdapter recordingDataDbAdapter;

    public DbRecordingDataStore() {
        recordingDataDbAdapter = RecordingDataDbAdapter.getInstance();
    }

    @Override
    public RecordingEntity createRecording(RecordingEntity recordingEntity) {

        long cassetteId = recordingEntity.cassetteId;
        int sequenceInCassette = recordingEntity.sequenceInTheCassette;
        long dateTimeOfRecording = recordingEntity.dateTimeOfRecording;
        String filePath = recordingEntity.audioFilePath;
        int length = recordingEntity.length;
        long id = recordingDataDbAdapter.createRecording(cassetteId, sequenceInCassette, dateTimeOfRecording, filePath, length);

        recordingEntity.id = id;

        return recordingEntity;
    }

    @Override
    public RecordingEntity getRecordingDetails(long recordingId) {
        Cursor cursor = recordingDataDbAdapter.getRecordingById(recordingId);

        if (cursor == null) {
            return null;
        }

        RecordingEntity recordingEntity = RecordingEntity.createRecordingEntityFromCursor(cursor);

        return recordingEntity;
    }

    @Override
    public List<RecordingEntity> getAllRecordings() {
        Cursor cursor = recordingDataDbAdapter.getAllRecordings();

        return DbRecordingDataStore.getListOfRecordingsFromCursor(cursor);
    }

    @Override
    public List<RecordingEntity> getAllRecordingsWhichBelongToCassette(long cassetteId) {
        Cursor cursor = recordingDataDbAdapter.getAllRecordingsWhichBelongToCassette(cassetteId);

        return DbRecordingDataStore.getListOfRecordingsFromCursor(cursor);
    }

    @Override
    public List<RecordingEntity> getAllRecordingsBetweenDatesDescending(long fromDate, long toDate) {
        Cursor cursor = recordingDataDbAdapter.getAllRecordingsBetweenDatesDescending(fromDate, toDate);

        return DbRecordingDataStore.getListOfRecordingsFromCursor(cursor);
    }

    @Override
    public List<RecordingEntity> getAllRecordingsBetweenDatesWhichBelongToCassetteDescending(long cassetteId, long fromDate, long toDate) {
        Cursor cursor = recordingDataDbAdapter.getAllRecordingsBetweenDatesForCassetteDescending(cassetteId, fromDate, toDate);

        return DbRecordingDataStore.getListOfRecordingsFromCursor(cursor);
    }

    @Override
    public List<RecordingEntity> getAllRecordingsWhichContainTitleOrDescriptionSimilarTo(String searchClause) {
        Cursor cursor = recordingDataDbAdapter.getAllRecordingsWithTitleAndDescriptionLike(searchClause);

        return DbRecordingDataStore.getListOfRecordingsFromCursor(cursor);
    }

    @Override
    public boolean updateRecording(RecordingEntity recordingEntity) {

        long id = recordingEntity.id;
        String title = recordingEntity.title,
                description = recordingEntity.description;

        boolean wasSuccess = recordingDataDbAdapter.updateRecording(id, title, description);

        return wasSuccess;
    }

    @Override
    public boolean deleteRecording(long id) {
        return recordingDataDbAdapter.deleteRecording(id);
    }

    private static List<RecordingEntity> getListOfRecordingsFromCursor(Cursor cursor) {
        List<RecordingEntity> recordingEntityList = new LinkedList<>();

        if (cursor == null) {
            return recordingEntityList;
        }

        RecordingEntity recordingEntity;
        while (cursor.moveToNext()) {
            recordingEntity = RecordingEntity.createRecordingEntityFromCursor(cursor);
            if (recordingEntity != null) {
                recordingEntityList.add(recordingEntity);
            }
        }

        return recordingEntityList;
    }
}
