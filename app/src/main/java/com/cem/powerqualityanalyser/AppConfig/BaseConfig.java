package com.cem.powerqualityanalyser.AppConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BaseConfig {
    private Context contxt;
    protected SharedPreferences settings;
    private Editor editor;

    public void Init(Context contxt) {
        settings = contxt.getSharedPreferences(this.getClass().getSimpleName(), 0);
        editor = settings.edit();
    }

    protected void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    protected String readString(String key) {

        String str = settings.getString(key, "");
        return str;
    }

    protected void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    protected int readInt(String key) {

        int value = settings.getInt(key, 0);
        return value;
    }

    protected int readInt(String key,int defValule) {
        int value = settings.getInt(key, defValule);
        return value;
    }

    protected void saveBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean readBoolean(String key, boolean defvalue) {

        boolean value = settings.getBoolean(key, defvalue);
        return value;
    }
    protected void saveFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    protected float readFloat(String key, float defvalue) {

        float value = settings.getFloat(key, defvalue);
        return value;
    }
}
