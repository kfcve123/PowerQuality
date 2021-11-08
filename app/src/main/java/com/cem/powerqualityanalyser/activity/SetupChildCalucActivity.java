package com.cem.powerqualityanalyser.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFiveFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildTwoFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

import serialport.amos.cem.com.libamosserial.ModelAllData;

/**
 * 计算方式 1
 */
public class SetupChildCalucActivity extends BaseActivity implements View.OnClickListener {

    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;

    private SetupOneChildOneFragment setupOneChildOneFragment;
    private SetupOneChildTwoFragment setupOneChildTwoFragment;
    private SetupOneChildThreeFragment setupOneChildThreeFragment;
    private SetupOneChildFourFragment setupOneChildFourFragment;
    private SetupOneChildFiveFragment setupOneChildFiveFragment;

    private TextView set_caluc_title;
    private int fragmentIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getFragmentManager();
        setContentView(R.layout.fragment_set_one);
        set_caluc_title = findViewById(R.id.set_caluc_title);

        setViewShow(0);
    }

    @Override
    public byte[] getMode() {
        return null;
    }

    private void  setViewShow(int index){
        if(index ==0){
            if(setupOneChildOneFragment ==null)
                setupOneChildOneFragment = new SetupOneChildOneFragment();
            showFragment(setupOneChildOneFragment,"0");
            set_caluc_title.setText(R.string.set_caluc_var_title);
        }else if(index ==1){
            if(setupOneChildTwoFragment ==null)
                setupOneChildTwoFragment = new SetupOneChildTwoFragment();
            showFragment(setupOneChildTwoFragment,"1");
            set_caluc_title.setText(R.string.set_caluc_wh_title);
        }else if(index ==2){
            if(setupOneChildThreeFragment ==null)
                setupOneChildThreeFragment = new SetupOneChildThreeFragment();
            showFragment(setupOneChildThreeFragment,"2");
            set_caluc_title.setText(R.string.set_caluc_fk_title);
        }else if(index ==3){
            if(setupOneChildFourFragment ==null)
                setupOneChildFourFragment = new SetupOneChildFourFragment();
            showFragment(setupOneChildFourFragment,"3");
            set_caluc_title.setText(R.string.set_caluc_fr_title);
        }else if(index ==4){
            if(setupOneChildFiveFragment ==null)
                setupOneChildFiveFragment = new SetupOneChildFiveFragment();
            showFragment(setupOneChildFiveFragment,"4");
            set_caluc_title.setText(R.string.set_caluc_plt_title);
        }

    }

    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.set_one_content_layout, fragment, tag);
        fragmentTransaction.commit();

    }

    @Override
    public List<BaseBottomAdapterObj> initFirstButton() {
        return null;
    }

    @Override
    protected List<BaseBottomAdapterObj> initBottomData() {

        List<BaseBottomAdapterObj> data=new ArrayList<>();
        data.add(new BaseBottomAdapterObj(0,Res2String(R.string.set_caluc_botton_var)));
        data.add(new BaseBottomAdapterObj(1,Res2String(R.string.set_caluc_botton_wh)));
        data.add(new BaseBottomAdapterObj(2,Res2String(R.string.set_caluc_botton_fk)));
        data.add(new BaseBottomAdapterObj(3,Res2String(R.string.set_caluc_botton_fr)));
        data.add(new BaseBottomAdapterObj(4,Res2String(R.string.set_caluc_botton_ptl)));
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
        possKeyDown(keyCode);
        return super.onKeyDown(keyCode, event);
    }

    private void possKeyDown(int keyCode) {
        MeterKeyValue key = MeterKeyValue.valueOf(keyCode);
        switch (key) {
            case Up:
                if (setupOneChildTwoFragment != null) {
                    setupOneChildTwoFragment.moveCursor(-1);
                }
                break;
            case Down:
                if (setupOneChildTwoFragment != null)
                    setupOneChildTwoFragment.moveCursor(1);
                break;
            case Left:
                if (setupOneChildThreeFragment != null) {
                    setupOneChildThreeFragment.moveCursor(-1);
                }
                break;
            case Right:
                if (setupOneChildThreeFragment != null) {
                    setupOneChildThreeFragment.moveCursor(1);
                }
                break;
        }
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