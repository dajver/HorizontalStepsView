package com.app.horizontalsteps.view;

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
    }

    public Button getMainButton() {
        return mainButton;
    }

    public Button getSubButton() {
        return subButton;
    }
}
