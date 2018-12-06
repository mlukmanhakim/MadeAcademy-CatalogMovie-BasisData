package com.dicoding.lukman.catalogmovie.data.notification.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by #PemimpinMuda on 12/4/2018.
 */

public class AlarmPreference {

    private final String NOTIF_STATUS =  "notifstatus" ;
    private final String FIRST_STATUS = "firs";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public AlarmPreference(Context context){
        String PREF_NAME = "AlarmPreference";
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }


    public void setOnOfAlarm( Boolean status){
        editor.putBoolean(NOTIF_STATUS, status);
        editor.commit();
    }
    public void setFirs(String first){
        editor.putString(FIRST_STATUS, first);
        editor.commit();
    }

    public String getFirst(){
        return mSharedPreferences.getString(FIRST_STATUS, null);
    }

    public Boolean getStatusAlarm(){
        return mSharedPreferences.getBoolean(NOTIF_STATUS,true);
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
