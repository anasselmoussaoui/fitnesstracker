package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes.dex */
public class GetData extends SendData {
    public static final byte DataNum_Delete = -103;
    public static final int DataNum_Last = 0;
    int dataNum = 0;

    public int getDataNum() {
        return this.dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }
}
