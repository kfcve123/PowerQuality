package com.cem.powerqualityanalyser.databeannew;

import com.cem.powerqualityanalyser.databean.DataPhaseObj;

/**
 *
 */
public class DataMeterUnbalanced extends BaseData {
    private DataPhaseObjN unbalValue;
    private DataPhaseObjN vnegValue;
    private DataPhaseObjN vzerolValue;
    private DataPhaseObjN anegValue;
    private DataPhaseObjN azeroValue;


    public DataMeterUnbalanced() {
    }

    public DataMeterUnbalanced(DataPhaseObjN unbalValue, DataPhaseObjN vnegValue, DataPhaseObjN vzerolValue, DataPhaseObjN anegValue, DataPhaseObjN azeroValue) {
        this.unbalValue = unbalValue;
        this.vnegValue = vnegValue;
        this.vzerolValue = vzerolValue;
        this.anegValue = anegValue;
        this.azeroValue = azeroValue;
    }

    public DataPhaseObjN getUnbalValue() {
        return unbalValue;
    }

    public void setUnbalValue(DataPhaseObjN unbalValue) {
        this.unbalValue = unbalValue;
    }

    public DataPhaseObjN getVnegValue() {
        return vnegValue;
    }

    public void setVnegValue(DataPhaseObjN vnegValue) {
        this.vnegValue = vnegValue;
    }

    public DataPhaseObjN getVzerolValue() {
        return vzerolValue;
    }

    public void setVzerolValue(DataPhaseObjN vzerolValue) {
        this.vzerolValue = vzerolValue;
    }

    public DataPhaseObjN getAnegValue() {
        return anegValue;
    }

    public void setAnegValue(DataPhaseObjN anegValue) {
        this.anegValue = anegValue;
    }

    public DataPhaseObjN getAzeroValue() {
        return azeroValue;
    }

    public void setAzeroValue(DataPhaseObjN azeroValue) {
        this.azeroValue = azeroValue;
    }


}
