package com.cem.powerqualityanalyser.tool;

import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;

public class ButtonListenerTool {
    private ArrayList<MeterKeyValue>  pw;
    private ArrayList<MeterKeyValue> loogerValues;
    //调试按键时间间隔
    private final long interval = 1000;
    private  long lastTime = 0;
    private static final ButtonListenerTool tool = new ButtonListenerTool();

    public static ButtonListenerTool getTool(){
        return tool;
    }


    private ButtonListenerTool(){
        pw = new ArrayList<>();
        pw.add(MeterKeyValue.Up);
        pw.add(MeterKeyValue.Up);
        pw.add(MeterKeyValue.Down);
        pw.add(MeterKeyValue.Down);
        pw.add(MeterKeyValue.Left);
        pw.add(MeterKeyValue.Right);
        pw.add(MeterKeyValue.Left);
        pw.add(MeterKeyValue.Right);
        pw.add(MeterKeyValue.Enter);
        loogerValues = new ArrayList<>();
    }

    public boolean onClick(MeterKeyValue meterKeyValue){
        long currentTimeMillis = System.currentTimeMillis();
        if (lastTime == 0){
            lastTime = System.currentTimeMillis();
            loogerValues.add(meterKeyValue);
            return false;
        }else if (currentTimeMillis - lastTime > interval){
            reset();
            loogerValues.add(meterKeyValue);
            return false;
        }else{
            lastTime = System.currentTimeMillis();
            if (loogerValues.size() < pw.size()){
                loogerValues.add(meterKeyValue);
            }else{
                loogerValues.remove(0);
                loogerValues.add(meterKeyValue);
            }
            return isVerification();
        }
    }
    private void reset(){
        loogerValues.clear();
        lastTime = 0;
    }
    private boolean isVerification(){
        if (pw.size() != loogerValues.size())
            return false;
        for (int i = 0; i < pw.size(); i++) {
            if (!pw.get(i).equals(loogerValues.get(i)))
                return false;
        }
       reset();
        return true;
    }
}
