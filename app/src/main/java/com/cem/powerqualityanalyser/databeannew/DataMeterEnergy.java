package com.cem.powerqualityanalyser.databeannew;

public class DataMeterEnergy extends BaseData{

    private DataPhaseObjN kVahValue;
    private DataPhaseObjN kwhValue;
    private DataPhaseObjN kwhForwValue;
    private DataPhaseObjN kwhRevValue;
    private DataPhaseObjN kVarhValue;

    public DataMeterEnergy(){

    }

    public DataMeterEnergy(DataPhaseObjN kVahValue, DataPhaseObjN kwhValue, DataPhaseObjN kwhForwValue, DataPhaseObjN kwhRevValue, DataPhaseObjN kVarhValue) {
        this.kVahValue = kVahValue;
        this.kwhValue = kwhValue;
        this.kwhForwValue = kwhForwValue;
        this.kwhRevValue = kwhRevValue;
        this.kVarhValue = kVarhValue;
    }

    public DataPhaseObjN getkVahValue() {
        return kVahValue;
    }

    public void setkVahValue(DataPhaseObjN kVahValue) {
        this.kVahValue = kVahValue;
    }

    public DataPhaseObjN getKwhValue() {
        return kwhValue;
    }

    public void setKwhValue(DataPhaseObjN kwhValue) {
        this.kwhValue = kwhValue;
    }

    public DataPhaseObjN getKwhForwValue() {
        return kwhForwValue;
    }

    public void setKwhForwValue(DataPhaseObjN kwhForwValue) {
        this.kwhForwValue = kwhForwValue;
    }

    public DataPhaseObjN getKwhRevValue() {
        return kwhRevValue;
    }

    public void setKwhRevValue(DataPhaseObjN kwhRevValue) {
        this.kwhRevValue = kwhRevValue;
    }

    public DataPhaseObjN getkVarhValue() {
        return kVarhValue;
    }

    public void setkVarhValue(DataPhaseObjN kVarhValue) {
        this.kVarhValue = kVarhValue;
    }
}
