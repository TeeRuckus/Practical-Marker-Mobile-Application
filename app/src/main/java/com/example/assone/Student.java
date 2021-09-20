/* TODO:
    - you will need to over ride the  validate user name method because the way which
    a student and a staff's usert name is going to be stored is going to be really different
 */
package com.example.assone;
import java.util.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student extends User implements Serializable
{
    //the classfield which are going to be unique to the student
    private String currInstructor;

    public Student()
    {
        super();
        currInstructor = "Admin";
    }

    //we will need an alternate constructor when the admin will create student
    public Student(String inName, String inUserName, String inEmail, String inCountry, String inInstructor)
    {
        super(inName, inUserName, inEmail, inCountry);
        if (super.validateName(inInstructor))
        {
            currInstructor = inInstructor;
        }
    }


    public String getInstructor()
    {
        return new String(currInstructor);
    }

    public void setInstructor(String inInstructor)
    {
        if(validateInstructor(inInstructor))
        {
            currInstructor = inInstructor;
        }

    }

    @Override
    public String getType()
    {
        return "STUDENT";
    }
    private boolean validateInstructor(String inInstructor)
    {
        boolean valid = true;

        if (inInstructor.length() == 0)
        {
            throw new IllegalArgumentException("ERROR: make sure that the instructor has more than one letter");
        }

        return valid;
    }

    @Override
    protected boolean validateUserName(String inUserName)
    {
        boolean valid = true;
        //the pattern which we will want to be matching for the student email address

        String regex = "[0-9]{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inUserName);

        if(!(matcher.matches()))
        {
            throw new IllegalArgumentException("ERROR: invali student id" + inUserName);
        }

        return  valid;
    }

    public String toString()
    {
        return "Student," + super.toString() + ",instructor: " + currInstructor;
    }
}
