package com.cem.powerqualityanalyser.fragment.setone;

import android.app.Fragment;
import android.view.View;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewVertical;

import java.lang.reflect.Field;


public class SetupOneChildTwoFragment extends BaseFragment implements AmountViewVertical.OnVerticalAmountChangeListener{


    private AppConfig config;
    private AmountViewVertical wh_amountview;
    private int wh_index = 0;


    @Override
    public int setContentView() {
        return R.layout.fragment_set_one_childtwo;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        wh_index = config.getSet_Caluc_Wh();
        wh_amountview = (AmountViewVertical) findViewById(R.id.wh_amountview);
        wh_amountview.setShowAmount(true);
        String[] showItem=getString(R.string.set_caluc_wh_item).split(",");
        wh_amountview.setAmount(wh_index);
        wh_amountview.setContent(showItem[wh_index]);
        wh_amountview.setTextSize(35);
        wh_amountview.setDataArray(showItem);
        wh_amountview.setOnVerticalAmountChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()){
            case R.id.wh_amountview:
                log.e("========" + amount);
                wh_index = amount;
                config.setSet_Caluc_Wh(wh_index-1);
                break;


        }
    }


    public void moveCursor(int i) {
        if (wh_amountview.hasFocus()){
            if (i > 0)
                wh_amountview.getAmount_dncrease_vertical().performClick();
            else
                wh_amountview.getAmount_increase_vertical().performClick();
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
