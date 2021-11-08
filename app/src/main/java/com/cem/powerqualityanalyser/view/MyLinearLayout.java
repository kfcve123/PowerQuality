package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.graphics.PointF;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLinearLayout extends LinearLayout {
    private PointF userClick;
    public MyLinearLayout(Context context) {
        super(context);
        initView();
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        userClick =new PointF();
    }

    public PointF getUserClickPoint() {
        return userClick;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                userClick.x=event.getX();
                userClick.y=event.getY();
                break;

        }
        return super.onTouchEvent(event);
    }
}
