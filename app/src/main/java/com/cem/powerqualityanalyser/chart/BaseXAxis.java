package com.cem.powerqualityanalyser.chart;

import android.util.SparseArray;

import com.github.mikephil.charting.components.XAxis;

public class BaseXAxis extends XAxis {

    public BaseXAxis() {
        super();
    }

    private SparseArray<String> labels;

    public SparseArray<String> getXLabels() {
        return labels;
    }

    public void setXLabels(SparseArray<String> labels) {
        this.labels = labels;
    }


    public  void setLabelHeight(int mLabelHeight){
        this.mLabelHeight = mLabelHeight;
    }


}
