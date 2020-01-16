package com.young.xmlnamespace;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Paint paint;
    private Rect bound;
    private String text;
    private int textColor;
    private int textSize;
    private int width;
    private int height;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomView);
        int n = typedArray.getIndexCount();
        for(int i=0;i<n;i++){
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.CustomView_Text:
                    text = typedArray.getString(index);
                    break;
                case R.styleable.CustomView_TextColor:
                    textColor = typedArray.getColor(index,Color.BLACK);
                    break;
                case R.styleable.CustomView_TextSize:
                    textSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,23,getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        paint = new Paint();
        bound = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text,0,text.length(),bound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
        paint.setColor(textColor);
        canvas.drawText(text,getMeasuredWidth()/2-bound.width()/2,getMeasuredHeight()/2+bound.height()/2,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            int desired = getPaddingLeft()+bound.width()+getPaddingRight();
            width = desired;
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            int desired = getPaddingTop()+bound.height()+getPaddingBottom();
            height = desired;
        }
        setMeasuredDimension(width,height);
    }
}
