package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;

public class MonitorBaseView extends MPChartBaseView implements OnChartValueSelectedListener {


    protected TextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected MonitorBarChart monitorBarChart;


    public MonitorBaseView(Context context) {
        super(context);
    }

    public MonitorBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MonitorBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.monitor_barchar_layout, this);
        textview_l1 = view.findViewById(R.id.textview_l1);
        textview_l2 = view.findViewById(R.id.textview_l2);
        textview_l3 = view.findViewById(R.id.textview_l3);
        textview_l4 = view.findViewById(R.id.textview_l4);

        monitorBarChart = view.findViewById(R.id.monitorbarchart);
        monitorBarChart.setOnChartValueSelectedListener(this);
 //       monitorBarChart.setRenderer(new MonitorBarChartRenderer());
        setBarChart();
        setAxisLeft();
        setAxisRight();
        setXAxis();
        setLegend();
        setMarkerView();
    }

    private void setMarkerView(){

    }

    private void setLegend(){
        Legend l = monitorBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTextColor(getResources().getColor(R.color.colorwhite,null));
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setEnabled(false);
    }

    private void setXAxis() {
        XAxis xAxis = monitorBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(20f);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setCenterAxisLabels(true);
    }

    private void setAxisRight() {
        YAxis rightAxis = monitorBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);
    }

    private void setAxisLeft() {
        YAxis leftAxis = monitorBarChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(5, true);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("#0%");
                return mFormat.format(value);
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaximum(1.2f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.enableAxisLineDashedLine(40,40,10);
//        leftAxis.enableGridDashedLine(5f, 5f, 0f);
        leftAxis.setDrawGridLines(false);

        LimitLine ll = new LimitLine(64000f, "Allowed");
        ll.setLineColor(Color.WHITE);
        ll.setLineWidth(1f);
        ll.setTextColor(Color.WHITE);
        ll.setTextSize(12f);
        // .. and more styling options
        boolean isDashedLine = true;
        if(isDashedLine){
            ll.enableDashedLine(5f,3f,0);  //设置虚线
        }else{
            ll.disableDashedLine();
        }


        LimitLine ll2 = new LimitLine(74000f, "Limit");
        ll2.setLineColor(Color.WHITE);
        ll2.setLineWidth(1f);
        ll2.setTextColor(Color.WHITE);
        ll2.setTextSize(12f);
        if(isDashedLine){
            ll2.enableDashedLine(5f,3f,0);  //设置虚线
        }else{
            ll2.disableDashedLine();
        }

        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.addLimitLine(ll);
        leftAxis.addLimitLine(ll2);

    }

    protected void setLimitLines(float limit1,float limit2){
        YAxis leftAxis = monitorBarChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        LimitLine ll = new LimitLine(limit1, "Allowed");
        ll.setLineColor(Color.WHITE);
        ll.setLineWidth(1f);
        ll.setTextColor(Color.WHITE);
        ll.setTextSize(12f);
        // .. and more styling options
        boolean isDashedLine = true;
        if(isDashedLine){
            ll.enableDashedLine(5f,3f,0);  //设置虚线
        }else{
            ll.disableDashedLine();
        }


        LimitLine ll2 = new LimitLine(limit2, "Limit");
        ll2.setLineColor(Color.WHITE);
        ll2.setLineWidth(1f);
        ll2.setTextColor(Color.WHITE);
        ll2.setTextSize(12f);
        if(isDashedLine){
            ll2.enableDashedLine(5f,3f,0);  //设置虚线
        }else{
            ll2.disableDashedLine();
        }
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.addLimitLine(ll);
        leftAxis.addLimitLine(ll2);
    }




    private void setBarChart() {
        monitorBarChart.getDescription().setEnabled(false);
        monitorBarChart.setPinchZoom(false);
        monitorBarChart.setScaleEnabled(false);
        monitorBarChart.setDoubleTapToZoomEnabled(false);
   //     monitorBarChart.setTouchEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be drawn
        //harmonicsbarchart.setMaxVisibleValueCount(60);
        monitorBarChart.setDrawBorders(false);
        monitorBarChart.setDrawGridBackground(false);
        monitorBarChart.setDrawBarShadow(false);
        monitorBarChart.setDrawValueAboveBar(true);

        //显示边界
        //设置动画效果
        monitorBarChart.animateY(1000);
        monitorBarChart.animateX(1000);

    }

    protected int iLastEntry = 0;
    protected int iLastDataset = 0;
    protected Highlight iLastHightlight;

    public void moveCursor(int i){
        if(monitorBarChart!=null) {
            if(iLastHightlight == null) {
                iLastEntry = 0;
                iLastDataset = 0;
            }else {
                if (iLastEntry == 0 && iLastDataset == 0) {
                    switch (i) {
                        case -1:
                            iLastEntry = 0;
                            iLastDataset = 0;
                            break;
                        case 1:
                            iLastEntry = 0;
                            iLastDataset = iLastDataset + i;
                            break;
                    }
                } else if (iLastEntry == 0 && iLastDataset == 1) {
                    iLastEntry = 0;
                    iLastDataset = iLastDataset + i;
                } else if (iLastEntry == 0 && iLastDataset == 2) {

                    switch (i) {
                        case -1:
                            iLastEntry = 0;
                            iLastDataset = iLastDataset + i;
                            break;
                        case 1:
                            iLastEntry = iLastEntry + i;
                            iLastDataset = 0;
                            break;
                    }
                } else if (iLastEntry == 1 && iLastDataset == 0) {

                    switch (i) {
                        case -1:
                            iLastEntry = iLastEntry + i;
                            iLastDataset = 2;
                            break;
                        case 1:
                            iLastDataset = iLastDataset + i;
                            break;
                    }

                } else if (iLastEntry == 1 && iLastDataset == 1) {
                    switch (i) {
                        case -1:
                            iLastDataset = iLastDataset + i;
                            break;
                        case 1:
                            iLastEntry = iLastEntry + i;
                            iLastDataset = iLastDataset + i;
                            break;

                    }

                } else if (iLastEntry == 1 && iLastDataset == 2) {

                    switch (i) {
                        case -1:
                            iLastDataset = iLastDataset + i;
                            break;
                        case 1:
                            iLastEntry = iLastEntry + i;
                            iLastDataset = 0;
                            break;
                    }

                } else if (iLastEntry == 2 && iLastDataset == 0) {

                    switch (i) {
                        case -1:
                            iLastDataset = 2;
                            break;
                        case 1:
                            iLastDataset = iLastDataset + i;
                            break;
                    }

                } else if (iLastEntry == 2 && iLastDataset == 1) {
                    switch (i) {
                        case -1:
                            iLastDataset = iLastDataset + i;
                            break;
                        case 1:
                            iLastEntry = iLastEntry + i;
                            iLastDataset = iLastDataset + i;
                            break;
                    }
                } else if (iLastDataset == 2 && iLastEntry == 2) {

                    switch (i) {
                        case -1:
                            iLastDataset = iLastDataset + i;
                            break;
                        case 1://
                            iLastEntry = iLastEntry + i;
                            iLastDataset = 2;
                            break;
                    }
                }
            }
            showTopView(iLastEntry,iLastDataset);
            monitorBarChart.highlightValue(iLastEntry, iLastDataset);
        }
    }

    private void showTopView(int iLastEntry, int iLastDataset) {

        textview_l1.setText("---");
        textview_l2.setText("---");
        textview_l3.setText("---");
        textview_l4.setText("---");
    }

    protected RectF mOnValueSelectedRectF = new RectF();
 //   String LOG_TAG = "MPAndroidChart";
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float value = e.getX();
        int iEntry = (int) e.getX(); //有错误
        int iDataSet = h.getDataSetIndex();
//        Log.e(LOG_TAG, "Highlighted:=== " + h.getX());
//        Log.e(LOG_TAG, "Highlighted:=== " + h.getDataSetIndex());
//        Log.e(LOG_TAG,"e.getX() = " + e.getX() );
//        log.e(" h.getDataSetIndex() = " + h.getDataSetIndex());


        iLastHightlight = h;
        if (iLastDataset != iDataSet) {
            iLastDataset = iDataSet;
 //           monitorBarChart.highlightValue(iLastEntry, iLastDataset);
        }
        if (iLastEntry != iEntry)
            iLastEntry = iEntry;


    }

    @Override
    public void onNothingSelected() {
    //    iLastEntry = 0;


    }
}
