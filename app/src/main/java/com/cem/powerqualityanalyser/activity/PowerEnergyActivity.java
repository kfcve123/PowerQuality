package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.RightModeView;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


//功率与电能
public class PowerEnergyActivity extends BaseActivity implements BaseFragmentTrend.OnWirAndRightIndexCallBack{
    private int popwindowsIndex=0;
    private int trendPopIndex = 0;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private PowerEnergyTrend Fragment_first;
    private PowerEnergyMeter Fragment_Second;
    private PowerMeter Fragment_three;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.powerenergy_icon);
        setTitle("");
        setViewShow(0,1);
        setScreen(this);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return sendOrderData(config.getSet_Wir(),0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (true){
            byte meterDataCount=18*3;
//            byte[] bytes=new byte[meterDataCount*4];
            byte[] bytes=new byte[266];
            bytes[0]=meterDataCount;

        }
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    /**
     * 初始化底部按钮数据
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {

        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.power_energy_array)[0], Res2Stringarr(R.array.power_energy_array)));
        data.add(new BaseBottomAdapterObj(1,null,Res2String(R.string.Trend),Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2,null));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)));
        return  data;
    }

    /**
     * 弹窗菜单点击事件
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

        switch (obj.getId()) {
            case 0:
                popwindowsIndex = positio;
                setViewShow(positio, trendIndex);
                serialHelper.sendData(sendOrderData(config.getSet_Wir(), 0));
                break;
            case 2://切换曲线值显示
                trendPopIndex = positio;
                if (currentFramentIndex == 0) {
                    if (popwindowsIndex == 0) {
                        if (null != Fragment_first)
                            Fragment_first.setEnergyModeIndex(wir_index, eneryRightIndex, positio);
                    } else if (popwindowsIndex == 1) {
                        if (null != Fragment_first)
                            Fragment_first.setPowerModeIndex(wir_index, powerRightIndex, positio);
                    }
                }
                break;
        }
    }

    private int trendIndex = 1;
    private byte[] cmd;
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
                trendIndex = obj.getSwitchindex();
                setViewShow(popwindowsIndex,obj.getSwitchindex());
                serialHelper.sendData(sendOrderData(config.getSet_Wir(),0));

                break;
            case 2:
                if(currentFramentIndex == 1) {
                    if (obj.getSwitchindex() == 0) {
                        cmd = sendOrderData(config.getSet_Wir(), 0);
                    } else {
                        cmd = stopOrder;
                    }
                    serialHelper.sendData(cmd);
                    if (openLog)
                        Toast.makeText(this, BleUtil.dec_hex(cmd), Toast.LENGTH_LONG).show();

                }
                break;
            case 3:
                if(currentFramentIndex == 2){
                    if(Fragment_Second!=null) {
                        Fragment_Second.startRecord();
                        updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.energy_reset)),3);
                        updateBottomView(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)),4);
                    }
                }
                break;
            case 4:
                if(currentFramentIndex == 0) {
                    isTrendHold = obj.getSwitchindex() == 0 ? true : false;
                    isPowerHold = false;
                    isEnergyHold = false;
                }else if(currentFramentIndex == 1){
                    isPowerHold = obj.getSwitchindex() == 0 ? true : false;
                    isTrendHold = false;
                    isEnergyHold = false;
                }else if(currentFramentIndex == 2){
                    isEnergyHold = obj.getSwitchindex() == 0 ? true : false;
                    isPowerHold = false;
                    isTrendHold = false;
                    if(Fragment_Second!=null) {
                        Fragment_Second.setPause(isEnergyHold);
                    }
                }

                break;
        }
    }
    private int currentFramentIndex;


    private BaseBottomAdapterObj baseBottomAdapterObj = null;
    public void updateWirData(int wir_index,int popIndex,int powerRightIndex,int eneryRightIndex){
        switch (popIndex) {
            case 0:
                switch (wir_index) {
                case 9://1Q +NEUTRAL
                    switch (powerRightIndex) {//切换右边选项
                        case 0:
                        case 1:
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                            break;
                        case 2:
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "cosØ", new String[]{"cosØ"});

                            break;
                    }
                    break;
                case 7://1Q SPLIT PHASE
                    switch (powerRightIndex) {//切换右边选项
                        case 0:
                        case 1:
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                            break;
                        case 2://∑
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF"});
                            break;

                    }
                    break;
                case 6:// 2½-ELEMENT
                case 2://3QIT

                    switch (powerRightIndex) {//切换右边选项
                        case 0://3L
                        case 1://L1
                        case 2://L2
                        case 3://L3
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                            break;
                        case 4://∑
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF"});
                            break;
                    }
                    break;
                case 8://1Q IT NO NEUTRAL
                case 5://3QHIGH LEG
                case 4://3QDELTA
                case 3://2-ELEMENT
                case 1://3QOPEN LEG
                    baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                    break;
                case 0://3QWYE
                    log.e("-------------" + eneryRightIndex);
                    switch (powerRightIndex) {
                        case 0://3L
                        case 1://L1
                        case 2://L2
                        case 3://L3
                        case 5://∑
                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                            break;
                        case 4://N

                            baseBottomAdapterObj = new BaseBottomAdapterObj(2, "cosØ", new String[]{"cosØ"});
                            break;

                    }
                    break;
            }
                break;
            case 1:
                switch (wir_index) {
                    case 9://1Q +NEUTRAL
                        switch (eneryRightIndex) {//切换右边选项
                            case 0:
                            case 1://∑
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                                break;
                        }
                        break;
                    case 7://1Q SPLIT PHASE
                        switch (eneryRightIndex) {//切换右边选项
                            case 0:
                            case 1:
                            case 2://∑
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kW", "kVA", "kvar", "kVAharm", "kVAunb", "kWfund", "kVAfund", "Wfund", "PF", "cosØ"});
                                break;

                        }
                        break;

                    case 8://1Q IT NO NEUTRAL
                    case 5://3QHIGH LEG
                    case 4://3QDELTA
                    case 3://2-ELEMENT
                    case 1://3QOPEN LEG
                        baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kW", new String[]{"kWh", "kVAh", "kvarh", "kwh forw", "kWh rev"});
                        break;
                    case 0://3QWYE
                    case 6:// 2½-ELEMENT
                    case 2://3QIT
                        log.e("-------------" + eneryRightIndex);
                        switch (eneryRightIndex) {
                            case 0://3L
                            case 1://L1
                            case 2://L2
                            case 3://L3
                            case 4://∑
                                baseBottomAdapterObj = new BaseBottomAdapterObj(2, "kWh", new String[]{"kWh", "kVAh", "kvarh", "kwh forw", "kWh rev"});
                                break;
                        }
                        break;
                }
                break;
        }
        updateBottomView(baseBottomAdapterObj,2,true);
    }

    private void setViewShow(int popIndew,int index) {
        if (popIndew == 0) {//PowerEnergyMeter 的情况
            if (index == 0) {
                if (null == Fragment_first) {
                    Fragment_first = new PowerEnergyTrend();
                }
                currentFramentIndex = 0;
                updateBottomView(new BaseBottomAdapterObj(3,null),3);
                updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
                updateWirData(wir_index,popIndew,powerRightIndex,eneryRightIndex);
                showFragment(Fragment_first, Res2String(R.string.Trend));
            } else {
                if (null == Fragment_three) {
                    Fragment_three = new PowerMeter();
                    Fragment_three.setOnWirAndRightIndexCallBack(this);
                }
                currentFramentIndex = 1;
                updateBottomView(new BaseBottomAdapterObj(2,null),2);
                updateBottomView(new BaseBottomAdapterObj(3,null),3);
                updateBottomView(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)),4);
                showFragment(Fragment_three, Res2String(R.string.power));
            }
        } else {//PowerMeter 的情况
            if (index == 0) {
                if (null == Fragment_first) {
                    Fragment_first = new PowerEnergyTrend();
                }
                currentFramentIndex = 0;
                updateBottomView(new BaseBottomAdapterObj(3,null),3);
                updateBottomView(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)),4);
                updateWirData(wir_index,popIndew,powerRightIndex,eneryRightIndex);
                showFragment(Fragment_first, Res2String(R.string.Trend));
            } else {
                if (null == Fragment_Second) {
                    Fragment_Second = new PowerEnergyMeter();
                    Fragment_Second.setOnWirAndRightIndexCallBack(this);
                }
                currentFramentIndex = 2;
                updateBottomView(new BaseBottomAdapterObj(2,null),2);
                updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Start)),3);
                updateBottomView(new BaseBottomAdapterObj(4,null),4);
                showFragment(Fragment_Second, Res2String(R.string.energy));
            }
        }
    }

    private void showFragment(Fragment fragment, String tag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View rootview = getWindow().getDecorView();
        View currentView= rootview.findFocus();
        //TAG为当前Activity名称
        if(currentView!=null)
            log.e("当前焦点所在View："+currentView.toString());
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
//        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:

                break;
            case Right:

                break;
            case Menu:
            case Back:
                if(isStart){
                    isStartAlert(Res2String(R.string.powerenergy));
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
        log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:

                break;
            case Right:

                break;
        }
    }

    @Override
    public void onDataReceived(byte[] bytes) {
 //       log.e("------------------------" +BleUtil.dec_hex(bytes));
    }

    private boolean isPowerHold;
    private boolean isEnergyHold;
    private boolean isTrendHold;

    //对象列表
    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if(!isStart)
            isStart = true;
        if(modelAllData!=null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E2_Power_electric_energy) {
//            dissLoading();
            List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_three != null && Fragment_three.isAdded()) {
                        if(!isPowerHold)
                            Fragment_three.setShowMeterData(modelAllData);
                    } else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                        if(!isEnergyHold)
                            Fragment_Second.setShowMeterData(modelAllData);
                    } else if (Fragment_first != null && Fragment_first.isAdded()) {
                        if(!isTrendHold)
                            Fragment_first.setShowMeterData(modelAllData, trendPopIndex);

                    }
                }

        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }


    protected void updateBottomData(BaseBottomAdapterObj obj,int position){
        if(bottomData!=null && bottomData.size()>position + 1) {
            bottomData.remove(position);
            bottomData.add(position, obj);
            updateBottomView(bottomData.get(position),position);
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
        if(serialHelper!=null) {
            if(popwindowsIndex == 1) {
                eneryRightIndex = right;
            } else if(popwindowsIndex == 0){
                powerRightIndex = right;
            }

            serialHelper.sendData(sendOrderData(wir, right));

        }
    }
    private int powerRightIndex;
    private int eneryRightIndex;

    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData(int wir, int right) {
        byte[] order = new byte[0];
        if (popwindowsIndex == 1) {//Energy
            switch (wir) {
                case 0://3QWYE
                    switch (right) {
                        case 0:
                            order = Commad.E2_3_WYE_E_3L;
                            break;
                        case 1:
                            order = Commad.E2_3_WYE_E_L1;
                            break;
                        case 2:
                            order = Commad.E2_3_WYE_E_L2;
                            break;
                        case 3:
                            order = Commad.E2_3_WYE_E_L3;
                            break;
                        case 4:
                            order = Commad.E2_3_IWYE_E_S;
                            break;

                    }
                    break;
                case 1://3QOPEN LEG
                    order = Commad.E2_1_IT_E;
                    break;
                case 2://3QIT
                    switch (right) {
                        case 0:
                            order = Commad.E2_3_IT_E_3L;
                            break;
                        case 1:
                            order = Commad.E2_3_IT_E_L1;
                            break;
                        case 2:
                            order = Commad.E2_3_IT_E_L2;
                            break;
                        case 3:
                            order = Commad.E2_3_IT_E_L3;
                            break;
                        case 4:
                            order = Commad.E2_3_IT_E_S;
                            break;
                    }
                    break;
                case 3://3QHIGH LEG
                    order = Commad.E2_3_HIGH_E;
                    break;
                case 4://3QDELTA
                    order = Commad.E2_3_DELTA_E;
                    break;
                case 5://2-ELEMENT
                    order = Commad.E2_2_ELEMENT_E;
                    break;
                case 6://2½-ELEMENT
                    switch (right) {
                        case 0:
                            order = Commad.E2_21_ELEMENT_E_3L;
                            break;
                        case 1:
                            order = Commad.E2_21_ELEMENT_E_L1;
                            break;
                        case 2:
                            order = Commad.E2_21_ELEMENT_E_L2;
                            break;
                        case 3:
                            order = Commad.E2_21_ELEMENT_E_L3;
                            break;
                        case 4:
                            order = Commad.E2_21_ELEMENT_E_S;
                            break;
                    }
                    break;
                case 7://1Q SPLIT PHASE
                    switch (right) {
                        case 0:
                            order = Commad.E2_1_SPLIT_E_L1;
                            break;
                        case 1:
                            order = Commad.E2_1_SPLIT_E_L2;
                            break;
                        case 2:
                            order = Commad.E2_1_SPLIT_E_S;
                            break;
                    }
                    break;
                case 8://1Q IT NO NEUTRAL
                    order = Commad.E2_1_IT_E;
                    break;
                case 9://1Q +NEUTRAL
                    switch (right) {
                        case 0:
                            order = Commad.E2_1_NEUTRAL_E_L1;
                            break;
                        case 1:
                            order = Commad.E2_1_NEUTRAL_E_S;
                            break;
                    }
                    break;
            }
        } else if (popwindowsIndex == 0) {//Power
            switch (wir) {
                case 0://3QWYE
                    switch (right) {
                        case 0:
                        case 5:
                            order = Commad.E2_3_WYE_P_3L;
                            break;
                        case 1:
                            order = Commad.E2_3_WYE_P_L1;
                            break;
                        case 2:
                            order = Commad.E2_3_WYE_P_L2;
                            break;
                        case 3:
                            order = Commad.E2_3_WYE_P_L3;
                            break;
                        case 4:
                            order = Commad.E2_3_WYE_P_N;
                            break;

                    }
                    break;
                case 1://3QOPEN LEG
                    order = Commad.E2_1_IT_P;
                    break;
                case 2://3QIT
                    switch (right) {
                        case 0:
                        case 4:
                            order = Commad.E2_3_IT_P_3L;
                            break;

                        case 1:
                            order = Commad.E2_3_IT_P_L1;
                            break;
                        case 2:
                            order = Commad.E2_3_IT_P_L2;
                            break;
                        case 3:
                            order = Commad.E2_3_IT_P_L3;
                            break;
                    }
                    break;
                case 3://3QHIGH LEG
                    order = Commad.E2_3_HIGH_P;
                    break;
                case 4://3QDELTA
                    order = Commad.E2_3_DELTA_P;
                    break;
                case 5://2-ELEMENT
                    order = Commad.E2_2_ELEMENT_P;
                    break;
                case 6://2½-ELEMENT
                    switch (right) {
                        case 0:
                        case 4:
                            order = Commad.E2_21_ELEMENT_P_3L;
                            break;
                        case 1:
                            order = Commad.E2_21_ELEMENT_P_L1;
                            break;
                        case 2:
                            order = Commad.E2_21_ELEMENT_P_L2;
                            break;
                        case 3:
                            order = Commad.E2_21_ELEMENT_P_L3;
                            break;
                    }
                    break;
                case 7://1Q SPLIT PHASE
                    switch (right) {
                        case 0:
                        case 2:
                            order = Commad.E2_1_SPLIT_P_L1;
                            break;
                        case 1:
                            order = Commad.E2_1_SPLIT_P_L2;
                            break;

                    }
                    break;
                case 8://1Q IT NO NEUTRAL
                    order = Commad.E2_1_IT_P;
                    break;
                case 9://1Q +NEUTRAL
                    switch (right) {
                        case 0:
                        case 2:
                            order = Commad.E2_1_NEUTRAL_P_L1;
                            break;
                        case 1:
                            order = Commad.E2_1_NEUTRAL_P_S;
                            break;
                    }
                    break;
            }
        }
        if (openLog)
            Toast.makeText(this, BleUtil.dec_hex(order), Toast.LENGTH_SHORT).show();
        return order;
    }



}
