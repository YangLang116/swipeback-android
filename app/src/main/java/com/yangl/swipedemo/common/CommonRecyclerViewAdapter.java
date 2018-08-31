package com.yangl.swipedemo.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yangl.swipedemo.R;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: xxx
 */
public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private final int[] mImgResources = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7};

    private int mLayoutId;
    private View.OnClickListener mListener;

    public CommonRecyclerViewAdapter(int layoutId) {
        this.mLayoutId = layoutId;
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.mListener = clickListener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        if (mListener != null) {
            rootView.setOnClickListener(mListener);
        }
        return new CommonViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        ImageView itemView = (ImageView) (holder.itemView);
        int index = position % mImgResources.length;
        int imgResource = mImgResources[index];
        itemView.setImageResource(imgResource);
        itemView.setTag(imgResource);
    }

    @Override
    public int getItemCount() {
        return 45;
    }

}
