package com.tpicy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bharatpickle.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ReviewActivity extends AppCompatActivity {
    RatingBar ratingBar;
    MaterialToolbar mtAccountBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mtAccountBack= findViewById(R.id.mtAccountBack);

        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(ReviewActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        mtAccountBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();

            }
        });

    }
}