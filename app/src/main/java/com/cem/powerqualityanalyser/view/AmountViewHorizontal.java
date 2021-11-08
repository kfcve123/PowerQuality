package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.tool.log;


/**
 * 加减器
 */
public class AmountViewHorizontal extends LinearLayout implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "AmountView";
    private int amount = 1; //
    private int goods_storage = 400; //
    private int limit_down = 1;
    private OnHorizonalAmountChangeListener mListener;
    private String[] dataArray;
    private boolean showAmount = false;
    private TextView amount_tv_horizontal;
    private ImageView amount_increase_horizontal, amount_dncrease_horizontal;
    private RelativeLayout amount_layout;
    private boolean showArrow = true;
    public void setShowArrow(boolean showArrow){
        this.showArrow = showArrow;
    }

    public void setTextSize(int size) {
        if(amount_tv_horizontal!=null)
            amount_tv_horizontal.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    public void setValueBg(int res){
        amount_tv_horizontal.setBackgroundResource(res);
    }
    private boolean showbg = true;
    public void setShowValueBg(boolean show){
        showbg = show;
    }
    public void setValueBg(boolean show){
        if(!show){
            amount_tv_horizontal.setBackground(null);
        }
    }

    public boolean isShowAmount() {
        return showAmount;
    }

    public void setShowAmount(boolean showAmount) {
        this.showAmount = showAmount;
        if(showAmount){
            amount_increase_horizontal.setVisibility(VISIBLE);
            amount_dncrease_horizontal.setVisibility(VISIBLE);
        }else{
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

    public AmountViewHorizontal(Context context) {
        this(context, null);
    }

    public AmountViewHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_amount_horizontal, this);
        amount_layout = findViewById(R.id.amount_layout);
        amount_layout.setOnFocusChangeListener(this);
        amount_tv_horizontal = findViewById(R.id.amount_tv_horizontal);
        amount_increase_horizontal = findViewById(R.id.amount_increase_horizontal);
        amount_dncrease_horizontal = findViewById(R.id.amount_dncrease_horizontal);
        amount_increase_horizontal.setOnClickListener(this);
        amount_dncrease_horizontal.setOnClickListener(this);
    }

    public void setAmountViewLayout(int width){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,LinearLayout.LayoutParams.WRAP_CONTENT);
        amount_layout.setLayoutParams(params);
        invalidate();
    }

    public int getLimit_down() {
        return limit_down;
    }

    public void setLimit_down(int limit_down) {
        this.limit_down = limit_down;
    }

    public void setOnHorizonalAmountChangeListener(OnHorizonalAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.amount_increase_horizontal) {
            if (amount > limit_down) {
                if (dataArray != null){
                    amount--;
                    amount_tv_horizontal.setText(dataArray[amount-1] + "");
                }else if (dataArray == null){
                    amount--;
                    amount_tv_horizontal.setText(amount + "");
                }
//                amount--;
//                amount_tv_horizontal.setText(amount + "");
            }
        } else if (i == R.id.amount_dncrease_horizontal) {
            if (amount < goods_storage) {
                if (dataArray != null && amount < dataArray.length){
                    amount++;
                    amount_tv_horizontal.setText(dataArray[amount-1] + "");
                }else if (dataArray == null){
                    amount++;
                    amount_tv_horizontal.setText(amount + "");
                }

//                amount++;
//                amount_tv_horizontal.setText(amount + "");
            }
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public void setContent(String s) {
        if(amount_tv_horizontal!=null)
            amount_tv_horizontal.setText(s);
    }
    public void setDataArray(String[] dataArray) {
        this.dataArray = dataArray;
        goods_storage = dataArray.length + 1;
    }


    public String getContent(){
        if (amount_tv_horizontal != null)
            return amount_tv_horizontal.getText().toString();
        return "";
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
//        log.e("--------" + v.toString() +"------------" +  hasFocus);
        if (!hasFocus){
            amount_increase_horizontal.setVisibility(INVISIBLE);
            amount_dncrease_horizontal.setVisibility(INVISIBLE);
            if(!showbg) {
                amount_tv_horizontal.setBackgroundResource(R.mipmap.set_time_nocontent_bg);
                amount_tv_horizontal.setTextColor(getResources().getColor(R.color.set_text_color));
            }
            this.amount_increase_horizontal.setImageResource(R.mipmap.amount_left);
            this.amount_dncrease_horizontal.setImageResource(R.mipmap.amount_right);
        }else{
            this.amount_increase_horizontal.setImageResource(R.mipmap.amount_left);
            this.amount_dncrease_horizontal.setImageResource(R.mipmap.amount_right);
            if(showArrow) {
                amount_increase_horizontal.setVisibility(VISIBLE);
                amount_dncrease_horizontal.setVisibility(VISIBLE);
            }
            if(!showbg) {
                amount_tv_horizontal.setBackgroundResource(R.mipmap.set_time_content_bg);
                amount_tv_horizontal.setTextColor(getResources().getColor(R.color.set_text_color_choose));
            }
        }
    }


    public interface OnHorizonalAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.requestFocus();
        return super.onTouchEvent(event);
    }
}

