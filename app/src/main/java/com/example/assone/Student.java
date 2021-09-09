/* TODO:
    - you will need to over ride the  validate user name method because the way which
    a student and a staff's usert name is going to be stored is going to be really different
 */
package com.example.assone;
import java.util.*;

public class Student extends User
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

    private boolean validateInstructor(String inInstructor)
    {
        boolean valid = true;

        if (inInstructor.length() == 0)
        {
            throw new IllegalArgumentException("ERROR: make sure that the instructor has more than one letter");
        }

        return valid;
    }

    //overriding all accessor methods so the user will not have permissions to change data
    public void setName(String inName)
    {
        throw new IllegalArgumentException("Error: don't have apporpriate permissions to set name");
    }

    public void setUserName(String inUserName)
    {
        throw new IllegalArgumentException("Error: don't have appropriate permissions to set user name");
    }

    public void setEmail(String inEmail)
    {
        throw new IllegalArgumentException("Error: don't have appropriate permissions to set email");
    }

    public void setCountry(String inCountry)
    {
        throw new IllegalArgumentException("Error: don't have appropriate permissions to set country");
    }

    public void setPassword(int inPassword)
    {
        throw new IllegalArgumentException("Error: don't have appropriate permissions to set password");
    }


    //you will need to override the other methods so when they're used they can't be used at all

    public String toString()
    {
        return "Student," + super.toString() + ",instructor: " + currInstructor;
    }
}
