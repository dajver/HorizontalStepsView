package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.horizontalsteps.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 6/21/17.
 */

public class ButtonStepsView extends LinearLayout {

    @BindView(R.id.buttonsView)
    LinearLayout buttonsView;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView scrollView;

    private ButtonView buttonView;

    private int counter = 0;
    private String stepNumber = "1";
    private Context context;

    private Listener listener;

    private List<Button> allMainBtns;
    private List<Button> allSubBtns;

    public ButtonStepsView(Context context) {
        super(context);
        init(context);
    }

    public ButtonStepsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ButtonStepsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        inflate(context, R.layout.view_buttons_steps, this);
        setOrientation(VERTICAL);
        ButterKnife.bind(this);

        allMainBtns = new ArrayList<>();
        allSubBtns = new ArrayList<>();
    }

    public void createButton() {
        listener.onUpdateAdapter();

        buttonView = new ButtonView(context);
        buttonsView.addView(addBigButton());

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    public View addBigButton() {
        counter++;

        stepNumber = String.valueOf(counter);
        listener.onStepCounterChanged(stepNumber);

        buttonView.getMainButton().setId(counter);
        buttonView.getMainButton().setText(String.valueOf(counter));
        buttonView.getMainButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
        allMainBtns.add(buttonView.getMainButton());
        allSubBtns.add(buttonView.getSubButton());

        makeAllButtonsSmall(allMainBtns);
        return buttonView;
    }

    public void addSubBtn() {
        listener.onUpdateAdapter();

        stepNumber = String.valueOf(String.format("%d.1", counter));
        listener.onStepCounterChanged(stepNumber);

        buttonView.getSubButton().setVisibility(View.VISIBLE);
        buttonView.getSubButton().setText(String.format("%d.1", counter));
        buttonView.getSubButton().setId(counter);
        buttonView.getSubButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubButtonClick(view);
            }
        });
        makeAllButtonsSmall(allSubBtns);
    }

    private void makeAllButtonsSmall(List<Button> buttons) {
        for (int i = 0; i < allMainBtns.size(); i++) {
            allMainBtns.get(i).setLayoutParams (buttonView.getSmallButtonSizeStyle());
            allMainBtns.get(i).setTextSize(buttonView.smallTextSize());

            allSubBtns.get(i).setLayoutParams (buttonView.getSmallButtonSizeStyle());
            allSubBtns.get(i).setTextSize(buttonView.smallTextSize());
        }
        if(buttons != null) {
            buttons.get(buttons.size() - 1).setLayoutParams(buttonView.getBigButtonSizeStyle());
            buttons.get(buttons.size() - 1).setTextSize(buttonView.textSize());
        }
    }

    private void onButtonClick(View v) {
        stepNumber = ((TextView) v).getText().toString();
        listener.onStepCounterChanged(stepNumber);

        makeAllButtonsSmall(null);
        allMainBtns.get(v.getId() - 1).setLayoutParams (buttonView.getBigButtonSizeStyle());
        allMainBtns.get(v.getId() - 1).setTextSize(buttonView.textSize());

        listener.selectData();
    }

    private void onSubButtonClick(View v) {
        stepNumber = ((TextView) v).getText().toString();
        listener.onStepCounterChanged(stepNumber);

        makeAllButtonsSmall(null);
        allSubBtns.get(v.getId() - 1).setLayoutParams (buttonView.getBigButtonSizeStyle());
        allSubBtns.get(v.getId() - 1).setTextSize(buttonView.textSize());

        listener.selectData();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onUpdateAdapter();
        void onStepCounterChanged(String stepNumber);
        void selectData();
    }
}
