package com.cem.powerqualityanalyser.libs;

public class MeterSurge_A extends MeterSudden_UP_Down {
    public MeterSurge_A(byte[] bytes) {
        super(bytes);
        this.unit = "A";
    }
}
