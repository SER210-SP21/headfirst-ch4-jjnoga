package edu.quinnipiac.ser210.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) { //if there is prior save data, update the variables to match that data
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    /*
    @Override
    protected void onStop() { //gets called when your activity has stopped being visible to the user
        super.onStop();
        wasRunning = running;
        running = false;
    } */

    /*
    @Override
    protected void onStart() { //gets called when your activity becomes visible to the user
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    } */

    @Override
    protected void onPause() { //gets called only when the activity is no longer in the foreground.
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() { //gets called when the activity appears in the foreground and has the focus.
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler(); //used to schedule code that should be run at some point in the future
                                                //You can also use it to post code that needs to run on a different thread than the main Android thread.
        handler.post(new Runnable() { //The post() method posts code that needs to be run as soon as possible (which is usually almost immediately).

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); //The code will run as soon as possible after the delay.
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState) { //useful for saving the state in case the screen rotates
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
}