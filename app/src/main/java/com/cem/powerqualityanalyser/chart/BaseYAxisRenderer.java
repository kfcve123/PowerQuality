package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BaseYAxisRenderer extends YAxisRenderer {

    protected BaseYAxis mYAxis;

    public BaseYAxisRenderer(ViewPortHandler viewPortHandler, BaseYAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
        mYAxis = yAxis;
    }

}
