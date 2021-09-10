/*
TODO:
    - you will need to remove the student hash table as it's not the responsibility of this class to
    keep a track of the students, that is going to be the reponsibility of the graph only
 */
package com.example.assone;
import java.util.*;
import java.io.Serializable;

public class Instructor extends User implements Serializable
{
    public Instructor()
    {
        super();
    }


    //we will need an alternate constructor when the admin creates an instructor
    public Instructor(String inName, String inUserName, String inEmail, String inCountry)
    {
        super(inName, inUserName, inEmail, inCountry);
    }

    //we will need to disable all the mutators for the instructor, so he can't make changes on his
    //account

    @Override
    public String getType()
    {
        return "INSTRUCTOR";
    }


    public String toString()
    {
        return   "Instructor," + super.toString();
    }
}
