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

//电能质量监测
public class PowerMonitorActivity extends BaseActivity {
    private PowerMonitorSet Fragment_Set;
//    private DipsSwellsMeter Fragment_first;
    private PowerMonitorBar Fragment_Second;
    private int currentFragmentIndex = 2;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int funTypeIndex=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.powermonitor_icon);
        setTitle("");
        setViewShow(2);
        setBottom4TextSize(18);

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
        data.add(new BaseBottomAdapterObj(0,null));
        data.add(new BaseBottomAdapterObj(1,null));
        data.add(new BaseBottomAdapterObj(2,null));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.defaults)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.Start)));
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

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:


                break;
            case 1:

                break;
            case 2:
//                if (currentFragmentIndex != 2) {
//                    setViewShow(obj.getSwitchindex());
//                }
                break;
            case 3:
                if (currentFragmentIndex == 2) {
                    AppConfig.getInstance().setDipsSet_Default(true);
                    if (Fragment_Set != null)
                        Fragment_Set.resetSet();
                }
                break;

            case 4:
                if(currentFragmentIndex == 2) {
                    setViewShow(1);
                    if(AppConfig.getInstance().isDipsSet_Default())
                        AppConfig.getInstance().setDipsSet_Default(false);
                    if(Fragment_Set!=null)
                        Fragment_Set.saveSetting();
                }else{
                    isHold = obj.getSwitchindex() == 0 ? true : false;
                }
                break;
        }
    }

    private void setViewShow(int index){
        currentFragmentIndex = index;
        if (index==1){
            if (null == Fragment_Second) {
                Fragment_Second = new PowerMonitorBar();
            }
            updateBottomView(new BaseBottomAdapterObj(0,Res2String(R.string.powermonitor_vrms)),0);
            updateBottomView(new BaseBottomAdapterObj(1, R.mipmap.harmonics_icon,R.mipmap.harmonics_icon), 1);
            updateBottomView(new BaseBottomAdapterObj(2,R.mipmap.transients_icon,R.mipmap.transients_icon), 2);
            updateBottomView(new BaseBottomAdapterObj(3,R.mipmap.dips_swells_icon,R.mipmap.dips_swells_icon), 3,true);
            updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
            showFragment(Fragment_Second,Res2String(R.string.Trend));

        }else if(index == 2){
            if (null == Fragment_Set) {
                Fragment_Set = new PowerMonitorSet();
            }
            showFragment(Fragment_Set,Res2String(R.string.Setup));
        } else {

//            if (null == Fragment_first) {
//                Fragment_first = new DipsSwellsMeter();
//            }
//            updateBottomView(new BaseBottomAdapterObj(0,null),0);
//            updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Event)),3);
//            showFragment(Fragment_first,Res2String(R.string.Meter));
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
                /*if (Fragment_first != null && Fragment_first.isAdded())
                    Fragment_first.setShowMeterData(modelAllData);
                else {
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setShowMeterData(modelAllData,funTypeIndex);
                }*/
                if (Fragment_Second != null && Fragment_Second.isAdded())
                    Fragment_Second.setShowMeterData(modelAllData,funTypeIndex);

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        possKeyDown(keyCode);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        //       log.e("========" + key.toString());
        switch (key) {
            case Up:
                if(Fragment_Set!=null && Fragment_Set.isAdded()){
                    Fragment_Set.upKey();
                }
                break;
            case Down:
                if(Fragment_Set!=null && Fragment_Set.isAdded()) {
                    Fragment_Set.downKey();
                }
                break;
            case Left:
                if(Fragment_Set!=null && Fragment_Set.isAdded()) {
                    Fragment_Set.moveCursor(-1);
                    if(Fragment_Set.forbidMoveRight()){
                        return true;
                    }
                }
                break;
            case Right:
                if(Fragment_Set!=null && Fragment_Set.isAdded()) {
                    Fragment_Set.moveCursor(1);
                    if(Fragment_Set.forbidMoveRight()){
                        return true;
                    }
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
                if(Fragment_Set!=null && Fragment_Set.isAdded()){


                }
                break;
            case Down:

                break;
            case Left:
                if(Fragment_Set!=null && Fragment_Set.isAdded())
                    Fragment_Set.moveCursor(-1);

                break;
            case Right:
                if(Fragment_Set!=null && Fragment_Set.isAdded())
                    Fragment_Set.moveCursor(1);
                break;
        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
