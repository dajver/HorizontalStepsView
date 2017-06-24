package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.horizontalsteps.R;

import butterknife.BindDimen;
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

    @BindDimen(R.dimen.mainButtonSize)
    int mainButtonSize;
    @BindDimen(R.dimen.mainButtonTextSize)
    int mainButtonTextSize;
    @BindDimen(R.dimen.smallButtonSize)
    int smallButtonSize;
    @BindDimen(R.dimen.smallButtonTextSize)
    int smallButtonTextSize;

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
        bigSize = new LinearLayout.LayoutParams(mainButtonSize, mainButtonSize);
        smallSize = new LinearLayout.LayoutParams(smallButtonSize, smallButtonSize);
        mainButton.setLayoutParams(bigSize);
        bigSize.setMargins(0, 0, 0, 20);
        smallSize.setMargins(0, 0, 0, 20);
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
        return smallButtonTextSize;
    }

    public int textSize() {
        return mainButtonTextSize;
    }
}
