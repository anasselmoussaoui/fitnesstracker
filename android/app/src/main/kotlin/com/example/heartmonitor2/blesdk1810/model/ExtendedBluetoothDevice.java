package com.example.heartmonitor2.blesdk1810.model;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes8.dex */
public class ExtendedBluetoothDevice {
    public static final int NO_RSSI = -1000;
    public final BluetoothDevice device;
    public String name;
    public int rssi;

    public ExtendedBluetoothDevice(BluetoothDevice device, String name, int rssi) {
        this.device = device;
        this.name = name;
        this.rssi = rssi;
    }

    public ExtendedBluetoothDevice(BluetoothDevice device) {
        this.device = device;
        this.name = device.getName();
        this.rssi = -1000;
    }

    public boolean matches(BluetoothDevice mdevice) {
        return this.device.getAddress().equals(mdevice.getAddress());
    }
}
