/*
we don't need to run too many tests, as most things were tested when testing the admin class
 */
package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class InstructorTest {

    private Instructor testInstructor = new Instructor();

    @Test
    public void testAlternateMethod()
    {
        String newName = "Steve Jobs";
        String newUserName = "283690A";
        String newEmail = "Steve.jobs@curtin.edu.au";
        String newCountry = "Austria";

        testInstructor = new Instructor(newName, newUserName, newEmail, newCountry);

        //testing if the alternate instructor set the class fields to the right values
        assertEquals("alternate instructor new name", newName, testInstructor.getName());
        assertEquals("alternate instructor new userName", newUserName, testInstructor.getUserName());
        assertEquals("alternate instructor new email", newEmail, testInstructor.getEmail());
        assertEquals("alternate instructor new country", newCountry, testInstructor.getCountry());
    }
}