package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.cem.powerqualityanalyser.chart.ScopeBaseView;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

public class ScopeTrendVie extends ScopeTrendBaseView {

    public ScopeTrendVie(Context context) {
        super(context);
        initView(context);
    }

    public ScopeTrendVie(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScopeTrendVie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ScopeTrendVie(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                scopeLineChart.getLineData().clearValues();
            } else if (msg.what == 2) {
                scopeLineChart.clearValues();
            }
        }
    };


    public void setTextNValue(String frequency) {
        try {
            float v = Float.parseFloat(frequency);
            textview_l4.setText("    " + String.format("%." + 2 + "f", v) + "Hz");
        } catch (Exception e) {
            textview_l4.setText("    " + frequency + "Hz");
        }
    }

    public float valueToPercentage(float value, float maxValue, float minValue) {
        if (maxValue - minValue == 0)
            return 0;
        return (value * 2 / (maxValue - minValue)) - 1;
    }

    /**
     * 根据传来的数据设置最大值
     */
    private void setMaxAndMinValue(List<List<Float>> dataList, int wir_right_index) {
        maxLeftTrendView = 0f;
        minLeftTrendView = 0f;
        maxRightTrendView = 0f;
        minRightTrendView = 0f;
        maxValue = new float[dataList.size()];
        minValue = new float[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            List<Float> floats = dataList.get(i);
            float max = 0;
            float min = dataList.get(i).get(0);
            for (int j = 0; j < floats.size(); j++) {
                if (floats.get(j) > max)
                    max = floats.get(j);
                if (floats.get(j) < min)
                    min = floats.get(j);
            }
            maxValue[i] = max;
            minValue[i] = min;

            if (maxLeftTrendView < max) {
                maxLeftTrendView = max;
            }
            if (minLeftTrendView > min) {
                minLeftTrendView = min;
            }
        }

        if (dataList.size() == 2) {
            List<Float> floats = dataList.get(1);
            float max = 0;
            float min = dataList.get(1).get(0);
            for (int j = 0; j < floats.size(); j++) {
                if (floats.get(j) > max)
                    max = floats.get(j);
                if (floats.get(j) < min)
                    min = floats.get(j);
            }

            if (maxRightTrendView < max) {
                maxRightTrendView = max;
            }
            if (minRightTrendView > min) {
                minRightTrendView = min;
            }
        }


    }

    public void setLineData(List<List<Float>> dataList, boolean newData, int wir_right_index) {
        LineData lineData = scopeLineChart.getLineData();
        if (newData && lineData != null) {
            lineData.clearValues();
//            mChart.highlightValues(null);
//            iLastEntry = 0;
        }
        if (lineData == null) {
            lineData = new LineData();
            lineData.setValueTypeface(tfRegular);
            scopeLineChart.setData(lineData);
        }

        setMaxAndMinValue(dataList, wir_right_index);
        if (maxLeftTrendView <= 0.5f)
            scopeLineChart.getAxisLeft().setAxisMaximum(0.5f);
        else if (maxLeftTrendView > 0.5f && maxLeftTrendView <= 5f)
            scopeLineChart.getAxisLeft().setAxisMaximum(5 * 2);
        else if (maxLeftTrendView > 5f && maxLeftTrendView <= 400f)
            scopeLineChart.getAxisLeft().setAxisMaximum(maxLeftTrendView * 1.2f);
        else if (maxLeftTrendView > 400f && maxLeftTrendView <= 600f)
            scopeLineChart.getAxisLeft().setAxisMaximum(maxLeftTrendView * 1.5f);
        else
            scopeLineChart.getAxisLeft().setAxisMaximum(maxLeftTrendView * 2);
        if (minLeftTrendView >= -0.5f) {
            scopeLineChart.getAxisLeft().setAxisMinimum(-0.5f);
        } else if (minLeftTrendView >= -5f && minLeftTrendView < -0.5f)
            scopeLineChart.getAxisLeft().setAxisMinimum(-5 * 2);
        else if (minLeftTrendView >= -400f && minLeftTrendView < -5f)
            scopeLineChart.getAxisLeft().setAxisMinimum(minLeftTrendView * 1.2f);
        else if (minLeftTrendView >= -600f && minLeftTrendView < -400f)
            scopeLineChart.getAxisLeft().setAxisMinimum(minLeftTrendView * 1.5f);
        else
            scopeLineChart.getAxisLeft().setAxisMinimum(minLeftTrendView * 2);
        if (wir_right_index > 2) {
            scopeLineChart.getAxisRight().setEnabled(true);
            scopeLineChart.getAxisRight().setAxisMinimum(minRightTrendView * 2);
            scopeLineChart.getAxisRight().setAxisMaximum(maxRightTrendView * 2);
        } else {
            scopeLineChart.getAxisRight().setEnabled(false);
        }

        //       log.e("-------maxRightTrendView-----" + maxRightTrendView + "-------minRightTrendView----==" + minRightTrendView);

        if (dataList != null) {
            if (lineData != null) {
                for (int i = 0; i < dataList.size(); i++) {
                    ILineDataSet set = lineData.getDataSetByIndex(i);

                    if (set == null) {
                        set = createSet(i);
                        lineData.addDataSet(set);
                    }
                    if (dataList.size() == 2) {
                        if (lineData.getDataSetByIndex(1) != null)
                            lineData.getDataSetByIndex(1).setAxisDependency(YAxis.AxisDependency.RIGHT);
                    } else {
                        set.setAxisDependency(YAxis.AxisDependency.LEFT);
                    }

                    /*if(newData)
                        set.clear();*/

                    if (dataList.get(i).size() > 0) {
                        for (int j = 0; j < dataList.get(i).size(); j++) {
                            //                           lineData.addEntry(new Entry(set.getEntryCount(), valueToPercentage(dataList.get(i).get(j),maxValue[i],minValue[i])), i);
                            lineData.addEntry(new Entry(set.getEntryCount(), dataList.get(i).get(j)), i);
                        }
                    } else {
                        log.e("=======dataList.get(i).size()=====" + dataList.get(i).size());
                    }
                }
                lineData.notifyDataChanged();
                // let the chart know it's data has changed
                scopeLineChart.notifyDataSetChanged();

                // limit the number of visible entries
                //mChart.setVisibleXRangeMaximum(30);
                // mChart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                scopeLineChart.moveViewToX(lineData.getEntryCount());
            }
        }
    }


    /**
     * 恢复原始
     * 重设所有缩放和拖动，使图表完全适合它的边界（完全缩小）。  
     */
    public void fitScreen() {
        scopeLineChart.fitScreen();
        //    mChart.setVisibleXRangeMaximum(maxXRange);
        scopeLineChart.getViewPortHandler().getMatrixTouch().postScale(0f, 0f);
    }

    /**
     * 放大缩小
     *
     * @param xScale
     * @param yScale
     */
    public void zoomScale(float xScale, float yScale) {
        if (!scopeLineChart.isScaleXEnabled()) {
            //         scopeLineChart.setScaleYEnabled(true);
        }
        //缩放第二种方式  
       /* scopeLineChart.fitScreen();
        scopeLineChart.getViewPortHandler().getMatrixTouch().postScale(xScale, yScale);*/
        //缩放第一种方式  
        Matrix matrix = new Matrix();
        scopeLineChart.fitScreen();
        matrix.postScale(xScale, yScale);
        scopeLineChart.getViewPortHandler().refresh(matrix, scopeLineChart, false);
    }

    public void setScopeModeIndex(int positio) {
        switch (positio) {
            case 0:
                setScopeMode(ScopeType.VOLT4V);
                break;
            case 1:
                setScopeMode(ScopeType.VOLT3U);
                break;
            case 2:
                setScopeMode(ScopeType.AMP);
                break;
            case 3:
                setScopeMode(ScopeType.L1);
                break;
            case 4:
                setScopeMode(ScopeType.L2);
                break;
            case 5:
                setScopeMode(ScopeType.L3);
                break;
            case 6:
                setScopeMode(ScopeType.N);
                break;
        }
    }


    private void setScopeMode(ScopeType scopeType) {
        this.scopeType = scopeType;
        if (lastMode != scopeType) {
            lastMode = scopeType;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
        switch (scopeType) {
            case VOLT4V:
            case AMP:
                showL1L2L3L4(true, true, true, true);
                break;
            case VOLT3U:
                showL1L2L3L4(true, true, true, false);
                break;
            case L1:
            case L2:
            case L3:
                showL1L2L3L4(true, true, false, false);
                break;
        }

    }

    public void setLastEntry(int iLastEntry) {
        this.iLastEntry = iLastEntry;
    }

}
