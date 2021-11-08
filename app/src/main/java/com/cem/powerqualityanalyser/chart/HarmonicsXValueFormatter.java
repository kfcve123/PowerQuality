package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class HarmonicsXValueFormatter extends ValueFormatter {

    private final DecimalFormat mFormat;

    public HarmonicsXValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0");
    }
    
    @Override
    public String getFormattedValue(float value) {
 //       return mFormat.format(value) + suffix;
        int index = (int)value;

//        if(value == 0){
//            return "THD";
//        }else
        if(value == 0){
            return "DC";
        }else{
            return mFormat.format(value);
        }
    }

   /* @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int index = (int)value;

        if(value == 0){
            return "THD";
        }else if(value == 1){
            return "DC";
        }else{
            return mFormat.format(value - 2);
        }


    }*/

     /*if (axis instanceof XAxis) {
            return mFormat.format(value);
        } else if (value > 0) {
            return mFormat.format(value) + suffix;
        } else {
            return mFormat.format(value);
        }*/
}
