package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class StudentTest
{

    private Student testStudent = new Student();

    @Test
    public void alternateMethod()
    {
        String newName = "Elon Musk";
        String newUserName = "19476700";
        String newEmail = "19476700@student.curtin.edu.au";
        String newCountry = "America";
        String newInstructor = "Admin";

        testStudent = new Student(newName, newUserName, newEmail, newCountry, newInstructor);
    }
}