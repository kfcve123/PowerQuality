package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterDebufB1 extends BaseMeterData {
    private PhaseObj v_Value;
    private PhaseObj v_Max;
    private PhaseObj a_Value;
    private MeterData frequency;

    public MeterDebufB1(byte[] bytes) {
        super(bytes);
        this.v_Value = this.CreatePhaseObj(PhaseType.V_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_Max = this.CreatePhaseObj(PhaseType.V_Max, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
    }

    public PhaseObj getV_Value() {
        return this.v_Value;
    }

    public PhaseObj getV_Max() {
        return this.v_Max;
    }

    public PhaseObj getA_Value() {
        return this.a_Value;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }
}
