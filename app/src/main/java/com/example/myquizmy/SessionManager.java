package com.example.myquizmy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myquizmy.Login.Sign_in_Activity;

import java.util.HashMap;

public class SessionManager {


    private static final String PREFS_NAME = "MyPhone";
    static final String KEY_USER_PHONE = "userPhone";
    public static final String LOGIN = "login";

    private Context context;
    static SharedPreferences pref;
    public SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public static boolean isLogin(Context context) {

        return pref.getBoolean(LOGIN, false);
    }

    public void createSession(String userPhone) {
        editor.putString(KEY_USER_PHONE, userPhone);
        editor.putBoolean(LOGIN, true);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USER_PHONE, pref.getString(KEY_USER_PHONE, ""));
        return user;
    }

    public void logout(Context context) {
        editor.clear();
        editor.commit();



    }
}
