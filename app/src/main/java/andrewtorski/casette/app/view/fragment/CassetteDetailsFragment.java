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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import andrewtorski.casette.R;
import andrewtorski.casette.app.di.components.CassetteComponent;
import andrewtorski.casette.app.model.CassetteModel;
import andrewtorski.casette.app.model.RecordingModel;
import andrewtorski.casette.app.model.processor.UserReadableProcessor;
import andrewtorski.casette.app.presenter.CassetteDetailsPresenter;
import andrewtorski.casette.app.view.CassetteDetailsView;
import andrewtorski.casette.app.view.adapter.RecordingLayoutManager;
import andrewtorski.casette.app.view.adapter.RecordingsAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CassetteDetailsFragment extends BaseFragment implements CassetteDetailsView {

    public interface RecordingListListener {
        void onRecordingClicked(final RecordingModel recordingModel);
    }

    //region Private fields

    private static int instanceCount = 0;

    private final static String TAG = "CAS_DET_FRAG";
    private final static String ARGUMENT_KEY_CASSETTE_ID = "andrewtorski.cassette.ARGUMENT_CASSETTE_ID";
    @Inject
    CassetteDetailsPresenter cassetteDetailsPresenter;

    private RecordingsAdapter recordingsAdapter;
    private RecordingLayoutManager recordingLayoutManager;
    private CassetteDetailsView view;
    private RecordingListListener recordingListListener;
    private RecordingsAdapter.OnItemClickListener onItemClickListener = new RecordingsAdapter.OnItemClickListener() {
        @Override
        public void onRecordingItemClicked(RecordingModel recordingModel) {
            if (CassetteDetailsFragment.this.cassetteDetailsPresenter != null && recordingModel != null) {
                CassetteDetailsFragment.this.cassetteDetailsPresenter.onRecordingClicked(recordingModel);
            }
        }
    };

    @Bind(R.id.cassette_details_title)
    TextView tv_title;

    @Bind(R.id.cassette_details_description)
    TextView tv_description;

    @Bind(R.id.cassette_details_date_time)
    TextView tv_dateTime;

    @Bind(R.id.cassette_details_length)
    TextView tv_length;

    @Bind(R.id.cassette_details_recordings)
    RecyclerView rv_recordings;

    //endregion Private fields

    //region Constructors

    public CassetteDetailsFragment() {
        super();
        Log.d(TAG, "New instance was created.");
        instanceCount++;
    }

    //endregion Constructors

    //region Methods

    //endregion Methods

    //region Static Methods

    /**
     * Returns a new instance of the CassetteDetailsFragment with the provided id of the cassette bundled in.
     */
    public static CassetteDetailsFragment getNewInstance(long cassetteId) {
        Log.d(TAG, "Getting new instance");
        CassetteDetailsFragment fragment = new CassetteDetailsFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putLong(ARGUMENT_KEY_CASSETTE_ID, cassetteId);

        fragment.setArguments(argumentsBundle);
        Log.d(TAG, "Returned new instance.");
        return fragment;
    }

    //endregion Static Methods

    //region Private helper Methods

    /**
     * Binds provided CassetteModel properties to this fragment's views.
     */
    private void bind(CassetteModel cassette) {
        tv_title.setText(cassette.getTitle());
        tv_description.setText(cassette.getDescription());

        Date date = cassette.getDateTimeOfCreation();
        long length = cassette.getLength();

        String userReadableDate = UserReadableProcessor.getUserReadableDate(date);
        String userReadableLength = UserReadableProcessor.getUserReadableLengthOfTimeFromMilliseconds(length);

        tv_dateTime.setText(userReadableDate);
        tv_length.setText(userReadableLength);
    }

    private void setupUI() {
        Log.d(TAG, "Setting up UI");
        this.recordingLayoutManager = new RecordingLayoutManager(this.getActivity());
        this.rv_recordings.setLayoutManager(recordingLayoutManager);
        this.recordingsAdapter = new RecordingsAdapter(new ArrayList<RecordingModel>());
        this.recordingsAdapter.setOnItemClickListener(this.onItemClickListener);
        this.rv_recordings.setAdapter(recordingsAdapter);
        Log.d(TAG, "Set up UI.");
    }

    private void initialize() {
        Log.d(TAG, "Initializing...");
        this.getComponent(CassetteComponent.class).inject(this);
        this.cassetteDetailsPresenter.setView(this);
        Bundle arguments = this.getArguments();
        long cassetteId = arguments.getLong(ARGUMENT_KEY_CASSETTE_ID);
        this.cassetteDetailsPresenter.initialize(cassetteId);
        Log.d(TAG, "Initialized.");
    }

    //endregion Private helper Methods

    //region Fragment Methods


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Creating view...");
        //super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_details_cassette, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        Log.d(TAG, "Created view.");
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "Attaching to activity...");
        super.onAttach(activity);
        Log.d(TAG, "Attached to activity.");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    @Override
    public void onStart() {
        super.onStart();
        //this.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.cassetteDetailsPresenter.resume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.cassetteDetailsPresenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.cassetteDetailsPresenter.pause();
    }

    //endregion Fragment Methods

    //region CassetteDetailsView Methods

    @Override
    public void renderCassetteAndRecordings(CassetteModel cassetteModel) {
        Log.d(TAG, "Rendering cassette and recordings...");
        this.bind(cassetteModel);
        recordingsAdapter.setRecordingModelList(cassetteModel.getRecordingModelList());
        Log.d(TAG, "Rendered cassette and recordings.");

    }

    //endregion CassetteDetailsView Methods

    //region LoadUserDetails Methods

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    @Override
    public void showLoading() {

    }

    /**
     * Hide a loading view.
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    @Override
    public void showRetry() {

    }

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    @Override
    public void hideRetry() {

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

    //endregion LoadUserDetails Methods
}