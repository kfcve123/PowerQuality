package com.cem.powerqualityanalyser.databean;

import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;

public class DataMeterAllParameter extends BaseData {
    private DataPhaseObjN v_StarValue;
    private DataPhaseObj v_StarMax;
    private DataPhaseObjN a_Value;
    private DataPhaseObj a_Max;
    private DataMeterData frequency;
    private DataPhaseObj phase_Pf;
    private DataPhaseObj v_triangleValue;
    private DataPhaseObjN v_triangleDCValue;

    public DataMeterAllParameter(DataPhaseObjN v_StarValue, DataPhaseObj v_StarMax, DataPhaseObjN a_Value, DataPhaseObj a_Max, DataMeterData frequency, DataPhaseObj phase_Pf, DataPhaseObj v_triangleValue, DataPhaseObjN v_triangleDCValue) {
        this.v_StarValue = v_StarValue;
        this.v_StarMax = v_StarMax;
        this.a_Value = a_Value;
        this.a_Max = a_Max;
        this.frequency = frequency;
        this.phase_Pf = phase_Pf;
        this.v_triangleValue = v_triangleValue;
        this.v_triangleDCValue = v_triangleDCValue;
    }

    public DataMeterAllParameter() {
    }

    public DataPhaseObjN getV_StarValue() {
        return v_StarValue;
    }

    public void setV_StarValue(DataPhaseObjN v_StarValue) {
        this.v_StarValue = v_StarValue;
    }

    public DataPhaseObj getV_StarMax() {
        return v_StarMax;
    }

    public void setV_StarMax(DataPhaseObj v_StarMax) {
        this.v_StarMax = v_StarMax;
    }

    public DataPhaseObjN getA_Value() {
        return a_Value;
    }

    public void setA_Value(DataPhaseObjN a_Value) {
        this.a_Value = a_Value;
    }

    public DataPhaseObj getA_Max() {
        return a_Max;
    }

    public void setA_Max(DataPhaseObj a_Max) {
        this.a_Max = a_Max;
    }

    public DataMeterData getFrequency() {
        return frequency;
    }

    public void setFrequency(DataMeterData frequency) {
        this.frequency = frequency;
    }

    public DataPhaseObj getPhase_Pf() {
        return phase_Pf;
    }

    public void setPhase_Pf(DataPhaseObj phase_Pf) {
        this.phase_Pf = phase_Pf;
    }

    public DataPhaseObj getV_triangleValue() {
        return v_triangleValue;
    }

    public void setV_triangleValue(DataPhaseObj v_triangleValue) {
        this.v_triangleValue = v_triangleValue;
    }

    public DataPhaseObjN getV_triangleDCValue() {
        return v_triangleDCValue;
    }

    public void setV_triangleDCValue(DataPhaseObjN v_triangleDCValue) {
        this.v_triangleDCValue = v_triangleDCValue;
    }
}
