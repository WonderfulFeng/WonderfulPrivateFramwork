package com.example.administrator.myprivateframework.instance;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.administrator.myprivateframework.app.MyApplication;

/**
 * Created by Administrator on 2017/9/19.
 */

public class SharedPreferenceInstance {

    private SharedPreferences mPreferences;
    private static SharedPreferenceInstance mInstance = null;
    private boolean isFirstUse;

    public static final String SP_KEY_ISFIRSTUSE = "SP_KEY_ISFIRSTUSE";

    private SharedPreferenceInstance() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp().getApplicationContext());
    }

    public static SharedPreferenceInstance getInstance() {
        return mInstance == null ? new SharedPreferenceInstance() : mInstance;
    }

    public boolean getIsFirstUse() {
        return mPreferences == null ? true : mPreferences.getBoolean(SP_KEY_ISFIRSTUSE, true);
    }

    public void saveIsFirstUse(boolean isFirstUse) {
        if (mPreferences == null) return;
        mPreferences.edit().putBoolean(SP_KEY_ISFIRSTUSE, isFirstUse).apply();
    }

}
