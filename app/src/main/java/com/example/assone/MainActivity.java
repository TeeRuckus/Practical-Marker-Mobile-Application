package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    //making a tag variable, so that more meaningful debuggin messages will appear on the screen
    private static final String TAG = "MainActivity.";
    private Button logInBttn;
    private EditText passOne;
    private EditText passTwo;
    private EditText passThree;
    private EditText passFour;
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
        logInBttn = (Button) findViewById(R.id.logInBttn);
        passOne = (EditText)  findViewById(R.id.passOne);
        passTwo = (EditText)  findViewById(R.id.passTwo);
        passThree = (EditText)  findViewById(R.id.passThree);
        passFour = (EditText)  findViewById(R.id.passFour);

        //if an admin node doesn't exit, we should launch the registration activity and create an admin for the system
        if(adminNode == null)
        {
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("pracGrader", pracGrader);
            startActivity(intent);
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