package com.example.crudsoccerleaguevolley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private  static  final String LOGIN = "IS_LOGIN";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LOGIN",PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email,String username){
        editor.putBoolean(LOGIN,true);
        editor.putString(EMAIL,email);
        editor.putString(USERNAME,username);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i =  new Intent(context,LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity)context).finish();
        }
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));

        return user;
    }

    public void logout(){
       editor.clear();
       editor.commit();
       Intent i = new Intent(context,LoginActivity.class);
       context.startActivity(i);
        ((HomeActivity) context).finish();
    }


}
