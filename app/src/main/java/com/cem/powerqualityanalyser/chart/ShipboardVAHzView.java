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

public class ShipboardVAHzView extends MoreChartBase{

    private boolean newData;
    protected ShipbroadType shipbroadType = ShipbroadType.VrmsI;
    protected ShipbroadType lastMode = ShipbroadType.VrmsI;
    public enum ShipbroadType {
        VrmsI, Arms, Hz,VrmsII
    }

    public boolean isNewData() {
        return newData;
    }

    public void setNewData(boolean newData) {
        this.newData = newData;
    }

    public ShipboardVAHzView(Context context) {
        super(context);
    }

    public ShipboardVAHzView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShipboardVAHzView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ShipboardVAHzView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setShipModeIndex(int positio){
        switch (positio) {
            case 0:
                setShipMode(ShipbroadType.VrmsI);
                break;
            case 1:
                setShipMode(ShipbroadType.Arms);
                break;
            case 2:
                setShipMode(ShipbroadType.Hz);
                break;
            case 3:
                setShipMode(ShipbroadType.VrmsII);
                break;
        }
    }

    private  void setShipMode(ShipbroadType shipMode) {
        this.shipbroadType = shipMode;
        if (lastMode != shipMode) {
            lastMode = shipMode;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
        switch (shipbroadType){
            case VrmsI:
                showL1L2L3L4(true,true,true,false);
                this.unit = "V";
                break;
            case Hz:
                showL1L2L3L4(true,false,false,false);
                this.unit = "Hz";
                break;
            case Arms:
                showL1L2L3L4(true,true,true,true);
                this.unit = "A";
                break;
            case VrmsII:
                showL1L2L3L4(true,true,true,true);
                this.unit = "V";
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
                       if (newData) {
                            set.clear();
                        }

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


}
