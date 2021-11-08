package com.cem.powerqualityanalyser.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.view.ConfigChooseView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * Config 修改Vnom界面
 */
public class ConfigVnomInfoActivity extends BaseActivity implements View.OnClickListener {


    private ConfigChooseView configchooseview11,configchooseview12,configchooseview13,configchooseview14,configchooseview15,configchooseview16;
    private ConfigChooseView configchooseview21,configchooseview22,configchooseview23,configchooseview24,configchooseview25,configchooseview26;
    private ConfigChooseView configchooseview31,configchooseview32,configchooseview33,configchooseview34,configchooseview35,configchooseview36;
    private ConfigChooseView configchooseview41,configchooseview42,configchooseview43,configchooseview44,configchooseview45,configchooseview46;
    private ArrayList<ConfigChooseView> configChooseViews;
    private int config_vnom = 0;
    private String[] vnomArray;
    private String config_vnom_value = "58V";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_vnom);
        config_vnom = config.getConfig_vnom();
        config_vnom_value = config.getConfig_vnom_value();

        configChooseViews = new ArrayList<>();
        configchooseview11 = findViewById(R.id.configchooseview11);
        configchooseview12  = findViewById(R.id.configchooseview12);
        configchooseview13 = findViewById(R.id.configchooseview13);
        configchooseview14  = findViewById(R.id.configchooseview14);
        configchooseview15 = findViewById(R.id.configchooseview15);
        configchooseview16  = findViewById(R.id.configchooseview16);

        configchooseview21 = findViewById(R.id.configchooseview21);
        configchooseview22  = findViewById(R.id.configchooseview22);
        configchooseview23 = findViewById(R.id.configchooseview23);
        configchooseview24  = findViewById(R.id.configchooseview24);
        configchooseview25 = findViewById(R.id.configchooseview25);
        configchooseview26  = findViewById(R.id.configchooseview26);

        configchooseview31 = findViewById(R.id.configchooseview31);
        configchooseview32  = findViewById(R.id.configchooseview32);
        configchooseview33 = findViewById(R.id.configchooseview33);
        configchooseview34  = findViewById(R.id.configchooseview34);
        configchooseview35 = findViewById(R.id.configchooseview35);
        configchooseview36  = findViewById(R.id.configchooseview36);

        configchooseview41 = findViewById(R.id.configchooseview41);
        configchooseview42  = findViewById(R.id.configchooseview42);
        configchooseview43 = findViewById(R.id.configchooseview43);
        configchooseview44  = findViewById(R.id.configchooseview44);
        configchooseview45 = findViewById(R.id.configchooseview45);
        configchooseview46  = findViewById(R.id.configchooseview46);

        vnomArray = new String[]{"58V", "100V", "115V", "120V", "133V", "139V", "200V", "230V", "277V", "347V", "400V", "440V", "100V", "199V", "230V", "340V", "400V", "690V", "179V", "200V", "240V", "390V", "600V", "900V"};

//        configchooseview11.setText("58V");
//        configchooseview12.setText("100V");
//        configchooseview13.setText("115V");
//        configchooseview14.setText("120V");
//        configchooseview15.setText("133V");
//        configchooseview16.setText("139V");
//
//        configchooseview21.setText("200V");
//        configchooseview22.setText("230V");
//        configchooseview23.setText("277V");
//        configchooseview24.setText("347V");
//        configchooseview25.setText("400V");
//        configchooseview26.setText("440V");
//
//        configchooseview31.setText("100V");
//        configchooseview32.setText("199V");
//        configchooseview33.setText("230V");
//        configchooseview34.setText("340V");
//        configchooseview35.setText("400V");
//        configchooseview36.setText("690V");
//
//        configchooseview41.setText("179V");
//        configchooseview42.setText("200V");
//        configchooseview43.setText("240V");
//        configchooseview44.setText("390V");
//        configchooseview45.setText("600V");
//        configchooseview46.setText("900V");


        configchooseview11.setOnClickListener(this);
        configchooseview12.setOnClickListener(this);
        configchooseview13.setOnClickListener(this);
        configchooseview14.setOnClickListener(this);
        configchooseview15.setOnClickListener(this);
        configchooseview16.setOnClickListener(this);

        configchooseview21.setOnClickListener(this);
        configchooseview22.setOnClickListener(this);
        configchooseview23.setOnClickListener(this);
        configchooseview24.setOnClickListener(this);
        configchooseview25.setOnClickListener(this);
        configchooseview26.setOnClickListener(this);

        configchooseview31.setOnClickListener(this);
        configchooseview32.setOnClickListener(this);
        configchooseview33.setOnClickListener(this);
        configchooseview34.setOnClickListener(this);
        configchooseview35.setOnClickListener(this);
        configchooseview36.setOnClickListener(this);

        configchooseview41.setOnClickListener(this);
        configchooseview42.setOnClickListener(this);
        configchooseview43.setOnClickListener(this);
        configchooseview44.setOnClickListener(this);
        configchooseview45.setOnClickListener(this);
        configchooseview46.setOnClickListener(this);

        configChooseViews.add(configchooseview11);
        configChooseViews.add(configchooseview12);
        configChooseViews.add(configchooseview13);
        configChooseViews.add(configchooseview14);
        configChooseViews.add(configchooseview15);
        configChooseViews.add(configchooseview16);

        configChooseViews.add(configchooseview21);
        configChooseViews.add(configchooseview22);
        configChooseViews.add(configchooseview23);
        configChooseViews.add(configchooseview24);
        configChooseViews.add(configchooseview25);
        configChooseViews.add(configchooseview26);

        configChooseViews.add(configchooseview31);
        configChooseViews.add(configchooseview32);
        configChooseViews.add(configchooseview33);
        configChooseViews.add(configchooseview34);
        configChooseViews.add(configchooseview35);
        configChooseViews.add(configchooseview36);

        configChooseViews.add(configchooseview41);
        configChooseViews.add(configchooseview42);
        configChooseViews.add(configchooseview43);
        configChooseViews.add(configchooseview44);
        configChooseViews.add(configchooseview45);
        configChooseViews.add(configchooseview46);

        configChooseViews.get(config_vnom).setChooseShow(true);
        setConfigChooseViewValue(vnomArray);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void setConfigChooseViewValue(String[] vnomArray) {
        for(int i = 0;i<configChooseViews.size();i++) {
            configChooseViews.get(i).setText(vnomArray[i]);
        }
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,""));
        data.add(new BaseBottomAdapterObj(1,""));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,""));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.back)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()){
            case 4:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.configchooseview11:
                showConfigChoose(0);
                break;
            case R.id.configchooseview12:
                showConfigChoose(1);
                break;

            case R.id.configchooseview13:
                showConfigChoose(2);
                break;

            case R.id.configchooseview14:
                showConfigChoose(3);
                break;
            case R.id.configchooseview15:
                showConfigChoose(4);
                break;

            case R.id.configchooseview16:
                showConfigChoose(5);
                break;

            case R.id.configchooseview21:
                showConfigChoose(6);
                break;
            case R.id.configchooseview22:
                showConfigChoose(7);
                break;

            case R.id.configchooseview23:
                showConfigChoose(8);
                break;

            case R.id.configchooseview24:
                showConfigChoose(9);
                break;
            case R.id.configchooseview25:
                showConfigChoose(10);
                break;

            case R.id.configchooseview26:
                showConfigChoose(11);
                break;

            case R.id.configchooseview31:
                showConfigChoose(12);
                break;
            case R.id.configchooseview32:
                showConfigChoose(13);
                break;

            case R.id.configchooseview33:
                showConfigChoose(14);
                break;

            case R.id.configchooseview34:
                showConfigChoose(15);
                break;
            case R.id.configchooseview35:
                showConfigChoose(16);
                break;

            case R.id.configchooseview36:
                showConfigChoose(17);
                break;

            case R.id.configchooseview41:
                showConfigChoose(18);
                break;
            case R.id.configchooseview42:
                showConfigChoose(19);
                break;

            case R.id.configchooseview43:
                showConfigChoose(20);
                break;

            case R.id.configchooseview44:
                showConfigChoose(21);
                break;
            case R.id.configchooseview45:
                showConfigChoose(22);
                break;

            case R.id.configchooseview46:
                showConfigChoose(23);
                break;
        }

    }

    private void showConfigChoose(int index) {
        for(int i = 0;i<configChooseViews.size();i++){
            if(i == index){
                configChooseViews.get(i).setChooseShow(true);
                config.setConfig_vnom(i);
                config.setConfig_vnom_value(vnomArray[i]);
            }else{
                configChooseViews.get(i).setChooseShow(false);
            }
        }
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