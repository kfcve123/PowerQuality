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
import com.cem.powerqualityanalyser.AppConfig.MeterTypeValue;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.DataModel;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


//电压电流频率
public class VoltsAmpsHertzActivity extends BaseActivity implements BaseFragmentTrend.OnWirAndRightIndexCallBack {

    private MeterTypeValue meterTypeValue = MeterTypeValue.VoltsAmpsHertz;
    private List<DataModel> dataModels = new ArrayList<>();

    private int popwindowsIndex=0;
    private int wir_right_index = 0;
    private VoltsAmpsHertzMeter Fragment_first;
    private VoltsAmpsHertzTrend Fragment_Second;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.voltsampshertz_icon);
        setTitle("");
        setViewShow(1);
        setBottom3TextSize(18);
        setScreen(this);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return sendOrderData(config.getSet_Wir(),wir_right_index);
    }


    private BaseBottomAdapterObj baseBottomAdapterObj = null;
    public void updateWirData(int wir_index, int wir_right_index){
        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://2V
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://2A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                    case 2://L1
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});
                        break;
                    case 3://N
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){//切换右边选项
                    case 0://U
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                    case 2://L1
                    case 3://L2
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});
                        break;
                    case 4://N
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;

                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3U
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 2://3A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});
                        break;
                    case 6://N
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;
                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3U
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 2://3A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;

                }
                break;
            case 0://3QWYE
                switch (wir_right_index){
                    case 0://4V
                    case 1://3U
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 2://4A
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A≃)","PEAK-(A≃)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD(%f)","THD(%r)","PST","PLT","FHL","FK"});
                        break;
                    case 6://N
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;

                }
                break;
        }
       updateBottomData(baseBottomAdapterObj,2);
    }

    private void setViewShow(int index){
        if (index==0){
            if (null == Fragment_Second) {
                Fragment_Second = new VoltsAmpsHertzTrend();
            }
            showFragment(Fragment_Second,Res2String(R.string.Trend));
            updateWirData(wir_index,wir_right_index);
        }else {
            if (null == Fragment_first) {
                Fragment_first = new VoltsAmpsHertzMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(0,null),2);
            Fragment_first.setOnWirAndRightIndexCallBack(this);
            showFragment(Fragment_first,Res2String(R.string.Meter));
        }
    }

    private void showFragment(Fragment  fragment,String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {

        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.Trend)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2,null));
//        data.add(new BaseBottomAdapterObj(2,Res2Stringarr(R.array.VrmsKW_array)[0],Res2Stringarr(R.array.VrmsKW_array)));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        popwindowsIndex=positio;
        switch (obj.getId()){
            case 2:
                if(null!=Fragment_Second){
                    Fragment_Second.setVoltsModeIndex(wir_index,wir_right_index,positio);
                }
                break;
        }
    }

    protected void updateBottomData(BaseBottomAdapterObj obj,int position){
        if(bottomData!=null && bottomData.size()>position + 1) {
            bottomData.remove(position);
            bottomData.add(position, obj);
            updateBottomView(bottomData.get(position),position);
        }
    }

    private byte[] cmd;
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0://TREND切换
                setViewShow(0);
 //               updateBottomData(obj,2);
 //               updateBottomView(new BaseBottomAdapterObj(2,Res2Stringarr(R.array.VrmsKW_array)[0],Res2Stringarr(R.array.VrmsKW_array)),2);
                break;
            case 1:
                setViewShow(1);
 //               updateBottomView(new BaseBottomAdapterObj(2,null),2);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4://HOLD
                isHold = obj.getSwitchindex() == 0 ? true : false;
                break;
        }

    }

    @Override
    public void onDataReceived(byte[] bytes) {
 //       log.e("----------" + BleUtil.dec_hex(bytes));
    }

    //对象列表
    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if(!isStart)
            isStart = true;
        if(modelAllData!=null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E0_Voltage_and_current) {
 //           dissLoading();
            if (!isHold) {
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setShowMeterData(modelAllData);
                } else {
                    //筛选出选中的选项的数据转成对象传入曲线
                    if (Fragment_Second != null && Fragment_Second.isAdded())
                        Fragment_Second.setShowMeterData(modelAllData, popwindowsIndex);
                }
            }
        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        refeshHeadColor();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View rootview = getWindow().getDecorView();
        View currentView= rootview.findFocus();
        //TAG为当前Activity名称
        if(currentView!=null)
            log.e("当前焦点所在View："+currentView.toString());
        else
            log.e("当前焦点所在View："+currentView);

        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:
                if(Fragment_Second!=null && Fragment_Second.isAdded())
                    Fragment_Second.moveCursor(-1);
                else if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setFocusOnLeft();
                }
                break;
            case Right:
                if(Fragment_Second!=null && Fragment_Second.isAdded())
                    Fragment_Second.moveCursor(1);
                else if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setFocusOnRight();
                }
                break;
            case Menu:
            case Back:
                if(isStart){
                    isStartAlert(Res2String(R.string.voltsamps));
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


    private void posskey(int keyCode){
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
                if(Fragment_Second!=null)
                    Fragment_Second.moveCursor(-1);
                break;
            case Right:
                if(Fragment_Second!=null)
                    Fragment_Second.moveCursor(1);
                break;

        }

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
    public void returnWirAndRight(int wir, int right) {
        this.wir_right_index = right;
        if(serialHelper!=null) {
            if(null!=Fragment_Second){
                Fragment_Second.setVoltsModeIndex(wir_index,wir_right_index,0);
 //               updateWirData(wir_index,wir_right_index);
            }
            serialHelper.sendData(sendOrderData(wir, right));
        }
    }


    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData(int wir,int right){
        byte[] order = new byte[0];
        switch (wir){
            case 0://3QWYE
                switch (right){
                    case 0://4V
                        order = Commad.E0_3_WYE_4V;
                        break;
                    case 1://3U
                        order = Commad.E0_3_WYE_3U;
                        break;
                    case 2://4A
                        order = Commad.E0_3_WYE_4A;
                        break;
                    case 3://L1
                        order = Commad.E0_3_WYE_L1;
                        break;
                    case 4://L2
                        order = Commad.E0_3_WYE_L2;
                        break;
                    case 5://L3
                        order = Commad.E0_3_WYE_L3;
                        break;
                    case 6://N
                        order = Commad.E0_3_WYE_N;
                        break;

                }
                break;
            case 1://3QOPEN LEG
                switch (right){
                    case 0://3V
                        order = Commad.E0_3_OPEN_3V;
                        break;
                    case 1://3U
                        order = Commad.E0_3_OPEN_3U;
                        break;
                    case 2://3A
                        order = Commad.E0_3_OPEN_3A;
                        break;

                }
                break;
            case 2://3QIT
                switch (right){
                    case 0://3V
                        order = Commad.E0_3_IT_3V;
                        break;
                    case 1://3U
                        order = Commad.E0_3_IT_3U;
                        break;
                    case 2://3A
                        order = Commad.E0_3_IT_3A;
                        break;

                }
                break;
            case 5://3QHIGH LEG
                switch (right){
                    case 0:
                        order = Commad.E0_3_HIGH_3V;
                        break;

                    case 1:
                        order = Commad.E0_3_HIGH_3U;
                        break;

                    case 2:
                        order = Commad.E0_3_HIGH_3A;
                        break;

                    case 3:
                        order = Commad.E0_3_HIGH_L1;
                        break;
                    case 4:
                        order = Commad.E0_3_HIGH_L2;
                        break;
                    case 5:
                        order = Commad.E0_3_HIGH_L3;
                        break;
                    case 6:
                        order = Commad.E0_3_HIGH_N;
                        break;
                }
                break;
            case 4://3QDELTA
                switch (right){
                    case 0://3V
                        order = Commad.E0_3_DELTA_3V;
                        break;
                    case 1://3U
                        order = Commad.E0_3_DELTA_3U;
                        break;
                    case 2://3A
                        order = Commad.E0_3_DELTA_3A;
                        break;

                }
                break;
            case 3://2-ELEMENT
                switch (right){
                    case 0://3V
                        order = Commad.E0_2_ELEMENT_3V;
                        break;
                    case 1://3U
                        order = Commad.E0_2_ELEMENT_3U;
                        break;
                    case 2://3A
                        order = Commad.E0_2_ELEMENT_3A;
                        break;

                }
                break;
            case 6://2½-ELEMENT
                switch (right){
                    case 0:
                        order = Commad.E0_21_ELEMENT_3A;

                        break;
                    case 1:
                        order = Commad.E0_21_ELEMENT_3V;

                        break;
                    case 2:
                        order = Commad.E0_21_ELEMENT_3U;

                        break;
                    case 3:
                        order = Commad.E0_21_ELEMENT_L1;

                        break;
                    case 4:
                        order = Commad.E0_21_ELEMENT_L2;

                        break;
                    case 5:
                        order = Commad.E0_21_ELEMENT_L3;

                        break;
                    case 6:
                        order = Commad.E0_21_ELEMENT_N;

                        break;
                }
                break;
            case 7://1Q SPLIT PHASE
                switch (right){
                    case 0:
                        order = Commad.E0_1_SPLIT_3V;
                        break;
                    case 1:
                        order = Commad.E0_1_SPLIT_3A;
                        break;
                    case 2:
                        order = Commad.E0_1_SPLIT_L1;
                        break;
                    case 3:
                        order = Commad.E0_1_SPLIT_L2;
                        break;
                    case 4:
                        order = Commad.E0_1_SPLIT_N;
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (right){
                    case 0://3U
                        order = Commad.E0_1_IT_U;
                        break;
                    case 1://3A
                        order = Commad.E0_1_IT_A;
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (right){
                    case 1:
                        order = Commad.E0_1_NEUTRAL_2A;
                        break;
                    case 0:
                        order = Commad.E0_1_NEUTRAL_2V;
                        break;
                    case 2:
                        order = Commad.E0_1_NEUTRAL_L1;
                        break;
                    case 3:
                        order = Commad.E0_1_NEUTRAL_N;
                        break;
                }
                break;
        }
        if(openLog)
            Toast.makeText(this, BleUtil.dec_hex(order),Toast.LENGTH_SHORT).show();
        return order;
    }

}
