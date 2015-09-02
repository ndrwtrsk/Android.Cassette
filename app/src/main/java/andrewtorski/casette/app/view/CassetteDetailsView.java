package andrewtorski.casette.app.view;

import andrewtorski.casette.app.model.CassetteModel;

public interface CassetteDetailsView extends LoadDataView {
    void renderCassetteAndRecordings(CassetteModel cassetteModel);

    /*
        Future Stubs:
        1. Update Cassette,
        2. Delete Cassette,
        3. Update Recording,
        4. Delete Recording,
        5. Record?
     */
}
