package com.blogspot.kakakikikeke.sensortest;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.beans.Ranking;
import com.blogspot.kakakikikeke.sensortest.utils.Const;

import java.util.List;

public class RankingAdapter extends ArrayAdapter<Ranking> {

    private LayoutInflater layoutInflater;
    private Typeface face;

    public RankingAdapter(Context context, int resource, List<Ranking> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        face = Typeface.createFromAsset(context.getAssets(), Const.FONT_NAME);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ranking item = getItem(position);
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.ranking_list_layout, null);
        }
        TextView rank = (TextView) convertView.findViewById(R.id.rank);
        rank.setTypeface(face);
        rank.setText(String.valueOf(item.getRank()));
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setTypeface(face);
        name.setText(item.getName());
        TextView clearCount = (TextView) convertView.findViewById(R.id.clear_count);
        clearCount.setTypeface(face);
        clearCount.setText(String.valueOf(item.getClearCount()));

        return convertView;
    }
}
