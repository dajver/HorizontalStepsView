package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.horizontalsteps.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 6/20/17.
 */

public class ButtonView extends LinearLayout {

    @BindView(R.id.mainButton)
    Button mainButton;
    @BindView(R.id.subButton)
    Button subButton;

    private LinearLayout.LayoutParams smallSize;
    private LinearLayout.LayoutParams bigSize;

    public ButtonView(Context context) {
        super(context);
        init(context);
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_steps_button, this);
        setOrientation(HORIZONTAL);
        ButterKnife.bind(this);
        setupView();
    }

    private void setupView() {
        final int btnSize = 260;
        final int btnSmallSize = 80;

        bigSize = new LinearLayout.LayoutParams(btnSize, btnSize);
        smallSize = new LinearLayout.LayoutParams(btnSmallSize, btnSmallSize);
        mainButton.setLayoutParams(bigSize);
        bigSize.setMargins(0,0,0,20);
        smallSize.setMargins(0,0,0,20);
    }

    public Button getMainButton() {
        return mainButton;
    }

    public Button getSubButton() {
        return subButton;
    }

    public LayoutParams getSmallButtonSizeStyle() {
        return smallSize;
    }

    public LayoutParams getBigButtonSizeStyle() {
        return bigSize;
    }

    public int smallTextSize() {
        final int smallTextSize = 16;
        return smallTextSize;
    }

    public int textSize() {
        int textSize = 25;
        return textSize;
    }
}
