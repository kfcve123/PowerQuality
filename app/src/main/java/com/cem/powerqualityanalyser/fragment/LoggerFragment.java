package com.cem.powerqualityanalyser.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.activity.RecorderActivity;
import com.cem.powerqualityanalyser.activity.TrendChartRecordActivity;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;


public class LoggerFragment extends BaseFragment implements TrendChartRecordActivity.LoogerTouchEvent, AmountViewHorizontal.OnHorizonalAmountChangeListener, View.OnFocusChangeListener {
    private String[] intervalArray = {"1s","2s","5s","30s","1min","5min","10min","14min","30min","60min"};
    private String[] durationArray = {"3min","10min","30min","1hour","3hour","1day","2day","3day","4day","5day","6day","7day"};
    private AmountViewHorizontal AmountViewHorizontal_interval,AmountViewHorizontal_duration;
    private EditText edit_fileNmae;
    @Override
    public int setContentView() {
        return R.layout.fragment_logger;
    }

    @Override
    public void onInitView() {
        AmountViewHorizontal_interval = (AmountViewHorizontal) findViewById(R.id.interval);
        AmountViewHorizontal_duration = (AmountViewHorizontal) findViewById(R.id.duration);
        AmountViewHorizontal_duration.setOnHorizonalAmountChangeListener(this);
        AmountViewHorizontal_interval.setOnHorizonalAmountChangeListener(this);
        AmountViewHorizontal_interval.setDataArray(intervalArray);
        AmountViewHorizontal_duration.setDataArray(durationArray);
        AmountViewHorizontal_interval.setContent(intervalArray[0]);
        AmountViewHorizontal_duration.setContent(durationArray[0]);
        ((TrendChartRecordActivity) getActivity()).registerLoggerTouch(this);
        edit_fileNmae = (EditText) findViewById(R.id.edit_filename);
        edit_fileNmae.setOnFocusChangeListener(this);

        AmountViewHorizontal_interval.setAmountViewLayout(220);
        AmountViewHorizontal_duration.setAmountViewLayout(220);
        AmountViewHorizontal_interval.setShowArrow(true);
        AmountViewHorizontal_duration.setShowArrow(true);

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()){
            case R.id.interval:

                break;
            case R.id.duration:

                break;
        }
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

    public long getInterval(){
        String content = AmountViewHorizontal_interval.getContent();
        int multiple = 1;
        String num = "";
        if (!TextUtils.isEmpty(content)){
            if (content.endsWith("s")){
                multiple = 1000;
                num = content.replace("s","");
            }else if (content.endsWith("min")){
                multiple = 1000 * 60;
                num = content.replace("min","");
            }
            return Long.valueOf(num) * multiple;
        }
        return 0;
    }

    public long getDuration(){
        String content = AmountViewHorizontal_duration.getContent();
        int multiple = 1;
        String num = "";
        if (!TextUtils.isEmpty(content)){
            if (content.endsWith("s")){
                multiple = 1000;
                num = content.replace("s","");
            }else if (content.endsWith("min")){
                multiple = 1000 * 60;
                num = content.replace("min","");
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

    public String getFileName(){
        return edit_fileNmae.getText().toString();
    }

    public void clearName(){
        if(edit_fileNmae!=null)
            edit_fileNmae.setText("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getActivity().getCurrentFocus();
            if (isShouldHideInput(v, event)) {
                if(hideInputMethod(getActivity(), v)) {
                    edit_fileNmae.setCursorVisible(false);
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
            edit_fileNmae.setCursorVisible(true);
        }
        return true;

    }


    public void moveCursor(int i) {
        if (AmountViewHorizontal_duration.hasFocus()){
            if (i > 0)
                AmountViewHorizontal_duration.getAmount_dncrease_horizontal().performClick();
            else
                AmountViewHorizontal_duration.getAmount_increase_horizontal().performClick();

        }
        if (AmountViewHorizontal_interval.hasFocus()){
            if (i > 0)
                AmountViewHorizontal_interval.getAmount_dncrease_horizontal().performClick();
            else
                AmountViewHorizontal_interval.getAmount_increase_horizontal().performClick();
        }
    }

    /**
     * 失去焦点，隐藏软键盘
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            hideInputMethod(v.getContext(),v);
        else
            edit_fileNmae.setCursorVisible(true);
    }
}
