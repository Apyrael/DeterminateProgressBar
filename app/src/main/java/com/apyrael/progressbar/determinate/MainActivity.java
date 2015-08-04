package com.apyrael.progressbar.determinate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

    /**
     * Handler that will be used to keep calling a method to update the progress bar.
     */
    private Handler pbHandler = new Handler();

    /**
     * Well, this is the progress bar instance, is it not?
     */
    private ProgressBar pbProgress;

    /**
     * The start/stop toggle button.
     */
    private Button btnStartStop;

    /**
     * Value indicating whether the progress bar is currently running.
     */
    private boolean isRunning;

    /**
     * Runnable that will be called to update the progress bar.
     */
    private Runnable pbRunnable = new Runnable() {

        @Override
        public void run() {
            pbHandler.postDelayed(this, 500);
            if (pbProgress.getProgress() == pbProgress.getMax()) {
                // stop the handler from being called anymore.
                pbHandler.removeCallbacks(pbRunnable);
                btnStartStop.setText(getString(R.string.start));
                pbProgress.setProgress(0);
                isRunning = false;
            } else {
                // increment it by one.
                pbProgress.setProgress(pbProgress.getProgress() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbProgress = (ProgressBar) findViewById(R.id.progressBar);
        btnStartStop = (Button) findViewById(R.id.btnStartStop);
        setupOnClickListeners();
    }

    /**
     * Sets up the on click listeners for the respective views.
     */
    private void setupOnClickListeners() {

        setupStartStopButtonOnClickListener();
    }

    /**
     * Sets up the start/stop button on click.
     */
    private void setupStartStopButtonOnClickListener() {

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    // start the runnable that will update the progress bar
                    isRunning = true;
                    pbHandler.post(pbRunnable);
                    btnStartStop.setText(getString(R.string.stop));
                } else {
                    // stop the handler
                    isRunning = false;
                    pbHandler.removeCallbacks(pbRunnable);
                    btnStartStop.setText(getString(R.string.start));
                }
            }
        });
    }
}
