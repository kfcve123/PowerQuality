package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.command.RecordCommand;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.databean.EventsBean;

import com.cem.powerqualityanalyser.fragment.LoggerTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.libs.MeterAllParameter;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.MeterHz_400;
import com.cem.powerqualityanalyser.libs.MeterSudden_UP_Down;
import com.cem.powerqualityanalyser.libs.MeterThreeHarmonic;
import com.cem.powerqualityanalyser.libs.MeterThreeUnbalanced;
import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.sqlBean.SqlApi;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlMeterData;
import com.cem.powerqualityanalyser.tool.SqlDataTool;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.view.HintDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import serialport.amos.cem.com.libamosserial.ModelAllData;

public class LoggerThendActivity extends BaseActivity{
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private DipsSwellsTrend eventFragment;
    private LoggerTrend loggerTrend;
    private List<String> parameterNames;//被勾选的
    private SqlDataBean sqlDataBean;


    private BaseMeterData baseData;//要存入数据库的数据
    private HintDialog hintDialog;

    private long duration,interval;
    private byte[] meterCommand;
    private String fileName;
    private long starTime;
    private Timer timer;
    private int saveCount = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        duration = intent.getLongExtra("Duration",0);
        interval = intent.getLongExtra("Interval",0);
        meterCommand = intent.getByteArrayExtra("MeterCommand");
        parameterNames = intent.getStringArrayListExtra("parameterNames");
        fileName = intent.getStringExtra("FileName");
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(0);
        starTime = System.currentTimeMillis();
        initDataCoreTable();


    }

    @Override
    public byte[] getMode() {
        return meterCommand;
    }

    private void initDataCoreTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parameterNames.size(); i++) {
            if (i == parameterNames.size()-1){
                stringBuilder.append(parameterNames.get(i));
            }else{
                stringBuilder.append(parameterNames.get(i)+",");
            }
        }

        sqlDataBean = new SqlDataBean();
        sqlDataBean.setCheckParameters(stringBuilder.toString());
        sqlDataBean.setStartDate(new Date());
        sqlDataBean.setFileName(fileName + "-" + sqlDataBean.getFormatDate());
        sqlDataBean.setDeviceName("DT-7760");
        if (meterCommand == RecordCommand.Record_Command_Power) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_Power);
        } else if (meterCommand == RecordCommand.Record_Command_Energy) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_Energy);
        } else if (meterCommand == RecordCommand.Record_Command_AmpHarmonic) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_AmpHarmonic);
        } else if (meterCommand == RecordCommand.Record_Command_VoltHarmonic) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_VoltHarmonic);
        } else if (meterCommand == RecordCommand.Record_Command_VoltAmp) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_VoltAmp);
        } else if (meterCommand == RecordCommand.Record_Command_Flicker) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_Flicker);
        } else if (meterCommand == RecordCommand.Record_Command_Frequency) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_Frequency);
        } else if (meterCommand == RecordCommand.Record_Command_Unbalance) {
            sqlDataBean.setModeType(SqlApi.Mode_Record_Unbalance);
        }
        sqlDataBean.save();
        saveCount = 0;
    }


    protected void setViewShow(int index) {
        if (this.isDestroyed())
            return;
        if (index == 0){
            if (null == loggerTrend) {
                loggerTrend = new LoggerTrend();
            }
            public_bottom.getChildAt(2).setVisibility(View.VISIBLE);
            public_bottom.getChildAt(1).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(4).setVisibility(View.VISIBLE);
            loggerTrend.clearTableListData();
            showFragment(loggerTrend,index+"");
        }

        else if (index == 1){
            if (null == eventFragment) {
                eventFragment = new DipsSwellsTrend();
            }
            public_bottom.getChildAt(0).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(1).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(2).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(3).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(4).setVisibility(View.INVISIBLE);
            showFragment(eventFragment,index+"");
        }
    }
    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(Res2String(R.string.Event)));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(Res2String(R.string.Stop)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 2:
                setViewShow(1);
                break;
            case 4:
                stopClick();
                break;
        }
    }

    private EventsBean eventsBeanL1 = new EventsBean(),eventsBeanL2 = new EventsBean(),eventsBeanL3 = new EventsBean();

    private void recordDipValue(MeterSudden_UP_Down mupdown){
        PhaseObj phaseValue = mupdown.getPhaseValue();
        parseVL1(phaseValue.getPhaseA());
        parseVL2(phaseValue.getPhaseB());
        parseVL3(phaseValue.getPhaseC());

    }

    private int thresValue,thresValueTwo,hysterValue,hysterValueTwo,thresValue3,hysterValue3;

    private List<EventsBean> eventsBeans = new ArrayList<EventsBean>();

    private void parseVL3(MeterData L3) {
        //骤降事件开始记录
        if (L3.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL3.getType())){
            eventsBeanL3.setType("Dip");
            eventsBeanL3.setLine("L3");
            eventsBeanL3.setStartTime(new Date());
        }else if(L3.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL3.getType())){
            //骤升事件开始记录
            eventsBeanL3.setType("Sweels");
            eventsBeanL3.setLine("L3");
            eventsBeanL3.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L3.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL3.getType())){
            eventsBeanL3.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL3.copySelf());
            eventsBeanL3.clearself();
        }else if (L3.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL3.getType())){
            //骤升事件结束记录
            eventsBeanL3.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL3.copySelf());
            eventsBeanL3.clearself();
        }

    }

    private void parseVL2(MeterData L2) {
        //骤降事件开始记录
        if (L2.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL2.getType())){
            eventsBeanL2.setType("Dip");
            eventsBeanL2.setLine("L2");
            eventsBeanL2.setStartTime(new Date());
        }else if(L2.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL2.getType())){
            //骤升事件开始记录
            eventsBeanL2.setType("Sweels");
            eventsBeanL2.setLine("L2");
            eventsBeanL2.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L2.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL2.getType())){
            eventsBeanL2.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL2.copySelf());
            eventsBeanL2.clearself();
        }else if (L2.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL2.getType())){
            //骤升事件结束记录
            eventsBeanL2.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL2.copySelf());
            eventsBeanL2.clearself();
        }

    }

    private void parseVL1(MeterData L1){
        //骤降事件开始记录
        if (L1.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL1.getType())){
            eventsBeanL1.setType("Dip");
            eventsBeanL1.setLine("L1");
            eventsBeanL1.setStartTime(new Date());
        }else if(L1.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL1.getType())){
            //骤升事件开始记录
            eventsBeanL1.setType("Sweels");
            eventsBeanL1.setLine("L1");
            eventsBeanL1.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L1.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL1.getType())){
            eventsBeanL1.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL1.copySelf());
            eventsBeanL1.clearself();
        }else if (L1.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL1.getType())){
            //骤升事件结束记录
            eventsBeanL1.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL1.copySelf());
            eventsBeanL1.clearself();
        }

    }

    private void recordInrushValue(MeterSudden_UP_Down mupdown){
        PhaseObj phaseValue = mupdown.getPhaseValue();
        parseAL1(phaseValue.getPhaseA());
        parseAL2(phaseValue.getPhaseB());
        parseAL3(phaseValue.getPhaseC());

    }

    private void parseAL3(MeterData L3) {

        if(L3.getValue() > thresValue3&& TextUtils.isEmpty(eventsBeanL3.getType())){
            //骤升事件开始记录
            eventsBeanL3.setType("Inrush");
            eventsBeanL3.setLine("L3");
            eventsBeanL3.setStartTime(new Date());
        }

        if (L3.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL3.getType())){
            //骤升事件结束记录
            eventsBeanL3.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL3.copySelf());
            eventsBeanL3.clearself();
        }

    }

    private void parseAL2(MeterData L2) {

        if(L2.getValue() > thresValue3 && TextUtils.isEmpty(eventsBeanL2.getType())){
            //骤升事件开始记录
            eventsBeanL2.setType("Inrush");
            eventsBeanL2.setLine("L2");
            eventsBeanL2.setStartTime(new Date());
        }

        if (L2.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL2.getType())){
            //骤升事件结束记录
            eventsBeanL2.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL2.copySelf());
            eventsBeanL2.clearself();
        }

    }


    private void parseAL1(MeterData L1){

        if(L1.getValue() > thresValue3 && TextUtils.isEmpty(eventsBeanL1.getType())){
            //骤升事件开始记录
            eventsBeanL1.setType("Inrush");
            eventsBeanL1.setLine("L1");
            eventsBeanL1.setStartTime(new Date());
        }

        if (L1.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL1.getType())){
            //骤升事件结束记录
            eventsBeanL1.setEndTime(new Date());
            //生成对象，保存到List中
            eventsBeans.add(eventsBeanL1.copySelf());
            eventsBeanL1.clearself();
        }

    }



    private void showDialog(String title, String message, String left, String right, DialogInterface.OnClickListener leftListener,DialogInterface.OnClickListener rightListener){
        if (hintDialog == null){
            hintDialog = new HintDialog(this);
        }

        hintDialog.showDialog(title,message,left,right,leftListener,rightListener);
    }


    private void stopClick(){
        showDialog("", "", Res2String(R.string.Sure), Res2String(R.string.No),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                                    停止记录，保存文件
                        stopLogger();

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void stopLogger() {
        if (timer != null){
            timer.cancel();
            timer = null;
            if (sqlDataBean != null && sqlDataBean.isSaved()){
                DBManager.getInstance().updateBeanEndDateSync(sqlDataBean.getId(),new Date(),null);
                finish();
            }else{
                finish();
            }
        }
    }


    @Override
    public void destoryself() {

        //清除对象池中的状态
//        MeterDataPool.recycle(sqlDataBean);

        if (eventFragment != null && eventFragment.isAdded()){
            setViewShow(0);
        }else if(loggerTrend != null && loggerTrend.isAdded()){
            stopClick();
        }else{
            super.destoryself();
        }
    }

    @Override
    public void onBackPressed() {
        destoryself();
    }

    private void loggerData() {
        if (timer != null)
            return;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                saveCount += SqlDataTool.getSaveCount(baseData);

                if (saveCount > SqlApi.MAX_LENGTH){
                    if (sqlDataBean != null && sqlDataBean.isSaved()){
                        DBManager.getInstance().updateBeanEndDate(sqlDataBean.getId(), new Date());
                        initDataCoreTable();
                        loggerData();
                    }
                }else{
                    saveData();
                }
            }
        },0,interval);

    }

    private void saveData(){
        switch (sqlDataBean.modeType){
            case SqlApi.Mode_Power:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlData(LoggerThendActivity.this,(MeterAllParameter) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);
                }
                break;
            case SqlApi.Mode_Unbalance:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlData(LoggerThendActivity.this,(MeterThreeUnbalanced) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);
                }
                break;
            case SqlApi.Mode_Dip:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlData(LoggerThendActivity.this,(MeterSudden_UP_Down) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);

                }
                break;
            case SqlApi.Mode_Harmonic:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlData(LoggerThendActivity.this,(MeterThreeHarmonic) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);

                }
                break;
            case SqlApi.Mode_Shipboard:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlData(LoggerThendActivity.this,(MeterHz_400) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);

                }
                break;
            case SqlApi.Mode_Inrush:
                if (baseData != null){
                    List<SqlMeterData> sqlMeterDataList = SqlDataTool.toSqlDataInrush(LoggerThendActivity.this,(MeterSudden_UP_Down) baseData,sqlDataBean.getCheckParameters());
                    DBManager.getInstance().insetMeterData(sqlDataBean.getFileName(),sqlMeterDataList,null);

                }
                break;
        }
        baseData = null;
    }

    @Override
    public void onDataReceived(byte[] bytes) {

    }

    private ModelAllData modelist;

    @Override
    public void onDataReceivedModel(ModelAllData list) {

        /*if (getMode() == MeterCommand.Sudden_UP_Down || getMode() == MeterCommand.Surge_A){
            MeterSudden_UP_Down mupdown = (MeterSudden_UP_Down) baseMeterData;
            if (getMode() == MeterCommand.Sudden_UP_Down)
                recordDipValue(mupdown);
            else
                recordInrushValue(mupdown);
            if (eventFragment != null && eventFragment.isAdded()){
                eventFragment.addDataToList(eventsBeans);
            }
        }

        if (loggerTrend != null && loggerTrend.isAdded() && meterCommand.value() == this.meterCommand){

            loggerTrend.setShowMeterData(baseMeterData,parameterNames);
            if (loggerTrend.isAdded() && (System.currentTimeMillis() - starTime) < duration){
                //写入数据库
                baseData  =  baseMeterData;
                loggerData();
            }else if (loggerTrend.isAdded() && (System.currentTimeMillis() - starTime) > duration){
                stopLogger();
            }
        }*/
    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
