package com.cem.powerqualityanalyser.activity;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.newchart.InrushTrendView;
import com.cem.powerqualityanalyser.newchart.UnbalanceTrendView;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 瞬变 Meter
 */
public class UnbalanceTrend extends BaseFragmentTrend {

    private UnbalanceTrendView unbalanceTrendView;
    private int lastPosition = -1;
    private Integer[] topBgRes;
    private String configV;
    private String configHz;

    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int funTypeIndex) {
        if(modelAllData!=null)
            if(unbalanceTrendView!=null) {
                unbalanceTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        unbalanceTrendView.setInrushHz(modelAllData.getHzData()==null?"----Hz":modelAllData.getHzData() + "Hz");
                        unbalanceTrendView.showMeterAllParamObj(modelAllData.getModelLineData().get(funTypeIndex));
                    }
                });
            }
    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int wir_right_index, final int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    private ModelLineData getSelectModelLineData(ModelAllData modelAllData,int wir_index,int wir_right_index,int popwindowsIndex){
        if(modelAllData!=null) {
            ModelLineData modelLineData = new ModelLineData();
            switch (wir_index){
                case 0://3QWYE
                    switch (wir_right_index){
                        case 0://4V
                        case 1://4A
                        case 2://L1
                        case 3://L2
                        case 4://L3
                        case 5://N
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                case 7://1Q SPLIT PHASE
                case 8://1Q IT NO NEUTRAL
                case 9://1Q +NEUTRAL
                    break;
            }
            return modelLineData;
        }
        return null;
    }

    public void setShowMeterData(final ModelLineData modelLineData) {
        if(modelLineData!=null) {
            if (unbalanceTrendView != null) {
                unbalanceTrendView.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }

    @Override
    public void onInitViews() {

        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();

        unbalanceTrendView = (UnbalanceTrendView) findViewById(R.id.balanceView);
        unbalanceTrendView.setDragEnabled(true);
        unbalanceTrendView.setScanleEnable(true);
        String[] showItem2=getString(R.string.set_wir_item).split(",");
        unbalanceTrendView.setInrushTopView(20f,showItem2[wir_index] + "      " +  configV + "      " + configHz);
        topBgRes = new Integer[]{R.mipmap.top_black_bg,R.mipmap.top_yellow_bg,R.mipmap.top_red_bg,R.mipmap.top_blue_bg,R.mipmap.top_green_bg};
//        topBgRes = getResources().getIntArray(R.array.top_backgroud_res_array);
        unbalanceTrendView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1()-1],topBgRes[config.getSetup_Show_Color_VL2()-1],topBgRes[config.getSetup_Show_Color_VL3()-1],topBgRes[config.getSetup_Show_Color_VN()-1]);
        setVoltsModeIndex(wir_index,0,0);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_unbalance_trend_layout;
    }

    public void setShowMeterData(){

    }

    public void setHold(boolean isHold){

    }

    public void setVoltsModeIndex(int wir_index,int wir_right_index,int positio) {
        if(unbalanceTrendView!=null) {
            unbalanceTrendView.setVoltsModeIndex(wir_index,wir_right_index,positio);
            if(lastPosition!= positio) {
                lastPosition = positio;
                unbalanceTrendView.setNewData(true);
            }
        }
    }

    public void showCursor(boolean enable){
        if(unbalanceTrendView!=null)
            unbalanceTrendView.showCursor(enable);
    }

    private void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(unbalanceTrendView!=null)
            unbalanceTrendView.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(unbalanceTrendView!=null)
            unbalanceTrendView.moveCursor(i);
    }


}
