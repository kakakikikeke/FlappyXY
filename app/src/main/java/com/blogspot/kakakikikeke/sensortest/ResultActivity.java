package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.utils.Const;

public class ResultActivity extends AppCompatActivity {

    private TextView clearCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent i = getIntent();
        clearCount = (TextView) findViewById(R.id.clear_count);
        clearCount.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        String label = (String) clearCount.getText();
        clearCount.setText(label + i.getIntExtra(Const.INTENT_INDEX_NAME_CLEAR_COUNT, 0));
    }

    public void reStartGame(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void showRanking(View view) {
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.dispatchKeyEvent(e);
    }
}
