package com.app.horizontalsteps.ui.view;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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

public class ButtonStepsView extends LinearLayout {

    @BindView(R.id.buttonsView)
    LinearLayout buttonsView;
    @BindView(horizontalScrollView)
    ObservableHorizontalScrollView scrollView;

    private ButtonView buttonView;

    private int selectedStep = 0;
    private int buttonNumberCounter = 0;
    private String stepNumber = "1";
    private Context context;

    private Listener listener;

    private List<Button> allMainBtns;
    private List<Button> allSubBtns;
    private List<View> allLinesViews;
    private List<ButtonView> buttonViewList;

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
        buttonViewList = new ArrayList<>();
    }

    public void addMainButton() {
        listener.onUpdateAdapter();
        buttonNumberCounter++;
        selectedStep = buttonNumberCounter - 1;
        stepNumber = String.valueOf(buttonNumberCounter);

        buttonView = new ButtonView(context);
        buttonView.getMainButton().setId(buttonNumberCounter);
        buttonView.getMainButton().setText(stepNumber);
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
        buttonViewList.add(buttonView);

        makeAllButtonsSmall(buttonView, allMainBtns);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    public void addSubBtn() {
        listener.onUpdateAdapter();
        stepNumber = String.valueOf(String.format("%d.1", selectedStep + 1));

        buttonViewList.get(selectedStep).getSubButton().setVisibility(View.VISIBLE);
        buttonViewList.get(selectedStep).getSubButton().setText(stepNumber);
        buttonViewList.get(selectedStep).getSubButton().setId(selectedStep);
        buttonViewList.get(selectedStep).getSubButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view, allSubBtns);
            }
        });
        makeAllButtonsSmall(buttonViewList.get(selectedStep).getSubButton(), allSubBtns);
    }

    private void onButtonClick(final View v, final List<Button> buttons) {
        stepNumber = ((TextView) v).getText().toString();
        selectedStep = v.getId() - 1;

        makeAllButtonsSmall(v, null);
        if(buttons == allSubBtns) {
            buttons.get(selectedStep + 1).setLayoutParams(buttonView.getBigSubButton());
            allLinesViews.get(selectedStep + 1).setLayoutParams(buttonView.getLineLongSize());
            buttons.get(selectedStep + 1).setTextSize(buttonView.textSize());
        } else {
            buttons.get(selectedStep).setLayoutParams(buttonView.getBigButtonSizeStyle());
            allLinesViews.get(selectedStep).setLayoutParams(buttonView.getLineNormalSize());
            buttons.get(selectedStep).setTextSize(buttonView.textSize());
        }

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollToView(scrollView, buttons.get(selectedStep));
            }
        }, 100L);

        listener.selectData();
    }

    private void scrollToView(HorizontalScrollView scrollViewParent, View view) {
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        scrollViewParent.smoothScrollTo(childOffset.x, 0);
    }

    private void getDeepChildOffset(ViewGroup mainParent, ViewParent parent, View child, Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    private void makeAllButtonsSmall(View v, List<Button> clickedButtons) {
        setButtonsStyle(allMainBtns);
        setButtonsStyle(allSubBtns);
        //instead of one which was clicked
        if(clickedButtons != null) {
            int id = v.getId();
            if(clickedButtons == allSubBtns) {
                clickedButtons.get(id).setLayoutParams(buttonView.getBigSubButton());
                allLinesViews.get(id).setLayoutParams(buttonView.getLineLongSize());
                clickedButtons.get(id).setTextSize(buttonView.textSize());
            } else {
                clickedButtons.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getBigButtonSizeStyle());
                allLinesViews.get(clickedButtons.size() - 1).setLayoutParams(buttonView.getLineNormalSize());
                clickedButtons.get(clickedButtons.size() - 1).setTextSize(buttonView.textSize());
            }
        }
    }

    private void setButtonsStyle(List<Button> buttons) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setLayoutParams(buttonView.getSmallButtonSizeStyle());
            buttons.get(i).setTextSize(buttonView.smallTextSize());
            allLinesViews.get(i).setLayoutParams(buttonView.getLineNormalSize());
        }
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onUpdateAdapter();
        void selectData();
    }
}
