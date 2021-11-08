package com.cem.powerqualityanalyser.activity;

import android.app.admin.DevicePolicyManager;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.application.MyApplication;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.TextImageView;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public class ConfigActiity extends BaseActivity implements View.OnClickListener{

    private TextView config_tv7,config_tv6,config_tv5,config_tv4,config_tv3,config_tv2,config_tv1;
    private boolean isFocus1,isFocus2,isFocus3,isFocus4,isFocus5,isFocus6,isFocus7,isFocus8;
    private ImageView config_wir_imv;
    private int wir_index = 0;
    private int wir_right_index = 0;
    private Integer[] strRes,resours1,resours2,resours3,resours4,resours5,resours6,resours7;
    private Integer[] colorRes;
    private int cirColorIndex1,cirColorIndex2,cirColorIndex3,cirColorIndex4;
    private ImageView set_wir_cir1,set_wir_cir2,set_wir_cir3,set_wir_cir4,set_wir_cir5,set_wir_cir6,set_wir_cir7,set_wir_cir8;
    private int config_nomilal = 0;
    private String config_vnom_value;

    private TextView config_tv8,config_tv9,config_tv10,config_tv11,config_tv12,config_tv13,config_tv14,config_tv15;

    private boolean manualset = true;

    private LinearLayout config_ll2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configset_layout);
        setTitle(R.string.config_set);
        colorRes = new Integer[]{R.mipmap.set_wir_color1,R.mipmap.set_wir_color2,R.mipmap.set_wir_color3,R.mipmap.set_wir_color4,R.mipmap.set_wir_color5};
 //        resours1 = new Integer[]{R.mipmap.set_wir351,R.mipmap.set_wir352,R.mipmap.set_wir353,R.mipmap.set_wir354};
//        resours2 = new Integer[]{R.mipmap.set_wir341,R.mipmap.set_wir342,R.mipmap.set_wir343,R.mipmap.set_wir344};
//        resours3 = new Integer[]{R.mipmap.set_wir331,R.mipmap.set_wir332,R.mipmap.set_wir333,R.mipmap.set_wir334};
        resours1 = new Integer[]{R.mipmap.set_wir_2_a_element_a,R.mipmap.set_wir_2_a_element_b};
        resours2 = new Integer[]{R.mipmap.set_wir_2_b_element_a,R.mipmap.set_wir_2_b_element_b,R.mipmap.set_wir_2_b_element_c};
        resours3 = new Integer[]{R.mipmap.set_wir_3_delta_a,R.mipmap.set_wir_3_delta_b,R.mipmap.set_wir_3_delta_c};
        resours4 = new Integer[]{R.mipmap.set_wir_3_hight_leg_a,R.mipmap.set_wir_3_hight_leg_b,R.mipmap.set_wir_3_hight_leg_c};
        resours5 = new Integer[]{R.mipmap.set_wir_3_it_a,R.mipmap.set_wir_3_it_b,R.mipmap.set_wir_3_it_c};
        resours6 = new Integer[]{R.mipmap.set_wir_3_open_leg_a,R.mipmap.set_wir_3_open_leg_b,R.mipmap.set_wir_3_open_leg_c};
        resours7 = new Integer[]{R.mipmap.set_wir_3_wye_a,R.mipmap.set_wir_3_wye_b,R.mipmap.set_wir_3_wye_c};

        initView();

        set_wir_cir1 = findViewById(R.id.set_wir_cir1);
        set_wir_cir2 = findViewById(R.id.set_wir_cir2);
        set_wir_cir3 = findViewById(R.id.set_wir_cir3);
        set_wir_cir4 = findViewById(R.id.set_wir_cir4);
        set_wir_cir5 = findViewById(R.id.set_wir_cir5);
        set_wir_cir6 = findViewById(R.id.set_wir_cir6);
        set_wir_cir7 = findViewById(R.id.set_wir_cir7);
        set_wir_cir8 = findViewById(R.id.set_wir_cir8);

    }


    private void getClampConfig(){
        LiveEventBus
                .get("updateconfig", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable String s) {
                      updateConfig();
                    }
                });
    }


    @Override
    public byte[] getMode() {
        return null;
    }

    private void setWirImage(){
        wir_index = config.getSet_Wir();

        String[] showItem2=getString(R.string.set_wir_item).split(",");
        config_tv4.setText(showItem2[wir_index]);

        wir_right_index = config.getSetup_Wir_Right_Index();
        switch (wir_index){
            case 9:
                config_wir_imv.setBackgroundResource(R.mipmap.set_wir_neutral);
                break;

            case 8:
                config_wir_imv.setBackgroundResource(R.mipmap.set_wir_no_neutral);
                break;

            case 7:
                config_wir_imv.setBackgroundResource(R.mipmap.set_wir_split_phase);
                break;


            case 6:
                config_wir_imv.setBackgroundResource(resours1[wir_right_index]);
                break;
            case 5:
                config_wir_imv.setBackgroundResource(resours2[wir_right_index]);
                break;
            case 4:
                config_wir_imv.setBackgroundResource(resours3[wir_right_index]);
                break;

            case 3:
                config_wir_imv.setBackgroundResource(resours4[wir_right_index]);

                break;
            case 2:
                config_wir_imv.setBackgroundResource(resours5[wir_right_index]);
                break;

            case 1:
                config_wir_imv.setBackgroundResource(resours6[wir_right_index]);
                break;
            case 0:
                config_wir_imv.setBackgroundResource(resours7[wir_right_index]);
                break;

        }

    }

    private String[] neutralshowItems;
    private String[] neutralshowItems3;
    private String[] neutralshowItems5;

    private void updateConfig(){
        config_tv8.setText(neutralshowItems[config.getConfig_AmpScale_Phase_AmpClamp()-1]);
        config_tv9.setText(neutralshowItems[config.getConfig_AmpScale_Neutral_AmpClamp()-1]);
        config_tv10.setText(neutralshowItems3[config.getConfig_AmpScale_Phase_Norminal()-1]);
        config_tv11.setText(neutralshowItems3[config.getConfig_AmpScale_Neutral_Norminal()-1]);
        config_tv14.setText(neutralshowItems5[config.getConfig_AmpScale_Phase_Ratio()-1]);
        config_tv15.setText(neutralshowItems5[config.getConfig_AmpScale_Neutral_Ratio()-1]);

        config_tv12.setText(neutralshowItems5[config.getConfig_VoltScale_Phase_VoltRatio()-1]);
        config_tv13.setText(neutralshowItems5[config.getConfig_VoltScale_Neutral_VoltRatio()-1]);

    }

    private void initView() {
        config_ll2 = findViewById(R.id.config_ll2);

        config_tv1 = findViewById(R.id.config_tv1);
        config_tv2 = findViewById(R.id.config_tv2);
        config_tv3 = findViewById(R.id.config_tv3);
        config_tv4 = findViewById(R.id.config_tv4);
        config_tv5 = findViewById(R.id.config_tv5);
        config_tv6 = findViewById(R.id.config_tv6);
        config_tv7 = findViewById(R.id.config_tv7);

        config_tv8 = findViewById(R.id.config_tv8);
        config_tv9 = findViewById(R.id.config_tv9);
        config_tv10 = findViewById(R.id.config_tv10);
        config_tv11 = findViewById(R.id.config_tv11);
        config_tv12 = findViewById(R.id.config_tv12);
        config_tv13 = findViewById(R.id.config_tv13);
        config_tv14 = findViewById(R.id.config_tv14);
        config_tv15 = findViewById(R.id.config_tv15);

        neutralshowItems = new String[]{"1 V/A","100 mV/A","10 mV/A","1 mV/A","0.1 mV/A"};
        neutralshowItems3 = new String[10000];
        for (int i = 0; i < 10000; i++) {
            neutralshowItems3[i] = (i +1)+ "A";
        }
        neutralshowItems5 = new String[]{"1:1","10:1","100:1","1000:1","10000:1",};

        updateConfig();


        config_tv1.clearFocus();
//        config_tv1.setBackground(getResources().getDrawable(R.mipmap.config_tv_bg));
//        config_tv1.setTextColor(getResources().getColor(R.color.set_text_color_choose));


        config_tv6.setText(config_vnom_value);
        config_wir_imv = findViewById(R.id.config_wir_imv);
 //       setWirImage();

        config_tv8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv8.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv9.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv10.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv11.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv12.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv13.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv14.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
                    config_tv15.setBackground(getResources().getDrawable(R.drawable.list_corner_round));
//                    config_tv8.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv8.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv9.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv10.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv11.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv12.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv13.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv14.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    config_tv15.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus8 = true;
                    isFocus1 = false;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus7 = false;

                }else{
                    config_tv8.setBackground(null);
                    config_tv8.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv9.setBackground(null);
                    config_tv9.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv10.setBackground(null);
                    config_tv10.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv11.setBackground(null);
                    config_tv11.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv12.setBackground(null);
                    config_tv12.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv13.setBackground(null);
                    config_tv13.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv14.setBackground(null);
                    config_tv14.setTextColor(getResources().getColor(R.color.set_text_color));
                    config_tv15.setBackground(null);
                    config_tv15.setTextColor(getResources().getColor(R.color.set_text_color));

                    isFocus8 = false;
                }
            }
        });


        config_tv1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv1.setBackground(getResources().getDrawable(R.mipmap.config_tv_bg));
                    config_tv1.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus1 = true;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    config_tv1.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv1.setTextColor(getResources().getColor(R.color.set_text_color));
                    isFocus1 = false;
                }
            }
        });
        config_tv2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv2.setBackground(getResources().getDrawable(R.mipmap.config_tv_bg));
 //                   config_tv2.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv2.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus2 = true;
                    isFocus1 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    isFocus2 = false;
                    config_tv2.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv2.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        config_tv3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv3.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv3.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus3 = true;
                    isFocus2 = false;
                    isFocus1 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    isFocus3 = false;
                    config_tv3.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv3.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        config_tv4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv4.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv4.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus4 = true;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus1 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    isFocus4 = false;
                    config_tv4.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv4.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        config_tv5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv5.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv5.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus5 = true;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus1 = false;
                    isFocus6 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    isFocus5 = false;
                    config_tv5.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv5.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        config_tv6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv6.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv6.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus6 = true;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus1 = false;
                    isFocus7 = false;
                    isFocus8 = false;
                }else{
                    isFocus6 = false;
                    config_tv6.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv6.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        config_tv7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_tv7.setBackgroundResource(R.mipmap.config_tv_bg);
                    config_tv7.setTextColor(getResources().getColor(R.color.set_text_color_choose));
                    isFocus7 = true;
                    isFocus2 = false;
                    isFocus3 = false;
                    isFocus4 = false;
                    isFocus5 = false;
                    isFocus6 = false;
                    isFocus1 = false;
                    isFocus8 = false;
                }else{
                    isFocus7 = false;
                    config_tv7.setBackground(getResources().getDrawable(R.mipmap.config_tv_nobg));
                    config_tv7.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                config_tv2.setVisibility(View.VISIBLE);
            }
        },1000l);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new TimeThread().start();
        setWirImage();
        getClampConfig();
        cirColorIndex1 = config.getSetup_Show_Color_VL1();
        cirColorIndex2 = config.getSetup_Show_Color_VL2();
        cirColorIndex3 = config.getSetup_Show_Color_VL3();
        cirColorIndex4 = config.getSetup_Show_Color_VN();
        config_nomilal = config.getConfig_nominal();
        config_vnom_value = config.getConfig_vnom_value();

        switch (config_nomilal){
            case 0:
                config_tv5.setText(R.string.config_nominal_frequency_50hz);
                break;
            case 1:
                config_tv5.setText(R.string.config_nominal_frequency_60hz);
                break;
            case 2:
                config_tv5.setText(R.string.config_nominal_frequency_400hz);
                break;
        }

        config_tv6.setText(config_vnom_value);
        set_wir_cir1.setImageResource(colorRes[cirColorIndex1-1]);
        set_wir_cir2.setImageResource(colorRes[cirColorIndex2-1]);
        set_wir_cir3.setImageResource(colorRes[cirColorIndex3-1]);
        set_wir_cir4.setImageResource(colorRes[cirColorIndex4-1]);

        set_wir_cir5.setImageResource(colorRes[cirColorIndex1-1]);
        set_wir_cir6.setImageResource(colorRes[cirColorIndex2-1]);
        set_wir_cir7.setImageResource(colorRes[cirColorIndex3-1]);
        set_wir_cir8.setImageResource(colorRes[cirColorIndex4-1]);

    }

    @Override
    public void onBATDataReceived(float v, float v1,boolean dcIn) {

    }

    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {
        //Log.e("上层收到的对象为：","长度为："+list.size());

    }

    @Override
    public void onDataReceivedList(List list) {

    }



    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1; //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long time = System.currentTimeMillis();
                    Date date = new Date(time);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                    config_tv2.setText(format.format(date));
                    config_tv3.setText(format2.format(date));
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!manualset) {
            possKeyDown(keyCode);
        }else{
            MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
            if(key == MeterKeyValue.Enter || key == MeterKeyValue.Up || key == MeterKeyValue.Down || key == MeterKeyValue.Left || key == MeterKeyValue.Right){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void possKeyDown(int keyCode) {
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
//        log.e("=========" + key.toString());
        Intent intent = new Intent();
            switch (key) {
                case Enter:
                    if (isFocus1) {
                        intent.setClass(ConfigActiity.this, SetupChildDateActivity.class);
                        startActivity(intent);
                    } else if (isFocus2) {
                        intent.setClass(ConfigActiity.this, SetupChildDateActivity.class);
                        startActivity(intent);
                    } else if (isFocus3) {
                        intent.setClass(ConfigActiity.this, SetupChildDateActivity.class);
                        startActivity(intent);
                    } else if (isFocus4) {
                        intent.setClass(ConfigActiity.this, SetupChildWirActivity.class);
                        startActivity(intent);
                    } else if (isFocus5) {
                        intent.setClass(ConfigActiity.this, ConfigHzInfoActivity.class);
                        startActivity(intent);
                    } else if (isFocus6) {
                        intent.setClass(ConfigActiity.this, ConfigVnomInfoActivity.class);
                        startActivity(intent);
                    } else if (isFocus7) {
                        intent.setClass(ConfigActiity.this, ConfigLimitInfoActivity.class);
                        startActivity(intent);
                    } else if (isFocus8) {
                        intent.setClass(ConfigActiity.this, ConfigAmpsInfoActivity.class);
                        startActivity(intent);
                    }
                    break;
                case Left:

                    break;

                case Right:

                    break;
                case Up:

                    break;

                case Down:

                    break;
            }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.config_bottom_viewconfig)));
        data.add(new BaseBottomAdapterObj(1,""));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.config_bottom_scope_scale)));
        data.add(new BaseBottomAdapterObj(3,null,Res2String(R.string.config_bottom_manualset),Res2String(R.string.config_bottom_back)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.config_bottom_ok)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        Intent intent = new Intent();
        switch (obj.getId()){
            case 0:
            //调ViewConfig
                intent.setClass(ConfigActiity.this,SetupChildWirActivity.class);
                startActivity(intent);
                break;
            case 2://SCALE切换
                intent.setClass(ConfigActiity.this,ConfigScaleActivity.class);
                startActivity(intent);
                break;
            case 3:
                setViewShow(obj.getSwitchindex());
                break;
            case 4:
                //跳OK
                if(!manualset) {
                    if(isFocus1){
                        intent.setClass(ConfigActiity.this, SetupChildDateActivity.class);
                        startActivity(intent);
                    }else if(isFocus2){
                        intent.setClass(ConfigActiity.this,SetupChildDateActivity.class);
                        startActivity(intent);
                    }else if(isFocus3){
                        intent.setClass(ConfigActiity.this,SetupChildDateActivity.class);
                        startActivity(intent);
                    }else if(isFocus4){
                        intent.setClass(ConfigActiity.this,SetupChildWirActivity.class);
                        startActivity(intent);
                    }else if(isFocus5){
                        intent.setClass(ConfigActiity.this,ConfigHzInfoActivity.class);
                        startActivity(intent);
                    }else if(isFocus6){
                        intent.setClass(ConfigActiity.this,ConfigVnomInfoActivity.class);
                        startActivity(intent);
                    }else if(isFocus7){
                        intent.setClass(ConfigActiity.this,ConfigLimitInfoActivity.class);
                        startActivity(intent);
                    }else if(isFocus8){
                        intent.setClass(ConfigActiity.this,ConfigAmpsInfoActivity.class);
                        startActivity(intent);
                    }
                }else{
                    intent.setClass(ConfigActiity.this, MainActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    private void setViewShow(int index){
        if (index==1){
            manualset = true;
            config_ll2.requestFocus();

        }else {
            manualset = false;
            config_tv1.requestFocus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if(getApplication()!=null && ((MyApplication)getApplication()).getSerialPower()!=null) {
            ((MyApplication) getApplication()).getSerialPower().closeSerialPort();
            if(((MyApplication) getApplication()).getPowerTimer()!=null)
                ((MyApplication) getApplication()).getPowerTimer().cancel();
        }*/
    }
}
