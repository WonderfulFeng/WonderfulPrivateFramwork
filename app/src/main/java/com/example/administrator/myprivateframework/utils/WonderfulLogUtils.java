package com.example.administrator.myprivateframework.utils;

import android.util.Log;

import com.example.administrator.myprivateframework.app.MyApplication;

/**
 * Created by Administrator on 2017/8/30.
 */

public class WonderfulLogUtils {
    public static void logi(String TAG, String content) {
        if (!MyApplication.getApp().isReleased()) {
            Log.i(TAG, content);
        }
    }

    public static void loge(String TAG, String content) {
        if (!MyApplication.getApp().isReleased()) {
            Log.e(TAG, content);
        }
    }

    public static void logd(String TAG, String content) {
        if (!MyApplication.getApp().isReleased()) {
            Log.d(TAG, content);
        }
    }


}
