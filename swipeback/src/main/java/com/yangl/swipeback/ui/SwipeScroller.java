package com.yangl.swipeback.ui;

import android.content.Context;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: xxx
 *
 */
public class SwipeScroller extends Scroller {

    private static final int DEFAULT_DURATION = 400;

    private int mDuration = DEFAULT_DURATION;

    public SwipeScroller(Context context) {
        super(context, new LinearInterpolator());
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setScrollDuration(int duration) {
        this.mDuration = duration;
    }
}
