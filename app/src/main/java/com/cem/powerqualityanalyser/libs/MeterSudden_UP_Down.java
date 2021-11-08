package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterSudden_UP_Down extends BaseMeterData {
    private PhaseObj phaseValue;
    private MeterData valueFrequency;
    private GraphDataObj listGraphPhase;
    private int byteCount = 2;
    private int dataCount = 0;
    protected String unit = "V";

    public MeterSudden_UP_Down(byte[] bytes) {
        super(bytes);
        this.phaseValue = this.CreatePhaseObj(PhaseType.V_Value, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.valueFrequency = this.getMeterData();
        this.dataCount = this.waveformByteCount / 3 / this.byteCount;
        int aIndex = this.waveformByteStart;
        this.listGraphPhase = new GraphDataObj(PhaseType.V_HalfWave, aIndex, bytes, this.byteCount, this.dataCount);
    }

    public PhaseObj getPhaseValue() {
        return this.phaseValue;
    }

    public MeterData getValueFrequency() {
        return this.valueFrequency;
    }

    public GraphDataObj getListGraphPhase() {
        return this.listGraphPhase;
    }

    public String getUnit() {
        return this.unit;
    }

}
