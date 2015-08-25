package andrewtorski.cassette.data.repository;

import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;
import andrewtorski.cassette.data.entity.mapper.RecordingEntityDataMapper;
import andrewtorski.cassette.data.repository.datasource.DbRecordingDataStore;
import andrewtorski.cassette.data.repository.datasource.RecordingDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.RecordingRepository;

/**
 * Implementation of the @{andrewtorski.cassette.domain.repository.RecordingRepository}.
 * <p/>
 * For now this implementation uses Database to persist and retrieve data(@{DbRecordingDataStore},
 * but it should pose absolutely no problem of creating a CassetteDataStoreFactory which would
 * provide with access to, say: RestApiCassetteDataStore or perhaps JsonFileCassetteDataStore.
 */
public class RecordingDataRepository implements RecordingRepository {

    //region Private fields

    /**
     * DataStore used to access persistence layer.
     */
    private RecordingDataStore dataStore;

    /**
     * Mapper object used to transform between Cassette and CassetteEntity.
     */
    private RecordingEntityDataMapper mapper = new RecordingEntityDataMapper();

    //endregion Private fields

    //region Constructors

    /**
     * Initializes a new instance of the CassetteDataRepository class using provided
     * {@link andrewtorski.cassette.data.repository.datasource.RecordingDataStore} to access data.
     *
     * @param dataStore RecordingDataStore which will be used by initialized Repository to
     *                  access data.
     */
    public RecordingDataRepository(RecordingDataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Initializes a new instance of the RecordingDataRepository class with it's RecordingDataStore
     * implicitly set to DbRecordingStore which utilizes SQLite database to persist and retrieve data.
     */
    public RecordingDataRepository() {
        this.dataStore = new DbRecordingDataStore();
    }

    //endregion Constructors

    //region RecordingRepository implemented methods.

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Recording get(long id) {
        RecordingEntity recordingEntity = dataStore.get(id);

        return mapper.transfrom(recordingEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Recording recording) {
        RecordingEntity recordingEntity = mapper.transform(recording);

        return dataStore.update(recordingEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Recording recording) {
        return this.delete(recording.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(long id) {
        return dataStore.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recording> getAll() {
        List<RecordingEntity> recordingEntities = dataStore.getAll();

        return mapper.transform(recordingEntities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recording> getAllForCassette(Cassette cassette) {
        List<RecordingEntity> recordingEntityList = dataStore.getAllForCassette(cassette.getId());

        return mapper.transform(recordingEntityList);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recording> getAllWhichTitleOrDescriptionIsLike(String searchClause) {
        List<RecordingEntity> recordingEntityList = dataStore.getAllWithTitleOrDescriptionLike(searchClause);

        return mapper.transform(recordingEntityList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return dataStore.count();
    }

    //endregion RecordingRepository implemented methods.
}
