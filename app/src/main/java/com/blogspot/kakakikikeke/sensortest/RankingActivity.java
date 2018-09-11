package com.blogspot.kakakikikeke.sensortest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.blogspot.kakakikikeke.sensortest.models.Score;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RankingActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_activity);
        realm = Realm.getDefaultInstance();
        RealmResults<Score> scores = realm.where(Score.class).sort("point", Sort.DESCENDING).findAll();
        MyListAdapter adapter = new MyListAdapter(scores);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
