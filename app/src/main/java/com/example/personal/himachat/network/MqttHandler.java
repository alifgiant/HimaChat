package com.example.personal.himachat.network;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maaakbar on 4/24/16.
 */
public class MqttHandler {
    final String TAG = "MqttHandler";
    Context context;
    final static String topic = "HIMACHAT";
    MqttAndroidClient client;
    MqttCallback mqttCallback;

    public MqttHandler(Context context, MqttCallback mqttCallback) {
        this.context = context;
        this.mqttCallback = mqttCallback;
        setupMQTT();
    }

    private void setupMQTT(){
        String clientID = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, NetHelper.getMqttDomain(context), clientID);
        client.setCallback(mqttCallback);
    }

    public void connect(){
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "mqtt connect success");
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "mqtt connect failed");
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
//            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(){
        int qos = 1;
        try {
            IMqttToken subscToken = client.subscribe(topic, qos);
            subscToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "mqtt subscribe success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "mqtt subscribe failed");
                }
            });
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void publish(String username, String messageText, boolean isMale, String date){
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("message", messageText);
            json.put("is_male", isMale);
            json.put("date", date);
            Log.i(TAG, "publish: "+json.toString());
            message.setPayload(json.toString().getBytes());
            client.publish(topic, message);
            Log.i(TAG, "onPublish success");
        }catch (JSONException | MqttException e){
            Log.i(TAG, "onPublish error");
            e.printStackTrace();
        }
    }
}
