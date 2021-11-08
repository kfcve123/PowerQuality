package com.cem.powerqualityanalyser.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Config 修改Hz界面
 */
public class ConfigHzInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView lefttext1,lefttext2,lefttext3;
    private ImageView leftchoose1,leftchoose2,leftchoose3;
    private int config_nominal = 0;
    private LinearLayout left_ll3,left_ll2,left_ll1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_hz);
        config_nominal = config.getConfig_nominal();

        lefttext1 = findViewById(R.id.lefttext1);
        lefttext2 = findViewById(R.id.lefttext2);
        lefttext3 = findViewById(R.id.lefttext3);
        leftchoose1 = findViewById(R.id.leftchoose1);
        leftchoose2 = findViewById(R.id.leftchoose2);
        leftchoose3 = findViewById(R.id.leftchoose3);
        left_ll3 = findViewById(R.id.left_ll3);
        left_ll2 = findViewById(R.id.left_ll2);
        left_ll1 = findViewById(R.id.left_ll1);
        left_ll3.setVisibility(View.INVISIBLE);

        left_ll3.setOnClickListener(this);
        left_ll2.setOnClickListener(this);
        left_ll1.setOnClickListener(this);

        switch (config_nominal){
            case 0:
                left_ll1.requestFocus();
                leftchoose1.setImageResource(R.mipmap.config_choose_on);
                leftchoose2.setImageResource(R.mipmap.config_choose_no);
                leftchoose3.setImageResource(R.mipmap.config_choose_no);
                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.colorwhite));
                lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));

                break;
            case 1:
                left_ll2.requestFocus();
                leftchoose2.setImageResource(R.mipmap.config_choose_on);
                leftchoose1.setImageResource(R.mipmap.config_choose_no);
                leftchoose3.setImageResource(R.mipmap.config_choose_no);
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext2.setTextColor(getResources().getColor(R.color.colorwhite));
                lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));

                break;

            case 2:
                left_ll3.requestFocus();
                leftchoose3.setImageResource(R.mipmap.config_choose_on);
                leftchoose2.setImageResource(R.mipmap.config_choose_no);
                leftchoose1.setImageResource(R.mipmap.config_choose_no);
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext3.setTextColor(getResources().getColor(R.color.colorwhite));
                break;
        }


        left_ll1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_nominal = 0;
                    config.setConfig_nominal(config_nominal);
                    leftchoose1.setImageResource(R.mipmap.config_choose_on);
                    leftchoose2.setImageResource(R.mipmap.config_choose_no);
                    leftchoose3.setImageResource(R.mipmap.config_choose_no);
                    lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                    lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext1.setTextColor(getResources().getColor(R.color.colorwhite));
                    lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                    lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        left_ll2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_nominal = 1;
                    config.setConfig_nominal(config_nominal);
                    leftchoose2.setImageResource(R.mipmap.config_choose_on);
                    leftchoose1.setImageResource(R.mipmap.config_choose_no);
                    leftchoose3.setImageResource(R.mipmap.config_choose_no);
                    lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                    lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                    lefttext2.setTextColor(getResources().getColor(R.color.colorwhite));
                    lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });

        left_ll3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    config_nominal = 2;
                    config.setConfig_nominal(config_nominal);
                    leftchoose3.setImageResource(R.mipmap.config_choose_on);
                    leftchoose2.setImageResource(R.mipmap.config_choose_no);
                    leftchoose1.setImageResource(R.mipmap.config_choose_no);
                    lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                    lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                    lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                    lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                    lefttext3.setTextColor(getResources().getColor(R.color.colorwhite));
                }
            }
        });


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
            case 4://Back
                destoryself();
                break;
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_ll1:
                config_nominal = 0;
                config.setConfig_nominal(config_nominal);
                leftchoose1.setImageResource(R.mipmap.config_choose_on);
                leftchoose2.setImageResource(R.mipmap.config_choose_no);
                leftchoose3.setImageResource(R.mipmap.config_choose_no);

                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.colorwhite));
                lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));
                break;
            case R.id.left_ll2:
                config_nominal = 1;
                config.setConfig_nominal(config_nominal);
                leftchoose2.setImageResource(R.mipmap.config_choose_on);
                leftchoose1.setImageResource(R.mipmap.config_choose_no);
                leftchoose3.setImageResource(R.mipmap.config_choose_no);
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext2.setTextColor(getResources().getColor(R.color.colorwhite));
                lefttext3.setTextColor(getResources().getColor(R.color.set_text_color));
                break;

            case R.id.left_ll3:
                config_nominal  = 2;
                config.setConfig_nominal(config_nominal);
                leftchoose3.setImageResource(R.mipmap.config_choose_on);
                leftchoose2.setImageResource(R.mipmap.config_choose_no);
                leftchoose1.setImageResource(R.mipmap.config_choose_no);
                lefttext3.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
                lefttext1.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext2.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
                lefttext1.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext2.setTextColor(getResources().getColor(R.color.set_text_color));
                lefttext3.setTextColor(getResources().getColor(R.color.colorwhite));
                break;
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