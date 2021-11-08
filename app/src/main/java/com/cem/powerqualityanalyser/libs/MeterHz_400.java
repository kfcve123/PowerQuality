package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

public class MeterHz_400 extends BaseMeterData {
    private PhaseObjN v_StarValue;
    private PhaseObj v_TriangleValue;
    private PhaseObjN a_TriangleValue;
    private MeterData frequency;

    public MeterHz_400(byte[] bytes) {
        super(bytes);
        this.v_StarValue = this.CreatePhaseObj(PhaseType.V_Star, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_TriangleValue = this.CreatePhaseObj(PhaseType.V_Triangle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_TriangleValue = this.CreatePhaseObj(PhaseType.A_Triangle, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
    }

    public PhaseObjN getV_StarValue() {
        return this.v_StarValue;
    }

    public PhaseObj getV_TriangleValue() {
        return this.v_TriangleValue;
    }

    public PhaseObjN getA_TriangleValue() {
        return this.a_TriangleValue;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }
}
