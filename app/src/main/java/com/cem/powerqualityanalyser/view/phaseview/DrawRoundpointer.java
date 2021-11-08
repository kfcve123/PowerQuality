package com.cem.powerqualityanalyser.view.phaseview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.cem.powerqualityanalyser.R;

import java.text.DecimalFormat;
import java.util.List;

public class DrawRoundpointer extends View {
    private PaintFlagsDrawFilter mDrawFilter;
    private float diameter;
    private float centerX;  //圆心X坐标
    private float centerY;  //圆心Y坐标
    private RectF bgRect;
    private Context context;
    private float Degrees;
    private float Percentage;
    private boolean havepointer=false;
    private int back_color;
    private  int lines_color;
    private float bgArcWidth ;
    private float lines_width;
    private float pointer_hight;
    private float pointet_bottom;
    private float startangle=0;
    private DecimalFormat decimalFormat=new DecimalFormat("0");
    private DashPathEffect dashPathEffect = new DashPathEffect(new float[]{5,5},0);
    private Paint outerArcPaint=new Paint();
    private Paint LinesPaint=new Paint();
    private Paint PointerPaint=new Paint();
    private Paint InsideRound=new Paint();
    private Paint round=new Paint();
    private Paint textPaint=new Paint();
    private Paint backPaint = new Paint();
    private Path mPath;
    private List<Pointer> allpointer;
    public DrawRoundpointer(Context context) {
        super(context);
        this.context=context;
    }


    public DrawRoundpointer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initCofig(context, attrs);

        initView();
    }


    public DrawRoundpointer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initCofig(context, attrs);

        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    private void init(){
        if (bgRect==null) {
            if (getHeight() < 50 || getWidth() < 50) {
                Toast.makeText(context, "View is so small", Toast.LENGTH_SHORT).show();
            } else {
                if (getHeight() >= getWidth()) {
                    diameter = getWidth() - 50;
                } else {
                    diameter = getHeight() - 50;
                }
                setMeasuredDimension(getWidth(), getHeight());
                centerX = getWidth() / 2;
                centerY = getHeight() / 2;
                bgRect = new RectF();
                bgRect.top = centerY - diameter / 2 + bgArcWidth + 1;
                bgRect.left = centerX - diameter / 2 + bgArcWidth + 1;
                bgRect.bottom = centerY + diameter / 2 - (bgArcWidth + 1);
                bgRect.right = centerX + diameter / 2 - (bgArcWidth + 1);
            }
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        init();
        canvas.setDrawFilter(mDrawFilter);
        canvas.rotate(180,centerX,centerY);
        canvas.save();

        canvas.drawCircle(centerX,centerY,diameter/2 - 2,backPaint);
        canvas.drawCircle(centerX,centerY,diameter/2,round);
        canvas.drawCircle(centerX,centerY,diameter*2/5,outerArcPaint);
        canvas.drawCircle(centerX,centerY,diameter*3/10,outerArcPaint);
        canvas.drawCircle(centerX,centerY,diameter/5,outerArcPaint);

        for (int i=0;i<6;i++) {
            startangle=i*30;
            canvas.save();
            canvas.rotate(startangle, centerX, centerY);
            if (startangle==0||startangle==120){
                canvas.drawText(decimalFormat.format(startangle),centerX - diameter / 2-25,centerY,textPaint);
            }    canvas.save();
            canvas.rotate(180,centerX,centerY);
            if (startangle+180==240){
                canvas.drawText(decimalFormat.format(startangle+180),centerX - diameter / 2-25,centerY,textPaint);
            }
            canvas.restore();
            mPath.reset();
            mPath.moveTo(centerX - diameter / 2, centerY);
            mPath.lineTo(centerX + diameter / 2, centerY);
            canvas.drawPath(mPath,LinesPaint);
            canvas.restore();

        }
        canvas.drawCircle(centerX,centerY,diameter/5,InsideRound);
        if (havepointer){
            if (allpointer!=null&&allpointer.size()>0){
                for (int i=0;i<allpointer.size();i++){
                    if (allpointer.get(i).getColor()!=0){
                        if (lines_color!=allpointer.get(i).getColor()){
                            PointerPaint.setColor(allpointer.get(i).getColor());
                        }else{
                            PointerPaint.setColor(lines_color);
                        }
                    }else {
                        PointerPaint.setColor(lines_color);
                    }
                    if(allpointer.get(i).getPercentage()>1||allpointer.get(i).getPercentage()<0){
                        Toast.makeText(context,"Group "+String.valueOf(i)+" data is out of range",Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    canvas.save();
                    canvas.rotate(allpointer.get(i).getDegrees(),centerX,centerY);
                    drawTria(centerX,centerY,centerX-(diameter/2)*allpointer.get(i).getPercentage(),centerY,pointer_hight,pointet_bottom,canvas,PointerPaint);
                    canvas.restore();
                }
            }

        }

    }
    private void drawTria(float fromX, float fromY, float toX, float toY,
                            float heigth, float bottom,Canvas canvas,Paint paint) {
        canvas.drawLine(fromX, fromY, toX, toY, paint);
        float juli = (float) Math.sqrt((toX - fromX) * (toX - fromX)+ (toY - fromY) * (toY - fromY));// 获取线段距离
        float juliX = toX - fromX;// 有正负，不要取绝对值
        float juliY = toY - fromY;// 有正负，不要取绝对值
        float dianX = toX - (heigth / juli * juliX);
        float dianY = toY - (heigth / juli * juliY);
        float dian2X = fromX + (heigth / juli * juliX);
        float dian2Y = fromY + (heigth / juli * juliY);//终点的箭头
        Path path = new Path();
        path.moveTo(toX, toY);// 此点为三边形的起点
        path.lineTo(dianX + (bottom / juli * juliY), dianY- (bottom / juli * juliX));
        path.lineTo(dianX - (bottom / juli * juliY), dianY+ (bottom / juli * juliX));
        path.close(); // 使这些点构成封闭的三边形
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, paint);

    }
    private void initCofig(Context context, AttributeSet attrs) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawRoundpointer);
        bgArcWidth = a.getDimension(R.styleable.DrawRoundpointer_back_width, dipToPx(2));
        back_color=a.getColor(R.styleable.DrawRoundpointer_back_color, Color.parseColor("#5871CCFB"));
        lines_color=a.getColor(R.styleable.DrawRoundpointer_lines_color,Color.WHITE);
        lines_width=a.getDimension(R.styleable.DrawRoundpointer_lines_width,dipToPx(2));
        pointer_hight=a.getFloat(R.styleable.DrawRoundpointer_pointer_hight,dipToPx(3));
        pointet_bottom=a.getFloat(R.styleable.DrawRoundpointer_pointer_bottom,dipToPx(3));
        a.recycle();*/
        bgArcWidth=dipToPx(2);
        back_color= Color.parseColor("#5f5f5f");
        lines_color=Color.WHITE;
        lines_width=dipToPx(2);
        pointer_hight=dipToPx(3);
        pointet_bottom=dipToPx(3);
    }
    private void  initView(){
        backPaint.setColor(this.context.getResources().getColor(R.color.circleBack));
        backPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(back_color);

        round.setAntiAlias(true);
        round.setStyle(Paint.Style.STROKE);
        round.setStrokeWidth(bgArcWidth);
        round.setColor(back_color);
        round.setStrokeCap(Paint.Cap.ROUND);

        outerArcPaint.setAntiAlias(true);
        outerArcPaint.setStyle(Paint.Style.STROKE);
        outerArcPaint.setStrokeWidth(dipToPx(1));
        outerArcPaint.setColor(back_color);
        outerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        outerArcPaint.setPathEffect(dashPathEffect);

//        InsideRound.setStyle(Paint.Style.FILL);
//        InsideRound.setAlpha(1);
        try {
            InsideRound.setColor(((ColorDrawable)getBackground()).getColor());
        }catch (Exception ex){
            InsideRound.setColor(Color.WHITE);
        }
        InsideRound.setAntiAlias(true);
//        InsideRound.setStrokeCap(Paint.Cap.ROUND);

        LinesPaint.setStyle(Paint.Style.STROKE);
        LinesPaint.setColor(back_color);
        LinesPaint.setStrokeWidth(dipToPx(1));
        LinesPaint.setPathEffect(dashPathEffect);



        PointerPaint.setColor(lines_color);
        PointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        PointerPaint.setStrokeWidth(lines_width);


        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPath=new Path();
    }
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setPointer(List<Pointer> pointers) {
        havepointer = true;
        allpointer = pointers;
        invalidate();
    }
}
