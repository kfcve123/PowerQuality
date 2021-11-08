package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFiveFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildTwoFragment;
import com.cem.powerqualityanalyser.meterobject.RightListViewItemObj;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.AmountColorViewHorizontal;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;
import com.cem.powerqualityanalyser.view.RightModeView;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * 电气接线选择 2
 */
public class SetupChildWirActivity extends BaseActivity implements View.OnClickListener, AmountViewHorizontal.OnHorizonalAmountChangeListener {

    private Integer[] strRes,resours1,resours2,resours3,resours4,resours5,resours6,resours7;
    private AmountViewHorizontal set_wir_amountview;
    private ImageView set_wir_imv;
    private RightModeView rightModeView;
    private String[] showItem;
    private int wir_index = 0;
    private int wir_right_index = 0;
    private String[] showItem2;
    private List<RightListViewItemObj> strList;
    private TextView set_wir_tv;
    private ImageView set_wir_cir1,set_wir_cir2,set_wir_cir3,set_wir_cir4,set_wir_cir5,set_wir_cir6,set_wir_cir7,set_wir_cir8;
    private Integer[] colorRes;
    private int cirColorIndex1,cirColorIndex2,cirColorIndex3,cirColorIndex4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_two);
        colorRes = new Integer[]{R.mipmap.set_wir_color1,R.mipmap.set_wir_color2,R.mipmap.set_wir_color3,R.mipmap.set_wir_color4,R.mipmap.set_wir_color5};
//        resours1 = new Integer[]{R.mipmap.set_wir351,R.mipmap.set_wir352,R.mipmap.set_wir353,R.mipmap.set_wir354};
//        resours2 = new Integer[]{R.mipmap.set_wir341,R.mipmap.set_wir342,R.mipmap.set_wir343,R.mipmap.set_wir344};
//        resours3 = new Integer[]{R.mipmap.set_wir331,R.mipmap.set_wir332,R.mipmap.set_wir333,R.mipmap.set_wir334};
        resours1 = new Integer[]{R.mipmap.set_wir_2_a_element_a,R.mipmap.set_wir_2_a_element_b};

        resours4 = new Integer[]{R.mipmap.set_wir_2_b_element_a,R.mipmap.set_wir_2_b_element_b,R.mipmap.set_wir_2_b_element_c};
        resours3 = new Integer[]{R.mipmap.set_wir_3_delta_a,R.mipmap.set_wir_3_delta_b,R.mipmap.set_wir_3_delta_c};
        resours2 = new Integer[]{R.mipmap.set_wir_3_hight_leg_a,R.mipmap.set_wir_3_hight_leg_b,R.mipmap.set_wir_3_hight_leg_c};

        resours5 = new Integer[]{R.mipmap.set_wir_3_it_a,R.mipmap.set_wir_3_it_b,R.mipmap.set_wir_3_it_c};
        resours6 = new Integer[]{R.mipmap.set_wir_3_open_leg_a,R.mipmap.set_wir_3_open_leg_b,R.mipmap.set_wir_3_open_leg_c};
        resours7 = new Integer[]{R.mipmap.set_wir_3_wye_a,R.mipmap.set_wir_3_wye_b,R.mipmap.set_wir_3_wye_c};


        strRes = new Integer[]{R.string.set_wir_content1,R.string.set_wir_content2,R.string.set_wir_content3,R.string.set_wir_content4};
        wir_index = config.getSet_Wir();
        wir_right_index = config.getSetup_Wir_Right_Index();
        set_wir_imv = findViewById(R.id.set_wir_imv);

        showItem = getResources().getStringArray(R.array.set_wir_arrays1);
        set_wir_amountview = findViewById(R.id.set_wir_amountview);
        showItem2=getString(R.string.set_wir_item).split(",");
        set_wir_amountview.setDataArray(showItem2);
        set_wir_amountview.setAmount(wir_index + 1);
        set_wir_amountview.setContent(showItem2[wir_index]);
        set_wir_amountview.setValueBg(false);
        set_wir_tv = findViewById(R.id.set_wir_tv);
        set_wir_amountview.setOnHorizonalAmountChangeListener(this);
        rightModeView = findViewById(R.id.set_wir_rightmodeview);
        strList =  new ArrayList();
        getRightViewData(wir_index,wir_right_index);
//        strList.add(new RightListViewItemObj("Y4-V",-1));
//        strList.add(new RightListViewItemObj("Y4-A",-1));
//        strList.add(new RightListViewItemObj("W",-1));
//        rightModeView.setModeList(strList);

        rightModeView.setSelection(wir_right_index);
        rightModeView.setUpDownClick(false);
        rightModeView.setUpDownClick(false);
        rightModeView.setOnItemCheckCallBack(new RightModeView.OnItemCheckCallBack() {
            @Override
            public void onItemCheck(int item) {
                wir_right_index = item;
                switch (wir_index){
                    case 6:
                        set_wir_imv.setBackgroundResource(resours1[item]);
                        set_wir_tv.setText("");
                        break;
                    case 5:
                        set_wir_imv.setBackgroundResource(resours2[item]);
                        set_wir_tv.setText("");
                        break;
                    case 4:
                        set_wir_imv.setBackgroundResource(resours3[item]);
                        set_wir_tv.setText("");
                        break;
                    case 3:
                        set_wir_imv.setBackgroundResource(resours4[item]);
                        set_wir_tv.setText("");
                        break;
                    case 2:
                        set_wir_imv.setBackgroundResource(resours5[item]);
                        set_wir_tv.setText("");
                        break;
                    case 1:
                        set_wir_imv.setBackgroundResource(resours6[item]);
                        set_wir_tv.setText("");
                        break;
                    case 0:
                        set_wir_imv.setBackgroundResource(resours7[item]);
                        set_wir_tv.setText("");
                        break;
                }
                config.setSetup_Wir_Right_Index(wir_right_index);
            }
        });

        cirColorIndex1 = config.getSetup_Show_Color_VL1();
        cirColorIndex2 = config.getSetup_Show_Color_VL2();
        cirColorIndex3 = config.getSetup_Show_Color_VL3();
        cirColorIndex4 = config.getSetup_Show_Color_VN();

        set_wir_cir1 = findViewById(R.id.set_wir_cir1);
        set_wir_cir2 = findViewById(R.id.set_wir_cir2);
        set_wir_cir3 = findViewById(R.id.set_wir_cir3);
        set_wir_cir4 = findViewById(R.id.set_wir_cir4);
        set_wir_cir5 = findViewById(R.id.set_wir_cir5);
        set_wir_cir6 = findViewById(R.id.set_wir_cir6);
        set_wir_cir7 = findViewById(R.id.set_wir_cir7);
        set_wir_cir8 = findViewById(R.id.set_wir_cir8);

        set_wir_cir1.setImageResource(colorRes[cirColorIndex1-1]);
        set_wir_cir2.setImageResource(colorRes[cirColorIndex2-1]);
        set_wir_cir3.setImageResource(colorRes[cirColorIndex3-1]);
        set_wir_cir4.setImageResource(colorRes[cirColorIndex4-1]);

        set_wir_cir5.setImageResource(colorRes[cirColorIndex1-1]);
        set_wir_cir6.setImageResource(colorRes[cirColorIndex2-1]);
        set_wir_cir7.setImageResource(colorRes[cirColorIndex3-1]);
        set_wir_cir8.setImageResource(colorRes[cirColorIndex4-1]);


    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void getRightViewData(int index){
        strList.clear();
        switch (index){
            case 9:// 1Q +NEUTRAL

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.INVISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_neutral);

                break;

            case 8://1Q IT NO NEUTRAL
                set_wir_tv.setText("");
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_no_neutral);
                rightModeView.setVisibility(View.INVISIBLE);
                break;
            case 7://1Q SPLIT PHASE
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.INVISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_split_phase);
                break;

            case 6: //2½-ELEMENT
                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_2_a_element_a);
                break;
            case 5://3QHIGH LEG

                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V1V2",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_3_hight_leg_a);
                break;


            case 4://3QDELTA
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_3_delta_a);
                break;
            case 3://2-ELEMENT
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_2_b_element_a);
                break;


            case 2://3QIT
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_3_it_a);
                break;
            case 1://3QOPEN LEG
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_3_open_leg_a);
                break;

            case 0://3QWYE
                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V1V2",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_3_wye_a);
                break;

        }
        rightModeView.setSelection(0);
        rightModeView.setModeList(strList);
        rightModeView.notifyDataSetChanged();
    }

    private void getRightViewData(int index,int rightIndex){
        strList.clear();
        switch (index){
            case 9:// 1Q +NEUTRAL
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.INVISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_neutral);
                break;

            case 8://1Q IT NO NEUTRAL
                set_wir_tv.setText("");
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_no_neutral);
                rightModeView.setVisibility(View.INVISIBLE);
                break;
            case 7://1Q SPLIT PHASE
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.INVISIBLE);
                set_wir_imv.setBackgroundResource(R.mipmap.set_wir_split_phase);
                break;

            case 6: //2½-ELEMENT
                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours1[rightIndex]);
                break;

            case 5://3QHIGH LEG

                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V1V2",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours4[rightIndex]);
                break;

            case 4://3QDELTA
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours3[rightIndex]);
                break;
            case 3://2-ELEMENT
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));

                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours2[rightIndex]);
                break;


            case 2://3QIT
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours5[rightIndex]);
                break;
            case 1://3QOPEN LEG
                strList.add(new RightListViewItemObj("3A",-1));
                strList.add(new RightListViewItemObj("A1A2",-1));
                strList.add(new RightListViewItemObj("A3A1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours6[rightIndex]);
                break;

            case 0://3QWYE
                strList.add(new RightListViewItemObj("3V",-1));
                strList.add(new RightListViewItemObj("V1V2",-1));
                strList.add(new RightListViewItemObj("V3V1",-1));
                set_wir_tv.setText("");
                rightModeView.setVisibility(View.VISIBLE);
                set_wir_imv.setBackgroundResource(resours7[rightIndex]);
                break;


        }
        rightModeView.setModeList(strList);
        rightModeView.notifyDataSetChanged();
    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
      return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.back)));
        data.add(new BaseBottomAdapterObj(1,null));
        data.add(new BaseBottomAdapterObj(2,null));
        data.add(new BaseBottomAdapterObj(3,null));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.Sure)));
        return data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        switch (obj.getId()) {
            case 0:
                onBackPressed();
                break;
            case 4:
                config.setSet_Wir(wir_index);
                getRightViewData(wir_index);
                onBackPressed();
                break;

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Left:
                moveCursor(-1);
//                log.e("========Left======" + wir_index);
                set_wir_amountview.setFocusable(true);
                set_wir_amountview.requestFocus();
                set_wir_amountview.setShowAmount(true);
                set_wir_amountview.setBackground(null);
                break;
            case Right:
                moveCursor(1);
 //               log.e("=======Right=======" + wir_index);
                if(wir_index>=showItem2.length-1){
                    rightModeView.setListViewFocusable(true);
//                    set_wir_amountview.setShowAmount(false);
                    rightModeView.requestListViewFocus();
                }else{
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
//        log.e("==============" + key);
        switch (key) {
            case Left:
                moveCursor(-1);
 //               log.e("========Left======" + wir_index);
                set_wir_amountview.setFocusable(true);
                set_wir_amountview.requestFocus();
                set_wir_amountview.setShowAmount(true);
                set_wir_amountview.setBackground(null);
                break;
            case Right:
                moveCursor(1);
//                log.e("=======Right=======" + wir_index);
                if(wir_index>=showItem2.length){
                    rightModeView.setListViewFocusable(true);
//                    set_wir_amountview.setShowAmount(false);
                    rightModeView.requestListViewFocus();
                }
                break;
            case Down:
                rightModeView.setListViewFocusable(true);
                rightModeView.requestListViewFocus();
                break;
        }

    }

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()){
            case R.id.set_wir_amountview:
//                log.e("=====wir_index====" + wir_index);
                wir_index = amount-1;
                break;
        }

    }

    /*public void moveItem(int i){
        if(rightModeView.hasFocus()){
            rightModeView.moveItem(i);
        }
    }*/

    public void moveCursor(int i) {
        if (set_wir_amountview.hasFocus()){
            if (i > 0)
                set_wir_amountview.getAmount_dncrease_horizontal().performClick();
            else
                set_wir_amountview.getAmount_increase_horizontal().performClick();
        }

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onDataReceived(byte[] bytes) {

    }

    @Override
    public void onDataReceivedModel(ModelAllData list) {

    }

    @Override
    public void onDataReceivedList(List list) {

    }
}