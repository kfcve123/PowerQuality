package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

public class ScopeTrendView extends ScopeTrendBaseView{

    private Legend.LegendForm legendForm = Legend.LegendForm.NONE;
    private boolean drawsetCircles;
    private ArrayList<Entry> values1 = new ArrayList<>();
    private ArrayList<Entry> values2 = new ArrayList<>();
    private ArrayList<Entry> values3 = new ArrayList<>();
    private ArrayList<Entry> values4 = new ArrayList<>();
    private LineDataSet set1, set2, set3,set4;
    private LineData lineData;

    public ScopeTrendView(Context context) {
        super(context);
    }

    public ScopeTrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScopeTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScopeTrendView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected  void setDrawsetCircles(boolean drawsetCircles){
        this.drawsetCircles = drawsetCircles;
    }

    protected void setLegendForm(Legend.LegendForm form){
        this.legendForm = form;
    }


    public void setScopeTopView(float textSize, String s) {
        if(scope_config_tv!=null){
            scope_config_tv.setText(s);
            scope_config_tv.setTextSize(textSize);
        }
    }

    public void setShowMeterData(ModelLineData modelLineData,boolean cosurOpen){
        if(modelLineData!=null) {
  //          setScopeData(modelLineData.getaValue().getValue(),modelLineData.getbValue().getValue(),modelLineData.getcValue().getValue(),modelLineData.getnValue().getValue());
 //           scopeLineChart.invalidate();

//            setTopTitle(modelLineData.getaValue() !=null?modelLineData.getaValue().getValue():"0",modelLineData.getbValue() !=null?modelLineData.getbValue().getValue():"0",modelLineData.getcValue() !=null?modelLineData.getcValue().getValue():"",modelLineData.getnValue() !=null?modelLineData.getnValue().getValue():"");
            addEntry(ojbToFloatValues(modelLineData));
        }
    }


    public void setTopLeftTitle(final String l1, final String l2, final String l3, final String ln) {
        l1a.setText(l1);
        l1b.setText(l2);
        l1c.setText(l3);
        n1.setText(ln);
    }

    private boolean visablea,visableb,visablec,visablen;
    public void setLineDataSetVisable(boolean visablea, boolean visableb, boolean visablec, boolean visablen,AppConfig config){
        this.visablea = visablea;
        this.visableb = visableb;
        this.visablec = visablec;
        this.visablen = visablen;
        if(scopeLineChart.getData().getDataSetCount()>0)
            scopeLineChart.getLineData().getDataSets().get(0).setVisible(visablea);
        if(scopeLineChart.getData().getDataSetCount()>1)
            scopeLineChart.getLineData().getDataSets().get(1).setVisible(visableb);
        if(scopeLineChart.getData().getDataSetCount()>2)
            scopeLineChart.getLineData().getDataSets().get(2).setVisible(visablec);
        if(scopeLineChart.getData().getDataSetCount()>3)
            scopeLineChart.getLineData().getDataSets().get(3).setVisible(visablen);
        scopeLineChart.invalidate();
        if(visablea){
            if (top_l1a_rl.getVisibility() != View.VISIBLE)
                top_l1a_rl.setVisibility(View.VISIBLE);

            if (scope_bottom_tv2.getVisibility() != View.VISIBLE)
                scope_bottom_tv2.setVisibility(View.VISIBLE);
        }else {
            if (top_l1a_rl.getVisibility() != View.INVISIBLE)
                top_l1a_rl.setVisibility(View.INVISIBLE);
            if (scope_bottom_tv2.getVisibility() != View.INVISIBLE)
                scope_bottom_tv2.setVisibility(View.INVISIBLE);
        }
        if(visableb){
            if (top_l1b_rl.getVisibility() != View.VISIBLE)
                top_l1b_rl.setVisibility(View.VISIBLE);
            if (scope_bottom_tv3.getVisibility() != View.VISIBLE)
                scope_bottom_tv3.setVisibility(View.VISIBLE);
        }else {
            if (top_l1b_rl.getVisibility() != View.INVISIBLE)
                top_l1b_rl.setVisibility(View.INVISIBLE);
            if (scope_bottom_tv3.getVisibility() != View.INVISIBLE)
                scope_bottom_tv3.setVisibility(View.INVISIBLE);
        }
        if(visablec){
            if (top_l1c_rl.getVisibility() != View.VISIBLE)
                top_l1c_rl.setVisibility(View.VISIBLE);
            if (scope_bottom_tv4.getVisibility() != View.VISIBLE)
                scope_bottom_tv4.setVisibility(View.VISIBLE);
        }else {
            if (top_l1c_rl.getVisibility() != View.INVISIBLE)
                top_l1c_rl.setVisibility(View.INVISIBLE);
            if (scope_bottom_tv4.getVisibility() != View.INVISIBLE)
                scope_bottom_tv4.setVisibility(View.INVISIBLE);
        }
        if(visablen){
            if (top_n1_rl.getVisibility() != View.VISIBLE)
                top_n1_rl.setVisibility(View.VISIBLE);
            if (scope_bottom_tv5.getVisibility() != View.VISIBLE)
                scope_bottom_tv5.setVisibility(View.VISIBLE);
        }else {
            if (top_n1_rl.getVisibility() != View.INVISIBLE)
                top_n1_rl.setVisibility(View.INVISIBLE);
            if (scope_bottom_tv5.getVisibility() != View.INVISIBLE)
                scope_bottom_tv5.setVisibility(View.INVISIBLE);
        }
        setTopBag(imgid[config.getSetup_Show_Color_VL1()-1],imgid[config.getSetup_Show_Color_VL2()-1],imgid[config.getSetup_Show_Color_VL3()-1],imgid[config.getSetup_Show_Color_VN()-1]);

    }

    private void setTopTitle(final String l1, final String l2, final String l3, final String ln, final AppConfig config){
        textview_l1.post(new Runnable() {
            @Override
            public void run() {
                if(selectEntry!=-1){
                    setTopTitle(scopeLineChart.getData().getDataSetByIndex(0).getEntryForIndex(selectEntry).getY()+"",scopeLineChart.getData().getDataSetByIndex(1).getEntryForIndex(selectEntry).getY()+"",scopeLineChart.getData().getDataSetByIndex(2).getEntryForIndex(selectEntry).getY()+"",scopeLineChart.getData().getDataSetByIndex(3).getEntryForIndex(selectEntry).getY()+"");
                }else{
                    setTopTitle(l1,l2,l3,ln);
                }
                setTopBag(imgid[config.getSetup_Show_Color_VL1()-1],imgid[config.getSetup_Show_Color_VL2()-1],imgid[config.getSetup_Show_Color_VL3()-1],imgid[config.getSetup_Show_Color_VN()-1]);
            }
        });
    }

    public void setTopTitle(final String l1, final String l2, final String l3, final String ln){
        textview_l1.setText(l1);
        textview_l2.setText(l2);
        textview_l3.setText(l3);
        textview_l4.setText(ln);
        /*textview_l1.post(new Runnable() {
            @Override
            public void run() {
                if (l1 == null || l1.equals("")) {
                    if (top_l1a_rl.getVisibility() != View.INVISIBLE)
                        top_l1a_rl.setVisibility(View.INVISIBLE);
                } else {
                    if(!visablea)
                        if (top_l1a_rl.getVisibility() != View.VISIBLE)
                            top_l1a_rl.setVisibility(View.VISIBLE);
                }
                if (l2 == null || l2.equals("")) {
                    if (top_l1b_rl.getVisibility() != View.INVISIBLE)
                        top_l1b_rl.setVisibility(View.INVISIBLE);
                } else {
                    if(!visableb)
                    if (top_l1b_rl.getVisibility() != View.VISIBLE)
                        top_l1b_rl.setVisibility(View.VISIBLE);
                }

                if (l3 == null || l3.equals("")) {
                    if (top_l1c_rl.getVisibility() != View.INVISIBLE)
                        top_l1c_rl.setVisibility(View.INVISIBLE);
                } else {
                    if(!visablec)
                    if (top_l1c_rl.getVisibility() != View.VISIBLE)
                        top_l1c_rl.setVisibility(View.VISIBLE);
                }
                if (ln == null || ln.equals("")) {
                    if (top_n1_rl.getVisibility() != View.INVISIBLE)
                        top_n1_rl.setVisibility(View.INVISIBLE);
                } else {
                    if(!visablen)
                    if (top_n1_rl.getVisibility() != View.VISIBLE)
                        top_n1_rl.setVisibility(View.VISIBLE);
                }

                textview_l1.setText(l1);
                textview_l2.setText(l2);
                textview_l3.setText(l3);
                textview_l4.setText(ln);
            }
        });*/
    }


    public void setTopBag(int l1a,int l1b,int l1c,int n1){
        top_l1a_rl.setBackgroundResource(l1a);
        top_l1b_rl.setBackgroundResource(l1b);
        top_l1c_rl.setBackgroundResource(l1c);
        top_n1_rl.setBackgroundResource(n1);
    }
    private int selectEntry = -1;

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        super.onValueSelected(e,h);
        selectEntry = (int) e.getX();
        scopeLineChart.centerViewToAnimated(e.getX(), e.getY(), scopeLineChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        setTopTitle(scopeLineChart.getData().getDataSetByIndex(0).getEntryForIndex((int) e.getX()).getY()+"",scopeLineChart.getData().getDataSetByIndex(1).getEntryForIndex((int) e.getX()).getY()+"",scopeLineChart.getData().getDataSetByIndex(2).getEntryForIndex((int) e.getX()).getY()+"",scopeLineChart.getData().getDataSetByIndex(3).getEntryForIndex((int) e.getX()).getY()+"");
    }


    protected void setScopeData(String aValue,String bValue,String cValue,String nValue) {

        aValue = ((float) (Math.random() * 10) + 30f) +"";
        bValue = ((float) (Math.random() * 20) + 30f) +"";
        cValue = ((float) (Math.random() * 30) + 30f) +"";
        nValue = ((float) (Math.random() * 40) + 30f) +"";

        if (DataFormatUtil.isNumber(aValue) && set1!=null)
            values1.add(new Entry(set1.getEntryCount(), Float.valueOf(aValue)));
        if (DataFormatUtil.isNumber(bValue) && set2!=null)
            values2.add(new Entry(set2.getEntryCount(), Float.valueOf(bValue)));
        if (DataFormatUtil.isNumber(cValue) && set3!=null)
            values3.add(new Entry(set3.getEntryCount(), Float.valueOf(cValue)));
        if (DataFormatUtil.isNumber(nValue) && set4!=null)
            values4.add(new Entry(set4.getEntryCount(), Float.valueOf(nValue)));

        if (scopeLineChart.getData() != null &&
                scopeLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(2);
            set4 = (LineDataSet) scopeLineChart.getData().getDataSetByIndex(3);

            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            scopeLineChart.getData().notifyDataChanged();
            scopeLineChart.notifyDataSetChanged();
            scopeLineChart.moveViewTo(lineData.getEntryCount() - 20 - 1, 50f, YAxis.AxisDependency.LEFT);
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "DataSet 1");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setForm(legendForm);
            set1.setDrawCircles(drawsetCircles);
            set1.setColor(ColorList.ALL_METER_TITLE_COLOR[1]);

            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(ColorList.ALL_METER_TITLE_COLOR[2]);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setForm(legendForm);
            set2.setDrawCircles(drawsetCircles);
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "DataSet 3");
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            set3.setColor(ColorList.ALL_METER_TITLE_COLOR[3]);
            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setDrawCircles(drawsetCircles);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));
            set3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set3.setForm(legendForm);

            set4 = new LineDataSet(values3, "DataSet 4");
            set4.setAxisDependency(YAxis.AxisDependency.LEFT);
            set4.setColor(ColorList.ALL_METER_TITLE_COLOR[4]);
            set4.setCircleColor(Color.WHITE);
            set4.setLineWidth(2f);
            set4.setDrawCircles(drawsetCircles);
            set4.setCircleRadius(3f);
            set4.setFillAlpha(65);
            set4.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set4.setDrawCircleHole(false);
            set4.setHighLightColor(Color.rgb(244, 117, 117));
            set4.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set4.setForm(legendForm);

            // create a data object with the data sets
            lineData = new LineData(set1, set2, set3,set4);
            lineData.setValueTextColor(Color.WHITE);
            lineData.setDrawValues(false);
            lineData.setValueTextSize(9f);

            // set data
            scopeLineChart.setData(lineData);
        }
    }

    private void addEntry(ArrayList<Float> yValuess) {
        try {
            if(yValuess!=null && yValuess.size()>0) {
                for (int i = 0; i < lineData.getDataSetCount(); i++) {
                    ILineDataSet set = lineData.getDataSetByIndex(i);
                    lineData.addEntry(new Entry(set.getEntryCount(), yValuess.get(i)), i);
                    lineData.notifyDataChanged();
                    // let the chart know it's data has changed
                    scopeLineChart.notifyDataSetChanged();
                    scopeLineChart.setVisibleXRangeMaximum(164);

                    // this automatically refreshes the chart (calls invalidate())
                    scopeLineChart.moveViewTo(set.getEntryCount()-164-1, 50f, YAxis.AxisDependency.LEFT);
 //                   scopeLineChart.moveViewTo(0, 50f, YAxis.AxisDependency.LEFT);
//                   scopeLineChart.invalidate();
                }
            }
        }catch (Exception e){

        }
    }
    public void clearValues(){
        if(scopeLineChart!=null){
            scopeLineChart.clearValues();
        }
    }

    @Override
    protected void setLineCharAttribute() {
        super.setLineCharAttribute();
        setLegendForm(Legend.LegendForm.NONE);
        // no description text
        scopeLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        scopeLineChart.setTouchEnabled(true);

        scopeLineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        scopeLineChart.setDragEnabled(true);
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
        scopeLineChart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = scopeLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(false);
//        l.setYOffset(11f);

        XAxis xAxis = scopeLineChart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = scopeLineChart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//        leftAxis.setAxisMaximum(200f);
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = scopeLineChart.getAxisRight();
        rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        rightAxis.setEnabled(false);

        set1 = new LineDataSet(values1, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set1.setMode(LineDataSet.Mode.LINEAR);

        set1.setForm(legendForm);
        set1.setDrawCircles(drawsetCircles);
        set1.setColor(ColorList.ALL_METER_TITLE_COLOR[1]);

        //set1.setFillFormatter(new MyFillFormatter(0f));
        //set1.setDrawHorizontalHighlightIndicator(false);
        //set1.setVisible(false);
        //set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2 = new LineDataSet(values2, "DataSet 2");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(ColorList.ALL_METER_TITLE_COLOR[2]);
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setMode(LineDataSet.Mode.LINEAR);
        set2.setForm(legendForm);
        set2.setDrawCircles(drawsetCircles);
        //set2.setFillFormatter(new MyFillFormatter(900f));

        set3 = new LineDataSet(values3, "DataSet 3");
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(ColorList.ALL_METER_TITLE_COLOR[3]);
        set3.setCircleColor(Color.WHITE);
        set3.setLineWidth(2f);
        set3.setDrawCircles(drawsetCircles);
        set3.setCircleRadius(3f);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set3.setDrawCircleHole(false);
        set3.setHighLightColor(Color.rgb(244, 117, 117));
        set3.setMode(LineDataSet.Mode.LINEAR);
        set3.setForm(legendForm);

        set4 = new LineDataSet(values4, "DataSet 4");
        set4.setAxisDependency(YAxis.AxisDependency.LEFT);
        set4.setColor(ColorList.ALL_METER_TITLE_COLOR[4]);
        set4.setCircleColor(Color.WHITE);
        set4.setLineWidth(2f);
        set4.setDrawCircles(drawsetCircles);
        set4.setCircleRadius(3f);
        set4.setFillAlpha(65);
        set4.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set4.setDrawCircleHole(false);
        set4.setHighLightColor(Color.rgb(244, 117, 117));
        set4.setMode(LineDataSet.Mode.LINEAR);
        set4.setForm(legendForm);

        // create a data object with the data sets
        lineData = new LineData(set1, set2, set3,set4);
        lineData.setValueTextColor(Color.WHITE);
        lineData.setDrawValues(false);
        lineData.setValueTextSize(9f);

        // set data
        scopeLineChart.setData(lineData);

    }


    protected float leftAxisMax;
    protected void setYLeftAxisMax(float leftAxisMax){
        this.leftAxisMax = leftAxisMax;
    }
    protected float leftAxisMin;
    protected void setYLeftAxisMin(float leftAxisMin){
        this.leftAxisMin = leftAxisMin;
    }

    protected float rightAxisMax;
    protected void setYRightAxisMax(float rightAxisMax){
        this.rightAxisMax = rightAxisMax;
    }

    protected float rightAxisMin;
    protected void setYRightAxisMin(float rightAxisMin){
        this.rightAxisMin = rightAxisMin;
    }

    public void setLineData(List<List<Float>> dataList,boolean newData){
        LineData lineData = scopeLineChart.getLineData();
        if (newData && lineData!=null) {
            lineData.clearValues();
//            mChart.highlightValues(null);
//            iLastEntry = 0;
        }
        if (lineData == null) {
            log.e("=======newLineData=====");
            lineData = new LineData();
            lineData.setValueTypeface(tfRegular);
            scopeLineChart.setData(lineData);
        }

        setMaxAndMinValue(dataList);
        if(dataList!=null) {
            if (lineData != null) {
                for (int i = 0; i < dataList.size(); i++) {
                    ILineDataSet set = lineData.getDataSetByIndex(i);
                    if (set == null) {
//                        log.e("=======newLineDataSet=====");
                        set = createSet(i);
                        lineData.addDataSet(set);
                    }
                    /*if(newData)
                        set.clear();*/

                    if(dataList.get(i).size()>0) {
                        for (int j = 0; j < dataList.get(i).size(); j++) {
                            lineData.addEntry(new Entry(set.getEntryCount(), valueToPercentage(dataList.get(i).get(j),maxValue[i],minValue[i])), i);
                        }
                    }else{
                        log.e("=======dataList.get(i).size()=====" + dataList.get(i).size());
                    }
                }
                lineData.notifyDataChanged();
                // let the chart know it's data has changed
                scopeLineChart.notifyDataSetChanged();

                // limit the number of visible entries
                //mChart.setVisibleXRangeMaximum(30);
                // mChart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                scopeLineChart.moveViewToX(lineData.getEntryCount());
            }
        }
    }

    /**
     * 根据传来的数据设置最大值
     */
    private void setMaxAndMinValue(List<List<Float>> dataList){
        log.e("---------" + dataList.size());
        maxValue = new float[dataList.size()];
        minValue = new float[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            List<Float> floats = dataList.get(i);
            float max = 100;
            log.e("-----------" + dataList.get(i).size());
            float min = dataList.get(i).get(0);
            for (int j = 0; j < floats.size(); j++) {
                if (floats.get(j) > max)
                    max = floats.get(j);
                if (floats.get(j) < min)
                    min = floats.get(j);
            }
            maxValue[i] = max;
            minValue[i] = min;
        }
    }

    public float valueToPercentage(float value,float maxValue,float minValue){
        if (maxValue - minValue == 0)
            return 0;
        return (value  * 2 / (maxValue - minValue)) - 1;
    }

}
