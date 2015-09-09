package com.blogspot.kakakikikeke.sensortest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.beans.Ranking;

import java.util.List;

public class RankingAdapter extends ArrayAdapter<Ranking> {

    private LayoutInflater layoutInflater;

    public RankingAdapter(Context context, int resource, List<Ranking> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ranking item = getItem(position);
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.ranking_list_layout, null);
        }
        TextView rank = (TextView) convertView.findViewById(R.id.rank);
        rank.setText(String.valueOf(item.getRank()));
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(item.getName());
        TextView clearCount = (TextView) convertView.findViewById(R.id.clear_count);
        clearCount.setText(String.valueOf(item.getClearCount()));
        TextView registedDate = (TextView) convertView.findViewById(R.id.registed_date);
        registedDate.setText(item.getRegistedDate().toString());

        return convertView;
    }
}
