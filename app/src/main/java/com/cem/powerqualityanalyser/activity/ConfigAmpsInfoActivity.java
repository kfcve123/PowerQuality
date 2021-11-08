package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * Config 修改底部参数界面
 */
public class ConfigAmpsInfoActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ConfigAmpScaleFragment Fragment_First;
    private ConfigVoltScaleFragment Fragment_Second;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(1);
    }

    private void setViewShow(int index){
        if (index==1){
            if (null == Fragment_First) {
                Fragment_First = new ConfigAmpScaleFragment();
            }
            showFragment(Fragment_First,Res2String(R.string.config_amps));
        }else {
            if (null == Fragment_Second) {
                Fragment_Second = new ConfigVoltScaleFragment();
            }
            showFragment(Fragment_Second,Res2String(R.string.config_volt));
        }
    }


    private void showFragment(Fragment fragment, String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public byte[] getMode() {
        return null;
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {

        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0, null));
        data.add(new BaseBottomAdapterObj(1, null));
        data.add(new BaseBottomAdapterObj(2, null, Res2String(R.string.config_volt2), Res2String(R.string.config_amps2)));
        data.add(new BaseBottomAdapterObj(3, null, Res2String(R.string.config_neutral), Res2String(R.string.config_phase)));
        data.add(new BaseBottomAdapterObj(4, Res2String(R.string.back)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()) {
            case 2:
                setViewShow(obj.getSwitchindex());
                break;
            case 3:
                if(Fragment_First!=null && Fragment_First.isAdded()){
                    if (obj.getSwitchindex() == 1) {
                        Fragment_First.stopRight();
                    } else {
                        Fragment_First.stopLeft();

                    }

                }else if(Fragment_Second!=null && Fragment_Second.isAdded()){
                    if (obj.getSwitchindex() == 1) {
                        Fragment_Second.stopRight();
                    } else {
                        Fragment_Second.stopLeft();
                    }
                }

                break;
            case 4:
                onBackPressed();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Fragment_First!=null)
            Fragment_First.saveSetting();
        if(Fragment_Second!=null)
            Fragment_Second.saveSetting();
        LiveEventBus.get("updateconfig").post("Message By PostValue: " + new Random().nextInt(100));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        possKeyDown(keyCode);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Left:
                if (Fragment_First != null && Fragment_First.isAdded()) {
                    Fragment_First.moveCursor(-1);
                    if(!Fragment_First.forbidMoveRight()){
                        return true;
                    }
                }else if(Fragment_Second!=null && Fragment_Second.isAdded()){
                    Fragment_Second.moveCursor(-1);
                    if(!Fragment_Second.forbidMoveRight()){
                        return true;
                    }

                }

                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded()) {
                    Fragment_First.moveCursor(1);
                    if(Fragment_First.forbidMoveRight()){
                        return true;
                    }
                }else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    Fragment_Second.moveCursor(1);
                    if(Fragment_Second.forbidMoveRight()){
                        return true;
                    }
                }
                break;
            case Back:
                onBackPressed();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View view) {

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