package com.cem.powerqualityanalyser.chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MonitorXDipSweelsValueFormatter extends ValueFormatter {

    private final DecimalFormat mFormat;

    private String[] labels;
    private BarLineChartBase<?> chart;

    public MonitorXDipSweelsValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0");
    }

    public MonitorXDipSweelsValueFormatter(BarLineChartBase<?> chart, String[] labels) {
        mFormat = new DecimalFormat("###,###,###,##0");
        this.chart = chart;
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value) {
 //       return mFormat.format(value) + suffix;
        int index = (int)value;
//        log.e("=========" + value);
       if(value == 0){
            return "Volt";
        }else if(value == 1){
            return "Dip&Sweels";
        }else{
            return "Freq";
        }

  //      return labels[(int)value];

    }

}
