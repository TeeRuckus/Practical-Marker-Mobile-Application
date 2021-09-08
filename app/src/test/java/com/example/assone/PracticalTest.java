package com.example.assone;

//adding this shit in, I don't know if I will actually need ior not in running my code
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Hashtable;

public class PracticalTest {
    //actually seeing if I can create a practical object
    private Practical testPrac = new Practical();

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
        assertEquals("ACCESSOR: default constructor: getTotalMarks", actualMarks, resultMarks, 0.001);
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
        assertEquals("MUTATOR: setting data valid for total marks", actualAvailMarks, testPrac.getTotalMark(), 0.001);
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
    public void testAddPracticalSections() {

    }

    @Test
    public void testRetrievePracticalSection() {

    }

    @Test
    public void testChangeSetMark() {

    }

    @Test
    public void testDelPractical()
    {

    }

}