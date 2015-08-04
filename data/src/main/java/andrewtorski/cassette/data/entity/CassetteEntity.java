package andrewtorski.cassette.data.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Database entity for the Cassette.
 */
@Table(name = "Cassette")
public class CassetteEntity extends Model{

    //region Public Fields

    /*
    * No field containing primary key column is included in this class as ActiveAndroid's base class
    * Model already provides that.
    * */

    /** Title of this Recording.
     *  Not required. */
    @Column(name = "title")
    public String title;

    /** Description of the Recording.
     *  Not required. */
    @Column(name = "description")
    public String descripition;

    /** UNIX time representation of date and time of creating this Cassette. */
    @Column(name = "date_time_of_creation")
    public long dateTimeOfCreation;

    /** Number of the Recordings on this Cassette. */
    @Column(name = "number_of_recordings")
    public int numberOfRecordings;

    /** Was this Cassette compiled to one File.
     *  0 - was not compiled.
     *  1 - was compiled. */
    @Column(name = "is_compiled")
    public int isCompiled;

    /** Compiled audio file path.
     *  This string is null or empty if this.wasCompiled is 0. */
    @Column(name = "compiled_file_path")
    public String compiledFilePath;

    /** UNIX time representation of date and time of compilation of this Cassette. */
    @Column(name = "date_time_compilation")
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

    //endregion Constructors

    //region Methods

    /**
     * Returns a collection of RecordingEntities associated with this CassetteEntity.
     * @return Collection of RecordingEntities.
     *
     * Note: See @{RecordingEntity} for the second end of this relationship.
     */
    public List<RecordingEntity> recordings(){
        return getMany(RecordingEntity.class, "cassette");
    }

    //endregion Methods

    //region Static Methods

    /**
     * Returns a collection of all CassetteEntities present.
     * @return Collection of CassetteEntities.
     */
    public static List<CassetteEntity> getAll(){
        return new Select()
                .from(CassetteEntity.class)
                .execute();
    }

    /**
     * Returns a CassetteEntity using the provided identifier.
     * @param id Identifier of the returned CassetteEntity.
     * @return Reference to CassetteEntity object or null.
     */
    public static CassetteEntity getById(final int id){
        return new Select()
                .from(CassetteEntity.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    //endregion Static Methods

}
