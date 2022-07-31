package com.tpicy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bharatpickle.R;
import com.google.android.material.appbar.MaterialToolbar;

public class NotificationActivity extends AppCompatActivity {
    MaterialToolbar mtNotificationBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mtNotificationBack = findViewById(R.id.mtNotificationBack);
        mtNotificationBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}