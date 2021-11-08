package com.cem.powerqualityanalyser.activity;

import android.view.View;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.Random;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 瞬变 参数设置  Volt lever: 0 到 7 ，0.01 一加
 */
public class ConfigAmpScaleFragment extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {


    private AmountViewHorizontal phase_ampclamp_amountview,phase_clamp_range_amountview,phase_nominal_range_amountview,phase_sen_amountview,phase_ratio_amountview;
    private AmountViewHorizontal neutral_ampclamp_amountview,neutral_clamp_range_amountview,neutral_nominal_range_amountview,neutral_sen_amountview,neutral_ratio_amountview;
    private int phaseAmountValue  = 1;
    private int phaseAmountValue3  = 3000;
    private int phaseAmountValue4  = 1;
    private int phaseAmountValue5  = 1;

    private int neutralAmountValue = 1;
    private int neutralAmountValue3 = 3000;
    private int neutralAmountValue4 = 1;
    private int neutralAmountValue5 = 1;

    private String[] phaseshowItems,phaseshowItems3,phaseshowItems4,phaseshowItems5;
//    private String[] neutralshowItems,neutralshowItems3,neutralshowItems4,neutralshowItems5;

    private boolean forbinRight;

    public boolean forbidMoveRight(){
        return forbinRight;
    }

    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        forbinRight = false;
        phase_ampclamp_amountview.setClickable(false);
        phase_clamp_range_amountview.setClickable(false);
        phase_nominal_range_amountview.setClickable(false);
        phase_sen_amountview.setClickable(false);
        phase_ratio_amountview.setClickable(false);

        phase_ampclamp_amountview.setShowAmount(false);
        phase_clamp_range_amountview.setShowAmount(false);
        phase_nominal_range_amountview.setShowAmount(false);
        phase_sen_amountview.setShowAmount(false);
        phase_ratio_amountview.setShowAmount(false);


        neutral_ampclamp_amountview.setClickable(true);
        neutral_clamp_range_amountview.setClickable(true);
        neutral_nominal_range_amountview.setClickable(true);
        neutral_sen_amountview.setClickable(true);
        neutral_ratio_amountview.setClickable(true);

        neutral_ampclamp_amountview.requestFocus();
        neutral_ampclamp_amountview.setShowAmount(true);

        neutral_ampclamp_amountview.setFocusable(true);
        neutral_clamp_range_amountview.setFocusable(true);
        neutral_nominal_range_amountview.setFocusable(true);
        neutral_sen_amountview.setFocusable(true);
        neutral_ratio_amountview.setFocusable(true);


        phase_ampclamp_amountview.setFocusable(false);
        phase_clamp_range_amountview.setFocusable(false);
        phase_nominal_range_amountview.setFocusable(false);
        phase_sen_amountview.setFocusable(false);
        phase_ratio_amountview.setFocusable(false);

    }


    /**
     * 停用右半边控件
     */
    public void stopRight() {
        forbinRight = true;

        phase_ampclamp_amountview.setClickable(true);
        phase_clamp_range_amountview.setClickable(true);
        phase_nominal_range_amountview.setClickable(true);
        phase_sen_amountview.setClickable(true);
        phase_ratio_amountview.setClickable(true);

        neutral_ampclamp_amountview.setClickable(false);
        neutral_clamp_range_amountview.setClickable(false);
        neutral_nominal_range_amountview.setClickable(false);
        neutral_sen_amountview.setClickable(false);
        neutral_ratio_amountview.setClickable(false);


        phase_ampclamp_amountview.requestFocus();
        phase_ampclamp_amountview.setShowAmount(true);

        phase_ampclamp_amountview.setFocusable(true);
        phase_clamp_range_amountview.setFocusable(true);
        phase_nominal_range_amountview.setFocusable(true);
        phase_sen_amountview.setFocusable(true);
        phase_ratio_amountview.setFocusable(true);

        neutral_ampclamp_amountview.setFocusable(false);
        neutral_clamp_range_amountview.setFocusable(false);
        neutral_nominal_range_amountview.setFocusable(false);
        neutral_sen_amountview.setFocusable(false);
        neutral_ratio_amountview.setFocusable(false);


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
        return R.layout.fragment_amp_scale;
    }


    @Override
    public void onInitView() {
        super.onInitView();
        /**
         * LeftView
         */

        phase_ampclamp_amountview = (AmountViewHorizontal) findViewById(R.id.phase_ampclamp_amountview);
        phase_clamp_range_amountview = (AmountViewHorizontal) findViewById(R.id.phase_clamp_range_amountview);
        phase_nominal_range_amountview = (AmountViewHorizontal) findViewById(R.id.phase_nominal_range_amountview);
        phase_sen_amountview = (AmountViewHorizontal) findViewById(R.id.phase_sen_amountview);
        phase_ratio_amountview = (AmountViewHorizontal) findViewById(R.id.phase_ratio_amountview);


        phase_ampclamp_amountview.setAmountViewLayout(220);
        phase_clamp_range_amountview.setAmountViewLayout(220);
        phase_nominal_range_amountview.setAmountViewLayout(220);
        phase_sen_amountview.setAmountViewLayout(220);
        phase_ratio_amountview.setAmountViewLayout(220);


        phaseshowItems = new String[]{"1 V/A","100 mV/A","10 mV/A","1 mV/A","0.1 mV/A"};
        phaseshowItems3 = new String[10000];
        for (int i = 0; i < 10000; i++) {
            phaseshowItems3[i] = (i +1)+ "A";
        }
        phaseshowItems4 = new String[]{"x1"};
        phaseshowItems5 = new String[]{"1:1","10:1","100:1","1000:1","10000:1",};


        phase_ampclamp_amountview.setDataArray(phaseshowItems);
        phase_ampclamp_amountview.setOnHorizonalAmountChangeListener(this);
        phase_ampclamp_amountview.setShowAmount(true);
        phase_ampclamp_amountview.setShowValueBg(true);

        phase_clamp_range_amountview.setShowValueBg(true);
        phase_clamp_range_amountview.setShowAmount(false);
        phase_clamp_range_amountview.setDataArray(phaseshowItems);

        phase_nominal_range_amountview.setShowValueBg(true);
        phase_nominal_range_amountview.setOnHorizonalAmountChangeListener(this);
        phase_nominal_range_amountview.setShowAmount(false);
        phase_nominal_range_amountview.setDataArray(phaseshowItems3);

        phase_sen_amountview.setShowValueBg(true);
        phase_sen_amountview.setOnHorizonalAmountChangeListener(this);
        phase_sen_amountview.setShowAmount(false);
        phase_sen_amountview.setDataArray(phaseshowItems4);

        phase_ratio_amountview.setShowValueBg(true);
        phase_ratio_amountview.setOnHorizonalAmountChangeListener(this);
        phase_ratio_amountview.setShowAmount(false);
        phase_ratio_amountview.setDataArray(phaseshowItems5);


        phase_ratio_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    phase_ratio_amountview.setShowAmount(true);
                }else{
                    phase_ratio_amountview.setShowAmount(false);
                }
            }
        });

        phase_sen_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    phase_sen_amountview.setShowAmount(true);
                }else{
                    phase_sen_amountview.setShowAmount(false);
                }
            }
        });

        phase_nominal_range_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    phase_nominal_range_amountview.setShowAmount(true);
                }else{
                    phase_nominal_range_amountview.setShowAmount(false);
                }
            }
        });


        phase_clamp_range_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    phase_clamp_range_amountview.setShowAmount(true);
                }else{
                    phase_clamp_range_amountview.setShowAmount(false);
                }
            }
        });

        phase_ampclamp_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    phase_ampclamp_amountview.setShowAmount(true);
                }else{
                    phase_ampclamp_amountview.setShowAmount(false);
                }
            }
        });


        /**
         * RightView
         */


        neutral_ampclamp_amountview = (AmountViewHorizontal) findViewById(R.id.neutral_ampclamp_amountview);
        neutral_clamp_range_amountview = (AmountViewHorizontal) findViewById(R.id.neutral_clamp_range_amountview);
        neutral_nominal_range_amountview = (AmountViewHorizontal) findViewById(R.id.neutral_nominal_range_amountview);
        neutral_sen_amountview = (AmountViewHorizontal) findViewById(R.id.neutral_sen_amountview);
        neutral_ratio_amountview = (AmountViewHorizontal) findViewById(R.id.neutral_ratio_amountview);


        neutral_ampclamp_amountview.setClickable(false);
        neutral_clamp_range_amountview.setClickable(false);
        neutral_nominal_range_amountview.setClickable(false);
        neutral_sen_amountview.setClickable(false);
        neutral_ratio_amountview.setClickable(false);



        neutral_ampclamp_amountview.setAmountViewLayout(220);
        neutral_clamp_range_amountview.setAmountViewLayout(220);
        neutral_nominal_range_amountview.setAmountViewLayout(220);
        neutral_sen_amountview.setAmountViewLayout(220);
        neutral_ratio_amountview.setAmountViewLayout(220);

//        neutralshowItems = new String[]{"1 V/A","100 mV/A","10 mV/A","1 mV/A","0.1 mV/A"};
//        neutralshowItems3 = new String[10000];
//        for (int i = 0; i < 10000; i++) {
//            neutralshowItems3[i] = (i +1)+ "A";
//        }
//        neutralshowItems4 = new String[]{"x1"};
//        neutralshowItems5 = new String[]{"1:1","10:1","100:1","1000:1","10000:1",};


        neutral_ampclamp_amountview.setShowValueBg(true);
        neutral_ampclamp_amountview.setOnHorizonalAmountChangeListener(this);
        neutral_ampclamp_amountview.setDataArray(phaseshowItems);
        neutral_ampclamp_amountview.setShowAmount(false);

        neutral_clamp_range_amountview.setShowValueBg(true);
        neutral_clamp_range_amountview.setOnHorizonalAmountChangeListener(this);
        neutral_clamp_range_amountview.setDataArray(phaseshowItems);
        neutral_clamp_range_amountview.setShowAmount(false);

        neutral_nominal_range_amountview.setShowValueBg(true);
        neutral_nominal_range_amountview.setOnHorizonalAmountChangeListener(this);
        neutral_nominal_range_amountview.setDataArray(phaseshowItems3);
        neutral_nominal_range_amountview.setShowAmount(false);

        neutral_sen_amountview.setShowValueBg(true);
        neutral_sen_amountview.setOnHorizonalAmountChangeListener(this);
        neutral_sen_amountview.setDataArray(phaseshowItems4);
        neutral_sen_amountview.setShowAmount(false);

        neutral_ratio_amountview.setShowValueBg(true);
        neutral_ratio_amountview.setOnHorizonalAmountChangeListener(this);
        neutral_ratio_amountview.setDataArray(phaseshowItems5);
        neutral_ratio_amountview.setShowAmount(false);

        neutral_ampclamp_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    neutral_ampclamp_amountview.setShowAmount(true);
                }else{
                    neutral_ampclamp_amountview.setShowAmount(false);
                }
            }
        });

        neutral_clamp_range_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    neutral_clamp_range_amountview.setShowAmount(true);
                }else{
                    neutral_clamp_range_amountview.setShowAmount(false);
                }
            }
        });

        neutral_nominal_range_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    neutral_nominal_range_amountview.setShowAmount(true);
                }else{
                    neutral_nominal_range_amountview.setShowAmount(false);
                }
            }
        });


        neutral_sen_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    neutral_sen_amountview.setShowAmount(true);
                }else{
                    neutral_sen_amountview.setShowAmount(false);
                }
            }
        });

        neutral_ratio_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    neutral_ratio_amountview.setShowAmount(true);
                }else{
                    neutral_ratio_amountview.setShowAmount(false);
                }
            }
        });


        phaseAmountValue = config.getConfig_AmpScale_Phase_Ratio();
        phaseAmountValue3 = config.getConfig_AmpScale_Phase_Norminal();
        phaseAmountValue4 = config.getConfig_AmpScale_Phase_Sensitivity();
        phaseAmountValue5 = config.getConfig_AmpScale_Phase_Ratio();

        neutralAmountValue = config.getConfig_AmpScale_Neutral_Ratio();
        neutralAmountValue3 = config.getConfig_AmpScale_Neutral_Norminal();
        neutralAmountValue4 = config.getConfig_AmpScale_Neutral_Sensitivity();
        neutralAmountValue5 = config.getConfig_AmpScale_Neutral_Ratio();

        phase_ampclamp_amountview.setFocusable(true);
        phase_ampclamp_amountview.setFocusableInTouchMode(true);
        phase_ampclamp_amountview.requestFocus();
        phase_ampclamp_amountview.requestFocusFromTouch();

        phase_ampclamp_amountview.setAmount(phaseAmountValue);
        phase_ampclamp_amountview.setContent(phaseshowItems[phaseAmountValue-1]);
        phase_clamp_range_amountview.setAmount(phaseAmountValue);
        phase_clamp_range_amountview.setContent(phaseshowItems[phaseAmountValue-1]);
        phase_nominal_range_amountview.setAmount(phaseAmountValue3);
        phase_nominal_range_amountview.setContent(phaseshowItems3[phaseAmountValue3-1]);
        phase_sen_amountview.setAmount(phaseAmountValue4);
        phase_sen_amountview.setContent(phaseshowItems4[phaseAmountValue4-1]);
        phase_ratio_amountview.setAmount(phaseAmountValue5);
        phase_ratio_amountview.setContent(phaseshowItems5[phaseAmountValue5-1]);


        neutral_ampclamp_amountview.setAmount(neutralAmountValue);
        neutral_ampclamp_amountview.setContent(phaseshowItems[neutralAmountValue-1]);
        neutral_clamp_range_amountview.setAmount(neutralAmountValue);
        neutral_clamp_range_amountview.setContent(phaseshowItems[neutralAmountValue-1]);
        neutral_nominal_range_amountview.setAmount(neutralAmountValue3);
        neutral_nominal_range_amountview.setContent(phaseshowItems3[neutralAmountValue3-1]);
        neutral_sen_amountview.setAmount(neutralAmountValue4);
        neutral_sen_amountview.setContent(phaseshowItems4[neutralAmountValue4-1]);
        neutral_ratio_amountview.setAmount(neutralAmountValue5);
        neutral_ratio_amountview.setContent(phaseshowItems5[neutralAmountValue5-1]);

        stopRight();
    }


    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.phase_ampclamp_amountview:
                phaseAmountValue = amount;
                phase_ampclamp_amountview.setContent(phaseshowItems[amount - 1]);
                phase_clamp_range_amountview.setContent(phaseshowItems[amount - 1]);
                break;
            case R.id.phase_nominal_range_amountview:
                phaseAmountValue3 = amount;
                phase_nominal_range_amountview.setContent(phaseshowItems3[amount - 1]);
                break;
            case R.id.phase_sen_amountview:
                phaseAmountValue4 = amount;
                phase_sen_amountview.setContent(phaseshowItems4[amount - 1]);
                break;
            case R.id.phase_ratio_amountview:
                phaseAmountValue5 = amount;
                phase_ratio_amountview.setContent(phaseshowItems5[amount - 1]);
                break;

            case R.id.neutral_ampclamp_amountview:
                neutralAmountValue = amount;
                neutral_ampclamp_amountview.setContent(phaseshowItems[amount - 1]);
                neutral_clamp_range_amountview.setContent(phaseshowItems[amount - 1]);
                break;
            case R.id.neutral_nominal_range_amountview:
                neutralAmountValue3 = amount;
                neutral_nominal_range_amountview.setContent(phaseshowItems3[amount - 1]);
                break;
            case R.id.neutral_sen_amountview:
                neutralAmountValue4 = amount;
                neutral_sen_amountview.setContent(phaseshowItems4[amount - 1]);
                break;
            case R.id.neutral_ratio_amountview:
                neutralAmountValue5 = amount;
                neutral_ratio_amountview.setContent(phaseshowItems5[amount - 1]);
                break;

        }
    }

    public void moveCursor(int i) {
        if (phase_ampclamp_amountview.hasFocus() && forbinRight) {
            if (i > 0)
                phase_ampclamp_amountview.getAmount_dncrease_horizontal().performClick();
            else
                phase_ampclamp_amountview.getAmount_increase_horizontal().performClick();
        } else if (phase_nominal_range_amountview.hasFocus() && forbinRight) {

            if (i > 0)
                phase_nominal_range_amountview.getAmount_dncrease_horizontal().performClick();
            else
                phase_nominal_range_amountview.getAmount_increase_horizontal().performClick();

        } else if (phase_sen_amountview.hasFocus() && forbinRight) {

            if (i > 0)
                phase_sen_amountview.getAmount_dncrease_horizontal().performClick();
            else
                phase_sen_amountview.getAmount_increase_horizontal().performClick();

        } else if (phase_ratio_amountview.hasFocus() && forbinRight) {

            if (i > 0)
                phase_ratio_amountview.getAmount_dncrease_horizontal().performClick();
            else
                phase_ratio_amountview.getAmount_increase_horizontal().performClick();

        }else if(neutral_ampclamp_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                neutral_ampclamp_amountview.getAmount_dncrease_horizontal().performClick();
            else
                neutral_ampclamp_amountview.getAmount_increase_horizontal().performClick();
        }else if(neutral_nominal_range_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                neutral_nominal_range_amountview.getAmount_dncrease_horizontal().performClick();
            else
                neutral_nominal_range_amountview.getAmount_increase_horizontal().performClick();

        }else if(neutral_sen_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                neutral_sen_amountview.getAmount_dncrease_horizontal().performClick();
            else
                neutral_sen_amountview.getAmount_increase_horizontal().performClick();

        }else if(neutral_ratio_amountview.hasFocus() && !forbinRight){

            if (i > 0)
                neutral_ratio_amountview.getAmount_dncrease_horizontal().performClick();
            else
                neutral_ratio_amountview.getAmount_increase_horizontal().performClick();
        }
    }

    public void resetSet(){
        stopRight();
        phaseAmountValue = 1;
        phaseAmountValue3 = 3000;
        phaseAmountValue4 = 1;
        phaseAmountValue5 = 1;

        neutralAmountValue = 1;
        neutralAmountValue3 = 3000;
        neutralAmountValue4 = 1;
        neutralAmountValue5 = 1;


        config.setConfig_AmpScale_Phase_Ratio(phaseAmountValue);
        config.setConfig_AmpScale_Phase_Norminal(phaseAmountValue3);
        config.setConfig_AmpScale_Phase_Sensitivity(phaseAmountValue4);
        config.setConfig_AmpScale_Phase_Ratio(phaseAmountValue5);

        config.setConfig_AmpScale_Neutral_Ratio(neutralAmountValue);;
        config.setConfig_AmpScale_Neutral_Norminal(neutralAmountValue3);
        config.setConfig_AmpScale_Neutral_Sensitivity(neutralAmountValue4);
        config.setConfig_AmpScale_Neutral_Ratio(neutralAmountValue5);



        phase_ampclamp_amountview.setAmount(phaseAmountValue);
        phase_clamp_range_amountview.setAmount(phaseAmountValue);
        phase_nominal_range_amountview.setAmount(phaseAmountValue3);
        phase_sen_amountview.setAmount(phaseAmountValue4);
        phase_ratio_amountview.setAmount(phaseAmountValue5);

        phase_ampclamp_amountview.setContent(phaseshowItems[phaseAmountValue - 1]);
        phase_clamp_range_amountview.setContent(phaseshowItems[phaseAmountValue - 1]);
        phase_nominal_range_amountview.setContent(phaseshowItems3[phaseAmountValue3 - 1]);
        phase_sen_amountview.setContent(phaseshowItems4[phaseAmountValue4 - 1]);
        phase_ratio_amountview.setContent(phaseshowItems5[phaseAmountValue5 - 1]);

        neutral_ampclamp_amountview.setAmount(neutralAmountValue);
        neutral_clamp_range_amountview.setAmount(neutralAmountValue);

        neutral_nominal_range_amountview.setAmount(neutralAmountValue3);
        neutral_sen_amountview.setAmount(neutralAmountValue4);
        neutral_ratio_amountview.setAmount(neutralAmountValue5);


        neutral_ampclamp_amountview.setContent(phaseshowItems[neutralAmountValue - 1]);
        neutral_clamp_range_amountview.setContent(phaseshowItems[neutralAmountValue - 1]);
        neutral_nominal_range_amountview.setContent(phaseshowItems3[neutralAmountValue3 - 1]);
        neutral_sen_amountview.setContent(phaseshowItems4[neutralAmountValue4 - 1]);
        neutral_ratio_amountview.setContent(phaseshowItems5[neutralAmountValue5 - 1]);

    }

    public void  saveSetting(){
        config.setConfig_AmpScale_Phase_AmpClamp(phaseAmountValue);
        config.setConfig_AmpScale_Phase_Norminal(phaseAmountValue3);
        config.setConfig_AmpScale_Phase_Sensitivity(phaseAmountValue4);
        config.setConfig_AmpScale_Phase_Ratio(phaseAmountValue5);

        config.setConfig_AmpScale_Neutral_AmpClamp(neutralAmountValue);;
        config.setConfig_AmpScale_Neutral_Norminal(neutralAmountValue3);
        config.setConfig_AmpScale_Neutral_Sensitivity(neutralAmountValue4);
        config.setConfig_AmpScale_Neutral_Ratio(neutralAmountValue5);

    }


}
