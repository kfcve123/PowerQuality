package com.cem.powerqualityanalyser.fragment.setseven;

import android.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewVertical;

import java.lang.reflect.Field;

/**
 * 自动关机
 */
public class SetupSevenChildTwoFragment extends BaseFragment implements View.OnClickListener {


    private AppConfig config;
    private boolean setautooff;
    private TextView set_show_autooff_true,set_show_autooff_false;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_seven_childtwo;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        setautooff = config.isDefault_automatic_Screen();

        set_show_autooff_true = (TextView) findViewById(R.id.set_show_autooff_true);
        set_show_autooff_false = (TextView) findViewById(R.id.set_show_autooff_false);

        set_show_autooff_true.setOnClickListener(this);
        set_show_autooff_false.setOnClickListener(this);
        if(setautooff){
            set_show_autooff_true.setBackgroundResource(R.mipmap.set_show_select_bg);
            set_show_autooff_false.setBackgroundResource(0);
            set_show_autooff_true.setTextColor(getResources().getColor(R.color.colorwhite));
            set_show_autooff_false.setTextColor(getResources().getColor(R.color.set_text_color));
        }else{
            set_show_autooff_true.setBackgroundResource(0);
            set_show_autooff_false.setBackgroundResource(R.mipmap.set_show_select_bg);
            set_show_autooff_true.setTextColor(getResources().getColor(R.color.set_text_color));
            set_show_autooff_false.setTextColor(getResources().getColor(R.color.colorwhite));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_show_autooff_true:
                config.setDefault_Automatic_Screen(true);
                set_show_autooff_true.setBackgroundResource(R.mipmap.set_show_select_bg);
                set_show_autooff_false.setBackgroundResource(0);
                set_show_autooff_true.setTextColor(getResources().getColor(R.color.colorwhite));
                set_show_autooff_false.setTextColor(getResources().getColor(R.color.set_text_color));
                break;
            case R.id.set_show_autooff_false:
                set_show_autooff_true.setBackgroundResource(0);
                set_show_autooff_false.setBackgroundResource(R.mipmap.set_show_select_bg);
                config.setDefault_Automatic_Screen(false);
                set_show_autooff_true.setTextColor(getResources().getColor(R.color.set_text_color));
                set_show_autooff_false.setTextColor(getResources().getColor(R.color.colorwhite));
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



}
