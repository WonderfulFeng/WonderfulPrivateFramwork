package com.example.administrator.myprivateframework.utils;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CheckPermServer {

    public static final String[] PERMISSION_BODY = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    public static final int PERMISSION_REQUEST_CODE = 0;// 系统授权管理页面时的结果参数
    public static final int PERMISSION_GRANTED = 0;// 标识权限授权
    public static final int PERMISSION_DENIEG = 1;// 权限不足，权限被拒绝的时候
    public static final String PACKAGE_URL_SCHEME = "package:";//权限方案

    private Context mContext;

    /**
     * 权限不足的回调接口
     */
    private OnClickListener mNotOnClickListener;

    public CheckPermServer(Context mContext, OnClickListener mNotOnClickListener) {
        super();
        this.mContext = mContext;
        this.mNotOnClickListener = mNotOnClickListener;
    }

    //检查权限时，判断系统的权限集合
    public boolean permissionSet(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(permission)) {//是否添加完全部权限集合
                ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
                return true;
            }
        }
        return false;
    }


    //检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    private boolean isLackPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }

    //显示对话框提示用户缺少权限
    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");//提示帮助
        builder.setMessage("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。\n最后点击两次后退按钮，即可返回。");

        //如果是拒绝授权，则退出应用
        //退出
        builder.setNegativeButton("退出", mNotOnClickListener);
        //打开设置，让用户选择打开权限
        builder.setPositiveButton("设置", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();//打开设置
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    //打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mContext.getPackageName()));
        mContext.startActivity(intent);
    }

    // 获取全部权限
    public boolean hasAllPermissionGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
