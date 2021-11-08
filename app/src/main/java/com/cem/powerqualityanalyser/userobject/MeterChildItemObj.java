package com.cem.powerqualityanalyser.userobject;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

public class MeterChildItemObj {
    private String childstr;
    private String childUnit;
    private  int resImagID;
    private boolean gElectricity;
    private boolean rElectricity;
    private SpannableStringBuilder spannableString;
    private SpannableString spannString;


    public MeterChildItemObj(String child) {
        this.childstr = child;
    }

    /**
     * 传值用
     * @param child
     * @param resImagID
     */
    public MeterChildItemObj(String child , String childUnit,int resImagID) {
        this.childstr = child;
        this.childUnit = childUnit;
        this.resImagID=resImagID;
    }

    public MeterChildItemObj(String child, String childUnit,boolean isGElectricit, boolean isRElectricit) {
        this.childstr = child;
        this.childUnit = childUnit;
        this.gElectricity = isGElectricit;
        this.rElectricity = isRElectricit;
    }

    public String getChildStr() {
        return childstr;
    }

    public String getChildUnit(){
        return childUnit;
    }



    public MeterChildItemObj(SpannableString spannableString) {
        this.spannString = spannableString;

    }

    public MeterChildItemObj(SpannableString spannableString ,String childUnit, int resImagID) {
        this.spannString = spannableString;
        this.childUnit = childUnit;
        this.resImagID=resImagID;
    }


    public MeterChildItemObj(SpannableString spannableString, String childUnit,boolean isGElectricit, boolean isRElectricit) {
        this.spannString = spannableString;
        this.childUnit = childUnit;
        this.gElectricity = isGElectricit;
        this.rElectricity = isRElectricit;
    }


    public SpannableString getChildSpannStr() {
        return spannString;
    }

    /**
     * 带文字效果（不同size的内容，带图片的，带特殊符号的）
     * @param spannableString
     */
    public MeterChildItemObj(SpannableStringBuilder spannableString) {
        this.spannableString = spannableString;

    }

    public MeterChildItemObj(SpannableStringBuilder spannableString ,String childUnit, int resImagID) {
        this.spannableString = spannableString;
        this.childUnit = childUnit;
        this.resImagID=resImagID;
    }


    public MeterChildItemObj(SpannableStringBuilder spannableString, String childUnit,boolean isGElectricit, boolean isRElectricit) {
        this.spannableString = spannableString;
        this.childUnit = childUnit;
        this.gElectricity = isGElectricit;
        this.rElectricity = isRElectricit;
    }

    public SpannableStringBuilder getSpannableString() {
        return spannableString;
    }

    public void setSpannableString(SpannableStringBuilder stringBuilder){
        this.spannableString = stringBuilder;
    }

    public boolean isgElectricity() {
        return gElectricity;
    }

    public void setgElectricity(boolean gElectricity) {
        this.gElectricity = gElectricity;
    }

    public boolean isrElectricity() {
        return rElectricity;
    }

    public void setrElectricity(boolean rElectricity) {
        this.rElectricity = rElectricity;
    }



    public int getResImagID() {
        return resImagID;
    }

    public void setChildstr(String childstr) {
        this.childstr = childstr;
    }

    public void setResImagID(int resImagID) {
        this.resImagID = resImagID;
    }
    public void setItem(String childstr,int resImagID) {
        this.childstr = childstr;
        this.resImagID=resImagID;
    }

    public void setItem(SpannableString childstr,int resImagID) {
        this.spannString = childstr;
        this.resImagID=resImagID;
    }
}
