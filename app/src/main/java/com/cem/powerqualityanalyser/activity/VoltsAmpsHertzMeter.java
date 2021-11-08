package com.cem.powerqualityanalyser.activity;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.DataModel;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


public class VoltsAmpsHertzMeter extends BaseFragmentTrend {

    private MyTableListView stickyLayout;
    private MeterGroupListObj groupListObj1;
    private TextView Group_list_middleText,Group_list_leftText,Group_list_rightText;
    private ImageView Group_list_rightview;
    private RightModeView rightModeView;
    private List<RightListViewItemObj> strList;
    private int showItem = 3;
    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    private BaseBottomAdapterObj baseBottomAdapterObj = null;

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {


    }

    @Override
    public void setShowMeterData(final ModelAllData list) {
        addSelectMeterData(wir_index, wir_right_index, list);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (list.getModelLineData().size() > 0) {
                    tv_hz.setText(list.getHzData() == null || list.getHzData().equals("- - -") ? "----Hz" : DataFormatUtil.formatValue(Float.valueOf(list.getHzData()), 2) + "Hz");
                }
                if (stickyLayout.showItemsCount() < 1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });
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
    

    /**
     * 实时值
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list){
        switch (wir_index){
            case 0://3QWYE
                switch (wir_right_index){
                    case 0://4V

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V)",5,8), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V)",5,8), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");
                        break;
                    case 1://3U

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,8), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,8), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 2://4A

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");

                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, list.getFkLineData(), showItem,"");
                        break;
                    case 6://N

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, list.getThdrLineData(), showItem,"");
                        break;

                }
                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (wir_right_index){
                    case 0://3V

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V)",5,8), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V)",5,8), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 8,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 9,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 1://3U

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V)",5,8), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V)",5,8), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 2://3A

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");

                        break;

                }
                break;
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                switch (wir_right_index){
                    case 0://3V

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V)",5,8), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V)",5,8), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 8,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 9,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 1://3U

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V)",5,8), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V)",5,8), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 2://3A

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");

                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, list.getFkLineData(), showItem,"");
                        break;

                    case 6://N

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, list.getThdrLineData(), showItem,"");

                        break;

                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://3V
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;

                    case 1://3A

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");

                        break;
                    case 2://L1
                    case 3://L2
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, list.getFkLineData(), showItem,"");

                        break;

                    case 4://N
                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, list.getThdrLineData(), showItem,"");

                        break;

                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){
                    case 0://U
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 1://A

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");

                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (wir_right_index){
                    case 0://2V
                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1, groupListObj1,list.getDcLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");

                        break;
                    case 1://2A
                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, list.getFkLineData(), showItem,"");
                        
                        break;
                    case 2://L1

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, list.getMaxLineData(), showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, list.getMinLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, list.getThdrLineData(), showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, list.getPstLineData(), showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, list.getPltLineData(), showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, list.getFhlLineData(), showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, list.getFkLineData(), showItem,"");

                        break;
                    case 3://N

                        addMeterData(getSpannableString("RMS"), 0, groupListObj1,list.getRmsLineData(), showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, list.getPeakALineData(), showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, list.getPeakBLineData(), showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, list.getCfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, list.getThdfLineData(), showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, list.getThdrLineData(), showItem,"");

                        break;
                }
                break;
        }
    }




    @Override
    public int setContentView() {
        return R.layout.fragment_volts_amps_hertz_trend_layout;
    }

    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();

        Group_list_middleText = (TextView) findViewById(R.id.Group_list_middleText);
        Group_list_leftText = (TextView) findViewById(R.id.Group_list_leftText);
        Group_list_rightText = (TextView) findViewById(R.id.Group_list_rightText);
        Group_list_rightview = (ImageView) findViewById(R.id.Group_list_rightview);
        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);

        tv_hz = (TextView) findViewById(R.id.tv_hz);

        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
        stickyLayout.requestFocus();
        stickyLayout.setSelected(true);
//        stickyLayout.setListFocusAble(false);
//        rightModeView.getViewFoucs();

        groupListObj1=new MeterGroupListObj();
        rightModeView.setUpDownClick(false);

        String[] showItem2=getString(R.string.set_wir_item).split(",");
        Group_list_rightText.setTextSize(18f);
        Group_list_rightText.setText(configV + "  " + configHz + "  " +  showItem2[wir_index]);
        Group_list_middleText.setText(R.string.allmeter_votls_v);
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        rightModeView.hideUpDownView();
        switch (wir_index) {
            case 0://3QWYE
                showItem = 5;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(4);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                strList.clear();

                strList.add(new RightListViewItemObj("4V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("4A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                break;

            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3c_array));
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;

            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                showItem = 5;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(4);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 9,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;
            case 7://1Q SPLIT PHASE
                showItem = 4;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(3);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});


                break;
            case 8://1Q IT NO NEUTRAL
                showItem = 2;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(1);
                groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));
                strList.clear();
                strList.add(new RightListViewItemObj("U", -1));
                strList.add(new RightListViewItemObj("A", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                break;
            case 9://1Q +NEUTRAL
                showItem = 3;
                groupListObj1.Clear();
                stickyLayout.setShowDividerCount(2);
                groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));
                strList.clear();
                strList.add(new RightListViewItemObj("2V", -1));
                strList.add(new RightListViewItemObj("2A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));

                addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V≃)","PEAK-(V≃)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                break;

        }

//        ((VoltsAmpsHertzActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
        rightModeView.setModeList(strList);
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                if (stickyLayout.showItemsCount()<1) {
                    stickyLayout.addItem(groupListObj1);
                }
                stickyLayout.notifyChildChanged();
            }
        });

        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index);
            }
        });

 //       rightModeView.setSelection(0);

    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index){
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);

        switch (wir_index) {
            case 9://1Q +NEUTRAL
                switch (wir_right_index) {//切换右边选项
                    case 0://2V
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;
                    case 1://2A
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1n_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;

                    case 2://L1
                        Group_list_middleText.setText("");
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});

                        break;
                    case 3://N
                        Group_list_middleText.setText("");
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;
                }
                break;
            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){//切换右边选项
                    case 0://U
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        showItem = 2;
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.L1L2_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;

                    case 1://A
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        showItem = 2;
                        stickyLayout.setShowDividerCount(1);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1_array));

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;

                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        showItem = 4;
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");


                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3A
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        showItem = 4;
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2n_array));

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(A≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                    case 2://L1
                    case 3://L2
                        Group_list_middleText.setText("");
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});
                        break;

                    case 4://N
                        Group_list_middleText.setText("");
                        showItem = 3;
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.Clear();
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;

                }
                break;
            case 6:// 2½-ELEMENT
            case 5://3QHIGH LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        showItem = 5;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3U
                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;
                    case 2://3A
                        showItem = 5;
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        showItem = 3;
                        Group_list_middleText.setText("");
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD%f"), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD%r"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD%f","THD%r","PST","PLT","FHL","FK"});
                        break;
                    case 6://N
                        showItem = 3;
                        Group_list_middleText.setText("");
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;


                }
                break;
            case 4://3QDELTA
            case 3://2-ELEMENT
            case 2://3QIT
            case 1://3QOPEN LEG
                switch (wir_right_index) {//切换右边选项
                    case 0://3V
                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3c_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3U
                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;
                    case 2://3A
                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l3_array));

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A=)","PEAK-(A=)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","FHL","FK"});
                        break;

                }
                break;
            case 0://3QWYE
                switch (wir_right_index){
                    case 0://4V
                        showItem = 5;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});
                        break;
                    case 1://3U

                        showItem = 4;
                        Group_list_middleText.setText(R.string.allmeter_votls_v);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(3);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1l2l2l3l3l1_array));

                        addMeterData(getSpannableString("RMS(V≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC(V=)",2,6), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(V≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(V≃)",5,9), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(V≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(V≃)",3,7), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(V≃)","DC(V=)","PEAK+(V=)","PEAK-(V=)","MAX(V≃)","MIN(V≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;
                    case 2://4A
                        showItem = 5;
                        Group_list_middleText.setText(R.string.allmeter_current_a);
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(4);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.l1al2bl3cn_array));

                        addMeterData(getSpannableString("RMS(A≃)",3,7), 0, groupListObj1,modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+(A≃)",5,9), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-(A≃)",5,9), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX(A≃)",3,7), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN(A≃)",3,7), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%f)",3,7), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD(%r)",3,7), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 9,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS(A≃)","PEAK+(A≃)","PEAK-(A≃)","MAX(A≃)","MIN(A≃)","CF","THD(%f)","THD(%r)","PST","PLT"});

                        break;
                    case 3://L1
                    case 4://L2
                    case 5://L3
                        showItem = 3;
                        Group_list_middleText.setText("");
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MAX"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("MIN"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 6,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 7,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 8,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PST"), 9,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PLT"), 10,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FHL"), 11,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("FK"), 12,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS(V≃)",new String[]{"RMS","DC","PEAK+","PEAK-","MAX","MIN","CF","THD(%f)","THD(%r)","PST","PLT","FHL","FK"});
                        break;
                    case 6://N
                        showItem = 3;
                        Group_list_middleText.setText("");
                        groupListObj1.Clear();
                        stickyLayout.setShowDividerCount(2);
                        groupListObj1.addHeader(getResources().getStringArray(R.array.currenta_array));

                        addMeterData(getSpannableString("RMS"), 0,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("DC"), 1,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK+"), 2,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("PEAK-"), 3,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("CF"), 4,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 5,groupListObj1, modelLineData, showItem,"");
                        addMeterData(getSpannableString("THD"), 6,groupListObj1, modelLineData, showItem,"");

                        baseBottomAdapterObj = new BaseBottomAdapterObj(2,"RMS",new String[]{"RMS","DC","PEAK+","PEAK-","CF","THD%f","THD%r"});
                        break;

                }
                break;
        }
 //       ((VoltsAmpsHertzActivity)getActivity()).updateBottomData(baseBottomAdapterObj,2);
        stickyLayout.notifyChildChanged();
    }

    public void setFocusOnLeft() {
 //       stickyLayout.requestFocus();
        stickyLayout.getViewFoucs();
        rightModeView.setListViewFocusable(false);
        rightModeView.setListViewFocusableInTouchMode(false);
        rightModeView.lostFocus(true);

    }

    public void setFocusOnRight() {
        stickyLayout.setListFocusAble(false);
        rightModeView.getViewFoucs();
        rightModeView.lostFocus(false);
        rightModeView.setSelection(0);


    }
}
