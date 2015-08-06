package andrewtorski.cassette.data.entity;

import com.activeandroid.Model;

import java.util.Date;

/**
 * Database entity for Recording.
 */
public class RecordingEntity extends Model {

    //region Public Fields

    /**
     * Unique identifier of the Recording.
     */
    public long id;

    /**
     * Id of the cassette to which this
     */
    public long cassetteId;

    /**
     * Title of this Recording.
     * Not required.
     */
    public String title;

    /**
     * Description of the Recording.
     * Not required.
     */
    public String description;

    /**
     * UNIX time representation of date and time of the Recording.
     */
    public long dateTimeOfRecording;

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

    //region Constructors

    public RecordingEntity(long id, int cassetteId, String title, String description,
                           long dateTimeOfRecording, int length, String audioFilePath,
                           int sequenceInTheCassette) {
        this.id = id;
        this.cassetteId = cassetteId;
        this.title = title;
        this.description = description;
        this.dateTimeOfRecording = dateTimeOfRecording;
        this.length = length;
        this.audioFilePath = audioFilePath;
        this.sequenceInTheCassette = sequenceInTheCassette;
    }

    public RecordingEntity(long cassetteId, int sequenceInTheCassette, Date dateTimeOfRecording,
                           String audioFilePath, int length) {
        this.cassetteId = cassetteId;
        this.sequenceInTheCassette = sequenceInTheCassette;
        this.audioFilePath = audioFilePath;
        this.length = length;
        this.dateTimeOfRecording = dateTimeOfRecording.getTime();
    }

    //endregion Constructors
}
