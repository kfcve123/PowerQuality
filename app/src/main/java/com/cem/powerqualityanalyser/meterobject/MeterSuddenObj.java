package com.cem.powerqualityanalyser.meterobject;

import java.util.List;

/**
 * 骤升骤降/浪涌
 */
public class MeterSuddenObj {
    private  float frequency;
    private List<Float> l1;
    private List<Float> l2;
    private List<Float> l3;
    public  MeterSuddenObj(float frequency ,List<Float> l1,List<Float> l2,List<Float> l3 ){
        this.frequency=frequency;
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
    }

    public float getFrequency() {
        return frequency;
    }

    public List<Float> getL1() {
        return l1;
    }

    public List<Float> getL2() {
        return l2;
    }

    public List<Float> getL3() {
        return l3;
    }
}
