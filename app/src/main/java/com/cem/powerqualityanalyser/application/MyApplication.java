package com.cem.powerqualityanalyser.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;


import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.CrashHandler;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.litepal.LitePal;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.OnSerialPortDataListener;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class MyApplication extends Application implements OnSerialPortDataListener {
    private static Context context;
    private SerialPortHelp serialPower;
    private Intent intent;
    private Timer timer;
    private boolean dcIn;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
 //       CrashHandler.getInstance().init(this);
        LitePal.initialize(this);
        context = getApplicationContext();
        serialPower = new SerialPortHelp();
        boolean open = serialPower.openSerialPort("/dev/ttyS4",9600);
        serialPower.setOnSerialPortDataListener(this);
        byte[] stopOrder = new byte[]{(byte) 0xEF, (byte) 0xEF};
        serialPower.sendData(stopOrder);
        intent = new Intent("BATTERY_CHANGED");
        timer=new Timer();
        timer.schedule(timerTask,0,10000l);
        SerialPortHelp.isOpenLogCat = true;
        SerialPortHelp.isOpenBATLogCat = false;
//        SQLiteDatabase database = Connector.getDatabase();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        LiveEventBus.config().lifecycleObserverAlwaysActive(true);

    }
    private  void init(){
        AppConfig.getInstance().Init(this);
    }
    /**
     * 获取全局上下文*/
    public static Context getContext() {
        return context;
    }

    public SerialPortHelp getSerialPower(){
        return serialPower;
    }

    public Timer getPowerTimer(){
        return timer;
    }

    private long powerCute = 0;
    @Override
    public void onBATDataReceived(final float v, final float v1,boolean isCharging) {
//        log.e("------电压------" + v);
//        log.e("------电量------" + v1);
//        log.e("------状态------" + isCharging);

        this.vValue = v;
        this.v1Value = v1;
        this.dcIn = isCharging;
    }

    private float vValue = 0f;
    private float v1Value = 0f;

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(v1Value>=0) {
                intent.putExtra("powerlevel", (int) v1Value);
                intent.putExtra("voltage",vValue);
                intent.putExtra("powerdc",dcIn);
                intent.putExtra("powerstatus",DataFormatUtil.formatValue(v1Value,2));
                sendBroadcast(intent);
            }
        }
    };

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