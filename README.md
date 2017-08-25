# MyRatingBar
  解决官方RatingBar无法自由调整宽高的问题<br>
  可选则适应宽度或者高度
  
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
    
    
    根布局添加 xmlns:app="http://schemas.android.com/apk/res-auto"<br><br>
    
    <com.zjd.myratingbar.MyRatingBar
        app:base_of_width_or_height="width"
        app:stars="15"
        app:step="1"
        app:rating="10"
        android:layout_gravity="center"
        android:layout_width="300dp"
        android:layout_height="30dp" />
    
