package com.example.personal.himachat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maaakbar on 4/24/16.
 */
public class AppConfig {
    final static String pref = "HimaChat";
    final static String user = "username";
    final static String gender = "gender";
    final static String signed = "signed";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE);
    }
    public static void saveUser(Context context, String username, boolean isMale){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(user, username);
        editor.putBoolean(gender, isMale);
        editor.apply();
    }
    public static String getLoggedUserName(Context context){
        return getSharedPreferences(context).getString(user, "");
    }
    public static boolean getLoggedGender(Context context){
        return getSharedPreferences(context).getBoolean(gender, true);
    }

    public static boolean SIGNED_IN = true;
    public static boolean SIGNED_OUT = false;
    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(signed, status);
        editor.apply();
    }
    public static boolean getSignedInStatus(Context context){
        return getSharedPreferences(context).getBoolean(signed, false);
    }
}
