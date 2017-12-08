package com.example.administrator.myprivateframework.utils;


import com.example.administrator.myprivateframework.app.GlobalConstant;
import com.example.administrator.myprivateframework.app.MyApplication;
import com.example.administrator.myprivateframework.base.BaseActivity;

/**
 * Created by wonderful on 2017/5/23.
 */

public class WonderfulCodeUtils {
    public static void checkedErrorCode(BaseActivity activity, Integer code, String toastMessage) {

        String toast = "默认提示";
        switch (code) {
            case GlobalConstant.TOKEN_DISABLE1:
                toast = "登录失效，请重新登录！";
                MyApplication.getApp().loginAgain(activity);
                WonderfulToastUtils.showToast(toast);
                return;
            case GlobalConstant.TOKEN_DISABLE2:
                toast = "登录失效，请重新登录！";
                MyApplication.getApp().loginAgain(activity);
                WonderfulToastUtils.showToast(toast);
                return;
            case GlobalConstant.JSON_ERROR:
                toast = "解析异常";
                WonderfulToastUtils.showToast(toast);
                return;
            case GlobalConstant.VOLLEY_ERROR:
                if (MyApplication.getApp().isConnect()) {
//                    toast = "当前网络不佳，请检查网络！";
                    toast = "未知错误！";
                } else {
                    toast = "当前无网络！";
                }
                WonderfulToastUtils.showToast(toast);
                return;
        }
        toast = toastMessage;
        if (!WonderfulStringUtils.isEmpty(toastMessage)) {
            WonderfulToastUtils.showToast(toast);
            return;
        }
    }
}
