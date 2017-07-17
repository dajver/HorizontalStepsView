package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.horizontalsteps.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.horizontalsteps.R.id.horizontalScrollView;

/**
 * Created by gleb on 6/21/17.
 */

public class ButtonStepsView extends LinearLayout implements ViewTreeObserver.OnPreDrawListener {

    @BindView(R.id.buttonsView)
    LinearLayout buttonsView;
    @BindView(horizontalScrollView)
    ObservableHorizontalScrollView scrollView;

    private ButtonView buttonView;

    private int counter = 0;
    private String stepNumber = "1";
    private Context context;

    private Listener listener;

    private List<Button> allMainBtns;
    private List<Button> allSubBtns;
    private List<View> allLinesViews;

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
        allLinesViews = new ArrayList<>();

        scrollView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public void addMainButton() {
        listener.onUpdateAdapter();

        counter++;

        stepNumber = String.valueOf(counter);
        listener.onStepCounterChanged(stepNumber);

        buttonView = new ButtonView(context);
        buttonView.getMainButton().setId(counter);
        buttonView.getMainButton().setText(String.valueOf(counter));
        buttonView.getMainButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v, allMainBtns);
            }
        });
        buttonsView.addView(buttonView);

        allMainBtns.add(buttonView.getMainButton());
        allSubBtns.add(buttonView.getSubButton());
        allLinesViews.add(buttonView.getLineView());

        makeAllButtonsSmall(allMainBtns);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
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
                onButtonClick(view, allSubBtns);
            }
        });
        makeAllButtonsSmall(allSubBtns);
    }

    private void onButtonClick(final View v, List<Button> buttons) {
        stepNumber = ((TextView) v).getText().toString();
        listener.onStepCounterChanged(stepNumber);

        makeAllButtonsSmall(null);
        if(buttons == allSubBtns) {
            buttons.get(v.getId() - 1).setLayoutParams(buttonView.getBigSubButton());
            allLinesViews.get(v.getId() - 1).setLayoutParams(buttonView.getLineLongSize());
        } else {
            buttons.get(v.getId() - 1).setLayoutParams(buttonView.getBigButtonSizeStyle());
            allLinesViews.get(v.getId() - 1).setLayoutParams(buttonView.getLineNormalSize());
        }
        buttons.get(v.getId() - 1).setTextSize(buttonView.textSize());

        listener.selectData();
    }

    private void makeAllButtonsSmall(List<Button> clickedButtons) {
        setButtonsStyle(allMainBtns);
        setButtonsStyle(allSubBtns);
        //instead of one which was clicked
        if(clickedButtons != null) {
            if(clickedButtons == allSubBtns) {
                clickedButtons.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getBigSubButton());
                allLinesViews.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getLineLongSize());
            } else {
                clickedButtons.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getBigButtonSizeStyle());
                allLinesViews.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getLineNormalSize());
            }
            clickedButtons.get(clickedButtons.size() - 1).setTextSize(buttonView.textSize());
        }
    }

    private void setButtonsStyle(List<Button> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setLayoutParams(buttonView.getSmallButtonSizeStyle());
            buttons.get(i).setTextSize(buttonView.smallTextSize());
            allLinesViews.get(i).setLayoutParams(buttonView.getLineNormalSize());
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onPreDraw() {
        scrollView.getViewTreeObserver().removeOnPreDrawListener(this);

        LinearLayout child = (LinearLayout) scrollView.getChildAt(0);

        int width = scrollView.getWidth();
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(width / 2, LinearLayout.LayoutParams.MATCH_PARENT);

        View leftSpacer = new View(getContext());
        leftSpacer.setLayoutParams(p);
        child.addView(leftSpacer, 0);

        View rightSpacer = new View(getContext());
        rightSpacer.setLayoutParams(p);
        child.addView(rightSpacer);

        return false;
    }

    public interface Listener {
        void onUpdateAdapter();
        void onStepCounterChanged(String stepNumber);
        void selectData();
    }
}
