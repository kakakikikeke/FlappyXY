package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.utils.Const;

public class GameCountDownActivity extends AppCompatActivity {

    TextView startCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_count_down_activity);

        Typeface face = Typeface.createFromAsset(getAssets(), Const.FONT_NAME);

        startCountDown = (TextView) findViewById(R.id.start_count_down);
        startCountDown.setTypeface(face);
        long interval = 10;
        long startTime = 4000;
        StartCountDownTimer countDownTimer = new StartCountDownTimer(startTime, interval);
        countDownTimer.start();
    }

    public class StartCountDownTimer extends CountDownTimer {

        public StartCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            startCountDown.setText("0");
            moveToMain();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished < 3000) {
                int mills = 1000;
                startCountDown.setText(String.valueOf(millisUntilFinished / mills));
            }
        }
    }

    private void moveToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent e) {
        return e.getKeyCode() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(e);
    }
}
