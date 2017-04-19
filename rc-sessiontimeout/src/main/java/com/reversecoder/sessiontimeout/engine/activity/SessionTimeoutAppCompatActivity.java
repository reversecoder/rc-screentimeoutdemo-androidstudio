package com.reversecoder.sessiontimeout.engine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.cancelSessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.cancelSessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.pauseSessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.pauseSessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.resumeSessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.resumeSessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.sendTouchInfoToSessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.setCurrentActivity;

/**
 * @author Md. Rashsadul Alam
 */
public class SessionTimeoutAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelSessionTimeoutTask();

        cancelSessionTimeoutCountDownTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseSessionTimeoutTask();

        pauseSessionTimeoutCountDownTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity(this);

        resumeSessionTimeoutTask();

        resumeSessionTimeoutCountDownTimer();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        sendTouchInfoToSessionTimeoutTask();
    }
}