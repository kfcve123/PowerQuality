package com.cem.powerqualityanalyser.activity;

import android.content.res.TypedArray;
import android.view.View;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.newchart.ScopeTrendVie;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.MeterOscTools;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

public class ScopeTrendTest extends BaseFragmentTrend {

    private RightModeView rightModeView;
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    private ScopeTrendVie scopeView;
    private Integer[] topBgRes;


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    private List<ModelLineData> selectAllDataList = new ArrayList<>();

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int wir_right_index, final int popwindowsIndex, final boolean cursorOpen) {
        if (modelAllData != null) {
            final List<List<Float>> meterGraphData = MeterOscTools.getMeterData(wir_index, wir_right_index, modelAllData.getModelLineData());
            if (meterGraphData != null && meterGraphData.size() > 0) {
                try {
                    scopeView.post(new Runnable() {
                        @Override
                        public void run() {
                            scopeView.setLineData(meterGraphData, true, wir_right_index);
                            showTextValue(modelAllData.getModelLineData(), popwindowsIndex, wir_right_index);
                            scopeView.setScopeBottomValues(meterGraphData);
                        }
                    });
                } catch (Exception e) {

                }
            }

        }

    }


    public void setScopeModeIndex(int positio) {
        scopeView.setScopeModeIndex(positio);
    }


    private void showTextValue(List<ModelLineData> waveforms, int popwindowsIndex, int index) {
        ArrayList<Float> values = new ArrayList<>();
        ModelLineData waveformV = new ModelLineData();
        ModelLineData waveformA = new ModelLineData();
        switch (index) {
            case 0:
                switch (popwindowsIndex) {
                    case 0:
                        waveformV = waveforms.get(0);
                        break;
                    case 1:
                        waveformV = waveforms.get(8);
                        break;
                    case 2:
                        waveformV = waveforms.get(11);
                        break;
                }

                break;
            case 1:
                switch (popwindowsIndex) {
                    case 0:
                        waveformV = waveforms.get(4);
                        break;
                    case 1:
                        waveformV = waveforms.get(10);
                        break;
                    case 2:
                        waveformV = waveforms.get(13);
                        break;
                }

                break;
            case 2:
                switch (popwindowsIndex) {
                    case 0:
                        waveformA = waveforms.get(2);
                        break;
                    case 1:
                        waveformA = waveforms.get(9);
                        break;
                    case 2:
                        waveformA = waveforms.get(12);
                        break;
                }

                break;
            case 3:
            case 4:
            case 5:
            case 6:

                switch (popwindowsIndex) {
                    case 0:
                        waveformV = waveforms.get(0);
                        waveformA = waveforms.get(2);
                        break;
                    case 1:
                        waveformV = waveforms.get(8);
                        waveformA = waveforms.get(9);
                        break;
                    case 2:
                        waveformV = waveforms.get(11);
                        waveformA = waveforms.get(12);
                        break;
                }


                break;
        }

        switch (index) {
            case 0:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getaValue().getValueFl());
                if (waveformV.getbValue() != null)
                    values.add(waveformV.getbValue().getValueFl());
                if (waveformV.getcValue() != null)
                    values.add(waveformV.getcValue().getValueFl());
                if (waveformV.getnValue() != null)
                    values.add(waveformV.getnValue().getValueFl());
                break;
            case 2:
                if (popwindowsIndex == 1) {
                    if (waveformA.getaValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getaValue().getValueFl() / 10f, 2)));
                    if (waveformA.getbValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getbValue().getValueFl() / 10f, 2)));
                    if (waveformA.getcValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getcValue().getValueFl() / 10f, 2)));
                    if (waveformA.getnValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getnValue().getValueFl() / 10f, 2)));

                } else {
                    if (waveformA.getaValue() != null)
                        values.add(waveformA.getaValue().getValueFl());
                    if (waveformA.getbValue() != null)
                        values.add(waveformA.getbValue().getValueFl());
                    if (waveformA.getcValue() != null)
                        values.add(waveformA.getcValue().getValueFl());
                    if (waveformA.getnValue() != null)
                        values.add(waveformA.getnValue().getValueFl());
                }
                break;
            case 1:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getaValue().getValueFl());
                if (waveformV.getbValue() != null)
                    values.add(waveformV.getbValue().getValueFl());
                if (waveformV.getcValue() != null)
                    values.add(waveformV.getcValue().getValueFl());
                break;
            case 3:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getaValue().getValueFl());
                if (popwindowsIndex == 1) {
                    if (waveformA.getaValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getaValue().getValueFl() / 10f, 2)));
                } else {
                    if (waveformA.getaValue() != null)
                        values.add(waveformA.getaValue().getValueFl());
                }
                break;
            case 4:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getbValue().getValueFl());
                if (popwindowsIndex == 1) {
                    if (waveformA.getbValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getbValue().getValueFl() / 10f, 2)));
                } else {
                    if (waveformA.getbValue() != null)
                        values.add(waveformA.getbValue().getValueFl());
                }
                break;
            case 5:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getcValue().getValueFl());
                if (popwindowsIndex == 1) {
                    if (waveformA.getcValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getcValue().getValueFl() / 10f, 2)));
                } else {
                    if (waveformA.getcValue() != null)
                        values.add(waveformA.getcValue().getValueFl());
                }
                break;
            case 6:
                if (waveformV.getaValue() != null)
                    values.add(waveformV.getnValue().getValueFl());
                if (popwindowsIndex == 1) {
                    if (waveformA.getnValue() != null)
                        values.add(Float.valueOf(DataFormatUtil.formatValue(waveformA.getnValue().getValueFl() / 10f, 2)));
                } else {
                    if (waveformA.getnValue() != null)
                        values.add(waveformA.getnValue().getValueFl());
                }
                break;
        }
        scopeView.showRunTextValue(values, popwindowsIndex);
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }


    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        scopeView = (ScopeTrendVie) findViewById(R.id.scopeView);

        rightModeView = (RightModeView) findViewById(R.id.modeview);
        strList = new ArrayList();
        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        String[] showItems = getString(R.string.set_wir_item).split(",");
        int[] topsBg = getResources().getIntArray(R.array.top_backgroud_res_array);
//        scopeView.setScopeTopView(20f,showItems[wir_index] + "      " +  configV + "      " + configHz);
        scopeView.setScopeTopView(20f, showItems[wir_index] + "      " + configV + "      " + configHz);


        topBgRes = new Integer[]{R.mipmap.top_black_bg, R.mipmap.top_yellow_bg, R.mipmap.top_red_bg, R.mipmap.top_blue_bg, R.mipmap.top_green_bg};
        scopeView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1() - 1], topBgRes[config.getSetup_Show_Color_VL2() - 1], topBgRes[config.getSetup_Show_Color_VL3() - 1], topBgRes[config.getSetup_Show_Color_VN() - 1]);


        rightModeView.hideUpDownView();
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3ØWYE
            case 5://3ØHIGH LEG
            case 6://2½-ELEMENT
                strList.clear();
                strList.add(new RightListViewItemObj("4V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("4A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(5, "3L");
//                scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
//                scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");

                break;
            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 3://2-ELEMENT
            case 4://3ØDELTA
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                refeshHeadColor(5, "3L");
//                scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
//                scopeTrendView.setTopLeftTitle("L1","L2","L3","");
                break;
            case 7://1Ø SPLIT PHASE
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(3, "L1L2N");
//                scopeTrendView.setLineDataSetVisable(true,true,false,true,config);
//                scopeTrendView.setTopLeftTitle("L1","L2","","N");
                break;
            case 9://1Ø +NEUTRAL
                strList.clear();
                strList.add(new RightListViewItemObj("2V", -1));
                strList.add(new RightListViewItemObj("2A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(3, "L1N");
//                scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                scopeTrendView.setTopLeftTitle("L1","L2","","");
            case 8://1Ø IT NO NEUTRAL
                strList.clear();
                refeshHeadColor(5, "3L");
//                scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                scopeTrendView.setTopLeftTitle("L1","L2","","");
                break;
        }

        rightModeView.setModeList(strList);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
                onWirAndRightIndexCallBack.returnWirAndRight(wir_index, wir_right_index);
                updateWirData(wir_index, wir_right_index);
                setScopeModeIndex(wir_right_index);
                setFocusOnRight();
            }
        });

        setFocusOnLeft();
    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     *
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index) {
        switch (wir_index) {
            case 9://1Ø +NEUTRAL
                switch (wir_right_index) {
                    case 0://2V
                    case 1://2A
                        refeshHeadColor(3, "L1N");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("L1","N","","");
                        break;
                    case 2://L1
                        refeshHeadColor(3, "L1");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");

                    case 3://N
                        refeshHeadColor(3, "N");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                }

                break;
            case 7://1Ø SPLIT PHASE
                switch (wir_right_index) {
                    case 0://3V
                    case 1://3A
                        refeshHeadColor(3, "L1L2N");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
//                        scopeTrendView.setTopLeftTitle("L1","L2","N","");
                        break;
                    case 2://L1
                        refeshHeadColor(5, "L1");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                    case 3://L2
                        refeshHeadColor(5, "L2");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                    case 4://N
                        refeshHeadColor(5, "N");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                }

                break;
            case 8://1Ø IT NO NEUTRAL  没有右边侧边栏

                break;
            case 3://2-ELEMENT
            case 4://3QDELTA        这三个 显示需要调整
            case 2://3ØIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {
                    case 0://3V
                    case 2://3A
                        refeshHeadColor(5, "3L");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
//                        scopeTrendView.setTopLeftTitle("L1","L2","L3","");
                        break;
                    case 1://3U
                        refeshHeadColor(5, "3L");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
//                        scopeTrendView.setTopLeftTitle("L1L2","L2L3","L3L1","");
                        break;
                }
                break;
            case 0://3QWYE
            case 5://3QHIGH LEG   这三个 显示需要调整
            case 6:// 2½-ELEMENT
                refeshHeadColor(5, "3L");
                /*switch (wir_right_index){
                    case 0://4V
                        refeshHeadColor(5,"3L");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
//                        scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");

                        break;
                    case 1://3U
                        refeshHeadColor(5,"3L");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
//                        scopeTrendView.setTopLeftTitle("U12","U23","U31","");

                        break;
                    case 2://4A
                        refeshHeadColor(5,"3L");
//                        scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
//                        scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");

                        break;
                    case 3://L1
                        refeshHeadColor(5,"L1");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");

                        break;
                    case 4://L2
                        refeshHeadColor(5,"L2");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");

                        break;
                    case 5://L3
                        refeshHeadColor(5,"L3");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");

                        break;
                    case 6://N
                        refeshHeadColor(5,"N");
//                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
//                        scopeTrendView.setTopLeftTitle("V","A","","");

                        break;


                }*/
                break;
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_scope_trendtest_layout;
    }


    public void zoomScale(float yScale) {
        zoomScale(yScale, 1f);
    }

    private void zoomScale(float xScale, float yScale) {
        if (scopeView != null)
            scopeView.zoomScale(xScale, yScale);
    }

    public void showCursor(boolean enable) {
        if (scopeView != null)
            scopeView.showCursor(enable);
    }

    public void moveCursor(int i) {
        if (scopeView != null)
            scopeView.moveCursor(i);
    }

    public void fitScreen() {
        if (scopeView != null)
            scopeView.fitScreen();
    }

    public void setTouchEnable(boolean enable) {
        scopeView.setTouchEnable(enable);
    }

    public void setLastEntry(int iLastEntry) {
        scopeView.setLastEntry(iLastEntry);
    }


    public void setFocusOnLeft() {
        scopeView.setFocusable(true);
        scopeView.setFocusableInTouchMode(true);
        scopeView.requestFocus();

        rightModeView.setListViewFocusable(false);
        rightModeView.setListViewFocusableInTouchMode(false);
        rightModeView.lostFocus(true);
    }

    public void setFocusOnRight() {
        scopeView.setFocusable(false);
        scopeView.setFocusableInTouchMode(false);


        rightModeView.getViewFoucs();
        rightModeView.lostFocus(false);
//        rightModeView.setSelection(0);

    }
}
