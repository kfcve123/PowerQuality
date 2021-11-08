package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.MeterSudden_UP_Down;
import com.cem.powerqualityanalyser.libs.MeterThreeHarmonic;
import com.cem.powerqualityanalyser.libs.MeterThreeUnbalanced;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;


import java.util.ArrayList;

public class MonitorView extends MonitorBaseView{

    public MonitorView(Context context) {
        super(context);
    }

    public MonitorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MonitorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    boolean hign = false;
    public void setShowMeterData3(Object obj){

        if(!hign) {
            drawBarChart();
            hign = true;
        }
    }


    public void setShowMeterData(Object obj){
        float groupSpace = 0.4f;
        float barWidth = 0.12f; // x4 DataSet
        float barSpace = 0.08f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        // (0.12 + 0.08)  * 3 +  0.4 = 1.00
        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied

        int groupCount = 3;
        int startYear = 0;
        int endYear = startYear + groupCount;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

        float randomMultiplier = 1f;
        float monitorIndex = -1;

        if(obj instanceof MeterThreeHarmonic) {
            monitorIndex = 0;
            log.e("======MeterThreeHarmonic=====");
            MeterThreeHarmonic harmonicObj = (MeterThreeHarmonic) obj;

            log.e("======000====" + harmonicObj.getFrequency().getShowValue());
            log.e("======111====" + harmonicObj.getV_PhaseValue().getPhaseA().getValue());
            log.e("======222====" + harmonicObj.getV_PhaseValue().getPhaseB().getShowValue());
            log.e("======333====" + harmonicObj.getV_PhaseValue().getPhaseC().getShowValue());

            float voltValue = 0f;
            float volt1 = 0f;
            float volt2 = 0f;
            float dipsValue = 0f;

            values1.add(new BarEntry(0, 0.1f));
            values2.add(new BarEntry(0, 0.1f));
            values3.add(new BarEntry(0, 0.1f));
            values1.add(new BarEntry(1, 0.2f));
            values2.add(new BarEntry(1, 0.2f));
            values3.add(new BarEntry(1, 0.2f));


            values1.add(new BarEntry(2, 0.3f));
            values2.add(new BarEntry(2, 0.3f));
            values3.add(new BarEntry(2, 0.3f));

            /*values1.add(new BarEntry(0, harmonicObj.getV_PhaseValue().getPhaseA().getValue()));
            values2.add(new BarEntry(0, harmonicObj.getV_PhaseValue().getPhaseB().getValue()));
            values3.add(new BarEntry(0, harmonicObj.getV_PhaseValue().getPhaseC().getValue()));
            int monitor_harmonic_number = AppConfig.getInstance().getMonitor_Harmonic_Number();

            if(monitor_harmonic_number ==0){
                values1.add(new BarEntry(1, harmonicObj.getV_PhaseValue().getPhaseA().getValue()));
                values2.add(new BarEntry(1, harmonicObj.getV_PhaseValue().getPhaseB().getValue()));
                values3.add(new BarEntry(1, harmonicObj.getV_PhaseValue().getPhaseC().getValue()));
            }else if(monitor_harmonic_number ==1){
                values1.add(new BarEntry(1, harmonicObj.getV_THD_PhaseValue().getPhaseA().getValue()));
                values2.add(new BarEntry(1, harmonicObj.getV_THD_PhaseValue().getPhaseB().getValue()));
                values3.add(new BarEntry(1, harmonicObj.getV_THD_PhaseValue().getPhaseC().getValue()));
            }else{
                monitor_harmonic_number = monitor_harmonic_number +  2;
                values1.add(new BarEntry(1, harmonicObj.getListV_Phase().getValueA().get(monitor_harmonic_number)));
                values2.add(new BarEntry(1, harmonicObj.getListV_Phase().getValueA().get(monitor_harmonic_number)));
                values3.add(new BarEntry(1, harmonicObj.getListV_Phase().getValueA().get(monitor_harmonic_number)));
            }


            values1.add(new BarEntry(2, harmonicObj.getFrequency().getValue()));
            values2.add(new BarEntry(2, harmonicObj.getFrequency().getValue()));
            values3.add(new BarEntry(2, harmonicObj.getFrequency().getValue()));*/
            monitorBarChart.getXAxis().setValueFormatter(new MonitorXHarmonicValueFormatter());
            monitorBarChart.getAxisLeft().setAxisMaximum(1.2f);
            setLimitLines(0.8f,1.0f);
        }else if(obj instanceof MeterThreeUnbalanced){
            MeterThreeUnbalanced unbalancedObj = (MeterThreeUnbalanced) obj;
            monitorIndex = 1;
//            log.e("======000====" + unbalancedObj.getVneg().getShowValue());
//            log.e("======111====" + unbalancedObj.getV_fundWave().getPhaseA().getShowValue());
//            log.e("======222====" + unbalancedObj.getV_fundWave().getPhaseB().getShowValue());
//            log.e("======333====" + unbalancedObj.getV_fundWave().getPhaseC().getShowValue());

            float voltValue = 0f;
            float volt1 = 0f;
            float volt2 = 0f;
            float dipsValue = 0f;

            values1.add(new BarEntry(0, 0.1f));
            values2.add(new BarEntry(0, 0.1f));
            values3.add(new BarEntry(0, 0.1f));
            values1.add(new BarEntry(1, 0.2f));
            values2.add(new BarEntry(1, 0.2f));
            values3.add(new BarEntry(1, 0.2f));


            values1.add(new BarEntry(2, 0.3f));
            values2.add(new BarEntry(2, 0.3f));
            values3.add(new BarEntry(2, 0.3f));

            /*values1.add(new BarEntry(2, unbalancedObj.getFrequency().getValue()));
            values2.add(new BarEntry(2, unbalancedObj.getFrequency().getValue()));
            values3.add(new BarEntry(2, unbalancedObj.getFrequency().getValue()));

            values1.add(new BarEntry(0, unbalancedObj.getV_fundWave().getPhaseA().getValue()));
            values2.add(new BarEntry(0, unbalancedObj.getV_fundWave().getPhaseB().getValue()));
            values3.add(new BarEntry(0, unbalancedObj.getV_fundWave().getPhaseC().getValue()));

            values1.add(new BarEntry(1, unbalancedObj.getVneg().getValue()));
            values2.add(new BarEntry(1, unbalancedObj.getVneg().getValue()));
            values3.add(new BarEntry(1, unbalancedObj.getVneg().getValue()));*/

            monitorBarChart.getXAxis().setValueFormatter(new MonitorXUnbalanceValueFormatter());
            monitorBarChart.getAxisLeft().setAxisMaximum(1.2f);
            setLimitLines(0.8f,1.0f);
        }else if(obj instanceof MeterSudden_UP_Down) {
            monitorIndex = 2;
            MeterSudden_UP_Down suddenObj = (MeterSudden_UP_Down) obj;
//            log.e("======000====" + suddenObj.getValueFrequency().getShowValue());
//            log.e("======111====" + suddenObj.getPhaseValue().getPhaseA().getShowValue());
//            log.e("======222====" + suddenObj.getPhaseValue().getPhaseB().getShowValue());
//            log.e("======333====" + suddenObj.getPhaseValue().getPhaseC().getShowValue());

            float voltValue = 0f;
            float volt1 = 0f;
            float volt2 = 0f;
            float dipsValue = 0f;

            values1.add(new BarEntry(0, 0.1f));
            values2.add(new BarEntry(0, 0.1f));
            values3.add(new BarEntry(0, 0.1f));
            values1.add(new BarEntry(1, 0.2f));
            values2.add(new BarEntry(1, 0.2f));
            values3.add(new BarEntry(1, 0.2f));


            values1.add(new BarEntry(2, 0.3f));
            values2.add(new BarEntry(2, 0.3f));
            values3.add(new BarEntry(2, 0.3f));

           /* values1.add(new BarEntry(0, suddenObj.getValueFrequency().getValue()));
            values2.add(new BarEntry(0, suddenObj.getValueFrequency().getValue()));
            values3.add(new BarEntry(0, suddenObj.getValueFrequency().getValue()));

            values1.add(new BarEntry(1, suddenObj.getPhaseValue().getPhaseA().getValue()));
            values2.add(new BarEntry(1, suddenObj.getPhaseValue().getPhaseB().getValue()));
            values3.add(new BarEntry(1, suddenObj.getPhaseValue().getPhaseC().getValue()));

            values1.add(new BarEntry(2, suddenObj.getValueFrequency().getValue()));
            values2.add(new BarEntry(2, suddenObj.getValueFrequency().getValue()));
            values3.add(new BarEntry(2, suddenObj.getValueFrequency().getValue()));*/
            monitorBarChart.getXAxis().setValueFormatter(new MonitorXDipSweelsValueFormatter());
            monitorBarChart.getAxisLeft().setAxisMaximum(1.2f);
            setLimitLines(0.8f,1.0f);

        }
        BaseMonitorDataSet set1, set2, set3;

        if (monitorBarChart.getData() != null && monitorBarChart.getData().getDataSetCount() > 0) {

            set1 = (BaseMonitorDataSet) monitorBarChart.getData().getDataSetByIndex(0);
            set2 = (BaseMonitorDataSet) monitorBarChart.getData().getDataSetByIndex(1);
            set3 = (BaseMonitorDataSet) monitorBarChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            monitorBarChart.getData().notifyDataChanged();
            monitorBarChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BaseMonitorDataSet(values1, "Volt");
            set1.resetColors();
            set1.setColor(getResources().getColor(R.color.value_yellow,null));
            set1.setDrawValues(false);
            set1.setBarBorderColor(Color.WHITE);
            if(monitorIndex == 0){
                set2 = new BaseMonitorDataSet(values2, "Harmonic");
            }else if(monitorIndex ==1){
                set2 = new BaseMonitorDataSet(values2, "Unbalance");
            }else if(monitorIndex == 2){
                set2 = new BaseMonitorDataSet(values2, "Dip&Sweels");
            }else{
                set2 = new BaseMonitorDataSet(values2,"Dip&Sweels");
            }
        //    set2.setColor(getResources().getColor(R.color.value_blue,null));
            set2.setDrawValues(false);
            set2.setBarBorderColor(Color.WHITE);

            set3 = new BaseMonitorDataSet(values3, "Freq");
            set3.setColor(getResources().getColor(R.color.value_red,null));
            set3.setDrawValues(false);
            set3.setBarBorderColor(Color.WHITE);

            set1.setHighLightAlpha(255);
            set2.setHighLightAlpha(255);
            set3.setHighLightAlpha(255);

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            data.setHighlightEnabled(true);
            monitorBarChart.setData(data);
        }

//        MarkerView mv = new MarkerView(this, R.layout);
//        mv.setChartView(monitorBarChart); // For bounds control
//        monitorBarChart.setMarker(mv);

        monitorBarChart.setViewPortOffsets(50,20,20,50f);
        // specify the width each bar should have
        monitorBarChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        monitorBarChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        monitorBarChart.getXAxis().setAxisMaximum(startYear + monitorBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        monitorBarChart.groupBars(startYear, groupSpace, barSpace);
        monitorBarChart.invalidate();
    }


    public void setShowMeterData2(Object obj) {

        /*float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

        if(obj instanceof MeterThreeHarmonic) {
            MeterThreeHarmonic harmonicObj = (MeterThreeHarmonic) obj;
            int size = harmonicObj.getDataCount();
            ArrayList<BarEntry> values = new ArrayList<>();

        }else if(obj instanceof MeterThreeUnbalanced){
            MeterThreeUnbalanced unbalancedObj = (MeterThreeUnbalanced) obj;


        }else if(obj instanceof MeterSudden_UP_Down){
            MeterSudden_UP_Down suddenObj = (MeterSudden_UP_Down) obj;
            BarDataSet set1, set2, set3;
            if (monitorBarChart.getData() != null &&
                    monitorBarChart.getData().getDataSetCount() > 0) {

                set1 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(0);
                set2 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(1);
                set3 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(2);
                set1.setValues(values1);
                set2.setValues(values2);
                set3.setValues(values3);
                monitorBarChart.getData().notifyDataChanged();
                monitorBarChart.notifyDataSetChanged();

            } else {
                set1 = new BarDataSet(values1, "Volt");
                set1.setColor(getResources().getColor(R.color.value_yellow,null));
                set2 = new BarDataSet(values2, "Freq");
                set2.setColor(getResources().getColor(R.color.value_blue,null));
                set3 = new BarDataSet(values3, "Unbalance");
                set3.setColor(getResources().getColor(R.color.value_red,null));

                BarData data = new BarData(set1, set2, set3);
                data.setValueFormatter(new LargeValueFormatter());
                data.setValueTypeface(tfLight);
                monitorBarChart.setData(data);
            }

        }
        monitorBarChart.getBarData().setBarWidth(0.1f);
        monitorBarChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        monitorBarChart.getXAxis().setAxisMaximum(0 + monitorBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * 1);
        monitorBarChart.groupBars(0, groupSpace, barSpace);
        monitorBarChart.invalidate();*/
  //      drawBarChart();
    }

    private void drawBarChart(){
        float groupSpace = 0.4f;
        float barWidth = 0.12f; // x4 DataSet
        float barSpace = 0.08f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        // (0.12 + 0.08)  * 3 +  0.4 = 1.00
        // (barSpace + barWidth) * 5 + groupSpace = 1
        // multiplied by 5 because there are 5 five bars
        // labels will be centered as long as the equation is satisfied

        int groupCount = 3;
        int startYear = 0;
        int endYear = startYear + groupCount;

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();

        float randomMultiplier = 1f;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        BarDataSet set1, set2, set3;

        if (monitorBarChart.getData() != null && monitorBarChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) monitorBarChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            monitorBarChart.getData().notifyDataChanged();
            monitorBarChart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            monitorBarChart.setData(data);
        }
        monitorBarChart.getXAxis().setValueFormatter(new MonitorXDipSweelsValueFormatter());
        monitorBarChart.getData().setHighlightEnabled(true);

        monitorBarChart.setViewPortOffsets(50,20,20,50f);
        // specify the width each bar should have
        monitorBarChart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        monitorBarChart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        monitorBarChart.getXAxis().setAxisMaximum(startYear + monitorBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        monitorBarChart.groupBars(startYear, groupSpace, barSpace);
        monitorBarChart.invalidate();
    }


}
