package com.example.personal.himachat.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maaakbar on 4/24/16.
 */
public class Chat {
    String username;
    String message;
    boolean isMale;
    String date;

    public Chat() {
    }

    public Chat(String username, String message, boolean isMale, String date) {
        this.username = username;
        this.message = message;
        this.isMale = isMale;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public Chat setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Chat setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isMale() {
        return isMale;
    }

    public Chat setMale(boolean male) {
        isMale = male;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Chat setDate(String date) {
        this.date = date;
        return this;
    }

    public static Chat ParseJSON(String response){
        Chat chat = new Chat();
        try {
            JSONObject object = new JSONObject(response);
            chat.setUsername(object.getString("username"))
                .setMale(object.getBoolean("is_male"))
                .setMessage(object.getString("message"))
                .setDate(object.getString("date"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return chat;
    }

}
