package com.cem.powerqualityanalyser.activity;



import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.newchart.PowerEnergyView;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;


import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 趋势图
 */
public class PowerEnergyTrend extends BaseFragmentTrend {

    private int lastPosition = -1;
    private Integer[] topBgRes;
    private String configV;
    private String configHz;
    private PowerEnergyView powerEnergyView;


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int funTypeIndex) {
        if(modelAllData!=null)
            if(powerEnergyView!=null) {
                powerEnergyView.post(new Runnable() {
                    @Override
                    public void run() {
                        powerEnergyView.showMeterAllParamObj(modelAllData.getModelLineData().get(funTypeIndex));
                    }
                });
            }
    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }

    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();

        powerEnergyView = (PowerEnergyView) findViewById(R.id.powerenergyView);
        powerEnergyView.setDragEnabled(true);
        powerEnergyView.setScanleEnable(true);
        String[] showItem2=getString(R.string.set_wir_item).split(",");
        powerEnergyView.setPowerEnergyTopView(20f,configV + "      " + configHz + "      " +  showItem2[wir_index]);
        topBgRes = new Integer[]{R.mipmap.top_black_bg,R.mipmap.top_yellow_bg,R.mipmap.top_red_bg,R.mipmap.top_blue_bg,R.mipmap.top_green_bg};
//        topBgRes = getResources().getIntArray(R.array.top_backgroud_res_array);
        powerEnergyView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1()-1],topBgRes[config.getSetup_Show_Color_VL2()-1],topBgRes[config.getSetup_Show_Color_VL3()-1],topBgRes[config.getSetup_Show_Color_VN()-1]);
        setPowerModeIndex(wir_index,0,0);
    }


    @Override
    public int setContentView() {
        return R.layout.fragment_powerenergy_trend_layout;
    }



    public void setPowerModeIndex(int wir_index,int wir_right_index,int positio) {
        if (powerEnergyView != null) {
            powerEnergyView.setPowerModeIndex(wir_index,wir_right_index,positio);
            if (lastPosition != positio) {
                lastPosition = positio;
                powerEnergyView.setNewData(true);
            }
        }
    }

    public void setEnergyModeIndex(int wir_index,int wir_right_index,int positio) {
        if (powerEnergyView != null) {
            powerEnergyView.setPowerModeIndex(wir_index,wir_right_index,positio);
            if (lastPosition != positio) {
                lastPosition = positio;
                powerEnergyView.setNewData(true);
            }
        }
    }


    private void zoomScale(float yScale) {
        zoomScale(0f, yScale);
    }

    private void zoomScale(float xScale, float yScale) {
        if (powerEnergyView != null)
            powerEnergyView.zoomScale(xScale, yScale);
    }

    public void moveCursor(int i) {
        if (powerEnergyView != null)
            powerEnergyView.moveCursor(i);
    }

    public void setHold(boolean isHold) {
        powerEnergyView.setHold(isHold);
    }

}
