package com.tpicy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tpicy.models.SessionModel;

public class SessionManager {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ROLE = "role";
    public static final String KEY_STATUS = "status";

    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_UPDATED_AT = "updated_at";


    public static final String KEY_API_SECRET = "api_secret";
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_API_TOKEN = "api_token";


    public static final String KEY_LAST_MESSAGE_ID = "lastmessageid";
    public static final String KEY_FIRST_TIME = "firsttime";
    private static final String PREF_NAME = "userData";
    private static final String IS_LOGIN = "isLogin";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    private static SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLastMessageID(String lastMessageID) {
        editor.putString(KEY_LAST_MESSAGE_ID, lastMessageID);
        editor.commit();
    }


   public void createLoginSession(SessionModel sessionModel) {
        editor.putString(KEY_LAST_MESSAGE_ID, "");
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(KEY_FIRST_TIME, true);

        editor.putString(KEY_ID, sessionModel.getId());
        editor.putString(KEY_NAME, sessionModel.getName());
        editor.putString(KEY_PHONE, sessionModel.getMobile());
        editor.putString(KEY_EMAIL, sessionModel.getEmail());
        editor.putString(KEY_GENDER, sessionModel.getGender());
        editor.putString(KEY_ROLE, sessionModel.getRole());
        editor.putString(KEY_STATUS, sessionModel.getStatus());

        editor.putString(KEY_API_SECRET, sessionModel.getApi_secret());
        editor.putString(KEY_API_KEY, sessionModel.getApi_key());
        //  editor.putString(KEY_API_TOKEN, sessionModel.getApi_token());


        editor.putString(KEY_LAST_MESSAGE_ID, sessionModel.getName());

        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();

    }

   /* public void updateSession(String id, String name, String mobile, String email, String dob, String birthPlace, String birthLat, String birthLng, String birthTime, String address, String gender, String martialStatus, String occupation, String qualification) {
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_BIRTH_PLACE, birthPlace);
        editor.putString(KEY_BIRTH_LAT, birthLat);
        editor.putString(KEY_BIRTH_LNG, birthLng);
        editor.putString(KEY_BIRTH_TIME, birthTime);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_MARTIAL_STATUS, martialStatus);
        editor.putString(KEY_OCCUPATION, occupation);
        editor.putString(KEY_QUALIFICATION, qualification);
        editor.commit();
    }*/


    public void logoutUser() {
       clearSession();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getId() {
        return pref.getString(KEY_ID, null);
    }

    public String getName() {
        return pref.getString(KEY_NAME, null);
    }

    public void setName(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }


    public String getRole() {
        return pref.getString(KEY_ROLE, null);
    }

    public String getMobile() {
        return pref.getString(KEY_PHONE, null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }


    public String geStatus() {
        return pref.getString(KEY_STATUS, null);
    }

    public String getApiSecret() {
        return pref.getString(KEY_API_SECRET, null);
    }

    public String getApiKey() {
        return pref.getString(KEY_API_KEY, null);
    }

    public String getApiToken() {
        return pref.getString(KEY_API_TOKEN, null);
    }

    public String getLastMessageId() {
        return pref.getString(KEY_LAST_MESSAGE_ID, null);
    }


    public Boolean getFirstTime() {
        return pref.getBoolean(KEY_FIRST_TIME, false);
    }

    public void setFirstTime(Boolean firstTime) {
        editor.putBoolean(KEY_FIRST_TIME, firstTime);
        editor.commit();
    }

    public boolean getFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void getSessionData(String key){

    }

}
