package com.example.administrator.myprivateframework.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.app.MyApplication;
import com.example.administrator.myprivateframework.entity.User;
import com.example.administrator.myprivateframework.utils.WonderfulKeyboardUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private PopupWindow loadingPopup;
    private TextView tvLoadingText;
    private OfflineReceiver offlineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutId());
        ButterKnife.bind(this);
        ActivityManage.addActivity(this);
        initLoadingPopup();
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

    public void a() {
    }


    protected abstract int getActivityLayoutId();

    protected abstract void initViews();

    protected abstract void obtainData();

    protected abstract void fillWidget();

    protected abstract void loadData();


    @Override
    protected void onResume() {
        super.onResume();
        offlineReceiver = new OfflineReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".force_offline");
        registerReceiver(offlineReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (offlineReceiver != null) {
            unregisterReceiver(offlineReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        ActivityManage.removeActivity(this);
        hideLoadingPopup();
        super.onDestroy();
    }

    /********************************************************************/
    private void initLoadingPopup() {
        View loadingView = getLayoutInflater().inflate(R.layout.pop_loading, null);
        tvLoadingText = (TextView) loadingView.findViewById(R.id.tvLoadingText);
        loadingPopup = new PopupWindow(loadingView, MyApplication.getApp().getmWidth(), MyApplication.getApp().getmHeight());
        loadingPopup.setFocusable(true);
        loadingPopup.setClippingEnabled(false);
        loadingPopup.setBackgroundDrawable(new ColorDrawable());
    }

    public void displayLoadingPopup() {
        loadingPopup.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void hideLoadingPopup() {
        loadingPopup.dismiss();
        tvLoadingText.setText("玩命加载中...");
    }

    public void setLoadingText(String loadingText) {
        tvLoadingText.setText(loadingText + "...");
    }

    /********************************************************************/

    /********************************************************************/
    public String getToken() {
        User user = MyApplication.getApp().getCurrentUser();
        return user == null ? "" : user.getToken();
    }
    /********************************************************************/

    /********************************************************************/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                WonderfulKeyboardUtils.hideKeyboard(ev, view, this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    /********************************************************************/
    /********************************************************************/

    class OfflineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("下线通知")
                    .setMessage("您的账号在别处登录，您已被迫下线。如非本人操作，则密码可能已泄漏，建议您重新登录后修改密码！")
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false);
            builder.create();
            builder.show();
        }
    }
    /********************************************************************/


}
