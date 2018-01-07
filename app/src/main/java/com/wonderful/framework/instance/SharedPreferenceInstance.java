package com.wonderful.framework.instance;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wonderful.framework.app.MyApplication;

/**
 * Created by Administrator on 2017/9/19.
 */

public class SharedPreferenceInstance {

    private SharedPreferences mPreferences;
    private static SharedPreferenceInstance mInstance = null;

    public static final String SP_KEY_ISFIRSTUSE = "SP_KEY_ISFIRSTUSE";

    private SharedPreferenceInstance() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApp().getApplicationContext());
    }

    public synchronized static SharedPreferenceInstance getInstance() {
        return mInstance == null ? new SharedPreferenceInstance() : mInstance;
    }
    /**获取是否是第一次使用APP**/
    public boolean getIsFirstUse() {
        return mPreferences == null ? true : mPreferences.getBoolean(SP_KEY_ISFIRSTUSE, true);
    }
    /**保存是否是第一次使用APP*/
    public void saveIsFirstUse(boolean isFirstUse) {
        if (mPreferences == null) return;
        mPreferences.edit().putBoolean(SP_KEY_ISFIRSTUSE, isFirstUse).apply();
    }

}
