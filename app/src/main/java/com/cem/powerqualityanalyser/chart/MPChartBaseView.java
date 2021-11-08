package com.cem.powerqualityanalyser.chart;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;

import java.util.ArrayList;

import serialport.amos.cem.com.libamosserial.ModelLineData;

public abstract class MPChartBaseView extends RelativeLayout{
    protected Typeface tfRegular,tfLight;
    protected float axisMinimum = 0f;
    protected float axisMaximum = 200f;
    protected float maxXRange = 20f;
    protected int lableCount = 5;
    protected String noDataShow;
    protected LayoutInflater inflater;


    private void initAttributeset(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tfRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
        tfLight= Typeface.createFromAsset(context.getAssets(),"OpenSans-Light.ttf");

        axisMinimum = 0f;
        axisMaximum = 200f;
        maxXRange = 20f;
        lableCount = 5;
        noDataShow = "没有数据哦";
        setLineCharAttribute();
    }

    public int getLableCount() {
        return lableCount;
    }

    public void setLableCount(int lableCount) {
        this.lableCount = lableCount;
    }

    public float getAxisMinimum() {
        return axisMinimum;
    }

    public void setAxisMinimum(float axisMinimum) {
        this.axisMinimum = axisMinimum;
    }

    public float getAxisMaximum() {
        return axisMaximum;
    }

    public void setAxisMaximum(float axisMaximum) {
        this.axisMaximum = axisMaximum;
    }


    public MPChartBaseView(Context context) {
        super(context);
        initAttributeset(context);
    }

    public MPChartBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeset(context);
    }

    public MPChartBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeset(context);
    }

    public MPChartBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributeset(context);
    }

    protected  abstract void setLineCharAttribute();

    /**
     * 拿到数据对象转成Float数值处理
     * @param meterAllObj
     * @return
     */
    public ArrayList<Float> ojbToFloatValues(ModelLineData meterAllObj){
        ArrayList<Float> values = new ArrayList<>();
        try {
            if(meterAllObj.getaValue()!=null && isNumOrOl(meterAllObj.getaValue().getValue()))
                values.add(Float.valueOf(meterAllObj.getaValue().getValue()));
            if(meterAllObj.getbValue()!=null && isNumOrOl(meterAllObj.getbValue().getValue()))
                values.add(Float.valueOf(meterAllObj.getbValue().getValue()));
            if(meterAllObj.getcValue()!=null && isNumOrOl(meterAllObj.getcValue().getValue()))
                values.add(Float.valueOf(meterAllObj.getcValue().getValue()));
            if(meterAllObj.getnValue()!=null && isNumOrOl(meterAllObj.getnValue().getValue()))
                values.add(Float.valueOf(meterAllObj.getnValue().getValue()));
        }catch (NumberFormatException e){

        }
        return values;
    }

    private boolean isNumOrOl(String value){
        if(value!=null) {
            if (value.equals("") || value.equals(getResources().getString(R.string.ol_value))) {
                return false;
            }
            return true;
        }
        return false;
    }


}
