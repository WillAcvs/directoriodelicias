package com.directoriodelicias.apps.delicias.network.api_request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class LoginRequest extends StringRequest {

    public LoginRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public LoginRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
