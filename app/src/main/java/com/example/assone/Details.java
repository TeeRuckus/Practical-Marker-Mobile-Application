package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends AppCompatActivity {
    private Graph pracGrader;
    private String currUser;
    private String clickedPerson;

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