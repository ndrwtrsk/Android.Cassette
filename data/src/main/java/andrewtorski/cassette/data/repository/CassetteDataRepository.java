package andrewtorski.cassette.data.repository;

import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;

/**
 * Created by andrew on 04.08.15.
 */
public class CassetteDataRepository implements CassetteRepository {

    private CassetteDataStore cassetteDataStore;

    @Override
    public void createCassette(String title, String description) {

    }

    @Override
    public Cassette getCassette(int cassetteId) {
        return null;
    }
}
