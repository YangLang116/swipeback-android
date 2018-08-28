package com.yangl.swipedemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yangl.swipeback.ui.SwipeBackLayout;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: xxx
 */
public class UiHelper {

    private static final String TAG = "SwipeLayoutActivity";

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public void setContent(Activity activity, int orientation) {
        //打开用于全屏效果
//        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.setContentView(R.layout.activity_orientation_list);
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView, orientation);
    }

    public void initRecyclerView(RecyclerView recyclerView, int orientation) {
        Context context = recyclerView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                orientation == HORIZONTAL ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL,
                false));
        recyclerView.setAdapter(new RecyclerView.Adapter<DemoViewHolder>() {
            @NonNull
            @Override
            public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new DemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
                holder.titleView.setText("Position = " + position);
            }

            @Override
            public int getItemCount() {
                return 45;
            }
        });
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

    static class DemoViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;

        public DemoViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.text);
        }
    }
}
