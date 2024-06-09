package com.example.choice;

import java.io.Serializable;

public class Choice implements Serializable {
    public static float PER_ANGLE;

    private String color;

    private String desc;

    private String id;

    private float startAngle;

    private int weight = 1;

    public String getColor() {
        return this.color;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getId() {
        return this.id;
    }

    public float getStartAngle() {
        return this.startAngle;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setColor(String paramString) {
        this.color = paramString;
    }

    public void setDesc(String paramString) {
        this.desc = paramString;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public void setStartAngle(float paramFloat) {
        this.startAngle = paramFloat;
    }

    public void setWeight(int paramInt) {
        this.weight = paramInt;
    }
}
