package com.yangl.swipeback.ui;

import com.yangl.swipeback.R;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: 全屏滑动返回Activity
 */
public class VerticalSwipeBackActivity extends OrientationSwipeBackActivity {

    protected void initWindowEnterAnim() {
        overridePendingTransition(R.anim.slide_bottom_enter, 0);
    }

    protected void initWindowExitAnim() {
        overridePendingTransition(0, R.anim.slide_bottom_exit);
    }

    @Override
    protected void installSwipeLayout() {
        super.installSwipeLayout();
        getSwipeLayout().setSwipeSensitivity(0.85f);
        getSwipeLayout().needSwipeShadow(false);
        getSwipeLayout().setSwipeOrientation(SwipeBackLayout.VERTICAL);
    }
}
