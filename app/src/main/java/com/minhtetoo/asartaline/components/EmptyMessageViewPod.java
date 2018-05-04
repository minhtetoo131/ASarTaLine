package com.minhtetoo.asartaline.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.minhtetoo.asartaline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by min on 2/20/2018.
 */

public class EmptyMessageViewPod extends RelativeLayout {

    @BindView(R.id.iv_empty_messages)
    ImageView ivEmpty;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;


    public EmptyMessageViewPod(Context context) {
        super(context);
    }

    public EmptyMessageViewPod(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyMessageViewPod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EmptyMessageViewPod(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setEmptyData(int emptyImageId, String emptyMsg) {
        ivEmpty.setImageResource(emptyImageId);
        tvEmpty.setText(emptyMsg);
    }

    public void setEmptyData(String emptyMsg) {
        tvEmpty.setText(emptyMsg);
    }
}
