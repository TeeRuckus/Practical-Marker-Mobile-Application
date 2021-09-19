/*TODO:
    - you will need to figure out a way which you can get the prac grader into this class
 */
package com.example.assone;

import android.content.Intent;
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

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

public class userViewList extends Fragment
{

    //my own defined class fields for this recycler vie
    //private HashMap<String, Graph.Vertex> userMap;
    private ArrayList<Graph.Vertex> userMap;

    private RecyclerView rv;
    private userAdapter adapter;
    private LinearLayoutManager rvLayout;
    //for debuggin and logging purposes of the programme
    private static final String  TAG = "userViewList.";

    private String currUser;
    private Graph pracGrader;

    private enum state {
        admin,
        instructor,
        student
    }

    private static state currState;

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
        pracGrader = userViewing.getGraph();
        currUser = userViewing.getCurrUser();

        //depending on who the user is going to be, it's going to dependent on how the data is going to be loaded
        switch(currState)
        {
            case admin:
                //load all the vertices which are going to be in the current network
                userMap = pracGrader.adminLoad();

                break;
            case instructor:
                //do nothign for the current moment
                userMap = pracGrader.instructorLoad(currUser);
                break;
            case student:
                //do nothign for the current moment
                break;
        }
       /* I don't think that you actually need this code to do what you want to do
       if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
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

        return view;
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
        private EditText score;
        private Button viewUser;
        private Graph.Vertex vert;
        private String currUser;

        private TextWatcher tw;

        public userViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.user_view_list, parent, false));

            //getting reference to all the major UI components in each row of the list
            nameEditor = (EditText) itemView.findViewById(R.id.userNameView);
            score = (EditText) itemView.findViewById(R.id.scoreUserView);
            viewUser = (Button) itemView.findViewById(R.id.viewUserList);

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

                    //TODO: you will need to figure out how you're going to get the scores once you
                    //have implemented the practicals for the users in the programme
                }
            };

            //an event listener for when the user is going to click on the view page.
            //Once the View page is clicked a new details page should be launched which is going to include
            //all the details of the current user

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
            //nameEditor.addTextChangedListener(tw);


            //TODO: you will need to do the same thing with the score which you set
            //TODO: you will need to have different bind methods. One for viewing, and one
            //for editing
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
            holder.bind(userMap.get(position));
        }

        @Override
        public int getItemCount()
        {
           return userMap.size();
        }
    }

}