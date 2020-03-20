package com.newproject.android_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {
    private static final String LOGGED_IN_PREF = "logged_in_status";

    static SharedPreferences getPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setLoggedIn(Context context,boolean loggedin){
        SharedPreferences.Editor editor=getPreference(context).edit();
        editor.putBoolean(LOGGED_IN_PREF,loggedin);
        editor.apply();
    }
    public static boolean getLoggedStatus(Context context){
        return getPreference(context).getBoolean(LOGGED_IN_PREF,false);
    }
}
