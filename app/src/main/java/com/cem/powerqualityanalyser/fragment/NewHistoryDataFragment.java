package com.cem.powerqualityanalyser.fragment;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.meterobject.NewHistoryMeterAllObj;
import com.cem.powerqualityanalyser.newchart.HistoryChart;
import com.cem.powerqualityanalyser.newchart.NewHistoryChart;

import java.util.Date;
import java.util.List;

public class NewHistoryDataFragment extends  BaseFragment {
    private NewHistoryChart newHistoryChart;
    private int lastPosition = -1;

    @Override
    public int setContentView() {
        return R.layout.fragment_history_new;
    }

    @Override
    public void onInitView() {
        newHistoryChart = (NewHistoryChart) findViewById(R.id.voltsView);
        newHistoryChart.setDragEnabled(true);
        newHistoryChart.setScanleEnable(true);
    }

    public void setShowMeterData(final NewHistoryMeterAllObj allParameter){
        if(allParameter!=null)
            newHistoryChart.post(new Runnable() {
                @Override
                public void run() {
                    newHistoryChart.showMeterAllParamObj(allParameter);
                }
            });
    }

    public void setShowMeterData(final List<NewHistoryMeterAllObj> allParameterList){
        if(allParameterList!=null)
            newHistoryChart.post(new Runnable() {
                @Override
                public void run() {
                    newHistoryChart.showMeterAllParamObjList(allParameterList);
                }
            });
    }

    public void setHistoryDate(Date startDate, Date endDate){
        if (newHistoryChart!= null){
            newHistoryChart.setXDate(startDate,endDate);
        }
    }

    public void setFreqValue(String freq){
        newHistoryChart.setFreqValue(freq);
    }
    public void clearValues(){
        newHistoryChart.clearValues();
    }


    public void showCursor(boolean enable){
//        if(voltsAmpsHertzView!=null)
//            voltsAmpsHertzView.showCursor(enable);
    }

    private void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(newHistoryChart!=null)
            newHistoryChart.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(newHistoryChart!=null)
            newHistoryChart.moveCursor(i);
    }
}
