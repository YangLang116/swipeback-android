package com.yangl.swipedemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yangl.swipeback.ui.SwipeBackLayout;
import com.yangl.swipedemo.R;
import com.yangl.swipedemo.asactivity.Config;
import com.yangl.swipedemo.common.CommonRecyclerViewAdapter;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description:
 */
public class UiHelper {

    private static final String TAG = "SwipeLayoutActivity";

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public void setContent(Activity activity, int orientation) {
        activity.setContentView(R.layout.activity_orientation_list);
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView, orientation);
    }

    public void initRecyclerView(RecyclerView recyclerView, final int orientation) {
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, orientation == HORIZONTAL ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new CommonRecyclerViewAdapter(orientation == HORIZONTAL ? R.layout.item_horizontal : R.layout.item_vertical));
    }

    public void setSwipeLayoutConfig(SwipeBackLayout swipeBackLayout, Config config) {
        swipeBackLayout.setSwipeGestureEnable(config.isOpen);
        swipeBackLayout.setSwipeSensitivity(config.sensitiveArea);
        swipeBackLayout.setSwipeScrimColor(Color.argb(config.colorA, config.colorR, config.colorG, config.colorB));
        swipeBackLayout.setSwipeSpeed(config.speed);
        swipeBackLayout.needSwipeShadow(config.edge);
        swipeBackLayout.addOnSwipeProgressChangedListener(new SwipeBackLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onChanged(float horizontalPercent, float verticalPercent) {
                String log = String.format("onChanged: (%f, %f)", horizontalPercent, verticalPercent);
                Log.d(TAG, log);
            }
        });
    }
}
