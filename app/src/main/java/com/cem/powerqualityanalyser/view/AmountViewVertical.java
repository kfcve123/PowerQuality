package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;


/**
 * 垂直方向加减器
 */
public class AmountViewVertical extends LinearLayout implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "AmountView";
    private int amount = 1; //购买数量
    private int goods_storage = 400; //商品库存
    private int limit_down = 1;
    private OnVerticalAmountChangeListener mListener;
    private String[] dataArray;
    private boolean showAmount = false;

    private TextView amount_tv_vertical;
    private ImageView amount_increase_vertical, amount_dncrease_vertical;

    private AmountType amountType = AmountType.ContentInt;


    private enum AmountType{
        ContentInt,
        ContentStr;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public void setTextSize(int size) {
        if(amount_tv_vertical!=null)
            amount_tv_vertical.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }
    public boolean isShowAmount() {
        return showAmount;
    }

    public void setShowAmount(boolean showAmount) {
        this.showAmount = showAmount;
        if(showAmount){
            amount_increase_vertical.setVisibility(VISIBLE);
            amount_dncrease_vertical.setVisibility(VISIBLE);
        }else{
            amount_increase_vertical.setVisibility(INVISIBLE);
            amount_dncrease_vertical.setVisibility(INVISIBLE);
        }
    }

    public ImageView getAmount_increase_vertical() {
        return amount_increase_vertical;
    }

    public void setAmount_increase_vertical(ImageView amount_increase_vertical) {
        this.amount_increase_vertical = amount_increase_vertical;
    }

    public ImageView getAmount_dncrease_vertical() {
        return amount_dncrease_vertical;
    }

    public void setAmount_dncrease_vertical(ImageView amount_dncrease_vertical) {
        this.amount_dncrease_vertical = amount_dncrease_vertical;
    }

    public AmountViewVertical(Context context) {
        this(context, null);
    }

    public AmountViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_amount_vertical, this);
        findViewById(R.id.amount_layout).setOnFocusChangeListener(this);
        amount_tv_vertical = view.findViewById(R.id.amount_tv_vertical);
        amount_increase_vertical = view.findViewById(R.id.amount_increase_vertical);
        amount_dncrease_vertical = view.findViewById(R.id.amount_dncrease_vertical);

        amount_increase_vertical.setOnClickListener(this);
        amount_dncrease_vertical.setOnClickListener(this);
    }

    public int getLimit_down() {
        return limit_down;
    }

    public void setLimit_down(int limit_down) {
        this.limit_down = limit_down;
    }

    public void setOnVerticalAmountChangeListener(OnVerticalAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setDataArray(String[] dataArray) {
        this.dataArray = dataArray;
        goods_storage = dataArray.length + 1;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.amount_increase_vertical) {
            if (amount < goods_storage) {
                if (dataArray != null && amount < dataArray.length){
                    amount++;
                    amount_tv_vertical.setText(dataArray[amount-1] + "");
                }else if (dataArray == null){
                    amount++;
                    amount_tv_vertical.setText(amount + "");
                }
           /* }else if(amount == goods_storage){
                amount =0;
                amount_tv_vertical.setText(dataArray[0] + "");*/
            }
        } else if (i == R.id.amount_dncrease_vertical) {

            if (amount > limit_down) {
                if (dataArray != null){
                    amount--;
                    amount_tv_vertical.setText(dataArray[amount-1] + "");
                }else if (dataArray == null){
                    amount--;
                    amount_tv_vertical.setText(amount + "");
                }
            /*}else if(amount == limit_down){
                amount = goods_storage;
                amount_tv_vertical.setText(dataArray[amount-1] + "");*/
            }
        }
        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    public void setContent(String s) {
        if(amount_tv_vertical!=null)
            amount_tv_vertical.setText(s);

    }
    public String getContent(){
        if (amount_tv_vertical != null)
            return amount_tv_vertical.getText().toString();
        return "";
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
//            this.amount_increase_vertical.setImageResource(R.mipmap.amount_up);
//            this.amount_dncrease_vertical.setImageResource(R.mipmap.amount_down);
            amount_increase_vertical.setVisibility(INVISIBLE);
            amount_dncrease_vertical.setVisibility(INVISIBLE);
            amount_tv_vertical.setBackgroundResource(R.mipmap.set_time_nocontent_bg);
            amount_tv_vertical.setTextColor(getResources().getColor(R.color.set_text_color));
        }else{
            amount_increase_vertical.setVisibility(VISIBLE);
            amount_dncrease_vertical.setVisibility(VISIBLE);
//            this.amount_increase_vertical.setImageResource(R.mipmap.amount_up2);
//            this.amount_dncrease_vertical.setImageResource(R.mipmap.amount_down2);
            amount_tv_vertical.setTextColor(getResources().getColor(R.color.set_text_color_choose));
            amount_tv_vertical.setBackgroundResource(R.mipmap.set_time_content_bg);
        }
    }



    public interface OnVerticalAmountChangeListener {
        void onAmountChange(View view, int amount);
    }


}
