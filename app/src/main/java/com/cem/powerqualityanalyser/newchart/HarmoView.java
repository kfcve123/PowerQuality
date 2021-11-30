package com.cem.powerqualityanalyser.newchart;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import android.util.Log;
import android.view.View;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.BaseBarChart;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import serialport.amos.cem.com.libamosserial.ModelLineData;

public class HarmoView extends HarmoBaseView {

    private int maxVisibleValueCount = 25;
    private Legend.LegendForm legendForm = Legend.LegendForm.NONE;
    private boolean drawValuesEnabled;
    private int barselect = 0;


    protected void setDrawValuesEnabled(boolean enabled) {
        this.drawValuesEnabled = enabled;
    }

    protected void setLegendForm(Legend.LegendForm form) {
        this.legendForm = form;
    }

    public HarmoView(Context context) {
        super(context);
    }

    public HarmoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HarmoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HarmoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int barMode = 3;
    private String rightItem;

    public void setHarmonicsBarMode(int mode, String rightItem) {
        barMode = mode;
        select_harmoinics_tv.setText(funTypeIndexString + "-h1");
        this.rightItem = rightItem;
        if (mode == 2) {
            sticky_ll.setBackgroundResource(R.mipmap.harmonics_graph_bg2);
            harmoinics_3item_ll.setVisibility(View.GONE);
            harmoinics_2item_ll.setVisibility(View.VISIBLE);
            harmoinics_title_1item.setVisibility(View.GONE);
            harmoinics_l1_title_2item.setText("L1");
            harmoinics_l2_title_2item.setText("L2");
            setLineDataSetVisable(true, true, false, false);
        } else if (mode == 3) {
            sticky_ll.setBackgroundResource(R.mipmap.harmonics_graph_bg1);
            harmoinics_3item_ll.setVisibility(View.VISIBLE);
            harmoinics_title_ll_3item.setVisibility(VISIBLE);
            harmoinics_2item_ll.setVisibility(View.GONE);
            harmoinics_title_1item.setVisibility(View.GONE);
            harmoinics_l1_title_3item.setText("L1");
            harmoinics_l2_title_3item.setText("L2");
            harmoinics_l3_title_3item.setText("L3");
            setLineDataSetVisable(true, true, true, false);
        } else {
            sticky_ll.setBackgroundResource(R.mipmap.harmonics_graph_bg3);
            harmoinics_3item_ll.setVisibility(View.VISIBLE);
            harmoinics_title_1item.setVisibility(View.VISIBLE);
            harmoinics_title_ll_3item.setVisibility(View.GONE);
            harmoinics_2item_ll.setVisibility(View.GONE);
            harmoinics_title_1item.setText(rightItem);
            if (rightItem.equals("L1") || rightItem.equals("L1L2")) {
                setLineDataSetVisable(true, false, false, false);
            } else if (rightItem.equals("L2") || rightItem.equals("L2L3")) {
                setLineDataSetVisable(false, true, false, false);
            } else if (rightItem.equals("L3") || rightItem.equals("L3L1")) {
                setLineDataSetVisable(false, false, true, false);
            } else if (rightItem.equals("N")) {
                setLineDataSetVisable(false, false, false, true);
            } else {
                setLineDataSetVisable(true, false, false, false);
            }
        }
        harmonicsbarchart.invalidate();
    }

    private int wir_index;

    public void setWir(String wir, int wir_index) {
        this.wir_index = wir_index;
        wir_tv.setText(wir);
    }

    private String funTypeIndexString = "V";

    public void setHarmonicsType(String funTypeIndex) {
        this.funTypeIndexString = funTypeIndex;
        select_harmoinics_tv.setText(funTypeIndexString + "-h01");
    }

    public void refeshHeadColor(int l1, int l2, int l3, int n, String rightItem) {
        TypedArray ar = getResources().obtainTypedArray(R.array.harmonics_item_arrays);
        final int len = ar.length();
        int[] dataColorArray = new int[len];
        for (int i = 0; i < len; i++) {
            dataColorArray[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        harmoinics_l1_title_3item.setBackgroundResource(dataColorArray[l1 - 1]);
        harmoinics_l2_title_3item.setBackgroundResource(dataColorArray[l2 - 1]);
        harmoinics_l3_title_3item.setBackgroundResource(dataColorArray[l3 - 1]);

        harmoinics_l1_title_2item.setBackgroundResource(dataColorArray[l1 - 1]);
        harmoinics_l2_title_2item.setBackgroundResource(dataColorArray[l2 - 1]);
        if (rightItem.equals("L1") || rightItem.equals("L1L2")) {
            harmoinics_title_1item.setBackgroundResource(dataColorArray[l1 - 1]);
        } else if (rightItem.equals("L2") || rightItem.equals("L2L3")) {
            harmoinics_title_1item.setBackgroundResource(dataColorArray[l2 - 1]);
        } else if (rightItem.equals("L3") || rightItem.equals("L3L1")) {
            harmoinics_title_1item.setBackgroundResource(dataColorArray[l3 - 1]);
        } else if (rightItem.equals("N")) {
            harmoinics_title_1item.setBackgroundResource(dataColorArray[n - 1]);
        } else {
            harmoinics_title_1item.setBackgroundResource(dataColorArray[l1 - 1]);
        }
    }

    @Override
    protected void setLineCharAttribute() {
        super.setLineCharAttribute();
        harmonicsbarchart.setMaxVisibleValueCount(maxVisibleValueCount);
//        setLegendForm(Legend.LegendForm.NONE);
        harmonicsbarchart.getAxisRight().setEnabled(false);
        harmonicsbarchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        harmonicsbarchart.getXAxis().setAxisMinimum(0f);
        harmonicsbarchart.getXAxis().setGranularity(1f);
//        harmonicsbarchart.getLegend().setEnabled(false);
        harmonicsbarchart.getDescription().setEnabled(false);
        harmonicsbarchart.setDrawGridBackground(false);
        harmonicsbarchart.setDrawBorders(true);
        XAxis xAxis = harmonicsbarchart.getXAxis();
        xAxis.setDrawGridLines(false);
        //       xAxis.setAxisMaximum(50);
        xAxis.setDrawAxisLine(false);
        xAxis.setAxisMinimum(0);
        //       xAxis.setLabelCount(50);

        YAxis leftAxis = harmonicsbarchart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        harmonicsbarchart.getAxisRight().setAxisMinimum(0f);
        harmonicsbarchart.getAxisRight().setAxisMaximum(100f);
        harmonicsbarchart.getAxisLeft().setAxisMinimum(0f); // start at zero
        harmonicsbarchart.getAxisLeft().setAxisMaximum(100f); // the axis maximum is 100
        setBarChart(harmonicsbarchart);
    }

    private boolean visablea, visableb, visablec, visablen;

    private void setLineDataSetVisable(boolean visablea, boolean visableb, boolean visablec, boolean visablen) {
        this.visablea = visablea;
        this.visableb = visableb;
        this.visablec = visablec;
        this.visablen = visablen;
        if (harmonicsbarchart.getData() != null) {
            if (harmonicsbarchart.getData().getDataSetCount() > 0)
                harmonicsbarchart.getBarData().getDataSets().get(0).setVisible(visablea);
            if (harmonicsbarchart.getData().getDataSetCount() > 1)
                harmonicsbarchart.getBarData().getDataSets().get(1).setVisible(visableb);
            if (harmonicsbarchart.getData().getDataSetCount() > 2)
                harmonicsbarchart.getBarData().getDataSets().get(2).setVisible(visablec);
            if (harmonicsbarchart.getData().getDataSetCount() > 3)
                harmonicsbarchart.getBarData().getDataSets().get(3).setVisible(visablen);
        }
        harmonicsbarchart.invalidate();

    }

    private void setBarChart(BarChart chart) {
        chart.getDescription().setEnabled(false);

//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);

        chart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
    }



    /*public void setShowMeterData2(List<ModelLineData> chooseUserFullModeList, int wir_right_index,int singleBarSize) {
        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

        int groupCount = 2000 + 1;
        int startYear = 1980;
        int endYear = startYear + groupCount;


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<BarEntry> values4 = new ArrayList<>();

        float randomMultiplier = 20;

        for (int i = startYear; i < endYear; i++) {
            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
        }

        BarDataSet set1, set2, set3, set4;

        if (harmonicsbarchart.getData() != null && harmonicsbarchart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));

            BarData data = new BarData(set1, set2, set3, set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            harmonicsbarchart.setData(data);
        }

        // specify the width each bar should have
        harmonicsbarchart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        harmonicsbarchart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        harmonicsbarchart.getXAxis().setAxisMaximum(startYear + harmonicsbarchart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        harmonicsbarchart.groupBars(startYear, groupSpace, barSpace);
        harmonicsbarchart.invalidate();
    }*/


    private int selectEntry;
    private int selectSet;

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        selectEntry = (int) e.getX();
        selectSet = h.getDataSetIndex();
        Log.e("moveCursor", selectEntry + "--" + iLastEntry + "===" + "前" + "===" + selectSet);
        if (iLastEntry != selectEntry) {
            iLastEntry = selectEntry;
        }
            /*if (iLastSet != selectSet) {
                iLastSet = selectSet;
            }
        } else {
            if (iLastSet != selectSet) {
                iLastSet = selectSet;
            }
        }*/
        Log.e("moveCursor", selectEntry + "--" + iLastEntry + "===" + "后" + "===" + selectSet);
        harmonicsbarchart.centerViewToAnimated(e.getX(), e.getY(), harmonicsbarchart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        setTopTitle(harmonicsbarchart.getData().getDataSetByIndex(0).getEntryForIndex((int) e.getX()).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(1).getEntryForIndex((int) e.getX()).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(2).getEntryForIndex((int) e.getX()).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(3).getEntryForIndex((int) e.getX()).getY() + "");
    }

    @Override
    public void onNothingSelected() {
        /*harmonicsbarchart.highlightValues(null);
        iLastEntry = 0;
        cursorEnable = false;*/
    }

    public void moveCursor(int i, int rightIndex) {
        if (harmonicsbarchart != null) {
            if (showCurson) {
                if (iLastEntry + i > 0 && iLastEntry + i < harmonicsbarchart.getBarData().getEntryCount() - 1) {
                    harmonicsbarchart.setHighlightFullBarEnabled(true);
                    if (rightIndex != 0) {
                        iLastSet = rightIndex - 1;
                        harmonicsbarchart.highlightValue((float) (iLastEntry + i), iLastSet);
                        if (iLastSet == 2 || iLastSet == 3) {
                            harmonicsbarchart.highlightValue((float) (iLastEntry + 2), iLastSet);
                        }
                    } else {
                        if (i == 1) {
                            if (iLastSet == 0) {
                                iLastSet = 1;
                                iLastEntry = iLastEntry - 1;
                            } else if (iLastSet == 1) {
                                iLastSet = 2;
                            } else if (iLastSet == 2) {
                                iLastSet = 3;
                            } else if (iLastSet == 3) {
                                iLastSet = 0;
                                iLastEntry = iLastEntry;
                            }
                            harmonicsbarchart.highlightValue((float) iLastEntry + i, iLastSet);
                        } else if (i == -1) {
                            if (iLastSet == 0) {
                                iLastSet = 3;
                                iLastEntry = iLastEntry;
                            } else if (iLastSet == 1) {
                                iLastSet = 0;
                            } else if (iLastSet == 2) {
                                iLastSet = 1;
                            } else if (iLastSet == 3) {
                                iLastSet = 2;
                                iLastEntry = iLastEntry + 1;
                            }
                            harmonicsbarchart.highlightValue((float) iLastEntry, iLastSet);
                        }

                        /*iLastSet = 0;
                        harmonicsbarchart.highlightValue((float) (iLastEntry + i), iLastSet);*/
                    }
                    Log.e("moveCursor", iLastEntry + "===" + "===" + iLastSet);
                }
            }
        }
    }


    protected boolean showCurson = false;

    public void showCursor(boolean enable) {
        this.showCurson = enable;
        if (enable) {
            for (int i = 0; i < charts.size(); i++) {
                charts.get(i).highlightValue(iLastEntry, iLastSet);
                charts.get(i).getData().setHighlightEnabled(true);
            }
        } else {
            for (int i = 0; i < charts.size(); i++) {
                charts.get(i).highlightValue(null);
                charts.get(i).getData().setHighlightEnabled(false);
            }
        }

    }


    private void setTopTitle(final String l1, final String l2, final String l3,
                             final String ln) {
        select_harmoinics_tv.post(new Runnable() {
            @Override
            public void run() {
                if (selectEntry < 9) {
                    select_harmoinics_tv.setText(funTypeIndexString + "-h0" + (selectEntry + 1));
                } else {
                    select_harmoinics_tv.setText(funTypeIndexString + "-h" + (selectEntry + 1));
                }
                if (barMode == 2) {
                    harmoinics_l1_value_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                    harmoinics_l1_value1_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value1_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                    harmoinics_l1_value2_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value2_2item.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                } else {
                    harmoinics_l1_value.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                    harmoinics_l3_value.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l3), 2));

                    harmoinics_l1_value1.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value1.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                    harmoinics_l3_value1.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l3), 2));

                    harmoinics_l1_value2.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l1), 2));
                    harmoinics_l2_value2.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l2), 2));
                    harmoinics_l3_value2.setText(DataFormatUtil.frontCompWithZore(Float.valueOf(l3), 2));
                }
            }
        });
    }


    /**
     * @param modelLineData          全部对象
     * @param chooseUserFullModeList 全部显示Bar的对象
     * @param wir_index              接线方式
     * @param funTypeIndex           V -A - S
     * @param wir_right_index        - 3L,L1,L2,L3,N
     * @param barIndex               - 第几个Bar
     */
    private void setHarmoninicsGridValue(List<ModelLineData> modelLineData, ModelLineData
            selectMode, int wir_index, int funTypeIndex, int wir_right_index) {
        switch (wir_index) {
            case 0://3ØWYE
                switch (funTypeIndex) {
                    case 0://V
                        switch (wir_right_index) {
                            case 0:
                                harmoinics_l1_value.setText(selectMode.getaValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(selectMode.getbValue().getValueFl() + " %f");
                                harmoinics_l3_value.setText(selectMode.getcValue().getValueFl() + " %f");

                                harmoinics_l1_value1.setText(modelLineData.get(0).getaValue().getValueFl() + " V");
                                harmoinics_l2_value1.setText(modelLineData.get(0).getbValue().getValueFl() + " V");
                                harmoinics_l3_value1.setText(modelLineData.get(0).getcValue().getValueFl() + " V");

                                harmoinics_l1_value2.setText(modelLineData.get(2).getaValue().getValueFl() + "");
                                harmoinics_l2_value2.setText(modelLineData.get(2).getbValue().getValueFl() + "");
                                harmoinics_l3_value2.setText(modelLineData.get(2).getcValue().getValueFl() + "");

                                break;
                            case 1:
                                harmoinics_l1_value.setText(selectMode.getaValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getaValue().getValueFl() + " V");
                                harmoinics_l3_value.setText(modelLineData.get(2).getaValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(5).getaValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getaValue().getValueFl() + " %f");
                                harmoinics_l3_value1.setText("");

                                harmoinics_l1_value2.setText("min " + modelLineData.get(6).getaValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Vd  " + modelLineData.get(7).getaValue().getValueFl() + " V");
                                harmoinics_l3_value2.setText("");
                                break;
                            case 2:
                                harmoinics_l1_value.setText(selectMode.getbValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getbValue().getValueFl() + " V");
                                harmoinics_l3_value.setText(modelLineData.get(2).getbValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(5).getbValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getbValue().getValueFl() + " %f");
                                ;
                                harmoinics_l3_value1.setText("");

                                harmoinics_l1_value2.setText("min " + modelLineData.get(6).getbValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Vd  " + modelLineData.get(7).getbValue().getValueFl() + " V");
                                harmoinics_l3_value2.setText("");

                                break;
                            case 3:
                                harmoinics_l1_value.setText(selectMode.getcValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getcValue().getValueFl() + " V");
                                harmoinics_l3_value.setText(modelLineData.get(2).getcValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(5).getcValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getcValue().getValueFl() + " %f");
                                harmoinics_l3_value1.setText("");

                                harmoinics_l1_value2.setText("min " + modelLineData.get(6).getcValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Vd  " + modelLineData.get(7).getcValue().getValueFl() + " V");
                                harmoinics_l3_value2.setText("");

                                break;
                            case 4:
                                harmoinics_l1_value.setText(selectMode.getcValue().getValueFl() + " %r");
                                harmoinics_l2_value.setText(modelLineData.get(0).getcValue().getValueFl() + " V");
                                harmoinics_l3_value.setText("");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(5).getcValue().getValueFl() + " %r");
                                harmoinics_l2_value1.setText(modelLineData.get(3).getcValue().getValueFl() + "V");
                                harmoinics_l3_value1.setText("");

                                harmoinics_l1_value2.setText("min " + modelLineData.get(6).getcValue().getValueFl() + " %r");
                                harmoinics_l2_value2.setText("");
                                harmoinics_l3_value2.setText("");

                                break;

                        }
                        break;
                    case 2://A
                        switch (wir_right_index) {
                            case 0:
                                harmoinics_l1_value.setText(selectMode.getaValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(selectMode.getbValue().getValueFl() + " %f");
                                harmoinics_l3_value.setText(selectMode.getcValue().getValueFl() + " %f");

                                harmoinics_l1_value1.setText(modelLineData.get(0).getaValue().getValueFl() + " A");
                                harmoinics_l2_value1.setText(modelLineData.get(0).getbValue().getValueFl() + " A");
                                harmoinics_l3_value1.setText(modelLineData.get(0).getcValue().getValueFl() + " A");

                                harmoinics_l1_value2.setText(modelLineData.get(2).getaValue().getValueFl() + "");
                                harmoinics_l2_value2.setText(modelLineData.get(2).getbValue().getValueFl() + "");
                                harmoinics_l3_value2.setText(modelLineData.get(2).getcValue().getValueFl() + "");

                                break;
                            case 1:
                                harmoinics_l1_value.setText(selectMode.getaValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getaValue().getValueFl() + " A");
                                harmoinics_l3_value.setText(modelLineData.get(2).getaValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(6).getaValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getaValue().getValueFl() + " %f");
                                harmoinics_l3_value1.setText("K " + modelLineData.get(5).getaValue().getValueFl());

                                harmoinics_l1_value2.setText("min " + modelLineData.get(7).getaValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Ad  " + modelLineData.get(8).getaValue().getValueFl());
                                harmoinics_l3_value2.setText("");

                                break;

                            case 2:
                                harmoinics_l1_value.setText(selectMode.getbValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getbValue().getValueFl() + " A");
                                harmoinics_l3_value.setText(modelLineData.get(2).getbValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(6).getbValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getbValue().getValueFl() + " %f");
                                harmoinics_l3_value1.setText("K " + modelLineData.get(5).getbValue().getValueFl());

                                harmoinics_l1_value2.setText("min " + modelLineData.get(7).getbValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Ad  " + modelLineData.get(8).getbValue().getValueFl());
                                harmoinics_l3_value2.setText("");

                                break;

                            case 3:
                                harmoinics_l1_value.setText(selectMode.getcValue().getValueFl() + " %f");
                                harmoinics_l2_value.setText(modelLineData.get(0).getcValue().getValueFl() + " A");
                                harmoinics_l3_value.setText(modelLineData.get(2).getcValue().getValueFl() + "");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(6).getcValue().getValueFl() + " %f");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getcValue().getValueFl() + " %f");
                                harmoinics_l3_value1.setText("K " + modelLineData.get(5).getcValue().getValueFl());

                                harmoinics_l1_value2.setText("min " + modelLineData.get(7).getcValue().getValueFl() + " %f");
                                harmoinics_l2_value2.setText("Ad  " + modelLineData.get(8).getcValue().getValueFl());
                                harmoinics_l3_value2.setText("");

                                break;

                            case 4:
                                harmoinics_l1_value.setText(selectMode.getcValue().getValueFl() + " %r");
                                harmoinics_l2_value.setText(modelLineData.get(0).getcValue().getValueFl() + " A");
                                harmoinics_l3_value.setText("");

                                harmoinics_l1_value1.setText("max " + modelLineData.get(6).getcValue().getValueFl() + " %r");
                                harmoinics_l2_value1.setText("THD " + modelLineData.get(3).getcValue().getValueFl() + " %r");
                                harmoinics_l3_value1.setText("K " + modelLineData.get(5).getcValue().getValueFl());

                                harmoinics_l1_value2.setText("min " + modelLineData.get(7).getcValue().getValueFl() + " %r");
                                harmoinics_l2_value2.setText("");
                                harmoinics_l3_value2.setText("");

                                break;
                        }
                        break;
                    case 1://S
                        switch (wir_right_index) {
                            case 0:


                                break;
                        }

                        break;
                }


                break;
            case 5://3ØHIGH LEG
            case 6://2½-ELEMENT
            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 3://2-ELEMENT
            case 4://3ØDELTA
            case 9://1Ø +NEUTRAL
            case 7://1Ø SPLIT PHASE
            case 8://1Ø IT NO NEUTRAL

                break;

        }
    }


    private void setTextViewValue(ModelLineData modelLineData, int wir_index, int singleBar) {
        harmoinics_l1_value.setText(modelLineData.getaValue().getValue());
        harmoinics_l2_value.setText(modelLineData.getbValue().getValue());
        harmoinics_l3_value.setText(modelLineData.getcValue().getValue());
    }


    public void setShowMeterData3(List<ModelLineData> modelLineData, int wir_index,
                                  int funTypeIndex, int wir_right_index) {
        List<ModelLineData> chooseUserFullModeList = chooseUserFullModeList(modelLineData, wir_index, funTypeIndex, wir_right_index);

        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
        values1.clear();
        values2.clear();
        values3.clear();
        values4.clear();
        int groupCount = 50;
        int startYear = 0;
        int endYear = startYear + groupCount;

        float randomMultiplier = 60;
        if (chooseUserFullModeList.size() >= groupCount) {
            for (int i = startYear; i < endYear; i++) {
                values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));

                /*if (DataFormatUtil.isNumber(chooseUserFullModeList.get(i).getaValue().getValue()))
                    values1.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getaValue().getValue())));
                if (DataFormatUtil.isNumber(chooseUserFullModeList.get(i).getbValue().getValue()))
                    values2.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getbValue().getValue())));
                if (DataFormatUtil.isNumber(chooseUserFullModeList.get(i).getcValue().getValue()))
                    values3.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getcValue().getValue())));
                if (DataFormatUtil.isNumber(chooseUserFullModeList.get(i).getnValue().getValue()))
                    values4.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getnValue().getValue())));*/

            }
        }

        BarDataSet set1, set2, set3, set4;

        if (harmonicsbarchart.getData() != null && harmonicsbarchart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(3);
            if (set1 != null)
                set1.setValues(values1);
            if (set2 != null)
                set2.setValues(values2);
            if (set3 != null)
                set3.setValues(values3);
            if (set4 != null)
                set4.setValues(values4);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "");
            set1.setColor(ColorList.ALL_METER_TITLE_COLOR[1]);
            set2 = new BarDataSet(values2, "");
            set2.setColor(ColorList.ALL_METER_TITLE_COLOR[2]);
            set3 = new BarDataSet(values3, "");
            set3.setColor(ColorList.ALL_METER_TITLE_COLOR[3]);
            set4 = new BarDataSet(values4, "");
            set4.setColor(ColorList.ALL_METER_TITLE_COLOR[4]);

            BarData data = new BarData();
            data.addDataSet(set1);
            data.addDataSet(set2);
            data.addDataSet(set3);
            data.addDataSet(set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);

            harmonicsbarchart.setData(data);
        }

        // specify the width each bar should have
        harmonicsbarchart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        harmonicsbarchart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        harmonicsbarchart.getXAxis().setAxisMaximum(startYear + harmonicsbarchart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        if (harmonicsbarchart.getData().getDataSetCount() > 1)
            harmonicsbarchart.groupBars(startYear, groupSpace, barSpace);
        if (chooseUserFullModeList.size() >= groupCount)
            setHarmoninicsGridValue(modelLineData, chooseUserFullModeList.get(1), wir_index, funTypeIndex, wir_right_index);
//            setTopTitle(harmonicsbarchart.getData().getDataSetByIndex(0).getEntryForIndex(selectEntry).getY()+"",harmonicsbarchart.getData().getDataSetByIndex(1).getEntryForIndex(selectEntry).getY()+"",harmonicsbarchart.getData().getDataSetByIndex(2).getEntryForIndex(selectEntry).getY()+"",harmonicsbarchart.getData().getDataSetByIndex(3).getEntryForIndex(selectEntry).getY()+"");
        harmonicsbarchart.invalidate();

        //默认是25个格子，然后还有如果获取焦点左上角的滑动的图标，可以通过左右按键滚动
//        zoomScale(2, 1);
    }

    /**
     * @param chooseUserFullModeList
     * @param wir_right_index  右侧菜单当前选中的第几栏
     * @param singleBarSize  右侧菜单有几个 ，3L ,L1,L2,L3 or N
     */
    private boolean setshowL1, setshowL2, setshowL3;
    private ArrayList<BarEntry> values1 = new ArrayList<>();
    private ArrayList<BarEntry> values2 = new ArrayList<>();
    private ArrayList<BarEntry> values3 = new ArrayList<>();
    private ArrayList<BarEntry> values4 = new ArrayList<>();

    public void setShowMeterData(List<ModelLineData> chooseUserFullModeList,
                                 int wir_right_index, int singleBarSize) {
        values1.clear();
        values2.clear();
        values3.clear();
        values4.clear();
        /*if(singleBarSize == 3){
            setshowL1 = true;
            setshowL2 = true;
            setshowL3 = true;
        }else if(singleBarSize == 2){
            setshowL1 = true;
            setshowL2 = true;
            setshowL3 = false;
        }*/

        float groupSpace = 0.2f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f; // x4 DataSet
        int groupCount = 50;
        int startYear = 0;
        int endYear = startYear + groupCount;

        for (int i = 1; i < chooseUserFullModeList.size(); i++) {
            values1.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getaValue().getValue())));
            values2.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getbValue().getValue())));
            values3.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getcValue().getValue())));
            values4.add(new BarEntry(i, Float.valueOf(chooseUserFullModeList.get(i).getnValue().getValue())));

        }

        BarDataSet set1, set2, set3, set4;

        if (harmonicsbarchart.getData() != null && harmonicsbarchart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(2);
            set4 = (BarDataSet) harmonicsbarchart.getData().getDataSetByIndex(3);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            set4.setValues(values4);
            harmonicsbarchart.getData().notifyDataChanged();
            harmonicsbarchart.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "");
            set1.setColor(ColorList.ALL_METER_TITLE_COLOR[1]);
            set2 = new BarDataSet(values2, "");
            set2.setColor(ColorList.ALL_METER_TITLE_COLOR[2]);
            set3 = new BarDataSet(values3, "");
            set3.setColor(ColorList.ALL_METER_TITLE_COLOR[3]);
            set4 = new BarDataSet(values4, "");
            set4.setColor(ColorList.ALL_METER_TITLE_COLOR[4]);
            set1.setForm(legendForm);
            set2.setForm(legendForm);
            set3.setForm(legendForm);
            set4.setForm(legendForm);
            set1.setDrawValues(drawValuesEnabled);
            set2.setDrawValues(drawValuesEnabled);
            set3.setDrawValues(drawValuesEnabled);
            set4.setDrawValues(drawValuesEnabled);
            BarData data = new BarData(set1, set2, set3, set4);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(tfLight);
            harmonicsbarchart.setData(data);
        }
        harmonicsbarchart.groupBars(0, 50, 0.2f);

        // specify the width each bar should have
        harmonicsbarchart.getBarData().setBarWidth(barWidth);

        // restrict the x-axis range
        harmonicsbarchart.getXAxis().setAxisMinimum(startYear);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        harmonicsbarchart.getXAxis().setAxisMaximum(startYear + harmonicsbarchart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        if (harmonicsbarchart.getData().getDataSetCount() > 1)
            harmonicsbarchart.groupBars(startYear, groupSpace, barSpace);
        setTopTitle(harmonicsbarchart.getData().getDataSetByIndex(0).getEntryForIndex(selectEntry).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(1).getEntryForIndex(selectEntry).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(2).getEntryForIndex(selectEntry).getY() + "", harmonicsbarchart.getData().getDataSetByIndex(3).getEntryForIndex(selectEntry).getY() + "");

        harmonicsbarchart.invalidate();

    }

    private float valueTo100(float value) {
        return Integer.parseInt(new DecimalFormat("0").format(value * 100));
    }


    private List<ModelLineData> lineData = new ArrayList<>();
    private int singleBarSize = 1;

    private List<ModelLineData> chooseUserFullModeList(List<ModelLineData> lineDataList,
                                                       int wir_index, int funTypeIndex, int wir_right_index) {
        try {
            lineData.clear();
            switch (wir_index) {
                case 0://3QWYE
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                case 7://1Q SPLIT PHASE
                case 9://1Q +NEUTRAL
                    switch (funTypeIndex) {
                        case 2://A
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 9));
                            }
                            break;
                        case 1://S
                            lineData.add(lineDataList.get(1));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 6));
                            }

                            break;
                        case 0://V
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 8));
                            }

                            break;
                    }
                    break;
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
                case 8://1Q IT NO NEUTRAL  列表和柱状图的数据结构待谢工确认
                    switch (funTypeIndex) {
                        case 2://A
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 9));
                            }
                            break;
                        case 1://S
                            lineData.add(lineDataList.get(1));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 6));
                            }

                            break;
                        case 0://U
                            lineData.add(lineDataList.get(4));
                            for (int i = 0; i < 50; i++) {
                                lineData.add(lineDataList.get(i + 7));
                            }

                            break;
                    }
                    break;
            }
        } catch (Exception e) {

        }
        return lineData;
    }


}
