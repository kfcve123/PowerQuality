package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.fragment.NewHistoryDataFragment;
import com.cem.powerqualityanalyser.meterobject.NewHistoryMeterAllObj;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.tool.DataToMeterTool;
import com.cem.powerqualityanalyser.tool.SqlDataTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

public class InrushHistoryActivity extends BaseActivity {
    private  int popwindowsIndex=0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    //对应参数中的勾选项
    private List<String> checkedParameters;
    private NewHistoryDataFragment Fragment_Second;

    private String fileName;
    private InrushDataBean dataCoreTable;
    private List<BaseData> meterList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTest();
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setViewShow(0);
        fileName = getIntent().getStringExtra("fileName");

    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void initTest() {
        checkedParameters = new ArrayList<>();
        checkedParameters.add(Res2String(R.string.parameter_Arms));
        checkedParameters.add(Res2String(R.string.parameter_Vrms));
    }

    private void setViewShow(int index) {
        if (index == 0) {
            if (null == Fragment_Second) {
                Fragment_Second = new NewHistoryDataFragment();
            }
            showFragment(Fragment_Second, Res2String(R.string.Meter));
        }
    }

    private void showFragment(Fragment fragment, String tag){
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
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.left)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.right)));
        data.add(new BaseBottomAdapterObj(2,checkedParameters.get(0),checkedParameters.toArray(new String[checkedParameters.size()])));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,null));
        return  data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataCoreTable == null){
            showLoading();
            DBManager.getInstance().findInrushFromName(true, fileName, new DBManager.DBQueryInrushParameterListener() {
                @Override
                public void onFail() {
                    dissLoading();
                    Toast.makeText(InrushHistoryActivity.this,"加载数据失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(List<InrushDataBean> dataMeterAllParameterList) {
                    dissLoading();
                    if (dataMeterAllParameterList!= null && dataMeterAllParameterList.size() > 0){
                        dataCoreTable = dataMeterAllParameterList.get(0);
                        meterList = SqlDataTool.inrushDataBean2BaseData(InrushHistoryActivity.this, dataCoreTable);
                        initData();
                    }
                }

            });
        }
    }

    private void initData(){
        this.checkedParameters.clear();
        String[] split = dataCoreTable.getCheckParameters().split(",");
        this.checkedParameters.addAll(Arrays.asList(split));
        possList = new List[checkedParameters.size()];
        initBottomButtonData();
        refreshData();
    }

    private List<NewHistoryMeterAllObj>[] possList;

    private void refreshData(){
        if (possList[popwindowsIndex] == null){
            List<NewHistoryMeterAllObj> meterAllParamObjList = new DataToMeterTool(this).getInrushMeterAllObj(popwindowsIndex,meterList,checkedParameters);
            possList[popwindowsIndex] = meterAllParamObjList;
        }
        Fragment_Second.clearValues();

        //设置开始和结束时间
        if (possList[popwindowsIndex].size() > 0){
            Fragment_Second.setHistoryDate(possList[popwindowsIndex].get(0).getDate(),possList[popwindowsIndex].get(possList[popwindowsIndex].size() - 1).getDate());
        }

        if (possList[popwindowsIndex] != null)
            Fragment_Second.setShowMeterData(possList[popwindowsIndex]);

    }

    /**
     * 弹窗菜单点击事件
     * @param obj
     * @param positio
     */
    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {
        popwindowsIndex=positio;
        refreshData();
    }
    /**
     * 底部按钮点击事件
     * @param view
     * @param obj
     */
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        if (obj.getId() == 0){
            leftMove();
        }else if (obj.getId() == 1){
            rightMove();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        possKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    private void leftMove(){
        if(Fragment_Second!=null)
            Fragment_Second.moveCursor(-1);
    }
    private void rightMove(){
        if(Fragment_Second!=null)
            Fragment_Second.moveCursor(1);
    }

    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:
                leftMove();
                break;
            case Right:
                rightMove();
                break;

        }
    }

    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData modelAllData) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }
}
