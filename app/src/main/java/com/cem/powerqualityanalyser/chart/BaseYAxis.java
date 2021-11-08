package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.components.YAxis;

public class BaseYAxis extends YAxis {
    private float baseValue=Float.NaN;
    private String minValue;


    public BaseYAxis() {
        super();
    }

    public BaseYAxis(AxisDependency axis) {
        super(axis);
    }

    public void setShowMaxAndUnit(String minValue) {
        //setShowOnlyMinMax(true);
        this.minValue = minValue;
    }
    public float getBaseValue() {
        return baseValue;
    }

    public String getMinValue(){
        return minValue;
    }
    public void setBaseValue(float baseValue) {
        this.baseValue = baseValue;
    }

}
