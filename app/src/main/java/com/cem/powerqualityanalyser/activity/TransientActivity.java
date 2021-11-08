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
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//瞬变 测试2
public class TransientActivity extends BaseActivity {

    private TransientMeter Fragment_Second;
    private TransientSet Fragment_First;
    private TransientTrend Fragment_Third;

    private int currentFragmentIndex = 0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int firstPopIndex = 0;
    private int secondPopIndex = -1;//第二个PopWindow的选中角标，例如L1 L2 L3 N
    private boolean cursorEnable;//开启光标显示
    private boolean cursorZoom;//切换到有光标模式
    private boolean isSetMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.transients_icon);
        setTitle("");
        showLoading();
        setViewShow(0);
        setBottom1TextSize(18);
        setBottom4TextSize(18);
        dissLoading(1500l);
    }

    @Override
    public byte[] getMode() {
        return new byte[]{(byte) 0xE7, 0x00};
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data = new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0, null));
        data.add(new BaseBottomAdapterObj(1, null));
        data.add(new BaseBottomAdapterObj(2, null, Res2String(R.string.trigger), Res2String(R.string.time)));
        data.add(new BaseBottomAdapterObj(3, Res2String(R.string.defaul)));
        data.add(new BaseBottomAdapterObj(4, Res2String(R.string.Start)));
        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int position) {
        switch (obj.getId()) {
            case 0:
                if (firstPopIndex != position) {
                    firstPopIndex = position;
                    Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                    if(cursorZoom) {
                        if (firstPopIndex == 2) {//F
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1_array),true), 3,true);
                        } else {//V  A
                            switch (wir_index) {
                                case 0://3QWYE
                                case 5://3QHIGH LEG
                                case 6://2½-ELEMENT
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
                                    break;
                                case 1://3QOPEN LEG
                                case 2://3QIT
                                case 3://2-ELEMENT
                                case 4://3QDELTA
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3_array)), 3);
                                    break;
                                case 7://1Q SPLIT PHASE
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2n_array)), 3);
                                    break;
                                case 8://1Q IT NO NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1_array)), 3);
                                    break;
                                case 9://1Q +NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1n_array)), 3);
                                    break;
                            }
                        }
                    }
                }
                break;

            case 3:
                if (secondPopIndex != position) {
                    secondPopIndex = position;
                    Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                }
                break;
        }
    }


    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()) {
            case 0:

                break;
            case 1:
                if (currentFragmentIndex == 2) {
                    cursorZoom = !cursorZoom;
                    if(cursorZoom){
                        updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.back)),1);
                        updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Cursor),Res2String(R.string.On),Res2String(R.string.Off)),2);
                        if(firstPopIndex == 2){//F
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1_array)), 3);
                        }else {//V  A
                            switch (wir_index) {
                                case 0://3QWYE
                                case 5://3QHIGH LEG
                                case 6://2½-ELEMENT
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
                                    break;
                                case 1://3QOPEN LEG
                                case 2://3QIT
                                case 3://2-ELEMENT
                                case 4://3QDELTA
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3_array)), 3);
                                    break;
                                case 7://1Q SPLIT PHASE
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2n_array)), 3);
                                    break;
                                case 8://1Q IT NO NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1_array)), 3);
                                    break;
                                case 9://1Q +NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1n_array)), 3);
                                    break;
                            }
                        }
                    }else{
                        updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor_zoom)),1);
                        updateBottomView(new BaseBottomAdapterObj(2,null,Res2String(R.string.Meter),Res2String(R.string.Trend)),2);
                        updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Event)),3);
                        secondPopIndex = -1;
                        Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                        cursorEnable = false;
                        if (Fragment_Third != null)
                            Fragment_Third.showCursor(false);
                    }
                }
                break;
            case 2:
                if (isSetMode){
                    showLeftRight(obj.getSwitchindex());
                }else{
                    if (currentFragmentIndex == 2 && cursorZoom) {
                        cursorEnable = !cursorEnable;
                        if (Fragment_Third != null)
                            Fragment_Third.showCursor(cursorEnable);
                        if (cursorEnable) {
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                            Fragment_Third.openCursorTopShow(wir_index, firstPopIndex, secondPopIndex);
                        } else {
                            switch (wir_index){
                                case 0://3QWYE
                                case 5://3QHIGH LEG
                                case 6://2½-ELEMENT
                                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1l2l3n_array)),3);
                                    break;
                                case 1://3QOPEN LEG
                                case 2://3QIT
                                case 3://2-ELEMENT
                                case 4://3QDELTA
                                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1l2l3_array)),3);
                                    break;
                                case 7://1Q SPLIT PHASE
                                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1l2n_array)),3);
                                    break;
                                case 8://1Q IT NO NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1_array)),3);
                                    break;
                                case 9://1Q +NEUTRAL
                                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1n_array)),3);
                                    break;
                            }
                            Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                        }

                    } else
                        setViewShow(obj.getSwitchindex() + 1);
                }
                break;
            case 3:
                if (currentFragmentIndex == 0) {
                    AppConfig.getInstance().setTransientSet_Default(true);
                    if (Fragment_First != null)
                        Fragment_First.resetSet();
                    updateBottomView(new BaseBottomAdapterObj(2, null, Res2String(R.string.trigger), Res2String(R.string.time)),2);
                }
                break;
            case 4:
                if (currentFragmentIndex == 0) {
                    AppConfig.getInstance().setTransientSet_Default(false);
                    if (Fragment_First != null)
                        Fragment_First.saveSetting();
                    setViewShow(2);
                } else {
                    isHold = obj.getSwitchindex() == 0 ? true : false;
                }

                break;
        }
    }

    private void showLeftRight(int index) {
        if (null == Fragment_First) {
            Fragment_First = new TransientSet();
        }
        if (index == 1) {
            Fragment_First.stopRight();
        } else {
            Fragment_First.stopLeft();

        }
    }

    private void setViewShow(int index) {
        currentFragmentIndex = index;
        if (index == 1) {
            isSetMode = false;
            if (null == Fragment_Second) {
                Fragment_Second = new TransientMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(0,null),0);
            updateBottomView(new BaseBottomAdapterObj(1,null),1);
            showFragment(Fragment_Second, Res2String(R.string.Meter));
        } else if (index == 0) {
            isSetMode = true;
            if (null == Fragment_First) {
                Fragment_First = new TransientSet();
            }
            showFragment(Fragment_First, Res2String(R.string.set));
        } else if(index == 2){
            isSetMode = false;
            if(null == Fragment_Third){
                Fragment_Third = new TransientTrend();
            }
            updateBottomView(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.transient_avf_array)[firstPopIndex], Res2Stringarr(R.array.transient_avf_array)),0,true);
            updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor_zoom)),1);
            updateBottomView(new BaseBottomAdapterObj(2,null,Res2String(R.string.Meter),Res2String(R.string.Trend)),2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Event)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
            showFragment(Fragment_Third,Res2String(R.string.Trend));

            Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);

        }
    }

    private void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    public void onDataReceived(byte[] bytes) {
 //       log.e("------" + BleUtil.dec_hex(bytes));
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
    public void onDataReceivedModel(ModelAllData modelAllData) {
        if (modelAllData != null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E7_Transient) {
 //           dissLoading();
            if(!isStart)
                isStart = true;
            if (!isHold) {
                List<ModelLineData> dataList = modelAllData.getModelLineData();
                if (dataList != null) {
                    if (Fragment_Third != null && Fragment_Third.isAdded()) {
                        Fragment_Third.setShowMeterData(modelAllData, wir_index, firstPopIndex, secondPopIndex, cursorEnable);
                    } else {
                        if (Fragment_Second != null && Fragment_Second.isAdded())
                            Fragment_Second.setShowMeterData(modelAllData);
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    View rootview = getWindow().getDecorView();
        View currentView= rootview.findFocus();
        //TAG为当前Activity名称
        if(currentView!=null)
            log.e("当前焦点所在View："+currentView.toString());*/
    }
    private int zoomSize = 1; //放大比例

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View rootview = getWindow().getDecorView();
        View currentView= rootview.findFocus();
        //TAG为当前Activity名称
//        if(currentView!=null)
//            log.e("切换前当前焦点所在View："+currentView.toString());
//        possKeyDown(keyCode);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
//        log.e("========" + key.toString());
        switch (key) {
            case Up:
                if(Fragment_First!=null && Fragment_First.isAdded()){
        //           Fragment_First.upKey();
                    if(Fragment_First.forbidMoveUp())
                        return true;
                } else if(null!=Fragment_Third && Fragment_Third.isAdded()){
                    if(cursorEnable) {
                        if (zoomSize < 3) {
                            zoomSize++;
                        }
                        Fragment_Third.zoomScale(zoomSize);
                    }else{

                    }
                }
                break;
            case Down:
                if (Fragment_First != null && Fragment_First.isAdded()) {
                    Fragment_First.downKey();
                    if(Fragment_First.forbidMoveDown())
                        return true;
                } else if(null!=Fragment_Third && Fragment_Third.isAdded()) {
                    if(cursorEnable) {
                        if (zoomSize > 1) {
                            zoomSize--;
                        }
                        Fragment_Third.zoomScale(zoomSize);
                    }else {

                    }
                }
                break;

            case Left:
                if (Fragment_First != null && Fragment_First.isAdded()){
                    Fragment_First.moveCursor(-1);
                    if(!Fragment_First.forbidMoveRight()){
                        return true;
                    }

                } else if(Fragment_Third!=null && Fragment_Third.isAdded()){
                    Fragment_Third.moveCursor(-1);
                }

                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded()){
                    Fragment_First.moveCursor(1);
                    if(Fragment_First.forbidMoveRight() || Fragment_First.forbidTimeMoveRight()){
                        return true;
                    }
                } else if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.moveCursor(1);
                }

                break;
            case Back:
            case Menu:
                if(currentFragmentIndex!=0) {
                    setViewShow(0);
                    updateBottomView(new BaseBottomAdapterObj(0,null),0);
                    updateBottomView(new BaseBottomAdapterObj(1,null),1);
                    updateBottomView(new BaseBottomAdapterObj(2, null, Res2String(R.string.trigger), Res2String(R.string.time)),2);
                    updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.defaults)),3);
                    updateBottomView(new BaseBottomAdapterObj(4,Res2String(R.string.Start)),4);
                    return true;
                }

                break;
        }

        return super.onKeyDown(keyCode, event);
    }


    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        log.e("========" + key.toString());
        switch (key) {
            case Up:
                /*if(Fragment_First!=null && Fragment_First.isAdded())
                    Fragment_First.upKey();
                else if(Fragment_Second!=null && Fragment_Second.isAdded()){

                }*/
                break;
            case Down:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.downKey();
                break;
            case Left:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.moveCursor(-1);

                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.moveCursor(1);
                break;
        }
    }


    @Override
    public void onDataReceivedList(List list) {

    }
}
