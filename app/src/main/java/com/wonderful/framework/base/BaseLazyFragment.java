package com.wonderful.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2018/1/7.
 */

public abstract class BaseLazyFragment extends BaseFragment {
    /**
     * 是否正在加载数据
     */
    protected boolean isLoad = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tryToLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        tryToLoadData();
    }

    private void tryToLoadData() {
        if (!isInit) return;
        if (getUserVisibleHint()) {
            loadData();
            isLoad = true;
        } else {
            if (isLoad) stopLoad();
        }
    }

    @Override
    public void onDestroyView() {
        isInit = false;
        isLoad = false;
        super.onDestroyView();
    }

    /**
     * 隐藏时停止 加载数据
     */
    protected void stopLoad() {
    }
}
