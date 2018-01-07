package com.wonderful.framework.ui.intercept;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/1/7.
 */

public class WonderfulViewPager extends ViewPager {

    private boolean isIntercept = true;

    public WonderfulViewPager(Context context) {
        super(context);
    }

    public WonderfulViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept && super.onInterceptTouchEvent(ev);
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
}
