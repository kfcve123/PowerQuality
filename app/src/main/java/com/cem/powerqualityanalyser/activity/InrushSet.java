package com.cem.powerqualityanalyser.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


/**
 * 浪涌设置
 */
public class InrushSet extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    private TextView inrush_set_value, inrush_set_value2, inrush_set_value3, inrush_set_value4, inrush_set_value5, inrush_set_value6, inrush_set_value7, inrush_set_value8;
    private AmountViewHorizontal amount_view, amount_view2, amount_view3, amount_view4;
    private int amountValue, amountValue2, amountValue3, amountValue4 = 1;
    private int thresValue, hysterValue;

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
    public void onInitView() {
        super.onInitView();
        inrush_set_value = (TextView) findViewById(R.id.inrush_set_value);
        inrush_set_value2 = (TextView) findViewById(R.id.inrush_set_value2);
        inrush_set_value3 = (TextView) findViewById(R.id.inrush_set_value3);
        inrush_set_value4 = (TextView) findViewById(R.id.inrush_set_value4);
        inrush_set_value5 = (TextView) findViewById(R.id.inrush_set_value5);
        inrush_set_value6 = (TextView) findViewById(R.id.inrush_set_value6);
        inrush_set_value7 = (TextView) findViewById(R.id.inrush_set_value7);
        inrush_set_value8 = (TextView) findViewById(R.id.inrush_set_value8);

        amount_view = (AmountViewHorizontal) findViewById(R.id.amount_view);
        amount_view2 = (AmountViewHorizontal) findViewById(R.id.amount_view2);
        amount_view3 = (AmountViewHorizontal) findViewById(R.id.amount_view3);
        amount_view4 = (AmountViewHorizontal) findViewById(R.id.amount_view4);
        amount_view2.setShowAmount(false);
        amount_view3.setShowAmount(false);
        amount_view4.setShowAmount(false);

        amount_view.setOnHorizonalAmountChangeListener(this);
        amount_view2.setOnHorizonalAmountChangeListener(this);
        amount_view3.setOnHorizonalAmountChangeListener(this);
        amount_view4.setOnHorizonalAmountChangeListener(this);

        if (AppConfig.getInstance().isInrushSet_Default()) {
            String[] showItem = getString(R.string.inrush_set).split(",");
            ArrayList<Integer> amountValues = new ArrayList<>();
            for (int i = 0; i < showItem.length; i++) {
                String string = showItem[i];
                amountValues.add(Integer.valueOf(string));
            }
            amountValue = amountValues.get(0);
            amountValue2 = amountValues.get(1);
            amountValue3 = amountValues.get(2);
            amountValue4 = amountValues.get(3);
        } else {
            amountValue = config.getInrushSet_Duration();
            amountValue2 = config.getInrushSet_Amps_Norminal();
            amountValue3 = config.getInrushSet_Threshold();
            amountValue4 = config.getInrushSet_Hysteresis();
        }

        amount_view.setAmount(amountValue);
        amount_view2.setAmount(amountValue2);
        amount_view3.setAmount(amountValue3);
        amount_view4.setAmount(amountValue4);

        amount_view.setContent(amountValue + "m");
        amount_view2.setContent(amountValue2 + "A");
        amount_view3.setContent(amountValue3 + "%");
        amount_view4.setContent(amountValue4 + "%");

        thresValue = Math.round(amountValue2 * amountValue3 / 100f);
        hysterValue = Math.round(amountValue2 * amountValue4 / 100f);
        inrush_set_value7.setText(thresValue + "A");
        inrush_set_value8.setText(hysterValue + "A");
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_inrush_setting;
    }

    @Override
    public void onInitViews() {


    }

    public long getDuration(){
        String content = amount_view.getContent();
        int multiple = 1;
        String num = "";
        if (!TextUtils.isEmpty(content)){
            if (content.endsWith("s")){
                multiple = 1000;
                num = content.replace("s","");
            }else if (content.endsWith("m")){
                multiple = 1000 * 60;
                num = content.replace("m","");
            }else if (content.endsWith("hour")){
                multiple = 1000 * 60 * 60;
                num = content.replace("hour","");
            }else if (content.endsWith("day")){
                multiple = 1000 * 60 * 60 * 24;
                num = content.replace("day","");
            }
            return Long.valueOf(num) * multiple;
        }
        return 0;
    }


    public int getThresValue() {
        return thresValue;
    }

    public int getHysterValue() {
        return hysterValue;
    }

    public void saveSetting() {
        config.setInrushSet_Duration(amountValue);
        config.setInrushSet_Amps_Norminal(amountValue2);
        config.setInrushSet_Threshold(amountValue3);
        config.setInrushSet_Hysteresis(amountValue4);
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.amount_view:
                amountValue = amount;
                amount_view.setContent(amountValue + "m");
                break;
            case R.id.amount_view2:
                amountValue2 = amount;
                amount_view2.setContent(amountValue2 + "A");
                break;
            case R.id.amount_view3:
                amountValue3 = amount;
                amount_view3.setContent(amountValue3 + "%");
                break;
            case R.id.amount_view4:
                amountValue4 = amount;
                amount_view4.setContent(amountValue4 + "%");
                break;
        }
        thresValue = Math.round(amountValue2 * amountValue3 / 100f);
        hysterValue = Math.round(amountValue2 * amountValue4 / 100f);
        inrush_set_value7.setText(thresValue + "A");
        inrush_set_value8.setText(hysterValue + "A");
    }

    public void resetSet() {
        String[] showItem = getString(R.string.inrush_set).split(",");
        ArrayList<Integer> amountValues = new ArrayList<>();
        for (int i = 0; i < showItem.length; i++) {
            String string = showItem[i];
            amountValues.add(Integer.valueOf(string));
        }
        amountValue = amountValues.get(0);
        amountValue2 = amountValues.get(1);
        amountValue3 = amountValues.get(2);
        amountValue4 = amountValues.get(3);

        amount_view.setAmount(amountValue);
        amount_view2.setAmount(amountValue2);
        amount_view3.setAmount(amountValue3);
        amount_view4.setAmount(amountValue4);

        amount_view.setContent(amountValue + "m");
        amount_view2.setContent(amountValue2 + "A");
        amount_view3.setContent(amountValue3 + "%");
        amount_view4.setContent(amountValue4 + "%");

        thresValue = Math.round(amountValue2 * amountValue3 / 100f);
        hysterValue = Math.round(amountValue2 * amountValue4 / 100f);
        inrush_set_value7.setText(thresValue + "A");
        inrush_set_value8.setText(hysterValue + "A");

    }

    public void moveCursor(int i) {
        if (amount_view.hasFocus()) {
            if (i > 0)
                amount_view.getAmount_dncrease_horizontal().performClick();
            else
                amount_view.getAmount_increase_horizontal().performClick();

        } else if (amount_view2.hasFocus()) {
            if (i > 0)
                amount_view2.getAmount_dncrease_horizontal().performClick();
            else
                amount_view2.getAmount_increase_horizontal().performClick();
        } else if (amount_view3.hasFocus()) {
            if (i > 0)
                amount_view3.getAmount_dncrease_horizontal().performClick();
            else
                amount_view3.getAmount_increase_horizontal().performClick();
        } else if (amount_view4.hasFocus()) {
            if (i > 0)
                amount_view4.getAmount_dncrease_horizontal().performClick();
            else
                amount_view4.getAmount_increase_horizontal().performClick();
        }
    }


}
