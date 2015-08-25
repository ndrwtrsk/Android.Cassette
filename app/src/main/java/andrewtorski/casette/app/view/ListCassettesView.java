package andrewtorski.casette.app.view;

import java.util.Collection;

import andrewtorski.casette.app.model.CassetteModel;

public interface ListCassettesView extends LoadDataView {
    void renderCassetteList(Collection<CassetteModel> cassetteModelCollection);

    void viewCassette(CassetteModel cassetteModel);
}
