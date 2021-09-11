package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //making a tag variable, so that more meaningful debuggin messages will appear on the screen
    private static final String TAG = "MainActivity.";
    private Button logInBttn;
    private EditText userName;
    private EditText passOne;
    private EditText passTwo;
    private EditText passThree;
    private EditText passFour;
    private TextView errorUserName;
    private TextView errorPassword;
    private static Boolean loaded = false;
    private Graph pracGrader;

    private enum state {
        New,
        initial,
        used
    }

    private static state currState = state.New;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if(!(loaded))
        {
            pracGrader = new Graph();
        }
        else
        {
            pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        }*/

        switch(currState)
        {
            case initial:
                //the graph structure is coming from the graph structure
                pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
                break;
            case New:
                pracGrader = new Graph();
                break;
        }

        //pracGrader = new Graph();
        Graph.Vertex adminNode = pracGrader.getVertex();

        //getting all the UI element from the actuall applicatio

        //if an admin node doesn't exit, we should launch the registration activity and create an admin for the system
        //TODO: refactor this code so it will be a switch case statement with an enumerator which is going to tell you waht state you're in
        if(adminNode == null)
        {
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("pracGrader", pracGrader);
            Register.initial();
            startActivity(intent);
        }
        else
        {
            getUIComponents();
            //if an admin exists you will want to be able to log in

            //only check if the user exist once the log in button has being pressed
            logInBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currUser = userName.getText().toString();

                    try
                    {
                        //seeing if the current user is going to exist in our current graph
                        Graph.Vertex userNode = pracGrader.getVertex(currUser);
                        errorUserName.setText("");
                        errorPassword.setText("");

                        //launch the next activity, alongside who the current user is going to be
                        String currentUserName =  userNode.getKey();
                        Intent intent = new Intent(MainActivity.this, UserHomePage.class);
                        intent.putExtra("pracGrader", pracGrader);
                        //we will need to know who the user is so we can grab their type in next activity and load appropriate fragments
                        intent.putExtra("currUser", currentUserName);
                        UserHomePage.inUse();
                        UserHomePage.tutor();
                        startActivity(intent);
                    }
                    catch (IllegalArgumentException err)
                    {
                        errorUserName.setText("User Doesn't exist");
                        errorPassword.setText("yet to be implemented");
                    }


                }
            });

        }
    }

    public void getUIComponents()
    {
        logInBttn = findViewById(R.id.logInBttn);
        userName = findViewById(R.id.userName_logIn);
        passOne = findViewById(R.id.passOne);
        passTwo = findViewById(R.id.passTwo);
        passThree = findViewById(R.id.passThree);
        passFour = findViewById(R.id.passFour);
        errorUserName = findViewById(R.id.errorUser);
        errorPassword = findViewById(R.id.errorPassword);

    }

    public static void New()
    {
        currState = state.New;
    }

    public static void initial()
    {
        currState = state.initial;
    }
}