package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UserHomePage extends AppCompatActivity {

    private Graph pracGrader;
    private String currUserName;
    private FragmentManager fm;
    private static final String TAG = "userHomePage.";
    private mainButtons mainBttnsFrag;
    private bottomButtonTray bottomBttnsFrag;

    //all the class fields of the UI
    private TextView userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");

        userDetails = findViewById(R.id.userDetails);
        char firstLetterCaptial = myUtils.getType(currUserName, pracGrader);

        switch(firstLetterCaptial)
        {
            case 'A':
                //the code for when the user is an admin
                userDetails.setText("Admin - " + currUserName);

                //attaching the appropriate fragments for teh admin
                fm = getSupportFragmentManager();
                mainBttnsFrag = (mainButtons) fm.findFragmentById(R.id.mainHomeArea);
                bottomBttnsFrag = (bottomButtonTray) fm.findFragmentById(R.id.buttonCluster);

                //we don't really care what was attached their, we want to override it
                mainBttnsFrag = new mainButtons();
                bottomBttnsFrag = new bottomButtonTray();

                //staging for the data I want to pass into teh fragment
                Bundle argsMainBttn = new Bundle();
                Bundle argsCluster = new Bundle();

                argsMainBttn.putString("currUser", currUserName);
                argsMainBttn.putSerializable("pracGrader", pracGrader);

                argsCluster.putString("currUser", currUserName);
                argsCluster.putSerializable("pracGrader", pracGrader);

                //sending all the data off into the fragments
                mainBttnsFrag.setArguments(argsMainBttn);
                bottomBttnsFrag.setArguments(argsCluster);

                fm.beginTransaction().add(R.id.mainHomeArea, mainBttnsFrag).commit();
                fm.beginTransaction().add(R.id.buttonCluster, bottomBttnsFrag).commit();

                break;

            case 'I':
                //the code for when the user is an instructor
                userDetails.setText("Instructor - " + currUserName);

                break;

            case 'S':
                //the code for when the user is a student
                userDetails.setText("Student - " + currUserName);
                break;
        }
    }
}