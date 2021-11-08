package com.cem.powerqualityanalyser.meterobject;

import java.util.List;

/**
 * 谐波对象
 */
public class MeterHarmonicObj {
    private float thd;
    private float dc;
    private List<Float> graphList;
    private float rmsValue;
    private int size;
    private String unit = "";
    private int phaseIndex = 0;
    public MeterHarmonicObj(float thd ,float dc ,List<Float> graphList){
        this.thd=thd;
        this.dc=dc;
        this.graphList=graphList;
        this.size = graphList.size() + 1;
    }

    public MeterHarmonicObj(float thd ,float dc ,List<Float> graphList,float rmsValue){
        this.thd=thd;
        this.dc=dc;
        this.graphList=graphList;
        this.rmsValue = rmsValue;
        this.size = graphList.size() + 1;
    }

    public int getPhaseIndex() {
        return phaseIndex;
    }

    public void setPhaseIndex(int phaseIndex) {
        this.phaseIndex = phaseIndex;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRmsShowValue(){
        return String.format("%.2f", rmsValue);
    }

    public float getRmsValue() {
        return rmsValue;
    }

    public void setRmsValue(float rmsValue) {
        this.rmsValue = rmsValue;
    }

    public int getSize(){
        return size;
    }

    public float getThd() {
        return thd;
    }

    public float getDc() {
        return dc;
    }

    public List<Float> getGraphList() {
        return graphList;
    }
}
