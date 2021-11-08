package com.cem.powerqualityanalyser.databean;


import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;

/**
 * 旧的船机电 模块的整个数据对象
 */
public class DataMeterHz_400 extends BaseData {
    private DataPhaseObjN v_StarValue;
    private DataPhaseObj v_TriangleValue;
    private DataPhaseObjN a_TriangleValue;
    private DataMeterData frequency;


    public DataMeterHz_400(DataPhaseObjN v_StarValue, DataPhaseObj v_TriangleValue, DataPhaseObjN a_TriangleValue, DataMeterData frequency) {
        this.v_StarValue = v_StarValue;
        this.v_TriangleValue = v_TriangleValue;
        this.a_TriangleValue = a_TriangleValue;
        this.frequency = frequency;
    }

    public DataMeterHz_400() {
    }

    public DataPhaseObjN getV_StarValue() {
        return v_StarValue;
    }

    public void setV_StarValue(DataPhaseObjN v_StarValue) {
        this.v_StarValue = v_StarValue;
    }

    public DataPhaseObj getV_TriangleValue() {
        return v_TriangleValue;
    }

    public void setV_TriangleValue(DataPhaseObj v_TriangleValue) {
        this.v_TriangleValue = v_TriangleValue;
    }

    public DataPhaseObjN getA_TriangleValue() {
        return a_TriangleValue;
    }

    public void setA_TriangleValue(DataPhaseObjN a_TriangleValue) {
        this.a_TriangleValue = a_TriangleValue;
    }

    public DataMeterData getFrequency() {
        return frequency;
    }

    public void setFrequency(DataMeterData frequency) {
        this.frequency = frequency;
    }
}
