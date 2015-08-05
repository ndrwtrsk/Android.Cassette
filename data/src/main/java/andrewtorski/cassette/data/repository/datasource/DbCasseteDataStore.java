package andrewtorski.cassette.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import andrewtorski.cassette.data.db.CassetteAppDbHelper;
import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * {@link CassetteDataStore} implementation based on file system data store.
 */
public class DbCasseteDataStore implements CassetteDataStore
{
    private CassetteAppDbHelper dbHelper;
    private SQLiteDatabase db;

    public DbCasseteDataStore(Context context) {
        dbHelper = new CassetteAppDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    @Override
    public CassetteEntity createCassette(String title, String description, Date dateTimeOfCreation) {

    }

    @Override
    public CassetteEntity getCassetteEntityDetails(int cassetteId) {
        return null;
    }
}
