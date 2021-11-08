package com.cem.powerqualityanalyser.AppConfig;


import android.content.Context;

public class AppConfig  extends  BaseConfig {

    public static final String MainPutActivityNameKey = "putName";
    private static AppConfig instance;
    private boolean lockScreen = false;
    private int maxZoom = 1;

    private static final String Setup_System_Brightness = "Setup_System_Brightness";
    private int default_brightness = 128;


    private static final String Setup_System_Automatic_Screen = "Setup_System_Automatic_Screen";
    private boolean default_automatic_screen = true;

    private static final String Setup_Night_Mode = "Setup_Night_Mode";
    private boolean setup_Night_Mode = true;

    private static final String Setup_System_Language = "Setup_System_Language";
    private int default_language = 0;

    private static final String Setup_Show_Color_VL1 = "Setup_Show_Color_VL1";
    private int setup_Show_Color_VL1 = 1;

    private static final String Setup_Show_Color_VL2 = "Setup_Show_Color_VL2";
    private int setup_Show_Color_VL2 = 2;

    private static final String Setup_Show_Color_VL3 = "Setup_Show_Color_VL3";
    private int setup_Show_Color_VL3 = 3;

    private static final String Setup_Show_Color_VN = "Setup_Show_Color_VN";
    private int setup_Show_Color_VN = 4;

    private static final String Setup_Show_Color_AL1 = "Setup_Show_Color_AL1";
    private int setup_Show_Color_AL1 = 1;

    private static final String Setup_Show_Color_AL2 = "Setup_Show_Color_AL2";
    private int setup_Show_Color_AL2 = 2;

    private static final String Setup_Show_Color_AL3 = "Setup_Show_Color_AL3";
    private int setup_Show_Color_AL3 = 3;

    private static final String Setup_Show_Color_AN = "Setup_Show_Color_AN";
    private int setup_Show_Color_AN = 4;

    public int getSetup_Show_Color_VL1() {
        return setup_Show_Color_VL1;
    }

    public void setSetup_Show_Color_VL1(int setup_Show_Color_VL1) {
        this.setup_Show_Color_VL1 = setup_Show_Color_VL1;
        saveInt(Setup_Show_Color_VL1,setup_Show_Color_VL1);
    }

    public int getSetup_Show_Color_VL2() {
        return setup_Show_Color_VL2;
    }

    public void setSetup_Show_Color_VL2(int setup_Show_Color_VL2) {
        this.setup_Show_Color_VL2 = setup_Show_Color_VL2;
        saveInt(Setup_Show_Color_VL2,setup_Show_Color_VL2);
    }

    public int getSetup_Show_Color_VL3() {
        return setup_Show_Color_VL3;
    }

    public void setSetup_Show_Color_VL3(int setup_Show_Color_VL3) {
        this.setup_Show_Color_VL3 = setup_Show_Color_VL3;
        saveInt(Setup_Show_Color_VL3,setup_Show_Color_VL3);
    }

    public int getSetup_Show_Color_VN() {
        return setup_Show_Color_VN;
    }

    public void setSetup_Show_Color_VN(int setup_Show_Color_VN) {
        this.setup_Show_Color_VN = setup_Show_Color_VN;
        saveInt(Setup_Show_Color_VN,setup_Show_Color_VN);
    }

    public int getSetup_Show_Color_AL1() {
        return setup_Show_Color_AL1;
    }

    public void setSetup_Show_Color_AL1(int setup_Show_Color_AL1) {
        this.setup_Show_Color_AL1 = setup_Show_Color_AL1;
        saveInt(Setup_Show_Color_AL1,setup_Show_Color_AL1);
    }

    public int getSetup_Show_Color_AL2() {
        return setup_Show_Color_AL2;
    }

    public void setSetup_Show_Color_AL2(int setup_Show_Color_AL2) {
        this.setup_Show_Color_AL2 = setup_Show_Color_AL2;
        saveInt(Setup_Show_Color_AL2,setup_Show_Color_AL2);
    }

    public int getSetup_Show_Color_AL3() {
        return setup_Show_Color_AL3;
    }

    public void setSetup_Show_Color_AL3(int setup_Show_Color_AL3) {
        this.setup_Show_Color_AL3 = setup_Show_Color_AL3;
        saveInt(Setup_Show_Color_AL3,setup_Show_Color_AL3);
    }

    public int getSetup_Show_Color_AN() {
        return setup_Show_Color_AN;
    }

    public void setSetup_Show_Color_AN(int setup_Show_Color_AN) {
        this.setup_Show_Color_AN = setup_Show_Color_AN;
        saveInt(Setup_Show_Color_AN,setup_Show_Color_AN);
    }




    private static final String InrushSet_Default = "InrushSet_Default";
    private boolean inrushSet_Default = true;

    private static final String InrushSet_Duration ="InrushSet_Duration";
    private int inrushSet_Duration = 1;
    private static final String InrushSet_Amps_Norminal ="InrushSet_Amps_Norminal";
    private int inrushSet_Amps_Norminal = 1;
    private static final String InrushSet_Threshold ="InrushSet_Threshold";
    private int inrushSet_Threshold = 1;
    private static final String InrushSet_Hysteresis ="InrushSet_Hysteresis";
    private int inrushSet_Hysteresis = 1;


    private static final String DipsSet_Nominal ="DipsSet_Nominal";
    private int dipsSet_Nominal = 1;
    private static final String DipsSet_Threshold ="DipsSet_Threshold";
    private int dipsSet_Threshold = 1;
    private static final String DipsSet_Hysteresis ="DipsSet_Hysteresis";
    private int dipsSet_Hysteresis = 1;

    private static final String SwellsSet_Nominal ="SwellsSet_Nominal";
    private int swellsSet_Nominal = 1;
    private static final String SwellsSet_Threshold ="SwellsSet_Threshold";
    private int swellsSet_Threshold = 1;
    private static final String SwellsSet_Hysteresis ="SwellsSet_Hysteresis";
    private int swellsSet_Hysteresis = 1;




    private static final String Setup_Conect_Method = "Setup_Conect_Method";
    private int default_method = 0;


    private static final String Monitor_Volt_ExtremeProbability ="Monitor_Volt_ExtremeProbability";
    private int monitor_Volt_ExtremeProbability = 1;
    private static final String Monitor_Volt_IpperLimit ="Monitor_Volt_IpperLimit";
    private int monitor_Volt_IpperLimit = 1;
    private static final String Monitor_Volt_LowerLimit ="Monitor_Volt_LowerLimit";
    private int monitor_Volt_LowerLimit = 1;

    private static final String Monitor_Volt_UpperLimit ="Monitor_Volt_UpperLimit";
    private int monitor_Volt_UpperLimit = 1;
    private static final String Monitor_Volt_LowerLimit2 ="Monitor_Volt_LowerLimit";
    private int monitor_Volt_LowerLimit2 = 1;

    private static final String Monitor_SwellsSet_Threshold ="Monitor_SwellsSet_Threshold";
    private int monitor_swellsSet_Threshold = 1;
    private static final String Monitor_SwellsSet_Hysteresis ="Monitor_SwellsSet_Hysteresis";
    private int monitor_swellsSet_Hysteresis = 1;
    private static final String Monitor_SwellsSet_Threshold2 ="Monitor_SwellsSet_Threshold";
    private int monitor_swellsSet_Threshold2 = 1;
    private static final String Monitor_SwellsSet_Hysteresis2 ="Monitor_SwellsSet_Hysteresis";
    private int monitor_swellsSet_Hysteresis2 = 1;

    private static final String Monitor_FreSet_Upper ="Monitor_FreSet_Upper";
    private int monitor_fresSet_Upper = 1;
    private static final String Monitor_FreSet_Lower ="Monitor_FreSet_Lower";
    private int monitor_fresSet_Lower = 1;
    private static final String Monitor_FreSet_Upper2 ="Monitor_FreSet_Upper2";
    private int monitor_fresSet_Upper2 = 1;
    private static final String Monitor_FreSet_Lower2 ="Monitor_FreSet_Lower2";
    private int monitor_fresSet_Lower2 = 1;
    private static final String Monitor_FreSet_Probability ="Monitor_FreSet_Probability";
    private int monitor_FreSet_Probability = 1;


    private static final String Monitor_Harmonic_Number ="Monitor_Harmonic_Number";
    private int monitor_Harmonic_Number = 0;
    private static final String Monitor_UnblanceSet_Probability ="Monitor_UnblanceSet_Probability";
    private int monitor_UnblanceSet_Probability = 1;
    private static final String Monitor_UnblanceSet_Upper ="Monitor_UnblanceSet_Upper";
    private int monitor_UnblanceSet_Upper = 1;
    private static final String Monitor_UnblanceSet_Upper2 ="Monitor_UnblanceSet_Upper2";
    private int monitor_UnblanceSet_Upper2 = 1;

    private static final String Monitor_HarmonicSet_Probability ="Monitor_HarmonicSet_Probability";
    private int monitor_HarmonicSet_Probability = 1;
    private static final String Monitor_HarmonicSet_Upper ="Monitor_HarmonicSet_Upper";
    private int monitor_HarmonicSet_Upper = 1;
    private static final String Monitor_HarmonicSet_Upper2 ="Monitor_HarmonicSet_Upper2";
    private int monitor_HarmonicSet_Upper2 = 1;
    private static final String Monitor_First_Volt ="Monitor_First_Volt";
    private boolean monitor_First_Volt = true;
    private static final String Monitor_First_Freq ="Monitor_First_Freq";
    private boolean monitor_First_Freq = true;
    private static final String Monitor_First_Duration ="Monitor_First_Duration";
    private int monitor_First_Duration = 0;
    private static final String Monitor_First_Moudle ="Monitor_First_Moudle";
    private int monitor_First_Moudle = 1;

    /**
     * 接线方式
     */
    private static final String Setup_Wir = "Setup_Wir";
    private int setup_wir = 0;

    public int getSet_Wir() {
        return setup_wir;
    }

    public void setSet_Wir(int setup_wir) {
        this.setup_wir = setup_wir;
        saveInt(Setup_Wir,setup_wir);
    }

    private static final String Setup_Wir_Right_Index = "Setup_Wir_Right_Index";
    private int setup_Wir_Right_Index = 0;

    public int getSetup_Wir_Right_Index() {
        return setup_Wir_Right_Index;
    }

    public void setSetup_Wir_Right_Index(int setup_Wir_Right_Index) {
        this.setup_Wir_Right_Index = setup_Wir_Right_Index;
        saveInt(Setup_Wir_Right_Index,setup_Wir_Right_Index);
    }



    private static final String Setup_Caluc_PLT = "Setup_Caluc_PLT";
    private boolean setup_Caluc_plt = true;

    public boolean isSetup_Caluc_plt() {
        return setup_Caluc_plt;
    }

    public void setSetup_Caluc_plt(boolean setup_Caluc_plt) {
        this.setup_Caluc_plt = setup_Caluc_plt;
        saveBoolean(Setup_Caluc_PLT,setup_Caluc_plt);
    }


    private static final String Setup_Caluc_Fr = "Setup_Caluc_Fr";
    private boolean setup_Caluc_fr = true;

    public boolean isSetup_Caluc_fr() {
        return setup_Caluc_fr;
    }

    public void setSetup_Caluc_fr(boolean setup_Caluc_fr) {
        this.setup_Caluc_fr = setup_Caluc_fr;
        saveBoolean(Setup_Caluc_Fr,setup_Caluc_fr);
    }


    private static final String Setup_Caluc_Var = "Setup_Caluc_Var";
    private boolean setup_Caluc_Var = true;

    public boolean isSetup_Caluc_Var() {
        return setup_Caluc_Var;
    }

    public void setSetup_Caluc_Var(boolean setup_Caluc_Var) {
        this.setup_Caluc_Var = setup_Caluc_Var;
        saveBoolean(Setup_Caluc_Var,setup_Caluc_Var);
    }

    private static final String Set_Caluc_Wh = "Set_Caluc_Wh";
    private int set_Caluc_Wh = 0;

    public int getSet_Caluc_Wh() {
        return set_Caluc_Wh;
    }

    public void setSet_Caluc_Wh(int set_Caluc_Wh) {
        this.set_Caluc_Wh = set_Caluc_Wh;
        saveInt(Set_Caluc_Wh,set_Caluc_Wh);
    }

    private static final String Set_Caluc_Fk_e = "Set_Caluc_Fk_e";
    private int set_Caluc_Fk_e = 0;

    public int getSet_Caluc_Fk_e() {
        return set_Caluc_Fk_e;
    }

    public void setSet_Caluc_Fk_e(int set_Caluc_Fk_e) {
        this.set_Caluc_Fk_e = set_Caluc_Fk_e;
        saveInt(Set_Caluc_Fk_e,set_Caluc_Fk_e);
    }

    private static final String Set_Caluc_Fk_q = "Set_Caluc_Fk_q";
    private int set_Caluc_Fk_q = 0;

    public int getSet_Caluc_Fk_q() {
        return set_Caluc_Fk_q;
    }

    public void setSet_Caluc_Fk_q(int set_Caluc_Fk_q) {
        this.set_Caluc_Fk_q = set_Caluc_Fk_q;
        saveInt(Set_Caluc_Fk_q,set_Caluc_Fk_q);
    }

    private static final String Config_Nominal = "Config_Nominal";
    private int config_nominal = 0;

    public int getConfig_nominal() {
        return config_nominal;
    }

    public void setConfig_nominal(int config_nominal) {
        this.config_nominal = config_nominal;
        saveInt(Config_Nominal,config_nominal);
    }


    private static final String Config_Vnom = "Config_Vnom";
    private int config_vnom = 0;

    public int getConfig_vnom() {
        return config_vnom;
    }

    public void setConfig_vnom(int config_vnom) {
        this.config_vnom = config_vnom;
        saveInt(Config_Vnom,config_vnom);
    }

    private static final String Config_Vnom_Value = "Config_Vnom_Value";
    private String config_vnom_value = "58V";

    public String getConfig_vnom_value() {
        return config_vnom_value;
    }

    public void setConfig_vnom_value(String config_vnom_value) {
        this.config_vnom_value = config_vnom_value;
        saveString(Config_Vnom_Value,config_vnom_value);
    }

    @Override
    public void Init(Context contxt) {
        super.Init(contxt);

        setup_Caluc_plt = readBoolean(Setup_Caluc_PLT,true);
        setup_Caluc_fr = readBoolean(Setup_Caluc_Fr,true);
        setup_Caluc_Var = readBoolean(Setup_Caluc_Var,true);

        config_nominal = readInt(Config_Nominal);
        config_vnom = readInt(Config_Vnom);
        config_vnom_value = settings.getString(Config_Vnom_Value,"58V");

        set_Caluc_Fk_e = readInt(Set_Caluc_Fk_e);
        set_Caluc_Fk_q = readInt(Set_Caluc_Fk_q);
        set_Caluc_Wh = readInt(Set_Caluc_Wh);


        default_brightness =  settings.getInt(Setup_System_Brightness, 128);
        default_automatic_screen = readBoolean(Setup_System_Automatic_Screen,true);
        setup_Night_Mode = readBoolean(Setup_Night_Mode,true);
        default_language = readInt(Setup_System_Language);

        setup_Show_Color_VL1 = settings.getInt(Setup_Show_Color_VL1,1);
        setup_Show_Color_VL2 = settings.getInt(Setup_Show_Color_VL2,2);
        setup_Show_Color_VL3= settings.getInt(Setup_Show_Color_VL3,3);
        setup_Show_Color_VN = settings.getInt(Setup_Show_Color_VN,4);

//        setup_Show_Color_AL1 = settings.getInt(Setup_Show_Color_AL1,1);
//        setup_Show_Color_AL2 = settings.getInt(Setup_Show_Color_AL2,2);
//        setup_Show_Color_AL3 = settings.getInt(Setup_Show_Color_AL3,3);
//        setup_Show_Color_AN = settings.getInt(Setup_Show_Color_AN,4);


        setup_wir = settings.getInt(Setup_Wir, 0);
        setup_Wir_Right_Index = readInt(Setup_Wir_Right_Index);

        config_neutral_amp = readInt(Config_Neutral_Amp,2000);
        config_phase_amp = readInt(Config_Phase_Amp,2000);
        config_neutral_volt = readInt(Config_Neutral_Volt,400);
        config_phase_volt = readInt(Config_Phase_Volt,400);
        config_ScopeScale_Default = readBoolean(Config_ScopeScale_Default,true);

        /**
         * ConfigAmpsInfo set
         */
        config_ampScale_phase_ampclamp = readInt(Config_AmpScale_Phase_AmpClamp,1);
        config_ampScale_phase_norminal = readInt(Config_AmpScale_Phase_Norminal,3000);
        config_ampScale_phase_sen = readInt(Config_AmpScale_Phase_Sensitivity,1);
        config_ampScale_phase_ratio = readInt(Config_AmpScale_Phase_Ratio,1);

        config_ampScale_neutral_ampclamp = readInt(Config_AmpScale_Neutral_AmpClamp,1);
        config_ampScale_neutral_norminal = readInt(Config_AmpScale_Neutral_Norminal,3000);
        config_ampScale_neutral_sen = readInt(Config_AmpScale_Neutral_Sensitivity,1);
        config_ampScale_neutral_ratio = readInt(Config_AmpScale_Neutral_Ratio,1);

        config_voltScale_phase_volt = readInt(Config_VoltScale_Phase_VoltRatio,1);
        config_voltScale_neutral_volt = readInt(Config_VoltScale_Phase_VoltRatio,1);

        /*-------------------*/

        inrushSet_Default = readBoolean(InrushSet_Default,true);
        inrushSet_Duration = readInt(InrushSet_Duration);
        inrushSet_Amps_Norminal = readInt(InrushSet_Amps_Norminal);
        inrushSet_Threshold = readInt(InrushSet_Threshold);
        inrushSet_Hysteresis = readInt(InrushSet_Hysteresis);

        dipsSet_Default = readBoolean(DipsSet_Default,true);
        dipswell_trigger_voltage = readBoolean(DipSwell_Trigger_Voltage,true);
        dipswell_trigger_amps = readBoolean(DipSwell_Trigger_AMPS,true);
        dipswell_trigger_amplevel = readInt(DipSwell_Trigger_AmpLevel,1);
        dipswell_trigger_voltlevel = readInt(DipSwell_Trigger_VoltLevel,1);


        dipswell_time_immediate = readBoolean(DipSwell_Time_Immediate,true);
        dipswell_time_year = readInt(DipSwell_Time_Year,1);
        dipswell_time_month = readInt(DipSwell_Time_Month,1);
        dipswell_time_day = readInt(DipSwell_Time_Day,1);
        dipswell_time_hours = readInt(DipSwell_Time_Hours,1);
        dipswell_time_minutes = readInt(DipSwell_Time_Minutes,1);



        transientSet_Default = readBoolean(TransientSet_Default,true);
        transient_trigger_voltage = readBoolean(Transient_Trigger_Voltage,true);
        transient_trigger_amps = readBoolean(Transient_Trigger_AMPS,true);
        transient_trigger_amplevel = readInt(Transient_Trigger_AmpLevel,1);
        transient_trigger_voltlevel = readInt(Transient_Trigger_VoltLevel,1);

        transient_time_immediate = readBoolean(Transient_Time_Immediate,true);
        transient_time_year = readInt(Transient_Time_Year,1);
        transient_time_month = readInt(Transient_Time_Month,1);
        transient_time_day = readInt(Transient_Time_Day,1);
        transient_time_hours = readInt(Transient_Time_Hours,1);
        transient_time_minutes = readInt(Transient_Time_Minutes,1);



        powerMonitor_time_immediate = readBoolean(PowerMonitor_Time_Immediate,true);
        powerMonitor_time_year = readInt(PowerMonitor_Time_Year,1);
        powerMonitor_time_month = readInt(PowerMonitor_Time_Month,1);
        powerMonitor_time_day = readInt(PowerMonitor_Time_Day,1);
        powerMonitor_time_hours = readInt(PowerMonitor_Time_Hours,1);
        powerMonitor_time_minutes = readInt(PowerMonitor_Time_Minutes,1);

        powerMonitor_duratrion = readInt(PowerMonitor_Duration,1);

        dipsSet_Nominal = readInt(DipsSet_Nominal);
        dipsSet_Threshold = readInt(DipsSet_Threshold);
        dipsSet_Hysteresis = readInt(DipsSet_Hysteresis);

        swellsSet_Nominal = readInt(SwellsSet_Nominal);
        swellsSet_Threshold = readInt(SwellsSet_Threshold);
        swellsSet_Hysteresis = readInt(SwellsSet_Hysteresis);


        default_method = readInt(Setup_Conect_Method);

        monitor_Volt_ExtremeProbability = readInt(Monitor_Volt_ExtremeProbability);
        monitor_Volt_IpperLimit = readInt(Monitor_Volt_IpperLimit);
        monitor_Volt_LowerLimit = readInt(Monitor_Volt_LowerLimit);
        monitor_Volt_UpperLimit = readInt(Monitor_Volt_UpperLimit);
        monitor_Volt_LowerLimit2 = readInt(Monitor_Volt_LowerLimit2);
        monitor_swellsSet_Threshold = readInt(Monitor_SwellsSet_Threshold);
        monitor_swellsSet_Threshold2 = readInt(Monitor_SwellsSet_Threshold2);
        monitor_swellsSet_Hysteresis = readInt(Monitor_SwellsSet_Hysteresis);
        monitor_swellsSet_Hysteresis2 = readInt(Monitor_SwellsSet_Hysteresis2);

        monitor_fresSet_Upper = readInt(Monitor_FreSet_Upper);
        monitor_fresSet_Upper2 = readInt(Monitor_FreSet_Upper2);
        monitor_fresSet_Lower = readInt(Monitor_FreSet_Lower);
        monitor_fresSet_Lower2 = readInt(Monitor_FreSet_Lower2);
        monitor_FreSet_Probability = readInt(Monitor_FreSet_Probability);

        monitor_Harmonic_Number = readInt(Monitor_Harmonic_Number);
        monitor_UnblanceSet_Probability = readInt(Monitor_UnblanceSet_Probability);
        monitor_UnblanceSet_Upper = readInt(Monitor_UnblanceSet_Upper);
        monitor_UnblanceSet_Upper2 = readInt(Monitor_UnblanceSet_Upper2);

        monitor_HarmonicSet_Probability = readInt(Monitor_HarmonicSet_Probability);
        monitor_HarmonicSet_Upper = readInt(Monitor_HarmonicSet_Upper);
        monitor_HarmonicSet_Upper2 = readInt(Monitor_HarmonicSet_Upper2);
        monitor_First_Freq = readBoolean(Monitor_First_Freq,true);
        monitor_First_Volt = readBoolean(Monitor_First_Volt,true);
        monitor_First_Duration = readInt(Monitor_First_Duration);
        monitor_First_Moudle = readInt(Monitor_First_Moudle);


    }

    public int getDefault_method(){
        return default_method;
    }

    public void setDefault_method(int default_method){
        this.default_method = default_method;
        saveInt(Setup_Conect_Method,default_method);
    }


    public boolean isInrushSet_Default() {
        return inrushSet_Default;
    }

    public void setInrushSet_Default(boolean inrushSet_Default) {
        this.inrushSet_Default = inrushSet_Default;
        saveBoolean(InrushSet_Default,inrushSet_Default);
    }

    public int getInrushSet_Duration() {
        return inrushSet_Duration;
    }

    public void setInrushSet_Duration(int inrushSet_Duration) {
        this.inrushSet_Duration = inrushSet_Duration;
        saveInt(InrushSet_Duration,inrushSet_Duration);
    }

    public int getInrushSet_Amps_Norminal() {
        return inrushSet_Amps_Norminal;
    }

    public void setInrushSet_Amps_Norminal(int inrushSet_Amps_Norminal) {
        this.inrushSet_Amps_Norminal = inrushSet_Amps_Norminal;
        saveInt(InrushSet_Amps_Norminal,inrushSet_Amps_Norminal);
    }

    public int getInrushSet_Threshold() {
        return inrushSet_Threshold;
    }

    public void setInrushSet_Threshold(int inrushSet_Threshold) {
        this.inrushSet_Threshold = inrushSet_Threshold;
        saveInt(InrushSet_Threshold,inrushSet_Threshold);
    }

    public int getInrushSet_Hysteresis() {
        return inrushSet_Hysteresis;
    }

    public void setInrushSet_Hysteresis(int inrushSet_Hysteresis) {
        this.inrushSet_Hysteresis = inrushSet_Hysteresis;
        saveInt(InrushSet_Hysteresis,inrushSet_Hysteresis);
    }

    public int getDipsSet_Nominal() {
        return dipsSet_Nominal;
    }

    public void setDipsSet_Nominal(int dipsSet_Nominal) {
        this.dipsSet_Nominal = dipsSet_Nominal;
        saveInt(DipsSet_Nominal,dipsSet_Nominal);
    }

    public int getDipsSet_Threshold() {
        return dipsSet_Threshold;
    }

    public void setDipsSet_Threshold(int dipsSet_Threshold) {
        this.dipsSet_Threshold = dipsSet_Threshold;
        saveInt(DipsSet_Threshold,dipsSet_Threshold);
    }

    public int getDipsSet_Hysteresis() {
        return dipsSet_Hysteresis;
    }

    public void setDipsSet_Hysteresis(int dipsSet_Hysteresis) {
        this.dipsSet_Hysteresis = dipsSet_Hysteresis;
        saveInt(DipsSet_Hysteresis,dipsSet_Hysteresis);
    }

    public int getSwellsSet_Nominal() {
        return swellsSet_Nominal;
    }

    public void setSwellsSet_Nominal(int swellsSet_Nominal) {
        this.swellsSet_Nominal = swellsSet_Nominal;
        saveInt(SwellsSet_Nominal,swellsSet_Nominal);
    }

    public int getSwellsSet_Threshold() {
        return swellsSet_Threshold;
    }

    public void setSwellsSet_Threshold(int swellsSet_Threshold) {
        this.swellsSet_Threshold = swellsSet_Threshold;
        saveInt(SwellsSet_Threshold,swellsSet_Threshold);
    }

    public int getSwellsSet_Hysteresis() {
        return swellsSet_Hysteresis;
    }

    public void setSwellsSet_Hysteresis(int swellsSet_Hysteresis) {
        this.swellsSet_Hysteresis = swellsSet_Hysteresis;
        saveInt(SwellsSet_Hysteresis,swellsSet_Hysteresis);
    }

    public int getMonitor_Volt_ExtremeProbability() {
        return monitor_Volt_ExtremeProbability;
    }

    public void setMonitor_Volt_ExtremeProbability(int monitor_Volt_ExtremeProbability) {
        this.monitor_Volt_ExtremeProbability = monitor_Volt_ExtremeProbability;
        saveInt(Monitor_Volt_ExtremeProbability,monitor_Volt_ExtremeProbability);
    }

    public int getMonitor_Volt_IpperLimit() {
        return monitor_Volt_IpperLimit;
    }

    public void setMonitor_Volt_IpperLimit(int monitor_Volt_IpperLimit) {
        this.monitor_Volt_IpperLimit = monitor_Volt_IpperLimit;
        saveInt(Monitor_Volt_IpperLimit,monitor_Volt_IpperLimit);
    }

    public int getMonitor_Volt_LowerLimit() {
        return monitor_Volt_LowerLimit;
    }

    public void setMonitor_Volt_LowerLimit(int monitor_Volt_LowerLimit) {
        this.monitor_Volt_LowerLimit = monitor_Volt_LowerLimit;
        saveInt(Monitor_Volt_LowerLimit,monitor_Volt_LowerLimit);
    }



    public int getMonitor_Volt_UpperLimit() {
        return monitor_Volt_UpperLimit;
    }

    public void setMonitor_Volt_UpperLimit(int monitor_Volt_UpperLimit) {
        this.monitor_Volt_UpperLimit = monitor_Volt_UpperLimit;
        saveInt(Monitor_Volt_UpperLimit,monitor_Volt_UpperLimit);

    }

    public int getMonitor_Volt_LowerLimit2() {
        return monitor_Volt_LowerLimit2;
    }

    public void setMonitor_Volt_LowerLimit2(int monitor_Volt_LowerLimit2) {
        this.monitor_Volt_LowerLimit2 = monitor_Volt_LowerLimit2;
        saveInt(Monitor_Volt_LowerLimit2,monitor_Volt_LowerLimit2);
    }

    public int getMonitor_swellsSet_Threshold() {
        return monitor_swellsSet_Threshold;
    }

    public void setMonitor_swellsSet_Threshold(int monitor_swellsSet_Threshold) {
        this.monitor_swellsSet_Threshold = monitor_swellsSet_Threshold;
        saveInt(Monitor_SwellsSet_Threshold,monitor_swellsSet_Threshold);
    }

    public int getMonitor_swellsSet_Hysteresis() {
        return monitor_swellsSet_Hysteresis;
    }

    public void setMonitor_swellsSet_Hysteresis(int monitor_swellsSet_Hysteresis) {
        this.monitor_swellsSet_Hysteresis = monitor_swellsSet_Hysteresis;
        saveInt(Monitor_SwellsSet_Hysteresis,monitor_swellsSet_Hysteresis);
    }

    public int getMonitor_swellsSet_Threshold2() {
        return monitor_swellsSet_Threshold2;
    }

    public void setMonitor_swellsSet_Threshold2(int monitor_swellsSet_Threshold2) {
        this.monitor_swellsSet_Threshold2 = monitor_swellsSet_Threshold2;
        saveInt(Monitor_SwellsSet_Threshold2,monitor_swellsSet_Threshold2);
    }

    public int getMonitor_swellsSet_Hysteresis2() {
        return monitor_swellsSet_Hysteresis2;
    }

    public void setMonitor_swellsSet_Hysteresis2(int monitor_swellsSet_Hysteresis2) {
        this.monitor_swellsSet_Hysteresis2 = monitor_swellsSet_Hysteresis2;
        saveInt(Monitor_SwellsSet_Hysteresis2,monitor_swellsSet_Hysteresis2);

    }

    public int getMonitor_fresSet_Upper() {
        return monitor_fresSet_Upper;
    }

    public void setMonitor_fresSet_Upper(int monitor_fresSet_Upper) {
        this.monitor_fresSet_Upper = monitor_fresSet_Upper;
        saveInt(Monitor_FreSet_Upper,monitor_fresSet_Upper);
    }

    public int getMonitor_fresSet_Lower() {
        return monitor_fresSet_Lower;
    }

    public void setMonitor_fresSet_Lower(int monitor_fresSet_Lower) {
        this.monitor_fresSet_Lower = monitor_fresSet_Lower;
        saveInt(Monitor_FreSet_Lower,monitor_fresSet_Lower);
    }

    public int getMonitor_fresSet_Upper2() {
        return monitor_fresSet_Upper2;
    }

    public void setMonitor_fresSet_Upper2(int monitor_fresSet_Upper2) {
        this.monitor_fresSet_Upper2 = monitor_fresSet_Upper2;
        saveInt(Monitor_FreSet_Upper2,monitor_fresSet_Upper2);

    }

    public int getMonitor_fresSet_Lower2() {
        return monitor_fresSet_Lower2;
    }

    public void setMonitor_fresSet_Lower2(int monitor_fresSet_Lower2) {
        this.monitor_fresSet_Lower2 = monitor_fresSet_Lower2;
        saveInt(Monitor_FreSet_Lower2,monitor_fresSet_Lower2);
    }

    public int getMonitor_FreSet_Probability() {
        return monitor_FreSet_Probability;
    }

    public void setMonitor_FreSet_Probability(int monitor_FreSet_Probability) {
        this.monitor_FreSet_Probability = monitor_FreSet_Probability;
        saveInt(Monitor_FreSet_Probability,monitor_FreSet_Probability);
    }

    public int getMonitor_UnblanceSet_Probability() {
        return monitor_UnblanceSet_Probability;
    }

    public void setMonitor_UnblanceSet_Probability(int monitor_UnblanceSet_Probability) {
        this.monitor_UnblanceSet_Probability = monitor_UnblanceSet_Probability;
        saveInt(Monitor_UnblanceSet_Probability,monitor_UnblanceSet_Probability);
    }

    public int getMonitor_UnblanceSet_Upper() {
        return monitor_UnblanceSet_Upper;
    }

    public void setMonitor_UnblanceSet_Upper(int monitor_UnblanceSet_Upper) {
        this.monitor_UnblanceSet_Upper = monitor_UnblanceSet_Upper;
        saveInt(Monitor_UnblanceSet_Upper,monitor_UnblanceSet_Upper);
    }

    public int getMonitor_UnblanceSet_Upper2() {
        return monitor_UnblanceSet_Upper2;
    }

    public void setMonitor_UnblanceSet_Upper2(int monitor_UnblanceSet_Upper2) {
        this.monitor_UnblanceSet_Upper2 = monitor_UnblanceSet_Upper2;
        saveInt(Monitor_UnblanceSet_Upper2,monitor_UnblanceSet_Upper2);

    }

    public int getMonitor_HarmonicSet_Probability() {
        return monitor_HarmonicSet_Probability;
    }

    public void setMonitor_HarmonicSet_Probability(int monitor_HarmonicSet_Probability) {
        this.monitor_HarmonicSet_Probability = monitor_HarmonicSet_Probability;
        saveInt(Monitor_HarmonicSet_Probability,monitor_HarmonicSet_Probability);

    }

    public int getMonitor_HarmonicSet_Upper() {
        return monitor_HarmonicSet_Upper;
    }

    public void setMonitor_HarmonicSet_Upper(int monitor_HarmonicSet_Upper) {
        this.monitor_HarmonicSet_Upper = monitor_HarmonicSet_Upper;
        saveInt(Monitor_HarmonicSet_Upper,monitor_HarmonicSet_Upper);

    }

    public int getMonitor_HarmonicSet_Upper2() {
        return monitor_HarmonicSet_Upper2;
    }

    public void setMonitor_HarmonicSet_Upper2(int monitor_HarmonicSet_Upper2) {
        this.monitor_HarmonicSet_Upper2 = monitor_HarmonicSet_Upper2;
        saveInt(Monitor_HarmonicSet_Upper2,monitor_HarmonicSet_Upper2);

    }

    public boolean isMonitor_First_Volt() {
        return monitor_First_Volt;
    }

    public void setMonitor_First_Volt(boolean monitor_First_Volt) {
        this.monitor_First_Volt = monitor_First_Volt;
        saveBoolean(Monitor_First_Volt,monitor_First_Volt);
    }

    public boolean isMonitor_First_Freq() {
        return monitor_First_Freq;
    }

    public void setMonitor_First_Freq(boolean monitor_First_Freq) {
        this.monitor_First_Freq = monitor_First_Freq;
        saveBoolean(Monitor_First_Freq,monitor_First_Freq);

    }

    public int getMonitor_First_Duration() {
        return monitor_First_Duration;
    }

    public void setMonitor_First_Duration(int monitor_First_Duration) {
        this.monitor_First_Duration = monitor_First_Duration;
        saveInt(Monitor_First_Duration,monitor_First_Duration);
    }

    public int getMonitor_First_Moudle() {
        return monitor_First_Moudle;
    }

    public void setMonitor_First_Moudle(int monitor_First_Moudle) {
        this.monitor_First_Moudle = monitor_First_Moudle;
        saveInt(Monitor_First_Moudle,monitor_First_Moudle);
    }

    public int getMonitor_Harmonic_Number() {
        return monitor_Harmonic_Number;
    }

    public void setMonitor_Harmonic_Number(int monitor_Harmonic_Number) {
        this.monitor_Harmonic_Number = monitor_Harmonic_Number;
        saveInt(Monitor_Harmonic_Number,monitor_Harmonic_Number);
    }

    public  static AppConfig  getInstance(){
        if (instance==null)
            instance=new AppConfig();
        return  instance;
    }

    /**
     * 获取是否锁屏
     * @return
     */
    public boolean isLockScreen() {
        return lockScreen;
    }

    /**
     * 设置是否锁屏
     * @param lockScreen
     */
    public void setLockScreen(boolean lockScreen) {
        this.lockScreen = lockScreen;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }


    public int getDefault_brightness() {
        return default_brightness;
    }

    public void setDefault_brightness(int brightness) {
        this.default_brightness = brightness;
        saveInt(Setup_System_Brightness,brightness);

    }

    public int getDefault_language(){
        return default_language;
    }

    public void setDefault_language(int language){
        this.default_language = language;
        saveInt(Setup_System_Language,language);
    }


    public boolean isDefault_automatic_Screen() {
        return default_automatic_screen;
    }

    public void setDefault_Automatic_Screen(boolean automatic) {
        this.default_automatic_screen = automatic;
        saveBoolean(Setup_System_Automatic_Screen,automatic);
    }

    public boolean isSetup_Night_Mode() {
        return setup_Night_Mode;
    }

    public void setSetup_Night_Mode(boolean setup_Night_Mode) {
        this.setup_Night_Mode = setup_Night_Mode;
        saveBoolean(Setup_Night_Mode,setup_Night_Mode);
    }


    /**
     * 瞬变设置配置信息
     */

    private static final String TransientSet_Default = "TransientSet_Default";
    private boolean transientSet_Default = true;

    public boolean isTransientSet_Default() {
        return transientSet_Default;
    }
    public void setTransientSet_Default(boolean transientSet_Default) {
        this.transientSet_Default = transientSet_Default;
        saveBoolean(TransientSet_Default,transientSet_Default);
    }


    private static final String Transient_Trigger_Voltage = "Transient_Trigger_Voltage";
    private boolean transient_trigger_voltage = true;

    public boolean isTransient_Trigger_Voltage(){return  transient_trigger_voltage;};
    public void setTransient_Trigger_Voltage(boolean defaultValue){
        this.transient_trigger_voltage = defaultValue;
        saveBoolean(Transient_Trigger_Voltage,defaultValue);
    }


    private static final String Transient_Trigger_AMPS = "Transient_Trigger_AMPS";
    private boolean transient_trigger_amps = true;

    public boolean isTransient_Trigger_AMPS(){return  transient_trigger_amps;};
    public void setTransient_Trigger_AMPS(boolean defaultValue){
        this.transient_trigger_amps = defaultValue;
        saveBoolean(Transient_Trigger_AMPS,defaultValue);
    }


    private static final String Transient_Trigger_VoltLevel = "Transient_Trigger_VoltLevel";
    private int transient_trigger_voltlevel = 1;

    public int getTransient_Trigger_VoltLevel (){
        return transient_trigger_voltlevel;
    }

    public void setTransient_Trigger_VoltLevel (int defaultValue){
        this.transient_trigger_voltlevel = defaultValue;
        saveInt(Transient_Trigger_VoltLevel,defaultValue);
    }



    private static final String Transient_Trigger_AmpLevel = "Transient_Trigger_AmpLevel";
    private int transient_trigger_amplevel = 1;

    public int getTransient_Trigger_AmpLevel (){
        return transient_trigger_amplevel;
    }

    public void setTransient_Trigger_AmpLevel (int defaultValue){
        this.transient_trigger_amplevel = defaultValue;
        saveInt(Transient_Trigger_AmpLevel,defaultValue);
    }



    private static final String Transient_Time_Immediate = "Transient_Time_Immediate";
    private boolean  transient_time_immediate = true;

    public boolean isTransient_Time_Immediate(){return  transient_time_immediate;};
    public void setTransient_Time_Immediate(boolean defaultValue){
        this.transient_time_immediate = defaultValue;
        saveBoolean(Transient_Time_Immediate,defaultValue);
    }


    private static final String Transient_Time_Year = "Transient_Time_Year";
    private int transient_time_year = 1;

    public int getTransient_Time_Year (){
        return transient_time_year;
    }

    public void setTransient_Time_Year (int defaultValue){
        this.transient_time_year = defaultValue;
        saveInt(Transient_Time_Year,defaultValue);
    }

    private static final String Transient_Time_Month = "Transient_Time_Month";
    private int transient_time_month = 1;

    public int getTransient_Time_Month (){
        return transient_time_month;
    }

    public void setTransient_Time_Month (int defaultValue){
        this.transient_time_month = defaultValue;
        saveInt(Transient_Time_Month,defaultValue);
    }


    private static final String Transient_Time_Day = "Transient_Time_Day";
    private int transient_time_day = 1;

    public int getTransient_Time_Day (){
        return transient_time_day;
    }

    public void setTransient_Time_Day (int defaultValue){
        this.transient_time_day = defaultValue;
        saveInt(Transient_Time_Day,defaultValue);
    }


    private static final String Transient_Time_Hours = "Transient_Time_Hours";
    private int transient_time_hours = 1;

    public int getTransient_Time_Hours (){
        return transient_time_hours;
    }

    public void setTransient_Time_Hours (int defaultValue){
        this.transient_time_hours = defaultValue;
        saveInt(Transient_Time_Hours,defaultValue);
    }

    private static final String Transient_Time_Minutes = "Transient_Time_Minutes";
    private int transient_time_minutes = 1;

    public int getTransient_Time_Minutes (){
        return transient_time_minutes;
    }

    public void setTransient_Time_Minutes (int defaultValue){
        this.transient_time_minutes = defaultValue;
        saveInt(Transient_Time_Minutes,defaultValue);
    }


    /**
     * 暂升暂降 配置信息
     */

    private static final String DipsSet_Default = "DipsSet_Default";
    private boolean dipsSet_Default = true;

    public boolean isDipsSet_Default() {
        return dipsSet_Default;
    }
    public void setDipsSet_Default(boolean dipsSet_Default) {
        this.dipsSet_Default = dipsSet_Default;
        saveBoolean(DipsSet_Default,dipsSet_Default);
    }


    private static final String DipSwell_Trigger_Voltage = "DipSwell_Trigger_Voltage";
    private boolean dipswell_trigger_voltage = true;

    public boolean isDipSwell_Trigger_Voltage(){return  dipswell_trigger_voltage;};
    public void setDipSwell_Trigger_Voltage(boolean defaultValue){
        this.dipswell_trigger_voltage = defaultValue;
        saveBoolean(DipSwell_Trigger_Voltage,defaultValue);
    }


    private static final String DipSwell_Trigger_AMPS = "DipSwell_Trigger_AMPS";
    private boolean dipswell_trigger_amps = true;

    public boolean isDipSwell_Trigger_AMPS(){return  dipswell_trigger_amps;};
    public void setDipSwell_Trigger_AMPS(boolean defaultValue){
        this.dipswell_trigger_amps = defaultValue;
        saveBoolean(DipSwell_Trigger_AMPS,defaultValue);
    }



    private static final String DipSwell_Trigger_VoltLevel = "DipSwell_Trigger_VoltLevel";
    private int dipswell_trigger_voltlevel = 1;

    public int getDipSwell_Trigger_VoltLevel (){
        return dipswell_trigger_voltlevel;
    }

    public void setDipSwell_Trigger_VoltLevel (int defaultValue){
        this.dipswell_trigger_voltlevel = defaultValue;
        saveInt(DipSwell_Trigger_VoltLevel,defaultValue);
    }

    private static final String DipSwell_Trigger_AmpLevel = "DipSwell_Trigger_AmpLevel";
    private int dipswell_trigger_amplevel = 1;

    public int getDipSwell_Trigger_AmpLevel (){
        return dipswell_trigger_amplevel;
    }

    public void setDipSwell_Trigger_AmpLevel (int defaultValue){
        this.dipswell_trigger_amplevel = defaultValue;
        saveInt(DipSwell_Trigger_AmpLevel,defaultValue);
    }


    private static final String DipSwell_Time_Immediate = "DipSwell_Time_Immediate";
    private boolean dipswell_time_immediate = true;

    public boolean isDipSwell_Trigger_Immediate(){return  dipswell_time_immediate;};
    public void setDipSwell_Time_Immediate(boolean dipsWell_trigger_immediate){
        this.dipswell_time_immediate = dipsWell_trigger_immediate;
        saveBoolean(DipSwell_Time_Immediate,dipsWell_trigger_immediate);
    }

    private static final String DipSwell_Time_Year = "DipSwell_Time_Year";
    private int dipswell_time_year = 1;

    public int getDipSweel_Time_Year (){
        return dipswell_time_year;
    }

    public void setDipSweel_Time_Year (int defaultValue){
        this.dipswell_time_year = defaultValue;
        saveInt(DipSwell_Time_Year,defaultValue);
    }


    private static final String DipSwell_Time_Month = "DipSwell_Time_Month";
    private int dipswell_time_month = 1;

    public int getDipSweel_Time_Month (){
        return dipswell_time_month;
    }

    public void setDipSweel_Time_Month (int defaultValue){
        this.dipswell_time_month = defaultValue;
        saveInt(DipSwell_Time_Month,defaultValue);
    }

    private static final String DipSwell_Time_Day = "DipSwell_Time_Day";
    private int dipswell_time_day = 1;

    public int getDipSweel_Time_Day (){
        return dipswell_time_day;
    }

    public void setDipSweel_Time_Day (int defaultValue){
        this.dipswell_time_day = defaultValue;
        saveInt(DipSwell_Time_Day,defaultValue);
    }

    private static final String DipSwell_Time_Hours = "DipSwell_Time_Hours";
    private int dipswell_time_hours = 1;

    public int getDipSweel_Time_Hours (){
        return dipswell_time_hours;
    }

    public void setDipSweel_Time_Hours (int defaultValue){
        this.dipswell_time_hours = defaultValue;
        saveInt(DipSwell_Time_Hours,defaultValue);
    }

    private static final String DipSwell_Time_Minutes = "DipSwell_Time_Minutes";
    private int dipswell_time_minutes = 1;

    public int getDipSweel_Time_Minutes (){
        return dipswell_time_minutes;
    }

    public void setDipSweel_Time_Minutes (int defaultValue){
        this.dipswell_time_minutes = defaultValue;
        saveInt(DipSwell_Time_Minutes,defaultValue);
    }

    /**
     * 电能质量监测
     */

    private static final String PowerMonitor_Duration = "PowerMonitor_Duration";
    private int powerMonitor_duratrion = 1;

    public int getPowerMonitor_Duration (){
        return powerMonitor_duratrion;
    }

    public void setPowerMonitor_Duration (int defaultValue){
        this.powerMonitor_duratrion = defaultValue;
        saveInt(PowerMonitor_Duration,defaultValue);
    }



    private static final String PowerMonitor_Time_Immediate = "PowerMonitor_Time_Immediate";
    private boolean  powerMonitor_time_immediate = true;

    public boolean isPowerMonitor_Time_Immediate(){return  powerMonitor_time_immediate;};
    public void setPowerMonitor_Time_Immediate(boolean defaultValue){
        this.powerMonitor_time_immediate = defaultValue;
        saveBoolean(PowerMonitor_Time_Immediate,defaultValue);
    }


    private static final String PowerMonitor_Time_Year = "PowerMonitor_Time_Year";
    private int powerMonitor_time_year = 1;

    public int getPowerMonitor_Time_Year (){
        return powerMonitor_time_year;
    }

    public void setPowerMonitor_Time_Year (int defaultValue){
        this.powerMonitor_time_year = defaultValue;
        saveInt(PowerMonitor_Time_Year,defaultValue);
    }

    private static final String PowerMonitor_Time_Month = "PowerMonitor_Time_Month";
    private int powerMonitor_time_month = 1;

    public int getPowerMonitor_Time_Month (){
        return powerMonitor_time_month;
    }

    public void setPowerMonitor_Time_Month (int defaultValue){
        this.powerMonitor_time_month = defaultValue;
        saveInt(PowerMonitor_Time_Month,defaultValue);
    }


    private static final String PowerMonitor_Time_Day = "PowerMonitor_Time_Day";
    private int powerMonitor_time_day = 1;

    public int getPowerMonitor_Time_Day (){
        return powerMonitor_time_day;
    }

    public void setPowerMonitor_Time_Day (int defaultValue){
        this.powerMonitor_time_day = defaultValue;
        saveInt(PowerMonitor_Time_Day,defaultValue);
    }


    private static final String PowerMonitor_Time_Hours = "PowerMonitor_Time_Hours";
    private int powerMonitor_time_hours = 1;

    public int getPowerMonitor_Time_Hours (){
        return powerMonitor_time_hours;
    }

    public void setPowerMonitor_Time_Hours (int defaultValue){
        this.powerMonitor_time_hours = defaultValue;
        saveInt(PowerMonitor_Time_Hours,defaultValue);
    }

    private static final String PowerMonitor_Time_Minutes = "PowerMonitor_Time_Minutes";
    private int powerMonitor_time_minutes = 1;

    public int getPowerMonitor_Time_Minutes (){
        return powerMonitor_time_minutes;
    }

    public void setPowerMonitor_Time_Minutes (int defaultValue){
        this.powerMonitor_time_minutes = defaultValue;
        saveInt(PowerMonitor_Time_Minutes,defaultValue);
    }

    /**
     * Config Scope scale
     */
    private static final String Config_Phase_Volt = "Config_Phase_Volt";
    private int config_phase_volt = 391;

    public int getConfig_Phase_Volt (){
        return config_phase_volt;
    }

    public void setConfig_Phase_Volt (int defaultValue){
        this.config_phase_volt = defaultValue;
        saveInt(Config_Phase_Volt,defaultValue);
    }

    private static final String Config_Phase_Amp = "Config_Phase_Amp";
    private int config_phase_amp = 1991;

    public int getConfig_Phase_Amp (){
        return config_phase_amp;
    }

    public void setConfig_Phase_Amp (int defaultValue){
        this.config_phase_amp = defaultValue;
        saveInt(Config_Phase_Amp,defaultValue);
    }

    private static final String Config_Neutral_Volt = "Config_Neutral_Volt";
    private int config_neutral_volt = 391;

    public int getConfig_Neutral_Volt (){
        return config_neutral_volt;
    }

    public void setConfig_Neutral_Volt (int defaultValue){
        this.config_neutral_volt = defaultValue;
        saveInt(Config_Neutral_Volt,defaultValue);
    }

    private static final String Config_Neutral_Amp = "Config_Neutral_Amp";
    private int config_neutral_amp = 1991;

    public int getConfig_Neutral_Amp (){
        return config_neutral_amp;
    }

    public void setConfig_Neutral_Amp (int defaultValue){
        this.config_neutral_amp = defaultValue;
        saveInt(Config_Neutral_Amp,defaultValue);
    }

    private static final String Config_ScopeScale_Default = "Config_ScopeScale_Default";
    private boolean config_ScopeScale_Default = true;

    public boolean isConfig_ScopeScale_Default() {
        return config_ScopeScale_Default;
    }
    public void setConfig_ScopeScale_Default(boolean config_ScopeScale_Default) {
        this.config_ScopeScale_Default = config_ScopeScale_Default;
        saveBoolean(Config_ScopeScale_Default,config_ScopeScale_Default);
    }

    /**
     *  AMPS SCALING  Phase    5
     */


    private static final String Config_AmpScale_Phase_AmpClamp = "Config_AmpScale_Phase_AmpClamp";
    private int config_ampScale_phase_ampclamp = 1;

    public int getConfig_AmpScale_Phase_AmpClamp (){
        return config_ampScale_phase_ampclamp;
    }

    public void setConfig_AmpScale_Phase_AmpClamp (int defaultValue){
        this.config_ampScale_phase_ampclamp = defaultValue;
        saveInt(Config_AmpScale_Phase_AmpClamp,defaultValue);
    }

    private static final String Config_AmpScale_Phase_Norminal = "Config_AmpScale_Phase_Norminal";
    private int config_ampScale_phase_norminal = 3000;

    public int getConfig_AmpScale_Phase_Norminal (){
        return config_ampScale_phase_norminal;
    }

    public void setConfig_AmpScale_Phase_Norminal (int defaultValue){
        this.config_ampScale_phase_norminal = defaultValue;
        saveInt(Config_AmpScale_Phase_Norminal,defaultValue);
    }

    private static final String Config_AmpScale_Phase_Sensitivity = "Config_AmpScale_Phase_Sensitivity";
    private int config_ampScale_phase_sen = 1;

    public int getConfig_AmpScale_Phase_Sensitivity (){
        return config_ampScale_phase_sen;
    }

    public void setConfig_AmpScale_Phase_Sensitivity (int defaultValue){
        this.config_ampScale_phase_sen = defaultValue;
        saveInt(Config_AmpScale_Phase_Sensitivity,defaultValue);
    }

    private static final String Config_AmpScale_Phase_Ratio = "Config_AmpScale_Phase_Ratio";
    private int config_ampScale_phase_ratio = 1;

    public int getConfig_AmpScale_Phase_Ratio (){
        return config_ampScale_phase_ratio;
    }

    public void setConfig_AmpScale_Phase_Ratio (int defaultValue){
        this.config_ampScale_phase_ratio = defaultValue;
        saveInt(Config_AmpScale_Phase_Ratio,defaultValue);
    }


    /**
     *  AMPS SCALING  Neutral  5
     */


    private static final String Config_AmpScale_Neutral_AmpClamp = "Config_AmpScale_Neutral_AmpClamp";
    private int config_ampScale_neutral_ampclamp = 1;

    public int getConfig_AmpScale_Neutral_AmpClamp (){
        return config_ampScale_neutral_ampclamp;
    }

    public void setConfig_AmpScale_Neutral_AmpClamp (int defaultValue){
        this.config_ampScale_neutral_ampclamp = defaultValue;
        saveInt(Config_AmpScale_Neutral_AmpClamp,defaultValue);
    }

    private static final String Config_AmpScale_Neutral_Norminal = "Config_AmpScale_Neutral_Norminal";
    private int config_ampScale_neutral_norminal = 3000;

    public int getConfig_AmpScale_Neutral_Norminal (){
        return config_ampScale_neutral_norminal;
    }

    public void setConfig_AmpScale_Neutral_Norminal (int defaultValue){
        this.config_ampScale_neutral_norminal = defaultValue;
        saveInt(Config_AmpScale_Neutral_Norminal,defaultValue);
    }

    private static final String Config_AmpScale_Neutral_Sensitivity = "Config_AmpScale_Phase_Sensitivity";
    private int config_ampScale_neutral_sen = 1;

    public int getConfig_AmpScale_Neutral_Sensitivity (){
        return config_ampScale_neutral_sen;
    }

    public void setConfig_AmpScale_Neutral_Sensitivity (int defaultValue){
        this.config_ampScale_neutral_sen = defaultValue;
        saveInt(Config_AmpScale_Neutral_Sensitivity,defaultValue);
    }

    private static final String Config_AmpScale_Neutral_Ratio = "Config_AmpScale_Phase_Ratio";
    private int config_ampScale_neutral_ratio = 1;

    public int getConfig_AmpScale_Neutral_Ratio (){
        return config_ampScale_neutral_ratio;
    }

    public void setConfig_AmpScale_Neutral_Ratio (int defaultValue){
        this.config_ampScale_neutral_ratio = defaultValue;
        saveInt(Config_AmpScale_Neutral_Ratio,defaultValue);
    }



    /**
     *  Volt SCALING  Phase    1
     */
    private static final String Config_VoltScale_Phase_VoltRatio = "Config_VoltScale_Phase_VoltRatio";
    private int config_voltScale_phase_volt = 1;

    public int getConfig_VoltScale_Phase_VoltRatio (){
        return config_voltScale_phase_volt;
    }

    public void setConfig_VoltScale_Phase_VoltRatio (int defaultValue){
        this.config_voltScale_phase_volt = defaultValue;
        saveInt(Config_VoltScale_Phase_VoltRatio,defaultValue);
    }

    /**
     *  Volt SCALING  Neutral  1
     */
    private static final String Config_VoltScale_Neutral_VoltRatio = "Config_AmpScale_Neutral_VoltRatio";
    private int config_voltScale_neutral_volt = 1;

    public int getConfig_VoltScale_Neutral_VoltRatio (){
        return config_voltScale_neutral_volt;
    }

    public void setConfig_VoltScale_Neutral_VoltRatio (int defaultValue){
        this.config_voltScale_neutral_volt = defaultValue;
        saveInt(Config_VoltScale_Neutral_VoltRatio,defaultValue);
    }


}
