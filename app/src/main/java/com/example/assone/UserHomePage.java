package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class UserHomePage extends AppCompatActivity {

    //all the class fields of the UI
    private Graph pracGrader;
    private String currUserName;
    private FragmentManager fm;
    private static final String TAG = "userHomePage.";
    private mainButtons mainBttnsFrag;
    private bottomButtonTray bottomBttnsFrag;
    private TextView userDetails;

    /*this is to allow communication between the between the bottom tray of buttons, and this activity
    therefore, if the state is changed in the bottom tray button fragment this activity is going to
    change activities back to the log in screen
     */
    private enum state {
            inUse,
            exit
    }

    //this enum to tell the activity which mode they're going to be in. This is going to be mainly
    //used by the admin and the tutors
    private enum mode {
        add,
        edit,
        delete,
        view,
        none
    }

    //the enum is to keep a track on what thing the current mode is going to be apply too
    private enum use {
        practical,
        student,
        tutor
    }

    private static state currState;
    private static mode currMode;
    private static use currUse;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");

        switch (currState)
        {
            case inUse:
                setUpFragments();
                userDetails = findViewById(R.id.userDetails);
                char firstLetterCaptial = myUtils.getType(currUserName, pracGrader);
                Context cntx = getApplicationContext();
                CharSequence text;
                int duration;
                Toast toast;

                switch(firstLetterCaptial)
                {
                    case 'A':
                        userDetails.setText("Admin - " + currUserName);

                        switch(currUse)
                        {
                            case practical:
                                text = "practical selected";
                                duration = Toast.LENGTH_SHORT;
                                toast = Toast.makeText(cntx, text, duration);
                                toast.show();
                                break;

                            case student:
                                text = "Student Selected";
                                duration = Toast.LENGTH_SHORT;
                                toast = Toast.makeText(cntx, text, duration);
                                toast.show();
                                userModeSelect();
                                break;

                            case tutor:
                                text = "Tutor selected";
                                duration = Toast.LENGTH_SHORT;
                                toast = Toast.makeText(cntx, text, duration);
                                toast.show();
                                userModeSelect();
                                break;
                        }

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
                Intent intent = new Intent(UserHomePage.this, MainActivity.class);
                intent.putExtra("pracGrader", pracGrader);
                startActivity(intent);
                break;
            default:
                Log.i(TAG, "I THINK I DID'T GET THE INVITE");
                break;
        }
    }

    public void userModeSelect()
    {
        switch(currMode)
        {
            case add:
                Intent intent = new Intent(UserHomePage.this, Register.class);
                intent.putExtra("pracGrader", pracGrader);
                intent.putExtra("currUser", currUserName);
                userRegistrationSelect();
                startActivity(intent);
                break;
        }
    }

    public void userRegistrationSelect()
    {
        switch(currUse)
        {
            case student:
                Register.student();
                break;
            case tutor:
                Register.instructor();
                break;
        }
    }

    public void setUpFragments()
    {
        //TODO: maybe this is a good place where you might want to recreate your whole entire current activity
        //attaching the appropriate fragments for the admin
        fm = getSupportFragmentManager();
        mainBttnsFrag = (mainButtons) fm.findFragmentById(R.id.mainHomeArea);
        bottomBttnsFrag = (bottomButtonTray) fm.findFragmentById(R.id.buttonCluster);

        //we don't really care what was attached their, we want to override it
        mainBttnsFrag = new mainButtons();
        bottomBttnsFrag = new bottomButtonTray();

        //staging for the data I want to pass into the fragment
        Bundle argsMainBttn = new Bundle();
        Bundle argsCluster = new Bundle();

        //passing the necessary information to the main buttons fragment
        argsMainBttn.putString("currUser", currUserName);
        argsMainBttn.putSerializable("pracGrader", pracGrader);

        //passing the necessary infromation which is needed to the bottom buttons
        argsCluster.putString("currUser", currUserName);
        argsCluster.putSerializable("pracGrader", pracGrader);

        //sending all the data off into there respective fragments
        mainBttnsFrag.setArguments(argsMainBttn);
        bottomBttnsFrag.setArguments(argsCluster);

        //actually finalising the fragments which were created
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

    public static void add()
    {
        currMode = mode.add;
    }

    public static void edit()
    {
        currMode = mode.edit;
    }

    public static void delete()
    {
        currMode = mode.delete;
    }

    public static void view()
    {
        currMode = mode.view;
    }

    public static void none() {currMode = mode.none;}

    public static void practical()
    {
        currUse = use.practical;
    }

    public static void student()
    {
        currUse = use.student;
    }

    public static void tutor()
    {
        currUse = use.tutor;
    }
}