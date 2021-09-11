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
    private enum state {
            inUse,
            exit
    }

    private static state currState;

    //all the class fields of the UI
    private TextView userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");

        //currState = state.inUse;
        switch (currState)
        {
            case inUse:
                setUpFragments();
                userDetails = findViewById(R.id.userDetails);
                char firstLetterCaptial = myUtils.getType(currUserName, pracGrader);
                //firstLetterCaptial = 'I';
                switch(firstLetterCaptial)
                {
                    case 'A':
                        //the code for when the user is an admin
                        userDetails.setText("Admin - " + currUserName);
                        break;

                    case 'I':
                        //the code for when the user is an instructor
                        userDetails.setText("Tutor - " + currUserName);
                        break;

                    case 'S':
                        //the code for when the user is a student
                        userDetails.setText("Student - " + currUserName);
                        break;
                }

                break;

            case exit:
                //going back to the log in scree, with the current informaiton of the graph
                Log.i(TAG, "I AM READY TO LEAVE NIGGA");

                break;
            default:
                Log.i(TAG, "I THINK I DID'T GET THE INVITE");
                break;
        }


    }

    public void setUpFragments()
    {
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

        //TODO: the bottom button cluster doesn't even need the prac grader to be passed into it
        argsMainBttn.putString("currUser", currUserName);
        argsMainBttn.putSerializable("pracGrader", pracGrader);

        argsCluster.putString("currUser", currUserName);
        argsCluster.putSerializable("pracGrader", pracGrader);

        //sending all the data off into the fragments
        mainBttnsFrag.setArguments(argsMainBttn);
        bottomBttnsFrag.setArguments(argsCluster);

        fm.beginTransaction().add(R.id.mainHomeArea, mainBttnsFrag).commit();
        fm.beginTransaction().add(R.id.buttonCluster, bottomBttnsFrag).commit();


    }

    public static void leave()
    {
        //change the state to leave;
        currState = state.exit;
    }

    public static void inUse()
    {
        currState = state.inUse;
    }
}