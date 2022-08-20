package com.tpicy;

import static com.tpicy.Utils.URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.tpicy.adapters.AddressAdapter;
import com.tpicy.databinding.ActivityAddressBinding;
import com.tpicy.models.AddressModel;
import com.tpicy.models.GetAddressModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    ArrayList<AddressModel> arrayAddress = new ArrayList<>();
    RecyclerView recyclerView;
    String url = "http://ottego.com/pickle/pickle/address_list";
    SessionManager sessionManager;
    ActivityAddressBinding b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddressBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.rcViewAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        b.mtAddressBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        b.tvAddressAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
        getAddress();
    }
    public void getAddress() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                Gson gson = new Gson();
                GetAddressModel address = gson.fromJson(response, GetAddressModel.class);
                if (address.status) {
                    AddressAdapter adapter = new AddressAdapter(AddressModel.ADDRESS_COMPARATOR);
                    b.rcViewAddress.setAdapter(adapter);
                    adapter.submitList(address.data);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api_key", sessionManager.getApiKey());
                params.put("api_secret", sessionManager.getApiSecret());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(AddressActivity.this).myAddToRequest(stringRequest);
    }
}

