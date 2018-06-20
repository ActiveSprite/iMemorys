package com.example.guhugang.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;

/**
 * Created by guhug on 2018/5/5.
 */

public class CircleCheckBox extends View {
    private Paint CirclePaint;
    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint selectPaint;
    private int currentNumber;
    private boolean mChecked=false;
    Paint.FontMetrics fontMetrics;
    public CircleCheckBox(Context context) {
        super(context);
        initView();
    }

    public CircleCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CircleCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public CircleCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    private void initView(){
        CirclePaint=new Paint();
        CirclePaint.setAntiAlias(true);
        CirclePaint.setStrokeWidth(6);
        CirclePaint.setColor(Color.WHITE);
        CirclePaint.setStyle(Paint.Style.STROKE);

        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(25);

        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(Color.parseColor("#3399ff"));

        selectPaint=new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setColor(Color.parseColor("#9900ff"));

        fontMetrics = textPaint.getFontMetrics();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(dp2px(35),dp2px(35));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawNumber(canvas);
    }
    private void drawCircle(Canvas canvas){
        canvas.drawArc(new RectF(3, 3, getMeasuredWidth()-3, getMeasuredHeight()-3), 0, 360, true, CirclePaint);
    }
    private void drawNumber(Canvas canvas){
        if(mChecked){
            canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredHeight()/2-6,backgroundPaint);
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
            int baseLineY = (int) (getMeasuredHeight()/2 - top/2 - bottom/2);//基线中间点的y轴计算公式
            canvas.drawText(String.valueOf(currentNumber),getMeasuredWidth()/2,baseLineY,textPaint);
//            canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,10,selectPaint);
        }
    }
    public void setChecked(boolean isChecked){
        mChecked=isChecked;
    }
    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }
}
