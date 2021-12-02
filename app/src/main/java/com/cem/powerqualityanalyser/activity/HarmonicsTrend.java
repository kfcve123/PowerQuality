package com.cem.powerqualityanalyser.activity;


import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.HarmonicsBarChart;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.newchart.HarmoView;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


public class HarmonicsTrend extends BaseFragmentTrend implements OnChartValueSelectedListener {

    private TextView Group_list_middleText, Group_list_leftText, Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;

    private List<RightListViewItemObj> strList;

    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    //   private int harmonicsType = 0;
    private HarmoView harmoView;

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }

    @Override
    public void setShowMeterData(final ModelAllData list) {

    }

    @Override
    public void setShowMeterData(ModelAllData list, final int funTypeIndex) {

        final List<ModelLineData> modelLineData = list.getModelLineData();
        if (modelLineData != null) {
            if (harmoView != null) {
                harmoView.post(new Runnable() {
                    @Override
                    public void run() {
                        harmoView.setShowMeterData3(modelLineData, wir_index, funTypeIndex, wir_right_index);
                    }
                });
            }
        }
    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }


    /**
     * 实时值
     *
     * @param wir_index
     * @param wir_right_index
     * @param list            如何定义
     */
    public void addSelectMeterData(int wir_index, int wir_right_index, ModelAllData list, int funTypeIndex) {
        switch (wir_index) {
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
        return R.layout.fragment_harmonics_trend_layout;
    }


    private void setHarmonicsBarMode(int mode) {
        setHarmonicsBarMode(mode, "");
    }

    private void setHarmonicsBarMode(int mode, String rightItem) {
        harmoView.setHarmonicsBarMode(mode, rightItem);
        harmoView.refeshHeadColor(config.getSetup_Show_Color_VL1(), config.getSetup_Show_Color_VL2(), config.getSetup_Show_Color_VL3(), config.getSetup_Show_Color_VN(), rightItem);
    }


    @Override
    public void onInitViews() {
        String[] showItem2 = getString(R.string.set_wir_item).split(",");
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        harmoView = (HarmoView) findViewById(R.id.harmoview);
        harmoView.setWir(showItem2[wir_index], wir_index);

        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_leftText = (TextView) findViewById(R.id.Group_list_leftText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);
        strList = new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);
        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        rightModeView.setUpDownClick(false);
        rightModeView.hideUpDownView();


        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                setHarmonicsBarMode(3);
                rightModeView.hideUpDownView();
                strList.clear();
                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
//                strList.add(new RightListViewItemObj("-.+", -1));

                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});

                break;

            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                setHarmonicsBarMode(3);
                strList.clear();
                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));
                strList.add(new RightListViewItemObj("L2L3", -1));
                strList.add(new RightListViewItemObj("L3L1", -1));
                //                strList.add(new RightListViewItemObj("-.+", -1));

                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "PEAK+(V≃)", "PEAK-(V≃)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                break;

            case 7://1Q SPLIT PHASE
                setHarmonicsBarMode(2);
                strList.clear();
                strList.add(new RightListViewItemObj("2L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));

                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V≃)", "PEAK-(V≃)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});

                break;
            case 8://1Q IT NO NEUTRAL
                setHarmonicsBarMode(3);
                strList.clear();
                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V≃)", "PEAK-(V≃)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                break;
            case 9://1Q +NEUTRAL
                setHarmonicsBarMode(3);
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));

                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V≃)", "PEAK-(V≃)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                break;

        }

        //       ((HarmonicsActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
        rightModeView.setModeList(strList);

        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
                onWirAndRightIndexCallBack.returnWirAndRight(wir_index, wir_right_index);
                updateWirData(wir_index, wir_right_index);
            }
        });

        setFocusOnLeft();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     *
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index) {
        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://L1
                        setHarmonicsBarMode(1, "L1");
                        break;
                    case 2://N
                        setHarmonicsBarMode(1, "N");
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                setHarmonicsBarMode(1);
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://2L
                        setHarmonicsBarMode(2);
                        break;
                    case 1://L1
                        setHarmonicsBarMode(1, "L1");
                        break;
                    case 2://L2
                        setHarmonicsBarMode(1, "L2");
                        break;
                    case 3://N
                        setHarmonicsBarMode(1, "N");
                        break;

                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
            case 0://3QWYE
                switch (wir_right_index) {//切换右边选项
                    case 0://3L
                        setHarmonicsBarMode(3);
                        break;
                    case 1://L1
                        setHarmonicsBarMode(1, "L1");
                        break;
                    case 2://L2
                        setHarmonicsBarMode(1, "L2");
                        break;
                    case 3://L3
                        setHarmonicsBarMode(1, "L3");
                        break;
                    case 4://N
                        setHarmonicsBarMode(1, "N");
                        break;


                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3L
                        setHarmonicsBarMode(3);
                        break;
                    case 1://L1L2
                        setHarmonicsBarMode(1, "L1L2");
                        break;
                    case 2://L2L3
                        setHarmonicsBarMode(1, "L2L3");
                        break;
                    case 3://L3L2
                        setHarmonicsBarMode(1, "L3L1");
                        break;

                }
                break;


        }

    }

    public void setHarmonicsType(int funTypeIndex) {//切换A ,S  V
        if (wir_index == 0 || wir_index == 5 || wir_index == 6 || wir_index == 7 || wir_index == 9) {//VSA
            harmoView.setHarmonicsType(getResources().getStringArray(R.array.asv_array)[funTypeIndex]);
        } else {//ASU
            harmoView.setHarmonicsType(getResources().getStringArray(R.array.asu_array)[funTypeIndex]);
        }
        updateRightList(wir_index, funTypeIndex);
        //       updateWirData(wir_index,0,harmonicsType);
    }

    private void updateRightList(int wir_index, int harmonicsType) {
        wir_right_index = 0;
        switch (wir_index) {
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6:// 2½-ELEMENT
                switch (harmonicsType) {
                    case 2://A
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        break;
                    case 1://S
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                    case 0://V
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (harmonicsType) {
                    case 2://A
                        strList.clear();
                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                    case 1://S
                        strList.clear();
                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                    case 0://V
                        strList.clear();
                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (harmonicsType) {
                    case 2://A
                        strList.clear();
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                    case 1://S

                        strList.clear();
                        strList.add(new RightListViewItemObj("L1", -1));
                        break;
                    case 0://V
                        strList.clear();
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (harmonicsType) {
                    case 2://A
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                    case 1://S
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));
                        break;
                    case 0://U
                        strList.clear();
                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (harmonicsType) {
                    case 2://A
                        strList.clear();
                        break;
                    case 1://S
                        strList.clear();
                        break;
                    case 0://U
                        strList.clear();
                        break;
                }
                break;
        }
        rightModeView.setSelection(0);
        rightModeView.hideUpDownView();
        rightModeView.notifyDataSetChanged();
    }

    public void showCursor(boolean enable) {
        if (harmoView != null)
            harmoView.showCursor(enable);
    }

    public void zoomScale(float yScale) {
        zoomScale(yScale, yScale);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void moveCursor(int i) {
        if (harmoView != null)
            harmoView.moveCursor(i, wir_right_index);
    }

    public void setRightIndex(int right) {
        rightModeView.setSelection(right);
        rightModeView.invalidate();
    }

    private void zoomScale(float xScale, float yScale) {
        if (harmoView != null)
            harmoView.zoomScale(xScale, yScale);
    }

    public void setFocusOnLeft() {
        harmoView.setFocusable(true);
        harmoView.setFocusableInTouchMode(true);
        harmoView.requestFocus();

        rightModeView.setListViewFocusable(false);
        rightModeView.setListViewFocusableInTouchMode(false);
        rightModeView.lostFocus(true);
    }

    public void setFocusOnRight() {
        harmoView.setFocusable(false);
        harmoView.setFocusableInTouchMode(false);

        rightModeView.getViewFoucs();
        rightModeView.lostFocus(false);
//        rightModeView.setSelection(0);

    }

}
