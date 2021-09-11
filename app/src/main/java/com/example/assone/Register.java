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
import java.lang.reflect.Field;
import android.graphics.drawable.Drawable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Register extends AppCompatActivity {

    private static final String TAG = "register.";
    //all the UI elements of this activity
    private Graph pracGrader;
    private EditText adminName;
    private EditText staffID;
    private EditText emailAddress;
    private TextView country;
    private EditText passWord;
    private Button register;
    private TextView errorName;
    private TextView errorStaffID;
    private TextView errorEmail;
    private TextView errorPassword;
    private Spinner spinnerFlags;
    private FlagAdapter adapterFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //TODO: you will need to have a swithc case statement which will tell it what to display dependent on the user which is using the applicaiton

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


        //getting all the country flags from the look up table so we can display on the registration form
        String[] flags = myUtils.getCountryNames();

        //creating and laoding the flag list
        //myUtils utils = new myUtils();
        ArrayList<Flag> allFlags = loadFlags();

        /*
        TODO: you will need to come back and set an click listener so you can get the country which was selected
            - you will have to keep in mind that you have used a base adapter to do your things
         */
        spinnerFlags = (Spinner) findViewById(R.id.countryList_spinner);
        adapterFlag = new FlagAdapter(Register.this, allFlags);
        spinnerFlags.setAdapter(adapterFlag);


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

                //TODO: you will need to add code here to grab the text which was selected from the drop down menu
                newAdmin.setCountry("AUSTRALIA");

                //password, they is no need for validation as android studio is going to make sure that it's going to be a length of four
                //TODO: you will need to come back and figure out what you will need to do for her, and actually get this working

                //TODO: you will need to change this back to 3, I am just setting it to 0 so that I don't have to type anything in
                if(checks == 0)
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

    //all the helper methods for this activity

    //PURPOSE: to be able to reverse search in the look up table, so we can obtain a key from the value
    public String getKey(String code)
    {
        //TODO: unfortunately this is going to be a linear search. Find a way to improve time complexity
        //grabbing all the keys which are going to be in the look up table
        Set<String> currKeys = myUtils.countryLookUp.keySet();

        for(String entry : currKeys)
        {
            String currCode = myUtils.countryLookUp.get(entry);
            if(currCode.equals(code))
            {
                //foundKey = entry;
                // I having a return here because I don't want to go through the whole list, once item is found end
                return entry;
            }

        }

        return null;
    }

    /*
    PURPOSE: to create a list of all the possible flags whcih the progarmme can have
        - this function needs to be non static because it's going to use some methods from its parent
        class, and those methods are non static methods
     */
    public ArrayList<Flag> loadFlags()
    {
        ArrayList<Flag> retFlag = new ArrayList<>();
        List<String> flagNames = getImageNames();
        List<Integer> drawableIDs = getImagesDrawableID(flagNames);
        int ii = 0;

        //going through every flag name and making a flag object and returning it
        for (String currName : flagNames)
        {
            //getting what the country code is going to be so we reverse search it in our look up table
            String[] nameDetails = currName.split("_");

            //the second element is going to be the flags country code
            String flagName = getKey(nameDetails[1]);

            //the flag names, and the drawable ideas are going to be ordered
            int currentID = drawableIDs.get(ii);
            Flag currentFlag = new Flag(flagName, currentID);
            if (flagName != null)
            {
                //only add the flags which we found the key for
                retFlag.add(currentFlag);
            }
            ii++;
        }

        return retFlag;
    }

    //PURPOSE: to get the drawable ids given a list of drawable file names
    public List<Integer> getImagesDrawableID(List<String> inIDs)
    {
        ArrayList<Integer> retListImages = new ArrayList<>();

        //actually going through all the resource folders and getting the drawable integer numbers
        for(String currID : inIDs)
        {
            int resID = getResources().getIdentifier(currID, "drawable", getPackageName());
            retListImages.add(resID);
        }

        return retListImages;
    }

    public List<String> getImageNames()
    {
        ArrayList<String> retNames = new ArrayList<>();
        Field[] drawables = com.example.assone.R.drawable.class.getFields();
        ArrayList<Drawable>  drawableResources = new ArrayList<>();

        for (Field field : drawables)
        {
            //we want to grab every single file which is going to begin with the word flag
            String[] nameOfField  = field.getName().split("_");

            if (nameOfField[0].trim().equals("flag"))
            {
                retNames.add(field.getName());
            }
        }

        return retNames;
    }

}