package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;

import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//闪变
public class FlickerActivity extends BaseActivity {
//    private FlickerTrend Fragment_first;
    private FlickerMeter Fragment_Second;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.flicker_icon);
        setTitle("");
        showLoading();
        setViewShow(1);
        dissLoading(1500l);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        refeshHeadColor();
    }


    @Override
    public byte[] getMode() {
//        return new byte[]{(byte) 0xE6,0x00};
        return null;
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
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
      /*  data.add(new BaseBottomAdapterObj(0,null));
        data.add(new BaseBottomAdapterObj(1,null,Res2String(R.string.Trend),Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Trend)));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));*/
        data.add(new BaseBottomAdapterObj(0,null));
        data.add(new BaseBottomAdapterObj(1,null));
        data.add(new BaseBottomAdapterObj(2,null));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.Start)));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }
    private byte[] cmd;

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:

                break;
            case 1:
                setViewShow(obj.getSwitchindex());
                break;
            case 3:
                if(AppConfig.getInstance().getConfig_nominal() ==0)
                    cmd  = new byte[]{(byte) 0xE6,0x00};
                else if(AppConfig.getInstance().getConfig_nominal() ==1)
                    cmd  = new byte[]{(byte) 0xE6,0x01};
                serialHelper.sendData(cmd);
                if(openLog)
                    Toast.makeText(this, BleUtil.dec_hex(cmd),Toast.LENGTH_LONG).show();
                break;
            case 4:
                isHold = obj.getSwitchindex() == 0 ? true : false;
                break;
        }
    }

    private void setViewShow(int index){
        if (index==1){
            if (null == Fragment_Second) {
                Fragment_Second = new FlickerMeter();

            }
            showFragment(Fragment_Second,Res2String(R.string.Meter));
        }else {
//            if (null == Fragment_first) {
//                Fragment_first = new FlickerTrend();
//
//            }
//            showFragment(Fragment_first,Res2String(R.string.Trend));
        }
    }
    private  void  showFragment(Fragment fragment, String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if(modelAllData!=null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E6_Flicker) {
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setShowMeterData(modelAllData);
                /*if (Fragment_first != null && Fragment_first.isAdded())
                    Fragment_first.setShowMeterData(modelAllData);
                else {
                    //筛选出选中的选项的数据转成对象传入曲线
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setShowMeterData(modelAllData);
                }*/
                }
            }
        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
