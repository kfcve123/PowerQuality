package com.cem.powerqualityanalyser.fragment;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.newchart.HistoryChart;

import java.util.Date;
import java.util.List;

public class HistoryDataFragment extends  BaseFragment {
    private HistoryChart voltsAmpsHertzView;
    private int lastPosition = -1;

    @Override
    public int setContentView() {
        return R.layout.fragment_history;
    }

    @Override
    public void onInitView() {
        voltsAmpsHertzView = (HistoryChart) findViewById(R.id.voltsView);
        voltsAmpsHertzView.setDragEnabled(true);
        voltsAmpsHertzView.setScanleEnable(true);
    }

    public void setShowMeterData(final HistoryMeterAllObj allParameter){
        if(allParameter!=null)
            voltsAmpsHertzView.post(new Runnable() {
                @Override
                public void run() {
                    voltsAmpsHertzView.showMeterAllParamObj(allParameter);
                }
            });
    }

    public void setShowMeterData(final List<HistoryMeterAllObj> allParameterList){
        if(allParameterList!=null)
            voltsAmpsHertzView.post(new Runnable() {
                @Override
                public void run() {
                    voltsAmpsHertzView.showMeterAllParamObjList(allParameterList);
                }
            });
    }

    public void setHistoryDate(Date startDate, Date endDate){
        if (voltsAmpsHertzView!= null){
            voltsAmpsHertzView.setXDate(startDate,endDate);
        }
    }

    public void setFreqValue(String freq){
        voltsAmpsHertzView.setFreqValue(freq);
    }
    public void clearValues(){
        voltsAmpsHertzView.clearValues();
    }


    public void showCursor(boolean enable){
//        if(voltsAmpsHertzView!=null)
//            voltsAmpsHertzView.showCursor(enable);
    }

    private void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(voltsAmpsHertzView!=null)
            voltsAmpsHertzView.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(voltsAmpsHertzView!=null)
            voltsAmpsHertzView.moveCursor(i);
    }
}
