package com.cem.powerqualityanalyser.fragment.setone;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;

import java.lang.reflect.Field;


public class SetupOneChildFourFragment extends BaseFragment implements View.OnClickListener{


    private AppConfig config;
    private ImageView fr_choose_on, fr_choose_off;
    private boolean faropen;


    @Override
    public int setContentView() {
        return R.layout.fragment_set_one_childfour;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        faropen = config.isSetup_Caluc_fr();
        fr_choose_on = (ImageView) findViewById(R.id.fr_choose_on);
        fr_choose_off = (ImageView) findViewById(R.id.fr_choose_off);
        fr_choose_on.setOnClickListener(this);
        fr_choose_off.setOnClickListener(this);

        if(faropen){
            fr_choose_on.setImageResource(R.mipmap.set_choose_on);
            fr_choose_off.setImageResource(R.mipmap.set_choose_no);
        }else{
            fr_choose_on.setImageResource(R.mipmap.set_choose_no);
            fr_choose_off.setImageResource(R.mipmap.set_choose_on);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_choose_on:
                fr_choose_on.setImageResource(R.mipmap.set_choose_on);
                fr_choose_off.setImageResource(R.mipmap.set_choose_no);
                config.setSetup_Caluc_fr(true);
                break;
            case R.id.fr_choose_off:
                fr_choose_on.setImageResource(R.mipmap.set_choose_no);
                fr_choose_off.setImageResource(R.mipmap.set_choose_on);
                config.setSetup_Caluc_fr(false);
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
