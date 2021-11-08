package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.HarmonicsBarChart;
import com.cem.powerqualityanalyser.chart.MPChartBaseView;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class HarmoBaseView extends MPChartBaseView implements OnChartValueSelectedListener {

    //共用
    protected LinearLayout sticky_ll;
    protected TextView wir_tv,select_harmoinics_tv;
    protected LinearLayout harmoinics_3item_ll,harmoinics_2item_ll;
    //一栏的情况
    protected TextView harmoinics_title_1item;
    // 3 栏的情况
    protected LinearLayout harmoinics_title_ll_3item;
    protected TextView harmoinics_l1_title_3item,harmoinics_l2_title_3item,harmoinics_l3_title_3item;
    protected TextView harmoinics_l1_value,harmoinics_l2_value,harmoinics_l3_value;
    protected TextView harmoinics_l1_value1,harmoinics_l2_value1,harmoinics_l3_value1;
    protected TextView harmoinics_l1_value2,harmoinics_l2_value2,harmoinics_l3_value2;

    //2栏的情况
    protected TextView harmoinics_l1_title_2item,harmoinics_l2_title_2item;
    protected TextView harmoinics_l1_value_2item,harmoinics_l2_value_2item;
    protected TextView harmoinics_l1_value1_2item,harmoinics_l2_value1_2item;
    protected TextView harmoinics_l1_value2_2item,harmoinics_l2_value2_2item;
    protected HarmonicsBarChart harmonicsbarchart;

    public HarmoBaseView(Context context) {
        super(context);
    }

    public HarmoBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HarmoBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HarmoBaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void setLineCharAttribute() {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.harmonics_barchar_layout, this);

        harmonicsbarchart = (HarmonicsBarChart) view.findViewById(R.id.harmoncibarchart);
        harmonicsbarchart.setOnChartValueSelectedListener(this);
        sticky_ll = (LinearLayout) view.findViewById(R.id.sticky_ll);
        harmoinics_3item_ll = (LinearLayout) view.findViewById(R.id.harmoinics_3item_ll);
        harmoinics_2item_ll = (LinearLayout) view.findViewById(R.id.harmoinics_2item_ll);
        wir_tv = (TextView) findViewById(R.id.wir_tv);

        select_harmoinics_tv = (TextView) view.findViewById(R.id.select_harmoinics_tv);
        /* 三栏数据和一栏的情况*/
        /* 一栏数据的情况*/

        harmoinics_title_1item = (TextView) view.findViewById(R.id.harmoinics_title_1item);
        // 3 栏的情况 水平布局 L1,L2,L3   harmoinics_title_ll_3item 来控制显示1栏或是3栏
        harmoinics_title_ll_3item = (LinearLayout) view.findViewById(R.id.harmoinics_title_ll_3item);
        harmoinics_l1_title_3item = (TextView) view.findViewById(R.id.harmoinics_l1_title_3item);
        harmoinics_l2_title_3item = (TextView) view.findViewById(R.id.harmoinics_l2_title_3item);
        harmoinics_l3_title_3item = (TextView) view.findViewById(R.id.harmoinics_l3_title_3item);

        harmoinics_l1_value = (TextView) view.findViewById(R.id.harmoinics_l1_vaule);
        harmoinics_l2_value = (TextView) view.findViewById(R.id.harmoinics_l2_value);
        harmoinics_l3_value = (TextView) view.findViewById(R.id.harmoinics_l3_value);

        harmoinics_l1_value1 = (TextView) view.findViewById(R.id.harmoinics_l1_vaule1);
        harmoinics_l2_value1 = (TextView) view.findViewById(R.id.harmoinics_l2_value1);
        harmoinics_l3_value1 = (TextView) view.findViewById(R.id.harmoinics_l3_value1);

        harmoinics_l1_value2 = (TextView) view.findViewById(R.id.harmoinics_l1_vaule2);
        harmoinics_l2_value2 = (TextView) view.findViewById(R.id.harmoinics_l2_value2);
        harmoinics_l3_value2 = (TextView) view.findViewById(R.id.harmoinics_l3_value2);

        /* 二栏数据的情况*/
        //2栏的情况 水平布局 L1,L2

        harmoinics_l1_title_2item = (TextView) view.findViewById(R.id.harmoinics_l1_title_2item);
        harmoinics_l2_title_2item = (TextView) view.findViewById(R.id.harmoinics_l2_title_2item);

        harmoinics_l1_value_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value_2item);
        harmoinics_l2_value_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value_2item);

        harmoinics_l1_value1_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value1_2item);
        harmoinics_l2_value1_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value1_2item);

        harmoinics_l1_value2_2item = (TextView) view.findViewById(R.id.harmoinics_l1_value2_2item);
        harmoinics_l2_value2_2item = (TextView) view.findViewById(R.id.harmoinics_l2_value2_2item);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 放大缩小
     * @param xScale
     * @param yScale
     */
    public void zoomScale(float xScale,float yScale){
        if(harmonicsbarchart.isScaleXEnabled()){

        }
        //缩放第二种方式  
//        harmonicsbarchart.fitScreen();
//        harmonicsbarchart.getViewPortHandler().getMatrixTouch().postScale(xScale,yScale);
        //缩放第一种方式  
        Matrix matrix = new Matrix();
        harmonicsbarchart.fitScreen();
        matrix.postScale(xScale, yScale);
        harmonicsbarchart.getViewPortHandler().refresh(matrix, harmonicsbarchart, false);
        harmonicsbarchart.notifyDataSetChanged();
        harmonicsbarchart.invalidate();

    }

}
