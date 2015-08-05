package andrewtorski.cassette.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import andrewtorski.cassette.data.entity.CassetteEntity;
import andrewtorski.cassette.domain.entity.Cassette;

/**
 * Helps mapping @{ CassetteEntity } from data access layer to @ { Cassette } present in the
 * domain layer.
 * TODO: Should this be in a helper library instead of DAL?
 */
public class CassetteEntityDataMapper {

    public CassetteEntityDataMapper() {
    }

    /**
     * Takes a @{CassetteEntity} and transforms it to a Cassette. This transformation includes:
     * 1) parsing DateTimes from long to actual Dates.
     * 2) parsing boolean values from integral values.
     * <p/>
     * NOTE: This transformation does not include instantiating a collection of Recordings inside.
     * This should be done in either TODO: repository or data store.
     *
     * @param cassetteEntity CassetteEntity to transform.
     * @return Newly transformed Cassette object, or null.
     */
    public Cassette transform(CassetteEntity cassetteEntity) {
        Cassette cassette;
        if (cassetteEntity == null) {
            return null;
        }

        Date dateTimeOfCreation = new Date(cassetteEntity.dateTimeOfCreation);
        Date dateTimeOfCompilation = new Date(cassetteEntity.dateTimeOfCompilation);
        boolean isCompiled = cassetteEntity.isCompiled == 1;

        cassette = new Cassette(cassetteEntity.getId(), cassetteEntity.title,
                cassetteEntity.descripition, dateTimeOfCreation, cassetteEntity.length, isCompiled,
                cassetteEntity.compiledFilePath, dateTimeOfCompilation,
                cassetteEntity.numberOfRecordings, null);

        /* NOTE:
            This transformation does not include instantiating a collection of Recordings inside.
            This should be done in either CassetteRepository or DataStore  */

        return cassette;
    }

    /**
     * Takes a collection of CassetteEntities and transforms it into a List of Cassettes and returns
     * it.
     *
     * @param cassetteEntityCollection Collection of CassetteEntities to transform.
     * @return List of Cassettes.
     */
    public List<Cassette> transform(Collection<CassetteEntity> cassetteEntityCollection) {
        //TODO: Array list or linked list?
        ArrayList<Cassette> cassetteArrayList = new ArrayList<>();

        if (cassetteEntityCollection == null) {
            return cassetteArrayList;
        }
        Cassette cassette;
        for (CassetteEntity cassetteEntity : cassetteEntityCollection) {
            cassette = this.transform(cassetteEntity);
            if (cassette != null) {
                cassetteArrayList.add(cassette);
            }
        }

        return cassetteArrayList;
    }
}
