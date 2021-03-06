package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class Details extends AppCompatActivity {
    private Graph pracGrader;
    private String currUser;
    private static String clickedPerson;
    private static final String TAG = "Details.";

    //getting all the UI elements
    private TextView banner;
    private ImageView country;
    private EditText name;
    private EditText userID;
    private EditText userEmail;
    private EditText studentOwner;
    private Button delButton;
    private Button viewPracs;

    @Override
    public void onBackPressed() {
        //if the user has inputted anythign into the edit text we should update the name
        boolean valid = true;

        //counter variables to see the things which were changed
        //at the end they should be the same number
        int changed = 0;
        int correct  = 0;

        if(userEmail.getText().toString().length() != 0)
        {
            valid = changeEmail();
            changed++;
            correct++;

            if(!valid)
            {
                correct--;
            }
        }


        if(userID.getText().toString().length() != 0)
        {
            valid = changeID();
            changed++;
            correct++;

            if(!valid)
            {
                correct--;
            }
        }


        if (name.getText().toString().length() != 0)
        {
            valid = changeNames();
            changed++;
            correct++;

            if(!valid)
            {
                name.setText("");
                name.setHint("User Name exists");
                name.setHintTextColor(Color.RED);
                correct--;
            }
        }


        if(changed == correct)
        {
            //go back to the activity which launched us which is going to be userViewing, and view the list of students
            Intent intent = new Intent(Details.this, userViewing.class);
            intent.putExtra("pracGrader", pracGrader);
            Log.e(TAG, "SENDING SOLDIERS " + currUser);
            intent.putExtra("currUser", currUser);
            //intent.putExtra("clickedUser", clickedPerson);
            userViewing.view();

            if (pracGrader.getVertex(currUser).getValue().getType().equals("INSTRUCTOR"))
            {
                userViewList.instructor();
            }
            startActivity(intent);
        }

    }

    public boolean changeNames()
    {
        boolean valid = false;
        Graph.Vertex currVert = pracGrader.getVertex(clickedPerson);
        User user = currVert.getValue();
        String newName = name.getText().toString();

        if(pracGrader.UserDoesNotExist(newName))
        {
            valid = true;
            // if someone doesn't have that name we can update it to that name
            user.setName(newName);
            //this.clickedPerson = newName;
            //updating the changes in the actual graph, the should change everywhere were this current object is referenced
            currVert.setKey(newName);
            pracGrader.update(clickedPerson, currVert);
        }

        return valid;
    }

    public boolean changeID()
    {
        boolean valid = false;

        //getting the person which was clicked on
        Graph.Vertex currVert = pracGrader.getVertex(clickedPerson);
        User currUser = currVert.getValue();
        String newID = userID.getText().toString();

        //trying to set the currernt users ID, to the one inputted
        try
        {
            currUser.setUserName(newID);
            valid = true;
        }
        catch (IllegalArgumentException e)
        {
            userID.setText("");
            userID.setHint("Invalid ID");
            userID.setHintTextColor(Color.RED);
        }

        return valid;
    }

    public boolean changeEmail()
    {
        //getting who the current user is going ot be
        boolean valid = false;
        Graph.Vertex currVert = pracGrader.getVertex(clickedPerson);
        User currUser = currVert.getValue();
        String newEmail = userEmail.getText().toString();

        //trying to set the current user to the one which was inputted
        try
        {
            currUser.setEmail(newEmail);
            valid = true;
        }
        catch (IllegalArgumentException e)
        {
            userEmail.setText("");
            userEmail.setHint("Invalid Email");
            userEmail.setHintTextColor(Color.RED);
        }

        return valid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        loadUI();
        //when this is created we want to grab the data which was passed into this activity
        pracGrader = (Graph) getIntent().getSerializableExtra("pracGrader");
        currUser = getIntent().getStringExtra("currUser");
        clickedPerson = getIntent().getStringExtra("clickedPerson");

        //getting who the current user is going to be so we can set their current details on the screen
        Graph.Vertex currUserVert = pracGrader.getVertex(clickedPerson);
        User currUserObj  = currUserVert.getValue();
        country.setImageResource(currUserObj.getFlag().getImage());

        name.setHint(clickedPerson);
        userEmail.setHint(currUserObj.getEmail());
        userID.setHint(currUserObj.getUserName());

        char firstLetterCaptial = myUtils.getType(clickedPerson, pracGrader);

        switch (firstLetterCaptial)
        {
            case 'A':
                banner.setText("Admin Details");
                //disabling the delete button as you should not be able to delete the admin node from your programme
                delButton.setClickable(false);
                delButton.setText("");
                delButton.setBackgroundColor(Color.TRANSPARENT);
                disableOwner();
                disableViewPracs();

                break;
            case 'I':
                banner.setText("Instructor Details");
                disableOwner();
                disableViewPracs();

                break;
            case 'S':
                banner.setText("Student Details");
                Student currStudent = (Student) currUserObj;
                studentOwner.setHint(currStudent.getInstructor());
                userViewList.admin();

                //the student details is going to have the view prac button activated
                viewPracs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //when the button is clicked open the users
                        Intent intent = new Intent(Details.this, userViewing.class);
                        intent.putExtra("pracGrader", pracGrader);
                        intent.putExtra("currUser", currUser);
                        intent.putExtra("clickedPerson", clickedPerson);
                        userViewing.practicalList();
                        startActivity(intent);
                    }
                });

                break;
        }

        delButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pracGrader.delVertex(clickedPerson);
                Intent intent = new Intent(Details.this, userViewing.class);
                intent.putExtra("pracGrader", pracGrader);
                intent.putExtra("currUser", currUser);
                userViewing.view();
                startActivity(intent);
            }
        });
    }

    public void disableViewPracs()
    {
        viewPracs.setClickable(false);
        viewPracs.setText("");
        viewPracs.setBackgroundColor(Color.TRANSPARENT);
    }

    public void disableOwner()
    {
        studentOwner.setClickable(false);
        studentOwner.setEnabled(false);
        studentOwner.setHint("");
        studentOwner.setText("");
        studentOwner.setBackgroundColor(Color.TRANSPARENT);
    }

    public void loadUI()
    {
        //loading all the UI elements which are needed in this activity
        banner = findViewById(R.id.bannerDetails);
        country = findViewById(R.id.userImageDetails);
        name = findViewById(R.id.userNameDetails);
        userID = findViewById(R.id.userIDDetails);
        userEmail = findViewById(R.id.userEmailDetails);
        studentOwner = findViewById(R.id.studentOwnerDetails);
        delButton = findViewById(R.id.deleteButton);
        viewPracs = findViewById(R.id.viewPracsBttn);
    }

    public static String getClickedPerson()
    {
        return clickedPerson;
    }
}