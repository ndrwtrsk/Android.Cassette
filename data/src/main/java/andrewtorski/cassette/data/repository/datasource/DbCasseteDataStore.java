package andrewtorski.cassette.data.repository.datasource;

import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * {@link CassetteDataStore} implementation based on file system data store.
 */
public class DbCasseteDataStore implements CassetteDataStore
{
    @Override
    public CassetteEntity createCassette(String title, String description) {
        return null;
    }

    @Override
    public CassetteEntity getCassetteEntityDetails(int cassetteId) {
        return null;
    }
}
