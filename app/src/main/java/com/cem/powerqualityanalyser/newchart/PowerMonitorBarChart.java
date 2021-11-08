package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.cem.powerqualityanalyser.chart.BaseBarChart;
import com.cem.powerqualityanalyser.meterobject.MeterHarmonicObj;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

public class PowerMonitorBarChart extends BaseBarChart{

    public PowerMonitorBarChart(Context context) {
        super(context);
    }

    public PowerMonitorBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PowerMonitorBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setShowMeterData(List<ModelLineData> lineDataList, int dataSetSize) {

        int size = lineDataList.size();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float val;
//            if(i == 0){
//                val = harmonicObj.getThd();
//            }

            val = Float.valueOf(lineDataList.get(i).getaValue().getValue());

            values.add(new BarEntry(i, val));
        }



        BarDataSet set1;
        if (getData() != null && getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) getData().getDataSetByIndex(0);
     //       set1.setColor(ColorList.SCOPE_LINE_COLOR[harmonicObj.getPhaseIndex()]);
            set1.setValues(values);
            getData().notifyDataChanged();
            notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
//            set1.setColors(ColorList.SCOPE_LINE_COLOR[0]);
 //           set1.setColor(ColorList.SCOPE_LINE_COLOR[harmonicObj.getPhaseIndex()]);
            set1.setDrawValues(false);
            set1.setStackLabels(new String[]{"DC","1","5","10","15","20","25","30","35","40","45","50"});
            setMaxVisibleValueCount(25);
            set1.setBarBorderColor(Color.WHITE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);
            setData(data);
            setFitBars(true);
        }

        //       harmonicsbarchart.setViewPortOffsets(50,20,20,50f); 搭配设置x的字体 xAxis.setTextSize(20f);
        invalidate();
    }
}
