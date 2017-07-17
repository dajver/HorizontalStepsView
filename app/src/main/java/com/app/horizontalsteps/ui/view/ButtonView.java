package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
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
    @BindView(R.id.lineView)
    View lineView;

    @BindDimen(R.dimen.mainButtonSize)
    int mainButtonSize;
    @BindDimen(R.dimen.mainButtonTextSize)
    int mainButtonTextSize;
    @BindDimen(R.dimen.smallButtonSize)
    int smallButtonSize;
    @BindDimen(R.dimen.smallButtonTextSize)
    int smallButtonTextSize;
    @BindDimen(R.dimen.buttonPadding)
    int buttonPadding;
    @BindDimen(R.dimen.bigSubButtonPaddingBottom)
    int bigSubButtonPadding;
    @BindDimen(R.dimen.lineLongSize)
    int lineLongSize;
    @BindDimen(R.dimen.lineNormalSize)
    int lineNormalSize;
    @BindDimen(R.dimen.lineHeight)
    int lineHeight;
    @BindDimen(R.dimen.paddingOfLine)
    int paddingOfLine;

    private LinearLayout.LayoutParams smallSize;
    private LinearLayout.LayoutParams bigSize;
    private LinearLayout.LayoutParams bigSubButton;

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
        bigSize.setMargins(0, 0, 0, buttonPadding);
        smallSize = new LinearLayout.LayoutParams(smallButtonSize, smallButtonSize);
        smallSize.setMargins(0, 0, 0, buttonPadding);
        mainButton.setLayoutParams(bigSize);

        bigSubButton = new LinearLayout.LayoutParams(mainButtonSize, mainButtonSize);
        bigSubButton.setMargins(0, 0, 0, bigSubButtonPadding);
    }

    public Button getMainButton() {
        return mainButton;
    }

    public Button getSubButton() {
        return subButton;
    }

    public View getLineView() {
        return lineView;
    }

    public LayoutParams getSmallButtonSizeStyle() {
        return smallSize;
    }

    public LayoutParams getBigButtonSizeStyle() {
        return bigSize;
    }

    public LayoutParams getBigSubButton() {
        return bigSubButton;
    }

    public LayoutParams getLineNormalSize() {
        LayoutParams params = new LayoutParams(lineNormalSize, lineHeight);
        params.setMargins(0, paddingOfLine, 0, 0);
        return params;
    }

    public LayoutParams getLineLongSize() {
        LayoutParams params = new LayoutParams(lineLongSize, lineHeight);
        params.setMargins(0, paddingOfLine, 0, 0);
        return params;
    }

    public int smallTextSize() {
        return smallButtonTextSize;
    }

    public int textSize() {
        return mainButtonTextSize;
    }
}
