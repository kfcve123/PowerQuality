package com.cem.powerqualityanalyser.meterobject;


import com.cem.powerqualityanalyser.libs.MeterData;

public class MeterAllParamObj {
    protected MeterData l1;
    protected MeterData l2;
    protected MeterData l3;
    protected MeterData n;
    protected  String unit;
    public MeterAllParamObj(MeterData l1, MeterData l2, MeterData l3, MeterData n, String unit){
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.n=n;
        this.unit=unit;
    } 

    public MeterData getL1() {
        return l1;
    }

    public MeterData getL2() {
        return l2;
    }

    public MeterData getL3() {
        return l3;
    }

    public MeterData getN() {
        return n;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
