package com.cem.powerqualityanalyser.command;

public class RecordCommand {

    public static byte[] Record_Command_VoltAmp = new byte[]{(byte) 0xEB, 0x00};
    public static byte[] Record_Command_Flicker = new byte[]{(byte) 0xEB, 0x01};
    public static byte[] Record_Command_Frequency = new byte[]{(byte) 0xEB, 0x02};
    public static byte[] Record_Command_VoltHarmonic = new byte[]{(byte) 0xEB, 0x03};
    public static byte[] Record_Command_AmpHarmonic = new byte[]{(byte) 0xEB, 0x04};
    public static byte[] Record_Command_Unbalance = new byte[]{(byte) 0xEB, 0x05};
    public static byte[] Record_Command_Power = new byte[]{(byte) 0xEB, 0x06};
    public static byte[] Record_Command_Energy = new byte[]{(byte) 0xEB, 0x07};

}
