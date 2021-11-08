package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class DipsSwellsMoreChartBase extends DipsSwellsTrendBaseView {
    protected final int XRangeMaximum = 100;
    protected final int labelCount = 10;
    protected String unit = "";

    public DipsSwellsMoreChartBase(Context context) {
        super(context);
    }

    public DipsSwellsMoreChartBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DipsSwellsMoreChartBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DipsSwellsMoreChartBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void initChartListener() {
        super.initChartListener();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l1.setText(showValue  + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l2.setText(showValue  + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart3.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l3.setText(showValue  + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart4.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l4.setText(showValue + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart.getXAxis().setLabelCount(labelCount,true);
        mChart2.getXAxis().setLabelCount(labelCount,true);
        mChart3.getXAxis().setLabelCount(labelCount,true);
        mChart4.getXAxis().setLabelCount(labelCount,true);


        mChart.setHighlightPerTapEnabled(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart2.setHighlightPerTapEnabled(false);
        mChart2.setHighlightPerDragEnabled(false);
        mChart3.setHighlightPerTapEnabled(false);
        mChart3.setHighlightPerDragEnabled(false);
        mChart4.setHighlightPerTapEnabled(false);
        mChart4.setHighlightPerDragEnabled(false);

        mChart.setViewPortOffsets(40,0,25,0);
        mChart2.setViewPortOffsets(40,0,25,0);
        mChart3.setViewPortOffsets(40,0,25,0);
        mChart4.setViewPortOffsets(40,0,25,20);
        mChart4.setExtraBottomOffset(6);//底部文字被遮挡处理
    }

    /**
     * 设置曲线边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setViewPortOffsets(int left,int top,int right,int bottom){
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).setViewPortOffsets(left,top,right,bottom);
        }
    }

    public void setAxisRightEnabled(){
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).getAxisRight().setEnabled(true);
        }
    }

    public void setenableGridDashedLine(){
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).getAxisLeft().setDrawGridLinesBehindData(false);
        }
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
 //       return super.dispatchTouchEvent(ev);
        for (VoltsAmpsHertzLineChart chart:charts) {
            chart.dispatchTouchEvent(ev);
        }
        //移动会有误差，手抬起，让X轴对齐
        if (ev.getAction() == MotionEvent.ACTION_UP){
            float lowestVisibleX = mChart.getLowestVisibleX();
            mChart2.moveViewToX(lowestVisibleX);
            mChart3.moveViewToX(lowestVisibleX);
            mChart4.moveViewToX(lowestVisibleX);
        }
        return true;
    }
    public void moveCursor(int i){
        if(cursorEnable) {
            if(mChart!=null) {
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
