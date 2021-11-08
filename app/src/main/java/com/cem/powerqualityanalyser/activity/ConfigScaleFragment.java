package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 瞬变 参数设置  Volt lever: 0 到 7 ，0.01 一加
 */
public class ConfigScaleFragment extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {


    private AmountViewHorizontal vlot_phase_amountview,amp_phase_amountview,vlot_neutral_amountview,amp_neutral_amountview;

    private int amountValue ,amountValue3= 391;
    private int amountValue2,amountValue4 = 1991;

    private boolean scaleDefault;
    private boolean forbinRight;

    public boolean forbidMoveRight(){
        return forbinRight;
    }

    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        forbinRight = false;
        vlot_phase_amountview.setClickable(false);
        amp_phase_amountview.setClickable(false);
        vlot_neutral_amountview.setClickable(true);
        amp_neutral_amountview.setClickable(true);

        vlot_neutral_amountview.requestFocus();
        vlot_neutral_amountview.setShowAmount(true);
        vlot_neutral_amountview.setFocusable(true);
        amp_neutral_amountview.setFocusable(true);

        vlot_phase_amountview.setFocusable(false);
        amp_phase_amountview.setFocusable(false);

    }


    /**
     * 停用右半边控件
     */
    public void stopRight() {
        forbinRight = true;
        vlot_phase_amountview.setClickable(true);
        amp_phase_amountview.setClickable(true);
        vlot_neutral_amountview.setClickable(false);
        amp_neutral_amountview.setClickable(false);

        vlot_phase_amountview.requestFocus();
        vlot_phase_amountview.setShowAmount(true);
        vlot_phase_amountview.setFocusable(true);
        amp_phase_amountview.setFocusable(true);

        vlot_neutral_amountview.setFocusable(false);
        amp_neutral_amountview.setFocusable(false);

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
        return R.layout.fragment_scope_scale;
    }

    private String[] showItems,showItems2;




    @Override
    public void onInitView() {
        super.onInitView();
        /**
         * LeftView
         */

        vlot_phase_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_phase_amountview);
        amp_phase_amountview = (AmountViewHorizontal) findViewById(R.id.amp_phase_amountview);

        vlot_phase_amountview.setAmountViewLayout(220);
        amp_phase_amountview.setAmountViewLayout(220);

        showItems = new String[990];
        showItems2 = new String[3991];
        for (int i = 0; i < 990; i++) {
            showItems[i] = DataFormatUtil.frontCompWithZore((float) i / 100 + 0.1f, 2) + "kV";
        }
        for (int i = 0; i < 3991; i++) {
            showItems2[i] = DataFormatUtil.frontCompWithZore((float) i / 100 + 0.1f, 2) + "kA";
        }
        vlot_phase_amountview.setDataArray(showItems);
        vlot_phase_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_phase_amountview.setShowValueBg(true);

        amp_phase_amountview.setShowValueBg(true);
        amp_phase_amountview.setOnHorizonalAmountChangeListener(this);
        amp_phase_amountview.setShowAmount(false);
        amp_phase_amountview.setDataArray(showItems2);

        vlot_phase_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    vlot_phase_amountview.setShowAmount(true);
                }else{
                    vlot_phase_amountview.setShowAmount(false);
                }
            }
        });

        amp_phase_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amp_phase_amountview.setShowAmount(true);
                }else{
                    amp_phase_amountview.setShowAmount(false);
                }
            }
        });


        /**
         * RightView
         */

        vlot_neutral_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_neutral_amountview);
        amp_neutral_amountview = (AmountViewHorizontal) findViewById(R.id.amp_neutral_amountview);

        vlot_neutral_amountview.setClickable(false);
        amp_neutral_amountview.setClickable(false);

        vlot_neutral_amountview.setAmountViewLayout(220);
        amp_neutral_amountview.setAmountViewLayout(220);


        vlot_neutral_amountview.setShowValueBg(true);
        vlot_neutral_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_neutral_amountview.setDataArray(showItems);

        vlot_neutral_amountview.setShowAmount(false);
        amp_neutral_amountview.setShowAmount(false);

        amp_neutral_amountview.setShowValueBg(true);
        amp_neutral_amountview.setOnHorizonalAmountChangeListener(this);
        amp_neutral_amountview.setDataArray(showItems2);

        vlot_neutral_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    vlot_neutral_amountview.setShowAmount(true);
                }else{
                    vlot_neutral_amountview.setShowAmount(false);
                }
            }
        });

        amp_neutral_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amp_neutral_amountview.setShowAmount(true);
                }else{
                    amp_neutral_amountview.setShowAmount(false);
                }
            }
        });


        if(config.isConfig_ScopeScale_Default()){
            amountValue = 391;
            amountValue2 = 1991;
            amountValue3 = 391;
            amountValue4 = 1991;
            scaleDefault = true;

        }else{
            amountValue = config.getConfig_Phase_Volt();
            amountValue2 = config.getConfig_Phase_Amp();
            amountValue3 = config.getConfig_Neutral_Volt();
            amountValue4 = config.getConfig_Neutral_Amp();

            scaleDefault = config.isConfig_ScopeScale_Default();

        }

        vlot_phase_amountview.setFocusable(true);
        vlot_phase_amountview.setFocusableInTouchMode(true);
        vlot_phase_amountview.requestFocus();
        vlot_phase_amountview.requestFocusFromTouch();

        vlot_phase_amountview.setAmount(amountValue);
        vlot_phase_amountview.setContent(showItems[amountValue-1]);
        amp_phase_amountview.setAmount(amountValue2);
        amp_phase_amountview.setContent(showItems2[amountValue2-1]);

        vlot_neutral_amountview.setAmount(amountValue3);
        vlot_neutral_amountview.setContent(showItems[amountValue3-1]);
        amp_neutral_amountview.setAmount(amountValue4);
        amp_neutral_amountview.setContent(showItems2[amountValue4-1]);


        stopRight();
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.vlot_phase_amountview:
                amountValue = amount;
                vlot_phase_amountview.setContent(showItems[amount - 1]);
                break;
            case R.id.amp_phase_amountview:
                amountValue2 = amount;
                amp_phase_amountview.setContent(showItems2[amount - 1]);
                break;
            case R.id.vlot_neutral_amountview:
                amountValue3 = amount;
                vlot_neutral_amountview.setContent(showItems[amount - 1]);
                break;
            case R.id.amp_neutral_amountview:
                amountValue4 = amount;
                amp_neutral_amountview.setContent(showItems2[amount - 1]);
                break;

        }
    }


    public void moveCursor(int i) {
        if (vlot_phase_amountview.hasFocus() && forbinRight) {
            if (i > 0)
                vlot_phase_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_phase_amountview.getAmount_increase_horizontal().performClick();
        } else if (amp_phase_amountview.hasFocus() && forbinRight) {

            if (i > 0)
                amp_phase_amountview.getAmount_dncrease_horizontal().performClick();
            else
                amp_phase_amountview.getAmount_increase_horizontal().performClick();

        }else if(vlot_neutral_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                vlot_neutral_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_neutral_amountview.getAmount_increase_horizontal().performClick();
        }else if(amp_neutral_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                amp_neutral_amountview.getAmount_dncrease_horizontal().performClick();
            else
                amp_neutral_amountview.getAmount_increase_horizontal().performClick();
        }
    }

    public void resetSet(){
        stopRight();
        amountValue = 391;
        amountValue2 = 1991;
        amountValue3 = 391;
        amountValue4 = 1991;
        scaleDefault = true;
        config.setConfig_Phase_Volt(amountValue);
        config.setConfig_Phase_Amp(amountValue2);

        config.setConfig_Neutral_Amp(amountValue3);
        config.setConfig_Neutral_Volt(amountValue4);
        config.setConfig_ScopeScale_Default(scaleDefault);


        vlot_phase_amountview.setAmount(amountValue);
        amp_phase_amountview.setAmount(amountValue2);
        vlot_neutral_amountview.setAmount(amountValue3);
        amp_neutral_amountview.setAmount(amountValue4);

        vlot_phase_amountview.setContent(showItems[amountValue - 1]);
        amp_phase_amountview.setContent(showItems2[amountValue2 - 1]);
        vlot_neutral_amountview.setContent(showItems[amountValue3 - 1]);
        amp_neutral_amountview.setContent(showItems2[amountValue4 - 1]);


    }

    public void saveSetting(){
        config.setConfig_Phase_Volt(amountValue);
        config.setConfig_Phase_Amp(amountValue2);
        config.setConfig_Neutral_Volt(amountValue3);
        config.setConfig_Neutral_Amp(amountValue4);
        config.setConfig_ScopeScale_Default(false);

    }


    public void upKey() {

    }

    public void downKey() {

    }
}
