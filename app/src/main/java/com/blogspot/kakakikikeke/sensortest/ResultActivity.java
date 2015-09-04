package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
    }

    public void reStartGame(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
