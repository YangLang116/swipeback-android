package com.yangl.swipedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yangl.swipedemo.asactivity.SwipeConfigActivity;
import com.yangl.swipedemo.asview.SimpleImagesActivity;
import com.yangl.swipedemo.withcoordinator.WithCoordinateLayoutActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.function_note);
        setContentView(R.layout.activity_splash);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_as_view:
                startActivity(new Intent(this, SimpleImagesActivity.class));
                break;
            case R.id.view_as_activity:
                startActivity(new Intent(this, SwipeConfigActivity.class));
                break;
            case R.id.view_with_coordinate_layout:
                startActivity(new Intent(this, WithCoordinateLayoutActivity.class));
                break;
        }
    }
}
