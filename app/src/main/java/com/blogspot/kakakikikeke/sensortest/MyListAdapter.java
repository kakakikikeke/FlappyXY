package com.blogspot.kakakikikeke.sensortest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.blogspot.kakakikikeke.sensortest.models.Score;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

class MyListAdapter extends RealmBaseAdapter<Score> implements ListAdapter {

    private static class ViewHolder {
        TextView pointText;
        TextView dateText;
    }

    MyListAdapter(OrderedRealmCollection<Score> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.pointText = convertView.findViewById(R.id.point);
            viewHolder.dateText = convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (adapterData != null) {
            final Score item = adapterData.get(position);
            viewHolder.pointText.setText(item.getPoint());
            viewHolder.dateText.setText(item.getDate());
        }
        return convertView;
    }
}
