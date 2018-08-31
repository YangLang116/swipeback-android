package com.yangl.swipedemo.asview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yangl.swipedemo.R;
import com.yangl.swipedemo.common.CommonRecyclerViewAdapter;

public class SimpleImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_images);
        RecyclerView mImageGridListView = findViewById(R.id.recyclerView);
        mImageGridListView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        CommonRecyclerViewAdapter imagesAdapter = new CommonRecyclerViewAdapter(R.layout.item_grid);
        imagesAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imageRes = (int) v.getTag();
                //打开SwipeBackLayout图片弹窗
                new ImageScanDialog(SimpleImagesActivity.this, imageRes).show();
            }
        });
        mImageGridListView.setAdapter(imagesAdapter);

    }
}
