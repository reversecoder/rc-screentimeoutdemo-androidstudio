package com.reversecoder.sessiontimeout.demo.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reversecoder.sessiontimeout.SessionTimeout;
import com.reversecoder.sessiontimeout.demo.R;

public class MainActivity extends SessionTimeout.TimeoutCompatActivity {

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
        //set durations in minutes
        SessionTimeout.setTimeoutDuration(5000);

        //start the sensor
        SessionTimeout.start();


        //You can also simply call:
        //TimeoutSensor.start(1); where the argument passed is the duration in minutes.

        if (SessionTimeout.timeoutSensorTask != null) {
            tvStatus.setText(SessionTimeout.timeoutSensorTask.isRunning() ? "Status: RUNNING" : "Status: CANCELLED");
            tvDuration.setText("Current duration: " + String.valueOf(SessionTimeout.getTimeoutDuration()));
            etDuration.setText(String.valueOf(SessionTimeout.getTimeoutDuration()));
            btnStartStop.setText(SessionTimeout.timeoutSensorTask.isRunning() ? "STOP" : "START");
        }
    }
}
