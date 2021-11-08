package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.fragment.LoggerFragment;
import com.cem.powerqualityanalyser.fragment.ParameterSetFragment;
import com.cem.powerqualityanalyser.libs.MeterCommand;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.tool.MemoryTool;
import com.cem.powerqualityanalyser.tool.MeterDataPool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.NamedDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;


//记录器  旧版

public class RecorderActivity extends BaseActivity {
    private FragmentTransaction fragmentTransaction;
    private LoggerFragment loggerFragment;
    private FragmentManager fragmentManager;
    private LoogerTouchEvent loggerTouchEvent;
    private ParameterSetFragment parameterSetFragment;

    private NamedDialog namedDialog;
    private ArrayList<String> parameterNames;//被勾选的

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }


    protected void setViewShow(int index) {
        if (this.isDestroyed())
            return;
        if (index == 0) {
            if (null == loggerFragment) {
                loggerFragment = new LoggerFragment();
            }
            public_bottom.getChildAt(1).setVisibility(View.VISIBLE);
            public_bottom.getChildAt(4).setVisibility(View.VISIBLE);
            showFragment(loggerFragment,index+"");
        }else if (index == 1){
            if (null == parameterSetFragment) {
                parameterSetFragment = new ParameterSetFragment();
            }
            public_bottom.getChildAt(1).setVisibility(View.INVISIBLE);
            public_bottom.getChildAt(4).setVisibility(View.INVISIBLE);
            showFragment(parameterSetFragment,index+"");
        }
    }
    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userView, fragment, tag);
        fragmentTransaction.commit();
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
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(Res2String(R.string.Parameter)));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(Res2String(R.string.Start)));
        return  data;
    }

    /**
     * 弹窗菜单点击事件
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }



    /**
     * 底部按钮点击事件
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(final View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 1:
                setViewShow(1);
                break;
            case 4:
                stratClick(view);
                break;
        }
    }

    private void stratClick(View view) {
        if (!checkFileName() ){
            Toast.makeText(this,"please input file name",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkIsChecked()){
            Toast.makeText(this,"please check parameter",Toast.LENGTH_SHORT).show();
            return;
        }
        this.parameterNames = parameterSetFragment.getCheckName();
 //       final TextView textView = (TextView) view.findViewById(R.id.bottom_view_context);

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
                            namedDialog.dismiss();
                        }
                    });
                }
            });
        }else{
            toLoggerThrend();
        }
    }

   private void toLoggerThrend(){
        if (MemoryTool.getFreeProportion() < 0.05){
            Toast.makeText(this,R.string.memoryHint,Toast.LENGTH_SHORT).show();
            return;
        }

       Intent intent = new Intent(this,LoggerThendActivity.class);
       intent.putExtra(AppConfig.MainPutActivityNameKey, "Logger");
       intent.putStringArrayListExtra("parameterNames",parameterNames);
       intent.putExtra("Duration",loggerFragment.getDuration());
       intent.putExtra("Interval",loggerFragment.getInterval());
       intent.putExtra("MeterCommand",parameterSetFragment.getMeterCommand().value());
       intent.putExtra("FileName",loggerFragment.getFileName());
       startActivity(intent);
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
        return  parameterSetFragment.getCheckName().size() > 0 && parameterSetFragment.getMeterCommand() != null;
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (loggerFragment.isAdded()){
            loggerTouchEvent.onTouchEvent(ev);
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

    public interface LoogerTouchEvent{
        boolean onTouchEvent(MotionEvent event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.loggerTouchEvent = null;
    }
    @Override
    public void destoryself() {
        if (parameterSetFragment != null && parameterSetFragment.isAdded()){
            setViewShow(0);
        }else{
            super.destoryself();
        }
    }

    @Override
    public void onBackPressed() {
        destoryself();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        possKeyDown(keyCode);
        //解决自定义控件不对齐，左键会改变焦点的问题
        if (( keyCode == MeterKeyValue.Left.value() || keyCode == MeterKeyValue.Right.value()) && getCurrentFocus() instanceof LinearLayout)
            return true;
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
}
