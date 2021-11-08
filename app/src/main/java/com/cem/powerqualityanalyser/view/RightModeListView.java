package com.cem.powerqualityanalyser.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

public class RightModeListView extends ListView {


    public RightModeListView(Context context) {
        super(context);
    }

    public RightModeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RightModeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RightModeListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {

        int lastSelectItem = getSelectedItemPosition();

        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if (gainFocus) {

            setSelection(lastSelectItem);

        }

    }


}
