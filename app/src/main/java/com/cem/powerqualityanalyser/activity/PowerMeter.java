package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
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
 * POWER  功率
 */
public class PowerMeter extends BaseFragmentTrend {

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1,groupListObj2;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private int showItem2 =3;
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
        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);


        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
 //       stickyLayout.setListToucheAble(false);
        stickyLayout.setListFocusAble(false);
        rightModeView.getViewFoucs();

        groupListObj1=new MeterGroupListObj();
        groupListObj2 = new MeterGroupListObj();
        rightModeView.setUpDownClick(false);

        String[] showItems=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(configV + "  " + configHz + "  " +  showItems[wir_index]);
        Group_list_middleText.setText(R.string.allmeter_power);
        Group_list_leftText.setText("");
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3ØWYE
                rightModeView.hideUpDownView();
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
                strList.add(new RightListViewItemObj("∑", -1));


                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;
            case 1://3ØOPEN LEG
            case 3://2-ELEMENT
            case 4://3ØDELTA
                rightModeView.setVisibility(View.INVISIBLE);
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                strList.clear();
                showItem2 = 4;
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"");
                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;
            case 5://3ØHIGH LEG

                rightModeView.setVisibility(View.INVISIBLE);
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                strList.clear();
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1n_array));
                showItem2 = 5;
                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"");
                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});


                break;
            case 8://1Ø IT NO NEUTRAL
                rightModeView.setVisibility(View.INVISIBLE);
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                strList.clear();
                groupListObj2.Clear();
                groupListObj2.addHeader(getResources().getStringArray(R.array.L1L2_array));
                showItem2 = 2;
                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"");
                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});
                break;

            case 2://3ØIT
            case 6://2½-ELEMENT
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));
                strList.clear();

                strList.add(new RightListViewItemObj("3L", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("∑", -1));

                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;

            case 7://1Ø SPLIT PHASE
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("∑", -1));


                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;

            case 9://1Ø +NEUTRAL
//                rightModeView.hideUpDownView();
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));
                strList.add(new RightListViewItemObj("∑", -1));

                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;

        }

//        ((PowerEnergyActivity)getActivity()).updateBottomData(baseBottomAdapterObj,3);
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
                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index);
            }
        });

        rightModeView.setSelection(0);
//        rightModeView.hideUpDownView();
//        rightModeView.notifyDataSetChanged();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_power_energy_trend_layout;
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {
        List<ModelLineData> modelLineData = list.getModelLineData();
        if(modelLineData!=null) {
            addSelectMeterData(wir_index,wir_right_index,list);
            stickyLayout.post(new Runnable() {
                @Override
                public void run() {
                   if (stickyLayout.showItemsCount() < 1) {
                        stickyLayout.addItem(groupListObj1);
                        if(groupListObj2.getHeaderSize()>0){
                            stickyLayout.addItem(groupListObj2);
                        }
                    }
                    stickyLayout.notifyChildChanged();

                }
            });
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
                switch (wir_right_index){
                    case 0://3L
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"");
                        break;
                    case 1://L1
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L1");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L1");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L1");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L1");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L1");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L1");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L1");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L1");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L1");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"L1");
                        break;
                    case 2://L2
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L2");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L2");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L2");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L2");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L2");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L2");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L2");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L2");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L2");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"L2");
                        break;
                    case 3://L3
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L3");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L3");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L3");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L3");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L3");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L3");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L3");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L3");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L3");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"L3");
                        break;
                    case 4://N
                        addMeterData(getSpannableString("cosØ"), 0,groupListObj1, list.getCosLineData(), showItem,"N");
                        break;
                    case 5://∑
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L1",true);
                        break;

                }
                break;
            case 1://3QOPEN LEG        groupListObj2  L1L2 L2L3 L3L1
            case 3://2-ELEMENT
            case 4://3QDELTA
            case 5://3QHIGH LEG  groupListObj2 L1L2 L2L3 L3L1 N
            case 8://1Ø IT NO NEUTRAL   // groupListObj2  L1L2

                addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, list.getkVALineData(), showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"");
                addMeterData(getSpannableString("cosØ"), 0,groupListObj2, list.getCosLineData(), showItem2,"");

                break;
            case 2://3QIT
            case 6://2½-ELEMENT
                switch (wir_right_index){
                    case 0://3L
                    case 1://L1
                    case 2://L2
                    case 3://L3
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"");
                        break;
                    case 4://∑
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L1",true);
                        break;

                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://L1
                    case 1://L2
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"");
                        break;
                    case 2://∑
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L1",true);
                        break;

                }
                break;

            case 9://1Ø +NEUTRAL
                switch (wir_right_index){
                    case 0://L1
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"");
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"");
                        addMeterData(getSpannableString("cosØ"), 9,groupListObj1, list.getCosLineData(), showItem,"");
                        break;
                    case 1://N
                        addMeterData(getSpannableString("cosØ"), 0,groupListObj1, list.getCosLineData(), showItem,"");
                        break;
                    case 2://∑
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,list.getkWLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVA"), 1, groupListObj1,list.getkVALineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, list.getKvarLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, list.getkVAHarmLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, list.getkVAunbLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, list.getkWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, list.getkVAfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, list.getWfundLineData(), showItem,"L1",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, list.getPFLineData(), showItem,"L1",true);
                        break;
                }
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
            case 9://1Ø +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://L1
                        refeshHeadColor(3,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L1");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 1://N
                        refeshHeadColor(3,"N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));
                        addMeterData(getSpannableString("cosф"), 0,groupListObj1, modelLineData, showItem,"N");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"cosф",new String[]{"cosф"});
                        break;

                    case 2://∑
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF"});
                        break;
                }
                break;
            case 7://1Ø SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://L1
                        refeshHeadColor(3,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L1");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 1://L2
                        refeshHeadColor(3,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L2");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 2://∑
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF"});
                        break;

                }
                break;
            case 8://1Ø IT NO NEUTRAL  没有右边侧边栏
                refeshHeadColor(1,"L1");
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                groupListObj2.addHeader(getResources().getStringArray(R.array.L1L2_array));
                showItem2 = 2;
                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L1");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"L1");
                baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                break;
            case 5://3QHIGH LEG   这三个 显示需要调整？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
                refeshHeadColor(1,"L1");
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1n_array));
                showItem2 = 5;
                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"");
                break;
            case 4://3QDELTA        这三个 显示需要调整？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
            case 3://2-ELEMENT
            case 1://3QOPEN LEG
                refeshHeadColor(1,"L1");
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                groupListObj2.Clear();
                showItem2 = 4;
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");

                addMeterData(getSpannableString("cosф"), 0,groupListObj2, modelLineData, showItem2,"");

                break;
            case 6:// 2½-ELEMENT
            case 2://3ØIT
                switch (wir_right_index) {//切换右边选项
                    case 0://3L
                        refeshHeadColor(5,"3L");
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 1://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L1");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 2://L2
                        refeshHeadColor(5,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L2");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;

                    case 3://L3
                        refeshHeadColor(5,"L3");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L3");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});
                        break;
                    case 4://∑
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"",true);

                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF"});
                        break;

                }
                break;
            case 0://3QWYE
                switch (wir_right_index){
                    case 0://3L
                        refeshHeadColor(5,"3L");
                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_power);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});
                        break;
                    case 1://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        Group_list_middleText.setText(R.string.allmeter_power);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L1");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 2://L2
                        refeshHeadColor(5,"L2");
                        showItem = 2;
                        Group_list_middleText.setText(R.string.allmeter_power);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L2");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});

                        break;
                    case 3://L3
                        refeshHeadColor(5,"L3");
                        showItem = 2;
                        Group_list_middleText.setText(R.string.allmeter_power);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));
                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("cosф"), 9,groupListObj1, modelLineData, showItem,"L3");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF","cosф"});
                        break;
                    case 4://N
                        refeshHeadColor(5,"N");
                        showItem = 2;
                        Group_list_middleText.setText(R.string.allmeter_power);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));

                        addMeterData(getSpannableString("cosф"), 1,groupListObj1, modelLineData, showItem,"N");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"cosф",new String[]{"cosф"});
                        break;
                    case 5://∑
                        log.e("-----------3WEY---∑---------");
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        Group_list_middleText.setText("");
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kW"), 0, groupListObj1,modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVA"), 1,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kvar"), 2,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAharm",3,7), 3,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAunb",3,6), 4,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kWfund",2,6), 5,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("kVAfund",3,7), 6,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("Wfund",1,5), 7,groupListObj1, modelLineData, showItem,"",true);
                        addMeterData(getSpannableString("PF"), 8,groupListObj1, modelLineData, showItem,"",true);

                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kW","kVA","kvar","kVAharm","kVAunb","kWfund","kVAfund","Wfund","PF"});
                        break;

                }
                break;
        }
        //       ((PowerEnergyActivity)getActivity()).updateBottomData(baseBottomAdapterObj,3);
        stickyLayout.notifyChildChanged();
    }


}
