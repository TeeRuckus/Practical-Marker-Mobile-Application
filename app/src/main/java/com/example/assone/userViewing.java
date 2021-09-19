package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class userViewing extends AppCompatActivity {


    /*
    this activity will be used in the combination with the recycler view hence we will need to
    singletons to be able to retrieve the prac grader and the current user
     */
    private static Graph pracGrader;
    private static String currUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_viewing);

        //reading in the passed in pracGrader and currUser from the previous activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUserName = getIntent().getStringExtra("currUser");
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