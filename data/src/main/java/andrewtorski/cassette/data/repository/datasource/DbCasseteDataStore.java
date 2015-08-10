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

        long id = dbAdapter.create(title, description, dateTimeOfCreation);

        //TODO: what to do when -1 is returned from create?
        //  1)  return null
        //  2)  return cassetteEntity with id as -1
        //      and then perform some additional actions in either domain layer or presentation?

        cassetteEntity.id = id;

        return cassetteEntity;
    }

    @Override
    public CassetteEntity get(long cassetteId) {
        Cursor cursor = dbAdapter.getById(cassetteId);

        if (cursor == null) {
            return null;
        }

        CassetteEntity cassetteEntity = CassetteEntity.createFromCursor(cursor);

        cursor.close();

        return cassetteEntity;
    }

    /**
     * Returns a linked list of all existent CassetteEntities.
     *
     * @return Linked list of CassetteEntities.
     */
    @Override
    public List<CassetteEntity> getAll() {
        Cursor cursor = dbAdapter.getAll();
        return DbCasseteDataStore.getListOfCasseettesFromCursor(cursor);
    }

    @Override
    public List<CassetteEntity> getAllBetweenDates(long fromDate, long toDate) {
        Cursor cursor = dbAdapter.getAllCreatedBetweenDates(fromDate, toDate);
        return DbCasseteDataStore.getListOfCasseettesFromCursor(cursor);
    }

    @Override
    public boolean update(CassetteEntity cassetteEntity) {
        if (cassetteEntity == null) {
            return false;
        }

        boolean wasSuccess = dbAdapter.update(cassetteEntity.id, cassetteEntity.title,
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
        boolean wasSuccess = dbAdapter.delete(id);
        return wasSuccess;
    }

    private static List<CassetteEntity> getListOfCasseettesFromCursor(Cursor cursor) {
        //  LinkedList is used here, because indexing operation is completely out of our interests
        //  right now. ArrayList would prove to be slower here, as it has to be extended as it's
        //  capacity changes.
        List<CassetteEntity> cassetteEntityList = new LinkedList<>();
        if (cursor == null) {
            return cassetteEntityList;
        }

        CassetteEntity cassetteEntity;
        while (cursor.moveToNext()) {
            cassetteEntity = CassetteEntity.createFromCursor(cursor);

            if (cassetteEntity != null) {
                cassetteEntityList.add(cassetteEntity);
            }
        }
        cursor.close();
        return cassetteEntityList;
    }
}
