package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes6.dex */
public class Notifier extends SendData {
    public static final int Data_Facebook = 3;
    public static final int Data_Sms = 2;
    public static final int Data_Stop_Tel = 255;
    public static final int Data_Tel = 0;
    public static final int Data_Telegra = 4;
    public static final int Data_Twitter = 7;
    public static final int Data_Vk = 8;
    public static final int Data_WeChat = 1;
    public static final int Data_WhatApp = 9;
    String info;
    int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        return "Notifier{type=" + this.type + ", info='" + this.info + "'}";
    }
}
