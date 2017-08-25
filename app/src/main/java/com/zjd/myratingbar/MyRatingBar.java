package com.zjd.myratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by 左金栋 on 2017/8/24.
 */

public class MyRatingBar extends View {
    private Context context;
    private int width;//设置高
    private int height;//设置高

    private Paint paint;
    //星星总数
    private int starSum=3;
    //当前星星数
    private float rating=3;
    //每步间隔
    private float step=0.1f;
    //每步距离
    private float stepWidth=0;
    //每个星星宽度
    private int starWidth=0;
    //适应宽高度(0:宽度,1:高度)
    private int width_height=0;

    public MyRatingBar(Context context) {
        super(context);
        this.context=context;
        initPaint();
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        this.context=context;
        initPaint();
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initPaint();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        starSum=a.getInt(R.styleable.MyRatingBar_stars,5);
        step=a.getFloat(R.styleable.MyRatingBar_step,1f);
        width_height=a.getInt(R.styleable.MyRatingBar_base_of_width_or_height, 0);
        rating=a.getFloat(R.styleable.MyRatingBar_rating,starSum);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroundStars(canvas);
        drawStars(canvas);
    }

    /**
     * 背景
     * @param canvas
     */
    private void drawBackgroundStars(Canvas canvas) {
        Bitmap bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.icon_star_gray_128)).getBitmap();
        for (int i = 0; i < starSum; i++) {
            canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),new Rect(starWidth*i,0,starWidth*(i+1),starWidth),paint);
        }
    }

    /**
     * 星星
     * @param canvas
     */
    private void drawStars(Canvas canvas) {
        Bitmap bitmap=((BitmapDrawable)getResources().getDrawable(R.drawable.icon_star_yellow_128)).getBitmap();
        stepWidth=step*starWidth;
        int stepNum=(int)(currentX/stepWidth);
        rating=round((double)stepNum*step,2);
        for (int i = 0; i < stepNum*stepWidth/starWidth; i++) {
            int leftX=starWidth*i;
            int rightX=starWidth*(i+1);
            int x=(int)(stepNum*stepWidth-starWidth*i);
            if(currentX>leftX&&currentX<rightX){
                canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth()*x/starWidth,bitmap.getHeight()),new Rect(leftX,0,x+i*starWidth,starWidth),paint);
            }else{
                if(stepNum*stepWidth/starWidth<i+1){
                    canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth()*x/starWidth,bitmap.getHeight()),new Rect(leftX,0,x+i*starWidth,starWidth),paint);
                }else {
                    canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),new Rect(leftX,0,rightX,starWidth),paint);
                }
            }
        }
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return (float) b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getMeasuredWidth(), widthMeasureSpec);// 获得控件的宽度
        height = getDefaultSize(getMeasuredHeight(), heightMeasureSpec);//获得控件的高度
        if(width_height==0){
            starWidth=width/starSum;
            setMeasuredDimension(width, starWidth);//设置宽和高
        }else if(width_height==1){
            starWidth=height;
            setMeasuredDimension(starWidth*starSum, starWidth);//设置宽和高
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
        currentX=width*rating/starSum;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint=new Paint();
        paint.setAntiAlias(true);
    }

    private float currentX=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentX=event.getX()+stepWidth/2;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                if(event.getX()>=0&&event.getX()<=width){
                    currentX=event.getX()+stepWidth/2;
                }else if(event.getX()<0){
                    currentX=stepWidth/2;
                }else if(event.getX()>width){
                    currentX=width+stepWidth/2;
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public int getStarSum() {
        return starSum;
    }

    public void setStarSum(int starSum) {
        this.starSum = starSum;
        invalidate();
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        invalidate();
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
        invalidate();
    }
}
