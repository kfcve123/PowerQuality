package com.cem.powerqualityanalyser.databeannew;

import com.cem.powerqualityanalyser.databean.DataPhaseObj;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.meterobject.NewHistoryMeterAllObj;

public class DataPhaseObjN extends DataPhaseObj {

    private DataMeterData phaseN;


    public DataPhaseObjN(DataMeterData phaseA, DataMeterData phaseB, DataMeterData phaseC,DataMeterData phaseN) {
        super(phaseA, phaseB, phaseC);
        this.phaseN = phaseN;
    }

    public DataPhaseObjN() {
        super(null,null,null);
    }


    public HistoryMeterAllObj toMeterAllParamObj(){
        HistoryMeterAllObj meterAllParamObj = new HistoryMeterAllObj(this.phaseA.toMeterData(),this.phaseB.toMeterData(),this.phaseC.toMeterData(),this.phaseN.toMeterData(),"",phaseA.getDate());
        return meterAllParamObj;
    }

    public NewHistoryMeterAllObj toNewMeterAllParamObj(){
        NewHistoryMeterAllObj meterAllParamObj = new NewHistoryMeterAllObj(this.phaseA.toNewMeterData(),this.phaseB.toNewMeterData(),this.phaseC.toNewMeterData(),this.phaseN.toNewMeterData(),null,phaseA.getDate());
        return meterAllParamObj;
    }


    public DataMeterData getPhaseN() {
        return phaseN;
    }

    public void setPhaseN(DataMeterData phaseN) {
        this.phaseN = phaseN;
    }
}
