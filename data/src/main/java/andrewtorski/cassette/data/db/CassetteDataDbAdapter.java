package andrewtorski.cassette.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;
import andrewtorski.global.GlobalValues;

/**
 * Gives access to CRUD operations on Cassette table.
 * Uses Singleton pattern to ensure that only one instance of this class exists during the runtime.
 */
public class CassetteDataDbAdapter {


    //region Private Fields

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    /**
     * Singleton instance.
     */
    private static CassetteDataDbAdapter instance;
    //endregion Private Static Fields

    //region Private Class DatabaseHelper definition.

    /**
     * Gives access to the database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, CassetteDbContract.DATABASE_NAME, null, CassetteDbContract.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    //endregion Private Class DatabaseHelper definition.

    //region Constructor

    /**
     * Intializes a new instance of the CassetteDataDbAdapter class.
     * THIS DOES NOT OPEN THE CONNECTION!
     */
    private CassetteDataDbAdapter(Context context) {
        this.context = context;
    }

    //endregion Constructor

    //region Methods

    /**
     * Opens the connection to the database.
     *
     * @return This instance.
     */
    public CassetteDataDbAdapter open() {
        this.dbHelper = new DatabaseHelper(this.context);
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the connection to the database.
     */
    public void close() {
        this.db.close();
    }

    /**
     * Inserts basic Cassette data into the database.
     *
     * @param title              Title of the Cassette.
     * @param description        Description of the Cassette.
     * @param dateTimeOfCreation Date and Time of the creation of the Cassette. UNIX time expressed
     *                           as seconds.
     * @return Id of the newly inserted row or -1 if insertion was not possible.
     */
    public long createCassette(String title, String description, long dateTimeOfCreation) {
        ContentValues values = new ContentValues();
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_TITLE, title);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_DESCRIPTION, description);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_DATE_TIME_OF_CREATION, dateTimeOfCreation);

        return this.db.insert(CassetteDbContract.CassetteTable.TABLE_NAME, null, values);
    }

    /**
     * Returns all rows in Cassette table by means of a Cursor.
     *
     * @return Cursor containing all Cassette rows.
     */
    public Cursor getAllCassettes() {
        return db.query(CassetteDbContract.CassetteTable.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllCassettesCreatedBetweenDatesDescending(long fromDate, long toDate) {
        String betweenSelectClause = CassetteDbContract.CassetteTable.COLUMN_NAME_DATE_TIME_OF_CREATION
                + " BETWEEN " + fromDate + " AND " + toDate;
        String orderBy = "DESCENDING";

        return db.query(true, CassetteDbContract.CassetteTable.TABLE_NAME, null, betweenSelectClause,
                null, null, null, orderBy, null);
    }

    /**
     * Searches for the Cassette row of specified identifier and then returns a cursor positioned on the
     * first Cassette row.
     *
     * @param id Identifier of the Cassette row.
     * @return Cursor positioned on the first Cassette row. Null, if nothing was found.
     */
    public Cursor getCassetteById(long id) {
        Cursor cursor = this.db.query(true, CassetteDbContract.CassetteTable.TABLE_NAME, null,
                CassetteDbContract.CassetteTable.COLUMN_NAME_ID + "=" + id, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     *  Updates Cassette row of specified id with provided data.
     *
     *  CassetteEntity's dateTimeOfCreation is not specified in the method's argument as it is not
     *  expected for the date of creation to change once CassetteEntity was created.
     *
     * @param id Identifier of the Cassette row.
     * @param title New title of the Cassette.
     * @param description New description of the Cassette.
     * @param length new length in milliseconds of all Recordings contained on updated Cassette.
     * @param numberOfRecordings New count of Recordings contained on update Cassette.
     * @param isCompiled Was this Cassette compiled.
     * @param compiledFilePath New compiled file path for updated Cassette.
     * @param dateTimeOfCompilation New date and time of compilation of updated Cassette.
     * @return Was anything updated.
     */
    public boolean updateCassette(long id, String title, String description, int length, int numberOfRecordings,
                                  int isCompiled, String compiledFilePath, long dateTimeOfCompilation) {

        ContentValues values = new ContentValues();

        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_TITLE, title);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_DESCRIPTION, description);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_LENGTH, length);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_NUMBER_OF_RECORDINGS, numberOfRecordings);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_IS_COMPILED, isCompiled);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_COMPILED_FILE_PATH, compiledFilePath);
        values.put(CassetteDbContract.CassetteTable.COLUMN_NAME_DATE_TIME_OF_COMPILATION, dateTimeOfCompilation);

        int rowsAffected = this.db.update(CassetteDbContract.CassetteTable.TABLE_NAME, values,
                CassetteDbContract.CassetteTable.COLUMN_NAME_ID + "=" + id, null);

        return rowsAffected > 0;
    }

    /**
     * Deletes a Cassette row of specified identifier.
     *
     * @param id Identifier of the Cassette row.
     * @return Was any row deleted.
     */
    public boolean deleteCassette(long id) {
        int recordsDeleted = this.db.delete(CassetteDbContract.CassetteTable.TABLE_NAME,
                CassetteDbContract.CassetteTable.COLUMN_NAME_ID + "=" + id,
                null);
        return recordsDeleted > 0;
    }

    //endregion Methods

    //region Static Methods


    /**
     * Retrieves singleton instance of the CassetteDataDbAdapter.
     *
     * @return Singleton instance of the CassetteDataDbAdapter.
     */
    public static synchronized CassetteDataDbAdapter getInstance() {
        return instance == null ? new CassetteDataDbAdapter(GlobalValues.getContext()) : instance;
    }

    //endregion Static Methods

}
