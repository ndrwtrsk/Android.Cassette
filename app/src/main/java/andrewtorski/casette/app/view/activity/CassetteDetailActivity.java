package andrewtorski.casette.app.view.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import andrewtorski.casette.R;
import andrewtorski.casette.app.di.HasComponent;
import andrewtorski.casette.app.di.components.CassetteComponent;
import andrewtorski.casette.app.di.components.DaggerCassetteComponent;
import andrewtorski.casette.app.di.modules.CassetteModule;
import andrewtorski.casette.app.view.fragment.CassetteDetailsFragment;

public class CassetteDetailActivity extends BaseActivity implements HasComponent<CassetteComponent> {

    //region Private fields

    private static final String TAG = "CAS_DET_ACT";

    private static final String INTENT_EXTRA_PARAM_CASSETTE_ID = "andrewtorski.cassette.INTENT_PARAM_CASSETTE_ID";
    private static final String INSTANCE_STATE_PARAM_CASSETTE_ID = "andrewtorski.cassette.STATE_PARAM_CASSETTE_ID";

    private long cassetteId;

    private CassetteComponent cassetteComponent;

    //endregion Private fields

    public static Intent getCallingIntent(Context context, long cassetteId) {
        Log.d(TAG, "Got calling intent.");
        Intent intent = new Intent(context, CassetteDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, cassetteId);

        return intent;
    }

    //region Private methods

    private void initializeInjector() {
        this.cassetteComponent = DaggerCassetteComponent.builder()
                .applicationComponent(this.getApplicationComponent())
                .activityModule(this.getActivityModule())
                .cassetteModule(new CassetteModule(this.cassetteId))
                .build();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.cassetteId = this.getIntent().getLongExtra(INTENT_EXTRA_PARAM_CASSETTE_ID, -1L);
            Fragment detailsFragment = CassetteDetailsFragment.getNewInstance(this.cassetteId);
            this.addFragment(R.id.activity_cassette_details_fl_fragment, detailsFragment);
        } else {
            this.cassetteId = savedInstanceState.getLong(INSTANCE_STATE_PARAM_CASSETTE_ID);
        }
    }

    //endregion Private methods

    //region Activity Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_cassette_detail);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cassette_detail, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putLong(INSTANCE_STATE_PARAM_CASSETTE_ID, this.cassetteId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion Activity Methods

    //region HasComponent Methods

    @Override
    public CassetteComponent getComponent() {
        return cassetteComponent;
    }

    //endregion HasComponent Methods

}
