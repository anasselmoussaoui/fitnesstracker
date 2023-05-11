package com.example.heartmonitor2.blesdk1810.Util;

import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.example.heartmonitor2.blesdk1810.callback.DataListener1810;
import com.example.heartmonitor2.blesdk1810.constant.BleConst;
import com.example.heartmonitor2.blesdk1810.constant.DeviceConst;
import com.example.heartmonitor2.blesdk1810.constant.DeviceKey;
import com.example.heartmonitor2.blesdk1810.model.Clock;
import com.example.heartmonitor2.blesdk1810.model.GetData;
import com.example.heartmonitor2.blesdk1810.model.MyAutomaticHRMonitoring;
import com.example.heartmonitor2.blesdk1810.model.MyDeviceInfo;
import com.example.heartmonitor2.blesdk1810.model.MyDeviceTime;
import com.example.heartmonitor2.blesdk1810.model.MyPersonalInfo;
import com.example.heartmonitor2.blesdk1810.model.MySedentaryReminder;
import com.example.heartmonitor2.blesdk1810.model.Notifier;
import com.example.heartmonitor2.blesdk1810.model.StepModel;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class BleSDK {
    public static final int DATA_DELETE = 99;
    public static final int DATA_READ_CONTINUE = 2;
    public static final int DATA_READ_START = 0;
    public static final byte DistanceMode_KM = Byte.MIN_VALUE;
    public static final byte DistanceMode_MILE = -127;
    public static final String TAG = "BleSDK";
    public static final byte TempUnit_C = Byte.MIN_VALUE;
    public static final byte TempUnit_F = -127;
    public static final byte TimeMode_12h = -127;
    public static final byte TimeMode_24h = Byte.MIN_VALUE;
    public static final byte WristOn_DisEnable = Byte.MIN_VALUE;
    public static final byte WristOn_Enable = -127;
    static boolean isEnterActivityMode = false;
    private static boolean isSettingSocial = false;

    public static byte[] deleteAllClock() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Clock;
        value[1] = GetData.DataNum_Delete;
        crcValue(value);
        return value;
    }

    public static byte[] SetTempUnit(byte unit) {
        byte[] value = new byte[16];
        value[0] = 3;
        value[12] = unit;
        crcValue(value);
        return value;
    }

    public static byte[] GetTemperatureCorrectionValue() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Set_TemperatureCorrection;
        value[1] = 0;
        crcValue(value);
        return value;
    }

    public static byte[] setClockData(List<Clock> clockList) {
        int size = clockList.size();
        byte[] totalValue = new byte[(39 * size) + 2];
        int i = 0;
        while (true) {
            int i2 = 1;
            if (i < clockList.size()) {
                Clock clock = clockList.get(i);
                byte[] value = new byte[39];
                String content = clock.getContent();
                byte[] infoValue = getInfoValue(content, 30);
                value[0] = DeviceConst.CMD_Set_Clock;
                value[1] = (byte) size;
                value[2] = (byte) clock.getNumber();
                value[3] = clock.isEnable() ? (byte) 1 : (byte) 0;
                value[4] = (byte) clock.getType();
                value[5] = ResolveUtil.getTimeValue(clock.getHour());
                value[6] = ResolveUtil.getTimeValue(clock.getMinute());
                value[7] = (byte) clock.getWeek();
                if (infoValue.length != 0) {
                    i2 = infoValue.length;
                }
                value[8] = (byte) i2;
                System.arraycopy(infoValue, 0, value, 9, infoValue.length);
                System.arraycopy(value, 0, totalValue, i * 39, value.length);
                i++;
            } else {
                Log.i(TAG, "setClockData: " + totalValue.length);
                totalValue[totalValue.length - 2] = DeviceConst.CMD_Set_Clock;
                totalValue[totalValue.length - 1] = -1;
                return totalValue;
            }
        }
    }

    public static byte[] EnterActivityMode(int activityMode, int WorkMode) {
        if (1 == WorkMode) {
            isEnterActivityMode = true;
        } else {
            isEnterActivityMode = false;
        }
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Start_EXERCISE;
        value[1] = (byte) WorkMode;
        value[2] = (byte) activityMode;
        crcValue(value);
        return value;
    }

    public static void DataParsingWithData(byte[] value, DataListener1810 dataListener) {
        new HashMap();
        switch (value[0]) {
            case -104:
                if (isSettingSocial) {
                    dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SocialdistanceSetting));
                    return;
                }
                Map<String, Object> mapsa = new HashMap<>();
                mapsa.put(DeviceKey.DataType, BleConst.SocialdistanceGetting);
                mapsa.put(DeviceKey.End, true);
                Map<String, String> mapll = new HashMap<>();
                int interval = ResolveUtil.getValue(value[2], 0);
                int duration = ResolveUtil.getValue(value[3], 0);
                mapll.put("scanInterval", interval + "");
                mapll.put("scanTime", duration + "");
                mapll.put("signalStrength", ((int) value[4]) + "");
                mapsa.put(DeviceKey.Data, mapll);
                dataListener.dataCallback(mapsa);
                return;
            case 1:
                dataListener.dataCallback(ResolveUtil.setTimeSuccessful());
                return;
            case 2:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetPersonalInfo));
                return;
            case 3:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetDeviceInfo));
                return;
            case 4:
                dataListener.dataCallback(ResolveUtil.getDeviceInfo(value));
                return;
            case 5:
                dataListener.dataCallback(ResolveUtil.setMacSuccessful());
                return;
            case 6:
                Map<String, Object> maps = new HashMap<>();
                Map<String, String> mapb = new HashMap<>();
                mapb.put("Type", value[1] == 0 ? "Manual" : "automatic");
                maps.put(DeviceKey.Data, mapb);
                maps.put(DeviceKey.DataType, BleConst.StartTakePhoto);
                maps.put(DeviceKey.End, true);
                dataListener.dataCallback(maps);
                return;
            case 9:
                dataListener.dataCallback(ResolveUtil.getActivityData(value));
                return;
            case 11:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetStepGoal));
                return;
            case 16:
                Map<String, Object> aaaa = new HashMap<>();
                aaaa.put(DeviceKey.DataType, BleConst.SocialdistanceGetting);
                aaaa.put(DeviceKey.End, true);
                dataListener.dataCallback(aaaa);
                return;
            case 18:
                dataListener.dataCallback(ResolveUtil.Reset());
                return;
            case 19:
                dataListener.dataCallback(ResolveUtil.getDeviceBattery(value));
                return;
            case 22:
                Map<String, Object> mapdd = new HashMap<>();
                Map<String, String> mapbb = new HashMap<>();
                mapbb.put("type", ((int) value[1]) + "");
                mapdd.put(DeviceKey.Data, mapbb);
                byte b = value[1];
                if (b == 2) {
                    mapdd.put(DeviceKey.DataType, BleConst.StartTakePhoto);
                } else if (b == 4) {
                    mapdd.put(DeviceKey.DataType, BleConst.StopTakePhoto);
                }
                mapdd.put(DeviceKey.End, true);
                dataListener.dataCallback(mapdd);
                return;
            case 24:
                dataListener.dataCallback(ResolveUtil.getActivityExerciseData(value));
                return;
            case 25:
            default:
                return;
            case 32:
                Map<String, Object> mapbbzcz = new HashMap<>();
                mapbbzcz.put(DeviceKey.DataType, BleConst.BackHomeView);
                mapbbzcz.put(DeviceKey.End, true);
                dataListener.dataCallback(mapbbzcz);
                return;
            case 34:
                dataListener.dataCallback(ResolveUtil.getDeviceAddress(value));
                return;
            case 35:
                dataListener.dataCallback(ResolveUtil.updateClockSuccessful(value));
                return;
            case 37:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetSedentaryReminder));
                return;
            case 38:
                dataListener.dataCallback(ResolveUtil.getActivityAlarm(value));
                return;
            case 39:
                dataListener.dataCallback(ResolveUtil.getDeviceVersion(value));
                return;
            case 42:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetAutomaticHRMonitoring));
                return;
            case 43:
                dataListener.dataCallback(ResolveUtil.getAutoHeart(value));
                return;
            case 46:
                dataListener.dataCallback(ResolveUtil.MCUReset());
                return;
            case 54:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetMotorVibrationWithTimes));
                return;
            case 56:
                if (value[2] != 0 && value[3] != 0) {
                    dataListener.dataCallback(ResolveUtil.GetTemperatureCorrectionValue(value));
                    return;
                }
                return;
            case 61:
                dataListener.dataCallback(ResolveUtil.setMethodSuccessful(BleConst.SetDeviceName));
                return;
            case 62:
                dataListener.dataCallback(ResolveUtil.getDeviceName(value));
                return;
            case 65:
                dataListener.dataCallback(ResolveUtil.getDeviceTime(value));
                return;
            case 66:
                dataListener.dataCallback(ResolveUtil.getUserInfo(value));
                return;
            case 75:
                dataListener.dataCallback(ResolveUtil.getGoal(value));
                return;
            case 77:
                dataListener.dataCallback(ResolveUtil.Notify());
                return;
            case 81:
                dataListener.dataCallback(ResolveUtil.getTotalStepData(value));
                return;
            case 82:
                dataListener.dataCallback(ResolveUtil.getDetailData(value));
                return;
            case 83:
                dataListener.dataCallback(ResolveUtil.getSleepData(value));
                return;
            case 84:
                dataListener.dataCallback(ResolveUtil.getHeartData(value));
                return;
            case 85:
                dataListener.dataCallback(ResolveUtil.getOnceHeartData(value));
                return;
            case 86:
                dataListener.dataCallback(ResolveUtil.getHrvTestData(value));
                return;
            case 87:
                dataListener.dataCallback(ResolveUtil.getClockData(value));
                return;
            case 92:
                dataListener.dataCallback(ResolveUtil.getExerciseData(value));
                return;
            case 96:
                dataListener.dataCallback(ResolveUtil.getTempData(value));
                return;
        }
    }

    public static byte[] GetDetailSleepDataWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_SleepData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] MotorVibrationWithTimes(int times) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Set_MOT_SIGN;
        value[1] = (byte) times;
        crcValue(value);
        return value;
    }

    public static byte[] setStepModel(StepModel stepModel) {
        byte[] value = new byte[16];
        return value;
    }

    public static byte[] RealTimeStep(boolean enable, boolean tempEnable) {
        byte[] value = new byte[16];
        value[0] = 9;
        value[1] = enable ? (byte) 1 : (byte) 0;
        value[2] = tempEnable ? (byte) 1 : (byte) 0;
        crcValue(value);
        return value;
    }

    public static byte[] stopGo() {
        byte[] value = new byte[16];
        value[0] = 9;
        value[1] = 0;
        crcValue(value);
        return value;
    }

    public static byte[] SetPersonalInfo(MyPersonalInfo info) {
        byte[] value = new byte[16];
        int male = info.getSex();
        int age = info.getAge();
        int height = info.getHeight();
        int weight = info.getWeight();
        int stepLength = info.getStepLength();
        value[0] = 2;
        value[1] = (byte) male;
        value[2] = (byte) age;
        value[3] = (byte) height;
        value[4] = (byte) weight;
        value[5] = (byte) stepLength;
        crcValue(value);
        return value;
    }

    public static byte[] GetPersonalInfo() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_GET_USERINFO;
        crcValue(value);
        return value;
    }

    public static byte[] SetDeviceTime(MyDeviceTime time) {
        byte zoneValue;
        byte[] value = new byte[16];
        String timeZone = ResolveUtil.getCurrentTimeZone();
        String zone = timeZone.substring(3);
        if (zone.contains("-")) {
            zoneValue = Byte.valueOf(String.valueOf(zone.replace("-", ""))).byteValue();
        } else {
            zoneValue = (byte) (Byte.valueOf(zone).byteValue() + 128);
        }
        int year = time.getYear();
        int month = time.getMonth();
        int day = time.getDay();
        int hour = time.getHour();
        int min = time.getMinute();
        int second = time.getSecond();
        value[0] = 1;
        value[1] = ResolveUtil.getTimeValue(year);
        value[2] = ResolveUtil.getTimeValue(month);
        value[3] = ResolveUtil.getTimeValue(day);
        value[4] = ResolveUtil.getTimeValue(hour);
        value[5] = ResolveUtil.getTimeValue(min);
        value[6] = ResolveUtil.getTimeValue(second);
        value[8] = zoneValue;
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceTime() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_GET_TIME;
        crcValue(value);
        return value;
    }

    public static byte[] GetDetailActivityDataWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_DetailData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] GetTotalActivityDataWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_TotalData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] enterOTA() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Start_Ota;
        crcValue(value);
        return value;
    }

    public static byte[] getTemperatureData(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_TempData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceVersion() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Version;
        crcValue(value);
        return value;
    }

    public static byte[] GetAlarmClock(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Clock;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] SetAlarmClockWithAllClock(Clock clock) {
        byte[] value = new byte[37];
        String content = clock.getContent();
        byte[] infoValue = getInfoValue(content, 30);
        value[0] = DeviceConst.CMD_Set_Clock;
        value[1] = (byte) clock.getNumber();
        value[2] = (byte) clock.getType();
        value[3] = ResolveUtil.getTimeValue(clock.getHour());
        value[4] = ResolveUtil.getTimeValue(clock.getMinute());
        value[5] = (byte) clock.getWeek();
        value[6] = (byte) infoValue.length;
        System.arraycopy(infoValue, 0, value, 7, infoValue.length);
        return value;
    }

    public static byte[] SetSedentaryReminder(MySedentaryReminder activityAlarm) {
        byte[] value = {DeviceConst.CMD_Set_ActivityAlarm, ResolveUtil.getTimeValue(activityAlarm.getStartHour()), ResolveUtil.getTimeValue(activityAlarm.getStartMinute()), ResolveUtil.getTimeValue(activityAlarm.getEndHour()), ResolveUtil.getTimeValue(activityAlarm.getEndMinute()), (byte) activityAlarm.getWeek(), (byte) activityAlarm.getIntervalTime(), (byte) (activityAlarm.getLeastStep() & 255), activityAlarm.isEnable() ? (byte) 1 : (byte) 0};
        crcValue(value);
        return value;
    }

    public static byte[] GetSedentaryReminder() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_ActivityAlarm;
        crcValue(value);
        return value;
    }

    public static byte[] Reset() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Reset;
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceMacAddress() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Address;
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceBatteryLevel() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_BatteryLevel;
        crcValue(value);
        return value;
    }

    public static byte[] MCUReset() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Mcu_Reset;
        crcValue(value);
        return value;
    }

    public static byte[] SetDeviceName(String strDeviceName) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Set_Name;
        int length = strDeviceName.length() <= 14 ? strDeviceName.length() : 14;
        for (int i = 0; i < length; i++) {
            value[i + 1] = (byte) strDeviceName.charAt(i);
        }
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceName() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Name;
        crcValue(value);
        return value;
    }

    public static byte[] SetDistanceUnit(byte unit) {
        byte[] value = new byte[16];
        value[0] = 3;
        value[1] = unit;
        crcValue(value);
        return value;
    }

    public static byte[] setWristOnEnable(boolean enable) {
        byte[] value = new byte[16];
        value[0] = 3;
        value[3] = enable ? (byte) -127 : Byte.MIN_VALUE;
        crcValue(value);
        return value;
    }

    public static byte[] SetTimeModeUnit(byte unit) {
        byte[] value = new byte[16];
        value[0] = 3;
        value[2] = unit;
        crcValue(value);
        return value;
    }

    public static byte[] GetHRVDataWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_HrvTestData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] SetAutomaticHRMonitoring(MyAutomaticHRMonitoring autoHeart) {
        int time = autoHeart.getTime();
        byte[] value = {DeviceConst.CMD_Set_AutoHeart, (byte) autoHeart.getOpen(), ResolveUtil.getTimeValue(autoHeart.getStartHour()), ResolveUtil.getTimeValue(autoHeart.getStartMinute()), ResolveUtil.getTimeValue(autoHeart.getEndHour()), ResolveUtil.getTimeValue(autoHeart.getEndMinute()), (byte) autoHeart.getWeek(), (byte) (time & 255), (byte) ((time >> 8) & 255)};
        crcValue(value);
        return value;
    }

    public static byte[] GetAutomaticHRMonitoring() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_AutoHeart;
        crcValue(value);
        return value;
    }

    public static byte[] GetActivityModeDataWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_SPORTData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] GetStaticHRWithMode(int mode) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_OnceHeartData;
        value[1] = (byte) mode;
        crcValue(value);
        return value;
    }

    public static byte[] GetDynamicHRWithMode(int Number) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_HeartData;
        value[1] = (byte) Number;
        crcValue(value);
        return value;
    }

    public static byte[] SetStepGoal(int stepGoal) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Set_Goal;
        value[4] = (byte) ((stepGoal >> 24) & 255);
        value[3] = (byte) ((stepGoal >> 16) & 255);
        value[2] = (byte) ((stepGoal >> 8) & 255);
        value[1] = (byte) (stepGoal & 255);
        crcValue(value);
        return value;
    }

    public static byte[] GetStepGoal() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Get_Goal;
        crcValue(value);
        return value;
    }

    public static byte[] setSocialSetting(int Interval, int duration, short rssi) {
        isSettingSocial = true;
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_SET_SOCIAL;
        value[1] = 1;
        value[2] = (byte) Interval;
        value[3] = (byte) duration;
        value[4] = (byte) rssi;
        crcValue(value);
        return value;
    }

    public static byte[] getSocialSetting() {
        isSettingSocial = false;
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_SET_SOCIAL;
        value[1] = 0;
        crcValue(value);
        return value;
    }

    public static byte[] SetDeviceID(String deviceId) {
        byte[] value = new byte[16];
        value[0] = 5;
        for (int i = 0; i < 6; i++) {
            value[i + 1] = (byte) deviceId.charAt(i);
        }
        crcValue(value);
        return value;
    }

    public static byte[] SetDeviceInfo(MyDeviceInfo deviceBaseParameter) {
        byte[] value = new byte[16];
        value[0] = 3;
        value[1] = (byte) (deviceBaseParameter.isDistanceUnit() ? 129 : 128);
        value[2] = (byte) (deviceBaseParameter.isShowHour() ? 129 : 128);
        value[3] = (byte) (deviceBaseParameter.isRainHandEnable() ? 129 : 128);
        value[4] = (byte) (deviceBaseParameter.isHandleState() ? 129 : 128);
        value[5] = (byte) (deviceBaseParameter.isScreenState() ? 129 : 128);
        value[9] = (byte) (deviceBaseParameter.getBaseHeart() + 128);
        value[11] = (byte) (deviceBaseParameter.getBrightnessLevel() + 128);
        crcValue(value);
        return value;
    }

    public static byte[] GetDeviceInfo() {
        byte[] value = new byte[16];
        value[0] = 4;
        crcValue(value);
        return value;
    }

    public static byte[] setNotifyData(Notifier sendData) {
        String info = sendData.getInfo();
        byte[] infoValue = TextUtils.isEmpty(info) ? new byte[1] : getInfoValue(info, 60);
        byte[] value = new byte[infoValue.length + 3];
        value[0] = DeviceConst.CMD_Notify;
        value[1] = sendData.getType() == 8 ? (byte) -1 : (byte) sendData.getType();
        value[2] = (byte) infoValue.length;
        System.arraycopy(infoValue, 0, value, 3, infoValue.length);
        return value;
    }

    public static byte[] SetTemperatureCorrectionValue(int tempValue) {
        byte[] value = new byte[16];
        value[0] = DeviceConst.CMD_Set_TemperatureCorrection;
        value[1] = 1;
        byte[] tempArray = intTobyte(tempValue);
        value[2] = tempArray[1];
        value[3] = tempArray[0];
        crcValue(value);
        return value;
    }

    public static byte[] intTobyte(int num) {
        return new byte[]{(byte) ((num >> 8) & 255), (byte) (num & 255)};
    }

    public static int byteArrayToInt(byte[] arr) {
        short targets = (short) ((arr[1] & 255) | ((arr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
        return targets;
    }

    public static byte[] SetHeartbeatPackets(int heartbeatPacketsInterval) {
        byte[] value = new byte[16];
        value[0] = 6;
        value[1] = (byte) heartbeatPacketsInterval;
        crcValue(value);
        return value;
    }

    public static byte[] EnterPhotoMode() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.Enter_photo_mode;
        crcValue(value);
        return value;
    }

    public static byte[] ExitPhotoMode() {
        byte[] value = new byte[16];
        value[0] = DeviceConst.Exit_photo_mode;
        crcValue(value);
        return value;
    }

    public static byte[] Float2ByteArray(float f) {
        int intbits = Float.floatToIntBits(f);
        return Float2ByteArray(intbits);
    }

    public static byte[] Float2ByteArray(int i) {
        byte[] b = {(byte) (i & 255), (byte) ((65280 & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) (((-16777216) & i) >> 24)};
        return b;
    }

    public static byte[] getInfoValue(String info, int maxLength) {
        byte[] nameBytes = null;
        try {
            nameBytes = info.getBytes("UTF-8");
            if (nameBytes.length >= maxLength) {
                byte[] real = new byte[maxLength];
                char[] chars = info.toCharArray();
                int length = 0;
                for (char c : chars) {
                    String s = String.valueOf(c);
                    byte[] nameB = s.getBytes("UTF-8");
                    if (nameB.length + length == maxLength) {
                        System.arraycopy(nameBytes, 0, real, 0, real.length);
                        return real;
                    } else if (nameB.length + length > maxLength) {
                        System.arraycopy(nameBytes, 0, real, 0, length);
                        return real;
                    } else {
                        length += nameB.length;
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nameBytes;
    }

    public static void crcValue(byte[] value) {
        byte crc = 0;
        for (int i = 0; i < value.length - 1; i++) {
            crc = (byte) (value[i] + crc);
        }
        int i2 = value.length;
        value[i2 - 1] = (byte) (crc & 255);
    }
}
