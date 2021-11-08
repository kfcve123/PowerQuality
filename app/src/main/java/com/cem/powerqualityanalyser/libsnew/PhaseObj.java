package com.cem.powerqualityanalyser.libsnew;

import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.PhaseType;

public class PhaseObj {

    private MeterData phaseA;
    private MeterData phaseB;
    private MeterData phaseC;
    private PhaseType type;

    public PhaseObj(PhaseType type, MeterData a, MeterData b, MeterData c) {
        this.type = type;
        this.phaseA = a;
        this.phaseB = b;
        this.phaseC = c;
    }

    public MeterData getPhaseA() {
        return this.phaseA;
    }

    public MeterData getPhaseB() {
        return this.phaseB;
    }

    public MeterData getPhaseC() {
        return this.phaseC;
    }

    public PhaseType getType() {
        return this.type;
    }


}
