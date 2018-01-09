package com.wonderful.framework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public abstract class BaseTransFragmentActivity extends BaseActivity {
    protected List<BaseFragment> fragments = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        initFragments();
        super.onCreate(savedInstanceState);
    }

    protected abstract void initFragments();

    public abstract int getContainerId();

    public void addFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(getContainerId(), fragment).commit();
    }

    public void removeFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        fragments.remove(fragment);
    }

    protected void showFragment(BaseFragment fragment) {
        hideFragments();
        if (!fragment.isAdded()) addFragment(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.show(fragment).commit();
    }

    protected void hideFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (!fragments.get(i).isHidden()) {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }

//    notice: 下面两个方法用于平板开发框架搭建 一个按钮对应多个界面 且有记录顺序  详见速分期开发
//    public abstract void switchFragment(int layoutId, String tag);
//    public abstract void switchButton(Button btn);
//
}
