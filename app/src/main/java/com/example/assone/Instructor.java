/*
TODO:
    - you will need to remove the student hash table as it's not the responsibility of this class to
    keep a track of the students, that is going to be the reponsibility of the graph only
 */
package com.example.assone;
import java.util.*;

public class Instructor extends User
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

    public String toString()
    {
        return   "Instructor," + super.toString();
    }
}
