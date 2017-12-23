package com.wonderful.framework.ui.intercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Wonderful on 2017/7/29.
 * 带事件拦截的ScrollView
 */

public class WonderfulScrollView extends ScrollView {
    private boolean isIntercept = true;

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    public WonderfulScrollView(Context context) {
        super(context);
    }

    public WonderfulScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WonderfulScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept && super.onInterceptTouchEvent(ev);
    }
}
