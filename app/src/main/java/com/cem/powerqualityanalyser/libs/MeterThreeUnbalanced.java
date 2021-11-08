package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

public class MeterThreeUnbalanced extends BaseMeterData {
    private PhaseObjN v_fundWave;
    private PhaseObj a_fundWave;
    private PhaseObj v_Angle;
    private PhaseObj a_Angle;
    private MeterData frequency;
    private MeterData vneg;
    private MeterData aneg;
    private MeterData vzero;
    private MeterData azero;

    public MeterThreeUnbalanced(byte[] bytes) {
        super(bytes);
        this.v_fundWave = this.CreatePhaseObj(PhaseType.V_FundWave, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_fundWave = this.CreatePhaseObj(PhaseType.A_FundWave, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_Angle = this.CreatePhaseObj(PhaseType.V_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Angle = this.CreatePhaseObj(PhaseType.A_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
        this.vneg = this.getMeterData();
        this.aneg = this.getMeterData();
        this.vzero = this.getMeterData();
        this.azero = this.getMeterData();
    }

    public PhaseObjN getV_fundWave() {
        return this.v_fundWave;
    }

    public PhaseObj getA_fundWave() {
        return this.a_fundWave;
    }

    public PhaseObj getV_Angle() {
        return this.v_Angle;
    }

    public PhaseObj getA_Angle() {
        return this.a_Angle;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }

    public MeterData getVneg() {
        return this.vneg;
    }

    public MeterData getAneg() {
        return this.aneg;
    }

    public MeterData getVzero() {
        return this.vzero;
    }

    public MeterData getAzero() {
        return this.azero;
    }
}
