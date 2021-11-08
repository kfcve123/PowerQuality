package com.cem.powerqualityanalyser.enums;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.activity.HarmonicsActivity;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.view.datalist.BaseViewHolder;
import com.cem.powerqualityanalyser.view.datalist.GroupedRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public enum HarmonicsType {
    A(1,"A"),
    S(2,"S"),
    V(3,"V"),
    U(4,"U");

    private int datavalue = 0;
    private String valueStr = "";

    private HarmonicsType(int value, String str) {
        this.datavalue = value;
        this.valueStr = str;
    }

    public static HarmonicsType valueOf(int value) {
        for (HarmonicsType s : HarmonicsType.values()) {
            if (s.Value() == value) {
                return s;
            }
        }
        return A;
    }

    public int Value() {
        return this.datavalue;
    }

    public String ValueStr() {
        return this.valueStr;
    }

}