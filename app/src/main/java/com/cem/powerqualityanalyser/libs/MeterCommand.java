package com.cem.powerqualityanalyser.libs;

public enum MeterCommand {
    None(0),
    AllParameter(225),
    VA_Vector(226),
    VA_Waveform(227),
    ThreeHarmonic(228),
    Hz_400(229),
    Inverter(230),
    Sudden_UP_Down(231),
    Surge_A(232),
    Flicker(233),
    Transient(234),
    ThreeUnbalanced(235),
    Debug_B0(176),
    Debug_B1(177),
    Debug_B2(178),
    Debug_B3(179),
    Debug_B4(180),
    Debug_B5(181),
    Debug_B6(182),
    Debug_B7(183);

    private int value = 0;

    private MeterCommand(int value) {
        this.value = value;
    }

    public static MeterCommand valueOf(int value) {
        switch(value) {
            case 176:
                return Debug_B0;
            case 177:
                return Debug_B1;
            case 178:
                return Debug_B2;
            case 179:
                return Debug_B3;
            case 180:
                return Debug_B4;
            case 181:
                return Debug_B5;
            case 182:
                return Debug_B6;
            case 183:
                return Debug_B7;
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            case 198:
            case 199:
            case 200:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 213:
            case 214:
            case 215:
            case 216:
            case 217:
            case 218:
            case 219:
            case 220:
            case 221:
            case 222:
            case 223:
            case 224:
            default:
                return None;
            case 225:
                return AllParameter;
            case 226:
                return VA_Vector;
            case 227:
                return VA_Waveform;
            case 228:
                return ThreeHarmonic;
            case 229:
                return Hz_400;
            case 230:
                return Inverter;
            case 231:
                return Sudden_UP_Down;
            case 232:
                return Surge_A;
            case 233:
                return Flicker;
            case 234:
                return Transient;
            case 235:
                return ThreeUnbalanced;
        }
    }

    public int value() {
        return this.value;
    }
}
