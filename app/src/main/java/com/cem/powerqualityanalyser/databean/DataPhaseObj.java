package com.cem.powerqualityanalyser.databean;

import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.meterobject.NewHistoryMeterAllObj;

public class DataPhaseObj extends BaseData {

    protected DataMeterData phaseA;
    protected DataMeterData phaseB;
    protected DataMeterData phaseC;

    public DataPhaseObj(DataMeterData phaseA, DataMeterData phaseB, DataMeterData phaseC) {
        this.phaseA = phaseA;
        this.phaseB = phaseB;
        this.phaseC = phaseC;
    }

    public DataPhaseObj() {
    }

    public DataMeterData getPhaseA() {
        return phaseA;
    }

    public void setPhaseA(DataMeterData phaseA) {
        this.phaseA = phaseA;
    }

    public DataMeterData getPhaseB() {
        return phaseB;
    }

    public void setPhaseB(DataMeterData phaseB) {
        this.phaseB = phaseB;
    }

    public DataMeterData getPhaseC() {
        return phaseC;
    }

    public void setPhaseC(DataMeterData phaseC) {
        this.phaseC = phaseC;
    }

    public HistoryMeterAllObj toMeterAllParamObj(){
        HistoryMeterAllObj meterAllParamObj = new HistoryMeterAllObj(this.phaseA.toMeterData(),this.phaseB.toMeterData(),this.phaseC.toMeterData(),null,"",phaseA.getDate());
        return meterAllParamObj;
    }

    public NewHistoryMeterAllObj toNewMeterAllParamObj(){
        NewHistoryMeterAllObj meterAllParamObj = new NewHistoryMeterAllObj(this.phaseA.toNewMeterData(),this.phaseB.toNewMeterData(),this.phaseC.toNewMeterData(),null,phaseA.getDate());
        return meterAllParamObj;
    }



}
