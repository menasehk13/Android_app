package com.newproject.android_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;
    PrefManager(Context context){
        this.context=context;
    }
    public void saveUserDetail(String name,String lastname,String department,String studentid ,String section,String year){
        SharedPreferences preferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=preferences.edit();
        editor.putString("firstname",name);
        editor.putString("lastname",lastname);
        editor.putString("department",department);
        editor.putString("studentid",studentid);
        editor.putString("section",section);
        editor.putString("year",year);
        editor.apply();
    }
public String getname(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return sharedPreferences.getString("firstname","");
}
public String getlastname(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return sharedPreferences.getString("lastname","");
}
public String getdepartement(){
        SharedPreferences preferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return preferences.getString("department","");
}
public String getsection(){
        SharedPreferences preferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return preferences.getString("section","");
}
public String getyear(){
        SharedPreferences preferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return preferences.getString("year","");
}
public String getStudentId(){
        SharedPreferences preferences=context.getSharedPreferences("saveuser",Context.MODE_PRIVATE);
        return preferences.getString("studentid","");
}
}
