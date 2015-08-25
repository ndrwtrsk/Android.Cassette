package andrewtorski.cassette.data.repository.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.repository.CassetteRepository;

/**
 * Created by andrew on 24/08/15.
 */
public class CassetteTestRepository implements CassetteRepository {
    ArrayList<Cassette> cassetteList = new ArrayList<>();


    @Inject
    public CassetteTestRepository() {
        cassetteList.addAll(SampleDataProvider.getCassettes());
    }

    /**
     * Persists provided Cassette..
     *
     * @param cassette Cassette to be persisted.
     */
    @Override
    public Cassette create(Cassette cassette) {
        cassetteList.add(cassette);
        return cassette;
    }

    /**
     * Retrieves a Cassette using the provided id.
     *
     * @param cassetteId Id of the Cassette.
     * @return Reference to Cassette or null if nothing was found.
     */
    @Override
    public Cassette get(long cassetteId) {
        Cassette foundCassette;

        for (int i = 0; i < cassetteList.size(); i++) {
            foundCassette = cassetteList.get(i);
            if (foundCassette.getId() == cassetteId) {
                return foundCassette;
            }
        }

        return null;
    }

    /**
     * Returns a list of all Cassettes present.
     * These Cassettes do not include their associated Recordings.
     * Should be used for listing purposes exclusively.
     *
     * @return List of Cassettes.
     */
    @Override
    public List<Cassette> getAll() {
        return cassetteList;
    }

    /**
     * Returns all CassetteEntities which were created between provided date span.
     * List is sorted descendingly.
     * Date span is expressed as two UNIX time values.
     *
     * @param fromDate UNIX time representing from-date.
     * @param ToDate   UNIX time representing to-date.
     * @return List of CassetteEntities.
     */
    @Override
    public List<Cassette> getAllBetweenDatesDescending(Date fromDate, Date ToDate) {
        return null;
    }

    /**
     * Updates Cassette.
     *
     * @param cassette Cassette to be updated.
     * @return Was update successful.
     */
    @Override
    public boolean update(Cassette cassette) {
        Cassette foundCassette;

        for (int i = 0; i < cassetteList.size(); i++) {
            foundCassette = cassetteList.get(i);
            if (foundCassette.getId() == cassette.getId()) {
                cassetteList.set(i, cassette);
                return true;
            }
        }

        return false;
    }

    /**
     * Deletes Cassette.
     *
     * @param cassette Cassette to be deleted.
     * @return Was deletion successful.
     */
    @Override
    public boolean delete(Cassette cassette) {
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
        Cassette foundCassette;
        for (int i = 0; i < cassetteList.size(); i++) {
            foundCassette = cassetteList.get(i);
            if (foundCassette.getId() == id) {
                cassetteList.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Return the number of Cassettes persisted.
     */
    @Override
    public int count() {
        return cassetteList.size();
    }
}

