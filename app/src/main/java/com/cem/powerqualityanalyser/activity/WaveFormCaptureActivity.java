package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

//波形捕捉
public class WaveFormCaptureActivity extends BaseActivity {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();

        setViewShow(1);

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
        data.add(new BaseBottomAdapterObj(null,Res2String(R.string.Trend),Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:
                setViewShow(obj.getSwitchindex());
                break;
        }
    }

    private  void  setViewShow(int index){
        /*if (index==0){
            if (null == Fragment_Second) {
                Fragment_Second = new FlickerMeter();

            }
            showFragment(Fragment_Second,Res2String(R.string.Meter));
        }else {
            if (null == Fragment_first) {
                Fragment_first = new FlickerTrend();

            }
            showFragment(Fragment_first,Res2String(R.string.Trend));
        }*/
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
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
