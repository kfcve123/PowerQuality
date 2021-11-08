package com.cem.powerqualityanalyser.activity;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.SetAdapter;
import com.cem.powerqualityanalyser.tool.LanguageStore;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.PagingItemDecoration;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import serialport.amos.cem.com.libamosserial.ModelAllData;

//用户设置
public class SetupActivity extends BaseActivity implements View.OnClickListener, SetAdapter.SetOnItemClickListner {

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SetAdapter setAdapter;
    private PagingItemDecoration pagingItemDecoration = null;
    private List<Class> activityClass;
    private  WifiManager wifiManager;  // 声明一个对象
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        setAdapter = new SetAdapter(this);
        recyclerView = findViewById(R.id.set_recyclerview);
        layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(setAdapter);
        setAdapter.setSetOnItemClickListner(this);
        // 获取当前apk的WiFi Service
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mLocationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        setAdapter.setWifiBtGpsEnable(wifiManager.isWifiEnabled(),mBluetoothAdapter.isEnabled(),mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));

        setTitle("");
        setTop_icon(R.mipmap.setting_icon);
        initClass();
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void initClass() {
        activityClass = new ArrayList<>();
//        activityClass.add(SetupChildCalucActivity.class);
        activityClass.add(SetupChildWirActivity.class);
        activityClass.add(ConfigScaleActivity.class);
 //       activityClass.add(SetupChildPointActivity.class);
        activityClass.add(SetupChildDateActivity.class);
 //       activityClass.add(SetupChildTrendActivity.class);
        activityClass.add(SetupChildShowActivity.class);
        activityClass.add(SetupChildDeleteActivity.class);
 //       activityClass.add(SetupChildTimerActivity.class);
        activityClass.add(SetupChildInfoActivity.class);
 //       activityClass.add(SetupChildWarnActivity.class);

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
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_botton_zh)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.set_botton_en)));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,""));
        data.add(new BaseBottomAdapterObj(4,""));
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
    final String[] locals = { "zh_rCN","en"};
    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

        switch (obj.getId()) {
            case 0://CN
            case 1://EN
                LanguageStore.setLanguageLocal(SetupActivity.this, locals[obj.getId()]);
                LiveEventBus.get("changeLanguage").post("Message By PostValue: " + new Random().nextInt(100));
                break;
            case 2://FR

                break;
            case 3://DE

                break;

        }
    }


    @Override
    public void onClick(View v) {


    }




    @Override
    public void setItemIndex(int index) {

        int setIndex = 0;
        switch (index){
            case 1:
                setIndex = 0;
                break;
            case 3:
                setIndex = 1;
                break;
            case 5:
                setIndex = 2;
                break;
            case 6:
                setIndex = 3;
                break;
            case 7:
                setIndex = 4;
                break;
            case 8:
                setIndex = 5;
                break;
            case 9:
                setIndex = 6;
                break;
            case 10:
                setIndex = 7;
                break;

        }
        if(index!=0 && index!= 2 && index!= 4)
            intentActivity(setIndex);
    }

    private  long clickTime;

    public void intentActivity(int selectIndex){
        if (System.currentTimeMillis()- clickTime>1000) {
            clickTime=System.currentTimeMillis();
            Intent firstIntent = new Intent(this, activityClass.get(selectIndex));
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(firstIntent);
           overridePendingTransition(0, 0);
        }
    }


    @Override
    public void setItemIndex(int index, boolean toggle) {
        switch (index){
            case 0://wifi
                // 调用函数打开/关闭WiFi,status为boolean变量true/false
                wifiManager.setWifiEnabled(toggle);
                break;
            case 2://bt67
                if(toggle){
                    mBluetoothAdapter.enable();
                }else{
                    mBluetoothAdapter.disable();
                }
                break;
            case 4://GPS
                openGPS(this);
                break;
        }
    }

    public void openGPS(Context context) {
        Intent enableLocate = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(enableLocate, 100);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        /**
         * 增加这个功能会报异常 Activity has been destroyed 待解决
         */
        /*switch (key) {
            case Back:
                if (fragmentIndex != -1) {
                    showDefaultFragment();
                    return true;
                }
                break;
        }*/

        return super.onKeyDown(keyCode, event);
    }



    private  void possKeDown(int keyCode){
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
        switch (key){
            case Back:

                break;

        }
    }

    /**
     * 测试飞行模式
     *
     * @param enable
     */
    private void setAirPlaneModeByRadio(boolean enable) {
        try {
            Class<?> telephonyManager = Class.forName("android.telephony.TelephonyManager");
            Method setRadio = telephonyManager.getMethod("setRadio", boolean.class);
            setRadio.invoke(telephonyManager.getMethod("getDefault").invoke(null), enable);
            if (enable) {
                Method toggleRadioOnOff = telephonyManager.getMethod("toggleRadioOnOff");
                toggleRadioOnOff.invoke(telephonyManager.getMethod("getDefault").invoke(null));
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.e(e.getMessage());
        }
    }


    private void shotDown(){
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    private void reboot(){
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        pm.reboot("");
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
}
