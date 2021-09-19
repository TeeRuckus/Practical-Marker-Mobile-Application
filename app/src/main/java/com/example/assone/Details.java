package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {
    private Graph pracGrader;
    private String currUser;
    private String clickedPerson;
    private static final String TAG = "Details.";

    //getting all the UI elements
    private TextView banner;
    private ImageView country;
    private EditText name;
    private EditText userID;
    private EditText userEmail;
    private EditText studentOwner;
    private Button delButton;

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
                break;
            case 'I':
                banner.setText("Instructor Details");
                disableOwner();
                break;
            case 'S':
                banner.setText("Student Details");
                Student currStudent = (Student) currUserObj;
                studentOwner.setHint(currStudent.getInstructor());
                userViewList.admin();
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
    }
}