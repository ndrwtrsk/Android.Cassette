package andrewtorski.cassette.data.entity;

import android.database.Cursor;

import com.activeandroid.Model;

import java.util.Date;

import andrewtorski.cassette.data.db.schema.CassetteDbContract;

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

    public RecordingEntity(long id, long cassetteId, String title, String description,
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


    //region Static Methods


    /**
     * Constructs a RecordingEntity based on the information currently contained in the Cursor.
     *
     * @param cursor Cursor which holds information about this
     * @return RecordingEntity created from data in Cursor.
     */
    public static RecordingEntity createRecordingEntityFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        int idColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_ID);
        int titleColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_TITLE);
        int descriptionColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_DESCRIPTION);
        int cassetteIdColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_CASSETTE_ID);
        int dateTimeOfRecordingColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_DATE_TIME_OF_RECORDING);
        int lengthColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_LENGTH);
        int audioFilePathColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_AUDIO_FILE_PATH);
        int sequenceInTheCassetteColumnIndex = cursor.getColumnIndex(CassetteDbContract.RecordingTable.COLUMN_NAME_SEQUENCE_IN_CASSETTE);

        long id = cursor.getLong(idColumnIndex),
                cassetteId = cursor.getLong(cassetteIdColumnIndex),
                dateTimeOfRecording = cursor.getLong(dateTimeOfRecordingColumnIndex);

        int length = cursor.getInt(lengthColumnIndex),
                sequenceInTheCassette = cursor.getInt(sequenceInTheCassetteColumnIndex);

        String title = cursor.getString(titleColumnIndex),
                description = cursor.getString(descriptionColumnIndex),
                audioFilePath = cursor.getString(audioFilePathColumnIndex);

        RecordingEntity result = new RecordingEntity(id, cassetteId, title, description, dateTimeOfRecording,
                length, audioFilePath, sequenceInTheCassette);

        return result;
    }

    //endregion Static Methods
}
