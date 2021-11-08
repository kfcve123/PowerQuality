package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//能耗计算器
public class EneryCalculatorActivity extends BaseActivity {
    private EneryCalculatorSet Fragment_third;
    private EneryCalculatorMeter Fragment_four;
    private EneryCalculatorDefault Fragment_first;
    private EneryCalculatorTimeSet Fragment_Second;

    private int currentFragmentIndex = 1;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int funTypeIndex=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.calculator);
        setTitle("");
        setViewShow(1);
        setBottom2TextSize(20);
    }

    @Override
    public byte[] getMode() {
        return new byte[]{(byte) 0xE8,0x00};
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set)));
        data.add(new BaseBottomAdapterObj(1,null));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.Hold)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.run)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        switch (obj.getId()) {
            case 0://切换 V / A
                funTypeIndex=positio;

                break;
        }
    }
    private boolean cancel;

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()) {
            case 0:
                if(currentFragmentIndex ==1)
                    setViewShow(3);
                else if(currentFragmentIndex == 3){
                    Fragment_third.changeMFt(obj.getSwitchindex() == 1? true:false);
                }
                break;
            case 1:
                if(currentFragmentIndex == 4){
                    setViewShow(1);
                } else if(currentFragmentIndex == 3){
                    Fragment_third.changeMmAwg(obj.getSwitchindex() == 1? true:false);
                }
                break;
            case 2:
                if(currentFragmentIndex == 1){
                    if(cancel){//跳时间设置
                        setViewShow(2);
                    }else{//跳Meter
                        setViewShow(4);
                    }
                }else if(currentFragmentIndex == 2){

                }else if(currentFragmentIndex == 3){
                    showLeftRight(obj.getSwitchindex());
                }else if(currentFragmentIndex == 4){

                }
                break;
            case 3:
                if(currentFragmentIndex == 1){
                    if (!cancel) {
                        cancel = true;
                        updateBottomView(new BaseBottomAdapterObj(0, null), 0);
                        updateBottomView(new BaseBottomAdapterObj(2, Res2String(R.string.timed)), 2);
                        updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.cancel)), 3);
                        updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.now)), 4);
                    }else{
                        cancel = false;
                        updateBottomView(new BaseBottomAdapterObj(0,Res2String(R.string.set)),0);
                        updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Meter)),2);
                        updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Hold)),3);
                        updateBottomView(new BaseBottomAdapterObj(4,Res2String(R.string.run)),4);
                    }
                }else if(currentFragmentIndex == 2){
                    setViewShow(1);
                }else if(currentFragmentIndex == 3){

                }else if(currentFragmentIndex == 4){

                }
                break;

            case 4:
                if(currentFragmentIndex == 3){
                    setViewShow(1);
                }
                break;
        }
    }

    private void showLeftRight(int index) {
        if (null == Fragment_third) {
            Fragment_third = new EneryCalculatorSet();
        }
        if (index == 1) {
            Fragment_third.stopRight();
        } else {
            Fragment_third.stopLeft();

        }
    }


    private void setViewShow(int index){
        if(this.isDestroyed())
            return;
        currentFragmentIndex = index;
        if (index==1){
            if (null == Fragment_first) {
                Fragment_first = new EneryCalculatorDefault();
            }
            cancel = false;
            updateBottomView(new BaseBottomAdapterObj(0,Res2String(R.string.set)),0);
            updateBottomView(new BaseBottomAdapterObj(1,null),1);
            updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Meter)),2);
            updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Hold)),3);
            updateBottomView(new BaseBottomAdapterObj(4,Res2String(R.string.run)),4);
            showFragment(Fragment_first,Res2String(R.string.defaults));

        } else if(index == 2){
            if (null == Fragment_Second) {
                Fragment_Second = new EneryCalculatorTimeSet();
            }
            updateBottomView(new BaseBottomAdapterObj(0,null),0);
            updateBottomView(new BaseBottomAdapterObj(1,null),1);
            updateBottomView(new BaseBottomAdapterObj(2, null), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.cancel)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.Start)), 4);

            showFragment(Fragment_Second, Res2String(R.string.timed));
        }else if(index == 3) {
            if (null == Fragment_third) {
                Fragment_third = new EneryCalculatorSet();
            }
            updateBottomView(new BaseBottomAdapterObj(0, null, Res2String(R.string.unit_m), Res2String(R.string.unit_ft)), 0);
            updateBottomView(new BaseBottomAdapterObj(1, null, Res2String(R.string.unit_mm), Res2String(R.string.unit_awg)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, null, Res2String(R.string.cable), Res2String(R.string.rate)), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.unit)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.back)), 4);
            showFragment(Fragment_third, Res2String(R.string.Setup));
        } else {

            if (null == Fragment_four) {
                Fragment_four = new EneryCalculatorMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(0,null),0);
            updateBottomView(new BaseBottomAdapterObj(1, Res2String(R.string.back)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, null), 2);
            updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Event)),3);
            updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
            showFragment(Fragment_four,Res2String(R.string.Meter));
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if (modelAllData != null) {
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                dissLoading();
                if (Fragment_first != null && Fragment_first.isAdded())
                    Fragment_first.setShowMeterData(modelAllData);
                else {
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setShowMeterData(modelAllData,funTypeIndex);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        //       log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:
                if (Fragment_third != null && Fragment_third.isAdded()) {
                    Fragment_third.checkMode();
                    if(Fragment_third.forbidMoveDown()){
                        return true;
                    }
                }else if(Fragment_Second!=null && Fragment_Second.isAdded()) {
                    Fragment_Second.downKey();
                }

                break;
            case Left:
                if (Fragment_third != null && Fragment_third.isAdded()) {
                    Fragment_third.moveCursor(-1);
                    if (!Fragment_third.forbidMoveRight()) {
                        return true;
                    }
                }else if(Fragment_Second!=null && Fragment_Second.isAdded()) {
                    Fragment_Second.moveCursor(-1);
                }

                break;
            case Right:
                if (Fragment_third != null && Fragment_third.isAdded()) {
                    Fragment_third.moveCursor(1);
                    if(Fragment_third.forbidMoveRight()){
                        return true;
                    }
                } else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    Fragment_Second.moveCursor(1);
                    if(Fragment_Second.forbidMoveRight()){
                        return true;
                    }
                }
                break;
            case Back:
                if(currentFragmentIndex!=1) {
                    setViewShow(1);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        //       log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:


                break;
            case Right:

                break;
            case Back:
                if(currentFragmentIndex!=1)
                    setViewShow(1);
                break;
        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
