package com.cem.powerqualityanalyser.libs;

public enum PhaseType {
    None(0),
    A_Value(1),
    V_Value(2),
    A_Angle(3),
    V_Angle(4),
    A_Percentage(5),
    V_Percentage(6),
    A_GraphWaveform(7),
    V_GraphWaveform(8),
    V_THD(9),
    A_THD(10),
    A_GraphHarmonic(11),
    V_GraphHarmonic(12),
    W_GraphHarmonic(13),
    V_Star(14),
    V_StarMax(15),
    V_StarCF(16),
    V_Triangle(17),
    V_TriangleMax(18),
    V_TriangleDCValue(19),
    A_Triangle(20),
    A_Max(21),
    A_CF(22),
    V_HalfWave(23),
    V_FundWave(24),
    A_FundWave(25),
    V_neg(26),
    A_neg(27),
    V_zero(28),
    A_Vzero(29),
    Phase_KW(30),
    Phase_Kvar(31),
    Phase_KVA(32),
    Phase_ThreeKW(33),
    Phase_Pf(34),
    Phase_ThreeKVA(35),
    V_Max(36),
    V_DC(37);

    private int value = 0;

    private PhaseType(int value) {
        this.value = value;
    }

    public static PhaseType valueOf(int value) {
        switch(value) {
            case 1:
                return A_Value;
            case 2:
                return V_Value;
            case 3:
                return A_Angle;
            case 4:
                return V_Angle;
            case 5:
                return A_Percentage;
            case 6:
                return V_Percentage;
            case 7:
                return A_GraphWaveform;
            case 8:
                return V_GraphWaveform;
            case 9:
                return V_THD;
            case 10:
                return A_THD;
            case 11:
                return A_GraphHarmonic;
            case 12:
                return V_GraphHarmonic;
            case 13:
                return W_GraphHarmonic;
            case 14:
                return V_Star;
            case 15:
                return V_StarMax;
            case 16:
                return V_StarCF;
            case 17:
                return V_Triangle;
            case 18:
                return V_TriangleMax;
            case 19:
                return V_TriangleDCValue;
            case 20:
                return A_Triangle;
            case 21:
                return A_Max;
            case 22:
                return A_CF;
            case 23:
                return V_HalfWave;
            case 24:
                return V_FundWave;
            case 25:
                return A_FundWave;
            case 26:
                return V_neg;
            case 27:
                return A_neg;
            case 28:
                return V_zero;
            case 29:
                return A_Vzero;
            case 30:
                return Phase_KW;
            case 31:
                return Phase_Kvar;
            case 32:
                return Phase_KVA;
            case 33:
                return Phase_ThreeKW;
            case 34:
                return Phase_Pf;
            case 35:
                return Phase_ThreeKVA;
            case 36:
                return V_Max;
            default:
                return None;
        }
    }

    public int value() {
        return this.value;
    }
}
