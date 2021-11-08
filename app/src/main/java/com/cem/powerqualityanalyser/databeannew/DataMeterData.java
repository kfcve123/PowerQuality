package com.cem.powerqualityanalyser.databeannew;

import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.MeterTools;

import java.util.Date;

import serialport.amos.cem.com.libamosserial.ModelBaseData;

public class DataMeterData extends BaseData {
    private float value;
    private String showValue;
    private int dataCount;
    private boolean gElectricity;
    private boolean rElectricity;
    private String unit;

    public DataMeterData(float value) {
        this.setData(value);
    }
    private void setData(float value) {
        this.value = value;
        this.showValue = this.dataProcess(value);
    }

    public DataMeterData(float value, int dataCount, boolean gElectricity, boolean rElectricity) {
        this.value = value;
        this.dataCount = dataCount;
        this.gElectricity = gElectricity;
        this.rElectricity = rElectricity;
        this.showValue = this.dataProcess(value);

    }

    public DataMeterData(float value, int dataCount,Date date, boolean gElectricity, boolean rElectricity) {
        this.date = date;
        this.value = value;
        this.dataCount = dataCount;
        this.gElectricity = gElectricity;
        this.rElectricity = rElectricity;
        this.showValue = dataProcess(value);
    }


    public DataMeterData(float value, int dataCount, boolean gElectricity, boolean rElectricity,Date date) {
        this.value = value;
        this.dataCount = dataCount;
        this.gElectricity = gElectricity;
        this.rElectricity = rElectricity;
        this.date = date;
        this.showValue = dataProcess(value);
    }

    public DataMeterData(float value, int dataCount, String unit,boolean gElectricity, boolean rElectricity,Date date) {
        this.value = value;
        this.dataCount = dataCount;
        this.unit = unit;
        this.gElectricity = gElectricity;
        this.rElectricity = rElectricity;
        this.date = date;
        this.showValue = dataProcess(value);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isgElectricity() {
        return gElectricity;
    }

    public void setgElectricity(boolean gElectricity) {
        this.gElectricity = gElectricity;
    }

    public boolean isrElectricity() {
        return rElectricity;
    }

    public void setrElectricity(boolean rElectricity) {
        this.rElectricity = rElectricity;
    }

    //    public DataMeterData(float value, int dataCount) {
//        this.value = value;
//        this.dataCount = dataCount;
//        this.showValue = dataProcess(value);
//    }

    public MeterData toMeterData(){
        MeterData meterData = new MeterData(this.getValue());
        return meterData;
    }

    public ModelBaseData toNewMeterData(){
        ModelBaseData meterData = new ModelBaseData(this.getValue(),this.getDataCount());
        return meterData;
    }

    private String dataProcess(float data) {
        String showData = MeterTools.FormatData(data, this.dataCount);
        if (data >= 65262.0F) {
            showData = "----";
        }
        return showData;
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.setData(value);
    }

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}
