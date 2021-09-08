package com.example.assone;
import java.util.*;
/*
 TODO:
    - so how you can send the practical from the admin to all the instructors: you should handle this inside your graph class
    - how you can send the prac from the current instructor to all the students: you should handle this inside your graph class
    - then the instructor can edit each one of those individual practicals which he created for
    each student in his class at the current moment
    - you will need to delete the code which was supposed to handle all the details of the instructor
 */

public class Admin extends User{
    //the classfieleds which are unique to the admin
    //private Hashtable<String, Instructor> instructors;
    private Hashtable<String, Practical> practicals;


    public Admin()
    {
        super();
        //instructors = new Hashtable<String, Instructor>();
        practicals = new Hashtable<String, Practical>();
    }

    //TOOD: you might not even need this method, you're going to load your data from a data base anyways
    //so you can use the laod method to just load all your data into this data strcutre
    public Admin(String inName, String inUserName, String inEmail, String inCountry, int inPassword,
                 Hashtable<String, Instructor> inInstructors, Hashtable<String, Practical> inPracs)
    {
        super(inName, inUserName, inEmail, inCountry, inPassword);
        //instructors = (Hashtable<String, Instructor>) inInstructors.clone();
        practicals = (Hashtable<String, Practical>) inPracs.clone();
    }

    //TODO: what you will have to do here, is that you have to give the instructor the practicals which you have at the current moment
    /*public void addInstructor(Instructor inInstructor)
    {
        //making the keys case insentive
        String nameKey = super.cleanString(inInstructor.getUserName());
        instructors.put(nameKey, inInstructor);
    }*/

    /*public void delInstructor(String inName)
    {
        //making sure the inputted instructor name is going to be a valid name
        if(super.validateName(inName))
        {
            if (instructors.isEmpty())
            {
                throw new IllegalArgumentException("Error: no instrustors have being added as yet: " + instructors.size());
            }
            else
            {
                //cleaning any white spaces, and making the look up name case insensitive
                inName = super.cleanString(inName);
                instructors.remove(inName);
            }
        }

    }*/

    public void addPrac(Practical inPrac)
    {
        String titleKey = super.cleanString(inPrac.getTitle());
        practicals.put(titleKey, inPrac);
    }

    public void delPrac(String pracName)
    {
        if(super.validateName(pracName))
        {
            if (practicals.isEmpty())
            {
                throw new IllegalArgumentException("Error: no practicals have being added as yet: " + practicals.size());
            }
            else
            {
                //cleaning any white spaces, and making the look up name case insensitive
                pracName = super.cleanString(pracName);
                practicals.remove(pracName);
            }

        }
    }

    //TODO: you will need to get rid of this method completely
    /*public String dispInstructors()
    {
        List<String> retInstructors = new LinkedList<String>();
        //getting all the keys of the instructors hashtable so we can iteratate over it
        Set<String> myKeys = instructors.keySet();
        for (String key : myKeys)
        {
            retInstructors.addLast((instructors.get(key)).toString());
        }
        return retInstructors;
    }*/

    /*public String dispStudents()
    {
        List<String> students = new LinkedList<String>();
        //getting all the keys of the instructors
        Set<String> myKeys = instructors.keySet();

        for (String key : myKeys)
        {
            currInstructor = instructors.get(key);
            currStudents = currInstructor.getStudents();
            //getting all the keys of the students
            Set<String> currKeys = currStudents.keySet();
            for (String studentKey : currKeys)
            {
                students.addLast((currStudents.get(studentKey)).toString());
            }
        }

        return students;
    }*/


    public String toString()
    {
        return "Admin," + super.toString();
    }
}
