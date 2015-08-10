package andrewtorski.data_test.cassette.data.repository.datasource.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.data.repository.datasource.DbCassetteDataStore;
import andrewtorski.global.GlobalValues;

/**
 * Tests functionalities exposed by the DbCassetteDataStore which is a database implementation of
 * a CassetteDataStore.
 */
public class DbCassetteDataStoreTest extends AndroidTestCase {

    /**
     * Reference to the tested Store.
     */
    private CassetteDataStore dataStore;

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
        dataStore = new DbCassetteDataStore();
    }

    /**
     * Returns a basic CassetteEntity only with it's title, desription and datetime of creation set.
     * Title = "title", description = "desc", datetime = 2000-01-01;
     */
    private static CassetteEntity getSimpleCassette() {
        return new CassetteEntity("title", "desc", simpleCassetteDateTimeCreation);
    }

    /**
     * Persist simple
     */
    public void test_create() {
        //  Arrange
        CassetteEntity cassetteBeforePersisting = getSimpleCassette();

        //  Act
        CassetteEntity cassetteAfterPersisting = dataStore.create(cassetteBeforePersisting);

        //  Assert
        assertTrue(cassetteAfterPersisting.id != -1);
        //  DbCassetteDataStore#create(CassetteEntity) returns the same reference which it was provided
        //  in the parameter, albeit with id changed.
        assertSame(cassetteBeforePersisting, cassetteAfterPersisting);
        assertEquals(cassetteBeforePersisting.title, cassetteAfterPersisting.title);
        assertEquals(cassetteBeforePersisting.descripition, cassetteAfterPersisting.descripition);
        assertEquals(cassetteBeforePersisting.dateTimeOfCreation, cassetteAfterPersisting.dateTimeOfCreation);
    }

    public void test_get() {
        //  Arrange
        CassetteEntity entity = getSimpleCassette();
        String title = entity.title, description = entity.descripition;
        long date = entity.dateTimeOfCreation;

        entity = dataStore.create(entity);

        long id = entity.id;

        entity = null;

        //  act
        entity = dataStore.get(id);

        //  assert
        assertNotNull(entity);
        assertEquals(id, entity.id);
        assertEquals(title, entity.title);
        assertEquals(description, entity.descripition);
        assertEquals(date, entity.dateTimeOfCreation);
    }

    /**
     * Persist simple cassette, update all it's possible fields, retrieve it and assert.
     */
    public void test_update_allFields() {
        //  Arrange
        CassetteEntity cassetteEntity = getSimpleCassette();
        cassetteEntity = dataStore.create(cassetteEntity);

        long id = cassetteEntity.id;
        String newTitle = "newTitle", newDescripition = "newDesc", path = "path";
        int length = 12, numberOfRecordings = 13, isCompiled = 1;
        long dateCompilation = 13000;

        //  Act
        cassetteEntity.title = newTitle;
        cassetteEntity.descripition = newDescripition;
        cassetteEntity.compiledFilePath = path;
        cassetteEntity.isCompiled = isCompiled;
        cassetteEntity.length = length;
        cassetteEntity.numberOfRecordings = numberOfRecordings;
        cassetteEntity.dateTimeOfCompilation = dateCompilation;

        boolean wasSuccess = dataStore.update(cassetteEntity);
        //  retrieve the entity from database.
        CassetteEntity newCassetteEntity = dataStore.get(cassetteEntity.id);

        //  Assert
        assertTrue("Update was not successful.", wasSuccess);
        assertEquals(id, newCassetteEntity.id);
        assertEquals(newTitle, newCassetteEntity.title);
        assertEquals(newDescripition, newCassetteEntity.descripition);
        assertEquals(path, newCassetteEntity.compiledFilePath);
        assertEquals(length, newCassetteEntity.length);
        assertEquals(dateCompilation, newCassetteEntity.dateTimeOfCompilation);
        assertEquals(isCompiled, newCassetteEntity.isCompiled);
        assertEquals(numberOfRecordings, newCassetteEntity.numberOfRecordings);
    }

    /**
     * Persist simple cassette, delete it, check count.
     */
    public void test_delete() {
        //  Arrange
        CassetteEntity cassetteEntity1 = getSimpleCassette();
        cassetteEntity1 = dataStore.create(cassetteEntity1);

        CassetteEntity cassetteEntity2 = getSimpleCassette();
        cassetteEntity2 = dataStore.create(cassetteEntity2);

        //  Act
        //  first pass CassetteEntity to #delete method
        boolean wasFirstDeleteSuccessful = dataStore.delete(cassetteEntity1);
        int countAfterFirstDelete = dataStore.count();
        //  secondly pass int (id of cassette) to #delete method
        boolean wasSecondDeleteSuccessful = dataStore.delete(cassetteEntity2.id);
        int countAfterSecondDelete = dataStore.count();

        //  Assert
        assertTrue(wasFirstDeleteSuccessful);
        assertEquals(1, countAfterFirstDelete);
        assertTrue(wasSecondDeleteSuccessful);
        assertEquals(0, countAfterSecondDelete);
    }

    public void test_willGetAllReturnEmptyCollection_If_NoEntitesArePresent() {
        //  Act
        List<CassetteEntity> returnedList = dataStore.getAll();

        // Assert
        assertNotNull(returnedList);
        assertEquals(0, returnedList.size());
    }

    /**
     * Persist three entities, then get them and assert that they're really them.
     */
    public void test_getAll() {
        //  Arrange
        CassetteEntity[] cassetteEntities = {getSimpleCassette(), getSimpleCassette(), getSimpleCassette()};
        for (int i = 0; i < cassetteEntities.length; i++) {
            dataStore.create(cassetteEntities[i]);
        }
        //  Act
        ArrayList<CassetteEntity> cassetteEntityList = new ArrayList<>(dataStore.getAll());

        //  Assert
        assertEquals(3, dataStore.count());
        assertEquals(1, cassetteEntityList.get(0).id);
        assertEquals(2, cassetteEntityList.get(1).id);
        assertEquals(3, cassetteEntityList.get(2).id);
    }
}
