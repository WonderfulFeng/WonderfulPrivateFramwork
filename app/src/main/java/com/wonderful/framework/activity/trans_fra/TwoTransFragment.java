package com.wonderful.framework.activity.trans_fra;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseTransFragment;
import com.wonderful.framework.ui.intercept.WonderfulScrollView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class TwoTransFragment extends BaseTransFragment {

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
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 1500);
            }
        });
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        SinaRefreshView sinaRefreshView = new SinaRefreshView(getActivity());
        sinaRefreshView.setArrowResource(R.mipmap.icon_refresh_arrow_down);
        sinaRefreshView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight));
        refreshLayout.setHeaderView(sinaRefreshView);
        BallPulseView ballPulseView = new BallPulseView(getActivity());
        refreshLayout.setBottomView(ballPulseView);
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
