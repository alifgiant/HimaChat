package com.example.personal.himachat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal.himachat.network.NetHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText fukkname,email,usrname,pword;
    Button register;
    Spinner gender;
    CheckBox agreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fukkname = (EditText) findViewById(R.id.txtFullname);
        email = (EditText) findViewById(R.id.txtEmal);
        usrname = (EditText) findViewById(R.id.txtUsernameForm);
        pword = (EditText) findViewById(R.id.txtPassForm);

        register = (Button) findViewById(R.id.butRegister);
        register.setOnClickListener(this);

        gender = (Spinner) findViewById(R.id.spinnerGender);
        agreement = (CheckBox) findViewById(R.id.checkAgreement);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.butRegister:
                if (!TextUtils.isEmpty(fukkname.getText().toString())) {
                    if (isValidEmail(email.getText().toString())) {
                        if (!TextUtils.isEmpty(usrname.getText().toString())) {
                            if (!TextUtils.isEmpty(pword.getText().toString())) {
                                if (agreement.isChecked()) {
                                    NetHelper.doRegister(RegisterActivity.this, fukkname.getText().toString(),
                                            email.getText().toString(), usrname.getText().toString(),
                                            pword.getText().toString(),
                                            gender.getSelectedItem().equals("Boy"),
                                            responseHandler);
                                }else {
                                    agreement.setError("Please accept the agreement");
                                }
                            }else{
                                pword.setError("Please insert this field");
                            }
                        }else{
                            usrname.setError("Please insert this field");
                        }
                    }else{
                        email.setError("Please insert this field");
                    }
                }else {
                    fukkname.setError("Please insert this field");
                }
                break;
        }
    }

    boolean isValidEmail(String email){
        if (!TextUtils.isEmpty(email) && email.contains("@")){
            return true;
        }return false;
    }

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Toast.makeText(RegisterActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }
    };
}
