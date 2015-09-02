package andrewtorski.casette.app.presenter;

import android.util.Log;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import andrewtorski.casette.app.exception.ErrorMessageFactory;
import andrewtorski.casette.app.model.CassetteModel;
import andrewtorski.casette.app.model.mapper.CassetteModelDataMapper;
import andrewtorski.casette.app.view.ListCassettesView;
import andrewtorski.cassette.domain.entity.Cassette;
import andrewtorski.cassette.domain.exception.ErrorBundle;
import andrewtorski.cassette.domain.usecase.ListCassettesUseCase;

public class ListCassettesPresenter implements Presenter {

    //region Private fields

    public final String TAG = "LI_CAS_PRES";

    private List<CassetteModel> cassetteModelList;

    private ListCassettesView view;

    private ListCassettesUseCase useCase;

    private CassetteModelDataMapper mapper = new CassetteModelDataMapper();

    //endregion Private fields

    //region Constructor
    @Inject
    public ListCassettesPresenter(@Named("listCassettes") ListCassettesUseCase useCase) {
        Log.d(TAG, "Constructed and injected usecase.");
        this.useCase = useCase;
    }

    //endregion Constructor

    //region Methods

    public void setView(ListCassettesView view) {
        this.view = view;
    }

    public void initialize() {
        this.loadCassetteList();
    }

    private void loadCassetteList() {
        Log.d(TAG, "Loading cassette list.");
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onCassetteClicked(CassetteModel cassetteModel) {
        Log.d(TAG, "Cassette was clicked.");
        this.view.viewCassette(cassetteModel);
    }

    private void showViewLoading() {
        this.view.showLoading();
    }

    private void hideViewLoading() {
        this.view.hideLoading();
    }

    private void showViewRetry() {
        this.view.showRetry();
    }

    private void hideViewRetry() {
        this.view.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.view.getContext(), errorBundle.getException());
        this.view.showError(errorMessage);
    }

    private void showCassettesCollectionInView(Collection<Cassette> cassetteCollection) {
        final Collection<CassetteModel> cassetteModels = this.mapper.transformCassettes(cassetteCollection);
        Log.d(TAG, "Was called showCassettesCollectionInView()");
        //render cassettes in the view
        this.view.renderCassetteList(cassetteModels);
    }

    private void getUserList() {
        List<Cassette> cassetteList = this.useCase.cassettes();
        Log.d(TAG, "Retrieved cassettes.");
        this.showCassettesCollectionInView(cassetteList);
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
        this.cassetteModelList = null;
    }

    //endregion Presenter methods
}
