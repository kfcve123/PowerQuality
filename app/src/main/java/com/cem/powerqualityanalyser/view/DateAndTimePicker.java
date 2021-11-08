package com.cem.powerqualityanalyser.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

public class DateAndTimePicker extends Dialog {

    private Context context;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private int year= 2013;//实际值
    private int month = 6;//角标
    private int day = 20;//实际值
    private int hour = 5;
    private int miniut = 40;


    private TextView picker_save,picker_cancel;
    private String dateString,timeString;


    public DateAndTimePicker(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public DateAndTimePicker(Context context, int theme) {
        super(context, theme);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMiniut(int miniut) {
        this.miniut = miniut;
    }

    private SimpleDateFormat format;
    private Calendar calendar;
    private boolean is24Hour = true;

    public void setSimpleFormat(SimpleDateFormat format){
        this.format = format;
        if(datePicker!=null && onDateAndTimePickerCallBack!=null) {
            dateString = format.format(calendar.getTime());
            onDateAndTimePickerCallBack.returnTime(dateString, timeString);
        }
    }

    public DatePicker getDatePicker(){
        return datePicker;
    }

    public void setIs24HourView(boolean is24HourView){
        this.is24Hour = is24HourView;
        if(timePicker!=null) {
            timePicker.setIs24HourView(is24HourView);
            if(is24HourView){
                if(hour>11) {
                    if(miniut<10)
                        timeString = (hour-12) + ":0" + miniut +"PM";
                    else
                        timeString = (hour-12) + ":" + miniut +"PM";
                }else{
                    if(miniut<10) {
                        timeString = hour + ":0" + miniut +"AM";
                    }else{
                        timeString = hour + ":" + miniut +"AM";
                    }
                }
            }else{
                if(miniut<10) {
                    timeString = hour + ":0" + miniut;
                }else{
                    timeString = hour + ":" + miniut;
                }
            }
            onDateAndTimePickerCallBack.returnTime(dateString, timeString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_time_picker_view);
        setTitle(R.string.set_system_time);
        calendar = Calendar.getInstance();
        year = calendar.getWeekYear();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        miniut = calendar.get(Calendar.MINUTE);

        datePicker = (DatePicker) findViewById(R.id.dpPicker);
        timePicker = (TimePicker) findViewById(R.id.tpPicker);
        datePicker.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        timePicker.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        format = new SimpleDateFormat("MM-dd-yyyy");
        picker_save = (TextView)findViewById(R.id.picker_save);
        picker_cancel = (TextView)findViewById(R.id.picker_cancel);
        picker_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.e("");
                onDateAndTimePickerCallBack.returnTime(dateString,timeString);
                onDateAndTimePickerCallBack.returnTime(calendar.getWeekYear(),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
                dismiss();
            }
        });

        picker_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // 获取一个日历对象，并初始化为当前选中的时间
                log.e("----" + dayOfMonth);
                calendar.set(year, monthOfYear, dayOfMonth);
                dateString = format.format(calendar.getTime());
            }
        });
        
        timePicker.setIs24HourView(is24Hour);
        timePicker.setHour(hour);
        timePicker.setMinute(miniut);

        calendar.set(year, month, day);
        dateString = format.format(calendar.getTime());
        if(miniut<10) {
            timeString = hour + ":0" + miniut;
        }else{
            timeString = hour + ":" + miniut;
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay,
                                              int minute) {
                        if(timePicker.is24HourView()) {
                            timeString = hourOfDay + ":" + minute;
                        }else{
                            if(hourOfDay>12) {
                                if(miniut<10)
                                    timeString = (hourOfDay-12) + ":0" + miniut;
                                else
                                    timeString = (hourOfDay-12) + ":" + miniut;
                            }else{
                                if(miniut<10) {
                                    timeString = hourOfDay + ":0" + miniut;
                                }else{
                                    timeString = hourOfDay + ":" + miniut;
                                }
                            }
                        }
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    }
                });


    }


    public interface OnDateAndTimePickerCallBack{
        void  returnTime(String date, String time);
        void returnTime(int year, int month, int day, int hour, int miniute);
    }

    private OnDateAndTimePickerCallBack onDateAndTimePickerCallBack;

    public void setOnDateAndTimePickerCallBack(OnDateAndTimePickerCallBack onDateAndTimePickerCallBack){
        this.onDateAndTimePickerCallBack = onDateAndTimePickerCallBack;
    }

}
