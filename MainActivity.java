package com.example.rudy.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, buttonPause, buttonReset, buttonResume;
    long rem1 = 180, rem2 = 180, seconds1, seconds2; //rem is remaining time left on the timer
    boolean isPaused = false, isPaused1, isPaused2, isReset;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.player1);
        button2 = (Button) findViewById(R.id.player2);
        buttonPause = (Button) findViewById(R.id.btnPause);
        buttonPause.setEnabled(false);
        buttonReset = (Button) findViewById(R.id.btnReset);
        buttonResume = (Button) findViewById(R.id.btnResume);
        buttonResume.setVisibility(View.INVISIBLE);
    }

    //when first timer is clicked
    public void start1(View view){

        flag=1; //for pause and resume buttons validation purpose only
        button1.setEnabled(false); //sets timer1 so it can't be clicked
        button2.setEnabled(true);
        buttonPause.setEnabled(true);
        isPaused = false;
        isPaused1 = true; //pauses this timer
        isPaused2 = false;
        seconds1 = rem1;
        isReset = false;

        CountDownTimer countDownTimer = new CountDownTimer(seconds1 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                //if pause condition is true, cancel (pause) the timer. If not, run the timer
                if(isPaused2 || isPaused || isReset){
                    cancel();
                }
                else {
                    NumberFormat f = new DecimalFormat("00");
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;

                    button2.setText(f.format(min) + ":" + f.format(sec));
                    rem1 = (min * 60) + sec;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    //when second timer is clicked
    public void start2(View view){

        flag=2; //for pause and resume buttons validation purpose only
        button1.setEnabled(true);
        button2.setEnabled(false); //sets clock2 so it can't be clicked
        buttonPause.setEnabled(true);
        isPaused = false;
        isPaused2 = true; //pauses this timer
        isPaused1 = false;
        seconds2 = rem2;
        isReset = false;

        CountDownTimer countDownTimer = new CountDownTimer(seconds2 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                //if pause condition is true, cancel (pause) the timer. If not, run the timer
                if(isPaused1 || isPaused || isReset) {
                    cancel();
                }
                else {
                    NumberFormat f = new DecimalFormat("00");
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;

                    button1.setText(f.format(min) + ":" + f.format(sec));
                    rem2 = (min * 60) + sec;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    //when pause button is clicked
    public void pause(View view){
        isPaused = true;
        buttonPause.setVisibility(View.INVISIBLE);
        buttonResume.setVisibility(View.VISIBLE);

        //if pause button is clicked, none of the timers can be clicked
        if(flag == 1){
            button2.setEnabled(false);
        }
        else{
            button1.setEnabled(false);
        }
    }

    //when resume button is clicked (after pausing)
    public void resume(View view) {
        isPaused = false;
        buttonPause.setVisibility(View.VISIBLE);
        buttonResume.setVisibility(View.INVISIBLE);

        seconds1 = rem1;
        seconds2 = rem2;

        //if flag is 1, timer 2 is resumed and vice versa
        if (flag == 1) {

            button2.setEnabled(true);

            CountDownTimer countDownTimer = new CountDownTimer(seconds1 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if (isPaused2 || isPaused || isReset) {
                        cancel();
                    } else {
                        NumberFormat f = new DecimalFormat("00");
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;

                        button2.setText(f.format(min) + ":" + f.format(sec));
                        rem1 = (min * 60) + sec;
                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
        else{
            button1.setEnabled(true);

            CountDownTimer countDownTimer = new CountDownTimer(seconds2 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if (isPaused1 || isPaused || isReset) {
                        cancel();
                    } else {
                        NumberFormat f = new DecimalFormat("00");
                        long min = (millisUntilFinished / 60000) % 60;
                        long sec = (millisUntilFinished / 1000) % 60;

                        button1.setText(f.format(min) + ":" + f.format(sec));
                        rem2 = (min * 60) + sec;
                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }

    //when reset button is clicked
    public void reset(View view){
        isReset = true;
        buttonPause.setEnabled(false);
        button1.setEnabled(true);
        button2.setEnabled(true);
        buttonResume.setVisibility(View.INVISIBLE);
        buttonPause.setVisibility(View.VISIBLE);

        //sets the seconds back to 180 (3 minutes)
        rem1 = 180;
        rem2 = 180;

        NumberFormat f = new DecimalFormat("00");
        long min = (180000 / 60000) % 60;
        long sec = (180000 / 1000) % 60;

        button1.setText(f.format(min) + ":" + f.format(sec));
        button2.setText(f.format(min) + ":" + f.format(sec));
    }

}
