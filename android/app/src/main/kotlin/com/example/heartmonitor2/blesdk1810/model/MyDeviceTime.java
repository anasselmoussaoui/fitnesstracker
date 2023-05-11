package com.example.heartmonitor2.blesdk1810.model;

import java.util.Calendar;

/* loaded from: classes8.dex */
public class MyDeviceTime extends SendData {
    int day;
    int hour;
    int minute;
    int month;
    int second;
    int year;

    public MyDeviceTime() {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(1);
        this.month = calendar.get(2) + 1;
        this.day = calendar.get(5);
        this.hour = calendar.get(11);
        this.minute = calendar.get(12);
        this.second = calendar.get(13);
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String toString() {
        return "MyDeviceTime{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", minute=" + this.minute + ", second=" + this.second + '}';
    }
}
