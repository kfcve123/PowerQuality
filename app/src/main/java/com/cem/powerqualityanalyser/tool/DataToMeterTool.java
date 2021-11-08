package com.cem.powerqualityanalyser.tool;

import android.content.Context;


import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databean.DataMeterAllParameter;
import com.cem.powerqualityanalyser.databean.DataMeterHz_400;
import com.cem.powerqualityanalyser.databean.DataMeterSudden_UP_Down;
import com.cem.powerqualityanalyser.databean.DataMeterThreeHarmonic;
import com.cem.powerqualityanalyser.databean.DataMeterThreeUnbalanced;
import com.cem.powerqualityanalyser.databeannew.DataMeterAmpHarmonic;
import com.cem.powerqualityanalyser.databeannew.DataMeterDipsSwells;
import com.cem.powerqualityanalyser.databeannew.DataMeterEnergy;
import com.cem.powerqualityanalyser.databeannew.DataMeterFlicker;
import com.cem.powerqualityanalyser.databeannew.DataMeterFrequency;
import com.cem.powerqualityanalyser.databeannew.DataMeterInrush;
import com.cem.powerqualityanalyser.databeannew.DataMeterPower;
import com.cem.powerqualityanalyser.databeannew.DataMeterUnbalanced;
import com.cem.powerqualityanalyser.databeannew.DataMeterVoltAmp;
import com.cem.powerqualityanalyser.databeannew.DataMeterVoltHarmonic;
import com.cem.powerqualityanalyser.meterobject.HistoryMeterAllObj;
import com.cem.powerqualityanalyser.meterobject.NewHistoryMeterAllObj;

import java.util.ArrayList;
import java.util.List;

public class DataToMeterTool {
    private Context context;

    public DataToMeterTool(Context context) {
        this.context = context;
    }

    public String getFreq(List<BaseData> meterList){
        if (meterList == null || meterList.size() == 0)
            return "";
        BaseData baseData = meterList.get(0);
        if (baseData instanceof DataMeterAllParameter){
            return ((DataMeterAllParameter) baseData).getFrequency().getShowValue();
        }else if (baseData instanceof DataMeterThreeUnbalanced){
            return ((DataMeterThreeUnbalanced) baseData).getFrequency().getShowValue();
        }else if (baseData instanceof DataMeterSudden_UP_Down){
            return ((DataMeterSudden_UP_Down) baseData).getValueFrequency().getShowValue();
        }else if (baseData instanceof DataMeterThreeHarmonic){
            return ((DataMeterThreeHarmonic) baseData).getFrequency().getShowValue();
        }else if (baseData instanceof DataMeterHz_400){
            return ((DataMeterHz_400) baseData).getFrequency().getShowValue();
        }else{
            return "";
        }
    }

    public List<HistoryMeterAllObj> getMeterAllObj(int popwindowsIndex, List<BaseData> meterList, List<String> checkedParameters) {

        //选择的参数
        String checked = checkedParameters.get(popwindowsIndex);

        if (meterList.get(0) instanceof DataMeterAllParameter){
            return possAllMeter(checked,meterList);
        }else if (meterList.get(0) instanceof DataMeterThreeUnbalanced){
            return possUnbalance(checked,meterList);
        }else if (meterList.get(0) instanceof DataMeterSudden_UP_Down){
            return possDipSweel(checked,meterList);
        }else if (meterList.get(0) instanceof DataMeterThreeHarmonic){
            return possHarmonic(checked,meterList);
        }else if (meterList.get(0) instanceof DataMeterHz_400){
            return possHz400(checked,meterList);
        }

        return null;
    }

    private List<HistoryMeterAllObj> possDipSweel(String checked,  List<BaseData> dipsData) {
        List<HistoryMeterAllObj> list = new ArrayList<>();
        if (dipsData != null && dipsData.size() > 0){
            for (int i = 0; i < dipsData.size(); i++) {
                DataMeterSudden_UP_Down dataMeterSudden_up_down = (DataMeterSudden_UP_Down) dipsData.get(i);
                if (context.getResources().getString(R.string.Urms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterSudden_up_down.getPhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("V");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Irms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterSudden_up_down.getPhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("A");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Freq).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = new HistoryMeterAllObj(dataMeterSudden_up_down.getValueFrequency().toMeterData(), null, null, null, "Hz");
                    list.add(meterAllParamObj);
                }
            }
        }
        return list;
    }

    private List<HistoryMeterAllObj> possHz400(String checked, List<BaseData> shipboardData) {
        List<HistoryMeterAllObj> list = new ArrayList<>();
        if (shipboardData != null && shipboardData.size() > 0){
            for (int i = 0; i < shipboardData.size(); i++) {
                DataMeterHz_400 dataMeterHz_400 = (DataMeterHz_400) shipboardData.get(i);
                if (context.getResources().getString(R.string.Urms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj =dataMeterHz_400.getV_StarValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("V");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Irms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterHz_400.getA_TriangleValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("A");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Freq).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = new HistoryMeterAllObj(dataMeterHz_400.getFrequency().toMeterData(),null,null,null,"Hz");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Uabc).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterHz_400.getV_TriangleValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("V");
                    list.add(meterAllParamObj);
                }
            }
        }
        return list;
    }

    private List<HistoryMeterAllObj> possHarmonic(String checked, List<BaseData> harmonicData) {
        List<HistoryMeterAllObj> list = new ArrayList<>();
        if (harmonicData != null && harmonicData.size() > 0){
            for (int i = 0; i < harmonicData.size(); i++) {
                DataMeterThreeHarmonic dataMeterThreeHarmonic = (DataMeterThreeHarmonic) harmonicData.get(i);
                if (context.getResources().getString(R.string.Urms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterThreeHarmonic.getV_PhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("V");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Irms).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterThreeHarmonic.getA_PhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("A");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Freq).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = new HistoryMeterAllObj(dataMeterThreeHarmonic.getFrequency().toMeterData(),null,null,null,"Hz");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Uthd).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterThreeHarmonic.getV_THD_PhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("V");
                    list.add(meterAllParamObj);
                }else if (context.getResources().getString(R.string.Ithd).equals(checked)){
                    HistoryMeterAllObj meterAllParamObj = dataMeterThreeHarmonic.getA_THD_PhaseValue().toMeterAllParamObj();
                    meterAllParamObj.setUnit("A");
                    list.add(meterAllParamObj);
                }
            }

        }
        return list;
    }

    private List<HistoryMeterAllObj> possUnbalance(String checked, List<BaseData> unbalancedData) {
        List<HistoryMeterAllObj> list = new ArrayList<>();
        if (unbalancedData != null && unbalancedData.size() > 0){
            for (int i = 0; i < unbalancedData.size(); i++) {
                DataMeterThreeUnbalanced dataMeterThreeUnbalanced = (DataMeterThreeUnbalanced) unbalancedData.get(i);
                if (context.getResources().getString(R.string.Vfund).equals(checked)){
                    HistoryMeterAllObj e = dataMeterThreeUnbalanced.getV_fundWave().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }else if (context.getResources().getString(R.string.Afund).equals(checked)){
                    HistoryMeterAllObj e = dataMeterThreeUnbalanced.getA_fundWave().toMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }else if (context.getResources().getString(R.string.Freq).equals(checked)){
                    HistoryMeterAllObj e = new HistoryMeterAllObj(dataMeterThreeUnbalanced.getFrequency().toMeterData(),null,null,null,"Hz");
                    list.add(e);
                }else if (context.getResources().getString(R.string.fV).equals(checked)){
                    HistoryMeterAllObj e = dataMeterThreeUnbalanced.getV_Angle().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }else if (context.getResources().getString(R.string.fV).equals(checked)){
                    HistoryMeterAllObj e = dataMeterThreeUnbalanced.getA_Angle().toMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }
            }
        }
        return list;
    }

    private List<HistoryMeterAllObj> possAllMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<HistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.Urms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter) powerData.get(i)).getV_StarValue().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.Upk).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getV_StarMax().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.Irms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getA_Value().toMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.Ipk).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getA_Max().toMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.Freq).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = new HistoryMeterAllObj(((DataMeterAllParameter)powerData.get(i)).getFrequency().toMeterData(),null,null,null,"Hz");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.PF).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getPhase_Pf().toMeterAllParamObj();
                    e.setUnit("");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.Uabc).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getV_triangleValue().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.udc).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    HistoryMeterAllObj e = ((DataMeterAllParameter)powerData.get(i)).getV_triangleDCValue().toMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }
        }
        return list;
    }


    /*--------新协议数据结构---------*/

    public List<NewHistoryMeterAllObj> getNewMeterAllObj(int popwindowsIndex, List<BaseData> meterList, List<String> checkedParameters) {
        //选择的参数
        String checked = checkedParameters.get(popwindowsIndex);
        if(meterList.size()>0) {
            if (meterList.get(0) instanceof DataMeterVoltAmp) {
                return possVoltAmpMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterFlicker) {
                return possFlickerMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterFrequency) {
                return possFrequencyMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterVoltHarmonic) {
                return possVoltHarmonicMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterAmpHarmonic) {
                return possAmpHarmonicMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterUnbalanced) {
                return possUnbalanceMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterPower) {
                return possPowerMeter(checked, meterList);
            } else if (meterList.get(0) instanceof DataMeterEnergy) {
                return possEnergyMeter(checked, meterList);
            }
        }

        return null;
    }

    private List<NewHistoryMeterAllObj> possVoltAmpMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Urms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getUrmsValue()!=null){
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getUrmsValue().toNewMeterAllParamObj();
                        e.setUnit("V");
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Vrms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getVrmsValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getVrmsValue().toNewMeterAllParamObj();
                        e.setUnit("V");
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Arms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getArmsValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getArmsValue().toNewMeterAllParamObj();
                        e.setUnit("A");
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Ucf).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getUcfValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getUcfValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Vcf).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getVcfValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getVcfValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Acf).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getAcfValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getAcfValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Upeak1).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getUpeakUpValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getUpeakUpValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Upeak2).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getUpeakDownValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getUpeakDownValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Vpeak1).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    if(((DataMeterVoltAmp) powerData.get(i)).getVpeakUpValue()!=null) {
                        NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getVpeakUpValue().toNewMeterAllParamObj();
                        list.add(e);
                    }
                }
            }else if (context.getResources().getString(R.string.parameter_Vpeak2).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getVpeakDownValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Apeak1).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getApeakUpValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Apeak2).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getApeakDownValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Udc).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getUdcValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Vdc).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getVdcValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Adc).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltAmp) powerData.get(i)).getAdcValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possFlickerMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_pstmin).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterFlicker) powerData.get(i)).getPstMinValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_plt).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterFlicker) powerData.get(i)).getPltValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_pst).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterFlicker) powerData.get(i)).getPstValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_plt).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterFlicker) powerData.get(i)).getPltValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possFrequencyMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.Urms).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterFrequency) powerData.get(i)).getHzValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possVoltHarmonicMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Vfund).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getvFundValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_thdf).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getThdfValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_thdr).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getThdrValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_DC).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVoltDcValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H1).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth1Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H2).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth2Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H3).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth3Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H4).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth4Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H5).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth5Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H6).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth6Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H7).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth7Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H8).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth8Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H9).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth9Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H10).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth10Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H11).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth11Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H12).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth12Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H13).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth13Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H14).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth14Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H15).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth15Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H16).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth16Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H17).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth17Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H18).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth18Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H19).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth19Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H20).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth20Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H21).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth21Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H22).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth22Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H23).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth23Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H24).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth24Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H25).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth25Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H26).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth26Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H27).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth27Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H28).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth28Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H29).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth29Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H30).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth30Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H31).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth31Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H32).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth32Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H33).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth33Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H34).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth34Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H35).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth35Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H36).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth36Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H37).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth37Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H38).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth38Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H39).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth39Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H40).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth40Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H41).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth41Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_volt_H42).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth42Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H43).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth43Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H44).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth44Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H45).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth45Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H46).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth46Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H47).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth47Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H48).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth48Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H49).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth49Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_volt_H50).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterVoltHarmonic) powerData.get(i)).getVolth50Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possAmpHarmonicMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Afund).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getaFundValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_thdf).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getThdfValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_thdr).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getThdrValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_DC).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmpDcValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H1).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph1Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H2).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph2Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H3).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph3Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H4).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph4Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H5).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph5Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H6).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph6Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H7).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph7Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H8).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph8Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H9).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph9Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H10).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph10Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H11).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph11Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H12).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph12Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H13).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph13Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H14).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph14Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H15).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph15Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H16).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph16Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H17).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph17Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H18).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph18Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H19).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph19Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H20).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph20Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H21).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph21Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H22).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph22Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H23).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph23Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H24).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph24Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H25).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph25Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H26).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph26Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H27).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph27Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H28).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph28Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H29).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph29Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H30).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph30Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H31).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph31Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H32).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph32Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H33).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph33Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H34).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph34Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H35).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph35Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H36).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph36Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H37).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph37Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H38).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph38Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H39).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph39Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H40).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph40Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H41).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph41Value().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_amp_H42).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph42Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H43).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph43Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H44).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph44Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H45).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph45Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H46).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph46Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H47).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph47Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H48).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph48Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H49).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph49Value().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_amp_H50).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterAmpHarmonic) powerData.get(i)).getAmph50Value().toNewMeterAllParamObj();
                    list.add(e);
                }


            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possUnbalanceMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Unbal).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterUnbalanced) powerData.get(i)).getUnbalValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_Vneg).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterUnbalanced) powerData.get(i)).getVnegValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_Vzero).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterUnbalanced) powerData.get(i)).getVzerolValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_Aneg).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterUnbalanced) powerData.get(i)).getAnegValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_Azero).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterUnbalanced) powerData.get(i)).getAzeroValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possPowerMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_kW).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKwValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_kVA).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKvaValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_kvar).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKvarValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kVA_Harm).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKvaHarmValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_cos).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getCosValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kVA_Unb).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKvaUnbValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kW_fund).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKwFundValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kVA_fund).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getKvaFundValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_W_fund).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterPower) powerData.get(i)).getwFundValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }
        }
        return list;
    }
    private List<NewHistoryMeterAllObj> possEnergyMeter(String checked,List<BaseData> meterList){
        List<BaseData> powerData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (powerData != null && powerData.size() > 0){
            if (context.getResources().getString(R.string.parameter_kVAh).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterEnergy) powerData.get(i)).getkVahValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_kvarh).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterEnergy) powerData.get(i)).getkVarhValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }else if (context.getResources().getString(R.string.parameter_kWh_forw).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterEnergy) powerData.get(i)).getKwhForwValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kWh_rev).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterEnergy) powerData.get(i)).getKwhRevValue().toNewMeterAllParamObj();
                    list.add(e);
                }

            }else if (context.getResources().getString(R.string.parameter_kWh).equals(checked)){
                for (int i = 0; i < powerData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterEnergy) powerData.get(i)).getKwhValue().toNewMeterAllParamObj();
                    list.add(e);
                }
            }
        }
        return list;
    }


    public List<NewHistoryMeterAllObj> getInrushMeterAllObj(int popwindowsIndex, List<BaseData> meterList, List<String> checkedParameters) {
        //选择的参数
        String checked = checkedParameters.get(popwindowsIndex);
        if(meterList.size()>0) {
            if (meterList.get(0) instanceof DataMeterInrush) {
                return possInrushHistoryMeter(checked, meterList);
            }
        }
        return null;
    }

    private List<NewHistoryMeterAllObj> possInrushHistoryMeter(String checked,List<BaseData> meterList){
        List<BaseData> historyData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (historyData != null && historyData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Vrms).equals(checked)){
                for (int i = 0; i < historyData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterInrush) historyData.get(i)).getVrmsValue().toNewMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }else if(context.getResources().getString(R.string.parameter_Arms).equals(checked)){
                for (int i = 0; i < historyData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterInrush) historyData.get(i)).getArmsValue().toNewMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }
            }
        }
        return list;
    }


    public List<NewHistoryMeterAllObj> getDipsSwellsMeterAllObj(int popwindowsIndex, List<BaseData> meterList, List<String> checkedParameters) {
        //选择的参数
        String checked = checkedParameters.get(popwindowsIndex);
        if(meterList.size()>0) {
            if (meterList.get(0) instanceof DataMeterDipsSwells) {
                return possDipsSwellsHistoryMeter(checked, meterList);
            }
        }
        return null;
    }


    private List<NewHistoryMeterAllObj> possDipsSwellsHistoryMeter(String checked,List<BaseData> meterList){
        List<BaseData> historyData = meterList;
        List<NewHistoryMeterAllObj> list = new ArrayList<>();
        if (historyData != null && historyData.size() > 0){
            if (context.getResources().getString(R.string.parameter_Vrms).equals(checked)){
                for (int i = 0; i < historyData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterDipsSwells) historyData.get(i)).getVrmsValue().toNewMeterAllParamObj();
                    e.setUnit("V");
                    list.add(e);
                }
            }else if(context.getResources().getString(R.string.parameter_Arms).equals(checked)){
                for (int i = 0; i < historyData.size(); i++) {
                    NewHistoryMeterAllObj e = ((DataMeterDipsSwells) historyData.get(i)).getArmsValue().toNewMeterAllParamObj();
                    e.setUnit("A");
                    list.add(e);
                }
            }
        }
        return list;
    }


}
