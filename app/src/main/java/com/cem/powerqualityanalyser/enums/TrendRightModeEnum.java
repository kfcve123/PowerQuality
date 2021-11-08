package com.cem.powerqualityanalyser.enums;

public enum TrendRightModeEnum {
    NONE(0,"NONE"),
    //3QWYE
    //3Ø HIGH LEG
    //2½-ELEMENT
    V4L1L2L3N(1,"4VL1L2L3N"),
    A4L1L2L3N(2,"4AL1L2L3N"),

    //3Ø DELTA
    //2-ELEMENT
    //3ØIT
    //3Ø OPEN LEG
    U3L1L2L2L3L3L1(3,"3UL1L2L2L3L3L1"),
    A3L1L2L2L3L3L1(4,"3AL1L2L2L3L3L1"),

    //1Ø SPLIT PHASE
    V3L1L2N(5,"3VL1L2N"),
    A3L1L2N(6,"3AL1L2N"),

    //1Ø +NEUTRAL
    V2L1N(7,"2VL1N"),
    A2L1N(8,"2AL1N"),


    VL1(9,"L1"),
    VL2(10,"L2"),
    VL3(11,"L3"),

    AL1(12,"L1"),
    AL2(13,"L2"),
    AL3(14,"L3"),

    L1L2(15,"L1L2"),
    L2L3(16,"L2L3"),
    L3L1(17,"L3L1"),

    FL1(18,"L1"),

    N(19,"N");

    private int datavalue = 0;
    private String valueStr = "";

    private TrendRightModeEnum(int value, String str) {
        this.datavalue = value;
        this.valueStr = str;
    }

    public static TrendRightModeEnum valueOf(int value) {
        for (TrendRightModeEnum s : TrendRightModeEnum.values()) {
            if (s.Value() == value) {
                return s;
            }
        }
        return V4L1L2L3N;
    }

    public int Value() {
        return this.datavalue;
    }

    public String ValueStr() {
        return this.valueStr;
    }


}
