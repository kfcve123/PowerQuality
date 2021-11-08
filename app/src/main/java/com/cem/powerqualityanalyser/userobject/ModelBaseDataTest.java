package com.cem.powerqualityanalyser.userobject;

import com.cem.powerqualityanalyser.libs.MeterTools;

public class ModelBaseDataTest {
    public String value;
    public String value_Unit;

    public ModelBaseDataTest() {
    }

    public ModelBaseDataTest(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue_Unit(String value_Unit) {
        this.value_Unit = value_Unit;
    }

    public String getValue_Unit() {
        return this.value_Unit;
    }


    private int dataPoint;
    public float valueFl;
    private int olValue = 15658734;


    public ModelBaseDataTest(float valueFl) {
        this.setData(valueFl);
    }

    public ModelBaseDataTest(float value, int pointCount) {
        this.setData(value, pointCount);
    }

    public float getValueFl(){
        return valueFl;
    }

    private void setData(float value) {
        this.valueFl = value;
        this.value = this.dataProcess(value);
    }

    private void setData(float value, int pointCount) {
        this.valueFl = value;
        this.dataPoint = pointCount;
        this.value = this.dataProcess(value);
    }

    private String dataProcess(float data) {
        String showData = MeterTools.FormatData(data, this.dataPoint);
        if (data >= (float)this.olValue) {
            showData = "----";
        }

        return showData;
    }

    public String FormatData(float data, int pointCount) {
        return String.format("%." + pointCount + "f", data);
    }



}
