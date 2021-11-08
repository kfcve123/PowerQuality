package com.cem.powerqualityanalyser.meterobject;

import com.cem.powerqualityanalyser.libs.MeterData;

import serialport.amos.cem.com.libamosserial.ModelBaseData;

public class HistoryMeterAllObj extends MeterAllParamObj {
    private java.util.Date Date;

    public HistoryMeterAllObj(MeterData l1, MeterData l2, MeterData l3, MeterData n, String unit) {
        super(l1, l2, l3, n, unit);
    }

    public HistoryMeterAllObj(MeterData l1, MeterData l2, MeterData l3, MeterData n, String unit, java.util.Date date) {
        super(l1, l2, l3, n, unit);
        Date = date;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

}
