package com.cem.powerqualityanalyser.activity;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.newchart.VoltsAmpsHzView;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


public class VoltsAmpsHertzTrend extends BaseFragmentTrend {

    private VoltsAmpsHzView voltsAmpsHertzView;
    private int lastPosition = -1;
    private Integer[] topBgRes;

    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int funTypeIndex) {
        if(modelAllData!=null)
            if(voltsAmpsHertzView!=null) {
                voltsAmpsHertzView.post(new Runnable() {
                    @Override
                    public void run() {
                        voltsAmpsHertzView.showMeterAllParamObj(getSelectModelLineData(modelAllData, wir_index, wir_right_index, funTypeIndex));
                    }
                });
            }
    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int wir_right_index, final int popwindowsIndex) {



    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    private ModelLineData getSelectModelLineData(ModelAllData modelAllData,int wir_index,int wir_right_index,int popwindowsIndex){
        if(modelAllData!=null) {
            ModelLineData modelLineData = new ModelLineData();
            switch (wir_index){
                case 0://3QWYE
                    switch (wir_right_index){
                        case 0://4V
                        case 1://3U
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    //测试曲线值是否正确
//                                    ModelBaseData aValue = new ModelBaseData("11");
//                                    ModelBaseData bValue = new ModelBaseData("13");
//                                    ModelBaseData cValue = new ModelBaseData("15");
//                                    ModelBaseData nValue = new ModelBaseData("17");
//                                    modelLineData = new ModelLineData(aValue,bValue,cValue,nValue);
                                    break;
                                    
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 2://4A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 3://L1
                        case 4://L2
                        case 5://L3
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                                case 11:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 12:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 6://N
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
                    switch (wir_right_index){
                        case 0://3V
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 1://3U
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 2://3A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                    switch (wir_right_index){
                        case 0://3V
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 1://3U
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 2://3A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 3://L1
                        case 4://L2
                        case 5://L3
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                                case 11:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 12:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 6://N
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 7://1Q SPLIT PHASE
                    switch (wir_right_index){
                        case 0://3V
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 1://3A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 2://L1
                        case 3://L2
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                                case 11:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 12:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                             break;
                        case 4://N
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 8://1Q IT NO NEUTRAL
                    switch (wir_right_index){
                        case 0://U
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 1://A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                    }
                    break;
                case 9://1Q +NEUTRAL
                    switch (wir_right_index){
                        case 0://2V
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                            }
                            break;
                        case 1://2A
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 2://L1
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getMaxLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getMinLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 7:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 8:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                                case 9:
                                    modelLineData = modelAllData.getPstLineData();
                                    break;
                                case 10:
                                    modelLineData = modelAllData.getPltLineData();
                                    break;
                                case 11:
                                    modelLineData = modelAllData.getFhlLineData();
                                    break;
                                case 12:
                                    modelLineData = modelAllData.getFkLineData();
                                    break;
                            }
                            break;
                        case 3://N
                            switch (popwindowsIndex){
                                case 0:
                                    modelLineData = modelAllData.getRmsLineData();
                                    break;
                                case 1:
                                    modelLineData = modelAllData.getDcLineData();
                                    break;
                                case 2:
                                    modelLineData = modelAllData.getPeakALineData();
                                    break;
                                case 3:
                                    modelLineData = modelAllData.getPeakBLineData();
                                    break;
                                case 4:
                                    modelLineData = modelAllData.getCfLineData();
                                    break;
                                case 5:
                                    modelLineData = modelAllData.getThdfLineData();
                                    break;
                                case 6:
                                    modelLineData = modelAllData.getThdrLineData();
                                    break;
                            }
                            break;
                    }
                    break;
            }
            return modelLineData;
        }
        return null;
    }


    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {
        
    }

    @Override
    public void onInitViews() {
        voltsAmpsHertzView = (VoltsAmpsHzView)findViewById(R.id.voltsView);
        voltsAmpsHertzView.setDragEnabled(true);
        voltsAmpsHertzView.setScanleEnable(true);

        topBgRes = new Integer[]{R.mipmap.top_black_bg,R.mipmap.top_yellow_bg,R.mipmap.top_red_bg,R.mipmap.top_blue_bg,R.mipmap.top_green_bg};
        voltsAmpsHertzView.setTopBag(topBgRes[config.getSetup_Show_Color_VL1()-1],topBgRes[config.getSetup_Show_Color_VL2()-1],topBgRes[config.getSetup_Show_Color_VL3()-1],topBgRes[config.getSetup_Show_Color_VN()-1]);
        voltsAmpsHertzView.setAxisRightEnabled();
        voltsAmpsHertzView.setenableGridDashedLine();
        setVoltsModeIndex(wir_index,0,0);
 //       updateWirData(wir_index,wir_right_index);
    }

    @Override
    public int setContentView() {
        return R.layout.activity_volts_amps_hertz;
    }

    public void setShowMeterData(){

    }

    public void setHold(boolean isHold){
        voltsAmpsHertzView.setHold(isHold);
    }

    public void setVoltsModeIndex(int wir_index,int wir_right_index,int positio) {
        if(voltsAmpsHertzView!=null) {
            voltsAmpsHertzView.setVoltsModeIndex(wir_index,wir_right_index,positio);
            if(lastPosition!= positio) {
                lastPosition = positio;
                voltsAmpsHertzView.setNewData(true);

            }
        }
    }


    public void showCursor(boolean enable){
        if(voltsAmpsHertzView!=null)
            voltsAmpsHertzView.showCursor(enable);
    }

    private void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(voltsAmpsHertzView!=null)
            voltsAmpsHertzView.zoomScale(xScale,yScale);
    }

    public void moveCursor(int i ){
        if(voltsAmpsHertzView!=null)
            voltsAmpsHertzView.moveCursor(i);
    }

}
