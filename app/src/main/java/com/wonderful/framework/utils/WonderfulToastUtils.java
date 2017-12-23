package com.wonderful.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.wonderful.framework.app.MyApplication;

public class WonderfulToastUtils {
    private static Context context = MyApplication.getApp();
    private static TextView textView = MyApplication.getApp().getTvToast();
    private static int duration = 2000;
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
        }
    };

    public static void showMyViewToast(String s) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            textView.setText(s);
        } else {
            mToast = new Toast(context);
            textView.setText(s);
        }
        mToast.setView(textView);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mHandler.postDelayed(r, duration);
        mToast.show();
    }

    public static void showToast(String s) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            try {
                mToast.setText(s);
            } catch (Exception e) {
                // 这里是由于mToast是static的如果之前已经使用了自定义的toast（即是new出来的）那么，就会出现错误;
                mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            }
        } else {
            mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        }
        mHandler.postDelayed(r, duration);
        mToast.show();
    }

    public static void setDuration(int duration) {
        WonderfulToastUtils.duration = duration;
    }
}
