package com.example.public_art;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    WorkRecyclerView work1;
    WorkRecyclerView work2;
    RadioGroup rg;
    RadioButton rb1;
    RadioButton rb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout() {
        work1 = findViewById(R.id.workrv1);
        work2 = findViewById(R.id.workrv2);
        rg = findViewById(R.id.rg_select);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rb1.isChecked()) {
                    work1.setVisibility(View.VISIBLE);
                    work2.setVisibility(View.GONE);
                }

                if(rb2.isChecked()) {
                    work2.setVisibility(View.VISIBLE);
                    work1.setVisibility(View.GONE);
                }
            }
        });
        rb1.setChecked(true);

        work1.loadJson("1館.json");
        work2.loadJson("2館.json");
    }
}