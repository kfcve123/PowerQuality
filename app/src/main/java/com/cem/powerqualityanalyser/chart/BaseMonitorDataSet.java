package com.cem.powerqualityanalyser.chart;

import android.graphics.Color;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class BaseMonitorDataSet extends BarDataSet {


    public BaseMonitorDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
        mColors.add(Color.rgb(255, 255, 255));
   //     setHighLightAlpha(0);
    }
}
