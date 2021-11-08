package com.cem.powerqualityanalyser.newchart;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

/**
 * 新的
 */
public class UnbalanceTrendView extends UnbalanceMoreChartBase {

    private boolean newData;

    public UnbalanceTrendView(Context context) {
        super(context);
    }

    public UnbalanceTrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnbalanceTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UnbalanceTrendView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setNewData(boolean b) {
        this.newData = b;
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

    public void setInrushTopView(float textSize, String s) {
        if(inrush_config_tv!=null){
            inrush_config_tv.setText(s);
            inrush_config_tv.setTextSize(textSize);
        }
    }

    public void setInrushHz(String hz){
        if(tv_hz!=null)
            tv_hz.setText(hz);
    }


    public void setVoltsModeIndex(int wir_index,int wir_right_index,int positio) {
        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://2V
                    case 1://2A
                    case 2://L1
                    case 3://N
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,false,false);
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){//切换右边选项
                    case 0://U
                    case 1://A
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,false,false,false);
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                    case 1://3A
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,true,false);
                        break;
                    case 2://L1
                    case 3://L2
                    case 4://N
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,false,false);
                        break;
                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
            case 0://3QWYE
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                    case 2://3A
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,true,true);
                        break;
                    case 1://3U
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,true,false);
                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                    case 6://N
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,false,false);
                    break;
                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                    case 1://3U
                    case 2://3A
                        setTopLeftTitle("L1","L2","L3","N");
                        showL1L2L3L4(true,true,true,false);
                        break;
                }
                break;
            //4V
            //4A
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


    private void setLineData2(List<List<Float>> dataList, boolean newData) {
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

    public void showMeterAllParamObj(ModelLineData meterAllObj) {
        if (meterAllObj != null){
            List<Float> values = ojbToFloatValues(meterAllObj);
//            showTopViewValue(meterAllObj);
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

}
