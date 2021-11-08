package com.cem.powerqualityanalyser.userobject;

public enum MeterKeyValue {
    None(0),
    Scope(131),
    Menu(132),
    Logger(133),
    IR(1),
    Power(2),
    Back(4),
    F1(135),
    F2(138),
    F3(139),
    F4(140),
    F5(141),
    Light(142),
    Setup(136),
    Memory(137),
    Save(134),
    Up(19),
    Down(20),
    Left(21),
    Right(22),
    Enter(23);//Enter未知
    private int value = 0;
    private MeterKeyValue(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }
    public int value() {
        return this.value;
    }

    public static MeterKeyValue valueOf(int value) {

        for (MeterKeyValue s : MeterKeyValue.values()){
            if ( s.value==value){
                return s;
            }
        }
        return None;


    }

}
