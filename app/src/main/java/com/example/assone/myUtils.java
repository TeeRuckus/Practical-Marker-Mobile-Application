/*
TODO:
    - you will need to add code her for validating all your passwords, to make sure that the
    right passwords were set
 */
package com.example.assone;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.Map;

/*
    my utility class which are going to have common functions which are used in the programme
 */
public final class myUtils
{
    /*private static final HashMap<String, String> countryLookUp = (HashMap<String, String>) Map.ofEntries(
        entry("ANDORRO", "ad"),
        entry("UNITED ARAB EMIRATES", "ae"),
        entry("AFGHANISTAN", "af")
    );*/

    private static final HashMap<String, String> countryLookUp = new HashMap<String, String>() {{
        put("ANDORRO", "ad");
        put("UNITED ARAB EMIRATES", "ae");
        put("AFGHANISTAN", "af");
        put("ANTIGUA AND BARBUDA", "ag");
        put("ANGUILLA", "ai");
        put("ALBANIA", "al");
        //TODO: come back and finish this off
    }};

    //look up table for the country name which correspond to which country ID
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
