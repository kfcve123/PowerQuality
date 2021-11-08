package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.enums.HarmonicsType;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.meterobject.MeterHarmonicObj;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.MeterHarmonicTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.RightModeView;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//谐波
public class HarmonicsActivity extends BaseActivity implements BaseFragmentTrend.OnWirAndRightIndexCallBack{

    private int funTypeIndex=0;//底部第一个按钮的Pop角标
    private HarmonicsTrend Fragment_first;
    private HarmonicsMeter Fragment_Second;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int currentFragmentIndex = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.harmonics_icon);
        setTitle("");
        setViewShow(1);
        setBottom2TextSize(18);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return sendOrderData(funTypeIndex);
    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refeshHeadColor();
    }

    /**
     * 初始化底部按钮数据
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        if(wir_index == 0 ||wir_index == 5 ||wir_index == 6||wir_index == 7||wir_index == 9)//ASV
            data.add(new BaseBottomAdapterObj(Res2Stringarr(R.array.asv_array)[0], Res2Stringarr(R.array.asv_array)));
        else//ASU
            data.add(new BaseBottomAdapterObj(Res2Stringarr(R.array.asu_array)[0], Res2Stringarr(R.array.asu_array)));
        data.add(new BaseBottomAdapterObj(1,null,Res2String(R.string.harmonic_graph),Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.Trend)));
 //       data.add(new BaseBottomAdapterObj(3,Res2String(R.string.Event)));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return  data;
    }

    /**
     * 弹窗菜单点击事件
     * @param obj  切换了 S, U, V
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        if (obj.getId() == 0) {
            funTypeIndex = positio;
            if(serialHelper!=null) {
                serialHelper.sendData(sendOrderData(funTypeIndex));
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setHarmonicsType(funTypeIndex);
                }else {
                    //筛选出选中的选项的数据转成对象传入曲线
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setHarmonicsType(funTypeIndex);
                }

            }
        }
    }
    private int i  = 1;
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
                if(currentFragmentIndex ==1){


                }else {
                    if (Fragment_first != null) {
                        if (i < 3) {
                            i++;
                        }
                        Fragment_first.zoomScale(i);
                    }
                }
                break;
            case 3:
                if(currentFragmentIndex ==1){

                }else {
                    if (Fragment_first != null) {
                        if (i > 1) {
                            i--;
                        }
                        Fragment_first.zoomScale(i);
                    }
                }
                break;
            case 4:
                isHold = obj.getSwitchindex() == 0 ? true : false;
                log.e("hold :" + isHold);
//                obj.setSwitchindex(obj.getSwitchindex() == 1 ? 1 : 0);
                break;
        }

    }

    private void setViewShow(int index){
        currentFragmentIndex = index;
        if (index==1){
            if (null == Fragment_Second) {
                Fragment_Second = new HarmonicsMeter();
            }
//            updateBottomView(new BaseBottomAdapterObj(2,false,Res2String(R.string.Trend)),2);
//            updateBottomView(new BaseBottomAdapterObj(3,false,Res2String(R.string.Event)),3);
            if(wir_index == 0 ||wir_index == 5 ||wir_index == 6||wir_index == 7||wir_index == 9)//ASV
                updateBottomView(new BaseBottomAdapterObj(Res2Stringarr(R.array.asv_array)[0], Res2Stringarr(R.array.asv_array)),0);
            else//ASU
                updateBottomView(new BaseBottomAdapterObj(Res2Stringarr(R.array.asu_array)[0], Res2Stringarr(R.array.asu_array)),0);
            updateBottomView(new BaseBottomAdapterObj(2,false,null),2);
            updateBottomView(new BaseBottomAdapterObj(3,false,null),3);
            showFragment(Fragment_Second,Res2String(R.string.Meter));
            Fragment_Second.setOnWirAndRightIndexCallBack(this);
            setBottom2TextSize(18);
        }else {
            if (null == Fragment_first) {
                Fragment_first = new HarmonicsTrend();
            }
            if(wir_index == 0 ||wir_index == 5 ||wir_index == 6||wir_index == 7||wir_index == 9)//ASV
                updateBottomView(new BaseBottomAdapterObj(Res2Stringarr(R.array.asv_array)[0], Res2Stringarr(R.array.asv_array)),0);
            else//ASU
                updateBottomView(new BaseBottomAdapterObj(Res2Stringarr(R.array.asu_array)[0], Res2Stringarr(R.array.asu_array)),0);
            updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Zoom),true,R.mipmap.zoom_on),2);
            updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Zoom),true,R.mipmap.zoom_off),3);
            showFragment(Fragment_first,Res2String(R.string.harmonic_graph));
            Fragment_first.setOnWirAndRightIndexCallBack(this);
            setBottom2TextSize(18);
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public void onDataReceived(byte[] bytes) {
  //      log.e("-----" + BleUtil.dec_hex(bytes));
    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        try {
            if(!isStart)
                isStart = true;
            if (modelAllData != null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E3_Harmonic) {
                dissLoading();
                if (!isHold) {
                    if (Fragment_first != null && Fragment_first.isAdded())
                        Fragment_first.setShowMeterData(modelAllData, funTypeIndex);
                    else {
                        //筛选出选中的选项的数据转成对象传入曲线
                        if (Fragment_Second != null && Fragment_Second.isAdded())
                            Fragment_Second.setShowMeterData(modelAllData, funTypeIndex);
                    }
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }

    public void updateBottomData(BaseBottomAdapterObj baseBottomAdapterObj, int i) {


    }

    @Override
    public void returnWirAndRight(int wir, int right) {
//        if(Fragment_first!=null)
//            Fragment_first.setRightIndex(right);
//        if(Fragment_Second!=null)
//            Fragment_Second.setRightIndex(right);


    }

    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData(int funIndex){
        byte[] order = new byte[0];
        if(wir_index == 0 ||wir_index == 5 ||wir_index == 6||wir_index == 7||wir_index == 9){
            switch (funIndex){
                case 2://A
                    order = new byte[]{(byte) 0xE3,0X00};
                    break;
                case 1://S
                    order = new byte[]{(byte) 0xE3,0X10};
                    break;
                case 0://V
                    order = new byte[]{(byte) 0xE3,0X20};
                    break;
            }
        }else{
            switch (funIndex){
                case 2://A
                    order = new byte[]{(byte) 0xE3,0X00};
                    break;
                case 1://S
                    order = new byte[]{(byte) 0xE3,0X10};
                    break;
                case 0://U
                    order = new byte[]{(byte) 0xE3,0X30};
                    break;
            }
        }
        if(openLog)
            Toast.makeText(this, BleUtil.dec_hex(order),Toast.LENGTH_LONG).show();
        return order;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //        log.e("按键值："+keyCode);
        View rootview = getWindow().getDecorView();
        View currentView= rootview.findFocus();
        //TAG为当前Activity名称
        if(currentView!=null)
            log.e("当前焦点所在View："+currentView.toString());
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:
                if(Fragment_first!=null)
                    Fragment_first.moveCursor(-1);
                break;
            case Right:
                if(Fragment_first!=null)
                    Fragment_first.moveCursor(1);
                break;
            case Back:
                if(isStart){
                    isStartAlert(Res2String(R.string.harmonics));
                }
                break;
            case Power:


                break;

        }
        if(currentView instanceof RightModeView){
            //          return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
//        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:
                if(Fragment_first!=null && Fragment_first.isAdded())
                    Fragment_first.moveCursor(-1);
                break;
            case Right:
                if(Fragment_first!=null && Fragment_first.isAdded())
                    Fragment_first.moveCursor(1);
                break;
        }
    }
}
