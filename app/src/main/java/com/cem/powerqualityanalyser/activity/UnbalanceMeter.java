package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Power
 */
public class UnbalanceMeter extends BaseFragmentTrend {


    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1,groupListObj2;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
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
        wir_index = config.getSet_Wir();

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
        rightModeView.hideUpDownView();
        String[] showItems=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(configV + "  " + configHz + "  " +  showItems[wir_index]);
        Group_list_middleText.setText(R.string.allmeter_unbalance);
        Group_list_leftText.setText("");
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);

        switch (wir_index) {
            case 0://3QWYE
            case 6://2½-ELEMENT
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                strList.clear();
                showItem2 = 5;
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");



                break;
            case 5://3QHIGH LEG
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                strList.clear();
                showItem2 = 5;
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                strList.add(new RightListViewItemObj("4V", -1));
                strList.add(new RightListViewItemObj("4A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
                addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");
                break;

            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA

                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                strList.clear();
                showItem2 = 4;
                groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));
                strList.add(new RightListViewItemObj("L2L3", -1));
                strList.add(new RightListViewItemObj("L3L1", -1));

                addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                addMeterData(getSpannableString("ØV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");

                break;
            case 8://1Q IT NO NEUTRAL
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                strList.clear();

                strList.add(new RightListViewItemObj("U", -1));
                strList.add(new RightListViewItemObj("A", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));

                addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");

                break;
            case 7://1Q SPLIT PHASE
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

                addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");

                break;
            case 9://1Q +NEUTRAL

                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();

                strList.add(new RightListViewItemObj("V", -1));
                strList.add(new RightListViewItemObj("A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("ØV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");
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
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_unbalance_meter_layout;
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {
        List<ModelLineData> modelLineData = list.getModelLineData();
        if(modelLineData!=null) {
            addSelectMeterData(wir_index,wir_right_index,list);
            stickyLayout.post(new Runnable() {
                @Override
                public void run() {
                    if(list!=null) {
                        if (list.getModelLineData().size() > 0) {
                            tv_hz.setText(list.getHzData() == null ? "----Hz" : list.getHzData() + "Hz");
                        }
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

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }


    /**
     * 实时值
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list){
        switch (wir_index){
            case 0://3QWYE
            case 6://2½-ELEMENT
                switch (wir_right_index){
                    case 0://3V
                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2,list.getModelLineData().get(2), showItem2,"");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, list.getModelLineData().get(7), showItem2,"");

                        break;
                    case 1://3A
                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,list.getModelLineData().get(3), showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, list.getModelLineData().get(4), showItem2,"");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj2, list.getModelLineData().get(8), showItem2,"");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L1");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, list.getModelLineData().get(2), showItem2,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L1");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L1");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L2");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"L2");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, list.getModelLineData().get(2), showItem2,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L2");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L2");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L2");
                        break;
                    case 4://L3
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L3");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"L3");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, list.getModelLineData().get(2), showItem2,"L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L3");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L3");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L3");
                        break;
                    case 5://N
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"N");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"N");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, list.getModelLineData().get(2), showItem2,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"N");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"N");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"N");
                        break;

                }
                break;
            case 2://3QIT
            case 1://3QOPEN LEG
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (wir_right_index){
                    case 0://3U
                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        if(list.getModelLineData().size()>5)
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(5), showItem2,"");
                        else
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"");
                        if(list.getModelLineData().size()>6)
                            addMeterData(getSpannableString("φV(°)"), 1,groupListObj2,list.getModelLineData().get(6), showItem2,"");
                        else
                            addMeterData(getSpannableString("φV(°)"), 1,groupListObj2,list.getModelLineData().get(2), showItem2,"");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, list.getModelLineData().get(7), showItem2,"");
                        break;
                    case 1://3A
                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,list.getModelLineData().get(3), showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, list.getModelLineData().get(4), showItem2,"");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj2, list.getModelLineData().get(8), showItem2,"");
                        break;
                    case 2://L1L2
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L1");
                        if (list.getModelLineData().size() > 5)
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(5), showItem2, "L1");
                        else
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L1");

                        if (list.getModelLineData().size() > 6)
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(6), showItem2, "L1");
                        else
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(2), showItem2, "L1");


                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L1");
                        if(list.getModelLineData().size()>7)
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L1");
                        if(list.getModelLineData().size()>8)
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L1");
                        break;
                    case 3://L2L3
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L2");
                        if (list.getModelLineData().size() > 5)
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(5), showItem2, "L2");
                        else
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L2");

                        if (list.getModelLineData().size() > 6)
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(6), showItem2, "L2");
                        else
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(2), showItem2, "L2");

                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L2");
                        if(list.getModelLineData().size()>7)
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L2");
                        if(list.getModelLineData().size()>8)
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L2");
                        break;
                    case 4://L3L1
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L3");
                        if (list.getModelLineData().size() > 5)
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(5), showItem2, "L3");
                        else
                            addMeterData(getSpannableString("V fund"), 0, groupListObj2, list.getModelLineData().get(1), showItem2, "L3");

                        if (list.getModelLineData().size() > 6)
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(6), showItem2, "L3");
                        else
                            addMeterData(getSpannableString("φV(°)"), 1, groupListObj2, list.getModelLineData().get(2), showItem2, "L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,list.getModelLineData().get(3), showItem2,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, list.getModelLineData().get(4), showItem2,"L3");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L3");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L3");
                        break;
                }
                break;
            case 5://3QHIGH LEG
                switch (wir_right_index){
                    case 0://4V
                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2,list.getModelLineData().get(2), showItem2,"");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, list.getModelLineData().get(7), showItem2,"");
                        break;
                    case 1://4A
                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,list.getModelLineData().get(3), showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, list.getModelLineData().get(4), showItem2,"");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj2, list.getModelLineData().get(8), showItem2,"");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L1");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L2");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L2");
                        break;
                    case 4://L3
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L3");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L3");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"L3");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"L3");
                        break;
                    case 5://N
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"N");
                        if(list.getModelLineData().size()>7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, list.getModelLineData().get(7), showItem2,"N");
                        if(list.getModelLineData().size()>8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, list.getModelLineData().get(8), showItem2,"N");
                        break;

                }
                break;


            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){
                    case 0://U
                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1,list.getModelLineData().get(2), showItem,"");
                        if(list.getModelLineData().size()>7)
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj1, list.getModelLineData().get(7), showItem,"");
                        break;
                    case 1://A
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(3), showItem,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(4), showItem,"");
                        if(list.getModelLineData().size()>8)
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2, groupListObj1,list.getModelLineData().get(8), showItem,"");
                        break;
                    case 2://L1L2
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)", 3, 7), 4, groupListObj1, list.getModelLineData().get(7), showItem, "L1");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,list.getModelLineData().get(8), showItem,"L1");
                        break;
                }
                break;

            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://3V
                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1,list.getModelLineData().get(2), showItem,"");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj1, list.getModelLineData().get(7), showItem,"");
                        break;
                    case 1://3A
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(3), showItem,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(4), showItem,"");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 2, groupListObj1,list.getModelLineData().get(8), showItem,"");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L1");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, list.getModelLineData().get(7), showItem,"L1");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,list.getModelLineData().get(8), showItem,"L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"L2");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, list.getModelLineData().get(7), showItem,"L2");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,list.getModelLineData().get(8), showItem,"L2");
                        break;
                    case 4://N
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, list.getModelLineData().get(2), showItem,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,list.getModelLineData().get(3), showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, list.getModelLineData().get(4), showItem,"N");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, list.getModelLineData().get(7), showItem,"N");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,list.getModelLineData().get(8), showItem,"N");
                        break;
                }

                break;
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {
                    case 0://V
                        addMeterData(getSpannableString("V fund"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "");
                        addMeterData(getSpannableString("φV(°)"), 1, groupListObj1, list.getModelLineData().get(2), showItem, "");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)", 3, 7), 2, groupListObj1, list.getModelLineData().get(7), showItem, "");
                        break;
                    case 1://A
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1, list.getModelLineData().get(3), showItem, "");
                        addMeterData(getSpannableString("φA(°)"), 1, groupListObj1, list.getModelLineData().get(4), showItem, "");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)", 3, 7), 2, groupListObj1, list.getModelLineData().get(8), showItem, "");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "L1");
                        addMeterData(getSpannableString("φA(°)"), 1, groupListObj1, list.getModelLineData().get(2), showItem, "L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1, list.getModelLineData().get(3), showItem, "L1");
                        addMeterData(getSpannableString("φA(°)"), 3, groupListObj1, list.getModelLineData().get(4), showItem, "L1");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)", 3, 7), 3, groupListObj1, list.getModelLineData().get(7), showItem, "L1");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)", 3, 7), 4, groupListObj1, list.getModelLineData().get(8), showItem, "L1");
                        break;
                    case 3://N
                        addMeterData(getSpannableString("A fund"), 0, groupListObj1, list.getModelLineData().get(1), showItem, "N");
                        addMeterData(getSpannableString("φA(°)"), 1, groupListObj1, list.getModelLineData().get(2), showItem, "N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1, list.getModelLineData().get(3), showItem, "N");
                        addMeterData(getSpannableString("φA(°)"), 3, groupListObj1, list.getModelLineData().get(4), showItem, "N");
                        if (list.getModelLineData().size() > 7)
                            addMeterData(getSpannableString("RMS(V≃)", 3, 7), 3, groupListObj1, list.getModelLineData().get(7), showItem, "N");
                        if (list.getModelLineData().size() > 8)
                            addMeterData(getSpannableString("RMS(A≃)", 3, 7), 4, groupListObj1, list.getModelLineData().get(8), showItem, "N");
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
        switch (wir_index) {
            case 0://3QWYE
            case 6:// 2½-ELEMENT
                switch (wir_right_index) {//切换右边选项
                    case 0://4V
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                        groupListObj2.Clear();
                        showItem2 = 5;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");

                        break;
                    case 1://3A
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj2.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_aarray));
                        showItem2 = 5;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");
                        break;

                    case 2://L1
                        refeshHeadColor(5,"L1");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, modelLineData, showItem2,"L1");
                        break;
                    case 3://L2
                        refeshHeadColor(5,"L2");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, modelLineData, showItem2,"L2");
                        break;
                    case 4://L3
                        refeshHeadColor(5,"L3");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l3_array));
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, modelLineData, showItem2,"L3");
                        break;
                    case 5://N
                        refeshHeadColor(5,"N");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.n_array));
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"N");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"N");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj2, modelLineData, showItem2,"N");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj2, modelLineData, showItem2,"N");


                        break;
                }
                break;
            case 5://3QHIGH LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://4V
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                        groupListObj2.Clear();
                        showItem2 = 5;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");
                        break;
                    case 1://4A
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj2.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_aarray));
                        showItem2 = 5;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj2, modelLineData, showItem2,"");
                        break;

                    case 2://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"L1");
                        break;
                    case 3://L2
                        refeshHeadColor(5,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"L2");
                        break;
                    case 4://L3
                        refeshHeadColor(5,"L3");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"L3");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"L3");
                        break;
                    case 5://N
                        refeshHeadColor(5,"N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"N");
                        break;

                }
                break;
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://V
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2,groupListObj1, modelLineData, showItem,"");
                        break;
                    case 1://A
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2,groupListObj1, modelLineData, showItem,"");
                        break;

                    case 2://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj1, modelLineData, showItem,"L1");
                        break;
                    case 3://N
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5,groupListObj1, modelLineData, showItem,"N");
                        break;


                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://V
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");
                        break;
                    case 1://A
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");
                        break;

                    case 2://L1L2
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,modelLineData, showItem,"L1");
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        refeshHeadColor(4,"2L");
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");
                        break;
                    case 1://3A
                        refeshHeadColor(4,"2L");
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("A fund"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2, groupListObj1,modelLineData, showItem,"");

                        break;

                    case 2://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj1,modelLineData, showItem,"L1");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,modelLineData, showItem,"L1");
                        break;

                    case 3://L2
                        refeshHeadColor(5,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj1,modelLineData, showItem,"L2");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,modelLineData, showItem,"L2");
                        break;

                    case 4://N
                        refeshHeadColor(5,"N");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.n_array));
                        groupListObj2.Clear();

                        addMeterData(getSpannableString("V fund"), 0, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj1, modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj1,modelLineData, showItem,"N");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj1,modelLineData, showItem,"N");
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (wir_right_index) {//切换右边选项
                    case 0://3U
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_varray));
                        groupListObj2.Clear();
                        showItem2 = 4;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                        addMeterData(getSpannableString("Vunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 2, groupListObj2,modelLineData, showItem2,"");

                        break;
                    case 1://3A
                        refeshHeadColor(5,"3L");
                        showItem = 3;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj2.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_aarray));
                        showItem2 = 4;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                        addMeterData(getSpannableString("Aunbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("A fund"), 0, groupListObj2,modelLineData, showItem2,"");
                        addMeterData(getSpannableString("φA(°)"), 1,groupListObj2, modelLineData, showItem2,"");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 2, groupListObj2,modelLineData, showItem2,"");
                        break;

                    case 2://L1L2
                        refeshHeadColor(5,"L1");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.L1L2_array));

                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj2,modelLineData, showItem2,"L1");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj2,modelLineData, showItem2,"L1");
                        break;
                    case 3://L2L3
                        refeshHeadColor(5,"L2");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.L2L3_array));

                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj2,modelLineData, showItem2,"L2");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj2,modelLineData, showItem2,"L2");
                        break;
                    case 4://L3L1
                        refeshHeadColor(5,"L3");
                        showItem = 5;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.unbalance_array));
                        groupListObj2.Clear();
                        showItem2 = 2;
                        groupListObj2.addHeader(getResources().getStringArray(R.array.L3L1_array));
                        addMeterData(getSpannableString("unbal(%)"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("V fund"), 0, groupListObj2,modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("φV(°)"), 1,groupListObj2, modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("A fund"), 2, groupListObj2,modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("φA(°)"), 3,groupListObj2, modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 4, groupListObj2,modelLineData, showItem2,"L3");
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 5, groupListObj2,modelLineData, showItem2,"L3");

                        break;
                }
                break;
        }
        stickyLayout.notifyChildChanged();
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }
}
