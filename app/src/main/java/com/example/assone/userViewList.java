/*TODO:
    - you will need to figure out a way which you can get the prac grader into this class
 */
package com.example.assone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.HashMap;

public class userViewList extends Fragment
{

    //my own defined class fields for this recycler vie
    private HashMap<String, Graph.Vertex> userMap;

    private RecyclerView rv;
    private userAdapter adapter;
    private LinearLayoutManager rvLayout;

    private String currUser;
    private Graph pracGrader;

    private enum state {
        admin,
        instructor,
        student
    }

    private static state currState;

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

        userMap = new HashMap<>();
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
        //getting all the reference for the major UI elements
        View view = inflater.inflate(R.layout.fragment_user_view_list, container, false);

        //getting where the recyclerview is going to be plugging into during run time of the application
        rv = (RecyclerView) view.findViewById(R.id.userList);


        // set up the RecyclerView
        adapter = new userAdapter();
        rvLayout = new LinearLayoutManager(getActivity());
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user_view_list, container, false);
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

        private TextWatcher tw;

        /*public userViewHolder(@NonNull View itemView) {
            super(itemView);
        } */

        public userViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.user_view_list, parent, false));

            //getting reference to all the major UI components

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

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}