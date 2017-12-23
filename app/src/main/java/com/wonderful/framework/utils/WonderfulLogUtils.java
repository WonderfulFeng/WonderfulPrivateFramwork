package com.wonderful.framework.utils;

import android.util.Log;

import com.wonderful.framework.app.MyApplication;

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
