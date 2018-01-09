package com.wonderful.framework.activity.trans_fra;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gyf.barlibrary.OnKeyboardListener;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseTransFragment;
import com.wonderful.framework.ui.intercept.WonderfulScrollView;
import com.wonderful.framework.utils.WonderfulToastUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class FourTransFragment extends BaseTransFragment {

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
        return R.layout.fragment_home_four;
    }

    @Override
    protected void initViews() {
        immersionBar.setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                //isPopup为true，软键盘弹出，为false，软键盘关闭
                WonderfulToastUtils.showToast((isPopup ? "软件盘弹出" : "软键盘关闭") + "  高度：" + keyboardHeight);
            }
        });
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
        if (!isSetTitle) {
            immersionBar.setTitleBar(getActivity(), llTitle);
            isSetTitle = true;
        }
    }

}
