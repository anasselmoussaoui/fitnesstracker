package com.example.heartmonitor2.blesdk1810.model;

import java.io.Serializable;

/* loaded from: classes6.dex */
public class Clock extends SendData implements Serializable {
    String content;
    boolean enable;
    int hour;
    int minute;
    int number;
    int type;
    byte week;

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getContent() {
        return this.content == null ? "" : this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(byte week) {
        this.week = week;
    }

    public String toString() {
        return "Clock{number=" + this.number + ", type=" + this.type + ", hour=" + this.hour + ", minute=" + this.minute + ", week=" + ((int) this.week) + ", content='" + this.content + "', enable=" + this.enable + '}';
    }
}
