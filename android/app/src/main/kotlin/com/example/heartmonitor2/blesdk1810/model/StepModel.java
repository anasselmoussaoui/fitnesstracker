package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes3.dex */
public class StepModel extends SendData {
    boolean stepState;

    public boolean isStepState() {
        return this.stepState;
    }

    public void setStepState(boolean stepState) {
        this.stepState = stepState;
    }

    public String toString() {
        return "StepModel{stepState=" + this.stepState + '}';
    }
}
