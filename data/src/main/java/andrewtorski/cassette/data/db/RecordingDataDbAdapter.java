package andrewtorski.cassette.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;
import andrewtorski.global.GlobalValues;

/**
 * Provides access to CRUD operations on Recording table.
 * Uses Singleton pattern to ensure that only one instance of this class exists during the runtime.
 */
public class RecordingDataDbAdapter {

    //region Private Fields

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    private static final String TAG = "RecDataAdapter";

    /**
     * Singleton instance.
     */
    private static RecordingDataDbAdapter instance;

    public int count() {
        Cursor cursor = this.db.rawQuery("SELECT count(*) FROM " + CassetteDbContract.CassetteTable.TABLE_NAME, null);

        if (cursor == null) {
            return -1;
        }
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    //endregion Private Fields

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
            db.execSQL(CassetteDbContract.CassetteTable.getCreateTableStatement());
            db.execSQL(CassetteDbContract.RecordingTable.getCreateTableStatement());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(CassetteDbContract.RecordingTable.getDropTableStatement());
            db.execSQL(CassetteDbContract.CassetteTable.getDropTableStatement());
        }
    }

    //endregion Private Class DatabaseHelper definition.

    //region Constructor

    /**
     * Intializes a new instance of the CassetteDataDbAdapter class.
     * THIS DOES NOT OPEN THE CONNECTION!
     */
    public RecordingDataDbAdapter(Context context) {
        this.context = context;
    }

    //endregion Constructor

    //region Methods

    public RecordingDataDbAdapter open() {
        this.dbHelper = new DatabaseHelper(this.context);
        this.db = this.dbHelper.getWritableDatabase();
        this.db.setForeignKeyConstraintsEnabled(true);
        return this;
    }

    /**
     * Returns true if the database is currently open.
     */
    public boolean isOpen() {
        return (db != null) && db.isOpen();
    }

    public boolean doesCassetteTableExist() {

        final String sqlite_masterTableName = "sqlite_master";
        final String selectionClause = " WHERE name = '" + CassetteDbContract.RecordingTable.TABLE_NAME + "'";

        final String query = "SELECT 1 as result FROM " + sqlite_masterTableName + selectionClause;

        Cursor cursor = this.db.rawQuery(query, null);

        if (cursor == null) {
            return false;
        }

        cursor.moveToFirst();

        int result = cursor.getInt(0);
        cursor.close();
        return result == 1;
    }

    /**
     * Closes the connection to the database.
     */
    public void close() {
        if (db != null && db.isOpen()) {
            this.db.close();
        }
    }

    /**
     * Inserts basic Recording data into the database.
     *
     * @param cassetteId            Id of the Cassette to which this Recording belongs to.
     * @param sequenceInTheCassette Sequence number in the Cassette.
     * @param dateTimeOfRecording   UNIX time of date and time of recording.
     * @param audioFilePath         Path to the actual audio file.
     * @param length                Length(in milliseconds) of the recording.
     * @return Id of the newly created recording, -1 if insertion didn't succeed.
     */
    public long create(long cassetteId, int sequenceInTheCassette,
                       long dateTimeOfRecording, String audioFilePath,
                       int length) {
        ContentValues values = new ContentValues();
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID, cassetteId);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_SEQUENCE_IN_CASSETTE, sequenceInTheCassette);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_DATE_TIME_OF_RECORDING, dateTimeOfRecording);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_AUDIO_FILE_PATH, audioFilePath);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_LENGTH, length);

        return this.db.insert(CassetteDbContract.RecordingTable.TABLE_NAME, null, values);
    }

    /**
     * Returns a cursor positioned on the Recording of specified id.
     *
     * @param id Identifier of the Recording to retrieve.
     * @return Cursor positioned on the Recording, or null if no Recording of specified id was found.
     */
    public Cursor getById(long id) {
        /*
            HERE WAS A BUG BUT I CRUSHED IT.
            What was wrong: Instead of COLUMN_NAME_CASSETTE_ID, COLUMN_NAME_ID was specified which
            didn't do much right...
         */
        String selection = CassetteDbContract.RecordingTable.COLUMN_NAME_ID + "=" + id;
        Cursor cursor = this.db.query(true, CassetteDbContract.RecordingTable.TABLE_NAME, null, selection, null,
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        for (String columnName : cursor.getColumnNames()) {
            Log.d("RecordinAdapter", "col: " + columnName);
        }
        return cursor;
    }

    /**
     * Returns a cursor containing all rows in Recording table.
     */
    public Cursor getAll() {
        return this.db.query(CassetteDbContract.RecordingTable.TABLE_NAME, null, null, null, null,
                null, null);
    }

    /**
     * Returns a cursor containing all Recordings which date of recording is contained within the
     * provided epoch time span.
     *
     * @param fromDate From date, epoch time.
     * @param toDate   To date, epoch time.
     * @return Cursor.
     */
    public Cursor getAllBetween(long fromDate, long toDate) {
        String betweenSelectClause = CassetteDbContract.RecordingTable.COLUMN_NAME_DATE_TIME_OF_RECORDING
                + " BETWEEN " + fromDate + " AND " + toDate;

        String orderBy = "DESCENDING";

        return db.query(true, CassetteDbContract.RecordingTable.TABLE_NAME, null, betweenSelectClause, null,
                null, null, orderBy, null);
    }

    public Cursor getAllForCassetteBetweenDates(long cassetteId, long fromDate, long toDate) {
        String betweenSelectClause = CassetteDbContract.RecordingTable.COLUMN_NAME_DATE_TIME_OF_RECORDING
                + " BETWEEN " + fromDate + " AND " + toDate + " AND "
                + CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID + "=" + cassetteId;

        String orderBy = "DESCENDING";

        return db.query(true, CassetteDbContract.RecordingTable.TABLE_NAME, null, betweenSelectClause, null,
                null, null, orderBy, null);
    }

    public Cursor getAllTitleDescriptionLike(String likeWhat) {
        String likeClause = CassetteDbContract.RecordingTable.COLUMN_NAME_TITLE + " LIKE " + likeWhat;
        likeClause += " OR " + CassetteDbContract.RecordingTable.COLUMN_NAME_DESCRIPTION + " LIKE " + likeWhat;

        return db.query(true, CassetteDbContract.RecordingTable.TABLE_NAME, null, likeClause, null,
                null, null, null, null);
    }

    /**
     * Returns a cursor containing Recordings which belong to the Cassette of the specified id.
     *
     * @param cassetteId Identifier of the Cassette for which are looking for connected Recordings.
     * @return Cursor containing Recordings.
     */
    public Cursor getAllForCassette(long cassetteId) {
        String selection = CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID + "=" + cassetteId;
        Log.d(TAG, "Selection clause = '" + selection + "'");
        return this.db.query(CassetteDbContract.RecordingTable.TABLE_NAME, null, selection, null, null,
                null, null);
    }

    /**
     * Updates Recording of specified if with provided title and description.
     *
     * @param id          Identifier of the Recording to update.
     * @param title       New title.
     * @param description New description.
     * @return Was update successful.
     */
    public boolean update(long id, String title, String description) {
        ContentValues values = new ContentValues();

        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_TITLE, title);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_DESCRIPTION, description);

        String whereClause = CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID + "=" + id;

        int rowsAffected = this.db.update(CassetteDbContract.RecordingTable.TABLE_NAME, values,
                whereClause, null);

        return rowsAffected > 0;
    }

    /**
     * Deletes Recording of specified id.
     *
     * @param id Identifier of the Recording to delete.
     * @return Was deletion successful.
     */
    public boolean delete(long id) {
        String whereClause = CassetteDbContract.RecordingTable.COLUMN_NAME_ID + "=" + id;

        int recordsDeleted = this.db.delete(CassetteDbContract.RecordingTable.TABLE_NAME, whereClause,
                null);

        return recordsDeleted > 0;
    }

    //endregion Methods

    //region Static Methods

    public static synchronized RecordingDataDbAdapter getInstance() {
        if (instance == null) {
            instance = new RecordingDataDbAdapter(GlobalValues.getContext());
        }

        return instance;
    }

    //endregion Static Methods
}
