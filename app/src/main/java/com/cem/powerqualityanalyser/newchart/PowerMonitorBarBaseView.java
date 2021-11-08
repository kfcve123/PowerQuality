package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.HarmonicsBarChart;
import com.cem.powerqualityanalyser.chart.MPChartBaseView;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.MyNoPaddingTextView;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
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
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class PowerMonitorBarBaseView extends MPChartBaseView implements OnChartValueSelectedListener {

    protected MyNoPaddingTextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected TextView right_tv, right_tv2, right_tv3, right_tv4;
    protected View rootView;
    protected boolean cursorEnable = false;
    protected TextView l1a, l1b, l1c, n1;
    protected RelativeLayout top_n1_rl, top_l1a_rl, top_l1b_rl, top_l1c_rl;
    protected TextView inrush_config_tv;
    protected int[] imgid;
    protected TextView tv_time;
    protected TextView tv_hz;
    protected String unitL1, unitL2, unitL3, unitLn;

    protected PowerMonitorBarChart powerMonitorBarChart;

    public void setPowerMonitorTopView(float textSize,String configTV){
        if(inrush_config_tv!=null){
            inrush_config_tv.setTextSize(textSize);
            inrush_config_tv.setText(configTV);
        }
    }



    public void setTopBag(int l1a, int l1b, int l1c, int n1) {
        top_l1a_rl.setBackgroundResource(l1a);
        top_l1b_rl.setBackgroundResource(l1b);
        top_l1c_rl.setBackgroundResource(l1c);
        top_n1_rl.setBackgroundResource(n1);
    }

    public void setTopUnit(String unitL1, String unitL2, String unitL3, String unitLn) {
        if(unitL1!=null)
            this.unitL1 = unitL1;
        if(unitL2!=null)
            this.unitL2 = unitL2;
        if(unitL3!=null)
            this.unitL3 = unitL3;
        if(unitLn!=null)
            this.unitLn = unitLn;
    }

    public void setTopLeftTitle(String l1, String l2, String l3, String ln) {
        if (l1 != null)
            l1a.setText(l1);
        if (l2 != null)
            l1b.setText(l2);
        if (l3 != null)
            l1c.setText(l3);
        if (ln != null)
            n1.setText(ln);
    }

    public void showTopViewValue(String l1, String l2, String l3, String n) {
        if (l1 != null)
            textview_l1.setText(l1);
        if (l2 != null)
            textview_l2.setText(l2);
        if (l3 != null)
            textview_l3.setText(l3);
        if (n != null)
            textview_l4.setText(n);
    }

    public void showTopViewValue(float l1, float l2, float l3, float n) {
        textview_l1.setText(l1 + unitL1);
        textview_l2.setText(l2 + unitL2);
        textview_l3.setText(l3 + unitL3);
        textview_l4.setText(n + unitLn);
    }


    public PowerMonitorBarBaseView(Context context) {
        super(context);
    }

    public PowerMonitorBarBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PowerMonitorBarBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PowerMonitorBarBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initBarView(){
        powerMonitorBarChart = rootView.findViewById(R.id.powerMonitorBarChart);
        powerMonitorBarChart.setDrawBarShadow(false);
        powerMonitorBarChart.setDrawValueAboveBar(false);

        powerMonitorBarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        powerMonitorBarChart.setMaxVisibleValueCount(16);

        // scaling can now only be done on x- and y-axis separately
        powerMonitorBarChart.setPinchZoom(false);
        powerMonitorBarChart.setDrawGridBackground(false);

        XAxis xAxis = powerMonitorBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setDrawLabels(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = powerMonitorBarChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLinesBehindData(false);
        leftAxis.setDrawGridLines(false);


        YAxis rightAxis = powerMonitorBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setEnabled(false);

        Legend l = powerMonitorBarChart.getLegend();
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

    public void setDefaultValue() {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            float multi = (10 + 1);
            float val = (float) (Math.random() * multi) + multi / 3;
            values.add(new BarEntry(i, val));
        }

        BarDataSet set1;
        if (powerMonitorBarChart.getData() != null &&
                powerMonitorBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) powerMonitorBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            powerMonitorBarChart.getData().notifyDataChanged();
            powerMonitorBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
//            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setColor(Color.BLUE);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
 //           set1.setBarBorderWidth(30f);
            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);
            powerMonitorBarChart.setData(data);
            powerMonitorBarChart.setFitBars(true);
        }
        heightLimit(13f,"Limit",2, Color.BLACK);
        heightLimit(11f,"Allowed %",2,Color.BLACK);
        heightLimit(0.01f,"",0.5f,Color.BLACK);
        powerMonitorBarChart.invalidate();

    }

    public void heightLimit(float height, String name, float linewidth,int color){
        LimitLine limitLine = new LimitLine(height,name);
        //设置警告线的的位置，LimitLabelPosition枚举值
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        //设置警告线的名称
//        limitLine.setLabel();
        //设置警告线的颜色
        limitLine.setLineColor(color);
        //设置警告线的宽度
        limitLine.setLineWidth(linewidth);
        //是否启用
        //        limitLine.setEnabled();
        //设置警告线上文本的颜色
        limitLine.setTextColor(color);
        //设置警告线上文本的字体大小
        limitLine.setTextSize(12f);
        //设置警告线上文本的字体类型，如字体加粗等
        limitLine.setTypeface(Typeface.DEFAULT);
        powerMonitorBarChart.getAxisLeft().setDrawLimitLinesBehindData(true);
        //设置警告线在x轴上的偏移量
//        limitLine.setXOffset();
        powerMonitorBarChart.getAxisLeft().addLimitLine(limitLine);
    }



    @Override
    protected void setLineCharAttribute() {
        rootView = (RelativeLayout) inflater.inflate(R.layout.powermonitor_bar_view_layout, this);
        initBarView();
        textview_l1 = rootView.findViewById(R.id.textview_l1);
        textview_l2 = rootView.findViewById(R.id.textview_l2);
        textview_l3 = rootView.findViewById(R.id.textview_l3);
        textview_l4 = rootView.findViewById(R.id.textview_l4);
        textview_l1.setText("_ _ _._  V");
        textview_l2.setText("_ _ _._  V");
        textview_l3.setText("_ _ _._  V");
        textview_l4.setText("_ _ _._  V");

        top_n1_rl = rootView.findViewById(R.id.top_n1_rl);
        top_l1a_rl = rootView.findViewById(R.id.top_l1a_rl);
        top_l1b_rl = rootView.findViewById(R.id.top_l1b_rl);
        top_l1c_rl = rootView.findViewById(R.id.top_l1c_rl);

        l1a = rootView.findViewById(R.id.l1a);
        l1b = rootView.findViewById(R.id.l1b);
        l1c = rootView.findViewById(R.id.l1c);
        n1 = rootView.findViewById(R.id.n1);

        right_tv = rootView.findViewById(R.id.right_tv);
        right_tv2 = rootView.findViewById(R.id.right_tv2);
        right_tv3 = rootView.findViewById(R.id.right_tv3);
        right_tv4 = rootView.findViewById(R.id.right_tv4);

        tv_time = findViewById(R.id.tv_time);
        tv_hz = findViewById(R.id.tv_hz);
        tv_hz.setVisibility(INVISIBLE);
        inrush_config_tv = findViewById(R.id.inrush_config_tv);
        initChartListener();
        TypedArray ar = getResources().obtainTypedArray(R.array.top_backgroud_res_array);
        final int len = ar.length();
        imgid = new int[len];
        for (int i = 0; i < len; i++) {
            imgid[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        setDefaultValue();
    }

    public void setTimeText(String time) {
        if (tv_time != null)
            tv_time.setText(time);
    }

    public void setHzText(String hzText) {
        if (tv_hz != null)
            tv_hz.setText(hzText);
    }

    public void setHzVisable(boolean show) {
        if (tv_hz != null)
            tv_hz.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void showRightLegend() {
        right_tv.setVisibility(VISIBLE);
        right_tv2.setVisibility(VISIBLE);
        right_tv3.setVisibility(VISIBLE);
        right_tv3.setVisibility(VISIBLE);
    }


    public void goneRightLegend() {
        right_tv.setVisibility(GONE);
        right_tv2.setVisibility(GONE);
        right_tv3.setVisibility(GONE);
        right_tv3.setVisibility(GONE);
    }




    protected void initChartListener() {
    }


    @Override
    public void onNothingSelected() {
        log.e("=====onNothingSelected====" + iLastEntry);

        iLastEntry = 0;
        cursorEnable = false;
    }

    protected int iLastEntry;

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int iEntry = (int) e.getX();
        int valEntry = (int) e.getY();
        //log.e("e.getX() = " + iEntry + "     e.getY() = " + valEntry);
        // 获取选中value的坐标
//        MPPointD p = getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
        /*if(iLastEntry!= iEntry){
            iLastEntry = iEntry;
        }
        if(e!=null && h!=null)
            showSelectValue(e,h);*/

            if (iLastEntry != iEntry) {
                iLastEntry = iEntry;
                if(cursorEnable) {

                }
            }
    }

    /*private void getPixelForValues(Entry e){
        getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
    }*/

    public void moveCursor(int i) {
        if (cursorEnable) {

        }
    }

    protected void showSelectValue(Entry e, Highlight h) {


    }




    private void showTextValue(ArrayList<Float> values) {


    }

    public void zoomScale(float xScale, float yScale) {
        //缩放第一种方式  
        /*Matrix matrix = new Matrix();
        mChart.fitScreen();
        matrix.postScale(xScale, yScale);
         charts.get(i).getViewPortHandler().getMatrixTouch().postScale(xScale, yScale);

        mChart.getViewPortHandler().refresh(matrix, mChart, false);*/

    }

    public void showCursor(boolean enable) {
        this.cursorEnable = enable;


    }


    protected void showL1L2L3L4(boolean l1show, boolean l2show, boolean l3show, boolean l4show) {
        if (l1show) {
            if (top_l1a_rl.getVisibility() != View.VISIBLE)
                top_l1a_rl.setVisibility(View.VISIBLE);
        } else {
            if (top_l1a_rl.getVisibility() != View.INVISIBLE)
                top_l1a_rl.setVisibility(View.INVISIBLE);
        }
        if (l2show) {
            if (top_l1b_rl.getVisibility() != View.VISIBLE)
                top_l1b_rl.setVisibility(View.VISIBLE);
        } else {
            if (top_l1b_rl.getVisibility() != View.INVISIBLE)
                top_l1b_rl.setVisibility(View.INVISIBLE);
        }

        if (l3show) {
            if (top_l1c_rl.getVisibility() != View.VISIBLE)
                top_l1c_rl.setVisibility(View.VISIBLE);
        } else {
            if (top_l1c_rl.getVisibility() != View.INVISIBLE)
                top_l1c_rl.setVisibility(View.INVISIBLE);
        }

        if (l4show) {
            if (top_n1_rl.getVisibility() != View.VISIBLE)
                top_n1_rl.setVisibility(View.VISIBLE);
        } else {
            if (top_n1_rl.getVisibility() != View.INVISIBLE)
                top_n1_rl.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 修改接线的颜色
     *
     * @param color
     * @param color2
     * @param color3
     * @param color4
     */
    protected void setTopTextViewBgColor(int color, int color2, int color3, int color4) {
        textview_l1.setBackgroundColor(color);
        textview_l2.setBackgroundColor(color2);
        textview_l3.setBackgroundColor(color3);
        textview_l4.setBackgroundColor(color4);
    }


    public void setRightLegend(String legendL1,String legendL2,String legendL3,String legendLn,int legendTextSize){
        if (right_tv != null) {
            if(legendTextSize!=0)
                right_tv.setTextSize(legendTextSize);
            else
                right_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            right_tv.setText(legendL1);
        }
        if (right_tv2 != null) {
            if(legendTextSize!=0)
                right_tv2.setTextSize(legendTextSize);
            else
                right_tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            right_tv2.setText(legendL2);
        }
        if (right_tv3 != null) {
            if(legendTextSize!=0)
                right_tv3.setTextSize(legendTextSize);
            else
                right_tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            right_tv3.setText(legendL3);
        }
        if (right_tv4 != null) {
            if(legendTextSize!=0)
                right_tv4.setTextSize(legendTextSize);
            else
                right_tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            right_tv4.setText(legendLn);
        }

    }



}
