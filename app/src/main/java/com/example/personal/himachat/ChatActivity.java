package com.example.personal.himachat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.personal.himachat.network.MqttHandler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    String username = "maakbar";
    boolean isMale = true;

    ListView chat;
    EditText messaging;
    Button send;

    MqttHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        username = AppConfig.getLoggedUserName(ChatActivity.this);
        isMale = AppConfig.getLoggedGender(ChatActivity.this);

        chat = (ListView) findViewById(R.id.listDaftarChat);
        messaging = (EditText) findViewById(R.id.formMessage);
        send = (Button) findViewById(R.id.butSend);

        send.setOnClickListener(this);

        handler = new MqttHandler(ChatActivity.this, callback);
    }

    MqttCallback callback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        handler.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.disconnect();
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        switch (v.getId()){
            case R.id.butSend:
                handler.publish(username, messaging.getText().toString(), isMale, date);
                messaging.setText("");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AppConfig.setLoggedInStatus(ChatActivity.this, AppConfig.SIGNED_OUT);
        Intent move = new Intent(ChatActivity.this, LoginActivity.class);
        startActivity(move);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
