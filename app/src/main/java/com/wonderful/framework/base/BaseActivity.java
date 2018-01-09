package com.wonderful.framework.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.wonderful.framework.R;
import com.wonderful.framework.app.MyApplication;
import com.wonderful.framework.utils.WonderfulKeyboardUtils;
import com.wonderful.framework.utils.WonderfulStringUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private PopupWindow loadingPopup;
    private TextView tvLoadingText;
    private Unbinder unbinder;
    protected ImmersionBar immersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutId());
        unbinder = ButterKnife.bind(this);
        ActivityManage.addActivity(this);
        initLoadingPopup();
        if (isImmersionBarEnabled()) initImmersionBar();
        initViews();
        obtainData();
        fillWidget();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }

    /**
     * 子类重写实现扩展设置
     */
    protected void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.keyboardEnable(true).init();
    }

    /**
     * 获取布局ID
     */
    protected abstract int getActivityLayoutId();

    /**
     * 是否启用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化工作
     */
    protected abstract void initViews();

    /**
     * 获取本地或传递的数据数据
     */
    protected abstract void obtainData();

    /**
     * 控件填充
     */
    protected abstract void fillWidget();

    /**
     * 初始数据加载
     */
    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManage.removeActivity(this);
        hideLoadingPopup(true);
        if (immersionBar != null) immersionBar.destroy();
    }

    /**
     * 初始化加载dialog
     */
    private void initLoadingPopup() {
        View loadingView = getLayoutInflater().inflate(R.layout.pop_loading, null);
        tvLoadingText = (TextView) loadingView.findViewById(R.id.tvLoadingText);
        loadingPopup = new PopupWindow(loadingView, MyApplication.getApp().getmWidth(), MyApplication.getApp().getmHeight());
        loadingPopup.setFocusable(true);
        loadingPopup.setClippingEnabled(false);
        loadingPopup.setBackgroundDrawable(new ColorDrawable());
    }

    /**
     * 显示加载框
     */
    public void displayLoadingPopup(String loadingText) {
        if (!WonderfulStringUtils.isEmpty(loadingText))
            tvLoadingText.setText(loadingText + "...");
        loadingPopup.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 隐藏加载框
     *
     * @param isChange
     */
    public void hideLoadingPopup(boolean isChange) {
        if (isChange) tvLoadingText.setText(R.string.loading);
        loadingPopup.dismiss();
    }

    /**
     * 获取用户token
     */
    public String getToken() {
        return MyApplication.getApp().getCurrentUser().getToken();
    }

    /**
     * 处理软件盘智能弹出和隐藏
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                WonderfulKeyboardUtils.editKeyboard(ev, view, this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
