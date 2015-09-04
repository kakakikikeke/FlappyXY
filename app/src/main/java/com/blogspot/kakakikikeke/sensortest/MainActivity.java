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

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SensorTest";
    private static final int Z = 0;
    private static final int X = 1;
    private static final int Y = 2;
    private SensorManager mSensorManager;
    private TextView[] mSensor = new TextView[3];
    private TextView count;
    private ProgressBar mProgressBar;
    private Intent aResult;
    private int[] answers = new int[3];
    private float[] mGeomagnetic;
    private float[] mAcceleration;
    private final int mills = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor[Z] = (TextView) findViewById(R.id.sensor_0_text);
        mSensor[X] = (TextView) findViewById(R.id.sensor_1_text);
        mSensor[Y] = (TextView) findViewById(R.id.sensor_2_text);
        count = (TextView) findViewById(R.id.count);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        long startTime = 6500;
        int maxTime = 5;
        long interval = 10;
        mProgressBar.setMax(maxTime);
        aResult = new Intent(this, ResultActivity.class);
        initAnswers();

        GameCountDownTimer countDownTimer = new GameCountDownTimer(startTime, interval);
        mProgressBar.setProgress(maxTime);
        count.setText(String.valueOf(maxTime));
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
        float[] inR = new float[16];
        float[] outR = new float[16];
        float[] I = new float[16];
        float[] mOrientation = new float[3];
        Log.i(TAG, "Strart onSensorChanged");
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = event.values.clone();
                Log.i(TAG, "Get the value of magnetic field");
                break;
            case Sensor.TYPE_ACCELEROMETER:
                Log.i(TAG, "Get the value of accelerometer");
                mAcceleration = event.values.clone();
                break;
        }
        if (mGeomagnetic != null && mAcceleration != null) {
            SensorManager.getRotationMatrix(inR, I, mAcceleration, mGeomagnetic);
            SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
            SensorManager.getOrientation(outR, mOrientation);
            Log.i(TAG, "orientation[Z] : " + radianToDegree(mOrientation[Z]));
            Log.i(TAG, "orientation[X] : " + radianToDegree(mOrientation[X]));
            Log.i(TAG, "orientation[Y] : " + radianToDegree(mOrientation[Y]));
            mSensor[Z].setText(String.valueOf("Z : " + radianToDegree(mOrientation[Z])));
            mSensor[X].setText(String.valueOf("X : " + radianToDegree(mOrientation[X])));
            mSensor[Y].setText(String.valueOf("Y :" + radianToDegree(mOrientation[Y])));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    private void initAnswers() {
        TextView vAnswers = (TextView) findViewById(R.id.answers);
        answers[Z] = getRandomDegree();
        answers[X] = getRandomDegree();
        answers[Y] = getRandomDegree();
        vAnswers.setText(
                "[Z] -> " + answers[Z] + "\n" +
                        "[X] -> " + answers[X] + "\n" +
                        "[Y] -> " + answers[Y]);
    }

    private int getRandomDegree() {
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

    int radianToDegree(float rad) {
        return (int) Math.floor(Math.toDegrees(rad));
    }

    public class GameCountDownTimer extends CountDownTimer {

        public GameCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            count.setText("0");
            startActivity(aResult);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "millisUntilFinished : " + millisUntilFinished);
            if (millisUntilFinished < 5000) {
                mProgressBar.setProgress((int) (millisUntilFinished / mills));
                count.setText(String.valueOf(millisUntilFinished / mills));
            }
        }
    }
}
