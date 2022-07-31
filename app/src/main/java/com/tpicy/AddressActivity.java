package com.tpicy;

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
import com.bharatpickle.R;
import com.tpicy.adapters.AddressAdapter;
import com.tpicy.models.AddressModel;
import com.tpicy.models.GetAddressModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    ArrayList<AddressModel> arrayAddress = new ArrayList<>();
    RecyclerView recyclerView;
    String url = "http://ottego.com/pickle/pickle/address_list";
    SessionManager sessionManager;
    MaterialToolbar mtAddressBack;

    TextView tvAddressName, tvAddressVill, tvAddressDistrict, tvAddressState, tvAddressPin, tvAddressMobile,tvAddressAddAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address);
        sessionManager = new SessionManager(this);
        tvAddressName = findViewById(R.id.tvAddressName);
        tvAddressVill = findViewById(R.id.tvAddressVill);
        tvAddressDistrict = findViewById(R.id.tvAddressDistrict);
        tvAddressState = findViewById(R.id.tvAddressState);
        tvAddressPin = findViewById(R.id.tvAddressPin);
        mtAddressBack = findViewById(R.id.mtAddressBack);
        tvAddressAddAddress = findViewById(R.id.tvAddressAddAddress);
        tvAddressMobile = findViewById(R.id.tvAddressMobile);
        recyclerView = findViewById(R.id.rcViewAddress);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mtAddressBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();

            }
        });
        tvAddressAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
        getAddress();

    }


    public void getAddress (){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                Gson gson = new Gson();
                GetAddressModel addressList = gson.fromJson(response, GetAddressModel.class);
                if (addressList.status) {

                    AddressAdapter adapter = new AddressAdapter(AddressActivity.this, addressList.data, new ItemListener() {
                        @Override
                        public void onSelect(String id) {

                            Intent myIntent = new Intent(AddressActivity.this, DetailsProductActivity.class);
                            startActivity(myIntent);
                        }
                    });
                    recyclerView.setAdapter(adapter);

                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {error.printStackTrace();}

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

