package com.cem.powerqualityanalyser.allmode;

/**
 * Record /View Parameter 处理
 */
public enum RecordMeterMode {
    None(0),
    VoltAmp(225),
    Flicker(226),
    Frequency(227),
    VoltHarmonic(228),
    AmpHarmonic(229),
    Unbalance(230),
    Power(231),
    Energy(232);

    private int value = 0;

    private RecordMeterMode(int value) {
        this.value = value;
    }

    public static RecordMeterMode valueOf(int value) {
        switch(value) {


            default:
                return None;
        }
    }

    public int value() {
        return this.value;
    }


}
