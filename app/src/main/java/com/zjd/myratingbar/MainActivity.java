package com.zjd.myratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyRatingBar ratingBar_1,ratingBar_2,ratingBar_3;
    private TextView tv_rating;
    private EditText et_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar_1= (MyRatingBar) findViewById(R.id.ratingBar_1);
        ratingBar_2= (MyRatingBar) findViewById(R.id.ratingBar_2);
        ratingBar_3= (MyRatingBar) findViewById(R.id.ratingBar_3);

        tv_rating= (TextView) findViewById(R.id.tv_rating);
        et_rating= (EditText) findViewById(R.id.et_rating);
    }

    public void onClick(View view) {
        tv_rating.setText(ratingBar_1.getRating()+"\n"+ratingBar_2.getRating()+"\n"+ratingBar_3.getRating());
    }

    public void setRating(View view) {
        ratingBar_1.setRating(Float.valueOf(et_rating.getText().toString().trim()));
        ratingBar_2.setRating(Float.valueOf(et_rating.getText().toString().trim()));
        ratingBar_3.setRating(Float.valueOf(et_rating.getText().toString().trim()));
    }
}
