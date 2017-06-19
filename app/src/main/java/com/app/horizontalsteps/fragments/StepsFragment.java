package com.app.horizontalsteps.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.horizontalsteps.CreateStepActivity;
import com.app.horizontalsteps.R;
import com.app.horizontalsteps.adapter.StepsAdapter;
import com.app.horizontalsteps.db.StepsController;
import com.app.horizontalsteps.db.items.StepsDataIModel;
import com.app.horizontalsteps.view.ButtonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class StepsFragment extends Fragment {

    private static final int RESULT_CODE_CREATE_STEP = 3;

    private int counter = 0;
    private int audioCounter = 0;

    private List<Button> allMainBtns;
    private List<Button> allSubBtns;

    @BindView(R.id.buttonsView)
    LinearLayout buttonsView;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView hScroll;
    @BindView(R.id.listView2)
    ListView list;

    private ButtonView buttonView;
    private StepsAdapter adapter;
    private RealmResults<StepsDataIModel> recData;

    private long lastWriteID;
    private String stepNumber = "1";

    private LinearLayout.LayoutParams sp;
    private LinearLayout.LayoutParams vp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, rootView);

        allMainBtns = new ArrayList<>();
        allSubBtns = new ArrayList<>();

        createButtons();

        recData = new StepsController(getActivity()).getInfo(stepNumber, String.valueOf(lastWriteID));
        adapter = new StepsAdapter(getActivity(), R.layout.item_steps, recData);
        list.setAdapter(adapter);

        return rootView;
    }

    @OnClick(R.id.nextBtn)
    public void onNextClick() {
        audioCounter = 0;

        Intent i = new Intent(getActivity(), CreateStepActivity.class);
        startActivityForResult(i, RESULT_CODE_CREATE_STEP);
    }

    @OnClick(R.id.recBtn)
    public void onRecClick() {
        audioCounter++;

        String fileName = "Item_" + String.format("%03d", audioCounter);
        new StepsController(getActivity()).addInfo(String.valueOf(lastWriteID), stepNumber, "let", fileName);
        selectData();
    }

    private void selectData() {
        recData = new StepsController(getActivity()).getInfo(stepNumber, String.valueOf(lastWriteID));
        if(recData.size() != 0) {
            for (int i = 0; i < recData.size(); i++) {
                adapter = new StepsAdapter(getActivity(), R.layout.item_steps, recData);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else {
            list.setAdapter(null);
        }
    }

    private void createButtons() {
        list.setAdapter(null);

        buttonView = new ButtonView(getActivity());
        buttonsView.addView(initButtons());

        hScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
    }

    private View initButtons() {
        counter++;

        stepNumber = String.valueOf(counter);

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
        buttonView.getMainButton().setText(""+counter);
        buttonView.getMainButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber = ((TextView) v).getText().toString();

                for (int i = 0; i < allMainBtns.size(); i++) {
                    allMainBtns.get(i).setLayoutParams (sp);
                    allMainBtns.get(i).setTextSize(smallTextSize);

                    allSubBtns.get(i).setLayoutParams (sp);
                    allSubBtns.get(i).setTextSize(smallTextSize);
                }
                allMainBtns.get(v.getId() - 1).setLayoutParams (vp);
                allMainBtns.get(v.getId() - 1).setTextSize(textSize);

                selectData();
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

    private void addSubBtn() {
        final int textSize = 25;
        final int smallTextSize = 16;

        list.setAdapter(null);

        stepNumber = String.valueOf(counter + " ext");

        buttonView.getSubButton().setVisibility(View.VISIBLE);
        buttonView.getSubButton().setText(counter + " ext");
        buttonView.getSubButton().setId(counter);
        buttonView.getSubButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber = ((TextView) v).getText().toString();

                for (int i = 0; i < allSubBtns.size(); i++) {
                    allMainBtns.get(i).setLayoutParams (sp);
                    allMainBtns.get(i).setTextSize(smallTextSize);

                    allSubBtns.get(i).setLayoutParams (sp);
                    allSubBtns.get(i).setTextSize(smallTextSize);
                }

                allSubBtns.get(v.getId() - 1).setLayoutParams (vp);
                allSubBtns.get(v.getId() - 1).setTextSize(textSize);

                selectData();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE_CREATE_STEP: {
                if (resultCode == Activity.RESULT_OK) {
                    if(resultCode == getActivity().RESULT_OK) {
                        String result = data.getStringExtra(CreateStepActivity.RESULT);
                        if(result.equals(CreateStepActivity.RESULT_SECTION)) {
                            createButtons();
                        } else if(result.equals(CreateStepActivity.RESULT_TRIGGER)) {
                            createButtons();
                        } else if(result.equals(CreateStepActivity.RESULT_UNDER_TOUR)) {
                            addSubBtn();
                        }
                    }
                }
            } break;
        }
    }
}