package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes3.dex */
public class MyAutomaticHRMonitoring extends SendData {
    int endHour;
    int endMinute;
    int open;
    int startHour;
    int startMinute;
    int time;
    int week;

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getOpen() {
        return this.open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return this.startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return this.endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String toString() {
        return "MyAutomaticHRMonitoring{open=" + this.open + ", startHour=" + this.startHour + ", startMinute=" + this.startMinute + ", endHour=" + this.endHour + ", endMinute=" + this.endMinute + ", week=" + this.week + ", time=" + this.time + '}';
    }
}
