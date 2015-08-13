package andrewtorski.data_test.cassette.data.db.test;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.db.RecordingDataDbAdapter;
import andrewtorski.cassette.data.entity.RecordingEntity;
import andrewtorski.cassette.data.repository.CassetteDataRepository;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbCassetteDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.global.GlobalValues;

/**
 * Tests functionalities of the RecordingDataDbAdapter.
 */
public class RecordingDataDbAdapterTest extends AndroidTestCase {

    private static long simpleCassetteDateTimeCreationEpochTime = 946684800; //2000-01-01;
    private static Date simpleCassetteDateTimeCreation = new Date(simpleCassetteDateTimeCreationEpochTime);

    private final static String TAG = "TEST";

    /**
     * Since functionalities that are provided by RecordingDataDbAdapter require existing Cassette
     * entities we'll be using Cassette Repository
     */
    private CassetteRepository cassetteRepository;
    private RecordingDataDbAdapter recordingAdapter;

    /**
     *
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        GlobalValues.setContext(context);
        recordingAdapter = RecordingDataDbAdapter.getInstance();
        recordingAdapter.open();

        CassetteDataStore cassetteDataStore = new DbCassetteDataStore();
        cassetteRepository = new CassetteDataRepository(cassetteDataStore);
    }

    public void test_openingAndClosing() {
        //  Assert
        assertTrue(recordingAdapter.isOpen());
        recordingAdapter.close();
        assertFalse(recordingAdapter.isOpen());
    }

    public void test_thatRecordingTableExist() {
        //  Assert
        assertTrue(recordingAdapter.doesCassetteTableExist());
    }

    /**
     * This test associates a Recoridng with a Cassette(meaning that Recording explicitly points to
     * Cassette which was earlier persisted and has an identifier which is present in the respective
     * table.
     * <p/>
     * 1. Create a cassette
     * 2. Persist it using repository.
     * 3. Create a recording.
     * 4. Persist it using the adapter.
     * 5. Check if it's id is different than -1(-1 means that something, somewhere went wrong).
     */
    public void test_insert() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        //  Assert
        assertTrue(cassette.getId() != -1);
        //  Arrange
        RecordingEntity recording = getSimpleRecording();
        recording.cassetteId = cassette.getId();


        //  Act
        recording = persistRecording(recordingAdapter, recording);
        Log.e(TAG, "insert recordingid = " + recording.id);
        //  Assert
        assertTrue(recording.id != 1);
    }

    /**
     * This test doesn't associate Recording with any Cassette whatsoever, and therefore a null is passed
     * to SQLite DB. As a NOT NULL constraint is put on 'cassette_id' column, we expect that the returned
     * index will be equal -1, meaning that something went wrong.
     * <p/>
     * 1. Create a recording.
     * 2. Persist it using the adapter.
     * 3. Check if it's id is indeed -1.
     */
    public void test_insert_RecordingDoesntPointToCassette_NullIsPassed() {
        //  Arrange
        RecordingEntity recording = getSimpleRecording();

        //  Act
        recording = persistRecording(recordingAdapter, recording);

        //  Assert
        assertTrue(recording.id == -1);
    }

    /**
     * This test associates a Recoridng with a Cassette(meaning that Recording explicitly points to
     * Cassette which was not persisted and it's identifier is not present in respective table.
     * We expect that the returned index will be equal -1, meaning that something went wron.g
     * <p/>
     * 1. Create a cassette
     * 2. DO NOT Persist it using repository.
     * 3. Create a recording.
     * 4. Persist it using the adapter.
     * 5. Check if it's id is equal to -1.
     */
    public void test_insert_RecordingPointsToNonexistentCassette() {
        //  Arrange
        //  by default cassette's id is equal to -1.
        Cassette cassette = getSimpleCassette();
        //  Arrange
        RecordingEntity recording = getSimpleRecording();
        recording.cassetteId = cassette.getId();

        //  Act
        recording = persistRecording(recordingAdapter, recording);

        //  Assert
        assertTrue(recording.id == -1);
    }

    /**
     * Test for #getById(long) method.
     * <p/>
     * 1.   Persist a Cassette.
     * 2.   Persist a Recording.
     * 3.   With newly retrieved Recording id retrieve it using #getById(long)
     */
    public void test_getById() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);

        RecordingEntity recording = getSimpleRecording();
        recording.cassetteId = cassette.getId();
        recording = persistRecording(recordingAdapter, recording);

        long recordingId = recording.id;

        //  Assert
        Log.e(TAG, "recordingId = " + recordingId);

        //  Act
        Cursor cursor = recordingAdapter.getById(recordingId);
        assertNotNull("Cursor containing recording is null.", cursor);
        RecordingEntity retrievedRecording = RecordingEntity.createFromCursor(cursor);

        //  Assert
        assertNotNull(retrievedRecording);
        assertTrue(retrievedRecording.id != -1);
    }

    /**
     * Test #getAll() method.
     * <p/>
     * 1.   Persist Cassette.
     * 2.   Associate few Recordings with newly created Cassette and persist them.
     * 3.   Pull them using #getAll() method.
     * 4.   Assert that they're really them and not null.
     */
    public void test_getAll() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        RecordingEntity recording1 = getSimpleRecording(),
                recording2 = getSimpleRecording(),
                recording3 = getSimpleRecording();

        cassette = cassetteRepository.create(cassette);
        assertTrue(cassette.getId() != -1);

        recording1.cassetteId = recording2.cassetteId
                = recording3.cassetteId = cassette.getId();

        recording1 = persistRecording(recordingAdapter, recording1);
        recording2 = persistRecording(recordingAdapter, recording2);
        recording3 = persistRecording(recordingAdapter, recording3);

        Log.e(TAG, "Recording1 Id = " + recording1.id);
        Log.e(TAG, "Recording2 Id = " + recording2.id);
        Log.e(TAG, "Recording3 Id = " + recording3.id);
        assertTrue(recording1.id != -1);
        assertTrue(recording2.id != -1);
        assertTrue(recording3.id != -1);

        //  Act
        Cursor cursor = recordingAdapter.getAll();

        //  Assert
        assertNotNull("Cursor with recordings is null.", cursor);

        List<RecordingEntity> list = getListOfRecordingFromCursor(cursor);

        assertEquals("Number of retrieved elements is not equal to 3.", 3, list.size());
    }

    /**
     * Tests the #getAllForCassette(long id) method.
     * <p/>
     * 1.   Create 3 cassettes and persist them.
     * 2.   For each cassette create a few Recordings and persist them.
     * 3.   For each cassette retrieve their cassettes
     */
    public void test_getAllForCassette() {
        //  Arrange
        Cassette cassette1 = getSimpleCassette(),
                cassette2 = getSimpleCassette(),
                cassette3 = getSimpleCassette();

        cassette1 = cassetteRepository.create(cassette1);
        cassette2 = cassetteRepository.create(cassette2);
        cassette3 = cassetteRepository.create(cassette3);

        //  Small convention for naming below RecordingEntity references:
        //      recording(numberOfRecording)_(numberOfCassette)
        //  Therefore recording1_1 means that it's recording number 1 which belongs to cassette1.
        RecordingEntity
                recording1_1 = getSimpleRecording(), recording1_2 = getSimpleRecording(),
                recording1_3 = getSimpleRecording(),
                recording2_1 = getSimpleRecording(), recording2_2 = getSimpleRecording(),
                recording2_3 = getSimpleRecording(),
                recording3_1 = getSimpleRecording(), recording3_2 = getSimpleRecording(),
                recording3_3 = getSimpleRecording();

        recording1_1.cassetteId = recording2_1.cassetteId = recording3_1.cassetteId = cassette1.getId();
        recording1_1.audioFilePath = recording2_1.audioFilePath = recording3_1.audioFilePath = "Cassette1";
        recording1_2.cassetteId = recording2_2.cassetteId = recording3_2.cassetteId = cassette2.getId();
        recording1_2.audioFilePath = recording2_2.audioFilePath = recording3_2.audioFilePath = "Cassette2";
        recording1_3.cassetteId = recording2_3.cassetteId = recording3_3.cassetteId = cassette3.getId();
        recording1_3.audioFilePath = recording2_3.audioFilePath = recording3_3.audioFilePath = "Cassette3";

        RecordingEntity[][] recordingsForCassettes = {
                {recording1_1, recording2_1, recording3_1},
                {recording1_2, recording2_2, recording3_2},
                {recording1_3, recording2_3, recording3_3}
        };

        int i = 0;
        for (RecordingEntity[] array : recordingsForCassettes) {
            for (RecordingEntity recordingEntity : array) {
                Log.i(TAG, "i = " + i++);
                recordingEntity = persistRecording(recordingAdapter, recordingEntity);
                Log.e(TAG, recordingEntity.toString());
            }
        }

        assertEquals(9, i);

        //  Act
        Cursor cursor = recordingAdapter.getAllForCassette(cassette1.getId());
        assertNotNull("Cursor with recordings for cassette1 is null.", cursor);
        assertEquals(3, cursor.getCount());
        Log.d(TAG, "Cursor count for cassette1 = " + cursor.getCount());
        List<RecordingEntity> recordingsRetrievedForCassette1
                = getListOfRecordingFromCursor(cursor);

        cursor = recordingAdapter.getAllForCassette(cassette2.getId());
        assertNotNull("Cursor with recordings for cassette2 is null.", cursor);
        assertEquals(3, cursor.getCount());
        Log.d(TAG, "Cursor count for cassette2 = " + cursor.getCount());
        List<RecordingEntity> recordingsRetrievedForCassette2
                = getListOfRecordingFromCursor(cursor);


        cursor = recordingAdapter.getAllForCassette(cassette3.getId());
        List<RecordingEntity> recordingsRetrievedForCassette3
                = getListOfRecordingFromCursor(cursor);
        assertEquals(3, cursor.getCount());
        Log.d(TAG, "Cursor count for cassette3 = " + cursor.getCount());
        assertNotNull("Cursor with recordings for cassette3 is null.", cursor);

        //  Assert
        Log.d(TAG, "Number of Recordings for cassette1  = " + recordingsRetrievedForCassette1.size());
        Log.d(TAG, "Number of Recordings for cassette2  = " + recordingsRetrievedForCassette2.size());
        Log.d(TAG, "Number of Recordings for cassette3  = " + recordingsRetrievedForCassette3.size());
        assertTrue(recordingsRetrievedForCassette1.size() == 3);
        assertTrue(recordingsRetrievedForCassette2.size() == 3);
        assertTrue(recordingsRetrievedForCassette3.size() == 3);

        for (RecordingEntity recordingAssociatedWithCassette1 : recordingsRetrievedForCassette1) {
            assertEquals(cassette1.getId(), recordingAssociatedWithCassette1.cassetteId);
        }
        for (RecordingEntity recordingAssociatedWithCassette2 : recordingsRetrievedForCassette2) {
            assertEquals(cassette2.getId(), recordingAssociatedWithCassette2.cassetteId);
        }
        for (RecordingEntity recordingAssociatedWithCassette3 : recordingsRetrievedForCassette3) {
            assertEquals(cassette3.getId(), recordingAssociatedWithCassette3.cassetteId);
        }
    }

    public void test_update() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity oldRecording = getSimpleRecording();
        oldRecording.cassetteId = cassette.getId();
        oldRecording = persistRecording(recordingAdapter, oldRecording);

        //  Act
        oldRecording.title = "new title";
        oldRecording.description = "new desc";

        boolean updateWasSuccess = recordingAdapter.update(oldRecording.id, oldRecording.title,
                oldRecording.description);

        //  Assert
        assertTrue(updateWasSuccess);

        Cursor cursor = recordingAdapter.getById(oldRecording.id);
        assertNotNull(cursor);
        RecordingEntity newRecording = RecordingEntity.createFromCursor(cursor);

        assertNotNull(newRecording);
        assertNotNull(newRecording.title);
        assertNotNull(newRecording.description);

        assertEquals("new title", newRecording.title);
        assertEquals("new desc", newRecording.description);

    }

    public void test_update_recordingWhichDoesntExist() {
        //  Arrange
        boolean updateWasSuccess = recordingAdapter.update(12, "a",
                "b");

        //  Assert
        assertFalse(updateWasSuccess);
    }

    public void test_delete() {
        //  Arrange
        Cassette cassette = getSimpleCassette();
        cassette = cassetteRepository.create(cassette);
        RecordingEntity oldRecording = getSimpleRecording();
        oldRecording.cassetteId = cassette.getId();
        oldRecording = persistRecording(recordingAdapter, oldRecording);

        //  Act
        boolean deleteWasSuccess = recordingAdapter.delete(oldRecording.id);

        //  Assert
        assertTrue(deleteWasSuccess);
        Cursor cursor = recordingAdapter.getById(oldRecording.id);
        RecordingEntity recordingEntity = RecordingEntity.createFromCursor(cursor);
    }


    //region Private helper methods

    private static RecordingEntity persistRecording(RecordingDataDbAdapter recordingAdapter,
                                                    RecordingEntity recordingEntity) {
        if (recordingEntity == null) {
            throw new NullPointerException();
        }
        recordingEntity.id = recordingAdapter.create(recordingEntity.cassetteId,
                recordingEntity.sequenceInTheCassette, recordingEntity.dateTimeOfRecording,
                recordingEntity.audioFilePath, recordingEntity.length);

        return recordingEntity;
    }

    private static List<RecordingEntity> getListOfRecordingFromCursor(Cursor cursor) {
        LinkedList<RecordingEntity> list = new LinkedList<>();

        if (cursor == null) {
            return list;
        }

        RecordingEntity entity;
        while (cursor.moveToNext()) {
            entity = RecordingEntity.createFromCursor(cursor);
            if (entity != null) {
                list.add(entity);
            }
        }

        cursor.close();

        return list;
    }

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

    //endregion Private helper methods
}
