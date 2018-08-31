package com.yangl.swipedemo.asactivity;

import android.os.Bundle;

import com.yangl.swipeback.ui.SwipeBackLayout;
import com.yangl.swipeback.ui.VerticalSwipeBackActivity;
import com.yangl.swipeback.utils.WindowUtils;
import com.yangl.swipedemo.utils.UiHelper;

/**
 * NOTE:需要下滑返回，继承VerticalSwipeBackActivity即可
 */
public class VerticalListActivity extends VerticalSwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config config = (Config) getIntent().getSerializableExtra("data");
        if (config.fullScreen) {
            WindowUtils.setActivityFullScreen(this);
        }
        if (config.statusBarTransparent) {
            WindowUtils.setStatusBarTransparent(this);
        }

        UiHelper uiHelper = new UiHelper();
        uiHelper.setContent(this, UiHelper.VERTICAL);


        SwipeBackLayout swipeLayout = getSwipeLayout();
        uiHelper.setSwipeLayoutConfig(swipeLayout, config);
    }
}
