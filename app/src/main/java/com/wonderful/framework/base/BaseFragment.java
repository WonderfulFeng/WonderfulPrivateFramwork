package com.wonderful.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/26.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    Unbinder unbinder;
    protected ImmersionBar immersionBar;
    /**
     * 是否已经初始化视图
     */
    protected boolean isInit = false;
    /**
     * 重新显示时是否需要加载数据
     */
    protected boolean isNeedLoad = true;
    /**
     * 是否调用过setTitelBar
     */
    protected boolean isSetTitle = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, rootView);
        isInit = true;
        if (isImmersionBarEnabled()) initImmersionBar();
        initViews();
        obtainData();
        fillWidget();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void obtainData();

    protected abstract void fillWidget();

    protected abstract void loadData();

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE).init();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void displayLoadingPopup() {
        if (getActivity() != null) ((BaseActivity) getActivity()).displayLoadingPopup();
    }

    protected void hideLoadingPopup( ) {
        if (getActivity() != null) ((BaseActivity) getActivity()).hideLoadingPopup();
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    protected void finish() {
        getActivity().finish();
    }
}
