package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.blogspot.kakakikikeke.sensortest.utils.ConfigPropUtil;

public class StartActivity extends AppCompatActivity {

    private ConfigPropUtil config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        config = new ConfigPropUtil();
    }

    public void startGame(View view) {
        Intent i = new Intent(this, GameCountDownActivity.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent e) {
        return e.getKeyCode() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(e);
    }
}
