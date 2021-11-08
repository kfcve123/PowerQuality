package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

//仪表总参数
public class DeviceParametersActivity extends BaseActivity implements View.OnClickListener {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;
    //    protected SetupFirstFragment Fragment_first;
//    protected SetupSecondFragment Fragment_Second;
//    protected SetupThirdFragment Fragment_three;
//    protected SetupFourFragment Fragment_four;
//    protected SetupFiveFragment Fragment_five;
    private TextView setup_tv1, setup_tv2, setup_tv3, setup_tv4, setup_tv5;
    private LinearLayout leftmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setContentView(R.layout.activity_setup);
        //      public_bottom.setVisibility(View.GONE);
        leftmenu = findViewById(R.id.leftmenu);
        leftmenu.setVisibility(View.GONE);
        setup_tv1 = findViewById(R.id.setup_tv1);
        setup_tv2 = findViewById(R.id.setup_tv2);
        setup_tv3 = findViewById(R.id.setup_tv3);
        setup_tv4 = findViewById(R.id.setup_tv4);
        setup_tv5 = findViewById(R.id.setup_tv5);
        setup_tv1.setOnClickListener(this);
        setup_tv2.setOnClickListener(this);
        setup_tv3.setOnClickListener(this);
        setup_tv4.setOnClickListener(this);
        setup_tv5.setOnClickListener(this);
        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    protected void setViewShow(int index) {
        /*if (index == 1) {
            if (null == Fragment_Second) {
                Fragment_Second = new SetupSecondFragment();
            }
            showFragment(Fragment_Second, Res2String(R.string.Meter));
        } else if (index == 0) {
            if (null == Fragment_first) {
                Fragment_first = new SetupFirstFragment();
            }
            showFragment(Fragment_first, Res2String(R.string.Trend));
        } else if (index == 2) {
            if (null == Fragment_three) {
                Fragment_three = new SetupThirdFragment();
            }
            showFragment(Fragment_three, Res2String(R.string.Trend));

        } else if (index == 3) {
            if (null == Fragment_four) {
                Fragment_four = new SetupFourFragment();
            }
            showFragment(Fragment_four, Res2String(R.string.Trend));

        } else if (index == 4) {
            if (null == Fragment_five) {
                Fragment_five = new SetupFiveFragment();
            }
            showFragment(Fragment_five, Res2String(R.string.Trend));
        }*/
    }

    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.urerLayout, fragment, tag);
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
        List<BaseBottomAdapterObj> data = new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.setup_overview)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.setup_system)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.setup_conect)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.setup_freq)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.setup_info)));
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
        switch (obj.getId()) {
            case 0:
                shotDown();
                break;
            case 1:
                reboot();
                break;
            case 2:
                setAirPlaneModeByRadio(true);

                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup_tv1:
                setViewShow(0);
//                setup_tv1.setBackgroundResource(R.drawable.u24_normal);
//                setup_tv2.setBackgroundResource(R.drawable.u6_normal);
//                setup_tv3.setBackgroundResource(R.drawable.u6_normal);
//                setup_tv4.setBackgroundResource(R.drawable.u6_normal);
//                setup_tv5.setBackgroundResource(R.drawable.u6_normal);
                break;
            case R.id.setup_tv2:
                setViewShow(1);

                break;
            case R.id.setup_tv3:
                setViewShow(2);

                break;
            case R.id.setup_tv4:
                setViewShow(3);

                break;
            case R.id.setup_tv5:
                setViewShow(4);

                break;
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


    private void shotDown() {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    private void reboot() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        pm.reboot("");
    }

    private void toLoggerThrend() {
 /*       Intent intent = new Intent(this,VerifyActivity.class);
        intent.putExtra(AppConfig.MainPutActivityNameKey, "Verify");
        startActivity(intent);*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       /* if (this.Fragment_five != null && this.Fragment_five.isAdded() &&ButtonListenerTool.getTool().onClick(MeterKeyValue.valueOf(keyCode))){
            Toast.makeText(this,"进入调试模式",Toast.LENGTH_SHORT).show();
            toLoggerThrend();
        }*/
        return super.onKeyDown(keyCode, event);
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
