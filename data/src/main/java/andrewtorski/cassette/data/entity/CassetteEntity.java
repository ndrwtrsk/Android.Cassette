package andrewtorski.cassette.data.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.Date;

/**
 * Database entity for the Cassette.
 */
public class CassetteEntity extends Model{

    //region Public Fields

    /**
     * Unique identifier of the  Cassette
     */
    private int id;

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
    @Column(name = "length")
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

    //endregion Constructors
}
