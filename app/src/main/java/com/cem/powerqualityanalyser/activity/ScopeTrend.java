package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.chart.ScopeView;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.newchart.ScopeTrendView;
import com.cem.powerqualityanalyser.tool.MeterOscTools;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

public class ScopeTrend extends BaseFragmentTrend {

    private RightModeView rightModeView;
    private int wir_right_index = 0;
    private List<RightListViewItemObj> strList;
    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    private ScopeTrendView scopeTrendView;


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int funTypeIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    private List<ModelLineData> selectAllDataList = new ArrayList<>();

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, final int wir_index, final int wir_right_index, final int popwindowsIndex, final boolean cursorOpen) {

        if(modelAllData!=null) {
            if (scopeTrendView != null) {
                showScopeTrendChart(400,modelAllData,wir_index,wir_right_index,popwindowsIndex,cursorOpen);
                /*final List<List<Float>> meterGraphData = MeterOscTools.getMeterData(wir_index,wir_right_index, modelAllData.getModelLineData().subList(14,modelAllData.getModelLineData().size()));
                if(meterGraphData!=null && meterGraphData.size()>0) {
                    try {
                        scopeTrendView.post(new Runnable() {
                            @Override
                            public void run() {
                                scopeTrendView.setLineData(meterGraphData,false);
                                scopeTrendView.setTopTitle(modelAllData.getModelLineData().get(0).getaValue() != null ? modelAllData.getModelLineData().get(0).getaValue().getValue() : "0", modelAllData.getModelLineData().get(0).getbValue() != null ? modelAllData.getModelLineData().get(0).getbValue().getValue() : "0", modelAllData.getModelLineData().get(0).getcValue() != null ? modelAllData.getModelLineData().get(0).getcValue().getValue() : "", modelAllData.getModelLineData().get(0).getnValue() != null ? modelAllData.getModelLineData().get(0).getnValue().getValue() : "");
                            }
                        });
                    }catch (Exception e){

                    }
                }*/

            }
        }
    }


    /**
     * Inrush 根据接线方式，V,A popIndex 和 L1,L2,L3,N的popInde决定筛选哪些数据去显示
     * @param modelAllData
     * @param wir_index
     * @param firstPopIndex
     * @param secondPopIndex
     * @param showCursor
     */
    private void showScopeTrendChart(int getDataSize,final ModelAllData modelAllData, final int wir_index, final int firstPopIndex, final int secondPopIndex,final boolean showCursor){
        final List<ModelLineData> modelAllDataList = modelAllData.getModelLineData();
        selectAllDataList.clear();
        if(modelAllDataList!=null) {
            scopeTrendView.post(new Runnable() {
                @Override
                public void run() {
                    scopeTrendView.setTopTitle(modelAllDataList.get(0).getaValue() != null ? modelAllDataList.get(0).getaValue().getValue() : "0", modelAllDataList.get(0).getbValue() != null ? modelAllDataList.get(0).getbValue().getValue() : "0", modelAllDataList.get(0).getcValue() != null ? modelAllDataList.get(0).getcValue().getValue() : "", modelAllDataList.get(0).getnValue() != null ? modelAllDataList.get(0).getnValue().getValue() : "");
//                    scopeTrendView.clearValues();
                }
            });
            /*selectAllDataList.addAll(modelAllDataList.subList(14,modelAllDataList.size()));
            for (ModelLineData modelLineData : selectAllDataList) {
                scopeTrendView.setShowMeterData(modelLineData, showCursor);
            }*/

            selectAllDataList.addAll(modelAllDataList.subList(14,modelAllDataList.size()));
            for (ModelLineData modelLineData : selectAllDataList) {
                scopeTrendView.setShowMeterData(modelLineData, showCursor);
            }
        }
    }

    public void setShowMeterData(final List<ModelLineData> modelLineDataList,final boolean cursorOpen) {
        if(modelLineDataList!=null && modelLineDataList.size()>0) {
            if (scopeTrendView != null) {
                scopeTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        for (ModelLineData modelLineData : modelLineDataList) {
                            scopeTrendView.setShowMeterData(modelLineData, cursorOpen);
                        }
                    }
                });
            }
        }
    }


    public void setShowMeterData(final ModelLineData modelLineData,final boolean cursorOpen) {
        if(modelLineData!=null) {
            if (scopeTrendView != null) {
                scopeTrendView.post(new Runnable() {
                    @Override
                    public void run() {
                        scopeTrendView.setShowMeterData(modelLineData,cursorOpen);

                    }
                });
            }
        }
    }

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }


    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        scopeTrendView = (ScopeTrendView) findViewById(R.id.scopetrendview);

        rightModeView = (RightModeView) findViewById(R.id.modeview);
        strList =  new ArrayList();
        tv_hz = (TextView) findViewById(R.id.tv_hz);
        tv_hz.setVisibility(View.INVISIBLE);
        String[] showItems=getString(R.string.set_wir_item).split(",");
        int[] topsBg = getResources().getIntArray(R.array.top_backgroud_res_array);
        scopeTrendView.setScopeTopView(20f,showItems[wir_index] + "      " +  configV + "      " + configHz);
        rightModeView.hideUpDownView();
        ModelLineData modelLineData = new ModelLineData();
        ModelBaseData modelBaseData = new ModelBaseData("---");
        modelLineData.setaValue(modelBaseData);
        modelLineData.setbValue(modelBaseData);
        modelLineData.setcValue(modelBaseData);
        modelLineData.setnValue(modelBaseData);
        BaseBottomAdapterObj baseBottomAdapterObj = null;
        switch (wir_index) {
            case 0://3ØWYE
            case 5://3ØHIGH LEG
            case 6://2½-ELEMENT
                strList.clear();
                strList.add(new RightListViewItemObj("4V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("4A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(5,"3L");
                scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
                scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");
 //               scopeTrendView.setTopTitle("----", "----", "----", "----",config);
                break;
            case 1://3ØOPEN LEG
            case 2://3ØIT
            case 3://2-ELEMENT
            case 4://3ØDELTA
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                refeshHeadColor(5,"3L");
                scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
                scopeTrendView.setTopLeftTitle("L1","L2","L3","");
                break;
            case 7://1Ø SPLIT PHASE
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(3,"L1L2N");
                scopeTrendView.setLineDataSetVisable(true,true,false,true,config);
                scopeTrendView.setTopLeftTitle("L1","L2","","N");
                break;
            case 9://1Ø +NEUTRAL
                strList.clear();
                strList.add(new RightListViewItemObj("2V", -1));
                strList.add(new RightListViewItemObj("2A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("N", -1));
                refeshHeadColor(3,"L1N");
                scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                scopeTrendView.setTopLeftTitle("L1","L2","","");
            case 8://1Ø IT NO NEUTRAL
                strList.clear();
                refeshHeadColor(5,"3L");
                scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                scopeTrendView.setTopLeftTitle("L1","L2","","");
                break;
        }

        rightModeView.setModeList(strList);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
//                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index);
            }
        });

    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index){
        switch (wir_index) {
            case 9://1Ø +NEUTRAL
                switch (wir_right_index) {
                    case 0://2V
                    case 1://2A
                        refeshHeadColor(3,"L1N");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("L1","N","","");
                        break;
                    case 2://L1
                        refeshHeadColor(3,"L1");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");

                    case 3://N
                        refeshHeadColor(3,"N");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                }

                break;
            case 7://1Ø SPLIT PHASE
                switch (wir_right_index) {
                    case 0://3V
                    case 1://3A
                        refeshHeadColor(3,"L1L2N");
                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
                        scopeTrendView.setTopLeftTitle("L1","L2","N","");
                        break;
                    case 2://L1
                        refeshHeadColor(5,"L1");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                    case 3://L2
                        refeshHeadColor(5,"L2");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                    case 4://N
                        refeshHeadColor(5,"N");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
                        break;
                }

                break;
            case 8://1Ø IT NO NEUTRAL  没有右边侧边栏

                break;
            case 3://2-ELEMENT
            case 4://3QDELTA        这三个 显示需要调整
            case 2://3ØIT
            case 1://3QOPEN LEG
                switch (wir_right_index){
                    case 0://3V
                    case 2://3A
                        refeshHeadColor(5,"3L");
                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
                        scopeTrendView.setTopLeftTitle("L1","L2","L3","");
                        break;
                    case 1://3U
                        refeshHeadColor(5,"3L");
                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
                        scopeTrendView.setTopLeftTitle("L1L2","L2L3","L3L1","");
                        break;
                }
                break;
            case 0://3QWYE
            case 5://3QHIGH LEG   这三个 显示需要调整
            case 6:// 2½-ELEMENT
                switch (wir_right_index){
                    case 0://4V
                        refeshHeadColor(5,"3L");
                        scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
                        scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");
 //                       scopeTrendView.setTopTitle("----", "----", "----", "----",config);
                        break;
                    case 1://3U
                        refeshHeadColor(5,"3L");
                        scopeTrendView.setLineDataSetVisable(true,true,true,false,config);
                        scopeTrendView.setTopLeftTitle("U12","U23","U31","");
 //                       scopeTrendView.setTopTitle("----", "----", "----", "",config);

                        break;
                    case 2://4A
                        refeshHeadColor(5,"3L");
                        scopeTrendView.setLineDataSetVisable(true,true,true,true,config);
                        scopeTrendView.setTopLeftTitle("L1(A)","L2(B)","L3(C)","N");
 //                       scopeTrendView.setTopTitle("----", "----", "----", "----",config);
                        break;
                    case 3://L1
                        refeshHeadColor(5,"L1");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
  //                      scopeTrendView.setTopTitle("----", "----", "", "",config);
                        break;
                    case 4://L2
                        refeshHeadColor(5,"L2");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
  //                      scopeTrendView.setTopTitle("----", "----", "", "",config);
                        break;
                    case 5://L3
                        refeshHeadColor(5,"L3");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
  //                      scopeTrendView.setTopTitle("----", "----", "", "",config);
                        break;
                    case 6://N
                        refeshHeadColor(5,"N");
                        scopeTrendView.setLineDataSetVisable(true,true,false,false,config);
                        scopeTrendView.setTopLeftTitle("V","A","","");
 //                       scopeTrendView.setTopTitle("----", "----", "", "",config);
                        break;


                }
                break;
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_scope_trend_layout;
    }


    public void zoomScale(float yScale){
        zoomScale(0f,yScale);
    }

    private void zoomScale(float xScale,float yScale){
        if(scopeTrendView!=null)
            scopeTrendView.zoomScale(xScale,yScale);
    }

    public void showCursor(boolean enable){
        if(scopeTrendView!=null)
            scopeTrendView.showCursor(enable);
    }

    public void moveCursor(int i ){
        if(scopeTrendView!=null)
            scopeTrendView.moveCursor(i);
    }

    public void fitScreen() {
        if(scopeTrendView!=null)
            scopeTrendView.fitScreen();
    }

    public void setTouchEnable(boolean enable) {
        scopeTrendView.setTouchEnable(enable);
    }

    public void setLastEntry(int i) {

    }


}
