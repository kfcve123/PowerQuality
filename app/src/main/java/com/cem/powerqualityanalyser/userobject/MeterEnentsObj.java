package com.cem.powerqualityanalyser.userobject;

public class MeterEnentsObj {
    private String typeStr;
    private  float startValue;
    private  float endValue;
    public MeterEnentsObj(String typeStr, float startValue, float endValue){
        this.typeStr=typeStr;
        this.startValue=startValue;
        this.endValue=endValue;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public float getStartValue() {
        return startValue;
    }

    public float getEndValue() {
        return endValue;
    }
}
