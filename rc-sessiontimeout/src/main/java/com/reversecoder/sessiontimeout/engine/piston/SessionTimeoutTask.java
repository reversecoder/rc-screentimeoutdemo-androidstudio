package com.reversecoder.sessiontimeout.engine.piston;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import com.reversecoder.sessiontimeout.engine.bearing.ResumableTimer;
import com.reversecoder.sessiontimeout.engine.dialog.AppCompatDialogFragment;
import com.reversecoder.sessiontimeout.engine.dialog.AppDialogFragment;

import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.destroySessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.finishCurrentActivity;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.getCurrentActivity;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.sessionTimeoutDuration;

/**
 * @author Md. Rashsadul Alam
 */
public class SessionTimeoutTask extends ResumableTimer {
    private static long lastUsed;
    PowerManager pm;
    long idle = 0;

    public SessionTimeoutTask() {
        super();
        pm = (PowerManager) getCurrentActivity().getApplicationContext().getSystemService(Context.POWER_SERVICE);
        touch();
    }

    public SessionTimeoutTask(long interval, long duration) {
        super(interval, duration);
        pm = (PowerManager) getCurrentActivity().getApplicationContext().getSystemService(Context.POWER_SERVICE);
        touch();
    }

    @Override
    protected void onTick() {
        if (isRunning()) {
            idle = System.currentTimeMillis() - lastUsed;
            if (idle > sessionTimeoutDuration) {
                idle = 0;
                if (pm.isScreenOn()) {
                    if (getCurrentActivity() instanceof Activity) {
                        AppDialogFragment appDialogFragment = new AppDialogFragment();
                        appDialogFragment.setCancelable(false);
                        appDialogFragment.show(((Activity) getCurrentActivity()).getFragmentManager(), "dialog");
                    } else if (getCurrentActivity() instanceof AppCompatActivity) {
                        AppCompatDialogFragment appCompatDialogFragment = new AppCompatDialogFragment();
                        appCompatDialogFragment.setCancelable(false);
                        appCompatDialogFragment.show(((AppCompatActivity) getCurrentActivity()).getSupportFragmentManager(), "compatdialog");
                    }
                } else {
                    if (getCurrentActivity() != null) {
                        finishCurrentActivity();
                    }
                    System.exit(0);
                }

                destroySessionTimeoutTask();
            }
        }
    }

    @Override
    protected void onFinish() {
        System.out.println("onFinish called!");
        this.cancel();
    }

    public synchronized void touch() {
        lastUsed = System.currentTimeMillis();
    }
}