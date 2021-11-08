package com.cem.powerqualityanalyser.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.SetAdapter;
import com.cem.powerqualityanalyser.view.AmountViewVertical;
import com.cem.powerqualityanalyser.view.PagingItemDecoration;


/**
 * 日期和时间
 */
public class SetupFiveFragment extends BaseFragment implements AmountViewVertical.OnVerticalAmountChangeListener{


    private AppConfig config;

    private AmountViewVertical amount_view1, amount_view2, amount_view3, amount_view4,amount_view5, amount_view6, amount_view7, amount_view8,amount_view9;
    private int amountValue1, amountValue2, amountValue3, amountValue4, amountValue5, amountValue6, amountValue7, amountValue8, amountValue9 = 1;





    @Override
    public int setContentView() {
        return R.layout.fragment_set_five;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        amount_view6 = (AmountViewVertical) findViewById(R.id.amount_view6);
        amount_view7 = (AmountViewVertical) findViewById(R.id.amount_view7);


    }

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()) {
            case R.id.amount_view2:
                amountValue2 = amount;
                break;
            case R.id.amount_view3:
                amountValue3 = amount;
                break;
            case R.id.amount_view4:
                amountValue4 = amount;
                break;

            case R.id.amount_view6:
                amountValue6 = amount;
                break;
            case R.id.amount_view7:
                amountValue7 = amount;
                break;
        }
    }

    public void moveCursor(int i) {
        if (amount_view1.hasFocus()){
            if (i > 0)
                amount_view1.getAmount_dncrease_vertical().performClick();
            else
                amount_view1.getAmount_increase_vertical().performClick();

        }else if (amount_view2.hasFocus()){
            if (i > 0)
                amount_view2.getAmount_dncrease_vertical().performClick();
            else
                amount_view2.getAmount_increase_vertical().performClick();
        }else if (amount_view3.hasFocus()){
            if (i > 0)
                amount_view3.getAmount_dncrease_vertical().performClick();
            else
                amount_view3.getAmount_increase_vertical().performClick();
        }else if (amount_view4.hasFocus()){
            if (i > 0)
                amount_view4.getAmount_dncrease_vertical().performClick();
            else
                amount_view4.getAmount_increase_vertical().performClick();
        }else if (amount_view5.hasFocus()){
            if (i > 0)
                amount_view5.getAmount_dncrease_vertical().performClick();
            else
                amount_view5.getAmount_increase_vertical().performClick();
        }else if (amount_view6.hasFocus()){
            if (i > 0)
                amount_view6.getAmount_dncrease_vertical().performClick();
            else
                amount_view6.getAmount_increase_vertical().performClick();
        }else if (amount_view7.hasFocus()){
            if (i > 0)
                amount_view7.getAmount_dncrease_vertical().performClick();
            else
                amount_view7.getAmount_increase_vertical().performClick();
        }
    }

}
