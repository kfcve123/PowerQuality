package com.cem.powerqualityanalyser.meterobject;


import serialport.amos.cem.com.libamosserial.ModelBaseData;

public class NewHistoryMeterAllObj extends NewMeterAllParamObj {
    private java.util.Date Date;

    public NewHistoryMeterAllObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n) {
        super(l1, l2, l3, n);
    }

    public NewHistoryMeterAllObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n, String unit, java.util.Date date) {
        super(l1, l2, l3, n, unit);
        Date = date;
    }


    public NewHistoryMeterAllObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n, String unit) {
        super(l1, l2, l3, n, unit);
    }

    public NewHistoryMeterAllObj(ModelBaseData l1, ModelBaseData l2, ModelBaseData l3, ModelBaseData n,java.util.Date date) {
        super(l1, l2, l3, n);
    }




    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

}
