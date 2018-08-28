package com.yangl.swipedemo;

import android.os.Bundle;

import com.yangl.swipeback.ui.HorizontalSwipeBackActivity;
import com.yangl.swipeback.ui.SwipeBackLayout;

public class HorizontalListActivity extends HorizontalSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper uiHelper = new UiHelper();
        uiHelper.setContent(this, UiHelper.HORIZONTAL);
        SwipeBackLayout swipeLayout = getSwipeLayout();
        Config config = (Config) getIntent().getSerializableExtra("data");
        uiHelper.setSwipeLayoutConfig(swipeLayout, config);
    }
}
