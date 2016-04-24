package com.example.personal.himachat.network;

import android.content.Context;
import android.util.Log;

import com.example.personal.himachat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by maaakbar on 4/24/16.
 */
public class NetHelper {

    private static final String TAG = "NetHelper";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static String getWebDomain(Context context){
        return "http://"+context.getString(R.string.domain_url)+":3000/api";
    }
    public static String getMqttDomain(Context context){
        return "tcp://"+context.getString(R.string.domain_url)+":1883";
    }

    public static void doLogin(Context context, String username, String password,
                               AsyncHttpResponseHandler responseHandler){
        String url = getWebDomain(context)+"/login";

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        Log.i(TAG, "doLogin: "+url);

        client.post(url, params, responseHandler);
    }

    public static void doRegister(Context context,String fullname, String email, String username,
                                  String password, boolean gender, AsyncHttpResponseHandler eventListener) {
        // register url
        String url = getWebDomain(context)+"/add_user";

        // request
        RequestParams params = new RequestParams();
        params.put("fullname", fullname);
        params.put("email", email);
        params.put("username", username);
        params.put("gender", gender);
        params.put("password", password);

        Log.i(TAG, "doRegister: "+url);

        // methode post menggunakan library loopj
        client.post(url, params, eventListener);
    }
}
