package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.ButtonListenerTool;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

//警告提示
public class WarningActivity extends BaseActivity implements View.OnClickListener {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;
    protected WarningTimeFragment Fragment_first;
    protected WarningSetFragment Fragment_Second;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.warn_info);
        setTitle("");
        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    protected void setViewShow(int index) {
        if (index == 1) {
            if (null == Fragment_Second) {
                Fragment_Second = new WarningSetFragment();
            }
            showFragment(Fragment_Second, Res2String(R.string.set));
        } else if (index == 0) {
            if (null == Fragment_first) {
                Fragment_first = new WarningTimeFragment();
            }
            showFragment(Fragment_first, Res2String(R.string.time));
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }



    /**
     * 初始化底部按钮数据
     *
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();

        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.Zoom),true,R.mipmap.warn_set));
        data.add(new BaseBottomAdapterObj(1,null));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Zoom),true,R.mipmap.warn_file));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.Zoom),true,R.mipmap.warn_save));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.Zoom),true,R.mipmap.warn_run));
        return data;
    }

    /**
     * 弹窗菜单点击事件
     *
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    /**
     * 底部按钮点击事件
     *
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        setViewShow(obj.getId());
        switch (obj.getId()){
            case 0:
                setViewShow(1);
                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    public void registerWarnTouch(WarnTouchEvent warnTouchEvent){
        this.warnTouchEvent = warnTouchEvent;
    }

    private WarnTouchEvent warnTouchEvent;
    public interface WarnTouchEvent{
        boolean onTouchEvent(MotionEvent event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Fragment_first.isAdded()){
            Fragment_first.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        warnTouchEvent = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    /**
     * 测试飞行模式
     *
     * @param enable
     */
    private void setAirPlaneModeByRadio(boolean enable) {
        try {
            Class<?> telephonyManager = Class.forName("android.telephony.TelephonyManager");
            Method setRadio = telephonyManager.getMethod("setRadio", boolean.class);
            setRadio.invoke(telephonyManager.getMethod("getDefault").invoke(null), enable);
            if (enable) {
                Method toggleRadioOnOff = telephonyManager.getMethod("toggleRadioOnOff");
                toggleRadioOnOff.invoke(telephonyManager.getMethod("getDefault").invoke(null));
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.e(e.getMessage());
        }
    }


    private void shotDown(){
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    private void reboot(){
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        pm.reboot("");
    }


    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
