package com.cem.powerqualityanalyser.meterobject;


import com.cem.powerqualityanalyser.libs.MeterData;

public class MeterScopeObj extends MeterUnbalancedObj {
    private MeterData l1Value;
    private MeterData l2Value;
    private MeterData l3Value;

    private MeterData A_Unbalanced;
    private MeterData V_Unbalanced;

    public  MeterScopeObj(MeterData frequency,MeterUnbalancedAngleObj l1,MeterUnbalancedAngleObj l2,MeterUnbalancedAngleObj l3,MeterUnbalancedAngleObj ln,MeterData l1Value,MeterData l2Value,MeterData l3Value,MeterData V_Unbalanced,MeterData A_Unbalanced,float maxFund){
        this.frequency=frequency;
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.ln=ln;
       this.l1Value = l1Value;
       this.l2Value = l2Value;
       this.l3Value = l3Value;
       this.A_Unbalanced = A_Unbalanced;
       this.V_Unbalanced = V_Unbalanced;
       this.maxFund = maxFund;
    }

    public MeterData getL1Value() {
        return l1Value;
    }

    public void setL1Value(MeterData l1Value) {
        this.l1Value = l1Value;
    }

    public MeterData getL2Value() {
        return l2Value;
    }

    public void setL2Value(MeterData l2Value) {
        this.l2Value = l2Value;
    }

    public MeterData getL3Value() {
        return l3Value;
    }

    public void setL3Value(MeterData l3Value) {
        this.l3Value = l3Value;
    }

    public MeterData getA_Unbalanced() {
        return A_Unbalanced;
    }

    public void setA_Unbalanced(MeterData a_Unbalanced) {
        A_Unbalanced = a_Unbalanced;
    }

    public MeterData getV_Unbalanced() {
        return V_Unbalanced;
    }

    public void setV_Unbalanced(MeterData v_Unbalanced) {
        V_Unbalanced = v_Unbalanced;
    }
}
