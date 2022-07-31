package com.tpicy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bharatpickle.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    TextInputEditText tietAddAddressName;
    TextInputEditText tietAddAddressHome;
    TextInputEditText tietAddAddressDistrict;
    TextInputEditText tietAddAddressState;
    TextInputEditText tietAddAddressPinCode;
    TextInputEditText tietAddAddressMobile;
    MaterialButton mbAddAddressSave;
    String url = Utils.URL + "add_address";
    String name = "";
    String house = "";
    String district = "";
    String state = "";
    String pin_code="";
    String mobile="";
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        sessionManager = new SessionManager(AddAddressActivity.this);

        fromXml();
        mbAddAddressSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkForm()) {
                    addAddress();
                }
            }
        });

    }

    private void fromXml() {
        tietAddAddressName = findViewById(R.id.tietAddAddressName);
        tietAddAddressHome = findViewById(R.id.tietAddAddressHome);
        tietAddAddressDistrict = findViewById(R.id.tietAddAddressDistrict);
        tietAddAddressState = findViewById(R.id.tietAddAddressState);
        tietAddAddressPinCode = findViewById(R.id.tietAddAddressPinCode);
        tietAddAddressMobile = findViewById(R.id.tietAddAddressMobile);
        mbAddAddressSave = findViewById(R.id.mbAddAddressSave);
    }

    boolean checkForm() {
        name = tietAddAddressName.getText().toString().trim();
        house = tietAddAddressHome.getText().toString().trim();
        district = tietAddAddressDistrict.getText().toString().trim();
        state = tietAddAddressState.getText().toString().trim();
        pin_code = tietAddAddressPinCode.getText().toString().trim();
        mobile = tietAddAddressMobile.getText().toString().trim();

        if (name.isEmpty()) {
            tietAddAddressName.setError("Name is empty");
            tietAddAddressName.requestFocus();
            return false;
        } else if (name.length() < 4) {
            tietAddAddressName.setError("Name should be minimum 4 characters");
            tietAddAddressName.requestFocus();
            return false;
        }
        if (house.isEmpty()) {
            Toast.makeText(this, "Enter Home", Toast.LENGTH_SHORT).show();
            tietAddAddressHome.setFocusableInTouchMode(true);
            tietAddAddressHome.requestFocus();
            return false;
        } else if (house.length() < 4){
            Toast.makeText(this, "address should be minimum 4 characters", Toast.LENGTH_SHORT).show();
            tietAddAddressHome.setFocusableInTouchMode(true);
            tietAddAddressHome.requestFocus();
            return false;
        }
        if (district.isEmpty()) {
            Toast.makeText(this, "Enter District", Toast.LENGTH_SHORT).show();
            tietAddAddressDistrict.setFocusableInTouchMode(true);
            tietAddAddressDistrict.requestFocus();
            return false;
        } else if (district.length() < 4){
            Toast.makeText(this, "district should be minimum 3 characters", Toast.LENGTH_SHORT).show();
            tietAddAddressDistrict.setFocusableInTouchMode(true);
            tietAddAddressDistrict.requestFocus();
            return false;
        }
        if (state.isEmpty()) {
            Toast.makeText(this, "Enter State", Toast.LENGTH_SHORT).show();
            tietAddAddressState.setFocusableInTouchMode(true);
            tietAddAddressState.requestFocus();
            return false;
        } else if (state.length() < 3){
            Toast.makeText(this, "state should be minimum 3 characters", Toast.LENGTH_SHORT).show();
            tietAddAddressState.setFocusableInTouchMode(true);
            tietAddAddressState.requestFocus();
            return false;
        }
        if (pin_code.isEmpty()) {
            Toast.makeText(this, "Enter Pin Code", Toast.LENGTH_SHORT).show();
            tietAddAddressPinCode.setFocusableInTouchMode(true);
            tietAddAddressPinCode.requestFocus();
            return false;
        } else if (pin_code.length() < 4){
            Toast.makeText(this, "Pin Code should be minimum 6 characters", Toast.LENGTH_SHORT).show();
            tietAddAddressPinCode.setFocusableInTouchMode(true);
            tietAddAddressPinCode.requestFocus();
            return false;
        }

        if (mobile.isEmpty()) {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show();
            tietAddAddressMobile.setFocusableInTouchMode(true);
            tietAddAddressMobile.requestFocus();
            return false;
        } else if (!Utils.myMobileValid(mobile)) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            tietAddAddressMobile.setFocusableInTouchMode(true);
            tietAddAddressMobile.requestFocus();
            return false;
        }
        return true;
    }

    void addAddress() {
        final ProgressDialog progressDialog = ProgressDialog.show(AddAddressActivity.this, null, "Processing...", false, false);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        Intent intent = new Intent(AddAddressActivity.this,AddressActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AddAddressActivity.this, "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(AddAddressActivity.this, " Please try again.", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("house", house);
                params.put("district", district);
                params.put("state",state);
                params.put("pin_code", pin_code);
                params.put("mobile",mobile);
                params.put("api_key", sessionManager.getApiKey());
                params.put("api_secret", sessionManager.getApiSecret());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(AddAddressActivity.this).myAddToRequest(stringRequest);
    }
}
