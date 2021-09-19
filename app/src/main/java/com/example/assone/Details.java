package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Details extends AppCompatActivity {
    private Graph pracGrader;
    private String currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //when this is created we want to grab the data which was passed into this activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUser = getIntent().getStringExtra("currUser");
    }
}