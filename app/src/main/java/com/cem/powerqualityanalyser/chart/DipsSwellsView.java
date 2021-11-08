package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import com.cem.powerqualityanalyser.meterobject.MeterSuddenObj;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.List;

public class DipsSwellsView extends MoreChartDip{

    private boolean newData;

    public boolean isNewData() {
        return newData;
    }

    public void setNewData(boolean newData) {
        this.newData = newData;
    }

    public DipsSwellsView(Context context) {
        super(context);
    }

    public DipsSwellsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DipsSwellsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DipsSwellsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){

            }else if(msg.what == 2){

            }
        }
    };

    /*private LineDataSet createSet(int index) {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorList.SCOPE_LINE_COLOR[index]);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(ColorList.CURSOR_LINE_COLOR[0]);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighlightLineWidth(4f);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }*/

    private void setLineData2(List<List<Float>> dataList,boolean newData) {
        for(LineChart chart:charts) {
            LineData lineData = chart.getLineData();
            if (newData && lineData!=null)
                lineData.clearValues();
            if (lineData == null) {
                lineData = new LineData();
                lineData.setValueTypeface(tfRegular);
                chart.setData(lineData);
            }

            if (dataList != null) {
                if (lineData != null) {
                    for (int i = 0; i < dataList.size(); i++) {
                        ILineDataSet set = lineData.getDataSetByIndex(i);
                        if (set == null) {
                            set = createSet(i);
                            lineData.addDataSet(set);
                        }
                        /*if (newData)
                            set.clear();*/

                        if (dataList.get(i).size() > 0) {
                            for (int j = 0; j < dataList.get(i).size(); j++) {
                                lineData.addEntry(new Entry(set.getEntryCount(), dataList.get(i).get(j)), i);
                            }
                        } else {
                            log.e("=======dataList.get(i).size()=====" + dataList.get(i).size());
                        }
                    }
                    lineData.notifyDataChanged();
                    // let the chart know it's data has changed
                    chart.notifyDataSetChanged();
                    // move to the latest entry
                    chart.moveViewToX(lineData.getEntryCount());
                }
            }
        }
    }


    private List<List<Float>> objTolist(MeterSuddenObj suddenObj) {
        List<List<Float>> dataList = new ArrayList<>();
        dataList.add(suddenObj.getL1());
        dataList.add(suddenObj.getL2());
        dataList.add(suddenObj.getL3());
        return dataList;
    }


    public void setShowMeterSudden(MeterSuddenObj suddenObj) {

        List<List<Float>> dataList = objTolist(suddenObj);
        if (suddenObj != null) {
            for (int i = 0; i < 3; i++) {
                LineData lineData = charts.get(i).getLineData();
                if (lineData == null) {
                    lineData = new LineData();
                    lineData.setValueTypeface(tfRegular);
                    charts.get(i).setData(lineData);
                }

                if (dataList != null) {
                    if (lineData != null) {
                        ILineDataSet set = lineData.getDataSetByIndex(0);
                        if (set == null) {
                            set = createSet(i);
                            lineData.addDataSet(set);
                        }
                        if (newData)
                            set.clear();
                        float[] runLastValues = new float[3];
                        if (dataList.get(i).size() > 0) {
                            for (int j = 0; j < dataList.get(i).size(); j++) {
                                lineData.addEntry(new Entry(set.getEntryCount(), dataList.get(i).get(j)), 0);
                                if (j == dataList.get(i).size() - 1){
                                    runLastValues[i] = dataList.get(i).get(j);
                                }
                            }
                            showTextRunValue(runLastValues);
                        } else {
                            log.e("=======dataList.get(i).size()=====" + dataList.get(i).size());
                        }
                        lineData.notifyDataChanged();
                        charts.get(i).notifyDataSetChanged();
                        charts.get(i).setVisibleXRangeMaximum(XRangeMaximum);
                        charts.get(i).moveViewToX(lineData.getEntryCount() - XRangeMaximum);
                    }
                }
            }

        }
    }

    /* private void showMeterAllParamObj(MeterAllParamObj meterAllObj) {
        if (meterAllObj != null){
            List<Float> values = ojbToFloatValues(meterAllObj);
            showTopViewValue(meterAllObj);
            for (int i = 0; i < charts.size(); i++) {
                VoltsAmpsHertzLineChart chart = charts.get(i);
                LineData lineData = chart.getLineData();
                if (lineData == null) {
                    lineData = new LineData();
                    lineData.setValueTypeface(tfRegular);
                    chart.setData(lineData);
                }
                if (meterAllObj != null) {
                    if (lineData != null) {
                        ILineDataSet set = lineData.getDataSetByIndex(0);
                        if (set == null) {
                            set = createSet(i);
                            lineData.addDataSet(set);
                        }
                        if (newData)
                            set.clear();
                        lineData.addEntry(new Entry(set.getEntryCount(), values.get(0)), 0);
                        lineData.notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.moveViewToX(lineData.getEntryCount());
                    }
                }
            }
            newData = false;
        }

    }

    private  void setShowMeterSudden2(MeterSuddenObj suddenObj) {
        if(suddenObj!=null){
            for (int i = 0; i < charts.size(); i++) {
                VoltsAmpsHertzLineChart chart = charts.get(i);
                LineData lineData = chart.getLineData();
                if (lineData == null) {
                    lineData = new LineData();
                    lineData.setValueTypeface(tfRegular);
                    chart.setData(lineData);
                }
                if (suddenObj != null) {
                    if (lineData != null) {
                        ILineDataSet set = lineData.getDataSetByIndex(i);
                        if (set == null) {
                            set = createSet(i);
                            lineData.addDataSet(set);
                        }
                        if (newData)
                            set.clear();
                        if(i == 0)
                            lineData.addEntry(new Entry(set.getEntryCount(), suddenObj.getFrequency()), i);
                        else if(i == 1){
                            for(float value : suddenObj.getL1()){
                                lineData.addEntry(new Entry(set.getEntryCount(), value), i);
                            }
                        } else if(i == 2) {
                            for(float value : suddenObj.getL2()){
                                lineData.addEntry(new Entry(set.getEntryCount(), value), i);
                            }
                        } else if(i == 3) {
                            for(float value : suddenObj.getL3()){
                                lineData.addEntry(new Entry(set.getEntryCount(), value), i);
                            }
                        }
                        lineData.notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.moveViewToX(lineData.getEntryCount());
                    }
                }
            }


        }

    }*/

}
