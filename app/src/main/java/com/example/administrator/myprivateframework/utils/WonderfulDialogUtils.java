package com.example.administrator.myprivateframework.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.app.MyApplication;

/**
 * Created by Administrator on 2017/12/6.
 */

public class WonderfulDialogUtils {
    /**
     * 显示成功或是提示的对话框
     *
     * @param context Activity对象
     * @param type    0 提示  1 操作成功
     */
    public static void showSuccessDialog(Activity context, int type) {
        View view;
        if (type == 0) view = LayoutInflater.from(context).inflate(R.layout.dialog_operate_notice, null);//提示
        else view = LayoutInflater.from(context).inflate(R.layout.dialog_operate_notice, null);//成功
        final PopupWindow popupWindow = new PopupWindow(view, MyApplication.getApp().getmWidth(), MyApplication.getApp().getmHeight());
        TextView tvSure = (TextView) view.findViewById(R.id.tvSure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        RelativeLayout rlRoot = (RelativeLayout) view.findViewById(R.id.rlRoot);
        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
