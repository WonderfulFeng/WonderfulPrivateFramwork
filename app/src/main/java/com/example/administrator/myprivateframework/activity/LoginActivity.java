package com.example.administrator.myprivateframework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.base.BaseActivity;

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
}
