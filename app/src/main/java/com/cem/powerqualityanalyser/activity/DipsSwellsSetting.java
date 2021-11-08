package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;


import java.util.ArrayList;

import serialport.amos.cem.com.libamosserial.ModelAllData;

public class DipsSwellsSetting extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    private AmountViewHorizontal dips_amount_view, dips_amount_view2, dips_amount_view3, dips_amount_view4, dips_amount_view5, dips_amount_view6;
    private TextView dips_set_value, dips_set_value2, dips_set_value3, dips_set_value4, dips_set_value5, dips_set_value6, dips_set_value7, dips_set_value8, dips_set_value9, dips_set_value10;
    private int amountValue, amountValue2, amountValue3, amountValue4, amountValue5, amountValue6;
    private int thresValue,hysterValue,thresValue2,hysterValue2;

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
        return R.layout.fragment_dips_swells_setting;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();

        dips_set_value = (TextView) findViewById(R.id.dips_set_value);
        dips_set_value2 = (TextView) findViewById(R.id.dips_set_value2);
        dips_set_value3 = (TextView) findViewById(R.id.dips_set_value3);
        dips_set_value4 = (TextView) findViewById(R.id.dips_set_value4);
        dips_set_value5 = (TextView) findViewById(R.id.dips_set_value5);

        dips_set_value6 = (TextView) findViewById(R.id.dips_set_value6);
        dips_set_value7 = (TextView) findViewById(R.id.dips_set_value7);
        dips_set_value8 = (TextView) findViewById(R.id.dips_set_value8);
        dips_set_value9 = (TextView) findViewById(R.id.dips_set_value9);
        dips_set_value10 = (TextView) findViewById(R.id.dips_set_value10);

        dips_amount_view = (AmountViewHorizontal) findViewById(R.id.dips_amount_view);
        dips_amount_view2 = (AmountViewHorizontal) findViewById(R.id.dips_amount_view2);
        dips_amount_view3 = (AmountViewHorizontal) findViewById(R.id.dips_amount_view3);
        dips_amount_view4 = (AmountViewHorizontal) findViewById(R.id.dips_amount_view4);
        dips_amount_view5 = (AmountViewHorizontal) findViewById(R.id.dips_amount_view5);
        dips_amount_view6 = (AmountViewHorizontal) findViewById(R.id.dips_amount_view6);

        dips_amount_view.setShowAmount(true);
        dips_amount_view2.setShowAmount(false);
        dips_amount_view3.setShowAmount(false);
        dips_amount_view4.setShowAmount(false);
        dips_amount_view5.setShowAmount(false);
        dips_amount_view6.setShowAmount(false);


        dips_amount_view.setOnHorizonalAmountChangeListener(this);
        dips_amount_view2.setOnHorizonalAmountChangeListener(this);
        dips_amount_view3.setOnHorizonalAmountChangeListener(this);
        dips_amount_view4.setOnHorizonalAmountChangeListener(this);
        dips_amount_view5.setOnHorizonalAmountChangeListener(this);
        dips_amount_view6.setOnHorizonalAmountChangeListener(this);



        if(AppConfig.getInstance().isDipsSet_Default()){
            String[] showItem=getString(R.string.dips_set).split(",");
            ArrayList<Integer> amountValues = new ArrayList<>();
            for (int i = 0; i < showItem.length; i++) {
                String string = showItem[i];
                amountValues.add(Integer.valueOf(string));
            }
            amountValue = amountValues.get(0);
            amountValue2 = amountValues.get(1);
            amountValue3 = amountValues.get(2);
            amountValue4 = amountValues.get(3);
            amountValue5 = amountValues.get(4);
            amountValue6 = amountValues.get(5);
        }else{
            amountValue = config.getDipsSet_Nominal();
            amountValue2 = config.getDipsSet_Threshold();
            amountValue3 = config.getDipsSet_Hysteresis();
            amountValue4 = config.getSwellsSet_Nominal();
            amountValue5 = config.getSwellsSet_Threshold();
            amountValue6 = config.getSwellsSet_Hysteresis();
        }

        dips_amount_view.setAmount(amountValue);
        dips_amount_view2.setAmount(amountValue2);
        dips_amount_view3.setAmount(amountValue3);
        dips_amount_view4.setAmount(amountValue4);
        dips_amount_view5.setAmount(amountValue5);
        dips_amount_view6.setAmount(amountValue6);

        dips_amount_view.setContent(amountValue + "");
        dips_amount_view2.setContent(amountValue2 + "%");
        dips_amount_view3.setContent(amountValue3 + "%");
        dips_amount_view4.setContent(amountValue4 + "");
        dips_amount_view5.setContent(amountValue5 + "%");
        dips_amount_view6.setContent(amountValue6 + "%");

        thresValue = Math.round(amountValue * amountValue2/100f);
        hysterValue = Math.round(amountValue * amountValue3/100f);
        thresValue2 = Math.round(amountValue4 * amountValue5/100f);
        hysterValue2 = Math.round(amountValue4 * amountValue6/100f);

        dips_set_value4.setText(thresValue + "V");
        dips_set_value5.setText(hysterValue + "V");
        dips_set_value9.setText(thresValue2 + "V");
        dips_set_value10.setText(hysterValue2 + "V");
    }

    public int getThresValue(){
        return thresValue;
    }

    public int getHysterValue(){
        return hysterValue;
    }

    public int getThresValueTwo(){
        return thresValue2;
    }

    public int getHysterValueTwo(){
        return hysterValue2;
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.dips_amount_view:
                amountValue = amount;
                break;

            case R.id.dips_amount_view2:
                amountValue2 = amount;
                dips_amount_view2.setContent(amountValue2 + "%");

                break;

            case R.id.dips_amount_view3:
                amountValue3 = amount;
                dips_amount_view3.setContent(amountValue3 + "%");
                break;

            case R.id.dips_amount_view4:
                amountValue4 = amount;
                break;
            case R.id.dips_amount_view5:
                amountValue5 = amount;
                dips_amount_view5.setContent(amountValue5 + "%");
                break;

            case R.id.dips_amount_view6:
                amountValue6 = amount;
                dips_amount_view6.setContent(amountValue6 + "%");
                break;
        }
        thresValue = Math.round(amountValue * amountValue2/100f);
        hysterValue = Math.round(amountValue * amountValue3/100f);
        thresValue2 = Math.round(amountValue4 * amountValue5/100f);
        hysterValue2 = Math.round(amountValue4 * amountValue6/100f);

        dips_set_value4.setText(thresValue + "V");
        dips_set_value5.setText(hysterValue + "V");
        dips_set_value9.setText(thresValue2 + "V");
        dips_set_value10.setText(hysterValue2 + "V");

    }

    public void resetSet() {
        String[] showItem=getString(R.string.dips_set).split(",");
        ArrayList<Integer> amountValues = new ArrayList<>();
        for (int i = 0; i < showItem.length; i++) {
            String string = showItem[i];
            amountValues.add(Integer.valueOf(string));
        }
        amountValue = amountValues.get(0);
        amountValue2 = amountValues.get(1);
        amountValue3 = amountValues.get(2);
        amountValue4 = amountValues.get(3);
        amountValue5 = amountValues.get(4);
        amountValue6 = amountValues.get(5);

        dips_amount_view.setAmount(amountValue);
        dips_amount_view2.setAmount(amountValue2);
        dips_amount_view3.setAmount(amountValue3);
        dips_amount_view4.setAmount(amountValue4);
        dips_amount_view5.setAmount(amountValue5);
        dips_amount_view6.setAmount(amountValue6);

        dips_amount_view.setContent(amountValue + "");
        dips_amount_view2.setContent(amountValue2 + "%");
        dips_amount_view3.setContent(amountValue3 + "%");
        dips_amount_view4.setContent(amountValue4 + "");
        dips_amount_view5.setContent(amountValue5 + "%");
        dips_amount_view6.setContent(amountValue6 + "%");

        thresValue = Math.round(amountValue * amountValue2/100f);
        hysterValue = Math.round(amountValue * amountValue3/100f);
        thresValue2 = Math.round(amountValue4 * amountValue5/100f);
        hysterValue2 = Math.round(amountValue4 * amountValue6/100f);

        dips_set_value4.setText(thresValue + "V");
        dips_set_value5.setText(hysterValue + "V");
        dips_set_value9.setText(thresValue2 + "V");
        dips_set_value10.setText(hysterValue2 + "V");
    }

    public void saveSetting(){
        config.setDipsSet_Nominal(amountValue);
        config.setDipsSet_Threshold(amountValue2);
        config.setDipsSet_Hysteresis(amountValue3);

        config.setSwellsSet_Nominal(amountValue4);
        config.setSwellsSet_Threshold(amountValue5);
        config.setSwellsSet_Hysteresis(amountValue6);

    }


    public void moveCursor(int i) {
        if (dips_amount_view.hasFocus()){
            if (i > 0)
                dips_amount_view.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view.getAmount_increase_horizontal().performClick();

        }else if (dips_amount_view2.hasFocus()){
            if (i > 0)
                dips_amount_view2.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view2.getAmount_increase_horizontal().performClick();
        }else if (dips_amount_view3.hasFocus()){
            if (i > 0)
                dips_amount_view3.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view3.getAmount_increase_horizontal().performClick();
        }else if (dips_amount_view4.hasFocus()){
            if (i > 0)
                dips_amount_view4.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view4.getAmount_increase_horizontal().performClick();
        }else if (dips_amount_view5.hasFocus()){
            if (i > 0)
                dips_amount_view5.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view5.getAmount_increase_horizontal().performClick();
        }else if (dips_amount_view6.hasFocus()){
            if (i > 0)
                dips_amount_view6.getAmount_dncrease_horizontal().performClick();
            else
                dips_amount_view6.getAmount_increase_horizontal().performClick();
        }
    }

}
