package com.app.horizontalsteps.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.horizontalsteps.CreateStepActivity;
import com.app.horizontalsteps.R;
import com.app.horizontalsteps.ui.adapter.StepsAdapter;
import com.app.horizontalsteps.ui.db.StepsController;
import com.app.horizontalsteps.ui.db.items.StepsDataIModel;
import com.app.horizontalsteps.ui.view.ButtonStepsView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class StepsFragment extends Fragment implements ButtonStepsView.Listener {

    private static final int RESULT_CODE_CREATE_STEP = 3;

    @BindView(R.id.listView2)
    ListView listView;
    @BindView(R.id.buttonsStepsView)
    ButtonStepsView buttonStepsView;

    private StepsAdapter adapter;
    private RealmResults<StepsDataIModel> recData;

    private int itemsCount = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recData = new StepsController(getActivity()).getInfo(buttonStepsView.getStepNumber());
        adapter = new StepsAdapter(getActivity(), recData);
        listView.setAdapter(adapter);

        buttonStepsView.setListener(this);
        buttonStepsView.addMainButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.nextBtn)
    public void onNextClick() {
        itemsCount = 0;

        Intent i = new Intent(getActivity(), CreateStepActivity.class);
        startActivityForResult(i, RESULT_CODE_CREATE_STEP);
    }

    @OnClick(R.id.recBtn)
    public void onRecClick() {
        itemsCount++;

        String fileName = "Item_" + String.format("%03d", itemsCount);
        new StepsController(getActivity()).addInfo(buttonStepsView.getStepNumber(), fileName);
        selectData();
    }

    public void selectData() {
        recData = new StepsController(getActivity()).getInfo(buttonStepsView.getStepNumber());
        if(recData.size() != 0) {
            adapter = new StepsAdapter(getActivity(), recData);
            listView.setAdapter(adapter);
        } else
            listView.setAdapter(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE_CREATE_STEP:
                if (resultCode == Activity.RESULT_OK) {
                    if(resultCode == getActivity().RESULT_OK) {
                        String result = data.getStringExtra(CreateStepActivity.RESULT);
                        if(result.equals(CreateStepActivity.RESULT_SECTION)) {
                            buttonStepsView.addMainButton();
                        } else if(result.equals(CreateStepActivity.RESULT_UNDER_TOUR)) {
                            buttonStepsView.addSubBtn();
                        }
                    }
                }
            break;
        }
    }

    @Override
    public void onUpdateAdapter() {
        listView.setAdapter(null);
    }
}