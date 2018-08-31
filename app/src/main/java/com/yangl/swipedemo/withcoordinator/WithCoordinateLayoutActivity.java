package com.yangl.swipedemo.withcoordinator;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.yangl.swipeback.ui.VerticalSwipeBackActivity;
import com.yangl.swipedemo.R;
import com.yangl.swipedemo.common.CommonRecyclerViewAdapter;

public class WithCoordinateLayoutActivity extends VerticalSwipeBackActivity {

    private int mTouchSlop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        setContentView(R.layout.activity_with_coordinate_layout);
        RecyclerView mRecyclerView = findViewById(R.id.center_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new CommonRecyclerViewAdapter(R.layout.item_vertical));
    }

    @Override
    public void onBackPressed() {
        smoothToFinish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (handleSwipeBackEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    //处理左滑内容下滑消失
    private float mDownX;
    private float mDownY;
    private boolean mIsBeingDragged;

    private boolean handleSwipeBackEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getRawX();
                float currentY = ev.getRawY();
                float deltaX = currentX - mDownX;
                float deltaY = currentY - mDownY;
                if ((deltaX > Math.abs(deltaY) && deltaX > mTouchSlop) || mIsBeingDragged) {
                    mIsBeingDragged = true;
                    getSwipeLayout().scrollTo(0, (int) -deltaX);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;
                    getSwipeLayout().smartSmoothScroll();
                    return true;
                }
                break;
        }
        return false;
    }
}
