package com.cem.powerqualityanalyser.activity;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.enums.TrendRightModeEnum;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.newchart.DipsSwellsTrendView;
import com.cem.powerqualityanalyser.newchart.InrushTrendView;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 瞬变 Meter
 */
public class DipsSwellsTrend extends BaseFragmentTrend {


    private DipsSwellsTrendView dipsSwellsTrendView;
    private int lastPosition = -1;
    private Integer[] topBgRes;
    private String configV;
    private String configHz;
    private CustomTimer customTimer;// 倒计时计时器
    private int getDataSize = 100;
    private List<ModelLineData> selectAllDataList = new ArrayList<>();


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int wir_right_index, final int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int firstPopIndex, final int secondPopIndex, final boolean showCursor) {
        if(modelAllData!=null)
            if(dipsSwellsTrendView!=null) {
                dipsSwellsTrendView.post(new Runnable() {
                    @Override
                    public void run() {
  //                      dipsSwellsTrendView.showMeterAllParamObj(getSelectModelLineData(modelAllData,wir_index,firstPopIndex,secondPopIndex),showCursor);
                        showDipSwellsTrendChart(getDataSize,modelAllData, wir_index, firstPopIndex, secondPopIndex, showCursor);

                    }
                });
            }
    }

    /**
     * Inrush 根据接线方式，V,A popIndex 和 L1,L2,L3,N的popInde决定筛选哪些数据去显示
     * @param modelAllData
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     * @param showCursor
     */
    private void showDipSwellsTrendChart(int getDataSize,final ModelAllData modelAllData, final int wir_index, final int firstPopIndex, final int secondPopIndex,final boolean showCursor){
        List<ModelLineData> modelAllDataList = modelAllData.getModelLineData();
        selectAllDataList.clear();
        if(modelAllDataList!=null) {
            selectAllDataList.addAll(modelAllDataList);
            for (ModelLineData modelLineData : selectAllDataList) {
                dipsSwellsTrendView.showMeterAllParamObj(modelLineData, showCursor);
            }
        }
    }


    /**
     *  两个PopWindow Index 决定如何显示数据 ，和Meter 部分断开联系
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex 默认 -1 全显示
     */
    public void updateTrendRightAndPopMode(final int wir_index, final int firstPopIndex, final int secondPopIndex){
        if(dipsSwellsTrendView!=null){
            setDipsModeIndex(wir_index,firstPopIndex,secondPopIndex);
            dipsSwellsTrendView.updateTrendRightMode(getRightMode(wir_index,firstPopIndex,secondPopIndex));
        }
    }

    /**
     * 打开光标时的顶部显示 和数据值切换处理
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     */
    public void openCursorTopShow(final int wir_index, final int firstPopIndex, final int secondPopIndex){
        if(dipsSwellsTrendView!=null){
            dipsSwellsTrendView.setDipsCursorIndex(wir_index,firstPopIndex,secondPopIndex);
        }
    }

    private TrendRightModeEnum getRightMode(int wir_index, int firstPopIndex, int secondPopIndex){
        TrendRightModeEnum mode = TrendRightModeEnum.NONE;
        switch (wir_index){
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                if (firstPopIndex == 0) { //Vrms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.VL1;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.VL2;
                            break;
                        case 2://L3
                            mode = TrendRightModeEnum.VL3;
                            break;
                        case 3://N
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.V4L1L2L3N;
                            break;
                    }
                } else {//Arms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.AL1;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.AL2;
                            break;
                        case 2://L3
                            mode = TrendRightModeEnum.AL3;
                            break;
                        case 3://N
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.A4L1L2L3N;
                            break;
                    }
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                if (firstPopIndex == 0) {//Vrms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.L1L2;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.L2L3;
                            break;
                        case 2://L3
                            mode = TrendRightModeEnum.L3L1;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.U3L1L2L2L3L3L1;
                            break;
                    }
                } else {//Arms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.AL1;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.AL2;
                            break;
                        case 2://L3
                            mode = TrendRightModeEnum.AL3;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.A3L1L2L2L3L3L1;
                            break;
                    }
                }
                break;
            case 7://1Q SPLIT PHASE
                if (firstPopIndex == 0) {//Vrms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.VL1;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.VL2;
                            break;
                        case 2://N
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.V3L1L2N;
                            break;
                    }
                } else {//Arms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.AL1;
                            break;
                        case 1://L2
                            mode = TrendRightModeEnum.AL2;
                            break;
                        case 2://L3
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.A3L1L2N;
                            break;
                    }
                }
                break;
            case 8://1Q IT NO NEUTRAL
                if(firstPopIndex == 0)
                    mode = TrendRightModeEnum.VL1;
                else
                    mode = TrendRightModeEnum.AL1;
                break;
            case 9://1Q +NEUTRAL
                if (firstPopIndex == 0) {//Vrms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.VL1;
                            break;
                        case 1://N
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.V2L1N;
                            break;
                    }
                } else {//Arms
                    switch (secondPopIndex) {
                        case 0://L1
                            mode = TrendRightModeEnum.AL1;
                            break;
                        case 1://N
                            mode = TrendRightModeEnum.N;
                            break;
                        default:
                        case -1:
                            mode = TrendRightModeEnum.A2L1N;
                            break;
                    }
                }
                break;
        }
        return mode;
    }

    private ModelLineData getSelectModelLineData(ModelAllData modelAllData,int wir_index,int firstPopIndex,int secondPopIndex){
        if(modelAllData!=null) {
            ModelLineData modelLineData = modelAllData.getModelLineData().get(firstPopIndex);
            /*switch (wir_index){
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
            }*/
            return modelLineData;
        }
        return null;
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }

    @Override
    public void onInitViews() {

        configV = config.getConfig_vnom_value();
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        switch (config.getConfig_nominal()){
            case 0:
                getDataSize = 100;
                break;
            case 1:
            case 2:
                getDataSize = 120;
                break;
        }

        dipsSwellsTrendView = (DipsSwellsTrendView) findViewById(R.id.dipsSwellsTrendView);
        dipsSwellsTrendView.setDragEnabled(true);
        dipsSwellsTrendView.setScanleEnable(true);
        String[] showItem2=getString(R.string.set_wir_item).split(",");
        dipsSwellsTrendView.setDipsTopView(20f,showItem2[wir_index] +"      " + configV + "      " + configHz);
        topBgRes = new Integer[]{R.mipmap.top_black_bg,R.mipmap.top_yellow_bg,R.mipmap.top_red_bg,R.mipmap.top_blue_bg,R.mipmap.top_green_bg};
//        topBgRes = getResources().getIntArray(R.array.top_backgroud_res_array);
        dipsSwellsTrendView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1()-1],topBgRes[config.getSetup_Show_Color_VL2()-1],topBgRes[config.getSetup_Show_Color_VL3()-1],topBgRes[config.getSetup_Show_Color_VN()-1]);
        updateTrendRightAndPopMode(wir_index,0,-1);
        customTimer = new CustomTimer();
        customTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
                final int time= (int) l;
                dipsSwellsTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0) {
                            dipsSwellsTrendView.setTimeText(DataFormatUtil.getTime(time));
                            dipsSwellsTrendView.setTimeView(true);
                        }else {
                            dipsSwellsTrendView.setTimeText("");
                            dipsSwellsTrendView.setTimeView(false);
                        }
                    }
                });

            }
        });

    }

    public void setTopUnit(String unitL1,String unitL2,String unitL3,String unitLn){
        if(dipsSwellsTrendView!=null)
            dipsSwellsTrendView.setTopUnit(unitL1,unitL2,unitL3,unitLn);
    }

    private boolean startRecord = true;

    public void setStartRecord(boolean startRecord){
        this.startRecord = startRecord;
        dipsSwellsTrendView.setNewData(true);
    }

    private long starTime;
    public long getStartTime(){
        return starTime;
    }
    private void startRecord(){
        if(customTimer!=null) {
            if (!startRecord) {
                starTime = System.currentTimeMillis();
                startRecord = true;
                customTimer.StartCustomTimer();
            }
        }
    }

    public void stopRecordTime() {
        customTimer.StopTimer();
        dipsSwellsTrendView.post(new Runnable() {
            @Override
            public void run() {
                dipsSwellsTrendView.setTimeText(DataFormatUtil.getTime(0));
                dipsSwellsTrendView.setNewData(true);
            }
        });

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_dipssweels_trend_layout;
    }

    public void setShowMeterData(){

    }

    public void setHold(boolean isHold){

    }
    /**
     * 修改数据显示和曲线显示
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     */
    public void setDipsModeIndex(int wir_index,int firstPopIndex,int secondPopIndex) {
        if(dipsSwellsTrendView!=null) {
            dipsSwellsTrendView.setDipsModeIndex(wir_index,firstPopIndex,secondPopIndex);
            if(lastPosition!= firstPopIndex) {
                lastPosition = firstPopIndex;
//                dipsSwellsTrendView.setNewData(true);
            }
        }
    }

    public void showCursor(boolean enable){
        if(dipsSwellsTrendView!=null)
            dipsSwellsTrendView.showCursor(enable);
    }

    public void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(dipsSwellsTrendView!=null)
            dipsSwellsTrendView.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(dipsSwellsTrendView!=null)
            dipsSwellsTrendView.moveCursor(i);
    }



}
