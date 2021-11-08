package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;

public class BaseLineChart extends LineChart {

    public BaseLineChart(Context context) {
        super(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



}
