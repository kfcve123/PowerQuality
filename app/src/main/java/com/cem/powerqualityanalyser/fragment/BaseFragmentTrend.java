package com.cem.powerqualityanalyser.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.BaseMeterData;
import com.cem.powerqualityanalyser.callback.CustomTimer;
import com.cem.powerqualityanalyser.callback.CustomTimerCallback;
import com.cem.powerqualityanalyser.libs.MeterData;
import com.cem.powerqualityanalyser.libsnew.PhaseObj;
import com.cem.powerqualityanalyser.libsnew.PhaseObjN;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.DataFormatUtil;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;

import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.ModelBaseData;
import serialport.amos.cem.com.libamosserial.ModelLineData;


public abstract class BaseFragmentTrend extends BaseFragment{

    public abstract void setShowMeterData(ModelAllData modelAllData);

    public abstract void setShowMeterData(ModelAllData modelAllData,int funTypeIndex);

    public abstract void setShowMeterData(ModelAllData modelAllData,int wir_index,int wir_right_index,int popwindowsIndex);

    public abstract void setShowMeterData(ModelAllData modelAllData,int wir_index,int wir_right_index,int popwindowsIndex,boolean showCursor);

    public  abstract void setShowMeterData(BaseMeterData baseMeterData);
    public  abstract void onInitViews();
    private TextView leftText,middleText;
    private CustomTimer myTimer;// 倒计时计时器
    protected  int maxTime=10;
    protected int maxDataCount=0;//最大接收处理数据 用于定时器倒计时使用
    protected  int recDataIndex=0;//当前接收处理数据
    protected AppConfig config;
    protected int wir_index = 0; //接线方式
    protected int wir_right_index = 0;


    public interface OnWirAndRightIndexCallBack{
        void returnWirAndRight(int wir,int right);
    }

    protected OnWirAndRightIndexCallBack onWirAndRightIndexCallBack;

    public void setOnWirAndRightIndexCallBack(OnWirAndRightIndexCallBack callBack){
        this.onWirAndRightIndexCallBack = callBack;
    }

    public ModelLineData isOlModelLineData(ModelLineData modelLineData){


        return null;
    }


    @Override
    public int setContentView() {
 //       return R.layout.activity_group_list;
        return -1;
    }
    @Override
    public void onInitView(){
        config = AppConfig.getInstance();
        wir_index = config.getSet_Wir();
        myTimer=new CustomTimer(1000,maxTime);
        myTimer.setOnTimeCallback(new CustomTimerCallback() {
            @Override
            public void OnTimeTick(final String s, long l, boolean b) {
             final int time= (int) (maxTime-l/1000);
                middleText.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time!=0)
                            middleText.setText(String.format("00:00:%02d",time));
                        else {
                            middleText.setText("");
                        }
                    }
                });

            }
        });
        leftText= (TextView) findViewById(R.id.Group_list_leftText);
        middleText= (TextView) findViewById(R.id.Group_list_middleText);
        onInitViews();
    }
    protected  void setLeftText(String str){
        leftText.setText(str);
    }
    protected  void setLeftText(int strRes){
        leftText.setText(strRes);
    }
    public void StartTime(){
        myTimer.StartTimer();
    }
    public void StopTime(){
        myTimer.StopTimer();
        middleText.post(new Runnable() {
            @Override
            public void run() {
                middleText.setText("");
            }
        });
    }

    public void startRecordTime(){

    }

    /*private int getVOlValue(String nominal){

        return Float.valueOf(config.getConfig_vnom())
    }*/


    /**
     *
     * @param str
     * @param index
     * @param groupListObj
     * @param model
     * @param showitemSize
     * @param group  决定是去取A,or B, or C  or N 相值显示
     */
    protected void addMeterData(SpannableString str, int index, MeterGroupListObj groupListObj, ModelLineData model, int showitemSize,String group) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
            }
            boolean b = str.toString().equals("kvar") || str.toString().equals("kvarh");
            if (item.getItemSize() < showitemSize) {
                if(groupListObj.getHeaderSize() == 5){
                    if(b){
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit(),model.getaValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getaValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                        item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit(),model.getbValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getbValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                        item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit(),model.getcValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getcValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                        item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit(),model.getnValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getnValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                    }else {
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                        item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                        item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                        item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                    }
                }else {
                    if(groupListObj.getHeaderSize() == 4) {
                        if(b){
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit(),model.getaValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getaValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit(),model.getbValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getbValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit(),model.getnValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getnValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            } else {
                                item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit(),model.getcValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getcValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            }
                        }else {
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                            item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                            if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                            } else {
                                item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                            }
                        }
                    }else if(groupListObj.getHeaderSize() == 3){
                        if(b){
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit(),model.getaValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getaValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit(),model.getnValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getnValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            } else {
                                item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit(),model.getbValue().getValueType()== ModelBaseData.EM_valueType.GAN_XING,model.getbValue().getValueType()== ModelBaseData.EM_valueType.RONG_XING);
                            }

                        }else {
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                            if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                            } else {
                                item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                            }
                        }
                    } else if(groupListObj.getHeaderSize() == 2){
                        if(b) {
                            if (group.equals("L1")) {
                                item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit(), model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                            } else if (group.equals("L2")) {
                                item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit(), model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                            } else if (group.equals("L3")) {
                                item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit(), model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                            } else if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit(), model.getnValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getnValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                            } else {
                                item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit(), model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                            }
                        }else {
                            if (group.equals("L1")) {
                                item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                            } else if (group.equals("L2")) {
                                item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                            } else if (group.equals("L3")) {
                                item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                            } else if (group.equals("N")) {
                                item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                            } else {
                                item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                            }
                        }
                    }
                }

            } else {
                if(groupListObj.getHeaderSize() == 5){
                    if(b){
                        item.setItem(1, model.getaValue().getValue(),model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        item.setItem(2, model.getbValue().getValue(),model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        item.setItem(3, model.getcValue().getValue(),model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        item.setItem(4, model.getnValue().getValue(),model.getnValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getnValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                    }else {
                        item.setItem(1, model.getaValue().getValue());
                        item.setItem(2, model.getbValue().getValue());
                        item.setItem(3, model.getcValue().getValue());
                        item.setItem(4, model.getnValue().getValue());
                    }
                }else if(groupListObj.getHeaderSize() == 4) {
                    if(b){
                        item.setItem(1, model.getaValue().getValue(),model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        item.setItem(2, model.getbValue().getValue(),model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        if (group.equals("N")) {
                            item.setItem(3, model.getnValue().getValue(),model.getnValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getnValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else {
                            item.setItem(3, model.getcValue().getValue(),model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        }
                    }else {
                        item.setItem(1, model.getaValue().getValue());
                        item.setItem(2, model.getbValue().getValue());
                        if (group.equals("N")) {
                            item.setItem(3, model.getnValue().getValue());
                        } else {
                            item.setItem(3, model.getcValue().getValue());
                        }
                    }
                }else if(groupListObj.getHeaderSize() == 3){
                    if(b){
                        item.setItem(1, model.getaValue().getValue(),model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        if (group.equals("N")) {
                            item.setItem(2, model.getnValue().getValue(),model.getnValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getnValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else {
                            item.setItem(2, model.getbValue().getValue(),model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        }
                    }else {
                        item.setItem(1, model.getaValue().getValue());
                        if (group.equals("N")) {
                            item.setItem(2, model.getnValue().getValue());
                        } else {
                            item.setItem(2, model.getbValue().getValue());
                        }
                    }
                } else if(groupListObj.getHeaderSize() == 2){
                    if(b){
                        if (group.equals("L1")) {
                            item.setItem(1, model.getaValue().getValue(),model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else if (group.equals("L2")) {
                            item.setItem(1, model.getbValue().getValue(),model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else if (group.equals("L3")) {
                            item.setItem(1, model.getcValue().getValue(),model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else if (group.equals("N")) {
                            item.setItem(1, model.getnValue().getValue(),model.getnValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getnValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        } else {
                            item.setItem(1, model.getaValue().getValue(),model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING, model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING);
                        }

                    }else {
                        if (group.equals("L1")) {
                            item.setItem(1, model.getaValue().getValue());
                        } else if (group.equals("L2")) {
                            item.setItem(1, model.getbValue().getValue());
                        } else if (group.equals("L3")) {
                            item.setItem(1, model.getcValue().getValue());
                        } else if (group.equals("N")) {
                            item.setItem(1, model.getnValue().getValue());
                        } else {
                            item.setItem(1, model.getaValue().getValue());
                        }
                    }


                }
                //item.setItem(4, model.getDm_N());
            }

        } catch (Exception ex) {
//            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }

    /**
     *
     * @param str
     * @param index
     * @param groupListObj
     * @param model
     * @param ol  是否超出量程值
     * @param showitemSize
     * @param group
     */
    protected void addMeterData(SpannableString str, int index, MeterGroupListObj groupListObj, ModelLineData model, boolean ol,int showitemSize,String group) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
            }
            if (item.getItemSize() < showitemSize) {
                if(groupListObj.getHeaderSize() == 5){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());item.AddMeterChildItem("OL",model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                    item.AddMeterChildItem(model.getnValue().getValue(),model.getnValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    if(group.equals("N")){
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getnValue().getValue_Unit());
                    }else {
                        item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                    }
                }else if(groupListObj.getHeaderSize() == 3){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    if(group.equals("N")){
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getnValue().getValue_Unit());
                    }else {
                        item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                    }
                } else if(groupListObj.getHeaderSize() == 2){
                    if(group.equals("L1")) {
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                    }else if(group.equals("L2")){
                        item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                    }else if(group.equals("L3")){
                        item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                    }else if(group.equals("N")){
                        item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                    }else{
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                    }
                }

            } else {
                if(groupListObj.getHeaderSize() == 5){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getbValue().getValue());
                    item.setItem(3,model.getcValue().getValue());
                    item.setItem(4,model.getnValue().getValue());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getbValue().getValue());
                    if(group.equals("N")){
                        item.setItem(3, model.getnValue().getValue());
                    }else {
                        item.setItem(3, model.getcValue().getValue());
                    }
                }else if(groupListObj.getHeaderSize() == 3){
                    item.setItem(1,model.getaValue().getValue());
                    if(group.equals("N")){
                        item.setItem(2, model.getnValue().getValue());
                    }else {
                        item.setItem(2, model.getbValue().getValue());
                    }
                } else if(groupListObj.getHeaderSize() == 2){

                    if(group.equals("L1")) {
                        item.setItem(1,model.getaValue().getValue());
                    }else if(group.equals("L2")){
                        item.setItem(1,model.getbValue().getValue());
                    }else if(group.equals("L3")){
                        item.setItem(1,model.getcValue().getValue());
                    }else if(group.equals("N")){
                        item.setItem(1,model.getnValue().getValue());
                    }else{
                        item.setItem(1,model.getaValue().getValue());
                    }


                }
                //item.setItem(4, model.getDm_N());
            }

        } catch (Exception ex) {
            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }

    /**
     *
     * @param str
     * @param index
     * @param groupListObj
     * @param model
     * @param showitemSize
     * @param group
     * @param total Power 模块的 求和处理
     */
    protected void addMeterData(SpannableString str, int index, MeterGroupListObj groupListObj, ModelLineData model, int showitemSize,String group,boolean total) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
            }
            //           log.e("=====getItemSize====" + item.getItemSize() + "-----------" + showitemSize);
            boolean b = str.toString().equals("kvar") || str.toString().equals("kvarh");
            if (item.getItemSize() < showitemSize) {
                if(groupListObj.getHeaderSize() == 5){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                    item.AddMeterChildItem(model.getnValue().getValue(),model.getnValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    if(group.equals("N")){
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getnValue().getValue_Unit());
                    }else {
                        item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                    }
                }else if(groupListObj.getHeaderSize() == 3){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    if(group.equals("N")){
                        item.AddMeterChildItem(model.getaValue().getValue(), model.getnValue().getValue_Unit());
                    }else {
                        item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                    }
                } else if(groupListObj.getHeaderSize() == 2){
                    if(total){
                        if(DataFormatUtil.isNumber(model.getaValue().getValue()) && DataFormatUtil.isNumber(model.getbValue().getValue()) && DataFormatUtil.isNumber(model.getcValue().getValue())) {
                            if(b){

                            }else {
                                float totalValue = model.getaValue().getValueFl() + model.getbValue().getValueFl() + model.getcValue().getValueFl();
                                item.AddMeterChildItem(totalValue + "", model.getaValue().getValue_Unit());
                            }
                        }else{
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                        }
                    }else{
                        if(group.equals("L1")) {
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                        }else if(group.equals("L2")){
                            item.AddMeterChildItem(model.getbValue().getValue(), model.getbValue().getValue_Unit());
                        }else if(group.equals("L3")){
                            item.AddMeterChildItem(model.getcValue().getValue(), model.getcValue().getValue_Unit());
                        }else if(group.equals("N")){
                            item.AddMeterChildItem(model.getnValue().getValue(), model.getnValue().getValue_Unit());
                        }else{
                            item.AddMeterChildItem(model.getaValue().getValue(), model.getaValue().getValue_Unit());
                        }
                    }
                }

            } else {
                if(groupListObj.getHeaderSize() == 5){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getbValue().getValue());
                    item.setItem(3,model.getcValue().getValue());
                    item.setItem(4,model.getnValue().getValue());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getbValue().getValue());
                    if(group.equals("N")){
                        item.setItem(3, model.getnValue().getValue());
                    }else {
                        item.setItem(3, model.getcValue().getValue());
                    }
                }else if(groupListObj.getHeaderSize() == 3){
                    item.setItem(1,model.getaValue().getValue());
                    if(group.equals("N")){
                        item.setItem(2, model.getnValue().getValue());
                    }else {
                        item.setItem(2, model.getbValue().getValue());
                    }
                } else if(groupListObj.getHeaderSize() == 2){
                    if(total){
                        if(DataFormatUtil.isNumber(model.getaValue().getValue()) && DataFormatUtil.isNumber(model.getbValue().getValue()) && DataFormatUtil.isNumber(model.getcValue().getValue())) {
                           if(b){
                               float totalValue  = 0f;
                               if(model.getaValue().getValueFl()> model.getbValue().getValueFl() && model.getaValue().getValueFl()> model.getcValue().getValueFl()){
                                   totalValue = model.getaValue().getValueFl();
                                   if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                        if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                            totalValue = totalValue + model.getbValue().getValueFl();
                                        }else if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                            totalValue = totalValue - model.getbValue().getValueFl();
                                        }else{

                                        }

                                       if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue + model.getcValue().getValueFl();
                                       }else if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue - model.getcValue().getValueFl();
                                       }else{

                                       }
                                       if(totalValue>0f)
                                            item.setItem(1, totalValue + "",true,false);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", false, true);
                                       }
                                   }else  if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){

                                       if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getbValue().getValueFl();
                                       }else if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getbValue().getValueFl();
                                       }else{

                                       }

                                       if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getcValue().getValueFl();
                                       }else if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getcValue().getValueFl();
                                       }else{

                                       }

                                       if(totalValue>0f)
                                           item.setItem(1, totalValue + "",false,true);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", true, true);
                                       }

                                   }else{
                                       totalValue = model.getaValue().getValueFl() + model.getbValue().getValueFl() + model.getcValue().getValueFl();
                                       item.setItem(1, totalValue + "");
                                   }
                               }else  if(model.getbValue().getValueFl()> model.getaValue().getValueFl() && model.getbValue().getValueFl()> model.getcValue().getValueFl()){
                                   totalValue = model.getbValue().getValueFl();
                                   if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                       if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue + model.getaValue().getValueFl();
                                       }else if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue - model.getaValue().getValueFl();
                                       }else{

                                       }

                                       if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue + model.getcValue().getValueFl();
                                       }else if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue - model.getcValue().getValueFl();
                                       }else{

                                       }

                                       if(totalValue>0f)
                                           item.setItem(1, totalValue + "",true,false);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", false, true);
                                       }
                                   }else  if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){

                                       if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getaValue().getValueFl();
                                       }else if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getaValue().getValueFl();
                                       }else{

                                       }

                                       if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getcValue().getValueFl();
                                       }else if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getcValue().getValueFl();
                                       }else{

                                       }

                                       if(totalValue>0f)
                                           item.setItem(1, totalValue + "",false,true);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", true, true);
                                       }
                                   }else{
                                       totalValue = model.getaValue().getValueFl() + model.getbValue().getValueFl() + model.getcValue().getValueFl();
                                       item.setItem(1, totalValue + "");
                                   }

                               }else  if(model.getcValue().getValueFl()> model.getaValue().getValueFl() && model.getcValue().getValueFl()> model.getbValue().getValueFl()){
                                   totalValue = model.getcValue().getValueFl();
                                   if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                       if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue + model.getaValue().getValueFl();
                                       }else if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue - model.getaValue().getValueFl();
                                       }else{

                                       }

                                       if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue + model.getbValue().getValueFl();
                                       }else if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue - model.getbValue().getValueFl();
                                       }else{

                                       }

                                       if(totalValue>0f)
                                           item.setItem(1, totalValue + "",true,false);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", false, true);
                                       }
                                   }else  if(model.getcValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){

                                       if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getaValue().getValueFl();
                                       }else if(model.getaValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getaValue().getValueFl();
                                       }else{

                                       }

                                       if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.RONG_XING){
                                           totalValue = totalValue + model.getbValue().getValueFl();
                                       }else if(model.getbValue().getValueType() == ModelBaseData.EM_valueType.GAN_XING){
                                           totalValue = totalValue - model.getbValue().getValueFl();
                                       }else{

                                       }

                                       if(totalValue>0f)
                                           item.setItem(1, totalValue + "",false,true);
                                       else {
                                           totalValue = - totalValue;
                                           item.setItem(1, totalValue + "", true, true);
                                       }
                                   }else{
                                       totalValue = model.getaValue().getValueFl() + model.getbValue().getValueFl() + model.getcValue().getValueFl();
                                       item.setItem(1, totalValue + "");
                                   }

                               }

                           }else {
                               float totalValue = model.getaValue().getValueFl() + model.getbValue().getValueFl() + model.getcValue().getValueFl();
                               item.setItem(1, totalValue + "");
                           }
                        }else{
                            item.setItem(1, model.getaValue().getValue());
                        }
                    }else {
                        if (group.equals("L1")) {
                            item.setItem(1, model.getaValue().getValue());
                        } else if (group.equals("L2")) {
                            item.setItem(1, model.getbValue().getValue());
                        } else if (group.equals("L3")) {
                            item.setItem(1, model.getcValue().getValue());
                        } else if (group.equals("N")) {
                            item.setItem(1, model.getnValue().getValue());
                        } else {
                            item.setItem(1, model.getaValue().getValue());
                        }
                    }


                }
                //item.setItem(4, model.getDm_N());
            }

        } catch (Exception ex) {
            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }


    protected void addMeterData(SpannableStringBuilder str, int index, MeterGroupListObj groupListObj, ModelLineData model, int showitemSize) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str.toString());
                groupListObj.addChild(item);
            }
            //               log.e("=====getItemSize====" + item.getItemSize() + "-----------" + showitemSize);
            if (item.getItemSize() < showitemSize) {
                if(groupListObj.getHeaderSize() == 5){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                    item.AddMeterChildItem(model.getnValue().getValue(),model.getnValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 4) {

                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 3){

                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                } else if(groupListObj.getHeaderSize() == 2){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                }

            } else {

                if(groupListObj.getHeaderSize() == 5){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                    item.setItem(3,model.getaValue().getValue());
                    item.setItem(4,model.getaValue().getValue());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                    item.setItem(3,model.getaValue().getValue());
                }else if(groupListObj.getHeaderSize() == 3){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                } else if(groupListObj.getHeaderSize() == 2){
                    item.setItem(1,model.getaValue().getValue());
                }
                //item.setItem(4, model.getDm_N());
            }

        } catch (Exception ex) {
            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }

    protected void addMeterData(String str, int index, MeterGroupListObj groupListObj, ModelLineData model, int showitemSize) {
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
  //              log.e("=====groupListObj.getChildSize() ===="  + groupListObj.getChildSize() + "-----------");
            }
 //               log.e("=====getItemSize====" + item.getItemSize() + "----------==" + showitemSize);
            if (item.getItemSize() < showitemSize) {
  //              log.e("=====getaValue=1212===" + groupListObj.getHeaderSize());
                if(groupListObj.getHeaderSize() == 5){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                    item.AddMeterChildItem(model.getnValue().getValue(),model.getnValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                    item.AddMeterChildItem(model.getcValue().getValue(),model.getcValue().getValue_Unit());
                }else if(groupListObj.getHeaderSize() == 3){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                    item.AddMeterChildItem(model.getbValue().getValue(),model.getbValue().getValue_Unit());
                } else if(groupListObj.getHeaderSize() == 2){
                    item.AddMeterChildItem(model.getaValue().getValue(),model.getaValue().getValue_Unit());
                }
                //                item.AddMeterChildItem(model.getDm_N());
            } else {
                               log.e("=====getaValue=1212===" + groupListObj.getHeaderSize());
                if(groupListObj.getHeaderSize() == 5){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                    item.setItem(3,model.getaValue().getValue());
                    item.setItem(4,model.getaValue().getValue());
                }else if(groupListObj.getHeaderSize() == 4) {
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                    item.setItem(3,model.getaValue().getValue());
                }else if(groupListObj.getHeaderSize() == 3){
                    item.setItem(1,model.getaValue().getValue());
                    item.setItem(2,model.getaValue().getValue());
                } else if(groupListObj.getHeaderSize() == 2){
                    item.setItem(1,model.getaValue().getValue());
                }
                //item.setItem(4, model.getDm_N());
            }

        } catch (Exception ex) {
            log.e("表格添加数据错误：" + ex.getMessage());
        }
    }

    protected void addMeterData(String str, int index, MeterGroupListObj groupListObj, PhaseObj obj){
       try {
           MeterGroupChildObj item;
           if (groupListObj.getChildSize() > index) {
               item = groupListObj.getChild(index);
           } else {
               item = new MeterGroupChildObj();
               item.AddMeterChildItem(str);
               groupListObj.addChild(item);
           }

           if (item.getItemSize() < 3 + 1) {
               item.AddMeterChildItem(Meter2String(obj.getPhaseA()),obj.getPhaseA().isGElectricit(),obj.getPhaseA().isElectricity());
               item.AddMeterChildItem(Meter2String(obj.getPhaseB()),obj.getPhaseB().isGElectricit(),obj.getPhaseB().isElectricity());
               item.AddMeterChildItem(Meter2String(obj.getPhaseC()),obj.getPhaseC().isGElectricit(),obj.getPhaseC().isElectricity());
           } else {
               item.setItem(1, Meter2String(obj.getPhaseA()));
               item.setItem(2, Meter2String(obj.getPhaseB()));
               item.setItem(3, Meter2String(obj.getPhaseC()));
           }
           if (obj instanceof PhaseObjN) {
               if (item.getItemSize() < 4 + 1) {
                   item.AddMeterChildItem(Meter2String(((PhaseObjN) obj).getPhaseN()), ((PhaseObjN) obj).getPhaseN().isGElectricit(),((PhaseObjN) obj).getPhaseN().isElectricity());
               } else {
                   item.setItem(4, (Meter2String(((PhaseObjN) obj).getPhaseN())));
               }
           }
       }catch ( Exception ex){
           log.e("表格添加数据错误："+ex.getMessage());
       }
    }

    protected void addMeterData(String str, int index, MeterGroupListObj groupListObj, MeterData meterData1, String unit1, MeterData meterData2, String unit2){
        try {
            MeterGroupChildObj item;
            if (groupListObj.getChildSize() > index) {
                item = groupListObj.getChild(index);
            } else {
                item = new MeterGroupChildObj();
                item.AddMeterChildItem(str);
                groupListObj.addChild(item);
            }

            if (item.getItemSize() < 4 + 1) {
                item.AddMeterChildItem(Meter2String(meterData1),meterData1.isGElectricit(),meterData1.isElectricity());
                item.AddMeterChildItem(unit1);
                item.AddMeterChildItem(Meter2String(meterData2),meterData1.isGElectricit(),meterData1.isElectricity());
                item.AddMeterChildItem(unit2);
            } else {
                item.setItem(1, Meter2String(meterData1));
                item.setItem(2, unit1);
                item.setItem(3, Meter2String(meterData2));
                item.setItem(4, unit2);
            }
        }catch ( Exception ex){
            log.e("表格添加数据错误："+ex.getMessage());
        }
    }

    protected String Meter2String(MeterData data){
        if (data == null)
            return "";
        String str=data.getShowValue();
        if (!str.contains("--")){
           str=String .format("%."+data.getDataPoint()+"f",data.getValue());

        }
        return str;
    }


    protected String Meter2String(int count, float data){
        String str=String .format("%."+count+"f",data);

        return str;
    }



    protected void addMeterData(String str, int index, MeterGroupListObj groupListObj, MeterData obj){
        MeterGroupChildObj item;
        if (groupListObj.getChildSize()>index){
            item=groupListObj.getChild(index);
        }else{
            item=new MeterGroupChildObj();
            item.AddMeterChildItem(str);
            groupListObj.addChild(item);
        }

        if (item.getItemSize()<1+1){
            item.AddMeterChildItem(Meter2String(obj),obj == null ? false : obj.isGElectricit(),obj == null ? false : obj.isElectricity());

        }else{
            item.setItem(1,Meter2String(obj));

        }
    }


    /**
     * 封装SpannableStringBuilder 拼接方法
     * @param content
     * @param start
     * @param end
     * @param res
     * @return
     */
    protected SpannableStringBuilder  getStringBuilder(String content,int start,int end,int res){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(content);
        ImageSpan imageSpan = new ImageSpan(this.getContext(), res);
        spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(0.6f);
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(0.6f);
        spannableString.setSpan(relativeSizeSpan1, start-1, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(relativeSizeSpan2, end, end+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 单文字的修改字体大小的拼接处理
     * @param content
     * @param start
     * @param end
     * @return
     */
    protected SpannableStringBuilder  getStringBuilder(String content,int start,int end,float proportion){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(content);
        RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(proportion);
        spannableString.setSpan(relativeSizeSpan1, start-1, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }


    protected SpannableStringBuilder getStringBuilder(String content,int start,int end){
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(content);
        RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(0.2f);
        spannableString.setSpan(relativeSizeSpan1, start-1, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 参考方法
     * @param s
     * @return
     */
    protected SpannableString getSpannableStringTest(String s){
        s = "Hello Everyone";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,5, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
    }

    /**
     * 正常输入
     * @param s
     * @return
     */
    protected SpannableString getSpannableString(String s){
        SpannableString ss1=  new SpannableString(s);
//        ss1.setSpan(new RelativeSizeSpan(2f), 0,5, 0); // set size
//        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
    }


    protected SpannableString getSpannableString(String s,float proportion,int start ,int end){
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(proportion), start,end, 0); // set size
        //       ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
    }

    protected SpannableString getSpannableString(String s,int start ,int end){
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(0.7f), start,end, 0); // set size
        //       ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        return ss1;
    }

    /**
     * 如何正确筛选显示A,B,C,N 的背景颜色
     * @param rightSize
     * @param dataColor
     */
    protected void refeshHeadColor(int rightSize,String dataColor){
        TypedArray ar = getResources().obtainTypedArray(R.array.colorview_arrays);
        final int len = ar.length();
        int[] dataColorArray = new int[len];
        for (int i = 0; i < len; i++){
            dataColorArray[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        if(rightSize>=5) {
            if (dataColor.equals("3L")) {
                ColorList.ALL_METER_TITLE_COLOR[0] = Color.parseColor("#305B7F");
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
                ColorList.ALL_METER_TITLE_COLOR[2] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
                ColorList.ALL_METER_TITLE_COLOR[3] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL3()-1]);
                ColorList.ALL_METER_TITLE_COLOR[4] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            } else if (dataColor.equals("L1")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
            } else if (dataColor.equals("L2")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
            } else if (dataColor.equals("L3")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL3()-1]);
            } else if (dataColor.equals("N")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            }
        }else{
            if (dataColor.equals("2L")) {
                ColorList.ALL_METER_TITLE_COLOR[0] = Color.parseColor("#305B7F");
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
                ColorList.ALL_METER_TITLE_COLOR[2] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
                ColorList.ALL_METER_TITLE_COLOR[3] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL3()-1]);
                ColorList.ALL_METER_TITLE_COLOR[4] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            } else if (dataColor.equals("L1")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
            } else if (dataColor.equals("L2")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
            } else if (dataColor.equals("N")) {
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            } else if (dataColor.equals("L1N")){
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
                ColorList.ALL_METER_TITLE_COLOR[2] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            } else if (dataColor.equals("L1L2N")){
                ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
                ColorList.ALL_METER_TITLE_COLOR[2] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
                ColorList.ALL_METER_TITLE_COLOR[3] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
            }
        }

    }

}
