package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.utils.Const;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.SaveCallback;

public class ResultActivity extends AppCompatActivity {

    private static final String RANKING_CLASS = "ranking";
    private static final String NAME_FIELD = "name";
    private static final String SCORE_FIELD = "score";
    private TextView clearCount;
    private EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent i = getIntent();
        TextView clearCountLabel = (TextView) findViewById(R.id.clear_count_label);
        clearCountLabel.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        clearCount = (TextView) findViewById(R.id.clear_count);
        clearCount.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        clearCount.setText(String.valueOf(i.getIntExtra(Const.INTENT_INDEX_NAME_CLEAR_COUNT, 0)));
        userName = (EditText) findViewById(R.id.name);
    }

    public void reStartGame(View view) {
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

    public void registUser(View view) {
        String name = userName.getText().toString();
        String score = clearCount.getText().toString();
        registUserToNCMB(name, Integer.parseInt(score));
    }

    private void registUserToNCMB(String name, int score) {
        NCMBObject obj = new NCMBObject(RANKING_CLASS);
        obj.put(NAME_FIELD, name);
        obj.put(SCORE_FIELD, score);
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(NCMBException e) {
                if(e != null){

                }else {

                }
            }
        });
    }
}
