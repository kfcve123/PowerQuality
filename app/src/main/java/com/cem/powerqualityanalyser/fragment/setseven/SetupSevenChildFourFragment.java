package com.cem.powerqualityanalyser.fragment.setseven;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.fragment.BaseFragment;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.view.AmountColorViewHorizontal;

import java.lang.reflect.Field;

/**
 * 颜色
 */
public class SetupSevenChildFourFragment extends BaseFragment implements View.OnClickListener, AmountColorViewHorizontal.OnColorHorizonalAmountChangeListener {


    private AppConfig config;
    private AmountColorViewHorizontal colorViewHorizontal1,colorViewHorizontal2,colorViewHorizontal3,colorViewHorizontal4,colorViewHorizontal5,colorViewHorizontal6,colorViewHorizontal7,colorViewHorizontal8;
    private int amountValue1, amountValue2, amountValue3, amountValue4, amountValue5, amountValue6, amountValue7, amountValue8 = 1;

    @Override
    public void onAmountChange(View view, int amount) {
        switch (view.getId()){
            case R.id.colorViewHorizontal1:
                amountValue1 = amount;
                config.setSetup_Show_Color_VL1(amountValue1);
                break;

            case R.id.colorViewHorizontal2:
                amountValue2 = amount;
                config.setSetup_Show_Color_VL2(amountValue2);
                break;
            case R.id.colorViewHorizontal3:
                amountValue3 = amount;
                config.setSetup_Show_Color_VL3(amountValue3);
                break;

            case R.id.colorViewHorizontal4:
                amountValue4 = amount;
                config.setSetup_Show_Color_VN(amountValue4);
                break;

            case R.id.colorViewHorizontal5:
                amountValue5 = amount;
                config.setSetup_Show_Color_AL1(amountValue5);
                break;

            case R.id.colorViewHorizontal6:
                amountValue6 = amount;
                config.setSetup_Show_Color_AL2(amountValue6);
                break;

            case R.id.colorViewHorizontal7:
                amountValue7 = amount;
                config.setSetup_Show_Color_AL3(amountValue7);
                break;

            case R.id.colorViewHorizontal8:
                amountValue8 = amount;
                config.setSetup_Show_Color_AN(amountValue8);
                break;
        }
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_set_seven_childfour;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        colorViewHorizontal1 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal1);
        colorViewHorizontal2 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal2);
        colorViewHorizontal3 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal3);
        colorViewHorizontal4 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal4);

        colorViewHorizontal5 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal5);
        colorViewHorizontal6 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal6);
        colorViewHorizontal7 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal7);
        colorViewHorizontal8 = (AmountColorViewHorizontal) findViewById(R.id.colorViewHorizontal8);

        colorViewHorizontal1.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal2.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal3.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal4.setOnColorHorizonalAmountChangeListener(this);

        colorViewHorizontal5.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal6.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal7.setOnColorHorizonalAmountChangeListener(this);
        colorViewHorizontal8.setOnColorHorizonalAmountChangeListener(this);

        amountValue1 = config.getSetup_Show_Color_VL1();
        amountValue2 = config.getSetup_Show_Color_VL2();
        amountValue3 = config.getSetup_Show_Color_VL3();
        amountValue4 = config.getSetup_Show_Color_VN();

        amountValue5 = config.getSetup_Show_Color_AL1();
        amountValue6 = config.getSetup_Show_Color_AL2();
        amountValue7 = config.getSetup_Show_Color_AL3();
        amountValue8 = config.getSetup_Show_Color_AN();

        colorViewHorizontal1.setAmount(amountValue1-1);
        colorViewHorizontal2.setAmount(amountValue2-1);
        colorViewHorizontal3.setAmount(amountValue3-1);
        colorViewHorizontal4.setAmount(amountValue4-1);
//        colorViewHorizontal5.setAmount(amountValue5-1);
//        colorViewHorizontal6.setAmount(amountValue6-1);
//        colorViewHorizontal7.setAmount(amountValue7-1);
//        colorViewHorizontal8.setAmount(amountValue8-1);

    }


    @Override
    public void onClick(View view) {


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

    public void moveCursor(int i) {
        if (colorViewHorizontal1.hasFocus()){
            if (i > 0)
                colorViewHorizontal1.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal1.getAmount_increase_horizontal().performClick();

        }else if (colorViewHorizontal2.hasFocus()){
            if (i > 0)
                colorViewHorizontal2.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal2.getAmount_increase_horizontal().performClick();
        }else if (colorViewHorizontal3.hasFocus()){
            if (i > 0)
                colorViewHorizontal3.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal3.getAmount_increase_horizontal().performClick();
        }else if (colorViewHorizontal4.hasFocus()){
            if (i > 0)
                colorViewHorizontal4.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal4.getAmount_increase_horizontal().performClick();
        }else if (colorViewHorizontal5.hasFocus()){
            if (i > 0)
                colorViewHorizontal5.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal5.getAmount_increase_horizontal().performClick();
        }else if (colorViewHorizontal6.hasFocus()){
            if (i > 0)
                colorViewHorizontal6.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal6.getAmount_increase_horizontal().performClick();

        }else if (colorViewHorizontal7.hasFocus()){
            if (i > 0)
                colorViewHorizontal7.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal7.getAmount_increase_horizontal().performClick();

        }else if (colorViewHorizontal8.hasFocus()){
            if (i > 0)
                colorViewHorizontal8.getAmount_dncrease_horizontal().performClick();
            else
                colorViewHorizontal8.getAmount_increase_horizontal().performClick();
        }
    }





}
