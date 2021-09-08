package com.example.assone;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.String;

/*
    TODO:
        - I will need to implment the following methods from my UML diagram
            - addStdnt()
            - delStdnt()
            - editStdnt()
        - I have not included a copy constructor because I don't think that I will need it anywhere
        in my programme
 */

public abstract class User
{

    //PRIVATE CLASS FIELDS
    private String name;
    //assuming username is going to be the staff id or student id
    private String userName;
    private String email;
    private String country;
    private int password;

    public User()
    {
        name = "John Doe";
        userName = "12345678";
        email = "johnDoe@curtin.edu.au";
        country = "AUSTRALIA";
        password = 1234;
    }

    public User(String inName, String inUserName, String inEmail, String inCountry, int inPassword)
    {
        if(validateName(inName) && validateUserName(inUserName) && validateEmail(inEmail))
        {
            if(validateCountry(inCountry) && validatePassword(inPassword))
            {
                name = inName;
                userName = inUserName;
                email = inEmail;
                country = inCountry;
                password = inPassword;
            }
        }
    }

    //ACCESSOR METHODS
    public String getName()
    {
        return new String(name);
    }

    public String getUserName()
    {
        return new String(userName);
    }

    public String getEmail()
    {
        return new String(email);
    }

    public String getCountry()
    {
        return new String(country);
    }

    public int getPassword()
    {
        return password;
    }

    //MUTATOR METHODS
    public void setName(String inName)
    {
        if(validateName(inName))
        {
            name = inName;
        }
    }

    public void setUserName(String inUserName)
    {

        if(validateUserName(inUserName))
        {
            userName = userName;
        }

    }

    public void setEmail(String inEmail)
    {
        if(validateEmail(inEmail))
        {
            email = inEmail;
        }
    }

    public void setCountry(String inCountry)
    {
        if (validateCountry(inCountry))
        {
            country = inCountry;
        }
    }

    public void setPassword(int inPassword)
    {
        if(validatePassword(inPassword))
        {
            password = inPassword;
        }
    }


    /***********************************************************************************************
     * ASSERTION: always return a string which is going to be in the following format
     * [name user], [user id], [user email], [country]
     ***********************************************************************************************/
    public String toString()
    {
        return  name +","+ userName +","+ email +","+ country;
    }

    /***********************************************************************************************
     * ASSERTION: the function is going to break if an empty string is given as its input
     ***********************************************************************************************/
    protected boolean validateName(String inName)
    {
        boolean valid = true;

        if(inName.length() == 0)
        {
            throw new IllegalArgumentException("ERROR: invalid name: " + inName);
        }
        return valid;

    }


    /***********************************************************************************************
     * ASSERTION: function will break if the email is not in the format of string followed by the
     * "@" symbol another string followed by .com
     ***********************************************************************************************/
    protected boolean validateEmail(String inEmail)
    {

        /*this code is going to be adapted from the following
            source: https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
            data accessed: 7th of September 2021
            publish data: 26th of December 2020
            Author: Lokesh Gupta
         */

        //the string must match this following pattern otherwise, the string is not valid
        boolean valid = true;
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern  pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inEmail);

        if(!(matcher.matches()))
        {
            throw new IllegalArgumentException("ERROR: invalid email: " + inEmail);
        }

        return valid;
    }


    /***********************************************************************************************
     * ASSERTION: a valid user name is going to have 6 numbers, followed by a captial letter for the
     * staff id
     ***********************************************************************************************/
    protected boolean validateUserName(String inUserName)
    {
        boolean valid = true;
        //what we will want to match
        String regex = "[0-9]{6}[A-Z]{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inUserName);

        if(!(matcher.matches()))
        {
            throw new IllegalArgumentException("ERROR: invalid staff id: " + inUserName);
        }

        return valid;

    }

    protected boolean validateCountry(String inCountry)
    {
        //TODO: I will need to figure out how the country flags are going to be kept, and how to link flags to a drawable id
        boolean valid = true;

        return valid;

    }

    protected boolean validatePassword(int inPassword)
    {
        //TODO: I will need to figure out how I am going to be storing my password
        boolean valid = true;

        return valid;
    }

    /***********************************************************************************************
     * PURPOSE: children classes are going to use strings for look up functions, and deleting functions
     * hence, the purpose of the function is to trim any leading or lagging white spaces, and to make
     * the look up string case insenstive
     ***********************************************************************************************/
    protected String cleanString(String inString)
    {
        //deleting all leading and lagging white spaces
        inString = inString.trim();
        inString = inString.toUpperCase();
        return inString;
    }


}
