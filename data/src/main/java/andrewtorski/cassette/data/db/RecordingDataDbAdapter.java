package andrewtorski.cassette.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    /**
     * Singleton instance.
     */
    private static RecordingDataDbAdapter instance;

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
    public RecordingDataDbAdapter(Context context) {
        this.context = context;
    }

    //endregion Constructor

    //region Methods

    /**
     * Opens the connection to the database.
     *
     * @return This instance.
     */
    public RecordingDataDbAdapter open() {
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
     * Inserts basic Recording data into the database.
     *
     * @param cassetteId            Id of the Cassette to which this Recording belongs to.
     * @param sequenceInTheCassette Sequence number in the Cassette.
     * @param dateTimeOfRecording   UNIX time of date and time of recording.
     * @param audioFilePath         Path to the actual audio file.
     * @param length                Length(in milliseconds) of the recording.
     * @return
     */
    public long createRecording(long cassetteId, int sequenceInTheCassette,
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

    public boolean updateRecording(long id, String title, String description) {
        ContentValues values = new ContentValues();

        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_TITLE, title);
        values.put(CassetteDbContract.RecordingTable.COLUMN_NAME_DESCRIPTION, description);

        String whereClause = CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID + "=" + id;

        int rowsAffected = this.db.update(CassetteDbContract.RecordingTable.TABLE_NAME, values,
                whereClause, null);

        return rowsAffected > 0;
    }

    public boolean deleteRecording(long id) {
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
