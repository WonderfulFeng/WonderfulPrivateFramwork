package com.wonderful.framework.activity.trans_fra;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseTransFragmentActivity;

import butterknife.BindView;

public class TransActivity extends BaseTransFragmentActivity {

    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.llOne)
    LinearLayout llOne;
    @BindView(R.id.llTwo)
    LinearLayout llTwo;
    @BindView(R.id.llThree)
    LinearLayout llThree;
    @BindView(R.id.llFour)
    LinearLayout llFour;
    @BindView(R.id.llTab)
    LinearLayout llTab;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_trans;
    }

    @Override
    protected void initViews() {
        llOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 0);
            }
        });
        llTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 1);
            }
        });
        llThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 2);
            }
        });
        llFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecte(v, 3);
            }
        });
        selecte(llOne, 0);
    }

    private void selecte(View v, int page) {
        llOne.setSelected(false);
        llTwo.setSelected(false);
        llThree.setSelected(false);
        llFour.setSelected(false);
        v.setSelected(true);
        showFragment(fragments.get(page));
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
    protected void initFragments() {
        fragments.add(new OneTransFragment());
        fragments.add(new TwoTransFragment());
        fragments.add(new ThreeTransFragment());
        fragments.add(new FourTransFragment());
    }

    @Override
    public int getContainerId() {
        return R.id.flContainer;
    }
}
