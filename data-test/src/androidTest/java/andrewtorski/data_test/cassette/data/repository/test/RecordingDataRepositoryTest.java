package andrewtorski.data_test.cassette.data.repository.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.repository.CassetteDataRepository;
import andrewtorski.cassette.data.repository.RecordingDataRepository;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbCassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbRecordingDataStore;
import andrewtorski.cassette.data.repository.datasource.RecordingDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.cassette.domain.repository.RecordingRepository;
import andrewtorski.global.GlobalValues;

public class RecordingDataRepositoryTest extends AndroidTestCase {

    private static long simpleCassetteDateTimeCreationEpochTime = 946684800; //2000-01-01;
    private static Date simpleCassetteDateTimeCreation = new Date(simpleCassetteDateTimeCreationEpochTime);

    private CassetteRepository cassetteRepository;
    private RecordingRepository recordingRepository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "text_");
        GlobalValues.setContext(context);
        CassetteDataStore cassetteDataStore = new DbCassetteDataStore();
        cassetteRepository = new CassetteDataRepository(cassetteDataStore);
        RecordingDataStore recordingDataStore = new DbRecordingDataStore();
        recordingRepository = new RecordingDataRepository(recordingDataStore);
    }

    public void test_count() {
        assertEquals(0, recordingRepository.count());
    }

    public void test_create() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        Recording recording = getSimpleRecording(cassette.getId());

        //  Act
        recording = recordingRepository.create(recording);

        //  Assert
        assertTrue(recording.getId() != -1);
        assertEquals(1, recordingRepository.count());
    }

    public void test_getById() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        Recording recording = getSimpleRecording(cassette.getId());
        recording = recordingRepository.create(recording);

        //  Act
        Recording retrievedRecording = recordingRepository.get(recording.getId());

        //  Assert
        assertNotNull(retrievedRecording);
        assertEquals(recording.getId(), retrievedRecording.getId());
    }

    public void test_update() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        Recording recording = getSimpleRecording(cassette.getId());
        recording = recordingRepository.create(recording);

        //  Act
        recording.setTitle("New Title");
        recording.setDescripition("New Desc");

        boolean updateWasSuccessful = recordingRepository.update(recording);

        //  Assert
        assertTrue(updateWasSuccessful);
    }

    public void test_delete() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        Recording recording = getSimpleRecording(cassette.getId());
        recording = recordingRepository.create(recording);

        //  Act
        boolean deleteWasSuccessful = recordingRepository.delete(recording.getId());

        //  Assert
        assertTrue(deleteWasSuccessful);
        assertEquals(0, recordingRepository.count());
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

    public void test_getAll() {
        //  Arrange
        Cassette cassette1 = getSimpleCassette(),
                cassette2 = getSimpleCassette();
        cassette1 = cassetteRepository.create(cassette1);
        cassette2 = cassetteRepository.create(cassette2);
        int recordingsPerCassette = 5;
        Recording recording;
        for (int i = 0; i < recordingsPerCassette; i++) {
            recording = getSimpleRecording(cassette1.getId());
            recordingRepository.create(recording);
        }
        for (int i = 0; i < recordingsPerCassette; i++) {
            recording = getSimpleRecording(cassette2.getId());
            recordingRepository.create(recording);
        }

        //  Act
        List<Recording> allRecordings = recordingRepository.getAll();

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
        Recording recording;
        for (int i = 0; i < recordingsPerCassette; i++) {
            recording = getSimpleRecording(cassette1.getId());
            recordingRepository.create(recording);
        }
        for (int i = 0; i < recordingsPerCassette; i++) {
            recording = getSimpleRecording(cassette2.getId());
            recordingRepository.create(recording);
        }

        //  Act
        List<Recording> recordingsForCassette1 = recordingRepository.getAllForCassette(cassette1);
        List<Recording> recordingsForCassette2 = recordingRepository.getAllForCassette(cassette2);

        //  Assert
        assertEquals(recordingsPerCassette, recordingsForCassette1.size());
        assertEquals(recordingsPerCassette, recordingsForCassette2.size());
    }


    private static Recording getSimpleRecording(long cassetteId) {

        Recording recording = new Recording(-2, cassetteId, "", "", simpleCassetteDateTimeCreation, 123, "path",
                123);

        return recording;
    }
}
