package com.cem.powerqualityanalyser.databean;

import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;

/**
 * 旧 不平衡模块的整个数据对象
 */
public class DataMeterThreeUnbalanced extends BaseData {
    private DataPhaseObjN v_fundWave;
    private DataPhaseObj a_fundWave;
    private DataPhaseObj v_Angle;
    private DataPhaseObj a_Angle;
    private DataMeterData frequency;

    public DataMeterThreeUnbalanced(DataPhaseObjN v_fundWave, DataPhaseObj a_fundWave, DataPhaseObj v_Angle, DataPhaseObj a_Angle, DataMeterData frequency) {
        this.v_fundWave = v_fundWave;
        this.a_fundWave = a_fundWave;
        this.v_Angle = v_Angle;
        this.a_Angle = a_Angle;
        this.frequency = frequency;
    }

    public DataMeterThreeUnbalanced() {
    }

    public DataPhaseObjN getV_fundWave() {
        return v_fundWave;
    }

    public void setV_fundWave(DataPhaseObjN v_fundWave) {
        this.v_fundWave = v_fundWave;
    }

    public DataPhaseObj getA_fundWave() {
        return a_fundWave;
    }

    public void setA_fundWave(DataPhaseObj a_fundWave) {
        this.a_fundWave = a_fundWave;
    }

    public DataPhaseObj getV_Angle() {
        return v_Angle;
    }

    public void setV_Angle(DataPhaseObj v_Angle) {
        this.v_Angle = v_Angle;
    }

    public DataPhaseObj getA_Angle() {
        return a_Angle;
    }

    public void setA_Angle(DataPhaseObj a_Angle) {
        this.a_Angle = a_Angle;
    }

    public DataMeterData getFrequency() {
        return frequency;
    }

    public void setFrequency(DataMeterData frequency) {
        this.frequency = frequency;
    }
}
