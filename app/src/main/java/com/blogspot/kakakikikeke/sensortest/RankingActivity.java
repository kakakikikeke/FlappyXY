package com.blogspot.kakakikikeke.sensortest;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.kakakikikeke.sensortest.beans.Ranking;
import com.blogspot.kakakikikeke.sensortest.utils.Const;
import com.nifty.cloud.mb.FindCallback;
import com.nifty.cloud.mb.NCMBException;
import com.nifty.cloud.mb.NCMBObject;
import com.nifty.cloud.mb.NCMBQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private static final String MISSED_MESSAGE = "取得に失敗しました、再度お試しください";
    private static final String LOADING = "ランキング情報の取得中...";
    private RankingAdapter adapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_activity);
        ListView rankingListView = (ListView) findViewById(R.id.ranking_list);
        Typeface face = Typeface.createFromAsset(getAssets(), Const.FONT_NAME);

        View header = getLayoutInflater().inflate(R.layout.ranking_list_header, null);
        TextView headerRank = (TextView) header.findViewById(R.id.header_rank);
        headerRank.setTypeface(face);
        TextView headerName = (TextView) header.findViewById(R.id.header_name);
        headerName.setTypeface(face);
        TextView headerClearCount = (TextView) header.findViewById(R.id.header_clear_count);
        headerClearCount.setTypeface(face);
        rankingListView.addHeaderView(header);

        adapter = new RankingAdapter(getApplicationContext(), R.layout.ranking_list_layout, new ArrayList<Ranking>());
        rankingListView.setAdapter(adapter);
        progress = new ProgressDialog(this);
        progress.setMessage(LOADING);
        progress.show();
        getUsersRanking();
    }

    private void getUsersRanking() {
        NCMBQuery<NCMBObject> query = new NCMBQuery<>(Const.RANKING_CLASS);
        query.setLimit(100);
        query.orderByDescending(Const.SCORE_FIELD);
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> results, NCMBException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), MISSED_MESSAGE, Toast.LENGTH_LONG).show();
                    progress.dismiss();
                } else {
                    adapter.addAll(bindRanking(results));
                    adapter.notifyDataSetChanged();
                    progress.dismiss();
                }
            }

            private ArrayList<Ranking> bindRanking(List<NCMBObject> results) {
                ArrayList<Ranking> users = new ArrayList<>();
                int rank = 1;
                for (NCMBObject obj: results) {
                    Ranking r = new Ranking(
                            (String) obj.get(Const.NAME_FIELD),
                            rank,
                            (Integer) obj.get(Const.SCORE_FIELD),
                            (Date) obj.get("createDate")
                    );
                    rank++;
                    users.add(r);
                }
                return users;
            }
        });
    }
}