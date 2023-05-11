package com.example.heartmonitor2.blesdk1810.model;

/* loaded from: classes6.dex */
public class MyPersonalInfo extends SendData {
    int age;
    int height;
    int sex;
    int stepLength = 70;
    int weight;

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStepLength() {
        return this.stepLength;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public String toString() {
        return "MyPersonalInfo{sex=" + this.sex + ", age=" + this.age + ", height=" + this.height + ", weight=" + this.weight + ", stepLength=" + this.stepLength + '}';
    }
}
