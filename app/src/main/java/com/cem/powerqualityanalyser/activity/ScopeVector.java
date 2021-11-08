package com.cem.powerqualityanalyser.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.fragment.BaseFragmentTrend;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.view.MyNoPaddingTextView;
import com.cem.powerqualityanalyser.view.RightModeView;
import com.cem.powerqualityanalyser.view.phaseview.DrawRoundpointer;
import com.cem.powerqualityanalyser.view.phaseview.Pointer;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelLineData;

public class ScopeVector extends BaseFragmentTrend {

    protected DrawRoundpointer roundpointer;
    protected ListView UnbalanceLeft;
    protected List<Pointer> pointers = new ArrayList<>();
    protected ListAdapter adapter;
    protected List<ListViewItemObj> listitems;
    protected TextView l1a,l1b,l1c,n1;
    protected MyNoPaddingTextView textview_l1, textview_l2, textview_l3, textview_ln;
    protected RelativeLayout top_l1a_rl,top_l1b_rl,top_l1c_rl,top_ln_rl;

    private RightModeView rightModeView;
    private List<RightListViewItemObj> strList;
    private boolean changeRightIndex;
    private TextView tv_hz;
    private String configV;
    private String configHz;
    protected TextView scope_config_tv;
    protected String[] showItem2;

    /**
     * 实时值
     * @param wir_index
     * @param wir_right_index
     * @param list  如何定义
     */
    float maxfundV,maxfundA;
    float percentagel1,percentagel2,percentagel3;
    private Pointer l1,l2,l3;
    String titl1 = "---";
    String titl2 = "---";
    String titl3 = "---";
    String titl4 = "---";

    public void addSelectMeterData(int wir_index,int wir_right_index,ModelAllData list){
        if (listitems == null)
            listitems = new ArrayList<>();
        else
            listitems.clear();
        try{
            switch (wir_index){
                case 0://3QWYE
                case 5://3QHIGH LEG
                case 6://2½-ELEMENT
                    switch (wir_right_index){
                        case 0://3V
                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|V2|=", list.getModelLineData().get(0).getbValue().getValue() + (list.getModelLineData().get(0).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|V3|=", list.getModelLineData().get(0).getcValue().getValue() + (list.getModelLineData().get(0).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getcValue().getValue_Unit())));

                            listitems.add(new ListViewItemObj("φV1=", list.getModelLineData().get(1).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φV2=", list.getModelLineData().get(1).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φV3=", list.getModelLineData().get(1).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(0).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getcValue().getValue())) {
                                maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()));
                                maxfundV = Math.max(maxfundV, Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) / maxfundV;

                            }
                            if (percentagel1 == 0)
                                percentagel1 = 1;

                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.ALL_METER_TITLE_COLOR[1]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getbValue().getValue()), percentagel2, ColorList.ALL_METER_TITLE_COLOR[2]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);
                            if (list.getModelLineData().get(1).getcValue() != null) {
                                percentagel3 = Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()) / maxfundV;
                                if (percentagel3 == 0)
                                    percentagel3 = 1;
                                l3 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getcValue().getValue()), percentagel3, ColorList.ALL_METER_TITLE_COLOR[3]);
                                if (pointers.size() < 3)
                                    pointers.add(l3);
                                else
                                    pointers.set(2, l3);
                            } else {
                                if (pointers.size() > 2) {
                                    pointers.remove(2);
                                }
                            }

                            titl1 = list.getModelLineData().get(0).getaValue().getValue();
                            titl2 = list.getModelLineData().get(0).getbValue().getValue();
                            titl3 = list.getModelLineData().get(0).getcValue().getValue();
                            titl4 = list.getModelLineData().get(0).getnValue().getValue();

                            setTopLeftTitle("L1","L2","L3","N");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 1://3A
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A3|=", list.getModelLineData().get(2).getcValue().getValue() + (list.getModelLineData().get(2).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getcValue().getValue_Unit())));

                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3=", list.getModelLineData().get(3).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(2).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getcValue().getValue())) {
                                maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                                maxfundA = Math.max(maxfundA, Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundA;
                            }
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue())/ maxfundA;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);
                            if (list.getModelLineData().get(2).getcValue() != null) {
                                percentagel3 = Float.valueOf(list.getModelLineData().get(2).getcValue().getValue())/ maxfundA;
                                if (percentagel3 == 0)
                                    percentagel3 = 1;
                                l3 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getcValue().getValue()), percentagel3, ColorList.SCOPE_LINE_COLOR[2]);
                                if (pointers.size() < 3)
                                    pointers.add(l3);
                                else
                                    pointers.set(2, l3);
                            } else {
                                if (pointers.size() > 2) {
                                    pointers.remove(2);
                                }
                            }

                            titl1 = list.getModelLineData().get(2).getaValue().getValue();
                            titl2 = list.getModelLineData().get(2).getbValue().getValue();
                            titl3 = list.getModelLineData().get(2).getcValue().getValue();
                            titl4 = list.getModelLineData().get(2).getnValue().getValue();

                            setTopLeftTitle("L1","L2","L3","N");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                        case 2://L1

                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()) - Float.valueOf(list.getModelLineData().get(1).getaValue().getValue())),1)));
                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3=", list.getModelLineData().get(3).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(0).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getcValue().getValue())) {
                                maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()));
                                maxfundV = Math.max(maxfundV, Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) / maxfundV;
                            }
                            if(DataFormatUtil.isNumber(list.getModelLineData().get(2).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getcValue().getValue())) {
                                maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                                maxfundA = Math.max(maxfundA, Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));
                                percentagel2 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundA;
                            }

                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);

                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);

                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }
                            titl1 = list.getModelLineData().get(0).getaValue().getValue();
                            titl2 = list.getModelLineData().get(2).getaValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1","L1","","");
                            setTopTitle(titl1, titl2, titl3, titl4);

                            break;
                        case 3://L2
                            listitems.add(new ListViewItemObj("|V2|=", list.getModelLineData().get(0).getbValue().getValue() + (list.getModelLineData().get(0).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()) - Float.valueOf(list.getModelLineData().get(1).getbValue().getValue())),1)));
                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3=", list.getModelLineData().get(3).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(0).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getcValue().getValue())) {
                                maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()));
                                maxfundV = Math.max(maxfundV, Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()) / maxfundV;
                            }
                            if(DataFormatUtil.isNumber(list.getModelLineData().get(2).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getcValue().getValue())) {
                                maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                                maxfundA = Math.max(maxfundA, Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));
                                percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()) / maxfundA;
                            }
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getbValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }

                            titl1 = list.getModelLineData().get(0).getbValue().getValue();
                            titl2 = list.getModelLineData().get(2).getbValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L2","L2","","");
                            setTopTitle(titl1, titl2, titl3, titl4);

                            break;
                        case 4://L3
                            listitems.add(new ListViewItemObj("|V3|=", list.getModelLineData().get(0).getcValue().getValue() + (list.getModelLineData().get(0).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getcValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A3|=", list.getModelLineData().get(2).getcValue().getValue() + (list.getModelLineData().get(2).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getcValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getcValue().getValue()) - Float.valueOf(list.getModelLineData().get(1).getcValue().getValue())),1)));
                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3=", list.getModelLineData().get(3).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(0).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(0).getcValue().getValue())) {
                                maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()));
                                maxfundV = Math.max(maxfundV, Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(0).getcValue().getValue()) / maxfundV;
                            }
                            if(DataFormatUtil.isNumber(list.getModelLineData().get(2).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getcValue().getValue())) {
                                maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                                maxfundA = Math.max(maxfundA, Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));
                                percentagel2 = Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()) / maxfundA;
                            }
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getcValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getcValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }

                            titl1 = list.getModelLineData().get(0).getcValue().getValue();
                            titl2 = list.getModelLineData().get(2).getcValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L3","L3","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                    }
                    break;
                case 1://3QOPEN LEG
                case 2://3QIT
                case 3://2-ELEMENT
                case 4://3QDELTA
                    switch (wir_right_index){
                        case 0://3U
                            listitems.add(new ListViewItemObj("|V1V2|=", list.getModelLineData().get(4).getaValue().getValue() + (list.getModelLineData().get(4).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|V2V3|=", list.getModelLineData().get(4).getbValue().getValue() + (list.getModelLineData().get(4).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|V3V1|=", list.getModelLineData().get(4).getcValue().getValue() + (list.getModelLineData().get(4).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getcValue().getValue_Unit())));

                            listitems.add(new ListViewItemObj("φV1V2=", list.getModelLineData().get(5).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φV2V3=", list.getModelLineData().get(5).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φV3V1=", list.getModelLineData().get(5).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(4).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(4).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(4).getcValue().getValue())) {
                                maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(4).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(4).getbValue().getValue()));
                                maxfundV = Math.max(maxfundV, Float.valueOf(list.getModelLineData().get(4).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(4).getaValue().getValue()) / maxfundV;
                            }

                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(5).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(4).getbValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(5).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);
                            if (list.getModelLineData().get(2).getcValue() != null) {
                                percentagel3 = Float.valueOf(list.getModelLineData().get(4).getcValue().getValue()) / maxfundV;
                                if (percentagel3 == 0)
                                    percentagel3 = 1;
                                l3 = new Pointer(Float.valueOf(list.getModelLineData().get(5).getcValue().getValue()), percentagel3, ColorList.SCOPE_LINE_COLOR[2]);
                                if (pointers.size() < 3)
                                    pointers.add(l3);
                                else
                                    pointers.set(2, l3);
                            } else {
                                if (pointers.size() > 2) {
                                    pointers.remove(2);
                                }
                            }

                            titl1 = list.getModelLineData().get(4).getaValue().getValue();
                            titl2 = list.getModelLineData().get(4).getbValue().getValue();
                            titl3 = list.getModelLineData().get(4).getcValue().getValue();
                            titl4 = "";

                            setTopLeftTitle("L1L2","L2L3","L3L1","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 1://3A
                            listitems.add(new ListViewItemObj("|A1A2|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2A3|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A3A1|=", list.getModelLineData().get(2).getcValue().getValue() + (list.getModelLineData().get(2).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getcValue().getValue_Unit())));

                            listitems.add(new ListViewItemObj("φA1A2=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2A3=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3A1=", list.getModelLineData().get(3).getcValue().getValue()));

                            if(DataFormatUtil.isNumber(list.getModelLineData().get(2).getaValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getbValue().getValue()) && DataFormatUtil.isNumber(list.getModelLineData().get(2).getcValue().getValue())) {
                                maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                                maxfundA = Math.max(maxfundA, Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));
                                percentagel1 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundA;
                            }

                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()) / maxfundA;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);
                            if (list.getModelLineData().get(4).getcValue() != null) {
                                percentagel3 = Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()) / maxfundA;
                                if (percentagel3 == 0)
                                    percentagel3 = 1;
                                l3 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getcValue().getValue()), percentagel3, ColorList.SCOPE_LINE_COLOR[2]);
                                if (pointers.size() < 3)
                                    pointers.add(l3);
                                else
                                    pointers.set(2, l3);
                            } else {
                                if (pointers.size() > 2) {
                                    pointers.remove(2);
                                }
                            }

                            titl1 = list.getModelLineData().get(2).getaValue().getValue();
                            titl2 = list.getModelLineData().get(2).getbValue().getValue();
                            titl3 = list.getModelLineData().get(2).getcValue().getValue();
                            titl4 = "";

                            setTopLeftTitle("L1L2","L2L3","L3L1","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                        case 2://L1
                            listitems.add(new ListViewItemObj("|V1V2|=", list.getModelLineData().get(4).getaValue().getValue() + (list.getModelLineData().get(4).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A1A2|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()) - Float.valueOf(list.getModelLineData().get(5).getaValue().getValue())),1)));

                            listitems.add(new ListViewItemObj("φA1A2=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2A3=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3A1=", list.getModelLineData().get(3).getcValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(4).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()));

                            percentagel1 = Float.valueOf(list.getModelLineData().get(4).getaValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundV;

                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }
                            titl1 = list.getModelLineData().get(4).getaValue().getValue();
                            titl2 = list.getModelLineData().get(2).getaValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1L2","L1L2","","");
                            setTopTitle(titl1, titl2, titl3, titl4);

                            break;
                        case 3://L2L3
                            listitems.add(new ListViewItemObj("|V2V3|=", list.getModelLineData().get(4).getbValue().getValue() + (list.getModelLineData().get(4).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2V3|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()) - Float.valueOf(list.getModelLineData().get(5).getbValue().getValue())),1)));
                            listitems.add(new ListViewItemObj("φA1A2=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2A3=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3A1=", list.getModelLineData().get(3).getcValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(4).getbValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));

                            percentagel1 = Float.valueOf(list.getModelLineData().get(4).getbValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }
                            titl1 = list.getModelLineData().get(4).getbValue().getValue();
                            titl2 = list.getModelLineData().get(2).getbValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L2L3","L2L3","","");
                            setTopTitle(titl1, titl2, titl3, titl4);

                            break;
                        case 4://L3
                            listitems.add(new ListViewItemObj("|V3V1|=", list.getModelLineData().get(4).getcValue().getValue() + (list.getModelLineData().get(4).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(4).getcValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A3A1|=", list.getModelLineData().get(2).getcValue().getValue() + (list.getModelLineData().get(2).getcValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getcValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getcValue().getValue()) - Float.valueOf(list.getModelLineData().get(5).getcValue().getValue())),1)));

                            listitems.add(new ListViewItemObj("φA1A2=", list.getModelLineData().get(3).getaValue().getValue()));
                            listitems.add(new ListViewItemObj("φA2A3=", list.getModelLineData().get(3).getbValue().getValue()));
                            listitems.add(new ListViewItemObj("φA3A1=", list.getModelLineData().get(3).getcValue().getValue()));


                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(4).getcValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()));

                            percentagel1 = Float.valueOf(list.getModelLineData().get(4).getcValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getcValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            if (pointers.size() > 2) {
                                pointers.remove(2);
                            }
                            titl1 = list.getModelLineData().get(4).getcValue().getValue();
                            titl2 = list.getModelLineData().get(2).getcValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L3L1","L3L1","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                    }
                    break;
                case 7://1Q SPLIT PHASE
                    switch (wir_right_index){
                        case 0://2V
                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|V2|=", list.getModelLineData().get(0).getbValue().getValue() + (list.getModelLineData().get(0).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φV1=", list.getModelLineData().get(1).getaValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()));

                            percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = list.getModelLineData().get(6).getbValue().getValue();
                            titl3 = list.getModelLineData().get(6).getnValue().getValue();
                            titl4 = "";

                            setTopLeftTitle("L1","L2","N","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 1://2A
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));

                            maxfundA = Math.max(Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                            percentagel1 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) /maxfundA;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()) /maxfundA;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(7).getaValue().getValue();
                            titl2 = list.getModelLineData().get(7).getbValue().getValue();
                            titl3 = list.getModelLineData().get(7).getnValue().getValue();
                            titl4 = "";

                            setTopLeftTitle("L1","L2","N","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                        case 2://L1

                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()) - Float.valueOf(list.getModelLineData().get(5).getaValue().getValue())),1)));

                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(4).getaValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()));

                            percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = list.getModelLineData().get(7).getaValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1","L1","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 3://L2
                            listitems.add(new ListViewItemObj("|V2|=", list.getModelLineData().get(0).getbValue().getValue() + (list.getModelLineData().get(0).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A2|=", list.getModelLineData().get(2).getbValue().getValue() + (list.getModelLineData().get(2).getbValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getbValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()) - Float.valueOf(list.getModelLineData().get(1).getbValue().getValue())),1)));

                            listitems.add(new ListViewItemObj("φA1=", list.getModelLineData().get(3).getaValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()));
                            percentagel1 = Float.valueOf(list.getModelLineData().get(0).getbValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getbValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getbValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(6).getbValue().getValue();
                            titl2 = list.getModelLineData().get(7).getbValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L2","L2","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                    }
                    break;
                case 8://1Q IT NO NEUTRAL
                    switch (wir_right_index){
                        case 0://V
                            listitems.add(new ListViewItemObj("|V1V2|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));

                            percentagel1 = 1;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = "";
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1L2","","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 1://A
                            listitems.add(new ListViewItemObj("|A1A2|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));

                            percentagel1 = 1;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            titl1 = list.getModelLineData().get(7).getaValue().getValue();
                            titl2 = "";
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1L2","","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                        case 2://L1L2
                            listitems.add(new ListViewItemObj("|V1V2|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A1A2|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V", DataFormatUtil.frontCompWithZore((Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()) - Float.valueOf(list.getModelLineData().get(5).getaValue().getValue())),1)));

                            listitems.add(new ListViewItemObj("φA1A2=", list.getModelLineData().get(3).getaValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()));
                            percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) /maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue());
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = list.getModelLineData().get(7).getaValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1L2","L1L2","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                    }
                    break;
                case 9://1Q +NEUTRAL
                    switch (wir_right_index){
                        case 0://V
                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));

                            percentagel1 = 1f;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(1).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = list.getModelLineData().get(6).getnValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1","N","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                        case 1://A
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));

                            percentagel1 = 1;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);

                            titl1 = list.getModelLineData().get(7).getaValue().getValue();
                            titl2 = list.getModelLineData().get(7).getnValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1","N","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;
                        case 2://L1

                            listitems.add(new ListViewItemObj("|V1|=", list.getModelLineData().get(0).getaValue().getValue() + (list.getModelLineData().get(0).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(0).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("|A1|=", list.getModelLineData().get(2).getaValue().getValue() + (list.getModelLineData().get(2).getaValue().getValue_Unit()==null?"":list.getModelLineData().get(2).getaValue().getValue_Unit())));
                            listitems.add(new ListViewItemObj("φA-V=", list.getModelLineData().get(3).getaValue().getValue()));

                            maxfundV = Math.max(Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()), Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()));
                            percentagel1 = Float.valueOf(list.getModelLineData().get(0).getaValue().getValue()) / maxfundV;
                            if (percentagel1 == 0)
                                percentagel1 = 1;
                            l1 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getaValue().getValue()), percentagel1, ColorList.SCOPE_LINE_COLOR[0]);
                            if (pointers.size() < 1)
                                pointers.add(l1);
                            else
                                pointers.set(0, l1);
                            percentagel2 = Float.valueOf(list.getModelLineData().get(2).getaValue().getValue()) / maxfundV;
                            if (percentagel2 == 0)
                                percentagel2 = 1;
                            l2 = new Pointer(Float.valueOf(list.getModelLineData().get(3).getbValue().getValue()), percentagel2, ColorList.SCOPE_LINE_COLOR[1]);
                            if (pointers.size() < 2)
                                pointers.add(l2);
                            else
                                pointers.set(1, l2);

                            titl1 = list.getModelLineData().get(6).getaValue().getValue();
                            titl2 = list.getModelLineData().get(7).getaValue().getValue();
                            titl3 = "";
                            titl4 = "";

                            setTopLeftTitle("L1","L1","","");
                            setTopTitle(titl1, titl2, titl3, titl4);
                            break;

                    }
                    break;


            }
        }catch (NumberFormatException e){

        }

    }


    @Override
    public void setShowMeterData(ModelAllData modelAllData) {


    }

    @Override
    public void setShowMeterData(final ModelAllData modelAllData, int funTypeIndex) {
        List<ModelLineData> modelLineData = modelAllData.getModelLineData();
        if(modelLineData!=null) {
            roundpointer.post(new Runnable() {
                @Override
                public void run() {
                    tv_hz.setText(modelAllData.getHzData()==null || modelAllData.getHzData().equals("- - -")?"----Hz": DataFormatUtil.formatValue(Float.valueOf(modelAllData.getHzData()),2) + "Hz");
                }
            });
            addSelectMeterData(wir_index,wir_right_index,modelAllData);
            upDataUI();
        }
    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex) {

    }

    @Override
    public void setShowMeterData(ModelAllData modelAllData, int wir_index, int wir_right_index, int popwindowsIndex, boolean showCursor) {

    }

    @Override
    public void setShowMeterData(BaseMeterData baseMeterData) {

    }

    @Override
    public void onInitViews() {
        configHz = getResources().getStringArray(R.array.confighz_array)[config.getConfig_nominal()];
        configV = config.getConfig_vnom_value();
        scope_config_tv = (TextView) findViewById(R.id.scope_config_tv);
        showItem2=getString(R.string.set_wir_item).split(",");
        setScopeTopView(20f,"PHASOR"   + "      " +  configV + "      " + configHz + "      " +  showItem2[wir_index]);

        listitems = new ArrayList<>();
        roundpointer = (DrawRoundpointer) findViewById(R.id.scopeRight);
        UnbalanceLeft = (ListView) findViewById(R.id.scopeLeft);
        adapter = new ListAdapter();
        UnbalanceLeft.setAdapter(adapter);
        textview_l1 = (MyNoPaddingTextView) findViewById(R.id.textview_l1);
        textview_l2 = (MyNoPaddingTextView) findViewById(R.id.textview_l2);
        textview_l3 = (MyNoPaddingTextView) findViewById(R.id.textview_l3);
        textview_ln = (MyNoPaddingTextView) findViewById(R.id.textview_l4);

        l1a = (TextView) findViewById(R.id.l1a);
        l1b = (TextView) findViewById(R.id.l1b);
        l1c = (TextView) findViewById(R.id.l1c);
        n1 = (TextView) findViewById(R.id.n1);

        top_l1a_rl = (RelativeLayout) findViewById(R.id.top_l1a_rl);
        top_l1b_rl = (RelativeLayout) findViewById(R.id.top_l1b_rl);
        top_l1c_rl = (RelativeLayout) findViewById(R.id.top_l1c_rl);
        top_ln_rl = (RelativeLayout) findViewById(R.id.top_n1_rl);

        strList =  new ArrayList();
        rightModeView = (RightModeView) findViewById(R.id.modeview);
        tv_hz = (TextView) findViewById(R.id.tv_hz);
        rightModeView.setUpDownClick(false);
        rightModeView.hideUpDownView();
        String[] showItems = getString(R.string.set_wir_item).split(",");
        switch (wir_index) {
            case 0://3QWYE
            case 5://3QHIGH LEG
            case 6://2½-ELEMENT
                strList.clear();
                strList.add(new RightListViewItemObj("3V", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                strList.add(new RightListViewItemObj("L3", -1));

                setTopLeftTitle("L1","L2","L3","N");
                setTopTitle("----", "----", "----", "----");

                break;
            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                strList.clear();
                strList.add(new RightListViewItemObj("3U", -1));
                strList.add(new RightListViewItemObj("3A", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));
                strList.add(new RightListViewItemObj("L2L3", -1));
                strList.add(new RightListViewItemObj("L3L1", -1));

                setTopLeftTitle("L1","L2","L3","");
                setTopTitle("----", "----", "----", "");


                break;
            case 8://1Q IT NO NEUTRAL
                strList.clear();
                strList.add(new RightListViewItemObj("V", -1));
                strList.add(new RightListViewItemObj("A", -1));
                strList.add(new RightListViewItemObj("L1L2", -1));

                setTopLeftTitle("L1L2","","","");
                setTopTitle("----", "", "", "");

                break;
            case 7://1Q SPLIT PHASE
                strList.clear();
                strList.add(new RightListViewItemObj("2V", -1));
                strList.add(new RightListViewItemObj("2A", -1));
                strList.add(new RightListViewItemObj("L1", -1));
                strList.add(new RightListViewItemObj("L2", -1));
                setTopLeftTitle("L1","L2","N","");
                setTopTitle("----", "----", "----", "");
                break;
            case 9://1Q +NEUTRAL
                strList.clear();
                strList.add(new RightListViewItemObj("V", -1));
                strList.add(new RightListViewItemObj("A", -1));
                strList.add(new RightListViewItemObj("L1", -1));

                setTopLeftTitle("L1","N","","");
                setTopTitle("----", "----", "", "");
                break;

        }
        rightModeView.setModeList(strList);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                changeRightIndex = true;
//                onWirAndRightIndexCallBack.returnWirAndRight(wir_index,wir_right_index);
                updateWirData(wir_index,wir_right_index);
            }
        });

        UnbalanceLeft.setFocusable(false);
        rightModeView.setUpDownClick(false);
        rightModeView.hideUpDownView();
        rightModeView.getViewFoucs();
        rightModeView.setSelection(0);

    }

    public void setScopeTopView(float textSize, String s) {
        if(scope_config_tv!=null){
            scope_config_tv.setText(s);
            scope_config_tv.setTextSize(textSize);
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_scope_vector_layout;
    }

    /**
     * 防止点击切换右边模式时 数据未传送过来显示空白的处理
     * @param wir_index
     * @param wir_right_index
     */
    private void updateWirData(int wir_index, int wir_right_index){

        switch (wir_index) {
            case 0://3QWYE
            case 6://2½-ELEMENT
                switch (wir_right_index){
                    case 0://3V
                    case 1://3A
                        setTopLeftTitle("L1","L2","L3","N");
                        setTopTitle("----", "----", "----", "----");
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","L1","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 3://L2
                        setTopLeftTitle("L2","L2","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 4://L3
                        setTopLeftTitle("L3","L3","","");
                        setTopTitle("----", "----", "", "");
                        break;
                }
            case 5://3QHIGH LEG
                switch (wir_right_index){
                    case 0://3V
                    case 1://3A
                        setTopLeftTitle("L1","L2","L3","N");
                        setTopTitle("----", "----", "----", "----");
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","L1","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 3://L2
                        setTopLeftTitle("L2","L2","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 4://L3
                        setTopLeftTitle("L3","L3","","");
                        setTopTitle("----", "----", "", "");
                        break;
                }
                break;

            case 1://3QOPEN LEG
            case 2://3QIT
            case 3://2-ELEMENT
            case 4://3QDELTA
                switch (wir_right_index){
                    case 0://3U
                    case 1://3A
                        setTopLeftTitle("L1L2","L2L3","L3L1","");
                        setTopTitle("----", "----", "----", "");
                        break;
                    case 2://L1L2
                        setTopLeftTitle("L1L2","L1L2","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 3://L2L3
                        setTopLeftTitle("L2L3","L2L3","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 4://L3L1
                        setTopLeftTitle("L3L1","L3L1","","");
                        setTopTitle("----", "----", "", "");
                        break;
                }

                break;

            case 8://1Q IT NO NEUTRAL
                switch (wir_right_index){
                    case 0://U
                    case 1://A
                        setTopLeftTitle("L1L2","","","");
                        setTopTitle("----", "", "", "");
                        break;
                    case 2://L1L2
                        setTopLeftTitle("L1L2","L1L2","","");
                        setTopTitle("----", "----", "", "");
                        break;

                }
                break;
            case 7://1Q SPLIT PHASE
                switch (wir_right_index){
                    case 0://2V
                    case 1://2A
                        setTopLeftTitle("L1","L2","N","");
                        setTopTitle("----", "----", "----", "");
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","L1","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 3://L2
                        setTopLeftTitle("L2","L2","","");
                        setTopTitle("----", "----", "", "");
                        break;
                }
                break;
            case 9://1Q +NEUTRAL
                switch (wir_right_index){
                    case 0://V
                    case 1://A
                        setTopLeftTitle("L1","N","","");
                        setTopTitle("----", "----", "", "");
                        break;
                    case 2://L1
                        setTopLeftTitle("L1","L1","","");
                        setTopTitle("----", "----", "", "");
                        break;

                }
                break;
        }

    }

    protected void setTopLeftTitle(final String l1, final String l2,final String l3,final String ln){
        if(l1a!=null) {
            l1a.post(new Runnable() {
                @Override
                public void run() {
                    if (l1 == null || l1.equals("")) {
                        if (l1a.getVisibility() != View.INVISIBLE)
                            l1a.setVisibility(View.INVISIBLE);
                    } else {
                        if (l1a.getVisibility() != View.VISIBLE)
                            l1a.setVisibility(View.VISIBLE);
                    }
                    if (l2 == null || l2.equals("")) {
                        if (l1b.getVisibility() != View.INVISIBLE)
                            l1b.setVisibility(View.INVISIBLE);
                    } else {
                        if (l1b.getVisibility() != View.VISIBLE)
                            l1b.setVisibility(View.VISIBLE);
                    }

                    if (l3 == null || l3.equals("")) {
                        if (l1c.getVisibility() != View.INVISIBLE)
                            l1c.setVisibility(View.INVISIBLE);
                    } else {
                        if (l1c.getVisibility() != View.VISIBLE)
                            l1c.setVisibility(View.VISIBLE);
                    }
                    if (ln == null || ln.equals("")) {
                        if (n1.getVisibility() != View.INVISIBLE)
                            n1.setVisibility(View.INVISIBLE);
                    } else {
                        if (n1.getVisibility() != View.VISIBLE)
                            n1.setVisibility(View.VISIBLE);
                    }

                    l1a.setText(l1);
                    l1b.setText(l2);
                    l1c.setText(l3);
                    n1.setText(ln);
                }
            });
        }

    }


    protected void setTopTitle(final String l1, final String l2,final String l3,final String ln){
        textview_l1.post(new Runnable() {
            @Override
            public void run() {
                if (l1 == null || l1.equals("")) {
                    if (top_l1a_rl.getVisibility() != View.INVISIBLE)
                        top_l1a_rl.setVisibility(View.INVISIBLE);
                } else {
                    if (top_l1a_rl.getVisibility() != View.VISIBLE)
                        top_l1a_rl.setVisibility(View.VISIBLE);
                }
                if (l2 == null || l2.equals("")) {
                    if (top_l1b_rl.getVisibility() != View.INVISIBLE)
                        top_l1b_rl.setVisibility(View.INVISIBLE);
                } else {
                    if (top_l1b_rl.getVisibility() != View.VISIBLE)
                        top_l1b_rl.setVisibility(View.VISIBLE);
                }

                if (l3 == null || l3.equals("")) {
                    if (top_l1c_rl.getVisibility() != View.INVISIBLE)
                        top_l1c_rl.setVisibility(View.INVISIBLE);
                } else {
                    if (top_l1c_rl.getVisibility() != View.VISIBLE)
                        top_l1c_rl.setVisibility(View.VISIBLE);
                }
                if (ln == null || ln.equals("")) {
                    if (top_ln_rl.getVisibility() != View.INVISIBLE)
                        top_ln_rl.setVisibility(View.INVISIBLE);
                } else {
                    if (top_ln_rl.getVisibility() != View.VISIBLE)
                        top_ln_rl.setVisibility(View.VISIBLE);
                }

                textview_l1.setText(l1);
                textview_l2.setText(l2);
                textview_l3.setText(l3);
                textview_ln.setText(ln);
            }
        });


    }

    protected void upDataUI() {
        if(roundpointer!=null) {
            roundpointer.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    roundpointer.setPointer(pointers);
                }
            });
        }
    }



    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listitems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListAdapter.ViewHoder hoder;
            if (view == null) {
                hoder = new ListAdapter.ViewHoder();
                view = viewGroup.inflate(viewGroup.getContext(), R.layout.unbalance_listviewitem_layout, null);
                hoder.title = view.findViewById(R.id.unbalnceText1);
                hoder.value = view.findViewById(R.id.unbalnceText2);
                view.setTag(hoder);
            } else {
                hoder = (ListAdapter.ViewHoder) view.getTag();
            }
            if (i < listitems.size()) {
                hoder.title.setText(listitems.get(i).title);
                hoder.value.setText(listitems.get(i).value);
            }
            return view;
        }

        private class ViewHoder {
            TextView title;
            TextView value;
        }
    }
    protected class ListViewItemObj {
        private String title;
        private String value;

        protected ListViewItemObj(String title, String value) {
            this.title = title;
            this.value = value;
        }
    }
}
