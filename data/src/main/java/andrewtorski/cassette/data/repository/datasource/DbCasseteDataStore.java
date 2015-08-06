package andrewtorski.cassette.data.repository.datasource;

import android.content.Context;

import andrewtorski.cassette.data.db.CassetteDataDbAdapter;
import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * {@link CassetteDataStore} implementation based on file system data store.
 */
public class DbCasseteDataStore implements CassetteDataStore {
    private CassetteDataDbAdapter cassetteDataDbAdapter;

    public DbCasseteDataStore(Context context) {
        cassetteDataDbAdapter = new CassetteDataDbAdapter(context);
    }


    @Override
    public CassetteEntity createCassette(CassetteEntity cassetteEntity) {
        return null;
    }

    @Override
    public CassetteEntity getCassetteEntityDetails(int cassetteId) {
        return null;
    }
}
