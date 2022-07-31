package com.tpicy;

public interface ApiListener {
    void onSuccess(String response);
    void onFailure(String error);
}
