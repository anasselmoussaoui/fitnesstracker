package com.example.heartmonitor2.blesdk1810.Util;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import com.example.heartmonitor2.blesdk1810.constant.BleConst;
import com.example.heartmonitor2.blesdk1810.constant.DeviceConst;
import com.example.heartmonitor2.blesdk1810.constant.DeviceKey;
import com.example.heartmonitor2.blesdk1810.model.DeviceBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class ResolveUtil {
    private static final String TAG = "ResolveUtil";

    public static void crcValue(byte[] value) {
        byte crc = 0;
        for (int i = 0; i < value.length - 1; i++) {
            crc = (byte) (value[i] + crc);
        }
        int i2 = value.length;
        value[i2 - 1] = (byte) (crc & 255);
    }

    public static byte getTimeValue(int value) {
        String data = value + "";
        Integer m = Integer.valueOf(Integer.parseInt(data, 16));
        return (byte) m.intValue();
    }

    public static Map<String, Object> getDeviceTime(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceTime);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[1]) + "-" + ByteToHexString(value[2]) + "-" + ByteToHexString(value[3]) + " " + ByteToHexString(value[4]) + ":" + ByteToHexString(value[5]) + ":" + ByteToHexString(value[6]);
        String gpsDate = ByteToHexString(value[9]) + "." + ByteToHexString(value[10]) + "." + ByteToHexString(value[11]);
        mapData.put(DeviceKey.DeviceTime, date);
        mapData.put(DeviceKey.GPSTime, gpsDate);
        maps.put(DeviceKey.Data, mapData);
        return maps;
    }

    public static String getGpsTime(byte[] value) {
        String gpsDate = ByteToHexString(value[9]) + "." + ByteToHexString(value[10]) + "." + ByteToHexString(value[11]);
        return gpsDate;
    }

    public static Map<String, Object> getUserInfo(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetPersonalInfo);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        String[] userInfo = new String[6];
        for (int i = 0; i < 5; i++) {
            userInfo[i] = String.valueOf(getValue(value[i + 1], 0));
        }
        String deviceId = "";
        for (int i2 = 6; i2 < 12; i2++) {
            if (value[i2] != 0) {
                deviceId = deviceId + ((char) getValue(value[i2], 0));
            }
        }
        userInfo[5] = deviceId;
        mapData.put(DeviceKey.Gender, userInfo[0]);
        mapData.put(DeviceKey.Age, userInfo[1]);
        mapData.put(DeviceKey.Height, userInfo[2]);
        mapData.put(DeviceKey.Weight, userInfo[3]);
        mapData.put(DeviceKey.Stride, userInfo[4]);
        return maps;
    }

    public static Map<String, Object> getDeviceInfo(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceInfo);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        String[] userInfo = new String[8];
        for (int i = 0; i < 6; i++) {
            userInfo[i] = String.valueOf(getValue(value[i + 1], 0));
        }
        userInfo[6] = String.valueOf(getValue(value[9], 0));
        userInfo[7] = String.valueOf(getValue(value[11], 0));
        mapData.put(DeviceKey.DistanceUnit, userInfo[0]);
        mapData.put(DeviceKey.TimeUnit, userInfo[1]);
        mapData.put(DeviceKey.WristOn, userInfo[2]);
        mapData.put(DeviceKey.LeftOrRight, userInfo[3]);
        mapData.put(DeviceKey.ANCS, userInfo[5]);
        mapData.put(DeviceKey.KBaseHeart, userInfo[6]);
        mapData.put(DeviceKey.Brightness, userInfo[7]);
        mapData.put(DeviceKey.TempUnit, String.valueOf(getValue(value[12], 0)));
        return maps;
    }

    public static Map<String, Object> getActivityData(byte[] value) {
        int i;
        int i2;
        int i3;
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.RealTimeStep);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        String[] activityData = new String[6];
        float cal = 0.0f;
        float distance = 0.0f;
        int time = 0;
        int exerciseTime = 0;
        int step = 0;
        for (int step2 = 1; step2 < 5; step2++) {
            step += getValue(value[step2], step2 - 1);
        }
        int i4 = 5;
        while (true) {
            i = 9;
            if (i4 >= 9) {
                break;
            }
            cal += getValue(value[i4], i4 - 5);
            i4++;
        }
        while (true) {
            int i5 = i;
            i2 = 13;
            if (i5 >= 13) {
                break;
            }
            distance += getValue(value[i5], i5 - 9);
            i = i5 + 1;
        }
        while (true) {
            int i6 = i2;
            i3 = 17;
            if (i6 >= 17) {
                break;
            }
            time += getValue(value[i6], i6 - 13);
            i2 = i6 + 1;
        }
        while (true) {
            int i7 = i3;
            if (i7 < 21) {
                exerciseTime += getValue(value[i7], i7 - 17);
                i3 = i7 + 1;
            } else {
                int heart = getValue(value[21], 0);
                int temp = getValue(value[22], 0) + getValue(value[23], 1);
                NumberFormat numberFormat = getNumberFormat(1);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(cal / 100.0f));
                BigDecimal bigDecimaCal = bigDecimal.setScale(1, RoundingMode.HALF_DOWN);
                activityData[0] = String.valueOf(step);
                activityData[1] = bigDecimaCal.floatValue() + "";
                numberFormat.setMinimumFractionDigits(2);
                activityData[2] = numberFormat.format((double) (distance / 100.0f));
                activityData[3] = String.valueOf(time / 60);
                activityData[4] = String.valueOf(heart);
                activityData[5] = String.valueOf(exerciseTime);
                mapData.put(DeviceKey.Step, activityData[0]);
                mapData.put(DeviceKey.Calories, activityData[1]);
                mapData.put(DeviceKey.Distance, activityData[2]);
                mapData.put(DeviceKey.ExerciseMinutes, activityData[3]);
                mapData.put(DeviceKey.HeartRate, activityData[4]);
                mapData.put(DeviceKey.ActiveMinutes, activityData[5]);
                numberFormat.setMinimumFractionDigits(1);
                mapData.put(DeviceKey.TempData, numberFormat.format(temp * 0.1f));
                return maps;
            }
        }
    }

    public static Map<String, Object> getGoal(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetStepGoal);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        int goal = 0;
        for (int i = 0; i < 4; i++) {
            goal += getValue(value[i + 1], i);
        }
        mapData.put(DeviceKey.StepGoal, String.valueOf(goal));
        return maps;
    }

    public static Map<String, Object> getDeviceBattery(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceBatteryLevel);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        int battery = getValue(value[1], 0);
        mapData.put(DeviceKey.BatteryLevel, String.valueOf(battery));
        return maps;
    }

    public static Map<String, Object> getDeviceAddress(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceMacAddress);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 7; i++) {
            sb.append(String.format("%02X", Byte.valueOf(value[i])));
            sb.append(":");
        }
        String address = sb.toString();
        mapData.put(DeviceKey.MacAddress, address.substring(0, address.lastIndexOf(":")));
        return maps;
    }

    public static Map<String, Object> getDeviceVersion(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceVersion);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        StringBuffer stringBuffer = new StringBuffer();
        int i = 1;
        while (i < 5) {
            stringBuffer.append(String.format("%X", Byte.valueOf(value[i])));
            stringBuffer.append(i == 4 ? "" : ".");
            i++;
        }
        mapData.put(DeviceKey.DeviceVersion, stringBuffer.toString());
        return maps;
    }

    public static Map<String, Object> Reset() {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.CMD_Reset);
        maps.put(DeviceKey.End, true);
        return maps;
    }

    public static Map<String, Object> MCUReset() {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.CMD_MCUReset);
        maps.put(DeviceKey.End, true);
        return maps;
    }

    public static Map<String, Object> Notify() {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.Notify);
        maps.put(DeviceKey.End, true);
        return maps;
    }

    public static Map<String, Object> getDeviceName(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDeviceName);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        String name = "";
        for (int i = 1; i < 15; i++) {
            int charValue = getValue(value[i], 0);
            if (charValue != 0 && charValue <= 127) {
                name = name + ((char) charValue);
            }
        }
        mapData.put("deviceName", name);
        return maps;
    }

    public static String getByteString(byte b) {
        byte[] array = new byte[8];
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i <= 6) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
            stringBuffer.append(String.valueOf((int) array[i]));
            stringBuffer.append(i == 6 ? "" : "-");
            i++;
        }
        return stringBuffer.toString();
    }

    public static String getByteArray(byte b) {
        byte[] array = new byte[8];
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <= 7; i++) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
            stringBuffer.append(String.valueOf((int) array[i]));
        }
        return stringBuffer.toString();
    }

    public static Map<String, Object> getAutoHeart(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetAutomaticHRMonitoring);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        int enable = getValue(value[1], 0);
        String startHour = ByteToHexString(value[2]);
        String startMin = ByteToHexString(value[3]);
        String stopHour = ByteToHexString(value[4]);
        String stopMin = ByteToHexString(value[5]);
        String week = getByteString(value[6]);
        int time = getValue(value[7], 0) + getValue(value[8], 1);
        String[] autoHeart = {String.valueOf(enable), startHour, startMin, stopHour, stopMin, week, String.valueOf(time)};
        mapData.put(DeviceKey.WorkMode, autoHeart[0]);
        mapData.put(DeviceKey.StartTime, autoHeart[1]);
        mapData.put(DeviceKey.KHeartStartMinter, autoHeart[2]);
        mapData.put(DeviceKey.EndTime, autoHeart[3]);
        mapData.put(DeviceKey.KHeartEndMinter, autoHeart[4]);
        mapData.put("weekValue", autoHeart[5]);
        mapData.put(DeviceKey.IntervalTime, autoHeart[6]);
        return maps;
    }

    public static Map<String, Object> getActivityAlarm(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetSedentaryReminder);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        String startHour = ByteToHexString(value[1]);
        String startMin = ByteToHexString(value[2]);
        String stopHour = ByteToHexString(value[3]);
        String stopMin = ByteToHexString(value[4]);
        String week = getByteString(value[5]);
        int time = getValue(value[6], 0);
        int step = getValue(value[7], 0);
        String[] activityAlarm = {startHour, startMin, stopHour, stopMin, week, String.valueOf(time), String.valueOf(step)};
        mapData.put(DeviceKey.StartTimeHour, activityAlarm[0]);
        mapData.put(DeviceKey.StartTimeMin, activityAlarm[1]);
        mapData.put(DeviceKey.EndTimeHour, activityAlarm[2]);
        mapData.put(DeviceKey.EndTimeMin, activityAlarm[3]);
        mapData.put("weekValue", activityAlarm[4]);
        mapData.put(DeviceKey.IntervalTime, activityAlarm[5]);
        mapData.put(DeviceKey.LeastSteps, activityAlarm[6]);
        return maps;
    }

    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v9 */
    public static Map<String, Object> getTotalStepData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetTotalActivityData);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int count = getStepCount(value);
        int length = value.length;
        int size = length / count;
        ?? r7 = 1;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        int i2 = 2;
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        int i3 = 0;
        while (i3 < size) {
            int flag = ((i3 + 1) * count) + r7;
            if (flag < length && value[flag] == -1) {
                maps.put(DeviceKey.End, Boolean.valueOf((boolean) r7));
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[(i3 * count) + i2]) + "." + ByteToHexString(value[(i3 * count) + 3]) + "." + ByteToHexString(value[(i3 * count) + 4]);
            int time = 0;
            float cal = 0.0f;
            float distance = 0.0f;
            int step = 0;
            for (int step2 = 0; step2 < 4; step2++) {
                step += getValue(value[step2 + 5 + (i3 * count)], step2);
            }
            for (int j = 0; j < 4; j++) {
                time += getValue(value[j + 9 + (i3 * count)], j);
            }
            for (int j2 = 0; j2 < 4; j2++) {
                distance += getValue(value[j2 + 13 + (i3 * count)], j2);
            }
            for (int j3 = 0; j3 < 4; j3++) {
                cal += getValue(value[j3 + 17 + (i3 * count)], j3);
            }
            int goal = count == 26 ? getValue(value[(i3 * count) + 21], i) : getValue(value[(i3 * count) + 21], i) + getValue(value[(i3 * count) + 22], r7);
            int exerciseTime = 0;
            for (int exerciseTime2 = 0; exerciseTime2 < 4; exerciseTime2++) {
                exerciseTime += getValue(value[(count - 4) + exerciseTime2 + (i3 * count)], exerciseTime2);
            }
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.Step, String.valueOf(step));
            hashMap.put(DeviceKey.ExerciseMinutes, String.valueOf(time));
            hashMap.put(DeviceKey.Calories, numberFormat.format(cal / 100.0f));
            hashMap.put(DeviceKey.Distance, numberFormat.format(distance / 100.0f));
            hashMap.put(DeviceKey.Goal, String.valueOf(goal));
            hashMap.put(DeviceKey.ActiveMinutes, String.valueOf(exerciseTime));
            list.add(hashMap);
            i3++;
            i = 0;
            r7 = 1;
            i2 = 2;
        }
        return maps;
    }

    private static int getStepCount(byte[] value) {
        int length = value.length;
        if (length == 2) {
            return 27;
        }
        if (length % 26 == 0) {
            return 26;
        }
        if (length % 27 == 0) {
            return 27;
        }
        if ((length - 2) % 26 == 0) {
            return 26;
        }
        if ((length - 2) % 27 != 0) {
            return 27;
        }
        return 27;
    }

    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v11 */
    public static Map<String, Object> getDetailData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDetailActivityData);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int count = 25;
        int length = value.length;
        int size = length / 25;
        new DeviceBean();
        ?? r8 = 1;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        int i2 = 2;
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        int i3 = 0;
        while (i3 < size) {
            Map<String, String> hashMap = new HashMap<>();
            int i4 = ((i3 + 1) * count) + r8;
            if (value[length - 1] == -1) {
                maps.put(DeviceKey.End, Boolean.valueOf((boolean) r8));
            }
            String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[(i3 * 25) + 3]) + "." + ByteToHexString(value[(i3 * 25) + 4]) + "." + ByteToHexString(value[(i3 * 25) + 5]) + " " + ByteToHexString(value[(i3 * 25) + 6]) + ":" + ByteToHexString(value[(i3 * 25) + 7]) + ":" + ByteToHexString(value[(i3 * 25) + 8]);
            float cal = 0.0f;
            float distance = 0.0f;
            StringBuffer stringBuffer = new StringBuffer();
            int step = 0;
            for (int step2 = 0; step2 < i2; step2++) {
                step += getValue(value[step2 + 9 + (i3 * 25)], step2);
            }
            for (int j = 0; j < i2; j++) {
                cal += getValue(value[j + 11 + (i3 * 25)], j);
            }
            for (int j2 = 0; j2 < i2; j2++) {
                distance += getValue(value[j2 + 13 + (i3 * 25)], j2);
            }
            int j3 = 0;
            while (j3 < 10) {
                StringBuffer stringBuffer2 = stringBuffer;
                stringBuffer2.append(String.valueOf(getValue(value[j3 + 15 + (i3 * 25)], i)));
                stringBuffer2.append(j3 == 9 ? "" : " ");
                j3++;
                stringBuffer = stringBuffer2;
                i = 0;
            }
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.KDetailMinterStep, String.valueOf(step));
            hashMap.put(DeviceKey.Calories, numberFormat.format(cal / 100.0f));
            hashMap.put(DeviceKey.Distance, numberFormat.format(distance / 100.0f));
            hashMap.put(DeviceKey.ArraySteps, stringBuffer.toString());
            list.add(hashMap);
            i3++;
            count = count;
            length = length;
            size = size;
            i = 0;
            r8 = 1;
            i2 = 2;
        }
        return maps;
    }

    public static Map<String, Object> getSleepData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDetailSleepData);
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int length = value.length;
        int size = length / 34;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        for (int i = 0; i < size; i++) {
            int flag = ((i + 1) * 34) + 1;
            if (flag < length && value[flag] == -1) {
                maps.put(DeviceKey.End, true);
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = ByteToHexString(value[(i * 34) + 3]) + "." + ByteToHexString(value[(i * 34) + 4]) + "." + ByteToHexString(value[(i * 34) + 5]) + " " + ByteToHexString(value[(i * 34) + 6]) + ":" + ByteToHexString(value[(i * 34) + 7]) + ":" + ByteToHexString(value[(i * 34) + 8]);
            hashMap.put("arrayDetailSleepData", date);
            int sleepLength = getValue(value[(i * 34) + 9], 0);
            StringBuffer stringBuffer = new StringBuffer();
            int j = 0;
            while (j < sleepLength) {
                stringBuffer.append(String.valueOf(getValue(value[j + 10 + (i * 34)], 0)));
                stringBuffer.append(j == sleepLength ? "" : " ");
                j++;
            }
            hashMap.put(DeviceKey.ArraySleep, stringBuffer.toString());
            list.add(hashMap);
        }
        return maps;
    }

    public static Map<String, Object> getHeartData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetDynamicHR);
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int length = value.length;
        int size = length / 24;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        for (int i = 0; i < size; i++) {
            int flag = ((i + 1) * 24) + 1;
            if (flag < length && value[flag] == -1) {
                maps.put(DeviceKey.End, true);
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[(i * 24) + 3]) + "." + ByteToHexString(value[(i * 24) + 4]) + "." + ByteToHexString(value[(i * 24) + 5]) + " " + ByteToHexString(value[(i * 24) + 6]) + ":" + ByteToHexString(value[(i * 24) + 7]) + ":" + ByteToHexString(value[(i * 24) + 8]);
            StringBuffer stringBuffer = new StringBuffer();
            int j = 0;
            while (j < 15) {
                stringBuffer.append(String.valueOf(getValue(value[j + 9 + (i * 24)], 0)));
                stringBuffer.append(j == 14 ? "" : " ");
                j++;
            }
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.ArrayDynamicHR, stringBuffer.toString());
            list.add(hashMap);
        }
        return maps;
    }

    public static Map<String, Object> getTempData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetTempHistoryData);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int count = 11;
        int length = value.length;
        int size = length / 11;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        NumberFormat numberFormat = getNumberFormat(1);
        int i2 = 0;
        while (i2 < size) {
            int flag = ((i2 + 1) * count) + 1;
            if (flag < length && value[flag] == -1) {
                maps.put(DeviceKey.End, true);
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = ByteToHexString(value[(i2 * count) + 3]) + "." + ByteToHexString(value[(i2 * count) + 4]) + "." + ByteToHexString(value[(i2 * count) + 5]) + " " + ByteToHexString(value[(i2 * count) + 6]) + ":" + ByteToHexString(value[(i2 * count) + 7]) + ":" + ByteToHexString(value[(i2 * count) + 8]);
            int tempValue = getValue(value[(i2 * count) + 9], i) + getValue(value[(i2 * count) + 10], 1);
            hashMap.put("strDate", date);
            hashMap.put(DeviceKey.TempData, numberFormat.format(tempValue * 0.1f));
            list.add(hashMap);
            i2++;
            count = count;
            i = 0;
        }
        return maps;
    }

    public static Map<String, Object> getOnceHeartData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetStaticHR);
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int length = value.length;
        int size = length / 10;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        for (int i = 0; i < size; i++) {
            int i2 = ((i + 1) * 10) + 1;
            if (value[length - 1] == -1) {
                maps.put(DeviceKey.End, true);
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[(i * 10) + 3]) + "." + ByteToHexString(value[(i * 10) + 4]) + "." + ByteToHexString(value[(i * 10) + 5]) + " " + ByteToHexString(value[(i * 10) + 6]) + ":" + ByteToHexString(value[(i * 10) + 7]) + ":" + ByteToHexString(value[(i * 10) + 8]);
            String heart = String.valueOf(getValue(value[(i * 10) + 9], 0));
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.StaticHR, heart);
            list.add(hashMap);
        }
        return maps;
    }

    public static Map<String, Object> getHrvTestData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetHRVData);
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int length = value.length;
        int size = length / 15;
        if (size != 0) {
            if (value[value.length - 1] == -1) {
                maps.put(DeviceKey.End, true);
            }
            for (int i = 0; i < size; i++) {
                Map<String, String> hashMap = new HashMap<>();
                String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[(i * 15) + 3]) + "." + ByteToHexString(value[(i * 15) + 4]) + "." + ByteToHexString(value[(i * 15) + 5]) + " " + ByteToHexString(value[(i * 15) + 6]) + ":" + ByteToHexString(value[(i * 15) + 7]) + ":" + ByteToHexString(value[(i * 15) + 8]);
                String hrv = String.valueOf(getValue(value[(i * 15) + 9], 0));
                String Tired = String.valueOf(getValue(value[(i * 15) + 12], 0));
                hashMap.put(DeviceKey.Date, date);
                hashMap.put(DeviceKey.HRV, hrv);
                hashMap.put(DeviceKey.Stress, Tired);
                list.add(hashMap);
            }
            return maps;
        }
        maps.put(DeviceKey.End, true);
        return maps;
    }

    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v3 */
    public static Map<String, Object> getClockData(byte[] value) {
        byte[] bArr = value;
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetAlarmClock);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int count = 41;
        int length = bArr.length;
        int size = length / 41;
        new DeviceBean();
        ?? r8 = 1;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        int i2 = 0;
        while (i2 < size) {
            int flag = ((i2 + 1) * count) + r8;
            if (flag < length && bArr[flag] == -1) {
                maps.put(DeviceKey.End, Boolean.valueOf((boolean) r8));
            }
            Map<String, String> hashMap = new HashMap<>();
            String id = String.valueOf(getValue(bArr[(i2 * count) + 4], i));
            String enable = String.valueOf(getValue(bArr[(i2 * count) + 5], i));
            String type = String.valueOf(getValue(bArr[(i2 * count) + 6], i));
            String hour = ByteToHexString(bArr[(i2 * count) + 7]);
            String min = ByteToHexString(bArr[(i2 * count) + 8]);
            String week = getByteString(bArr[(i2 * count) + 9]);
            int count2 = count;
            int lengthS = getValue(bArr[(i2 * count) + 10], 0);
            hashMap.put(DeviceKey.KAlarmId, id);
            hashMap.put(DeviceKey.OpenOrClose, enable);
            hashMap.put(DeviceKey.ClockType, type);
            hashMap.put(DeviceKey.ClockTime, hour);
            hashMap.put(DeviceKey.KAlarmMinter, min);
            hashMap.put("weekValue", week);
            String week2 = String.valueOf(lengthS);
            hashMap.put(DeviceKey.KAlarmLength, week2);
            list.add(hashMap);
            i2++;
            count = count2;
            bArr = value;
            i = 0;
            r8 = 1;
        }
        return maps;
    }

    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v4 */
    public static Map<String, Object> getHistoryGpsData(byte[] value) {
        byte[] bArr = value;
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetGPSData);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list = new ArrayList<>();
        maps.put(DeviceKey.Data, list);
        int count = 59;
        int length = bArr.length;
        int size = length / 59;
        ?? r7 = 1;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        int i2 = 0;
        while (i2 < size) {
            int flag = ((i2 + 1) * count) + r7;
            if (flag < length && i2 == size - 1 && bArr[flag] == -1) {
                maps.put(DeviceKey.End, Boolean.valueOf((boolean) r7));
            }
            Map<String, String> hashMap = new HashMap<>();
            int id = getValue(bArr[(i2 * count) + r7], i) + getValue(bArr[(i2 * count) + 2], r7);
            StringBuilder sb = new StringBuilder();
            sb.append(BleConst.SetAlarmClockWithAllClock);
            sb.append(ByteToHexString(bArr[(i2 * count) + 3]));
            sb.append(".");
            int i3 = 4;
            sb.append(ByteToHexString(bArr[(i2 * count) + 4]));
            sb.append(".");
            sb.append(ByteToHexString(bArr[(i2 * count) + 5]));
            sb.append(" ");
            int i4 = 6;
            sb.append(ByteToHexString(bArr[(i2 * count) + 6]));
            sb.append(":");
            sb.append(ByteToHexString(bArr[(i2 * count) + 7]));
            sb.append(":");
            sb.append(ByteToHexString(bArr[(i2 * count) + 8]));
            String date = sb.toString();
            byte[] valueLatitude = new byte[4];
            byte[] valueLongitude = new byte[4];
            StringBuffer stringBufferLatitude = new StringBuffer();
            StringBuffer stringBufferLongitude = new StringBuffer();
            int k = 0;
            while (true) {
                int k2 = k;
                if (k2 < i4) {
                    int j = 0;
                    while (true) {
                        int j2 = j;
                        if (j2 >= i3) {
                            break;
                        }
                        valueLatitude[3 - j2] = bArr[j2 + 9 + (i2 * count) + (k2 * 8)];
                        valueLongitude[3 - j2] = bArr[j2 + 13 + (i2 * count) + (k2 * 8)];
                        j = j2 + 1;
                    }
                    String Latitude = String.valueOf(getFloat(valueLatitude, 0));
                    String Longitude = String.valueOf(getFloat(valueLongitude, 0));
                    StringBuffer stringBufferLatitude2 = stringBufferLatitude;
                    stringBufferLatitude2.append(Latitude);
                    int count2 = count;
                    stringBufferLatitude2.append(k2 == 5 ? "" : ",");
                    StringBuffer stringBufferLongitude2 = stringBufferLongitude;
                    stringBufferLongitude2.append(Longitude);
                    int length2 = length;
                    stringBufferLongitude2.append(k2 == 5 ? "" : ",");
                    k = k2 + 1;
                    stringBufferLatitude = stringBufferLatitude2;
                    stringBufferLongitude = stringBufferLongitude2;
                    count = count2;
                    length = length2;
                    bArr = value;
                    i4 = 6;
                    i3 = 4;
                }
            }
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.KDataID, String.valueOf(id));
            hashMap.put(DeviceKey.Latitude, stringBufferLatitude.toString());
            hashMap.put(DeviceKey.Longitude, stringBufferLongitude.toString());
            list.add(hashMap);
            i2++;
            count = count;
            length = length;
            bArr = value;
            i = 0;
            r7 = 1;
        }
        return maps;
    }

    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v6 */
    public static Map<String, Object> getExerciseData(byte[] value) {
        List<Map<String, String>> list;
        NumberFormat numberFormatCal;
        byte[] bArr = value;
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.GetActivityModeData);
        int i = 0;
        maps.put(DeviceKey.End, false);
        List<Map<String, String>> list2 = new ArrayList<>();
        maps.put(DeviceKey.Data, list2);
        int count = 25;
        int length = bArr.length;
        int size = length / 25;
        ?? r7 = 1;
        if (size == 0) {
            maps.put(DeviceKey.End, true);
            return maps;
        }
        NumberFormat numberFormatCal2 = getNumberFormat(1);
        int i2 = 2;
        NumberFormat numberFormat = getNumberFormat(2);
        int i3 = 0;
        while (i3 < size) {
            int flag = ((i3 + 1) * count) + r7;
            if (flag < length && i3 == size - 1 && bArr[flag] == -1) {
                maps.put(DeviceKey.End, Boolean.valueOf((boolean) r7));
            }
            Map<String, String> hashMap = new HashMap<>();
            String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(bArr[(i3 * count) + 3]) + "." + ByteToHexString(bArr[(i3 * count) + 4]) + "." + ByteToHexString(bArr[(i3 * count) + 5]) + " " + ByteToHexString(bArr[(i3 * count) + 6]) + ":" + ByteToHexString(bArr[(i3 * count) + 7]) + ":" + ByteToHexString(bArr[(i3 * count) + 8]);
            String mode = String.valueOf(getValue(bArr[(i3 * count) + 9], i));
            String heartRate = String.valueOf(getValue(bArr[(i3 * count) + 10], i));
            int periodTime = getData(i2, (i3 * count) + 11, bArr);
            int length2 = length;
            int steps = getData(i2, (i3 * count) + 13, bArr);
            int size2 = size;
            int speedMin = getValue(bArr[(i3 * count) + 15], 0);
            int speedS = getValue(bArr[(i3 * count) + 16], 0);
            Map<String, Object> maps2 = maps;
            int i4 = 4;
            byte[] valueCal = new byte[4];
            int j = 0;
            while (true) {
                int j2 = j;
                list = list2;
                if (j2 >= i4) {
                    break;
                }
                valueCal[3 - j2] = bArr[j2 + 17 + (i3 * count)];
                j = j2 + 1;
                list2 = list;
                i4 = 4;
            }
            byte[] valueDistance = new byte[4];
            int j3 = 0;
            while (true) {
                int j4 = j3;
                numberFormatCal = numberFormatCal2;
                if (j4 < 4) {
                    valueDistance[3 - j4] = bArr[j4 + 21 + (i3 * count)];
                    j3 = j4 + 1;
                    numberFormatCal2 = numberFormatCal;
                }
            }
            float cal = getFloat(valueCal, 0);
            float distance = getFloat(valueDistance, 0);
            hashMap.put(DeviceKey.Date, date);
            hashMap.put(DeviceKey.ActivityMode, mode);
            hashMap.put(DeviceKey.HeartRate, heartRate);
            hashMap.put(DeviceKey.ActiveMinutes, String.valueOf(periodTime));
            hashMap.put(DeviceKey.Step, String.valueOf(steps));
            hashMap.put(DeviceKey.Pace, String.format("%02d", Integer.valueOf(speedMin)) + "'" + String.format("%02d", Integer.valueOf(speedS)) + "\"");
            hashMap.put(DeviceKey.Distance, numberFormat.format((double) distance));
            hashMap.put(DeviceKey.Calories, numberFormatCal.format((double) cal));
            list.add(hashMap);
            i3++;
            list2 = list;
            numberFormatCal2 = numberFormatCal;
            length = length2;
            size = size2;
            maps = maps2;
            count = count;
            bArr = value;
            i = 0;
            r7 = 1;
            i2 = 2;
        }
        return maps;
    }

    public static Map<String, Object> getActivityExerciseData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.EnterActivityMode);
        maps.put(DeviceKey.End, true);
        Map<String, String> map = new HashMap<>();
        maps.put(DeviceKey.Data, map);
        int heartRate = getValue(value[1], 0);
        int steps = 0;
        for (int steps2 = 0; steps2 < 4; steps2++) {
            steps += getValue(value[steps2 + 2], steps2);
        }
        byte[] valueCal = new byte[4];
        for (int i = 0; i < 4; i++) {
            valueCal[3 - i] = value[i + 6];
        }
        float kcal = getFloat(valueCal, 0);
        map.put(DeviceKey.HeartRate, heartRate == 255 ? BleConst.SetDeviceTime : String.valueOf(heartRate));
        map.put(DeviceKey.Step, String.valueOf(steps));
        map.put(DeviceKey.Calories, String.valueOf(kcal));
        return maps;
    }

    public static Map<String, Object> setTimeSuccessful() {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.SetDeviceTime);
        maps.put(DeviceKey.End, true);
        Map<String, String> map = new HashMap<>();
        maps.put(DeviceKey.Data, map);
        return maps;
    }

    public static Map<String, Object> setMacSuccessful() {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.CMD_Set_Mac);
        maps.put(DeviceKey.End, true);
        Map<String, String> map = new HashMap<>();
        maps.put(DeviceKey.Data, map);
        return maps;
    }

    public static Map<String, Object> function(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.End, true);
        Map<String, String> map = new HashMap<>();
        maps.put(DeviceKey.Data, map);
        if (value[1] == 2) {
            maps.put(DeviceKey.DataType, "TakePhotoMode");
        }
        if (value[1] == 4) {
            maps.put(DeviceKey.DataType, "FindMobilePhoneMode");
        }
        if (value[1] == 1 && value[2] == 0) {
            maps.put(DeviceKey.DataType, "RejectTelMode");
        }
        if (value[1] == 1 && value[2] == 1) {
            maps.put(DeviceKey.DataType, "TelMode");
        }
        return maps;
    }

    public static Map<String, Object> ECGQuality(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.End, true);
        maps.put(DeviceKey.DataType, BleConst.ECGQuality);
        maps.put(DeviceKey.Data, mapData);
        int heartValue = getValue(value[1], 0);
        int hrvValue = getValue(value[2], 0);
        int Quality = getValue(value[3], 0);
        mapData.put(DeviceKey.HeartRate, String.valueOf(heartValue));
        mapData.put(DeviceKey.HRV, String.valueOf(hrvValue));
        mapData.put(DeviceKey.ECGQualityValue, String.valueOf(Quality));
        return maps;
    }

    public static Map<String, Object> ECGData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, byte[]> mapData = new HashMap<>();
        maps.put(DeviceKey.End, true);
        maps.put(DeviceKey.DataType, BleConst.ECGDATA);
        maps.put(DeviceKey.Data, mapData);
        mapData.put(DeviceKey.ECGValue, value);
        return maps;
    }

    public static Map<String, Object> PPGData(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, byte[]> mapData = new HashMap<>();
        maps.put(DeviceKey.End, true);
        maps.put(DeviceKey.DataType, BleConst.PPGDATA);
        maps.put(DeviceKey.Data, mapData);
        mapData.put(DeviceKey.PPGValue, value);
        return maps;
    }

    public static Map<String, Object> ECGResult(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.End, true);
        maps.put(DeviceKey.DataType, BleConst.ECGResult);
        maps.put(DeviceKey.Data, mapData);
        int resultValue = getValue(value[1], 0);
        String date = BleConst.SetAlarmClockWithAllClock + ByteToHexString(value[10]) + "." + ByteToHexString(value[11]) + "." + ByteToHexString(value[12]) + " " + ByteToHexString(value[13]) + ":" + ByteToHexString(value[14]) + ":" + ByteToHexString(value[15]);
        int hrv = getValue(value[2], 0);
        int avBlock = getValue(value[3], 0);
        int hr = getValue(value[4], 0);
        int strees = getValue(value[5], 0);
        int highBp = getValue(value[6], 0);
        int lowBp = getValue(value[7], 0);
        int moodValue = getValue(value[8], 0);
        int breathValue = getValue(value[9], 0);
        mapData.put(DeviceKey.ECGResultValue, String.valueOf(resultValue));
        mapData.put(DeviceKey.Date, date);
        mapData.put(DeviceKey.ECGHrvValue, String.valueOf(hrv));
        mapData.put(DeviceKey.ECGAvBlockValue, String.valueOf(avBlock));
        mapData.put(DeviceKey.ECGHrValue, String.valueOf(hr));
        mapData.put(DeviceKey.ECGStreesValue, String.valueOf(strees));
        mapData.put(DeviceKey.ECGhighBpValue, String.valueOf(highBp));
        mapData.put(DeviceKey.ECGLowBpValue, String.valueOf(lowBp));
        mapData.put(DeviceKey.ECGMoodValue, String.valueOf(moodValue));
        mapData.put(DeviceKey.ECGBreathValue, String.valueOf(breathValue));
        return maps;
    }

    public static Map<String, Object> enterEcg(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.End, true);
        maps.put(DeviceKey.DataType, BleConst.ENTERECG);
        maps.put(DeviceKey.Data, mapData);
        return maps;
    }

    public static Map<String, Object> updateClockSuccessful(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.SetAlarmClockWithAllClock);
        maps.put(DeviceKey.End, true);
        Map<String, String> map = new HashMap<>();
        maps.put(DeviceKey.Data, map);
        map.put(DeviceKey.KClockLast, String.valueOf(getValue(value[value.length - 1], 0)));
        return maps;
    }

    public static int getData(int length, int start, byte[] value) {
        int data = 0;
        for (int j = 0; j < length; j++) {
            data += getValue(value[j + start], j);
        }
        return data;
    }

    public static int getValue(byte b, int count) {
        return (int) ((b & 255) * Math.pow(256.0d, count));
    }

    public static String ByteToHexString(byte a) {
        String s = Integer.toHexString(Byte.valueOf(a).intValue());
        if (s.length() == 1) {
            return BleConst.SetDeviceTime + s;
        }
        return s;
    }

    public static float getFloat(byte[] arr, int index) {
        return Float.intBitsToFloat(getInt(arr, index));
    }

    public static int getInt(byte[] arr, int index) {
        return ((arr[index + 0] << DeviceConst.CMD_HeartPackageFromDevice) & ViewCompat.MEASURED_STATE_MASK) | ((arr[index + 1] << DeviceConst.Exit_photo_mode) & 16711680) | ((arr[index + 2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (arr[index + 3] & 255);
    }

    public static NumberFormat getNumberFormat(int max) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(max);
        numberFormat.setGroupingUsed(false);
        return numberFormat;
    }

    public static String byte2Hex(byte data) {
        return String.format("%02X ", Byte.valueOf(data));
    }

    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, 0);
        Log.i(TAG, "getCurrentTimeZone: " + strTz);
        int _t = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000;
        String strTz2 = String.valueOf(_t);
        String strTz3 = "GMT" + strTz2;
        Log.i(TAG, "getCurrentTimeZone: " + strTz3);
        return strTz3;
    }

    public static Map<String, Object> setMethodSuccessful(String dataType) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, dataType);
        maps.put(DeviceKey.End, true);
        return maps;
    }

    public static Map<String, Object> GetTemperatureCorrectionValue(byte[] value) {
        Map<String, Object> maps = new HashMap<>();
        maps.put(DeviceKey.DataType, BleConst.CMD_Set_TemperatureCorrection);
        maps.put(DeviceKey.End, true);
        Map<String, String> mapData = new HashMap<>();
        maps.put(DeviceKey.Data, mapData);
        byte[] tempValue = {value[3], value[2]};
        int TemperatureCorrectionValue = BleSDK.byteArrayToInt(tempValue);
        mapData.put(DeviceKey.TemperatureCorrectionValue, String.valueOf(TemperatureCorrectionValue));
        return maps;
    }
}
