package andrewtorski.cassette.data.repository;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;
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

    private RecordingDataStore dataStore;

    private RecordingEntityDataMapper mapper;

    public RecordingDataRepository(RecordingDataStore dataStore) {
        this.dataStore = dataStore;
        mapper = new RecordingEntityDataMapper();
    }


    @Override
    public Recording create(Recording recording) {
        if (recording == null) {
            return null;
        }

        //TODO skip creating recordinEntity
        RecordingEntity recordingEntity = new RecordingEntity(recording.getCassetteId(), recording.getSequenceInTheCassette(),
                recording.getDateTimeOfRecording(), recording.getAudioFilePath(), recording.getLengthInMiliseconds());

        recordingEntity = dataStore.create(recordingEntity);

        recording = mapper.transfrom(recordingEntity);

        return recording;
    }

    @Override
    public boolean update(Recording recording) {
        RecordingEntity recordingEntity = mapper.transform(recording);

        boolean wasSuccess = dataStore.update(recordingEntity);

        return wasSuccess;
    }

    @Override
    public boolean delete(Recording recording) {
        return this.delete(recording.getId());
    }

    @Override
    public boolean delete(long id) {
        return dataStore.delete(id);
    }

    @Override
    public List<Recording> getAllForCassette(Cassette cassette) {
        List<RecordingEntity> recordingEntityList = dataStore.getAllForCassette(cassette.getId());

        return mapper.transform(recordingEntityList);
    }

    @Override
    public List<Recording> getAllBetweenDatesForCassette(Date fromDate, Date toDate) {
        //TODO what about nulls?
        //If both are null: return all
        //If fromDate is null, return all recordings up to toDate?
        //if toDate is null, return all recordings down from fromDate?
        long epochFromDate = fromDate.getTime(),
                epochToDate = toDate.getTime();
        List<RecordingEntity> recordingEntityList = dataStore.getAllBetweenDates(epochFromDate, epochToDate);

        return mapper.transform(recordingEntityList);
    }

    @Override
    public List<Recording> getAllBetweenDatesForCassette(Cassette cassette, Date fromDate, Date toDate) {
        //TODO what about nulls?
        //If both are null: return all
        //If fromDate is null, return all recordings up to toDate?
        //if toDate is null, return all recordings down from fromDate?
        long epochFromDate = fromDate.getTime(),
                epochToDate = toDate.getTime();
        List<RecordingEntity> recordingEntityList
                = dataStore.getAllBetweenDatesForCassette(cassette.getId(),
                epochFromDate, epochToDate);

        return mapper.transform(recordingEntityList);
    }

    @Override
    public List<Recording> getAllWhichTitleOrDescriptionIsLike(String searchClause) {
        List<RecordingEntity> recordingEntityList = dataStore.getAllWithTitleOrDescriptionLike(searchClause);

        return mapper.transform(recordingEntityList);
    }
}
