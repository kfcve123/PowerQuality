package com.cem.powerqualityanalyser.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 瞬变 Meter
 */
public class InrushMeter extends BaseFragmentTrend {

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1, groupListObj2;
    private TextView Group_list_middleText, Group_list_leftText, Group_list_rightText;
    private ImageView Group_list_rightview, leftFocusIv;
    private RightModeView rightModeView;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private int showItem2 = 3;
    private boolean changeRightIndex;

    private TextView tv_hz;
    private String configV;
    private String configHz;


    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_leftText = (TextView) findViewById(R.id.Group_list_leftText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);
        strList = new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);

        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        leftFocusIv = (ImageView) findViewById(R.id.icon_left_focus);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        groupListObj1 = new MeterGroupListObj();
        groupListObj2 = new MeterGroupListObj();

        String[] showItems = getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(showItems[wir_index] + "  " + configV + "  " + configHz);
        Group_list_middleText.setText(R.string.allmeter_surge);
        Group_list_leftText.setText("");
        Group_list_rightview.setVisibility(View.INVISIBLE);
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
                refeshHeadColor(5, "3L");
                rightModeView.hideUpDownView();
                showItem = 5;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(4);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                strList.clear();

                strList.add(new RightListViewItemObj("4V", -1));
                strList.add(new RightListViewItemObj("4A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                break;
            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 3://2-ELEMENT
            case 4://3ØDELTA
                refeshHeadColor(5, "3L");
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));
                strList.add(new RightListViewItemObj("L2L3", -1));
                strList.add(new RightListViewItemObj("L3L1", -1));

                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                break;
            case 9://1Ø +NEUTRAL
                refeshHeadColor(4, "L1N");
                rightModeView.hideUpDownView();
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));
                strList.clear();
                strList.add(new RightListViewItemObj("2V", -1));
                strList.add(new RightListViewItemObj("2A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));
                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");
                break;
            case 8://1Ø IT NO NEUTRAL
                refeshHeadColor(4, "L1");
                rightModeView.hideUpDownView();
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                strList.clear();

                showItem2 = 2;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1_array));
                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, modelLineData, showItem2, "");
                break;
            case 7://1Ø SPLIT PHASE
                refeshHeadColor(4, "L1L2N");
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));
                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                break;

        }
        rightModeView.setModeList(strList);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount() < 1) {
                    stickyLayout.addItem(groupListObj1);
                    if (groupListObj2.getHeaderSize() > 0) {
                        stickyLayout.addItem(groupListObj2);
                    }
                }
                stickyLayout.notifyChildChanged();
            }
        });

        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
                if (onWirAndRightIndexCallBack != null)
                    onWirAndRightIndexCallBack.returnWirAndRight(wir_index, wir_right_index);
                updateWirData(wir_index, wir_right_index);
                setFocusOnRight();
            }
        });
        stickyLayout.setListFocusAble(false);
        rightModeView.setUpDownClick(false);
        rightModeView.getViewFoucs();
        rightModeView.setSelection(0);

    }

    protected void setRightSelectItem(int index) {
        if (rightModeView != null) {
            rightModeView.setSelection(index);
            updateWirData(wir_index, index);
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_transient_meter_layout;
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {
        List<ModelLineData> modelLineData = list.getModelLineData();
        if (modelLineData != null) {
            addSelectMeterData(wir_index, wir_right_index, list);
            if (stickyLayout != null) {
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list.getModelLineData().size() > 0) {
                            tv_hz.setText(list.getHzData() == null || list.getHzData().equals("- - -") ? "----Hz" : DataFormatUtil.formatValue(Float.valueOf(list.getHzData()), 2) + "Hz");
                        }
                        if (stickyLayout.showItemsCount() < 1) {
                            stickyLayout.addItem(groupListObj1);
                            if (groupListObj2.getHeaderSize() > 0) {
                                stickyLayout.addItem(groupListObj2);
                            }
                        }
                        stickyLayout.notifyChildChanged();
                    }
                });
            }
        }
    }


    private int lastfunIndex = -1;

    @Override
    public void setShowMeterData(final ModelAllData list, final int funTypeIndex) {
        List<ModelLineData> modelLineData = list.getModelLineData();
        if (modelLineData != null) {
            addSelectMeterData(wir_index, wir_right_index, list);
            if (stickyLayout != null) {
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list.getModelLineData().size() > 0) {
                            tv_hz.setText(list.getHzData() == null ? "50.00Hz" : list.getHzData() + "Hz");
                        }
                        if (stickyLayout.showItemsCount() < 1) {
                            stickyLayout.addItem(groupListObj1);
                            if (groupListObj2.getHeaderSize() > 0) {
                                stickyLayout.addItem(groupListObj2);
                            }
                        }
                        stickyLayout.notifyChildChanged();
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

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }

    /**
     * 实时值
     *
     * @param wir_index
     * @param wir_right_index
     * @param list            如何定义
     */
    public void addSelectMeterData(int wir_index, int wir_right_index, ModelAllData list) {
        switch (wir_index) {
            case 0://3Ø WYE
            case 5://3QHIGH LEG  groupListObj2 L1L2 L2L3 L3L1 N
            case 6://2½-ELEMENT
                switch (wir_right_index) {
                    case 0://4V
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "");
                        break;
                    case 1://4A
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L2");
                        break;
                    case 4://L3
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L3");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L3");
                        break;
                    case 5://N
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (wir_right_index) {
                    case 0://3U
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "");
                        break;
                    case 1://3A
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "");
                        break;
                    case 2://L1L2
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L1");
                        break;
                    case 3://L2L3
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L2");
                        break;
                    case 4://L3L1
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L3");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L3");
                        break;
                }
                break;
            case 9://1Ø +NEUTRAL
                switch (wir_right_index) {
                    case 0://2V
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                        break;
                    case 1://2A
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L1");
                        break;
                    case 3://N
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {
                    case 0://3V
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                        break;
                    case 1://3A
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "L2");
                        break;
                    case 4://N
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        break;
                }
                break;
            case 8://1Ø IT NO NEUTRAL   // groupListObj2  L1L2
                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, list.getModelLineData().get(0), showItem, "N");
                addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "N");
                break;
        }
    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     *
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index) {
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 9://1Ø +NEUTRAL
                switch (wir_right_index) {
                    case 0://2V
                        refeshHeadColor(4, "L1N");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");

                        break;
                    case 1://2A
                        refeshHeadColor(4, "L1N");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");

                        break;
                    case 2://L1
                        refeshHeadColor(4, "L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L1");

                        break;
                    case 3://N
                        refeshHeadColor(4, "N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "N");

                        break;
                }
                break;
            case 7://1Ø SPLIT PHASE
                switch (wir_right_index) {
                    case 0://3V
                        refeshHeadColor(4, "L1L2N");
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");

                        break;
                    case 1://3A
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");

                        break;
                    case 2://L1
                        refeshHeadColor(4, "L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L1");

                        break;
                    case 3://L2
                        refeshHeadColor(4, "L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L2");

                        break;
                    case 4://N
                        refeshHeadColor(4, "N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "N");

                        break;
                }
                break;
            case 8://1Ø IT NO NEUTRAL  没有右边侧边栏


                break;
            case 4://3Ø DELTA        这三个 显示需要调整？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
            case 3://2-ELEMENT
            case 2://3ØIT
            case 1://3Ø OPEN LEG
                switch (wir_right_index) {
                    case 0://3U
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                        stickyLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                if (stickyLayout.showItemsCount() == 2) {
                                    stickyLayout.removeItem(groupListObj2);
                                }
                                stickyLayout.notifyChildChanged();
                            }
                        });
                        break;
                    case 1://3A
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                        stickyLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                if (stickyLayout.showItemsCount() == 2) {
                                    stickyLayout.removeItem(groupListObj2);
                                }
                                stickyLayout.notifyChildChanged();
                            }
                        });
                        break;
                    case 2://L1L2
                        refeshHeadColor(5, "L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        showItem2 = 2;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, modelLineData, showItem2, "L1");

                        stickyLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                if (stickyLayout.showItemsCount() == 1) {
                                    stickyLayout.addItem(groupListObj2);
                                }
                                stickyLayout.notifyChildChanged();
                            }
                        });


                        break;
                    case 3://L2L3
                        refeshHeadColor(5, "L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L2L3_array));
                        showItem2 = 2;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l2_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, modelLineData, showItem2, "L2");

                        stickyLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                if (stickyLayout.showItemsCount() == 1) {
                                    stickyLayout.addItem(groupListObj2);
                                }
                                stickyLayout.notifyChildChanged();
                            }
                        });

                        break;
                    case 4://L3L1
                        refeshHeadColor(5, "L3");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L3L1_array));
                        showItem2 = 2;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l3_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L3");
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2, modelLineData, showItem2, "L3");

                        stickyLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                if (stickyLayout.showItemsCount() == 1) {
                                    stickyLayout.addItem(groupListObj2);
                                }
                                stickyLayout.notifyChildChanged();
                            }
                        });

                        break;
                }
                break;
            case 0://3QWYE
            case 5://3Ø HIGH LEG   这三个 显示需要调整？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
            case 6:// 2½-ELEMENT
                switch (wir_right_index) {
                    case 0://4V
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "");

                        break;
                    case 1://4A
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1, modelLineData, showItem, "");
                        break;
                    case 2://L1
                        refeshHeadColor(5, "L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L1");
                        break;

                    case 3://L2
                        refeshHeadColor(5, "L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L2");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L2");
                        break;
                    case 4://L3
                        refeshHeadColor(5, "L3");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "L3");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "L3");
                        break;
                    case 5://N
                        refeshHeadColor(5, "N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1, modelLineData, showItem, "N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1, modelLineData, showItem, "N");
                        break;
                }
                break;
        }
        stickyLayout.notifyChildChanged();
    }

    public void setFocusOnLeft() {
        stickyLayout.requestFocus();
        stickyLayout.getViewFoucs();
        stickyLayout.setListFocusAble(true);
        stickyLayout.setListClickAble(true);
        stickyLayout.setListToucheAble(true);

        rightModeView.setListViewFocusable(false);
        rightModeView.setListViewFocusableInTouchMode(false);
        rightModeView.lostFocus(true);

        leftFocusIv.setVisibility(View.VISIBLE);
    }

    public void setFocusOnRight() {
        stickyLayout.setListFocusAble(false);


        rightModeView.getViewFoucs();
        rightModeView.lostFocus(false);
//        rightModeView.setSelection(0);

        leftFocusIv.setVisibility(View.GONE);

    }

    public void leftUpScroll() {
        upOnclick(getContext());
    }

    public void leftDownScroll() {
        downClick(getContext());
    }

    private void upOnclick(Context context) {
        MeterGroupListObj groupItem = stickyLayout.getGroupItem(0);
        int firstVisibleItem = stickyLayout.getFirstVisibleItem();
        if (firstVisibleItem >= 1) {
            stickyLayout.scrollToPosition(firstVisibleItem - 1);
        } else {
        }
    }

    private void downClick(Context context) {
        MeterGroupListObj groupItem = stickyLayout.getGroupItem(0);
        int lastVisibleItem = stickyLayout.getLastVisibleItem();
        if (lastVisibleItem <= (groupItem.getChildSize() - 1)) {
            stickyLayout.scrollToPosition(lastVisibleItem + 1);
        } else {
        }
    }

}
