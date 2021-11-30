package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.HarmonicsBarChart;
import com.cem.powerqualityanalyser.chart.MPChartBaseView;
import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class HarmoBaseView extends MPChartBaseView implements OnChartValueSelectedListener {

    //共用
    protected LinearLayout sticky_ll;
    protected TextView wir_tv, select_harmoinics_tv;
    protected LinearLayout harmoinics_3item_ll, harmoinics_2item_ll;
    //一栏的情况
    protected TextView harmoinics_title_1item;
    // 3 栏的情况
    protected LinearLayout harmoinics_title_ll_3item;
    protected TextView harmoinics_l1_title_3item, harmoinics_l2_title_3item, harmoinics_l3_title_3item;
    protected TextView harmoinics_l1_value, harmoinics_l2_value, harmoinics_l3_value;
    protected TextView harmoinics_l1_value1, harmoinics_l2_value1, harmoinics_l3_value1;
    protected TextView harmoinics_l1_value2, harmoinics_l2_value2, harmoinics_l3_value2;

    //2栏的情况
    protected TextView harmoinics_l1_title_2item, harmoinics_l2_title_2item;
    protected TextView harmoinics_l1_value_2item, harmoinics_l2_value_2item;
    protected TextView harmoinics_l1_value1_2item, harmoinics_l2_value1_2item;
    protected TextView harmoinics_l1_value2_2item, harmoinics_l2_value2_2item;
    protected HarmonicsBarChart harmonicsbarchart;
    protected ArrayList<HarmonicsBarChart> charts;
    protected boolean cursorEnable;

    protected int iLastEntry, iLastSet;

    public HarmoBaseView(Context context) {
        super(context);
    }

    public HarmoBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HarmoBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HarmoBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.harmonics_barchar_layout, this);
        charts = new ArrayList<>();

        harmonicsbarchart = (HarmonicsBarChart) view.findViewById(R.id.harmoncibarchart);
        harmonicsbarchart.setOnChartValueSelectedListener(this);
        harmonicsbarchart.getDescription().setEnabled(false);
        harmonicsbarchart.setPinchZoom(false);
        harmonicsbarchart.setDrawBarShadow(false);
        harmonicsbarchart.setDrawGridBackground(false);

        Legend l = harmonicsbarchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = harmonicsbarchart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);


        YAxis leftAxis = harmonicsbarchart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        harmonicsbarchart.getAxisRight().setEnabled(false);


        sticky_ll = (LinearLayout) view.findViewById(R.id.sticky_ll);
        harmoinics_3item_ll = (LinearLayout) view.findViewById(R.id.harmoinics_3item_ll);
        harmoinics_2item_ll = (LinearLayout) view.findViewById(R.id.harmoinics_2item_ll);
        wir_tv = (TextView) findViewById(R.id.wir_tv);

        select_harmoinics_tv = (TextView) view.findViewById(R.id.select_harmoinics_tv);
        /* 三栏数据和一栏的情况*/
        /* 一栏数据的情况*/

        harmoinics_title_1item = (TextView) view.findViewById(R.id.harmoinics_title_1item);
        // 3 栏的情况 水平布局 L1,L2,L3   harmoinics_title_ll_3item 来控制显示1栏或是3栏
        harmoinics_title_ll_3item = (LinearLayout) view.findViewById(R.id.harmoinics_title_ll_3item);
        harmoinics_l1_title_3item = (TextView) view.findViewById(R.id.harmoinics_l1_title_3item);
        harmoinics_l2_title_3item = (TextView) view.findViewById(R.id.harmoinics_l2_title_3item);
        harmoinics_l3_title_3item = (TextView) view.findViewById(R.id.harmoinics_l3_title_3item);

        harmoinics_l1_value = (TextView) view.findViewById(R.id.harmoinics_l1_vaule);
        harmoinics_l2_value = (TextView) view.findViewById(R.id.harmoinics_l2_value);
        harmoinics_l3_value = (TextView) view.findViewById(R.id.harmoinics_l3_value);

        harmoinics_l1_value1 = (TextView) view.findViewById(R.id.harmoinics_l1_vaule1);
        harmoinics_l2_value1 = (TextView) view.findViewById(R.id.harmoinics_l2_value1);
        harmoinics_l3_value1 = (TextView) view.findViewById(R.id.harmoinics_l3_value1);

        harmoinics_l1_value2 = (TextView) view.findViewById(R.id.harmoinics_l1_vaule2);
        harmoinics_l2_value2 = (TextView) view.findViewById(R.id.harmoinics_l2_value2);
        harmoinics_l3_value2 = (TextView) view.findViewById(R.id.harmoinics_l3_value2);

        /* 二栏数据的情况*/
        //2栏的情况 水平布局 L1,L2

        harmoinics_l1_title_2item = (TextView) view.findViewById(R.id.harmoinics_l1_title_2item);
        harmoinics_l2_title_2item = (TextView) view.findViewById(R.id.harmoinics_l2_title_2item);

        harmoinics_l1_value_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value_2item);
        harmoinics_l2_value_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value_2item);

        harmoinics_l1_value1_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value1_2item);
        harmoinics_l2_value1_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value1_2item);

        harmoinics_l1_value2_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value2_2item);
        harmoinics_l2_value2_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value2_2item);

        if (harmonicsbarchart != null) {
            AppConfig.getInstance().setMaxZoom(2);
            zoomScale(2, 1);
        }
        charts.add(harmonicsbarchart);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        /*int iEntry = (int) e.getX();
        int valEntry = (int) e.getY();
        log.e("e.getX() = " + iEntry + "     e.getY() = " + valEntry);
        // 获取选中value的坐标
//        MPPointD p = getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
        cursorEnable = true;
        if (iLastEntry != iEntry) {
            //           log.e("=====隐藏所有的高亮0000000000===="  + iEntry);
            iLastEntry = iEntry;
            harmonicsbarchart.highlightValue(iLastEntry,0);
        } else {
            //           log.e("=====隐藏所有的高亮222222222===="  + iEntry);
        }*/
    }

    @Override
    public void onNothingSelected() {
        /*harmonicsbarchart.highlightValues(null);
        iLastEntry = 0;
        cursorEnable = false;*/
    }

    /**
     * 放大缩小
     *
     * @param xScale
     * @param yScale
     */
    public void zoomScale(float xScale, float yScale) {
        if (harmonicsbarchart.isScaleXEnabled()) {

        }
        //缩放第二种方式  
//        harmonicsbarchart.fitScreen();
//        harmonicsbarchart.getViewPortHandler().getMatrixTouch().postScale(xScale,yScale);
        //缩放第一种方式  
        Matrix matrix = new Matrix();
        harmonicsbarchart.fitScreen();
        //是不是需要设置y轴固定是100，还是也要缩放，缩放也不应该跟着x轴缩放
//        matrix.postScale(xScale, yScale);
        matrix.postScale(xScale, 1);
        harmonicsbarchart.getViewPortHandler().refresh(matrix, harmonicsbarchart, false);
        harmonicsbarchart.notifyDataSetChanged();
        harmonicsbarchart.invalidate();

    }

}
