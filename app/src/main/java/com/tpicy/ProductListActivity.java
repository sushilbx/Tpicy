package com.tpicy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tpicy.adapters.ProductAdapter;
import com.tpicy.models.GetProductModel;
import com.tpicy.models.ProductModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {
    ArrayList<ProductModel> arrayProduct = new ArrayList<>();
    String url =  Utils.URL +"product_list";
    RecyclerView recyclerView;
    SessionManager sessionManager;
    MaterialToolbar mtProductListBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        sessionManager = new SessionManager(this);
        recyclerView = findViewById(R.id.recyclerProductList);
        mtProductListBack = findViewById(R.id.mtProductListBack);

        mtProductListBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();

            }
        });

//       recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL ));
        getProduct();
    }

    private void getProduct() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                Gson gson = new Gson();

                GetProductModel productList = gson.fromJson(response, GetProductModel.class);
                if (productList.status) {

                    ProductAdapter adapter = new ProductAdapter(ProductListActivity.this, productList.data, new ItemListener() {
                        @Override
                        public void onSelect(String id) {

                                Intent myIntent = new Intent(ProductListActivity.this, DetailsProductActivity.class);
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
        MySingleton.myGetMySingleton(ProductListActivity.this).myAddToRequest(stringRequest);
    }
}
