package com.cem.powerqualityanalyser.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.enums.HarmonicsType;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.ColorList;
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


public class HarmonicsMeter extends BaseFragmentTrend {


    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;
    private int wir_index = 0; //接线方式
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    private int harmonicsType = 0;

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }

    public void setHarmonicsType(int type){
        this.harmonicsType = type;
        updateRightList(wir_index,harmonicsType);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {

    }

    @Override
    public void setShowMeterData(final ModelAllData list,int funTypeIndex) {
        try {
            List<ModelLineData> modelLineData = list.getModelLineData();
            if (modelLineData != null) {
                addSelectMeterData(wir_index, wir_right_index, list, funTypeIndex);
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
//                        if (list.getModelLineData().size() > 0) {
//                            tv_hz.setText(list.getHzData() == null ? "50.00Hz" : list.getHzData() + "Hz");
//                        }
                        if (stickyLayout.showItemsCount() < 1) {
                            stickyLayout.addItem(groupListObj1);
                        }
                        stickyLayout.notifyChildChanged();
                    }
                });
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }


    @Override
    public int setContentView() {
        return R.layout.fragment_harmonics_meter_layout;
    }

    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        wir_index = config.getSet_Wir();

        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_leftText = (TextView) findViewById(R.id.Group_list_leftText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);
        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);

        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        stickyLayout.setListFocusAble(false);
        rightModeView.getViewFoucs();

        groupListObj1=new MeterGroupListObj();
        rightModeView.setUpDownClick(false);

        String[] showItem2=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(configV + "  " + configHz + "  " +  showItem2[wir_index]);
        Group_list_middleText.setText(R.string.allmeter_harmonics);
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                rightModeView.hideUpDownView();
                showItem = 5;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(4);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
  //              strList.add(new RightListViewItemObj("-_+", -1));

                addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Vfund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                for(int i = 1;i<=50;i++) {
                    addMeterData(getSpannableString("H" + i + "(%f)"), 4+i, groupListObj1, modelLineData, showItem,"");
                }
                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                break;
            case 7://1Q SPLIT PHASE
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3n_array));
                strList.clear();
                strList.add(new RightListViewItemObj("2L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Vfund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                for(int i = 1;i<=50;i++) {
                    addMeterData(getSpannableString("H" + i + "(%f)"), 4+i, groupListObj1, modelLineData, showItem,"");
                }
                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;
            case 8://1Q IT NO NEUTRAL
                rightModeView.hideUpDownView();
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                strList.clear();

                addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Ufund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("ØU(°)"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(%f)"), 5,groupListObj1, modelLineData, showItem,"");

                for(int i = 1;i<=50;i++) {
                    addMeterData(getSpannableString("H" + i + "(%f)"), 5+i, groupListObj1, modelLineData, showItem,"");
                }

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                break;
            case 9://1Q +NEUTRAL
                rightModeView.hideUpDownView();
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Vfund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(%f)"), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(%f)"), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Vd"), 7,groupListObj1, modelLineData, showItem,"");
                for(int i = 1;i<=50;i++) {
                    addMeterData(getSpannableString("H" + i + "(%f)"), 7+i, groupListObj1, modelLineData, showItem,"");
                }
                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));
                strList.add(new RightListViewItemObj("L2L3", -1));
                strList.add(new RightListViewItemObj("L3L1", -1));
                //              strList.add(new RightListViewItemObj("-_+", -1));

                addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Ufund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("ØU(°)"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");

                for(int i = 1;i<=50;i++) {
                    addMeterData(getSpannableString("H" + i + "(%f)"), 4+i, groupListObj1, modelLineData, showItem,"");
                }

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;
        }
        ((HarmonicsActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
        rightModeView.setModeList(strList);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });

        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index,harmonicsType);
            }
        });
        rightModeView.setSelection(0);
    }


    /**
     * A , S  ,V 切换
     * @param wir_index
     * @param harmonicsType
     */
    private void updateRightList(int wir_index, int harmonicsType) {
        wir_right_index = 0;
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        switch (wir_index){
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6:// 2½-ELEMENT
                switch (harmonicsType){
                    case 2://A
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Afund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØA(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 5,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+i, groupListObj1, modelLineData, showItem,"");
                        }

                        break;
                    case 1://S
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                        }


                        break;
                    case 0://V
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("L3", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                        }
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (harmonicsType){
                    case 2://A
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3n_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Afund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØA(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 5,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+i, groupListObj1, modelLineData, showItem,"");
                        }

                        break;
                    case 1://S
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                        }


                        break;
                    case 0://V
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3n_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("2L", -1));
                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("L2", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                        }
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (harmonicsType){
                    case 2://A
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("N", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Afund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØA(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Ad"), 8,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 8+i, groupListObj1, modelLineData, showItem,"");
                        }

                        break;
                    case 1://S
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        strList.clear();

                        addMeterData(getSpannableString("THD(%f)"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØW(°)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5,groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                        }


                        break;
                    case 0://V
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("L1", -1));
                        strList.add(new RightListViewItemObj("N", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vfund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØV(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Vd"), 7,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 7+i, groupListObj1, modelLineData, showItem,"");
                        }
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (harmonicsType){
                    case 2://A
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Afund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØA(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 4+i, groupListObj1, modelLineData, showItem,"");
                        }

                        break;
                    case 1://S
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));

                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 30; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                        }


                        break;
                    case 0://U
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));
                        strList.clear();

                        strList.add(new RightListViewItemObj("3L", -1));
                        strList.add(new RightListViewItemObj("L1L2", -1));
                        strList.add(new RightListViewItemObj("L2L3", -1));
                        strList.add(new RightListViewItemObj("L3L1", -1));
                        //              strList.add(new RightListViewItemObj("-_+", -1));

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                        }
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (harmonicsType){
                    case 2://A
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        strList.clear();

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Afund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+i, groupListObj1, modelLineData, showItem,"");
                        }

                        break;
                    case 1://S
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        strList.clear();

                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØW(°)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5,groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 30; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                        }


                        break;
                    case 0://U
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        strList.clear();

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("Ufund",1,5), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("ØU(°)"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5,groupListObj1, modelLineData, showItem,"");
                        for(int i = 1;i<=50;i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+i, groupListObj1, modelLineData, showItem,"");
                        }
                        break;
                }
                break;
         }
        rightModeView.setSelection(0);
        rightModeView.hideUpDownView();
        rightModeView.notifyDataSetChanged();
    }


    /**
     * 实时值
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list,int harmonicsType){
        switch (wir_index){
            case 0://3QWYE
            case 5://3QHIGH LEG
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"");
                                }
                                break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L1");
                                }

                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L2");
                                }
                                break;
                            case 3://L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L3");
                                }

                                break;
                            case 4://N
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, list.getModelLineData().get(7), showItem,"N");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"N");
                                }

                                break;
                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, list.getModelLineData().get(i + 5), showItem,"");
                                }
                                break;
                            case 1://L1
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i + 5), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i + 5), showItem,"L2");
                                }

                                break;
                            case 3://L3
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i + 5), showItem,"L3");
                                }

                                break;
                            case 4://-。+

                                break;

                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, list.getModelLineData().get(i + 7), showItem,"");
                                }
                                break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 7), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 7), showItem,"L2");
                                }
                                break;
                            case 3://L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 7), showItem,"L3");
                                }

                                break;
                            case 4://N

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, list.getModelLineData().get(5), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 7), showItem,"N");
                                }

                                break;

                        }
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"");
                                }
                                break;
                            case 1://L1L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L1");
                                }
                                break;
                            case 2://L2L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L2");
                                }
                                break;
                            case 3://L3L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i + 8), showItem,"L3");
                                }
                                break;
                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 3 + i, groupListObj1, list.getModelLineData().get(i+4), showItem,"");
                                }
                                     break;
                            case 1://L1L2
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L1");
                                }
                                break;
                            case 2://L2L3
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L2");
                                }
                                break;
                            case 3://L3L1
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L3");
                                }
                                break;

                        }
                        break;
                    case 0://U
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, list.getModelLineData().get(i+6), showItem,"");
                                }
                                              break;
                            case 1://L1L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, list.getModelLineData().get(i+6), showItem,"L1");
                                }

                                break;
                            case 2://L2L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, list.getModelLineData().get(i+6), showItem,"L2");
                                }

                                break;
                            case 3://L3L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, list.getModelLineData().get(i+6), showItem,"L3");
                                }

                                break;
                            case 4://-.+


                                break;
                        }
                        break;
                }
                break;
            case 6://2½-ELEMENT
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"");
                                }
                                break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L2");
                                }
                                break;
                            case 3://L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L3");
                                }
                                break;
                            case 4://N

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, list.getModelLineData().get(7), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"N");
                                }

                                break;

                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"");
                                }
                                 break;
                            case 1://L1
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L2");
                                }
                                break;
                            case 3://L3

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L3");
                                }
                                break;
                            case 4://-.+
//                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("DC"), 1, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("PEAK+"), 2, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("PEAK-"), 3, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("CF"), 4, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("THD"), 5, groupListObj1, modelLineData, showItem);
//                                addMeterData(getSpannableString("THD"), 6, groupListObj1, modelLineData, showItem);
                                break;

                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://3L
                               addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"");
                                }
                                 break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L2");
                                }
                                break;
                            case 3://L3
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L3");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L3");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L3");
                                }
                                break;
                            case 4://N
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, list.getModelLineData().get(5), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 5, groupListObj1, list.getModelLineData().get(2), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 5 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"N");
                                }
                                 break;

                        }
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://2L

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"");
                                }
                                break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L2");
                                }
                                break;
                            case 3://N

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, list.getModelLineData().get(7), showItem,"N");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"N");
                                }
                                break;

                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://2L
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"");
                                }
                                  break;
                            case 1://L1
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("W(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L1");
                                }
                            case 2://L2
                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("W(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"L2");
                                }
                                break;
                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://2L
                                 addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4+ i, groupListObj1, list.getModelLineData().get(i+7), showItem,"");
                                }
                                  break;
                            case 1://L1
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L1");
                                }
                                break;
                            case 2://L2
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L2");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L2");
                                }
                                break;
                            case 3://N
                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"N");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"N");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"N");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"N");
                                }
                                break;
                        }
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (harmonicsType) {
                    case 2://A
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem, "");
                        addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(6), showItem, "");
                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 4+ i, groupListObj1, list.getModelLineData().get(i+8), showItem,"");
                        }
                        break;
                    case 1://S
                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                        addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+ i, groupListObj1, list.getModelLineData().get(i+5), showItem,"");
                        }
                        break;
                    case 0://U
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                        addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(5), showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(6), showItem,"");
                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5+ i, groupListObj1, list.getModelLineData().get(i+6), showItem,"");
                        }
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://L1

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, list.getModelLineData().get(8), showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, list.getModelLineData().get(i+8), showItem,"L1");
                                }

                                break;
                            case 1://N

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("DC(%r)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 5, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 6, groupListObj1, list.getModelLineData().get(7), showItem,"N");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, list.getModelLineData().get(8), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 7 + i, groupListObj1, list.getModelLineData().get(8+i), showItem,"N");
                                }
                                break;

                        }
                        break;
                    case 1://S
                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"");
                        addMeterData(getSpannableString("W(°)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, list.getModelLineData().get(i+5), showItem,"");
                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://L1

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, list.getModelLineData().get(5), showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, list.getModelLineData().get(6), showItem,"L1");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7+ i, groupListObj1, list.getModelLineData().get(i+7), showItem,"L1");
                                }
                                break;
                            case 1://N

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, list.getModelLineData().get(0), showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, list.getModelLineData().get(1), showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, list.getModelLineData().get(2), showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 3, groupListObj1, list.getModelLineData().get(3), showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 4, groupListObj1, list.getModelLineData().get(5), showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 5, groupListObj1, list.getModelLineData().get(6), showItem,"N");
                                addMeterData(getSpannableString("Vd"), 6, groupListObj1, list.getModelLineData().get(7), showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 6 + i, groupListObj1, list.getModelLineData().get(i+7), showItem,"N");
                                }
                                break;
                        }
                        break;
                }
                break;
        }
    }


    /**
     * 点击右边菜单切换后的显示处理
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index,int harmonicsType){
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);

        BaseBottomAdapterObj baseBottomAdapterObj = null;

        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://L1
                                refeshHeadColor(2,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L1");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://N
                                refeshHeadColor(2,"N");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("DC(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 6, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 7, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 8 + i, groupListObj1, modelLineData, showItem,"N");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;

                        }
                        break;
                    case 1://S
                        refeshHeadColor(1,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("W(°)"), 3, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"");

                        for (int i = 1; i <= 50; i++) {
                            addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                        }
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://L1
                                refeshHeadColor(2,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7+ i, groupListObj1, modelLineData, showItem,"L1");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://N
                                refeshHeadColor(2,"N");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 5, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Vd"), 6, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 6 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                        }
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL  无右边切换集合

                break;
            case 7://1Q SPLIT PHASE
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://2L
                                refeshHeadColor(4,"2L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(3);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L1");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData,showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://N
                                refeshHeadColor(4,"N");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, modelLineData, showItem,"N");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, modelLineData, showItem,"N");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;

                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://2L
                                refeshHeadColor(3,"2L");
                                showItem = 3;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(2);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(3,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("W(°)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(3,"L2");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("W(°)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://2L
                                refeshHeadColor(4,"2L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(3);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4+ i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Vd"), 7, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L2");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://N
                                refeshHeadColor(4,"N");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("DC(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 5, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 6, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Ad(%r)"), 7, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 7 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                        }
                        break;
                }
                break;
            case 6:// 2½-ELEMENT
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(5,"3L");
                                showItem = 5;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(5,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(5,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(5,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 4://N
                                refeshHeadColor(5,"N");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "CF", "THD%f", "THD%r"});
                                break;

                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(4,"3L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(4,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;

                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(5,"3L");
                                showItem = 5;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(5,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(5,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(5,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 4://N
                                refeshHeadColor(5,"N");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("ØV(°)"), 5, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 5 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "CF", "THD%f", "THD%r"});
                                break;

                        }
                        break;
                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(4,"3L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(3);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1L2
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2L3
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L2L3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3L1
                                refeshHeadColor(4,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L3L1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(4,"3L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(3);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 3 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1L2
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2L3
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L2L3_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3L1
                                refeshHeadColor(4,"L3");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L3L1_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");

                                for (int i = 1; i <= 30; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;

                        }
                        break;
                    case 0://U
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(4,"3L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(3);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1L2
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2L3
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L2L3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3L1
                                refeshHeadColor(4,"L3");
                                showItem = 2;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.L3L1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Ufund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØU(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 6 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 4://-.+


                                break;
                        }
                        break;
                }
                break;
            case 0://3QWYE
            case 5://3QHIGH LEG
                switch (harmonicsType) {
                    case 2://A
                        switch (wir_right_index) {
                            case 0://4V
                                refeshHeadColor(5,"3L");
                                showItem = 5;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(5,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(5,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(5,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØA(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 7, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 8, groupListObj1, modelLineData, showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 8 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 4://N
                                refeshHeadColor(5,"N");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Afund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "CF", "THD%f", "THD%r"});
                                break;

                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(4,"3L");
                                showItem = 4;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 2 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(4,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(4,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(4,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("THD(%f)"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("K-factor"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØW(°)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");

                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 5 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;


                        }
                        break;
                    case 0://V
                        switch (wir_right_index) {
                            case 0://3L
                                refeshHeadColor(5,"3L");
                                showItem = 5;
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(4);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 4 + i, groupListObj1, modelLineData, showItem,"");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS(V≃)", "DC(V=)", "PEAK+(V=)", "PEAK-(V=)", "MAX(V≃)", "MIN(V≃)", "CF", "THD(%f)", "THD(%r)", "PST", "PLT"});
                                break;
                            case 1://L1
                                refeshHeadColor(5,"L1");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L1");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L1");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L1");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 2://L2
                                refeshHeadColor(5,"L2");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L2");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L2");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L2");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 3://L3
                                refeshHeadColor(5,"L3");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("ØV(°)"), 2, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("THD(%f)"), 3, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("DC(%f)"), 4, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MAX(%f)"), 5, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("MIN(%f)"), 6, groupListObj1, modelLineData, showItem,"L3");
                                addMeterData(getSpannableString("Ad"), 7, groupListObj1, modelLineData, showItem,"L3");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%f)"), 7 + i, groupListObj1, modelLineData, showItem,"L3");
                                }

                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS(V≃)", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "MAX", "MIN", "CF", "THD(%f)", "THD(%r)", "PST", "PLT", "FHL", "FK"});
                                break;
                            case 4://N
                                refeshHeadColor(5,"N");
                                showItem = 2;
                                Group_list_middleText.setText("");
                                groupListObj1.Clear();
                                stickyLayout.setShowDividerCount(1);
                                groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                                addMeterData(getSpannableString("RMS"), 0, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("Vfund", 1, 5), 1, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("THD(%r)"), 2, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MAX(%r)"), 3, groupListObj1, modelLineData, showItem,"N");
                                addMeterData(getSpannableString("MIN(%r)"), 4, groupListObj1, modelLineData, showItem,"N");
                                for (int i = 1; i <= 50; i++) {
                                    addMeterData(getSpannableString("H" + i + "(%r)"), 4 + i, groupListObj1, modelLineData, showItem,"N");
                                }
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "RMS", new String[]{"RMS", "DC", "PEAK+", "PEAK-", "CF", "THD%f", "THD%r"});
                                break;

                        }
                        break;
                }
                break;
        }
        ((HarmonicsActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
        stickyLayout.notifyChildChanged();
    }

    public void setRightIndex(int right) {
        rightModeView.setSelection(right);
        rightModeView.invalidate();
    }
}
