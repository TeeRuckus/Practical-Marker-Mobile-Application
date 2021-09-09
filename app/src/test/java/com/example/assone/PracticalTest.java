package com.example.assone;

//adding this shit in, I don't know if I will actually need ior not in running my code
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Hashtable;
import java.util.List;

public class PracticalTest {
    //actually seeing if I can create a practical object
    private Practical testPrac = new Practical();
    private static final double TOL = 0.001;

    @Test
    public void testAccessors() {
        //results which were obtained from the actual object
        float resultMarks = testPrac.getTotalMark();
        Hashtable<String, Practical.taskNode> resultSectionMarks = testPrac.getMarks();
        String resultDescription = testPrac.getdescrp();
        String resultTitle = testPrac.getTitle();

        //the results which we should be getting from the tests
        String actualTitle = "Practical title";
        String actualDescrpt = "this is a description of the class";
        float actualMarks = 100;
        Hashtable<String, Practical.taskNode> sectionMarks = new Hashtable<String, Practical.taskNode>();

        //testing if the obtained results are going to meet our expected results from the experimentation
        assertEquals("ACCESSOR: default constructor: getTotalMarks", actualMarks, resultMarks, TOL);
        assertEquals("ACCESSOR: default constructor: getTitle", actualTitle, resultTitle);
        assertEquals("ACCESSOR: default constructor: getDescp", actualDescrpt, resultDescription);
        assertEquals("ACCESSOR: default constructor: getMarks", sectionMarks, resultSectionMarks);
    }

    @Test
    public void testMutators() {
        String actualTitle = "Practical One";
        String actualDescrpt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In " +
                "consectetur lacinia efficitur. Nullam sed quam in libero auctor mollis sit amet " +
                "nec magna. Donec sodales a ante et mattis. Pellentesque at ligula sed ex maximus " +
                "fermentum vitae sit amet purus. Mauris laoreet hendrerit massa. Proin vitae e" +
                "nim tellus. Maecenas malesuada.";
        float actualAvailMarks = 100;

        testPrac.setTitle(actualTitle);
        testPrac.setDescrpt(actualDescrpt);
        testPrac.setTotalMarks(actualAvailMarks);

        assertEquals("MUTATOR: setting data valid for title", actualTitle, testPrac.getTitle());
        assertEquals("MUTATOR: setting data valid for description", actualDescrpt, testPrac.getdescrp());
        assertEquals("MUTATOR: setting data valid for total marks", actualAvailMarks, testPrac.getTotalMark(), TOL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorsInvalidInputTitle() {
        //testing the mutators but I am going to try to set with invalid input for the mutators
        String invalidTitle = "";
        testPrac.setTitle(invalidTitle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorsInvalidDescriptionOne() {
        String invalidDescrptOne = "";
        testPrac.setDescrpt(invalidDescrptOne);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorsInvalidDescriptionTwo() {
        String invalidDescrptTwo = "this doesn't have enough words to be a description";
        testPrac.setDescrpt(invalidDescrptTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorsInvalidMarksOne() {
        float invalidTotalMarksOne = 0;
        testPrac.setTotalMarks(invalidTotalMarksOne);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorsInvalidMarksTwo() {
        float invalidTotalMarksTwo = -1;
        testPrac.setTotalMarks(invalidTotalMarksTwo);
    }

    @Test
    public void testAccessorsCopyReturn() {
        //making sure that the accessors are actually going to be returning a copy of the object, and
        //not returning the memory address of the object, and then being able to change it

        //forcing the current test specimen to tbe the default one
        testPrac = new Practical();
        String actualTitle = "Practical title";
        String actualDescrpt = "this is a description of the class";

        String retTitle = testPrac.getTitle();
        String retDescrpt = testPrac.getdescrp();

        //seeing if copies are returned or actuall memory addresses are going to be returned
        retTitle = "this is going to be incorrect";
        retDescrpt = "make me fail";

        //getting what is going to be at the current memory addresses
        retTitle = testPrac.getTitle();
        retDescrpt = testPrac.getdescrp();

        //chaning the returned string to see if it will effect the string in the actual object
        assertEquals("ACCESSOR: returning a copy of fields title", actualTitle, retTitle);
        assertEquals("ACCESSOR: returning a copy of fields description", actualDescrpt, retDescrpt);
    }

    @Test
    public void testaccessorsTaskNode()
    {
        //Practical.taskNode newNode = new testPrac.taskNode();
        Practical.taskNode newNode  = testPrac.new taskNode();
        String defaultTitle = "task sample";
        String defaultDescription = "description of the task which has to be completed";
        float defaultScoredMarks = 0;
        int defaultAvailMarks = 10;

        assertEquals("ACCESSORS: task node normal use title", defaultTitle, newNode.getTaskTitle());
        assertEquals("ACCESSORS: task node normal use description", defaultDescription, newNode.getTaskDescrpt());
        assertEquals("ACCESSORS: task node normal use scored marks", defaultScoredMarks, newNode.getScoredMarks(), TOL);
        assertEquals("ACCESSORS: task node normal available marks", defaultAvailMarks, newNode.getAvailMarks());
    }

    @Test
    public void testMutatorsTaskNode()
    {
        String newTitle = "UI rotation";
        String  newShortDescrpt = "The task required the students to be able to rotate the UI in" +
                " whatever activity which they’re in. Therefore, no information should be lost" +
                " when the UI is rotated, and the UI should be consistent when rotation occurs as" +
                " well. Full marks will be awarded for anyone which can complete all tasks successfully";
        int newAvailMarks = 100;
        float newScoredMarks = 75;

        Practical.taskNode newNode = testPrac.new taskNode();

        //making all the changes of the methods
        newNode.setAvailMarks(newAvailMarks);
        newNode.setTaskTitle(newTitle);
        newNode.setTaskDescrpt(newShortDescrpt);
        newNode.setScoredMarks(newScoredMarks);

        assertEquals("MUTATORS: taskNode - setting the available marks", newAvailMarks, newNode.getAvailMarks());
        assertEquals("MUTATORS: taskNode - setting the task title", newTitle, newNode.getTaskTitle());
        assertEquals("MUTATORS: taskNode - setting the task description", newAvailMarks, newNode.getAvailMarks(), TOL);
        assertEquals("MUTATORS: taskNode - setting the scored marks", newScoredMarks, newNode.getScoredMarks(), TOL);
    }

    //testing invalid input for the accessors
    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidScoredMarks()
    {
        //you should not be able to assign marks which are higher than the actuall available marks
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setAvailMarks(10);
        newNode.setScoredMarks(20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidDescrpt()
    {
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setTaskDescrpt("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidDescrptTwo()
    {
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setTaskDescrpt("this description is not long enough for it to be one");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidMarks()
    {
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setAvailMarks(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidMarksTwo()
    {
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setAvailMarks(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMutatorTaskNodeInvalidTitle()
    {
        Practical.taskNode newNode = testPrac.new taskNode();
        newNode.setTaskTitle("");
    }

    @Test
    public void testSectionMethods()
    {
        String actualDescrpt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In " +
                "consectetur lacinia efficitur. Nullam sed quam in libero auctor mollis sit amet " +
                "nec magna. Donec sodales a ante et mattis. Pellentesque at ligula sed ex maximus " +
                "fermentum vitae sit amet purus. Mauris laoreet hendrerit massa. Proin vitae e" +
                "nim tellus. Maecenas malesuada.";

        String  [] pracTitles = new String[] {"Practical one", "Practical two", "Practical three",
                "Practical four", "Practical five"};
        int []  availableMarks = new int [] {20, 20, 20, 20, 20};
        float [] scoredMarks = new float [] {19, 18, 13, 10, 5};

        float average = 0;
        for (int ii = 0; ii < scoredMarks.length; ii++)
        {
            average += scoredMarks[ii];
        }

        int totalMarks = 0;
        for (int ii = 0; ii < availableMarks.length; ii++)
        {
            totalMarks += availableMarks[ii];
        }
        average /= totalMarks;

        //adding all five practical sections to the object
        for(int ii = 0; ii < pracTitles.length; ii++)
        {
            testPrac.addSection(pracTitles[ii], actualDescrpt, scoredMarks[ii], availableMarks[ii]);
        }

        //testing finding sections
        for(int ii = 0; ii < pracTitles.length; ii++)
        {
            Practical.taskNode currSection = testPrac.findSection(pracTitles[ii]);

            //asserting if the title, description, scored marks, and available marks will match
            //to the ones which were stored in the object
            assertEquals("Retrieved title from practical section: " +pracTitles[ii],
                    myUtils.cleanString(pracTitles[ii]), currSection.getTaskTitle());
            assertEquals("Retrieved description from practical section: " +pracTitles[ii],
                    actualDescrpt, currSection.getTaskDescrpt());
            assertEquals("Retrieved available marks from practical section: " +pracTitles[ii],
                    availableMarks[ii], currSection.getAvailMarks());
            assertEquals("Retrieved scored mark from practical section: " +pracTitles[ii],
                    scoredMarks[ii], currSection.getScoredMarks(), TOL);
        }

        //validating that you can get the average for the current made practical
        assertEquals("Testing if the current average of the pracs can be obtained", average,
                testPrac.getAverage(), TOL);

        //deleting a practical from the pracs which are going to be available

        int toBedeleted = 3;
        Practical.taskNode deletedPrac = testPrac.delSection(pracTitles[toBedeleted]);

        //ensuring the deleted is actually corresponding the correct poistion
        assertEquals("Delete task Node, checking if it was the right one which was deleted",
                myUtils.cleanString(pracTitles[toBedeleted]), deletedPrac.getTaskTitle());
        assertEquals("Deleted task Node: checking the description", actualDescrpt, deletedPrac.getTaskDescrpt());
        assertEquals("Deleted task NOde: checking available marks", availableMarks[toBedeleted],
                deletedPrac.getAvailMarks());
        assertEquals("Deleted task Node: checking the scored marks", scoredMarks[toBedeleted],
                deletedPrac.getScoredMarks(), TOL);

    }
}