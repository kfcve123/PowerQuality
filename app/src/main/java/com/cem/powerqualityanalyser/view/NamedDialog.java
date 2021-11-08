package com.cem.powerqualityanalyser.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;


public class NamedDialog extends Dialog implements View.OnClickListener {
    private TextView tvMessage;
    private Button leftButton;
    private Button rightButton;
    private View.OnClickListener leftListener,rightListener;
    public NamedDialog(@NonNull Context context) {
        super(context);
    }

    public NamedDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NamedDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_named);
        initView();
    }

    private void initView() {
        tvMessage = findViewById(R.id.tv_message);
        leftButton = findViewById(R.id.button_left);
        rightButton = findViewById(R.id.button_right);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_left:
                if (leftListener != null)
                    leftListener.onClick(v);

                break;

            case R.id.button_right:
                if (rightListener != null)
                    rightListener.onClick(v);
                break;

        }
    }

    public NamedDialog setTvMessage(String message){
        tvMessage.setText(message);
        return this;

    }
    public NamedDialog setLeftText(String left){
        leftButton.setText(left);
        return this;

    }
    public NamedDialog setRightText(String right){
        rightButton.setText(right);
        return this;

    }

    public NamedDialog setLeftListener(View.OnClickListener onClickListener){
        this.leftListener = onClickListener;
        return this;
    }
    public NamedDialog setRightListener(View.OnClickListener onClickListener){
        this.rightListener = onClickListener;
        return this;
    }

    @Override
    public void show() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width =display.getWidth();
        int height=display.getHeight();

        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        super.show();
    }
}
