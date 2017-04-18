package com.reversecoder.sessiontimeout;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public final class SessionTimeout {

    private static Activity mCurrentActivity = null;
    public static long timeoutDuration = 10000;
    //    public static TimeoutSensorTask timeoutSensorTask;
    public static SessionTimeoutTask timeoutSensorTask;
    public static PlayPauseCountDownTimer countDownTimer;
    public static String DIALOG_TITLE = "ATTENTION:";
    public static String DIALOG_MESSAGE = "Your session is about to expire.";
    public static String DIALOG_BUTTON = "STAY SIGNED IN";
    public static String DIALOG_TEXTVIEW = "Session will expire in: %s seconds.";

    public static void start(int timeDuration) {
        stop();
        timeoutDuration = timeDuration;
        timeoutSensorTask = new SessionTimeoutTask();
//        timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration*60*1000);
//        timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration);
        timeoutSensorTask.start();

    }

    public static void start() {
        stop();
        timeoutSensorTask = new SessionTimeoutTask();
//        timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration*60*1000);
//        timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration);
        timeoutSensorTask.start();
    }

    public static void setTimeoutDuration(int timeDuration) {
        timeoutDuration = timeDuration;
    }

    public static long getTimeoutDuration() {
        return timeoutDuration;
    }

    public static void stop() {
        if (timeoutSensorTask != null) {
            timeoutSensorTask.cancel();
        }
    }

    public static void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setDialogTitle(String title) {
        DIALOG_TITLE = title;
    }

    public static void setDialogMessage(String message) {
        DIALOG_MESSAGE = message;
    }

    public static void setDialogButton(String button) {
        DIALOG_BUTTON = button;
    }

    public static void setDialogTextview(String textview) {
        DIALOG_TEXTVIEW = textview;
    }

    public static class TimeoutCompatActivity extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setCurrentActivity(this);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (timeoutSensorTask != null) {
                timeoutSensorTask.cancel();
            }

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            if (timeoutSensorTask != null) {
//                if(timeoutSensorTask.isRunning()){
                timeoutSensorTask.pause();
//                }
            }

            if (countDownTimer != null) {
                countDownTimer.pause();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            setCurrentActivity(this);
            if (timeoutSensorTask != null) {
//                if(!timeoutSensorTask.isRunning()){
                timeoutSensorTask.resume();
//                }
            }

            if (countDownTimer != null) {
                countDownTimer.resume();
            }
        }

        @Override
        public void onUserInteraction() {
            super.onUserInteraction();
            SessionTimeoutTask.touch();
        }
    }

    public static class TimeoutActivity extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setCurrentActivity(this);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (timeoutSensorTask != null) {
                timeoutSensorTask.cancel();
            }

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            if (timeoutSensorTask != null) {
//                if(timeoutSensorTask.isRunning()){
                timeoutSensorTask.pause();
//                }
            }

            if (countDownTimer != null) {
                countDownTimer.pause();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();
            setCurrentActivity(this);

            if (timeoutSensorTask != null) {
//                if(!timeoutSensorTask.isRunning()){
                timeoutSensorTask.resume();
//                }
            }

            if (countDownTimer != null) {
                countDownTimer.resume();
            }
        }

        @Override
        public void onUserInteraction() {
            super.onUserInteraction();
            SessionTimeoutTask.touch();
        }
    }

//    public static class TimeoutSensorTask extends AsyncTask<Long, Void, Void> {
//
//        private static final String TAG = TimeoutSensorTask.class.getName();
//        private static long lastUsed;
//        PowerManager pm;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pm = (PowerManager) mCurrentActivity.getApplicationContext().getSystemService(Context.POWER_SERVICE);
//        }
//
//        @Override
//        protected Void doInBackground(Long... params) {
//            long idle = 0;
//            this.touch();
//            while(!isCancelled()){
//                idle = System.currentTimeMillis() - lastUsed;
//                try{
//                    Thread.sleep(1000); //check every 1 seconds
//                }catch (InterruptedException e){
//                    Log.d(TAG, "TimeoutSensor interrupted!");
//                }
//                if(idle > params[0]){
//                    idle = 0;
//                    this.cancel(true);
//                    if(pm.isScreenOn()) {
//                        if(mCurrentActivity instanceof Activity){
//                            AppDialogFragment appDialogFragment = new AppDialogFragment();
//                            appDialogFragment.setCancelable(false);
//                            appDialogFragment.show(((Activity) mCurrentActivity).getFragmentManager(), "dialog");
//                        }else if(mCurrentActivity instanceof AppCompatActivity){
//                            AppCompatDialogFragment appCompatDialogFragment = new AppCompatDialogFragment();
//                            appCompatDialogFragment.setCancelable(false);
//                            appCompatDialogFragment.show(((AppCompatActivity) mCurrentActivity).getSupportFragmentManager(), "compatdialog");
//                        }
//                    }else{
//                        if(mCurrentActivity != null) {
//                            mCurrentActivity.finishAffinity();
//                        }
//                        System.exit(0);
//                    }
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        public static synchronized void touch(){
//            lastUsed = System.currentTimeMillis();
//        }
//
//    }


    public static class SessionTimeoutTask extends PlayPauseTimer {
        private static long lastUsed;
        PowerManager pm;
        long idle = 0;

        public SessionTimeoutTask() {
            super();
            pm = (PowerManager) mCurrentActivity.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            touch();
        }

        public SessionTimeoutTask(long interval, long duration) {
            super(interval, duration);
            pm = (PowerManager) mCurrentActivity.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            touch();
        }

        @Override
        protected void onTick() {
            if (isRunning()) {
                idle = System.currentTimeMillis() - lastUsed;
                if (idle > timeoutDuration) {
                    idle = 0;
                    if (pm.isScreenOn()) {
                        if (mCurrentActivity instanceof Activity) {
                            AppDialogFragment appDialogFragment = new AppDialogFragment();
                            appDialogFragment.setCancelable(false);
                            appDialogFragment.show(((Activity) mCurrentActivity).getFragmentManager(), "dialog");
                        } else if (mCurrentActivity instanceof AppCompatActivity) {
                            AppCompatDialogFragment appCompatDialogFragment = new AppCompatDialogFragment();
                            appCompatDialogFragment.setCancelable(false);
                            appCompatDialogFragment.show(((AppCompatActivity) mCurrentActivity).getSupportFragmentManager(), "compatdialog");
                        }
                    } else {
                        if (mCurrentActivity != null) {
                            mCurrentActivity.finishAffinity();
                        }
                        System.exit(0);
                    }

                    if (timeoutSensorTask != null) {
                        timeoutSensorTask.cancel();
                        timeoutSensorTask = null;
                    }
                }
            }
        }

        @Override
        protected void onFinish() {
            System.out.println("onFinish called!");
            this.cancel();
        }

        public static synchronized void touch() {
            lastUsed = System.currentTimeMillis();
        }
    }


    public static class AppDialogFragment extends android.app.DialogFragment {
        public AppDialogFragment() {
            super();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.session_dialog, null);
            final TextView sessionText = (TextView) view.findViewById(R.id.session_text);
            countDownTimer = new PlayPauseCountDownTimer(31000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long diffSeconds = millisUntilFinished / 1000 % 60;
                    sessionText.setText(String.format(DIALOG_TEXTVIEW, diffSeconds));
                }

                @Override
                public void onFinish() {
                    if (mCurrentActivity != null) {
                        mCurrentActivity.finishAffinity();
                    }
                    System.exit(0);
                }
            };
            countDownTimer.start();
            builder.setView(view);
            builder.setMessage(DIALOG_MESSAGE).setTitle(DIALOG_TITLE)
                    .setPositiveButton(DIALOG_BUTTON, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                                countDownTimer = null;
                            }
                            if (timeoutSensorTask != null) {
                                timeoutSensorTask.cancel();
                                timeoutSensorTask = null;
                            }
                            timeoutSensorTask = new SessionTimeoutTask();
//                            timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration*60*1000);
                            timeoutSensorTask.start();
                            dismiss();
                        }
                    });
            return builder.create();
        }
    }

    public static class AppCompatDialogFragment extends android.support.v4.app.DialogFragment {
        public AppCompatDialogFragment() {
            super();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.session_dialog, null);
            final TextView sessionText = (TextView) view.findViewById(R.id.session_text);
            countDownTimer = new PlayPauseCountDownTimer(31000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long diffSeconds = millisUntilFinished / 1000 % 60;
                    sessionText.setText(String.format(DIALOG_TEXTVIEW, diffSeconds));
                }

                @Override
                public void onFinish() {
                    if (mCurrentActivity != null) {
                        mCurrentActivity.finishAffinity();
                    }
                    System.exit(0);
                }
            };
            countDownTimer.start();
            builder.setView(view);
            builder.setMessage(DIALOG_MESSAGE).setTitle(DIALOG_TITLE)
                    .setPositiveButton(DIALOG_BUTTON, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                                countDownTimer = null;
                            }
                            if (timeoutSensorTask != null) {
                                timeoutSensorTask.cancel();
                                timeoutSensorTask = null;
                            }
                            timeoutSensorTask = new SessionTimeoutTask();
//                            timeoutSensorTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, timeoutDuration*60*1000);
                            timeoutSensorTask.start();
                            dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
