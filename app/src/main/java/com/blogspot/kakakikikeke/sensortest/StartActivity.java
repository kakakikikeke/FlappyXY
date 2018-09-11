package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import io.realm.Realm;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        Realm.init(getApplicationContext());
    }

    public void startGame(View view) {
        Intent i = new Intent(this, GameCountDownActivity.class);
        startActivity(i);
    }

    public void showRanking(View view) {
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent e) {
        return e.getKeyCode() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(e);
    }
}
