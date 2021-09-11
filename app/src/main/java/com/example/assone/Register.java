/*
TODO: you can try to have flags next to the country names, when you're scrolling
through the drop down menu which you have created
 */
package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    private static final String TAG = "register.";
    Graph pracGrader;
    EditText adminName;
    EditText staffID;
    EditText emailAddress;
    TextView country;
    EditText passWord;
    Button register;
    TextView errorName;
    TextView errorStaffID;
    TextView errorEmail;
    TextView errorPassword;
    Spinner countryFlags;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //grabbing the graph data Structure which was created in the main activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");

        //finding all the UI elements
        adminName = (EditText) findViewById(R.id.adminName);
        staffID = (EditText) findViewById(R.id.adminUserName);
        emailAddress = (EditText) findViewById(R.id.adminEmail);
        passWord = (EditText) findViewById(R.id.adminPassword);
        register = (Button) findViewById(R.id.registerBttn);

        errorName = (TextView) findViewById(R.id.registerErrorUser);
        errorStaffID = (TextView) findViewById(R.id.registerErrorStaffID);
        errorEmail = (TextView) findViewById(R.id.registerErrorEmail);
        errorPassword = (TextView) findViewById(R.id.errorPassword);

        //TODO: you will need to refactor this code because the country is no longer a edit text, it's a text view
        //when I see refactor it, I mean you will need to completely delete it

        //playing around with the spinner to see if I can display country flags
        countryFlags = (Spinner) findViewById(R.id.countryList);

        //getting all the country flags from the look up table so we can display on the registration form
        String[] flags = myUtils.getCountryNames();

        //making an array adapter so we display the obtained country names as a drop down menu
        ArrayAdapter<String> flagsAdapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_list_item_1,flags);

        //setting the items on the actual adapter
        countryFlags.setAdapter(flagsAdapter);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = false;
                int checks = 0;

                //getting the texts from the UI elements
                String adminNameStr = adminName.getText().toString();
                String staffIDStr = staffID.getText().toString();
                String emailAddressStr = emailAddress.getText().toString();
                String passWordStr = passWord.getText().toString();
                String countryCode = "au";

                //going to set the class fields individually, as they're all goign to through the same exeption, so
                //the setting condition is going to detemine what is wrong with the programme


                Admin newAdmin = new Admin();

                try
                {
                    newAdmin.setName(adminNameStr);
                    checks++;
                    errorName.setText("");
                }
                catch (IllegalArgumentException err)
                {
                    Log.i(TAG, err.getMessage());
                    errorName.setText("username cannot be blank");
                    //display meaningful information
                }

                try
                {
                    newAdmin.setUserName(staffIDStr);
                    checks++;
                    errorStaffID.setText("");
                }
                catch (IllegalArgumentException err)
                {
                    Log.i(TAG, err.getMessage());
                    errorStaffID.setText("Staff ID must be 6 digits then letter");
                }

                try
                {
                    newAdmin.setEmail(emailAddressStr);
                    checks++;
                    errorEmail.setText("");
                }
                catch (IllegalArgumentException err)
                {
                    Log.i(TAG, err.getMessage());
                    errorEmail.setText("invalid email format");
                }

                //TODO: you will need to add code here to grab the text which was selected

                //password, they is no need for validation as android studio is going to make sure that it's going to be a length of four
                //TODO: you will need to come back and figure out what you will need to do for her, and actually get this working

                if(checks == 3)
                {
                    valid = true;
                }
                Log.i(TAG, "the current check is here: " + checks);


                if(valid)
                {
                    MainActivity.toggleLoaded();
                    pracGrader.addVertex(newAdmin);
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    intent.putExtra("pracGrader", pracGrader);
                    startActivity(intent);
                }
            }
        });
    }
}