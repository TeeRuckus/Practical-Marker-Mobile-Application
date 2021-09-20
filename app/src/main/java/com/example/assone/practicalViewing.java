package com.example.assone;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class practicalViewing extends Fragment {

    //my own personnal class fileds for this class
    private Button addBttn;
    private Graph pracGrader;
    private String currUser;
    private EditText pracTitle;
    private EditText pracTotalMarks;
    private EditText pracDescription;
    private TextView pracTitleView;
    private TextView availableMarksView;
    private TextView practicalDescrptionView;
    private static final String TAG = "practicalViewing.";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public practicalViewing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment practicalViewing.
     */
    // TODO: Rename and change types and number of parameters
    public static practicalViewing newInstance(String param1, String param2) {
        practicalViewing fragment = new practicalViewing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting what the current pracGrader, and user is from the activity which called us which is
        // going to be the userHomePage for the programme
        pracGrader = UserHomePage.getGraph();
        currUser = UserHomePage.getCurrUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // TODO: this is for the ADMIN adding of practicals, I will need to have an option where the instructor can just only change the marks and not the whole entire practical
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practical_viewing, container, false);
        addBttn = view.findViewById(R.id.addPracticalBttn);
        pracTitle = view.findViewById(R.id.practicalTitle);
        pracTotalMarks = view.findViewById(R.id.availableMarks);
        pracDescription = view.findViewById(R.id.practicalDescription);
        //You might need to change this, so you can just show and unshow the messages when an error shows
        pracTitleView = view.findViewById(R.id.practicalTitleView);
        availableMarksView = view.findViewById(R.id.practicalAvailableMarksView);
        practicalDescrptionView = view.findViewById(R.id.practicalDescriptionView);

        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a new practical object from the description which was given
                //TODO: you will need ot update the total available marks for the object


                boolean valid = distributePracs();

                if (valid)
                {
                    //display success and then clear the screen
                    Context cntx = getActivity().getApplicationContext();
                    CharSequence text = "Practical created";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(cntx, text, duration);
                    toast.show();
                    clearText();
                    getActivity().recreate();
                }

            }
        });
        //return inflater.inflate(R.layout.fragment_practical_viewing, container, false);
        return view;
    }

    public void clearText()
    {
        pracTitle.setText("");
        pracTotalMarks.setText("");
        pracDescription.setText("");
    }
    public boolean distributePracs()
    {
        boolean valid = false;
        int count = 0;
        //gettting information which the user had inputted in the fragment
        String tempTitle = pracTitle.getText().toString();
        String tempDescription = pracDescription.getText().toString();

        int paleNight_error = Color.parseColor("#ff5370");
        int paleNight_white = Color.parseColor("#eeffff");
        int paleNight_text = Color.parseColor("#676E95");

        Practical tempPrac = new Practical();

        try
        {
            float availableMarks = Float.parseFloat(pracTotalMarks.getText().toString());
            availableMarksView.setTextColor(paleNight_white);
            tempPrac.setTotalMarks(availableMarks);
            pracTotalMarks.setHintTextColor(paleNight_text);
            count++;
        }
        catch (NumberFormatException e)
        {
            availableMarksView.setTextColor(paleNight_error);
            pracTotalMarks.setHintTextColor(paleNight_error);
        }

        try
        {
            tempPrac.setTitle(tempTitle);
            count++;
            pracTitleView.setTextColor(paleNight_white);
            pracTitle.setHintTextColor(paleNight_text);
        }
        catch (IllegalArgumentException e)
        {
            pracTitleView.setTextColor(paleNight_error);
            pracTitle.setHintTextColor(paleNight_error);
        }

        try
        {
            count++;
            tempPrac.setDescrpt(tempDescription);
            pracDescription.setHintTextColor(paleNight_white);
            practicalDescrptionView.setTextColor(paleNight_white);
        }
        catch (IllegalArgumentException e)
        {
            pracDescription.setHintTextColor(paleNight_error);
            practicalDescrptionView.setTextColor(paleNight_error);
        }

        pracGrader.sendPracticals(tempPrac);
        //after this has being added, we want to notify the user and clear the last fields


        if (count == 3)
        {
            valid = true;
        }
        return valid;
    }
}