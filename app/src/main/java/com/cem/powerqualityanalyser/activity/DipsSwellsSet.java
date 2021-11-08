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
public class DipsSwellsSet extends BaseFragmentTrend implements AmountViewHorizontal.OnHorizonalAmountChangeListener {

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
     * 右边控件
     */
    private LinearLayout transient_set_right;
    private String[] showItemYear, showItemMonth, showItemDay, showItemHour, showItemMiniut;
    private AmountViewHorizontal amountview_year, amountview_month, amountview_day, amountview_hour, amountview_minutes;
    private CheckBox transient_immediate, transient_timed;
    private TextView transient_immediate_tv, transient_timed_tv;
    private TextView year_tv, month_tv, day_tv, hour_tv, minutes_tv;
    private int amountValue_time, amountValue_time2,amountValue_time3,amountValue_time4,amountValue_time5 = 1;
    private boolean transient_time_default;


    /**
     * 停用左半边控件
     */
    public void stopLeft() {
        log.e("-----Right------");
        forbinRight = false;
        forbinUp = false;
        forbinDown = false;
        forbinTimeRight = false;
        amp_amountview.setEnabled(false);
        vlot_amountview.setEnabled(false);
        transient_set_left.setEnabled(false);


        transient_voltage_transient.setClickable(false);
        transient_amps.setClickable(false);

        transient_set_left.setFocusable(false);
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

    private boolean forbinRight = true;
    public boolean forbidMoveRight(){
        return forbinRight;
    }

    /**
     * 停用右半边控件
     */
    public void stopRight() {
        forbinRight = true;
        forbinUp = false;
        forbinDown = false;
        log.e("-----Left------");
//        vlot_amountview.setFocusableInTouchMode(true);
        vlot_amountview.requestFocus();
        vlot_amountview.setShowAmount(true);

        vlot_amountview.setFocusable(true);
        amp_amountview.setFocusable(true);
        transient_voltage_transient.setFocusable(true);
        transient_amps.setFocusable(true);

        transient_voltage_transient.setClickable(true);
        transient_amps.setClickable(true);


        transient_set_left.setEnabled(true);
        amp_amountview.setEnabled(true);
        vlot_amountview.setEnabled(true);


        transient_voltage_transient_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_tv2.setTextColor(getResources().getColor(R.color.colorblack));
        voltlevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        amplevel_tv.setTextColor(getResources().getColor(R.color.colorblack));
        transient_amps_img.setImageResource(R.mipmap.amps_icon_select);


        amountview_year.setFocusable(false);
        amountview_month.setFocusable(false);
        amountview_day.setFocusable(false);
        amountview_hour.setFocusable(false);
        amountview_minutes.setFocusable(false);

        transient_immediate.setFocusable(false);
        transient_timed.setFocusable(false);
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
        return R.layout.fragment_dipswellset;
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

        amp_amountview.setShowValueBg(true);
        amp_amountview.setOnHorizonalAmountChangeListener(this);
        amp_amountview.setShowAmount(false);
        amp_amountview.setDataArray(showItems2);

        transient_voltage_transient = (CheckBox) findViewById(R.id.transient_voltage_transient);
        transient_voltage_transient.setFocusable(false);
        transient_amps = (CheckBox) findViewById(R.id.transient_amps);
        transient_amps.setFocusable(false);
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


        /**
         * RightView
         */
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
        transient_immediate.setChecked(true);
        transient_immediate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    forbinUp = true;
                    transient_immediate_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_immediate.setChecked(true);
                    transient_timed.setChecked(false);
                } else {
                    forbinUp = false;
                    transient_immediate_tv.setBackground(null);
                    transient_immediate_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        transient_voltage_transient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                voltage_transient = isChecked;
            }
        });

        transient_timed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    transient_timed_tv.setBackgroundResource(R.mipmap.logger_name_bg);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorwhite));
                    transient_timed.setChecked(true);
                    transient_immediate.setChecked(false);
                } else {
                    transient_timed_tv.setBackground(null);
                    transient_timed_tv.setTextColor(getResources().getColor(R.color.colorblack));
                }
            }
        });

        transient_amps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                amps_transient = isChecked;
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

        if(config.isTransientSet_Default()){
            amountValue = 1;
            amountValue2 = 1;
            voltage_transient = false;
            amps_transient = false;
            amountValue_time = 1;
            amountValue_time2 = 1;
            amountValue_time3 = 1;
            amountValue_time4 = 1;
            amountValue_time5 = 1;
            transient_time_default = true;

        }else{
            amountValue = config.getDipSwell_Trigger_VoltLevel();
            amountValue2 = config.getDipSwell_Trigger_AmpLevel();
            voltage_transient = config.isDipSwell_Trigger_Voltage();
            amps_transient = config.isDipSwell_Trigger_AMPS();

            amountValue_time = config.getDipSweel_Time_Year();
            amountValue_time2 = config.getDipSweel_Time_Month();
            amountValue_time3 = config.getDipSweel_Time_Day();
            amountValue_time4 = config.getDipSweel_Time_Hours();
            amountValue_time5 = config.getDipSweel_Time_Minutes();
            transient_time_default = config.isDipSwell_Trigger_Immediate();

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


        if(transient_time_default) {
            transient_immediate.setChecked(true);
            transient_timed.setChecked(false);
        }else{
            transient_immediate.setChecked(false);
            transient_timed.setChecked(true);
        }

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

        stopRight();
    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.vlot_amountview:
                amountValue = amount;
                vlot_amountview.setContent(showItems[amount - 1]);
                break;
            case R.id.amp_amountview:
                amountValue2 = amount;
                amp_amountview.setContent(showItems2[amount - 1]);
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
            vlot_amountview.setFocusable(false);
            amp_amountview.setFocusable(false);

        }else if(transient_immediate.hasFocus() ||  transient_timed.hasFocus()){
            forbinTimeRight = true;
            vlot_amountview.setFocusable(false);
            amp_amountview.setFocusable(false);

        }else if(amountview_year.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_year.getAmount_dncrease_horizontal().performClick();
            else
                amountview_year.getAmount_increase_horizontal().performClick();
        }else if(amountview_month.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_month.getAmount_dncrease_horizontal().performClick();
            else
                amountview_month.getAmount_increase_horizontal().performClick();
        }else if(amountview_day.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_day.getAmount_dncrease_horizontal().performClick();
            else
                amountview_day.getAmount_increase_horizontal().performClick();
        }else if(amountview_hour.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_hour.getAmount_dncrease_horizontal().performClick();
            else
                amountview_hour.getAmount_increase_horizontal().performClick();
        }else if(amountview_minutes.hasFocus()){
            transient_immediate.setFocusable(false);
            transient_timed.setFocusable(false);
            if (i > 0)
                amountview_minutes.getAmount_dncrease_horizontal().performClick();
            else
                amountview_minutes.getAmount_increase_horizontal().performClick();
        }
    }

    public void resetSet(){
        stopRight();
        amountValue = 1;
        amountValue2 = 1;
        voltage_transient = true;
        amps_transient = true;
        config.setDipSwell_Trigger_VoltLevel(amountValue);
        config.setDipSwell_Trigger_AmpLevel(amountValue2);
        config.setDipSwell_Trigger_Voltage(voltage_transient);
        config.setDipSwell_Trigger_AMPS(amps_transient);

        vlot_amountview.setAmount(amountValue);
        amp_amountview.setAmount(amountValue2);
        vlot_amountview.setContent(showItems[amountValue - 1]);
        amp_amountview.setContent(showItems2[amountValue2 - 1]);

        transient_voltage_transient.setChecked(voltage_transient);
        transient_amps.setChecked(amps_transient);

        amountValue_time = 1;
        amountValue_time2 = 1;
        amountValue_time3 = 1;
        amountValue_time4 = 1;
        amountValue_time5 = 1;
        transient_time_default = true;


        amountview_year.setAmount(amountValue_time);
        amountview_month.setAmount(amountValue_time2);
        amountview_day.setAmount(amountValue_time3);
        amountview_hour.setAmount(amountValue_time4);
        amountview_minutes.setAmount(amountValue_time5);


        amountview_year.setContent(showItemYear[amountValue_time - 1]);
        amountview_month.setContent(showItemMonth[amountValue_time2 - 1]);
        amountview_day.setContent(showItemDay[amountValue_time3 - 1]);
        amountview_hour.setContent(showItemHour[amountValue_time4 - 1]);
        amountview_minutes.setContent(showItemMiniut[amountValue_time5 - 1]);

        transient_immediate.setChecked(true);
        transient_timed.setChecked(false);

    }

    public void saveSetting(){
        config.setDipSwell_Trigger_VoltLevel(amountValue);
        config.setDipSwell_Trigger_AmpLevel(amountValue2);
        config.setDipSwell_Trigger_AMPS(amps_transient);
        config.setDipSwell_Trigger_Voltage(voltage_transient);

        config.setDipSwell_Time_Immediate(transient_time_default);
        config.setDipSweel_Time_Year(amountValue_time);
        config.setDipSweel_Time_Month(amountValue_time2);
        config.setDipSweel_Time_Day(amountValue_time3);
        config.setDipSweel_Time_Hours(amountValue_time4);
        config.setDipSweel_Time_Minutes(amountValue_time5);


    }


    private boolean forbinUp;
    public boolean forbidMoveUp(){
        return forbinUp;
    }

    private boolean forbinDown;
    public boolean forbidMoveDown(){
        return forbinDown;
    }

    private boolean forbinTimeRight;
    public boolean forbidTimeMoveRight(){
        return forbinTimeRight;
    }


    public void upKey() {
        if(forbinRight){



        }else {
            if(transient_immediate.hasFocus()) {
                forbinUp = true;
            }else{
                forbinUp = false;
            }
        }
    }

    public void downKey() {
        if(forbinRight) {
            transient_timed.setFocusable(false);
            transient_immediate.setFocusable(false);
            amountview_year.setFocusable(false);
            amountview_month.setFocusable(false);
            amountview_day.setFocusable(false);
            amountview_hour.setFocusable(false);
            amountview_minutes.setFocusable(false);

            amp_amountview.setFocusable(true);
            vlot_amountview.setFocusable(true);
            transient_voltage_transient.setFocusable(true);
            transient_amps.setFocusable(true);

            if (amp_amountview.hasFocus()) {
                forbinDown = false;
                transient_voltage_transient.setFocusable(true);
            } else if(vlot_amountview.hasFocus()){
                forbinDown = false;
            } else if (transient_voltage_transient.hasFocus()) {
                transient_amps.setFocusable(true);
                forbinDown = false;
            } else if(transient_amps.hasFocus()){
                forbinDown = true;
            }
        }else{
            forbinDown = false;
            amp_amountview.setFocusable(false);
            vlot_amountview.setFocusable(false);
            transient_voltage_transient.setFocusable(false);
            transient_amps.setFocusable(false);

            transient_timed.setFocusable(true);
            transient_immediate.setFocusable(true);
            amountview_year.setFocusable(true);
            amountview_month.setFocusable(true);
            amountview_day.setFocusable(true);
            amountview_hour.setFocusable(true);
            amountview_minutes.setFocusable(true);


//            private AmountViewHorizontal amountview_year, amountview_month, amountview_day, amountview_hour, amountview_minutes;
//            private CheckBox transient_immediate, transient_timed;
            if(amountview_year.hasFocus()){

            }

        }
    }
}
