package com.wonderful.framework.activity.page_fra;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wonderful.framework.R;
import com.wonderful.framework.adapter.BannerImageLoader;
import com.wonderful.framework.base.BaseLazyFragment;
import com.wonderful.framework.ui.intercept.WonderfulScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/7.
 */

public class OneFragment extends BaseLazyFragment {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibMessage)
    ImageButton ibMessage;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.scrollView)
    WonderfulScrollView scrollView;
    private List<Integer> imageUrls = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initViews() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initBanner();
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
        scrollView.setOnScrollChangedListener(new WonderfulScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                scroll(y);
            }
        });
    }

    private void scroll(int y) {
        if (y < banner.getHeight()) rlTitle.setAlpha((float) (1.0 * y / banner.getHeight()));
        else rlTitle.setAlpha(1);
    }

    private void initBanner() {
        titles.add("木兰词");
        titles.add("人生若只如初见");
        titles.add("何事秋风悲画扇");
        titles.add("等闲变却故人心");
        titles.add("却道故人心易变");
        titles.add("骊山语罢清宵半");
        titles.add("泪雨零铃终不怨");
        titles.add("何如薄幸锦衣郎");
        titles.add("比翼连枝当日愿");
        imageUrls.add(R.drawable.banner_one);
        imageUrls.add(R.drawable.banner_two);
        imageUrls.add(R.drawable.banner_three);
        imageUrls.add(R.drawable.banner_four);
        imageUrls.add(R.drawable.banner_five);
        imageUrls.add(R.drawable.banner_six);
        imageUrls.add(R.drawable.banner_seven);
        imageUrls.add(R.drawable.banner_eight);
        imageUrls.add(R.drawable.banner_nine);
        banner.setImages(imageUrls).setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE).setBannerTitles(titles).setImageLoader(new BannerImageLoader()).start();
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
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        immersionBar.setTitleBar(getActivity(), rlTitle);
    }

}
