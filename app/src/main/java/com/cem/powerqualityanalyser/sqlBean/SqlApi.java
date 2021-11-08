package com.cem.powerqualityanalyser.sqlBean;

public class SqlApi {
    public static final int MAX_LENGTH = 10000;

    public static final int PhaseType_A = 1;
    public static final int PhaseType_B = 2;
    public static final int PhaseType_C = 3;
    public static final int PhaseType_N = 4;
    //没有相的概念
    public static final int PhaseType_None = 5;


    public static final int Parameter_Urms = 201;
    public static final int Parameter_Uabc = 202;
    public static final int Parameter_Upk = 203;
    public static final int Parameter_Ipk = 204;
    public static final int Parameter_Freq = 205;
    public static final int Parameter_Irms = 206;
    public static final int Parameter_PF = 207;
    public static final int Parameter_Udc = 208;

    public static final int Parameter_Vfound = 209;
    public static final int Parameter_Afound = 210;
    public static final int Parameter_FaiV = 211;
    public static final int Parameter_FaiA = 212;

    public static final int Parameter_Urms2 = 213;
    public static final int Parameter_UthdList = 214;
    public static final int Parameter_IthdList = 215;
    public static final int Parameter_Ithd = 216;
    public static final int Parameter_Uthd = 217;
    public static final int Parameter_Irms2 = 218;



    public static final int Mode_Power = 101;
    public static final int Mode_Unbalance = 102;
    public static final int Mode_Dip = 103;
    public static final int Mode_Harmonic = 104;
    public static final int Mode_Shipboard = 105;
    public static final int Mode_Inrush = 106;



    /*----记录/查看----*/


    public static final int Mode_Record_VoltAmp = 101;
    public static final int Mode_Record_Flicker = 102;
    public static final int Mode_Record_Frequency = 103;
    public static final int Mode_Record_VoltHarmonic = 104;
    public static final int Mode_Record_AmpHarmonic = 105;
    public static final int Mode_Record_Unbalance = 106;
    public static final int Mode_Record_Power = 107;
    public static final int Mode_Record_Energy = 108;


    public static final int Parameter_Record_Urms = 201;
    public static final int Parameter_Record_Vrms = 202;
    public static final int Parameter_Record_Arms = 203;

    public static final int Parameter_Record_Ucf = 204;
    public static final int Parameter_Record_Vcf = 205;
    public static final int Parameter_Record_Acf = 206;

    public static final int Parameter_Record_Udc = 207;
    public static final int Parameter_Record_Vdc = 208;
    public static final int Parameter_Record_Adc = 209;

    public static final int Parameter_Record_Upeakjia = 210;
    public static final int Parameter_Record_Vpeakjia = 211;
    public static final int Parameter_Record_Apeakjia = 212;

    public static final int Parameter_Record_Upeakjian = 213;
    public static final int Parameter_Record_Vpeakjian = 214;
    public static final int Parameter_Record_Apeakjian = 215;

    public static final int Parameter_Record_Pst1min = 216;
    public static final int Parameter_Record_Pst = 217;
    public static final int Parameter_Record_Plt = 218;
    public static final int Parameter_Record_Pinst = 219;

    public static final int Parameter_Record_Hz = 220;

    public static final int Parameter_Record_Vfund = 221;
    public static final int Parameter_Record_VoltThdf = 222;
    public static final int Parameter_Record_VoltThdr = 223;
    public static final int Parameter_Record_VoltDc = 224;

    public static final int Parameter_Record_VoltH1 = 225;
    public static final int Parameter_Record_VoltH2 = 226;
    public static final int Parameter_Record_VoltH3 = 227;
    public static final int Parameter_Record_VoltH4 = 228;
    public static final int Parameter_Record_VoltH5 = 229;
    public static final int Parameter_Record_VoltH6 = 230;
    public static final int Parameter_Record_VoltH7 = 231;
    public static final int Parameter_Record_VoltH8 = 232;
    public static final int Parameter_Record_VoltH9 = 233;
    public static final int Parameter_Record_VoltH10 = 234;
    public static final int Parameter_Record_VoltH11 = 235;
    public static final int Parameter_Record_VoltH12 = 236;
    public static final int Parameter_Record_VoltH13 = 237;
    public static final int Parameter_Record_VoltH14 = 238;
    public static final int Parameter_Record_VoltH15 = 239;
    public static final int Parameter_Record_VoltH16 = 240;
    public static final int Parameter_Record_VoltH17 = 241;
    public static final int Parameter_Record_VoltH18 = 242;
    public static final int Parameter_Record_VoltH19 = 243;
    public static final int Parameter_Record_VoltH20 = 244;
    public static final int Parameter_Record_VoltH21 = 245;
    public static final int Parameter_Record_VoltH22 = 246;
    public static final int Parameter_Record_VoltH23 = 247;
    public static final int Parameter_Record_VoltH24 = 248;
    public static final int Parameter_Record_VoltH25 = 249;

    public static final int Parameter_Record_VoltH26 = 250;
    public static final int Parameter_Record_VoltH27 = 251;
    public static final int Parameter_Record_VoltH28 = 252;
    public static final int Parameter_Record_VoltH29 = 253;
    public static final int Parameter_Record_VoltH30 = 254;
    public static final int Parameter_Record_VoltH31 = 255;
    public static final int Parameter_Record_VoltH32 = 256;
    public static final int Parameter_Record_VoltH33 = 257;
    public static final int Parameter_Record_VoltH34 = 258;
    public static final int Parameter_Record_VoltH35 = 259;
    public static final int Parameter_Record_VoltH36 = 260;
    public static final int Parameter_Record_VoltH37 = 261;
    public static final int Parameter_Record_VoltH38 = 262;
    public static final int Parameter_Record_VoltH39 = 263;
    public static final int Parameter_Record_VoltH40 = 264;
    public static final int Parameter_Record_VoltH41 = 265;
    public static final int Parameter_Record_VoltH42 = 266;
    public static final int Parameter_Record_VoltH43 = 267;
    public static final int Parameter_Record_VoltH44 = 268;
    public static final int Parameter_Record_VoltH45 = 269;
    public static final int Parameter_Record_VoltH46 = 270;
    public static final int Parameter_Record_VoltH47 = 271;
    public static final int Parameter_Record_VoltH48 = 272;
    public static final int Parameter_Record_VoltH49 = 273;
    public static final int Parameter_Record_VoltH50 = 274;

    public static final int Parameter_Record_Afund = 275;
    public static final int Parameter_Record_AmpThdf = 276;
    public static final int Parameter_Record_AmpThdr = 277;
    public static final int Parameter_Record_AmpDc = 278;

    public static final int Parameter_Record_AmpH1 = 279;
    public static final int Parameter_Record_AmpH2 = 280;
    public static final int Parameter_Record_AmpH3 = 281;
    public static final int Parameter_Record_AmpH4 = 282;
    public static final int Parameter_Record_AmpH5 = 283;
    public static final int Parameter_Record_AmpH6 = 284;
    public static final int Parameter_Record_AmpH7 = 285;
    public static final int Parameter_Record_AmpH8 = 286;
    public static final int Parameter_Record_AmpH9 = 287;
    public static final int Parameter_Record_AmpH10 = 288;
    public static final int Parameter_Record_AmpH11 = 289;
    public static final int Parameter_Record_AmpH12 = 290;
    public static final int Parameter_Record_AmpH13 = 291;
    public static final int Parameter_Record_AmpH14 = 292;
    public static final int Parameter_Record_AmpH15 = 293;
    public static final int Parameter_Record_AmpH16 = 294;
    public static final int Parameter_Record_AmpH17 = 295;
    public static final int Parameter_Record_AmpH18 = 296;
    public static final int Parameter_Record_AmpH19 = 297;
    public static final int Parameter_Record_AmpH20 = 298;
    public static final int Parameter_Record_AmpH21 = 299;
    public static final int Parameter_Record_AmpH22 = 300;
    public static final int Parameter_Record_AmpH23 = 301;
    public static final int Parameter_Record_AmpH24 = 302;
    public static final int Parameter_Record_AmpH25 = 303;

    public static final int Parameter_Record_AmpH26 = 304;
    public static final int Parameter_Record_AmpH27 = 305;
    public static final int Parameter_Record_AmpH28 = 306;
    public static final int Parameter_Record_AmpH29 = 307;
    public static final int Parameter_Record_AmpH30 = 308;
    public static final int Parameter_Record_AmpH31 = 309;
    public static final int Parameter_Record_AmpH32 = 310;
    public static final int Parameter_Record_AmpH33 = 311;
    public static final int Parameter_Record_AmpH34 = 312;
    public static final int Parameter_Record_AmpH35 = 313;
    public static final int Parameter_Record_AmpH36 = 314;
    public static final int Parameter_Record_AmpH37 = 315;
    public static final int Parameter_Record_AmpH38 = 316;
    public static final int Parameter_Record_AmpH39 = 317;
    public static final int Parameter_Record_AmpH40 = 318;
    public static final int Parameter_Record_AmpH41 = 319;
    public static final int Parameter_Record_AmpH42 = 320;
    public static final int Parameter_Record_AmpH43 = 321;
    public static final int Parameter_Record_AmpH44 = 322;
    public static final int Parameter_Record_AmpH45 = 323;
    public static final int Parameter_Record_AmpH46 = 324;
    public static final int Parameter_Record_AmpH47 = 325;
    public static final int Parameter_Record_AmpH48 = 326;
    public static final int Parameter_Record_AmpH49 = 327;
    public static final int Parameter_Record_AmpH50 = 328;

    public static final int Parameter_Record_Unbal = 329;
    public static final int Parameter_Record_Vneg = 330;
    public static final int Parameter_Record_Vzero = 331;
    public static final int Parameter_Record_Aneg = 332;
    public static final int Parameter_Record_Azero = 333;

    public static final int Parameter_Record_kW = 334;
    public static final int Parameter_Record_kVA = 335;
    public static final int Parameter_Record_kvar = 336;
    public static final int Parameter_Record_kVAHarm = 337;
    public static final int Parameter_Record_cos = 338;
    public static final int Parameter_Record_kVAUnb = 339;
    public static final int Parameter_Record_kWfund = 340;
    public static final int Parameter_Record_kVAfund = 341;
    public static final int Parameter_Record_Wfund = 342;


    public static final int Parameter_Record_kVAh = 343;
    public static final int Parameter_Record_kWhForw = 344;
    public static final int Parameter_Record_kWh = 345;
    public static final int Parameter_Record_kvarh = 346;
    public static final int Parameter_Record_kWhRev = 347;




    public static final int Inrush_History_Vrms = 348;
    public static final int Inrush_History_Arms = 349;

    public static final int DipsSwells_History_Vrms = 350;
    public static final int DipsSwells_History_Arms = 351;


}
