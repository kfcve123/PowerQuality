package com.cem.powerqualityanalyser.databeannew;

/**
 *
 */
public class DataMeterPower extends BaseData {

    private DataPhaseObjN kwValue;
    private DataPhaseObjN kvaValue;
    private DataPhaseObjN kvarValue;
    private DataPhaseObjN kvaHarmValue;
    private DataPhaseObjN kvaUnbValue;
    private DataPhaseObjN kwFundValue;
    private DataPhaseObjN kvaFundValue;
    private DataPhaseObjN wFundValue;
    private DataPhaseObjN cosValue;


    public DataMeterPower() {

    }

    public DataPhaseObjN getKwValue() {
        return kwValue;
    }

    public void setKwValue(DataPhaseObjN kwValue) {
        this.kwValue = kwValue;
    }

    public DataPhaseObjN getKvaValue() {
        return kvaValue;
    }

    public void setKvaValue(DataPhaseObjN kvaValue) {
        this.kvaValue = kvaValue;
    }

    public DataPhaseObjN getKvarValue() {
        return kvarValue;
    }

    public void setKvarValue(DataPhaseObjN kvarValue) {
        this.kvarValue = kvarValue;
    }

    public DataPhaseObjN getKvaHarmValue() {
        return kvaHarmValue;
    }

    public void setKvaHarmValue(DataPhaseObjN kvaHarmValue) {
        this.kvaHarmValue = kvaHarmValue;
    }

    public DataPhaseObjN getKvaUnbValue() {
        return kvaUnbValue;
    }

    public void setKvaUnbValue(DataPhaseObjN kvaUnbValue) {
        this.kvaUnbValue = kvaUnbValue;
    }

    public DataPhaseObjN getKwFundValue() {
        return kwFundValue;
    }

    public void setKwFundValue(DataPhaseObjN kwFundValue) {
        this.kwFundValue = kwFundValue;
    }

    public DataPhaseObjN getKvaFundValue() {
        return kvaFundValue;
    }

    public void setKvaFundValue(DataPhaseObjN kvaFundValue) {
        this.kvaFundValue = kvaFundValue;
    }

    public DataPhaseObjN getwFundValue() {
        return wFundValue;
    }

    public void setwFundValue(DataPhaseObjN wFundValue) {
        this.wFundValue = wFundValue;
    }

    public DataPhaseObjN getCosValue() {
        return cosValue;
    }

    public void setCosValue(DataPhaseObjN cosValue) {
        this.cosValue = cosValue;
    }
}
