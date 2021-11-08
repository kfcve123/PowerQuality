package com.cem.powerqualityanalyser.meterobject;


import com.cem.powerqualityanalyser.libs.MeterData;

public class MeterUnbalancedAngleObj {
    private MeterData found;
    private MeterData angle;
    private String unit;
    private  String name;

    public MeterUnbalancedAngleObj(MeterData found,MeterData angle,String unit,String name){
        this.found=found;
        this.angle=angle;
        this.unit=unit;
        this.name=name;
    }

    public MeterData getFound() {
        return found;
    }

    public MeterData getAngle() {
        return angle;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }
}
