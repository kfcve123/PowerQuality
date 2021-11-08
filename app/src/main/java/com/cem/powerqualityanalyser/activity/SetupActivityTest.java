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
import com.cem.powerqualityanalyser.adapter.SetAdapter;
import com.cem.powerqualityanalyser.fragment.SetupDefaultFragment;
import com.cem.powerqualityanalyser.fragment.SetupEightFragment;
import com.cem.powerqualityanalyser.fragment.SetupElevenFragment;
import com.cem.powerqualityanalyser.fragment.SetupOneFragment;
import com.cem.powerqualityanalyser.fragment.SetupFourFragment;
import com.cem.powerqualityanalyser.fragment.SetupNineFragment;
import com.cem.powerqualityanalyser.fragment.SetupFiveFragment;
import com.cem.powerqualityanalyser.fragment.SetupSevenFragment;
import com.cem.powerqualityanalyser.fragment.SetupSixFragment;
import com.cem.powerqualityanalyser.fragment.SetupTenFragment;
import com.cem.powerqualityanalyser.fragment.SetupThreeFragment;
import com.cem.powerqualityanalyser.fragment.SetupTwoFragment;
import com.cem.powerqualityanalyser.tool.LanguageStore;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.jeremyliao.liveeventbus.LiveEventBus;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import serialport.amos.cem.com.libamosserial.ModelAllData;

//用户设置
public class SetupActivityTest extends BaseActivity implements View.OnClickListener, SetAdapter.SetOnItemClickListner {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;
    private SetupDefaultFragment defaultFragment;

    private SetupOneFragment setupOneFragment;
    private SetupTwoFragment setupTwoFragment;
    private SetupThreeFragment setupThreeFragment;
    private SetupFourFragment setupFourFragment;
    private SetupFiveFragment setupFiveFragment;
    private SetupSixFragment setupSixFragment;
    private SetupSevenFragment setupSevenFragment;
    private SetupEightFragment setupEightFragment;
    private SetupNineFragment setupNineFragment;
    private SetupTenFragment setupTenFragment;
    private SetupElevenFragment setupElevenFragment;


    private TextView setup_tv1, setup_tv2, setup_tv3, setup_tv4, setup_tv5;
    private LinearLayout leftmenu;
    private int fragmentIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setContentView(R.layout.activity_setup);
  //      public_bottom.setVisibility(View.GONE);
        leftmenu = findViewById(R.id.leftmenu);
        leftmenu.setVisibility(View.GONE);
        showDefaultFragment();
        /**
         * 底部按钮还是继承父类布局下的底部按钮逻辑，以下代码等于无效，底部按钮leftmenu被隐藏
         */
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

    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void showDefaultFragment(){
        if(defaultFragment == null){
            defaultFragment = new SetupDefaultFragment();
        }
        fragmentIndex = -1;
//        setButtonViewData(new BaseBottomAdapterObj(0,Res2String(R.string.set_botton_zh)),0);
//        setButtonViewData(new BaseBottomAdapterObj(1,Res2String(R.string.set_botton_en)),1);
//        setButtonViewData(new BaseBottomAdapterObj(2,Res2String(R.string.set_botton_fr)),2);
//        setButtonViewData(new BaseBottomAdapterObj(3,Res2String(R.string.set_botton_de)),3);
//        setButtonViewData(new BaseBottomAdapterObj(4,""),4);
        showFragment(defaultFragment,Res2String(R.string.set));
        defaultFragment.setSetOnItemClickListner(this);
    }

    protected void setViewShow(int index) {
        log.e("====" + index);
        if (index == 1) {
            if (null == setupOneFragment) {
                setupOneFragment = new SetupOneFragment();
            }
//            setButtonViewData(new BaseBottomAdapterObj(0,Res2String(R.string.set_caluc_botton_var)),0);
//            setButtonViewData(new BaseBottomAdapterObj(1,Res2String(R.string.set_caluc_botton_wh)),1);
//            setButtonViewData(new BaseBottomAdapterObj(2,Res2String(R.string.set_caluc_botton_fk)),2);
//            setButtonViewData(new BaseBottomAdapterObj(3,Res2String(R.string.set_caluc_botton_fr)),3);
//            setButtonViewData(new BaseBottomAdapterObj(4,Res2String(R.string.set_caluc_botton_ptl)),4);

            showFragment(setupOneFragment, Res2String(R.string.set_caluc));
        } else if (index == 3) {
            if (null == setupTwoFragment) {
                setupTwoFragment = new SetupTwoFragment();
            }
            showFragment(setupTwoFragment, Res2String(R.string.set_wir));
        } else if (index == 5) {
            if (null == setupThreeFragment) {
                setupThreeFragment = new SetupThreeFragment();
            }
            showFragment(setupThreeFragment, Res2String(R.string.set_clamp));

        } else if (index == 7) {
            if (null == setupFourFragment) {
                setupFourFragment = new SetupFourFragment();
            }
            showFragment(setupFourFragment, Res2String(R.string.set_point));

        } else if (index == 8) {
            if (null == setupFiveFragment) {
                setupFiveFragment = new SetupFiveFragment();
            }
            showFragment(setupFiveFragment, Res2String(R.string.set_date));
        } else if (index == 9) {
            if (null == setupSixFragment) {
                setupSixFragment = new SetupSixFragment();
            }
            showFragment(setupSixFragment, Res2String(R.string.set_trend));
        } else if (index == 10) {
            if (null == setupSevenFragment) {
                setupSevenFragment = new SetupSevenFragment();
            }
            showFragment(setupSevenFragment, Res2String(R.string.set_show));

        } else if (index == 11) {
            if (null == setupEightFragment) {
                setupEightFragment = new SetupEightFragment();
            }
            showFragment(setupEightFragment, Res2String(R.string.set_delete));

        } else if (index == 12) {
            if (null == setupNineFragment) {
                setupNineFragment = new SetupNineFragment();
            }
            showFragment(setupNineFragment, Res2String(R.string.set_timer));
        } else if (index == 13) {
            if (null == setupTenFragment) {
                setupTenFragment = new SetupTenFragment();
            }
            showFragment(setupTenFragment, Res2String(R.string.set_info));

        } else if (index == 14) {
            if (null == setupElevenFragment) {
                setupElevenFragment = new SetupElevenFragment();
            }
            showFragment(setupElevenFragment, Res2String(R.string.set_warn));
        }
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
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_botton_zh)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.set_botton_en)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.set_botton_fr)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.set_botton_de)));
        data.add(new BaseBottomAdapterObj(4,null));
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

    final String[] locals = { "zh_rCN","en","fr","de"};
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

        switch (obj.getId()) {
            case 0://CN
            case 1://EN
                LanguageStore.setLanguageLocal(SetupActivityTest.this, locals[obj.getId()]);
                LiveEventBus.get("changeLanguage").post("Message By PostValue: " + new Random().nextInt(100));
                break;
            case 2://FR

                break;
            case 3://DE

                break;

        }
    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.setup_tv1:
                setViewShow(0);
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
        }*/

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
    public void setItemIndex(int index) {
 //       log.e("==========" + index);
        fragmentIndex = index;
        setViewShow(index);
        switch (index){
            case 0:




                break;
            case 2:

                break;


            case 4:
                break;


            case 6:

                break;



        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        /**
         * 增加这个功能会报异常 Activity has been destroyed 待解决
         */
        /*switch (key) {
            case Back:
                if (fragmentIndex != -1) {
                    showDefaultFragment();
                    return true;
                }
                break;
        }*/
        return super.onKeyDown(keyCode, event);
    }



    private  void possKeDown(int keyCode){
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
        switch (key){
            case Back:

                break;

        }
    }


    @Override
    public void setItemIndex(int index, boolean toggle) {



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
