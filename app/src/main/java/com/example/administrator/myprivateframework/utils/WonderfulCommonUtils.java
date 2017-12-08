package com.example.administrator.myprivateframework.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2017/9/1.
 */

public class WonderfulCommonUtils {
    /**
     * 进入拨号界面并自动将电话号码设置上
     *
     * @param phoneNumber 电话号码
     */
    public static void intoDial(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 将指定内容粘贴到剪贴板
     *
     * @param content 剪切内容
     */
    public static void copyText(Context context, String content) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newRawUri("copyLable", Uri.parse(content));
        cm.setPrimaryClip(mClipData);
    }

}
