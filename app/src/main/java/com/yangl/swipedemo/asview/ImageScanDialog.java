package com.yangl.swipedemo.asview;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yangl.swipeback.ui.SwipeBackLayout;
import com.yangl.swipeback.utils.WindowUtils;
import com.yangl.swipedemo.R;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: xxx
 */
public class ImageScanDialog extends Dialog {

    public ImageScanDialog(@NonNull Context context, int imageRes) {
        super(context, R.style.style_common_dialog);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        int dialogHeight = WindowUtils.getScreenHeight() / 3 * 2;
        attributes.height = dialogHeight;
        attributes.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(attributes);

        setContentView(R.layout.dialog_image_scan);

        final ImageView scanImageView = findViewById(R.id.view_scan_img);
        scanImageView.setImageResource(imageRes);

        final TextView tipView = findViewById(R.id.view_tip);
        tipView.setText(getContext().getResources().getString(R.string.image_dialog_tip) + "0");

        SwipeBackLayout swipeBackLayout = findViewById(R.id.view_swipe_back);
        swipeBackLayout.setSwipeOrientation(SwipeBackLayout.VERTICAL);
        swipeBackLayout.setSwipeSensitivity(0.75f);
        swipeBackLayout.addOnSwipeProgressChangedListener(new SwipeBackLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onChanged(float horizontalPercent, float verticalPercent) {
                scanImageView.setAlpha(1 - verticalPercent);
                tipView.setText(getContext().getResources().getString(R.string.image_dialog_tip) + verticalPercent);
                if (verticalPercent >= 1) {
                    dismiss();
                }
            }
        });
    }
}
