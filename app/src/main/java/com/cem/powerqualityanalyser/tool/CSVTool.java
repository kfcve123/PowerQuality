package com.cem.powerqualityanalyser.tool;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.databean.DBManager;
import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.databean.DataPhaseObj;
import com.cem.powerqualityanalyser.databeannew.DataPhaseObjN;
import com.cem.powerqualityanalyser.databean.EventsBean;
import com.cem.powerqualityanalyser.sqlBean.SqlApi;
import com.cem.powerqualityanalyser.sqlBean.SqlDataBean;
import com.cem.powerqualityanalyser.sqlBean.SqlMeterData;

import org.litepal.LitePal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVTool {
    private Context context;
    public static String filePath = Environment.getExternalStorageDirectory().getPath();
    private StringBuilder stringBuilder;
    private String newLine = "\r\n";
    private String slipt = "\t";
    private final String FileHead = "Time" + slipt + "parameter" + slipt + "A" + slipt + "B" + slipt + "C" + slipt + "N" + slipt + "AB" + slipt + "AC" + slipt + "BC" + slipt + "Event";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");// HH:mm:ss
    private  ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private WriteListener writeListener;
    private EventsBean eventsBeanL1,eventsBeanL2,eventsBeanL3,eventsBeanL4,eventsBeanL5,eventsBeanL6;

    private int thresValue,hysterValue,thresValueTwo,hysterValueTwo, thresValue3,hysterValue3;


    public CSVTool(Context context, WriteListener w) {
        this.context = context;
        this.writeListener = w;
        AppConfig config = AppConfig.getInstance();
        int amountValue = config.getDipsSet_Nominal();
        int amountValue2 = config.getDipsSet_Threshold();
        int amountValue3 = config.getDipsSet_Hysteresis();
        int amountValue4 = config.getSwellsSet_Nominal();
        int amountValue5 = config.getSwellsSet_Threshold();
        int amountValue6 = config.getSwellsSet_Hysteresis();

        int amountValue7 = config.getInrushSet_Amps_Norminal();
        int amountValue8 = config.getInrushSet_Threshold();
        int amountValue9 = config.getInrushSet_Hysteresis();

        thresValue = Math.round(amountValue * amountValue2/100f);
        hysterValue = Math.round(amountValue * amountValue3/100f);
        thresValueTwo = Math.round(amountValue4 * amountValue5/100f);
        hysterValueTwo = Math.round(amountValue4 * amountValue6/100f);

        thresValue3 = Math.round(amountValue7 * amountValue8/100f);
        hysterValue3 = Math.round(amountValue7 * amountValue9/100f);
        init();
    }
    private void init(){
        eventsBeanL1 = new EventsBean();
        eventsBeanL2 = new EventsBean();
        eventsBeanL3 = new EventsBean();
        eventsBeanL4 = new EventsBean();
        eventsBeanL5 = new EventsBean();
        eventsBeanL6 = new EventsBean();
        stringBuilder = new StringBuilder();
        stringBuilder.append(FileHead).append(newLine);
    }
    public interface WriteListener{
        void writeFinish();
        void writeFail();
    }

    public void writeCsvFile(final String fileName) {
        init();
        DBManager.getInstance().findFromName(true, fileName, new DBManager.DBQueryAllParameterListener() {
            @Override
            public void onFail() {
                if (writeListener != null){
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            writeListener.writeFail();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(final List<SqlDataBean> dataMeterAllParameterList) {
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            SqlDataBean sqlDataBean = dataMeterAllParameterList.get(0);
                            long wt = System.currentTimeMillis();

                            File file = new File(filePath + File.separator + sqlDataBean.getFileName() + ".csv");
                            if (!file.exists()){
                                file.createNewFile();
                            }
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(file), "Unicode"));
                            long startTime = System.currentTimeMillis();
                            write2SD(sqlDataBean,bw);
                            log.e("转换时间 : " + (System.currentTimeMillis() - startTime));
                            bw.write(stringBuilder.toString());
                            bw.close();
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    writeListener.writeFinish();
                                }
                            });
                            log.e("csv耗时: " + (System.currentTimeMillis() - wt));
                            //转换完释放状态
                            MeterDataPool.recycle(sqlDataBean);
                        } catch (IOException e) {
                            log.e("write Error : " + e.getMessage());
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    if (writeListener != null)
                                        writeListener.writeFail();
                                }
                            });
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



    }


    private String parseL3(DataMeterData L3) {
        //骤降事件开始记录
        if (L3.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL3.getType())){
            eventsBeanL3.setType("Dip");
            eventsBeanL3.setLine("C");
            eventsBeanL3.setStartTime(new Date());
        }else if(L3.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL3.getType())){
            //骤升事件开始记录
            eventsBeanL3.setType("Sweels");
            eventsBeanL3.setLine("C");
            eventsBeanL3.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L3.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL3.getType())){
            eventsBeanL3.setEndTime(new Date());
            String s = eventsBeanL3.getLine() + " " + eventsBeanL3.getType();
            eventsBeanL3.clearself();

            return s;
        }else if (L3.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL3.getType())){
            //骤升事件结束记录
            eventsBeanL3.setEndTime(new Date());
            String s = eventsBeanL3.getLine() + " " + eventsBeanL3.getType();
            eventsBeanL3.clearself();
            return s;
        }
        return "";
    }
    private String parseL2(DataMeterData L2) {
        //骤降事件开始记录
        if (L2.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL2.getType())){
            eventsBeanL2.setType("Dip");
            eventsBeanL2.setLine("B");
            eventsBeanL2.setStartTime(new Date());
        }else if(L2.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL2.getType())){
            //骤升事件开始记录
            eventsBeanL2.setType("Sweels");
            eventsBeanL2.setLine("B");
            eventsBeanL2.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L2.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL2.getType())){
            eventsBeanL2.setEndTime(new Date());
            String s = eventsBeanL2.getLine() + " " + eventsBeanL2.getType();
            eventsBeanL2.clearself();

            return s;
        }else if (L2.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL2.getType())){
            //骤升事件结束记录
            eventsBeanL2.setEndTime(new Date());
            String s = eventsBeanL2.getLine() + " " + eventsBeanL2.getType();
            eventsBeanL2.clearself();
            return s;
        }
        return "";
    }
    private String parseL1(DataMeterData L1){
        //骤降事件开始记录
        if (L1.getValue() < thresValue && TextUtils.isEmpty(eventsBeanL1.getType())){
            eventsBeanL1.setType("Dip");
            eventsBeanL1.setLine("A");
            eventsBeanL1.setStartTime(new Date());
        }else if(L1.getValue() > thresValueTwo && TextUtils.isEmpty(eventsBeanL1.getType())){
            //骤升事件开始记录
            eventsBeanL1.setType("Sweels");
            eventsBeanL1.setLine("A");
            eventsBeanL1.setStartTime(new Date());
        }

        //骤降事件结束记录
        if (L1.getValue() > thresValue + hysterValue && "Dip".equals(eventsBeanL1.getType())){
            eventsBeanL1.setEndTime(new Date());
            //生成对象，保存到List中
            String s = eventsBeanL1.getLine() + " " + eventsBeanL1.getType();
            eventsBeanL1.clearself();
            return s;
        }else if (L1.getValue() < thresValueTwo - hysterValueTwo && "Sweels".equals(eventsBeanL1.getType())){
            //骤升事件结束记录
            eventsBeanL1.setEndTime(new Date());
            String s = eventsBeanL1.getLine() + " " + eventsBeanL1.getType();
            eventsBeanL1.clearself();
            return s;
        }

        return "";
    }
    private String parseInrushL3(DataMeterData L3) {
        if(L3.getValue() > thresValue3 && TextUtils.isEmpty(eventsBeanL6.getType())){
            //骤升事件开始记录
            eventsBeanL6.setType("Inrush");
            eventsBeanL6.setLine("C");
            eventsBeanL6.setStartTime(new Date());
        }
        if (L3.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL6.getType())){
            //骤升事件结束记录
            eventsBeanL6.setEndTime(new Date());
            String s = eventsBeanL6.getLine() + " " + eventsBeanL6.getType();
            eventsBeanL6.clearself();
            return s;
        }
        return "";
    }
    private String parseInrushL2(DataMeterData L2) {
        if(L2.getValue() > thresValue3 && TextUtils.isEmpty(eventsBeanL5.getType())){
            //骤升事件开始记录
            eventsBeanL5.setType("Inrush");
            eventsBeanL5.setLine("B");
            eventsBeanL5.setStartTime(new Date());
        }

        if (L2.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL5.getType())){
            //骤升事件结束记录
            eventsBeanL5.setEndTime(new Date());
            String s = eventsBeanL5.getLine() + " " + eventsBeanL5.getType();
            eventsBeanL5.clearself();
            return s;
        }
        return "";
    }
    private String parseInrushL1(DataMeterData L1){

        if(L1.getValue() > thresValue3 && TextUtils.isEmpty(eventsBeanL4.getType())){
            //骤升事件开始记录
            eventsBeanL4.setType("Inrush");
            eventsBeanL4.setLine("A");
            eventsBeanL4.setStartTime(new Date());
        }

        if (L1.getValue() < thresValue3 - hysterValue3 && "Inrush".equals(eventsBeanL4.getType())){
            //骤升事件结束记录
            eventsBeanL4.setEndTime(new Date());
            //生成对象，保存到List中
            String s = eventsBeanL4.getLine() + " " + eventsBeanL4.getType();
            eventsBeanL4.clearself();
            return s;
        }
        return "";
    }
    private void writeDataPhaseObj(StringBuilder stringBuilder, String parameter, DataPhaseObj dataPhaseObj,String event){
        stringBuilder.append(simpleDateFormat.format(dataPhaseObj.getPhaseA().getDate()));
        stringBuilder.append(slipt);
        stringBuilder.append(parameter);
        stringBuilder.append(slipt);

        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? " " : dataPhaseObj.getPhaseA().getShowValue());
        stringBuilder.append(slipt);
        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? " " : dataPhaseObj.getPhaseB().getShowValue());
        stringBuilder.append(slipt);
        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? " " : dataPhaseObj.getPhaseC().getShowValue());
        stringBuilder.append(slipt);
        if (dataPhaseObj instanceof DataPhaseObjN){
            stringBuilder.append(((DataPhaseObjN) dataPhaseObj).getPhaseN().getShowValue());
        }else{
            stringBuilder.append(" ");
        }
        stringBuilder.append(slipt);
        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? dataPhaseObj.getPhaseA().getShowValue() : " ");
        stringBuilder.append(slipt);
        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? dataPhaseObj.getPhaseB().getShowValue() : " ");
        stringBuilder.append(slipt);
        stringBuilder.append(Res2String(R.string.Uabc).equals(parameter) ? dataPhaseObj.getPhaseC().getShowValue() : " ");
        stringBuilder.append(slipt);
        stringBuilder.append(event);
        stringBuilder.append(newLine);
    }
    private void writeDataFreq(StringBuilder stringBuilder, String parameter, DataMeterData dataMeterData){
        stringBuilder.append(simpleDateFormat.format(dataMeterData.getDate()));
        stringBuilder.append(slipt);
        stringBuilder.append(parameter);
        stringBuilder.append(slipt);
        stringBuilder.append(dataMeterData.getShowValue());
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(slipt);
        stringBuilder.append(" ");
        stringBuilder.append(newLine);
    }
    private void parseObjN(List<SqlMeterData> sqlMeterDataList,int j,String parameter){
        SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        SqlMeterData sqlMeterDataN = sqlMeterDataList.get(j + 3);
        DataPhaseObjN dataPhaseObjN = MeterDataPool.obtainObjN();
        dataPhaseObjN.setPhaseA(sqlMeterDataA.toDataMeterData());
        dataPhaseObjN.setPhaseB(sqlMeterDataB.toDataMeterData());
        dataPhaseObjN.setPhaseC(sqlMeterDataC.toDataMeterData());
        dataPhaseObjN.setPhaseN(sqlMeterDataN.toDataMeterData());
//        DataPhaseObjN objN = new DataPhaseObjN(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData(), sqlMeterDataN.toDataMeterData());
        writeDataPhaseObj(stringBuilder,parameter,dataPhaseObjN,"");
        MeterDataPool.recycle(dataPhaseObjN);
    }
    private void parseObj(List<SqlMeterData> sqlMeterDataList,int j,String parameter){
        SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
        SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
        SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
        DataPhaseObj dataPhaseObj = MeterDataPool.obtainObj();
        dataPhaseObj.setPhaseA(sqlMeterDataA.toDataMeterData());
        dataPhaseObj.setPhaseB(sqlMeterDataB.toDataMeterData());
        dataPhaseObj.setPhaseC(sqlMeterDataC.toDataMeterData());

//        DataPhaseObj obj = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
        writeDataPhaseObj(stringBuilder,parameter,dataPhaseObj,"");
        MeterDataPool.recycle(dataPhaseObj);

    }
    private int parseList(List<SqlMeterData> sqlMeterDataList,int j,String parameter,int parameterCode){

        int dataCount = sqlMeterDataList.get(j).getDataCount();
        int index = 1;
        URMS2:for (;j<sqlMeterDataList.size() - 3;j = j + 3){
            if (sqlMeterDataList.get(j).getParameterType() == parameterCode){
                SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                DataPhaseObj dataPhaseObj = MeterDataPool.obtainObj();
                dataPhaseObj.setPhaseA(sqlMeterDataA.toDataMeterData());
                dataPhaseObj.setPhaseB(sqlMeterDataB.toDataMeterData());
                dataPhaseObj.setPhaseC(sqlMeterDataC.toDataMeterData());
//                DataPhaseObj dataPhaseObj1 = new DataPhaseObj(sqlMeterDataA.toDataMeterData(), sqlMeterDataB.toDataMeterData(), sqlMeterDataC.toDataMeterData());
                writeDataPhaseObj(stringBuilder,parameter + index,dataPhaseObj,"");
                MeterDataPool.recycle(dataPhaseObj);
                index ++;
            }else{
                break URMS2;
            }
        }
        return j;
    }
    private void write2SD(SqlDataBean sqlDataBean,BufferedWriter bw) throws IOException {

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

        switch (sqlDataBean.getModeType()){
            case SqlApi.Mode_Power:

                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Urms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Irms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Upk){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Upk));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Ipk){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Ipk));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uabc){

                            parseObj(sqlMeterDataList,j,Res2String(R.string.Uabc));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_PF){

                            parseObjN(sqlMeterDataList,j,Res2String(R.string.PF));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Udc){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.udc));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }

                    }
                }
                break;
            case SqlApi.Mode_Unbalance:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Vfound){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Vfund));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Afound){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Afund));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_FaiV){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.fV));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_FaiA){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.fA));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }

                    }
                }
                break;
            case SqlApi.Mode_Dip:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseL1(phaseA);
                            String event2 = parseL2(phaseA);
                            String event3 = parseL3(phaseA);
                            String event = event1+event2+event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Urms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms2){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Urms2),SqlApi.Parameter_Urms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }
                    }
                }

                break;
            case SqlApi.Mode_Harmonic:

                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Urms));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Irms));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uthd){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Uthd));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Ithd){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Ithd));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_UthdList){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Uthd2_50),SqlApi.Parameter_UthdList);
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_IthdList){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Ithd2_50),SqlApi.Parameter_IthdList);
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }

                    }
                }


                break;
            case SqlApi.Mode_Shipboard:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Urms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Irms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uabc){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Uabc));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }
                    }
                }

                break;
            case SqlApi.Mode_Inrush:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseInrushL1(phaseA);
                            String event2 = parseInrushL2(phaseB);
                            String event3 = parseInrushL3(phaseC);
                            String event = event1 + event2 + event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Irms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms2){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Irms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);

                        }
                    }
                }

                break;
        }


    }
    private  int getPhaseObjCount(Context context,int modeType,String checkParameters,List<SqlMeterData> dataList,int startIndex){
        String[] split = checkParameters.split(",");
        int count = 0;
        switch (modeType){
            case SqlApi.Mode_Power:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(R.string.Urms)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Irms)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Upk)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Ipk)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(R.string.PF)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.udc)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Uabc)))
                        count += 3;

                }

                break;

            case SqlApi.Mode_Unbalance:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(R.string.Vfund)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Afund)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(R.string.fV)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.fA)))
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
                    if (split[i].equals(Res2String(R.string.Urms)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Urms2)))//47-63个半波的有效值
                        count += urmsCount;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;

                }

                break;
            case SqlApi.Mode_Harmonic:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(R.string.Urms)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Irms)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(R.string.Uthd)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Ithd)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Uthd2_50)))
                        count += 3 * 50;
                    else if (split[i].equals(Res2String(R.string.Ithd2_50)))
                        count += 3 * 50;


                }

                break;
            case SqlApi.Mode_Shipboard:
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(Res2String(R.string.Urms)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Irms)))
                        count += 4;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;
                    else if (split[i].equals(Res2String(R.string.Uabc)))
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
                    if (split[i].equals(Res2String(R.string.Irms)))
                        count += 3;
                    else if (split[i].equals(Res2String(R.string.Irms2)))//47-63个半波的有效值
                        count += irmsCount;
                    else if (split[i].equals(Res2String(R.string.Freq)))
                        count += 1;

                }

                break;
        }
        return count;
    }

    /*------------------------新CSV导出-------------------------*/
    public void newwriteCsvFile(final String fileName) {
        init();
        DBManager.getInstance().findFromName(true, fileName, new DBManager.DBQueryAllParameterListener() {
            @Override
            public void onFail() {
                if (writeListener != null){
                    LitePal.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            writeListener.writeFail();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(final List<SqlDataBean> dataMeterAllParameterList) {
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            SqlDataBean sqlDataBean = dataMeterAllParameterList.get(0);
                            long wt = System.currentTimeMillis();

                            File file = new File(filePath + File.separator + sqlDataBean.getFileName() + ".csv");
                            if (!file.exists()){
                                file.createNewFile();
                            }
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(file), "Unicode"));
                            long startTime = System.currentTimeMillis();
                            newrite2SD(sqlDataBean,bw);
                            log.e("转换时间 : " + (System.currentTimeMillis() - startTime));
                            bw.write(stringBuilder.toString());
                            bw.close();
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    writeListener.writeFinish();
                                }
                            });
                            log.e("csv耗时: " + (System.currentTimeMillis() - wt));
                            //转换完释放状态
                            MeterDataPool.recycle(sqlDataBean);
                        } catch (IOException e) {
                            log.e("write Error : " + e.getMessage());
                            LitePal.getHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    if (writeListener != null)
                                        writeListener.writeFail();
                                }
                            });
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }


    private void newrite2SD(SqlDataBean sqlDataBean,BufferedWriter bw) throws IOException {

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

        switch (sqlDataBean.getModeType()){
            case SqlApi.Mode_Record_VoltAmp:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Urms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Urms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vrms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Vrms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Arms){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_Arms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Upeakjia){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_Upeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Upeakjian){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_Upeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vpeakjia){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Vpeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vpeakjian){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Vpeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Apeakjia){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Apeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Apeakjian){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Apeak1));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Udc){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_Udc));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vdc){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_Vdc));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Adc){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Adc));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Ucf){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Ucf));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vcf){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Vcf));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Acf){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_Acf));
                            j = j + 3;
                        }

                    }
                }
                break;
            case SqlApi.Mode_Record_Flicker:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pst1min){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.parameter_pstmin));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pst){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_pst));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Plt){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_plt));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Pinst){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.parameter_pinst));
                            j = j + 3;
                        }
                    }
                }
                break;
            case SqlApi.Mode_Record_Frequency:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Hz){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseL1(phaseA);
                            String event2 = parseL2(phaseA);
                            String event3 = parseL3(phaseA);
                            String event = event1+event2+event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Urms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms2){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Urms2),SqlApi.Parameter_Urms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }
                    }
                }
                break;
            case SqlApi.Mode_Record_VoltHarmonic:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Urms));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Irms));
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uthd){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Uthd));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Ithd){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Ithd));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_UthdList){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Uthd2_50),SqlApi.Parameter_UthdList);
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_IthdList){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Ithd2_50),SqlApi.Parameter_IthdList);
                            j = j - 1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }

                    }
                }
                break;
            case SqlApi.Mode_Record_AmpHarmonic:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Urms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Urms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms){
                            parseObjN(sqlMeterDataList,j,Res2String(R.string.Irms));
                            j = j + 3;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Uabc){
                            parseObj(sqlMeterDataList,j,Res2String(R.string.Uabc));
                            j = j + 2;

                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }
                    }
                }
                break;
            case SqlApi.Mode_Record_Unbalance:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Unbal){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseInrushL1(phaseA);
                            String event2 = parseInrushL2(phaseB);
                            String event3 = parseInrushL3(phaseC);
                            String event = event1 + event2 + event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Irms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vneg){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Irms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Aneg){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vzero){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Irms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_Vneg){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Record_Azero);
                            j = j -1;
                        }
                    }
                }
                break;
            case SqlApi.Mode_Record_Power:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kW){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseInrushL1(phaseA);
                            String event2 = parseInrushL2(phaseB);
                            String event3 = parseInrushL3(phaseC);
                            String event = event1 + event2 + event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Irms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms2){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Irms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);

                        }
                    }
                }
                break;
            case SqlApi.Mode_Record_Energy:
                for (int i = 0; i <lists.size() ; i++) {
                    List<SqlMeterData> sqlMeterDataList = lists.get(i);
                    for (int j = 0; j < sqlMeterDataList.size(); j++) {
                        if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Record_kVAh){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            SqlMeterData sqlMeterDataB = sqlMeterDataList.get(j + 1);
                            SqlMeterData sqlMeterDataC = sqlMeterDataList.get(j + 2);
                            DataMeterData phaseA = sqlMeterDataA.toDataMeterData();
                            DataMeterData phaseB = sqlMeterDataB.toDataMeterData();
                            DataMeterData phaseC = sqlMeterDataC.toDataMeterData();
                            String event1 = parseInrushL1(phaseA);
                            String event2 = parseInrushL2(phaseB);
                            String event3 = parseInrushL3(phaseC);
                            String event = event1 + event2 + event3;
                            DataPhaseObj dataPhaseObj = new DataPhaseObj(phaseA, phaseB, phaseC);
                            writeDataPhaseObj(stringBuilder,Res2String(R.string.Irms),dataPhaseObj,event);
                            j = j + 2;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Irms2){
                            j = parseList(sqlMeterDataList,j,Res2String(R.string.Irms2),SqlApi.Parameter_Irms2);
                            j = j -1;
                        }else if (sqlMeterDataList.get(j).getParameterType() == SqlApi.Parameter_Freq){
                            SqlMeterData sqlMeterDataA = sqlMeterDataList.get(j);
                            DataMeterData dataMeterData = sqlMeterDataA.toDataMeterData();
                            writeDataFreq(stringBuilder,Res2String(R.string.Freq),dataMeterData);

                        }
                    }
                }
                break;
        }

    }

    private int newgetPhaseObjCount(Context context,int modeType,String checkParameters,List<SqlMeterData> dataList,int startIndex){
        String[] split = checkParameters.split(",");
        int count = 0;
        for (int i = 0; i < split.length; i++) {
            count += 4;
        }
        return count;
    }
    private String Res2String(int resID){
        return  context.getResources().getString(resID);
    }

}
