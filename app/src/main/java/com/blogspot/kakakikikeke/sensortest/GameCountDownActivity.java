package com.blogspot.kakakikikeke.sensortest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
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

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool(int poolMax) {
        SoundPool pool;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            pool = new SoundPool(poolMax, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            pool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(poolMax)
                    .build();
        }
        return pool;
    }

    public class StartCountDownTimer extends CountDownTimer {

        private SoundPool soundPool;
        private int soundId;
        private boolean threeCountFlag = false;
        private boolean twoCountFlag = false;
        private boolean oneCountFlag = false;

        public StartCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
            soundPool = buildSoundPool(1);
            soundId = soundPool.load(getApplicationContext(), R.raw.count_down, 0);
        }

        @Override
        public void onFinish() {
            startCountDown.setText("Start!");
            soundPool.release();
            moveToMain();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished <= 3000) {
                if (millisUntilFinished < 3000 && !threeCountFlag) {
                    threeCountFlag = true;
                    soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F);
                } else if(millisUntilFinished < 2000 && !twoCountFlag) {
                    twoCountFlag = true;
                    soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F);
                } else if(millisUntilFinished < 1000 && !oneCountFlag) {
                    oneCountFlag = true;
                    soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F);
                }
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
