/*
TODO
    -  you will need to becareful on how you're going to be storing your keys
    if you want them to be all case insensitve, make sure that they're all like that from
    the beginning
 */
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

    //creating the admin for the network
    private Admin admin = new Admin("King Sajib", "295802P", "king.sajib@curtin.edu.au",
            "Australia");

    //creting all the testing instructors
    private Instructor teacherOne = new Instructor();
    private Instructor teacherTwo  = new Instructor("Tim Cook", "283691D", "Tim.cook@curtin.edu.au",
            "America");
    private Instructor teacherThree = new Instructor("Elon Musk", "283692E", "elon.musk@curtin.edu.au",
            "South Africa");


    //creating all the testing students
    private Student studentOne = new Student();
    private Student studentTwo = new Student("Tawana Kwaramba", "19476700", "19476700@student.curtin.edu.au",
            "Zimbabe", teacherTwo.getName());
    private Student studentThree = new Student("Sofia Boniglio", "14495655", "14495655@student.curtin.edu.au",
            "Australia", teacherThree.getName());
    private Student studentFour = new Student("Ashlee Capellan", "14224671", "14224671@curtin.edu.au",
            "Australia", teacherThree.getName());
    private Student studentFive = new Student("Elinor Raco", "14147356", "14147356@student.curtin.edu.au",
            "Australia", teacherThree.getName());

    @Test(expected = IllegalArgumentException.class)
    public void addingVertexWithoutRootNodeInstructor()
    {
        testGraph.addVertex(teacherOne);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingVertexWithoutRootNodeStudent()
    {
        testGraph.addVertex(studentOne);
    }

    @Test
    public void createAdmin()
    {
        testGraph.addVertex(admin);
        Graph.Vertex adminVert = testGraph.getVertex();
        assertEquals("returned admin ", "ADMIN", adminVert.getKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void doubleAdmin()
    {
        testGraph.addVertex(admin);
        testGraph.addVertex(admin);
    }

}