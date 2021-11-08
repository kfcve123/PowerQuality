package com.cem.powerqualityanalyser.sqlBean;

import com.cem.powerqualityanalyser.databeannew.DataMeterData;
import com.cem.powerqualityanalyser.tool.MeterDataPool;

/**
    最底层数据保留表
 */
public class SqlMeterData extends SqlBaseBean{
    //对应float数值
    public float value;
    public int dataCount;
    //对应ABCN四相
    public int phaseType;
    //对应勾选的参数
    public int parameterType;
    //对应选择测量的模块
    public int modeType;

    public String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean gElectricity = false;
    public boolean rElectricity = false;

    public boolean isgElectricity() {
        return gElectricity;
    }

    public void setgElectricity(boolean gElectricity) {
        this.gElectricity = gElectricity;
    }

    public boolean isrElectricity() {
        return rElectricity;
    }

    public void setrElectricity(boolean rElectricity) {
        this.rElectricity = rElectricity;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(int phaseType) {
        this.phaseType = phaseType;
    }

    public int getParameterType() {
        return parameterType;
    }

    public void setParameterType(int parameterType) {
        this.parameterType = parameterType;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public DataMeterData toDataMeterData(){
        DataMeterData dataMeterData = MeterDataPool.obtainData();
        dataMeterData.setDataCount(dataCount);
        dataMeterData.setValue(value);
        dataMeterData.setDate(time);
        dataMeterData.setUnit(unit);
        dataMeterData.setgElectricity(gElectricity);
        dataMeterData.setrElectricity(rElectricity);
        return dataMeterData;
    }
}
