package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!(loaded))
        {
            pracGrader = new Graph();
        }
        else
        {
            pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        }

        //pracGrader = new Graph();
        Graph.Vertex adminNode = pracGrader.getVertex();

        //getting all the UI element from the actuall applicatio

        //if an admin node doesn't exit, we should launch the registration activity and create an admin for the system
        if(adminNode == null)
        {
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("pracGrader", pracGrader);
            startActivity(intent);
        }
        else
        {
            logInBttn = findViewById(R.id.logInBttn);
            userName = findViewById(R.id.userName_logIn);
            passOne = findViewById(R.id.passOne);
            passTwo = findViewById(R.id.passTwo);
            passThree = findViewById(R.id.passThree);
            passFour = findViewById(R.id.passFour);
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
                        //String currentUserName =  userNode.getKey();
                    }
                    catch (IllegalArgumentException err)
                    {
                        errorUserName = findViewById(R.id.errorUser);
                        errorPassword = findViewById(R.id.errorPassword);
                        errorUserName.setText("User Doesn't exist");
                        errorPassword.setText("yet to be implemented");
                    }


                }
            });

        }
    }

    public static void toggleLoaded()
    {
        if(loaded)
        {
            loaded = false;
        }
        else
        {
            loaded = true;
        }
    }

    public static boolean getLoaded()
    {
        return loaded;
    }


}