package com.android_app_project.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android_app_project.activity.MainActivity;
import com.android_app_project.model.UserLoginResponse;


public class SharePrefManager {
    private static final  String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_JWT = "keyJWT";
    private static final String KEY_ID = "keyid";
    private static SharePrefManager mInstance;
    private static Context ctx;
    private SharePrefManager(Context context){
        ctx = context;
    }
    public static synchronized SharePrefManager getInstance(Context context){
        if(mInstance ==null){
            mInstance = new SharePrefManager(context);
        }
        return mInstance;
    }
    public void userLogin(UserLoginResponse user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JWT, user.getJWTtoken());
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JWT,null) !=null;
    }
    public UserLoginResponse getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new UserLoginResponse(
                sharedPreferences.getString(KEY_JWT,null)
        );
    }
    public void logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }
}
