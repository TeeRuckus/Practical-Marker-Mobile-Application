/*
TODO:
    - you will need to add code her for validating all your passwords, to make sure that the
    right passwords were set
 */
package com.example.assone;

/*
    my utility class which are going to have common functions which are used in the programme
 */
public final class myUtils
{
    public static String cleanString(String inString)
    {
        //deleting all leading and lagging white spaces
        inString = inString.trim();
        inString = inString.toUpperCase();
        return inString;
    }

    public static boolean validatePassword(Admin inAdmin)
    {
        return true;
    }

    public static boolean validatePassword(Student inStudent)
    {
        return true;
    }

    public static boolean validatePassword(Instructor inInstructor)
    {
        return true;
    }

    //The following functions are going to be respoinsible for the passowrd managment
    public static int getPassword(Admin inAdmin)
    {
        return 0;
    }

}
