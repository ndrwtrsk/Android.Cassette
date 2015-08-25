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
    private ListCassettesView listCassettesView;

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
        //this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_cassettes.setAdapter(cassettesAdapter);
        Log.d(TAG, "UI is set up.");
    }

    private void loadCassetteList() {
        this.listCassettesPresenter.initialize();
    }

    //endregion Methods

    //region Fragment overridden Methods

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(android.os.Bundle)} and {@link #onActivityCreated(android.os.Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
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

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(android.os.Bundle)} will be called after this.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "Attaching to activity...");
        super.onAttach(activity);
        assert activity != null;
        Log.d(TAG, "Attached to activity.");
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(android.os.Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
        this.loadCassetteList();
    }

    /**
     * Called when the view previously created by {@link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {@link #onStop()} and before {@link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {@link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.listCassettesPresenter.destroy();
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link android.app.Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        this.listCassettesPresenter.pause();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link android.app.Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
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
        //empty
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
