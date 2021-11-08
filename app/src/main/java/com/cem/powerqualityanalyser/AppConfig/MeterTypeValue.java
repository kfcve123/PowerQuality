package com.cem.powerqualityanalyser.AppConfig;



public enum MeterTypeValue {
    None(0),
    Scope(1),//示波器
    VoltsAmpsHertz(2),//电压电流频率--
    PowerEnergy(3),//功率电能--
    Harmonics(4),//谐波--
    Unbalance(5),//不平衡--
    Warning(6),//警告
    Flicker(7),//闪变--
    Transient(8);//瞬变--，后续模块视情况补充

    private int value = 0;

    private MeterTypeValue(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static MeterTypeValue valueOf(int value) {

        for (MeterTypeValue s : MeterTypeValue.values()) {
            if (s.value == value) {
                return s;
            }
        }
        return None;


    }


}
