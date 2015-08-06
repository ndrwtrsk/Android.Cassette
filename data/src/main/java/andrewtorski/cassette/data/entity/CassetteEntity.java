package andrewtorski.cassette.data.entity;

import android.database.Cursor;

import com.activeandroid.Model;

import java.util.Date;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;

/**
 * Database entity for the Cassette.
 */
public class CassetteEntity extends Model{

    //region Public Fields

    /**
     * Unique identifier of the  Cassette
     */
    public long id;

    /** Title of this Cassette.
     *  Not required. */
    public String title;

    /** Description of the Cassette.
     *  Not required. */
    public String descripition;

    /** UNIX time representation of date and time of creating this Cassette. */
    public long dateTimeOfCreation;

    /**
     * Total time length of the Cassette.
     * Express in milliseconds.
     */
    public int length;

    /** Number of the Recordings on this Cassette. */
    public int numberOfRecordings;

    /** Was this Cassette compiled to one File.
     *  0 - was not compiled.
     *  1 - was compiled. */
    public int isCompiled;

    /** Compiled audio file path.
     *  This string is null or empty if this.wasCompiled is 0. */
    public String compiledFilePath;

    /** UNIX time representation of date and time of compilation of this Cassette. */
    public long dateTimeOfCompilation;

    //endregion Public Fields

    //region Constructors

    /**
     * Default constructor.
     * */
    public CassetteEntity(){}

    /** Initializes a new instance of the CassetteEntity class using provided title, description
     *  and date and time of creation. */
    public CassetteEntity(String title, String description, Date dateTimeOfCreation){
        this.title = title;
        this.descripition = description;
        this.dateTimeOfCreation = dateTimeOfCreation.getTime();
    }

    public CassetteEntity(long id, String title, String descripition,
                          long dateTimeOfCreation, int length, int numberOfRecordings,
                          int isCompiled, String compiledFilePath, long dateTimeOfCompilation) {
        this.id = id;
        this.title = title;
        this.descripition = descripition;
        this.dateTimeOfCreation = dateTimeOfCreation;
        this.length = length;
        this.numberOfRecordings = numberOfRecordings;
        this.isCompiled = isCompiled;
        this.compiledFilePath = compiledFilePath;
        this.dateTimeOfCompilation = dateTimeOfCompilation;
    }
/*
    public CassetteEntity(Cursor cursor){
        if(cursor == null){
            return;
        }

        int idColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int titleColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int descriptionColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int dateTimeCreationColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int lengthColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int numberOfRecordingsColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int isCompiledColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int compiledFilePathColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int dateTimeCompilationColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);

        id = cursor.getLong(idColumnIndex);
        title = cursor.getString(titleColumnIndex);
        descripition = cursor.getString(descriptionColumnIndex);
        dateTimeOfCreation = cursor.getLong(dateTimeCreationColumnIndex);
        length = cursor.getInt(lengthColumnIndex);
        numberOfRecordings = cursor.getInt(numberOfRecordingsColumnIndex);
        isCompiled = cursor.getInt(isCompiledColumnIndex);
        compiledFilePath = cursor.getString(compiledFilePathColumnIndex);
        dateTimeOfCompilation = cursor.getLong(dateTimeCompilationColumnIndex);
    }*/

    //endregion Constructors


    /**
     * Constructs a CassetteEntity based on the information currently contained in the Cursor.
     *
     * @param cursor Cursor which holds information about this
     * @return
     */
    public static CassetteEntity createCassetteEntityFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        int idColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int titleColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int descriptionColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int dateTimeCreationColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int lengthColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int numberOfRecordingsColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int isCompiledColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int compiledFilePathColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);
        int dateTimeCompilationColumnIndex = cursor.getColumnIndex(CassetteDbContract.CassetteTable.COLUMN_NAME_ID);

        long id = cursor.getLong(idColumnIndex);
        String title = cursor.getString(titleColumnIndex);
        String descripiton = cursor.getString(descriptionColumnIndex);
        long dateTimeCreation = cursor.getLong(dateTimeCreationColumnIndex);
        int length = cursor.getInt(lengthColumnIndex);
        int numberOfRecordings = cursor.getInt(numberOfRecordingsColumnIndex);
        int isCompiled = cursor.getInt(isCompiledColumnIndex);
        String compiledFilePath = cursor.getString(compiledFilePathColumnIndex);
        long dateTimeCompilation = cursor.getLong(dateTimeCompilationColumnIndex);

        CassetteEntity cassette = new CassetteEntity(id, title, descripiton, dateTimeCreation, length,
                numberOfRecordings, isCompiled, compiledFilePath, dateTimeCompilation);

        return cassette;
    }
}
