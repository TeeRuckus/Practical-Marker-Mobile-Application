/*
TODO:
    - once you have tested the accessors here you don't need to test them for instructor becasue
    they're going to inheret from teh same parent class which is is going to be user
 */
package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class AdminTest
{
    private Admin testAdmin = new Admin();
    private static final double TOL = 0.001;

    @Test
    public void testAccessors()
    {
        assertEquals("ACCESSOR: default values admin name", "John Doe", testAdmin.getName());
        assertEquals("ACCESSOR: default values admin username", "12345678", testAdmin.getUserName());
        assertEquals("ACCESSOR: default values admin email", "johnDoe@curtin.edu.au", testAdmin.getEmail());
        assertEquals("ACCESSOR: default values admin country", "AUSTRALIA", testAdmin.getCountry());
        assertEquals("ACCESSOR: default values admin password", 1234, testAdmin.getPassword());
    }

}