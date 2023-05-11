package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes3.dex */
public class MySedentaryReminder extends SendData {
    boolean enable;
    int endHour;
    int endMinute;
    int intervalTime;
    int leastStep;
    int startHour;
    int startMinute;
    int week;

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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

    public int getIntervalTime() {
        return this.intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getLeastStep() {
        return this.leastStep;
    }

    public void setLeastStep(int leastStep) {
        this.leastStep = leastStep;
    }

    public String toString() {
        return "MySedentaryReminder{startHour=" + this.startHour + ", startMinute=" + this.startMinute + ", endHour=" + this.endHour + ", endMinute=" + this.endMinute + ", week=" + this.week + ", intervalTime=" + this.intervalTime + ", leastStep=" + this.leastStep + ", enable=" + this.enable + '}';
    }
}
