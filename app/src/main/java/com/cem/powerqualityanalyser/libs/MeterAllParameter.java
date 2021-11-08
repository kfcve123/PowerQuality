package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;

public class MeterAllParameter extends BaseMeterData{
    private PhaseObjN v_StarValue;
    private PhaseObj v_StarMax;
    private PhaseObj v_StarCF;
    private PhaseObj v_triangleValue;
    private PhaseObj v_triangleMax;
    private PhaseObjN v_triangleDCValue;
    private PhaseObjN a_Value;
    private PhaseObj a_Max;
    private PhaseObj a_CF;
    private PhaseObj v_Angle;
    private PhaseObj a_Angle;
    private PhaseObj phase_kw;
    private PhaseObj phase_kvar;
    private PhaseObj phase_kva;
    private PhaseObj phase_threekw;
    private PhaseObj phase_Pf;
    private MeterData angle_Cos;
    private MeterData frequency;
    private MeterData v_threeUnbalanced;
    private MeterData a_threeUnbalanced;

    public MeterAllParameter(byte[] bytes) {
        super(bytes);
        this.v_StarValue = this.CreatePhaseObj(PhaseType.V_Star, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_StarMax = this.CreatePhaseObj(PhaseType.V_StarMax, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_StarCF = this.CreatePhaseObj(PhaseType.V_StarCF, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_triangleValue = this.CreatePhaseObj(PhaseType.V_Triangle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_triangleMax = this.CreatePhaseObj(PhaseType.V_TriangleMax, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_triangleDCValue = this.CreatePhaseObj(PhaseType.V_TriangleDCValue, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Max = this.CreatePhaseObj(PhaseType.A_Max, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_CF = this.CreatePhaseObj(PhaseType.A_CF, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_Angle = this.CreatePhaseObj(PhaseType.V_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Angle = this.CreatePhaseObj(PhaseType.A_Angle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.phase_kw = this.CreatePhaseObj(PhaseType.Phase_KW, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.phase_kvar = this.CreatePhaseObj(PhaseType.Phase_Kvar, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.phase_kva = this.CreatePhaseObj(PhaseType.Phase_KVA, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.phase_threekw = this.CreatePhaseObj(PhaseType.Phase_ThreeKW, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.phase_Pf = this.CreatePhaseObj(PhaseType.Phase_Pf, this.getMeterData(), this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.angle_Cos = this.getMeterData();
        this.frequency = this.getMeterData();
        this.v_threeUnbalanced = this.getMeterData();
        this.a_threeUnbalanced = this.getMeterData();
    }

    public PhaseObjN getV_StarValue() {
        return this.v_StarValue;
    }

    public PhaseObj getV_StarMax() {
        return this.v_StarMax;
    }

    public PhaseObj getV_StarCF() {
        return this.v_StarCF;
    }

    public PhaseObj getV_triangleValue() {
        return this.v_triangleValue;
    }

    public PhaseObj getV_triangleMax() {
        return this.v_triangleMax;
    }

    public PhaseObjN getV_triangleDCValue() {
        return this.v_triangleDCValue;
    }

    public PhaseObjN getA_Value() {
        return this.a_Value;
    }

    public PhaseObj getA_Max() {
        return this.a_Max;
    }

    public PhaseObj getA_CF() {
        return this.a_CF;
    }

    public PhaseObj getV_Angle() {
        return this.v_Angle;
    }

    public PhaseObj getA_Angle() {
        return this.a_Angle;
    }

    public PhaseObj getPhase_kw() {
        return this.phase_kw;
    }

    public PhaseObj getPhase_kvar() {
        return this.phase_kvar;
    }

    public PhaseObj getPhase_kva() {
        return this.phase_kva;
    }

    public PhaseObj getPhase_threekw() {
        return this.phase_threekw;
    }

    public PhaseObj getPhase_Pf() {
        return this.phase_Pf;
    }

    public MeterData getAngle_Cos() {
        return this.angle_Cos;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }

    public MeterData getV_threeUnbalanced() {
        return this.v_threeUnbalanced;
    }

    public MeterData getA_threeUnbalanced() {
        return this.a_threeUnbalanced;
    }

}
