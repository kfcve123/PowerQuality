package com.cem.powerqualityanalyser.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFiveFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildFourFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildOneFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildThreeFragment;
import com.cem.powerqualityanalyser.fragment.setone.SetupOneChildTwoFragment;

import java.lang.reflect.Field;

/**
 * 计算方式
 */
public class SetupOneFragment extends BaseFragment{

    private AppConfig config;
    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager mChildFragmentManager;

    private SetupOneChildOneFragment setupOneChildOneFragment;
    private SetupOneChildTwoFragment setupOneChildTwoFragment;
    private SetupOneChildThreeFragment setupOneChildThreeFragment;
    private SetupOneChildFourFragment setupOneChildFourFragment;
    private SetupOneChildFiveFragment setupOneChildFiveFragment;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_one;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        mChildFragmentManager = getChildFragmentManager();
        setChildViewShow(0);
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

    public void setChildViewShow(int index) {

        if(index ==0){
            if(setupOneChildOneFragment ==null)
                setupOneChildOneFragment = new SetupOneChildOneFragment();
            showFragment(setupOneChildOneFragment,"0");
        }else if(index ==1){
            if(setupOneChildTwoFragment ==null)
                setupOneChildTwoFragment = new SetupOneChildTwoFragment();
            showFragment(setupOneChildTwoFragment,"1");
        }else if(index ==2){
            if(setupOneChildThreeFragment ==null)
                setupOneChildThreeFragment = new SetupOneChildThreeFragment();
            showFragment(setupOneChildThreeFragment,"2");
        }else if(index ==3){
            if(setupOneChildFourFragment ==null)
                setupOneChildFourFragment = new SetupOneChildFourFragment();
            showFragment(setupOneChildFourFragment,"3");
        }else if(index ==4){
            if(setupOneChildFiveFragment ==null)
                setupOneChildFiveFragment = new SetupOneChildFiveFragment();
            showFragment(setupOneChildFiveFragment,"4");
        }
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    protected void showFragment(Fragment fragment, String tag) {
        fragmentTransaction = mChildFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.set_one_content_layout, fragment, tag);
        fragmentTransaction.commit();

    }

}
