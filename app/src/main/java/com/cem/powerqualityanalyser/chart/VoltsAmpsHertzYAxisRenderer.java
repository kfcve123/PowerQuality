package com.cem.powerqualityanalyser.chart;

import android.graphics.Canvas;
import android.text.TextUtils;

import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class VoltsAmpsHertzYAxisRenderer extends BaseYAxisRenderer{

    protected VoltsAmpsHertzYAxis mYAxis;
    public VoltsAmpsHertzYAxisRenderer(ViewPortHandler viewPortHandler, VoltsAmpsHertzYAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
        this.mYAxis = yAxis;
    }

    @Override
    protected void computeAxisValues(float min, float max) {
        /*折线图左边没有basevalue，则调用系统*/
        if (Float.isNaN(mYAxis.getBaseValue())) {
            super.computeAxisValues(min, max);
            return;
        }
        float base = mYAxis.getBaseValue();
        float yMin = min;
        int labelCount = mYAxis.getLabelCount();
        float interval = (base - yMin) / labelCount;
        int n = labelCount * 2 + 1;
        mYAxis.mEntryCount = n;
        mYAxis.mEntries = new float[n];
        int i;
        float f;
        for (f = min, i = 0; i < n; f += interval, i++) {
            mYAxis.mEntries[i] = f;
        }
    }
    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
        /*当有最小text的时候*/
        if (!TextUtils.isEmpty(mYAxis.getMinValue())) {
            for (int i = 0; i < mYAxis.mEntryCount; i++) {
                /*获取对应位置的值*/
                String text = mYAxis.getFormattedLabel(i);
                if (i == 0) {
                    text = mYAxis.getMinValue();
                }
                if (i == 1) {
                    c.drawText(text, fixedPosition, mViewPortHandler.offsetTop()+2*offset+5 , mAxisLabelPaint);
                } else if (i == 0) {
                    c.drawText(text, fixedPosition, mViewPortHandler.contentBottom() - 3, mAxisLabelPaint);
                }
            }
        }
        else {
            for (int i = 0; i < mYAxis.mEntryCount; i++) {

                String text = mYAxis.getFormattedLabel(i);
                if (!mYAxis.isDrawTopYLabelEntryEnabled() && i >= mYAxis.mEntryCount - 1)
                    return;

                int labelHeight = Utils.calcTextHeight(mAxisLabelPaint, text);
                float pos = positions[i * 2 + 1] + offset;

                if ((pos - labelHeight) < mViewPortHandler.contentTop()) {

                    pos = mViewPortHandler.contentTop() + offset * 2.5f + 3;
                } else if ((pos + labelHeight / 2) > mViewPortHandler.contentBottom()) {
                    pos = mViewPortHandler.contentBottom() - 3;
                }
                c.drawText(text, fixedPosition, pos, mAxisLabelPaint);
            }

        }
    }


}
