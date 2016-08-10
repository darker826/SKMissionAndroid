package com.example.administrator.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 그래프의 Y축 그리기
 * @author Choi Jaeung(darker826, darker826@gmail.com)
 * 2016-08-04
 */
public class AxisYView extends View {
    private Paint mPointPaint;
    private float mThickness;
    private int mLineColor;
    private int mSpace;

    public AxisYView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOption(context, attrs);
    }
    public void setSpace(int mSpace){
        this.mSpace = mSpace;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mSpace != 0){
            drawAxisY(canvas);
        }
    }

    //컬러랑 두께 설정
    public void setOption(Context context, AttributeSet attrs){
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.GraphAxisY);
        mPointPaint = new Paint();

        mLineColor = types.getColor(R.styleable.GraphAxisY_AxisYLineColor, Color.BLACK);
        mThickness = types.getDimension(R.styleable.GraphAxisY_AxisYLineThickness, 1);
    }

    //Y축을 그린다.
    private void drawAxisY(Canvas canvas) {
        mPointPaint.setStrokeWidth(mThickness);
        mPointPaint.setColor(mLineColor);
        mSpace = (getHeight()/2)/38;
        //숫자
        for(int i = 0; i < getHeight() / 2; i += mSpace){
            if(i == 0) {
                canvas.drawLine(0, getHeight() / 2, 20, getHeight() / 2, mPointPaint);
            }else{
                canvas.drawLine(0, (getHeight() / 2) + i, 20, (getHeight() / 2) + i, mPointPaint);
                canvas.drawLine(0, (getHeight() / 2) - i, 20, (getHeight() / 2) - i, mPointPaint);
            }
        }
        //축
        canvas.drawLine(10, 0, 10, getHeight(), mPointPaint);
    }
}
