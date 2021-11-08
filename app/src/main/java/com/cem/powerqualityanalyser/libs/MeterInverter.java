package com.cem.powerqualityanalyser.libs;

import com.cem.powerqualityanalyser.libsnew.PhaseObj;

public class MeterInverter extends BaseMeterData {
    private PhaseObj v_StarValue;
    private PhaseObj v_TriangleValue;
    private PhaseObj a_TriangleValue;
    private MeterData frequency;
    private MeterData V_DC;
    private MeterData A_DC;
    private MeterData efficiency;

    public MeterInverter(byte[] bytes) {
        super(bytes);
        this.v_StarValue = this.CreatePhaseObj(PhaseType.V_Star, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.v_TriangleValue = this.CreatePhaseObj(PhaseType.V_Triangle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.a_TriangleValue = this.CreatePhaseObj(PhaseType.A_Triangle, this.getMeterData(), this.getMeterData(), this.getMeterData());
        this.frequency = this.getMeterData();
        this.V_DC = this.getMeterData();
        this.A_DC = this.getMeterData();
        this.efficiency = this.getMeterData();
    }
}
