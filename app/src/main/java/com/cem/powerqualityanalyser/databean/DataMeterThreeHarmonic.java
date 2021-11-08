package com.cem.powerqualityanalyser.databean;


import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataGraphDataObj;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;

/**
 * 旧 谐波 模块的整个数据对象
 */
public class DataMeterThreeHarmonic extends BaseData {

    private DataPhaseObj v_PhaseValue;
    private DataPhaseObj a_PhaseValue;
    private DataPhaseObjN v_THD_PhaseValue;
    private DataPhaseObjN a_THD_PhaseValue;
    private DataMeterData frequency;
    private DataGraphDataObj listA_Phase;
    private DataGraphDataObj listV_Phase;

    public DataMeterThreeHarmonic(DataPhaseObj v_PhaseValue, DataPhaseObj a_PhaseValue, DataPhaseObjN v_THD_PhaseValue, DataPhaseObjN a_THD_PhaseValue, DataMeterData frequency, DataGraphDataObj listA_Phase, DataGraphDataObj listV_Phase) {
        this.v_PhaseValue = v_PhaseValue;
        this.a_PhaseValue = a_PhaseValue;
        this.v_THD_PhaseValue = v_THD_PhaseValue;
        this.a_THD_PhaseValue = a_THD_PhaseValue;
        this.frequency = frequency;
        this.listA_Phase = listA_Phase;
        this.listV_Phase = listV_Phase;
    }

    public DataMeterThreeHarmonic() {
    }

    public DataPhaseObj getV_PhaseValue() {
        return v_PhaseValue;
    }

    public void setV_PhaseValue(DataPhaseObj v_PhaseValue) {
        this.v_PhaseValue = v_PhaseValue;
    }

    public DataPhaseObj getA_PhaseValue() {
        return a_PhaseValue;
    }

    public void setA_PhaseValue(DataPhaseObj a_PhaseValue) {
        this.a_PhaseValue = a_PhaseValue;
    }

    public DataPhaseObjN getV_THD_PhaseValue() {
        return v_THD_PhaseValue;
    }

    public void setV_THD_PhaseValue(DataPhaseObjN v_THD_PhaseValue) {
        this.v_THD_PhaseValue = v_THD_PhaseValue;
    }

    public DataPhaseObjN getA_THD_PhaseValue() {
        return a_THD_PhaseValue;
    }

    public void setA_THD_PhaseValue(DataPhaseObjN a_THD_PhaseValue) {
        this.a_THD_PhaseValue = a_THD_PhaseValue;
    }

    public DataMeterData getFrequency() {
        return frequency;
    }

    public void setFrequency(DataMeterData frequency) {
        this.frequency = frequency;
    }

    public DataGraphDataObj getListA_Phase() {
        return listA_Phase;
    }

    public void setListA_Phase(DataGraphDataObj listA_Phase) {
        this.listA_Phase = listA_Phase;
    }

    public DataGraphDataObj getListV_Phase() {
        return listV_Phase;
    }

    public void setListV_Phase(DataGraphDataObj listV_Phase) {
        this.listV_Phase = listV_Phase;
    }
}
