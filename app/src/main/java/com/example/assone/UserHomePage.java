/*
TODO:
    - some bugs which I will have to come back andd actually fix
       - so when you add a student whcich is going to have the same name as the admin, it
       will over rid that admin like nothing had happen
 */
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
    public void onBackPressed() {
        //super.onBackPressed();
        //we want when the user presses the back button the same thing as the leave button
        Intent intent = new Intent(UserHomePage.this, MainActivity.class);
        intent.putExtra("pracGrader", pracGrader);
        startActivity(intent);
    }

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

                switch(firstLetterCaptial)
                {
                    case 'A':
                        userDetails.setText("Admin - " + currUserName);
                        broadCastMessage();

                        break;

                    case 'I':
                        //the code for when the user is an instructor
                        userDetails.setText("Tutor - " + currUserName);

                        broadCastMessage();

                        switch(currUse)
                        {
                            case practical:
                                mainButtons.practical();
                                recreate();
                                break;
                            case student:
                                mainButtons.student();
                                recreate();
                                break;
                        }

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

    //this to display with a toast which button is going to be selected from the bottom tray of the programme
    public void broadCastMessage()
    {
        Context cntx = getApplicationContext();
        CharSequence text;
        int duration;
        Toast toast;

        switch(currUse)
        {
            case practical:
                text = "practical selected";
                duration = Toast.LENGTH_SHORT;
                toast = Toast.makeText(cntx, text, duration);
                toast.show();
                practicalSelect();
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

    }

    // a function which is going to handle the functionality of practicals depending on the
    public void practicalSelect()
    {
        Intent intent;
        char firstLetterCapital = myUtils.getType(currUserName, pracGrader);
        switch (firstLetterCapital)
        {
            case 'A':
                //the admin should be able to add practicals for all the students in the graph structure
                //TODO: come and finish impelmenting your practicals


                break;

            case 'I':
                break;

            case 'S':
                break;
        }

    }

    //this is the funciton which is actually going to perform the requested action of the programme
    public void userModeSelect()
    {
        Intent intent;
        //determing who the user is so we can set up the next fragment with the correct information

        char firstLetterCapital = myUtils.getType(currUserName, pracGrader);
        switch(firstLetterCapital)
        {
            case 'A':
                switch(currMode)
                {
                    case add:
                        intent = setUpRegistration();
                        userRegistrationSelect();
                        Register.adminAdd();
                        startActivity(intent);
                        break;

                    case view:
                        //starting the activity which will allow you to view the users of the programme
                        intent = setUpUserViewing();
                        //if this is called from the admin, we should load the data in realtion to the admin
                        userViewList.admin();

                        //TODO: you will probably have to add more code here to make this more functional here
                        startActivity(intent);
                        break;
                }
                break;

            case 'I':
                /*
                we will need to send over who the current instructor is going to be, so the newly
                registered student can be connected by and edge the owning instructor
                 */
                switch(currMode)
                {
                    case add:
                        intent = setUpRegistration();
                        Register.instructorAdd();
                        intent.putExtra("currInstructor", currUserName);
                        startActivity(intent);
                        break;
                }
                break;

        }
    }

    //to push the prac grader, and the current user given any intent
    public void sendInfo(Intent currIntent)
    {
        currIntent.putExtra("pracGrader", pracGrader);
        currIntent.putExtra("currUser", currUserName);
    }

    //to open up the viewing acitivity, so the user can view the uesrs of the graph
    public Intent setUpUserViewing()
    {
        Intent intent = new Intent(UserHomePage.this, userViewing.class);
        sendInfo(intent);
        return intent;
    }

    //to open up the user registration form so new users can be creted into the programme
    public Intent setUpRegistration()
    {
        Intent intent = new Intent(UserHomePage.this, Register.class);
        sendInfo(intent);
        return intent;
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