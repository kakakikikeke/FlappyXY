package com.blogspot.kakakikikeke.sensortest.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

public class Score extends RealmObject {
    private int point;
    private Date date;
    static public final String DATE_PATTERN ="yyyy/MM/dd HH:mm:ss";

    public void setPoint(int point) {
        this.point = point;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPoint() {
        return String.valueOf(this.point);
    }

    public String getDate() {
        return new SimpleDateFormat(DATE_PATTERN).format(this.date);
    }
}