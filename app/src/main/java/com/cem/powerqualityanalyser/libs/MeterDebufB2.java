package com.cem.powerqualityanalyser.libs;


import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

public class MeterDebufB2 extends BaseMeterData {
    private PhaseObjN a_Value;
    private PhaseObj a_Max;
    private MeterData angle;

    public MeterDebufB2(byte[] bytes) {
        super(bytes);
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Max = this.CreatePhaseObj(PhaseType.A_Max, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.angle = this.getMeterData();
    }

    public PhaseObjN getA_Value() {
        return this.a_Value;
    }

    public PhaseObj getA_Max() {
        return this.a_Max;
    }

    public MeterData getAngle() {
        return this.angle;
    }
}