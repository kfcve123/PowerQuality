package com.cem.powerqualityanalyser.tool;


import com.cem.powerqualityanalyser.libs.MeterAllParameter;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libs.PhaseType;
import com.cem.powerqualityanalyser.meterobject.MeterAllParamObj;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

/**
 * 功率与电能
 */
public class MeterPowerKWTool {
    private static PhaseObj lastkwh;
    private static PhaseObj lastKVAh;
    private static PhaseObj lastKvarh;


    public static MeterAllParamObj getMeterData(int typeIndex, MeterAllParameter allparam) {
        MeterAllParamObj obj = null;
        switch (typeIndex) {
            case 0:
                obj = possKW(allparam);
                break;
            case 1:
                obj = possKva(allparam);
                break;
            case 2:
                obj = possKvar(allparam);
                break;
            case 3:
                obj = possPF(allparam);
                break;
            case 4:
                obj = possKwh(allparam);
                break;
            case 5:
                obj = possKVAh(allparam);
                break;
            case 6:
                obj = possKvarh(allparam);
                break;
            case 7:
                obj = possVrmsTriangle(allparam);
                break;
            case 8:
                obj = possVrmsStar(allparam);
                break;
            case 9:
                obj = possVrmsTriangle(allparam);
                break;
        }
        return obj;
    }

    private static MeterAllParamObj possKW(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getPhase_kw().getPhaseA(), allparam.getPhase_kw().getPhaseB(), allparam.getPhase_kw().getPhaseC(), null, "KW");
        return obj;
    }

    private static MeterAllParamObj possKva(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getPhase_kva().getPhaseA(), allparam.getPhase_kva().getPhaseB(), allparam.getPhase_kva().getPhaseC(), null, "KW");
        return obj;
    }

    private static MeterAllParamObj possKvar(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getPhase_kvar().getPhaseA(), allparam.getPhase_kvar().getPhaseB(), allparam.getPhase_kvar().getPhaseC(), null, "KW");
        return obj;
    }

    private static MeterAllParamObj possPF(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getPhase_Pf().getPhaseA(), allparam.getPhase_Pf().getPhaseB(), allparam.getPhase_Pf().getPhaseC(), null, "KW");
        return obj;
    }

    private static MeterAllParamObj possVrmsStar(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getV_StarValue().getPhaseA(), allparam.getV_StarValue().getPhaseB(), allparam.getV_StarValue().getPhaseC(), allparam.getV_StarValue().getPhaseN(), "V");
        return obj;
    }

    private static MeterAllParamObj possVrmsTriangle(MeterAllParameter allparam) {
        MeterAllParamObj obj = new MeterAllParamObj(allparam.getV_triangleValue().getPhaseA(), allparam.getV_triangleValue().getPhaseB(), allparam.getV_triangleValue().getPhaseC(), null, "V");
        return obj;
    }

    private static MeterAllParamObj possKwh(MeterAllParameter allparam) {
        lastkwh = Data2kwH(lastkwh, allparam.getPhase_kw());
        MeterAllParamObj obj = new MeterAllParamObj(lastkwh.getPhaseA(), lastkwh.getPhaseB(), lastkwh.getPhaseC(), null, "KWh");
        return obj;
    }

    private static MeterAllParamObj possKVAh(MeterAllParameter allparam) {
        lastKVAh = Data2kwH(lastKVAh, allparam.getPhase_kva());
        MeterAllParamObj obj = new MeterAllParamObj(lastKVAh.getPhaseA(), lastKVAh.getPhaseB(), lastKVAh.getPhaseC(), null, "KWh");
        return obj;
    }

    private static MeterAllParamObj possKvarh(MeterAllParameter allparam) {
        lastKvarh = Data2kwH(lastKvarh, allparam.getPhase_kvar());
        MeterAllParamObj obj = new MeterAllParamObj(lastKvarh.getPhaseA(), lastKvarh.getPhaseB(), lastKvarh.getPhaseC(), null, "KWh");
        return obj;
    }

    public  static  PhaseObj  Data2kwH(PhaseObj lastobj, PhaseObj obj){
        if (lastobj==null)
            lastobj=obj;
        else{
            lastobj=new PhaseObj(PhaseType.None, kw2kwH(lastobj.getPhaseA(),obj.getPhaseA()),kw2kwH(lastobj.getPhaseB(),obj.getPhaseB()),kw2kwH(lastobj.getPhaseC(),obj.getPhaseC()));
        }
        return  lastobj;
    }

    private static MeterData kw2kwH(MeterData lase, MeterData data){
        if (data == null)
            data = new MeterData(0);

        float value=data.getValue()/3600;
        if (lase!=null)//电能属于累加值
            value=lase.getValue()+value;
        MeterData meterData = null;
        if (value >= 100)
            meterData =new MeterData(value);
        else if (value >= 10)
            meterData = new MeterData(value,1,false,false);
        else if (value >= 1)
            meterData = new MeterData(value,2,false,false);
        else if (value < 1)
            meterData = new MeterData(value,3,false,false);

        if (lase==null)
            lase=meterData;
        return meterData;
    }


    /**
     * 新电能累加计算
     * @param typeIndex
     * @param allparam
     * @return
     */
    public static ModelLineData getMeterData(int typeIndex, ModelAllData allparam) {
        ModelLineData obj = null;
        switch (typeIndex) {
            case 0:
                obj = possKWh(allparam);
                break;
            case 1:
                obj = possKVAh(allparam);
                break;
            case 2:
                obj = possKvarh(allparam);
                break;
            case 3:
                obj = possKwhform(allparam);
                break;
            case 4:
                obj = possKwhrev(allparam);
                break;
        }
        return obj;
    }

    private static ModelLineData lastkWhData;
    private static ModelLineData lastkwhforwData;
    private static ModelLineData lastkwhrevData;
    private static ModelLineData lastKVAhData;
    private static ModelLineData lastKvarhData;

    /**
     * Reset 重新开始统计功率和
     */
    public static void resetEnergy(){
        if(lastkWhData!=null)
            lastkWhData = new ModelLineData();
        if(lastkwhforwData!=null)
            lastkwhforwData = new ModelLineData();
        if(lastkwhrevData!=null)
            lastkwhrevData = new ModelLineData();
        if(lastKVAhData!=null)
            lastKVAhData = new ModelLineData();
        if(lastKvarhData!=null)
            lastKvarhData = new ModelLineData();

    }


    private static ModelLineData possKWh(ModelAllData allparam) {
        lastkWhData = Data2kwH(lastkWhData, allparam.getkWLineData());
        return lastkWhData;
    }

    private static ModelLineData possKVAh(ModelAllData allparam) {
        lastKVAhData = Data2kwH(lastKVAhData, allparam.getkVALineData());
        return lastKVAhData;
    }

    private static ModelLineData possKvarh(ModelAllData allparam) {
        lastKvarhData = Data2kwH(lastKvarhData, allparam.getKvarLineData());
        return lastKvarhData;
    }

    private static ModelLineData possKwhform(ModelAllData allparam) {
        lastkwhforwData = Data2kwH(lastkwhforwData, allparam.getKwhforwLineData());
        return lastkwhforwData;
    }
    private static ModelLineData possKwhrev(ModelAllData allparam) {
        lastkwhrevData = Data2kwH(lastkwhrevData, allparam.getkWhrevLineData());
        return  lastkwhrevData;
    }

    public static ModelLineData Data2kwH(ModelLineData lastobj, ModelLineData obj) {
        if (lastobj == null)
            lastobj = obj;
        else {
            lastobj = new ModelLineData(kw2kwH(lastobj.getaValue(), obj.getaValue()), kw2kwH(lastobj.getbValue(), obj.getbValue()), kw2kwH(lastobj.getcValue(), obj.getcValue()),null);
        }
        return lastobj;
    }



    /**
     * 显示:
     * <p>
     * 大于(等于)100KWh(千瓦时)，无小数
     * 小于100KWh时同时大于(等于)10KWh， 保留1位小数
     * 小于10KWh时同时大于(等于)1KWh， 保留2位小数
     * 小于1KWh时, 保留3位小数.
     *
     * @param lase
     * @param data
     * @return
     */


    private static ModelBaseData kw2kwH(ModelBaseData lase, ModelBaseData data) {
        if (data == null)
            data = new ModelBaseData(0);

        float value = data.getValueFl() / 3600;
        if (lase != null)//电能属于累加值
            value = lase.getValueFl() + value;
        ModelBaseData meterData = null;
        if (value >= 100)
            meterData = new ModelBaseData(value);
        else if (value >= 10)
            meterData = new ModelBaseData(value, 1);
        else if (value >= 1)
            meterData = new ModelBaseData(value, 2);
        else if (value < 1)
            meterData = new ModelBaseData(value, 3);

        if (lase == null)
            lase = meterData;
        return meterData;
    }



}
