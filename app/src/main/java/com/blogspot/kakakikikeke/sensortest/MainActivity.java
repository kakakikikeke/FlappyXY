package com.blogspot.kakakikikeke.sensortest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.utils.Const;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SensorTest";
    private static final int X = 0;
    private static final int Y = 1;
    private SensorManager sensorManager;
    private TextView[] sensorText = new TextView[2];
    private TextView[] answerText = new TextView[2];
    private TextView countDown;
    private ProgressBar progressBar;
    private Intent resultIntent;
    private int[] answers = new int[2];
    private boolean[] answersFlag = new boolean[2];
    private float[] geomagnetic;
    private float[] acceleration;
    private int maxTime = 5;
    private int clearCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorText[X] = (TextView) findViewById(R.id.sensor_x_text);
        sensorText[Y] = (TextView) findViewById(R.id.sensor_y_text);
        countDown = (TextView) findViewById(R.id.count_down);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setMax(maxTime);
        resultIntent = new Intent(this, ResultActivity.class);

        resetGame();
    }

    private void resetTimer() {
        long interval = 10;
        long startTime = 6500;
        GameCountDownTimer countDownTimer = new GameCountDownTimer(startTime, interval);
        progressBar.setProgress(maxTime);
        countDown.setText(String.valueOf(maxTime));
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] outR = new float[16];
        float[] mOrientation = new float[3];
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomagnetic = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                acceleration = event.values.clone();
                break;
        }
        if (geomagnetic != null && acceleration != null) {
            SensorManager.getRotationMatrix(outR, null, acceleration, geomagnetic);
            SensorManager.getOrientation(outR, mOrientation);
            int x = radianToDegree(mOrientation[X + 1]);
            int y = radianToDegree(mOrientation[Y + 1]);
            sensorText[X].setText(String.valueOf("X : " + x));
            sensorText[Y].setText(String.valueOf("Y :" + y));
            if (!answersFlag[X] && answers[X] == x) {
                Log.i(TAG, "orientation[X] : " + x);
                answersFlag[X] = true;
                answerText[X].setText(Const.CLEARED);
            }
            if (!answersFlag[Y] && answers[Y] == y) {
                Log.i(TAG, "orientation[Y] : " + y);
                answersFlag[Y] = true;
                answerText[Y].setText(Const.CLEARED);
            }
            if (isClear()) {
                next();
            }
        }
    }

    private void next() {
        clearCount++;
        resetGame();
    }

    private void resetGame() {
        answersFlag[X] = false;
        answersFlag[Y] = false;
        initAnswers();
        resetTimer();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void initAnswers() {
        answerText[X] = (TextView) findViewById(R.id.answer_x_text);
        answerText[Y] = (TextView) findViewById(R.id.answer_y_text);
        answers[X] = getXRandomDegree();
        answers[Y] = getYZRandomDegree();
        answerText[X].setText("[X]: " + answers[X]);
        answerText[Y].setText("[Y]: " + answers[Y]);
    }

    private int getYZRandomDegree() {
        double d = Math.random() * 180;
        int degree = (int) d;
        if (degree != 0) {
            degree++;
        }
        double m = Math.random() * 10;
        int sign = (int) m;
        if (sign >= 5) {
            degree *= (-1);
        }
        return degree;
    }

    private int getXRandomDegree() {
        double d = Math.random() * 90;
        int degree = (int) d;
        if (degree != 0) {
            degree++;
        }
        double m = Math.random() * 10;
        int sign = (int) m;
        if (sign >= 5) {
            degree *= (-1);
        }
        return degree;
    }

    int radianToDegree(float rad) {
        return (int) Math.floor(Math.toDegrees(rad));
    }

    public class GameCountDownTimer extends CountDownTimer {

        public GameCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            countDown.setText("0");
            if (isClear()) {
                next();
            } else {
                resultIntent.putExtra(Const.CLEAR_COUNT, clearCount);
                startActivity(resultIntent);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished < 5000) {
                int mills = 1000;
                progressBar.setProgress((int) (millisUntilFinished / mills));
                countDown.setText(String.valueOf(millisUntilFinished / mills));
            }
        }
    }

    private boolean isClear() {
        return answersFlag[X] && answersFlag[Y];
    }
}
