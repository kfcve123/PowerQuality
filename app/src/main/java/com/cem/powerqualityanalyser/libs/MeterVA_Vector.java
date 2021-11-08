package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterVA_Vector extends BaseMeterData {
    private PhaseObj v_Value;
    private PhaseObj a_Value;
    private PhaseObj v_Angle;
    private PhaseObj a_Angle;
    private MeterData frequency;
    private MeterData A_Unbalanced;
    private MeterData V_Unbalanced;
    private PhaseObj v_Percentage;
    private PhaseObj a_Percentage;

    public MeterVA_Vector(byte[] bytes) {
        super(bytes);
        this.v_Value = this.CreatePhaseObj(PhaseType.V_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_Angle = this.CreatePhaseObj(PhaseType.V_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Angle = this.CreatePhaseObj(PhaseType.V_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
        this.V_Unbalanced = this.getMeterData();
        this.A_Unbalanced = this.getMeterData();
        this.v_Percentage = this.CreatePhaseObj(PhaseType.V_Percentage, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Percentage = this.CreatePhaseObj(PhaseType.A_Percentage, this.getMeterData(), this.getMeterData(), this.getMeterData());
    }

    public PhaseObj getV_Value() {
        return this.v_Value;
    }

    public void setV_Value(PhaseObj v_Value) {
        this.v_Value = v_Value;
    }

    public PhaseObj getA_Value() {
        return this.a_Value;
    }

    public void setA_Value(PhaseObj a_Value) {
        this.a_Value = a_Value;
    }

    public PhaseObj getV_Angle() {
        return this.v_Angle;
    }

    public void setV_Angle(PhaseObj v_Angle) {
        this.v_Angle = v_Angle;
    }

    public PhaseObj getA_Angle() {
        return this.a_Angle;
    }

    public void setA_Angle(PhaseObj a_Angle) {
        this.a_Angle = a_Angle;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }

    public void setFrequency(MeterData frequency) {
        this.frequency = frequency;
    }

    public MeterData getA_Unbalanced() {
        return this.A_Unbalanced;
    }

    public void setA_Unbalanced(MeterData a_Unbalanced) {
        this.A_Unbalanced = a_Unbalanced;
    }

    public MeterData getV_Unbalanced() {
        return this.V_Unbalanced;
    }

    public void setV_Unbalanced(MeterData v_Unbalanced) {
        this.V_Unbalanced = v_Unbalanced;
    }

    public PhaseObj getV_Percentage() {
        return this.v_Percentage;
    }

    public void setV_Percentage(PhaseObj v_Percentage) {
        this.v_Percentage = v_Percentage;
    }

    public PhaseObj getA_Percentage() {
        return this.a_Percentage;
    }

    public void setA_Percentage(PhaseObj a_Percentage) {
        this.a_Percentage = a_Percentage;
    }
}
