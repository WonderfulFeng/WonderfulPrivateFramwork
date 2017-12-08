package com.example.administrator.myprivateframework.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myprivateframework.R;
import com.example.administrator.myprivateframework.activity.LoginActivity;
import com.example.administrator.myprivateframework.base.ActivityManage;
import com.example.administrator.myprivateframework.base.BaseActivity;
import com.example.administrator.myprivateframework.entity.User;
import com.example.administrator.myprivateframework.utils.WonderfulFileUtils;
import com.example.administrator.myprivateframework.utils.WonderfulLogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by pc on 2017/3/8.
 */

public class MyApplication extends Application {
    /**
     * 是否发布了
     */
    private boolean isReleased = false;

    /**
     * 当前是否有网络
     */
    private boolean isConnect = false;
    /**
     * 更新是否正在下载
     */
    private boolean isChecked = false;
    /**
     * 当前用户信息是否发生改变
     */
    private boolean isCurrentUserInfoChanged = true;
    /**
     * 请求队列
     */
    private RequestQueue queue;

    public static MyApplication app;
    private User currentUser = new User();
    /**
     * WonderfulToastView
     */
    private TextView tvToast;
    /**
     * 当前手机屏幕的宽高
     */
    private int mWidth;
    private int mHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initView();
        queue = Volley.newRequestQueue(this);
        getDisplayMetric();
        getCurrentUserFromFile();
		checkInternet();
    }

    private void initView() {
        tvToast = (TextView) View.inflate(app, R.layout.my_toast, null);
    }
	 /**
     * 检查是否有网络
     */
    private void checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
         if ((wifiState != null && wifiState == NetworkInfo.State.CONNECTED) || (mobileState != null && mobileState == NetworkInfo.State.CONNECTED)) {
            isConnect = true;
        }
    }

    /**
     * 获取屏幕的宽高
     */
    private void getDisplayMetric() {
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取程序的Application对象
     */
    public static MyApplication getApp() {
        return app;
    }

    /**
     * 重新登录  要求所有的Activity 均继承BaseActivity
     *
     * @param activity
     */
    public void loginAgain(BaseActivity activity) {
        ActivityManage.finishAll();
        setCurrentUser(null);
        WonderfulFileUtils.getLongSaveFile(this, "User", "user.info").delete();
        LoginActivity.actionStart(activity);
    }

    /**
     * 获取请求队列
     */
    public RequestQueue getRequestQueue() {
        return queue;
    }

    /**
     * 保存当前用户
     */
    public void saveCurrentUser() {
        try {
            File file = WonderfulFileUtils.getLongSaveFile(this, "User", "user.info");
            if (currentUser == null) {
                if (file.exists()) {
                    file.delete();
                }
                return;
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(currentUser);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取当前的user对象
     */
    public void getCurrentUserFromFile() {
        try {
            File file = new File(WonderfulFileUtils.getLongSaveDir(this, "User"), "user.info");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            this.currentUser = (User) ois.readObject();
            WonderfulLogUtils.logi("读出来的User", currentUser.toString());
            if (this.currentUser == null) {
                this.currentUser = new User();
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public boolean isReleased() {
        return isReleased;
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public TextView getTvToast() {
        return tvToast;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        saveCurrentUser();
    }

    public boolean isCurrentUserInfoChanged() {
        return isCurrentUserInfoChanged;
    }

    public void setCurrentUserInfoChanged(boolean currentUserInfoChanged) {
        isCurrentUserInfoChanged = currentUserInfoChanged;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

}
