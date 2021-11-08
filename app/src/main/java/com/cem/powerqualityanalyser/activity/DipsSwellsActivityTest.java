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
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;
    
//骤升骤降
public class DipsSwellsActivityTest extends BaseActivity {
//    private DipsSwellsSetting Fragment_Set;

    private DipsSwellsMeter Fragment_Third;
    private DipsSwellsSetTime Fragment_Second;
    private DipsSwellsSetTrigger Fragment_First;
    private DipsSwellsTrend Fragmet_Four;

    private int currentFragmentIndex = 0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int funTypeIndex=0;
    private int wir_right_index;
    private boolean cursorEnable;//开启光标显示
    private boolean cursorZoom;//切换到有光标模式
    private int lastRightPopPosition = 0;//第二个PopWindow的选中角标，例如L1 L2 L3 N
    private boolean isSetMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.dips_swells_icon);
        setTitle("");
        setViewShow(1);
        setBottom1TextSize(18);
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
        data.add(new BaseBottomAdapterObj(2, null, Res2String(R.string.trigger), Res2String(R.string.time)));
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


            case 3:


                break;
        }
    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 0:


                break;
            case 1:
                if (currentFragmentIndex == 3) {
                    cursorZoom = !cursorZoom;
                    if(cursorZoom){
                        funTypeIndex = 0;
                        updateBottomView(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.transient_avf_array)[funTypeIndex], Res2Stringarr(R.array.transient_avf_array)),0,true);
                        updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.back)),1);
                        updateBottomView(new BaseBottomAdapterObj(2,Res2String(R.string.Cursor),Res2String(R.string.On),Res2String(R.string.Off)),2);
                        updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg,Res2Stringarr(R.array.inrush_l1l2l3n_array)),3);

                    }else{
                        funTypeIndex = 0;//
                        updateBottomView(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.transient_av_array)[funTypeIndex], Res2Stringarr(R.array.transient_av_array)),0,true);
                        updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor_zoom)),1);
                        updateBottomView(new BaseBottomAdapterObj(2,null,Res2String(R.string.Meter),Res2String(R.string.Trend)),2);
                        updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Event)),3);
                        lastRightPopPosition = 0;

                        cursorEnable = false;
                        if (Fragmet_Four != null)
                            Fragmet_Four.showCursor(false);
                    }

                }
                break;
            case 2:
                if(isSetMode)
                    setViewShow(obj.getSwitchindex());
                else {
                    if (currentFragmentIndex == 3 && cursorZoom) {
                        cursorEnable = !cursorEnable;
                        if (Fragmet_Four != null)
                            Fragmet_Four.showCursor(cursorEnable);
                        if (cursorEnable) {
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
//                            Fragmet_Four.openCursorTopShow(wir_index, wir_right_index, funTypeIndex);
                        } else {
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
//                            Fragmet_Four.updateTrendRightAndPopMode(wir_index, lastRightPopPosition, funTypeIndex);
                        }

                    } else
                        setViewShow(obj.getSwitchindex() + 2);
                }
                break;
            case 3:
                if (currentFragmentIndex == 1) {
                    AppConfig.getInstance().setTransientSet_Default(true);
                    if (Fragment_First != null)
                        Fragment_First.resetSet();
                }else if(currentFragmentIndex ==0){
                    AppConfig.getInstance().setTransient_Time_Immediate(true);
                    if (Fragment_Second != null)
                        Fragment_Second.resetSet();
                }
                break;

            case 4:
                if (currentFragmentIndex == 0 || currentFragmentIndex == 1) {
                    if (currentFragmentIndex == 1) {
                        AppConfig.getInstance().setTransientSet_Default(false);
                        if (Fragment_First != null)
                            Fragment_First.saveSetting();
                    }else if(currentFragmentIndex ==0){
                        AppConfig.getInstance().setTransient_Time_Immediate(false);
                        if (Fragment_Second != null)
                            Fragment_Second.saveSetting();
                    }
                    setViewShow(3);
                } else {
                    isHold = obj.getSwitchindex() == 0 ? true : false;
                }

                break;
        }
    }

    private void setViewShow(int index){
        currentFragmentIndex = index;
        if (index == 2) {
            isSetMode = false;
            if (null == Fragment_Third) {
                Fragment_Third = new DipsSwellsMeter();
            }
            updateBottomView(new BaseBottomAdapterObj(0,null),0);
            updateBottomView(new BaseBottomAdapterObj(1,null),1);
            showFragment(Fragment_Third, Res2String(R.string.Meter));
        } else if (index == 1) {
            isSetMode = true;
            if (null == Fragment_First) {
                Fragment_First = new DipsSwellsSetTrigger();
            }
            showFragment(Fragment_First, Res2String(R.string.set));
        } else if (index == 0) {

            if (null == Fragment_Second) {
                Fragment_Second = new DipsSwellsSetTime();
            }
            showFragment(Fragment_Second, Res2String(R.string.set));
        } else if (index == 3) {

            if (null == Fragmet_Four) {
                Fragmet_Four = new DipsSwellsTrend();
            }
            updateBottomView(new BaseBottomAdapterObj(0,Res2Stringarr(R.array.transient_av_array)[funTypeIndex], Res2Stringarr(R.array.transient_av_array)),0,true);
            updateBottomView(new BaseBottomAdapterObj(1,Res2String(R.string.Cursor_zoom)),1);
            updateBottomView(new BaseBottomAdapterObj(2,null,Res2String(R.string.Meter),Res2String(R.string.Trend)),2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Event)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
            showFragment(Fragmet_Four, Res2String(R.string.Trend));
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
                if (Fragmet_Four != null && Fragmet_Four.isAdded())
                    Fragmet_Four.setShowMeterData(modelAllData, wir_index, wir_right_index, funTypeIndex, cursorEnable);
                else {
                    if (Fragment_Third != null && Fragment_Third.isAdded())
                        Fragment_Third.setShowMeterData(modelAllData);
                }
            }
        }
    }

    private int zoomSize = 1; //放大比例

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        possKeyDown(keyCode);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Up:
                if (null != Fragment_First && Fragment_First.isAdded()){
                    //                   Fragment_First.upKey();
                }else if(null!=Fragment_Second && Fragment_Second.isAdded()){
                    Fragment_Second.upKey();
                } else if(null!=Fragmet_Four && Fragmet_Four.isAdded()){
                    if(cursorEnable) {
                        if (zoomSize < 3) {
                            zoomSize++;
                        }
                        Fragmet_Four.zoomScale(zoomSize);
                    }else{

                    }
                }
                break;
            case Down:
                if(Fragment_First!=null && Fragment_First.isAdded()){
                    Fragment_First.downKey();
                } else if(null!=Fragmet_Four && Fragmet_Four.isAdded()) {
                    if(cursorEnable) {
                        if (zoomSize > 1) {
                            zoomSize--;
                        }
                        Fragmet_Four.zoomScale(zoomSize);
                    }else {

                    }
                }
                break;
            case Left:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.moveCursor(-1);
                else if(Fragment_Second!=null && Fragment_Second.isAdded()) {
                    Fragment_Second.moveCursor(-1);
                    if(Fragment_Second.forbidMoveRight()){
                        return true;
                    }
                } else if(Fragmet_Four!=null && Fragmet_Four.isAdded()){
                    Fragmet_Four.moveCursor(-1);
                }

                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded()) {
                    Fragment_First.moveCursor(1);
                    if (Fragment_First.forbidMoveRight()) {
                        return true;
                    }
                } else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    Fragment_Second.moveCursor(1);
                } else if (Fragmet_Four != null && Fragmet_Four.isAdded()) {
                    Fragmet_Four.moveCursor(1);
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
                if(Fragment_First!=null && Fragment_First.isAdded())
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
