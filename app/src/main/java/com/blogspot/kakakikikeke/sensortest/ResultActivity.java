package com.blogspot.kakakikikeke.sensortest;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.models.Score;
import com.blogspot.kakakikikeke.sensortest.utils.Const;
import com.blogspot.kakakikikeke.sensortest.utils.FactoryUtils;

import java.util.Date;

import io.realm.Realm;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
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
        TextView clearCountLabel = findViewById(R.id.clear_count_label);
        clearCountLabel.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        TextView clearCount = findViewById(R.id.clear_count);
        clearCount.setTypeface(Typeface.createFromAsset(getAssets(), Const.FONT_NAME));
        int point = i.getIntExtra(Const.INTENT_INDEX_NAME_CLEAR_COUNT, 0);
        if (point > 0) {
            storeScore(point);
        }
        clearCount.setText(String.valueOf(point));
    }

    public void storeScore(final int point) {
        try(Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    Score s = new Score();
                    s.setPoint(point);
                    s.setDate(new Date());
                    realm.insert(s);
                }
            });
        }
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
}
