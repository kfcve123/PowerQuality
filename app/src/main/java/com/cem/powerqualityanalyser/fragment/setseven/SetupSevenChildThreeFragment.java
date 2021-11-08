package com.cem.powerqualityanalyser.fragment.setseven;

import android.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import java.lang.reflect.Field;

/**
 * 夜间模式
 */
public class SetupSevenChildThreeFragment extends BaseFragment implements View.OnClickListener {



    private AppConfig config;
    private boolean setautooff;
    private TextView set_show_nightmode_true,set_show_nightmode_false;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_seven_childthree;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        setautooff = config.isSetup_Night_Mode();

        set_show_nightmode_true = (TextView) findViewById(R.id.set_show_nightmode_true);
        set_show_nightmode_false = (TextView) findViewById(R.id.set_show_nightmode_false);

        set_show_nightmode_true.setOnClickListener(this);
        set_show_nightmode_false.setOnClickListener(this);

        if(setautooff){
            set_show_nightmode_true.setBackgroundResource(R.mipmap.set_show_select_bg);
            set_show_nightmode_false.setBackgroundResource(0);
            set_show_nightmode_true.setTextColor(getResources().getColor(R.color.colorwhite));
            set_show_nightmode_false.setTextColor(getResources().getColor(R.color.set_text_color));
        }else{
            set_show_nightmode_true.setBackgroundResource(0);
            set_show_nightmode_false.setBackgroundResource(R.mipmap.set_show_select_bg);
            set_show_nightmode_true.setTextColor(getResources().getColor(R.color.set_text_color));
            set_show_nightmode_false.setTextColor(getResources().getColor(R.color.colorwhite));
        }
    }

    @Override
    public void onClick(View view) {
        log.e("=======" + view.getId());
        switch (view.getId()){
            case R.id.set_show_nightmode_true:
                config.setSetup_Night_Mode(true);
                set_show_nightmode_true.setBackgroundResource(R.mipmap.set_show_select_bg);
                set_show_nightmode_false.setBackgroundResource(0);
                set_show_nightmode_true.setTextColor(getResources().getColor(R.color.colorwhite));
                set_show_nightmode_false.setTextColor(getResources().getColor(R.color.set_text_color));
                break;
            case R.id.set_show_nightmode_false:
                config.setSetup_Night_Mode(false);
                set_show_nightmode_true.setBackgroundResource(0);
                set_show_nightmode_false.setBackgroundResource(R.mipmap.set_show_select_bg);
                set_show_nightmode_true.setTextColor(getResources().getColor(R.color.set_text_color));
                set_show_nightmode_false.setTextColor(getResources().getColor(R.color.colorwhite));
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
