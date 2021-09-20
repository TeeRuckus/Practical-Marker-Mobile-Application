package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class userViewing extends AppCompatActivity {


    /*
    this activity will be used in the combination with the recycler view hence we will need to
    singletons to be able to retrieve the prac grader and the current user
     */
    private static Graph pracGrader;
    private static String currUserName;
    private static TextView userBanner;
    //a tag whcih is going to be used for debuggin purposes for this programme
    private static final String TAG = "userViewing.";

    public enum state {
        practical,
        view
    }

    private static state currMode;

    // changing the behaviour of the back button, instead of it going to the last thing, the back
    // button is going to go back to the last activity

    @Override
    public void onBackPressed()
    {
            // super.onBackPressed();
            Intent intent = new Intent(userViewing.this, UserHomePage.class);
            intent.putExtra("pracGrader", pracGrader);
            intent.putExtra("currUser", currUserName);
            //setting it back to none mode so it won't recreate the view activity all over again
            UserHomePage.none();
            startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_viewing);

        //reading in the passed in pracGrader and currUser from the previous activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");
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
        }
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