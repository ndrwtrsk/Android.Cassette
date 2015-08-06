package andrewtorski.cassette.data.entity.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.cassette.data.entity.RecordingEntity;
import andrewtorski.cassette.domain.entity.Recording;

/**
 * Exposes methods to convert between Recording and RecordingEntity classes.
 */
public class RecordingEntityDataMapper {

    /**
     * Transforms a RecordingEntity into a Recording.
     *
     * @param recordingEntity
     * @return
     */
    public Recording transfrom(RecordingEntity recordingEntity) {
        if (recordingEntity == null) {
            return null;
        }

        Date dateTimeOfRecording = new Date(recordingEntity.dateTimeOfRecording);
        Recording recording = new Recording(recordingEntity.id, recordingEntity.cassetteId,
                recordingEntity.title, recordingEntity.description, dateTimeOfRecording,
                recordingEntity.length, recordingEntity.audioFilePath, recordingEntity.sequenceInTheCassette);

        return recording;
    }

    /**
     * Transforms a colleciton of RecordingEntities into a List of Recordings.
     *
     * @param recordingEntityCollection RecordingEntities to transform.
     * @return List of Recordings.
     */
    public List<Recording> transform(Collection<RecordingEntity> recordingEntityCollection) {
        List<Recording> recordingList = new LinkedList<>();

        if (recordingEntityCollection == null) {
            return recordingList;
        }

        Recording recording;
        for (RecordingEntity recordingEntity : recordingEntityCollection) {
            recording = this.transfrom(recordingEntity);
            if (recording != null) {
                recordingList.add(recording);
            }
        }

        return recordingList;
    }

    /**
     * Transforms Recording into more suitable RecordingEntity.
     *
     * @param recording Recording to transform.
     * @return Transformed RecordingEntity.
     */
    public RecordingEntity transform(Recording recording) {
        if (recording == null) {
            return null;
        }

        RecordingEntity recordingEntity = new RecordingEntity(recording.getCassetteId(),
                recording.getSequenceInTheCassette(), recording.getDateTimeOfRecording(), recording.getAudioFilePath(),
                recording.getLengthInMiliseconds());

        return recordingEntity;
    }

}
