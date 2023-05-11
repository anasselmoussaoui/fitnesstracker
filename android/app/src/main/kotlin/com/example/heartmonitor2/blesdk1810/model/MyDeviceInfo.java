package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes.dex */
public class MyDeviceInfo extends SendData {
    boolean ANCEState;
    boolean DistanceUnit;
    boolean HandleState;
    boolean RainHandEnable;
    boolean ScreenState;
    boolean ShowHour;
    int baseHeart;
    int brightnessLevel;
    boolean isUnitC;

    public boolean isUnitC() {
        return this.isUnitC;
    }

    public void setUnitC(boolean unitC) {
        this.isUnitC = unitC;
    }

    public int getBrightnessLevel() {
        return this.brightnessLevel;
    }

    public void setBrightnessLevel(int brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public int getBaseHeart() {
        return this.baseHeart;
    }

    public void setBaseHeart(int baseHeart) {
        this.baseHeart = baseHeart;
    }

    public boolean isDistanceUnit() {
        return this.DistanceUnit;
    }

    public void setDistanceUnit(boolean distanceUnit) {
        this.DistanceUnit = distanceUnit;
    }

    public boolean isShowHour() {
        return this.ShowHour;
    }

    public void setShowHour(boolean showHour) {
        this.ShowHour = showHour;
    }

    public boolean isRainHandEnable() {
        return this.RainHandEnable;
    }

    public void setRainHandEnable(boolean rainHandEnable) {
        this.RainHandEnable = rainHandEnable;
    }

    public boolean isHandleState() {
        return this.HandleState;
    }

    public void setHandleState(boolean handleState) {
        this.HandleState = handleState;
    }

    public boolean isScreenState() {
        return this.ScreenState;
    }

    public void setScreenState(boolean screenState) {
        this.ScreenState = screenState;
    }

    public boolean isANCEState() {
        return this.ANCEState;
    }

    public void setANCEState(boolean ANCEState) {
        this.ANCEState = ANCEState;
    }

    public String toString() {
        return "MyDeviceInfo{DistanceUnit=" + this.DistanceUnit + ", ShowHour=" + this.ShowHour + ", RainHandEnable=" + this.RainHandEnable + ", HandleState=" + this.HandleState + ", ScreenState=" + this.ScreenState + ", ANCEState=" + this.ANCEState + ", baseHeart=" + this.baseHeart + ", brightnessLevel=" + this.brightnessLevel + '}';
    }
}
