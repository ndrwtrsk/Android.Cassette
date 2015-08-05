package andrewtorski.cassette.data.entity;

import com.activeandroid.Model;

/**
 * Database entity for Recording.
 */
public class RecordingEntity extends Model {

    //region Public Fields

    /**
     * Unique identifier of the Recording.
     */
    public int id;

    /**
     * Id of the cassette to which this
     */
    public int cassetteId;

    /**
     * Title of this Recording.
     * Not required.
     */
    public String title;

    /**
     * Description of the Recording.
     * Not required.
     */
    public String descripition;

    /**
     * String representation of date and time of the Recording.
     */
    public String dateTimeOfRecording;

    /**
     * Length of the recording.
     * Expressed in milliseconds.
     */
    public int length;

    /**
     * Path to the audio file with the capture.
     */
    public String audioFilePath;

    /**
     * Sequence of this Recording in the Cassette.
     * Cassette is compromised of many recordings. First recording will
     * have number 0, second 1... etc
     */
    public int sequenceInTheCassette;

    //endregion Public Fields
}
