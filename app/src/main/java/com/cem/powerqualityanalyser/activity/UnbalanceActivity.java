package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.meterobject.MeterUnbalancedObj;

import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;


import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//不平衡
public class UnbalanceActivity extends BaseActivity {
    private int popwindowsIndex = 0;
    private int secondPopIndex = -1;//第二个PopWindow的选中角标，例如L1 L2 L3 N

    private UnbalanceMeter Fragment_first;
    private UnbalanceVector Fragment_Second;
    private UnbalanceTrend Fragment_Third;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int currentFragmentIndex = 0;
    private boolean cursorEnable;//开启光标显示
    private int zoomSize = 1; //放大比例

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.unbalance_icon);
        setTitle("");
        showLoading();
        setViewShow(1);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return sendOrderData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (true) {
            byte meterDataCount = 18;
            byte[] bytes = new byte[meterDataCount * 4 + 6];
            bytes[0] = meterDataCount;

        }
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    /**
     * 初始化底部按钮数据
     *
     * @return
     */
    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data = new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0, null));
        data.add(new BaseBottomAdapterObj(1, Res2String(R.string.Meter)));
        data.add(new BaseBottomAdapterObj(2, Res2String(R.string.Trend)));
        data.add(new BaseBottomAdapterObj(3, null));
        data.add(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)));
        return data;
    }

    /**
     * 弹窗菜单点击事件
     *
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int position) {
        popwindowsIndex = position;
        switch (obj.getId()) {
            case 3://L1.L2.L3,N切换不发命令，筛选数据
                if (null != Fragment_Third) {
                    secondPopIndex = position;
                    Fragment_Third.updateTrendRightAndPopMode(wir_index, 0, secondPopIndex);
                    Fragment_Third.openCursorTopShow(wir_index, 0, secondPopIndex);
                    Fragment_Third.showCursor(cursorEnable);
                }
                break;
        }
    }

    /**
     * 底部按钮点击事件
     *
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()) {
            case 0:
                if (currentFragmentIndex == 2) {
                    cursorEnable = obj.getSwitchindex() == 0 ? true : false;
                    if (Fragment_Third != null)
                        Fragment_Third.showCursor(cursorEnable);
                }
                break;
            case 1:
                setViewShow(obj.getSwitchindex());
                break;
            case 2:
                setViewShow(2);
                break;
            case 3:

                break;
            case 4:
                isHold = obj.getSwitchindex() == 0 ? true : false;
                break;
        }

    }

    private void setViewShow(int index) {
        if (index == 1) {
            currentFragmentIndex = 1;
            if (null == Fragment_Second) {
                Fragment_Second = new UnbalanceVector();
            }
            updateBottomView(new BaseBottomAdapterObj(0, null), 0);
            updateBottomView(new BaseBottomAdapterObj(3, null), 3);
            updateBottomView(new BaseBottomAdapterObj(1, null, Res2String(R.string.Meter), Res2String(R.string.phasor)), 1);
            showFragment(Fragment_Second, Res2String(R.string.phasor));
        } else if (index == 0) {
            currentFragmentIndex = 0;
            if (null == Fragment_first) {
                Fragment_first = new UnbalanceMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(0, null), 0);
            updateBottomView(new BaseBottomAdapterObj(3, null), 3);
            updateBottomView(new BaseBottomAdapterObj(2, Res2String(R.string.Trend)), 2);
            showFragment(Fragment_first, Res2String(R.string.Meter));
        } else {
            currentFragmentIndex = 2;
            if (null == Fragment_Third)
                Fragment_Third = new UnbalanceTrend();
            updateBottomView(new BaseBottomAdapterObj(0, Res2String(R.string.Cursor), Res2String(R.string.On), Res2String(R.string.Off)), 0);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
            showFragment(Fragment_Third, Res2String(R.string.Trend));

        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }

    /**
     * 根据接线方式和右边选择栏更换命令传输数据
     *
     * @param wir
     * @param right
     * @return
     */
    private byte[] sendOrderData() {
        byte[] order = new byte[0];
        order = new byte[]{(byte) 0xE4, 0X00};
        return order;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serialHelper != null) {
            serialHelper.closeSerialPort();
            serialHelper = null;
        }
    }

    @Override
    public void onDataReceived(byte[] bytes) {
        //       log.e("-----" + BleUtil.dec_hex(bytes));
    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if (!isStart)
            isStart = true;
        if (modelAllData != null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E4_Unbalanced) {
            //           dissLoading();
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_first != null && Fragment_first.isAdded())
                        Fragment_first.setShowMeterData(modelAllData);
                    else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                        Fragment_Second.setShowMeterData(modelAllData);
                    } else if (null != Fragment_Third && Fragment_Third.isAdded()) {
                        Fragment_Third.setShowMeterData(modelAllData, 0);
                    }
                }
            }
        }
    }

    @Override
    public void onDataReceivedList(List list) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View rootview = getWindow().getDecorView();
        View currentView = rootview.findFocus();
        if (currentView != null)
            log.e("当前焦点所在View：" + currentView.toString());
        else
            log.e("当前焦点所在View：" + currentView);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Up:
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    if (currentView != null && currentView.isFocusable() && currentView instanceof RecyclerView) {
                        Fragment_first.leftUpScroll();
                    }
                } else if (null != Fragment_Third && Fragment_Third.isAdded()) {
                    if (cursorEnable) {
                        if (zoomSize < 3) {
                            zoomSize++;
                            AppConfig.getInstance().setMaxZoom(zoomSize);
                        }
                        Fragment_Third.zoomScale(zoomSize);
                        //updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                    } else {

                    }
                }
                break;
            case Down:
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    if (currentView != null && currentView.isFocusable() && currentView instanceof RecyclerView) {
                        Fragment_first.leftDownScroll();
                    }
                } else if (null != Fragment_Third && Fragment_Third.isAdded()) {
                    if (cursorEnable) {
                        if (zoomSize > 1) {
                            zoomSize--;
                            AppConfig.getInstance().setMaxZoom(zoomSize);
                        }
                        Fragment_Third.zoomScale(zoomSize);
                        //updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                    } else {

                    }
                }
                break;
            case Left:
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setFocusOnLeft();
                } else if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.moveCursor(-1);
                }
                break;
            case Right:
                if (Fragment_first != null && Fragment_first.isAdded()) {
                    Fragment_first.setFocusOnRight();
                } else if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.moveCursor(1);
                }
                break;
            case Menu:
            case Back:
                if (isStart) {
                    isStartAlert(Res2String(R.string.unbalance));
                }
                break;
            case Power:


                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
