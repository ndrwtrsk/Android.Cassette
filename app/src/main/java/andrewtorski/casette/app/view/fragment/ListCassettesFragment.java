package andrewtorski.casette.app.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import andrewtorski.casette.R;
import andrewtorski.casette.app.di.components.CassetteComponent;
import andrewtorski.casette.app.model.CassetteModel;
import andrewtorski.casette.app.presenter.ListCassettesPresenter;
import andrewtorski.casette.app.view.ListCassettesView;
import andrewtorski.casette.app.view.adapter.CassetteLayoutManager;
import andrewtorski.casette.app.view.adapter.CassettesAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ListCassettesFragment extends BaseFragment implements ListCassettesView {

    public interface CassetteListListener {
        void onCassetteClicked(final CassetteModel cassetteModel);
    }

    //region Private fields

    private final String TAG = "LI_CAS_FRAG";

    @Inject
    ListCassettesPresenter listCassettesPresenter;

    @Bind(R.id.rv_cassettes)
    RecyclerView rv_cassettes;

    /*RelativeLayout rl_progress;
    RelativeLayout rl_retry;*/

    private CassettesAdapter cassettesAdapter;
    private CassetteLayoutManager cassetteLayoutManager;

    private CassetteListListener cassetteListListener;

    private CassettesAdapter.OnItemClickListener itemClickListener = new CassettesAdapter.OnItemClickListener() {
        @Override
        public void onCassetteItemClicked(CassetteModel cassetteModel) {
            if (ListCassettesFragment.this.listCassettesPresenter != null && cassetteModel != null) {
                ListCassettesFragment.this.listCassettesPresenter.onCassetteClicked(cassetteModel);
            }
        }
    };

    //endregion Private fields

    //region Constructor
    @Inject
    public ListCassettesFragment() {
        super();
        Log.d(TAG, "Created");
    }

    //endregion Constructor

    //region Methods

    private void initialize() {
        Log.d(TAG, "Initializing...");
        if (this.getComponent(CassetteComponent.class) == null) {
            Log.e(TAG, "Gotten component is null");
        }
        this.getComponent(CassetteComponent.class).inject(this);
        this.listCassettesPresenter.setView(this);
        Log.d(TAG, "Initialized.");
    }

    private void setupUI() {
        Log.d(TAG, "Setting up UI...");
        this.cassetteLayoutManager = new CassetteLayoutManager(this.getActivity());
        this.rv_cassettes.setLayoutManager(cassetteLayoutManager);

        this.cassettesAdapter = new CassettesAdapter(new ArrayList<CassetteModel>());
        this.cassettesAdapter.setOnItemClickListener(this.itemClickListener);
        this.rv_cassettes.setAdapter(cassettesAdapter);
        Log.d(TAG, "UI is set up.");
    }

    private void loadCassetteList() {
        this.listCassettesPresenter.initialize();
    }

    //endregion Methods

    //region Fragment overridden Methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "Creating view...");
        View fragmentView = inflater.inflate(R.layout.fragment_list_cassettes, container, true);
        ButterKnife.bind(this, fragmentView);
        setupUI();
        Log.d(TAG, "View was created.");
        return fragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "Attaching to activity...");
        super.onAttach(activity);
        if (activity instanceof CassetteListListener) {
            this.cassetteListListener = (CassetteListListener) activity;
        }
        Log.d(TAG, "Attached to activity.");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadCassetteList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.listCassettesPresenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.listCassettesPresenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.listCassettesPresenter.resume();
    }


    //endregion Fragment overridden Methods

    //region ListCassettesView implemented methods

    @Override
    public void renderCassetteList(Collection<CassetteModel> cassetteModelCollection) {
        Log.d(TAG, "Rendering cassette list...");
        if (cassetteModelCollection != null) {
            this.cassettesAdapter.setCassetteModelList(cassetteModelCollection);
        }
        Log.d(TAG, "Rendered cassette list...");
    }

    @Override
    public void viewCassette(CassetteModel cassetteModel) {
        Log.d(TAG, "Cassette was clicked.");
        if (this.cassetteListListener != null) {
            this.cassetteListListener.onCassetteClicked(cassetteModel);
        }
    }

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @Override
    public void showLoading() {
        //empty
    }

    /**
     * Hide a loading view.
     */
    @Override
    public void hideLoading() {
        //empty
    }

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    @Override
    public void showRetry() {
        //empty
    }

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @Override
    public void hideRetry() {
        //empty
    }

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    /**
     * Get a {@link android.content.Context}.
     */
    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    //endregion ListCassettesView implemented methods
}
