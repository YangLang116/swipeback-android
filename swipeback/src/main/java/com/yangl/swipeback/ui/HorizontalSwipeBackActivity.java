package com.yangl.swipeback.ui;


import com.yangl.swipeback.R;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: 全屏滑动返回Activity
 */
public class HorizontalSwipeBackActivity extends OrientationSwipeBackActivity {


    protected void initWindowEnterAnim() {
        overridePendingTransition(R.anim.slide_right_enter, 0);
    }

    protected void initWindowExitAnim() {
        overridePendingTransition(0, R.anim.slide_right_exit);
    }

}
