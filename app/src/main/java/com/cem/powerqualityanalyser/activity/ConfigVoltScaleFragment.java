package com.cem.powerqualityanalyser.activity;

import android.view.View;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 *
 */
public class ConfigVoltScaleFragment extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {


    private AmountViewHorizontal vlot_phase_amountview,vlot_neutral_amountview;

    private int amountValue,amountValue2= 1;
    private String[] showItems;

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
        vlot_neutral_amountview.setClickable(true);

        vlot_neutral_amountview.requestFocus();
        vlot_neutral_amountview.setShowAmount(true);
        vlot_neutral_amountview.setFocusable(true);

        vlot_phase_amountview.setFocusable(false);

    }


    /**
     * 停用右半边控件
     */
    public void stopRight() {
        forbinRight = true;
        vlot_phase_amountview.setClickable(true);
        vlot_neutral_amountview.setClickable(false);

        vlot_phase_amountview.requestFocus();
        vlot_phase_amountview.setShowAmount(true);
        vlot_phase_amountview.setFocusable(true);

        vlot_neutral_amountview.setFocusable(false);

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
        return R.layout.fragment_volt_scale;
    }


    @Override
    public void onInitView() {
        super.onInitView();
        /**
         * LeftView
         */

        vlot_phase_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_phase_amountview);

        vlot_phase_amountview.setAmountViewLayout(220);

        showItems = new String[]{"1:1","10:1","100:1","1000:1","10000:1"};

        vlot_phase_amountview.setDataArray(showItems);
        vlot_phase_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_phase_amountview.setShowValueBg(true);


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


        /**
         * RightView
         */

        vlot_neutral_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_neutral_amountview);

        vlot_neutral_amountview.setClickable(false);
        vlot_neutral_amountview.setAmountViewLayout(220);



        vlot_neutral_amountview.setShowValueBg(true);
        vlot_neutral_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_neutral_amountview.setDataArray(showItems);
        vlot_neutral_amountview.setShowAmount(false);

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


        amountValue = config.getConfig_VoltScale_Phase_VoltRatio();
        amountValue2 = config.getConfig_VoltScale_Neutral_VoltRatio();

        vlot_phase_amountview.setFocusable(true);
        vlot_phase_amountview.setFocusableInTouchMode(true);
        vlot_phase_amountview.requestFocus();
        vlot_phase_amountview.requestFocusFromTouch();

        vlot_phase_amountview.setAmount(amountValue);
        vlot_phase_amountview.setContent(showItems[amountValue-1]);


        vlot_neutral_amountview.setAmount(amountValue2);
        vlot_neutral_amountview.setContent(showItems[amountValue2-1]);


        stopRight();
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.vlot_phase_amountview:
                amountValue = amount;
                vlot_phase_amountview.setContent(showItems[amount - 1]);
                break;
            case R.id.vlot_neutral_amountview:
                amountValue2 = amount;
                vlot_neutral_amountview.setContent(showItems[amount - 1]);
                break;
        }
    }


    public void moveCursor(int i) {
        if (vlot_phase_amountview.hasFocus() && forbinRight) {
            if (i > 0)
                vlot_phase_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_phase_amountview.getAmount_increase_horizontal().performClick();
        }else if(vlot_neutral_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                vlot_neutral_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_neutral_amountview.getAmount_increase_horizontal().performClick();

        }
    }

    public void resetSet(){
        stopRight();
        amountValue = 1;
        amountValue2 = 1;

        vlot_phase_amountview.setAmount(amountValue);
        vlot_neutral_amountview.setAmount(amountValue2);

        vlot_phase_amountview.setContent(showItems[amountValue - 1]);
        vlot_neutral_amountview.setContent(showItems[amountValue2 - 1]);



    }

    public void saveSetting(){
        config.setConfig_VoltScale_Phase_VoltRatio(amountValue);
        config.setConfig_VoltScale_Neutral_VoltRatio(amountValue2);

    }


    public void upKey() {

    }

    public void downKey() {

    }
}
