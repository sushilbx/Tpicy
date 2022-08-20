package com.tpicy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.tpicy.adapters.ImageAdapter;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class DetailsProductActivity extends AppCompatActivity {
    String url = Utils.URL + "get_product_details";
    SessionManager sessionManager;
    RecyclerView recyclerView;
    Context context;

    TextView etDiscription, etPrice;
    MaterialButton mbAddToCart;
    MaterialToolbar mtDetailsProduct;
    ImageView etImage;
    String id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        sessionManager = new SessionManager(this);
        context=this;
        fromXml();
        getProductDetails();
        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.rvProductImages);

//       recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    private void fromXml() {
        mtDetailsProduct = findViewById(R.id.mtDetailsProduct);
        etDiscription = findViewById(R.id.productDetailsDiscription);
        etPrice = findViewById(R.id.productDetailsPrice);
        etImage = findViewById(R.id.productDetailsImage);
        mbAddToCart= findViewById(R.id.mbAddToCart) ;

        mtDetailsProduct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        mbAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.addToCart(context, id, 1, new ApiListener() {
                    @Override
                    public void onSuccess(String response) {
                        mbAddToCart.setText("Added to Cart");
                        mbAddToCart.setOnClickListener(null);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
            }
        });


    }

    private void getProductDetails() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                GetProductDetails model = gson.fromJson(response, GetProductDetails.class);

                if (model.status) {
                    String nm = model.data.name;
                    mtDetailsProduct.setTitle(nm);
                    etDiscription.setText(model.data.description);
                    etPrice.setText(model.data.price);
                    //For Photo Show
                    Glide.with(getApplicationContext())
                            .load(model.data.image)
                            .into(etImage);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    ImageAdapter adapter = new ImageAdapter(context,model.data.images,null);
                    recyclerView.setAdapter(adapter);
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
                params.put("id", id);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(DetailsProductActivity.this).myAddToRequest(stringRequest);
    }
}

