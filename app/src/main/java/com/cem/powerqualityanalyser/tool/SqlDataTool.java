package com.cem.powerqualityanalyser.tool;

import android.content.Context;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databeannew.BaseData;
import com.cem.powerqualityanalyser.databeannew.DataGraphDataObj;
import com.cem.powerqualityanalyser.databean.DataMeterAllParameter;
import com.cem.powerqualityanalyser.databeannew.DataMeterAmpHarmonic;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databean.DataMeterHz_400;
import com.cem.powerqualityanalyser.databean.DataMeterSudden_UP_Down;
import com.cem.powerqualityanalyser.databean.DataMeterThreeHarmonic;
import com.cem.powerqualityanalyser.databean.DataMeterThreeUnbalanced;
import com.cem.powerqualityanalyser.databean.DataPhaseObj;
import com.cem.powerqualityanalyser.databeannew.DataMeterDipsSwells;
import com.cem.powerqualityanalyser.databeannew.DataMeterEnergy;
import com.cem.powerqualityanalyser.databeannew.DataMeterFlicker;
import com.cem.powerqualityanalyser.databeannew.DataMeterFrequency;
import com.cem.powerqualityanalyser.databeannew.DataMeterInrush;
import com.cem.powerqualityanalyser.databeannew.DataMeterPower;
import com.cem.powerqualityanalyser.databeannew.DataMeterUnbalanced;
import com.cem.powerqualityanalyser.databeannew.DataMeterVoltAmp;
import com.cem.powerqualityanalyser.databeannew.DataMeterVoltHarmonic;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;
import com.cem.powerqualityanalyser.databean.ParseHelpBean;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.libs.GraphDataObj;
import com.cem.powerqualityanalyser.libs.MeterAllParameter;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.MeterHz_400;
import com.cem.powerqualityanalyser.libs.MeterSudden_UP_Down;
import com.cem.powerqualityanalyser.libs.MeterThreeHarmonic;
import com.cem.powerqualityanalyser.libs.MeterThreeUnbalanced;
import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;
import com.cem.powerqualityanalyser.libs.PhaseType;
import com.cem.powerqualityanalyser.sqlBean.DipsSwellsDataBean;
import com.cem.powerqualityanalyser.sqlBean.DipsSwellsMeterData;
import com.cem.powerqualityanalyser.sqlBean.InrushDataBean;
import com.cem.powerqualityanalyser.sqlBean.InrushMeterData;
import com.cem.powerqualityanalyser.sqlBean.SqlApi;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlMeterData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelLineData;

public class SqlDataTool {

    /**
     *  Power
     * @param context
     * @param meterAllParameter
     * @param checkParameters
     * @return
     */
    public static List<SqlMeterData> toSqlData(Context context, MeterAllParameter meterAllParameter, String checkParameters){
        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();
        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Urms))){
                PhaseObjN v_starValue = meterAllParameter.getV_StarValue();
                List<SqlMeterData> urmsList = possPhaseobj(v_starValue, SqlApi.Mode_Power, SqlApi.Parameter_Urms);
                sqlMeterDataList.addAll(urmsList);
            }else if (split[i].equals(Res2String(context, R.string.Irms))){
                PhaseObjN a_value = meterAllParameter.getA_Value();
                List<SqlMeterData> irmsList = possPhaseobj(a_value, SqlApi.Mode_Power, SqlApi.Parameter_Irms);
                sqlMeterDataList.addAll(irmsList);
            }else if (split[i].equals(Res2String(context, R.string.Upk))){
                PhaseObj v_starMax = meterAllParameter.getV_StarMax();
                List<SqlMeterData> upkList = possPhaseobj(v_starMax, SqlApi.Mode_Power, SqlApi.Parameter_Upk);
                sqlMeterDataList.addAll(upkList);
            }else if (split[i].equals(Res2String(context, R.string.Ipk))){
                PhaseObj a_max = meterAllParameter.getA_Max();
                List<SqlMeterData> ipkList = possPhaseobj(a_max, SqlApi.Mode_Power, SqlApi.Parameter_Ipk);
                sqlMeterDataList.addAll(ipkList);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = meterAllParameter.getFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Power, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.PF))){
                //PF 特殊情况，N值代表Pf总(三相功率因数)3位小数
                PhaseObj phase_pf = meterAllParameter.getPhase_Pf();
                List<SqlMeterData> pfList = possPhaseobj(phase_pf, SqlApi.Mode_Power, SqlApi.Parameter_PF);
                sqlMeterDataList.addAll(pfList);
            }else if (split[i].equals(Res2String(context, R.string.udc))){
                PhaseObjN v_triangleDCValue = meterAllParameter.getV_triangleDCValue();
                List<SqlMeterData> udcList = possPhaseobj(v_triangleDCValue, SqlApi.Mode_Power, SqlApi.Parameter_Udc);
                sqlMeterDataList.addAll(udcList);
            }else if (split[i].equals(Res2String(context, R.string.Uabc))){
                PhaseObj v_triangleValue = meterAllParameter.getV_triangleValue();
                List<SqlMeterData> uabcList = possPhaseobj(v_triangleValue, SqlApi.Mode_Power, SqlApi.Parameter_Uabc);
                sqlMeterDataList.addAll(uabcList);
            }

        }

        return sqlMeterDataList;
    }

    private static DataPhaseObjN parseObjN(List<SqlMeterData> sqlMeterDataList,int j){
        SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
        DataPhaseObjN objN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
        return objN;
    }
    private static DataPhaseObj parseObj(List<SqlMeterData> sqlMeterDataList,int j){
        SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        DataPhaseObj obj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
        return obj;
    }
    private static ParseHelpBean parseList(List<SqlMeterData> sqlMeterDataList, int j, int parameterCode){
        List<Float> floatA = new ArrayList<>();
        List<Float> floatB = new ArrayList<>();
        List<Float> floatC = new ArrayList<>();
        int dataCount = sqlMeterDataList.get(j).getDataCount();
        URMS2:for (;j<sqlMeterDataList.size() - 3;j = j + 3){
            if (sqlMeterDataList.get(j).getParameterType() == parameterCode){
                SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                floatA.add(sqlMeterDataA.value);
                floatB.add(sqlMeterDataB.value);
                floatC.add(sqlMeterDataC.value);
            }else{
                break URMS2;
            }

        }
        DataGraphDataObj dataGraphDataObj = new DataGraphDataObj(floatA, floatB, floatC,dataCount);
        return new ParseHelpBean(j,dataGraphDataObj);
    }


    public static List<BaseData> SqlDataBean2BaseData(Context context, SqlDataBean sqlDataBean){
        List<BaseData> baseDataList = null;

        //所有点数的集合
        List<SqlMeterData> dataList = sqlDataBean.getDataList();
        //一条数据构成的点数
        int phaseObjCount = getPhaseObjCount(context, sqlDataBean.modeType, sqlDataBean.getCheckParameters(),dataList,0);
        int listIndex = 0;
        List<List<SqlMeterData>> lists = new ArrayList<List<SqlMeterData>>();

        while (listIndex + phaseObjCount <= dataList.size()){
            lists.add(dataList.subList(listIndex,listIndex + phaseObjCount));
            listIndex = listIndex + phaseObjCount;
            phaseObjCount = getPhaseObjCount(context, sqlDataBean.modeType, sqlDataBean.getCheckParameters(),dataList,listIndex);
        }

        long startTime = System.currentTimeMillis();
        switch (sqlDataBean.getModeType()){
            case SqlApi.Mode_Power:

                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterAllParameter meterAllParameter = new DataMeterAllParameter();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN urms = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterAllParameter.setV_StarValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN irms = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterAllParameter.setA_Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Upk){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj upk = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            meterAllParameter.setV_StarMax(parseObj(sqlMeterDataList,j));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Ipk){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            meterAllParameter.setA_Max(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uabc){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            meterAllParameter.setV_triangleValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_PF){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN dataPhaseObjN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterAllParameter.setPhase_Pf(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Udc){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN dataPhaseObjN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterAllParameter.setV_triangleDCValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            meterAllParameter.setFrequency(dataMeterData);
                        }

                    }
                    baseDataList.add(meterAllParameter);
                }
                break;
            case SqlApi.Mode_Unbalance:

                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterThreeUnbalanced unbalanced = new DataMeterThreeUnbalanced();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Vfound){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN urms = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            unbalanced.setV_fundWave(parseObjN(sqlMeterDataList,j));
                            j = j + 3;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Afound){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            unbalanced.setA_fundWave(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_FaiV){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            unbalanced.setV_Angle(parseObj(sqlMeterDataList,j));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_FaiA){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            unbalanced.setV_Angle(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            unbalanced.setFrequency(dataMeterData);
                        }

                    }
                    baseDataList.add(unbalanced);
                }
                break;
            case SqlApi.Mode_Dip:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterSudden_UP_Down dataMeterSudden_up_down = new DataMeterSudden_UP_Down();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            dataMeterSudden_up_down.setPhaseValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms2){
//                            List<Float> floatA = new ArrayList<>();
//                            List<Float> floatB = new ArrayList<>();
//                            List<Float> floatC = new ArrayList<>();
//                            int dataCount = sqlMeterDataList.get(j).getDataCount();
//                            URMS2:for (j = j;j<sqlMeterDataList.size() - 3;j = j + 3){
//                                if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms2){
//                                    SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                                    SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                                    SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                                    floatA.add(sqlMeterDataA.value);
//                                    floatB.add(sqlMeterDataB.value);
//                                    floatC.add(sqlMeterDataC.value);
//                                }else{
//                                    break URMS2;
//                                }
//
//                            }
//                            DataGraphDataObj dataGraphDataObj = new DataGraphDataObj(floatA, floatB, floatC,dataCount);
                            ParseHelpBean parseHelpBean = parseList(sqlMeterDataList, j, SqlApi.Parameter_Urms2);
                            dataMeterSudden_up_down.setListGraphPhase(parseHelpBean.getDataGraphDataObj());
                            j = parseHelpBean.getIndex();
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            dataMeterSudden_up_down.setValueFrequency(dataMeterData);
                        }

                    }
                    baseDataList.add(dataMeterSudden_up_down);
                }

                break;
            case SqlApi.Mode_Harmonic:

                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterThreeHarmonic dataMeterThreeHarmonic = new DataMeterThreeHarmonic();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            dataMeterThreeHarmonic.setV_PhaseValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            dataMeterThreeHarmonic.setA_PhaseValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uthd){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN dataPhaseObj = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(),sqlMeterDataN.toDataMeterData());
                            dataMeterThreeHarmonic.setV_THD_PhaseValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Ithd){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN dataPhaseObj = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(),sqlMeterDataN.toDataMeterData());
                            dataMeterThreeHarmonic.setA_THD_PhaseValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_UthdList){
//                            List<Float> floatA = new ArrayList<>();
//                            List<Float> floatB = new ArrayList<>();
//                            List<Float> floatC = new ArrayList<>();
//                            int dataCount = sqlMeterDataList.get(j).getDataCount();
//                            uthd:for (;j < sqlMeterDataList.size() - 3;j = j + 3 ){
//                                if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_UthdList){
//                                    SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                                    SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                                    SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                                    floatA.add(sqlMeterDataA.value);
//                                    floatB.add(sqlMeterDataB.value);
//                                    floatC.add(sqlMeterDataC.value);
//                                }else{
//                                    break uthd;
//                                }
//                            }
//                            DataGraphDataObj dataGraphDataObj = new DataGraphDataObj(floatA, floatB, floatC,dataCount);
                            ParseHelpBean parseHelpBean = parseList(sqlMeterDataList, j, SqlApi.Parameter_UthdList);
                            dataMeterThreeHarmonic.setListV_Phase(parseHelpBean.getDataGraphDataObj());
                            j = parseHelpBean.getIndex();
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_IthdList){
//                            List<Float> floatA = new ArrayList<>();
//                            List<Float> floatB = new ArrayList<>();
//                            List<Float> floatC = new ArrayList<>();
//                            int dataCount = sqlMeterDataList.get(j).getDataCount();
//                            ithd:for (;j < sqlMeterDataList.size() - 3;j = j + 3 ){
//                                if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_IthdList){
//                                    SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                                    SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                                    SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                                    floatA.add(sqlMeterDataA.value);
//                                    floatB.add(sqlMeterDataB.value);
//                                    floatC.add(sqlMeterDataC.value);
//                                }else{
//                                    break ithd;
//                                }
//
//                            }
//                            DataGraphDataObj dataGraphDataObj = new DataGraphDataObj(floatA, floatB, floatC,dataCount);
                            ParseHelpBean parseHelpBean = parseList(sqlMeterDataList, j, SqlApi.Parameter_IthdList);
                            dataMeterThreeHarmonic.setListA_Phase(parseHelpBean.getDataGraphDataObj());
                            j = parseHelpBean.getIndex();
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            dataMeterThreeHarmonic.setFrequency(dataMeterData);
                        }

                    }
                    baseDataList.add(dataMeterThreeHarmonic);
                }


                break;
            case SqlApi.Mode_Shipboard:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterHz_400 meterHz_400 = new DataMeterHz_400();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN urms = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterHz_400.setV_StarValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
//                            DataPhaseObjN irms = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
                            meterHz_400.setA_TriangleValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uabc){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj uabc = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            meterHz_400.setV_TriangleValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            meterHz_400.setFrequency(dataMeterData);
                        }

                    }
                    baseDataList.add(meterHz_400);
                }

                break;
            case SqlApi.Mode_Inrush:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterSudden_UP_Down dataMeterSudden_up_down = new DataMeterSudden_UP_Down();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
//                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                            DataPhaseObj dataPhaseObj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                            dataMeterSudden_up_down.setPhaseValue(parseObj(sqlMeterDataList,j));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms2){
//                            List<Float> floatA = new ArrayList<>();
//                            List<Float> floatB = new ArrayList<>();
//                            List<Float> floatC = new ArrayList<>();
//                            int dataCount = sqlMeterDataList.get(j).getDataCount();
//                            IRMS2:for (j = j;j<sqlMeterDataList.size() - 3;j = j + 3){
//                                if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms2){
//                                    SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
//                                    SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
//                                    SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
//                                    floatA.add(sqlMeterDataA.value);
//                                    floatB.add(sqlMeterDataB.value);
//                                    floatC.add(sqlMeterDataC.value);
//                                }else{
//                                    break IRMS2;
//                                }
//
//                            }
                            ParseHelpBean parseHelpBean = parseList(sqlMeterDataList, j, SqlApi.Parameter_Irms2);
                            dataMeterSudden_up_down.setListGraphPhase(parseHelpBean.getDataGraphDataObj());
                            j = parseHelpBean.getIndex();
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            dataMeterSudden_up_down.setValueFrequency(dataMeterData);
                        }
                    }
                    baseDataList.add(dataMeterSudden_up_down);
                }

                break;
        }

        //转换完释放状态
        MeterDataPool.recycle(sqlDataBean);
        log.e("转换时间 : " + (System.currentTimeMillis() - startTime));

        return baseDataList;
    }


    private static int getPhaseObjCount(Context context,int modeType,String checkParameters,List<SqlMeterData> dataList,int startIndex){
        String[] split = checkParameters.split(",");
        int count = 0;
        switch (modeType){
            case SqlApi.Mode_Power:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Urms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Irms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Upk)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Ipk)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(context,R.string.PF)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.udc)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Uabc)))
                        count += 3;

                }

                break;

            case SqlApi.Mode_Unbalance:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Vfund)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Afund)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(context,R.string.fV)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.fA)))
                        count += 3;

                }

                break;
            case SqlApi.Mode_Dip:
                int urmsCount = 0;
                first:for (int i = startIndex; i < dataList.size(); i++) {
                    if (dataList.get(i).getParameterType() == SqlApi.Parameter_Urms2){
                        urmsCount ++;
                    }else if (dataList.get(i).getParameterType() != SqlApi.Parameter_Urms2 && urmsCount > 0){
                        break first;
                    }
                }


                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Urms)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Urms2)))//47-63个半波的有效值
                        count += urmsCount;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;

                }

                break;
            case SqlApi.Mode_Harmonic:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Urms)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Irms)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(context,R.string.Uthd)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Ithd)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Uthd2_50)))
                        count += 3 * 50;
                    else if (split[i].equals(Res2String(context,R.string.Ithd2_50)))
                        count += 3 * 50;


                }

                break;
            case SqlApi.Mode_Shipboard:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Urms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Irms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(context,R.string.Uabc)))
                        count += 3;

                }

                break;
            case SqlApi.Mode_Inrush:
                int irmsCount = 0;
                first:for (int i = startIndex; i < dataList.size(); i++) {
                    if (dataList.get(i).getParameterType() == SqlApi.Parameter_Irms2){
                        irmsCount ++;
                    }else if (dataList.get(i).getParameterType() != SqlApi.Parameter_Irms2 && irmsCount > 0){
                        break first;
                    }
                }


                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.Irms)))
                        count += 3;
                    else if (split[i].equals(Res2String(context,R.string.Irms2)))//47-63个半波的有效值
                        count += irmsCount;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 1;

                }

                break;
        }
        return count;
    }

    /**
     * Unbalance
     * @param context
     * @param unbalanced
     * @param checkParameters
     * @return
     */
    public static List<SqlMeterData> toSqlData(Context context, MeterThreeUnbalanced unbalanced, String checkParameters) {

        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();

        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Vfund))){
                PhaseObjN v_fundWave = unbalanced.getV_fundWave();
                List<SqlMeterData> vfoundList = possPhaseobj(v_fundWave, SqlApi.Mode_Unbalance, SqlApi.Parameter_Vfound);
                sqlMeterDataList.addAll(vfoundList);
            }else if (split[i].equals(Res2String(context, R.string.Afund))){
                PhaseObj a_fundWave = unbalanced.getA_fundWave();
                List<SqlMeterData> afoundList = possPhaseobj(a_fundWave, SqlApi.Mode_Unbalance, SqlApi.Parameter_Afound);
                sqlMeterDataList.addAll(afoundList);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = unbalanced.getFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Unbalance, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.fV))){
                PhaseObj v_angle = unbalanced.getV_Angle();
                List<SqlMeterData> vList = possPhaseobj(v_angle, SqlApi.Mode_Unbalance, SqlApi.Parameter_FaiV);
                sqlMeterDataList.addAll(vList);
            }else if (split[i].equals(Res2String(context, R.string.fA))){
                PhaseObj a_angle = unbalanced.getA_Angle();
                List<SqlMeterData> aList = possPhaseobj(a_angle, SqlApi.Mode_Unbalance, SqlApi.Parameter_FaiA);
                sqlMeterDataList.addAll(aList);
            }

        }
        return sqlMeterDataList;

    }

    /**
     * Dip
     * @param context
     * @param meterSudden_up_down
     * @param checkParameters
     * @return
     */
    public static List<SqlMeterData> toSqlData(Context context, MeterSudden_UP_Down meterSudden_up_down, String checkParameters) {

        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();

        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Urms))){
                PhaseObj phaseValue = meterSudden_up_down.getPhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Dip, SqlApi.Parameter_Urms);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = meterSudden_up_down.getValueFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Dip, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.Urms2))){
                GraphDataObj listGraphPhase = meterSudden_up_down.getListGraphPhase();
                listGraphPhase.getValueA();
                for (int j = 0; j <  listGraphPhase.getValueA().size(); j++) {
                    Float aFloat = listGraphPhase.getValueA().get(i);
                    Float aFloat1 = listGraphPhase.getValueB().get(i);
                    Float aFloat2 = listGraphPhase.getValueC().get(i);
                    PhaseObj phaseObj = new PhaseObj(PhaseType.None, new MeterData(aFloat, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat1, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat2, listGraphPhase.getDataCount(),false,false));
                    List<SqlMeterData> sqlMeterDataList1 = possPhaseobj(phaseObj, SqlApi.Mode_Dip, SqlApi.Parameter_Urms2);
                    sqlMeterDataList.addAll(sqlMeterDataList1);
                }
            }

        }
        return sqlMeterDataList;

    }


    /**
     * Harmonic
     * @param context
     * @param
     * @param checkParameters
     * @return
     */
    public static List<SqlMeterData> toSqlData(Context context, MeterThreeHarmonic meterThreeHarmonic, String checkParameters) {

        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();

        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Urms))){
                PhaseObj phaseValue = meterThreeHarmonic.getV_PhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Harmonic, SqlApi.Parameter_Urms);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = meterThreeHarmonic.getFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Harmonic, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.Irms))){
                PhaseObj phaseValue = meterThreeHarmonic.getA_PhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Harmonic, SqlApi.Parameter_Irms);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Uthd))){
                PhaseObjN phaseValue = meterThreeHarmonic.getV_THD_PhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Harmonic, SqlApi.Parameter_Uthd);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Ithd))){
                PhaseObjN phaseValue = meterThreeHarmonic.getA_THD_PhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Harmonic, SqlApi.Parameter_Ithd);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Uthd2_50))){
                GraphDataObj listGraphPhase = meterThreeHarmonic.getListV_Phase();
                for (int j = 0; j <  listGraphPhase.getValueA().size(); j++) {
                    Float aFloat = listGraphPhase.getValueA().get(i);
                    Float aFloat1 = listGraphPhase.getValueB().get(i);
                    Float aFloat2 = listGraphPhase.getValueC().get(i);
                    PhaseObj phaseObj = new PhaseObj(PhaseType.None, new MeterData(aFloat, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat1, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat2, listGraphPhase.getDataCount(),false,false));
                    List<SqlMeterData> sqlMeterDataList1 = possPhaseobj(phaseObj, SqlApi.Mode_Harmonic, SqlApi.Parameter_UthdList);
                    sqlMeterDataList.addAll(sqlMeterDataList1);
                }
            }else if (split[i].equals(Res2String(context, R.string.Ithd2_50))){
                GraphDataObj listGraphPhase = meterThreeHarmonic.getListA_Phase();
                for (int j = 0; j <  listGraphPhase.getValueA().size(); j++) {
                    Float aFloat = listGraphPhase.getValueA().get(i);
                    Float aFloat1 = listGraphPhase.getValueB().get(i);
                    Float aFloat2 = listGraphPhase.getValueC().get(i);
                    PhaseObj phaseObj = new PhaseObj(PhaseType.None, new MeterData(aFloat, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat1, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat2, listGraphPhase.getDataCount(),false,false));
                    List<SqlMeterData> sqlMeterDataList1 = possPhaseobj(phaseObj, SqlApi.Mode_Harmonic, SqlApi.Parameter_IthdList);
                    sqlMeterDataList.addAll(sqlMeterDataList1);
                }
            }

        }
        return sqlMeterDataList;

    }


    /**
     * Shipboard
     * @param context
     * @param meterHz_400
     * @param checkParameters
     * @return
     */
    public static List<SqlMeterData> toSqlData(Context context, MeterHz_400 meterHz_400, String checkParameters) {

        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();

        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Urms))){
                PhaseObjN v_starValue = meterHz_400.getV_StarValue();
                List<SqlMeterData> vfoundList = possPhaseobj(v_starValue, SqlApi.Mode_Shipboard, SqlApi.Parameter_Urms);
                sqlMeterDataList.addAll(vfoundList);
            }else if (split[i].equals(Res2String(context, R.string.Irms))){
                PhaseObjN a_triangleValue = meterHz_400.getA_TriangleValue();
                List<SqlMeterData> afoundList = possPhaseobj(a_triangleValue, SqlApi.Mode_Shipboard, SqlApi.Parameter_Irms);
                sqlMeterDataList.addAll(afoundList);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = meterHz_400.getFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Shipboard, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.Uabc))){
                PhaseObj v_triangleValue = meterHz_400.getV_TriangleValue();
                List<SqlMeterData> vList = possPhaseobj(v_triangleValue, SqlApi.Mode_Shipboard, SqlApi.Parameter_Uabc);
                sqlMeterDataList.addAll(vList);
            }

        }
        return sqlMeterDataList;

    }


    public static List<SqlMeterData> toSqlDataInrush(Context context, MeterSudden_UP_Down meterSudden_up_down, String checkParameters) {

        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();

        String[] split = checkParameters.split(",");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(Res2String(context, R.string.Irms))){
                PhaseObj phaseValue = meterSudden_up_down.getPhaseValue();
                List<SqlMeterData> list = possPhaseobj(phaseValue, SqlApi.Mode_Inrush, SqlApi.Parameter_Irms);
                sqlMeterDataList.addAll(list);
            }else if (split[i].equals(Res2String(context, R.string.Freq))){
                MeterData frequency = meterSudden_up_down.getValueFrequency();
                SqlMeterData sqlMeterData = possMeterData(frequency, SqlApi.Mode_Inrush, SqlApi.Parameter_Freq);
                sqlMeterDataList.add(sqlMeterData);
            }else if (split[i].equals(Res2String(context, R.string.Irms2))){
                GraphDataObj listGraphPhase = meterSudden_up_down.getListGraphPhase();
                listGraphPhase.getValueA();
                for (int j = 0; j <  listGraphPhase.getValueA().size(); j++) {
                    Float aFloat = listGraphPhase.getValueA().get(i);
                    Float aFloat1 = listGraphPhase.getValueB().get(i);
                    Float aFloat2 = listGraphPhase.getValueC().get(i);
                    PhaseObj phaseObj = new PhaseObj(PhaseType.None, new MeterData(aFloat, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat1, listGraphPhase.getDataCount(),false,false), new MeterData(aFloat2, listGraphPhase.getDataCount(),false,false));
                    List<SqlMeterData> sqlMeterDataList1 = possPhaseobj(phaseObj, SqlApi.Mode_Inrush, SqlApi.Parameter_Irms2);
                    sqlMeterDataList.addAll(sqlMeterDataList1);
                }
            }

        }
        return sqlMeterDataList;

    }

    private static SqlMeterData possMeterData(MeterData meterData, int modeTyoe, int parameterType){
        SqlMeterData sqlMeterData = MeterDataPool.obtainMeter();
        sqlMeterData.time = new Date();
        sqlMeterData.setModeType(modeTyoe);
        sqlMeterData.setParameterType(parameterType);
        sqlMeterData.setPhaseType(SqlApi.PhaseType_None);
        sqlMeterData.setDataCount(meterData.getDataPoint());
        sqlMeterData.setValue(meterData.getValue());
        sqlMeterData.setrElectricity(meterData.isElectricity());
        sqlMeterData.setgElectricity(meterData.isGElectricit());
        return sqlMeterData;
    }

    private static List<SqlMeterData> possPhaseobj(PhaseObj phaseObj,int modeTyoe,int parameterType){
        List<SqlMeterData> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SqlMeterData sqlMeterData = MeterDataPool.obtainMeter();
            sqlMeterData.time = new Date();
            sqlMeterData.setModeType(modeTyoe);
            sqlMeterData.setParameterType(parameterType);
            if (i == 0){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_A);
                sqlMeterData.setValue(phaseObj.getPhaseA().getValue());
                sqlMeterData.setgElectricity(phaseObj.getPhaseA().isGElectricit());
                sqlMeterData.setrElectricity(phaseObj.getPhaseA().isElectricity());
                sqlMeterData.setDataCount(phaseObj.getPhaseA().getDataPoint());
            }else if (i == 1){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_B);
                sqlMeterData.setValue(phaseObj.getPhaseB().getValue());
                sqlMeterData.setDataCount(phaseObj.getPhaseB().getDataPoint());
                sqlMeterData.setgElectricity(phaseObj.getPhaseB().isGElectricit());
                sqlMeterData.setrElectricity(phaseObj.getPhaseB().isElectricity());
            }else if (i == 2){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_C);
                sqlMeterData.setValue(phaseObj.getPhaseC().getValue());
                sqlMeterData.setDataCount(phaseObj.getPhaseC().getDataPoint());
                sqlMeterData.setgElectricity(phaseObj.getPhaseC().isGElectricit());
                sqlMeterData.setrElectricity(phaseObj.getPhaseC().isElectricity());
            }

            list.add(sqlMeterData);
        }

        if (phaseObj instanceof PhaseObjN){
            PhaseObjN phaseObj1 = (PhaseObjN) phaseObj;
            SqlMeterData sqlMeterData = MeterDataPool.obtainMeter();
            sqlMeterData.time = new Date();
            sqlMeterData.setModeType(modeTyoe);
            sqlMeterData.setParameterType(parameterType);
            sqlMeterData.setPhaseType(SqlApi.PhaseType_N);
            sqlMeterData.setValue(phaseObj1.getPhaseN().getValue());
            sqlMeterData.setDataCount(phaseObj1.getPhaseN().getDataPoint());
            sqlMeterData.setgElectricity(phaseObj1.getPhaseN().isGElectricit());
            sqlMeterData.setrElectricity(phaseObj1.getPhaseN().isElectricity());
            list.add(sqlMeterData);
        }

        return list;

    }

    public static int getSaveCount(BaseMeterData baseMeterData){
        if (baseMeterData instanceof MeterAllParameter){
            return 8;
        }else if (baseMeterData instanceof MeterThreeUnbalanced){
            return 5;
        }else if (baseMeterData instanceof MeterSudden_UP_Down){
            return 2 + ((MeterSudden_UP_Down) baseMeterData).getListGraphPhase().getValueA().size();

        }else if (baseMeterData instanceof MeterThreeHarmonic){
            return 5 + ((MeterThreeHarmonic) baseMeterData).getListA_Phase().getValueA().size() + ((MeterThreeHarmonic) baseMeterData).getListV_Phase().getValueA().size();
        }else if (baseMeterData instanceof MeterHz_400){
            return 4;
        }
        return 0;
    }

    private static  String Res2String(Context context,int resID){
        return  context.getResources().getString(resID);
    }


    /*----------------------------新协议对象--------------------------------*/

    private static int newgetPhaseObjCount(Context context,int modeType,String checkParameters,List<SqlMeterData> dataList,int startIndex){
        String[] split = checkParameters.split(",");
        int count = 0;
        switch (modeType){
            case SqlApi.Mode_Record_VoltAmp:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_Urms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vrms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Arms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Ucf)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vcf)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Acf)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Upeak1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Upeak2)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vpeak1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vpeak2)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Apeak1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Apeak2)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Udc)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vdc)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Adc)))
                        count += 4;
                }
                break;

            case SqlApi.Mode_Record_Flicker:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_pstmin)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_pst)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_plt)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_pinst)))
                        count += 4;

                }

                break;
            case SqlApi.Mode_Record_Frequency:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_pstmin)))
                        count += 4;
                }
                break;
            case SqlApi.Mode_Record_VoltHarmonic:
                for (int i = 0; i < split.length; i++) {
                    count += 4;
                    /*if (split[i].equals(Res2String(context,R.string.parameter_Vfund)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Irms)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Freq)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Uthd)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Ithd)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Uthd2_50)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.Ithd2_50)))
                        count += 4;*/

                }

                break;
            case SqlApi.Mode_Record_AmpHarmonic:
                for (int i = 0; i < split.length; i++) {
                    count += 4;
                    /*if (split[i].equals(Res2String(context,R.string.parameter_Afund)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_thdf)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_thdr)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_DC)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_amp_H1)))
                        count += 4;*/


                }

                break;
            case SqlApi.Mode_Record_Unbalance:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_Unbal)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vneg)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Vzero)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Aneg)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_Azero)))
                        count += 4;
                }
                break;
            case SqlApi.Mode_Record_Power:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_kW)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kVA)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kvar)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kVA_Harm)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_cos)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kVA_Unb)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kW_fund)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kVA_fund)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_W_fund)))
                        count += 4;
                }
                break;

            case SqlApi.Mode_Record_Energy:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(context,R.string.parameter_kVAh)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kvarh)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kWh_forw)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kWh_rev)))
                        count += 4;
                    else if (split[i].equals(Res2String(context,R.string.parameter_kWh)))
                        count += 4;
                }
                break;
        }
        return count;
    }

    public static List<BaseData> newSqlDataBean2BaseData(Context context, SqlDataBean sqlDataBean){
        List<BaseData> baseDataList = null;
        //所有点数的集合
        List<SqlMeterData> dataList = sqlDataBean.getDataList();
        //一条数据构成的点数
        int phaseObjCount = newgetPhaseObjCount(context, sqlDataBean.modeType, sqlDataBean.getCheckParameters(),dataList,0);
        int listIndex = 0;
        List<List<SqlMeterData>> lists = new ArrayList<List<SqlMeterData>>();

        while (listIndex + phaseObjCount <= dataList.size()){
            lists.add(dataList.subList(listIndex,listIndex + phaseObjCount));
            listIndex = listIndex + phaseObjCount;
            phaseObjCount = newgetPhaseObjCount(context, sqlDataBean.modeType, sqlDataBean.getCheckParameters(),dataList,listIndex);
        }

        long startTime = System.currentTimeMillis();
        switch (sqlDataBean.getModeType()){
            case SqlApi.Mode_Record_VoltAmp:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterVoltAmp dataMeterVoltAmp = new DataMeterVoltAmp();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Urms){
                            dataMeterVoltAmp.setUrmsValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vrms){
                            dataMeterVoltAmp.setVrmsValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Arms){
                            dataMeterVoltAmp.setArmsValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Udc){
                            dataMeterVoltAmp.setUdcValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vdc){
                            dataMeterVoltAmp.setVdcValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Adc){
                            dataMeterVoltAmp.setAdcValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Ucf){
                            dataMeterVoltAmp.setUcfValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vcf){
                            dataMeterVoltAmp.setVcfValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Acf){
                            dataMeterVoltAmp.setAcfValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Upeakjia){
                            dataMeterVoltAmp.setUpeakUpValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vpeakjia){
                            dataMeterVoltAmp.setVpeakUpValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Apeakjia){
                            dataMeterVoltAmp.setApeakUpValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Upeakjian){
                            dataMeterVoltAmp.setUpeakDownValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vpeakjian){
                            dataMeterVoltAmp.setVpeakDownValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Apeakjian){
                            dataMeterVoltAmp.setApeakDownValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }
                    }
                    baseDataList.add(dataMeterVoltAmp);
                }
                break;
            case SqlApi.Mode_Record_Flicker:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterFlicker dataMeterFlicker = new DataMeterFlicker();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pst1min){
                            dataMeterFlicker.setPstMinValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pst){
                            dataMeterFlicker.setPstValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Plt){
                            dataMeterFlicker.setPltValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pinst){
                            dataMeterFlicker.setPinstValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }
                    }
                    baseDataList.add(dataMeterFlicker);
                }
                break;
            case SqlApi.Mode_Record_Frequency:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterFrequency dataMeterFrequency = new DataMeterFrequency();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Hz){
                            dataMeterFrequency.setHzValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }
                    }
                    baseDataList.add(dataMeterFrequency);
                }
                break;
            case SqlApi.Mode_Record_VoltHarmonic:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterVoltHarmonic dataMeterVoltHarmonic = new DataMeterVoltHarmonic();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vfund){
                            dataMeterVoltHarmonic.setvFundValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltThdf){
                            dataMeterVoltHarmonic.setThdfValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltThdr){
                            dataMeterVoltHarmonic.setThdrValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltDc){
                            dataMeterVoltHarmonic.setThdrValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH1){
                            dataMeterVoltHarmonic.setVolth1Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH2){
                            dataMeterVoltHarmonic.setVolth2Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH3){
                            dataMeterVoltHarmonic.setVolth3Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH4){
                            dataMeterVoltHarmonic.setVolth4Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH5){
                            dataMeterVoltHarmonic.setVolth5Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH6){
                            dataMeterVoltHarmonic.setVolth6Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH7){
                            dataMeterVoltHarmonic.setVolth7Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH8){
                            dataMeterVoltHarmonic.setVolth8Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH9){
                            dataMeterVoltHarmonic.setVolth9Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH10){
                            dataMeterVoltHarmonic.setVolth10Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH11){
                            dataMeterVoltHarmonic.setVolth11Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH12){
                            dataMeterVoltHarmonic.setVolth12Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH13){
                            dataMeterVoltHarmonic.setVolth13Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH14){
                            dataMeterVoltHarmonic.setVolth14Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH15){
                            dataMeterVoltHarmonic.setVolth15Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH16){
                            dataMeterVoltHarmonic.setVolth16Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH17){
                            dataMeterVoltHarmonic.setVolth17Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH18){
                            dataMeterVoltHarmonic.setVolth18Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH19){
                            dataMeterVoltHarmonic.setVolth19Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH20){
                            dataMeterVoltHarmonic.setVolth20Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH21){
                            dataMeterVoltHarmonic.setVolth21Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH22){
                            dataMeterVoltHarmonic.setVolth22Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH23){
                            dataMeterVoltHarmonic.setVolth23Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH24){
                            dataMeterVoltHarmonic.setVolth24Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH25){
                            dataMeterVoltHarmonic.setVolth25Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH26){
                            dataMeterVoltHarmonic.setVolth26Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH27){
                            dataMeterVoltHarmonic.setVolth27Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH28){
                            dataMeterVoltHarmonic.setVolth28Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH29){
                            dataMeterVoltHarmonic.setVolth29Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH30){
                            dataMeterVoltHarmonic.setVolth30Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH31){
                            dataMeterVoltHarmonic.setVolth31Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH32){
                            dataMeterVoltHarmonic.setVolth32Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH33){
                            dataMeterVoltHarmonic.setVolth33Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH34){
                            dataMeterVoltHarmonic.setVolth34Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH35){
                            dataMeterVoltHarmonic.setVolth35Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH36){
                            dataMeterVoltHarmonic.setVolth36Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH37){
                            dataMeterVoltHarmonic.setVolth37Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH38){
                            dataMeterVoltHarmonic.setVolth38Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH39){
                            dataMeterVoltHarmonic.setVolth39Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH40){
                            dataMeterVoltHarmonic.setVolth40Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH41){
                            dataMeterVoltHarmonic.setVolth41Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH42){
                            dataMeterVoltHarmonic.setVolth42Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH43){
                            dataMeterVoltHarmonic.setVolth43Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH44){
                            dataMeterVoltHarmonic.setVolth44Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH45){
                            dataMeterVoltHarmonic.setVolth45Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH46){
                            dataMeterVoltHarmonic.setVolth46Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH47){
                            dataMeterVoltHarmonic.setVolth47Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH48){
                            dataMeterVoltHarmonic.setVolth48Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH49){
                            dataMeterVoltHarmonic.setVolth49Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_VoltH50){
                            dataMeterVoltHarmonic.setVolth50Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;

                        }
                    }
                    baseDataList.add(dataMeterVoltHarmonic);
                }
                break;
            case SqlApi.Mode_Record_AmpHarmonic:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterAmpHarmonic dataMeterAmpHarmonic = new DataMeterAmpHarmonic();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Afund){
                            dataMeterAmpHarmonic.setaFundValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpThdf){
                            dataMeterAmpHarmonic.setThdfValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpThdr){
                            dataMeterAmpHarmonic.setThdrValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpDc){
                            dataMeterAmpHarmonic.setAmpDcValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH1){
                            dataMeterAmpHarmonic.setAmph1Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH2){
                            dataMeterAmpHarmonic.setAmph2Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH3){
                            dataMeterAmpHarmonic.setAmph3Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH4){
                            dataMeterAmpHarmonic.setAmph4Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH5){
                            dataMeterAmpHarmonic.setAmph5Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH6){
                            dataMeterAmpHarmonic.setAmph6Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH7){
                            dataMeterAmpHarmonic.setAmph7Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH8){
                            dataMeterAmpHarmonic.setAmph8Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH9){
                            dataMeterAmpHarmonic.setAmph9Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH10){
                            dataMeterAmpHarmonic.setAmph10Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH11){
                            dataMeterAmpHarmonic.setAmph11Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH12){
                            dataMeterAmpHarmonic.setAmph12Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH13){
                            dataMeterAmpHarmonic.setAmph13Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH14){
                            dataMeterAmpHarmonic.setAmph14Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH15){
                            dataMeterAmpHarmonic.setAmph15Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH16){
                            dataMeterAmpHarmonic.setAmph16Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH17){
                            dataMeterAmpHarmonic.setAmph17Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH18){
                            dataMeterAmpHarmonic.setAmph18Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH19){
                            dataMeterAmpHarmonic.setAmph19Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH20){
                            dataMeterAmpHarmonic.setAmph20Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH21){
                            dataMeterAmpHarmonic.setAmph21Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH22){
                            dataMeterAmpHarmonic.setAmph22Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH23){
                            dataMeterAmpHarmonic.setAmph23Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH24){
                            dataMeterAmpHarmonic.setAmph24Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH25){
                            dataMeterAmpHarmonic.setAmph25Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH26){
                            dataMeterAmpHarmonic.setAmph26Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH27){
                            dataMeterAmpHarmonic.setAmph27Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH28){
                            dataMeterAmpHarmonic.setAmph28Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH29){
                            dataMeterAmpHarmonic.setAmph29Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH30){
                            dataMeterAmpHarmonic.setAmph30Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH31){
                            dataMeterAmpHarmonic.setAmph31Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH32){
                            dataMeterAmpHarmonic.setAmph32Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH33){
                            dataMeterAmpHarmonic.setAmph33Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH34){
                            dataMeterAmpHarmonic.setAmph34Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH35){
                            dataMeterAmpHarmonic.setAmph35Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH36){
                            dataMeterAmpHarmonic.setAmph36Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH37){
                            dataMeterAmpHarmonic.setAmph37Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH38){
                            dataMeterAmpHarmonic.setAmph38Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH39){
                            dataMeterAmpHarmonic.setAmph39Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH40){
                            dataMeterAmpHarmonic.setAmph40Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH41){
                            dataMeterAmpHarmonic.setAmph41Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH42){
                            dataMeterAmpHarmonic.setAmph42Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH43){
                            dataMeterAmpHarmonic.setAmph43Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH44){
                            dataMeterAmpHarmonic.setAmph44Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH45){
                            dataMeterAmpHarmonic.setAmph45Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH46){
                            dataMeterAmpHarmonic.setAmph46Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH47){
                            dataMeterAmpHarmonic.setAmph47Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH48){
                            dataMeterAmpHarmonic.setAmph48Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH49){
                            dataMeterAmpHarmonic.setAmph49Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_AmpH50){
                            dataMeterAmpHarmonic.setAmph50Value(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }
                    }
                    baseDataList.add(dataMeterAmpHarmonic);
                }
                break;
            case SqlApi.Mode_Record_Unbalance:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterUnbalanced dataMeterUnbalanced = new DataMeterUnbalanced();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Unbal){
                            dataMeterUnbalanced.setUnbalValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vneg){
                            dataMeterUnbalanced.setVnegValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Aneg){
                            dataMeterUnbalanced.setAnegValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vzero){
                            dataMeterUnbalanced.setVzerolValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Azero){
                            dataMeterUnbalanced.setAzeroValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }

                    }
                    baseDataList.add(dataMeterUnbalanced);
                }
                break;
            case SqlApi.Mode_Record_Power:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterPower dataMeterPower = new DataMeterPower();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kW){
                            dataMeterPower.setKwValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVA){
                            dataMeterPower.setKvaValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kvar){
                            dataMeterPower.setKvarValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVAHarm){
                            dataMeterPower.setKvaHarmValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_cos){
                            dataMeterPower.setCosValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVAUnb){
                            dataMeterPower.setKvaUnbValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kWfund){
                            dataMeterPower.setKwFundValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVAfund){
                            dataMeterPower.setKvaFundValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Wfund){
                            dataMeterPower.setwFundValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;

                        }

                    }
                    baseDataList.add(dataMeterPower);
                }
                break;
            case SqlApi.Mode_Record_Energy:
                baseDataList = new ArrayList<>();
                for (int i = 0; i <lists.size() ; i++) {
                    DataMeterEnergy dataMeterEnergy = new DataMeterEnergy();
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVAh){
                            dataMeterEnergy.setkVahValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kvarh){
                            dataMeterEnergy.setkVarhValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kWhForw){
                            dataMeterEnergy.setKwhForwValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kWhRev){
                            dataMeterEnergy.setKwhRevValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kWh){
                            dataMeterEnergy.setKwhValue(parseObjN(sqlMeterDataList,j));
                            j = j + 3;
                        }
                    }
                    baseDataList.add(dataMeterEnergy);

                }
                break;
        }

        //转换完释放状态
        MeterDataPool.recycle(sqlDataBean);
        log.e("转换时间 : " + (System.currentTimeMillis() - startTime));
        return baseDataList;
    }


    private static int toSqlVoltParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_Urms))){
            parameterMode = SqlApi.Parameter_Record_Urms;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vrms))){
            parameterMode = SqlApi.Parameter_Record_Vrms;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Arms))){
            parameterMode = SqlApi.Parameter_Record_Arms;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Ucf))){
            parameterMode = SqlApi.Parameter_Record_Ucf;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vcf))){
            parameterMode = SqlApi.Parameter_Record_Vcf;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Acf))){
            parameterMode = SqlApi.Parameter_Record_Acf;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Upeak1))){
            parameterMode = SqlApi.Parameter_Record_Upeakjia;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Upeak2))){
            parameterMode = SqlApi.Parameter_Record_Upeakjian;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vpeak1))){
            parameterMode = SqlApi.Parameter_Record_Vpeakjia;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vpeak2))){
            parameterMode = SqlApi.Parameter_Record_Vpeakjian;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Apeak1))){
            parameterMode = SqlApi.Parameter_Record_Apeakjia;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Apeak2))){
            parameterMode = SqlApi.Parameter_Record_Apeakjian;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Udc))){
            parameterMode = SqlApi.Parameter_Record_Udc;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vdc))){
            parameterMode = SqlApi.Parameter_Record_Vdc;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Adc))){
            parameterMode = SqlApi.Parameter_Record_Adc;
        }

        return parameterMode;
    }
    private static int toSqlFlickerParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_pstmin))){
            parameterMode = SqlApi.Parameter_Record_Pst1min;
        }else if (parameter.equals(Res2String(context, R.string.parameter_pst))){
            parameterMode = SqlApi.Parameter_Record_Pst;
        }else if (parameter.equals(Res2String(context, R.string.parameter_plt))){
            parameterMode = SqlApi.Parameter_Record_Plt;
        }else if (parameter.equals(Res2String(context, R.string.parameter_pinst))){
            parameterMode = SqlApi.Parameter_Record_Pinst;
        }

        return parameterMode;
    }
    private static int toSqlUnbalanceParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_Unbal))){
            parameterMode = SqlApi.Parameter_Record_Unbal;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vneg))){
            parameterMode = SqlApi.Parameter_Record_Vneg;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Vzero))){
            parameterMode = SqlApi.Parameter_Record_Vzero;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Aneg))){
            parameterMode = SqlApi.Parameter_Record_Aneg;
        }else if (parameter.equals(Res2String(context, R.string.parameter_Azero))){
            parameterMode = SqlApi.Parameter_Record_Azero;
        }

        return parameterMode;
    }
    private static int toSqlPowerParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_kW))){
            parameterMode = SqlApi.Parameter_Record_kW;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kVA))){
            parameterMode = SqlApi.Parameter_Record_kVA;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kvar))){
            parameterMode = SqlApi.Parameter_Record_kvar;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kVA_Harm))){
            parameterMode = SqlApi.Parameter_Record_kVAHarm;
        }else if (parameter.equals(Res2String(context, R.string.parameter_cos))){
            parameterMode = SqlApi.Parameter_Record_cos;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kVA_Unb))){
            parameterMode = SqlApi.Parameter_Record_kVAUnb;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kW_fund))){
            parameterMode = SqlApi.Parameter_Record_kWfund;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kVA_fund))){
            parameterMode = SqlApi.Parameter_Record_kVAfund;
        }else if (parameter.equals(Res2String(context, R.string.parameter_W_fund))){
            parameterMode = SqlApi.Parameter_Record_Wfund;
        }
        return parameterMode;
    }
    private static int toSqlEnergyParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_kVAh))){
            parameterMode = SqlApi.Parameter_Record_kVAh;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kWh_forw))){
            parameterMode = SqlApi.Parameter_Record_kWhForw;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kWh))){
            parameterMode = SqlApi.Parameter_Record_kWh;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kvarh))){
            parameterMode = SqlApi.Parameter_Record_kvarh;
        }else if (parameter.equals(Res2String(context, R.string.parameter_kWh_rev))){
            parameterMode = SqlApi.Parameter_Record_kWhRev;
        }

        return parameterMode;
    }
    private static int toSqlVoltHarmParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_Vfund))){
            parameterMode = SqlApi.Parameter_Record_Vfund;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_thdf))){
            parameterMode = SqlApi.Parameter_Record_VoltThdf;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_thdr))){
            parameterMode = SqlApi.Parameter_Record_VoltThdr;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_DC))){
            parameterMode = SqlApi.Parameter_Record_VoltDc;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H1))){
            parameterMode = SqlApi.Parameter_Record_VoltH1;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H2))){
            parameterMode = SqlApi.Parameter_Record_VoltH2;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H3))){
            parameterMode = SqlApi.Parameter_Record_VoltH3;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H4))){
            parameterMode = SqlApi.Parameter_Record_VoltH4;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H5))){
            parameterMode = SqlApi.Parameter_Record_VoltH5;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H6))){
            parameterMode = SqlApi.Parameter_Record_VoltH6;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H7))){
            parameterMode = SqlApi.Parameter_Record_VoltH7;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H8))){
            parameterMode = SqlApi.Parameter_Record_VoltH8;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H9))){
            parameterMode = SqlApi.Parameter_Record_VoltH9;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H10))){
            parameterMode = SqlApi.Parameter_Record_VoltH10;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H11))){
            parameterMode = SqlApi.Parameter_Record_VoltH11;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H12))){
            parameterMode = SqlApi.Parameter_Record_VoltH12;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H13))){
            parameterMode = SqlApi.Parameter_Record_VoltH13;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H14))){
            parameterMode = SqlApi.Parameter_Record_VoltH14;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H15))){
            parameterMode = SqlApi.Parameter_Record_VoltH15;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H16))){
            parameterMode = SqlApi.Parameter_Record_VoltH16;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H17))){
            parameterMode = SqlApi.Parameter_Record_VoltH17;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H18))){
            parameterMode = SqlApi.Parameter_Record_VoltH18;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H19))){
            parameterMode = SqlApi.Parameter_Record_VoltH19;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H20))){
            parameterMode = SqlApi.Parameter_Record_VoltH20;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H21))){
            parameterMode = SqlApi.Parameter_Record_VoltH21;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H22))){
            parameterMode = SqlApi.Parameter_Record_VoltH22;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H23))){
            parameterMode = SqlApi.Parameter_Record_VoltH23;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H24))){
            parameterMode = SqlApi.Parameter_Record_VoltH24;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H25))){
            parameterMode = SqlApi.Parameter_Record_VoltH25;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H26))){
            parameterMode = SqlApi.Parameter_Record_VoltH26;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H27))){
            parameterMode = SqlApi.Parameter_Record_VoltH27;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H28))){
            parameterMode = SqlApi.Parameter_Record_VoltH28;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H29))){
            parameterMode = SqlApi.Parameter_Record_VoltH29;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H30))){
            parameterMode = SqlApi.Parameter_Record_VoltH30;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H31))){
            parameterMode = SqlApi.Parameter_Record_VoltH31;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H32))){
            parameterMode = SqlApi.Parameter_Record_VoltH32;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H33))){
            parameterMode = SqlApi.Parameter_Record_VoltH33;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H34))){
            parameterMode = SqlApi.Parameter_Record_VoltH34;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H35))){
            parameterMode = SqlApi.Parameter_Record_VoltH35;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H36))){
            parameterMode = SqlApi.Parameter_Record_VoltH36;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H37))){
            parameterMode = SqlApi.Parameter_Record_VoltH37;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H38))){
            parameterMode = SqlApi.Parameter_Record_VoltH38;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H39))){
            parameterMode = SqlApi.Parameter_Record_VoltH39;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H40))){
            parameterMode = SqlApi.Parameter_Record_VoltH40;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H41))){
            parameterMode = SqlApi.Parameter_Record_VoltH41;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H42))){
            parameterMode = SqlApi.Parameter_Record_VoltH42;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H43))){
            parameterMode = SqlApi.Parameter_Record_VoltH43;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H44))){
            parameterMode = SqlApi.Parameter_Record_VoltH44;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H45))){
            parameterMode = SqlApi.Parameter_Record_VoltH45;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H46))){
            parameterMode = SqlApi.Parameter_Record_VoltH46;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H47))){
            parameterMode = SqlApi.Parameter_Record_VoltH47;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H48))){
            parameterMode = SqlApi.Parameter_Record_VoltH48;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H49))){
            parameterMode = SqlApi.Parameter_Record_VoltH49;
        }else if (parameter.equals(Res2String(context, R.string.parameter_volt_H50))){
            parameterMode = SqlApi.Parameter_Record_VoltH50;
        }

        return parameterMode;
    }
    private static int toSqlAmpHarmParameter(Context context,String parameter) {
        int parameterMode =-1;
        if (parameter.equals(Res2String(context, R.string.parameter_Afund))){
            parameterMode = SqlApi.Parameter_Record_Afund;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_thdf))){
            parameterMode = SqlApi.Parameter_Record_AmpThdf;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_thdr))){
            parameterMode = SqlApi.Parameter_Record_AmpThdr;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_DC))){
            parameterMode = SqlApi.Parameter_Record_AmpDc;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H1))){
            parameterMode = SqlApi.Parameter_Record_AmpH1;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H2))){
            parameterMode = SqlApi.Parameter_Record_AmpH2;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H3))){
            parameterMode = SqlApi.Parameter_Record_AmpH3;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H4))){
            parameterMode = SqlApi.Parameter_Record_AmpH4;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H5))){
            parameterMode = SqlApi.Parameter_Record_AmpH5;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H6))){
            parameterMode = SqlApi.Parameter_Record_AmpH6;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H7))){
            parameterMode = SqlApi.Parameter_Record_AmpH7;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H8))){
            parameterMode = SqlApi.Parameter_Record_AmpH8;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H9))){
            parameterMode = SqlApi.Parameter_Record_AmpH9;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H10))){
            parameterMode = SqlApi.Parameter_Record_AmpH10;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H11))){
            parameterMode = SqlApi.Parameter_Record_AmpH11;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H12))){
            parameterMode = SqlApi.Parameter_Record_AmpH12;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H13))){
            parameterMode = SqlApi.Parameter_Record_AmpH13;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H14))){
            parameterMode = SqlApi.Parameter_Record_AmpH14;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H15))){
            parameterMode = SqlApi.Parameter_Record_AmpH15;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H16))){
            parameterMode = SqlApi.Parameter_Record_AmpH16;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H17))){
            parameterMode = SqlApi.Parameter_Record_AmpH17;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H18))){
            parameterMode = SqlApi.Parameter_Record_AmpH18;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H19))){
            parameterMode = SqlApi.Parameter_Record_AmpH19;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H20))){
            parameterMode = SqlApi.Parameter_Record_AmpH20;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H21))){
            parameterMode = SqlApi.Parameter_Record_AmpH21;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H22))){
            parameterMode = SqlApi.Parameter_Record_AmpH22;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H23))){
            parameterMode = SqlApi.Parameter_Record_AmpH23;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H24))){
            parameterMode = SqlApi.Parameter_Record_AmpH24;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H25))){
            parameterMode = SqlApi.Parameter_Record_AmpH25;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H26))){
            parameterMode = SqlApi.Parameter_Record_AmpH26;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H27))){
            parameterMode = SqlApi.Parameter_Record_AmpH27;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H28))){
            parameterMode = SqlApi.Parameter_Record_AmpH28;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H29))){
            parameterMode = SqlApi.Parameter_Record_AmpH29;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H30))){
            parameterMode = SqlApi.Parameter_Record_AmpH30;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H31))){
            parameterMode = SqlApi.Parameter_Record_AmpH31;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H32))){
            parameterMode = SqlApi.Parameter_Record_AmpH32;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H33))){
            parameterMode = SqlApi.Parameter_Record_AmpH33;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H34))){
            parameterMode = SqlApi.Parameter_Record_AmpH34;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H35))){
            parameterMode = SqlApi.Parameter_Record_AmpH35;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H36))){
            parameterMode = SqlApi.Parameter_Record_AmpH36;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H37))){
            parameterMode = SqlApi.Parameter_Record_AmpH37;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H38))){
            parameterMode = SqlApi.Parameter_Record_AmpH38;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H39))){
            parameterMode = SqlApi.Parameter_Record_AmpH39;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H40))){
            parameterMode = SqlApi.Parameter_Record_AmpH40;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H41))){
            parameterMode = SqlApi.Parameter_Record_AmpH41;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H42))){
            parameterMode = SqlApi.Parameter_Record_AmpH42;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H43))){
            parameterMode = SqlApi.Parameter_Record_AmpH43;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H44))){
            parameterMode = SqlApi.Parameter_Record_AmpH44;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H45))){
            parameterMode = SqlApi.Parameter_Record_AmpH45;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H46))){
            parameterMode = SqlApi.Parameter_Record_AmpH46;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H47))){
            parameterMode = SqlApi.Parameter_Record_AmpH47;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H48))){
            parameterMode = SqlApi.Parameter_Record_AmpH48;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H49))){
            parameterMode = SqlApi.Parameter_Record_AmpH49;
        }else if (parameter.equals(Res2String(context, R.string.parameter_amp_H50))){
            parameterMode = SqlApi.Parameter_Record_AmpH50;
        }
        return parameterMode;
    }

    public static List<SqlMeterData> toSqlData(Context context, List<ModelLineData> lineDataArrayList, String checkParameters,int sqlDataBeanModeType) {
        List<SqlMeterData> sqlMeterDataList = new ArrayList<>();
        String[] split = checkParameters.split(",");
        if(split.length>0) {
            for (int i = 0; i < split.length; i++) {
                ModelLineData modelLineData = lineDataArrayList.get(i);
                switch (sqlDataBeanModeType) {
                    case SqlApi.Mode_Record_VoltAmp:
                        List<SqlMeterData> voltampList = possPhaseobj(modelLineData, SqlApi.Mode_Record_VoltAmp, toSqlVoltParameter(context, split[i]));
                        sqlMeterDataList.addAll(voltampList);
                        break;
                    case SqlApi.Mode_Record_Flicker:
                        List<SqlMeterData> flickerList = possPhaseobj(modelLineData, SqlApi.Mode_Record_Flicker, toSqlFlickerParameter(context, split[i]));
                        sqlMeterDataList.addAll(flickerList);
                        break;
                    case SqlApi.Mode_Record_Frequency:
                        List<SqlMeterData> freqList = possPhaseobj(modelLineData, SqlApi.Mode_Record_Frequency, SqlApi.Parameter_Record_Hz);
                        sqlMeterDataList.addAll(freqList);
                        break;
                    case SqlApi.Mode_Record_VoltHarmonic:
                        List<SqlMeterData> voltHarmList = possPhaseobj(modelLineData, SqlApi.Mode_Record_VoltHarmonic, toSqlVoltHarmParameter(context, split[i]));
                        sqlMeterDataList.addAll(voltHarmList);
                        break;
                    case SqlApi.Mode_Record_AmpHarmonic:
                        List<SqlMeterData> ampHarmList = possPhaseobj(modelLineData, SqlApi.Mode_Record_AmpHarmonic, toSqlAmpHarmParameter(context, split[i]));
                        sqlMeterDataList.addAll(ampHarmList);
                        break;
                    case SqlApi.Mode_Record_Unbalance:
                        List<SqlMeterData> unbalanceList = possPhaseobj(modelLineData, SqlApi.Mode_Record_Unbalance, toSqlUnbalanceParameter(context, split[i]));
                        sqlMeterDataList.addAll(unbalanceList);
                        break;
                    case SqlApi.Mode_Record_Power:
                        List<SqlMeterData> powerList = possPhaseobj(modelLineData, SqlApi.Mode_Record_Power, toSqlPowerParameter(context, split[i]));
                        sqlMeterDataList.addAll(powerList);
                        break;
                    case SqlApi.Mode_Record_Energy:
                        List<SqlMeterData> energyList = possPhaseobj(modelLineData, SqlApi.Mode_Record_Energy, toSqlEnergyParameter(context, split[i]));
                        sqlMeterDataList.addAll(energyList);
                        break;
                }
            }
        }
        return sqlMeterDataList;
    }

    private static List<SqlMeterData> possPhaseobj(ModelLineData phaseObj,int modeTyoe,int parameterType){
        List<SqlMeterData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SqlMeterData sqlMeterData = MeterDataPool.obtainMeter();
            sqlMeterData.time = new Date();
            sqlMeterData.setModeType(modeTyoe);
            sqlMeterData.setParameterType(parameterType);
            if (i == 0){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_A);
                sqlMeterData.setValue(phaseObj.getaValue().getValueFl());
                sqlMeterData.setUnit(phaseObj.getaValue().getValue_Unit());
                sqlMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 1){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_B);
                sqlMeterData.setValue(phaseObj.getbValue().getValueFl());
                sqlMeterData.setUnit(phaseObj.getbValue().getValue_Unit());
                sqlMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 2){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_C);
                sqlMeterData.setValue(phaseObj.getcValue().getValueFl());
                sqlMeterData.setUnit(phaseObj.getcValue().getValue_Unit());
                sqlMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if(i == 3){
                sqlMeterData.setPhaseType(SqlApi.PhaseType_N);
                sqlMeterData.setValue(phaseObj.getnValue().getValueFl());
                sqlMeterData.setUnit(phaseObj.getnValue().getValue_Unit());
                sqlMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }
            list.add(sqlMeterData);
        }
        return list;

    }


    /*-------------InrushMeterData-------------*/

    public static List<InrushMeterData> toInrushData(Context context, List<ModelLineData> lineDataArrayList, String checkParameters,int inrushDataBeanModeType) {
        List<InrushMeterData> inrushMeterDataList = new ArrayList<>();
        String[] split = checkParameters.split(",");
        if(split.length>0) {
            for (int i = 0; i < lineDataArrayList.size(); i++) {
                ModelLineData modelLineData = lineDataArrayList.get(i);
                switch (inrushDataBeanModeType) {
                    case SqlApi.Inrush_History_Arms:
                        List<InrushMeterData> armsList = possInrushPhaseobj(modelLineData, SqlApi.Mode_Inrush, SqlApi.Inrush_History_Arms);
                        inrushMeterDataList.addAll(armsList);
                        break;
                    case SqlApi.Inrush_History_Vrms:
                        List<InrushMeterData> vrmsList = possInrushPhaseobj(modelLineData, SqlApi.Mode_Inrush, SqlApi.Inrush_History_Vrms);
                        inrushMeterDataList.addAll(vrmsList);
                        break;
                }
            }
        }
        return inrushMeterDataList;
    }

    private static List<InrushMeterData> possInrushPhaseobj(ModelLineData phaseObj,int modeTyoe,int parameterType){
        List<InrushMeterData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            InrushMeterData inrushMeterData = MeterDataPool.obtainInrushMeter();
            inrushMeterData.time = new Date();
            inrushMeterData.setModeType(modeTyoe);
            inrushMeterData.setParameterType(parameterType);
            if (i == 0){
                inrushMeterData.setPhaseType(SqlApi.PhaseType_A);
                inrushMeterData.setValue(phaseObj.getaValue().getValueFl());
                inrushMeterData.setUnit(phaseObj.getaValue().getValue_Unit());
                inrushMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 1){
                inrushMeterData.setPhaseType(SqlApi.PhaseType_B);
                inrushMeterData.setValue(phaseObj.getbValue().getValueFl());
                inrushMeterData.setUnit(phaseObj.getbValue().getValue_Unit());
                inrushMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 2){
                inrushMeterData.setPhaseType(SqlApi.PhaseType_C);
                inrushMeterData.setValue(phaseObj.getcValue().getValueFl());
                inrushMeterData.setUnit(phaseObj.getcValue().getValue_Unit());
                inrushMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if(i == 3){
                inrushMeterData.setPhaseType(SqlApi.PhaseType_N);
                inrushMeterData.setValue(phaseObj.getnValue().getValueFl());
                inrushMeterData.setUnit(phaseObj.getnValue().getValue_Unit());
                inrushMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }
            list.add(inrushMeterData);
        }
        return list;

    }

    public static List<BaseData> inrushDataBean2BaseData(Context context, InrushDataBean inrushDataBean){
        List<BaseData> baseDataList = null;
        //所有点数的集合
        List<InrushMeterData> dataList = inrushDataBean.getDataList();
        //一条数据构成的点数
        int phaseObjCount = 4;
        int listIndex = 0;
        List<List<InrushMeterData>> lists = new ArrayList<List<InrushMeterData>>();
        while (listIndex + phaseObjCount <= dataList.size()){
            lists.add(dataList.subList(listIndex,listIndex + phaseObjCount));
            listIndex += 4;;
        }

        long startTime = System.currentTimeMillis();
        baseDataList = new ArrayList<>();
        for (int i = 0; i <lists.size() ; i++) {
            DataMeterInrush dataMeterInrush = new DataMeterInrush();
            List<InrushMeterData> inrushMeterDataList = lists.get(i);
            for (int j = 0; j < inrushMeterDataList.size(); j++) {
                if (inrushMeterDataList.get(j).getParameterType() == SqlApi.Inrush_History_Vrms){
                    dataMeterInrush.setVrmsValue(parseInrushObjN(inrushMeterDataList,j));
                    j = j + 3;
                }else if (inrushMeterDataList.get(j).getParameterType() == SqlApi.Inrush_History_Arms) {
                    dataMeterInrush.setArmsValue(parseInrushObjN(inrushMeterDataList, j));
                    j = j + 3;
                }
            }
            baseDataList.add(dataMeterInrush);
        }
        //转换完释放状态
        MeterDataPool.recycle(inrushDataBean);
        log.e("转换时间 : " + (System.currentTimeMillis() - startTime));
        return baseDataList;
    }

    private static DataPhaseObjN parseInrushObjN(List<InrushMeterData> sqlMeterDataList,int j){
        InrushMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        InrushMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        InrushMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        InrushMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
        DataPhaseObjN objN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
        return objN;
    }
    /*------------------DipsSwellsMeterData-------------------*/
    public static List<DipsSwellsMeterData> toDipsSwellsData(Context context, List<ModelLineData> lineDataArrayList, String checkParameters, int dipsSwellsDataBeanModeType) {
        List<DipsSwellsMeterData> dipsSwellsMeterDataList = new ArrayList<>();
        String[] split = checkParameters.split(",");
        if(split.length>0) {
            for (int i = 0; i < lineDataArrayList.size(); i++) {
                ModelLineData modelLineData = lineDataArrayList.get(i);
                switch (dipsSwellsDataBeanModeType) {
                    case SqlApi.DipsSwells_History_Arms:
                        List<DipsSwellsMeterData> armsList = possDipsSwellsPhaseobj(modelLineData, SqlApi.Mode_Dip, SqlApi.DipsSwells_History_Arms);
                        dipsSwellsMeterDataList.addAll(armsList);
                        break;
                    case SqlApi.DipsSwells_History_Vrms:
                        List<DipsSwellsMeterData> vrmsList = possDipsSwellsPhaseobj(modelLineData, SqlApi.Mode_Dip, SqlApi.DipsSwells_History_Vrms);
                        dipsSwellsMeterDataList.addAll(vrmsList);
                        break;
                }
            }
        }
        return dipsSwellsMeterDataList;
    }

    private static List<DipsSwellsMeterData> possDipsSwellsPhaseobj(ModelLineData phaseObj,int modeTyoe,int parameterType){
        List<DipsSwellsMeterData> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DipsSwellsMeterData dipsSwellsMeterData = MeterDataPool.obtainDipsSwellsMeter();
            dipsSwellsMeterData.time = new Date();
            dipsSwellsMeterData.setModeType(modeTyoe);
            dipsSwellsMeterData.setParameterType(parameterType);
            if (i == 0){
                dipsSwellsMeterData.setPhaseType(SqlApi.PhaseType_A);
                dipsSwellsMeterData.setValue(phaseObj.getaValue().getValueFl());
                dipsSwellsMeterData.setUnit(phaseObj.getaValue().getValue_Unit());
                dipsSwellsMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 1){
                dipsSwellsMeterData.setPhaseType(SqlApi.PhaseType_B);
                dipsSwellsMeterData.setValue(phaseObj.getbValue().getValueFl());
                dipsSwellsMeterData.setUnit(phaseObj.getbValue().getValue_Unit());
                dipsSwellsMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if (i == 2){
                dipsSwellsMeterData.setPhaseType(SqlApi.PhaseType_C);
                dipsSwellsMeterData.setValue(phaseObj.getcValue().getValueFl());
                dipsSwellsMeterData.setUnit(phaseObj.getcValue().getValue_Unit());
                dipsSwellsMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }else if(i == 3){
                dipsSwellsMeterData.setPhaseType(SqlApi.PhaseType_N);
                dipsSwellsMeterData.setValue(phaseObj.getnValue().getValueFl());
                dipsSwellsMeterData.setUnit(phaseObj.getnValue().getValue_Unit());
                dipsSwellsMeterData.setDataCount(phaseObj.getaValue().dataPoint);
            }
            list.add(dipsSwellsMeterData);
        }
        return list;

    }

    public static List<BaseData> dipsSwellsDataBean2BaseData(Context context, DipsSwellsDataBean dipsSwellsDataBean){
        List<BaseData> baseDataList = null;
        //所有点数的集合
        List<DipsSwellsMeterData> dataList = dipsSwellsDataBean.getDataList();
        log.e("-----------" + dataList.size());
        //一条数据构成的点数
        int phaseObjCount = 4;
        int listIndex = 0;
        List<List<DipsSwellsMeterData>> lists = new ArrayList<>();
        while (listIndex + phaseObjCount <= dataList.size()){
            lists.add(dataList.subList(listIndex,listIndex + phaseObjCount));
            listIndex += 4;;
        }

        long startTime = System.currentTimeMillis();
        baseDataList = new ArrayList<>();
        for (int i = 0; i <lists.size() ; i++) {
            DataMeterDipsSwells dataMeterDipsSwells = new DataMeterDipsSwells();
            List<DipsSwellsMeterData> dipsSwellsMeterDataList = lists.get(i);
            for (int j = 0; j < dipsSwellsMeterDataList.size(); j++) {
                if (dipsSwellsMeterDataList.get(j).getParameterType() == SqlApi.DipsSwells_History_Vrms){
                    dataMeterDipsSwells.setVrmsValue(parseDipsSwellsObjN(dipsSwellsMeterDataList,j));
                    j = j + 3;
                }else if (dipsSwellsMeterDataList.get(j).getParameterType() == SqlApi.DipsSwells_History_Arms) {
                    dataMeterDipsSwells.setArmsValue(parseDipsSwellsObjN(dipsSwellsMeterDataList, j));
                    j = j + 3;
                }
            }
            baseDataList.add(dataMeterDipsSwells);
        }
        //转换完释放状态
        MeterDataPool.recycle(dipsSwellsDataBean);
        log.e("转换时间 : " + (System.currentTimeMillis() - startTime));
        return baseDataList;
    }

    private static DataPhaseObjN parseDipsSwellsObjN(List<DipsSwellsMeterData> sqlMeterDataList,int j){
        DipsSwellsMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        DipsSwellsMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        DipsSwellsMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        DipsSwellsMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
        DataPhaseObjN objN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
        return objN;
    }



}
