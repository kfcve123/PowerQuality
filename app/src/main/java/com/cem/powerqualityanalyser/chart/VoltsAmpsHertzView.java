package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

/**
 * 旧的
 */
public class VoltsAmpsHertzView extends MoreChartBase{

    private boolean newData;
    protected VoltsAmpsHertzType voltsType = VoltsAmpsHertzType.VrmsI;
    protected VoltsAmpsHertzType lastMode = VoltsAmpsHertzType.VrmsI;
    public enum VoltsAmpsHertzType {
        VrmsI, Vrkp, CFV, Arms, Apk, CFA, Hz, VrmsII, Vpk
    }

    public boolean isNewData() {
        return newData;
    }

    public void setNewData(boolean newData) {
        this.newData = newData;
    }

    public VoltsAmpsHertzView(Context context) {
        super(context);
    }

    public VoltsAmpsHertzView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoltsAmpsHertzView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VoltsAmpsHertzView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    public void setVoltsModeIndex(int positio){
        switch (positio) {
            case 0:
                setVoltsMode(VoltsAmpsHertzType.VrmsI);
                break;
            case 1:
                setVoltsMode(VoltsAmpsHertzType.Vrkp);
                break;
            case 2:
                setVoltsMode(VoltsAmpsHertzType.CFV);
                break;
            case 3:
                setVoltsMode(VoltsAmpsHertzType.Arms);
                break;
            case 4:
                setVoltsMode(VoltsAmpsHertzType.Apk);
                break;
            case 5:
                setVoltsMode(VoltsAmpsHertzType.CFA);
                break;
            case 6:
                setVoltsMode(VoltsAmpsHertzType.Hz);
                break;
            case 7:
                setVoltsMode(VoltsAmpsHertzType.VrmsII);
                break;
            case 8:
                setVoltsMode(VoltsAmpsHertzType.Vpk);
                break;
        }
    }

    private  void setVoltsMode(VoltsAmpsHertzType voltType) {
        this.voltsType = voltType;
        if (lastMode != voltsType) {
            lastMode = voltsType;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
        switch (voltsType){
            case VrmsI:
                showL1L2L3L4(true,true,true,true);
                break;
            case Vrkp:
            case Arms:
            case Apk:

                break;
            case Hz:
                showL1L2L3L4(true,false,false,false);
                break;
            case VrmsII:
            case CFA:
            case Vpk:
            case CFV:
                showL1L2L3L4(true,true,true,false);
                break;
        }
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

   /* private LineDataSet createSet(int index) {
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
            if (lineData == null) {
                lineData = new LineData();
                lineData.setValueTypeface(tfRegular);
                chart.setData(lineData);
            }

            if (dataList != null) {
                if (lineData != null) {
                    //             lineData.getDataSets().clear();
                    for (int i = 0; i < dataList.size(); i++) {
                        ILineDataSet set = lineData.getDataSetByIndex(i);
                        if (set == null) {
                            set = createSet(i);
                            lineData.addDataSet(set);
                        }
                        if (newData)
                            set.clear();

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

    public void showMeterAllParamObj(ModelLineData meterAllObj) {
        if (meterAllObj != null){
            List<Float> values = ojbToFloatValues(meterAllObj);
            showTopViewValue(meterAllObj);
            for (int i = 0; i < charts.size(); i++) {
                VoltsAmpsHertzLineChart chart = charts.get(i);
                LineData lineData = chart.getLineData();
                if (newData && lineData!=null) {
                    lineData.clearValues();
                    chart.highlightValues(null);
                    iLastEntry = 0;
                }
                if (lineData == null) {
                    lineData = new LineData();
                    lineData.setValueTypeface(tfRegular);
                    chart.setData(lineData);
                }

                if (lineData != null) {
                    ILineDataSet set = lineData.getDataSetByIndex(0);
                    if (set == null) {
                        set = createSet(i);
                        lineData.addDataSet(set);
                    }

                       /* if (newData)
                            set.clear();*/
//                        lineData.addEntry(new Entry(set.getEntryCount(), values.get(0)), 0);
                    if (values.size() > i) {
                        chart.getAxisLeft().resetAxisMinimum();
                        chart.getAxisLeft().resetAxisMaximum();
                        lineData.addEntry(new Entry(set.getEntryCount(), values.get(i)), 0);
                        lineData.notifyDataChanged();
                        chart.notifyDataSetChanged();
//                          chart.moveViewToX(lineData.getEntryCount());
                        chart.setVisibleXRangeMaximum(XRangeMaximum);
                        chart.moveViewToX(lineData.getEntryCount() - XRangeMaximum);
                    } else {
                        chart.getAxisLeft().setAxisMinimum(-1);
                        chart.getAxisLeft().setAxisMaximum(1);
                        lineData.addEntry(new Entry(set.getEntryCount(), -2), 0);
                        lineData.notifyDataChanged();
                        chart.notifyDataSetChanged();
                        chart.setVisibleXRangeMaximum(XRangeMaximum);
                        chart.moveViewToX(lineData.getEntryCount() - XRangeMaximum);
                    }
                }

            }
            newData = false;
        }

    }


    private void showTopViewValue(ModelLineData meterAllObj){
        if(meterAllObj.getaValue()!=null)
            textview_l1.setText(meterAllObj.getaValue().getValue());
        if(meterAllObj.getbValue()!=null)
            textview_l2.setText(meterAllObj.getbValue().getValue());
        if(meterAllObj.getcValue()!=null)
            textview_l3.setText(meterAllObj.getcValue().getValue());
        if(meterAllObj.getnValue()!=null)
            textview_l4.setText(meterAllObj.getnValue().getValue());
    }

}
