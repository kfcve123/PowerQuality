package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cem.powerqualityanalyser.libs.MeterData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class MoreChartDip extends DipsSwellsBaseView {
    protected final int XRangeMaximum = 100;
    protected final int labelCount = 10;
    protected String unit = "";
    public MoreChartDip(Context context) {
        super(context);
    }

    public MoreChartDip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreChartDip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MoreChartDip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initChartListener() {
        super.initChartListener();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l1.setText("L1    " + showValue  + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();

                textview_l2.setText("L2    " + showValue  + "  " + unit);

            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart3.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();

                textview_l3.setText("L3    " + showValue  + "  " + unit);

            }

            @Override
            public void onNothingSelected() {

            }
        });


        mChart.getXAxis().setLabelCount(labelCount,true);
        mChart2.getXAxis().setLabelCount(labelCount,true);
        mChart3.getXAxis().setLabelCount(labelCount,true);

    }



    public void setHold(boolean isHold){
        setScanleEnable(isHold);
        setDragEnabled(isHold);
    }

    public void setScanleEnable(boolean scanleEnable){
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).setScaleEnabled(scanleEnable);
        }
    }
    public void setDragEnabled(boolean enable){
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).setDragEnabled(enable);
        }
    }


    /**
     * 拦截父控件的触摸事件，分发给所有chartView
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (VoltsAmpsHertzLineChart chart:charts) {
            chart.dispatchTouchEvent(ev);
        }
        //移动会有误差，手抬起，让X轴对齐
        if (ev.getAction() == MotionEvent.ACTION_UP){
            float lowestVisibleX = mChart.getLowestVisibleX();
            mChart2.moveViewToX(lowestVisibleX);
            mChart3.moveViewToX(lowestVisibleX);
        }
        return true;
    }

    public void moveCursor(int i){
        if(mChart!=null) {
            if(showCurson) {
                for (int j = 0; j < charts.size(); j++) {
                    Highlight[] highlighted = charts.get(j).getHighlighted();
                    if (highlighted != null && highlighted[0] != null){
                        charts.get(j).highlightValue(highlighted[0].getX() + i,0);
                    }
                }
            }
        }
    }

}
