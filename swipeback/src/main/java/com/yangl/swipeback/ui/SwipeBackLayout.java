package com.yangl.swipeback.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yangl.swipeback.R;
import com.yangl.swipeback.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {

    //默认的滑动敏感区域
    public static final float DEFAULT_SENSITIVITY = 0.55f;
    //默认背景的渐变色
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    //左滑返回
    public static final int HORIZONTAL = 0;
    //下滑返回
    public static final int VERTICAL = 1;

    //配置属性
    private int mWidth;
    private int mHeight;
    private int mTouchSlop;
    private int mTopOffset;
    private SwipeScroller mScroller;
    private Drawable mLeftShadowDrawable;
    private Drawable mTopShadowDrawable;

    //手势记录
    private float mDownX;
    private float mDownY;

    //状态位
    private boolean mIsFinish;

    //配置属性
    private int mScrimColor;
    private boolean mEnable = true;
    private boolean mNeedShadow = true;
    public int mSwipeOrientation = HORIZONTAL;
    private float mSensitivity = DEFAULT_SENSITIVITY;

    public List<OnSwipeProgressChangedListener> mProgressChangedListeners;
    private float mCurrentOpacity;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new SwipeScroller(context);
        mLeftShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
        mTopShadowDrawable = getResources().getDrawable(R.drawable.shadow_top);
        setSwipeScrimColor(DEFAULT_SCRIM_COLOR);
        if (context instanceof Activity) {
            mTopOffset = getTopOffsetForFitSystem((Activity) context);
        }
    }

    private int getTopOffsetForFitSystem(@NonNull Activity activity) {
        if (WindowUtils.isFullScreen(activity) || WindowUtils.isTransparentStatusBar(activity)) {
            return 0;
        }
        return WindowUtils.getStatusBarHeight(activity);
    }

    /**
     * 设置手势
     */
    public void setSwipeGestureEnable(boolean enable) {
        mEnable = enable;
    }

    public boolean getSwipeGestureEnable() {
        return mEnable;
    }

    /**
     * 设置滑动的方向
     */
    public void setSwipeOrientation(int orientation) {
        this.mSwipeOrientation = orientation;
    }

    public int getSwipeOrientation() {
        return mSwipeOrientation;
    }

    /**
     * 设置滑动敏感范围
     */
    public void setSwipeSensitivity(@FloatRange(from = 0, to = 1) float sensitivity) {
        this.mSensitivity = 1 - sensitivity;
    }

    /**
     * 设置滑动背景渐变
     */
    public void setSwipeScrimColor(int color) {
        this.mScrimColor = color;
        setBackgroundColor(color);
    }

    /**
     * 设置滑动速度
     */
    public void setSwipeSpeed(int duration) {
        mScroller.setScrollDuration(duration);
    }

    /**
     * 是否需要窗口边缘颜色加深效果
     */
    public void needSwipeShadow(boolean needShadow) {
        this.mNeedShadow = needShadow;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mEnable && shouldInterceptTouchEvent(ev);
    }

    private boolean shouldInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
            case MotionEvent.ACTION_MOVE:
                return (mSwipeOrientation == HORIZONTAL) ? shouldHorizontalIntercept(ev) : shouldVerticalIntercept(ev);
        }
        return false;
    }

    private boolean shouldHorizontalIntercept(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        if (canScroll(this, HORIZONTAL, currentX, currentY)) {
            return false;
        }
        float deltaX = currentX - mDownX;
        float deltaY = currentY - mDownY;
        return (deltaX > Math.abs(deltaY) && deltaX > mTouchSlop);
    }

    private boolean shouldVerticalIntercept(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        if (canScroll(this, VERTICAL, currentX, currentY)) {
            return false;
        }
        float deltaX = currentX - mDownX;
        float deltaY = currentY - mDownY;
        return (deltaY > Math.abs(deltaX) && deltaY > mTouchSlop);
    }

    protected boolean canScroll(View v, int orientation, float x, float y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
                        && canScroll(child, orientation, x + scrollX - child.getLeft(),
                        y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }
        return orientation == HORIZONTAL ? v.canScrollHorizontally(-1) : v.canScrollVertically(-1);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable || mIsFinish) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                if (mSwipeOrientation == HORIZONTAL) {
                    scrollTo((int) -deltaX, 0);
                } else {
                    scrollTo(0, (int) -deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                smartSmoothScroll();
                break;
        }
        return true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mNeedShadow) {
            drawShadow(canvas, mCurrentOpacity);
        }
    }

    private void drawShadow(Canvas canvas, float opacity) {
        if (mSwipeOrientation == HORIZONTAL && mLeftShadowDrawable != null) { //绘制左侧阴影
            int leftDrawableWidth = mLeftShadowDrawable.getIntrinsicWidth();
            int right = getLeft();
            int left = right - leftDrawableWidth;
            int top = getTop();
            int bottom = getBottom();
            mLeftShadowDrawable.setBounds(left, top, right, bottom);
            mLeftShadowDrawable.setAlpha((int) (opacity * 255));
            mLeftShadowDrawable.draw(canvas);
        }
        if (mSwipeOrientation == VERTICAL && mTopShadowDrawable != null) {  //绘制顶部阴影
            int topDrawableHeight = mTopShadowDrawable.getIntrinsicHeight();
            int bottom = getTop() + mTopOffset;
            int top = bottom - topDrawableHeight;
            int left = getLeft();
            int right = getRight();
            mTopShadowDrawable.setBounds(left, top, right, bottom);
            mTopShadowDrawable.setAlpha((int) (opacity * 255));
            mTopShadowDrawable.draw(canvas);
        }
    }

    public void smartSmoothScroll() {
        if (mSwipeOrientation == HORIZONTAL) {
            if (getScrollX() <= -mWidth * mSensitivity) {
                mIsFinish = true;
                smoothScrollBy(-(mWidth + getScrollX()), 0);
            } else {
                mIsFinish = false;
                smoothScrollBy(-getScrollX(), 0);
            }
        } else {
            if (getScrollY() <= -mHeight * mSensitivity) {
                mIsFinish = true;
                smoothScrollBy(0, -(mHeight + getScrollY()));
            } else {
                mIsFinish = false;
                smoothScrollBy(0, -getScrollY());
            }
        }
    }

    public void smoothToEnd() {
        if (mSwipeOrientation == HORIZONTAL) {
            mIsFinish = true;
            smoothScrollBy(-(mWidth + getScrollX()), 0);
        } else {
            mIsFinish = true;
            smoothScrollBy(0, -(mHeight + getScrollY()));
        }
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        x = Math.min(x, 0);
        y = Math.min(y, 0);
        super.scrollTo(x, y);
        float percentX = (-x * 1.0f / mWidth);
        float percentY = (-y * 1.0f / mHeight);
        mCurrentOpacity = (mSwipeOrientation == HORIZONTAL ? 1 - percentX : 1 - percentY);
        setScrim(mCurrentOpacity);
        if (mProgressChangedListeners != null && mProgressChangedListeners.size() > 0) {
            for (OnSwipeProgressChangedListener listener : mProgressChangedListeners) {
                listener.onChanged(percentX, percentY);
            }
        }
    }

    private void setScrim(float opacity) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * opacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);
        setBackgroundColor(color);
    }

    /**
     * 设置监听回调
     */
    public void addOnSwipeProgressChangedListener(OnSwipeProgressChangedListener listener) {
        if (listener == null) {
            return;
        }
        if (mProgressChangedListeners == null) {
            mProgressChangedListeners = new ArrayList<>();
        }
        mProgressChangedListeners.add(listener);
    }

    public void removeOnSwipeProgressChangedListener(OnSwipeProgressChangedListener listener) {
        if (listener == null) {
            return;
        }
        if (mProgressChangedListeners != null) {
            mProgressChangedListeners.remove(listener);
        }
    }

    public interface OnSwipeProgressChangedListener {

        void onChanged(float horizontalPercent, float verticalPercent);
    }
}