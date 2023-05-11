package com.example.heartmonitor2.blesdk1810.cmdenum;

import java.io.Serializable;

/* loaded from: classes8.dex */
public class YhDeviceData implements Serializable {
    String data = "";
    boolean dataEnd = false;
    int dataType;

    public boolean isDataEnd() {
        return this.dataEnd;
    }

    public void setDataEnd(boolean dataEnd) {
        this.dataEnd = dataEnd;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return this.data == null ? "" : this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return "YhDeviceData{dataType=" + this.dataType + ", data=" + this.data + ", dataEnd=" + this.dataEnd + '}';
    }
}
