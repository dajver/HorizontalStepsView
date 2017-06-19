package com.app.horizontalsteps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CreateStepActivity extends Activity {

    public static final String RESULT = "result";
    public static final String RESULT_SECTION = "createSection";
    public static final String RESULT_TRIGGER = "createTrigger";
    public static final String RESULT_UNDER_TOUR = "createUnderTour";

    private ImageButton sectionBtn;
    private ImageButton triggerBtn;
    private ImageButton underTourBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_step);

        init();
        buttonsEvents();
    }

    private void init() {
        sectionBtn = (ImageButton) findViewById(R.id.sectionBtn);
        triggerBtn = (ImageButton) findViewById(R.id.triggerBtn);
        underTourBtn = (ImageButton) findViewById(R.id.underTourBtn);
    }

    private void buttonsEvents() {
        sectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, RESULT_SECTION);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        triggerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, RESULT_TRIGGER);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        underTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT, RESULT_UNDER_TOUR);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
