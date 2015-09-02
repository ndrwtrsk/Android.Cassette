package andrewtorski.casette;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.IOException;


public class RecordingActivity extends Activity {


    private int backgroundColorBeforeTap;
    private int backgroundColorAfterTap;

    private Animation RECORD_BUTTON_SCALE_UP;
    private Animation RECORD_BUTTON_SCALE_DOWN;


    //  Audio capture

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private Button mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private Button mPlayButton = null;
    private MediaPlayer mPlayer = null;


    //  end of Audio capture


    public RecordingActivity() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/casette.3gp";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        Button button = (Button) findViewById(R.id.record_button);

        RECORD_BUTTON_SCALE_UP = AnimationUtils.loadAnimation(this, R.anim.record_anim_scale_up);
        RECORD_BUTTON_SCALE_DOWN = AnimationUtils.loadAnimation(this, R.anim.record_anim_scale_down);

        backgroundColorBeforeTap = Color.WHITE;
        backgroundColorAfterTap = getResources().getColor(R.color.primary_dark);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    recordButtonOnDown(v);
                    return false;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    recordButtonOnUp(v);
                    return false;
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recording, menu);
        return true;
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


    public void recordButtonOnDown(final View button) {
        button.startAnimation(RECORD_BUTTON_SCALE_UP);
        final RelativeLayout background = (RelativeLayout) findViewById(R.id.background);
        animateViewBackgroundColor(background, backgroundColorBeforeTap, backgroundColorAfterTap);
        //animateViewBackgroundColor(button, backgroundColorAfterTap, backgroundColorBeforeTap);

        GradientDrawable bgDrawable = (GradientDrawable) button.getBackground();

        bgDrawable.setColor(Color.WHITE);
    }

    public void recordButtonOnUp(final View button) {
        button.startAnimation(RECORD_BUTTON_SCALE_DOWN);
        final RelativeLayout background = (RelativeLayout) findViewById(R.id.background);
        animateViewBackgroundColor(background, backgroundColorAfterTap, backgroundColorBeforeTap);
        //animateViewBackgroundColor(button, backgroundColorBeforeTap, backgroundColorAfterTap);

        GradientDrawable bgDrawable = (GradientDrawable) button.getBackground();

        bgDrawable.setColor(getResources().getColor(R.color.primary));
    }

    private void animateViewBackgroundColor(final View view, int fromColor, int toColor) {

        ValueAnimator animator = ValueAnimator.ofArgb(fromColor, toColor);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                view.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });

        animator.setDuration(500);

        animator.start();
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    //  Anon classes
    class RecordButton extends Button {

        private boolean mStartRecording = true;

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
            }
        };

        public RecordButton(Context context) {
            super(context);
            setText("Rec");

        }
    }
}


