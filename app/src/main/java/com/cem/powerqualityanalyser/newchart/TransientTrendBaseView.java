package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.BaseYAxis;
import com.cem.powerqualityanalyser.chart.MPChartBaseView;
import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzLineChart;
import com.cem.powerqualityanalyser.chart.VoltsAmpsHertzYAxis;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.MyNoPaddingTextView;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TransientTrendBaseView extends MPChartBaseView implements OnChartValueSelectedListener {

    protected MyNoPaddingTextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected VoltsAmpsHertzLineChart mChart, mChart2, mChart3, mChart4;
    protected ArrayList<VoltsAmpsHertzLineChart> charts;
    protected RelativeLayout inrushchart_l1_rl, inrushchart_l2_rl, inrushchart_l3_rl, inrushchart_n_rl;
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

    /**
     * 记录每条线的最大值最小值
     */
    protected float maxLeftTrendView = 0f;
    protected float minLeftTrendView = 0f;

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


    public TransientTrendBaseView(Context context) {
        super(context);
    }

    public TransientTrendBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransientTrendBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TransientTrendBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        rootView = (RelativeLayout) inflater.inflate(R.layout.inrush_trend_view_layout, this);

        charts = new ArrayList<>();
        mChart = rootView.findViewById(R.id.voltsampshertzlinechart);
        mChart2 = rootView.findViewById(R.id.voltsampshertzlinechart2);
        mChart3 = rootView.findViewById(R.id.voltsampshertzlinechart3);
        mChart4 = rootView.findViewById(R.id.voltsampshertzlinechart4);
        //禁止拖拽高亮线
        mChart.setHighlightPerDragEnabled(false);
        mChart2.setHighlightPerDragEnabled(false);
        mChart3.setHighlightPerDragEnabled(false);
        mChart4.setHighlightPerDragEnabled(false);
        //XY同时缩放
        mChart.setPinchZoom(false);
        mChart2.setPinchZoom(false);
        mChart3.setPinchZoom(false);
        mChart4.setPinchZoom(false);
        //禁止双击放大
        mChart.setDoubleTapToZoomEnabled(false);
        mChart2.setDoubleTapToZoomEnabled(false);
        mChart3.setDoubleTapToZoomEnabled(false);
        mChart4.setDoubleTapToZoomEnabled(false);

        mChart.setOnChartValueSelectedListener(this);
        mChart2.setOnChartValueSelectedListener(this);
        mChart3.setOnChartValueSelectedListener(this);
        mChart4.setOnChartValueSelectedListener(this);

        mChart.setDrawBorders(true);
        mChart2.setDrawBorders(true);
        mChart3.setDrawBorders(true);
        mChart4.setDrawBorders(true);

        charts.add(mChart);
        charts.add(mChart2);
        charts.add(mChart3);
        charts.add(mChart4);

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

        inrushchart_l1_rl = findViewById(R.id.inrushchart_l1_rl);
        inrushchart_l2_rl = findViewById(R.id.inrushchart_l2_rl);
        inrushchart_l3_rl = findViewById(R.id.inrushchart_l3_rl);
        inrushchart_n_rl = findViewById(R.id.inrushchart_n_rl);


        right_tv = rootView.findViewById(R.id.right_tv);
        right_tv2 = rootView.findViewById(R.id.right_tv2);
        right_tv3 = rootView.findViewById(R.id.right_tv3);
        right_tv4 = rootView.findViewById(R.id.right_tv4);

        tv_time = findViewById(R.id.tv_time);
        tv_hz = findViewById(R.id.tv_hz);
        inrush_config_tv = findViewById(R.id.inrush_config_tv);
        inrushchart_n_rl = findViewById(R.id.inrushchart_n_rl);
        setLableCount(2);// < 2 == 2

        setViewOne();
        setViewTwo();
        setViewThree();
        setViewFour();

        //       setDefaultValue();
        initChartListener();

        TypedArray ar = getResources().obtainTypedArray(R.array.top_backgroud_res_array);
        final int len = ar.length();
        imgid = new int[len];
        for (int i = 0; i < len; i++) {
            imgid[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

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


    public void setL1ChartVisable(int l1ChartVisable) {
        if (inrushchart_l1_rl != null) {
            inrushchart_l1_rl.setVisibility(l1ChartVisable);
            /*if(l1ChartVisable == View.VISIBLE){
                mChart.getAxisLeft().setLabelCount(5);
                mChart.getXAxis().setEnabled(true);
                mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart.setViewPortOffsets(40,0,25,20);
                mChart.setExtraBottomOffset(6);//底部文字被遮挡处理
            }*/
        }
    }

    public void setL2ChartVisable(int l2ChartVisable) {
        if (inrushchart_l2_rl != null) {
            inrushchart_l2_rl.setVisibility(l2ChartVisable);
            /*if(l2ChartVisable == View.VISIBLE){
                mChart2.getAxisLeft().setLabelCount(5);
                mChart2.getXAxis().setEnabled(true);
                mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart2.setViewPortOffsets(40,0,25,20);
                mChart2.setExtraBottomOffset(6);//底部文字被遮挡处理
            }*/
        }
    }

    public void setL3ChartVisable(int l3ChartVisable) {
        if (inrushchart_l3_rl != null) {
            inrushchart_l3_rl.setVisibility(l3ChartVisable);
            /*if(l3ChartVisable == VISIBLE){
                mChart3.getAxisLeft().setLabelCount(5);
                mChart3.getXAxis().setEnabled(true);
                mChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mChart3.setViewPortOffsets(40,0,25,20);
                mChart3.setExtraBottomOffset(6);//底部文字被遮挡处理
            }*/
        }
    }

    public void setNChartVisable(int nChartVisable) {
        if (inrushchart_n_rl != null) {
            inrushchart_n_rl.setVisibility(nChartVisable);
            if(nChartVisable == VISIBLE)
                setLableCount(5);
        }
    }

    protected void initChartListener() {
    }

    private void setDefaultValue() {

        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        ArrayList<Entry> values3 = new ArrayList<>();
        ArrayList<Entry> values4 = new ArrayList<>();
        int count = 2;
        for (int i = 0; i < count; i++) {
            float val = 0f;
            values1.add(new Entry(i, val));
            values2.add(new Entry(i, val));
            values3.add(new Entry(i, val));
            values4.add(new Entry(i, val));
        }
        LineDataSet set1, set2, set3, set4;

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
            set3.setValues(values3);
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

        if (mChart4.getData() != null &&
                mChart4.getData().getDataSetCount() > 0) {
            set4 = (LineDataSet) mChart4.getData().getDataSetByIndex(0);
            set4.setValues(values4);
            mChart4.getData().notifyDataChanged();
            mChart4.notifyDataSetChanged();
        } else {
            set4 = createSet(3);
            set4.setValues(values4);
            // create a data object with the data sets
            LineData data = new LineData(set4);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);
            // set data
            mChart4.setData(data);
        }

    }

    protected LineDataSet createSet(int index) {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorList.VOLTS_LINE_COLOR[index]);
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

    /**
     * 总的Mchart 属性设置
     */
    private void setViewOne() {
        mChart.setData(new LineData());
        setLineChartOne();
        setAxisLeftOne();
        setAxisRightOne();
        setXAxisOne();
        setLegendOne();
    }

    private void setViewTwo() {
        mChart2.setData(new LineData());
        setLineCharTwo();
        setAxisLeftTwo();
        setAxisRightTwo();
        setXAxisTwo();
        setLegendTwo();
    }

    private void setViewThree() {
        mChart3.setData(new LineData());
        setLineChartThree();
        setAxisLeftThree();
        setAxisRightThree();
        setXAxisThree();
        setLegendThree();
    }

    private void setViewFour() {
        mChart4.setData(new LineData());
        setLineChartFour();
        setAxisLeftFour();
        setAxisRightFour();
        setXAxisFour();
        setLegendFour();
    }

    private void setLineChartOne() {
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setViewPortOffsets(40, 0, 10, 0);
    }

    private void setLineCharTwo() {
        mChart2.getDescription().setEnabled(false);
        mChart2.setTouchEnabled(true);
        mChart2.setDragEnabled(false);
        mChart2.setScaleEnabled(false);
        mChart2.setViewPortOffsets(40, 0, 10, 0);
    }

    private void setLineChartThree() {
        mChart3.getDescription().setEnabled(false);
        mChart3.setTouchEnabled(true);
        mChart3.setDragEnabled(false);
        mChart3.setScaleEnabled(false);
        mChart3.setViewPortOffsets(40, 0, 10, 0);
    }

    private void setLineChartFour() {
        mChart4.getDescription().setEnabled(false);
        mChart4.setTouchEnabled(true);
        mChart4.setDragEnabled(false);
        mChart4.setScaleEnabled(false);
        mChart4.setViewPortOffsets(40, 0, 10, 0);
    }


    private void setAxisLeftOne() {
        BaseYAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.ALL_METER_TITLE_COLOR[0]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40, 40, 10);

        //       leftAxis.enableGridDashedLine(5f, 5f, 0f);
        //       leftAxis.setYOffset(10);
    }

    private void setAxisLeftTwo() {
        YAxis leftAxis = mChart2.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[1]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(5f, 5f, 0);
        //       leftAxis.enableGridDashedLine(5f, 5f, 0f);
//        leftAxis.setYOffset(10f);
//        leftAxis.setSpaceBottom(40f);
//        leftAxis.setYOffset(10);
    }

    private void setAxisLeftThree() {
        VoltsAmpsHertzYAxis leftAxis = mChart3.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[2]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40, 40, 10);
//        leftAxis.enableGridDashedLine(5f, 5f, 0f);
        leftAxis.setYOffset(10);
    }

    private void setAxisLeftFour() {
        YAxis leftAxis = mChart4.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[3]);
        leftAxis.setLabelCount(lableCount, true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40, 40, 10);
//        leftAxis.enableGridDashedLine(5f, 5f, 0f); 虚线显示XGrid线
        leftAxis.setYOffset(10);
    }


    private void setAxisRightOne() {
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[1]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setAxisRightTwo() {
        YAxis rightAxis = mChart2.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[1]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setAxisRightThree() {
        YAxis rightAxis = mChart3.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[2]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setAxisRightFour() {
        YAxis rightAxis = mChart4.getAxisRight();
        rightAxis.setTextColor(ColorList.VOLTS_LINE_COLOR[3]);
        rightAxis.setLabelCount(lableCount, true);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
    }

    private void setLegendOne() {
        Legend legend = mChart.getLegend();
        legend.setTypeface(tfRegular);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setLegendTwo() {
        Legend legend = mChart2.getLegend();
        legend.setTypeface(tfRegular);
        legend.setTextColor(Color.WHITE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setLegendThree() {
        Legend legend = mChart3.getLegend();
        legend.setTypeface(tfRegular);
        legend.setTextColor(Color.WHITE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setLegendFour() {
        Legend legend = mChart4.getLegend();
        legend.setTypeface(tfRegular);
        legend.setTextColor(Color.BLACK);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);
    }

    private void setXAxisOne() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(11f);
        xAxis.setTypeface(tfLight);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);
    }

    private void setXAxisTwo() {
        XAxis xAxis = mChart2.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(11f);
        xAxis.setTypeface(tfLight);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);
    }

    private void setXAxisThree() {
        XAxis xAxis = mChart3.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(11f);
        xAxis.setTypeface(tfLight);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);

    }

    private void setXAxisFour() {
        XAxis xAxis = mChart4.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(11f);
        xAxis.setTypeface(tfLight);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);
    }


    @Override
    public void onNothingSelected() {
        log.e("=====onNothingSelected====" + iLastEntry);
        mChart.highlightValues(null);
        mChart2.highlightValues(null);
        mChart3.highlightValues(null);
        mChart4.highlightValues(null);
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
                    mChart.highlightValue(iLastEntry, 0);
                    mChart2.highlightValue(iLastEntry, 0);
                    mChart3.highlightValue(iLastEntry, 0);
                    mChart4.highlightValue(iLastEntry, 0);
                }
                showTopViewValue(mChart.getData().getEntryForHighlight(h).getY(), mChart2.getData().getEntryForHighlight(h).getY(), mChart3.getData().getEntryForHighlight(h).getY(), mChart4.getData().getEntryForHighlight(h).getY());
            }
    }

    /*private void getPixelForValues(Entry e){
        getPixelForValues(e.getX(), e.getY(), YAxis.AxisDependency.LEFT);
    }*/

    public void moveCursor(int i) {
        if (cursorEnable) {
            if (mChart != null) {
                if (iLastEntry + i > 0 && iLastEntry + i < mChart.getLineData().getEntryCount() - 1) {
                    mChart.highlightValue((float) (iLastEntry + i), 0);
                    showSelectValue(iLastEntry + i);
                }
            }
        }
    }

    protected void showSelectValue(Entry e, Highlight h) {


    }


    private void showSelectValue(int index) {
        if (index > 0 && index < mChart.getLineData().getEntryCount() - 1) {
            ArrayList<Float> values = new ArrayList<>();
            for (int i = 0; i < mChart.getLineData().getDataSetCount(); i++) {
                float value = mChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }
//            showTextValue(values);
        } else {//显示最后一组值,隐藏Cursor

        }
    }

    private void showTextValue(ArrayList<Float> values) {


    }

    public void zoomScale(float xScale, float yScale) {
        //缩放第一种方式  
        /*Matrix matrix = new Matrix();
        mChart.fitScreen();
        matrix.postScale(xScale, yScale);
        mChart.getViewPortHandler().refresh(matrix, mChart, false);*/
        for (int i = 0; i < charts.size(); i++) {
            charts.get(i).fitScreen();
            charts.get(i).getViewPortHandler().getMatrixTouch().postScale(xScale, yScale);
        }
    }

    public void showCursor(boolean enable) {
        this.cursorEnable = enable;
        if (enable) {
            for (int i = 0; i < charts.size(); i++) {
                charts.get(i).highlightValue(iLastEntry, 0);
                charts.get(i).getData().setHighlightEnabled(true);
                charts.get(i).setHighlightPerTapEnabled(true);
                charts.get(i).setHighlightPerDragEnabled(true);
            }
        } else {
            for (int i = 0; i < charts.size(); i++) {
                charts.get(i).highlightValue(null);
                charts.get(i).getData().setHighlightEnabled(false);
                charts.get(i).setHighlightPerTapEnabled(false);
                charts.get(i).setHighlightPerDragEnabled(false);
            }
        }

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
            if (legendTextSize != 0)
                right_tv.setTextSize(legendTextSize);
            right_tv.setText(legendL1);
        }
        if (right_tv2 != null) {
            if (legendTextSize != 0)
                right_tv2.setTextSize(legendTextSize);
            right_tv2.setText(legendL2);
        }
        if (right_tv3 != null) {
            if (legendTextSize != 0)
                right_tv3.setTextSize(legendTextSize);
            right_tv3.setText(legendL3);
        }
        if (right_tv4 != null) {
            if (legendTextSize != 0)
                right_tv4.setTextSize(legendTextSize);
            right_tv4.setText(legendLn);
        }
    }


}
