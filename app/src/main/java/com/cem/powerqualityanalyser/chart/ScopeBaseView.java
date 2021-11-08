package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class ScopeBaseView extends MPChartBaseView implements OnChartValueSelectedListener {

    protected TextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected ScopeType scopeType = ScopeType.VOLT;
    protected ScopeType lastMode = ScopeType.VOLT;

    protected ScopeLineChart mChart;
    protected int iLastEntry;
    protected boolean showCurson = true;
    //记录每条线的最大值
    protected float[] maxValue;
    protected float[] minValue;


    public enum ScopeType {
        VOLT, AMP, L1, L2, L3
    }

    public ScopeBaseView(Context context) {
        super(context);
    }

    public ScopeBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScopeBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScopeBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {

        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.scope_linechar_layout, this);
        mChart = view.findViewById(R.id.scopelinechart);
        mChart.setOnChartValueSelectedListener(this);
        textview_l1 = view.findViewById(R.id.textview_l1);
        textview_l2 = view.findViewById(R.id.textview_l2);
        textview_l3 = view.findViewById(R.id.textview_l3);
        textview_l4 = view.findViewById(R.id.textview_l4);

        //      mChart.setData(new LineData());

//        lineData.notifyDataChanged();
//        mChart.notifyDataSetChanged();

        setLineChart();
        setAxisLeft();
        setXAxis();
        setLegend();
        setDefaultValue();

    }

    private void setDefaultValue() {
        ArrayList<Entry> values1 = new ArrayList<>();
        int count = 5;
        for (int i = 0; i < count; i++) {
            float val = 0f;
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = 0f;
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = 0f;
            values3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = createSet(0);
            set2 = createSet(1);
            set3 = createSet(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    protected LineDataSet createSet(int index) {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorList.ALL_METER_TITLE_COLOR[index]);
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
//        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setMode(LineDataSet.Mode.LINEAR);

        //       set.setHighlightEnabled(false);
        return set;
    }

    private void setAxisLeft() {
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
//        leftAxis.setAxisMinimum(-500f);
//        leftAxis.setAxisMaximum(500f);
        leftAxis.setTextColor(getResources().getColor(R.color.colorwhite,null));
        leftAxis.setLabelCount(lableCount,true);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40,40,10);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
    }

    private void setLineChart() {
//        setTouchEnabled(boolean enabled) : 启用/禁用与图表的所有可能的触摸交互。
//        setDragEnabled(boolean enabled) : 启用/禁用拖动（平移）图表。
//        setScaleEnabled(boolean enabled) : 启用/禁用缩放图表上的两个轴。
//        setScaleXEnabled(boolean enabled) : 启用/禁用缩放在x轴上。
//        setScaleYEnabled(boolean enabled) : 启用/禁用缩放在y轴。
//        setPinchZoom(boolean enabled) : 如果设置为true，捏缩放功能。 如果false，x轴和y轴可分别放大。
//        setDoubleTapToZoomEnabled(boolean enabled) : 设置为false以禁止通过在其上双击缩放图表。
//        setHighlightPerDragEnabled(boolean enabled) : 设置为true，允许每个图表表面拖过，当它完全缩小突出。 默认值：true
//        setHighlightPerTapEnabled(boolean enabled) : 设置为false，以防止值由敲击姿态被突出显示。 值仍然可以通过拖动或编程方式突出显示。 默认值：true

        mChart.getDescription().setEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setNoDataText(noDataShow);
        mChart.animateX(3000);
        mChart.getAxisRight().setEnabled(false);

        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        //     mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(false);
        mChart.setScaleYEnabled(true);
        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);

    }

    private void setXAxis() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
    }

    private void setLegend(){
        Legend legend = mChart.getLegend();
        legend.setTypeface(tfRegular);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }


    protected void showL1L2L3L4(boolean l1show,boolean l2show,boolean l3show,boolean l4show){
        if(l1show){
            if(textview_l1.getVisibility()!= View.VISIBLE)
                textview_l1.setVisibility(View.VISIBLE);
        }else {
            if (textview_l1.getVisibility() != View.INVISIBLE)
                textview_l1.setVisibility(View.INVISIBLE);
        }
        if(l2show){
            if(textview_l2.getVisibility()!= View.VISIBLE)
                textview_l2.setVisibility(View.VISIBLE);
        }else {
            if (textview_l2.getVisibility() != View.INVISIBLE)
                textview_l2.setVisibility(View.INVISIBLE);
        }

        if(l3show){
            if(textview_l3.getVisibility()!= View.VISIBLE)
                textview_l3.setVisibility(View.VISIBLE);
        }else {
            if (textview_l3.getVisibility() != View.INVISIBLE)
                textview_l3.setVisibility(View.INVISIBLE);
        }

        if(l4show){
            if(textview_l4.getVisibility()!= View.VISIBLE)
                textview_l4.setVisibility(View.VISIBLE);
        }else {
            if (textview_l4.getVisibility() != View.INVISIBLE)
                textview_l4.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int iEntry = (int) e.getX();
        if(iLastEntry!=iEntry) {
            iLastEntry = iEntry;
 //           mChart.highlightValue(e.getX(),0);
        }

       if(e!=null && h!=null)
            showSelectValue(e,h);
    }
    public void setTouchEnable(boolean isEnable){
        this.mChart.setTouchEnabled(isEnable);
    }

    public void showCursor(boolean enable){
        /*for(ILineDataSet dataSet:mChart.getLineData().getDataSets()) {
            mChart.getLineData().getDataSetByIndex(0).setHighlightEnabled(enable);
        }*/
        showCurson = enable;
        if(enable){
            mChart.highlightValue(iLastEntry,0);
            mChart.getData().setHighlightEnabled(true);
           /* for(int i = 0;i<mChart.getData().getDataSetCount();i++){
                LineDataSet set = (LineDataSet) mChart.getData().getDataSetByIndex(i);
                set.setHighlightEnabled(true);

            }*/
        }else {
            mChart.highlightValues(null);
            //          log.e("======cursor enable false=======");
            /*for(int i = 0;i<mChart.getData().getDataSetCount();i++){
                LineDataSet set = (LineDataSet) mChart.getData().getDataSetByIndex(i);
                set.setHighlightEnabled(true);
                log.e("======cursor enable false=======");
            }*/
            if(mChart.getData().isHighlightEnabled()) {
                mChart.getData().setHighlightEnabled(false);
                log.e("======cursor enable false=======");
            }


        }
    }

    public void moveCursor(int i){
        if(showCurson) {
            if (mChart != null) {
  //              log.e("===" + mChart.getLineData().getDataSetByIndex(0).getEntryCount());
                if (iLastEntry + i > 0 && iLastEntry + i < mChart.getLineData().getEntryCount() - 1) {
                    mChart.highlightValue((float) (iLastEntry + i), 0);
                    showSelectValue(iLastEntry + i);
                }
            }
        }
    }

    private void showSelectValue(int index){
        ArrayList<Float> values = new ArrayList<>();
        for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
            //数组越界加的if判断
            if (index < mChart.getLineData().getDataSetByIndex(i).getEntryCount()){
                float value = mChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }

        }
        showTextValue(values);
    }


    protected void showSelectValue(Entry e, Highlight h) {
        ArrayList<Float> values = new ArrayList<>();
        int index = mChart.getLineData().getDataSetByIndex(h.getDataSetIndex()).getEntryIndex(e);
        if(mChart.getLineData().getDataSetCount()>0) {
            for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
                float value = mChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }
        }
        showTextValue(values);
    }

    public int pointV_A,pointV_B,pointV_C,pointA_A,pointA_B,pointA_C;
    private String formatValue(float value,int point){
        StringBuilder format = new StringBuilder("#0");
        for (int i = 0; i < point; i++) {
               if (i == 0)
                   format.append(".0");
               else
                   format.append("0");
        }
        return new DecimalFormat(format.toString()).format(value);
//        return  String.format("%." + 2 + "f",value );
    }

    /**
     * 百分比转化为真实值
     * @param percentage
     * @param maxValue
     * @param minValue
     * @return
     */
    private float percentageToValue(float percentage,float maxValue,float minValue){
        return (percentage) * (maxValue - minValue) / 2;
    }
    protected void showTextValue(ArrayList<Float> values){

        if(values!=null && maxValue != null && minValue != null) {
            switch (scopeType) {
                case VOLT:
                    if(values.size()>=3) {
                        float value = percentageToValue(values.get(0),maxValue[0],minValue[0]);
                        float value1 = percentageToValue(values.get(1),maxValue[1],minValue[1]);
                        float value2 = percentageToValue(values.get(2),maxValue[2],minValue[2]);

                        textview_l1.setText("A    " + formatValue(value,pointV_A) + "  V");
                        textview_l2.setText("B    " + formatValue(value1,pointV_B) + "  V");
                        textview_l3.setText("C    " + formatValue(value2,pointV_C) + "  V");
                    }
                    break;
                case AMP:
                    if(values.size()>=3) {
                        float value = percentageToValue(values.get(0),maxValue[0],minValue[0]);
                        float value1 = percentageToValue(values.get(1),maxValue[1],minValue[1]);
                        float value2 = percentageToValue(values.get(2),maxValue[2],minValue[2]);
                        textview_l1.setText("L1    " + formatValue(value,pointA_A) + "  A");
                        textview_l2.setText("L2    " + formatValue(value1,pointA_B) + "  A");
                        textview_l3.setText("L3    " + formatValue(value2,pointA_C) + "  A");
                    }
                    break;

                case L1:
                    if(values.size()>=2) {
                        float value = percentageToValue(values.get(0),maxValue[0],minValue[0]);
                        float value1 = percentageToValue(values.get(1),maxValue[1],minValue[1]);
                        textview_l1.setText("A    " + formatValue(value,pointV_A) + "  V");
                        textview_l2.setText("L1    " + formatValue(value1,pointA_A) + "  A");
                    }
                    break;
                case L2:
                    if(values.size()>=2) {
                        float value = percentageToValue(values.get(0),maxValue[0],minValue[0]);
                        float value1 = percentageToValue(values.get(1),maxValue[1],minValue[1]);
                        textview_l1.setText("B    " + formatValue(value,pointV_B) + "  V");
                        textview_l2.setText("L2    " + formatValue(value1,pointA_B) + "  A");
                    }
                    break;
                case L3:
                    if(values.size()>=2) {
                        float value = percentageToValue(values.get(0),maxValue[0],minValue[0]);
                        float value1 = percentageToValue(values.get(1),maxValue[1],minValue[1]);
                        textview_l1.setText("C  " + formatValue(value,pointV_C) + "  V");
                        textview_l2.setText("L3  " + formatValue(value1,pointA_C) + "  A");
                    }
                    break;
            }
        }

    }

    public void showRunTextValue(ArrayList<Float> values){

        if(values!=null) {
            switch (scopeType) {
                case VOLT:
                    if(values.size()>=3) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        float value2 = values.get(2);

                        textview_l1.setText("A    " + formatValue(value,pointV_A) + "  V");
                        textview_l2.setText("B    " + formatValue(value1,pointV_B) + "  V");
                        textview_l3.setText("C    " + formatValue(value2,pointV_C) + "  V");
                    }
                    break;
                case AMP:
                    if(values.size()>=3) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        float value2 = values.get(2);
                        textview_l1.setText("L1    " + formatValue(value,pointA_A) + "  A");
                        textview_l2.setText("L2    " + formatValue(value1,pointA_B) + "  A");
                        textview_l3.setText("L3    " + formatValue(value2,pointA_C) + "  A");
                    }
                    break;

                case L1:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        textview_l1.setText("A    " + formatValue(value,pointV_A) + "  V");
                        textview_l2.setText("L1    " + formatValue(value1,pointA_A) + "  A");
                    }
                    break;
                case L2:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        textview_l1.setText("B    " + formatValue(value,pointV_B) + "  V");
                        textview_l2.setText("L2    " + formatValue(value1,pointA_B) + "  A");
                    }
                    break;
                case L3:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        textview_l1.setText("C  " + formatValue(value,pointV_C) + "  V");
                        textview_l2.setText("L3  " + formatValue(value1,pointA_C) + "  A");
                    }
                    break;
            }
        }

    }
    @Override
    public void onNothingSelected() {
        log.e("========onNothingSelected========");
    }

}
