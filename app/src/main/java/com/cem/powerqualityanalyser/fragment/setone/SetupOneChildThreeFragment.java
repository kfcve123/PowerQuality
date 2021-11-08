package com.cem.powerqualityanalyser.fragment.setone;

import android.app.Fragment;
import android.view.View;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountViewHorizontal;

import java.lang.reflect.Field;


public class SetupOneChildThreeFragment extends BaseFragment implements AmountViewHorizontal.OnHorizonalAmountChangeListener {


    private AppConfig config;
    private AmountViewHorizontal fk_e_amountview,fk_q_amountview;
    private int fk_e_index = 0;
    private int fk_q_index = 0;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_one_childthree;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        fk_e_index = config.getSet_Caluc_Fk_e();
        fk_q_index = config.getSet_Caluc_Fk_q();

        fk_e_amountview = (AmountViewHorizontal) findViewById(R.id.fk_e_amountview);
        fk_e_amountview.setShowAmount(true);
        String[] showItem=getString(R.string.set_caluc_fke_item).split(",");
        fk_e_amountview.setAmount(fk_e_index);
        fk_e_amountview.setContent(showItem[fk_e_index]);
        fk_e_amountview.setTextSize(35);
        fk_e_amountview.setDataArray(showItem);
        fk_e_amountview.setOnHorizonalAmountChangeListener(this);

        fk_q_amountview = (AmountViewHorizontal) findViewById(R.id.fk_q_amountview);
        fk_q_amountview.setShowAmount(true);
        String[] showItem2=getString(R.string.set_caluc_fkq_item).split(",");
        fk_q_amountview.setAmount(fk_q_index);
        fk_q_amountview.setContent(showItem2[fk_q_index]);
        fk_q_amountview.setTextSize(35);
        fk_q_amountview.setDataArray(showItem2);
        fk_q_amountview.setOnHorizonalAmountChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void moveCursor(int i) {
        if (fk_e_amountview.hasFocus()){
 //           log.e("========fk_e_amountview======");
            if (i > 0)
                fk_e_amountview.getAmount_dncrease_horizontal().performClick();
            else
                fk_e_amountview.getAmount_increase_horizontal().performClick();
        }
        if(fk_q_amountview.hasFocus()){
 //           log.e("========fk_q_amountview======");
            if (i > 0)
                fk_q_amountview.getAmount_dncrease_horizontal().performClick();
            else
                fk_q_amountview.getAmount_increase_horizontal().performClick();

        }
    }


    @Override
    public void onAmountChange(View view, int amount) {
        log.e("========" + amount);
        switch (view.getId()){
            case R.id.fk_e_amountview:
                fk_e_index = amount;
                config.setSet_Caluc_Fk_e(fk_e_index -1);
                break;

            case R.id.fk_q_amountview:
                fk_q_index = amount;
                config.setSet_Caluc_Fk_q(fk_q_index -1);
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
