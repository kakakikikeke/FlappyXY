package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.kakakikikeke.sensortest.utils.Const;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.SaveCallback;

public class ResultActivity extends AppCompatActivity {

    private static final String RANKING_CLASS = "ranking";
    private static final String NAME_FIELD = "name";
    private static final String SCORE_FIELD = "score";
    private static final String REGISTERING_WORD = "登録中";
    private static final String REGISTERERD_WORD = "登録済";
    private static final String WARNING = "1文字以上入力してください";
    private static final String MISSED_MESSAGE = "登録に失敗しました、再度お試しください";
    private TextView clearCount;
    private EditText userName;
    private Button registButton;

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
        registButton = (Button) findViewById(R.id.regist);
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
        if (name.length() < 1) {
            Toast.makeText(this, WARNING, Toast.LENGTH_LONG).show();
            return;
        }
        registButton.setEnabled(false);
        registButton.setText(REGISTERING_WORD);
        userName.setEnabled(false);
        String score = clearCount.getText().toString();
        registUserToNCMB(name, Integer.parseInt(score));
    }

    private void registUserToNCMB(final String name, int score) {
        NCMBObject obj = new NCMBObject(RANKING_CLASS);
        obj.put(NAME_FIELD, name);
        obj.put(SCORE_FIELD, score);
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(NCMBException e) {
                if(e != null){
                    registButton.setEnabled(true);
                    registButton.setText(R.string.regist);
                    userName.setEnabled(true);
                    Toast.makeText(getApplicationContext(), MISSED_MESSAGE, Toast.LENGTH_LONG).show();
                }else {
                    registButton.setText(REGISTERERD_WORD);
                    userName.setText(name + " -> " + "100位");
                }
            }
        });
    }
}
