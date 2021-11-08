package com.cem.powerqualityanalyser.meterobject;


import com.cem.powerqualityanalyser.libs.MeterData;

public class MeterThreeUnbalanceObj extends MeterUnbalancedObj {
    private MeterData vneg;
    private  MeterData vzero;
    private  MeterData aneg;
    private  MeterData azero;

    public  MeterThreeUnbalanceObj(MeterData frequency,MeterUnbalancedAngleObj l1,MeterUnbalancedAngleObj l2,MeterUnbalancedAngleObj l3,MeterUnbalancedAngleObj ln, MeterData vneg, MeterData vzero, MeterData aneg, MeterData azero,float maxFund){
        this.frequency=frequency;
        this.l1=l1;
        this.l2=l2;
        this.l3=l3;
        this.ln=ln;
        this.vneg=vneg;
        this.vzero=vzero;
        this.aneg=aneg;
        this.azero=azero;
        this.maxFund=maxFund;
    }

    public MeterData getVneg() {
        return vneg;
    }

    public void setVneg(MeterData vneg) {
        this.vneg = vneg;
    }

    public MeterData getVzero() {
        return vzero;
    }

    public void setVzero(MeterData vzero) {
        this.vzero = vzero;
    }

    public MeterData getAneg() {
        return aneg;
    }

    public void setAneg(MeterData aneg) {
        this.aneg = aneg;
    }

    public MeterData getAzero() {
        return azero;
    }

    public void setAzero(MeterData azero) {
        this.azero = azero;
    }

}
