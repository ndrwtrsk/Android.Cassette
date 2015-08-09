package andrewtorski.cassette.data.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.cassette.data.entity.mapper.CassetteEntityDataMapper;
import andrewtorski.cassette.data.repository.datasource.CassetteDataStore;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;
import andrewtorski.cassette.domain.repository.CassetteRepository;
import andrewtorski.cassette.domain.repository.RecordingRepository;

/**
 * Implementation of the @{andrewtorski.cassette.domain.repository.CassetteRepository}.
 *
 * For now this implementation uses Database to persist and retrieve data(@{DbCassetteDataStore},
 * but it should pose absolutely no problem of creating a CassetteDataStoreFactory which would
 * provide with access to, say: RestApiCassetteDataStore or perhaps JsonFileCassetteDataStore.
 */
public class CassetteDataRepository implements CassetteRepository {

    private CassetteDataStore cassetteDataStore;

    private RecordingRepository recordingRepository;

    private CassetteEntityDataMapper mapper;

    public CassetteDataRepository(CassetteDataStore cassetteDataStore) {
        this.cassetteDataStore = cassetteDataStore;
        mapper = new CassetteEntityDataMapper();
    }

    /**
     * Creates a Cassette using title and description.
     *
     * @param cassette Cassette to be persisted.
     */
    @Override
    public Cassette createCassette(Cassette cassette) {
        CassetteEntity cassetteEntity = new CassetteEntity(cassette.getTitle(),
                cassette.getDescription(), cassette.getDateTimeOfCreation());

        cassetteEntity = cassetteDataStore.createCassette(cassetteEntity);

        cassette = mapper.transform(cassetteEntity);

        return cassette;
    }

    /** Retrieves a Cassette using the provided id.
     *
     *  Returned Cassette already has it's list of associated Recordings populated.
     *
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found. */
    @Override
    public Cassette getCassette(int cassetteId) {
        CassetteEntity cassetteEntity = cassetteDataStore.getCassetteEntityDetails(cassetteId);

        if (cassetteEntity == null) {
            return null;
        }

        //  TODO: Associate Cassette with Recordings!!!!
        return mapper.transform(cassetteEntity);
    }

    /**
     * Returns a list of all Cassettes present.
     * These Cassettes do not include their associated Recordings.
     * Should be used for listing purposes exclusively.
     * @return List of Cassettes.
     */
    @Override
    public List<Cassette> getAll() {
        //  linked list
        List<CassetteEntity> cassetteEntityList = cassetteDataStore.getAllCassettes();

        List<Cassette> cassetteList = new LinkedList<>(mapper.transform(cassetteEntityList));

        return cassetteList;
    }

    /**
     * Returns all CassetteEntities which were created between provided date span.
     * List is sorted descendingly.
     * Date span is expressed as two UNIX time values.
     * These Cassettes do not include their associated Recordings.
     * Should be used for listing purposes exclusively.
     *
     * @param fromDate UNIX time representing from-date.
     * @param toDate   UNIX time representing to-date.
     * @return List of CassetteEntities.
     */
    @Override
    public List<Cassette> getAllCassettesBetweenDatesDescending(Date fromDate, Date toDate) {
        long unixFromDate = fromDate.getTime(),
                unixToDate = toDate.getTime();
        List<CassetteEntity> cassetteEntityList = cassetteDataStore.getAllCassettesBetweenDatesDescending(unixFromDate, unixToDate);
        List<Cassette> resultList = new LinkedList<>();

        Cassette cassette;
        for (CassetteEntity cassetteEntity : cassetteEntityList) {
            cassette = mapper.transform(cassetteEntity);
            if (cassette != null) {
                resultList.add(cassette);
            }
        }

        return resultList;
    }

    /**
     * Populates provided Cassette's collection of Recordings.
     *
     * @param cassette Cassette to initialize.
     */
    @Override
    //TODO: return boolean for successful operation?
    public void initializeCassetteWithRecordings(Cassette cassette) {
        if (cassette == null) {
            return;
        }

        List<Recording> associatedRecordings = recordingRepository.getRecordingsForCassette(cassette);

        cassette.setRecordings(associatedRecordings);
    }

    /**
     * Updates Cassette.
     *
     * @param cassette Cassette to be updated.
     * @return Was update successful.
     */
    @Override
    public boolean update(Cassette cassette) {
        CassetteEntity cassetteEntity = mapper.transform(cassette);

        boolean wasSuccess = cassetteDataStore.updateCassette(cassetteEntity);

        return wasSuccess;
    }

    /**
     * Deletes Cassette.
     *
     * @param cassette Cassette to be deleted.
     * @return Was deletion successful.
     */
    @Override
    public boolean delete(Cassette cassette) {
        if (cassette == null) {
            return false;
        }

        return delete(cassette.getId());
    }

    /**
     * Deletes Cassette of specified Id.
     *
     * @param id Id of the Cassette to be deleted.
     * @return Was deletion successful.
     */
    @Override
    public boolean delete(long id) {
        boolean wasSuccess = cassetteDataStore.deleteCassette(id);

        return wasSuccess;
    }
}
