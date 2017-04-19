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

import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.sessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.destroySessionTimeoutCountDownTimer;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.destroySessionTimeoutTask;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.finishCurrentActivity;
import static com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager.startSessionTimeoutTask;

/**
 * @author Md. Rashsadul Alam
 */
public class AppDialogFragment extends android.app.DialogFragment {

    public String DIALOG_TITLE = "";
    public String DIALOG_MESSAGE = "";
    public String DIALOG_BUTTON = "";
    public String DIALOG_TEXTVIEW = "";

    public AppDialogFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.session_dialog, null);
        final TextView sessionText = (TextView) view.findViewById(R.id.session_text);
        sessionTimeoutCountDownTimer = new ResumableCountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long diffSeconds = millisUntilFinished / 1000 % 60;
                sessionText.setText(String.format(getDialogTextview(), diffSeconds));
            }

            @Override
            public void onFinish() {
                if (getActivity() != null) {
                    finishCurrentActivity();
                }
                System.exit(0);
            }
        };
        sessionTimeoutCountDownTimer.start();
        builder.setView(view);
        builder.setMessage(getDialogMessage()).setTitle(getDialogTitle())
                .setPositiveButton(getDialogButton(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        destroySessionTimeoutCountDownTimer();

                        destroySessionTimeoutTask();

                        startSessionTimeoutTask();

                        dismiss();
                    }
                });
        return builder.create();
    }


    public void setDialogTitle(String title) {
        DIALOG_TITLE = title;
    }

    public void setDialogMessage(String message) {
        DIALOG_MESSAGE = message;
    }

    public void setDialogButton(String button) {
        DIALOG_BUTTON = button;
    }

    public void setDialogTextview(String textview) {
        DIALOG_TEXTVIEW = textview;
    }

    public String getDialogTitle() {
        if (DIALOG_TITLE.equalsIgnoreCase("")) {
            DIALOG_TITLE = getActivity().getString(R.string.dialog_title);
        }
        return DIALOG_TITLE;
    }

    public String getDialogMessage() {
        if (DIALOG_MESSAGE.equalsIgnoreCase("")) {
            DIALOG_MESSAGE = getActivity().getString(R.string.dialog_message);
        }
        return DIALOG_MESSAGE;
    }

    public String getDialogButton() {
        if (DIALOG_BUTTON.equalsIgnoreCase("")) {
            DIALOG_BUTTON = getActivity().getString(R.string.dialog_btton);
        }
        return DIALOG_BUTTON;
    }

    public String getDialogTextview() {
        if (DIALOG_TEXTVIEW.equalsIgnoreCase("")) {
            DIALOG_TEXTVIEW = getActivity().getString(R.string.dialog_textview);
        }
        return DIALOG_TEXTVIEW;
    }
}