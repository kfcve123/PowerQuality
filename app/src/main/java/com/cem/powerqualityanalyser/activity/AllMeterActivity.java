package com.cem.powerqualityanalyser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.AppConfig.MeterTypeValue;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.RightModeAdapter;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.DataModel;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;
import serialport.amos.cem.com.libamosserial.OnSerialPortDataListener;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;

public class AllMeterActivity extends BaseActivity{

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1;
    private MeterTypeValue meterTypeValue = MeterTypeValue.None;
    private List<DataModel> dataModels = new ArrayList<>();

    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;

    private int wir_index = 0; //接线方式
    private int wir_right_index = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmeter_layout);
        wir_index = config.getSet_Wir();
        Intent intent = getIntent();
        meterTypeValue = MeterTypeValue.valueOf(intent.getIntExtra("metertypevalue",1));
        setScreen(this);
        addFalseData();
        initView();

    }

    @Override
    public byte[] getMode() {
        return new byte[0];
    }

    public static void setScreen(Context context) {
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

       int SCREENWIDTH = outMetrics.widthPixels;
       int  SCREENHEIGHT = outMetrics.heightPixels;

        log.e("======" + SCREENHEIGHT +"==========" + SCREENWIDTH);
    }


    private void addFalseData(){
        Random random = new Random();
        for(int i = 0;i<10;i++){
            DataModel dataModel = new DataModel();
            int value = random.nextInt(100) + i;
            value = 0;
            dataModel.setDm_L1A(value+"");
            dataModel.setDm_L2B(value + "");
            dataModel.setDm_L3C(value +"");
//            dataModel.setDm_N(value +"");
            dataModels.add(dataModel);
        }
    }


    private void initView() {
        Group_list_middleText = findViewById(R.id.Group_list_middleText);
        Group_list_leftText = findViewById(R.id.Group_list_leftText);
        Group_list_rightText = findViewById(R.id.Group_list_rightText);
        List<RightListViewItemObj> strList =  new ArrayList();
        rightModeView = findViewById(R.id.modeview);

        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        stickyLayout.setShowAllDividerMode();
        groupListObj1=new MeterGroupListObj();
        rightModeView.setUpDownClick(false);
        switch (meterTypeValue){
            case VoltsAmpsHertz:
                Group_list_middleText.setText(R.string.allmeter_votls_v);
                Group_list_leftText.setText("");
                switch (wir_index){
                    case 0://3相5线
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3c_array));
                        strList.clear();
                        strList.add(new RightListViewItemObj("3U",-1));
                        strList.add(new RightListViewItemObj("4V",-1));
                        strList.add(new RightListViewItemObj("4A",-1));
                        strList.add(new RightListViewItemObj("L1",-1));
                        strList.add(new RightListViewItemObj("L2",-1));
                        strList.add(new RightListViewItemObj("L3",-1));
                        strList.add(new RightListViewItemObj("N",-1));
                        break;

                    case 1://3相4线
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3c_array));
                        strList.clear();
                        strList.add(new RightListViewItemObj("3U",-1));
                        strList.add(new RightListViewItemObj("3V",-1));
                        strList.add(new RightListViewItemObj("3A",-1));
                        strList.add(new RightListViewItemObj("L1",-1));
                        strList.add(new RightListViewItemObj("L2",-1));
                        strList.add(new RightListViewItemObj("L3",-1));
                        break;
                    case 2://3相3线
                        strList.clear();
                        strList.add(new RightListViewItemObj("3U",-1));
                        strList.add(new RightListViewItemObj("3A",-1));
                        break;
                    case 3://单2相
                        strList.clear();
                        break;
                    case 4://单3相
                        strList.clear();
                        strList.add(new RightListViewItemObj("2V",-1));
                        strList.add(new RightListViewItemObj("2A",-1));
                        strList.add(new RightListViewItemObj("L1",-1));
                        strList.add(new RightListViewItemObj("N",-1));
                        break;
                    case 5://分2
                        strList.clear();
                        break;
                    case 6://分3
                        strList.clear();
                        strList.add(new RightListViewItemObj("U",-1));
                        strList.add(new RightListViewItemObj("2V",-1));
                        strList.add(new RightListViewItemObj("2A",-1));
                        strList.add(new RightListViewItemObj("L1",-1));
                        strList.add(new RightListViewItemObj("L2",-1));

                        break;
                    case 7://分4
                        strList.clear();
                        strList.add(new RightListViewItemObj("U",-1));
                        strList.add(new RightListViewItemObj("3V",-1));
                        strList.add(new RightListViewItemObj("3A",-1));
                        strList.add(new RightListViewItemObj("L1",-1));
                        strList.add(new RightListViewItemObj("L2",-1));
                        strList.add(new RightListViewItemObj("N",-1));
                        break;

                }
//                addMeterData("Vrms人(V)", 0, groupListObj1, dataModels.get(0));
//                addMeterData("Vpk(V)", 1, groupListObj1, dataModels.get(1));
//                addMeterData("CF V", 2, groupListObj1, dataModels.get(2));
//                addMeterData("Freq(Hz)", 3, groupListObj1, dataModels.get(3));
//
//                addMeterData("Udc(V)", 4, groupListObj1, dataModels.get(4));
//                addMeterData("Arms(A)", 5, groupListObj1, dataModels.get(5));
//                addMeterData("Apk(A)", 6, groupListObj1, dataModels.get(6));
//                addMeterData("CF A", 7, groupListObj1, dataModels.get(7));
//                addMeterData("ΦV(°)", 8, groupListObj1, dataModels.get(8));
//                addMeterData("ΦA(°)", 9, groupListObj1, dataModels.get(9));

                rightModeView.setModeList(strList);

                break;
            case PowerEnergy:

                Group_list_middleText.setText(R.string.allmeter_power_enrgy);
                Group_list_leftText.setText("");
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                addMeterData("Vrms人(V)", 0, groupListObj1, dataModels.get(0));
                addMeterData("Vpk(V)", 1, groupListObj1, dataModels.get(1));
                addMeterData("CF V", 2, groupListObj1, dataModels.get(2));
                addMeterData("Freq(Hz)", 3, groupListObj1, dataModels.get(3));

                addMeterData("Udc(V)", 4, groupListObj1, dataModels.get(4));
                addMeterData("Arms(A)", 5, groupListObj1, dataModels.get(5));
                addMeterData("Apk(A)", 6, groupListObj1, dataModels.get(6));
                break;

            case Harmonics:
                Group_list_middleText.setText(R.string.allmeter_harmonics);
                Group_list_leftText.setText("");
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                addMeterData("Vrms人(V)", 0, groupListObj1, dataModels.get(0));
                addMeterData("Vpk(V)", 1, groupListObj1, dataModels.get(1));
                addMeterData("CF V", 2, groupListObj1, dataModels.get(2));
                addMeterData("Freq(Hz)", 3, groupListObj1, dataModels.get(3));

                addMeterData("Udc(V)", 4, groupListObj1, dataModels.get(4));
                addMeterData("Arms(A)", 5, groupListObj1, dataModels.get(5));
                addMeterData("Apk(A)", 6, groupListObj1, dataModels.get(6));
                break;

            case Unbalance:
                Group_list_middleText.setText(R.string.allmeter_unbalance);
                Group_list_leftText.setText("");
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                addMeterData("Vrms人(V)", 0, groupListObj1, dataModels.get(0));
                addMeterData("Vpk(V)", 1, groupListObj1, dataModels.get(1));
                addMeterData("CF V", 2, groupListObj1, dataModels.get(2));
                addMeterData("Freq(Hz)", 3, groupListObj1, dataModels.get(3));

                addMeterData("Udc(V)", 4, groupListObj1, dataModels.get(4));
                addMeterData("Arms(A)", 5, groupListObj1, dataModels.get(5));
                addMeterData("Apk(A)", 6, groupListObj1, dataModels.get(6));
                break;

            case Flicker:
                Group_list_middleText.setText(R.string.allmeter_flicker);
                Group_list_leftText.setText("");
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                addMeterData("Vrms人(V)", 0, groupListObj1, dataModels.get(0));
                addMeterData("Vpk(V)", 1, groupListObj1, dataModels.get(1));
                addMeterData("CF V", 2, groupListObj1, dataModels.get(2));
                addMeterData("Freq(Hz)", 3, groupListObj1, dataModels.get(3));

                addMeterData("Udc(V)", 4, groupListObj1, dataModels.get(4));
                addMeterData("Arms(A)", 5, groupListObj1, dataModels.get(5));
                addMeterData("Apk(A)", 6, groupListObj1, dataModels.get(6));
                break;

            case Transient:


                break;

        }

        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });

        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
            }
        });

    }

    protected void addMeterData(String str, int index, MeterGroupListObj groupListObj, DataModel model) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
            }

            if (item.getItemSize() < 3 + 1) {
                item.AddMeterChildItem(model.getDm_L1A());
                item.AddMeterChildItem(model.getDm_L2B());
                item.AddMeterChildItem(model.getDm_L3C());
                item.AddMeterChildItem(model.getDm_N());
            } else {
                item.setItem(1, model.getDm_L1A());
                item.setItem(2, model.getDm_L2B());
                item.setItem(3, model.getDm_L3C());
                item.setItem(4, model.getDm_N());

            }

        } catch (Exception ex) {
            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {




        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.vlot_bottom_menu)));
        data.add(new BaseBottomAdapterObj(1,""));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.vlot_bottom_run)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.vlot_bottom_hold)));


        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

    }

    @Override
    public void onDataReceived(byte[] bytes) {

    }

    //对象列表
    @Override
    public void onDataReceivedModel(ModelAllData list) {
        //Log.e("上层收到的对象为：","长度为："+list.size());
        ModelAllData modelAllData=list;
        Log.e("上层收到的数据HZ的数据为：",modelAllData.getHzData());
        List<ModelLineData> list2=modelAllData.getModelLineData();

        Log.e("上层收到对象中list长度为：",list2.size()+"");
        for ( int n=0;n<list2.size();n++)
        {
            ModelLineData modelLineData=(ModelLineData)list2.get(n);
            log.e("上层收到的数据为" + modelLineData.getLineNumber() +"行"+ "这一行测量的是-------:"+ modelLineData.getLineName());
        }

        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });
    }

    @Override
    public void onDataReceivedList(List list) {

    }



     /*@Override
    public void onDataReceivedModel(List list) {
        for ( int n=0;n<list.size();n++)
        {
            DataModel dataModel=new DataModel();
            dataModel=(DataModel)list.get(n);
            addMeterData(dataModel.getDm_LineName(),n,groupListObj1,dataModel);
        }
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });
    }*/


    @Override
    protected void onResume() {
        super.onResume();
        switch (meterTypeValue) {
            case VoltsAmpsHertz:
                log.e("====发送命令===");
                if(serialHelper!=null)
                    serialHelper.sendData(Commad.E2_1_NEUTRAL_E_S);

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        posskey(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    private void posskey(int keyCode){
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);

        switch (key) {
            case Left:

                break;
            case Right:
                    rightModeView.setListViewFocusable(true);
                    rightModeView.requestListViewFocus();

                break;
        }

    }



}
