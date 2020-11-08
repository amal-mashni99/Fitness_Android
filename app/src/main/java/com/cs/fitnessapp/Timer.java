package com.cs.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private int seconds ;
    private boolean running;

    private NumberPicker h;
    private NumberPicker m;
    private NumberPicker s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent n= getIntent();
////////////////added
        h=findViewById(R.id.hours);
        h.setMaxValue(23);
        m=findViewById(R.id.minutes);
        m.setMaxValue(59);
        s=findViewById(R.id.seconds);
s.setMaxValue(59);




        if(savedInstanceState !=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }


        runTimer();
    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", running);
    }


    public void onClickStart(View view) {
        seconds=getDuration();
        if (seconds>0)
        running = true;

    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        seconds = 0;
        running = false;

    }
    private void runTimer(){
        final TextView txtView = (TextView) findViewById(R.id.timerView);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                
                int hours = seconds/3600;
                int minutes = seconds % 3600 /60;
                int snds = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, snds);
                txtView.setText(time);
                if(running){
                    --seconds;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private int getDuration() {
        final int minutes = m.getValue()*60;
        final int seconds = s.getValue();
        final int hours= h.getValue()*3600;
        return minutes+seconds+hours;
    }

}