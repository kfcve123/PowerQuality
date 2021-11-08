package com.cem.powerqualityanalyser.meterobject;


import com.cem.powerqualityanalyser.libs.MeterData;

/**
 * 不平衡
 */
public class MeterUnbalancedObj {
    //频率
    public MeterData frequency;
    //L1的相位和幅度
    public  MeterUnbalancedAngleObj l1;
    public  MeterUnbalancedAngleObj l2;
    public  MeterUnbalancedAngleObj l3;
    public  MeterUnbalancedAngleObj ln;
    public  float maxFund;


    public MeterUnbalancedObj() {
    }

    public MeterData getFrequency() {
        return frequency;
    }

    public MeterUnbalancedAngleObj getL1() {
        return l1;
    }

    public MeterUnbalancedAngleObj getL2() {
        return l2;
    }

    public MeterUnbalancedAngleObj getL3() {
        return l3;
    }

    public MeterUnbalancedAngleObj getLn() {
        return ln;
    }

    public float getMaxFund() {
        return maxFund;
    }

    public void setMaxFund(float maxFund) {
        this.maxFund = maxFund;
    }
}
