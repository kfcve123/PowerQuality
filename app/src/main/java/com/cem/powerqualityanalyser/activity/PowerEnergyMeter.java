package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.MeterPowerKWTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.DataModel;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * ENERGY 电能
 */
public class PowerEnergyMeter extends BaseFragmentTrend {


    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1,groupListObj2;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private boolean changeRightIndex;

    private TextView tv_hz;
    private String configV;
    private String configHz;
    private CustomTimer customTimer;// 倒计时计时器
    private ImageView iv_time;

    @Override
    public void onInitViews() {

        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();

        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_leftText = (TextView) findViewById(R.id.Group_list_leftText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);
        iv_time = (ImageView) findViewById(R.id.iv_time);
        iv_time.setVisibility(View.VISIBLE);

        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);

        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        stickyLayout.setListFocusAble(false);
        rightModeView.getViewFoucs();

        groupListObj1=new MeterGroupListObj();
        groupListObj2 = new MeterGroupListObj();
        rightModeView.setUpDownClick(false);

        String[] showItem2=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(configV + "  " + configHz + "  " +  showItem2[wir_index]);
        Group_list_middleText.setText(R.string.energy);
        setTimeText(DataFormatUtil.getTime(0));
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});
        rightModeView.hideUpDownView();
        switch (wir_index) {
            case 0://3QWYE
            case 2://3QIT
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


                addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                break;
            case 1://3QOPEN LEG
            case 3://2-ELEMENT
            case 4://3QDELTA
            case 5://3QHIGH LEG
            case 8://1Q IT NO NEUTRAL
                rightModeView.setVisibility(View.INVISIBLE);
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));
                strList.clear();

                addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                break;

            case 7://1Q SPLIT PHASE
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("∑", -1));

                addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                break;

            case 9://1Q +NEUTRAL
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));
                strList.clear();
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("∑", -1));

                addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                break;

        }

 //       ((PowerEnergyActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
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

        customTimer = new CustomTimer();
        customTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
                final int time= (int) l;
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0) {
                            setTimeText(DataFormatUtil.getTime(time));
                            setTimeView(true);
                        }else {
                            setTimeText(DataFormatUtil.getTime(0));
                            setTimeView(false);
                        }
                    }
                });

            }
        });
 //       startRecord();
    }
    public void setPause(boolean pause){
        customTimer.setPause(pause);
    }

    private long starTime;
    public long getStartTime(){
        return starTime;
    }
    private boolean startRecord;

    public void setStartRecord(boolean startRecord){
        this.startRecord = startRecord;
    }

    public void startRecord(){
        if(customTimer!=null) {
            starTime = System.currentTimeMillis();
            customTimer.StartCustomTimer();
            MeterPowerKWTool.resetEnergy();
        }
    }

    public void setTimeText(String time) {
        if (Group_list_leftText != null)
            Group_list_leftText.setText(time);
    }

    public void setTimeView(boolean show){
        if(iv_time!=null)
            iv_time.setVisibility(show?View.VISIBLE:View.INVISIBLE);
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
            case 2://3QIT
            case 6://2½-ELEMENT
                switch (wir_right_index){
                    case 0://3L
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1, MeterPowerKWTool.getMeterData(0,list), showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"");
                        break;
                    case 1://L1
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"L1");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"L1");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"L1");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"L1");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"L1");
                        break;
                    case 2://L2
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"L2");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"L2");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"L2");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"L2");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"L2");
                        break;
                    case 3://L3
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"L3");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"L3");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"L3");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"L3");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"L3");
                        break;
                    case 4://求和
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1, MeterPowerKWTool.getMeterData(0,list), showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"");
                        break;
                }
                break;
            case 1://3QOPEN LEG
            case 3://2-ELEMENT
            case 4://3QDELTA
            case 5://3QHIGH LEG
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){
                    case 0://Total
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"");
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://L1
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"L1");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"L1");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"L1");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"L1");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"L1");
                        break;

                    case 1://L2
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,MeterPowerKWTool.getMeterData(0,list), showItem,"L2");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,MeterPowerKWTool.getMeterData(1,list), showItem,"L2");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, MeterPowerKWTool.getMeterData(2,list), showItem,"L2");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, MeterPowerKWTool.getMeterData(3,list), showItem,"L2");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, MeterPowerKWTool.getMeterData(4,list), showItem,"L2");
                        break;
                    case 2://求和
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (wir_right_index){
                    case 0://L1
                    case 1://求和
                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
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
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://L1
                        refeshHeadColor(2,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;
                    case 1://∑
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://L1
                        refeshHeadColor(3,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;
                    case 1://L2
                        refeshHeadColor(3,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;
                    case 2://∑
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});


                        break;

                }
                break;
            case 6:// 2½-ELEMENT
            case 2://3QIT
            case 0://3QWYE
                switch (wir_right_index) {//切换右边选项
                    case 0://3L
                        refeshHeadColor(5,"3L");
                        showItem = 4;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;
                    case 1://L1
                        refeshHeadColor(5,"L1");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;
                    case 2://L2
                        refeshHeadColor(5,"L2");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l2_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});

                        break;

                    case 3://L3
                        refeshHeadColor(5,"L3");
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l3_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});
                        break;
                    case 4://∑
                        showItem = 2;
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.total_array));

                        addMeterData(getSpannableString("kWh"), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("kVAh"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kvarh"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kwh forw"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("kWh rev"), 4,groupListObj1, modelLineData, showItem,"");
                        baseBottomAdapterObj = new BaseBottomAdapterObj(3,"kW",new String[]{"kWh","kVAh","kvarh","kwh forw","kWh rev"});
                        break;

                }
                break;
            case 8://1Q IT NO NEUTRAL  没有右边侧边栏，只有Total一栏
            case 5://2-ELEMENT
            case 4://3QDELTA
            case 3://3QHIGH LEG
            case 1://3QOPEN LEG
                break;
        }
//        ((PowerEnergyActivity)getActivity()).updateBottomData(baseBottomAdapterObj,3);
        stickyLayout.notifyChildChanged();
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }
}
