package com.cem.powerqualityanalyser.sqlBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class InrushDataBean extends SqlBaseBean{
    public Date startDate;
    public Date endDate;
    public String fileName;
    public String checkParameters;
    public int modeType;
    public String deviceName;
    public List<InrushMeterData> dataList;

    public void saveOrUpdateData(final List<InrushMeterData> dataList){
        if (this.isSaved()){
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < dataList.size(); i++) {
                        InrushMeterData inrushMeterData = dataList.get(i);
                        inrushMeterData.save();
                        LitePal.getDatabase().execSQL("update " + InrushMeterData.class.getSimpleName().toLowerCase() + " set " + InrushDataBean.this.getTableName() + "_id = " + InrushDataBean.this.id + " where " + " id = '" + inrushMeterData.getId() + "'");
                    }
                }
            });
        }
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCheckParameters() {
        return checkParameters;
    }

    public void setCheckParameters(String checkParameters) {
        this.checkParameters = checkParameters;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<InrushMeterData> getDataList() {
        return dataList;
    }

    public void setDataList(List<InrushMeterData> dataList) {
        this.dataList = dataList;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void addMeterList(List<InrushMeterData> list){
        if (dataList == null)
            dataList = new ArrayList<>();
        this.dataList.addAll(list);
    }

    public String getFormatDate(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH/mm/ss");
        if (this.startDate != null){
            return startDate.getTime()+"";
        }
        return new Date().getTime()+"";
    }
}
