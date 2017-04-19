package com.reversecoder.sessiontimeout.demo.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reversecoder.sessiontimeout.engine.activity.SessionTimeoutAppCompatActivity;
import com.reversecoder.sessiontimeout.engine.injector.SessionTimeoutManager;
import com.reversecoder.sessiontimeout.demo.R;

/**
 * @author Md. Rashsadul Alam
 */
public class MainActivity extends SessionTimeoutAppCompatActivity {

    Button btnStartStop;
    Button btnGetDuration;
    Button btnSetDuration;
    EditText etDuration;
    TextView tvDuration;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initAction();
    }

    private void initUI() {
        btnStartStop = (Button) findViewById(R.id.button3);
        btnGetDuration = (Button) findViewById(R.id.button2);
        btnSetDuration = (Button) findViewById(R.id.button1);
        etDuration = (EditText) findViewById(R.id.editText1);
        tvDuration = (TextView) findViewById(R.id.textView1);
        tvStatus = (TextView) findViewById(R.id.textView2);
    }

    private void initAction() {
        SessionTimeoutManager.startSessionTimeoutTask(5000);

        if (SessionTimeoutManager.sessionTimeoutTask != null) {
            tvStatus.setText(SessionTimeoutManager.sessionTimeoutTask.isRunning() ? "Status: RUNNING" : "Status: CANCELLED");
            tvDuration.setText("Current duration: " + String.valueOf(SessionTimeoutManager.getSessionTimeoutDuration()));
            etDuration.setText(String.valueOf(SessionTimeoutManager.getSessionTimeoutDuration()));
            btnStartStop.setText(SessionTimeoutManager.sessionTimeoutTask.isRunning() ? "STOP" : "START");
        }
    }
}
