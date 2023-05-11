package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes3.dex */
public class ExerciseMode extends SendData {
    public static final int Mode_CYCLING = 1;
    public static final int Mode_RUN = 0;
    public static final int Mode_YOGA = 6;
    public static final int Status_CONTUINE = 3;
    public static final int Status_FINISH = 4;
    public static final int Status_PAUSE = 2;
    public static final int Status_START = 1;
    public static int[] modes = {0, 1, 6};
    int enableStatus;
    int exerciseMode;

    public int getExerciseMode(int position) {
        return modes[position];
    }

    public int getExerciseMode() {
        return this.exerciseMode;
    }

    public void setExerciseMode(int exerciseMode) {
        this.exerciseMode = exerciseMode;
    }

    public int getEnableStatus() {
        return this.enableStatus;
    }

    public void setEnableStatus(int enableStatus) {
        this.enableStatus = enableStatus;
    }
}
