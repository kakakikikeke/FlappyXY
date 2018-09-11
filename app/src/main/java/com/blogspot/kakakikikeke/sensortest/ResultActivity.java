package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.SoundPool;
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
import com.blogspot.kakakikikeke.sensortest.utils.FactoryUtils;

public class ResultActivity extends AppCompatActivity {

    private static final String REGISTERING_WORD = "登録中";
    private static final String REGISTERERD_WORD = "登録済";
    private static final String WARNING = "1文字以上入力してください";
    private static final String MISSED_REGIST_MESSAGE = "登録に失敗しました、再度お試しください";
    private static final String MISSED_GET_MESSAGE = "現在の順位取得に失敗しました";
    private TextView clearCount;
    private EditText userName;
    private Button registButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Typeface face = Typeface.createFromAsset(getAssets(), Const.FONT_NAME);
        SoundPool soundPool = FactoryUtils.buildSoundPool(1);
        final int soundId = soundPool.load(getApplicationContext(), R.raw.end, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (0 == status) {
                    soundPool.play(soundId, 0.2F, 0.2F, 0, 0, 1.0F);
                }
            }
        });

        Intent i = getIntent();
        TextView clearCountLabel = (TextView) findViewById(R.id.clear_count_label);
        clearCountLabel.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        clearCount = (TextView) findViewById(R.id.clear_count);
        clearCount.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        clearCount.setText(String.valueOf(i.getIntExtra(Const.INTENT_INDEX_NAME_CLEAR_COUNT, 0)));
        userName = (EditText) findViewById(R.id.name);
        registButton = (Button) findViewById(R.id.regist);
        registButton.setTypeface(face);
    }

    public void reStartGame(View view) {
        Intent i = new Intent(this, GameCountDownActivity.class);
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
    }

}
