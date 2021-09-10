package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    EditText country;
    EditText passWord;
    Button register;
    TextView errorName;
    TextView errorStaffID;
    TextView errorEmail;
    TextView errorCountry;
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
        country = (EditText) findViewById(R.id.adminCountry);
        passWord = (EditText) findViewById(R.id.adminPassword);
        register = (Button) findViewById(R.id.registerBttn);

        errorName = (TextView) findViewById(R.id.registerErrorUser);
        errorStaffID = (TextView) findViewById(R.id.registerErrorStaffID);
        errorEmail = (TextView) findViewById(R.id.registerErrorEmail);
        errorCountry = (TextView) findViewById(R.id.registerErrorCountry);
        errorPassword = (TextView) findViewById(R.id.errorPassword);


        //playing around with the spinner to see if I can display country flags
        countryFlags = (Spinner) findViewById(R.id.countryList);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = false;
                int checks = 0;

                //getting the texts from the UI elements
                String adminNameStr = adminName.getText().toString();
                String staffIDStr = staffID.getText().toString();
                String emailAddressStr = emailAddress.getText().toString();
                String countryStr = country.getText().toString();
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

                try
                {
                    countryCode = myUtils.getCountryCode(countryStr);
                    newAdmin.setCountry(countryStr);
                    checks++;
                    errorCountry.setText("");
                }
                catch (IllegalArgumentException err)
                {
                    //TODO: this is kinda of redantant, as you're required to create a drop down menu
                    Log.i(TAG, err.getMessage());
                    errorCountry.setText("Country doesn't exist");
                }

                //password, they is no need for validation as android studio is going to make sure that it's going to be a length of four
                //TODO: you will need to come back and figure out what you will need to do for her, and actually get this working

                if(checks == 4)
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