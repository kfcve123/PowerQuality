package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cem.powerqualityanalyser.R;

public class ConfigChooseView extends LinearLayout {
    private ImageView leftchoose;
    private TextView lefttext;

    public ConfigChooseView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public ConfigChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.configchoose_view,this);
        leftchoose = (ImageView) linearLayout.findViewById(R.id.leftchoose);
        lefttext=(TextView) linearLayout.findViewById(R.id.lefttext);
        leftchoose.setImageResource(R.mipmap.config_choose_no);
        lefttext.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
        lefttext.setTextColor(getResources().getColor(R.color.set_text_color));

    }

    public void setChooseShow(boolean show){
        if(show){
            leftchoose.setImageResource(R.mipmap.config_choose_on);
            lefttext.setBackground(getResources().getDrawable(R.mipmap.config_set_bg));
            lefttext.setTextColor(getResources().getColor(R.color.colorwhite));
        }else{
            leftchoose.setImageResource(R.mipmap.config_choose_no);
            lefttext.setBackground(getResources().getDrawable(R.mipmap.config_set_nobg));
            lefttext.setTextColor(getResources().getColor(R.color.set_text_color));
        }
    }

    public void setText(int res){
        if(lefttext!=null)
            lefttext.setText(res);

    }

    public void setText(String res){
        if(lefttext!=null)
            lefttext.setText(res);

    }

}
