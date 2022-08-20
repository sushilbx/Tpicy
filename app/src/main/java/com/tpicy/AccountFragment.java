package com.tpicy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tpicy.R;
import com.tpicy.models.GetProfileModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url = Utils.URL + "get_profile";
    private String mParam1;
    private String mParam2;
    TextView logOut, tvCartAccount, tvAccountName, tvAccountMobile, tvAccountGmail;
    SessionManager sessionManager;
    TextView tvAccountAddress, tvAccountReview, tvAccountPrivacyPolicy,tvAccountHelp;
    ShapeableImageView ivAccountImage;


    public AccountFragment() {

    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        logOut = view.findViewById(R.id.tvLogoutAccount);
        tvCartAccount = view.findViewById(R.id.tvCartAccount);
        sessionManager = new SessionManager(getContext());
        tvAccountReview = view.findViewById(R.id.tvAccountReview);
        tvAccountName = view.findViewById(R.id.tvAccountName);
        tvAccountMobile = view.findViewById(R.id.tvAccountMobile);
        tvAccountGmail = view.findViewById(R.id.tvAccountGmail);
        ivAccountImage = view.findViewById(R.id.ivAccountImage);
        tvAccountAddress = view.findViewById(R.id.tvAccountAddress);
        tvAccountHelp = view.findViewById(R.id.tvAccountHelp);
        tvAccountPrivacyPolicy = view.findViewById(R.id.tvAccountPrivacyPolicy);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sessionManager.logoutUser();
            }
        });
        ImageView ivMyAccountEditProfile = view.findViewById(R.id.ivMyAccountEditProfile);
        ivMyAccountEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        tvCartAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);

            }
        });
        tvAccountAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
        tvAccountReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });
        tvAccountPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PrivacyPolicyActivity.class);
                startActivity(intent);

            }
        });
        tvAccountHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GmailLoginActivity.class);
                startActivity(intent);

            }
        });
        getData();
        return view;

    }

    private void getData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("sushil", response);
                Gson gson = new Gson();
                GetProfileModel model = gson.fromJson(response, GetProfileModel.class);

                if (model.status) {
                    String nm = model.data.name;
                    tvAccountName.setText(nm);
                    tvAccountGmail.setText(model.data.email);
                    tvAccountMobile.setText(model.data.mobile);

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
}



