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
import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.fragment.HistoryDataFragment;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.sqlBean.SqlApi;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.tool.DataToMeterTool;
import com.cem.powerqualityanalyser.tool.SqlDataTool;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

public class HistoryLoggerActivity extends BaseActivity {
    private  int popwindowsIndex=0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    //对应参数中的勾选项
    private List<String> checkedParameters;
    private HistoryDataFragment Fragment_Second;

    private String fileName;
    private SqlDataBean dataCoreTable;
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
        checkedParameters.add(Res2String(R.string.Urms));
        checkedParameters.add(Res2String(R.string.Uabc));
        checkedParameters.add(Res2String(R.string.Upk));
        checkedParameters.add(Res2String(R.string.Ipk));
        checkedParameters.add(Res2String(R.string.Freq));
        checkedParameters.add(Res2String(R.string.Irms));
        checkedParameters.add(Res2String(R.string.PF));

    }


    private  void  setViewShow(int index){
        if (index==0){
            if (null == Fragment_Second) {
                Fragment_Second = new HistoryDataFragment();

            }
            showFragment(Fragment_Second,Res2String(R.string.Meter));
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
        data.add(new BaseBottomAdapterObj(Res2String(R.string.left)));
        data.add(new BaseBottomAdapterObj(Res2String(R.string.right)));
        data.add(new BaseBottomAdapterObj(checkedParameters.get(0),checkedParameters.toArray(new String[checkedParameters.size()])));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        return  data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataCoreTable == null){
            showLoading();
            DBManager.getInstance().findFromName(true, fileName, new DBManager.DBQueryAllParameterListener() {
                @Override
                public void onFail() {
                    HistoryLoggerActivity.this.dissLoading();
                    Toast.makeText(HistoryLoggerActivity.this,"加载数据失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(List<SqlDataBean> dataMeterAllParameterList) {
                    HistoryLoggerActivity.this.dissLoading();
                    if (dataMeterAllParameterList!= null &&dataMeterAllParameterList.size() > 0){
                        HistoryLoggerActivity.this.dataCoreTable = dataMeterAllParameterList.get(0);
                        meterList = SqlDataTool.SqlDataBean2BaseData(HistoryLoggerActivity.this, dataCoreTable);
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
        if (checkedParameters.contains(Res2String(R.string.Urms2))){
            checkedParameters.remove(Res2String(R.string.Urms2));
        }
        if (checkedParameters.contains(Res2String(R.string.Irms2))){
            checkedParameters.remove(Res2String(R.string.Irms2));
        }
        if (checkedParameters.contains(Res2String(R.string.Uthd2_50))){
            checkedParameters.remove(Res2String(R.string.Uthd2_50));
        }
        if (checkedParameters.contains(Res2String(R.string.Ithd2_50))){
            checkedParameters.remove(Res2String(R.string.Ithd2_50));
        }
        if (checkedParameters.contains(Res2String(R.string.Ithd2_50))){
            checkedParameters.remove(Res2String(R.string.Ithd2_50));
        }
        if (checkedParameters.contains(Res2String(R.string.Vfund)) && dataCoreTable.modeType == SqlApi.Mode_Harmonic){
            checkedParameters.remove(Res2String(R.string.Vfund));
        }
        if (checkedParameters.contains(Res2String(R.string.Afund)) && dataCoreTable.modeType == SqlApi.Mode_Harmonic){
            checkedParameters.remove(Res2String(R.string.Afund));
        }
        if (checkedParameters.contains(Res2String(R.string.Freq))){
            checkedParameters.remove(Res2String(R.string.Freq));
        }
        possList = new List[checkedParameters.size()];
        initBottomButtonData();
        refreshData();

    }

    private List<HistoryMeterAllObj>[] possList;

    private void refreshData(){
        if (possList[popwindowsIndex] == null){
            List<HistoryMeterAllObj> meterAllParamObjList = new DataToMeterTool(this).getMeterAllObj(popwindowsIndex,meterList,checkedParameters);
            possList[popwindowsIndex] = meterAllParamObjList;
        }
        Fragment_Second.clearValues();

        //设置开始和结束时间
        if (possList[popwindowsIndex].size() > 0){
            Fragment_Second.setHistoryDate(possList[popwindowsIndex].get(0).getDate(),possList[popwindowsIndex].get(possList[popwindowsIndex].size() - 1).getDate());
        }

        String freq = new DataToMeterTool(this).getFreq(meterList);
        Fragment_Second.setFreqValue(freq);
        if (possList[popwindowsIndex] != null)
//        for (int i = 0; i < possList[popwindowsIndex].size(); i++) {
//            Fragment_Second.setShowMeterData(possList[popwindowsIndex].get(i));
//        }
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
        log.e("========" + key.toString());
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
