package com.tpicy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tpicy.models.GetProfileModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    String url = Utils.URL + "get_profile";
    String updateUrl = Utils.URL + "update_profile";
    MaterialButton mbProfileSave;
    MaterialToolbar mtProfile;
    Context context;
    SessionManager sessionManager;
    int gender=1;




    TextInputEditText etName,etMobileNo,etEmail;
    RadioGroup radioGroup;
    RadioButton rdMale, rdFemale;
    TextView tvProfileDob;
    String enterfullname = "";
    String email = "";
    String dob = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = ProfileActivity.this;
        sessionManager = new SessionManager(context);
        mtProfile = findViewById(R.id.mtProfile);
        mtProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fromXml();
        listener();
        getData();

        setRadioListener();
    }


    private void fromXml() {
        etName = findViewById(R.id.tietProfileName);
        etMobileNo = findViewById(R.id.tietProfileMobileNo);
        etEmail = findViewById(R.id.tietProfileEmail);
        mbProfileSave = findViewById(R.id.mbProfileSave);
        radioGroup = findViewById(R.id.radio_group);
        rdMale = findViewById(R.id.mrbProfileMale);
        rdFemale = findViewById(R.id.mrbProfileFemale);
        tvProfileDob = findViewById(R.id.tvProfileDob);
        mtProfile=findViewById(R.id.mtProfile);
    }

    boolean checkForm() {

        enterfullname = etName.getText().toString().trim();

        email = etEmail.getText().toString().trim();

        if (enterfullname.isEmpty()) {
            etName.setError("Name is empty");
            etName.requestFocus();
            return false;
        } else if (enterfullname.length() < 4) {
            etName.setError("Name should be minimum 4 characters");
            etName.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            return false;
        } else if (!Utils.myEmailValid(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            etEmail.setFocusableInTouchMode(true);
            etEmail.requestFocus();
            return false;
        }

        return true;
    }


    private void listener() {

        tvProfileDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        mbProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    update();
                }
            }
        });
        mtProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void chooseDate() {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvProfileDob.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                dob = year + "-" + monthOfYear + "-" + dayOfMonth;

            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("sushil",response);
                Gson gson = new Gson();
                GetProfileModel model = gson.fromJson(response, GetProfileModel.class);

                if (model.status) {
                    String nm = model.data.name;
                    etName.setText(nm);
                    etEmail.setText(model.data.email);
                    etMobileNo.setText(model.data.mobile);
                    tvProfileDob.setText(model.data.dob);
                   switch (model.data.gender){
                       case 1 : {
                           rdMale.setChecked(true);
                           break;
                       }
                       case 2: {
                           rdFemale.setChecked(true);
                           break;
                       }
                   }

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
        MySingleton.myGetMySingleton(ProfileActivity.this).myAddToRequest(stringRequest);
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
                        onBackPressed();

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
                Toast.makeText(ProfileActivity.this, " Please try again.", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUrl, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", sessionManager.getApiKey());
                params.put("api_secret", sessionManager.getApiSecret());
                params.put("name", enterfullname);
                params.put("email", email);
                params.put("dob", dob);
                params.put("gender", String.valueOf(gender));

                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
    public void setRadioListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(i);
                switch(checkedRadioButton.getId()) {
                    case R.id.mrbProfileMale:
                        gender = 1;
                        break;
                    case R.id.mrbProfileFemale:
                        gender = 2;
                        break;
                }
            }
        });
    }
}