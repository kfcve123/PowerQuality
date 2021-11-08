package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;


import com.cem.powerqualityanalyser.meterobject.MeterHarmonicObj;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class HarmonicsView extends HarmonicsBaseView {

    public HarmonicsView(Context context) {
        super(context);
    }

    public HarmonicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HarmonicsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HarmonicsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setTopTextView(MeterHarmonicObj harmonicObj){
        textview_l1.setVisibility(INVISIBLE);
        textview_l2.setVisibility(INVISIBLE);
        textview_l3.setVisibility(INVISIBLE);
        switch (harmonicObj.getPhaseIndex()){
            case 0:
                textview_l1.setText("L1   "+ harmonicObj.getRmsShowValue()+harmonicObj.getUnit());
                textview_l1.setVisibility(VISIBLE);
                break;
            case 1:
                textview_l2.setText("L2   "+ harmonicObj.getRmsShowValue()+harmonicObj.getUnit());
                textview_l2.setVisibility(VISIBLE);
                break;
            case 2:
                textview_l3.setText("L3   "+ harmonicObj.getRmsShowValue()+harmonicObj.getUnit());
                textview_l3.setVisibility(VISIBLE);
                break;
        }

    }


    public void setShowMeterData(MeterHarmonicObj harmonicObj) {
        setTopTextView(harmonicObj);
        int size = harmonicObj.getSize();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float val;
//            if(i == 0){
//                val = harmonicObj.getThd();
//            }
            if(i == 0){
                val = harmonicObj.getDc();
            }else{
                val = harmonicObj.getGraphList().get(i-1);
            }
            values.add(new BarEntry(i, val));
        }
        BarDataSet set1;
        if (harmonicsbarchart.getData() != null &&
                harmonicsbarchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set1.setColor(ColorList.SCOPE_LINE_COLOR[harmonicObj.getPhaseIndex()]);
            set1.setValues(values);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
//            set1.setColors(ColorList.SCOPE_LINE_COLOR[0]);
            set1.setColor(ColorList.SCOPE_LINE_COLOR[harmonicObj.getPhaseIndex()]);
            set1.setDrawValues(false);
            set1.setStackLabels(new String[]{"DC","1","5","10","15","20","25","30","35","40","45","50"});
            set1.setBarBorderColor(Color.WHITE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);
            harmonicsbarchart.setData(data);
            harmonicsbarchart.setFitBars(true);
        }

 //       harmonicsbarchart.setViewPortOffsets(50,20,20,50f); 搭配设置x的字体 xAxis.setTextSize(20f);
        harmonicsbarchart.invalidate();
    }

    /*public void setShowMeterData(MeterHarmonicObj harmonicObj) {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            float val = (float) (Math.random());
            values.add(new BarEntry(i, val));
        }
        BarDataSet set1;
        if (harmonicsbarchart.getData() != null &&
                harmonicsbarchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorList.SCOPE_LINE_COLOR[0]);
            set1.setDrawValues(false);
            set1.setStackLabels(new String[]{"THD","DC","1","5","10","15","20","25","30","35","40","45","50"});
            set1.setBarBorderColor(Color.WHITE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.1f);
            harmonicsbarchart.setData(data);
            harmonicsbarchart.setFitBars(true);
        }
        harmonicsbarchart.invalidate();
    }*/

}
