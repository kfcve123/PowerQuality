package com.cem.powerqualityanalyser.databeannew;


import com.cem.powerqualityanalyser.databeannew.BaseData;

import java.util.List;

public class DataGraphDataObj extends BaseData {
    private List<Float> valueA;
    private List<Float> valueB;
    private List<Float> valueC;
    private float coefficient = 1.0F;
    private int dataCount;

    public DataGraphDataObj(List<Float> valueA, List<Float> valueB, List<Float> valueC,int dataCount) {
        this.valueA = valueA;
        this.valueB = valueB;
        this.valueC = valueC;
        this.dataCount = dataCount;
    }

    public List<Float> getValueA() {
        return valueA;
    }

    public void setValueA(List<Float> valueA) {
        this.valueA = valueA;
    }

    public List<Float> getValueB() {
        return valueB;
    }

    public void setValueB(List<Float> valueB) {
        this.valueB = valueB;
    }

    public List<Float> getValueC() {
        return valueC;
    }

    public void setValueC(List<Float> valueC) {
        this.valueC = valueC;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}
