package com.example.administrator.myprivateframework.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理器
 */
public class ActivityManage {
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 关闭所有的activity
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 关闭其它
     */
    public static void finishOther(Activity exceptActivity) {
        for (Activity activity : activities) {
            if (activity.hashCode() == exceptActivity.hashCode()) {
                continue;
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}

