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
import com.app.horizontalsteps.ui.db.items.StepsDataIModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by gleb on 6/21/17.
 */

public class ButtonStepsView extends LinearLayout {

    @BindView(R.id.buttonsView)
    LinearLayout buttonsView;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView hScroll;

    private ButtonView buttonView;

    private int counter = 0;
    private String stepNumber = "1";
    private Context context;

    private Listener listener;

    private LinearLayout.LayoutParams sp;
    private LinearLayout.LayoutParams vp;

    private List<Button> allMainBtns;
    private List<Button> allSubBtns;

    private RealmResults<StepsDataIModel> recData;

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

    public void createButtons() {
        listener.onUpdateAdapter();

        buttonView = new ButtonView(context);
        buttonsView.addView(initButtons());

        hScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
    }

    public View initButtons() {
        counter++;

        stepNumber = String.valueOf(counter);
        listener.onStepCounterChanged(stepNumber);

        final int btnSize = 260;
        final int btnSmallSize = 80;
        final int textSize = 25;
        final int smallTextSize = 16;

        vp = new LinearLayout.LayoutParams(btnSize, btnSize);
        sp = new LinearLayout.LayoutParams(btnSmallSize, btnSmallSize);
        buttonView.getMainButton().setLayoutParams(vp);
        vp.setMargins(0,0,0,20);
        sp.setMargins(0,0,0,20);
        buttonView.getMainButton().setId(counter);
        buttonView.getMainButton().setText("" + counter);
        buttonView.getMainButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber = ((TextView) v).getText().toString();
                listener.onStepCounterChanged(stepNumber);

                for (int i = 0; i < allMainBtns.size(); i++) {
                    allMainBtns.get(i).setLayoutParams (sp);
                    allMainBtns.get(i).setTextSize(smallTextSize);

                    allSubBtns.get(i).setLayoutParams (sp);
                    allSubBtns.get(i).setTextSize(smallTextSize);
                }
                allMainBtns.get(v.getId() - 1).setLayoutParams (vp);
                allMainBtns.get(v.getId() - 1).setTextSize(textSize);

                listener.selectData();
            }
        });
        allMainBtns.add(buttonView.getMainButton());
        allSubBtns.add(buttonView.getSubButton());

        for (int i = 0; i < allMainBtns.size(); i++) {
            allMainBtns.get(i).setLayoutParams (sp);
            allMainBtns.get(i).setTextSize(smallTextSize);

            allSubBtns.get(i).setLayoutParams (sp);
            allSubBtns.get(i).setTextSize(smallTextSize);
        }
        allMainBtns.get(allMainBtns.size() - 1).setLayoutParams(vp);
        allMainBtns.get(allMainBtns.size() - 1).setTextSize(textSize);

        return buttonView;
    }

    public void addSubBtn() {
        final int textSize = 25;
        final int smallTextSize = 16;

        listener.onUpdateAdapter();

        stepNumber = String.valueOf(counter + " ext");
        listener.onStepCounterChanged(stepNumber);

        buttonView.getSubButton().setVisibility(View.VISIBLE);
        buttonView.getSubButton().setText(counter + " ext");
        buttonView.getSubButton().setId(counter);
        buttonView.getSubButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber = ((TextView) v).getText().toString();
                listener.onStepCounterChanged(stepNumber);

                for (int i = 0; i < allSubBtns.size(); i++) {
                    allMainBtns.get(i).setLayoutParams (sp);
                    allMainBtns.get(i).setTextSize(smallTextSize);

                    allSubBtns.get(i).setLayoutParams (sp);
                    allSubBtns.get(i).setTextSize(smallTextSize);
                }

                allSubBtns.get(v.getId() - 1).setLayoutParams (vp);
                allSubBtns.get(v.getId() - 1).setTextSize(textSize);

                listener.selectData();
            }
        });
        for (int i = 0; i < allSubBtns.size(); i++) {
            allMainBtns.get(i).setLayoutParams (sp);
            allMainBtns.get(i).setTextSize(smallTextSize);

            allSubBtns.get(i).setLayoutParams (sp);
            allSubBtns.get(i).setTextSize(smallTextSize);
        }

        allSubBtns.get(allSubBtns.size() - 1).setLayoutParams(vp);
        allSubBtns.get(allSubBtns.size() - 1).setTextSize(textSize);
    }

    public void setRecData(RealmResults<StepsDataIModel> recData) {
        this.recData = recData;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
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
