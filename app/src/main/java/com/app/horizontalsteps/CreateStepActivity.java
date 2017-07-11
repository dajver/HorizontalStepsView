package com.app.horizontalsteps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateStepActivity extends Activity {

    public static final String RESULT = "result";
    public static final String RESULT_SECTION = "createSection";
    public static final String RESULT_UNDER_TOUR = "createUnderTour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_step);
        ButterKnife.bind(this);
    }

   @OnClick(R.id.sectionBtn)
   public void onSectionClick() {
       Intent returnIntent = new Intent();
       returnIntent.putExtra(RESULT, RESULT_SECTION);
       setResult(RESULT_OK, returnIntent);
       finish();
   }

    @OnClick(R.id.underTourBtn)
    public void onSubClick() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(RESULT, RESULT_UNDER_TOUR);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
