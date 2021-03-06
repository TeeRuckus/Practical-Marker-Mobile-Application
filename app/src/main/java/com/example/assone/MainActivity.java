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

        Graph.Vertex adminNode = pracGrader.getVertex();

        if(adminNode == null)
        {
            //if an admin hasn't being created in the appliction, we should create a new admin node
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("pracGrader", pracGrader);
            intent.putExtra("currUser", "");
            Register.initial();
            startActivity(intent);
        }
        else
        {
            //if an admin exists we will want to log into the application and view the contents
            getUIComponents();

            //once the log int button is pressed check if the user is going to exist in the made graph
            logInBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currUser = userName.getText().toString();

                    try
                    {
                        //seeing if the current user is going to exist in our current graph
                        Graph.Vertex userNode = pracGrader.getVertex(currUser);

                        // if an error had happened make sure to set the view back to a blank text
                        errorUserName.setText("");
                        errorPassword.setText("");

                        //launch the next activity, alongside who the current user is going to be
                        String currentUserName =  userNode.getKey();
                        int currPasswrod = userNode.getValue().getPassword();

                        // before we launch the next activity we need to make sure that the password is correct
                        if (currPasswrod == getPassWord()) {

                            Intent intent = new Intent(MainActivity.this, UserHomePage.class);
                            intent.putExtra("pracGrader", pracGrader);
                            //we will need to know who the user is so we can grab their type in next activity and load appropriate fragments
                            intent.putExtra("currUser", currentUserName);
                            UserHomePage.inUse();
                            UserHomePage.none();
                            //selecting the default person to do operations on as the tutor for the home page
                            UserHomePage.student();
                            startActivity(intent);
                        }
                        else
                        {
                            errorPassword.setText("incorrect password");
                        }
                    }
                    catch (IllegalArgumentException err)
                    {
                        errorUserName.setText("User Doesn't exist");
                    }


                }
            });

        }
    }

    public int getPassWord()
    {
        int userPassword = 0;

        //getting all teh individual UI elements as strings
        String passOneStr = passOne.getText().toString();
        String passTwoStr = passTwo.getText().toString();
        String passThreeStr = passThree.getText().toString();
        String passFourStr = passFour.getText().toString();
        String currPassword = passOneStr + passTwoStr + passThreeStr + passFourStr;

        //converting the cathcanated password into an actual integer

        try
        {
            userPassword = Integer.parseInt(currPassword);
            //if an error had occured in this context, reset error upon success
            errorPassword.setText("");
        }
        catch (NumberFormatException e)
        {
            //this actually not even needed here, the if and else statement in above context will catch it
            //if the user tries to log in without a password show the error onto the screen
            errorPassword.setText("No Password entered");
        }
        return  userPassword;
    }

    public void getUIComponents()
    {
        /* actually finding all the UI elements which are going to be on the screen, and making them
        accessible through the programme class fields
         */
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