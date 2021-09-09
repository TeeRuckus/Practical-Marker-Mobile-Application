/*
TODO:
    - you will need to make this database only accessible from this
    application
    -and you will need to make everything in this database encrypted as well
    so that not anyone can access the contents of this database
 */
package com.example.assone;

public class PasswordSchema
{
    public static class PassWordTable
    {
        //the name of the table
        public static final String NAME = "passwords";

        //creating the class of the table
        public static class cols
        {
            //the name of each column in the created table
            public static final String USER_NAME = "user_name";
            public static final String PASSWORD = "user_password";
        }

    }
}
