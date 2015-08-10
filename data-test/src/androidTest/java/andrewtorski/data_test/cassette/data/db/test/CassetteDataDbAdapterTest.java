package andrewtorski.data_test.cassette.data.db.test;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import andrewtorski.cassette.data.db.CassetteDataDbAdapter;
import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.global.GlobalValues;

/**
 * Tests functionalities provided by the CassetteDataDbAdapter.
 */
public class CassetteDataDbAdapterTest extends AndroidTestCase {

    private CassetteDataDbAdapter testedAdapter;

    /**
     * This method is executed before ANY of the following test methods.
     *
     * @throws Exception
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
            newId = testedAdapter.createCassette(title, description, datetime);
        } catch (Exception e) {
            wasExceptionThrown = true;
        }

        //  Assert
        assertFalse("Exception was thrown during insertion into Cassette", wasExceptionThrown);
        //  Since this is the first record inserted, newId should be equal to 1.
        assertEquals("New Id is not equal to 1.", 1, newId);

        /*
            Get the record.
         */
        //  Arrange

        //  act
        Cursor cursor = testedAdapter.getCassetteById(newId);
        assertNotNull("Cursor is null.", cursor);
        CassetteEntity cassetteEntity = CassetteEntity.createCassetteEntityFromCursor(cursor);


        //  Assert
        assertNotNull("CassetteEntity created from Cursor is null", cassetteEntity);
        assertEquals("CassetteEntity's title does not match", "title", cassetteEntity.title);
        assertEquals("CassetteEntity's title does not match", "description", cassetteEntity.descripition);
        assertEquals("CassetteEntity's title does not match", 12, cassetteEntity.dateTimeOfCreation);
    }

    public void test_thatThereAreNoRowsInTable() {

    }

}
