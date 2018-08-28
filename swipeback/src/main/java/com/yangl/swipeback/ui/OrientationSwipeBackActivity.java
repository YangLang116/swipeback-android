package com.yangl.swipeback.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.yangl.swipeback.utils.WindowUtils;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: 滑动返回界面
 */
abstract class OrientationSwipeBackActivity extends FragmentActivity {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindowEnterAnim();
    }

    protected abstract void initWindowEnterAnim();

    @Override
    public void finish() {
        super.finish();
        initWindowExitAnim();
    }

    protected abstract void initWindowExitAnim();

    public SwipeBackLayout getSwipeLayout() {
        return mSwipeBackLayout;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        installSwipeLayout();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        installSwipeLayout();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        installSwipeLayout();
    }

    protected void installSwipeLayout() {
        if (mSwipeBackLayout != null && mSwipeBackLayout.getParent() != null) {
            return;
        }
        mSwipeBackLayout = new SwipeBackLayout(this);
        mSwipeBackLayout.addOnSwipeProgressChangedListener(new SwipeBackLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onChanged(float horizontalPercent, float verticalPercent) {
                if (horizontalPercent >= 1 || verticalPercent >= 1) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });
        WindowUtils.convertActivityToTranslucent(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        mSwipeBackLayout.addView(decorChild);
        decor.addView(mSwipeBackLayout);
    }

    public void smoothToFinish() {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.smoothToEnd();
        }
    }
}
