package com.wonderful.framework.activity.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wonderful.framework.R;
import com.wonderful.framework.base.BaseActivity;
import com.wonderful.framework.utils.WonderfulLogUtils;

import java.text.DecimalFormat;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.bt)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void initViews() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
    }

    private void click() {
        format1("###,###.###", 2.3);
        format1("000,000.000", 11222.34567);
        format1("###,###.###￥", 111222.34567);
        format1("000,000.000￥", 11222.34567);
        format1("##.###%", 0.345678);
        format1("00.###%", 0.0345678);
        format1("###.###\u2030", 0.345678);
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

    public void format1(String pattern, double value) {   // 此方法专门用于完成数字的格式化显示
        DecimalFormat df = null;           // 声明一个DecimalFormat类的对象
        df = new DecimalFormat(pattern);   // 实例化对象，传入模板
        String str = df.format(value);     // 格式化数字
        WonderfulLogUtils.loge(value + "  " + pattern, str);
    }
}















