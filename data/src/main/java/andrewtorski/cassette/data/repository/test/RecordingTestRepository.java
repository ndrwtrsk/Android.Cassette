package andrewtorski.cassette.data.repository.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.RecordingRepository;

public class RecordingTestRepository implements RecordingRepository {

    private List<Recording> recordingList = SampleDataProvider.getRecordings();

    public RecordingTestRepository() {

    }

    @Override
    public Recording create(Recording recording) {
        recordingList.add(recording);
        return recording;
    }

    @Override
    public Recording get(long id) {
        Recording foundRecording = null;

        for (int i = 0; i < recordingList.size(); i++) {
            foundRecording = recordingList.get(i);
            if (foundRecording.getId() == id) {
                return foundRecording;
            }
        }

        return foundRecording;
    }

    @Override
    public boolean update(Recording recording) {
        if (recording == null) {
            return false;
        }

        Recording foundRecording;

        for (int i = 0; i < recordingList.size(); i++) {
            foundRecording = recordingList.get(i);
            if (foundRecording.getId() == recording.getId()) {
                recordingList.set(i, recording);
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean delete(Recording recording) {
        return recording != null && delete(recording.getId());
    }

    @Override
    public boolean delete(long id) {
        Recording foundRecording;
        for (int i = 0; i < recordingList.size(); i++) {
            foundRecording = recordingList.get(i);
            if (foundRecording.getId() == id) {
                recordingList.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Recording> getAll() {
        return recordingList;
    }

    @Override
    public List<Recording> getAllForCassette(Cassette cassette) {
        List<Recording> recordingsForCassette = new LinkedList<>();
        if (cassette == null) {
            return recordingsForCassette;
        }

        long cassetteId = cassette.getId();

        for (Recording recording : recordingList) {
            if (recording.getCassetteId() == cassetteId) {
                recordingsForCassette.add(recording);
            }
        }

        return recordingsForCassette;
    }

    @Override
    public List<Recording> getAllBetweenDatesForCassette(Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public List<Recording> getAllBetweenDatesForCassette(Cassette cassette, Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public List<Recording> getAllWhichTitleOrDescriptionIsLike(String searchClause) {
        return null;
    }

    @Override
    public int count() {
        return recordingList.size();
    }
}
