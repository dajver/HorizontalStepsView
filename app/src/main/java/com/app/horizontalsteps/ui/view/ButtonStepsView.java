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

    private int buttonNumberCounter = 0;
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
    }

    public void addMainButton() {
        listener.onUpdateAdapter();
        buttonNumberCounter++;
        stepNumber = String.valueOf(buttonNumberCounter);

        buttonView = new ButtonView(context);
        buttonView.getMainButton().setId(buttonNumberCounter);
        buttonView.getMainButton().setText(String.valueOf(buttonNumberCounter));
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
        stepNumber = String.valueOf(String.format("%d.1", buttonNumberCounter));

        buttonView.getSubButton().setVisibility(View.VISIBLE);
        buttonView.getSubButton().setText(String.format("%d.1", buttonNumberCounter));
        buttonView.getSubButton().setId(buttonNumberCounter);
        buttonView.getSubButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view, allSubBtns);
            }
        });
        makeAllButtonsSmall(allSubBtns);
    }

    private void onButtonClick(final View v, final List<Button> buttons) {
        stepNumber = ((TextView) v).getText().toString();

        makeAllButtonsSmall(null);
        if(buttons == allSubBtns) {
            buttons.get(v.getId() - 1).setLayoutParams(buttonView.getBigSubButton());
            allLinesViews.get(v.getId() - 1).setLayoutParams(buttonView.getLineLongSize());
        } else {
            buttons.get(v.getId() - 1).setLayoutParams(buttonView.getBigButtonSizeStyle());
            allLinesViews.get(v.getId() - 1).setLayoutParams(buttonView.getLineNormalSize());
        }
        buttons.get(v.getId() - 1).setTextSize(buttonView.textSize());

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollToView(scrollView, buttons.get(v.getId() - 1));
            }
        }, 100L);

        listener.selectData();
    }

    private void scrollToView(final HorizontalScrollView scrollViewParent, final View view) {
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        scrollViewParent.smoothScrollTo(childOffset.x / 2, 0);
    }

    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
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
