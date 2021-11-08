package com.cem.powerqualityanalyser.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MonitorBarChartRenderer extends BarChartRenderer {

    public MonitorBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);

 //       mHighlightPaint.setColor(Color.rgb(254, 199, 109));
        mHighlightPaint.setStyle(Paint.Style.STROKE);
        mRenderPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        BarData barData = mChart.getBarData();

        for (Highlight high : indices) {

            IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            BarEntry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set))
                continue;

            Transformer trans = mChart.getTransformer(set.getAxisDependency());

//            mHighlightPaint.setColor(set.getHighLightColor());
//            mHighlightPaint.setAlpha(set.getHighLightAlpha());
            mHighlightPaint.setColor(Color.rgb(254, 199, 109));

            boolean isStack = (high.getStackIndex() >= 0  && e.isStacked()) ? true : false;

            final float y1;
            final float y2;

            if (isStack) {

                if(mChart.isHighlightFullBarEnabled()) {

                    y1 = e.getPositiveSum();
                    y2 = -e.getNegativeSum();

                } else {
                    Range range = e.getRanges()[high.getStackIndex()];

                    y1 = range.from;
                    y2 = range.to;
                }

            } else {
                y1 = e.getY();
                y2 = 0.f;
            }

            prepareBarHighlight(e.getX(), y1, y2, barData.getBarWidth() / 2f, trans);

            /*protected void prepareBarHighlight(float x, float y1, float y2, float barWidthHalf, Transformer trans) {

                float left = x - barWidthHalf;
                float right = x + barWidthHalf;
                float top = y1;
                float bottom = y2;

                mBarRect.set(left, top, right, bottom);

                trans.rectToPixelPhase(mBarRect, mAnimator.getPhaseY());
            }*/


            setHighlightDrawPos(high, mBarRect);
            //c.drawRect(mBarRect, mHighlightPaint);
            c.drawLine(mBarRect.left/2 + mBarRect.right/2, mBarRect.bottom, mBarRect.left/2 + mBarRect.right/2, mBarRect.top - 200, mHighlightPaint);
        }

    }
}
