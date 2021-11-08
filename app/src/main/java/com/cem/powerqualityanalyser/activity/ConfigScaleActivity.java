package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * Config 修改底部参数界面
 */
public class ConfigScaleActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ConfigScaleFragment Fragment_First;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(0);
    }

    private void setViewShow(int index){
        if (index==0){
            if (null == Fragment_First) {
                Fragment_First = new ConfigScaleFragment();
            }
            showFragment(Fragment_First,Res2String(R.string.config_volt));

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
        data.add(new BaseBottomAdapterObj(2, null, Res2String(R.string.config_phase), Res2String(R.string.config_neutral)));
        data.add(new BaseBottomAdapterObj(3, Res2String(R.string.defaul)));
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
                showLeftRight(obj.getSwitchindex());
                break;
            case 3:
                if(Fragment_First!=null && Fragment_First.isAdded())
                    Fragment_First.resetSet();
                break;
            case 4:
                if(Fragment_First!=null && Fragment_First.isAdded())
                    Fragment_First.saveSetting();
                onBackPressed();
                break;

        }
    }

    private void showLeftRight(int index) {
        if (null == Fragment_First) {
            Fragment_First = new ConfigScaleFragment();
        }
        if (index == 1) {
            Fragment_First.stopRight();
        } else {
            Fragment_First.stopLeft();

        }
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
                }

                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded()) {
                    Fragment_First.moveCursor(1);
                    if(Fragment_First.forbidMoveRight()){
                        return true;
                    }
                }
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