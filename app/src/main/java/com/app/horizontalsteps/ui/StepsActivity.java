package com.app.horizontalsteps.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.horizontalsteps.R;

import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new StepsFragment()).commit();
    }
}