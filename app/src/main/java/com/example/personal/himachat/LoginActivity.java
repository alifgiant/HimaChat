package com.example.personal.himachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personal.himachat.network.NetHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText username,password;
    Button loginBut, registerBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        username = (EditText) findViewById(R.id.inputUsername);
        password = (EditText) findViewById(R.id.inputPassword);

        loginBut = (Button) findViewById(R.id.butLogins);
        registerBut = (Button) findViewById(R.id.butRgister);

        loginBut.setOnClickListener(this);
        registerBut.setOnClickListener(this);

        if (AppConfig.getSignedInStatus(LoginActivity.this)){
            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.butLogins:
                if (!TextUtils.isEmpty(username.getText().toString())) {
                    if(!TextUtils.isEmpty(password.getText().toString())) {
                        NetHelper.doLogin(LoginActivity.this, username.getText().toString(), password.getText().toString(), responseHandler);
                    }else{
                        password.setError("Fill this field");
                    }
                }else{
                    username.setError("Fill this field");
                }

                break;
            case R.id.butRgister:
                Intent intents = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intents);
                break;
        }
    }

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                // terima response dari server
                String result = new String(responseBody, "UTF-8");
                switch (result){

                    // kalau respons password salah
                    case "pass":
                        Toast.makeText(LoginActivity.this, "Your password is wrong", Toast.LENGTH_SHORT).show();
                        break;
                    // kalau respons sukses
                    default:
                        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                        startActivity(intent);
                        AppConfig.saveUser(LoginActivity.this, username.getText().toString(), Boolean.parseBoolean(result));
                        AppConfig.setLoggedInStatus(LoginActivity.this, AppConfig.SIGNED_IN);
                        finish();
                        break;
                }
            }catch (UnsupportedEncodingException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i("Login", "onFailure: "+statusCode);
            Toast.makeText(LoginActivity.this, "Your username is wrong", Toast.LENGTH_SHORT).show();

        }
    };
}
