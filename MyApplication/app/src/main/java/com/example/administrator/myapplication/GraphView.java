package com.example.administrator.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-26.
 * 원본: http://gyjmobile.tistory.com/entry/%EC%B4%88%EA%B0%84%EB%8B%A8-%EA%BA%BD%EC%9D%80%EC%84%A0-%EA%B7%B8%EB%9E%98%ED%94%84-%EB%A7%8C%EB%93%A4%EA%B8%B0
 *
 * 간단한 꺾은선 그래프 그리기
 * @author Choi Jaeung(darker826, darker826@gmail.com)
 * 2016-08-04
 */
public class GraphView extends View {

    private Paint mPointPaint;

    private float mThickness, axisXThickness;
    private int mPointSize, mPointRadius;
    private int mLineColor, axisXColor;
    private int mSpace;

    private List<Integer> mPoints;

    //생성자
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("함수", "생성자 실행");
        setTypes(context, attrs);

        mPoints = new ArrayList<Integer>();
    }

    //간격 설정
    public void setSpace(int mSpace){
        this.mSpace = mSpace;
    }

    //x축 생성
    private void drawAxisX(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(axisXThickness);
        paint.setColor(axisXColor);
        for (int i = mSpace; i < getWidth(); i += mSpace) {
            canvas.drawLine(i, (getHeight() / 2) - 10, i, (getHeight() / 2) + 10, paint);
        }
        canvas.drawLine(0, (getHeight() / 2), getWidth(), (getHeight() / 2), paint);
    }

    //그래프 값을 추가한다. 이 함수가 실행되면 onDraw()가 다시 실행됨.
    public void setPointY(int point) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = params.width+mSpace;
        setLayoutParams(params);
        mPoints.add(point);
        invalidate();
    }

    private void drawGraph(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(mThickness);
        paint.setColor(mLineColor);
        int mSpaceY = (getHeight()/2)/380;
        //선을 그린다.
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {
                canvas.drawLine(0, getHeight() / 2, (i + 1) * mSpace, getHeight() / 2 - mPoints.get(i) * mSpaceY, paint);
            } else {
                canvas.drawLine(i * mSpace, getHeight() / 2 - (mPoints.get(i - 1) * mSpaceY), (i + 1) * mSpace, getHeight() / 2 - (mPoints.get(i) * mSpaceY), paint);
            }
        }

        //점을 찍는다.
        for (int i = 0; i < mPoints.size(); i++) {
            canvas.drawCircle((i + 1) * mSpace, getHeight() / 2 - mPoints.get(i) * mSpaceY, mPointRadius, mPointPaint);
        }
    }

    //그래프 옵션을 받는다
    private void setTypes(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.GraphView);

        mPointPaint = new Paint();
        mPointPaint.setColor(types.getColor(R.styleable.GraphView_pointColor, Color.BLACK));
        mPointSize = (int) types.getDimension(R.styleable.GraphView_pointSize, 10);
        mPointRadius = mPointSize / 2;

        mLineColor = types.getColor(R.styleable.GraphView_lineColor, Color.BLACK);
        mThickness = types.getDimension(R.styleable.GraphView_lineThickness, 1);

        axisXColor = types.getColor(R.styleable.GraphView_axisXColor, Color.BLACK);
        axisXThickness = types.getDimension(R.styleable.GraphView_axisXThickness, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //x축을 먼저 그림
        drawAxisX(canvas);
        //그래프를 그림
        if(mPoints.size() > 0){
            drawGraph(canvas);
        }
    }

}
