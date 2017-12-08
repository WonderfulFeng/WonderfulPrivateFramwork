package com.example.administrator.myprivateframework.view;

/**
 * Created by Administrator on 2017/8/30.
 */

public interface IView {
    void checkedErrorCode(Integer errorCode, String toastMessage);
    void showLoadingPopup();
    void dismissLoadingPopup();
}
