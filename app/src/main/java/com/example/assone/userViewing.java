package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class userViewing extends AppCompatActivity {


    /*
    this activity will be used in the combination with the recycler view hence we will need to
    singletons to be able to retrieve the prac grader and the current user
     */
    private static Graph pracGrader;
    private static String currUserName;
    private static TextView userBanner;
    private static String clickedPerson;
    private static String currSelPrac;
    //a tag whcih is going to be used for debuggin purposes for this programme
    private static final String TAG = "userViewing.";

    public enum state {
        practical,
        view,
        practicalList,
        practicalView
    }

    private static state currMode;

    // changing the behaviour of the back button, instead of it going to the last thing, the back
    // button is going to go back to the last activity

    @Override
    public void onBackPressed()
    {
        Intent intent;
        switch (currMode)
        {
            case view: case practical:
                intent = new Intent(userViewing.this, UserHomePage.class);
                intent.putExtra("pracGrader", pracGrader);
                intent.putExtra("currUser", currUserName);
                //setting it back to none mode so it won't recreate the view activity all over again
                UserHomePage.none();
                startActivity(intent);
                break;
            case practicalList:
                // the thing which created us is going to be the user details page of the user.
                // hence, go back to the user detail page which created us
                intent = new Intent(userViewing.this, Details.class);
                intent.putExtra("pracGrader", pracGrader);
                intent.putExtra("currUser", currUserName);
                intent.putExtra("clickedPerson", clickedPerson);
                startActivity(intent);
                break;

            case practicalView:
                // we want to go back to the list of the available practicals for the current user
                //TODO: you will need to actually test this and make sure that it works
                intent = new Intent(userViewing.this, userViewing.class);
                userViewing.practicalList();
                intent.putExtra("pracGrader", pracGrader);
                intent.putExtra("currUser", currUserName);
                intent.putExtra("clickedPerson", clickedPerson);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_viewing);

        //reading in the passed in pracGrader and currUser from the previous activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");

        if (currMode == state.practicalList)
        {
            //we should be expecting a clicked person string into this activity
            clickedPerson = getIntent().getStringExtra("clickedPerson");
        }

        Log.e(TAG, "I have received your lord: " + currUserName);
        userBanner = findViewById(R.id.bannerUserViewing);

        FragmentManager fm = getSupportFragmentManager();

        //I am going to be using this activity to view teh list of users, and to add the practicals to all the students
        switch (currMode)
        {
            case view:
                // we've set everythign in a fragment, we don't have to do this. Although it's going to be
                // a lot easier to maintain our code because fragments permit greater UI design flexibility
                userBanner.setText("View Users");
                userViewList frag  = (userViewList) fm.findFragmentById(R.id.viewingContainer);

                //if they is nothing going to be attached to the current framgent
                if (frag == null) {
                    //actually committing the fragment and making it show on the screen
                    frag = new userViewList();
                    fm.beginTransaction()
                            .add(R.id.viewingContainer, frag)
                            .commit();
                }
                break;
            case practical:
                userBanner.setText("Creating Practical");

                practicalViewing fragPrac = (practicalViewing)  fm.findFragmentById(R.id.viewingContainer);

                if (fragPrac == null) {
                    //actually committing the fragment and making it show on the screen
                    fragPrac = new practicalViewing();
                    fm.beginTransaction()
                            .add(R.id.viewingContainer, fragPrac)
                            .commit();
                }
                break;

            case practicalList:
                userBanner.setText("Practicals");
                userViewList fragList = (userViewList) fm.findFragmentById(R.id.viewingContainer);

                if (fragList == null)
                {
                    fragList = new userViewList();
                    userViewList.practicalLoad();
                    fm.beginTransaction()
                            .add(R.id.viewingContainer, fragList)
                            .commit();
                }
                break;

            case practicalView:
                userBanner.setText("Practical Details");

                // attaching the practival viewing fragment, so you can see the current practical which has
                // being set

                currSelPrac = getIntent().getStringExtra("clickedPractical");
                Log.e(TAG, "SELECTED PRACTICAL: " + currSelPrac);

                practicalViewing fragPracView = (practicalViewing) fm.findFragmentById(R.id.viewingContainer);

                if (fragPracView == null)
                {
                    //actually comitting the fragment and making it show on the screen
                    fragPracView = new practicalViewing();
                    practicalViewing.editScore();
                    fm.beginTransaction()
                            .add(R.id.viewingContainer, fragPracView)
                            .commit();
                }

                break;
        }
    }


    public static String getClickedPrac()
    {
        return currSelPrac;
    }
    public static void practicalView()
    {
        currMode = state.practicalView;
    }

    public static void practicalList()
    {
        currMode = state.practicalList;
    }
    public static void practical()
    {
        currMode = state.practical;
    }

    public static void view()
    {
        currMode = state.view;
    }

    //being  able to grab the current data from whatever fragment which we're currently in
    public static Graph getGraph()
    {
        return pracGrader;
    }

    public static String getCurrUser()
    {
        return currUserName;
    }

    //being able to set data from whatever fragment which we're currently going to be in

    public static void setGraph(Graph inPracGrader)
    {
        pracGrader = inPracGrader;
    }
}