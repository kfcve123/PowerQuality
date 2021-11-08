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

import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.HintDialog;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//示波器
public class ScopeActivity extends BaseActivity implements BaseFragmentTrend.OnWirAndRightIndexCallBack {
    private ScopeVector Fragment_Second;
    private ScopeTrendTest Fragment_first;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int currentFragmentIndex = 0;
    private int funTypeIndex=0;
         

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.scope_icon);
        setTitle("");
        showLoading();
        setViewShow(1);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return new byte[]{(byte) 0xE1,0x00};
    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData(int funTypeIndex){
        byte[] order = new byte[0];
        if(wir_index == 0 ||wir_index == 5 ||wir_index == 6||wir_index == 7||wir_index == 9){
            switch (funTypeIndex){
                case 0://A
                    order = new byte[]{(byte) 0xE3,0X00};
                    break;
                case 1://SS
                    order = new byte[]{(byte) 0xE3,0X10};
                    break;
                case 2://V
                    order = new byte[]{(byte) 0xE3,0X20};
                    break;
            }
        }else{
            switch (funTypeIndex){
                case 0://A
                    order = new byte[]{(byte) 0xE3,0X00};
                    break;
                case 1://S
                    order = new byte[]{(byte) 0xE3,0X10};
                    break;
                case 2://U
                    order = new byte[]{(byte) 0xE3,0X30};
                    break;
            }
        }

        return order;
    }

    /**
     * 弹窗菜单点击事件
     * @param obj
     * @param positio
     */
    @Override
    public void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

        switch (obj.getId()) {
            case 0://切换 RMS ,THD ,CF
                funTypeIndex=positio;
                break;
        }
    }

    /**
     * 底部按钮点击事件
     * @param view
     * @param obj
     */
    private boolean cursorEnable = false;
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:

                break;
            case 1:
                cursorEnable = !cursorEnable;
                if (Fragment_first!=null) {
                    Fragment_first.showCursor(cursorEnable);
                }
                break;
            case  2:
                setViewShow(obj.getSwitchindex());
                break;
            case 3:
                if (Fragment_first!=null) {
                    Fragment_first.fitScreen();
                }
                break;
            case 4:
                isHold = obj.getSwitchindex() == 0 ? true : false;
                break;
        }
    }

    /**
     * 初始化底部按钮数据
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData(){
        List<BaseBottomAdapterObj> data=new ArrayList<>();

        data.add(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.scope_array)[0],Res2Stringarr(R.array.scope_array)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor),Res2String(R.string.On),Res2String(R.string.Off)));
        data.add(new BaseBottomAdapterObj(2,R.mipmap.triangle,R.mipmap.vectro_ico));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.Zoom),R.mipmap.up_down,AppConfig.getInstance().getMaxZoom()));
        data.add(new BaseBottomAdapterObj(4,null,Res2String(R.string.run),Res2String(R.string.Hold)));
        return  data;
    }

    private void setViewShow(int index) {
        currentFragmentIndex = index;
        if (index == 0) {
            if (null == Fragment_Second) {
                Fragment_Second = new ScopeVector();
            }
            updateBottomView(new BaseBottomAdapterObj(null),0);
            updateBottomView(new BaseBottomAdapterObj(null),1);
            updateBottomView(new BaseBottomAdapterObj(null),3);
 //           updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Trend)),3);
            showFragment(Fragment_Second, Res2String(R.string.Meter));
        } else {
            if (null == Fragment_first) {
                Fragment_first = new ScopeTrendTest();
                Fragment_first.showCursor(cursorEnable);
            }

            updateBottomView(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.scope_array)[0],Res2Stringarr(R.array.scope_array)),0);
            updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor),Res2String(R.string.On),Res2String(R.string.Off)),1);
            updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Zoom),R.mipmap.up_down,AppConfig.getInstance().getMaxZoom()),3);

            Fragment_first.setOnWirAndRightIndexCallBack(this);
            showFragment(Fragment_first, Res2String(R.string.Trend));
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        possKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }
    private int i  = 1;
    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
  //      log.e("========" + key.toString());
        switch (key) {
            case Up:
                if (Fragment_first != null) {
                    if (i < 3) {
                        i++;
                    }
                    Fragment_first.zoomScale(i);
                }
                break;
            case Down:
                if (Fragment_first != null) {
                    if (i > 1) {
                        i--;
                    }
                    Fragment_first.zoomScale(i);
                }
                break;
            case Left:
                if(Fragment_first!=null)
                    Fragment_first.moveCursor(-1);
                break;
            case Right:
                if(Fragment_first!=null)
                    Fragment_first.moveCursor(1);
                break;
        }
    }

    @Override
    public void onDataReceived(final byte[] bytes) {
 //       log.e("-----------" + BleUtil.dec_hex(bytes));
    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if(modelAllData!=null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E1_Oscilloscope) {
//            dissLoading();
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_first != null && Fragment_first.isAdded())
                        Fragment_first.setShowMeterData(modelAllData,wir_index,trendRightIndex,funTypeIndex,cursorEnable);
                    else {
                        //筛选出选中的选项的数据转成对象传入曲线
                        if (Fragment_Second != null && Fragment_Second.isAdded())
                            Fragment_Second.setShowMeterData(modelAllData, funTypeIndex);
                    }
                }
            }
        }
    }


    private ModelLineData selectModelLineData(ModelAllData modelAllData,int fun){
        if(modelAllData!=null && modelAllData.getModelLineData().size()>fun){
            return modelAllData.getModelLineData().get(fun);
        }
        return modelAllData.getModelLineData().get(0);
    }

    @Override
    public void onDataReceivedList(List list) {

    }

    private int trendRightIndex =0;

    @Override
    public void returnWirAndRight(int wir, int right) {
        this.trendRightIndex = right;
        this.wir_index = wir;
        serialHelper.sendData(sendMeterOrderData(wir,right));
    }

    private byte[] sendMeterOrderData(int wir,int wir_right) {
        byte[] cmd = new byte[2];
        switch (wir) {
            case 0://三相5线
                switch (wir_right) {
                    case 0:
                    case 1:
                        cmd = new byte[]{(byte) 0xE1, 0x00};
                        break;
                    case 2:
                        cmd = new byte[]{(byte) 0xE1, 0x01};
                        break;
                    case 3:
                        cmd = new byte[]{(byte) 0xE1, 0x10};
                        break;
                    case 4:
                        cmd = new byte[]{(byte) 0xE1, 0x20};
                        break;
                    case 5:
                        cmd = new byte[]{(byte) 0xE1, 0x30};
                        break;
                    case 6:
                        cmd = new byte[]{(byte) 0xE1, 0x40};
                        break;
                }
                break;
        }
        return cmd;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(serialHelper!=null){
//            serialHelper.closeSerialPort();
//            serialHelper = null;
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        refeshHeadColor();
    }
}
