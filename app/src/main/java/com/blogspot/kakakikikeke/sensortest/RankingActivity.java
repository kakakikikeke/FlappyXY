package com.blogspot.kakakikikeke.sensortest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.beans.Ranking;
import com.blogspot.kakakikikeke.sensortest.utils.Const;

import java.util.ArrayList;
import java.util.Date;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_activity);

        Typeface face = Typeface.createFromAsset(getAssets(), Const.FONT_NAME);

        ArrayList<Ranking> users = new ArrayList<Ranking>();
        for (int i = 1; i <= 100; i++) {
            users.add(new Ranking("hoge1", i, 100, new Date()));
        }

        RankingAdapter adapter = new RankingAdapter(this, R.layout.ranking_list_layout, users);

        ListView rankingListView = (ListView) findViewById(R.id.ranking_list);

        View header = getLayoutInflater().inflate(R.layout.ranking_list_header, null);
        TextView headerRank = (TextView) header.findViewById(R.id.header_rank);
        headerRank.setTypeface(face);
        TextView headerName = (TextView) header.findViewById(R.id.header_name);
        headerName.setTypeface(face);
        TextView headerClearCount = (TextView) header.findViewById(R.id.header_clear_count);
        headerClearCount.setTypeface(face);

        rankingListView.addHeaderView(header);
        rankingListView.setAdapter(adapter);
    }
}