package com.tpicy;

import android.app.ProgressDialog;
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
import com.bharatpickle.R;
import com.tpicy.models.SessionModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText tietSignupFullName;
    TextInputEditText tietSignupEmail;
    TextInputEditText tietSignupMob;
    TextInputEditText tietSignupPassword;
    MaterialButton mbSignupSubmit;
    String url = Utils.URL + "signup";
    String enterfullname = "";
    String email = "";
    String mobile = "";
    String password = "";


    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fromXml();

        sessionManager = new SessionManager(SignupActivity.this);

        mbSignupSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (checkForm()) {
                    signup();
                }
            }
        });
    }

    private void fromXml() {
        tietSignupFullName = findViewById(R.id.tietSignupFullName);
        tietSignupEmail = findViewById(R.id.tietSignupEmail);
        tietSignupMob = findViewById(R.id.tietSignupMob);
        tietSignupPassword = findViewById(R.id.tietSignuppPassword);
        mbSignupSubmit = findViewById(R.id.mbSignupSubmit);
    }

    boolean checkForm() {
        enterfullname = tietSignupFullName.getText().toString().trim();
        email = tietSignupEmail.getText().toString().trim();
        mobile = tietSignupMob.getText().toString().trim();
        password = tietSignupPassword.getText().toString().trim();

        if (enterfullname.isEmpty()) {
            tietSignupFullName.setError("Name is empty");
            tietSignupFullName.requestFocus();
            return false;
        } else if (enterfullname.length() < 4) {
            tietSignupFullName.setError("Name should be minimum 4 characters");
            tietSignupFullName.requestFocus();
            return false;
        }

        if (mobile.isEmpty()) {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show();
            tietSignupMob.setFocusableInTouchMode(true);
            tietSignupMob.requestFocus();
            return false;
        } else if (!Utils.myMobileValid(mobile)) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            tietSignupMob.setFocusableInTouchMode(true);
            tietSignupMob.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            tietSignupEmail.setFocusableInTouchMode(true);
            tietSignupEmail.requestFocus();
            return false;
        } else if (!Utils.myEmailValid(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            tietSignupEmail.setFocusableInTouchMode(true);
            tietSignupEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            tietSignupPassword.setError("Password is empty");
            tietSignupPassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            tietSignupPassword.setError("Password should be minimum 6 characters");
            tietSignupPassword.requestFocus();
            return false;
        }

        return true;
    }

    void signup() {
        final ProgressDialog progressDialog = ProgressDialog.show(SignupActivity.this, null, "Processing...", false, false);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONObject jsonObj = new JSONObject(jsonObject.getString("data"));

                        Gson gson = new Gson();
                       SessionModel sessionModel = gson.fromJson(String.valueOf(jsonObj), SessionModel.class);
                       sessionManager.createLoginSession(sessionModel);

                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(SignupActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Sorry, something went wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(SignupActivity.this, " Please try again.", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", enterfullname);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("status", "1");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(SignupActivity.this).myAddToRequest(stringRequest);
    }
}
