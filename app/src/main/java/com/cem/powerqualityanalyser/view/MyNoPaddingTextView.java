package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class MyNoPaddingTextView extends android.support.v7.widget.AppCompatTextView {
    private Rect minRect;

    public MyNoPaddingTextView(Context context) {
        super(context);
    }

    public MyNoPaddingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNoPaddingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (minRect == null) {
            //minRect用来获取文字显示所需要最小区域的左上角和右下角  坐标
            //该坐标是以（0,0）为基准的矩形坐标
            minRect = new Rect();
        }
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), minRect);
        final int width = minRect.width();
        final int height = minRect.height();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final String text = getText().toString();
        final int left = minRect.left;
        final int top = minRect.top;
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        /*此时文字的基线在（0,0），要达到刚好包裹文字的效果，相当于把以(0,0)为基线的minRect 移动到合适的位置
        x轴上由于左边内边距的存在，所以需要左移minRect.left距离
        y轴上相当于把mingRect的顶点向下移动minRect.top距离
        */
        canvas.drawText(text, -left, -top, paint);

    }
}
