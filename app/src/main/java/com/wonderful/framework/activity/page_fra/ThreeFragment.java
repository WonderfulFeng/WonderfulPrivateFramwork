package com.wonderful.framework.activity.page_fra;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseLazyFragment;
import com.wonderful.framework.ui.intercept.WonderfulScrollView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ThreeFragment extends BaseLazyFragment {

    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibMessage)
    ImageButton ibMessage;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    @BindView(R.id.scrollView)
    WonderfulScrollView scrollView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_three;
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

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        // 原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        if (getUserVisibleHint()) immersionBar.statusBarDarkFont(true, 0.2f).init();
        if (!isSetTitle) {
            immersionBar.setTitleBar(getActivity(), llTitle);
            isSetTitle = true;
        }
    }


}
