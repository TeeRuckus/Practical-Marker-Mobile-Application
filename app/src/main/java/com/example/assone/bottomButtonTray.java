package com.example.assone;

import static android.R.color.transparent;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottomButtonTray#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottomButtonTray extends Fragment {

    //all the UI elements which are found in the current fragment
    private Button trayBttnOne;
    private Button trayBttnTwo;
    private Button trayBttnThree;
    private Button leaveBttn;
    private String currUser;
    private Graph pracGrader;

    private static final String TAG = "bottomButtonTray.";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bottomButtonTray() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bottomButtonTray.
     */
    // TODO: Rename and change types and number of parameters
    public static bottomButtonTray newInstance(String param1, String param2) {
        bottomButtonTray fragment = new bottomButtonTray();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottom_button_tray, container, false);

        //finding all the buttons which are on the screen
        trayBttnOne = v.findViewById(R.id.trayBttnOne);
        trayBttnTwo = v.findViewById(R.id.trayBttnTwo);
        trayBttnThree = v.findViewById(R.id.trayBttnThree);
        leaveBttn = v.findViewById(R.id.trayBttnFour);

        //getting the information which was passed in from the userHomePage activity
        currUser = getArguments().getString("currUser");
        pracGrader = (Graph) getArguments().getSerializable("pracGrader");
        char firstLetterCapital = myUtils.getType(currUser, pracGrader);

        //firstLetterCapital = 'I';
        switch(firstLetterCapital)
        {
            case 'A':
                trayBttnOne.setText("Pracs");
                trayBttnTwo.setText("Student");
                trayBttnThree.setText("Tutors");

                break;

            case 'I':
                disableThreeBttns();

                break;

            case 'S':
                disableThreeBttns();

                break;
        }

        leaveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //what will leave when you click the leave button
                UserHomePage.leave();
                Log.i(TAG, "COMMAND IS SENT");
                //I want to reload the activity so that the new changes can be actually rendered in the application
                getActivity().recreate();
            }
        });

        return v;

        //when the leave button is pressed, the programme should go back to teh log in page
    }

    @SuppressLint("ResourceAsColor")
    public void disableThreeBttns()
    {
        trayBttnOne.setClickable(false);
        trayBttnTwo.setClickable(false);
        trayBttnThree.setClickable(false);
        trayBttnOne.setText("");
        trayBttnTwo.setText("");
        trayBttnThree.setText("");
        trayBttnOne.setBackgroundColor(transparent);
        trayBttnTwo.setBackgroundColor(transparent);
        trayBttnThree.setBackgroundColor(transparent);



    }
}