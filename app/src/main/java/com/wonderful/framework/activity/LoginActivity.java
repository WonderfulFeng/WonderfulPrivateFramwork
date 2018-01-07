package com.wonderful.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }
}
