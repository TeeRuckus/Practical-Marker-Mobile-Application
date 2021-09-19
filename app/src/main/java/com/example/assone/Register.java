/*
TODO: you can try to have flags next to the country names, when you're scrolling
through the drop down menu which you have created
 */
package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Register extends AppCompatActivity {

    private static final String TAG = "register.";
    //all the UI elements of this activity
    private Graph pracGrader;
    private String currUser;
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
    private String userNameStr;
    private String userIDStr;
    private String emailAddressStr;
    private int checks;
    private TextView banner;


    private enum state {
        initial,
        instructor,
        student
    }

    private enum addtype {
        adminAdd,
        instructorAdd
    }

    private static state currState;
    private static addtype typeOfAdd;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        /*depending on what state which we're going to be in, we want
        to call teh activity which called us here
         */
        Intent intent;
        switch(currState)
        {
            case initial:
                //do nothing as I don't want this button to do anything
                //at teh current moement
                break;
            case instructor:
                intent = packageInfo();
                UserHomePage.tutor();
                startActivity(intent);
                break;
            case student:
                intent = packageInfo();
                UserHomePage.student();
                startActivity(intent);
                break;

        }
    }

    public Intent packageInfo()
    {
        Intent intent = new Intent(Register.this, UserHomePage.class);
        intent.putExtra("pracGrader", pracGrader);
        intent.putExtra("currUser", currUser);
        UserHomePage.none();
        UserHomePage.inUse();
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //TODO: you will need to have a swithc case statement which will tell it what to display dependent on the user which is using the applicaiton

        //grabbing the graph data Structure which was created in the main activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUser = (String) getIntent().getStringExtra("currUser");
        getUIElements();
        //TODO: you will need to refactor this code because the country is no longer a edit text, it's a text view
        //when I see refactor it, I mean you will need to completely delete it


        //getting all the country flags from the look up table so we can display on the registration form
        String[] flags = myUtils.getCountryNames();
        //creating and laoding the flag list
        ArrayList<Flag> allFlags = loadFlags();

        /*
        TODO: you will need to come back and set an click listener so you can get the country which was selected
            - you will have to keep in mind that you have used a base adapter to do your things
         */
        spinnerFlags = (Spinner) findViewById(R.id.countryList_spinner);
        adapterFlag = new FlagAdapter(Register.this, allFlags);
        spinnerFlags.setAdapter(adapterFlag);

        switch (currState) {
            case instructor:
                //the problem is that thave this inside an onclick listener
                banner.setText("Create tutor");
                adminName.setHint("Instructor Name");

                User newInstructor = new Instructor();
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getFields();
                        boolean valid = registerUser(newInstructor);
                        Instructor createdInstructor = (Instructor) newInstructor;
                        pracGrader.addVertex(createdInstructor);

                        //showing the user that an instructor has being created
                        if (valid) {
                            Context cntx = getApplicationContext();
                            CharSequence text = "Instructor created";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(cntx, text, duration);
                            toast.show();
                            clearText();
                            recreate();
                        }
                    }
                });


                break;

            case student:
                /*
                the admin or the instructor can add students. If an instructor adds a student, the student
                must be attached to the instructor. If an admin adds a student, if no owner is given
                the admin will become the default owner.
                 */
                banner.setText("Create Student");
                adminName.setHint("Student Name");
                staffID.setHint("Student ID");
                emailAddress.setHint("Curtin Student Email");
                User newStudent = new Student();

                switch (typeOfAdd)
                {
                    case adminAdd:

                        register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getFields();
                                boolean  valid = registerUser(newStudent);
                                Student createdStudent = (Student) newStudent;
                                createdStudent.setInstructor(currUser);
                                pracGrader.addVertex(createdStudent);
                                if(valid)
                                {
                                    succesfulStudentCreate();
                                }
                            }
                        });

                        break;

                    case instructorAdd:

                        register.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getFields();
                                boolean valid = registerUser(newStudent);
                                Student createdStudent= (Student) newStudent;
                                pracGrader.addVertex(createdStudent, currUser);

                                if(valid)
                                {
                                    succesfulStudentCreate();
                                }
                            }
                        });
                        break;
                }


                break;

            case initial:
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFields();
                        User newAdmin = new Admin();
                        boolean valid = registerUser(newAdmin);
                        Admin createdAdmin = (Admin) newAdmin;
                        pracGrader.addVertex(createdAdmin);

                        if(valid)
                        {
                            //MainActivity.toggleLoaded();
                            MainActivity.initial();
                            //we will need to update the name of the admin
                            pracGrader.setAdmin(userNameStr);
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            intent.putExtra("pracGrader", pracGrader);
                            startActivity(intent);
                        }
                    }
                });

                break;
        }
    }

    public void succesfulStudentCreate()
    {
        Context cntx = getApplicationContext();
        CharSequence text = "Student Created";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(cntx, text, duration);
        toast.show();
        clearText();
        recreate();
    }

    public static void adminAdd()
    {
        typeOfAdd = addtype.adminAdd;
    }

    public static void instructorAdd()
    {
        typeOfAdd = addtype.instructorAdd;
    }

    public static void initial()
    {
        currState = state.initial;
    }

    public static void instructor()
    {
        currState = state.instructor;
    }

    public static void student()
    {
        currState = state.student;
    }

    public void clearText()
    {
        //clearing all the input after the user has being created
        adminName.setText("");
        staffID.setText("");
        emailAddress.setText("");
    }


    public boolean registerUser(User inUser)
    {
        boolean valid = false;

        try
        {
            inUser.setName(userNameStr);
            checks++;
            errorName.setText("");
        }
        catch (IllegalArgumentException err)
        {
            Log.e(TAG, err.getMessage());
            errorName.setText("username cannot be blank");
            //display meaningful information
        }

        try
        {
            inUser.setUserName(userIDStr);
            checks++;
            errorStaffID.setText("");
        }
        catch (IllegalArgumentException err)
        {
            Log.e(TAG, err.getMessage());
            errorStaffID.setText("Staff ID must be 6 digits then letter");
        }

        try
        {
            inUser.setEmail(emailAddressStr);
            checks++;
            errorEmail.setText("");
        }
        catch (IllegalArgumentException err)
        {
            Log.e(TAG, err.getMessage());
            errorEmail.setText("invalid email format");
        }

        //TODO: you will need to add code here to grab the text which was selected from the drop down menu
        inUser.setCountry("AUSTRALIA");

        //password, they is no need for validation as android studio is going to make sure that it's going to be a length of four
        //TODO: you will need to come back and figure out what you will need to do for her, and actually get this working

        //TODO: you will need to change this back to 3, I am just setting it to 0 so that I don't have to type anything in
        if(checks == 1)
        {
            valid = true;
        }
        Log.i(TAG, "the current check is here: " + checks);

        return valid;
    }

    public void getUIElements()
    {
        //finding all the UI elements
        adminName = findViewById(R.id.adminName);
        staffID = findViewById(R.id.adminUserName);
        emailAddress = findViewById(R.id.adminEmail);
        passWord = findViewById(R.id.adminPassword);
        register = findViewById(R.id.registerBttn);

        errorName = findViewById(R.id.registerErrorUser);
        errorStaffID = findViewById(R.id.registerErrorStaffID);
        errorEmail = findViewById(R.id.registerErrorEmail);
        errorPassword = findViewById(R.id.errorPassword);
        banner = findViewById(R.id.bannerRegister);

    }
    //all the helper methods for this activity
    public void getFields()
    {
        //getting the texts from the UI elements
         userNameStr = adminName.getText().toString();
         userIDStr = staffID.getText().toString();
         emailAddressStr = emailAddress.getText().toString();
    }

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