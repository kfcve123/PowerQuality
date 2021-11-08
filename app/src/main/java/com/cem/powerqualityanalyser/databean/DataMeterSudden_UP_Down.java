package com.cem.powerqualityanalyser.databean;

import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataGraphDataObj;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;

public class DataMeterSudden_UP_Down extends BaseData {
    private DataPhaseObj phaseValue;
    private DataMeterData valueFrequency;
    private DataGraphDataObj listGraphPhase;

    public DataMeterSudden_UP_Down(DataPhaseObj phaseValue, DataMeterData valueFrequency, DataGraphDataObj listGraphPhase) {
        this.phaseValue = phaseValue;
        this.valueFrequency = valueFrequency;
        this.listGraphPhase = listGraphPhase;
    }

    public DataMeterSudden_UP_Down() {
    }

    public DataPhaseObj getPhaseValue() {
        return phaseValue;
    }

    public void setPhaseValue(DataPhaseObj phaseValue) {
        this.phaseValue = phaseValue;
    }

    public DataMeterData getValueFrequency() {
        return valueFrequency;
    }

    public void setValueFrequency(DataMeterData valueFrequency) {
        this.valueFrequency = valueFrequency;
    }

    public DataGraphDataObj getListGraphPhase() {
        return listGraphPhase;
    }

    public void setListGraphPhase(DataGraphDataObj listGraphPhase) {
        this.listGraphPhase = listGraphPhase;
    }
}


