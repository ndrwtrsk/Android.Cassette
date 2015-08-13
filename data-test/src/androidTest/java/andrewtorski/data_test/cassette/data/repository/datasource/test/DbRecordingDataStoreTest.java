package andrewtorski.data_test.cassette.data.repository.datasource.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;
import andrewtorski.cassette.data.repository.CassetteDataRepository;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbCassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbRecordingDataStore;
import andrewtorski.cassette.data.repository.datasource.RecordingDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.global.GlobalValues;

/**
 * Created by andrew on 13.08.15.
 */
public class DbRecordingDataStoreTest extends AndroidTestCase {

    private static long simpleCassetteDateTimeCreationEpochTime = 946684800; //2000-01-01;
    private static Date simpleCassetteDateTimeCreation = new Date(simpleCassetteDateTimeCreationEpochTime);

    private final static String TAG = "TEST";

    private CassetteRepository cassetteRepository;
    private RecordingDataStore dataStore;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "text_");
        GlobalValues.setContext(context);
        CassetteDataStore cassetteDataStore = new DbCassetteDataStore();
        cassetteRepository = new CassetteDataRepository(cassetteDataStore);
        dataStore = new DbRecordingDataStore();
    }

    public void test_count() {
        assertEquals(0, dataStore.count());
    }

    public void test_create() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity recordingEntity = getSimpleRecording();
        recordingEntity.cassetteId = cassette.getId();

        //  Act
        recordingEntity = dataStore.create(recordingEntity);

        //  Assert
        assertEquals(1, recordingEntity.id);
    }

    public void test_getById() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity recordingEntity = getSimpleRecording();
        recordingEntity.cassetteId = cassette.getId();
        recordingEntity = dataStore.create(recordingEntity);

        //  Act
        RecordingEntity retrievedRecording = dataStore.get(recordingEntity.id);

        //  Assert
        assertNotNull(retrievedRecording);
        assertEquals(recordingEntity.cassetteId, retrievedRecording.cassetteId);
    }

    public void test_getALl() {
        //  Arrange
        Cassette cassette1 = getSimpleCassette(),
                cassette2 = getSimpleCassette();
        cassette1 = cassetteRepository.create(cassette1);
        cassette2 = cassetteRepository.create(cassette2);

        int recordingsPerCassette = 5;

        RecordingEntity recordingEntity;
        for (int i = 0; i < recordingsPerCassette; i++) {
            recordingEntity = getSimpleRecording();
            recordingEntity.cassetteId = cassette1.getId();
            dataStore.create(recordingEntity);
        }
        for (int i = 0; i < recordingsPerCassette; i++) {
            recordingEntity = getSimpleRecording();
            recordingEntity.cassetteId = cassette2.getId();
            dataStore.create(recordingEntity);
        }

        //  Act
        List<RecordingEntity> allRecordings = dataStore.getAll();

        //  Assert
        assertEquals(2 * recordingsPerCassette, allRecordings.size());
    }

    public void test_getAllForCassette() {
        //  Arrange
        Cassette cassette1 = getSimpleCassette(),
                cassette2 = getSimpleCassette();
        cassette1 = cassetteRepository.create(cassette1);
        cassette2 = cassetteRepository.create(cassette2);

        int recordingsPerCassette = 5;

        RecordingEntity recordingEntity;
        for (int i = 0; i < recordingsPerCassette; i++) {
            recordingEntity = getSimpleRecording();
            recordingEntity.cassetteId = cassette1.getId();
            recordingEntity.audioFilePath = "Cassette1";
            dataStore.create(recordingEntity);
        }
        for (int i = 0; i < recordingsPerCassette; i++) {
            recordingEntity = getSimpleRecording();
            recordingEntity.cassetteId = cassette2.getId();
            recordingEntity.audioFilePath = "Cassette2";
            dataStore.create(recordingEntity);
        }

        //  Act
        List<RecordingEntity> recordingsForCassette1 = dataStore.getAllForCassette(cassette1.getId());
        List<RecordingEntity> recordingsForCassette2 = dataStore.getAllForCassette(cassette2.getId());

        //  Assert
        assertEquals(recordingsPerCassette, recordingsForCassette1.size());
        assertEquals(recordingsPerCassette, recordingsForCassette2.size());

        for (RecordingEntity recording : recordingsForCassette1) {
            assertEquals("Cassette1", recording.audioFilePath);
        }
        for (RecordingEntity recording : recordingsForCassette2) {
            assertEquals("Cassette2", recording.audioFilePath);
        }
    }

    public void test_update() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity oldRecording = getSimpleRecording();
        oldRecording.cassetteId = cassette.getId();
        oldRecording = dataStore.create(oldRecording);

        //  Act
        oldRecording.title = "Title";
        oldRecording.description = "Desc";
        boolean updateWasSuccessful = dataStore.update(oldRecording);

        //  Assert
        assertTrue(updateWasSuccessful);

        RecordingEntity newRecording = dataStore.get(oldRecording.id);

        assertFalse(!newRecording.title.equals(oldRecording.title));
        assertFalse(!newRecording.description.equals(oldRecording.description));
    }

    public void test_delete() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity recording = getSimpleRecording();
        recording.cassetteId = cassette.getId();
        recording = dataStore.create(recording);

        //  Act
        boolean deleteWasSuccessful = dataStore.delete(recording.id);

        //  Assert
        assertTrue(deleteWasSuccessful);
        assertEquals(0, dataStore.count());
    }

    //region Private Static Helper Methods
    private static RecordingEntity getSimpleRecording() {
        long id = -2, cassetteId = -3;
        int sequenceInCassette = 1, length = 12;
        String title = "title", desc = "desc", audioFilePath = "path";
        long dateTimeOfRec = 123;
        return new RecordingEntity(id, cassetteId, title, desc,
                dateTimeOfRec, length, audioFilePath, sequenceInCassette);
    }

    private static Cassette getSimpleCassette() {
        long id = -2;
        String title = "title";
        String description = "desc";
        Date dateTimeOfCreation = simpleCassetteDateTimeCreation;
        int length = 12;
        boolean isCompiled = false;
        String compiledFilePath = "path";
        Date dateTimeOfCompilation = null;
        int numberOfRecordings = 13;

        Cassette cassette = new Cassette(id, title, description, dateTimeOfCreation, length,
                isCompiled, compiledFilePath, dateTimeOfCompilation, numberOfRecordings);

        return cassette;
    }

    //endregion Private Static Helper Methods
}
