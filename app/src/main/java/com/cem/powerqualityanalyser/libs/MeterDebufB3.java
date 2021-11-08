package com.cem.powerqualityanalyser.libs;


import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterDebufB3 extends BaseMeterData {
    private PhaseObj a_Value;
    private PhaseObj a_Max;

    public MeterDebufB3(byte[] bytes) {
        super(bytes);
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Max = this.CreatePhaseObj(PhaseType.A_Max, this.getMeterData(), this.getMeterData(), this.getMeterData());
    }

    public PhaseObj getA_Value() {
        return this.a_Value;
    }

    public PhaseObj getA_Max() {
        return this.a_Max;
    }
}
