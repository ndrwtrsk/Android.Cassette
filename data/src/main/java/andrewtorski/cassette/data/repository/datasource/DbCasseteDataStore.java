package andrewtorski.cassette.data.repository.datasource;

import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.db.CassetteDataDbAdapter;
import andrewtorski.cassette.data.entity.CassetteEntity;

/**
 * {@link CassetteDataStore} implementation based on SQLite database.
 */
public class DbCasseteDataStore implements CassetteDataStore {
    private CassetteDataDbAdapter dbAdapter;

    public DbCasseteDataStore() {
        dbAdapter = CassetteDataDbAdapter.getInstance();
    }


    @Override
    public CassetteEntity create(CassetteEntity cassetteEntity) {
        String title = cassetteEntity.title,
                description = cassetteEntity.descripition;
        long dateTimeOfCreation = cassetteEntity.dateTimeOfCreation;

        long id = dbAdapter.createCassette(title, description, dateTimeOfCreation);

        //TODO: what to do when -1 is returned from create?
        //  1)  return null
        //  2)  return cassetteEntity with id as -1
        //      and then perform some additional actions in either domain layer or presentation?

        cassetteEntity.id = id;

        return cassetteEntity;
    }

    @Override
    public CassetteEntity get(long cassetteId) {
        Cursor cursor = dbAdapter.getCassetteById(cassetteId);

        if (cursor == null) {
            return null;
        }

        CassetteEntity cassetteEntity = CassetteEntity.createCassetteEntityFromCursor(cursor);

        return cassetteEntity;
    }

    /**
     * Returns a linked list of all existent CassetteEntities.
     *
     * @return Linked list of CassetteEntities.
     */
    @Override
    public List<CassetteEntity> getAll() {
        //  LinkedList is used here, because indexing operation is completely out of our interests
        //  right now. ArrayList would prove to be slower here, as it has to be extended as it's
        //  capacity changes.
        LinkedList<CassetteEntity> cassetteEntityLinkedList = new LinkedList<>();

        Cursor cursor = dbAdapter.getAllCassettes();

        if (cursor == null) {
            return cassetteEntityLinkedList;
        }

        CassetteEntity cassetteEntity;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            cassetteEntity = CassetteEntity.createCassetteEntityFromCursor(cursor);
            cassetteEntityLinkedList.add(cassetteEntity);
        }

        return cassetteEntityLinkedList;
    }

    @Override
    public List<CassetteEntity> getAllBetweenDates(long fromDate, long toDate) {

        List<CassetteEntity> cassetteEntityList = new LinkedList<>();

        Cursor cursor = dbAdapter.getAllCassettesCreatedBetweenDatesDescending(fromDate, toDate);

        if (cursor == null) {
            return cassetteEntityList;
        }

        CassetteEntity cassetteEntity;
        while (cursor.moveToNext()) {
            cassetteEntity = CassetteEntity.createCassetteEntityFromCursor(cursor);
            cassetteEntityList.add(cassetteEntity);
        }

        return cassetteEntityList;
    }

    @Override
    public boolean update(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return false;
        }

        boolean wasSuccess = dbAdapter.updateCassette(cassetteEntity.id, cassetteEntity.title,
                cassetteEntity.descripition, cassetteEntity.length, cassetteEntity.numberOfRecordings,
                cassetteEntity.isCompiled, cassetteEntity.compiledFilePath, cassetteEntity.dateTimeOfCompilation);

        return wasSuccess;
    }

    @Override
    public boolean delete(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return false;
        }
        return delete(cassetteEntity.id);
    }

    @Override
    public boolean delete(long id) {
        boolean wasSuccess = dbAdapter.deleteCassette(id);
        return wasSuccess;
    }
}
