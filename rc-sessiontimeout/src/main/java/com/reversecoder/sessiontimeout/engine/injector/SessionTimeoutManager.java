package com.reversecoder.sessiontimeout.engine.injector;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.reversecoder.sessiontimeout.engine.bearing.ResumableCountDownTimer;
import com.reversecoder.sessiontimeout.engine.piston.SessionTimeoutTask;

/**
 * @author Md. Rashsadul Alam
 */
public final class SessionTimeoutManager {

    private static Activity mCurrentActivity = null;
    public static long sessionTimeoutDuration = 5 * 60 * 1000;
    public static SessionTimeoutTask sessionTimeoutTask;
    public static ResumableCountDownTimer sessionTimeoutCountDownTimer;

    public static void startSessionTimeoutTask(int timeDuration) {
        cancelSessionTimeoutTask();
        sessionTimeoutDuration = timeDuration;
        sessionTimeoutTask = new SessionTimeoutTask();
//        sessionTimeoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionTimeoutDuration*60*1000);
//        sessionTimeoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionTimeoutDuration);
        sessionTimeoutTask.start();

    }

    public static void startSessionTimeoutTask() {
        cancelSessionTimeoutTask();
        sessionTimeoutTask = new SessionTimeoutTask();
//        sessionTimeoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionTimeoutDuration*60*1000);
//        sessionTimeoutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sessionTimeoutDuration);
        sessionTimeoutTask.start();
    }

    public static void setSessionTimeoutDuration(int timeDuration) {
        sessionTimeoutDuration = timeDuration;
    }

    public static long getSessionTimeoutDuration() {
        return sessionTimeoutDuration;
    }

    public static void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void finishCurrentActivity() {
        if (mCurrentActivity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mCurrentActivity.finishAffinity();
            } else {
                Intent intent = mCurrentActivity.getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mCurrentActivity.finish();
            }
        }
    }

    public static void cancelSessionTimeoutTask() {
        if (sessionTimeoutTask != null) {
            sessionTimeoutTask.cancel();
        }
    }

    public static void cancelSessionTimeoutCountDownTimer() {
        if (sessionTimeoutCountDownTimer != null) {
            sessionTimeoutCountDownTimer.cancel();
        }
    }

    public static void pauseSessionTimeoutTask() {
        if (sessionTimeoutTask != null) {
            sessionTimeoutTask.pause();
        }
    }

    public static void pauseSessionTimeoutCountDownTimer() {
        if (sessionTimeoutCountDownTimer != null) {
            sessionTimeoutCountDownTimer.pause();
        }
    }

    public static void resumeSessionTimeoutTask() {
        if (sessionTimeoutTask != null) {
            sessionTimeoutTask.resume();
        }
    }

    public static void resumeSessionTimeoutCountDownTimer() {
        if (sessionTimeoutCountDownTimer != null) {
            sessionTimeoutCountDownTimer.resume();
        }
    }

    public static void sendTouchInfoToSessionTimeoutTask() {
        if (sessionTimeoutTask != null) {
            sessionTimeoutTask.touch();
        }
    }

    public static void destroySessionTimeoutTask() {
        if (sessionTimeoutTask != null) {
            sessionTimeoutTask.cancel();
            sessionTimeoutTask = null;
        }
    }

    public static void destroySessionTimeoutCountDownTimer() {
        if (sessionTimeoutCountDownTimer != null) {
            sessionTimeoutCountDownTimer.cancel();
            sessionTimeoutCountDownTimer = null;
        }
    }
}
