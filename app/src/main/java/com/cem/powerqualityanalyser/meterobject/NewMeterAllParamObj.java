package com.cem.powerqualityanalyser.meterobject;

import serialport.amos.cem.com.libamosserial.ModelBaseData;

public class NewMeterAllParamObj {
    protected ModelBaseData l1;
    protected ModelBaseData l2;
    protected ModelBaseData l3;
    protected ModelBaseData n;
    protected String unit;
    public NewMeterAllParamObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n, String unit){
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.n=n;
        this.unit=unit;
    }

    public NewMeterAllParamObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n){
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.n=n;
        this.unit=l1.getValue_Unit();
    }

    public ModelBaseData getL1() {
        return l1;
    }

    public ModelBaseData getL2() {
        return l2;
    }

    public ModelBaseData getL3() {
        return l3;
    }

    public ModelBaseData getN() {
        return n;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
