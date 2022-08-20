package com.tpicy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tpicy.models.AddressModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateAddressActivity extends AppCompatActivity {

    String updateUrl = Utils.URL + "update_address";
    TextInputEditText tietUpdateAddressName;
    TextInputEditText tietUpdateAddressHome;
    TextInputEditText tietUpdateAddressDistrict;
    TextInputEditText tietUpdateAddressState;
    TextInputEditText tietUpdateAddressPinCode;
    TextInputEditText tietUpdateAddressMobile;
    MaterialButton mbUpdateAddressSave;
    Context context;
    String name = "";
    String house = "";
    String district = "";
    String state = "";
    String pin_code = "";
    String mobile = "";
    String id = "";
    AddressModel model;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        //receive data
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");


        // json/text to class
        Gson gson = new Gson();
        model = gson.fromJson(data, AddressModel.class);
        id = model.id;


        context = UpdateAddressActivity.this;
        sessionManager = new SessionManager(context);
        fromXml();
        setData();
        listener();
    }

    private void setData() {
        tietUpdateAddressName.setText(model.name);
        tietUpdateAddressHome.setText(model.house);
        tietUpdateAddressDistrict.setText(model.district);
        tietUpdateAddressState.setText(model.state);
        tietUpdateAddressPinCode.setText(model.pin_code);
        tietUpdateAddressMobile.setText(model.mobile);
    }


    private void fromXml() {
        tietUpdateAddressName = findViewById(R.id.tietUpdateAddressName);
        tietUpdateAddressHome = findViewById(R.id.tietUpdateAddressHome);
        tietUpdateAddressDistrict = findViewById(R.id.tietUpdateAddressDistrict);
        tietUpdateAddressState = findViewById(R.id.tietUpdateAddressState);
        tietUpdateAddressPinCode = findViewById(R.id.tietUpdateAddressPinCode);
        tietUpdateAddressMobile = findViewById(R.id.tietUpdateAddressMobile);
        mbUpdateAddressSave = findViewById(R.id.mbUpdateAddressSave);
    }


    private void listener() {
        mbUpdateAddressSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    update();
                }
            }
        });
    }

    boolean checkForm() {
        name = tietUpdateAddressName.getText().toString().trim();
        house = tietUpdateAddressHome.getText().toString().trim();
        district = tietUpdateAddressDistrict.getText().toString().trim();
        state = tietUpdateAddressState.getText().toString().trim();
        pin_code = tietUpdateAddressPinCode.getText().toString().trim();
        mobile = tietUpdateAddressMobile.getText().toString().trim();

        if (name.isEmpty()) {
            tietUpdateAddressName.setError("Name is empty");
            tietUpdateAddressName.requestFocus();
            return false;
        } else if (name.length() < 4) {
            tietUpdateAddressName.setError("Name should be minimum 4 characters");
            tietUpdateAddressName.requestFocus();
            return false;
        }
        if (house.isEmpty()) {
            Toast.makeText(this, "Enter Home", Toast.LENGTH_SHORT).show();
            tietUpdateAddressHome.setFocusableInTouchMode(true);
            tietUpdateAddressHome.requestFocus();
            return false;
        } else if (house.length() < 4) {
            Toast.makeText(this, "address should be minimum 4 characters", Toast.LENGTH_SHORT).show();
            tietUpdateAddressHome.setFocusableInTouchMode(true);
            tietUpdateAddressHome.requestFocus();
            return false;
        }
        if (district.isEmpty()) {
            Toast.makeText(this, "Enter District", Toast.LENGTH_SHORT).show();
            tietUpdateAddressDistrict.setFocusableInTouchMode(true);
            tietUpdateAddressDistrict.requestFocus();
            return false;
        } else if (district.length() < 4) {
            Toast.makeText(this, "district should be minimum 3 characters", Toast.LENGTH_SHORT).show();
            tietUpdateAddressDistrict.setFocusableInTouchMode(true);
            tietUpdateAddressDistrict.requestFocus();
            return false;
        }
        if (state.isEmpty()) {
            Toast.makeText(this, "Enter State", Toast.LENGTH_SHORT).show();
            tietUpdateAddressState.setFocusableInTouchMode(true);
            tietUpdateAddressState.requestFocus();
            return false;
        } else if (state.length() < 3) {
            Toast.makeText(this, "state should be minimum 3 characters", Toast.LENGTH_SHORT).show();
            tietUpdateAddressState.setFocusableInTouchMode(true);
            tietUpdateAddressState.requestFocus();
            return false;
        }
        if (pin_code.isEmpty()) {
            Toast.makeText(this, "Enter Pin Code", Toast.LENGTH_SHORT).show();
            tietUpdateAddressPinCode.setFocusableInTouchMode(true);
            tietUpdateAddressPinCode.requestFocus();
            return false;
        } else if (pin_code.length() < 4) {
            Toast.makeText(this, "Pin Code should be minimum 6 characters", Toast.LENGTH_SHORT).show();
            tietUpdateAddressPinCode.setFocusableInTouchMode(true);
            tietUpdateAddressPinCode.requestFocus();
            return false;
        }

        if (mobile.isEmpty()) {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show();
            tietUpdateAddressMobile.setFocusableInTouchMode(true);
            tietUpdateAddressMobile.requestFocus();
            return false;
        } else if (!Utils.myMobileValid(mobile)) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            tietUpdateAddressMobile.setFocusableInTouchMode(true);
            tietUpdateAddressMobile.requestFocus();
            return false;
        }
        return true;
    }

    void update() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "Processing...", false, false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(UpdateAddressActivity.this,AddressActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();

                Toast.makeText(UpdateAddressActivity.this, " Please try again.", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUrl, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", sessionManager.getApiKey());
                params.put("api_secret", sessionManager.getApiSecret());
                params.put("id", id);
                params.put("name", name);
                params.put("house", house);
                params.put("district", district);
                params.put("pin_code", pin_code);
                params.put("mobile", mobile);
                params.put("state", state);

                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

}