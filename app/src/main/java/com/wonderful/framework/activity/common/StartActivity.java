package com.wonderful.framework.activity.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.wonderful.framework.R;
import com.wonderful.framework.activity.LoginActivity;
import com.wonderful.framework.activity.main.MainActivity;
import com.wonderful.framework.app.MyApplication;
import com.wonderful.framework.instance.SharedPreferenceInstance;
import com.wonderful.framework.utils.WonderfulStringUtils;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private Timer timer;
    int n = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImmersionBar.with(this).fullScreen(true).init();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (n == 0) {
                    timer.cancel();
                    timer = null;
                    ////TODO 不需要登录即可进入主界面的应用
//                    if (SharedPreferenceInstance.getInstance().getIsFirstUse())
//                        LeadActivity.actionStart(StartActivity.this);
//                    else
//                        OCRActivity.actionStart(StartActivity.this);
                    ////TODO 必须要登录才能进入程序主界面
                    if (SharedPreferenceInstance.getInstance().getIsFirstUse())
                        LeadActivity.actionStart(StartActivity.this);
                    else if (!WonderfulStringUtils.isEmpty(MyApplication.getApp().getCurrentUser().getToken())) //没有过期
                        MainActivity.actionStart(StartActivity.this);
                    else
                        LoginActivity.actionStart(StartActivity.this);
                    finish();
                }
                n--;
            }
        }, 10, 999);
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onBackPressed();

    }
}
