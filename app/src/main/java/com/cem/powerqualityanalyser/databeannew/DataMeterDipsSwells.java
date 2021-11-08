package com.cem.powerqualityanalyser.databeannew;

public class DataMeterDipsSwells extends BaseData{

    private DataPhaseObjN vrmsValue;
    private DataPhaseObjN armsValue;

    public DataMeterDipsSwells(){

    }

    public DataMeterDipsSwells(DataPhaseObjN vrmsValue, DataPhaseObjN armsValue) {
        this.vrmsValue = vrmsValue;
        this.armsValue = armsValue;
    }

    public DataPhaseObjN getVrmsValue() {
        return vrmsValue;
    }

    public void setVrmsValue(DataPhaseObjN vrmsValue) {
        this.vrmsValue = vrmsValue;
    }

    public DataPhaseObjN getArmsValue() {
        return armsValue;
    }

    public void setArmsValue(DataPhaseObjN armsValue) {
        this.armsValue = armsValue;
    }
}
