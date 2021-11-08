package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 能耗计算器 的Time 设置
 */
public class EneryCalculatorTimeSet extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    private AmountViewHorizontal amountview_duration,amountview_year, amountview_month, amountview_day, amountview_hour, amountview_minutes;
    private LinearLayout transient_set_right;
    private String[] showItemDuration,showItemYear, showItemMonth, showItemDay, showItemHour, showItemMiniut;
    private CheckBox transient_immediate, transient_timed;
    private TextView transient_immediate_tv, transient_timed_tv;
    private TextView year_tv, month_tv, day_tv, hour_tv, minutes_tv;
    private int amountValueDuration,amountValue, amountValue2,amountValue3,amountValue4,amountValue5 = 1;
    private boolean powerMonitor_time_default;
    private boolean forbinUp;
    private boolean forbinLeft;
    public boolean forbidMoveRight(){
        return forbinLeft;
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
        return R.layout.fragment_powermonitorset;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        transient_set_right = (LinearLayout) findViewById(R.id.transient_set_right);

        showItemDuration = new String[]{"2H","4H","8H","12H","1d","2d","3d","4d","5d","6d","7d"};
        showItemYear = new String[]{"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
        showItemMonth = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        showItemDay = new String[31];
        showItemHour = new String[24];
        showItemMiniut = new String[60];
        for (int i = 0; i < 31; i++) {
            showItemDay[i] = (i + 1) + "";
        }
        for (int i = 0; i < 24; i++) {
            showItemHour[i] = (i + 1) + "";
        }
        for (int i = 0; i < 60; i++) {
            showItemMiniut[i] = (i + 1) + "";
        }

        transient_immediate = (CheckBox) findViewById(R.id.transient_immediate);
        transient_timed = (CheckBox) findViewById(R.id.transient_timed);
        transient_immediate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                powerMonitor_time_default = isChecked;
            }
        });

        transient_immediate_tv = (TextView) findViewById(R.id.transient_immediate_tv);
        transient_timed_tv = (TextView) findViewById(R.id.transient_timed_tv);


        year_tv = (TextView) findViewById(R.id.year_tv);
        month_tv = (TextView) findViewById(R.id.month_tv);
        day_tv = (TextView) findViewById(R.id.day_tv);
        hour_tv = (TextView) findViewById(R.id.hour_tv);
        minutes_tv = (TextView) findViewById(R.id.minutes_tv);

        year_tv.setTextColor(getResources().getColor(R.color.colorblack));
        month_tv.setTextColor(getResources().getColor(R.color.colorblack));
        day_tv.setTextColor(getResources().getColor(R.color.colorblack));
        hour_tv.setTextColor(getResources().getColor(R.color.colorblack));
        minutes_tv.setTextColor(getResources().getColor(R.color.colorblack));

        transient_immediate.setClickable(false);
        transient_timed.setClickable(false);

        transient_immediate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    forbinLeft = true;
                    transient_immediate_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_immediate.setChecked(true);
                    transient_timed.setChecked(false);
                } else {
                    forbinLeft = true;
                    transient_immediate_tv.setBackground(null);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        transient_timed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    forbinLeft = true;
                    transient_timed_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_timed.setChecked(true);
                    transient_immediate.setChecked(false);
                } else {
                    forbinLeft = false;
                    transient_timed_tv.setBackground(null);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        amountview_duration = (AmountViewHorizontal) findViewById(R.id.amountview_duration);
        amountview_duration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    forbinUp = true;
                }else{
                    forbinUp = false;
                }
            }
        });


        amountview_year = (AmountViewHorizontal) findViewById(R.id.amountview_year);
        amountview_month = (AmountViewHorizontal) findViewById(R.id.amountview_month);
        amountview_day = (AmountViewHorizontal) findViewById(R.id.amountview_day);
        amountview_hour = (AmountViewHorizontal) findViewById(R.id.amountview_hour);
        amountview_minutes = (AmountViewHorizontal) findViewById(R.id.amountview_minutes);

        amountview_duration.setAmountViewLayout(220);
        amountview_duration.setOnHorizonalAmountChangeListener(this);
        amountview_duration.setDataArray(showItemDuration);
        amountview_duration.setShowAmount(false);
//        amountview_year.setAmountViewLayout(250);
//        amountview_month.setAmountViewLayout(250);
//        amountview_day.setAmountViewLayout(250);
//        amountview_hour.setAmountViewLayout(250);
//        amountview_minutes.setAmountViewLayout(250);

        amountview_year.setShowValueBg(true);
        amountview_year.setOnHorizonalAmountChangeListener(this);
        amountview_year.setDataArray(showItemYear);
        amountview_year.setAmountViewLayout(220);

        amountview_year.setShowAmount(false);
        amountview_month.setShowAmount(false);
        amountview_day.setShowAmount(false);
        amountview_hour.setShowAmount(false);
        amountview_minutes.setShowAmount(false);


        amountview_month.setShowValueBg(true);
        amountview_month.setOnHorizonalAmountChangeListener(this);
        amountview_month.setDataArray(showItemMonth);
        amountview_month.setAmountViewLayout(220);

        amountview_day.setShowValueBg(true);
        amountview_day.setOnHorizonalAmountChangeListener(this);
        amountview_day.setDataArray(showItemDay);
        amountview_day.setAmountViewLayout(220);

        amountview_hour.setShowValueBg(true);
        amountview_hour.setOnHorizonalAmountChangeListener(this);
        amountview_hour.setDataArray(showItemHour);
        amountview_hour.setAmountViewLayout(220);

        amountview_minutes.setShowValueBg(true);
        amountview_minutes.setOnHorizonalAmountChangeListener(this);
        amountview_minutes.setDataArray(showItemMiniut);
        amountview_minutes.setAmountViewLayout(220);

        if(AppConfig.getInstance().isPowerMonitor_Time_Immediate()){
            powerMonitor_time_default = true;
            transient_immediate.setChecked(true);
            transient_timed.setChecked(false);

            transient_immediate.setFocusable(true);
            transient_immediate.setFocusableInTouchMode(true);
            transient_immediate.requestFocus();
            transient_immediate.requestFocusFromTouch();

            amountValueDuration = 1;
            amountValue = 1;
            amountValue2 = 1;
            amountValue3 = 1;
            amountValue4 = 1;
            amountValue5 = 1;

        }else{
            transient_immediate.setChecked(false);
            transient_timed.setChecked(true);

            transient_timed.setFocusable(true);
            transient_timed.setFocusableInTouchMode(true);
            transient_timed.requestFocus();
            transient_timed.requestFocusFromTouch();

            powerMonitor_time_default = false;
            amountValueDuration = config.getPowerMonitor_Duration();
            amountValue = config.getPowerMonitor_Time_Year();
            amountValue2 = config.getPowerMonitor_Time_Month();
            amountValue3 = config.getPowerMonitor_Time_Day();
            amountValue4 = config.getPowerMonitor_Time_Hours();
            amountValue5 = config.getPowerMonitor_Time_Minutes();


            transient_timed_tv.setBackgroundResource(R.mipmap.logger_name_bg);
            transient_timed_tv.setTextColor(getResources().getColor(R.color.colorwhite));

        }

        amountview_duration.setAmount(amountValueDuration);
        amountview_duration.setContent(showItemDuration[amountValueDuration-1]);
        amountview_year.setAmount(amountValue);
        amountview_year.setContent(showItemYear[amountValue-1]);
        amountview_month.setAmount(amountValue2);
        amountview_month.setContent(showItemMonth[amountValue2-1]);
        amountview_day.setAmount(amountValue3);
        amountview_day.setContent(showItemDay[amountValue3-1]);
        amountview_hour.setAmount(amountValue4);
        amountview_hour.setContent(showItemHour[amountValue4-1]);
        amountview_minutes.setAmount(amountValue5);
        amountview_minutes.setContent(showItemMiniut[amountValue5-1]);

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.amountview_duration:
                amountValueDuration = amount;
                amountview_duration.setContent(showItemDuration[amount-1]);
                break;
            case R.id.amountview_year:
                amountValue = amount;
                amountview_year.setContent(showItemYear[amount - 1]);
                break;
            case R.id.amountview_month:
                amountValue2 = amount;
                amountview_month.setContent(showItemMonth[amount - 1]);
                break;
            case R.id.amountview_day:
                amountValue3 = amount;
                amountview_day.setContent(showItemDay[amount - 1]);
                break;
            case R.id.amountview_hour:
                amountValue4 = amount;
                amountview_hour.setContent(showItemHour[amount - 1]);
                break;
            case R.id.amountview_minutes:
                amountValue5 = amount;
                amountview_minutes.setContent(showItemMiniut[amount - 1]);
                break;

        }
    }


    public void moveCursor(int i) {
        if(amountview_duration.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_duration.getAmount_dncrease_horizontal().performClick();
            else
                amountview_duration.getAmount_increase_horizontal().performClick();
        }else if (amountview_year.hasFocus()) {
            forbinLeft = true;
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_year.getAmount_dncrease_horizontal().performClick();
            else
                amountview_year.getAmount_increase_horizontal().performClick();
        } else if (amountview_month.hasFocus()) {
            forbinLeft = true;
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_month.getAmount_dncrease_horizontal().performClick();
            else
                amountview_month.getAmount_increase_horizontal().performClick();
        } else if (amountview_day.hasFocus()) {
            forbinLeft = true;
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_day.getAmount_dncrease_horizontal().performClick();
            else
                amountview_day.getAmount_increase_horizontal().performClick();
        } else if (amountview_hour.hasFocus()) {
            forbinLeft = true;
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_hour.getAmount_dncrease_horizontal().performClick();
            else
                amountview_hour.getAmount_increase_horizontal().performClick();
        } else if (amountview_minutes.hasFocus()) {
            forbinLeft = true;
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_minutes.getAmount_dncrease_horizontal().performClick();
            else
                amountview_minutes.getAmount_increase_horizontal().performClick();
        }

    }


    public void upKey() {
        if (amountview_year.hasFocus()) {
            transient_timed.setFocusable(true);
        } else if (transient_timed.hasFocus()) {
            transient_immediate.setFocusable(true);
        }
    }

    public void downKey() {
        if(amountview_duration.hasFocus()){
            transient_immediate.setFocusable(true);
        }else if(transient_immediate.hasFocus()){
            transient_timed.setFocusable(true);
        }
    }

    public void resetSet() {
        transient_immediate.setChecked(true);
        transient_timed.setChecked(false);

        transient_immediate.setFocusable(true);
        transient_immediate.setFocusableInTouchMode(true);
        transient_immediate.requestFocus();
        transient_immediate.requestFocusFromTouch();

        amountValueDuration = 1;
        amountValue5 = 1;
        amountValue = 1;
        amountValue2 = 1;
        amountValue3 = 1;
        amountValue4 = 1;
        amountValue5 = 1;
        powerMonitor_time_default = true;

        amountview_duration.setAmount(amountValueDuration);
        amountview_year.setAmount(amountValue);
        amountview_month.setAmount(amountValue2);
        amountview_day.setAmount(amountValue3);
        amountview_hour.setAmount(amountValue4);
        amountview_minutes.setAmount(amountValue5);


        amountview_duration.setContent(showItemDuration[amountValueDuration-1]);
        amountview_year.setContent(showItemYear[amountValue - 1]);
        amountview_month.setContent(showItemMonth[amountValue2 - 1]);
        amountview_day.setContent(showItemDay[amountValue3 - 1]);
        amountview_hour.setContent(showItemHour[amountValue4 - 1]);
        amountview_minutes.setContent(showItemMiniut[amountValue5 - 1]);

        transient_immediate.setChecked(true);
        transient_timed.setChecked(false);
    }


    public void saveSetting() {
        config.setPowerMonitor_Time_Immediate(powerMonitor_time_default);
        config.setPowerMonitor_Duration(amountValueDuration);
        config.setPowerMonitor_Time_Year(amountValue);
        config.setPowerMonitor_Time_Month(amountValue2);
        config.setPowerMonitor_Time_Day(amountValue3);
        config.setPowerMonitor_Time_Hours(amountValue4);
        config.setPowerMonitor_Time_Minutes(amountValue5);

    }
}
