package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class PowerMonitorBarMoreChartBase extends PowerMonitorBarBaseView {
    protected final int XRangeMaximum = 100;
    protected final int labelCount = 10;
    protected String unit = "";

    public PowerMonitorBarMoreChartBase(Context context) {
        super(context);
    }

    public PowerMonitorBarMoreChartBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PowerMonitorBarMoreChartBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PowerMonitorBarMoreChartBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void initChartListener() {
        super.initChartListener();

    }



    /**
     * 拦截父控件的触摸事件，分发给所有chartView
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
    public void moveCursor(int i){
        if(cursorEnable) {

        }
    }
}
