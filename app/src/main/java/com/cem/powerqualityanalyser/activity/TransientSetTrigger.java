package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 瞬变 参数设置  Volt lever: 0 到 7 ，0.01 一加
 */
public class TransientSetTrigger extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    /**
     * 左边控件
     */
    private String[] showItems, showItems2;
    private AmountViewHorizontal vlot_amountview, amp_amountview;
    private LinearLayout transient_set_left;
    private CheckBox transient_voltage_transient, transient_amps;
    private LinearLayout transient_amps_ll;
    private TextView transient_voltage_transient_tv, transient_amps_tv, transient_amps_tv2;
    private ImageView transient_amps_img;
    private TextView voltlevel_tv, amplevel_tv;
    private int amountValue, amountValue2 = 1;
    private boolean voltage_transient;
    private boolean amps_transient;


    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        log.e("-----Right------");
        amp_amountview.setEnabled(false);
        vlot_amountview.setEnabled(false);
        transient_set_left.setEnabled(false);

        amp_amountview.setFocusable(false);
        vlot_amountview.setFocusable(false);
        transient_voltage_transient.setFocusable(false);
        transient_amps.setFocusable(false);

        transient_voltage_transient_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        transient_amps_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        transient_amps_tv2.setTextColor(getResources().getColor(R.color.transient_tv_color));
        voltlevel_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        amplevel_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        transient_amps_img.setImageResource(R.mipmap.amps_icon_unselect);
    }

    /**
     * 停用右半边控件
     */
    public void stopRight(boolean first) {
        log.e("-----Left------");
        if (!first) {
            vlot_amountview.setFocusable(true);
            vlot_amountview.setFocusableInTouchMode(true);
            vlot_amountview.requestFocus();
        }

        vlot_amountview.setFocusable(true);
        amp_amountview.setFocusable(true);
        transient_voltage_transient.setFocusable(true);
        transient_amps.setFocusable(true);

        transient_set_left.setEnabled(true);
        amp_amountview.setEnabled(true);
        vlot_amountview.setEnabled(true);


        transient_voltage_transient_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_tv2.setTextColor(getResources().getColor(R.color.colorblack));
        voltlevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        amplevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_img.setImageResource(R.mipmap.amps_icon_select);

    }


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }

    @Override
    public void onInitViews() {

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_transientsettrigger;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        transient_set_left = (LinearLayout) findViewById(R.id.transient_set_left);

        voltlevel_tv = (TextView) findViewById(R.id.voltlevel_tv);
        amplevel_tv = (TextView) findViewById(R.id.amplevel_tv);

        transient_voltage_transient = (CheckBox) findViewById(R.id.transient_voltage_transient);
        transient_voltage_transient.setFocusable(true);
        transient_amps = (CheckBox) findViewById(R.id.transient_amps);
        transient_amps.setFocusable(false);

        transient_voltage_transient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                voltage_transient = isChecked;
            }
        });

        transient_amps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amps_transient = isChecked;
            }
        });

        amp_amountview = (AmountViewHorizontal) findViewById(R.id.amp_amountview);
        vlot_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_amountview);

        amp_amountview.setAmountViewLayout(250);
        vlot_amountview.setAmountViewLayout(250);
        showItems = new String[700];
        showItems2 = new String[700];
        for (int i = 0; i < 700; i++) {
            showItems[i] = DataFormatUtil.frontCompWithZore((float) i / 100, 2) + "0kV";
            showItems2[i] = DataFormatUtil.frontCompWithZore((float) i / 10, 1) + "A";
        }

        vlot_amountview.setDataArray(showItems);
        vlot_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_amountview.setShowValueBg(true);
        vlot_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    vlot_amountview.setShowAmount(true);
                }else{
                    vlot_amountview.setShowAmount(false);
                }
            }
        });

        amp_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amp_amountview.setShowAmount(true);
                }else{
                    amp_amountview.setShowAmount(false);
                }
            }
        });


        amp_amountview.setDataArray(showItems2);
        amp_amountview.setOnHorizonalAmountChangeListener(this);
        amp_amountview.setShowValueBg(true);
        amp_amountview.setShowAmount(false);

        if(AppConfig.getInstance().isTransientSet_Default()){
            amountValue = 1;
            amountValue2 = 1;
            voltage_transient = true;
            amps_transient = true;
        }else{
            amountValue = config.getTransient_Trigger_VoltLevel();
            amountValue2 = config.getTransient_Trigger_AmpLevel();
            voltage_transient = config.isTransient_Trigger_Voltage();
            amps_transient = config.isTransient_Trigger_AMPS();
        }

        vlot_amountview.setFocusable(true);
        vlot_amountview.setFocusableInTouchMode(true);
        vlot_amountview.requestFocus();
        vlot_amountview.requestFocusFromTouch();


        transient_voltage_transient.setChecked(voltage_transient);
        transient_amps.setChecked(amps_transient);

        vlot_amountview.setAmount(amountValue);
        vlot_amountview.setContent(showItems[amountValue-1]);

        amp_amountview.setAmount(amountValue2);
        amp_amountview.setContent(showItems2[amountValue2-1]);

        transient_amps_ll = (LinearLayout) findViewById(R.id.transient_amps_ll);
        transient_amps_ll.setNextFocusDownId(R.id.transient_amps_ll);

        transient_voltage_transient_tv = (TextView) findViewById(R.id.transient_voltage_transient_tv);
        transient_amps_tv = (TextView) findViewById(R.id.transient_amps_tv);
        transient_amps_tv2 = (TextView) findViewById(R.id.transient_amps_tv2);
        transient_amps_img = (ImageView) findViewById(R.id.transient_amps_img);


        transient_voltage_transient.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    transient_voltage_transient_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_voltage_transient_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                } else {
                    transient_voltage_transient_tv.setBackground(null);
                    transient_voltage_transient_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        transient_amps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    transient_amps_ll.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_amps_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_amps_tv2.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_amps_img.setImageResource(R.mipmap.amps_icon_white);
                } else {
                    transient_amps_ll.setBackground(null);
                    transient_amps_tv.setTextColor(getResources().getColor(R.color.colorblack));
                    transient_amps_tv2.setTextColor(getResources().getColor(R.color.colorblack));
                    transient_amps_img.setImageResource(R.mipmap.amps_icon_select);
                }
            }
        });

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.vlot_amountview:
                amountValue = amount;
                vlot_amountview.setContent(showItems[amountValue - 1]);
                break;
            case R.id.amp_amountview:
                amountValue2 = amount;
                amp_amountview.setContent(showItems2[amountValue2 - 1]);
                break;
        }
    }


    public void moveCursor(int i) {
        if (vlot_amountview.hasFocus()) {
            transient_voltage_transient.setFocusable(false);
            transient_amps.setFocusable(false);

            if (i > 0)
                vlot_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_amountview.getAmount_increase_horizontal().performClick();
        } else if (amp_amountview.hasFocus()) {
            transient_voltage_transient.setFocusable(false);
            transient_amps.setFocusable(false);
            vlot_amountview.setFocusable(false);
            if (i > 0)
                amp_amountview.getAmount_dncrease_horizontal().performClick();
            else
                amp_amountview.getAmount_increase_horizontal().performClick();
        } else if(transient_amps.hasFocus() || transient_voltage_transient.hasFocus()){

            forbinRight = true;
            vlot_amountview.setFocusable(false);
            amp_amountview.setFocusable(false);

        }else{


        }
    }
    private boolean forbinRight;
    public boolean forbidMoveRight(){
        return forbinRight;
    }

    public void upKey() {

    }

    public void downKey() {
        if (amp_amountview.hasFocus()) {
            transient_voltage_transient.setFocusable(true);
        } else if (transient_voltage_transient.hasFocus()) {
            transient_amps.setFocusable(true);
        }
    }

    public void resetSet() {
        amountValue = 1;
        amountValue2 = 1;
        voltage_transient = true;
        amps_transient = true;
        config.setTransient_Trigger_VoltLevel(amountValue);
        config.setTransient_Trigger_AmpLevel(amountValue2);
        config.setTransient_Trigger_Voltage(voltage_transient);
        config.setTransient_Trigger_AMPS(amps_transient);

        vlot_amountview.setAmount(amountValue);
        amp_amountview.setAmount(amountValue2);
        vlot_amountview.setContent(showItems[amountValue - 1]);
        amp_amountview.setContent(showItems2[amountValue2 - 1]);

        transient_voltage_transient.setChecked(voltage_transient);
        transient_amps.setChecked(amps_transient);
    }

    public void saveSetting() {
        config.setTransient_Trigger_VoltLevel(amountValue);
        config.setTransient_Trigger_AmpLevel(amountValue2);
        config.setTransient_Trigger_AMPS(amps_transient);
        config.setTransient_Trigger_Voltage(voltage_transient);

    }

}
