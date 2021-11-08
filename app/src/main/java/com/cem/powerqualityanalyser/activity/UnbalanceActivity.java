package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.meterobject.MeterUnbalancedObj;

import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//不平衡
public class UnbalanceActivity extends BaseActivity {
    private int popwindowsIndex=0;
    private UnbalanceMeter Fragment_first;
    private UnbalanceVector Fragment_Second;
    private UnbalanceTrend Fragment_Third;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int currentFragmentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.unbalance_icon);
        setTitle("");
        showLoading();
        setViewShow(1);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return sendOrderData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (true){
            byte meterDataCount=18;
            byte[] bytes=new byte[meterDataCount*4 + 6];
            bytes[0]=meterDataCount;

        }
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    /**
     * 初始化底部按钮数据
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,null));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Trend)));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return  data;
    }

    /**
     * 弹窗菜单点击事件
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        popwindowsIndex=positio;
    }

    /**
     * 底部按钮点击事件
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:

                break;
            case 1:
                setViewShow(obj.getSwitchindex());
                break;
            case 2:
                setViewShow(2);
                break;
            case 3:

                break;
            case 4:
                isHold = obj.getSwitchindex() == 0 ? true : false;
                break;
        }

    }
    private void setViewShow(int index){
        if (index==1){
            if (null == Fragment_Second) {
                Fragment_Second = new UnbalanceVector();
            }
            updateBottomView(new BaseBottomAdapterObj(1,null,Res2String(R.string.Meter),Res2String(R.string.phasor)),1);
            showFragment(Fragment_Second,Res2String(R.string.phasor));
        }else if(index == 0){
            if (null == Fragment_first) {
                Fragment_first = new UnbalanceMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Trend)),2);
            showFragment(Fragment_first,Res2String(R.string.Meter));
        }else {
            if(null == Fragment_Third)
                Fragment_Third = new UnbalanceTrend();
            showFragment(Fragment_Third,Res2String(R.string.Trend));

        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }

    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData(){
        byte[] order = new byte[0];
        order = new byte[]{(byte) 0xE4,0X00};
        return order;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serialHelper!=null){
            serialHelper.closeSerialPort();
            serialHelper = null;
        }
    }

    @Override
    public void onDataReceived(byte[] bytes) {
        log.e("-----" + BleUtil.dec_hex(bytes));
    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if(!isStart)
            isStart = true;
        if(modelAllData!=null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E4_Unbalanced){
 //           dissLoading();
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_first != null && Fragment_first.isAdded())
                        Fragment_first.setShowMeterData(modelAllData);
                    else if (Fragment_Second != null && Fragment_Second.isAdded()){
                            Fragment_Second.setShowMeterData(modelAllData);
                    }else if(null!=Fragment_Third && Fragment_Third.isAdded()){
                        Fragment_Third.setShowMeterData(modelAllData,0);
                    }
                }
            }
        }
    }
    
    @Override
    public void onDataReceivedList(List list) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
//        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:

                break;
            case Right:

                break;
            case Menu:
            case Back:
                if(isStart){
                    isStartAlert(Res2String(R.string.unbalance));
                }
                break;
            case Power:


                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
