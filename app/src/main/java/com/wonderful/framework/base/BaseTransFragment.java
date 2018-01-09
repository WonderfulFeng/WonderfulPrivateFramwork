package com.wonderful.framework.base;

/**
 * Created by Administrator on 2018/1/9.
 */

public abstract class BaseTransFragment extends BaseFragment {
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && immersionBar != null)
            immersionBar.init();
    }
}
