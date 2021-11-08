package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.util.AttributeSet;

public class MonitorBarChart extends BaseBarChart{

    public MonitorBarChart(Context context) {
        super(context);
    }

    public MonitorBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new MonitorBarChartRenderer(this, mAnimator, mViewPortHandler);

    }
}
