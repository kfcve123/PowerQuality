package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author zhy
 */
public class PagingItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Paint paint;
    private View view;
    private GridLayoutManager mPageDecorationLastJudge;

    public PagingItemDecoration(Context context, GridLayoutManager pageDecorationLastJudge) {
        if (pageDecorationLastJudge == null) {
            throw new IllegalArgumentException("pageDecorationLastJudge must be no null");
        }
        paint = new Paint();
        //设置画出的线的 粗细程度
        paint.setStrokeWidth(2);
        //设置阴影
        //paint.setShadowLayer(10,0,5,Color.YELLOW);
        mPageDecorationLastJudge = pageDecorationLastJudge;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);

        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }



    float x = 0;
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin;
            final int top = child.getTop()-params.bottomMargin;
            final int bottom = child.getBottom()+params.bottomMargin;
            paint.setColor(Color.BLACK);
            if (top>50) {
                c.drawLine(left, top - 1, right + 5000, top - 1, paint);
                paint.setColor(Color.parseColor("#363232"));
                c.drawLine(left, top + 1, right + 5000, top + 1, paint);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getLeft() + params.rightMargin;
            final int right = left + child.getWidth();
            paint.setColor(Color.BLACK);
            if (left>50) {
                c.drawLine(left - 1, top, left - 1, bottom + 800, paint);
                paint.setColor(Color.parseColor("#363232"));
                c.drawLine(left + 1, top, left + 1, bottom + 800, paint);
         /*   mDivider.draw(c);
            c.drawd(mDivider,paint);*/
            }
        }
    }

    int j;
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
/*        this.view = view;
        if (mPageDecorationLastJudge.isPageLast(itemPosition)) {
            Log.d("canvas","最后一页:"+itemPosition);
            outRect.set(0, 0, 0, 0);
        } else if (mPageDecorationLastJudge.isLastRow(itemPosition))// 如果是最后一行，则不需要绘制底部
        {

            Log.d("canvas","行数:"+itemPosition);
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        } else if (mPageDecorationLastJudge.isLastColumn(itemPosition))// 如果是最后一列，则不需要绘制右边
        {
            j=itemPosition;
            Log.d("canvas","列数:"+itemPosition);
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            Log.d("canvas","其余:"+itemPosition);
            outRect.set(0, 0, mDivider.getIntrinsicWidth(),
                    mDivider.getIntrinsicHeight());
        }*/

    }


}