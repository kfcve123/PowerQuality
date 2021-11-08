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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * Config 修改Limits 界面  ------>模块选择----->模块的Limit设置
 */
public class ConfigLimitInfoActivity extends BaseActivity implements View.OnClickListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_limit);
    }

    @Override
    public byte[] getMode() {
        return null;
    }


    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_info_button_menu)));
        data.add(new BaseBottomAdapterObj(1,""));
        data.add(new BaseBottomAdapterObj(2,""));
        data.add(new BaseBottomAdapterObj(3,""));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.set_info_button_ok)));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

    }

    @Override
    public void onClick(View view) {

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