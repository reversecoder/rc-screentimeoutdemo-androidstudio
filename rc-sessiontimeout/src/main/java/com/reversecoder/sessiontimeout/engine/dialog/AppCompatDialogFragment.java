package com.reversecoder.sessiontimeout.engine.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.reversecoder.sessiontimeout.R;
import com.reversecoder.sessiontimeout.engine.bearing.ResumableCountDownTimer;

import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.destroySessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.destroySessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.finishCurrentActivity;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.getCurrentActivity;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.getSessionTimeoutDialogCallback;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.sessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.startSessionTimeoutTask;

/**
 * @author Md. Rashsadul Alam
 */
public class AppCompatDialogFragment extends android.support.v4.app.DialogFragment {

    public String DIALOG_TITLE = "";
    public String DIALOG_POSITIVE_BUTTON = "";
    public String DIALOG_NEGATIVE_BUTTON = "";
    public String DIALOG_MESSAGE = "";

    public AppCompatDialogFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        LayoutInflater inflater = getCurrentActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_session_timeout_message, null);
        final TextView tvMessaage = (TextView) view.findViewById(R.id.session_timeout_message);
        sessionTimeoutCountDownTimer = new ResumableCountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long diffSeconds = millisUntilFinished / 1000 % 60;
                tvMessaage.setText(String.format(getDialogMessage(), diffSeconds));
            }

            @Override
            public void onFinish() {
                finishCurrentActivity();
                System.exit(0);
            }
        };
        sessionTimeoutCountDownTimer.start();
        builder.setView(view);
        builder.setTitle(getDialogTitle())
                .setPositiveButton(getDialogPositiveButton(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                destroySessionTimeoutCountDownTimer();
                                destroySessionTimeoutTask();
                                startSessionTimeoutTask();
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(getDialogNegativeButton(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                destroySessionTimeoutCountDownTimer();
                                destroySessionTimeoutTask();

                                if (getSessionTimeoutDialogCallback() != null) {
                                    getSessionTimeoutDialogCallback().sessionTimeoutButtonClick(dialog);
                                } else {
                                    finishCurrentActivity();
                                    System.exit(0);
                                    dismiss();
                                }
                            }
                        }

                );
        return builder.create();
    }

    public void setDialogTitle(String title) {
        DIALOG_TITLE = title;
    }

    public void setDialogPositiveButton(String button) {
        DIALOG_POSITIVE_BUTTON = button;
    }

    public void setDialogNegativeButton(String button) {
        DIALOG_NEGATIVE_BUTTON = button;
    }

    public void setDialogMessage(String textview) {
        DIALOG_MESSAGE = textview;
    }

    public String getDialogTitle() {
        if (DIALOG_TITLE.equalsIgnoreCase("")) {
            DIALOG_TITLE = getCurrentActivity().getString(R.string.dialog_title);
        }
        return DIALOG_TITLE;
    }

    public String getDialogPositiveButton() {
        if (DIALOG_POSITIVE_BUTTON.equalsIgnoreCase("")) {
            DIALOG_POSITIVE_BUTTON = getCurrentActivity().getString(R.string.dialog_positive_button);
        }
        return DIALOG_POSITIVE_BUTTON;
    }

    public String getDialogNegativeButton() {
        if (DIALOG_NEGATIVE_BUTTON.equalsIgnoreCase("")) {
            DIALOG_NEGATIVE_BUTTON = getCurrentActivity().getString(R.string.dialog_negative_button);
        }
        return DIALOG_NEGATIVE_BUTTON;
    }

    public String getDialogMessage() {
        if (DIALOG_MESSAGE.equalsIgnoreCase("")) {
            DIALOG_MESSAGE = getCurrentActivity().getString(R.string.dialog_message);
        }
        return DIALOG_MESSAGE;
    }
}