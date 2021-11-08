package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MonitorXHarmonicValueFormatter extends ValueFormatter {

    private final DecimalFormat mFormat;

    public MonitorXHarmonicValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0");
    }
    
    @Override
    public String getFormattedValue(float value) {
 //       return mFormat.format(value) + suffix;
        int index = (int)value;

        if(value == 0){
            return "Volt";
        }else if(value == 1){
            return "Harmonic";
        }else{
            return "Freq";
        }
    }


}
