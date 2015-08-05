package andrewtorski.cassette.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;

/**
 * Gives access to CRUD operations on Cassette table.
 */
public class CassetteDataDbAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private Context context;

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

    /**
     * Intializes a new instance of the CassetteDataDbAdapter class.
     * THIS DOES NOT OPEN THE CONNECTION!
     */
    public CassetteDataDbAdapter(Context context) {
        this.context = context;
    }

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
        db.close();
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
    public Cursor getAll() {
        return db.query(CassetteDbContract.CassetteTable.TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * Searches for the Cassette row of specified identifier and then returns a cursor positioned on the
     * first Cassette row.
     *
     * @param id Identifier of the Cassette row.
     * @return Cursor positioned on the first Cassette row. Null, if nothing was found.
     */
    public Cursor getById(long id) {
        Cursor cursor = this.db.query(true, CassetteDbContract.CassetteTable.TABLE_NAME, null,
                CassetteDbContract.CassetteTable.COLUMN_NAME_ID + "=" + id, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /*
        TODO: Update cassette method.
     */
    public boolean updateCassette() {
        return false;
    }

    /**
     * Deletes a Cassette ow of specified identifier.
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
}
