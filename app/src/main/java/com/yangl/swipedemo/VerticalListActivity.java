package com.yangl.swipedemo;

import android.os.Bundle;

import com.yangl.swipeback.ui.SwipeBackLayout;
import com.yangl.swipeback.ui.VerticalSwipeBackActivity;

public class VerticalListActivity extends VerticalSwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper uiHelper = new UiHelper();
        uiHelper.setContent(this, UiHelper.VERTICAL);
        SwipeBackLayout swipeLayout = getSwipeLayout();
        swipeLayout.setSwipeOrientation(SwipeBackLayout.VERTICAL);
        Config config = (Config) getIntent().getSerializableExtra("data");
        uiHelper.setSwipeLayoutConfig(swipeLayout, config);
    }
}
