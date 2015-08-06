package andrewtorski.cassette.data.repository;

import java.util.ArrayList;
import java.util.List;

import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.cassette.data.entity.mapper.CassetteEntityDataMapper;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;

/**
 * Implementation of the @{andrewtorski.cassette.domain.repository.CassetteRepository} .
 *
 * For now this implementation uses Database to persist and retrieve data(@{DbCassetteDataStore},
 * but it should pose absolutely no problem of creating a CassetteDataStoreFactory which would
 * provide with access to, say: RestApiCassetteDataStore or perhaps JsonFileCassetteDataStore.
 *
 *
 */
public class CassetteDataRepository implements CassetteRepository {

    private CassetteDataStore cassetteDataStore;
    //private RecordingDataStore recordingDataStore;
    //or
    //private RecordingRepository???

    private CassetteEntityDataMapper mapper;

    public CassetteDataRepository(CassetteDataStore cassetteDataStore) {
        this.cassetteDataStore = cassetteDataStore;
        mapper = new CassetteEntityDataMapper();
    }

    @Override
    public Cassette createCassette(Cassette cassette) {
        CassetteEntity cassetteEntity = new CassetteEntity(cassette.getTitle(),
                cassette.getDescription(), cassette.getDateTimeOfCreation());

        cassetteEntity = cassetteDataStore.createCassette(cassetteEntity);

        cassette = mapper.transform(cassetteEntity);

        return cassette;
    }

    @Override
    public Cassette getCassette(int cassetteId) {
        CassetteEntity cassetteEntity = cassetteDataStore.getCassetteEntityDetails(cassetteId);

        if (cassetteEntity == null) {
            return null;
        }

        //  TODO: Associate Cassette with Recordings!!!!
        return mapper.transform(cassetteEntity);
    }

    @Override
    public List<Cassette> getAll() {
        //  linked list
        List<CassetteEntity> cassetteEntityList = cassetteDataStore.getAll();


        ArrayList<Cassette> cassetteList = new ArrayList<>(mapper.transform(cassetteEntityList));

        //  TODO: Associate Cassettes with their Recordings!!!!

        return cassetteList;
    }

    @Override
    public boolean update(Cassette cassette) {
        CassetteEntity cassetteEntity = mapper.transform(cassette);

        boolean wasSuccess = cassetteDataStore.update(cassetteEntity);

        return wasSuccess;
    }

    @Override
    public boolean delete(Cassette cassette) {
        if (cassette == null) {
            return false;
        }

        return delete(cassette.getId());
    }

    @Override
    public boolean delete(long id) {
        boolean wasSuccess = cassetteDataStore.delete(id);

        return wasSuccess;
    }
}
