package com.cem.powerqualityanalyser.activity;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.enums.TrendRightModeEnum;
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
        if (modelAllData != null)
            if (unbalanceTrendView != null) {
                unbalanceTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        unbalanceTrendView.setInrushHz(modelAllData.getHzData() == null ? "----Hz" : modelAllData.getHzData() + "Hz");
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

    /**
     * 两个PopWindow Index 决定如何显示数据 ，和Meter 部分断开联系
     *
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex 默认 -1 全显示
     */
    public void updateTrendRightAndPopMode(final int wir_index, final int firstPopIndex, final int secondPopIndex) {
        if (unbalanceTrendView != null) {
            setVoltsModeIndex(wir_index, firstPopIndex, secondPopIndex);
            unbalanceTrendView.updateTrendRightMode(getRightMode(wir_index, firstPopIndex, secondPopIndex));
        }
    }

    /**
     * 打开光标时的顶部显示 和数据值切换处理
     *
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     */
    public void openCursorTopShow(final int wir_index, final int firstPopIndex, final int secondPopIndex) {
        if (unbalanceTrendView != null) {
            unbalanceTrendView.setTransientCursorIndex(wir_index, firstPopIndex, secondPopIndex);
        }
    }

    private ModelLineData getSelectModelLineData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {
        if (modelAllData != null) {
            ModelLineData modelLineData = new ModelLineData();
            switch (wir_index) {
                case 0://3QWYE
                    switch (wir_right_index) {
                        case 0://4V
                        case 1://4A
                        case 2://L1
                        case 3://L2
                        case 4://L3
                        case 5://N
                            switch (popwindowsIndex) {
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
        if (modelLineData != null) {
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
        String[] showItem2 = getString(R.string.set_wir_item).split(",");
        unbalanceTrendView.setInrushTopView(20f, showItem2[wir_index] + "      " + configV + "      " + configHz);
        topBgRes = new Integer[]{R.mipmap.top_black_bg, R.mipmap.top_yellow_bg, R.mipmap.top_red_bg, R.mipmap.top_blue_bg, R.mipmap.top_green_bg};
//        topBgRes = getResources().getIntArray(R.array.top_backgroud_res_array);
        unbalanceTrendView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1() - 1], topBgRes[config.getSetup_Show_Color_VL2() - 1], topBgRes[config.getSetup_Show_Color_VL3() - 1], topBgRes[config.getSetup_Show_Color_VN() - 1]);
        setVoltsModeIndex(wir_index, 0, 0);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_unbalance_trend_layout;
    }

    public void setShowMeterData() {

    }

    public void setHold(boolean isHold) {

    }

    public void setVoltsModeIndex(int wir_index, int wir_right_index, int positio) {
        if (unbalanceTrendView != null) {
            unbalanceTrendView.setVoltsModeIndex(wir_index, wir_right_index, positio);
            if (lastPosition != positio) {
                lastPosition = positio;
                unbalanceTrendView.setNewData(true);
            }
        }
    }

    public void showCursor(boolean enable) {
        if (unbalanceTrendView != null)
            unbalanceTrendView.showCursor(enable);
    }

    public void zoomScale(float yScale) {
        zoomScale(yScale, 1f);
    }

    private void zoomScale(float xScale, float yScale) {
        if (unbalanceTrendView != null)
            unbalanceTrendView.zoomScale(xScale, yScale);
    }

    public void moveCursor(int i) {
        if (unbalanceTrendView != null)
            unbalanceTrendView.moveCursor(i);
    }

    private TrendRightModeEnum getRightMode(int wir_index, int firstPopIndex, int secondPopIndex) {
        TrendRightModeEnum mode = TrendRightModeEnum.NONE;
        switch (wir_index) {
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
                } else if (firstPopIndex == 2) {
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
                } else if (firstPopIndex == 2) {
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
                } else if (firstPopIndex == 2) {
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
                if (firstPopIndex == 0)
                    mode = TrendRightModeEnum.VL1;
                else if (firstPopIndex == 2)
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
                } else if (firstPopIndex == 2) {
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

}
