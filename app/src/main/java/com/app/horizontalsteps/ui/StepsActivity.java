package com.app.horizontalsteps.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.app.horizontalsteps.R;

public class StepsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new StepsFragment()).commit();
    }
}