package com.cem.powerqualityanalyser.databean;

import com.cem.powerqualityanalyser.databeannew.DataGraphDataObj;

public class ParseHelpBean {
    private int index;
    private DataGraphDataObj dataGraphDataObj;

    public ParseHelpBean(int index, DataGraphDataObj dataGraphDataObj) {
        this.index = index;
        this.dataGraphDataObj = dataGraphDataObj;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DataGraphDataObj getDataGraphDataObj() {
        return dataGraphDataObj;
    }

    public void setDataGraphDataObj(DataGraphDataObj dataGraphDataObj) {
        this.dataGraphDataObj = dataGraphDataObj;
    }
}
