package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterVA_Waveform extends BaseMeterData {
    private PhaseObj v_Value;
    private PhaseObj a_Value;
    private MeterData frequency;
    private MeterData v_Coefficient;
    private MeterData a_Coefficient;
    private GraphDataObj listV_Waveform;
    private GraphDataObj listA_Waveform;
    private int byteCount = 1;
    private int dataCount = 400;

    public MeterVA_Waveform(byte[] bytes) {
        super(bytes);
        this.v_Value = this.CreatePhaseObj(PhaseType.V_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_Value = this.CreatePhaseObj(PhaseType.A_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
        this.v_Coefficient = this.getMeterData();
        if (this.v_Coefficient == null) {
            this.v_Coefficient = new MeterData(10.0F);
        }

        this.a_Coefficient = this.getMeterData();
        if (this.a_Coefficient == null) {
            this.a_Coefficient = new MeterData(10.0F);
        }

        if (this.waveformByteCount >= this.dataCount * 3) {
            this.listV_Waveform = new GraphDataObj(PhaseType.V_GraphWaveform, this.waveformByteStart, bytes, this.byteCount, this.dataCount, this.v_Coefficient.getValue());
        }

        if (this.waveformByteCount >= this.dataCount * 6) {
            this.listA_Waveform = new GraphDataObj(PhaseType.A_GraphWaveform, this.waveformByteStart + this.dataCount * 3, bytes, this.byteCount, this.dataCount, this.a_Coefficient.getValue());
        }

    }

    public PhaseObj getV_Value() {
        return this.v_Value;
    }

    public PhaseObj getA_Value() {
        return this.a_Value;
    }

    public MeterData getFrequency() {
        return this.frequency;
    }

    public GraphDataObj getListV_Waveform() {
        return this.listV_Waveform;
    }

    public GraphDataObj getListA_Waveform() {
        return this.listA_Waveform;
    }

    public MeterData getV_Coefficient() {
        return this.v_Coefficient;
    }

    public MeterData getA_Coefficient() {
        return this.a_Coefficient;
    }
}
