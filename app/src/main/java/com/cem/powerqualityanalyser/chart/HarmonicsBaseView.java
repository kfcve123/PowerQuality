package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HarmonicsBaseView extends MPChartBaseView implements OnChartValueSelectedListener{

    protected TextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected HarmonicsBarChart harmonicsbarchart;

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public enum HarmonicsType {
        Volt,Amp,Watt
    }

    public enum HarmonicsFunctionType {
        L1,L2,L3,N,Total
    }


    public HarmonicsBaseView(Context context) {
        super(context);
    }

    public HarmonicsBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HarmonicsBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HarmonicsBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.harmonics_barchar_layout, this);
        textview_l1 = view.findViewById(R.id.textview_l1);
        textview_l2 = view.findViewById(R.id.textview_l2);
        textview_l3 = view.findViewById(R.id.textview_l3);
        textview_l4 = view.findViewById(R.id.textview_l4);
        textview_l2.setVisibility(View.INVISIBLE);
        textview_l3.setVisibility(View.INVISIBLE);
        textview_l4.setVisibility(View.INVISIBLE);

        harmonicsbarchart = view.findViewById(R.id.harmoncibarchart);
        harmonicsbarchart.setOnChartValueSelectedListener(this);
        setBarChart();
        setAxisLeft();
        setAxisRight();
        setXAxis();
        setLegend();
        setMarkerView();

        setDefaultValue();
    }

    private void setDefaultValue() {
        ArrayList<BarEntry> values = new ArrayList<>();
        float val = 0f;
        int size = 4;
        for (int i = 0; i < size; i++) {
            values.add(new BarEntry(i, val));
        }
        BarDataSet set1;
        if (harmonicsbarchart.getData() != null &&
                harmonicsbarchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorList.SCOPE_LINE_COLOR[0]);
            set1.setDrawValues(false);
            set1.setStackLabels(new String[]{"DC","1","5","10","15","20","25","30","35","40","45","50"});
            set1.setBarBorderColor(Color.WHITE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);
            harmonicsbarchart.setData(data);
            harmonicsbarchart.setFitBars(true);
        }
        harmonicsbarchart.invalidate();

    }

    private void setMarkerView() {
//        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
//        mv.setChartView(chart); // For bounds control
//        chart.setMarker(mv); // Set the marker to the chart
    }

    private void setBarChart(){
        harmonicsbarchart.getDescription().setEnabled(false);
        harmonicsbarchart.setPinchZoom(false);
        harmonicsbarchart.setScaleEnabled(false);
        harmonicsbarchart.setDoubleTapToZoomEnabled(false);
        harmonicsbarchart.setTouchEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be drawn
        //harmonicsbarchart.setMaxVisibleValueCount(60);
        harmonicsbarchart.setDrawBorders(false);
        harmonicsbarchart.setDrawGridBackground(false);
        harmonicsbarchart.setDrawBarShadow(false);
        harmonicsbarchart.setDrawValueAboveBar(true);
        //显示边界
        //设置动画效果
        harmonicsbarchart.animateY(1000);
        harmonicsbarchart.animateX(1000);
        // chart.setDrawYLabels(false);
        // chart.setDrawLegend(false);
    }

    private void setAxisLeft() {
        YAxis leftAxis = harmonicsbarchart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(3, true);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat mFormat = new DecimalFormat("#0%");
                return mFormat.format(value);
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaximum(1f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.enableAxisLineDashedLine(40,40,10);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);

    }

    private void setAxisRight() {
        YAxis rightAxis = harmonicsbarchart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);
    }


    private void setXAxis(){
        XAxis xAxis = harmonicsbarchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(new HarmonicsXValueFormatter());
 //       xAxis.setLabelCount(7);
 //       xAxis.setValueFormatter(xAxisFormatter);

    }

    private void setLegend(){
        Legend l = harmonicsbarchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);
    }


}
