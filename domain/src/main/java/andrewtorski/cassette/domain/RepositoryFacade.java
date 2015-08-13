package andrewtorski.cassette.domain;

import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.cassette.domain.repository.RecordingRepository;

/**
 * Provides public access to individual repositories and exposes operations which require two
 * repositories to finish. (like populate recording list inside cassette).
 */
public class RepositoryFacade {

    private CassetteRepository cassetteRepository;
    private RecordingRepository recordingRepository;

    public RepositoryFacade(CassetteRepository cassetteRepository, RecordingRepository recordingRepository) {
        this.cassetteRepository = cassetteRepository;
        this.recordingRepository = recordingRepository;
    }


    //region Cassette related Methods

    public Cassette create(Cassette cassette) {
        return cassetteRepository.create(cassette);
    }

    public boolean update(Cassette cassette) {
        return cassetteRepository.update(cassette);
    }

    public boolean delete(Cassette cassette) {
        //This will trigger cascade delete, no need to manually remove Recordings.
        return cassetteRepository.delete(cassette);
    }

    public List<Cassette> getAll() {
        return cassetteRepository.getAll();
    }

    public Cassette get(long cassetteId, boolean withRecordingsInitialized) {
        Cassette cassette = cassetteRepository.get(cassetteId);

        if (withRecordingsInitialized) {
            populateRecordings(cassette);
        }

        return cassette;
    }

    /**
     * Populates provided Cassette's list of owned Recordings.
     *
     * @param cassette Cassette to update.
     */
    public void populateRecordings(Cassette cassette) {
        if (cassette == null) {
            return;
        }

        List<Recording> recordingList = recordingRepository.getAllForCassette(cassette);

        cassette.setRecordings(recordingList);
    }

    //endregion Cassette related Methods

    //region Recording related Methods

    public Recording create(Recording recording) {
        return recordingRepository.create(recording);
    }

    public boolean update(Recording recording) {
        return recordingRepository.update(recording);
    }

    public boolean delete(Recording recording) {
        return recordingRepository.delete(recording);
    }

    //endregion Recording related Methods

}
