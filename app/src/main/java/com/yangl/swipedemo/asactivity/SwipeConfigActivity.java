package com.yangl.swipedemo.asactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.yangl.swipedemo.R;

public class SwipeConfigActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private Config mConfig;

    private TextView mSensitiveAreaView;

    private TextView mColorAView, mColorRView, mColorGView, mColorBView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_config);
        mConfig = new Config();
        Switch fullscreenSwitch = findViewById(R.id.sw_full_screen);
        fullscreenSwitch.setOnCheckedChangeListener(this);
        Switch statusBarSwitch = findViewById(R.id.sw_translation_status_bar);
        statusBarSwitch.setOnCheckedChangeListener(this);
        Switch enableSwitch = findViewById(R.id.sw_enable);
        enableSwitch.setOnCheckedChangeListener(this);
        RadioGroup orientationRadio = findViewById(R.id.rg_orientation);
        orientationRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectOrientation = checkedId == R.id.rb_horizontal ? Config.HORIZONTAL : Config.VERTICAL;
                mConfig.orientation = selectOrientation;
            }
        });
        mSensitiveAreaView = findViewById(R.id.tv_sensitive_area);
        SeekBar sensitiveAreaBar = findViewById(R.id.sb_sensitive_area);
        sensitiveAreaBar.setOnSeekBarChangeListener(this);

        mColorAView = findViewById(R.id.tv_a);
        mColorRView = findViewById(R.id.tv_r);
        mColorGView = findViewById(R.id.tv_g);
        mColorBView = findViewById(R.id.tv_b);
        SeekBar colorABar = findViewById(R.id.sb_a);
        SeekBar colorRBar = findViewById(R.id.sb_r);
        SeekBar colorGBar = findViewById(R.id.sb_g);
        SeekBar colorBBar = findViewById(R.id.sb_b);
        colorABar.setOnSeekBarChangeListener(this);
        colorRBar.setOnSeekBarChangeListener(this);
        colorGBar.setOnSeekBarChangeListener(this);
        colorBBar.setOnSeekBarChangeListener(this);

        SeekBar speedBar = findViewById(R.id.sb_speed);
        speedBar.setOnSeekBarChangeListener(this);

        Switch edgeSwitch = findViewById(R.id.sw_edge);
        edgeSwitch.setOnCheckedChangeListener(this);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwipeConfigActivity.this,
                        mConfig.orientation == Config.HORIZONTAL ? HorizontalListActivity.class : VerticalListActivity.class);
                intent.putExtra("data", mConfig);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_enable:
                mConfig.isOpen = isChecked;
                break;
            case R.id.sw_edge:
                mConfig.edge = isChecked;
                break;
            case R.id.sw_full_screen:
                mConfig.fullScreen = isChecked;
                break;
            case R.id.sw_translation_status_bar:
                mConfig.statusBarTransparent = isChecked;
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_sensitive_area:
                mSensitiveAreaView.setText("3. 设置滑动返回敏感区域(" + progress + "%):");
                mConfig.sensitiveArea = progress * 1.0f / 100;
                break;
            case R.id.sb_a:
                mColorAView.setText(String.format("A (%03d)", progress));
                mConfig.colorA = progress;
                break;
            case R.id.sb_r:
                mColorRView.setText(String.format("R (%03d)", progress));
                mConfig.colorR = progress;
                break;
            case R.id.sb_g:
                mColorGView.setText(String.format("G (%03d)", progress));
                mConfig.colorG = progress;
                break;
            case R.id.sb_b:
                mColorBView.setText(String.format("B (%03d)", progress));
                mConfig.colorB = progress;
                break;
            case R.id.sb_speed:
                mConfig.speed = progress;
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
