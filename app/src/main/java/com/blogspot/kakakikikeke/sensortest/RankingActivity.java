package com.blogspot.kakakikikeke.sensortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.blogspot.kakakikikeke.sensortest.beans.Ranking;

import java.util.ArrayList;
import java.util.Date;

public class RankingActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_activity);

        ArrayList<Ranking> users = new ArrayList<Ranking>();
        users.add(new Ranking("hoge1", 1, 100, new Date()));
        users.add(new Ranking("hoge2", 2, 100, new Date()));
        users.add(new Ranking("hoge3", 3, 100, new Date()));

        RankingAdapter adapter = new RankingAdapter(this, R.layout.ranking_list_layout, users);

        ListView rankingListView = (ListView) findViewById(R.id.ranking_list);
        rankingListView.setAdapter(adapter);
    }
}