/*
TODO:
    - you will need to remove the student hash table as it's not the responsibility of this class to
    keep a track of the students, that is going to be the reponsibility of the graph only
 */
package com.example.assone;
import java.util.*;

public class Instructor extends User
{
    //the classfields which are going to be unique to the instructor
    //private Hashtable<String, Student> students;
    private Hashtable<String, Practical> practicals;

    public Instructor()
    {
        super();
        //students = new Hashtable<String, Student>();
        practicals = new Hashtable<String, Practical>();
    }


    //we will need an alternate constructor when the admin creates an instructor
    public Instructor(String inName, String inUserName, String inEmail, String inCountry, int inPassword,
            Hashtable<String, Student> inStudents, Hashtable<String, Practical> inPracticals)
    {
        super(inName, inUserName, inEmail, inCountry, inPassword);
        //students = inStudents.clone();
        practicals = (Hashtable<String, Practical>) inPracticals.clone();
    }



    /*public String dispStudents()
    {
        List<String> retStudents = new LinkedList<String>();
        //getting all the students which belong to the current instructor
        Set<String> myKeys = retStudents.keySet();

        //going through each student, and adding them to the return list
        for (String key : myKeys)
        {
            retStudents.addLast((students.get(key)).toString());
        }

        return retStudents;
    }*/

    public void editPrac()
    {
        //TODO: I will need to come back to this when I actually figure out how I want to edit the practicals
    }

    public String toString()
    {
        return   "Instructor," + super.toString();
    }
}
