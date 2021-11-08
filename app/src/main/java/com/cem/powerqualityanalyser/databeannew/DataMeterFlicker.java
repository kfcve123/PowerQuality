package com.cem.powerqualityanalyser.databeannew;

public class DataMeterFlicker extends BaseData{

    private DataPhaseObjN pstMinValue;
    private DataPhaseObjN pstValue;
    private DataPhaseObjN pltValue;
    private DataPhaseObjN pinstValue;

    public DataMeterFlicker(){

    }

    public DataMeterFlicker(DataPhaseObjN pstMinValue, DataPhaseObjN pstValue, DataPhaseObjN pltValue, DataPhaseObjN pinstValue) {
        this.pstMinValue = pstMinValue;
        this.pstValue = pstValue;
        this.pltValue = pltValue;
        this.pinstValue = pinstValue;
    }

    public DataPhaseObjN getPstMinValue() {
        return pstMinValue;
    }

    public void setPstMinValue(DataPhaseObjN pstMinValue) {
        this.pstMinValue = pstMinValue;
    }

    public DataPhaseObjN getPstValue() {
        return pstValue;
    }

    public void setPstValue(DataPhaseObjN pstValue) {
        this.pstValue = pstValue;
    }

    public DataPhaseObjN getPltValue() {
        return pltValue;
    }

    public void setPltValue(DataPhaseObjN pltValue) {
        this.pltValue = pltValue;
    }

    public DataPhaseObjN getPinstValue() {
        return pinstValue;
    }

    public void setPinstValue(DataPhaseObjN pinstValue) {
        this.pinstValue = pinstValue;
    }
}
