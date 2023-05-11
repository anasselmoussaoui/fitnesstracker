package com.example.heartmonitor2.blesdk1810.model;

import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class DeviceBean {
    List<Map<String, String>> dataList;
    boolean finish;

    public List<Map<String, String>> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    public boolean isFinish() {
        return this.finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
