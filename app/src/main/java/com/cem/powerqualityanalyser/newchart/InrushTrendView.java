package com.cem.powerqualityanalyser.newchart;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.enums.TrendRightModeEnum;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

/**
 * 新的
 */
public class InrushTrendView extends InrushMoreChartBase {

    private boolean newData;

    public InrushTrendView(Context context) {
        super(context);
    }

    public InrushTrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InrushTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InrushTrendView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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


    /**
     * 设置顶部 L1,L2,L3,N的显示
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     */
    public void setInrushModeIndex(int wir_index,int firstPopIndex,int secondPopIndex) {
        switch (wir_index) {
            case 9://1Q +NEUTRAL
                if(firstPopIndex == 2){
                    setTopLeftTitle("MAX", "AVG", "MIN", "");
                    showL1L2L3L4(true, true, true, false);
                }else {
                    if (secondPopIndex == -1) {
                        setTopLeftTitle("L1", "N", "", "");
                        showL1L2L3L4(true, true, false, false);
                    } else {
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }
                break;
            case 8://1Q IT NO NEUTRAL
                if (firstPopIndex == 0) {
                    if(secondPopIndex == -1) {
                        setTopLeftTitle("L1L2", "", "", "");
                        showL1L2L3L4(true, false, false, false);
                    }else{
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }else if(firstPopIndex == 2){
                    setTopLeftTitle("MAX", "AVG", "MIN", "");
                    showL1L2L3L4(true, true, true, false);
                }else{
                    if(secondPopIndex == -1) {
                        setTopLeftTitle("L1", "", "", "");
                        showL1L2L3L4(true, false, false, false);
                    }else{
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }
                break;
            case 7://1Q SPLIT PHASE
                if(firstPopIndex == 2){
                    setTopLeftTitle("MAX", "AVG", "MIN", "");
                    showL1L2L3L4(true, true, true, false);
                }else {
                    if (secondPopIndex == -1) {
                        setTopLeftTitle("L1", "L2", "N", "");
                        showL1L2L3L4(true, true, true, false);
                    } else {
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
            case 0://3QWYE
                if(firstPopIndex == 2){
                    setTopLeftTitle("MAX", "AVG", "MIN", "");
                    showL1L2L3L4(true, true, true, false);
                }else {
                    if (secondPopIndex == -1) {
                        setTopLeftTitle("L1", "L2", "L3", "N");
                        showL1L2L3L4(true, true, true, true);
                    } else {
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                if (firstPopIndex == 0) {
                    if (secondPopIndex == -1) {
                        setTopLeftTitle("L1L2", "L2L3", "L3L1", "");
                        showL1L2L3L4(true, true, true, false);
                    } else {
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }else if(firstPopIndex == 2){
                    setTopLeftTitle("MAX", "AVG", "MIN", "");
                    showL1L2L3L4(true, true, true, false);
                } else {
                    if (secondPopIndex == -1) {
                        setTopLeftTitle("L1", "L2", "L3", "");
                        showL1L2L3L4(true, true, true, false);
                    } else {
                        setTopLeftTitle("MAX", "AVG", "MIN", "");
                        showL1L2L3L4(true, true, true, false);
                    }
                }
                break;
        }

    }

    /**
     * 打开光标时候的顶部数据显示
     * @param wir_index
     * @param wir_right_index
     * @param positio
     */
    public void setInrushCursorIndex(int wir_index,int wir_right_index,int positio) {
        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://2V
                    case 1://2A
                    case 2://L1
                        setTopLeftTitle("L1","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 3://N
                        setTopLeftTitle("N","","","");
                        showL1L2L3L4(true,false,false,false);
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
                        setTopLeftTitle("L1","L2","","");
                        showL1L2L3L4(true,true,false,false);
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 3://L2
                        setTopLeftTitle("L2","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 4://N
                        setTopLeftTitle("N","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
            case 0://3QWYE
                switch (wir_right_index) {//切换右边选项
                    case 0://4V
                    case 1://4A
                        setTopLeftTitle("L1","L2","L3","");
                        showL1L2L3L4(true,true,true,false);
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 3://L2
                        setTopLeftTitle("L2","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 4://L3
                        setTopLeftTitle("L3","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 5://N
                        setTopLeftTitle("N","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3U
                    case 1://3A
                        setTopLeftTitle("L1","L2","L3","");
                        showL1L2L3L4(true,true,true,false);
                        break;
                    case 2://L1L2
                        setTopLeftTitle("L1","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 3://L2L3
                        setTopLeftTitle("L2","","","");
                        showL1L2L3L4(true,false,false,false);
                        break;
                    case 4://L3L1
                        setTopLeftTitle("L3","","","");
                        showL1L2L3L4(true,false,false,false);
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


    /**
     * 实时显示最新的数据
     * @param meterAllObj
     */
    private void showTopViewValue(ModelLineData meterAllObj){
        if(meterAllObj.getaValue()!=null) {
            if(meterAllObj.getaValue().getValue_Unit()!= null)
                unitL1 = meterAllObj.getaValue().getValue_Unit();
            else
                unitL1 = "";
            textview_l1.setText(meterAllObj.getaValue().getValue() + unitL1);
        }
        if(meterAllObj.getbValue()!=null) {
            if(meterAllObj.getbValue().getValue_Unit()!= null)
                unitL2 = meterAllObj.getbValue().getValue_Unit();
            else
                unitL2 = "";
            textview_l2.setText(meterAllObj.getbValue().getValue() + unitL2);
        }
        if(meterAllObj.getcValue()!=null) {
            if(meterAllObj.getcValue().getValue_Unit()!= null)
                unitL3 = meterAllObj.getcValue().getValue_Unit();
            else
                unitL3 = "";
            textview_l3.setText(meterAllObj.getcValue().getValue() + unitL3);
        }
        if(meterAllObj.getnValue()!=null) {
            if(meterAllObj.getnValue().getValue_Unit()!= null)
                unitLn = meterAllObj.getnValue().getValue_Unit();
            else
                unitLn = "";
            textview_l4.setText(meterAllObj.getnValue().getValue() + unitLn);
        }
    }

    public void showMeterAllParamObj(ModelLineData meterAllObj,boolean showCursor) {
  //      log.e("----------showMeterAllParamObj-------------");
        if (meterAllObj != null){
            List<Float> values = ojbToFloatValues(meterAllObj);
            if(!showCursor)
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

    /**
     * 曲线条数修改
     * @param rightMode
     */
    public void updateTrendRightMode(TrendRightModeEnum rightMode){
        switch (rightMode){
            case VL1:
            case AL1:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(GONE);
                setNChartVisable(GONE);
                mChart.getAxisLeft().setLabelCount(5);
                mChart.getXAxis().setEnabled(true);
                mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart.setViewPortOffsets(40,0,15,20);
                mChart.setExtraBottomOffset(6);//底部文字被遮挡处理
                break;
            case L1L2:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(GONE);
                setNChartVisable(GONE);
                mChart.getAxisLeft().setLabelCount(5);
                mChart.getXAxis().setEnabled(true);
                mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart.setViewPortOffsets(40,0,15,20);
                mChart.setExtraBottomOffset(6);//底部文字被遮挡处理
                setRightLegend("L1L2","","","",13);

                break;
            case VL2:
            case AL2:
                setL1ChartVisable(GONE);
                setL2ChartVisable(VISIBLE);
                setL3ChartVisable(GONE);
                setNChartVisable(GONE);

                mChart2.getAxisLeft().setLabelCount(5);
                mChart2.getXAxis().setEnabled(true);
                mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart2.setViewPortOffsets(40,0,15,20);
                mChart2.setExtraBottomOffset(6);//底部文字被遮挡处理
                break;
            case L2L3:
                setL1ChartVisable(GONE);
                setL2ChartVisable(VISIBLE);
                setL3ChartVisable(GONE);
                setNChartVisable(GONE);

                mChart2.getAxisLeft().setLabelCount(5);
                mChart2.getXAxis().setEnabled(true);
                mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart2.setViewPortOffsets(40,0,15,20);
                mChart2.setExtraBottomOffset(6);//底部文字被遮挡处理
                setRightLegend("","L2L3","","",13);
                break;
            case VL3:
            case AL3:
                setL1ChartVisable(GONE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(VISIBLE);
                setNChartVisable(GONE);

                mChart3.getAxisLeft().setLabelCount(5);
                mChart3.getXAxis().setEnabled(true);
                mChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart3.setViewPortOffsets(40,0,15,20);
                mChart3.setExtraBottomOffset(6);//底部文字被遮挡处理
                break;
            case L3L1:
                setL1ChartVisable(GONE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(VISIBLE);
                setNChartVisable(GONE);

                mChart3.getAxisLeft().setLabelCount(5);
                mChart3.getXAxis().setEnabled(true);
                mChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart3.setViewPortOffsets(40,0,15,20);
                mChart3.setExtraBottomOffset(6);//底部文字被遮挡处理
                setRightLegend("","","L3L1","",13);
                break;
            case N:
                setL1ChartVisable(GONE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(GONE);
                setNChartVisable(VISIBLE);
                break;
            case A2L1N:
            case V2L1N:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(GONE);
                setL3ChartVisable(GONE);
                setNChartVisable(VISIBLE);

                mChart.getXAxis().setEnabled(false);
                mChart.setViewPortOffsets(40,0,20,0);
                mChart4.setViewPortOffsets(40,0,20,20);
                mChart.getAxisLeft().setLabelCount(2);
                mChart4.getAxisLeft().setLabelCount(2);
                setRightLegend("L1","","","N",0);
                break;

            case A3L1L2N:
            case V3L1L2N:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(VISIBLE);
                setL3ChartVisable(GONE);
                setNChartVisable(VISIBLE);
                mChart.getXAxis().setEnabled(false);
                mChart2.getXAxis().setEnabled(false);

                mChart.setViewPortOffsets(40,0,20,0);
                mChart2.setViewPortOffsets(40,0,20,0);
                mChart4.setViewPortOffsets(40,0,20,20);
                mChart.getAxisLeft().setLabelCount(2);
                mChart2.getAxisLeft().setLabelCount(2);
                mChart4.getAxisLeft().setLabelCount(2);
                setRightLegend("L1","L2","","N",0);

                break;
            case A3L1L2L2L3L3L1:
            case U3L1L2L2L3L3L1:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(VISIBLE);
                setL3ChartVisable(VISIBLE);
                setNChartVisable(GONE);

                mChart.getAxisLeft().setLabelCount(2);
                mChart2.getAxisLeft().setLabelCount(2);
                mChart3.getAxisLeft().setLabelCount(2);

                mChart.getXAxis().setEnabled(false);
                mChart2.getXAxis().setEnabled(false);
                mChart3.getXAxis().setEnabled(true);

                mChart.setViewPortOffsets(40,0,20,0);
                mChart2.setViewPortOffsets(40,0,20,0);
                mChart3.setViewPortOffsets(40,0,20,20);
                mChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart3.setExtraBottomOffset(6);//底部文字被遮挡处理
                setRightLegend("L1","L2","L3","",0);

                break;
            case A4L1L2L3N:
            case V4L1L2L3N:
            default:
                setL1ChartVisable(VISIBLE);
                setL2ChartVisable(VISIBLE);
                setL3ChartVisable(VISIBLE);
                setNChartVisable(VISIBLE);
                mChart.getXAxis().setEnabled(false);
                mChart2.getXAxis().setEnabled(false);
                mChart3.getXAxis().setEnabled(false);
                mChart.setViewPortOffsets(40,0,20,0);
                mChart2.setViewPortOffsets(40,0,20,0);
                mChart3.setViewPortOffsets(40,0,20,0);
                mChart4.setViewPortOffsets(40,0,20,20);
                mChart.getAxisLeft().setLabelCount(2);
                mChart2.getAxisLeft().setLabelCount(2);
                mChart3.getAxisLeft().setLabelCount(2);
                mChart4.getAxisLeft().setLabelCount(2);
                setRightLegend("L1","L2","L3","N",0);
        }
    }

}
