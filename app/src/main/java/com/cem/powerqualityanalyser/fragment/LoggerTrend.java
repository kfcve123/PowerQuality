package com.cem.powerqualityanalyser.fragment;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.libs.GraphDataObj;
import com.cem.powerqualityanalyser.libs.MeterAllParameter;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.MeterHz_400;
import com.cem.powerqualityanalyser.libs.MeterSudden_UP_Down;
import com.cem.powerqualityanalyser.libs.MeterThreeHarmonic;
import com.cem.powerqualityanalyser.libs.MeterThreeUnbalanced;
import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libs.PhaseType;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.datalist.MyTableListView;


import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

public class LoggerTrend extends BaseFragmentTrend {
    private MyTableListView stickyLayout;

    @Override
    public void setShowMeterData(ModelAllData modelAllData) {

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

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {
        if (baseMeterData instanceof MeterAllParameter){

        }
    }

    public void clearTableListData(){
        if (stickyLayout != null)
            stickyLayout.clearData();
    }

    public void setShowMeterData(final BaseMeterData baseMeterData, final List<String> parameters) {
        if (baseMeterData instanceof MeterAllParameter){
            setShowAllParametr(baseMeterData,parameters);
        }else if (baseMeterData instanceof MeterThreeUnbalanced){
            setShowUnbalance(baseMeterData,parameters);
        }else if (baseMeterData instanceof MeterThreeHarmonic){
            setShowHarmonic(baseMeterData,parameters);
        }else if (baseMeterData instanceof MeterSudden_UP_Down){
            setShowDips(baseMeterData,parameters);
        }else if (baseMeterData instanceof MeterHz_400){
            setShowShipBoard(baseMeterData,parameters);
        }
    }

    private void setShowShipBoard(final BaseMeterData baseMeterData, final List<String> parameters) {
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < parameters.size(); i++) {
                    MeterGroupListObj groupListObj;

                    if (stickyLayout.showItemsCount() < parameters.size()){
                        groupListObj = new MeterGroupListObj();
                        if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uabc))){
                            groupListObj.addHeader(getResources().getStringArray(R.array.abc_arrat));
                        }else{
                            groupListObj.addHeader(getResources().getStringArray(R.array.abcn_arrat));
                        }
                        stickyLayout.addItem(groupListObj);
                    }else{
                        groupListObj = stickyLayout.getGroupItem(i);
                    }

                    if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Urms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterHz_400) baseMeterData).getV_StarValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Irms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterHz_400) baseMeterData).getA_TriangleValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Freq))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterHz_400) baseMeterData).getFrequency());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uabc))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterHz_400) baseMeterData).getV_TriangleValue());
                    }
                }
                stickyLayout.notifyChildChanged();
            }
        });
    }

    private void setShowDips(final BaseMeterData baseMeterData, final List<String> parameters) {
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < parameters.size(); i++) {
                    MeterGroupListObj groupListObj;

                    if (stickyLayout.showItemsCount() < parameters.size()){
                        groupListObj = new MeterGroupListObj();
                        groupListObj.addHeader(getResources().getStringArray(R.array.abcn_arrat));
                        stickyLayout.addItem(groupListObj);
                    }else{
                        groupListObj = stickyLayout.getGroupItem(i);
                    }

                    if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Urms)) || parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Irms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterSudden_UP_Down) baseMeterData).getPhaseValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Freq))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterSudden_UP_Down) baseMeterData).getValueFrequency());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Urms2)) || parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Irms2))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterSudden_UP_Down) baseMeterData).getListGraphPhase());
                    }
                }
                stickyLayout.notifyChildChanged();
            }
        });
    }

    private void setShowHarmonic(final BaseMeterData baseMeterData, final List<String> parameters) {
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < parameters.size(); i++) {
                    MeterGroupListObj groupListObj;

                    if (stickyLayout.showItemsCount() < parameters.size()){
                        groupListObj = new MeterGroupListObj();
                        groupListObj.addHeader(getResources().getStringArray(R.array.abcn_arrat));
                        stickyLayout.addItem(groupListObj);
                    }else{
                        groupListObj = stickyLayout.getGroupItem(i);
                    }

                    if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Urms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getV_PhaseValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Irms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getA_PhaseValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Freq))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getFrequency());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Vfund))){
                        MeterData dc1= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListV_Phase().getValueA().get(0));
                        MeterData dc2= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListV_Phase().getValueB().get(0));
                        MeterData dc3= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListV_Phase().getValueC().get(0));
                        PhaseObj dc =new PhaseObj(PhaseType.None, dc1,dc2,dc3);
                        addMeterData(parameters.get(i),0,groupListObj,dc);
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Afund))){
                        MeterData dc1= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListA_Phase().getValueA().get(0));
                        MeterData dc2= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListA_Phase().getValueB().get(0));
                        MeterData dc3= new MeterData( ((MeterThreeHarmonic) baseMeterData).getListA_Phase().getValueC().get(0));
                        PhaseObj dc =new PhaseObj(PhaseType.None, dc1,dc2,dc3);
                        addMeterData(parameters.get(i),0,groupListObj,dc);
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uthd))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getV_THD_PhaseValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Ithd))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getA_THD_PhaseValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uthd2_50))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getListV_Phase());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Ithd2_50))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeHarmonic) baseMeterData).getListA_Phase());
                    }

                }
                stickyLayout.notifyChildChanged();
            }
        });
    }

    private void setShowUnbalance(final BaseMeterData baseMeterData, final List<String> parameters) {
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < parameters.size(); i++) {
                    MeterGroupListObj groupListObj;

                    if (stickyLayout.showItemsCount() < parameters.size()){
                        groupListObj = new MeterGroupListObj();
                        groupListObj.addHeader(getResources().getStringArray(R.array.abcn_arrat));
                        stickyLayout.addItem(groupListObj);
                    }else{
                        groupListObj = stickyLayout.getGroupItem(i);
                    }

                    if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Freq))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeUnbalanced) baseMeterData).getFrequency());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Vfund))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeUnbalanced) baseMeterData).getV_fundWave());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Afund))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeUnbalanced) baseMeterData).getA_fundWave());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.fA))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeUnbalanced) baseMeterData).getA_Angle());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.fV))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterThreeUnbalanced) baseMeterData).getV_Angle());
                    }
                }
                stickyLayout.notifyChildChanged();
            }
        });

    }

    private void setShowAllParametr(final BaseMeterData baseMeterData, final List<String> parameters){
        stickyLayout.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < parameters.size(); i++) {
                    MeterGroupListObj groupListObj;

                    if (stickyLayout.showItemsCount() < parameters.size()){
                        groupListObj = new MeterGroupListObj();
                        if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uabc))){
                            groupListObj.addHeader(getResources().getStringArray(R.array.abc_arrat));
                        }else{
                            groupListObj.addHeader(getResources().getStringArray(R.array.abcn_arrat));
                        }
                        stickyLayout.addItem(groupListObj);
                    }else{
                        groupListObj = stickyLayout.getGroupItem(i);
                    }

                    if (parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Urms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getV_StarValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Upk))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getV_StarMax());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Irms))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getA_Value());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Ipk))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getA_Max());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Freq))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getFrequency());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.PF))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getPhase_Pf());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.Uabc))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getV_triangleValue());
                    }else if(parameters.get(i).equals(LoggerTrend.this.getActivity().getResources().getString(R.string.udc))){
                        addMeterData(parameters.get(i),0,groupListObj,((MeterAllParameter) baseMeterData).getV_triangleDCValue());
                    }

                }
                stickyLayout.notifyChildChanged();
            }
        });
    }


    public void addMeterData(String str, int index, MeterGroupListObj groupListObj, GraphDataObj obj){

        for (int i=index;i<obj.getDataCount();i++ ){
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > i - index){
                item=groupListObj.getChild(i - index);
                item.setItem(1,Meter2String(2,obj.getValueA().get(i)));
                item.setItem(2,Meter2String(2,obj.getValueB().get(i)));
                item.setItem(3,Meter2String(2,obj.getValueC().get(i)));
            }else{
                item=new MeterGroupChildObj();
                item.AddMeterChildItem(str+" "+(i+1));
                groupListObj.addChild(item);
                item.AddMeterChildItem(Meter2String(2,obj.getValueA().get(i)));
                item.AddMeterChildItem(Meter2String(2,obj.getValueB().get(i)));
                item.AddMeterChildItem(Meter2String(2,obj.getValueC().get(i)));
            }

        }
    }


    @Override
    public void onInitViews() {
        stickyLayout = (MyTableListView) findViewById(R.id.sticky_layout);
    }
}
