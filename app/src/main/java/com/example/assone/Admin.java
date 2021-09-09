package com.example.assone;
import java.util.*;
/*
 TODO:
    - so how you can send the practical from the admin to all the instructors: you should handle this inside your graph class
    - how you can send the prac from the current instructor to all the students: you should handle this inside your graph class
    - then the instructor can edit each one of those individual practicals which he created for
    each student in his class at the current moment
    - you will need to come back and do the flag stuff later on
 */

public class Admin extends User{

    public Admin()
    {
        super();
    }

    //TOOD: you might not even need this method, you're going to load your data from a data base anyways
    //so you can use the laod method to just load all your data into this data strcutre
    public Admin(String inName, String inUserName, String inEmail, String inCountry)
    {
        super(inName, inUserName, inEmail, inCountry);
    }

    public void addPrac(Practical inPrac)
    {
        String titleKey = myUtils.cleanString(inPrac.getTitle());
        practicals.put(titleKey, inPrac);
    }


    public Practical delPrac(String pracName)
    {
        Practical removedPrac = null;

        if(super.validateName(pracName))
        {
            if (practicals.isEmpty())
            {
                throw new IllegalArgumentException("Error: no practicals have being added as yet: " + practicals.size());
            }
            else
            {
                //cleaning any white spaces, and making the look up name case insensitive
                pracName = myUtils.cleanString(pracName);
                removedPrac = practicals.remove(pracName);
            }
        }

        return removedPrac;
    }

    public String toString()
    {
        return "Admin," + super.toString();
    }
}
