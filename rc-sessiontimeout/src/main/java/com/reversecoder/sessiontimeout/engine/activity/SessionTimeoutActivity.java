package com.reversecoder.sessiontimeout.engine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

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
public class SessionTimeoutActivity extends Activity {

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
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        pauseSessionTimeoutTask();

        pauseSessionTimeoutCountDownTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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