package andrewtorski.casette.app.model.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.casette.app.model.RecordingModel;
import andrewtorski.cassette.domain.entity.Recording;

public class RecordingModelDataMapper {

    public Recording transform(RecordingModel recordingModel) {
        if (recordingModel == null) {
            return null;
        }

        return new Recording(recordingModel.getId(), recordingModel.getCassetteId(),
                recordingModel.getTitle(), recordingModel.getDescription(), recordingModel.getDateTimeOfRecording(),
                recordingModel.getLengthInMilliseconds(), recordingModel.getAudioFilePath(), recordingModel.getSequenceInTheCassette());
    }

    public RecordingModel transform(Recording recording) {
        if (recording == null) {
            return null;
        }

        return new RecordingModel(recording.getId(), recording.getCassetteId(), recording.getTitle(),
                recording.getDescripition(), recording.getDateTimeOfRecording(), recording.getLengthInMiliseconds(),
                recording.getAudioFilePath(), recording.getSequenceInTheCassette());
    }

    public List<RecordingModel> transform(Collection<Recording> recordingCollection) {
        List<RecordingModel> recordingModelList = new LinkedList<>();
        if (recordingCollection == null) {
            return recordingModelList;
        }

        RecordingModel recordingModel;
        for (Recording recording : recordingCollection) {
            recordingModel = this.transform(recording);
            if (recording != null) {
                recordingModelList.add(recordingModel);
            }
        }

        return recordingModelList;
    }

}
