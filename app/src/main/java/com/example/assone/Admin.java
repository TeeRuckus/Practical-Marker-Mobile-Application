package com.example.assone;
import java.util.*;
/*
 TODO:
    - you will need to think of way which you can send data in between the classes
    - so how you can send the practical from the admin to all the instructors
    - how you can send the prac from the current instructor to all the students
    - then the instructor can edit each one of those individual practicals which he created for
    each student in his class at the current moment
 */

public class Admin extends extends User{
    //the classfieleds which are unique to the admin
    private Hashtable<String, Instructor> instructors;
    private Hashtable<String, Practical> practicals;


    public Admin()
    {
        super();
        instsructors = new Hashtable<String, Instructor>();
        practicals = new Hashtable<String, Practical>():
    }

    //TOOD: you might not even need this method, you're going to load your data from a data base anyways
    //so you can use the laod method to just load all your data into this data strcutre
    public Admin(String inName, String inUserName, String inEmail, String inCountry, int inPassword, /
                 Hashtable<String, Instructor> inInstructors, Hashtable<String, Practical> inPracs)
    {
        super(inName, inUserNam, inEmail, inCountry, inPassword);
        instructors = inInstructors.clone();
        practicals = inPracs.clone();
    }

    //TODO: what you will have to do here, is that you have to give the instructor the practicals which you have at the current moment
    public void addInstructor(Instructor inInstructor)
    {
        //making the keys case insentive
        String nameKey = super.cleanString(inInstructor.getUserName);
        instructors.put(nameKey, inStructor);
    }

    public void delInstructor(String inName)
    {
        //making sure the inputted instructor name is going to be a valid name
        if(super.validateName(inName))
        {
            if (instructors.isEmpty())
            {
                throw new IllegalArgumentException("Error: no instrustors have being added as yet: " + instrusctors.size());
            }
            else
            {
                //cleaning any white spaces, and making the look up name case insensitive
                inName = super.cleanString(inName);
                instructors.remove(inName);
            }
        }

    }

    public void editInstructor()
    {
        //TODO: come back to this when you have actually implemented instructor, and have tested it
    }

    public void addPrac(Pracatical inPrac)
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

    //YOU WILL NEED ANOTHER METHOD WHICH IS GOING TO DELETE THE PRACTICAL FROM AN ACTUAL STUDENT
    //AND DO THE CHECKS TO MAKE SURE THAT THEY IS NO MEANINGFUL DATA INSIDE THEIR

    public void editPrac()
    {
        //TODO: come back to  this when you have actaully implemented this code
    }

    public void dispStudents()
    {

    }

    public String dispInstructors()
    {
        List<String> retInstructors = new LinkedList<String>();
        //getting all the keys of the instructors hashtable so we can iteratate over it
        Set<String> myKeys = instructors.keySet();
        for (String key : myKeys)
        {
            retInstructors.addLast((instructors.get(key)).toString());
        }
        return retInstructors;
    }

    public String dispStudents()
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
    }


    public String toString()
    {
        return "Admin," + super().toString();
    }
}
