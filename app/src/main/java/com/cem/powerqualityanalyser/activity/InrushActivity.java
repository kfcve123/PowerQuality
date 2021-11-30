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
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.fragment.FileListFragment;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.sqlBean.InrushMeterData;
import com.cem.powerqualityanalyser.sqlBean.SqlApi;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.SqlDataTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

//浪涌
public class InrushActivity extends BaseActivity implements BaseFragmentTrend.OnWirAndRightIndexCallBack {
    private InrushMeter Fragment_Second;
    private InrushSet Fragment_First;
    private InrushTrend Fragment_Third;
    private FileListFragment fileListFragment;

    private int wir_right_index;
    private int currentFragmentIndex = 0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private int firstPopIndex = 0;
    private int secondPopIndex = -1;//第二个PopWindow的选中角标，例如L1 L2 L3 N
    private boolean cursorEnable;//开启光标显示
    private boolean cursorZoom;//切换到有光标模式
    private boolean isRecording;
    private int inrushDataBeanModeType = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setTop_icon(R.mipmap.surge_icon);
        showLoading();
        setTitle("");
        setViewShow(0);
        setBottom4TextSize(18);
        dissLoading(1500l);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //       refeshHeadColor();

    }

    @Override
    public byte[] getMode() {
        return sendMeterOrderData(config.getSet_Wir(), 0);
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
        data.add(new BaseBottomAdapterObj(2, Res2String(R.string.logger_view)));
        data.add(new BaseBottomAdapterObj(3, Res2String(R.string.defaults)));
        data.add(new BaseBottomAdapterObj(4, Res2String(R.string.enter)));
        return data;
    }

    private SweetAlertDialog sweetAlertDialog;

    private void stopClick() {
        if (sweetAlertDialog == null)
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);

        sweetAlertDialog.setTitleText(Res2String(R.string.alert_title))
                .setContentText(Res2String(R.string.alert_content))
                .setCancelText(Res2String(R.string.alert_cancel))
                .setConfirmText(Res2String(R.string.alert_confirm))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        //停止记录，保存文件
                        stopLogger();
                    }
                });
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismiss();
            }
        });
        sweetAlertDialog.show();
    }

    /**
     * 记录完成后提示文件已保存，是否要去查看
     */
    private void recordSaveClick() {
        if (sweetAlertDialog == null)
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);

        sweetAlertDialog.setTitleText(Res2String(R.string.alert_save_title))
                .setContentText(Res2String(R.string.alert_save_content))
                .setCancelText(Res2String(R.string.alert_save_cancel))
                .setConfirmText(Res2String(R.string.alert_save_confirm))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        setViewShow(3);
                    }
                });
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismiss();
                setBottomMode(2);
            }
        });
        sweetAlertDialog.show();

    }


    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int position) {

        switch (obj.getId()) {
            case 0://切换 V / A  可以发命令，测量过程中不允许切换
                if (firstPopIndex != position) {
                    firstPopIndex = position;
                    if (!isRecording) {
                        Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
//                    serialHelper.sendData(sendTrendOrderData(firstPopIndex, secondPopIndex));
                        serialHelper.sendData(sendTrendOrderData(firstPopIndex));
                    }
                }
                break;
            case 3://L1.L2.L3,N切换不发命令，筛选数据
                if (secondPopIndex != position) {
                    secondPopIndex = position;
                    Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                }
                break;
        }

    }

    @Override
    public void returnWirAndRight(int wir, int right) {
        this.wir_index = wir;
        this.wir_right_index = right;
        serialHelper.sendData(sendMeterOrderData(wir, right));
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
                if (currentFragmentIndex == 3) {
                    fileListFragment.OpenFile();
                }
                break;
            case 1:
                if (currentFragmentIndex == 2) {
                    cursorZoom = !cursorZoom;
                    if (cursorZoom) {
                        setBottomMode(5);
                    } else {
                        setBottomMode(6);
                        secondPopIndex = -1;
                        Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                        //                       serialHelper.sendData(sendTrendOrderData(firstPopIndex,secondPopIndex));
                        serialHelper.sendData(sendTrendOrderData(firstPopIndex));
                        cursorEnable = false;
                        if (Fragment_Second != null)
                            Fragment_Third.showCursor(false);

                    }
                } else if (currentFragmentIndex == 3) {
                    fileListFragment.selectAll();
                }

                break;
            case 2:
                if (currentFragmentIndex == 2) {
                    if (cursorZoom) {
                        cursorEnable = !cursorEnable;
                        if (Fragment_Third != null)
                            Fragment_Third.showCursor(cursorEnable);
                        if (cursorEnable) {
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                            Fragment_Third.openCursorTopShow(wir_index, firstPopIndex, secondPopIndex);
                        } else {
                            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
                            Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
                        }

                    } else
                        setViewShow(obj.getSwitchindex() + 1);
                } else if (currentFragmentIndex == 0) {
                    setViewShow(3);
                } else if (currentFragmentIndex == 3) {
                    if (fileListFragment != null)
                        fileListFragment.deletefile();
                } else if (currentFragmentIndex == 1) {
                    setViewShow(obj.getSwitchindex() + 1);
                }
                break;
            case 3:
                if (currentFragmentIndex == 0) {
                    AppConfig.getInstance().setInrushSet_Default(true);
                    if (Fragment_First != null)
                        Fragment_First.resetSet();
                } else if (currentFragmentIndex == 3) {
                    if (fileListFragment != null)
                        fileListFragment.deletefile();
                } else if (currentFragmentIndex == 2) {
                    if (isNow) {
                        setViewShow(0);
                    } else {//Events

                    }
                }
                break;
            case 4:
                if (currentFragmentIndex == 0) {//设置下 Enter
                    if (AppConfig.getInstance().isInrushSet_Default())
                        AppConfig.getInstance().setInrushSet_Default(false);
                    if (Fragment_First != null)
                        Fragment_First.saveSetting();
                    setViewShow(2);
                    //                   initEvents();
                    isNow = true;
                    isTrendHold = false;
                    isTrendRun = false;
                } else if (currentFragmentIndex == 3) {//FileListFragment返回Enter
                    setViewShow(0);
                    isNow = false;
                    isTrendHold = false;
                    isTrendRun = false;
                    isRecording = false;
                } else if (currentFragmentIndex == 1) {// Meter 下的 Hold /Run处理
                    isMeterHold = obj.getSwitchindex() == 0 ? true : false;
                } else if (currentFragmentIndex == 2) {
                    if (isNow) {// Now 变Run
                        setBottomMode(3);
                        Fragment_Third.setStartRecord(false);
                    } else if (isTrendRun) {//Run 变 Hold /Stop
                        if (isRecording) //记录未结束 主动调用停止
                            stopClick();
                        else
                            setBottomMode(4);
                    } else if (isTrendHold) { //Stop 变 Now
                        setBottomMode(2);
                    }
                }
                break;
        }
    }

    private boolean isMeterHold; //Meter下的 Hold　和Ｒｕｎ
    private boolean isNow;
    private boolean isTrendHold;
    private boolean isTrendRun;

    private void setBottomMode(int bottomMode) {
        if (bottomMode == 0) { //FragmentSet
            updateBottomView(new BaseBottomAdapterObj(0, null), 0);
            updateBottomView(new BaseBottomAdapterObj(1, null), 1);
            updateBottomView(new BaseBottomAdapterObj(2, Res2String(R.string.logger_view)), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.defaults)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.enter)), 4);
        } else if (bottomMode == 1) {//FragmentMeter
            updateBottomView(new BaseBottomAdapterObj(0, null), 0);
            updateBottomView(new BaseBottomAdapterObj(1, null), 1);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Event)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, null, Res2String(R.string.Hold), Res2String(R.string.run)), 4);
        } else if (bottomMode == 2) {//FragmentThrend NOW
            isNow = true;
            isTrendHold = false;
            isTrendRun = false;
            isRecording = false;
            updateBottomView(new BaseBottomAdapterObj(0, Res2Stringarr(R.array.inrush_av_array)[firstPopIndex], Res2Stringarr(R.array.inrush_av_array)), 0, true);
            updateBottomView(new BaseBottomAdapterObj(1, null), 1);
            updateBottomView(new BaseBottomAdapterObj(2, null, Res2String(R.string.Meter), Res2String(R.string.Trend)), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.timed)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.now)), 4);
        } else if (bottomMode == 3) {//FragmentThrend RUN
            initDataCoreTable();
            isRecording = true;
            isTrendRun = true;
            isTrendHold = false;
            isNow = false;
            updateBottomView(new BaseBottomAdapterObj(0, Res2Stringarr(R.array.inrush_av_array)[firstPopIndex], Res2Stringarr(R.array.inrush_av_array)), 0, true);
            updateBottomView(new BaseBottomAdapterObj(1, Res2String(R.string.Cursor_zoom)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, null), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Event)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.Hold)), 4);
        } else if (bottomMode == 4) {//FragmentThrend HOLD
            isTrendHold = true;
            isTrendRun = false;
            isNow = false;
            isRecording = false;
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.run)), 4);
        } else if (bottomMode == 5) {//FragmenThrend Cursor 1
            updateBottomView(new BaseBottomAdapterObj(0, Res2Stringarr(R.array.inrush_av_array)[firstPopIndex], Res2Stringarr(R.array.inrush_av_array)), 0, true);
            updateBottomView(new BaseBottomAdapterObj(1, Res2String(R.string.back)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, Res2String(R.string.Cursor), Res2String(R.string.On), Res2String(R.string.Off)), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor_zoom_no), R.mipmap.zoomimg, Res2Stringarr(R.array.inrush_l1l2l3n_array)), 3);
            //                     updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.Zoom),R.mipmap.zoomimg,AppConfig.getInstance().getMaxZoom()),3);

        } else if (bottomMode == 6) {//FragmenThrend Cursor 2
            updateBottomView(new BaseBottomAdapterObj(0, Res2Stringarr(R.array.inrush_av_array)[firstPopIndex], Res2Stringarr(R.array.inrush_av_array)), 0, true);
            updateBottomView(new BaseBottomAdapterObj(1, Res2String(R.string.Cursor_zoom)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, null), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Event)), 3);

        } else if (bottomMode == 7) {//FileListFragment
            updateBottomView(new BaseBottomAdapterObj(0, Res2String(R.string.Open)), 0);
            updateBottomView(new BaseBottomAdapterObj(1, Res2String(R.string.SelectAll)), 1);
            updateBottomView(new BaseBottomAdapterObj(2, Res2String(R.string.logger_view)), 2);
            updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Delete)), 3);
            updateBottomView(new BaseBottomAdapterObj(4, Res2String(R.string.set)), 4);
        }
    }


    private void setViewShow(int index) {
        if (index == 1) {
            if (null == Fragment_Second) {
                Fragment_Second = new InrushMeter();
                Fragment_Second.setOnWirAndRightIndexCallBack(this);
            }
            setBottomMode(1);
            showFragment(Fragment_Second, Res2String(R.string.Meter));
            serialHelper.sendData(sendMeterOrderData(wir_index, wir_right_index));

        } else if (index == 0) {
            if (null == Fragment_First) {
                Fragment_First = new InrushSet();
            }
            setBottomMode(0);
            showFragment(Fragment_First, Res2String(R.string.set));
        } else if (index == 3) {
            if (null == fileListFragment)
                fileListFragment = new FileListFragment();
            setBottomMode(7);
            showFragment(fileListFragment, index + "");
            fileListFragment.setShareType(2);

        } else if (index == 2) {
            if (null == Fragment_Third) {
                Fragment_Third = new InrushTrend();
            }
            setBottomMode(2);
//            if(currentFragmentIndex!=1)
//                updateBottomView(new BaseBottomAdapterObj(4,null,Res2String(R.string.Hold),Res2String(R.string.run)),4);
            showFragment(Fragment_Third, Res2String(R.string.Trend));
            Fragment_Third.updateTrendRightAndPopMode(wir_index, firstPopIndex, secondPopIndex);
//            serialHelper.sendData(sendTrendOrderData(firstPopIndex, secondPopIndex));
            serialHelper.sendData(sendTrendOrderData(firstPopIndex));
            starTime = System.currentTimeMillis();
        }
        currentFragmentIndex = index;
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
        if (modelAllData != null && modelAllData.getValueType() == ModelAllData.AllData_valueType.E8_Surge_Suspended) {
            //           dissLoading();
            if (!isStart)
                isStart = true;
            if (!isTrendHold) {
                if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.setShowMeterData(modelAllData, wir_index, firstPopIndex, secondPopIndex, cursorEnable);
                    if (isRecording) {
                        if ((System.currentTimeMillis() - Fragment_Third.getStartTime()) < Fragment_First.getDuration()) {
                            //写入数据库
                            baseData = modelAllData;
                            loggerData();
                        } else {
                            stopLogger();
                        }
                    }
                } else {
                    if (!isMeterHold) {
                        if (Fragment_Second != null && Fragment_Second.isAdded())
                            Fragment_Second.setShowMeterData(modelAllData);
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
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        //       log.e("========" + key.toString());
        switch (key) {
            case Up:
                if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    if (cursorEnable) {
                        if (zoomSize < 3) {
                            zoomSize++;
                            AppConfig.getInstance().setMaxZoom(zoomSize);
                        }
                        Fragment_Third.zoomScale(zoomSize);
                        updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                    }
                }
                if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    if (currentView != null && currentView.isFocusable() && currentView instanceof RecyclerView) {
                        Fragment_Second.leftUpScroll();
                    }
                }
                break;
            case Down:
                if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    if (cursorEnable) {
                        if (zoomSize > 1) {
                            zoomSize--;
                            AppConfig.getInstance().setMaxZoom(zoomSize);
                        }
                        Fragment_Third.zoomScale(zoomSize);
                        updateBottomView(new BaseBottomAdapterObj(3, Res2String(R.string.Cursor), R.mipmap.left_right, AppConfig.getInstance().getMaxZoom()), 3);
                    }
                }
                if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    if (currentView != null && currentView.isFocusable() && currentView instanceof RecyclerView) {
                        Fragment_Second.leftDownScroll();
                    }
                }
                break;
            case Left:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.moveCursor(-1);
                else if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.moveCursor(-1);
                } else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    Fragment_Second.setFocusOnLeft();
                }
                break;
            case Right:
                if (Fragment_First != null && Fragment_First.isAdded())
                    Fragment_First.moveCursor(1);
                else if (Fragment_Third != null && Fragment_Third.isAdded()) {
                    Fragment_Third.moveCursor(1);
                } else if (Fragment_Second != null && Fragment_Second.isAdded()) {
                    Fragment_Second.setFocusOnRight();
                }
                break;
            case Back:
                if (isRecording) {
                    stopClick();
                } else {
                    if (currentFragmentIndex != 0) {
                        setViewShow(0);
                        return true;
                    }
                }
                break;
            case Power:
                //测量情况下嗯Power 提示 在测量期间不被允许

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int zoomSize = 1;


    private byte[] sendMeterOrderData(int wir, int wir_right_index) {
        byte[] cmd = new byte[2];
        switch (config.getConfig_nominal()) {
            case 0://50Hz
                switch (wir_right_index) {
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x00};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x10};
                        break;
                    case 2:
                        cmd = new byte[]{(byte) 0xE8, 0x20};
                        break;
                    case 3:
                        cmd = new byte[]{(byte) 0xE8, 0x30};
                        break;
                    case 4:
                        cmd = new byte[]{(byte) 0xE8, 0x40};
                        break;
                    case 5:
                        cmd = new byte[]{(byte) 0xE8, 0x50};
                        break;

                }
                break;
            case 1://60Hz
                switch (wir_right_index) {
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x01};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x11};
                        break;
                    case 2:
                        cmd = new byte[]{(byte) 0xE8, 0x21};
                        break;
                    case 3:
                        cmd = new byte[]{(byte) 0xE8, 0x31};
                        break;
                    case 4:
                        cmd = new byte[]{(byte) 0xE8, 0x41};
                        break;
                    case 5:
                        cmd = new byte[]{(byte) 0xE8, 0x51};
                        break;

                }
                break;
            case 2://400Hz

                break;
        }
        return cmd;
    }


    private byte[] sendTrendOrderData(int firstPopIndex, int secondPopIndex) {
        byte[] cmd = new byte[2];
        switch (config.getConfig_nominal()) {
            case 0://50Hz
                switch (secondPopIndex) {
                    case -1:
                        switch (firstPopIndex) {
                            case 0:
                                cmd = new byte[]{(byte) 0xE8, 0x10};
                                break;
                            case 1:
                                cmd = new byte[]{(byte) 0xE8, 0x00};
                                break;
                        }
                        break;
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x20};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x30};
                        break;
                    case 2:
                        cmd = new byte[]{(byte) 0xE8, 0x40};
                        break;
                    case 3:
                        cmd = new byte[]{(byte) 0xE8, 0x50};
                        break;
                }
                break;
            case 1://60Hz
                switch (secondPopIndex) {
                    case -1:
                        switch (firstPopIndex) {
                            case 0:
                                cmd = new byte[]{(byte) 0xE8, 0x11};
                                break;
                            case 1:
                                cmd = new byte[]{(byte) 0xE8, 0x01};
                                break;
                        }
                        break;
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x21};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x31};
                        break;
                    case 2:
                        cmd = new byte[]{(byte) 0xE8, 0x41};
                        break;
                    case 3:
                        cmd = new byte[]{(byte) 0xE8, 0x51};
                        break;
                }
                break;
            case 2://400Hz

                break;
        }
        return cmd;
    }

    private byte[] sendTrendOrderData(int firstPopIndex) {
        byte[] cmd = new byte[2];
        switch (config.getConfig_nominal()) {
            case 0://50Hz
                switch (firstPopIndex) {
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x10};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x00};
                        break;
                }
                break;
            case 1://60Hz
                switch (firstPopIndex) {
                    case 0:
                        cmd = new byte[]{(byte) 0xE8, 0x11};
                        break;
                    case 1:
                        cmd = new byte[]{(byte) 0xE8, 0x01};
                        break;
                }
                break;
            case 2://400Hz

                break;
        }
        return cmd;
    }

    private Timer timer;
    private InrushDataBean inrushDataBean;
    private int saveCount = 0;
    private ModelAllData baseData;//要存入数据库的数据
    private List<ModelLineData> lineDataList;
    private long starTime;

    private void stopLogger() {
        if (timer != null) {
            timer.cancel();
            isRecording = false;
            timer = null;
            if (inrushDataBean != null && inrushDataBean.isSaved()) {
                DBManager.getInstance().updateInrushBeanEndDateSync(inrushDataBean.getId(), new Date(), new DBManager.DBSaveListener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //                              setViewShow(3);
                                recordSaveClick();
                            }
                        });
                    }

                    @Override
                    public void onFail() {

                    }
                });
            }
        }

        Fragment_Third.stopRecordTime();
    }

    private void loggerData() {
        if (timer != null)
            return;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (baseData != null && baseData.getModelLineData() != null) {
                    saveCount += baseData.getModelLineData().size();
                    if (saveCount > SqlApi.MAX_LENGTH) {
                        if (inrushDataBean != null && inrushDataBean.isSaved()) {
                            DBManager.getInstance().updateInrushBeanEndDate(inrushDataBean.getId(), new Date());
                            loggerData();
                        }
                    } else {
                        saveData();
                    }
                }
            }
        }, 0, 1000);

    }


    /**
     * 点击 now 切换到Run时开始创建保存
     */
    private void initDataCoreTable() {
        inrushDataBean = new InrushDataBean();
        inrushDataBean.setStartDate(new Date());

        switch (firstPopIndex) {
            case 0:
                inrushDataBeanModeType = SqlApi.Inrush_History_Arms;
                inrushDataBean.setCheckParameters(Res2String(R.string.parameter_Arms));
                break;
            case 1:
                inrushDataBeanModeType = SqlApi.Inrush_History_Vrms;
                inrushDataBean.setCheckParameters(Res2String(R.string.parameter_Vrms));
                break;
        }
        inrushDataBean.setModeType(SqlApi.Mode_Inrush);
        inrushDataBean.setFileName("Inrush-" + inrushDataBean.getFormatDate());
        inrushDataBean.setDeviceName("DT-7760");
        inrushDataBean.save();
        saveCount = 0;
    }

    private void saveData() {
        if (baseData != null) {
            List<InrushMeterData> inrushMeterDataList = SqlDataTool.toInrushData(InrushActivity.this, baseData.getModelLineData(), inrushDataBean.getCheckParameters(), inrushDataBeanModeType);
            DBManager.getInstance().insetInrushMeterData(inrushDataBean.getFileName(), inrushMeterDataList, null);
        }
        //       baseData = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serialHelper != null) {
            serialHelper.closeSerialPort();
            serialHelper = null;

        }
    }

}
