package com.cem.powerqualityanalyser.view.datalist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.GroupedListAdapter;
import com.cem.powerqualityanalyser.tool.log;
import com.cem.powerqualityanalyser.userobject.MeterGroupChildObj;
import com.cem.powerqualityanalyser.userobject.MeterGroupListObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;
import com.cem.powerqualityanalyser.view.RightModeView;

public class MyTableListView extends StickyHeaderLayout {
    private GroupedListAdapter adapter;
    private RecyclerView rvList;

    public MyTableListView(@NonNull Context context) {
        super(context);
        initView();
    }

    public MyTableListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public MyTableListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        rvList = new RecyclerView(this.getContext());
        rvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new GroupedListAdapter(this.getContext());
        rvList.setBackgroundColor(getContext().getResources().getColor(R.color.tabledrive));
        rvList.addItemDecoration(new CommItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL, Color.WHITE, 1));
        //LinearLayout.LayoutParams layoutParams=   new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //  FrameLayout.LayoutParams  layoutParams =new LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        addView(rvList, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rvList.setAdapter(adapter);

        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                holder.itemView.requestFocus();
                holder.itemView.setFocusable(true);
            }
        });
    }

    public void addItem(MeterGroupListObj obj) {
        adapter.addItem(obj);
        adapter.notifyDataChanged();
    }

    public void scrollToPosition(int position) {
        rvList.smoothScrollToPosition(position);
    }

    public void removeItem(MeterGroupListObj obj) {
        adapter.removeItem(obj);
    }

    public void setHeadColors(int[] headColors) {
        if (this.adapter != null)
            adapter.setHeadColorList(headColors);
    }

    public void clearData() {
        adapter.clearData();
        adapter.notifyDataChanged();
    }

    public MeterGroupListObj getGroupItem(int index) {
        return adapter.getGroupItem(index);
    }

    public MeterGroupChildObj getChildItem(int index, int childIndex) {
        return adapter.getGroupChild(index, childIndex);
    }

    public void setChildItem(int index, int childIndex, int cid, String str) {
        MeterGroupChildObj obj = getChildItem(index, childIndex);
        obj.setItem(cid, str);
        adapter.notifyChildChanged(index, childIndex);
    }

    public void setChildItem(int index, int childIndex, int cid, String str, int resImageid) {
        MeterGroupChildObj obj = getChildItem(index, childIndex);
        obj.setItem(cid, str, resImageid);
        adapter.notifyChildChanged(index, childIndex);
    }

    /*public void setChildItem(int index, int childIndex, int cid, SpannableStringBuilder str) {
        MeterGroupChildObj obj = getChildItem(index, childIndex);
        obj.setItem(cid, str);
        adapter.notifyChildChanged(index, childIndex);
    }

    public void setChildItem(int index, int childIndex, int cid, SpannableStringBuilder str, int resImageid) {
        MeterGroupChildObj obj = getChildItem(index, childIndex);
        obj.setItem(cid, str, resImageid);
        adapter.notifyChildChanged(index, childIndex);
    }*/


    public void notifyChildChanged() {

        adapter.notifyDataChanged();
    }

    public int getShowDividerCount() {
        return adapter.getShowDividerCount();
    }

    /**
     * 显示一条竖线
     */
    public void setShowOneDividerMode() {
        adapter.setShowDividerCount(1);
    }

    /**
     * 显示全部竖线
     */
    public void setShowAllDividerMode() {
        adapter.setShowDividerCount(4);
    }

    public void setShowDividerCount(int showDividerCount) {
        adapter.setShowDividerCount(showDividerCount);
    }

    public int showItemsCount() {
        return adapter.getGroupCount();
    }

    public void setItemAccount(int i) {
        adapter.setItemAccount(i);
    }

    public class CommItemDecoration extends RecyclerView.ItemDecoration {

        private static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private int mSpace = 1;     //间隔
        private Rect mRect = new Rect(0, 0, 0, 0);
        private Paint mPaint = new Paint();

        private int mOrientation;

        private CommItemDecoration(Context context, int orientation, int color, int space) {
            mOrientation = orientation;
            if (space > 0) {
                mSpace = space;
            }
            mPaint.setColor(color);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mSpace;
                mRect.set(left, top, right, bottom);
                c.drawRect(mRect, mPaint);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mSpace;
                mRect.set(left, top, right, bottom);
                c.drawRect(mRect, mPaint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mSpace);
            } else {
                outRect.set(0, 0, mSpace, 0);
            }
        }

        public CommItemDecoration createVertical(Context context, int color, int height) {
            return new CommItemDecoration(context, VERTICAL_LIST, color, height);
        }

        public CommItemDecoration createHorizontal(Context context, int color, int width) {
            return new CommItemDecoration(context, HORIZONTAL_LIST, color, width);
        }
    }
}
