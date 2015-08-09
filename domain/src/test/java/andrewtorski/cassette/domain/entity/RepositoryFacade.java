package andrewtorski.cassette.domain.entity;

import java.util.List;

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

    public CassetteRepository getCassetteRepository() {
        return cassetteRepository;
    }

    public RecordingRepository getRecordingRepository() {
        return recordingRepository;
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
}
