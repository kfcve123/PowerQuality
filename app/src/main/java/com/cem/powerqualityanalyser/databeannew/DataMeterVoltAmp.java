package com.cem.powerqualityanalyser.databeannew;

public class DataMeterVoltAmp extends BaseData{

    private DataPhaseObjN urmsValue;
    private DataPhaseObjN vrmsValue;
    private DataPhaseObjN armsValue;
    private DataPhaseObjN udcValue;
    private DataPhaseObjN vdcValue;

    private DataPhaseObjN adcValue;
    private DataPhaseObjN ucfValue;
    private DataPhaseObjN vcfValue;
    private DataPhaseObjN acfValue;
    private DataPhaseObjN upeakUpValue;
    private DataPhaseObjN vpeakUpValue;
    private DataPhaseObjN apeakUpValue;
    private DataPhaseObjN upeakDownValue;
    private DataPhaseObjN vpeakDownValue;
    private DataPhaseObjN apeakDownValue;

    public DataMeterVoltAmp(){

    }

    public DataMeterVoltAmp(DataPhaseObjN urmsValue, DataPhaseObjN vrmsValue, DataPhaseObjN armsValue, DataPhaseObjN udcValue, DataPhaseObjN vdcValue, DataPhaseObjN adcValue, DataPhaseObjN ucfValue, DataPhaseObjN vcfValue, DataPhaseObjN acfValue, DataPhaseObjN upeakUpValue, DataPhaseObjN vpeakUpValue, DataPhaseObjN apeakUpValue, DataPhaseObjN upeakDownValue, DataPhaseObjN vpeakDownValue, DataPhaseObjN apeakDownValue) {
        this.urmsValue = urmsValue;
        this.vrmsValue = vrmsValue;
        this.armsValue = armsValue;
        this.udcValue = udcValue;
        this.vdcValue = vdcValue;
        this.adcValue = adcValue;
        this.ucfValue = ucfValue;
        this.vcfValue = vcfValue;
        this.acfValue = acfValue;
        this.upeakUpValue = upeakUpValue;
        this.vpeakUpValue = vpeakUpValue;
        this.apeakUpValue = apeakUpValue;
        this.upeakDownValue = upeakDownValue;
        this.vpeakDownValue = vpeakDownValue;
        this.apeakDownValue = apeakDownValue;
    }

    public DataPhaseObjN getUrmsValue() {
        return urmsValue;
    }

    public void setUrmsValue(DataPhaseObjN urmsValue) {
        this.urmsValue = urmsValue;
    }

    public DataPhaseObjN getVrmsValue() {
        return vrmsValue;
    }

    public void setVrmsValue(DataPhaseObjN vrmsValue) {
        this.vrmsValue = vrmsValue;
    }

    public DataPhaseObjN getArmsValue() {
        return armsValue;
    }

    public void setArmsValue(DataPhaseObjN armsValue) {
        this.armsValue = armsValue;
    }

    public DataPhaseObjN getUdcValue() {
        return udcValue;
    }

    public void setUdcValue(DataPhaseObjN udcValue) {
        this.udcValue = udcValue;
    }

    public DataPhaseObjN getVdcValue() {
        return vdcValue;
    }

    public void setVdcValue(DataPhaseObjN vdcValue) {
        this.vdcValue = vdcValue;
    }

    public DataPhaseObjN getAdcValue() {
        return adcValue;
    }

    public void setAdcValue(DataPhaseObjN adcValue) {
        this.adcValue = adcValue;
    }

    public DataPhaseObjN getUcfValue() {
        return ucfValue;
    }

    public void setUcfValue(DataPhaseObjN ucfValue) {
        this.ucfValue = ucfValue;
    }

    public DataPhaseObjN getVcfValue() {
        return vcfValue;
    }

    public void setVcfValue(DataPhaseObjN vcfValue) {
        this.vcfValue = vcfValue;
    }

    public DataPhaseObjN getAcfValue() {
        return acfValue;
    }

    public void setAcfValue(DataPhaseObjN acfValue) {
        this.acfValue = acfValue;
    }

    public DataPhaseObjN getUpeakUpValue() {
        return upeakUpValue;
    }

    public void setUpeakUpValue(DataPhaseObjN upeakUpValue) {
        this.upeakUpValue = upeakUpValue;
    }

    public DataPhaseObjN getVpeakUpValue() {
        return vpeakUpValue;
    }

    public void setVpeakUpValue(DataPhaseObjN vpeakUpValue) {
        this.vpeakUpValue = vpeakUpValue;
    }

    public DataPhaseObjN getApeakUpValue() {
        return apeakUpValue;
    }

    public void setApeakUpValue(DataPhaseObjN apeakUpValue) {
        this.apeakUpValue = apeakUpValue;
    }

    public DataPhaseObjN getUpeakDownValue() {
        return upeakDownValue;
    }

    public void setUpeakDownValue(DataPhaseObjN upeakDownValue) {
        this.upeakDownValue = upeakDownValue;
    }

    public DataPhaseObjN getVpeakDownValue() {
        return vpeakDownValue;
    }

    public void setVpeakDownValue(DataPhaseObjN vpeakDownValue) {
        this.vpeakDownValue = vpeakDownValue;
    }

    public DataPhaseObjN getApeakDownValue() {
        return apeakDownValue;
    }

    public void setApeakDownValue(DataPhaseObjN apeakDownValue) {
        this.apeakDownValue = apeakDownValue;
    }
}
