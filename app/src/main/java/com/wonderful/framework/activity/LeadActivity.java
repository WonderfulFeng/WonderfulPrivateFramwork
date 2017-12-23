package com.wonderful.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;
import com.wonderful.framework.instance.SharedPreferenceInstance;
import com.wonderful.framework.utils.WonderfulLogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class LeadActivity extends BaseActivity {

    @BindView(R.id.vpLead)
    ViewPager vpLead;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    TextView btInto;

    private List<View> pages = new ArrayList<>();
    private InnerPagerAdapter pagerAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LeadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_lead;
    }

    int lastPos;

    @Override
    protected void initViews() {
        pages = getPages();
        btInto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intoApp();
            }
        });
        vpLead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                WonderfulLogUtils.logi("position", position + "////" + positionOffset + "////" + positionOffsetPixels);
                if (position == 0) {
                    pages.get(position).setAlpha(1 - positionOffset);
                    if (pages.size() > 1)
                        pages.get(position + 1).setAlpha(positionOffset);
                } else if (position > 0 && position < pages.size() - 1) {
                    if (lastPos > position) {
                        pages.get(position + 1).setAlpha(positionOffset);
                        pages.get(position).setAlpha(1 - position);
                    } else {
                        pages.get(position).setAlpha(1 - positionOffset);
                        pages.get(position + 1).setAlpha(positionOffset);
                    }
                }
                lastPos = position;
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv1.setEnabled(true);
                        tv2.setEnabled(false);
                        tv3.setEnabled(false);
                        break;
                    case 1:
                        tv2.setEnabled(true);
                        tv1.setEnabled(false);
                        tv3.setEnabled(false);
                        break;
                    case 2:
                        tv3.setEnabled(true);
                        tv1.setEnabled(false);
                        tv2.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void intoApp() {
        SharedPreferenceInstance.getInstance().saveIsFirstUse(false);
        //TODO 不需要登录即可到主页面的APP
        com.wonderful.framework.activity.MainActivity.actionStart(LeadActivity.this);
        //TODO 需要登录才能进入主页的APP
//        if (!WonderfulStringUtils.isEmpty(MyApplication.getApp().getCurrentUser().getToken())) //没有过期
//            MainActivity.actionStart(LeadActivity.this);
//        else
//            LoginActivity.actionStart(LeadActivity.this);
        finish();
    }

    @Override
    protected void obtainData() {
    }

    @Override
    protected void fillWidget() {
        pagerAdapter = new InnerPagerAdapter();
        vpLead.setAdapter(pagerAdapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    private List<View> getPages() {
        List<View> pages = new ArrayList<>();
        pages.add(getLayoutInflater().inflate(R.layout.pager_image_one, null));
        pages.add(getLayoutInflater().inflate(R.layout.pager_image_two, null));
        View view = getLayoutInflater().inflate(R.layout.pager_image_three, null);
        btInto = (TextView) view.findViewById(R.id.btInto);
        pages.add(view);
        return pages;
    }

    class InnerPagerAdapter extends PagerAdapter {
        //这里父类方法 是抛出一个异常  切记要 删除super语句
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pages.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = pages.get(position);
            container.removeView(view);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
