package com.cem.powerqualityanalyser.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.libs.MeterCommand;
import com.cem.powerqualityanalyser.tool.BleUtil;
import com.cem.powerqualityanalyser.tool.ColorList;
import com.cem.powerqualityanalyser.tool.LanguageStore;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.MyLinearLayout;
import com.cem.powerqualityanalyser.view.PublicPopupWindow;
import com.cem.powerqualityanalyser.view.TextImageView;
import com.jeremyliao.liveeventbus.LiveEventBus;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import serialport.amos.cem.com.libamosserial.Commad;
import serialport.amos.cem.com.libamosserial.ModelAllData;
import serialport.amos.cem.com.libamosserial.OnSerialPortDataListener;
import serialport.amos.cem.com.libamosserial.SerialPortHelp;


public abstract class BaseActivity extends AllBaseActivity implements PublicPopupWindow.PopupWindowKeyDown, OnSerialPortDataListener {

    protected LinearLayout public_bottom;
    private RelativeLayout actionBarView;
    private TextView title;
    private ImageView top_icon;
    protected PublicPopupWindow publicPopupWindow,publicPopupWindow2,publicPopupWindow3;
    private LinearLayout userView;
    protected List<BaseBottomAdapterObj> bottomData;
    protected Typeface tf;
    protected boolean isHold;
    protected List<View> bottomViews;
    private boolean isFirstButton;
 //   public View loadingView;

    public SweetAlertDialog loadingView;

    protected SerialPortHelp serialHelper;
    private boolean openPort;

    protected AppConfig config;
    protected int wir_index = 0; //接线方式
    protected boolean openLog = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity_layout);
        init();
        changeAppLanguage();
        getLanguageEvent();
        if (getIntent().hasExtra(AppConfig.MainPutActivityNameKey)){
            String titleName=getIntent().getStringExtra(AppConfig.MainPutActivityNameKey);
//            setTitle(titleName);
        }

    }

    public static void setScreen(Context context) {
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int SCREENWIDTH = outMetrics.widthPixels;
        int SCREENHEIGHT = outMetrics.heightPixels;
  //      log.e("======" + SCREENHEIGHT +"==========" + SCREENWIDTH);
    }

    protected void initLib(){
        serialHelper = SerialPortHelp.getInstance();
        openPort = serialHelper.openSerialPort("/dev/ttyS1",115200);
//        log.e("===========" + openPort);
        byte[] cmd = getMode();
 //       log.e("当前指令："+ BleUtil.dec_hex(cmd));
        if (cmd!=null){
            if(openLog)
                Toast.makeText(this, BleUtil.dec_hex(cmd),Toast.LENGTH_LONG).show();
            serialHelper.sendData(cmd);
        }
        serialHelper.setOnSerialPortDataListener(this);
        SerialPortHelp.isOpenRandomNumber = false;
        SerialPortHelp.isOpenLogCat = false;

    }
    public abstract byte[] getMode();

    @Override
    public void setContentView(int layoutResID) {
      View view=  LayoutInflater.from(this).inflate(layoutResID,null);
      setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        userView.addView(view,layoutParams);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title.setText(title);
    }

    public void setTop_icon(int res){
        top_icon.setImageResource(res);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        title.setText(titleId);
    }
    protected String Res2String(int resID){
        return  getResources().getString(resID);
    }
    protected  String[] Res2Stringarr(int resID){
        return  getResources().getStringArray(resID);
    }

    private void init(){
        config = AppConfig.getInstance();
        wir_index = config.getSet_Wir();
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        userView=findViewById(R.id.userView);
        public_bottom = findViewById(R.id.public_bottom);
        top_icon = findViewById(R.id.top_icon);
        title=findViewById(R.id.top_title);
        title.setText(R.string.main_meter);
        List<BaseBottomAdapterObj> baselist=initFirstButton();
        if (baselist==null)
            initBottomButtonData();
        else{
            bottomData=  baselist;
            isFirstButton=true;
            initButtomView();
        }
        actionBarView = findViewById(R.id.actionBarView);
//        loadingView = findViewById(R.id.loading_view);
        loadingView = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loadingView.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingView.setTitleText(Res2String(R.string.alert_load_title));
        loadingView.setCancelable(false);
    }

    public abstract List<BaseBottomAdapterObj> initFirstButton();

    public void showLoading(){
        if (loadingView != null) {
 //           loadingView.setVisibility(View.VISIBLE);
            loadingView.show();
        }
    }

    public void dissLoading() {
//        loadingView.post(new Runnable() {
//            @Override
//            public void run() {
//                dismissLoading();
//            }
//        }
            actionBarView.post(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                }
            });
    }

    public void dissLoading(long time) {
        actionBarView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissLoading();
            }
        },time);
    }


    private void dismissLoading(){
        if (loadingView != null) {
  //          loadingView.setVisibility(View.GONE);
            if(!isFinishing())
                loadingView.dismiss();
        }
    }

    protected void initBottomButtonData(){
        isFirstButton = false;
        bottomData = initBottomData();
        initButtomView();
    }

    private void initButtomView() {

        bottom_view_Laout1 = public_bottom.findViewById(R.id.bottom_view_Laout1);
        bottom_view_Laout2 = public_bottom.findViewById(R.id.bottom_view_Laout2);
        bottom_view_Laout3 = public_bottom.findViewById(R.id.bottom_view_Laout3);
        bottom_view_Laout4 = public_bottom.findViewById(R.id.bottom_view_Laout4);
        bottom_view_Laout5 = public_bottom.findViewById(R.id.bottom_view_Laout5);

        bottom_view_context1 = public_bottom.findViewById(R.id.bottom_view_context1);
        bottom_view_context2 = public_bottom.findViewById(R.id.bottom_view_context2);
        bottom_view_context3 = public_bottom.findViewById(R.id.bottom_view_context3);
        bottom_view_context4 = public_bottom.findViewById(R.id.bottom_view_context4);
        bottom_view_context5 = public_bottom.findViewById(R.id.bottom_view_context5);
        bottomViews = new ArrayList<>();
        bottomViews.add(0,bottom_view_Laout1);
        bottomViews.add(1,bottom_view_Laout2);
        bottomViews.add(2,bottom_view_Laout3);
        bottomViews.add(3,bottom_view_Laout4);
        bottomViews.add(4,bottom_view_Laout5);

        imgeShow1 = findViewById(R.id.imgeShow1);
        imgeShow2 = findViewById(R.id.imgeShow2);
        imgeShow3 = findViewById(R.id.imgeShow3);
        imgeShow4 = findViewById(R.id.imgeShow4);
        imgeShow5 = findViewById(R.id.imgeShow5);

        bottom_view_img1 = findViewById(R.id.bottom_view_img1);
        bottom_view_img2 = findViewById(R.id.bottom_view_img2);
        bottom_view_img3 = findViewById(R.id.bottom_view_img3);
        bottom_view_img4 = findViewById(R.id.bottom_view_img4);
        bottom_view_img5 = findViewById(R.id.bottom_view_img5);


        bottom_view_Laout1.setOnClickListener(new onButtonClick());
        bottom_view_Laout2.setOnClickListener(new onButtonClick());
        bottom_view_Laout3.setOnClickListener(new onButtonClick());
        bottom_view_Laout4.setOnClickListener(new onButtonClick());
        bottom_view_Laout5.setOnClickListener(new onButtonClick());


        bottom_view_Laout1.setTag(bottomData.get(0));
        bottom_view_Laout2.setTag(bottomData.get(1));
        bottom_view_Laout3.setTag(bottomData.get(2));
        bottom_view_Laout4.setTag(bottomData.get(3));
        bottom_view_Laout5.setTag(bottomData.get(4));

        setBottomView(bottomData.get(0), bottom_view_context1, bottom_view_img1, imgeShow1);
        setBottomView(bottomData.get(1), bottom_view_context2, bottom_view_img2, imgeShow2);
        setBottomView(bottomData.get(2), bottom_view_context3, bottom_view_img3, imgeShow3);
        setBottomView(bottomData.get(3), bottom_view_context4, bottom_view_img4, imgeShow4);
        setBottomView(bottomData.get(4), bottom_view_context5, bottom_view_img5, imgeShow5);

    }

    protected void updateBottomView(BaseBottomAdapterObj obj,int poition){
        switch (poition){
            case 0:
                bottom_view_Laout1.setTag(obj);
                setBottomView(obj, bottom_view_context1, bottom_view_img1, imgeShow1);
                break;
            case 1:
                bottom_view_Laout2.setTag(obj);
                setBottomView(obj, bottom_view_context2, bottom_view_img2, imgeShow2);
                break;
            case 2:
                bottom_view_Laout3.setTag(obj);
                setBottomView(obj, bottom_view_context3, bottom_view_img3, imgeShow3);
                break;
            case 3:
                bottom_view_Laout4.setTag(obj);
                setBottomView(obj, bottom_view_context4, bottom_view_img4, imgeShow4);
                break;
            case 4:
                bottom_view_Laout5.setTag(obj);
                setBottomView(obj, bottom_view_context5, bottom_view_img5, imgeShow5);
                break;
        }
    }

    protected void updateBottomView(BaseBottomAdapterObj obj,int poition,boolean clearText){
        switch (poition){
            case 0:
                bottom_view_Laout1.setTag(obj);
                setBottomView(obj, bottom_view_context1, bottom_view_img1, imgeShow1,clearText);
                break;
            case 1:
                bottom_view_Laout2.setTag(obj);
                setBottomView(obj, bottom_view_context2, bottom_view_img2, imgeShow2,clearText);
                break;
            case 2:
                bottom_view_Laout3.setTag(obj);
                setBottomView(obj, bottom_view_context3, bottom_view_img3, imgeShow3,clearText);
                break;
            case 3:
                bottom_view_Laout4.setTag(obj);
                setBottomView(obj, bottom_view_context4, bottom_view_img4, imgeShow4,clearText);
                break;
            case 4:
                bottom_view_Laout5.setTag(obj);
                setBottomView(obj, bottom_view_context5, bottom_view_img5, imgeShow5,clearText);
                break;
        }
    }

    private void setBottomView(BaseBottomAdapterObj obj, TextView textView, ImageView img, ImageView showImg) {
        switch (obj.getType()) {
            case ImageZoom:
                if(!obj.isHideTitle()) {
                    if (textView != null) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(obj.getTitle());
                        showImg.setVisibility(View.GONE);
                    }
                }else{
                    if(showImg!=null){
                        textView.setVisibility(View.GONE);
                        showImg.setVisibility(View.VISIBLE);
                        showImg.setImageResource(obj.getImgResID2());
                    }
                }
                break;
            case Switch:
                if (obj.getImgResID2() > 0) {
                    if (img != null) {
                        showImg.setVisibility(View.VISIBLE);
                        showImg.setImageResource(obj.getSwitchindex() == 0 ? obj.getImgResID2() : obj.getImgResID());
                    }
                } else {
                    if (textView != null) {
 //                       log.e("-------" + Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(), obj.getOnStr(), obj.getOffStr())));
                        textView.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(), obj.getOnStr(), obj.getOffStr())));
                    }
                }
                break;
            case Text:
                //               if (textView.getText()==null||textView.getText().length()<1||!obj.isMore())
                if(!obj.isHideTitle()) {
                    if (textView.getText()==null||textView.getText().length()<1||!obj.isMore()){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(obj.getTitle());
                    }
                }

                break;
            case Image:
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;
            case Zoom:
                if (textView != null)
                    textView.setText(obj.getTitle() + Res2String(R.string.Zoomstr) + (obj.getSwitchindex()));
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;
            case TextImage:
                if (textView != null)
                    textView.setText(obj.getTitle());
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }

                break;
            case TextZoom:
                if (textView.getText() == null || textView.getText().length() < 1 || textView.getText().toString().equals("Event") ||textView.getText().toString().contains("Cursor")) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(obj.getTitle());
                }

                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;

        }
        if (obj.isMore() || obj.getImgResID() != 0) {
            if (obj.getTitle() == null || obj.getTitle().trim().equals("")) {
                if (img != null)
                    img.setVisibility(View.GONE);
            } else {
                if (img != null)
                    img.setVisibility(View.VISIBLE);
            }
            if (obj.getMoreArray() != null && obj.getMoreArray().length > 0) {
                if (textView != null)
                    textView.setTextColor(0xfff8ef07);
            } else {
                if (textView != null)
                    textView.setTextColor(getResources().getColor(R.color.colorwhite));
            }

        } else {
            if (img != null)
                img.setVisibility(View.GONE);
            if (textView != null)
                textView.setTextColor(getResources().getColor(R.color.colorwhite));
        }
    }

    private void setBottomView(BaseBottomAdapterObj obj, TextView textView, ImageView img, ImageView showImg,boolean clearText) {
        switch (obj.getType()) {
            case ImageZoom:
                if(!obj.isHideTitle()) {
                    if (textView != null) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(obj.getTitle());
                        showImg.setVisibility(View.GONE);
                    }
                }else{
                    if(showImg!=null){
                        textView.setVisibility(View.GONE);
                        showImg.setVisibility(View.VISIBLE);
                        showImg.setImageResource(obj.getImgResID2());
                    }
                }
                break;
            case Switch:
                if (obj.getImgResID2() > 0) {
                    if (img != null) {
                        showImg.setVisibility(View.VISIBLE);
                        showImg.setImageResource(obj.getSwitchindex() == 0 ? obj.getImgResID2() : obj.getImgResID());
                    }
                } else {
                    if (textView != null) {
                        //                       log.e("-------" + Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(), obj.getOnStr(), obj.getOffStr())));
                        textView.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(), obj.getOnStr(), obj.getOffStr())));
                    }
                }
                break;
            case Text:
                //               if (textView.getText()==null||textView.getText().length()<1||!obj.isMore())
                if(!obj.isHideTitle()) {
                    if (textView.getText()==null||textView.getText().length()<1||!obj.isMore()|| clearText){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(obj.getTitle());
                    }
                }

                break;
            case Image:
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;
            case Zoom:
                if (textView != null)
                    textView.setText(obj.getTitle() + Res2String(R.string.Zoomstr) + (obj.getSwitchindex()));
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;
            case TextImage:
                if (textView != null)
                    textView.setText(obj.getTitle());
                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }

                break;
            case TextZoom:
                if (textView.getText() == null || textView.getText().length() < 1 || clearText) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(obj.getTitle());
                }

                if (obj.getImgResID() != 0) {
                    if (img != null)
                        img.setImageResource(obj.getImgResID());
                }
                break;

        }
        if (obj.isMore() || obj.getImgResID() != 0) {
            if (obj.getTitle() == null || obj.getTitle().trim().equals("")) {
                if (img != null)
                    img.setVisibility(View.GONE);
            } else {
                if (img != null)
                    img.setVisibility(View.VISIBLE);
            }
            if (obj.getMoreArray() != null && obj.getMoreArray().length > 0) {
                if (textView != null)
                    textView.setTextColor(0xfff8ef07);
            } else {
                if (textView != null)
                    textView.setTextColor(getResources().getColor(R.color.colorwhite));
            }

        } else {
            if (img != null)
                img.setVisibility(View.GONE);
            if (textView != null)
                textView.setTextColor(getResources().getColor(R.color.colorwhite));
        }
    }


    private String Str2Html(String str, int index, String onStr, String offStr){

        String onColor="%s<font color='#f8ef07'><br>%s</font>";

            if (index == 1) {
               if (str!=null&&str.length()>0)

                onColor = String.format(onColor, str, onStr);
                else
                   onColor=onStr;
            } else {
                if (str!=null&&str.length()>0)
                onColor = String.format(onColor, str, offStr);
                else
                    onColor=offStr;
            }

        return  onColor;
    }

    protected  abstract List<BaseBottomAdapterObj> initBottomData();



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
 //       changeAppBrightness(AppConfig.getInstance().getDefault_brightness());
        initLib();
        super.onResume();

    }

    /**
     * 更新Head颜色
     */
    protected void refeshHeadColor(){
        TypedArray ar = getResources().obtainTypedArray(R.array.colorview_arrays);
        final int len = ar.length();
        int[] dataColorArray = new int[len];
        for (int i = 0; i < len; i++){
            dataColorArray[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        ColorList.ALL_METER_TITLE_COLOR[0] = Color.parseColor("#305B7F");
        ColorList.ALL_METER_TITLE_COLOR[1] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL1()-1]);
        ColorList.ALL_METER_TITLE_COLOR[2] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL2()-1]);
        ColorList.ALL_METER_TITLE_COLOR[3] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VL3()-1]);
        ColorList.ALL_METER_TITLE_COLOR[4] = getResources().getColor(dataColorArray[config.getSetup_Show_Color_VN()-1]);
    }


    // 根据亮度值修改当前window亮度
    public void changeAppBrightness( int brightness) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    private void showPopupWindowF(final View view, BaseBottomAdapterObj obj){
        if (publicPopupWindow3==null) {
            publicPopupWindow3 = new PublicPopupWindow(this);
            publicPopupWindow3.setOnPopupWindowItemClick(new PublicPopupWindow.PopupWindowClickCallback() {
                @Override
                public void onPopupWindowItemClick(BaseBottomAdapterObj obj,int position) {//点击事件
                    TextView button = null;
                    switch (obj.getId()){
                        case 0:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context1);
                            break;
                        case 1:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context2);
                            break;
                        case 2:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context3);
                            break;
                        case 3:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context4);
                            break;
                        case 4:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context5);
                            break;
                    }

                    if (obj.isChangeText()){
                        if(button!=null)
                            button.setText(obj.getMoreArray()[position]);
                    }
                    obj.setFirstButton(isFirstButton);
                    PopupWindowItemClick(obj,position);
                }
            });
            publicPopupWindow3.setAnimationStyle(R.style.mypopwindow_anim_style);
            publicPopupWindow3.setOnPopupWindowKeyDown(this);
        }

        publicPopupWindow3.setPopListData(obj);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int width = view.getWidth();
        publicPopupWindow3.setWidth(width);
        publicPopupWindow3.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        publicPopupWindow3.setOutsideTouchable(true);
        publicPopupWindow3.setFocusable(true);
        publicPopupWindow3.setBackgroundDrawable(null);
        publicPopupWindow3.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-publicPopupWindow3.getPopHight()-10);
    }


    private void showPopupWindow2(final View view, BaseBottomAdapterObj obj){
        if (publicPopupWindow2==null) {
            publicPopupWindow2 = new PublicPopupWindow(this);
            publicPopupWindow2.setOnPopupWindowItemClick(new PublicPopupWindow.PopupWindowClickCallback() {
                @Override
                public void onPopupWindowItemClick(BaseBottomAdapterObj obj,int position) {//点击事件
                    TextView button = null;
                    switch (obj.getId()){
                        case 0:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context1);
                            break;
                        case 1:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context2);
                            break;
                        case 2:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context3);
                            break;
                        case 3:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context4);
                            break;
                        case 4:
                            button =  publicPopupWindow2.getDropDownView() .findViewById(R.id.bottom_view_context5);
                            break;
                    }

                    /*switch (view.getId()){
                        case R.id.bottom_view_Laout1:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context1);
                            break;
                        case R.id.bottom_view_Laout2:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context2);
                            break;
                        case R.id.bottom_view_Laout3:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context3);
                            break;
                        case R.id.bottom_view_Laout4:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context4);
                            break;
                        case R.id.bottom_view_Laout5:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context5);
                            break;
                    }*/
                    if (obj.isChangeText()){
                        if(button!=null)
                            button.setText(obj.getMoreArray()[position]);
                    }
                    obj.setFirstButton(isFirstButton);
                    PopupWindowItemClick(obj,position);
                }
            });
            publicPopupWindow2.setAnimationStyle(R.style.mypopwindow_anim_style);
            publicPopupWindow2.setOnPopupWindowKeyDown(this);
        }

        publicPopupWindow2.setPopListData(obj);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int width = view.getWidth();
        publicPopupWindow2.setWidth(width);
        publicPopupWindow2.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        publicPopupWindow2.setOutsideTouchable(true);
        publicPopupWindow2.setFocusable(true);
        publicPopupWindow2.setBackgroundDrawable(null);
        publicPopupWindow2.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-publicPopupWindow2.getPopHight()-10);
    }

    private void showPopupWindow(final View view, BaseBottomAdapterObj obj){
        if (publicPopupWindow==null) {
            publicPopupWindow = new PublicPopupWindow(this);
            publicPopupWindow.setOnPopupWindowItemClick(new PublicPopupWindow.PopupWindowClickCallback() {
                @Override
                public void onPopupWindowItemClick(BaseBottomAdapterObj obj,int position) {//点击事件
                    TextView button = null;
                    switch (obj.getId()){
                        case 0:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context1);
                            break;
                        case 1:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context2);
                            break;
                        case 2:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context3);
                            break;
                        case 3:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context4);
                            break;
                        case 4:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context5);
                            break;
                    }

                    /*switch (view.getId()){
                        case R.id.bottom_view_Laout1:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context1);
                            break;
                        case R.id.bottom_view_Laout2:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context2);
                            break;
                        case R.id.bottom_view_Laout3:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context3);
                            break;
                        case R.id.bottom_view_Laout4:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context4);
                            break;
                        case R.id.bottom_view_Laout5:
                            button =  publicPopupWindow.getDropDownView() .findViewById(R.id.bottom_view_context5);
                            break;
                    }*/
                    if (obj.isChangeText()){
                        if(button!=null)
                            button.setText(obj.getMoreArray()[position]);
                    }
                    obj.setFirstButton(isFirstButton);
                    PopupWindowItemClick(obj,position);
                }
            });
            publicPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
            publicPopupWindow.setOnPopupWindowKeyDown(this);
        }

        publicPopupWindow.setPopListData(obj);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int width = view.getWidth();
        publicPopupWindow.setWidth(width);
        publicPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        publicPopupWindow.setOutsideTouchable(true);
        publicPopupWindow.setFocusable(true);
        publicPopupWindow.setBackgroundDrawable(null);
        publicPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-publicPopupWindow.getPopHight()-10);
    }
    protected  abstract void  PopupWindowItemClick(BaseBottomAdapterObj obj,int positio);//弹窗点击事件
    protected  abstract void  BottomViewClick(View view, BaseBottomAdapterObj obj);//底部按钮点击事件

    /** 结束当前页 */
    public void destoryself(){
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        log.e("按键值："+keyCode);
        if (publicPopupWindow!=null&&publicPopupWindow.isShowing()) {
            publicPopupWindow.dismiss();
        }else  if (publicPopupWindow2!=null&&publicPopupWindow2.isShowing()) {
            publicPopupWindow2.dismiss();
        }else  if (publicPopupWindow3!=null&&publicPopupWindow3.isShowing()) {
            publicPopupWindow3.dismiss();
        }else {
            possKeDown(keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(bottomData!=null)
            bottomData.clear();
       // CEMThreeLineLib.getInstance().setOnMeterDataCallback(null);
        if(serialHelper!=null) {
            serialHelper.sendData(stopOrder);
            log.e("---停止传输数据---");
        }
//       serialHelper.setOnSerialPortDataListener(null);
        if(loadingView!=null) {
            loadingView.dismiss();
            loadingView = null;
        }
        if(alertDialog!=null){
            alertDialog.dismiss();
            alertDialog = null;
        }

        super.onDestroy();
    }

    protected byte[] stopOrder = new byte[]{(byte) 0xEF, (byte) 0xEF};

    private TextImageView bottom_view_context1,bottom_view_context2,bottom_view_context3,bottom_view_context4,bottom_view_context5;
    private RelativeLayout bottom_view_Laout1,bottom_view_Laout2,bottom_view_Laout3,bottom_view_Laout4,bottom_view_Laout5;
    private ImageView imgeShow1,imgeShow2,imgeShow3,imgeShow4,imgeShow5;
    private ImageView bottom_view_img1,bottom_view_img2,bottom_view_img3,bottom_view_img4,bottom_view_img5;


    private class onButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view.getTag()!=null) {
                BaseBottomAdapterObj obj = (BaseBottomAdapterObj) view.getTag();
                switch (obj.getType()) {
                    case Switch:
                        obj.setSwitchindex(obj.getSwitchindex() == 0 ? 1 : 0);
                        //                   setSwitchView(obj);
                        break;
                }

                switch (view.getId()){
                    case R.id.bottom_view_Laout1:
                        setBottomView(obj, bottom_view_context1, bottom_view_img1, imgeShow1);
                        if (obj.isMore()) {
                            showPopupWindow(view, obj);
                        }
                        break;
                    case R.id.bottom_view_Laout2:
                        setBottomView(obj, bottom_view_context2, bottom_view_img2, imgeShow2);
                        break;
                    case R.id.bottom_view_Laout3:
                        setBottomView(obj, bottom_view_context3, bottom_view_img3, imgeShow3);
                        if (obj.isMore()) {
                            showPopupWindow(view, obj);
                        }
                        break;
                    case R.id.bottom_view_Laout4:
                        setBottomView(obj, bottom_view_context4, bottom_view_img4, imgeShow4);
                        if (obj.isMore()) {
                            if(obj.isFL1()) {
                                showPopupWindowF(view, obj);
                            }else
                                showPopupWindow2(view, obj);
                        }
                        break;
                    case R.id.bottom_view_Laout5:
                        setBottomView(obj, bottom_view_context5, bottom_view_img5, imgeShow5);
                        break;
                }

                if (obj != null) {
                    obj.setFirstButton(isFirstButton);
                    BottomViewClick(view, obj);
                    showSelectBg(obj.getId());
                }
            }
        }
    }

    private void setSwitchView(BaseBottomAdapterObj obj){
        switch (obj.getId()){
            case 0:
                bottom_view_context1.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(),obj.getOnStr(),obj.getOffStr())));
                break;
            case 1:
                bottom_view_context2.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(),obj.getOnStr(),obj.getOffStr())));
                break;
            case 2:
                bottom_view_context3.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(),obj.getOnStr(),obj.getOffStr())));
                break;
            case 3:
                bottom_view_context4.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(),obj.getOnStr(),obj.getOffStr())));
                break;
            case 4:
                bottom_view_context5.setText(Html.fromHtml(Str2Html(obj.getTitle(), obj.getSwitchindex(),obj.getOnStr(),obj.getOffStr())));
                break;
        }

    }

    protected void showSelectBg(int index){
        switch (index){
            case 0:
                bottom_view_Laout1.setBackgroundResource(R.mipmap.textview_select_bg1);
                bottom_view_Laout2.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout3.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout4.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout5.setBackgroundResource(R.mipmap.textview_bg1);
                break;
            case 1:
                bottom_view_Laout1.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout2.setBackgroundResource(R.mipmap.textview_select_bg1);
                bottom_view_Laout3.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout4.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout5.setBackgroundResource(R.mipmap.textview_bg1);
                break;
            case 2:
                bottom_view_Laout1.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout2.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout3.setBackgroundResource(R.mipmap.textview_select_bg1);
                bottom_view_Laout4.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout5.setBackgroundResource(R.mipmap.textview_bg1);
                break;
            case 3:
                bottom_view_Laout1.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout2.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout3.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout4.setBackgroundResource(R.mipmap.textview_select_bg1);
                bottom_view_Laout5.setBackgroundResource(R.mipmap.textview_bg1);
                break;
            case 4:
                bottom_view_Laout1.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout2.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout3.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout4.setBackgroundResource(R.mipmap.textview_bg1);
                bottom_view_Laout5.setBackgroundResource(R.mipmap.textview_select_bg1);
                break;
        }

    }
    public void setBottom1TextSize(int textSize){
        bottom_view_context1.setTextSize(textSize);
    }

    public void setBottom2TextSize(int textSize){
        bottom_view_context2.setTextSize(textSize);
    }

    public void setBottom3TextSize(int textSize){
        bottom_view_context3.setTextSize(textSize);
    }

    public void setBottom4TextSize(int textSize){
        bottom_view_context4.setTextSize(textSize);
    }
    public void setBottom5TextSize(int textSize){
        bottom_view_context5.setTextSize(textSize);
    }
    public void setBottomTextSize(int textSize){
        setBottom1TextSize(textSize);
        setBottom2TextSize(textSize);
        setBottom3TextSize(textSize);
        setBottom4TextSize(textSize);
        setBottom5TextSize(textSize);
    }


    private void possKeDown(int keyCode){
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
        switch (key){
            case F1:
                bottom_view_Laout1.performClick();
                break;
            case F2:
                bottom_view_Laout2.performClick();
                break;
            case F3:
                bottom_view_Laout3.performClick();
                break;
            case F4:
                bottom_view_Laout4.performClick();
                break;
            case F5:
                bottom_view_Laout5.performClick();
                break;
        }

    }

    /* private void initButtomView() {
        bottom_view_Laout1 = public_bottom.findViewById(R.id.bottom_view_Laout1);
        bottom_view_Laout2 = public_bottom.findViewById(R.id.bottom_view_Laout2);
        bottom_view_Laout3 = public_bottom.findViewById(R.id.bottom_view_Laout3);
        bottom_view_Laout4 = public_bottom.findViewById(R.id.bottom_view_Laout4);
        bottom_view_Laout5 = public_bottom.findViewById(R.id.bottom_view_Laout5);

        bottom_view_context1 = public_bottom.findViewById(R.id.bottom_view_context1);
        bottom_view_context2 = public_bottom.findViewById(R.id.bottom_view_context2);
        bottom_view_context3 = public_bottom.findViewById(R.id.bottom_view_context3);
        bottom_view_context4 = public_bottom.findViewById(R.id.bottom_view_context4);
        bottom_view_context5 = public_bottom.findViewById(R.id.bottom_view_context5);
        bottomViews = new ArrayList<>();
        bottomViews.add(0,bottom_view_Laout1);
        bottomViews.add(1,bottom_view_Laout1);
        bottomViews.add(2,bottom_view_Laout1);
        bottomViews.add(3,bottom_view_Laout1);
        bottomViews.add(4,bottom_view_Laout1);

        bottom_view_Laout1.setOnClickListener(new onButtonClick());
        bottom_view_Laout2.setOnClickListener(new onButtonClick());
        bottom_view_Laout3.setOnClickListener(new onButtonClick());
        bottom_view_Laout4.setOnClickListener(new onButtonClick());
        bottom_view_Laout5.setOnClickListener(new onButtonClick());
 //       showSelectBg(0);
    }

private  class  onViewClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            PointF userClickPoint=null;
            if (view instanceof MyLinearLayout){
                userClickPoint=((MyLinearLayout)view).getUserClickPoint();
            }
            if (view.getTag()!=null){
                BaseBottomAdapterObj obj= (BaseBottomAdapterObj) view.getTag();
                TextView textView=view.findViewById(R.id.bottom_view_context);
                ImageView img = view.findViewById(R.id.bottom_view_img);
                ImageView imgeShow= view.findViewById(R.id.imgeShow);
                RelativeLayout parview = view.findViewById(R.id.bottom_view_Laout);
                //              parview.setBackgroundResource(R.mipmap.textview_select_bg1);
                switch (obj.getType()){
                    case Switch:
                        obj.setSwitchindex(obj.getSwitchindex()==0?1:0);
                        break;
                    case Zoom:
                        int curindex=obj.getSwitchindex();
                        if (userClickPoint!=null&&userClickPoint.y>view.getHeight()/2 ){
                            curindex--;
                        }else
                            curindex++;

                        if (curindex>=obj.getMaxZoom()){
                            curindex=obj.getMaxZoom();
                        }else if (curindex<1){
                            curindex=1;
                        }
                        obj.setSwitchindex(curindex);
                        break;
                }

                if (obj.isMore() ){
                    showPopupWindow(parview,obj);
                }else{

                }
                setBottomView(obj,textView,img,imgeShow);
                if (obj.getId()==4) {
                    isHold = obj.getSwitchindex() == 0 ? true : false;
                    log.e("hold :"+isHold);
                }
                obj.setFirstButton(isFirstButton);
                BottomViewClick(view, obj);
                parview.setBackgroundResource(R.mipmap.textview_select_bg1);
            }
        }
    }

    protected void setButtonViewData(BaseBottomAdapterObj obj, int index) {
        switch (index) {
            case 0:
                bottom_view_context1.setText(obj.getTitle());
                bottom_view_Laout1.setTag(obj);
                break;
            case 1:
                bottom_view_context2.setText(obj.getTitle());
                bottom_view_Laout2.setTag(obj);
                break;
            case 2:
                bottom_view_context3.setText(obj.getTitle());
                bottom_view_Laout3.setTag(obj);
                break;
            case 3:
                bottom_view_Laout4.setTag(obj);
                bottom_view_context4.setText(obj.getTitle());
                break;
            case 4:
                bottom_view_context5.setText(obj.getTitle());
                bottom_view_Laout5.setTag(obj);
                break;
        }
    }
*/

     /*private  void possKeDown(int keyCode){
        MeterKeyValue key=MeterKeyValue.valueOf(keyCode);
        switch (key){
            case F1:
                public_bottom.getChildAt(0).performClick();
                break;
            case F2:
                if (public_bottom.getChildAt(1).getVisibility()== View.VISIBLE)
                public_bottom.getChildAt(1).performClick();
                break;
            case F3:
                if (public_bottom.getChildAt(2).getVisibility()== View.VISIBLE)
                public_bottom.getChildAt(2).performClick();
                break;
            case F4:
                if (public_bottom.getChildAt(3).getVisibility()== View.VISIBLE)
                public_bottom.getChildAt(3).performClick();
                break;
            case F5:
                if (public_bottom.getChildAt(4).getVisibility()== View.VISIBLE)
                public_bottom.getChildAt(4).performClick();
                break;
        }
    }*/

    /*protected void showSelectBg(int index){
        switch (index){
            case 0:
                public_bottom.getChildAt(0).setBackgroundResource(R.mipmap.textview_select_bg1);
                public_bottom.getChildAt(1).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(2).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(3).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(4).setBackgroundResource(R.mipmap.textview_bg1);

                break;

            case 1:
                public_bottom.getChildAt(0).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(1).setBackgroundResource(R.mipmap.textview_select_bg1);
                public_bottom.getChildAt(2).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(3).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(4).setBackgroundResource(R.mipmap.textview_bg1);

                break;

            case 2:
                public_bottom.getChildAt(0).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(1).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(2).setBackgroundResource(R.mipmap.textview_select_bg1);
                public_bottom.getChildAt(3).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(4).setBackgroundResource(R.mipmap.textview_bg1);
                break;

            case 3:
                public_bottom.getChildAt(0).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(1).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(2).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(3).setBackgroundResource(R.mipmap.textview_select_bg1);
                public_bottom.getChildAt(4).setBackgroundResource(R.mipmap.textview_bg1);
                break;

            case 4:
                public_bottom.getChildAt(0).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(1).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(2).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(3).setBackgroundResource(R.mipmap.textview_bg1);
                public_bottom.getChildAt(4).setBackgroundResource(R.mipmap.textview_select_bg1);

                break;
        }

    }*/

    /*private void initButtomView() {
        public_bottom.removeAllViews();
        if (bottomData != null) {
            int index = 0;
            bottomViews = new ArrayList<>();
            for (BaseBottomAdapterObj obj : bottomData) {
                obj.setId(index);
                View view = LayoutInflater.from(this).inflate(R.layout.base_buttom_itemlayout, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                TextView textView = view.findViewById(R.id.bottom_view_context);
                ImageView img = view.findViewById(R.id.bottom_view_img);
                view.setTag(obj);
                view.setOnClickListener(new onViewClick());
                public_bottom.addView(view, layoutParams);
                index++;
                ImageView imgeShow = view.findViewById(R.id.imgeShow);
                setBottomView(obj, textView, img, imgeShow);

                if (!obj.isMore() && obj.getImgResID() != 0) {
                    if (obj.getTitle() == null || obj.getTitle().trim().equals("")) {
                        imgeShow.setImageResource(obj.getImgResID());
                        imgeShow.setVisibility(View.VISIBLE);
                        img.setVisibility(View.GONE);
                    }
                } else {
                    imgeShow.setVisibility(View.GONE);

                }

                if (obj.getType() == BaseBottomAdapterObj.DataType.Text && obj.getTitle() == null) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    if (view.getVisibility() != View.VISIBLE)
                        view.setVisibility(View.VISIBLE);
                }

                bottomViews.add(view);
            }
        }
    }*/
        protected BaseBottomAdapterObj getBottomViewData(int index){
            if (index<bottomData.size())
                return bottomData.get(index);
            else
                return null;
        }

        /*protected void setBottomViewData(BaseBottomAdapterObj obj,int index){
            if (index < bottomData.size())
                bottomData.set(index, obj);
            View view = public_bottom.getChildAt(index);
            TextView textView = view.findViewById(R.id.bottom_view_context);
            ImageView img = view.findViewById(R.id.bottom_view_img);
            ImageView imgeShow = view.findViewById(R.id.imgeShow);
            view.setTag(obj);
            setBottomView(obj, textView, img, imgeShow);
        }*/

    @Override
    public void onBATDataReceived(float v, float v1,boolean dcIn) {
//        log.e("-----电压-----" + v);
//        log.e("-----电量-----" + v1);
    }

    private void getLanguageEvent(){
        LiveEventBus
                .get("changeLanguage", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable String s) {
                        changeAppLanguage();
                        recreate();//刷新界面
                    }
                });
    }



    public void changeAppLanguage() {
        String sta = LanguageStore.getLanguageLocal(this);
        if(sta != null && !"".equals(sta)){
            // 本地语言设置
            Locale myLocale = getLocale(sta);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }

    }

    private Locale getLocale(String sta){
        Locale locale ;
        if(sta.equals("zh_rCN")){
            locale =  Locale.SIMPLIFIED_CHINESE;
        }else if(sta.equals("en")){
            locale = Locale.ENGLISH;
        }else{
            locale = Locale.ENGLISH;
        }
        return locale;
    }
    private SweetAlertDialog alertDialog;
    protected boolean isStart;
    public void isStartAlert(String renwu){
        if(alertDialog == null)
            alertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText(Res2String(R.string.stop_meausre_title))
                .setContentText(String.format(getString(R.string.stop_meausre_content), renwu))
                .setCancelButton(Res2String(R.string.stop_meausre_cancel), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmText(Res2String(R.string.stop_meausre_sure))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                       destoryself();
                    }
                });
        alertDialog.show();
        log.e("--------");
    }

}
