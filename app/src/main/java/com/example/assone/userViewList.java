/*TODO:
    - you will need to figure out a way which you can get the prac grader into this class
 */
package com.example.assone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class userViewList extends Fragment
{

    //my own defined class fields for this recycler vie
    //private HashMap<String, Graph.Vertex> userMap;
    private ArrayList<Graph.Vertex> userMap;
    private ArrayList<Graph.Vertex> tempUserMap;
    private ArrayList<Practical> currUserPracs;

    private RecyclerView rv;
    private userAdapter adapter;
    private LinearLayoutManager rvLayout;
    //for debuggin and logging purposes of the programme
    private static final String  TAG = "userViewList.";
    private EditText searchUser;

    private String currUser;
    private Graph pracGrader;

    private enum state {
        admin,
        instructor,
        student,
        practicalLoad,
    }

    private enum viewType {
        studentView,
        instructorView
}

    private static state currState;
    private static viewType currView;

    // TODO: once you have this working, you should delete these parameters
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public userViewList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment userViewList.
     */
    // TODO: Rename and change types and number of parameters
    public static userViewList newInstance(String param1, String param2) {
        userViewList fragment = new userViewList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //creating a new map of users so the programme can use it

        userMap = new ArrayList<>();
        currUserPracs = new ArrayList<>();
        pracGrader = userViewing.getGraph();
        currUser = userViewing.getCurrUser();

        //depending on who the user is going to be, it's going to dependent on how the data is going to be loaded
        switch(currState)
        {
            case admin:
                switch(currView)
                {
                    case studentView:
                        userMap = pracGrader.adminStudentLoad();
                        break;

                    case instructorView:
                        userMap = pracGrader.adminInstructorLoad();
                        break;
                }
                break;

            case instructor:
                userMap = pracGrader.instructorLoad(currUser);
                break;
            case student:
                //do nothign for the current moment
                String studentName = UserHomePage.getCurrUser();
                Graph.Vertex studentVert = pracGrader.getVertex(studentName);
                User currStd = studentVert.getValue();

                //allowing the user to view their own practicals
                Student retStudent = (Student) currStd;
                currUserPracs = retStudent.pracLoad();

                break;
            case practicalLoad:
                // I really don't want to create another recycler view for displaying the students.
                // hence, to get around that I am going to do a band aid fix, and I am going to get
                // the practicals which belong to the user, and set them as vertexes tehn the bind
                // method is going to take care of everything else
                String clickedUser = Details.getClickedPerson();
                Graph.Vertex currVert = pracGrader.getVertex(clickedUser);
                User currUser =  currVert.getValue();

                //the user which can only view their practicals is the student at the current moment
                Student currStudent = (Student) currUser;
                currUserPracs = currStudent.pracLoad();
                Log.e(TAG, "Curr user pracs: " + currUserPracs);
                break;
        }

    }


    public void filter (String text)
    {
        // you will need to make this filtering algorithm dependent on what case the user is currently in
        text = myUtils.cleanString(text);

        // if it's going to be admin load, we're  going to be filtering graph vertexs
        ArrayList<Graph.Vertex> filteredVerts = new ArrayList<>();

        if (text.equals(""))
        {
            adapter.filterList(tempUserMap);
        }
        else
        {
            for (Graph.Vertex currVert : userMap)
            {
                //if they name matches
                if (currVert.getKey().contains(text)) {
                    filteredVerts.add(currVert);
                }

                // if the current flags matches we should alos add them to the return list
                if (currVert.getValue().getFlag().getName().contains(text))
                {
                    // removed the search from returning a double entry if the vertex already exists
                    if (! (filteredVerts.contains(currVert))) {
                        filteredVerts.add(currVert);
                    }
                }

                //TODO: you should be able to do searching with student id as well
            }
            //attaching the filtered list to our adapter
            adapter.filterList(filteredVerts);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //TODO: you will need add the code for the spinner, so we can filter out the current list which we're viewing
        View view = inflater.inflate(R.layout.fragment_user_view_list, container, false);
        //getting where the recyclerview is going to be plugging into during run time of the application
        rv = (RecyclerView) view.findViewById(R.id.userList);

        // set up the RecyclerView
        adapter = new userAdapter();
        rvLayout = new LinearLayoutManager(getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        //searchUser = (EditText)  findViewById(R.id.searchUser);
        searchUser = (EditText) view.findViewById(R.id.searchUser);

        // making a temp array List, so the original list can be viewing up unsuccesful search
        tempUserMap = new ArrayList<>(userMap);

        //listening to any changes to the search edit box
        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // don't really care about this one
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // don't really care about this one
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // when the edit text changes we want to to do thew following
                filter(editable.toString());

            }
        });

        return view;
    }

    public static void studentView()
    {
        currView = viewType.studentView;
    }

    public static void instructorView()
    {
        currView = viewType.instructorView;
    }

    public static void practicalLoad()
    {
        currState = state.practicalLoad;
    }

    public static void instructor()
    {
        currState = state.instructor;
    }

    public static void admin()
    {
        currState = state.admin;
    }

    public static void student()
    {
        currState = state.student;
    }

    //only this fragment had the right to use this class hence, I going to be making it a private class
    private class userViewHolder extends RecyclerView.ViewHolder
    {
        private EditText nameEditor;
        private ImageView currUserFlag;
        private EditText score;
        private Button viewUser;
        private Graph.Vertex vert;
        private Practical prac;
        private String currUser;
        private static final String TAG = "userViewList.";
        private LinearLayout currLayout;

        private TextWatcher tw;

        public userViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.user_view_list, parent, false));

            //getting reference to all the major UI components in each row of the list
            nameEditor = (EditText) itemView.findViewById(R.id.userNameView);
            score = (EditText) itemView.findViewById(R.id.scoreUserView);
            viewUser = (Button) itemView.findViewById(R.id.viewUserList);
            currUserFlag = (ImageView) itemView.findViewById(R.id.userFlagView);
            currLayout = (LinearLayout) itemView.findViewById(R.id.userCardView);

            //grabbing the required objects from the activity which had called us previously which is
            //going to be userViewing.java
            pracGrader = userViewing.getGraph();
            currUser = userViewing.getCurrUser();

            // 'tw' is an event handler that will be invoked whenever the name or the score of a
            //student is going to be edited
            tw = new TextWatcher() {
                //we're required to override these methods but we actually don't ever use them in
                //the programme which we're going to be making
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                // This is where we get notified that the text has actaully being changed in the programme
                @Override
                public void afterTextChanged(Editable editable)
                {
                    //when the text change, it's going to depend on what box they clicked
                    //if they clicked the name box, the users name should change.
                    //if they clicked the score box, the users score should change
                    vert.getValue().setName(nameEditor.getText().toString());

                    Log.e(TAG, "you have changed the name");

                    //TODO: you will need to figure out how you're going to get the scores once you
                    //have implemented the practicals for the users in the programme
                }
            };

            //an event listener for when the user is going to click on the view page.
            //Once the View page is clicked a new details page should be launched which is going to include
            //all the details of the current user

            switch (currState)
            {
                case admin: case instructor:
                    // when it's goign to be teh ordinary admin add, you should noe be able to click
                    // on teh score EditText
                    score.setEnabled(false);
                    viewUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            Intent intent = new Intent(getActivity(), Details.class);
                            intent.putExtra("pracGrader", pracGrader);
                            intent.putExtra("currUser", currUser);
                            intent.putExtra("clickedPerson", nameEditor.getText().toString());
                            startActivity(intent);
                        }
                    });

                    break;
                case practicalLoad: case student:
                    viewUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), userViewing.class);
                            intent.putExtra("pracGrader", pracGrader);
                            intent.putExtra("currUser", currUser);
                            intent.putExtra("clickedPractical", nameEditor.getText().toString());
                            userViewing.practicalView();
                            startActivity(intent);
                        }
                    });

                    break;
            }
        }


        public  void bind(Graph.Vertex inVert)
        {
            this.vert = inVert;
            // we must update teh displayed names, and scores. However, for each one we have
            // to temporarily disable the corresponding event handler, or else the event
            // handler would assume the *user* has edited the informatio of the current edit
            // text box which we're viewing

            //nameEditor.removeTextChangedListener(tw);
            nameEditor.setText(inVert.getValue().getName());
            currUserFlag.setImageResource(inVert.getValue().getFlag().getImage());
            //setting what the current flag is going to be
            nameEditor.setEnabled(false);
            score.setEnabled(false);


            //TODO: you will need to do the same thing with the score which you set
            //TODO: you will need to have different bind methods. One for viewing, and one
            //for editing
        }

        //overloading the bind method, so that it will also accept practical objects as well
        public void bind(Practical inPrac)
        {
            this.prac = inPrac;
            //seeing if I can display the current title of the prac on the recycler view  at the moment
            nameEditor.setText(inPrac.getTitle());
            //score.setText(Float.toString(inPrac.getScoredMarks()));

            //you will need to do your background shit here
            float scoredMarks = inPrac.getScoredMarks();
            float availableMarks = inPrac.getTotalMark();
            float percent = scoredMarks / availableMarks;
            nameEditor.setEnabled(false);
            score.setEnabled(false);

            String currentScore = Float.toString(percent * 100);
            score.setText(currentScore);

            if (percent <= 0.5)
            {
                //if the user failed a practical set the color in red
                currLayout.setBackgroundColor(Color.RED);
            }

        }
    }


    public class userAdapter extends RecyclerView.Adapter<userViewHolder>
    {
        //TODO: come back and do this when you finished doing all your layouts for your programme

        @NonNull
        @Override
        public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            return new userViewHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull userViewHolder holder, int position)
        {
            switch(currState)
            {
                case admin: case instructor:
                    holder.bind(userMap.get(position));
                    break;

                case practicalLoad: case student:
                    holder.bind(currUserPracs.get(position));
                    break;
            }
        }

        @Override
        public int getItemCount()
        {
            int retSize = 0;
            switch (currState)
            {
                case admin: case instructor:
                    //return userMap.size();
                    retSize = userMap.size();
                    break;

                case practicalLoad: case student:
                    //return currUserPracs.size();
                    retSize = currUserPracs.size();
                    break;
            }

            return retSize;
        }

        public void filterList(ArrayList<Graph.Vertex> inList)
        {
            userMap =  inList;
            notifyDataSetChanged();
        }
    }

}