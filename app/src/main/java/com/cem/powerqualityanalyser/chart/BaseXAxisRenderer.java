package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BaseXAxisRenderer extends XAxisRenderer {
    private final BarLineChartBase mChart;
    protected XAxis mXAxis;

    public BaseXAxisRenderer(ViewPortHandler viewPortHandler, BaseXAxis xAxis, Transformer trans, BarLineChartBase chart) {
        super(viewPortHandler, xAxis, trans);
        mXAxis = xAxis;
        mChart = chart;
    }

    private boolean showXAxis;





}
