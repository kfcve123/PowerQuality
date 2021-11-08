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
 * 能量计算器 设置
 */
public class EneryCalculatorSet extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    /**
     * 左边控件
     */
    private String[] showItemsMode, showItemsLength,showItemsArea,showItemsMater;
    private AmountViewHorizontal vlot_amountview, amp_amountview,area_amountview,material_amountview;
    private LinearLayout transient_set_left;
    private TextView voltlevel_tv, amplevel_tv,area_tv,material_tv;
    private int amountValue, amountValue2,amountValue3,amountValue4 = 1;



    /**
     * 右边控件
     */
    private LinearLayout transient_set_right;
    private String[] showItemYear, showItemMonth, showItemDay, showItemHour, showItemMiniut,showItemTariff,showItemUnit;
    private AmountViewHorizontal amountview_year, amountview_month, amountview_day, amountview_hour, amountview_minutes,amountview_tariff,amountview_unit;
    private TextView year_tv, month_tv, day_tv, hour_tv, minutes_tv,tariff_tv,unit_tv;
    private int amountValue_time, amountValue_time2,amountValue_time3,amountValue_time4,amountValue_time5,amountValue_time6,amountValue_time7 = 1;


    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        log.e("-----Right------");
        forbinRight = false;

        transient_set_left.setEnabled(false);
        amp_amountview.setEnabled(false);
        vlot_amountview.setEnabled(false);
        area_amountview.setEnabled(false);
        material_amountview.setEnabled(false);

        amp_amountview.setFocusable(false);
        vlot_amountview.setFocusable(false);
        area_amountview.setFocusable(false);
        material_amountview.setFocusable(false);

        voltlevel_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        amplevel_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        area_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        material_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));

        transient_set_right.setEnabled(true);
        amountview_year.setFocusable(true);
        amountview_year.setFocusableInTouchMode(true);
        amountview_year.requestFocus();
        amountview_year.setEnabled(true);
        amountview_year.setShowAmount(true);

        year_tv.setTextColor(getResources().getColor(R.color.colorblack));
        month_tv.setTextColor(getResources().getColor(R.color.colorblack));
        day_tv.setTextColor(getResources().getColor(R.color.colorblack));
        hour_tv.setTextColor(getResources().getColor(R.color.colorblack));
        minutes_tv.setTextColor(getResources().getColor(R.color.colorblack));
        tariff_tv.setTextColor(getResources().getColor(R.color.colorblack));
        unit_tv.setTextColor(getResources().getColor(R.color.colorblack));
    }

    private boolean forbinRight = true;
    public boolean forbidMoveRight(){
        return forbinRight;
    }

    /**
     * 停用右半边控件
     */
    public void stopRight() {
        forbinRight = true;
        log.e("-----Left------");
//        vlot_amountview.setFocusableInTouchMode(true);
        vlot_amountview.requestFocus();
        vlot_amountview.setShowAmount(true);

        vlot_amountview.setFocusable(true);
        amp_amountview.setFocusable(true);
        area_amountview.setFocusable(true);
        material_amountview.setFocusable(true);

        transient_set_left.setEnabled(true);

        vlot_amountview.setEnabled(true);
        amp_amountview.setEnabled(true);
        area_amountview.setEnabled(true);
        material_amountview.setEnabled(true);

        voltlevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        amplevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        area_tv.setTextColor(getResources().getColor(R.color.colorblack));
        material_tv.setTextColor(getResources().getColor(R.color.colorblack));

        amountview_year.setFocusable(false);
        amountview_month.setFocusable(false);
        amountview_day.setFocusable(false);
        amountview_hour.setFocusable(false);
        amountview_minutes.setFocusable(false);
        amountview_tariff.setFocusable(false);
        amountview_unit.setFocusable(false);

        transient_set_right.setEnabled(false);

        year_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        month_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        day_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        hour_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        minutes_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        tariff_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        unit_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
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
        return R.layout.fragment_enerycalculatorset;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        /**
         * LeftView
         */
        transient_set_left = (LinearLayout) findViewById(R.id.transient_set_left);

        voltlevel_tv = (TextView) findViewById(R.id.voltlevel_tv);
        amplevel_tv = (TextView) findViewById(R.id.amplevel_tv);
        area_tv = (TextView) findViewById(R.id.area_tv);
        material_tv = (TextView) findViewById(R.id.material_tv);

        amp_amountview = (AmountViewHorizontal) findViewById(R.id.amp_amountview);
        vlot_amountview = (AmountViewHorizontal) findViewById(R.id.vlot_amountview);
        area_amountview = (AmountViewHorizontal) findViewById(R.id.area_amountview);
        material_amountview = (AmountViewHorizontal) findViewById(R.id.material_amountview);

        amp_amountview.setAmountViewLayout(220);
        vlot_amountview.setAmountViewLayout(220);
        area_amountview.setAmountViewLayout(220);
        material_amountview.setAmountViewLayout(220);

        showItemsMode = new String[]{"ON","OFF"};
        showItemsLength = new String[100001];
        for (int i = 0; i < 100001; i++) {
            showItemsLength[i] = i + " m";
        }
        showItemsArea = new String[29];
        for (int i = 0; i < 29; i++) {
            showItemsArea[i] = i + " AWG";
        }
        showItemsMater = new String[]{"AI","Cu"};

        vlot_amountview.setDataArray(showItemsMode);
        vlot_amountview.setOnHorizonalAmountChangeListener(this);
        vlot_amountview.setShowValueBg(true);

        amp_amountview.setShowValueBg(true);
        amp_amountview.setOnHorizonalAmountChangeListener(this);
        amp_amountview.setShowAmount(false);
        amp_amountview.setDataArray(showItemsLength);

        area_amountview.setShowValueBg(true);
        area_amountview.setOnHorizonalAmountChangeListener(this);
        area_amountview.setShowAmount(false);
        area_amountview.setDataArray(showItemsArea);

        material_amountview.setShowValueBg(true);
        material_amountview.setOnHorizonalAmountChangeListener(this);
        material_amountview.setShowAmount(false);
        material_amountview.setDataArray(showItemsMater);

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

        area_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    area_amountview.setShowAmount(true);
                }else{
                    area_amountview.setShowAmount(false);
                }
            }
        });

        material_amountview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    material_amountview.setShowAmount(true);
                    forbinDown = true;
                }else{
                    material_amountview.setShowAmount(false);
                    forbinDown = false;
                }
            }
        });


        /**
         * RightView
         */
        transient_set_right = (LinearLayout) findViewById(R.id.transient_set_right);

        showItemYear = new String[]{"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        showItemMonth = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        showItemDay = new String[31];
        showItemHour = new String[24];
        showItemMiniut = new String[60];
        showItemTariff = new String[100];
        showItemUnit = new String[]{"$","￡","¥","€"};

        for (int i = 0; i < 31; i++) {
            showItemDay[i] = (i + 1) + "";
        }
        for (int i = 0; i < 24; i++) {
            showItemHour[i] = (i + 1) + "";
        }
        for (int i = 0; i < 60; i++) {
            showItemMiniut[i] = (i + 1) + "";
        }


        year_tv = (TextView) findViewById(R.id.year_tv);
        month_tv = (TextView) findViewById(R.id.month_tv);
        day_tv = (TextView) findViewById(R.id.day_tv);
        hour_tv = (TextView) findViewById(R.id.hour_tv);
        minutes_tv = (TextView) findViewById(R.id.minutes_tv);
        tariff_tv = (TextView) findViewById(R.id.tariff_tv);
        unit_tv = (TextView) findViewById(R.id.unit_tv);


        year_tv.setTextColor(getResources().getColor(R.color.colorblack));
        month_tv.setTextColor(getResources().getColor(R.color.colorblack));
        day_tv.setTextColor(getResources().getColor(R.color.colorblack));
        hour_tv.setTextColor(getResources().getColor(R.color.colorblack));
        minutes_tv.setTextColor(getResources().getColor(R.color.colorblack));


        amountview_year = (AmountViewHorizontal) findViewById(R.id.amountview_year);
        amountview_month = (AmountViewHorizontal) findViewById(R.id.amountview_month);
        amountview_day = (AmountViewHorizontal) findViewById(R.id.amountview_day);
        amountview_hour = (AmountViewHorizontal) findViewById(R.id.amountview_hour);
        amountview_minutes = (AmountViewHorizontal) findViewById(R.id.amountview_minutes);
        amountview_tariff = (AmountViewHorizontal) findViewById(R.id.amountview_tariff);
        amountview_unit = (AmountViewHorizontal) findViewById(R.id.amountview_unit);

        amountview_year.setShowValueBg(true);
        amountview_year.setOnHorizonalAmountChangeListener(this);
        amountview_year.setDataArray(showItemYear);
        amountview_year.setAmountViewLayout(200);

        amountview_year.setShowAmount(false);
        amountview_month.setShowAmount(false);
        amountview_day.setShowAmount(false);
        amountview_hour.setShowAmount(false);
        amountview_minutes.setShowAmount(false);
        amountview_tariff.setShowAmount(false);
        amountview_unit.setShowAmount(false);

        amountview_month.setShowValueBg(true);
        amountview_month.setOnHorizonalAmountChangeListener(this);
        amountview_month.setDataArray(showItemMonth);
        amountview_month.setAmountViewLayout(200);

        amountview_day.setShowValueBg(true);
        amountview_day.setOnHorizonalAmountChangeListener(this);
        amountview_day.setDataArray(showItemDay);
        amountview_day.setAmountViewLayout(200);

        amountview_hour.setShowValueBg(true);
        amountview_hour.setOnHorizonalAmountChangeListener(this);
        amountview_hour.setDataArray(showItemHour);
        amountview_hour.setAmountViewLayout(200);

        amountview_minutes.setShowValueBg(true);
        amountview_minutes.setOnHorizonalAmountChangeListener(this);
        amountview_minutes.setDataArray(showItemMiniut);
        amountview_minutes.setAmountViewLayout(200);

        amountview_tariff.setShowValueBg(true);
        amountview_tariff.setOnHorizonalAmountChangeListener(this);
        amountview_tariff.setDataArray(showItemTariff);
        amountview_tariff.setAmountViewLayout(200);

        amountview_unit.setShowValueBg(true);
        amountview_unit.setOnHorizonalAmountChangeListener(this);
        amountview_unit.setDataArray(showItemUnit);
        amountview_unit.setAmountViewLayout(200);


        amountview_year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_year.setShowAmount(true);
                }else{
                    amountview_year.setShowAmount(false);
                }
            }
        });

        amountview_month.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_month.setShowAmount(true);
                }else{
                    amountview_month.setShowAmount(false);
                }
            }
        });

        amountview_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_day.setShowAmount(true);
                }else{
                    amountview_day.setShowAmount(false);
                }
            }
        });

        amountview_hour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_hour.setShowAmount(true);
                }else{
                    amountview_hour.setShowAmount(false);
                }
            }
        });

        amountview_minutes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_minutes.setShowAmount(true);
                }else{
                    amountview_minutes.setShowAmount(false);
                }
            }
        });

        amountview_tariff.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_tariff.setShowAmount(true);
                }else{
                    amountview_tariff.setShowAmount(false);
                }
            }
        });

        amountview_unit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amountview_unit.setShowAmount(true);
                }else{
                    amountview_unit.setShowAmount(false);
                }
            }
        });


        if(AppConfig.getInstance().isTransientSet_Default()){
            amountValue = 1;
            amountValue2 = 1;
            amountValue3 = 1;
            amountValue4 = 1;

            amountValue_time = 1;
            amountValue_time2 = 1;
            amountValue_time3 = 1;
            amountValue_time4 = 1;
            amountValue_time5 = 1;
            amountValue_time6 = 1;
            amountValue_time7 = 1;

        }else{
            amountValue = config.getDipSwell_Trigger_VoltLevel();
            amountValue2 = config.getDipSwell_Trigger_AmpLevel();

            amountValue3 = 1;
            amountValue4 = 1;

            amountValue_time = config.getDipSweel_Time_Year();
            amountValue_time2 = config.getDipSweel_Time_Month();
            amountValue_time3 = config.getDipSweel_Time_Day();
            amountValue_time4 = config.getDipSweel_Time_Hours();
            amountValue_time5 = config.getDipSweel_Time_Minutes();
            amountValue_time6 = config.getDipSweel_Time_Hours();
            amountValue_time7 = config.getDipSweel_Time_Minutes();
        }

        vlot_amountview.setFocusable(true);
        vlot_amountview.setFocusableInTouchMode(true);
        vlot_amountview.requestFocus();
        vlot_amountview.requestFocusFromTouch();

        vlot_amountview.setAmount(amountValue);
        vlot_amountview.setContent(showItemsMode[amountValue-1]);
        amp_amountview.setAmount(amountValue2);
        amp_amountview.setContent(showItemsLength[amountValue2-1]);

        area_amountview.setAmount(amountValue3);
        area_amountview.setContent(showItemsArea[amountValue3-1]);
        material_amountview.setAmount(amountValue4);
        material_amountview.setContent(showItemsMater[amountValue4-1]);



        amountview_year.setAmount(amountValue_time);
        amountview_year.setContent(showItemYear[amountValue_time-1]);
        amountview_month.setAmount(amountValue_time2);
        amountview_month.setContent(showItemMonth[amountValue_time2-1]);
        amountview_day.setAmount(amountValue_time3);
        amountview_day.setContent(showItemDay[amountValue_time3-1]);
        amountview_hour.setAmount(amountValue_time4);
        amountview_hour.setContent(showItemHour[amountValue_time4-1]);
        amountview_minutes.setAmount(amountValue_time5);
        amountview_minutes.setContent(showItemMiniut[amountValue_time5-1]);

        amountview_tariff.setAmount(amountValue_time6);
        amountview_tariff.setContent(showItemTariff[amountValue_time6-1]);
        amountview_unit.setAmount(amountValue_time7);
        amountview_unit.setContent(showItemUnit[amountValue_time7-1]);

        stopRight();
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.vlot_amountview:
                amountValue = amount;
                vlot_amountview.setContent(showItemsMode[amount - 1]);
                if(amount == 1){
                    amp_amountview.setFocusable(false);
                    area_amountview.setFocusable(false);
                    vlot_amountview.setNextFocusDownId(R.id.material_amountview);
                    material_amountview.setNextFocusUpId(R.id.vlot_amountview);
                }else{
                    amp_amountview.setFocusable(true);
                    area_amountview.setFocusable(true);
                    vlot_amountview.setNextFocusDownId(R.id.amp_amountview);
                    material_amountview.setNextFocusUpId(R.id.area_amountview);
                }

                break;
            case R.id.amp_amountview:
                amountValue2 = amount;
                amp_amountview.setContent(showItemsLength[amount - 1]);
                break;
            case R.id.area_amountview:
                amountValue3 = amount;
                area_amountview.setContent(showItemsArea[amount - 1]);
                break;
            case R.id.material_amountview:
                amountValue4 = amount;
                material_amountview.setContent(showItemsMater[amount - 1]);
                break;
            case R.id.amountview_year:
                amountValue_time = amount;
                amountview_year.setContent(showItemYear[amount - 1]);
                break;
            case R.id.amountview_month:
                amountValue_time2 = amount;
                amountview_month.setContent(showItemMonth[amount - 1]);
                break;
            case R.id.amountview_day:
                amountValue_time3 = amount;
                amountview_day.setContent(showItemDay[amount - 1]);
                break;
            case R.id.amountview_hour:
                amountValue_time4 = amount;
                amountview_hour.setContent(showItemHour[amount - 1]);
                break;
            case R.id.amountview_minutes:
                amountValue_time5 = amount;
                amountview_minutes.setContent(showItemMiniut[amount - 1]);
                break;
            case R.id.amountview_tariff:
                amountValue_time6 = amount;
                amountview_tariff.setContent(showItemTariff[amount - 1]);
                break;
            case R.id.amountview_unit:
                amountValue_time7 = amount;
                amountview_unit.setContent(showItemUnit[amount - 1]);
                break;
        }
    }


    public void moveCursor(int i) {
        if (vlot_amountview.hasFocus()) {
            if (i > 0)
                vlot_amountview.getAmount_dncrease_horizontal().performClick();
            else
                vlot_amountview.getAmount_increase_horizontal().performClick();
        } else if (amp_amountview.hasFocus()) {
            vlot_amountview.setFocusable(false);
            area_amountview.setFocusable(false);
            material_tv.setFocusable(false);
            if (i > 0)
                amp_amountview.getAmount_dncrease_horizontal().performClick();
            else
                amp_amountview.getAmount_increase_horizontal().performClick();
        } else if (area_amountview.hasFocus()) {
            amp_amountview.setFocusable(false);
            vlot_amountview.setFocusable(false);
            material_tv.setFocusable(false);
            if (i > 0)
                area_amountview.getAmount_dncrease_horizontal().performClick();
            else
                area_amountview.getAmount_increase_horizontal().performClick();

        } else if (material_tv.hasFocus()) {
            vlot_amountview.setFocusable(false);
            area_amountview.setFocusable(false);
            amp_amountview.setFocusable(false);
            if (i > 0)
                amp_amountview.getAmount_dncrease_horizontal().performClick();
            else
                amp_amountview.getAmount_increase_horizontal().performClick();

        }else if(amountview_year.hasFocus()){
            if (i > 0)
                amountview_year.getAmount_dncrease_horizontal().performClick();
            else
                amountview_year.getAmount_increase_horizontal().performClick();
        }else if(amountview_month.hasFocus()){
            if (i > 0)
                amountview_month.getAmount_dncrease_horizontal().performClick();
            else
                amountview_month.getAmount_increase_horizontal().performClick();
        }else if(amountview_day.hasFocus()){
            if (i > 0)
                amountview_day.getAmount_dncrease_horizontal().performClick();
            else
                amountview_day.getAmount_increase_horizontal().performClick();
        }else if(amountview_hour.hasFocus()){
            if (i > 0)
                amountview_hour.getAmount_dncrease_horizontal().performClick();
            else
                amountview_hour.getAmount_increase_horizontal().performClick();
        }else if(amountview_minutes.hasFocus()){
            if (i > 0)
                amountview_minutes.getAmount_dncrease_horizontal().performClick();
            else
                amountview_minutes.getAmount_increase_horizontal().performClick();
        }else if(amountview_tariff.hasFocus()){
            if (i > 0)
                amountview_tariff.getAmount_dncrease_horizontal().performClick();
            else
                amountview_tariff.getAmount_increase_horizontal().performClick();
        }else if(amountview_unit.hasFocus()){
            if (i > 0)
                amountview_unit.getAmount_dncrease_horizontal().performClick();
            else
                amountview_unit.getAmount_increase_horizontal().performClick();
        }
    }

    public void resetSet(){
        stopRight();
        amountValue = 1;
        amountValue2 = 1;

        config.setDipSwell_Trigger_VoltLevel(amountValue);
        config.setDipSwell_Trigger_AmpLevel(amountValue2);

        vlot_amountview.setAmount(amountValue);
        amp_amountview.setAmount(amountValue2);
        vlot_amountview.setContent(showItemsMode[amountValue - 1]);
        amp_amountview.setContent(showItemsLength[amountValue2 - 1]);

        area_amountview.setAmount(amountValue3);
        material_amountview.setAmount(amountValue4);
        area_amountview.setContent(showItemsArea[amountValue3 - 1]);
        material_amountview.setContent(showItemsMater[amountValue4 - 1]);


        amountValue_time = 1;
        amountValue_time2 = 1;
        amountValue_time3 = 1;
        amountValue_time4 = 1;
        amountValue_time5 = 1;
        amountValue_time6 = 1;
        amountValue_time7 = 1;

        amountview_year.setAmount(amountValue_time);
        amountview_month.setAmount(amountValue_time2);
        amountview_day.setAmount(amountValue_time3);
        amountview_hour.setAmount(amountValue_time4);
        amountview_minutes.setAmount(amountValue_time5);
        amountview_tariff.setAmount(amountValue_time6);
        amountview_unit.setAmount(amountValue_time7);

        amountview_year.setContent(showItemYear[amountValue_time - 1]);
        amountview_month.setContent(showItemMonth[amountValue_time2 - 1]);
        amountview_day.setContent(showItemDay[amountValue_time3 - 1]);
        amountview_hour.setContent(showItemHour[amountValue_time4 - 1]);
        amountview_minutes.setContent(showItemMiniut[amountValue_time5 - 1]);
        amountview_tariff.setContent(showItemHour[amountValue_time6 - 1]);
        amountview_unit.setContent(showItemMiniut[amountValue_time7 - 1]);

    }

    public void saveSetting(){
        config.setDipSwell_Trigger_VoltLevel(amountValue);
        config.setDipSwell_Trigger_AmpLevel(amountValue2);

        config.setDipSweel_Time_Year(amountValue_time);
        config.setDipSweel_Time_Month(amountValue_time2);
        config.setDipSweel_Time_Day(amountValue_time3);
        config.setDipSweel_Time_Hours(amountValue_time4);
        config.setDipSweel_Time_Minutes(amountValue_time5);


    }

    private boolean forbinDown;
    public boolean forbidMoveDown(){
        return forbinDown;
    }


    public void upKey() {
        if(forbinRight){



        }else {

        }
    }

    /**
     * Mode On 下不可以修改Length 和 Area
     */
    public void checkMode(){
        if(amountValue == 1){
            amp_amountview.setFocusable(false);
            area_amountview.setFocusable(false);
            vlot_amountview.setNextFocusDownId(R.id.material_amountview);
            material_amountview.setNextFocusUpId(R.id.vlot_amountview);
        }else{
            amp_amountview.setFocusable(true);
            area_amountview.setFocusable(true);
            vlot_amountview.setNextFocusDownId(R.id.amp_amountview);
            material_amountview.setNextFocusUpId(R.id.area_amountview);
        }
    }

    public void downKey() {
        if(forbinRight) {
            amountview_year.setFocusable(false);
            amountview_month.setFocusable(false);
            amountview_day.setFocusable(false);
            amountview_hour.setFocusable(false);
            amountview_minutes.setFocusable(false);
            amountview_tariff.setFocusable(false);
            amountview_unit.setFocusable(false);

            amp_amountview.setFocusable(true);
            vlot_amountview.setFocusable(true);
            area_amountview.setFocusable(true);
            material_amountview.setFocusable(true);

            if (amp_amountview.hasFocus()) {
                forbinDown = true;

            } else if(material_amountview.hasFocus()){
                forbinDown = false;
            }
        }else{
            forbinDown = false;
            amp_amountview.setFocusable(false);
            vlot_amountview.setFocusable(false);
            area_amountview.setFocusable(false);
            material_amountview.setFocusable(false);

            amountview_year.setFocusable(true);
            amountview_month.setFocusable(true);
            amountview_day.setFocusable(true);
            amountview_hour.setFocusable(true);
            amountview_minutes.setFocusable(true);
            amountview_tariff.setFocusable(true);
            amountview_unit.setFocusable(true);
        }
    }

    private boolean mFt;
    public void changeMFt(boolean mFt) {
        this.mFt = mFt;
        String unit = mFt?" m":" ft";;
        showItemsLength = new String[100001];
        for (int i = 0; i < 100001; i++) {
            showItemsLength[i] = i + unit;
        }
        amp_amountview.setDataArray(showItemsLength);
        amp_amountview.setAmount(amountValue2);
        amp_amountview.setContent(showItemsLength[amountValue2-1]);
    }

    private boolean mmAwg;
    public void changeMmAwg(boolean mmAwg) {
        this.mmAwg = mmAwg;
        String unit = mmAwg?" mm²":" AWG";;
        showItemsArea = new String[29];
        for (int i = 0; i < 29; i++) {
            showItemsArea[i] = i + unit;
        }
        area_amountview.setDataArray(showItemsArea);
        area_amountview.setAmount(amountValue3);
        area_amountview.setContent(showItemsArea[amountValue3-1]);
    }
}
