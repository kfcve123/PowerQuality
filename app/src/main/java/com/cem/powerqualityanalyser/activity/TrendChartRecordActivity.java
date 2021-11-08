package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.fragment.FileListFragment;
import com.cem.powerqualityanalyser.fragment.LoggerFragment;
import com.cem.powerqualityanalyser.fragment.NewHistoryDataFragment;
import com.cem.powerqualityanalyser.fragment.ParameterNewSetFragment;
import com.cem.powerqualityanalyser.fragment.ParameterSetFragment;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.tool.ButtonListenerTool;
import com.cem.powerqualityanalyser.tool.MemoryTool;
import com.cem.powerqualityanalyser.tool.MeterDataPool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.NamedDialog;


import org.litepal.LitePal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import serialport.amos.cem.com.libamosserial.ModelAllData;

//记录/查看  新版
public class TrendChartRecordActivity extends BaseActivity implements View.OnClickListener {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;

    private TextView setup_tv1, setup_tv2, setup_tv3, setup_tv4, setup_tv5;
    private LinearLayout leftmenu;
    private LoogerTouchEvent loggerTouchEvent;

    private ParameterNewSetFragment parameterSetFragment;
    private LoggerFragment loggerFragment;

    private ArrayList<String> parameterNames;//被勾选的
    private ArrayList<Integer> parameterIndexs;//
    private NamedDialog namedDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setContentView(R.layout.activity_logger);
        setBottomTextSize(18);
  //      public_bottom.setVisibility(View.GONE);
        leftmenu = findViewById(R.id.leftmenu);
        leftmenu.setVisibility(View.GONE);
        setup_tv1 = findViewById(R.id.setup_tv1);
        setup_tv2 = findViewById(R.id.setup_tv2);
        setup_tv3 = findViewById(R.id.setup_tv3);
        setup_tv4 = findViewById(R.id.setup_tv4);
        setup_tv5 = findViewById(R.id.setup_tv5);
        setup_tv1.setOnClickListener(this);
        setup_tv2.setOnClickListener(this);
        setup_tv3.setOnClickListener(this);
        setup_tv4.setOnClickListener(this);
        setup_tv5.setOnClickListener(this);
        setViewShow(1);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    protected void setViewShow(int index) {
        if(this.isDestroyed())
            return;
        if (index == 0) {
            if (null == parameterSetFragment) {
                parameterSetFragment = new ParameterNewSetFragment();
            }
            updateBottomView(new BaseBottomAdapterObj(3,null,Res2String(R.string.SelectAll),Res2String(R.string.unSelectAll)),3);
     //       updateBottomView(new BaseBottomAdapterObj(3,Res2String(R.string.SelectAll)),3);
            showFragment(parameterSetFragment, Res2String(R.string.logger_parameter));
        } else if (index == 1) {
            if (null == loggerFragment) {
                loggerFragment = new LoggerFragment();
            }
            updateBottomView(new BaseBottomAdapterObj(3,null),3);
            showFragment(loggerFragment, Res2String(R.string.logger_logger));
        }
    }

    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.urerLayout, fragment, tag);
        fragmentTransaction.commit();
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
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.logger_parameter)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.logger_logger)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.logger_view)));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.Start)));
        return data;
    }

    /**
     * 弹窗菜单点击事件
     *
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    /**
     * 底部按钮点击事件
     *
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
 //       setViewShow(obj.getId());
        switch (obj.getId()){
            case 0:
//                shotDown();
                setViewShow(0);
                break;
            case 1:
                setViewShow(1);
                break;
            case 2://记录列表View
                startActivity(new Intent(this,CsvFileActivity.class));
                break;
            case 3:
                if(parameterSetFragment!=null && parameterSetFragment.isAdded()){
                    parameterSetFragment.selectAllParaMeter();
                }
                break;
            case 4:
                stratClick(view);
                break;
        }
    }

    /**
     * 开始记录
     * @param view
     */
    private void stratClick(View view){
        if (!checkFileName() ){
            Toast.makeText(this,"please input file name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkIsChecked()){
            Toast.makeText(this,"please check parameter",Toast.LENGTH_SHORT).show();
            return;
        }
        this.parameterNames = parameterSetFragment.getCheckName();
        this.parameterIndexs = parameterSetFragment.getCheckIndex();
        if (existFile(loggerFragment.getFileName())) {
            showDialog(Res2String(R.string.dilogforfilename), Res2String(R.string.Rename), Res2String(R.string.Cover), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    namedDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBManager.getInstance().deleteFromName(loggerFragment.getFileName(), new DBManager.DBUpdateOrDeleteListener() {
                        @Override
                        public void onFinish() {
                            toLoggerThrend();
                            //不跳转，直接当前页面后台记录
                            namedDialog.dismiss();
                        }
                    });
                }
            });
        }else{
            toLoggerThrend();
        }
    }


    private boolean existFile(String fileName){
        List<SqlDataBean> dataCoreTables = LitePal.where("filename = ?", fileName)
                .find(SqlDataBean.class, false);
        if (dataCoreTables.size() > 0) {
            for (SqlDataBean data: dataCoreTables) {
                MeterDataPool.recycle(data);
            }
            return true;
        }else{
            return false;
        }
    }

    private void toLoggerThrend(){
        if (MemoryTool.getFreeProportion() < 0.05){
            Toast.makeText(this,R.string.memoryHint,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,NewLoggerThendActivity.class);
        intent.putExtra(AppConfig.MainPutActivityNameKey, "Logger");
        intent.putStringArrayListExtra("parameterNames",parameterNames);
        intent.putIntegerArrayListExtra("parameterIndexs",parameterIndexs);
        intent.putExtra("Duration",loggerFragment.getDuration());
        intent.putExtra("Interval",loggerFragment.getInterval());
        intent.putExtra("MeterCommand",parameterSetFragment.getRecordCommand());
        intent.putExtra("FileName",loggerFragment.getFileName());
        startActivity(intent);

    }


    private void showDialog(String message, String left, String right, View.OnClickListener leftListener, View.OnClickListener rightLister){
        if (namedDialog == null)
            namedDialog = new NamedDialog(this);
        namedDialog.show();
        namedDialog.setLeftListener(leftListener)
                .setRightListener(rightLister)
                .setTvMessage(message)
                .setLeftText(left)
                .setRightText(right)
                .setCancelable(false);
    }

    public void registerLoggerTouch(LoogerTouchEvent loggerTouchEvent){
        this.loggerTouchEvent = loggerTouchEvent;
    }

    public interface LoogerTouchEvent{
        boolean onTouchEvent(MotionEvent event);
    }


    /**
     * 检查是否输入文件名
     * @return
     */
    private boolean checkFileName(){
        String fileName = loggerFragment.getFileName();
        return !TextUtils.isEmpty(fileName);
    }

    /**
     * 是否勾选参数
     * @return
     */
    private boolean checkIsChecked(){
        if (parameterSetFragment == null)
            return false;
        return  parameterSetFragment.getCheckName().size() > 0 && parameterSetFragment.getRecordCommand() != null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setup_tv1:
                setViewShow(0);
                break;
            case R.id.setup_tv2:
                setViewShow(1);
                break;
            case R.id.setup_tv3:
                setViewShow(2);
                break;
            case R.id.setup_tv4:
                setViewShow(3);
                break;
            case R.id.setup_tv5:
                setViewShow(4);
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (loggerFragment.isAdded()){
            loggerFragment.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        possKeyDown(keyCode);
        //解决自定义控件不对齐，左键会改变焦点的问题
        if (( keyCode == MeterKeyValue.Left.value() || keyCode == MeterKeyValue.Right.value())) {
            return true;
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
                if (loggerFragment != null && loggerFragment.isAdded())
                    loggerFragment.moveCursor(-1);
                break;
            case Right:
                if (loggerFragment != null && loggerFragment.isAdded())
                    loggerFragment.moveCursor(1);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.loggerTouchEvent = null;
    }

    public void destoryself() {
        if (parameterSetFragment != null && parameterSetFragment.isAdded()){
            setViewShow(1);
            showSelectBg(1);
        }else{
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        destoryself();
    }

}
