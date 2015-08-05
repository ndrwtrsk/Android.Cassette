package andrewtorski.cassette.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;

/**
 * Gives access to Cassette App's SQLite database.
 */
public class CassetteAppDbHelper extends SQLiteOpenHelper {

    public CassetteAppDbHelper(Context context) {
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
        onCreate(db);
    }
}
