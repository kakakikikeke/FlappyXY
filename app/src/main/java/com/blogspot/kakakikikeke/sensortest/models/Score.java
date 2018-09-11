package com.blogspot.kakakikikeke.sensortest.models;

import java.util.Date;

import io.realm.RealmObject;

public class Score extends RealmObject {
    private int point;
    private Date date;

    public void setPoint(int point) {
        this.point = point;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}