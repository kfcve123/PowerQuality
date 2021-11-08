package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
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
 * 能量计算器 Meter
 */
public class EneryCalculatorMeter extends BaseFragmentTrend {

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1,groupListObj2;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private boolean changeRightIndex;
    private int showItem2 = 3;

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
        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);

        tv_hz = (TextView) findViewById(R.id.tv_hz);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        groupListObj1=new MeterGroupListObj();
        groupListObj2 = new MeterGroupListObj();
        rightModeView.setUpDownClick(false);

        String[] showItems=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(showItems[wir_index]  + "  " +  configV + "  " + configHz);
        Group_list_middleText.setText(R.string.energy_loss);
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
            case 3://2-ELEMENT   第一个界面和 0 一样，总体和6一样
            case 6://2½-ELEMENT  第一个界面和 0 一样，总体和3一样
                refeshHeadColor(5,"3L");
                rightModeView.hideUpDownView();
                showItem = 5;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(4);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 5;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                break;
            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 4://3ØDELTA
                refeshHeadColor(5,"3L");
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 5;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.energy_loss_head_array2));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");
                break;
            case 5://3ØHIGH LEG
                refeshHeadColor(5,"L1");
                rightModeView.hideUpDownView();
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 5;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");
                break;
            case 7://1Ø SPLIT PHASE
                refeshHeadColor(4,"L1L2N");
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1l2total));
                strList.clear();
                strList.add(new RightListViewItemObj("2L", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 4;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");
                break;

            case 8://1Ø IT NO NEUTRAL
                refeshHeadColor(4,"L1N");
                rightModeView.hideUpDownView();
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));
                strList.clear();

                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 2;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1_array));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");
                break;
            case 9://1Ø +NEUTRAL
                refeshHeadColor(4,"L1N");
                rightModeView.hideUpDownView();
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));
                strList.clear();

                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("Loss", -1));
                strList.add(new RightListViewItemObj(Res2String(R.string.loss_energy), -1,18));
                strList.add(new RightListViewItemObj("Cost", -1));

                showItem2 = 3;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));
                addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");
                break;

        }
        rightModeView.setModeList(strList);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                    if(groupListObj2.getHeaderSize()>0){
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
//                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index);
            }
        });
        stickyLayout.setListFocusAble(false);
        rightModeView.setUpDownClick(false);
        rightModeView.getViewFoucs();
        rightModeView.setSelection(0);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_dipsswells_meter_layout;
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {
        List<ModelLineData> modelLineData = list.getModelLineData();
//        log.e("--------" + modelLineData.size());
        if(modelLineData!=null) {
            addSelectMeterData(wir_index,wir_right_index,list);
            if(stickyLayout!=null) {
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
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

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
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list){
        switch (wir_index){
            case 0://3Ø WYE
            case 3://2-ELEMENT
            case 6://2½-ELEMENT
                switch (wir_right_index) {
                    case 0:


                        break;
                    case 1:


                        break;

                    case 2:


                        break;
                    case 3:

                        break;
                }

                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 4://3QDELTA

                break;
            case 5://3QHIGH LEG  groupListObj2 L1L2 L2L3 L3L1 N

                break;
            case 7://1Q SPLIT PHASE

                break;
            case 8://1Ø IT NO NEUTRAL

                break;
            case 9://1Ø +NEUTRAL

                break;
        }
    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index){
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3QWYE
            case 3://2-ELEMENT
            case 6:// 2½-ELEMENT
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(5,"3L");
                        rightModeView.hideUpDownView();
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
            case 4://3Ø DELTA
            case 2://3ØIT
            case 1://3Ø OPEN LEG
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(5,"3L");
                        rightModeView.hideUpDownView();
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.energy_loss_head_array2));

                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
            case 5://3Ø HIGH LEG
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(5,"L1");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
            case 7://1Ø SPLIT PHASE
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(4,"L1L2N");
                        rightModeView.hideUpDownView();
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1l2total));

                        showItem2 = 4;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
            case 8://1Ø IT NO NEUTRAL  没有右边侧边栏
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(4,"L1N");
                        rightModeView.hideUpDownView();
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));

                        showItem2 = 2;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1_array));
                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
            case 9://1Ø +NEUTRAL
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(4,"L1N");
                        rightModeView.hideUpDownView();
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));

                        showItem2 = 3;
                        groupListObj2.Clear();
                        groupListObj2.addHeader(getResources().getStringArray(R.array.energy_loss_head_l1total));
                        addMeterData(getSpannableString("kWfund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh forw"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm"), 5, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb"), 6, groupListObj1,modelLineData, showItem,"");

                        addMeterData(getSpannableString("Arms"), 0, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://Loss
                    case 2://Loss energy
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("kWh R loss",0.8f,0,10), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh var loss",0.8f,0,12), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh unb loss",0.8f,0,12), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh harm loss",0.8f,0,13), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh an loss",0.8f,0,11), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh tot loss",0.8f,0,12), 5, groupListObj1,modelLineData, showItem,"");

                        break;
                    case 3://L2
                        refeshHeadColor(5,"N");
                        rightModeView.hideUpDownView();
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        showItem2 = 5;
                        groupListObj2.Clear();
                        addMeterData(getSpannableString("Cost R"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost var"), 1, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost unb"), 2, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vost harm"), 3, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost An"), 4, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Cost tot"), 5, groupListObj1,modelLineData, showItem,"");
                        break;
                }
                break;
        }
        stickyLayout.notifyChildChanged();
    }


}
