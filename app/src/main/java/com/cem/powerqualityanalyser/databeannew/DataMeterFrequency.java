package com.cem.powerqualityanalyser.databeannew;

public class DataMeterFrequency extends BaseData{

    private DataPhaseObjN hzValue;
    public DataMeterFrequency(){

    }

    public DataMeterFrequency(DataPhaseObjN hzValue) {
        this.hzValue = hzValue;
    }

    public DataPhaseObjN getHzValue() {
        return hzValue;
    }

    public void setHzValue(DataPhaseObjN hzValue) {
        this.hzValue = hzValue;
    }
}
