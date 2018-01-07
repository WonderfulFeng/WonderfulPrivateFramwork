package com.wonderful.framework.activity.page_fra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wonderful.framework.R;
import com.wonderful.framework.utils.WonderfulLogUtils;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WonderfulLogUtils.loge("Three+onCreateView", "执行了");
        return inflater.inflate(R.layout.fragment_home_three,null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        WonderfulLogUtils.loge("Three+setUserVisibleHint", "执行了" + isVisibleToUser);
    }
}
