package com.cem.powerqualityanalyser.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cem.powerqualityanalyser.R;


@SuppressLint("AppCompatCustomView")
public class TextImageView extends TextView {
    private   Drawable drawableTriangle;
    public TextImageView(Context context) {
        super(context);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setText(CharSequence text, BufferType type) {

        if (drawableTriangle==null) {
            drawableTriangle = getResources().getDrawable(R.mipmap.star_ico, null);
            drawableTriangle.setBounds(0, 0, drawableTriangle.getIntrinsicWidth(),
                    drawableTriangle.getIntrinsicHeight() );
        }


        Spanned sp=null;
        if (text!=null&&text.length()>0) {
            String stext=text.toString();
            if (stext.contains("人")){
                stext=stext.replace("人", "<img src='" + R.mipmap.star_ico + "'>");
                sp = setImage(stext);
                super.setText(sp, type);
                return;
            }

        }
        super.setText(text, type);
    }
    private  Spanned setImage(String htmlFor02){
        Spanned sp=Html.fromHtml(htmlFor02, new Html.ImageGetter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public Drawable getDrawable(String source) {
                return drawableTriangle;
            }
        }, null);
        return sp;
    }
}
