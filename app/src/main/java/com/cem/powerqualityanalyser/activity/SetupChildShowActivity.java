package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFiveFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildTwoFragment;
import com.cem.powerqualityanalyser.fragment.setseven.SetupSevenChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setseven.SetupSevenChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setseven.SetupSevenChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setseven.SetupSevenChildTwoFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * 显示 7
 */
public class SetupChildShowActivity extends BaseActivity implements View.OnClickListener {


    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;

    private SetupSevenChildOneFragment setupSevenChildOneFragment;
    private SetupSevenChildTwoFragment setupSevenChildTwoFragment;
    private SetupSevenChildThreeFragment setupSevenChildThreeFragment;
    private SetupSevenChildFourFragment setupSevenChildFourFragment;
    private TextView set_show_title;

    private int fragmentIndex = -1;
    private boolean defaultLight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setContentView(R.layout.fragment_set_seven);
        set_show_title = findViewById(R.id.set_show_title);

        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void  setViewShow(int index){
        if(index ==0){
            if(setupSevenChildOneFragment ==null)
                setupSevenChildOneFragment = new SetupSevenChildOneFragment();
            showFragment(setupSevenChildOneFragment,"0");
            set_show_title.setText(R.string.set_show_bright);
        }else if(index ==1){
            if(setupSevenChildTwoFragment ==null)
                setupSevenChildTwoFragment = new SetupSevenChildTwoFragment();
            showFragment(setupSevenChildTwoFragment,"1");
            set_show_title.setText(R.string.set_show_autooff);
        }else if(index ==2){
            if(setupSevenChildThreeFragment ==null)
                setupSevenChildThreeFragment = new SetupSevenChildThreeFragment();
            showFragment(setupSevenChildThreeFragment,"2");
            set_show_title.setText(R.string.set_show_nightmode);
        }else if(index ==3){
            if(setupSevenChildFourFragment ==null)
                setupSevenChildFourFragment = new SetupSevenChildFourFragment();
            showFragment(setupSevenChildFourFragment,"3");
            set_show_title.setText(R.string.set_show_color);
        }

    }

    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.set_seven_content_layout, fragment, tag);
        fragmentTransaction.commit();

    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {
        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_show_bright)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.set_show_autooff)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.set_show_nightmode)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.set_show_color)));
        data.add(new BaseBottomAdapterObj(4,""));
        return  data;
    }

    @Override
    protected void PopupWindowItemClick(BaseBottomAdapterObj obj, int positio) {

    }

    @Override
    protected void BottomViewClick(View view, BaseBottomAdapterObj obj) {
        setViewShow(obj.getId());
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        //       log.e("========" + key.toString());
        switch (key) {
            case Up:

                break;
            case Down:

                break;
            case Left:
                if(setupSevenChildFourFragment!=null && setupSevenChildFourFragment.isAdded())
                    setupSevenChildFourFragment.moveCursor(-1);
                break;
            case Right:
                if(setupSevenChildFourFragment!=null && setupSevenChildFourFragment.isAdded())
                    setupSevenChildFourFragment.moveCursor(1);
                break;
            case Light:
                if(setupSevenChildOneFragment!=null && setupSevenChildOneFragment.isAdded()) {
                    setWindowBrightness();
                    setupSevenChildOneFragment.setLightProgress(config.getDefault_brightness());
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置亮度
     * @param
     * @param
     */
    private void setWindowBrightness() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        if(!defaultLight){
            lp.screenBrightness = config.getDefault_brightness()/255;
            defaultLight = true;
        }
        log.e("--------------" + lp.screenBrightness);
        // 每次进来都是1 ，只能进来先加载前一次的设置值
        lp.screenBrightness += 0.25;
        if (lp.screenBrightness > 1){
            lp.screenBrightness = 0;
        }
        log.e("--------------" + lp.screenBrightness);
        config.setDefault_brightness((int) (255 * lp.screenBrightness));
        window.setAttributes(lp);
    }


    private void possKeyDown(int keyCode) {

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