package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.meterobject.MeterAllParamObj;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryChart extends VoltAmpsMoreChartBase {
    private final int XRangeMaximum = 100;
    private final int labelCount = 10;
    private String unit = "";
    private TextView tv_time;
    private TextView tv_freq;
    public HistoryChart(Context context) {
        super(context);
    }

    public HistoryChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HistoryChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void initChartListener() {
        super.initChartListener();
        rootView.findViewById(R.id.selectTime).setVisibility(VISIBLE);
        tv_time = rootView.findViewById(R.id.tv_time);
        tv_freq = rootView.findViewById(R.id.tv_freq);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                textview_l1.setText("L1    " + showValue  + "  " + unit);
                tv_time.setText(getFormattedDate(e.getX()));
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
        mChart4.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String showValue = new MeterData(e.getY()).getShowValue();
                Log.e("Chart4 Select",e.getX() + "    " + e.getY());
                textview_l4.setText("N    " + showValue + "  " + unit);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart.getXAxis().setLabelCount(labelCount,true);
        mChart2.getXAxis().setLabelCount(labelCount,true);
        mChart3.getXAxis().setLabelCount(labelCount,true);
        mChart4.getXAxis().setLabelCount(labelCount,true);

        mChart4.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value >= 0 && value < mChart.getLineData().getEntryCount()){
                    return getFormattedDate(value);
                }
                return super.getFormattedValue(value);
            }
        });
        mChart.setViewPortOffsets(40,0,25,0);
        mChart2.setViewPortOffsets(40,0,25,0);
        mChart3.setViewPortOffsets(40,0,25,0);
        mChart4.setViewPortOffsets(40,0,25,20);
    }
    public void setFreqValue(String freq){
        if (freq.isEmpty()){
            tv_freq.setText("");
        }else{
            tv_freq.setText("Freq  " + freq + "Hz");
        }
    }
    private Date startDate,endDate;

    public void setXDate(Date startDate,Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }
    private String getFormattedDate(float value){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss

        try {
            if (startDate != null && endDate != null && value > 0){
                long l = (endDate.getTime() - startDate.getTime()) / mChart.getLineData().getEntryCount();
                long v = (long) (value * l) + startDate.getTime();
                Date date = new Date(v);
                return simpleDateFormat.format(date);
            }else if (value == 0 && startDate != null){
                return simpleDateFormat.format(startDate);
            }
        }catch (Exception e){
            return "" + value;
        }
        return "" + value;
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
    private List<Float> ojbToFloatValues(MeterAllParamObj meterAllObj){
        List<Float> values = new ArrayList<>();
        if(meterAllObj.getL1()!=null)
            values.add(meterAllObj.getL1().getValue());
        if(meterAllObj.getL2()!=null)
            values.add(meterAllObj.getL2().getValue());
        if(meterAllObj.getL3()!=null)
            values.add(meterAllObj.getL3().getValue());
        if(meterAllObj.getN()!=null)
            values.add(meterAllObj.getN().getValue());
        return values;
    }


    private void showTopViewValue(MeterAllParamObj meterAllObj){
        unit = meterAllObj.getUnit();
        if(meterAllObj.getL1()!=null){
            textview_l1.setText("L1    " + meterAllObj.getL1().getShowValue()  + "  " + meterAllObj.getUnit());
        }else {
            textview_l1.setText("L1    ");
        }
        if(meterAllObj.getL2()!=null){
            textview_l2.setText("L2    " + meterAllObj.getL2().getShowValue()  + "  " + meterAllObj.getUnit());
        }else{
            textview_l2.setText("L2    ");
        }
        if(meterAllObj.getL3()!=null){
            textview_l3.setText("L3    " + meterAllObj.getL3().getShowValue()  + "  " + meterAllObj.getUnit());
        }else{
            textview_l3.setText("L3    ");
        }
        if(meterAllObj.getN()!=null){
            textview_l4.setText("N    ");
        }else{
            textview_l4.setText("N    ");

        }
    }

    public void clearValues(){
        for (int i = 0; i < charts.size(); i++) {
            VoltsAmpsHertzLineChart chart = charts.get(i);
            LineData lineData = chart.getLineData();
            if (lineData != null){
                lineData.clearValues();
                chart.highlightValues(null);
            }
        }
    }
    public void showMeterAllParamObj(HistoryMeterAllObj meterAllObj) {

        if (meterAllObj != null) {
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
                        if (values.size() > i) {
                            chart.getAxisLeft().resetAxisMinimum();
                            chart.getAxisLeft().resetAxisMaximum();

                            lineData.addEntry(new Entry(set.getEntryCount(), values.get(i)), 0);
                            lineData.notifyDataChanged();
                            chart.notifyDataSetChanged();
                            chart.setVisibleXRangeMaximum(XRangeMaximum);
                            chart.moveViewToX(lineData.getEntryCount() - XRangeMaximum);

                        }else{
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
            }
        }
    }


    public void showMeterAllParamObjList(List<HistoryMeterAllObj> meterAllObjList) {
        if (meterAllObjList != null) {

            for (int j = 0; j < meterAllObjList.size(); j++) {

                HistoryMeterAllObj meterAllObj = meterAllObjList.get(j);
                List<Float> values = ojbToFloatValues(meterAllObj);
                if (j == meterAllObjList.size() - 1){
                    showTopViewValue(meterAllObj);

                }
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
                            if (values.size() > i) {
                                chart.getAxisLeft().resetAxisMinimum();
                                chart.getAxisLeft().resetAxisMaximum();

                                lineData.addEntry(new Entry(set.getEntryCount(), values.get(i)), 0);

                            }else{
                                chart.getAxisLeft().setAxisMinimum(-1);
                                chart.getAxisLeft().setAxisMaximum(1);
                                lineData.addEntry(new Entry(set.getEntryCount(), -2), 0);
//                                lineData.notifyDataChanged();
//                                chart.notifyDataSetChanged();
//                                chart.setVisibleXRangeMaximum(XRangeMaximum);
//                                chart.moveViewToX(lineData.getEntryCount() - XRangeMaximum);
                            }

                        }
                    }
                }

            }

            for (int i = 0; i < charts.size(); i++) {
                VoltsAmpsHertzLineChart chart = charts.get(i);
                LineData lineData = chart.getLineData();
                lineData.notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.setVisibleXRangeMaximum(XRangeMaximum);
                chart.moveViewToX(lineData.getEntryCount() - XRangeMaximum);
            }

            if (meterAllObjList != null && meterAllObjList.size() > 0 && meterAllObjList.get(0).getN() == null){
                mChart4.getLineData().clearValues();
            }

        }
    }


    /**
     * 拦截父控件的触摸事件，分发给所有chartView
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mChart.dispatchTouchEvent(ev);
        mChart2.dispatchTouchEvent(ev);
        mChart3.dispatchTouchEvent(ev);
        mChart4.dispatchTouchEvent(ev);
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
