package andrewtorski.casette.app.model.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import andrewtorski.casette.app.model.CassetteModel;
import andrewtorski.casette.app.model.RecordingModel;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.entity.Recording;

public class CassetteModelDataMapper {

    private RecordingModelDataMapper recordingModelDataMapper = new RecordingModelDataMapper();

    public Cassette transform(CassetteModel cassetteModel) {
        if (cassetteModel == null) {
            return null;
        }

        Cassette cassette = new Cassette(cassetteModel.getId(), cassetteModel.getTitle(),
                cassetteModel.getDescription(), cassetteModel.getDateTimeOfCreation(), cassetteModel.getLength(),
                cassetteModel.isCompiled(), cassetteModel.getCompiledFilePath(), cassetteModel.getDateTimeOfCompilation(),
                cassetteModel.getNumberOfRecordings());

        return cassette;
    }

    public List<Cassette> transformModels(Collection<CassetteModel> cassetteModelCollection) {
        List<Cassette> cassetteList = new LinkedList<>();

        if (cassetteModelCollection == null) {
            return cassetteList;
        }

        Cassette cassette;
        for (CassetteModel cassetteModel : cassetteModelCollection) {
            cassette = this.transform(cassetteModel);
            if (cassette != null) {
                cassetteList.add(cassette);
            }
        }

        return cassetteList;
    }

    public CassetteModel transform(Cassette cassette) {
        if (cassette == null) {
            return null;
        }

        CassetteModel cassetteModel = new CassetteModel(cassette.getId(), cassette.getTitle(), cassette.getDescription(),
                cassette.getDateTimeOfCreation(), cassette.getLength(), cassette.isCompiled(), cassette.getCompiledFilePath(),
                cassette.getDateTimeOfCompilation(), cassette.getNumberOfRecordings());

        if (cassette.getRecordings() != null) {
            List<Recording> recordingList = cassette.getRecordings();
            List<RecordingModel> recordingModelList = recordingModelDataMapper.transform(recordingList);
            cassetteModel.setRecordingModelList(recordingModelList);
        }

        return cassetteModel;
    }

    public List<CassetteModel> transformCassettes(Collection<Cassette> cassetteCollection) {
        List<CassetteModel> cassetteModelList = new LinkedList<>();

        if (cassetteCollection == null) {
            return cassetteModelList;
        }

        CassetteModel cassetteModel;
        for (Cassette cassette : cassetteCollection) {
            cassetteModel = this.transform(cassette);
            if (cassetteModel != null) {
                cassetteModelList.add(cassetteModel);
            }
        }
        return cassetteModelList;
    }


}
