package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.utils.Const;
import com.blogspot.kakakikikeke.sensortest.utils.FactoryUtils;

public class GameCountDownActivity extends AppCompatActivity {

    TextView startCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_count_down_activity);

        Typeface face = Typeface.createFromAsset(getAssets(), Const.FONT_NAME);

        startCountDown = findViewById(R.id.start_count_down);
        startCountDown.setTypeface(face);
        long interval = 1000;
        long startTime = 3200;
        StartCountDownTimer countDownTimer = new StartCountDownTimer(startTime, interval);
        countDownTimer.start();
    }

    public class StartCountDownTimer extends CountDownTimer {

        private SoundPool soundPool;
        private int soundId;
        private boolean threeCountFlag = false;
        private boolean twoCountFlag = false;
        private boolean oneCountFlag = false;

        public StartCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
            soundPool = FactoryUtils.buildSoundPool(1);
            soundId = soundPool.load(getApplicationContext(), R.raw.count_down, 0);
        }

        @Override
        public void onFinish() {
            soundPool.release();
            moveToMain();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished <= 3000) {
                soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F);
                startCountDown.setText(String.valueOf((millisUntilFinished / 1000) + 1));
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
