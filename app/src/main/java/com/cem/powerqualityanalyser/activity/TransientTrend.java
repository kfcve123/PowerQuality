package com.cem.powerqualityanalyser.activity;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.enums.TrendRightModeEnum;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.newchart.InrushTrendView;
import com.cem.powerqualityanalyser.newchart.TransientTrendView;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 瞬变 Trend
 */
public class TransientTrend extends BaseFragmentTrend {

    private TransientTrendView transientTrendView;
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
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int firstPopIndex, final int secondPopIndex,final boolean showCursor) {
        if (modelAllData != null)
            if (transientTrendView != null) {
                transientTrendView.post(new Runnable() {
                    @Override
                    public void run() {
 //                       transientTrendView.showMeterAllParamObj(getSelectModelLineData(modelAllData, wir_index, firstPopIndex, secondPopIndex), showCursor);
                        showTransientTrendChart(getDataSize,modelAllData,wir_index,firstPopIndex,secondPopIndex,showCursor );
                    }
                });
            }
    }

    /**
     * Tranisent 根据接线方式， V,A,F popIndex 和L1,L2,L3 ,N的
     * @param getDataSize
     * @param modelAllData
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     * @param showCursor
     */
    private void showTransientTrendChart(int getDataSize,final ModelAllData modelAllData, final int wir_index, final int firstPopIndex, final int secondPopIndex,final boolean showCursor){
        List<ModelLineData> modelAllDataList = modelAllData.getModelLineData();
        selectAllDataList.clear();
        if(modelAllDataList!=null) {
            switch (firstPopIndex) {
                case 0://取电压的波形数据·  ·
                    selectAllDataList.addAll(modelAllDataList.subList(2,102));
                    break;
                case 1://取电流的波形数据
                    selectAllDataList.addAll(modelAllDataList.subList(102,202));
                    break;
            }
            for (ModelLineData modelLineData : selectAllDataList) {
                transientTrendView.showMeterAllParamObj(modelLineData, showCursor);
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
        if(transientTrendView!=null){
            setTransientModeIndex(wir_index,firstPopIndex,secondPopIndex);
            transientTrendView.updateTrendRightMode(getRightMode(wir_index,firstPopIndex,secondPopIndex));
        }
    }

    /**
     * 打开光标时的顶部显示 和数据值切换处理
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     */
    public void openCursorTopShow(final int wir_index, final int firstPopIndex, final int secondPopIndex){
        if(transientTrendView!=null){
            transientTrendView.setTransientCursorIndex(wir_index,firstPopIndex,secondPopIndex);
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
                } else if(firstPopIndex ==2){
                    mode = TrendRightModeEnum.FL1;
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
                } else if(firstPopIndex ==2){
                    mode = TrendRightModeEnum.FL1;
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
                } else if(firstPopIndex ==2){
                    mode = TrendRightModeEnum.FL1;
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
                else if(firstPopIndex ==2)
                    mode = TrendRightModeEnum.FL1;
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
                } else if(firstPopIndex ==2){
                    mode = TrendRightModeEnum.FL1;
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


    /**
     * 选择是拿那个数据对象的值进行显示
     * @param modelAllData
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     * @return
     */
    private ModelLineData getSelectModelLineData(ModelAllData modelAllData,int wir_index,int firstPopIndex,int secondPopIndex){
        if(modelAllData!=null) {
            ModelLineData modelLineData = modelAllData.getModelLineData().get(firstPopIndex);
            /*switch (wir_index){
                case 0://3QWYE
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
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

        transientTrendView = (TransientTrendView) findViewById(R.id.transienttrendview);
        transientTrendView.setDragEnabled(true);
        transientTrendView.setScanleEnable(true);
        transientTrendView.setHzVisable(true);
        transientTrendView.setXRangeMaximum(6000);

        String[] showItem2 = getString(R.string.set_wir_item).split(",");
        transientTrendView.setTransientTopView(20f,showItem2[wir_index] + "      " +  configV + "      " + configHz);
        topBgRes = new Integer[]{R.mipmap.top_black_bg,R.mipmap.top_yellow_bg,R.mipmap.top_red_bg,R.mipmap.top_blue_bg,R.mipmap.top_green_bg};
//        topBgRes = getResources().getIntArray(R.array.top_backgroud_res_array);
        transientTrendView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1()-1],topBgRes[config.getSetup_Show_Color_VL2()-1],topBgRes[config.getSetup_Show_Color_VL3()-1],topBgRes[config.getSetup_Show_Color_VN()-1]);
        transientTrendView.setTopUnit("V","V","V","");
        updateTrendRightAndPopMode(wir_index,0,-1);
        customTimer = new CustomTimer();
        customTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
                final int time= (int) l;
                transientTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0)
                            transientTrendView.setTimeText(DataFormatUtil.getTime(time));
                        else {
                            transientTrendView.setTimeText("");
                        }
                    }
                });

            }
        });
        startRecord();
    }


    public void setTopUnit(String unitL1,String unitL2,String unitL3,String unitLn){
        if(transientTrendView!=null)
            transientTrendView.setTopUnit(unitL1,unitL2,unitL3,unitLn);
    }


    private void startRecord(){
        customTimer.StartCustomTimer();
    }


    @Override
    public int setContentView() {
        return R.layout.fragment_transient_trend_layout;
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
    public void setTransientModeIndex(int wir_index,int firstPopIndex,int secondPopIndex) {
        if(transientTrendView!=null) {
            transientTrendView.setTransientModeIndex(wir_index,firstPopIndex,secondPopIndex);
            if(lastPosition!= firstPopIndex) {
                lastPosition = firstPopIndex;
 //               inrushTrendView.setNewData(true);
            }
        }
    }

    public void showCursor(boolean enable){
        if(transientTrendView!=null)
            transientTrendView.showCursor(enable);
    }

    public void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(transientTrendView!=null)
            transientTrendView.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(transientTrendView!=null)
            transientTrendView.moveCursor(i);
    }


}
