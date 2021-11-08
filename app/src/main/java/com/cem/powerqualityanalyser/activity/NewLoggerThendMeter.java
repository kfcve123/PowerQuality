package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
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
public class NewLoggerThendMeter extends BaseFragmentTrend {

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1;
    private TextView Group_list_middleText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private int showItem = 5;

    private String configV;
    private String configHz;

    private List<String> parameterNames;//被勾选的
    private CustomTimer customTimer;// 倒计时计时器
    private TextView tv_time;
    private boolean startRecord;

    public void setTableListData(List<String> parameterNames) {
         this.parameterNames = parameterNames;
    }

    @Override
    public void onInitViews() {

        parameterNames = new ArrayList<>();
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();

        tv_time = (TextView) findViewById(R.id.tv_time);
        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);

        Group_list_middleText.setText(R.string.trendchartrecord);
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        groupListObj1=new MeterGroupListObj();

        String[] showItems=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(showItems[wir_index]  + "  " +  configV + "  " + configHz);

        Group_list_rightview.setVisibility(View.INVISIBLE);
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);

        refeshHeadColor(5,"3L");
        groupListObj1.Clear();
        stickyLayout.setShowDividerCount(4);
        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3n_array));

        for(int i = 0;i<parameterNames.size();i++){
            addMeterData(getSpannableString(parameterNames.get(i)), i, groupListObj1,modelLineData, showItem,"");
        }

        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });

        customTimer = new CustomTimer();
        customTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
                final int time= (int) l;
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0)
                            tv_time.setText(DataFormatUtil.getTime(time));
                        else {
                            tv_time.setText("");
                        }
                    }
                });

            }
        });

    }

    private void startRecord(){
        if(!startRecord) {
            startRecord = true;
            customTimer.StartCustomTimer();
        }
    }


    public void clearTableListData(){
        if(parameterNames!=null)
            parameterNames.clear();
        else
            parameterNames = new ArrayList<>();
        if (stickyLayout != null)
            stickyLayout.clearData();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_newlogger_meter_layout;
    }


    @Override
    public void setShowMeterData(final ModelAllData list) {

    }

    public void setShowMeterData(List<ModelLineData> modelLineData){
        //        log.e("--------" + modelLineData.size());
        if(modelLineData!=null) {
            startRecord();
            addSelectMeterData(modelLineData);
            if(stickyLayout!=null) {
                stickyLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (stickyLayout.showItemsCount() < 1) {
                            stickyLayout.addItem(groupListObj1);
                        }
                        stickyLayout.notifyChildChanged();
                    }
                });
            }
        }
    }

    @Override
    public void setShowMeterData(ModelAllData list, int funTypeIndex) {

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
    public void addSelectMeterData(List<ModelLineData> modelLineData ){
        for(int i = 0;i<modelLineData.size();i++){
            addMeterData(getSpannableString(modelLineData.get(i).getLineName()), i, groupListObj1,modelLineData.get(i), showItem,"");
        }
    }


}
