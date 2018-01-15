package com.wonderful.framework.activity.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

}
