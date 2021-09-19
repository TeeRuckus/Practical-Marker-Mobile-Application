package com.example.assone;

import static android.R.color.transparent;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//the buttons which are in the current fragment

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainButtons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainButtons extends Fragment {

    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour;
    private Graph pracGrader;
    private String currUser;
    private static final String TAG = "mainButtons.";

    private enum instructorState {
        student,
        practical
    }

    private static instructorState currState;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainButtons() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainButtons.
     */
    // TODO: Rename and change types and number of parameters
    public static mainButtons newInstance(String param1, String param2) {
        mainButtons fragment = new mainButtons();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_main_buttons, container, false);
        //when this fragment is created what do you want to do in here?
        optionOne = v.findViewById(R.id.optionOne);
        optionTwo = v.findViewById(R.id.optionTwo);
        optionThree = v.findViewById(R.id.optionThree);
        optionFour = v.findViewById(R.id.optionFour);

        //getting the data which was sent in from the userHomepage activity
        pracGrader = (Graph) getArguments().getSerializable("pracGrader");
        currUser = getArguments().getString("currUser");

        //determing what buttons should be viewed with what text depeding on the user
        char firstLetterCaptial = myUtils.getType(currUser, pracGrader);

        //firstLetterCaptial = 'I';
        switch (firstLetterCaptial)
        {
            case 'A':
                setAdmin();

                //getting the type of action which will need to be performed
                //option one is going to correspond to the add button in the programme
                optionOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserHomePage.add();
                        /* we will need to refresh the activity so that the page can process the new
                        information which was passed from this activity */
                        getActivity().recreate();
                    }
                });


                //optionFour is going to correspond to the view button in the programme
                optionFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserHomePage.view();
                        /* we will need to refresh the activity so that the page can process the new
                        information which was passed from this activity */
                        getActivity().recreate();
                    }
                });


                break;
            case 'I':
                setInstructor();
                optionOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserHomePage.add();
                        getActivity().recreate();
                    }
                });
                break;
            case 'S':
                setStudent();

                break;
        }


        return v;
    }

    public void disableBottomTwoBttns()
    {
        optionThree.setClickable(false);
        optionFour.setClickable(false);
        optionThree.setText("");
        optionFour.setText("");
        //setting the buttons transparent so they're no longer visible
        optionThree.setBackgroundColor(Color.TRANSPARENT);
        optionFour.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setAdmin()
    {
        optionOne.setText("Add");
        optionTwo.setText("Edit");
        optionThree.setText("Delete");
        optionFour.setText("View");
    }

    public void setInstructor()
    {
        switch (currState)
        {
            case student:
                optionOne.setText("Add");
                optionTwo.setText("Edit");
                optionThree.setText("Delete");
                optionFour.setText("View");
                break;
            case practical:
                optionOne.setText("Edit");
                optionTwo.setText("view");
                disableBottomTwoBttns();
                break;
        }
        //TODO: when you change the mode to practicals you will need to disable the bottom two buttons
    }

    public static void student()
    {
        currState = instructorState.student;
    }

    public static void practical()
    {
        currState = instructorState.practical;
    }

    public void setStudent()
    {
        optionOne.setText("Practicals");
        optionTwo.setText("Summary");
        disableBottomTwoBttns();
    }


}