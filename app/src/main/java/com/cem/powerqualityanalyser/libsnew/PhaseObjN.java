package com.cem.powerqualityanalyser.libsnew;

import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libs.PhaseType;

public class PhaseObjN extends PhaseObj {
    private MeterData phaseN;

    public PhaseObjN(PhaseType type, MeterData a, MeterData b, MeterData c, MeterData n) {
        super(type, a, b, c);
        this.phaseN = n;
    }

    public MeterData getPhaseN() {
        return this.phaseN;
    }
}
