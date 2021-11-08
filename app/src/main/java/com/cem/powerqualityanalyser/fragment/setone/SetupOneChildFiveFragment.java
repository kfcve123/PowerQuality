package com.cem.powerqualityanalyser.fragment.setone;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;

import java.lang.reflect.Field;


public class SetupOneChildFiveFragment extends BaseFragment implements View.OnClickListener {


    private AppConfig config;
    private ImageView var_choose_on, var_choose_off;
    private boolean pltopen;


    @Override
    public int setContentView() {
        return R.layout.fragment_set_one_childfive;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        pltopen = config.isSetup_Caluc_plt();
        var_choose_on = (ImageView) findViewById(R.id.var_choose_on);
        var_choose_off = (ImageView) findViewById(R.id.var_choose_off);
        var_choose_on.setOnClickListener(this);
        var_choose_off.setOnClickListener(this);
        if(pltopen){
            var_choose_on.setImageResource(R.mipmap.set_choose_on);
            var_choose_off.setImageResource(R.mipmap.set_choose_no);
        }else{
            var_choose_on.setImageResource(R.mipmap.set_choose_no);
            var_choose_off.setImageResource(R.mipmap.set_choose_off);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.var_choose_on:
                var_choose_on.setImageResource(R.mipmap.set_choose_on);
                var_choose_off.setImageResource(R.mipmap.set_choose_no);
                config.setSetup_Caluc_plt(true);
                break;
            case R.id.var_choose_off:
                var_choose_on.setImageResource(R.mipmap.set_choose_no);
                var_choose_off.setImageResource(R.mipmap.set_choose_off);
                config.setSetup_Caluc_plt(false);
                break;


        }


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
