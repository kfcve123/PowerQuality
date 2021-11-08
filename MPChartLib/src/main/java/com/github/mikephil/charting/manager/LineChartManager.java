package com.github.mikephil.charting.manager;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineChartManager {

    private static LineChartManager manager;
    private LineData lineData;
    private ArrayList<LineDataSet> lineDataSets;
    private ArrayList<LineDataSet> selectDataSets;

    private ScopeType scopeType = ScopeType.VOLT;

    public enum ScopeType{
        VOLT,AMP,L1,L2,L3
    }

    public void setScopeType(ScopeType type){
        this.scopeType = type;
    }


    public static synchronized LineChartManager getInstance(){
        if(manager==null)
            manager = new LineChartManager();
        return manager;
    }

    public void init(Context contxt) {
        lineData = new LineData();
        lineDataSets = new ArrayList<>();
        selectDataSets = new ArrayList<>();
    }


    /**
     *  @Description:初始化图表的样式
     * @param context
     * @param mLineChart
     */
    private static void initDataStyle(Context context, LineChart mLineChart) {
        //设置图表是否支持触控操作
        mLineChart.setTouchEnabled(true);
        mLineChart.setScaleEnabled(false);
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        //设置折线的描述的样式（默认在图表的左下角）
        Legend title = mLineChart.getLegend();
        title.setForm(Legend.LegendForm.LINE);
        //设置x轴的样式
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisMinimum(0f); // start at zero
        yAxisLeft.setAxisMaximum(200f);

        //设置右边y轴的样式
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
    }


    public void setLeftyAxis(){


    }

    private void setRightyAxis(){


    }

    private void addEntry(){


    }

}
