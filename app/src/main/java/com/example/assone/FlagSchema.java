/*
TODO:
    - I don't think that I will actuall need this to make my application work
 */
package com.example.assone;

public class FlagSchema
{
    public static class FlagTable
    {
        //the name of the current table
        public static final String NAME = "flags";

        //creating the class of the table columnns
        public static class Cols
        {
            //the name of each column in my data base
            public static final String COUNTRY_NAME = "Country_name";
            public static final String COUNTRY_ID = "Country_ID";
        }
    }
}