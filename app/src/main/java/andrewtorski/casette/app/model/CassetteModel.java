package andrewtorski.casette.app.model;

import java.util.Date;
import java.util.List;

public class CassetteModel {

    //region Private Fields

    /**
     * Unique Identifier of the Cassete.
     */
    private long id;

    /**
     * Title of this Cassette.
     */
    private String title;

    /**
     * Description of this Cassette.
     * Not required.
     */
    private String description;

    /**
     * Date and Time of the creation of this Cassette.
     */
    private Date dateTimeOfCreation;

    /**
     * Total length of all Recordings.
     * Expressed in milliseconds.
     */
    private int length;

    /**
     * Was this Cassette compiled to one File.
     */
    private boolean isCompiled;

    /**
     * Compiled audio file path.
     * This string is null or empty if this.wasCompiled is null.
     */
    private String compiledFilePath;

    /**
     * Date and Time of when this Cassette was compiled to one file.
     * This reference is null, if this.wasCompiled is null.
     */
    private Date dateTimeOfCompilation;

    /**
     * Number of the Recordings on this Cassette.
     */
    private int numberOfRecordings;

    /**
     * Collection of associated Recordings.
     */
    private List<RecordingModel> recordingModelList;

    //endregion PrivateFields

    //region Constructors

    public CassetteModel(long id, String title,
                         String description, Date dateTimeOfCreation,
                         int length, boolean isCompiled,
                         String compiledFilePath, Date dateTimeOfCompilation, int numberOfRecordings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTimeOfCreation = dateTimeOfCreation;
        this.length = length;
        this.isCompiled = isCompiled;
        this.compiledFilePath = compiledFilePath;
        this.dateTimeOfCompilation = dateTimeOfCompilation;
        this.numberOfRecordings = numberOfRecordings;
    }

    //endregion Constructors

    //region Getters & Setters


    public long getId() {
        return id;
    }

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

    public Date getDateTimeOfCreation() {
        return dateTimeOfCreation;
    }

    public void setDateTimeOfCreation(Date dateTimeOfCreation) {
        this.dateTimeOfCreation = dateTimeOfCreation;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isCompiled() {
        return isCompiled;
    }

    public void setCompiled(boolean isCompiled) {
        this.isCompiled = isCompiled;
    }

    public String getCompiledFilePath() {
        return compiledFilePath;
    }

    public void setCompiledFilePath(String compiledFilePath) {
        this.compiledFilePath = compiledFilePath;
    }

    public Date getDateTimeOfCompilation() {
        return dateTimeOfCompilation;
    }

    public void setDateTimeOfCompilation(Date dateTimeOfCompilation) {
        this.dateTimeOfCompilation = dateTimeOfCompilation;
    }

    public int getNumberOfRecordings() {
        return numberOfRecordings;
    }

    public void setNumberOfRecordings(int numberOfRecordings) {
        this.numberOfRecordings = numberOfRecordings;
    }

    public List<RecordingModel> getRecordingModelList() {
        return recordingModelList;
    }

    public void setRecordingModelList(List<RecordingModel> recordingModelList) {
        this.recordingModelList = recordingModelList;
    }


    //endregion Getters & Setters

}
