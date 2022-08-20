package com.tpicy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static final String URL = "http://ottego.com/pickle/pickle/";


    public final static boolean myEmailValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean myMobileValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static void addToCart(Context context, String productId, int quantity, ApiListener listener) {
        SessionManager sessionManager = new SessionManager(context);

        String url = URL + "add_cart";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                listener.onSuccess(response);
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();

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
                params.put("id", productId);
                params.put("quantity", String.valueOf(quantity));
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
//   // public static void addToAddress(Context context, String name, String house,String district,String state,String pin_code,String mobile, ApiListener listener) {
//        SessionManager sessionManager = new SessionManager(context);
//
//        String url = URL + "add_address";
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("response", response);
//                listener.onSuccess(response);
//                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        };
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("api_key", sessionManager.getApiKey());
//                params.put("api_secret", sessionManager.getApiSecret());
//                params.put("name", name);
//                params.put("house", house);
//                params.put("district",district);
//                params.put("state",state);
//                params.put("pin_code",pin_code);
//                params.put("mobile",mobile);
//
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
//    }

    public static void removeCart(Context context, String cart_item_id, ApiListener listener) {
        SessionManager sessionManager = new SessionManager(context);

        String url = URL + "remove_cart";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                listener.onSuccess(response);
                Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();

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
                Log.e("sushil","cart_item_id: "+cart_item_id+"api_key: "+sessionManager.getApiKey());
                Map<String, String> params = new HashMap<>();
                params.put("api_key", sessionManager.getApiKey());
                params.put("api_secret", sessionManager.getApiSecret());
                params.put("id", cart_item_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    public static void removeAddress(Context context, String addresses_id, ApiListener listener) {
        SessionManager sessionManager = new SessionManager(context);

        String url = URL + "remove_address";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                listener.onSuccess(response);
                Toast.makeText(context, "Removed Successfully", Toast.LENGTH_SHORT).show();

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
                params.put("id", addresses_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}