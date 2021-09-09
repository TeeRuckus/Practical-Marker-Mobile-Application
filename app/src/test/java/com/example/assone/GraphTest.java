package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class GraphTest
{
    Graph testGraph = new Graph();

    @Before
    public void setUpConenctions()
    {
        Instructor teacherOne = new Instructor();
        Instructor teacherTwo  = new Instructor("Tim Cook", "283691D", "Tim.cook@curtin.edu.au",
                "America");
    }

}