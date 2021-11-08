package com.cem.powerqualityanalyser.activity;


import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.newchart.PowerMonitorBarView;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 电能质量监测
 */
public class PowerMonitorBar extends BaseFragmentTrend implements OnChartValueSelectedListener {

    private String configV;
    private String configHz;
    private PowerMonitorBarView powerMonitorBarView;
    private CustomTimer customTimer;// 倒计时计时器


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }
    @Override
    public void setShowMeterData(final ModelAllData list) {

    }

    @Override
    public void setShowMeterData(final ModelAllData list,final int funTypeIndex) {

        List<ModelLineData> modelLineData = list.getModelLineData();
        if (modelLineData != null) {

        }
    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    private List<ModelLineData> lineData = new ArrayList<>();
    private int singleBarSize = 1;
    private List<ModelLineData> chooseUserFullModeList(List<ModelLineData> lineDataList,int wir_index,int funTypeIndex,int wir_right_index){
        try {
            lineData.clear();
            switch (wir_index) {
                case 0://3QWYE
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                case 7://1Q SPLIT PHASE
                case 9://1Q +NEUTRAL
                    switch (funTypeIndex) {
                        case 0://A
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 9));
                            }
                            break;
                        case 1://S
                            lineData.add(lineDataList.get(1));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 6));
                            }

                            break;
                        case 2://V
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 8));
                            }

                            break;
                    }
                    break;
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
                case 8://1Q IT NO NEUTRAL  列表和柱状图的数据结构待谢工确认
                    switch (funTypeIndex) {
                        case 0://A
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 9));
                            }
                            break;
                        case 1://S
                            lineData.add(lineDataList.get(1));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 6));
                            }

                            break;
                        case 2://U
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 7));
                            }

                            break;
                    }
                    break;
            }
        } catch (Exception e) {

        }
        return lineData;
    }




    /**
     * 实时值
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list,int funTypeIndex){
        switch (wir_index){
            case 0://3QWYE
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA

                break;
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT

                break;
            case 7://1Q SPLIT PHASE

                break;
            case 8://1Q IT NO NEUTRAL

                break;
            case 9://1Q +NEUTRAL

                break;
        }
    }




    @Override
    public int setContentView() {
        return R.layout.fragment_powermonitor_bar_layout;
    }


    private void setHarmonicsBarMode(int mode){

    }

    @Override
    public void onInitViews() {
        String[] showItem2=getString(R.string.set_wir_item).split(",");
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        powerMonitorBarView = (PowerMonitorBarView) findViewById(R.id.powerMonitorBarView);
        powerMonitorBarView.setPowerMonitorTopView(20f,showItem2[wir_index] +"      " + configV + "      " + configHz);

        customTimer = new CustomTimer();
        customTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
                final int time= (int) l;
                powerMonitorBarView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0)
                            powerMonitorBarView.setTimeText(DataFormatUtil.getTime(time));
                        else {
                            powerMonitorBarView.setTimeText("");
                        }
                    }
                });

            }
        });
        startRecord();
    }

    private void startRecord(){
        customTimer.StartCustomTimer();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void updateRightList(int wir_index, int harmonicsType) {
        wir_right_index = 0;
        switch (wir_index){
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6:// 2½-ELEMENT

            case 7://1Q SPLIT PHASE

            case 9://1Q +NEUTRAL

            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA

            case 8://1Q IT NO NEUTRAL

                break;
        }

    }

    public void zoomScale(float yScale){
        zoomScale(yScale,yScale);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void moveCursor(int i) {


    }

    private void zoomScale(float xScale,float yScale){

    }

}
