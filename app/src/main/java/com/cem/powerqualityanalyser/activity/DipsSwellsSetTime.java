package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import serialport.amos.cem.com.libamosserial.ModelAllData;


/**
 * 瞬变 参数设置  Volt lever: 0 到 7 ，0.01 一加
 */
public class DipsSwellsSetTime extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

    /**
     * 右边控件
     */
    private AmountViewHorizontal amountview_year, amountview_month, amountview_day, amountview_hour, amountview_minutes;
    private LinearLayout transient_set_right;
    private String[] showItemYear, showItemMonth, showItemDay, showItemHour, showItemMiniut;
    private CheckBox transient_immediate, transient_timed;
    private TextView transient_immediate_tv, transient_timed_tv;
    private TextView year_tv, month_tv, day_tv, hour_tv, minutes_tv;

    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        log.e("-----Right------");

        transient_set_right.setEnabled(true);
        transient_immediate.setFocusable(true);
        transient_immediate.setFocusableInTouchMode(true);
        transient_immediate.requestFocus();


        transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorwhite));
        transient_timed_tv.setTextColor(getResources().getColor(R.color.colorblack));
        year_tv.setTextColor(getResources().getColor(R.color.colorblack));
        month_tv.setTextColor(getResources().getColor(R.color.colorblack));
        day_tv.setTextColor(getResources().getColor(R.color.colorblack));
        hour_tv.setTextColor(getResources().getColor(R.color.colorblack));
        minutes_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_immediate.setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_selector));
        transient_timed.setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_selector));

    }

    /**
     * 停用右半边控件
     */
    public void stopRight(boolean first) {
        log.e("-----Left------");
        amountview_year.setFocusable(false);
        amountview_month.setFocusable(false);
        amountview_day.setFocusable(false);
        amountview_hour.setFocusable(false);
        amountview_minutes.setFocusable(false);

        transient_set_right.setEnabled(false);

        transient_immediate_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        transient_timed_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        year_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        month_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        day_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        hour_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));
        minutes_tv.setTextColor(getResources().getColor(R.color.transient_tv_color));

        transient_immediate.setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_selector1));
        transient_timed.setButtonDrawable(getResources().getDrawable(R.drawable.radiobutton_selector1));
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
        return R.layout.fragment_transientsettime;
    }

    @Override
    public void onInitView() {

        transient_set_right = (LinearLayout) findViewById(R.id.transient_set_right);

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
                    transient_immediate_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                } else {
                    transient_immediate_tv.setBackground(null);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        transient_timed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    transient_timed_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                } else {
                    transient_timed_tv.setBackground(null);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });


        amountview_year = (AmountViewHorizontal) findViewById(R.id.amountview_year);
        amountview_month = (AmountViewHorizontal) findViewById(R.id.amountview_month);
        amountview_day = (AmountViewHorizontal) findViewById(R.id.amountview_day);
        amountview_hour = (AmountViewHorizontal) findViewById(R.id.amountview_hour);
        amountview_minutes = (AmountViewHorizontal) findViewById(R.id.amountview_minutes);

        amountview_year.setAmountViewLayout(250);
        amountview_month.setAmountViewLayout(250);
        amountview_day.setAmountViewLayout(250);
        amountview_hour.setAmountViewLayout(250);
        amountview_minutes.setAmountViewLayout(250);

        amountview_year.setShowValueBg(true);
        amountview_year.setOnHorizonalAmountChangeListener(this);
        amountview_year.setAmount(1);
        amountview_year.setContent(showItemYear[0]);
        amountview_year.setDataArray(showItemYear);
        amountview_year.setAmountViewLayout(220);

        amountview_year.setShowAmount(false);
        amountview_month.setShowAmount(false);
        amountview_day.setShowAmount(false);
        amountview_hour.setShowAmount(false);
        amountview_minutes.setShowAmount(false);

        amountview_month.setShowValueBg(true);
        amountview_month.setOnHorizonalAmountChangeListener(this);
        amountview_month.setAmount(1);
        amountview_month.setContent(showItemMonth[0]);
        amountview_month.setDataArray(showItemMonth);
        amountview_month.setAmountViewLayout(220);

        amountview_day.setShowValueBg(true);
        amountview_day.setOnHorizonalAmountChangeListener(this);
        amountview_day.setAmount(1);
        amountview_day.setContent(showItemDay[0]);
        amountview_day.setDataArray(showItemDay);
        amountview_day.setAmountViewLayout(220);

        amountview_hour.setShowValueBg(true);
        amountview_hour.setOnHorizonalAmountChangeListener(this);
        amountview_hour.setAmount(1);
        amountview_hour.setContent(showItemHour[0]);
        amountview_hour.setDataArray(showItemHour);
        amountview_hour.setAmountViewLayout(220);

        amountview_minutes.setShowValueBg(true);
        amountview_minutes.setOnHorizonalAmountChangeListener(this);
        amountview_minutes.setAmount(1);
        amountview_minutes.setContent(showItemMiniut[0]);
        amountview_minutes.setDataArray(showItemMiniut);
        amountview_minutes.setAmountViewLayout(220);

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.amountview_year:
                amountview_year.setContent(showItemYear[amount - 1]);
                break;
            case R.id.amountview_month:
                amountview_month.setContent(showItemMonth[amount - 1]);
                break;
            case R.id.amountview_day:
                amountview_day.setContent(showItemDay[amount - 1]);
                break;
            case R.id.amountview_hour:
                amountview_hour.setContent(showItemHour[amount - 1]);
                break;
            case R.id.amountview_minutes:
                amountview_minutes.setContent(showItemMiniut[amount - 1]);
                break;

        }
    }
    private boolean forbinLeft;
    public boolean forbidMoveRight(){
        return forbinLeft;
    }

    public void moveCursor(int i) {
        if (amountview_year.hasFocus()) {
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_year.getAmount_dncrease_horizontal().performClick();
            else
                amountview_year.getAmount_increase_horizontal().performClick();
        } else if (amountview_month.hasFocus()) {
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_month.getAmount_dncrease_horizontal().performClick();
            else
                amountview_month.getAmount_increase_horizontal().performClick();
        } else if (amountview_day.hasFocus()) {
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_day.getAmount_dncrease_horizontal().performClick();
            else
                amountview_day.getAmount_increase_horizontal().performClick();
        } else if (amountview_hour.hasFocus()) {
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_hour.getAmount_dncrease_horizontal().performClick();
            else
                amountview_hour.getAmount_increase_horizontal().performClick();
        } else if (amountview_minutes.hasFocus()) {
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

    public boolean downKey() {

        return false;
    }

    public void resetSet() {
    }

    public void saveSetting() {
    }
}
