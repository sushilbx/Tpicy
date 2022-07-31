package com.tpicy;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatpickle.R;
import com.google.android.material.appbar.MaterialToolbar;

public class CartActivity extends AppCompatActivity {
    MaterialToolbar mtCartBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mtCartBack = findViewById(R.id.mtCartBack);
        mtCartBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}
