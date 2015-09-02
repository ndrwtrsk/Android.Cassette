package andrewtorski.casette.app.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import andrewtorski.casette.app.model.CassetteModel;
import andrewtorski.casette.app.model.RecordingModel;
import andrewtorski.casette.app.model.mapper.CassetteModelDataMapper;
import andrewtorski.casette.app.view.CassetteDetailsView;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.usecase.CassetteDetailsUseCase;

/**
 * Presenter for displaying the details of a Cassette.
 */
public class CassetteDetailsPresenter implements Presenter {

    //region Private fields

    private static final String TAG = "CAS_DET_PRES";

    /**
     * Use case for this Presenter.
     */
    private CassetteDetailsUseCase useCase;

    /**
     * Id of the displayed Cassette.
     */
    private long cassetteId;

    /**
     * Displayed Cassette.
     */
    private CassetteModel cassetteModel;

    /**
     * View connected to this Presenter.
     */
    private CassetteDetailsView cassetteDetailsView;

    /**
     * Mapper.
     */
    private CassetteModelDataMapper mapper = new CassetteModelDataMapper();

    //endregion Private fields

    //region Constructor

    @Inject
    public CassetteDetailsPresenter(@Named("cassetteDetails") CassetteDetailsUseCase useCase) {
        this.useCase = useCase;
    }

    //endregion Constructor

    //region Methods

    public void setView(CassetteDetailsView view) {
        this.cassetteDetailsView = view;
    }

    public void initialize(long cassetteId) {
        this.cassetteId = cassetteId;
        getCassetteDetails();
    }

    public void getCassetteDetails() {
        Cassette cassette = this.useCase.cassetteWithRecordings(cassetteId);
        cassetteModel = mapper.transform(cassette);
        cassetteDetailsView.renderCassetteAndRecordings(cassetteModel);
    }

    public void onRecordingClicked(RecordingModel recordingModel) {
        //  stub for clicked.
    }

    //endregion Methods

    //region Presenter methods

    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    @Override
    public void resume() {

    }

    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    @Override
    public void pause() {

    }

    /**
     * Method that controls the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    @Override
    public void destroy() {
        cassetteModel = null;
    }

    //endregion Presenter methods
}