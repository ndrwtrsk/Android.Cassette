package andrewtorski.cassette.data.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Database entity for Recording.
 */
@Table(name = "Recording")
public class RecordingEntity extends Model{

    //region Public Fields

    /*
    * No field containing primary key column is included in this class as ActiveAndroid's base class
    * Model already provides that.
    * */

    @Column(name = "cassette")
    /** CassetteEntity to which this Recording belongs.
     *  Look into @{ CassetteEntity }for the second end of this relationship. */
    public CassetteEntity cassette;

    /** Title of this Recording.
     *  Not required. */
    @Column(name = "title")
    public String title;

    /** Description of the Recording.
     *  Not required. */
    @Column(name = "description")
    public String descripition;

    /** String representation of date and time of the Recording. */
    @Column(name = "date_time_recording")
    public String dateTimeOfRecording;

    /** Length of the recording.
     *  Expressed in milliseconds. */
    @Column(name = "length")
    public int length;

    /** Path to the audio file with the capture. */
    @Column(name = "audio_file_path")
    public String audioFilePath;

    /** Sequence of this Recording in the Cassette.
     *  Cassette is compromised of many recordings. First recording will
     *  have number 0, second 1... etc */
    @Column(name = "sequence_in_cassette")
    public int sequenceInTheCassette;

    //endregion Public Fields

    /*
    * Note: Active Android uses default constructor to instantiate objects of this class, and as
    * such it is advised not to implement any constructors unless it is needed.
    * */
}
