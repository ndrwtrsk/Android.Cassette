package andrewtorski.data_test.cassette.data.db.test;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Arrays;
import java.util.Date;

import andrewtorski.cassette.data.db.CassetteDataDbAdapter;
import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.global.GlobalValues;

/**
 * Tests functionalities provided by the CassetteDataDbAdapter.
 * <p/>
 * <h3>Key takeaways and lessons.</h3>
 * <ol>
 * <li>#setUp() is executed before every test method.</li>
 * <li>#setUp() recreates database before every test method.</li>
 * </ol>
 */
public class CassetteDataDbAdapterTest extends AndroidTestCase {

    private CassetteDataDbAdapter testedAdapter;

    /**
     * This method is executed before ANY of the following test methods.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        GlobalValues.setContext(context);
        testedAdapter = CassetteDataDbAdapter.getInstance();
        testedAdapter.open();
    }

    public void test_checkTestedAdapterAndContextForNull() {
        assertNotNull("Tested adapter is null.", testedAdapter);
        assertNotNull("Android Test Case context is null", getContext());
        assertNotNull("Global Values Context is null", GlobalValues.getContext());
    }

    public void test_openingAndClosingDb() {
        assertTrue("Connection to database is not open.", testedAdapter.isOpen());
        testedAdapter.close();
        assertFalse("Connection to database did not close.", testedAdapter.isOpen());
    }

    public void test_doesCassetteTableExist() {
        assertTrue("Cassette table does not exist.", testedAdapter.doesCassetteTableExist());
    }

    public void test_InsertingSingleRecordAndRetrievingIt_NoExceptions() {
        /*
            Insert.
         */
        //  Arrange
        String title = "title", description = "description";
        long datetime = 12;
        boolean wasExceptionThrown = false;
        long newId = 0;

        //  Act
        try {
            newId = testedAdapter.create(title, description, datetime);
        } catch (Exception e) {
            wasExceptionThrown = true;
        }

        //  Assert
        assertFalse("Exception was thrown during insertion into Cassette", wasExceptionThrown);
        //  Since this is the first record inserted, newId should be equal to 1.
        assertEquals("New Id is not equal to 1.", 1, newId);
        assertEquals(1, testedAdapter.count());
        /*
            Get the record.
         */
        //  Arrange

        //  act
        Cursor cursor = testedAdapter.getById(newId);
        assertNotNull("Cursor is null.", cursor);
        CassetteEntity cassetteEntity = CassetteEntity.createFromCursor(cursor);


        //  Assert
        assertNotNull("CassetteEntity created from Cursor is null", cassetteEntity);
        assertEquals("CassetteEntity's title does not match", "title", cassetteEntity.title);
        assertEquals("CassetteEntity's title does not match", "description", cassetteEntity.descripition);
        assertEquals("CassetteEntity's title does not match", 12, cassetteEntity.dateTimeOfCreation);
    }

    public void test_thatThereAreNoRowsInTable() {
        assertEquals(0, testedAdapter.count());
    }

    /**
     * Insert a few rows, retrieve them and check if they match up.
     */
    public void test_InsertRows_andGetAll() {
        //  Arrange
        String title1 = "title1", description1 = "desc1";
        long date1 = 1;
        String title2 = "title2", description2 = "desc2";
        long date2 = 2;
        String title3 = "title3", description3 = "desc3";
        long date3 = 3;

        String[] titles = {title1, title2, title3};
        String[] descriptions = {description1, description2, description3};
        long[] dates = {date1, date2, date3};
        long[] identifier = new long[3];

        //  Act
        for (int i = 0; i < 3; i++) {
            identifier[i] = testedAdapter.create(titles[i], descriptions[i], dates[i]);
        }

        //  Assert
        assertEquals(3, testedAdapter.count());
        long[] expectedIdentifiers = {1, 2, 3};
        assertTrue("Identifiers do not match.", Arrays.equals(expectedIdentifiers, identifier));

        //  Act
        //  retrieve rows from db
        CassetteEntity[] cassetteEntities = new CassetteEntity[3];
        for (int i = 0; i < 3; i++) {
            Cursor cursor = testedAdapter.getById(identifier[i]);
            cassetteEntities[i] = CassetteEntity.createFromCursor(cursor);
            //  Assert
            assertEquals(titles[i], cassetteEntities[i].title);
            assertEquals(descriptions[i], cassetteEntities[i].descripition);
            assertEquals(dates[i], cassetteEntities[i].dateTimeOfCreation);
        }
    }

    /**
     * Persist simple entity, then retrieve it, and change every possible field, again retrieve it
     * and check if updated fields were properly update.
     */
    public void test_updatingCassette_everyField() {
        //  Arrange
        /*
            First let's create a basic Entity and then persist the motherfucker.
         */
        String title = "title", description = "desc";
        long date = 12000;

        long id;
        //  Act
        id = testedAdapter.create(title, description, date);

        //  retrieve the entity.

        Cursor cursor = testedAdapter.getById(id);
        CassetteEntity oldCassetteEntity = CassetteEntity.createFromCursor(cursor);
        cursor.close();
        boolean wasSuccess = testedAdapter.update(id, "newTitle", "newDescription", 12, 12, 1, "path",
                13000);

        //  Assert

        assertTrue("Update was not successful", wasSuccess);

        //  Act
        // retrieve the newEntity
        cursor = testedAdapter.getById(id);
        CassetteEntity newCassetteEntity = CassetteEntity.createFromCursor(cursor);
        cursor.close();

        //  Assert
        assertNotNull(newCassetteEntity);
        assertEquals(id, newCassetteEntity.id);
        //  date of creation should be the same, despite the update
        assertEquals(oldCassetteEntity.dateTimeOfCreation, newCassetteEntity.dateTimeOfCreation);
        assertEquals("newTitle", newCassetteEntity.title);
        assertEquals("newDescription", newCassetteEntity.descripition);
        assertEquals(12, newCassetteEntity.length);
        assertEquals(12, newCassetteEntity.numberOfRecordings);
        assertEquals("path", newCassetteEntity.compiledFilePath);
        assertEquals(13000, newCassetteEntity.dateTimeOfCompilation);
    }

    /**
     * Persist simple entity, then retrieve it, and change only length and filepath fields,
     * again retrieve it and check if updated fields were properly update.
     */
    public void test_updatingCassette_justLengthAndFilePathFields() {
        //  Arrange
        /*
            First let's create a basic Entity and then persist the motherfucker.
         */
        String title = "title", description = "desc";
        long date = 12000;

        long id;
        //  Act
        id = testedAdapter.create(title, description, date);

        //  retrieve the entity.

        Cursor cursor = testedAdapter.getById(id);
        CassetteEntity oldCassetteEntity = CassetteEntity.createFromCursor(cursor);
        cursor.close();
        boolean wasSuccess = testedAdapter.update(id, oldCassetteEntity.title, oldCassetteEntity.descripition,
                12, oldCassetteEntity.numberOfRecordings, oldCassetteEntity.isCompiled, "path",
                oldCassetteEntity.dateTimeOfCompilation);

        //  Assert

        assertTrue("Update was not successful", wasSuccess);

        //  Act
        // retrieve the newEntity
        cursor = testedAdapter.getById(id);
        CassetteEntity newCassetteEntity = CassetteEntity.createFromCursor(cursor);
        cursor.close();

        //  Assert
        assertNotNull(newCassetteEntity);
        assertEquals(id, newCassetteEntity.id);
        //  date of creation should be the same, despite the update
        assertEquals(oldCassetteEntity.dateTimeOfCreation, newCassetteEntity.dateTimeOfCreation);
        assertEquals(oldCassetteEntity.title, newCassetteEntity.title);
        assertEquals(oldCassetteEntity.descripition, newCassetteEntity.descripition);
        assertEquals(12, newCassetteEntity.length);
        assertEquals(oldCassetteEntity.numberOfRecordings, newCassetteEntity.numberOfRecordings);
        assertEquals("path", newCassetteEntity.compiledFilePath);
        assertEquals(oldCassetteEntity.dateTimeOfCompilation, newCassetteEntity.dateTimeOfCompilation);
    }

    /**
     * Try updating an entity that doesn't exist. It should return false.
     */
    public void test_update_cassetteThatDoesntExist() {
        //  Arrange
        String title = "title", description = "desc";
        long date = 12000;

        long id = 23;

        CassetteEntity cassetteEntity = new CassetteEntity(title, description, new Date());
        cassetteEntity.id = id;
        //  Act
        boolean wasSuccess = testedAdapter.update(id, "newTitle", "newDescription", 12, 12, 1, "path",
                13000);

        //  Assert
        assertFalse("Update was successful", wasSuccess);
    }

    /**
     * Persist basic entity, delete it, check if it was successful, then query for the number of rows
     * and assert that the count is equal to 0.
     */
    public void test_deleting() {
        //  Arrange
        String title = "title", description = "desc";
        long date = 12000;

        long id;
        //  Act
        id = testedAdapter.create(title, description, date);

        //  retrieve the entity.
        Cursor cursor = testedAdapter.getById(id);
        CassetteEntity oldCassetteEntity = CassetteEntity.createFromCursor(cursor);
        cursor.close();

        boolean wasDeleteSuccessful = testedAdapter.delete(oldCassetteEntity.id);
        int count = testedAdapter.count();
        //  Assert
        assertTrue(wasDeleteSuccessful);
        assertEquals(0, count);
    }

    public void test_deleting_EntityThatDoesntExist() {
        //  Arrange
        long id = 23;
        //  Act
        boolean wasSuccess = testedAdapter.delete(id);
        //  Assert
        assertFalse("Update was successful", wasSuccess);
    }

}
