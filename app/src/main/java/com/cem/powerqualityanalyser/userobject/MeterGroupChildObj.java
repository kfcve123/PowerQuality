package com.cem.powerqualityanalyser.userobject;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class MeterGroupChildObj {
    private List<MeterChildItemObj> childList;

    public MeterGroupChildObj() {
        childList = new ArrayList<>();
    }

    public void AddMeterChildItem(MeterChildItemObj obj) {
        childList.add(obj);
    }

    public void AddMeterChildItem(String child, int resImagID) {
        childList.add(new MeterChildItemObj(child, "",resImagID));
    }

    public void AddMeterChildItem(String child, String childUnit,int resImagID) {
        childList.add(new MeterChildItemObj(child, childUnit,resImagID));
    }

    /**
     * 旧方法，废弃，等待删除
     * @param child
     */
    public void AddMeterChildItem(String child) {
        childList.add(new MeterChildItemObj(child,"", false, false));
    }

    public void AddMeterChildItem(SpannableString child) {
        childList.add(new MeterChildItemObj(child,"", false, false));
    }



    public void AddMeterChildItem(String child,String childUnit) {
        childList.add(new MeterChildItemObj(child,childUnit, false, false));
    }

    public void AddMeterChildItem(String child, boolean isGElectricit, boolean isRElectricit) {
        childList.add(new MeterChildItemObj(child,"", isGElectricit, isRElectricit));
    }

    public void AddMeterChildItem(String child,String childUnit, boolean isGElectricit, boolean isRElectricit) {
        childList.add(new MeterChildItemObj(child, childUnit,isGElectricit, isRElectricit));
    }

    public void AddMeterChildItem(SpannableStringBuilder child, int resImagID) {
        childList.add(new MeterChildItemObj(child, "",resImagID));
    }

    public void AddMeterChildItem(SpannableStringBuilder child) {
        childList.add(new MeterChildItemObj(child,"", false, false));
    }

    public void AddMeterChildItem(SpannableStringBuilder child, boolean isGElectricit, boolean isRElectricit) {
        childList.add(new MeterChildItemObj(child, "",isGElectricit, isRElectricit));
    }



    public int getItemSize() {
        return childList.size();
    }

    public MeterChildItemObj getItem(int index) {
        return childList.get(index);
    }

    public void setItem(int index, String child, int resImagID) {
        childList.get(index).setItem(child, resImagID);
    }

    public void setItem(int index, String child) {
        childList.get(index).setChildstr(child);
    }

    public void setItem(int index, String child,boolean isGElectricit,boolean isRElectricit) {
        childList.get(index).setChildstr(child);
        childList.get(index).setgElectricity(isGElectricit);
        childList.get(index).setrElectricity(isRElectricit);
    }

    /*public void setItem(int index, SpannableStringBuilder child) {
        childList.get(index).setSpannableString(child);
    }

    public void setItem(int index, SpannableStringBuilder child, int resImagID) {
        childList.get(index).setItem(child, resImagID);
    }*/

}
