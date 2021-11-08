package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.MPChartBaseView;
import com.cem.powerqualityanalyser.chart.ScopeLineChart;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
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
import java.util.List;


public class ScopeTrendBaseView  extends MPChartBaseView implements OnChartValueSelectedListener {

    protected MyNoPaddingTextView textview_l1, textview_l2, textview_l3, textview_l4;
    protected ScopeType scopeType = ScopeType.VOLT4V;
    protected ScopeType lastMode = ScopeType.VOLT4V;
    protected View rootView;
    protected TextView scope_config_tv;
    protected boolean showCurson;
    protected int iLastEntry;
    protected TextView scope_bottom_tv1,scope_bottom_tv2,scope_bottom_tv3,scope_bottom_tv4,scope_bottom_tv5;

    protected RelativeLayout top_n1_rl,top_l1a_rl,top_l1b_rl,top_l1c_rl;
    protected ScopeLineChart scopeLineChart;
    protected TextView l1a,l1b,l1c,n1;
    protected int[] imgid;

    //记录每条线的最大值
    protected float[] maxValue;
    protected float[] minValue;
    protected float maxLeftTrendView = 0f;
    protected float minLeftTrendView = 0f;
    protected float maxRightTrendView = 0f;
    protected float minRightTrendView = 0f;

    public enum ScopeType {
        VOLT4V, VOLT3U,AMP, L1, L2, L3,N
    }

    public void setScopeTopView(float textSize, String s) {
        if(scope_config_tv!=null){
            scope_config_tv.setText(s);
            scope_config_tv.setTextSize(textSize);
        }
    }

    public void setTopBag(int l1a,int l1b,int l1c,int n1){
        top_l1a_rl.setBackgroundResource(l1a);
        top_l1b_rl.setBackgroundResource(l1b);
        top_l1c_rl.setBackgroundResource(l1c);
        top_n1_rl.setBackgroundResource(n1);
    }

    public ScopeTrendBaseView(Context context) {
        super(context);
    }

    public ScopeTrendBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScopeTrendBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScopeTrendBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        rootView = (RelativeLayout) inflater.inflate(R.layout.scope_trend_layout, this);
        scopeLineChart = rootView.findViewById(R.id.scopelinechart);
        scopeLineChart.setOnChartValueSelectedListener(this);
        textview_l1 = rootView.findViewById(R.id.textview_l1);
        textview_l2 = rootView.findViewById(R.id.textview_l2);
        textview_l3 = rootView.findViewById(R.id.textview_l3);
        textview_l4 = rootView.findViewById(R.id.textview_l4);

        textview_l1.setText("----");
        textview_l2.setText("----");
        textview_l3.setText("----");
        textview_l4.setText("----");

        top_n1_rl = rootView.findViewById(R.id.top_n1_rl);
        top_l1a_rl = rootView.findViewById(R.id.top_l1a_rl);
        top_l1b_rl = rootView.findViewById(R.id.top_l1b_rl);
        top_l1c_rl = rootView.findViewById(R.id.top_l1c_rl);

        l1a = (TextView) findViewById(R.id.l1a);
        l1b = (TextView) findViewById(R.id.l1b);
        l1c = (TextView) findViewById(R.id.l1c);
        n1 = (TextView) findViewById(R.id.n1);


        scope_config_tv = findViewById(R.id.scope_config_tv);

        scope_bottom_tv1 = rootView.findViewById(R.id.scope_bottom_tv1);
        scope_bottom_tv2 = rootView.findViewById(R.id.scope_bottom_tv2);
        scope_bottom_tv3 = rootView.findViewById(R.id.scope_bottom_tv3);
        scope_bottom_tv4 = rootView.findViewById(R.id.scope_bottom_tv4);
        scope_bottom_tv5 = rootView.findViewById(R.id.scope_bottom_tv5);

        TypedArray ar = getResources().obtainTypedArray(R.array.top_backgroud_res_array);
        final int len = ar.length();
        imgid = new int[len];
        for (int i = 0; i < len; i++){
            imgid[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

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

        if (scopeLineChart.getData() != null &&
                scopeLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            scopeLineChart.getData().notifyDataChanged();
            scopeLineChart.notifyDataSetChanged();
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
            scopeLineChart.setData(data);
        }
    }

    protected LineDataSet createSet(int index) {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorList.ALL_METER_TITLE_COLOR[index + 1]);
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
 //       set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setMode(LineDataSet.Mode.LINEAR);

        //       set.setHighlightEnabled(false);
        return set;
    }

    private void setAxisLeft() {
        YAxis leftAxis = scopeLineChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(getResources().getColor(R.color.colorblack,null));
        leftAxis.setLabelCount(lableCount,true);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.enableAxisLineDashedLine(40,40,10);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);


        /*YAxis rightAxis = scopeLineChart.getAxisRight();
        rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setEnabled(false);*/

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


        scopeLineChart.getDescription().setEnabled(false);
        scopeLineChart.setDrawGridBackground(false);
        scopeLineChart.setNoDataText(noDataShow);
        scopeLineChart.animateX(3000);
        scopeLineChart.getAxisRight().setEnabled(false);

        scopeLineChart.setTouchEnabled(true);
        scopeLineChart.setDragEnabled(true);
        //     mChart.setScaleEnabled(true);
        scopeLineChart.setScaleXEnabled(false);
        scopeLineChart.setScaleYEnabled(false);
        scopeLineChart.setPinchZoom(false);
        scopeLineChart.setDoubleTapToZoomEnabled(false);
        scopeLineChart.setVisibleXRangeMaximum(200);

        /*scopeLineChart.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging

        scopeLineChart.setScaleEnabled(true);
        scopeLineChart.setDrawGridBackground(false);
        scopeLineChart.setHighlightPerDragEnabled(true);
        scopeLineChart.setDrawBorders(true);
        scopeLineChart.setVisibleXRangeMaximum(20);
//       scopeLineChart.setVisibleXRange(1,20);
        // if disabled, scaling can be done on x- and y-axis separately
        scopeLineChart.setPinchZoom(true);
        scopeLineChart.setExtraBottomOffset(6);//底部文字被遮挡处理
        // set an alternative background color
        //       scopeLineChart.setBackgroundColor(Color.LTGRAY);

        // add data
        scopeLineChart.animateX(1500);*/



    }

    private void setXAxis(){
        XAxis xAxis = scopeLineChart.getXAxis();
        xAxis.setEnabled(false);

        /*XAxis xAxis = scopeLineChart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = scopeLineChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);*/

    }

    private void setLegend(){
        Legend legend = scopeLineChart.getLegend();
        legend.setTypeface(tfRegular);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(false);

        // modify the legend ...
       /* l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(false);*/
//        l.setYOffset(11f);


    }

    protected void showL1L2L3L4(boolean l1show,boolean l2show,boolean l3show,boolean l4show){
        if(l1show){
            if(top_l1a_rl.getVisibility()!= View.VISIBLE)
                top_l1a_rl.setVisibility(View.VISIBLE);
        }else {
            if (top_l1a_rl.getVisibility() != View.INVISIBLE)
                top_l1a_rl.setVisibility(View.INVISIBLE);
        }
        if(l2show){
            if(top_l1b_rl.getVisibility()!= View.VISIBLE)
                top_l1b_rl.setVisibility(View.VISIBLE);
        }else {
            if (top_l1b_rl.getVisibility() != View.INVISIBLE)
                top_l1b_rl.setVisibility(View.INVISIBLE);
        }

        if(l3show){
            if(top_l1c_rl.getVisibility()!= View.VISIBLE)
                top_l1c_rl.setVisibility(View.VISIBLE);
        }else {
            if (top_l1c_rl.getVisibility() != View.INVISIBLE)
                top_l1c_rl.setVisibility(View.INVISIBLE);
        }

        if(l4show){
            if(top_n1_rl.getVisibility()!= View.VISIBLE)
                top_n1_rl.setVisibility(View.VISIBLE);
        }else {
            if (top_n1_rl.getVisibility() != View.INVISIBLE)
                top_n1_rl.setVisibility(View.INVISIBLE);
        }

    }

    private void setScope_bottom_tv1(String bottom_tv1){
        if(scope_bottom_tv1!=null)
            scope_bottom_tv1.setText(bottom_tv1);
    }

    private void setScope_bottom_tv2(String bottom_tv2){
        if(scope_bottom_tv2!=null)
            scope_bottom_tv2.setText(bottom_tv2);
    }

    private void setScope_bottom_tv3(String bottom_tv3){
        if(scope_bottom_tv3!=null)
            scope_bottom_tv3.setText(bottom_tv3);
    }

    private void setScope_bottom_tv4(String bottom_tv4){
        if(scope_bottom_tv4!=null)
            scope_bottom_tv4.setText(bottom_tv4);
    }

    private void setScope_bottom_tv5(String bottom_tv5){
        if(scope_bottom_tv5!=null)
            scope_bottom_tv5.setText(bottom_tv5);
    }


    public void setScopeBottoms(String bottom_tv1,String bottom_tv2,String bottom_tv3,String bottom_tv4,String bottom_tv5){
        setScope_bottom_tv1(bottom_tv1);
        setScope_bottom_tv2(bottom_tv2);
        setScope_bottom_tv3(bottom_tv3);
        setScope_bottom_tv4(bottom_tv4);
        setScope_bottom_tv5(bottom_tv5);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int iEntry = (int) e.getX();
        log.e(iLastEntry+ "==========" + iEntry);

            if (iLastEntry != iEntry) {
                iLastEntry = iEntry;
//                scopeLineChart.highlightValue(e.getX(), 0);
            }

        if(e!=null && h!=null)
            showSelectValue(e,h);
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 放大缩小
     * @param xScale
     * @param yScale
     */
    public void zoomScale(float xScale,float yScale){
        if(scopeLineChart.isScaleXEnabled()){

        }
        //缩放第二种方式  
        scopeLineChart.fitScreen();
        scopeLineChart.getViewPortHandler().getMatrixTouch().postScale(xScale,yScale);
        //缩放第一种方式  
        /*Matrix matrix = new Matrix();
        mChart.fitScreen();
        matrix.postScale(xScale, yScale);
        mChart.getViewPortHandler().refresh(matrix, mChart, false);*/
    }


    public void showCursor(boolean enable){
        /*for(ILineDataSet dataSet:mChart.getLineData().getDataSets()) {
            mChart.getLineData().getDataSetByIndex(0).setHighlightEnabled(enable);
        }*/
        showCurson = enable;
        if(enable){
            scopeLineChart.highlightValue(iLastEntry,0);
            scopeLineChart.getData().setHighlightEnabled(true);
           /* for(int i = 0;i<mChart.getData().getDataSetCount();i++){
                LineDataSet set = (LineDataSet) mChart.getData().getDataSetByIndex(i);
                set.setHighlightEnabled(true);

            }*/
        }else {
            scopeLineChart.highlightValues(null);
            //          log.e("======cursor enable false=======");
            /*for(int i = 0;i<mChart.getData().getDataSetCount();i++){
                LineDataSet set = (LineDataSet) mChart.getData().getDataSetByIndex(i);
                set.setHighlightEnabled(true);
                log.e("======cursor enable false=======");
            }*/
            if(scopeLineChart.getData().isHighlightEnabled()) {
                scopeLineChart.getData().setHighlightEnabled(false);
                log.e("======cursor enable false=======");
            }


        }
    }

    /**
     * 恢复原始
     * 重设所有缩放和拖动，使图表完全适合它的边界（完全缩小）。  
     */
    public void fitScreen(){
        scopeLineChart.fitScreen();
        //    mChart.setVisibleXRangeMaximum(maxXRange);
        scopeLineChart.getViewPortHandler().getMatrixTouch().postScale(0f,0f);
    }

    public void moveCursor(int i){
        if(showCurson) {
            if (scopeLineChart != null) {
                log.e("===" + scopeLineChart.getLineData());
                log.e("===" + scopeLineChart.getLineData().getDataSetByIndex(0));
                if (iLastEntry + i > 0 && iLastEntry + i < scopeLineChart.getLineData().getEntryCount() - 1) {
                    scopeLineChart.highlightValue((float) (iLastEntry + i), 0);
                    showSelectValue(iLastEntry + i);
                }
            }
        }
    }
    
    private void showSelectValue(int index){
        ArrayList<Float> values = new ArrayList<>();
        for (int i = 0; i < scopeLineChart.getLineData().getDataSetCount(); i++) {
            //数组越界加的if判断
            if (index < scopeLineChart.getLineData().getDataSetByIndex(i).getEntryCount()){
                float value = scopeLineChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }

        }
//        showTextValue(values);
    }

    protected void showSelectValue(Entry e, Highlight h) {
        ArrayList<Float> values = new ArrayList<>();
        int index = scopeLineChart.getLineData().getDataSetByIndex(h.getDataSetIndex()).getEntryIndex(e);
        if(scopeLineChart.getLineData().getDataSetCount()>0) {
            for (int i = 0; i < scopeLineChart.getLineData().getDataSetCount(); i++) {
                float value = scopeLineChart.getLineData().getDataSetByIndex(i).getEntryForIndex(index).getY();
                values.add(value);
            }
        }
        setScopeBottoms(values);
    }

    public void setTouchEnable(boolean isEnable){
        scopeLineChart.setTouchEnabled(isEnable);
    }

    public void setScopeBottomValues(List<List<Float>> meterGraphData){
        ArrayList<Float> values = new ArrayList<>();
        if(meterGraphData!=null) {
            for(int i = 0;i< meterGraphData.size();i++){
                values.add(meterGraphData.get(i).get(meterGraphData.get(i).size()-1));
            }
        }
        if(!showCurson)
            setScopeBottoms(values);
    }


    public void setScopeBottoms(List<Float> values){
        if(values!=null) {
            switch (scopeType) {
                case VOLT4V:
                    if (values.size() >= 4) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        float value2 = values.get(2);
                        float value3 = values.get(3);
                        setScopeBottoms("","V1= " + DataFormatUtil.formatValue(value,2) + "  V","V2= " + DataFormatUtil.formatValue(value1,2) + "  V","V3= " + DataFormatUtil.formatValue(value2,2) + "  V","VN= " + DataFormatUtil.formatValue(value3,2) + "  V");
                    }
                    break;
                case VOLT3U:
                    if(values.size()>=3) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        float value2 = values.get(2);
                        setScopeBottoms("","U1= " + DataFormatUtil.formatValue(value,2) + "  V","U2= " + DataFormatUtil.formatValue(value1,2) + "  V","U3= " + DataFormatUtil.formatValue(value2,2) + "  V","");
                    }
                    break;
                case AMP:
                    if(values.size()>=4) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        float value2 = values.get(2);
                        float value3 = values.get(3);
                        setScopeBottoms("","A1= " + DataFormatUtil.formatValue(value,2) + " A","A2= " + DataFormatUtil.formatValue(value1,2) + "  A","A3= " + DataFormatUtil.formatValue(value,2) + "  A","AN= " + DataFormatUtil.formatValue(value3,2) + "  A");
                    }
                    break;
                case L1:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        setScopeBottoms("","V1= " + DataFormatUtil.formatValue(value,2) + "  V","A1= " + DataFormatUtil.formatValue(value1,2) + "  A","","");
                    }
                    break;
                case L2:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        setScopeBottoms("","V2= " + DataFormatUtil.formatValue(value,2) + "  V","A2= " + DataFormatUtil.formatValue(value1,2) + "  A","","");
                    }
                    break;
                case L3:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        setScopeBottoms("","V3= " + DataFormatUtil.formatValue(value,2) + "  V","A3= " + DataFormatUtil.formatValue(value1,2) + "  A","","");
                    }

                    break;
                case N:
                    if(values.size()>=2) {
                        float value = values.get(0);
                        float value1 = values.get(1);
                        setScopeBottoms("","VN= " + value + "  V","AN= " + value1 + "  A","","");
                    }

                    break;
            }
        }

    }

    public void showRunTextValue(ArrayList<Float> values,int popwinIndex){
        if(values!=null) {
 //           log.e(values.size() + "------" + popwinIndex);
            switch (popwinIndex){
                case 0:
                    switch (scopeType) {
                        case VOLT4V:
                            if(values.size()>=4) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                float value3 = values.get(3);
                                textview_l1.setText(value + "  V");
                                textview_l2.setText(value1 + "  V");
                                textview_l3.setText(value2 + "  V");
                                textview_l4.setText(value3 + "  V");
                            }
                            break;
                        case VOLT3U:
                            if(values.size()>=3) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                textview_l1.setText(value + "  V");
                                textview_l2.setText(value1 + "  V");
                                textview_l3.setText(value2 + "  V");
                            }
                            break;
                        case AMP:
                            if(values.size()>=4) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                float value3 = values.get(3);
                                textview_l1.setText(value + "  A");
                                textview_l2.setText(value1 + "  A");
                                textview_l3.setText(value2 + "  A");
                                textview_l4.setText(value3 + "  A");
                            }
                            break;

                        case L1:
                        case L2:
                        case L3:
                        case N:
                            if(values.size()>=2) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                textview_l1.setText(value + "  V");
                                textview_l2.setText(value1 + "  A");
                            }
                            break;
                    }
                    break;
                case 1:
                    switch (scopeType) {
                        case VOLT4V:
                            if(values.size()>=4) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                float value3 = values.get(3);
                                textview_l1.setText(value + "  ");
                                textview_l2.setText(value1 + "  ");
                                textview_l3.setText(value2 + "  ");
                                textview_l4.setText(value3 + "  ");
                            }
                            break;
                        case VOLT3U:
                            if(values.size()>=3) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                textview_l1.setText(value + "  ");
                                textview_l2.setText(value1 + "  ");
                                textview_l3.setText(value2 + "  ");
                            }
                            break;
                        case AMP:
                            if(values.size()>=4) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                float value3 = values.get(3);
                                textview_l1.setText(value + "  ");
                                textview_l2.setText(value1 + "  ");
                                textview_l3.setText(value2 + "  ");
                                textview_l4.setText(value3 + "  ");
                            }
                            break;

                        case L1:
                        case L2:
                        case L3:
                        case N:
                            if(values.size()>=2) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                textview_l1.setText(value + "  ");
                                textview_l2.setText(value1 + "  ");
                            }
                            break;
                    }
                    break;
                case 2:
                    switch (scopeType) {
                        case VOLT4V:
                        case AMP:
                            if(values.size()>=4) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                float value3 = values.get(3);
                                textview_l1.setText(value + "  %f");
                                textview_l2.setText(value1 + "  %f");
                                textview_l3.setText(value2 + "  %f");
                                textview_l4.setText(value3 + "  %f");
                            }
                            break;
                        case VOLT3U:
                            if(values.size()>=3) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                float value2 = values.get(2);
                                textview_l1.setText(value + "  %f");
                                textview_l2.setText(value1 + "  %f");
                                textview_l3.setText(value2 + "  %f");
                            }
                            break;

                        case L1:
                        case L2:
                        case L3:
                            if(values.size()>=2) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                textview_l1.setText(value + "  %f");
                                textview_l2.setText(value1 + "  %f");
                            }
                            break;
                        case N:
                            if(values.size()>=2) {
                                float value = values.get(0);
                                float value1 = values.get(1);
                                textview_l1.setText(value + "  %r");
                                textview_l2.setText(value1 + "  %r");
                            }
                            break;
                    }

                    break;
            }

        }

    }


}
