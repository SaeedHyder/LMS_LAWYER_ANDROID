package com.ingic.lmslawyer.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.ingic.lmslawyer.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

//    @BindView(R.id.video_view)
//    tcking.github.com.giraffeplayer2.VideoView video_view;
    @BindView(R.id.videoView)
    VideoView videoView;

    final int TIME_INTERVAL_TO_CHECK = 500;// in millis
    final int MIN_TIME_INTERVAL_FOR_SPLASH = 2500; // in millis
    boolean workComplete = false;
    Timer checkWorkTimer;
    Runnable backgroundWork = new Runnable() {

        @Override
        public void run() {

            // This area can be used in Splash to do tasks that do not delay
            // Splash as well as do not extend its time if there processing time
            // is less than splash
            // Check Internet Connection and meet necessary conditions
            // to start the app. E.g. Disk Checks.
            // Check preferences and availability of certain data.
            workComplete = true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        try {

            String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.splash_updated_001;

            videoView.setVideoPath(uriPath);
            videoView.start();

//            video_view.setVideoPath(uriPath).getPlayer().start();


            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    showMainActivity();

                }
            });
         /*   video_view.setPlayerListener(new PlayerListener() {
                @Override
                public void onPrepared(GiraffePlayer giraffePlayer) {

                }

                @Override
                public void onBufferingUpdate(GiraffePlayer giraffePlayer, int percent) {

                }

                @Override
                public boolean onInfo(GiraffePlayer giraffePlayer, int what, int extra) {
                    return false;
                }

                @Override
                public void onCompletion(GiraffePlayer giraffePlayer) {
                    showMainActivity();

                }

                @Override
                public void onSeekComplete(GiraffePlayer giraffePlayer) {

                }

                @Override
                public boolean onError(GiraffePlayer giraffePlayer, int what, int extra) {
                    return false;
                }

                @Override
                public void onPause(GiraffePlayer giraffePlayer) {

                }

                @Override
                public void onRelease(GiraffePlayer giraffePlayer) {

                }

                @Override
                public void onStart(GiraffePlayer giraffePlayer) {

                }

                @Override
                public void onTargetStateChange(int oldState, int newState) {

                }

                @Override
                public void onCurrentStateChange(int oldState, int newState) {

                }

                @Override
                public void onDisplayModelChange(int oldModel, int newModel) {

                }

                @Override
                public void onPreparing(GiraffePlayer giraffePlayer) {

                }

                @Override
                public void onTimedText(GiraffePlayer giraffePlayer, IjkTimedText text) {

                }

                @Override
                public void onLazyLoadProgress(GiraffePlayer giraffePlayer, int progress) {

                }

                @Override
                public void onLazyLoadError(GiraffePlayer giraffePlayer, String message) {

                }
            });*/
        } catch (Exception ex) {
            showMainActivity();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        launchTimerAndTask();
    }

    private void launchTimerAndTask() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                showMainActivity();
            }
        }, MIN_TIME_INTERVAL_FOR_SPLASH);
        // Launch timer to test image changing and background threads work
       /* checkWorkTimer = new Timer();
        checkWorkTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (workComplete) {
                    initNextActivity();
                }
            }

        }, MIN_TIME_INTERVAL_FOR_SPLASH, TIME_INTERVAL_TO_CHECK);

        new Thread(backgroundWork).start();*/
    }

    private void initNextActivity() {
        checkWorkTimer.cancel();
        showMainActivity();

    }

    private void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}