package andrewtorski.data_test.cassette.data.repository.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Date;

import andrewtorski.cassette.data.repository.CassetteDataRepository;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbCassetteDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.global.GlobalValues;

/**
 * Test class for {@link andrewtorski.cassette.data.repository.CassetteDataRepository}.
 */
public class CassetteDataRepositoryTest extends AndroidTestCase {

    private CassetteRepository repository;

    private static long simpleCassetteDateTimeCreationEpochTime = 946684800; //2000-01-01;
    private static Date simpleCassetteDateTimeCreation = new Date(simpleCassetteDateTimeCreationEpochTime);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        /*
            Following setup of context is required as DbCassetteDataStore in it's empty constructor
             instantiates a CassetteDbDataAdapter and this in turns requires context to open the database.
         */
        RenamingDelegatingContext context = new RenamingDelegatingContext(super.getContext(), "test_");
        GlobalValues.setContext(context);
        CassetteDataStore dataStore = new DbCassetteDataStore();
        repository = new CassetteDataRepository(dataStore);
    }

    private static Cassette getSimpleCassette() {
        long id = -1;
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

    /**
     * Persist simple Cassette, then retrieve it and finally check if it's not null and whether it's
     * id is different than -1(meaning that everything went fine and that it was persisted.
     */
    public void test_create() {
        //----Arrange-----
        Cassette cassette = getSimpleCassette();
        //------Act-------
        cassette = repository.create(cassette);
        //----Assert------
        assertNotNull(cassette);
        assertTrue(repository.count() != 0);
        assertTrue(cassette.getId() != -1);
    }

    /**
     * Persist simple Cassette, and then get it using CassetteRepository#get()
     */
    public void test_get() {
        //----Arrange-----
        Cassette cassette = getSimpleCassette();
        cassette = repository.create(cassette);
        //------Act-------
        Cassette retrievedCassette = repository.get(cassette.getId());
        //----Assert------
        assertEquals(cassette.getId(), retrievedCassette.getId());
    }

    /**
     * Persist simple Cassette, then change all fields, update it, retrieve it and check if changes
     * were made.
     */
    public void test_update_everyField() {
        //----Arrange-----
        Cassette cassette = getSimpleCassette();
        cassette = repository.create(cassette);

        String newTitle = "newTitle",
                description = "newDescription",
                path = "newPath";
        int length = 120,
                numberOfRecords = 130;
        boolean isCompiled = true;
        Date dateTimeOfCompilation = new Date();

        cassette.setTitle(newTitle);
        cassette.setDescription(description);
        cassette.setCompiledFilePath(path);
        cassette.setLength(length);
        cassette.setNumberOfRecordings(numberOfRecords);
        cassette.setCompiled(isCompiled);
        cassette.setDateTimeOfCompilation(dateTimeOfCompilation);
        //------Act-------

        boolean updateWasSuccessful = repository.update(cassette);
        Cassette newUpdatedAndRetrievedCassette;
        if (updateWasSuccessful) {
            newUpdatedAndRetrievedCassette = repository.get(cassette.getId());
        } else {
            return;
        }
        //----Assert------
        assertTrue(updateWasSuccessful);
        assertEquals(cassette.getId(), newUpdatedAndRetrievedCassette.getId());
        assertEquals(newTitle, newUpdatedAndRetrievedCassette.getTitle());
        assertEquals(description, newUpdatedAndRetrievedCassette.getDescription());
        assertEquals(path, newUpdatedAndRetrievedCassette.getCompiledFilePath());
        //  date time of creation should be equal despite update.
        assertEquals(cassette.getDateTimeOfCreation(), newUpdatedAndRetrievedCassette.getDateTimeOfCreation());
        assertEquals(dateTimeOfCompilation, newUpdatedAndRetrievedCassette.getDateTimeOfCompilation());
        assertEquals(length, newUpdatedAndRetrievedCassette.getLength());
        assertEquals(numberOfRecords, newUpdatedAndRetrievedCassette.getNumberOfRecordings());
    }

    /**
     * Persist two Cassettes(one for Repo#delete(Cassette) other for #delete(int). Delete them one by one,
     * after each deletion save the current count of Cassettes and assert if operations were successful.
     */
    public void test_delete() {
        //----Arrange-----
        Cassette cassette1 = getSimpleCassette(),
                cassette2 = getSimpleCassette();
        cassette1 = repository.create(cassette1);
        cassette2 = repository.create(cassette2);

        //------Act-------
        boolean wasFirstDeleteSuccessful = repository.delete(cassette1);
        int countAfterFirstDelete = repository.count();
        boolean wasSecondDeleteSuccessful = repository.delete(cassette2.getId());
        int countAfterSecondDelete = repository.count();

        //----Assert------
        assertTrue(wasFirstDeleteSuccessful);
        assertEquals(1, countAfterFirstDelete);
        assertTrue(wasSecondDeleteSuccessful);
        assertEquals(0, countAfterSecondDelete);
    }

}
