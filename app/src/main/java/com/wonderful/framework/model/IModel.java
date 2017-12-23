package com.wonderful.framework.model;

/**
 * Created by Administrator on 2017/8/25.
 */

public interface IModel {
    interface AsyncCallback {
        void onSuccess(Object obj);

        void onError(Integer code, String toastMessage);

    }
}
