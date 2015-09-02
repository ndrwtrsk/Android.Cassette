package andrewtorski.casette.app.model;

import java.util.Date;

public class RecordingModel {
    //region Private Fields

    /**
     * Unique identifier of the Recording.
     */
    private long id;

    /**
     * Title of this Recording.
     * Not required.
     */
    private String title;

    /**
     * Description of the Recording.
     * Not required.
     */
    private String description;

    /**
     * Date and Time of the Recording.
     */
    private Date dateTimeOfRecording;

    /**
     * Length of the recording.
     * Expressed in milliseconds.
     */
    private int lengthInMilliseconds;

    /**
     * Path to the audio file with the capture.
     */
    private String audioFilePath;

    /**
     * Sequence of this Recording in the Cassette.
     * Cassette is compromised of many recordings. First recording will
     * have number 0, second 1... etc
     */
    private int sequenceInTheCassette;

    //region Navigation Fields

    /**
     * Identifier of the Cassette to which this Recording belongs.
     */
    private long cassetteId;


    //endregion Navigation Fields

    //endregion Private Fields

    //region Constructors

    public RecordingModel() {
        id = -1;
    }

    /**
     * Constructor which only should be used when Mapping from DAL RecordingEntity
     * to Domain Recording.
     */
    public RecordingModel(long id, long cassetteId, String title,
                          String description, Date dateTimeOfRecording,
                          int lengthInMilliseconds, String audioFilePath,
                          int sequenceInTheCassette) {
        this.id = id;
        this.cassetteId = cassetteId;
        this.title = title;
        this.description = description;
        this.dateTimeOfRecording = dateTimeOfRecording;
        this.lengthInMilliseconds = lengthInMilliseconds;
        this.audioFilePath = audioFilePath;
        this.sequenceInTheCassette = sequenceInTheCassette;
        this.cassetteId = cassetteId;
    }

    //endregion Constructors

    //region Getters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Date getDateTimeOfRecording() {
        return dateTimeOfRecording;
    }

    public int getLengthInMilliseconds() {
        return lengthInMilliseconds;
    }

    public String getAudioFilePath() {
        return audioFilePath;
    }

    public int getSequenceInTheCassette() {
        return sequenceInTheCassette;
    }

    public long getCassetteId() {
        return cassetteId;
    }

    //endregion Getters
}