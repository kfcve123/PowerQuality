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

public class DipsSwellsBaseView extends MPChartBaseView{

    protected TextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected VoltsAmpsHertzLineChart mChart, mChart2, mChart3;
    protected ArrayList<VoltsAmpsHertzLineChart> charts;
    protected TextView right_tv,right_tv2,right_tv3;
    protected boolean cursorEnable;

    public DipsSwellsBaseView(Context context) {
        super(context);
    }

    public DipsSwellsBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DipsSwellsBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DipsSwellsBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.dipssweels_linechar_layout, this);

        charts = new ArrayList<>();
        mChart = view.findViewById(R.id.dipslinechart);
        mChart2 = view.findViewById(R.id.dipslinechart2);
        mChart3 = view.findViewById(R.id.dipslinechart3);

        mChart.setOnChartValueSelectedListener(new L1SelectListener());
        mChart2.setOnChartValueSelectedListener(new L2SelectListener());
        mChart3.setOnChartValueSelectedListener(new L3SelectListener());

        charts.add(mChart);
        charts.add(mChart2);
        charts.add(mChart3);

        textview_l1 = view.findViewById(R.id.textview_l1);
        textview_l2 = view.findViewById(R.id.textview_l2);
        textview_l3 = view.findViewById(R.id.textview_l3);
        textview_l4 = view.findViewById(R.id.textview_l4);
        textview_l4.setVisibility(INVISIBLE);
        right_tv = view.findViewById(R.id.right_tv);
        right_tv2 = view.findViewById(R.id.right_tv2);
        right_tv3 = view.findViewById(R.id.right_tv3);

        setLableCount(2);// < 2 == 2

        setViewOne();
        setViewTwo();
        setViewThree();

        setDefaultValue();
        initChartListener();

    }
    protected void initChartListener(){}

    private void setDefaultValue() {
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        ArrayList<Entry> values3 = new ArrayList<>();
        int count = 2;
        float val = 0f;
        for (int i = 0; i < count; i++) {
            values1.add(new Entry(i, val));
            values2.add(new Entry(i, val));
            values3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = createSet(0);
            set1.setValues(values1);
            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            mChart.setData(data);
        }

        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set2 = (LineDataSet) mChart2.getData().getDataSetByIndex(0);
            set2.setValues(values2);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set2 = createSet(1);
            set2.setValues(values2);
            // create a data object with the data sets
            LineData data = new LineData(set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            mChart2.setData(data);
        }


        if (mChart3.getData() != null &&
                mChart3.getData().getDataSetCount() > 0) {
            set3 = (LineDataSet) mChart3.getData().getDataSetByIndex(0);
            set3.setValues(values2);
            mChart3.getData().notifyDataChanged();
            mChart3.notifyDataSetChanged();
        } else {
            set3 = createSet(2);
            set3.setValues(values3);
            // create a data object with the data sets
            LineData data = new LineData(set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            mChart3.setData(data);
        }
    }


    protected LineDataSet createSet(int index) {
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
    }


    private void setViewOne() {
        mChart.setData(new LineData());
        setLineChartOne();
        setAxisLeftOne();
        setAxisRight0ne();
        setXAxisOne();
        setLegendOne();
    }

    private void setLineChartOne() {
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setViewPortOffsets(30, 0, 10, 0);
    }

    private void setAxisLeftOne() {
        BaseYAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[0]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40,40,10);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);
    }

    private void setAxisRight0ne() {
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[0]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setXAxisOne() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.colorwhite));
        xAxis.setEnabled(false);
        xAxis.setDrawLabels(false);
    }

    private void setLegendOne() {
        Legend legend = mChart.getLegend();
        legend.setTypeface(tfRegular);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setViewTwo() {
        mChart2.setData(new LineData());
        setLineCharTwo();
        setAxisLeftTwo();
        setAxisRightTwo();
        setXAxisTwo();
        setLegendTwo();
    }

    private void setLineCharTwo() {
        mChart2.getDescription().setEnabled(false);
        mChart2.setTouchEnabled(true);
        mChart2.setDragEnabled(false);
        mChart2.setScaleEnabled(false);
        mChart2.setViewPortOffsets(30, 0, 10, 0);
    }

    private void setAxisLeftTwo() {
        YAxis leftAxis = mChart2.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[1]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(5f,5f,0);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);
        leftAxis.setYOffset(10f);
        leftAxis.setSpaceBottom(40f);
    }

    private void setAxisRightTwo() {
        YAxis rightAxis = mChart2.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[1]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setXAxisTwo() {
        XAxis xAxis = mChart2.getXAxis();
        xAxis.setTextColor(getResources().getColor(R.color.colorwhite));
        xAxis.setDrawLabels(true);
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private void setLegendTwo() {
        Legend legend = mChart2.getLegend();
        legend.setTypeface(tfRegular);
        legend.setTextColor(Color.WHITE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setViewThree() {
        mChart3.setData(new LineData());
        setLineChartThree();
        setAxisLeftThree();
        setAxisRightThree();
        setXAxisThree();
        setLegendThree();
    }



    private void setLineChartThree() {
        mChart3.getDescription().setEnabled(false);
        mChart3.setTouchEnabled(true);
        mChart3.setDragEnabled(false);
        mChart3.setScaleEnabled(false);
        mChart3.setViewPortOffsets(30, 0, 10, 30);
    }

    private void setAxisLeftThree() {
        YAxis leftAxis = mChart3.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[3]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40,40,10);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);
//        leftAxis.setValueFormatter(new LargeValueFormatter());
    }

    private void setAxisRightThree() {
        YAxis rightAxis = mChart3.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[3]);
        rightAxis.setLabelCount(2, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setXAxisThree() {
        XAxis xAxis = mChart3.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextColor(getResources().getColor(R.color.colorwhite));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

    }

    private void setLegendThree() {
        Legend legend = mChart3.getLegend();
        legend.setTypeface(tfRegular);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }


    protected int iLastEntry ;

//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        int iEntry = (int) e.getX();
//        int valEntry = (int)e.getY();
//        //log.e("e.getX() = " + iEntry + "     e.getY() = " + valEntry);
//        // 获取选中value的坐标
////        MPPointD p = getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
//
//        cursorEnable = true;
//        if(iLastEntry!=iEntry) {
//            //           log.e("=====隐藏所有的高亮0000000000===="  + iEntry);
//            iLastEntry = iEntry;
//            mChart.highlightValue(iLastEntry,0);
//            mChart2.highlightValue(iLastEntry,0);
//            mChart3.highlightValue(iLastEntry,0);
//
//        }else{
//            //           log.e("=====隐藏所有的高亮222222222===="  + iEntry);
//        }
//
//    }

    /*private void getPixelForValues(Entry e){
        getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
    }*/

    public void moveCursor(int i){
        if(showCurson) {
            if(mChart!=null) {
                if(cursorEnable) {
                    if (iLastEntry + i > 0 && iLastEntry + i < mChart.getLineData().getEntryCount() - 1) {
                        mChart.highlightValue((float) (iLastEntry + i), 0);
//                showSelectValue(iLastEntry + i);
                    }
                }
            }
        }

    }

    private void showSelectValue(int index){
        if(index>0 && index < mChart.getLineData().getEntryCount() - 1) {
            ArrayList<Float> values = new ArrayList<>();
            for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
                float value = mChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }
            showTextValue(values);
        }else{//显示最后一组值,隐藏Cursor

        }
    }
    private void showTextValue(ArrayList<Float> values){



    }
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

    public  static final int DIPSWELLS = 1;
    public  static final int INRUSH = 2;
    private int measureType = DIPSWELLS;

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }

    private void showTextValue(float[] values){
        textview_l1.setText("A    " + formatValue(values[0],2) + (measureType == DIPSWELLS ? "  V" : "  A"));
        textview_l2.setText("B    " + formatValue(values[1],2) + (measureType == DIPSWELLS ? "  V" : "  A"));
        textview_l3.setText("C    " + formatValue(values[2],2) + (measureType == DIPSWELLS ? "  V" : "  A"));

    }
    public void showTextRunValue(float[] values){
        if (!showCurson){
            textview_l1.setText("A    " + formatValue(values[0],2) + (measureType == DIPSWELLS ? "  V" : "  A"));
            textview_l2.setText("B    " + formatValue(values[1],2) + (measureType == DIPSWELLS ? "  V" : "  A"));
            textview_l3.setText("C    " + formatValue(values[2],2) + (measureType == DIPSWELLS ? "  V" : "  A"));
        }
    }

    public void zoomScale(float xScale, float yScale) {


    }
    protected boolean showCurson = false;
    public void showCursor(boolean enable) {
        showCurson = enable;
        if(enable){
            mChart.highlightValue(iLastEntry,0);
            mChart.getData().setHighlightEnabled(true);
            mChart2.highlightValue(iLastEntry,0);
            mChart2.getData().setHighlightEnabled(true);
            mChart3.highlightValue(iLastEntry,0);
            mChart3.getData().setHighlightEnabled(true);
        }else {
            mChart.highlightValues(null);
            mChart2.highlightValues(null);
            mChart3.highlightValues(null);

            if(mChart.getData().isHighlightEnabled()) {
                mChart.getData().setHighlightEnabled(false);
                mChart2.getData().setHighlightEnabled(false);
                mChart3.getData().setHighlightEnabled(false);

            }


        }

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

    //记录L1L2L3选择的点
    private  float[] values = new float[3];

    public class L1SelectListener implements OnChartValueSelectedListener{

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            int iEntry = (int) e.getX();
            int valEntry = (int)e.getY();
            cursorEnable = true;
            if(iLastEntry!=iEntry) {
                //           log.e("=====隐藏所有的高亮0000000000===="  + iEntry);
                iLastEntry = iEntry;
                mChart.highlightValue(iLastEntry,0);
                mChart2.highlightValue(iLastEntry,0);
                mChart3.highlightValue(iLastEntry,0);
            }

            int index = mChart.getLineData().getDataSetByIndex(h.getDataSetIndex()).getEntryIndex(e);
            float value = mChart.getLineData().getDataSetByIndex(0).getEntryForIndex(index).getY();
            values[0] = value;
            showTextValue(values);
        }

        @Override
        public void onNothingSelected() {
            log.e("=====onNothingSelected0000000000===="  + iLastEntry);
            mChart.highlightValues(null);
            mChart2.highlightValues(null);
            mChart3.highlightValues(null);

            iLastEntry = 0;
            cursorEnable = false;
        }
    }

    public class L2SelectListener implements OnChartValueSelectedListener{

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            int iEntry = (int) e.getX();
            int valEntry = (int)e.getY();
            if(iLastEntry!=iEntry) {
                iLastEntry = iEntry;
                mChart.highlightValue(iLastEntry,0);
                mChart2.highlightValue(iLastEntry,0);
                mChart3.highlightValue(iLastEntry,0);

            }
            int index = mChart2.getLineData().getDataSetByIndex(h.getDataSetIndex()).getEntryIndex(e);
            float value = mChart2.getLineData().getDataSetByIndex(0).getEntryForIndex(index).getY();
            values[1] = value;
            showTextValue(values);
        }

        @Override
        public void onNothingSelected() {
            mChart.highlightValues(null);
            mChart2.highlightValues(null);
            mChart3.highlightValues(null);
            iLastEntry = 0;
            cursorEnable = false;
        }
    }

    public class L3SelectListener implements OnChartValueSelectedListener{

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            int iEntry = (int) e.getX();
            int valEntry = (int)e.getY();
            cursorEnable = true;
            if(iLastEntry!=iEntry) {
                iLastEntry = iEntry;
                mChart.highlightValue(iLastEntry,0);
                mChart2.highlightValue(iLastEntry,0);
                mChart3.highlightValue(iLastEntry,0);

            }
            int index = mChart3.getLineData().getDataSetByIndex(h.getDataSetIndex()).getEntryIndex(e);
            float value = mChart3.getLineData().getDataSetByIndex(0).getEntryForIndex(index).getY();
            values[2] = value;
            showTextValue(values);
        }

        @Override
        public void onNothingSelected() {
            mChart.highlightValues(null);
            mChart2.highlightValues(null);
            mChart3.highlightValues(null);
            iLastEntry = 0;
            cursorEnable = false;
        }
    }
}
