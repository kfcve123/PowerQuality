package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;


/**
 * 加减器
 */
public class AmountColorViewHorizontal extends LinearLayout implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "AmountView";
    private int amount = 1; //购买数量
    private int goods_storage = 400; //商品库存
    private int limit_down = 1;
    private OnColorHorizonalAmountChangeListener mListener;
    private int[] dataColorArray;
    private boolean showAmount = false;
    private ImageView amount_iv_horizontal;
    private ImageView amount_increase_horizontal, amount_dncrease_horizontal;


    public boolean isShowAmount() {
        return showAmount;
    }

    public void setShowAmount(boolean showAmount) {
        this.showAmount = showAmount;
        if (showAmount) {
            amount_increase_horizontal.setVisibility(VISIBLE);
            amount_dncrease_horizontal.setVisibility(VISIBLE);
        } else {
            amount_increase_horizontal.setVisibility(INVISIBLE);
            amount_dncrease_horizontal.setVisibility(INVISIBLE);
        }
    }


    public ImageView getAmount_increase_horizontal() {
        return amount_increase_horizontal;
    }

    public void setAmount_increase_horizontal(ImageView amount_increase_horizontal) {
        this.amount_increase_horizontal = amount_increase_horizontal;
    }

    public ImageView getAmount_dncrease_horizontal() {
        return amount_dncrease_horizontal;
    }

    public void setAmount_dncrease_horizontal(ImageView amount_dncrease_horizontal) {
        this.amount_dncrease_horizontal = amount_dncrease_horizontal;
    }

    public AmountColorViewHorizontal(Context context) {
        this(context, null);
    }

    public AmountColorViewHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.colorview_amount_horizontal, this);
        findViewById(R.id.amount_layout).setOnFocusChangeListener(this);

        TypedArray ar = context.getResources().obtainTypedArray(R.array.colorview_arrays);
        final int len = ar.length();
        dataColorArray = new int[len];
        for (int i = 0; i < len; i++){
            dataColorArray[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        goods_storage = dataColorArray.length;

        amount_iv_horizontal = findViewById(R.id.amount_iv_horizontal);
        amount_increase_horizontal = findViewById(R.id.amount_increase_horizontal);
        amount_dncrease_horizontal = findViewById(R.id.amount_dncrease_horizontal);
        amount_increase_horizontal.setOnClickListener(this);
        amount_dncrease_horizontal.setOnClickListener(this);
    }

    public int getLimit_down() {
        return limit_down;
    }

    public void setLimit_down(int limit_down) {
        this.limit_down = limit_down;
    }

    public void setOnColorHorizonalAmountChangeListener(OnColorHorizonalAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        amount_iv_horizontal.setBackgroundColor(getResources().getColor(dataColorArray[amount]));
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.amount_increase_horizontal) {
            if (amount > limit_down) {
                if (dataColorArray != null) {
                    amount--;
                    amount_iv_horizontal.setBackgroundColor(getResources().getColor(dataColorArray[amount-1]));
                } else if (dataColorArray == null) {
                    amount--;
                    amount_iv_horizontal.setBackgroundColor(getResources().getColor(dataColorArray[amount]));
                }
            }
        } else if (i == R.id.amount_dncrease_horizontal) {
            if (amount < goods_storage) {
                if (dataColorArray != null && amount < dataColorArray.length) {
                    amount++;
                    amount_iv_horizontal.setBackgroundColor(getResources().getColor(dataColorArray[amount-1]));
                } else if (dataColorArray == null) {
                    amount++;
                    amount_iv_horizontal.setBackgroundColor(getResources().getColor(dataColorArray[amount]));
                }
            }
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public void setDataArray(int[] dataArray) {
        this.dataColorArray = dataArray;
        goods_storage = dataArray.length + 1;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            amount_increase_horizontal.setVisibility(INVISIBLE);
            amount_dncrease_horizontal.setVisibility(INVISIBLE);
            this.amount_increase_horizontal.setImageResource(R.mipmap.amount_left);
            this.amount_dncrease_horizontal.setImageResource(R.mipmap.amount_right);
        } else {
            this.amount_increase_horizontal.setImageResource(R.mipmap.amount_left);
            this.amount_dncrease_horizontal.setImageResource(R.mipmap.amount_right);
            amount_increase_horizontal.setVisibility(VISIBLE);
            amount_dncrease_horizontal.setVisibility(VISIBLE);
        }
    }


    public interface OnColorHorizonalAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.requestFocus();
        return super.onTouchEvent(event);
    }
}

