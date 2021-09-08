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
}
