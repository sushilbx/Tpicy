package com.tpicy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bharatpickle.R;
import com.tpicy.adapters.CartAdapter;
import com.tpicy.models.GetCartModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment implements ApiListener {
    String url = Utils.URL + "get_cart";
    RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView tvCartItems,tvCartTotalPrice,tvCartAmount,tvCartTotalAmount,tvCartTotalItems,tvCartPlaceOrder;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CartFragment() {

    }


    public static void newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getParentFragmentManager().setFragmentResultListener("cart", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                boolean result = bundle.getBoolean("result");
                if (result) {
                    getcart();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        sessionManager = new SessionManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerCartlist);
        tvCartItems = view.findViewById(R.id.tvCartItems);
        tvCartTotalItems = view.findViewById(R.id.tvCartTotalItems);
        tvCartTotalPrice = view.findViewById(R.id.tvCartTotalPrice);
        tvCartAmount = view.findViewById(R.id.tvCartAmount);
        tvCartTotalAmount = view.findViewById(R.id.tvCartTotalAmount);
        tvCartPlaceOrder=view.findViewById(R.id.tvCartPlaceOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        tvCartPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PaymentActivity.class);
                startActivity(intent);
            }
        });

        getcart();
        return view;
    }


    public void getcart() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("sushil", response);

                Gson gson = new Gson();

                GetCartModel cartList = gson.fromJson(response, GetCartModel.class);
                if (cartList.status) {


                    CartAdapter adapter = new CartAdapter(getContext(), cartList.data.products, CartFragment.this);
                    recyclerView.setAdapter(adapter);
                }
                if (cartList.status) {
                    String nm = cartList.data.total_item;
                    tvCartItems.setText(nm);
                    tvCartTotalPrice.setText(cartList.data.sub_total);
                    tvCartAmount.setText(cartList.data.sub_total);
                    tvCartTotalAmount.setText(cartList.data.sub_total);
                    tvCartTotalItems.setText(cartList.data.total_item);


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
        MySingleton.myGetMySingleton(getContext()).myAddToRequest(stringRequest);
    }


    @Override
    public void onSuccess(String response) {
        getcart();
    }

    @Override
    public void onFailure(String error) {

    }
}




