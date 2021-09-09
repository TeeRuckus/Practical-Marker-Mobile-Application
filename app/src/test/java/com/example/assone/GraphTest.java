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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

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

    @Test
    public void addInstructors()
    {
        //when adding instructors they should be automatically connected to the root node,
        //hence, if I get all root nodes neighbours all the instructors should be their
        testGraph.addVertex(admin);
        testGraph.addVertex(teacherOne);
        testGraph.addVertex(teacherTwo);
        testGraph.addVertex(teacherThree);

        //creating an array of the teacher names so they can be iterated over
        String [] actualNames = new String[] {myUtils.cleanString(teacherOne.getName()),
                myUtils.cleanString(teacherTwo.getName()),
                myUtils.cleanString(teacherThree.getName())};

        //getting everything which the admin node is currently connected too at the moment
        Graph.Vertex adminNode = testGraph.getVertex();
        HashMap<String, Graph.Vertex> connections = adminNode.getConnections();

        Set<String> keys = connections.keySet();

        for(String currKey : keys)
        {
            boolean hasKey = Arrays.asList(actualNames).contains(currKey);
            assertTrue("Admin connection has instructor: "+ currKey, hasKey);
        }
    }

    @Test
    public void addStudentAdmin()
    {
        //testing the student to see if they will be initially connected to the admin node of the application
        testGraph.addVertex(admin);
        testGraph.addVertex(studentOne);
        testGraph.addVertex(studentTwo);
        testGraph.addVertex(studentThree);

        //creating an array which are going to contain the actual names of the student
        String [] actualNames = new String [] {myUtils.cleanString(studentOne.getName()),
        myUtils.cleanString(studentTwo.getName()),
        myUtils.cleanString(studentThree.getName())};

        //getting everything which is connected to the current node at the moment
        Graph.Vertex adminNode = testGraph.getVertex();
        HashMap<String, Graph.Vertex> connections = adminNode.getConnections();

        Set<String> keys = connections.keySet();

        for (String currKey: keys)
        {
            boolean hasKey = Arrays.asList(actualNames).contains(currKey);
            assertTrue("Admin has current student: " + currKey, hasKey);
        }
    }

    @Test
    public void addStudentInstructor()
    {
        //testing the students to see if they're going to be added to an instructor node

        //setting up some basic connections in the network
        testGraph.addVertex(admin);
        testGraph.addVertex(teacherThree);

        String currInstructor = teacherThree.getName();

        //adding the student to the current instructor
        testGraph.addVertex(studentThree, currInstructor);
        testGraph.addVertex(studentFour, currInstructor);
        testGraph.addVertex(studentFive, currInstructor);
        testGraph.addVertex(studentOne, currInstructor);

        //getting all the students names
        String [] actualNames = new String [] {
                myUtils.cleanString(studentThree.getName()),
                myUtils.cleanString(studentFour.getName()),
                myUtils.cleanString(studentFive.getName()),
                myUtils.cleanString(studentOne.getName())
        };

        //retriving the current instructor node
        Graph.Vertex instructorVertex = testGraph.getVertex(currInstructor);
        HashMap<String, Graph.Vertex> connections = instructorVertex.getConnections();

        Set<String> keys = connections.keySet();


        //making sure that all the students are connected to their instructor
        for (String currKey : keys)
        {
            boolean hasKey = Arrays.asList(actualNames).contains(currKey);
            assertTrue("Instructor has curren student: " + currKey, hasKey);
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void deleteEmptyGraph()
    {
        testGraph.delVertex("ADMIN");
    }

    @Test
    public void deleteNoChildren()
    {
        testGraph.addVertex(admin);
        testGraph.addVertex(teacherOne);
        testGraph.addVertex(teacherTwo);
        testGraph.addVertex(teacherThree);


    }

}