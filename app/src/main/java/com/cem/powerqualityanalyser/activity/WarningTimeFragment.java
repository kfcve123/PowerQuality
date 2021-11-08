package com.cem.powerqualityanalyser.activity;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.view.DateAndTimePicker;


import java.text.SimpleDateFormat;
import java.util.Date;

import serialport.amos.cem.com.libamosserial.ModelAllData;


public class WarningTimeFragment extends BaseFragmentTrend implements View.OnClickListener, View.OnFocusChangeListener, WarningActivity.WarnTouchEvent {

    private DateAndTimePicker startPicker,endPicker;
    private TextView warn_end_time,warn_start_time;
    private EditText warn_file_name;

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
        return R.layout.fragment_warntime;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        warn_end_time = (TextView) findViewById(R.id.warn_end_time);
        warn_start_time = (TextView) findViewById(R.id.warn_start_time);
        warn_file_name = (EditText) findViewById(R.id.warn_file_name);
        warn_file_name.setOnFocusChangeListener(this);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        warn_start_time.setText(format.format(date));
        warn_end_time.setText(format.format(date));
        warn_start_time.setOnClickListener(this);
        warn_end_time.setOnClickListener(this);
        ((WarningActivity) getActivity()).registerWarnTouch(this);
        startPicker = new DateAndTimePicker(this.getContext());
        startPicker.setOnDateAndTimePickerCallBack(new DateAndTimePicker.OnDateAndTimePickerCallBack() {
            @Override
            public void returnTime(String date, String time) {
                warn_start_time.setText(date + "     " +  time);
            }

            @Override
            public void returnTime(int year, int month, int day, int hour, int miniute) {

            }
        });

        endPicker = new DateAndTimePicker(this.getContext());
        endPicker.setOnDateAndTimePickerCallBack(new DateAndTimePicker.OnDateAndTimePickerCallBack() {
            @Override
            public void returnTime(String date, String time) {
                warn_end_time.setText(date + "     " +  time);
            }

            @Override
            public void returnTime(int year, int month, int day, int hour, int miniute) {

            }
        });

    }

    public String getFileName(){
        return warn_file_name.getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getActivity().getCurrentFocus();
            if (isShouldHideInput(v, event)) {
                if(hideInputMethod(getActivity(), v)) {
                    warn_file_name.setCursorVisible(false);
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
            warn_file_name.setCursorVisible(true);
        }
        return true;

    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.warn_start_time:
                startPicker.show();
                break;
            case R.id.warn_end_time:
                endPicker.show();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            hideInputMethod(v.getContext(),v);
        else
            warn_file_name.setCursorVisible(true);
    }
}
