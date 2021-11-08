package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Toast;

import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

public class ScopeView extends ScopeBaseView{

    public ScopeView(Context context) {
        super(context);
        initView(context);
    }

    public ScopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScopeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ScopeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
    private void initView(Context context) {

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                mChart.getLineData().clearValues();
 //               mChart.clearValues();
            }else if(msg.what == 2){
                mChart.clearValues();
            }
        }
    };


    public void setTextNValue(String frequency){
        try {
            float v = Float.parseFloat(frequency);
            textview_l4.setText("    "+String.format("%." + 2 + "f",v )+"Hz");
        }catch (Exception e){
            textview_l4.setText("    "+frequency+"Hz");
        }
    }
         
    public float valueToPercentage(float value,float maxValue,float minValue){
        if (maxValue - minValue == 0)
            return 0;
        return (value  * 2/ (maxValue - minValue)) - 1;
    }
    /**
     * 根据传来的数据设置最大值
     */
    private void setMaxAndMinValue(List<List<Float>> dataList){
        maxValue = new float[dataList.size()];
        minValue = new float[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            List<Float> floats = dataList.get(i);
            float max = 0;
            float min = dataList.get(i).get(0);
            for (int j = 0; j < floats.size(); j++) {
                if (floats.get(j) > max)
                    max = floats.get(j);
                if (floats.get(j) < min)
                    min = floats.get(j);
            }
            maxValue[i] = max;
            minValue[i] = min;
//            Toast.makeText(this.getContext(),"----max-----" + max,Toast.LENGTH_SHORT).show();
//            Toast.makeText(this.getContext(),"----min-----" + min,Toast.LENGTH_SHORT).show();
            log.e("---------=" + max);
            log.e("---------=" + min);
        }
    }

    public void setLineData(List<List<Float>> dataList,boolean newData){
        LineData lineData = mChart.getLineData();
        if (newData && lineData!=null) {
            lineData.clearValues();
//            mChart.highlightValues(null);
//            iLastEntry = 0;
        }
        if (lineData == null) {
            log.e("=======newLineData=====");
            lineData = new LineData();
            lineData.setValueTypeface(tfRegular);
            mChart.setData(lineData);
        }

        setMaxAndMinValue(dataList);
        mChart.getAxisLeft().setAxisMinimum(minValue[0] * 2);
        mChart.getAxisLeft().setAxisMaximum(maxValue[0] * 2);
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
 //                           lineData.addEntry(new Entry(set.getEntryCount(), valueToPercentage(dataList.get(i).get(j),maxValue[i],minValue[i])), i);
                            lineData.addEntry(new Entry(set.getEntryCount(), dataList.get(i).get(j)), i);
                        }
                    }else{
                        log.e("=======dataList.get(i).size()=====" + dataList.get(i).size());
                    }
                }
                lineData.notifyDataChanged();
                // let the chart know it's data has changed
                mChart.notifyDataSetChanged();

                // limit the number of visible entries
                //mChart.setVisibleXRangeMaximum(30);
                // mChart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                mChart.moveViewToX(lineData.getEntryCount());
            }
        }
    }

    /*protected LineDataSet createSet(int index) {
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
 //       set.setHighlightEnabled(false);
        return set;
    }*/

    /**
     * 恢复原始
     * 重设所有缩放和拖动，使图表完全适合它的边界（完全缩小）。  
     */
    public void fitScreen(){
        mChart.fitScreen();
    //    mChart.setVisibleXRangeMaximum(maxXRange);
        mChart.getViewPortHandler().getMatrixTouch().postScale(0f,0f);
    }

    /**
     * 放大缩小
     * @param xScale
     * @param yScale
     */
    public void zoomScale(float xScale,float yScale){
        if(mChart.isScaleXEnabled()){

        }
        //缩放第二种方式  
        mChart.fitScreen();
        mChart.getViewPortHandler().getMatrixTouch().postScale(xScale,yScale);
        //缩放第一种方式  
        /*Matrix matrix = new Matrix();
        mChart.fitScreen();
        matrix.postScale(xScale, yScale);
        mChart.getViewPortHandler().refresh(matrix, mChart, false);*/
    }

    public void setScopeModeIndex(int positio){
        switch (positio) {
            case 0:
            case 1:
                setScopeMode(ScopeView.ScopeType.VOLT);
                break;
            case 2:
                setScopeMode(ScopeView.ScopeType.AMP);
                break;
            case 3:
                setScopeMode(ScopeView.ScopeType.L1);
                break;
            case 4:
                setScopeMode(ScopeView.ScopeType.L2);
                break;
            case 5:
                setScopeMode(ScopeView.ScopeType.L3);
                break;
        }
    }


    private  void setScopeMode(ScopeType scopeType) {
        this.scopeType = scopeType;
        if (lastMode != scopeType) {
            lastMode = scopeType;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
        switch (scopeType){
            case VOLT:
            case AMP:
                showL1L2L3L4(true,true,true,true);
                break;
            case L1:
            case L2:
            case L3:
                showL1L2L3L4(true,true,false,false);
                break;
        }

    }

    public void setLastEntry(int iLastEntry){
        this.iLastEntry = iLastEntry;
    }

    /* private ArrayList<ArrayList<Entry>> dataToEntry(List<List<Float>> dataList){
        ArrayList<ArrayList<Entry>> values = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<Entry> entries = new ArrayList<>();
            for(int j = 0; j< dataList.get(i).size();i++){
                entries.add(new Entry(i, dataList.get(i).get(j)));
            }
            values.add(entries);
        }
        return  values;
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }

    protected LineData generateLineData(Context context) {

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();

        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "sine.txt"), "Sine function");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "cosine.txt"), "Cosine function");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(context.getAssets(), "hugesine.txt"), "Cosine function2");

        ds1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        ds2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        ds3.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        ds1.setLineWidth(2f);
        ds2.setLineWidth(2f);
        ds3.setLineWidth(2f);

        ds1.setDrawCircles(false);
        ds2.setDrawCircles(false);
        ds3.setDrawCircles(false);

        ds1.setColor(getResources().getColor(R.color.value_green,null));
        ds2.setColor(getResources().getColor(R.color.value_red,null));
        ds3.setColor(getResources().getColor(R.color.value_blue,null));

        // load DataSets from textfiles in assets folders
        sets.add(ds1);
        sets.add(ds2);
        sets.add(ds3);
        LineData lineData = new LineData(sets);
        lineData.setValueTypeface(tfRegular);
        return lineData;
    }*/


}
