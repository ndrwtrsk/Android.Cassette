package andrewtorski.cassette.data.repository;

import java.util.List;

import andrewtorski.cassette.data.entity.mapper.RecordingEntityDataMapper;
import andrewtorski.cassette.data.repository.datasource.RecordingDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.RecordingRepository;

/**
 * Implementation of the @{andrewtorski.cassette.domain.repository.RecordingRepository}.
 *
 * For now this implementation uses Database to persist and retrieve data(@{DbRecordingDataStore},
 * but it should pose absolutely no problem of creating a CassetteDataStoreFactory which would
 * provide with access to, say: RestApiCassetteDataStore or perhaps JsonFileCassetteDataStore.
 */
public class RecordingDataRepository implements RecordingRepository {

    private RecordingDataStore recordingDataStore;

    private RecordingEntityDataMapper mapper;

    public RecordingDataRepository(RecordingDataStore recordingDataStore) {
        this.recordingDataStore = recordingDataStore;
        mapper = new RecordingEntityDataMapper();
    }


    @Override
    public Recording createRecording(Recording recording) {
        return null;
    }

    @Override
    public boolean update(Recording recording) {
        return false;
    }

    @Override
    public boolean delete(Recording recording) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Recording> getRecordingsForCassette(Cassette cassette) {
        return null;
    }
}
