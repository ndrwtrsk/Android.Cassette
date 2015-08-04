package andrewtorski.cassette.data.repository.datasource;

import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * Created by andrew on 04.08.15.
 */
public class AaCassetteDataStore implements CassetteDataStore {
    @Override
    public CassetteEntity createCassette(String title, String description) {
        return null;
    }

    @Override
    public CassetteEntity getCassetteEntityDetails(int cassetteId) {
        return CassetteEntity.getById(cassetteId);
    }
}
