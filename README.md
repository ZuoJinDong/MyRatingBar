# MyRatingBar
  解决官方RatingBar无法自由调整宽高的问题<br>
  可选则适应宽度或者高度
  
  [我的简书](http://www.jianshu.com/p/22b71023c5f6)  
  
  ![MyRatingBar.gif](http://upload-images.jianshu.io/upload_images/7584515-e3b9c7013d856b4e.gif?imageMogr2/auto-orient/strip)
  
  ##只设置了常用的几个属性
    
  base_of_width_or_height 以宽度为准或已高度为准<br>
  step 滑动间距<br>
  rating 当前星值<br>
  stars 星星总数

    <declare-styleable name="MyRatingBar">
        <attr name="base_of_width_or_height" format="enum">
            <enum name="width" value="0"/>
            <enum name="height" value="1"/>
        </attr>
        <attr name="step" format="float"/>
        <attr name="rating" format="float"/>
        <attr name="stars" format="integer"/>
    </declare-styleable>
    
    
    根布局添加 xmlns:app="http://schemas.android.com/apk/res-auto"<br>
    
    <com.zjd.myratingbar.MyRatingBar
        app:base_of_width_or_height="width"
        app:stars="15"
        app:step="1"
        app:rating="10"
        android:layout_gravity="center"
        android:layout_width="300dp"
        android:layout_height="30dp" />

核心部分 (根据手指在X轴的滑动画Bitmap)<br>
    
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
