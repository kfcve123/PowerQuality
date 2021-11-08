package com.cem.powerqualityanalyser.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;
import com.cem.powerqualityanalyser.view.AmountViewVertical;
import com.cem.powerqualityanalyser.view.DateAndTimePicker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * 日期时间 5
 */
public class SetupChildDateActivity extends BaseActivity implements AmountViewHorizontal.OnHorizonalAmountChangeListener{

    private AmountViewHorizontal amount_view6, amount_view7;
    private int amountValue1, amountValue2, amountValue3, amountValue4, amountValue5;
    private int amountValue6 = 1, amountValue7 = 1;

    private TextView setup_system_time;
    private DateAndTimePicker dateAndTimePicker;
    private String[] formatDateItems;
    private String[] formatTimeItems;
    private SimpleDateFormat dateFormat,timeFormat24,timeFormat12,fullFormat24,fullFormat12,fullFormat24dd,fullFormat12dd,formatHH;
    private List<SimpleDateFormat> formats;
    private int formatIndex = 0;
    private Date date;
    private String dataStr = "";
    private int hh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_five);
        setTitle(R.string.set);
        formatDateItems = new String[]{"dd-MM-yyyy","MM-dd-yyyy"};
        formatTimeItems = new String[]{"12/24","AM/PM"};
        amount_view6 = (AmountViewHorizontal) findViewById(R.id.amount_view6);
        amount_view7 = (AmountViewHorizontal) findViewById(R.id.amount_view7);
        amount_view6.setOnHorizonalAmountChangeListener(this);
        amount_view7.setOnHorizonalAmountChangeListener(this);
        amount_view6.setShowAmount(false);
        amount_view7.setShowAmount(false);
        amount_view6.setDataArray(formatDateItems);
        amount_view7.setDataArray(formatTimeItems);
        amount_view6.setShowValueBg(true);
        amount_view7.setShowValueBg(true);


        amount_view6.setContent(formatDateItems[amountValue6-1]);
        amount_view7.setContent(formatTimeItems[amountValue7-1]);
        amount_view6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amount_view6.setShowAmount(true);
                }else{
                    amount_view6.setShowAmount(false);
                }
            }
        });

        amount_view7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    amount_view7.setShowAmount(true);
                }else{
                    amount_view7.setShowAmount(false);
                }
            }
        });

        setup_system_time = findViewById(R.id.setup_system_time);
        date = new Date();
        formats = new ArrayList<>();

        fullFormat24dd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        fullFormat12dd = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        fullFormat24 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        fullFormat12 = new SimpleDateFormat("MM/dd/yyyy hh:mm");

        formatHH = new SimpleDateFormat("HH");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timeFormat24 = new SimpleDateFormat("HH:mm");
        timeFormat12 = new SimpleDateFormat("hh:mm");

        formats.add(fullFormat24dd);
        formats.add(fullFormat12dd);
        formats.add(fullFormat24);
        formats.add(fullFormat12);

        setup_system_time.setText(fullFormat24dd.format(date));
        hh = Integer.valueOf(formatHH.format(date));
        setup_system_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setup_system_time.setBackground(getDrawable(R.mipmap.logger_name_bg));
                    setup_system_time.setTextColor(getResources().getColor(R.color.colorwhite));
                }else{
                    setup_system_time.setBackground(getDrawable(R.mipmap.date_nocolor_bg));
                    setup_system_time.setTextColor(getResources().getColor(R.color.set_text_color));
                }
            }
        });
        setup_system_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialogshow();
            }
        });

        dateAndTimePicker = new DateAndTimePicker(this);
        dateAndTimePicker.setSimpleFormat(fullFormat24dd);

        dateAndTimePicker.setOnDateAndTimePickerCallBack(new DateAndTimePicker.OnDateAndTimePickerCallBack() {
            @Override
            public void returnTime(String date, String time) {
                try {
                    setup_system_time.setText(date + "     " +  time);
  //                  updateSystemTime(date +" " + time,formats.get(formatIndex));
 //                   updateSystemTime(2021,8,20,11,22);
                    //               updateSystemTime();
                }catch (Exception e){

                }
            }

            @Override
            public void returnTime(int year, int month, int day, int hour, int miniute) {
//                log.e(year + "---" + month +  "----" + day +" ---" + hour+ "-----" + miniute );
                updateSystemTime(year,month,day,hour,miniute);
            }
        });
        requestPermissions(new String[]{Manifest.permission.SET_TIME}, 10);


    }

    @Override
    public byte[] getMode() {
        return null;
    }

    public void updateSystemTime(){
        try {
            Process process = Runtime.getRuntime().exec("su");
            String datetime="20131023.112800"; //测试的设置的时间【时间格式 yyyyMMdd.HHmmss】
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("setprop persist.sys.timezone GMT\n");
            os.writeBytes("/system/bin/date -s "+datetime+"\n");
            os.writeBytes("clock -w\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSystemTime(String dataStr,SimpleDateFormat format){
        try {
            long timeInMillis = format.parse(dataStr).getTime();
            if (timeInMillis / 1000 < Integer.MAX_VALUE){
                log.e("---------------" + new Date(timeInMillis));
                SystemClock.setCurrentTimeMillis(timeInMillis);
            }
        }catch (Exception e){

        }
    }


    public void updateSystemTime(int year, int month, int day, int hour, int miniute){
        try {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR,year);
        instance.set(Calendar.MONTH,month);
        instance.set(Calendar.DAY_OF_MONTH,day);
        instance.set(Calendar.HOUR,hour);
        instance.set(Calendar.MINUTE,miniute);
        long timeInMillis = instance.getTimeInMillis();
        if (timeInMillis / 1000 < Integer.MAX_VALUE){
            log.e("---------------" + new Date(timeInMillis));
            SystemClock.setCurrentTimeMillis(timeInMillis);
        }
        }catch (Exception e){

        }
    }
    /**
     * 选择时间
     */
    private void timeDialogshow() {

        dateAndTimePicker.show();

    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        data.add(new BaseBottomAdapterObj(null));
        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.amount_view6:
                amountValue6 = amount;
                amount_view6.setContent(formatDateItems[amount-1]);
                date = new Date();
                hh = Integer.valueOf(formatHH.format(date));

                if(amountValue6 == 1){
                    if(amountValue7 == 1) {
                        formatIndex = 0;
                        dataStr = "";
                    }else {
                        formatIndex = 1;
                        if(hh>11){
                            dataStr = "PM";
                        }else{
                            dataStr = "PM";
                        }
                    }
                }else{
                    if(amountValue7 == 1) {
                        formatIndex = 2;
                        dataStr = "";
                    }else {
                        formatIndex = 3;
                        if (hh > 11) {
                            dataStr = "PM";
                        } else {
                            dataStr = "PM";
                        }
                    }
                }

                dateAndTimePicker.setSimpleFormat(formats.get(formatIndex));
                setup_system_time.setText(formats.get(formatIndex).format(date) + dataStr);
                break;
            case R.id.amount_view7:
                amountValue7 = amount;
                amount_view7.setContent(formatTimeItems[amount-1]);
                dateAndTimePicker.setIs24HourView(amount == 1?true:false);
                date = new Date();
                hh = Integer.valueOf(formatHH.format(date));

                if(amountValue6 == 1){
                    if(amountValue7 == 1) {
                        formatIndex = 0;
                        dataStr = "";
                    }else {
                        formatIndex = 1;
                        if(hh>11){
                            dataStr = "PM";
                        }else{
                            dataStr = "PM";
                        }
                    }
                }else{
                    if(amountValue7 == 1) {
                        formatIndex = 2;
                        dataStr = "";
                    }else {
                        formatIndex = 3;
                        if (hh > 11) {
                            dataStr = "PM";
                        } else {
                            dataStr = "PM";
                        }
                    }
                }
                setup_system_time.setText(formats.get(formatIndex).format(date) + dataStr);
                break;
        }
    }

    public void moveCursor(int i) {
        if (amount_view6.hasFocus()){
            if (i > 0)
                amount_view6.getAmount_dncrease_horizontal().performClick();
            else
                amount_view6.getAmount_increase_horizontal().performClick();
        }else if (amount_view7.hasFocus()){
            if (i > 0)
                amount_view7.getAmount_dncrease_horizontal().performClick();
            else
                amount_view7.getAmount_increase_horizontal().performClick();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        possKeyDown(keyCode);
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Left:
                moveCursor(-1);
                return true;
            case Right:
                moveCursor(1);
                return true;
            case Back:
                onBackPressed();
                break;
            case Enter:

                break;

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }
}