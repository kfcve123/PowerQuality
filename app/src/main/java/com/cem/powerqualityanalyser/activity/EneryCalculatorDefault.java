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
 * 能量损耗计算器 默认启动界面
 */
public class EneryCalculatorDefault extends BaseFragmentTrend {

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
        rightModeView.setVisibility(View.INVISIBLE);
        tv_hz = (TextView) findViewById(R.id.tv_hz);
 //       tv_hz.setText(list.getHzData() == null ? "50.00Hz" : list.getHzData() + "Hz");
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        groupListObj1=new MeterGroupListObj();
        groupListObj2 = new MeterGroupListObj();

        String[] showItems=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(16f);
        Group_list_rightText.setText(showItems[wir_index]  + "  " +  configV + "  " + configHz);
        Group_list_middleText.setText(R.string.enery_consumption_calculator);
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

            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 3://2-ELEMENT
            case 4://3ØDELTA
            case 9://1Ø +NEUTRAL
            case 8://1Ø IT NO NEUTRAL
            case 7://1Ø SPLIT PHASE

                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.energy_loss_array));
                strList.clear();

                addMeterData(getSpannableString("Effective"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Reactive"), 1, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Unbalance"), 2, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Distortion"), 3, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Neutral"), 4, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("Total"), 5, groupListObj1,modelLineData, showItem,"");
                break;

        }

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
        stickyLayout.setListFocusAble(false);
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
//                        if (list.getModelLineData().size() > 0) {
//                            tv_hz.setText(list.getHzData() == null ? "50.00Hz" : list.getHzData() + "Hz");
//                        }
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
            case 5://3QHIGH LEG  groupListObj2 L1L2 L2L3 L3L1 N
            case 6://2½-ELEMENT
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
            case 9://1Ø +NEUTRAL
            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://3V
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"N");
                        break;
                    case 1://3A
                        addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj1,list.getModelLineData().get(1), showItem,"N");
                        break;
                    case 2://L1
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L1");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1,list.getModelLineData().get(1), showItem,"L1");
                        break;
                    case 3://L2
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"L2");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1,list.getModelLineData().get(1), showItem,"L2");
                        break;
                    case 4://N
                        addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"N");
                        addMeterData(getSpannableString("Arms 1/2"), 1, groupListObj1,list.getModelLineData().get(1), showItem,"N");
                        break;
                }
                break;
            case 8://1Ø IT NO NEUTRAL   // groupListObj2  L1L2
                addMeterData(getSpannableString("Vrms 1/2"), 0, groupListObj1,list.getModelLineData().get(0), showItem,"N");
                addMeterData(getSpannableString("Arms 1/2"), 0, groupListObj2,list.getModelLineData().get(1), showItem2,"N");
                break;
        }
    }


}
