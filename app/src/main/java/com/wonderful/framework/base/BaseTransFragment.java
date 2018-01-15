package com.wonderful.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Administrator on 2018/1/9.
 */

public abstract class BaseTransFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNeedLoad) return;
        rootView.post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && immersionBar != null)
            immersionBar.init();
    }
}
