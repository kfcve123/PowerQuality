package com.cem.powerqualityanalyser.userobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeterGroupListObj {
    private List<String> headerList;
    private List<MeterGroupChildObj> childObjList;

    public MeterGroupListObj() {
        headerList = new ArrayList<>();
        childObjList = new ArrayList<>();
    }

    public void addHeader(String header) {
        headerList.add(header);
    }

    public void addHeader(String[] headers) {
        headerList.addAll(Arrays.asList(headers));
    }

    public void addChild(MeterGroupChildObj obj) {
        childObjList.add(obj);
    }

    public void Clear() {
        headerList.clear();
        childObjList.clear();
    }

    public void ClearHeader() {
        headerList.clear();
    }

    public void ClearChild() {
        childObjList.clear();
    }

    public MeterGroupChildObj getChild(int index) {
        if (childObjList.size() > index)
            return childObjList.get(index);
        else return null;
    }

    public int getHeaderSize() {
        return headerList.size();
    }

    public int getChildSize() {
        return childObjList.size();
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public List<MeterGroupChildObj> getChildObjList() {
        return childObjList;
    }
}
