package com.tpicy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bharatpickle.R;
import com.tpicy.models.ImageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ZoomActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String url = Utils.URL + "get_image";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        Intent intent = new Intent();
        String value = intent.getStringExtra("data");

        Gson gson = new Gson();
        String jsonOutput = value;
        Type listType = new TypeToken<List<ImageModel>>(){}.getType();
        List<ImageModel> images = gson.fromJson(jsonOutput, listType);

    }
}