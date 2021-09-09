/*
TODO:
    - once you have tested the accessors here you don't need to test them for instructor becasue
    they're going to inheret from teh same parent class which is is going to be user
    - I am not going to be testing password in here, as that's going to be the responsibility of the application
    - I am going to leave the flag stuff to the end, you will need to come back and finish that off
 */
package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

public class AdminTest {
    private Admin testAdmin = new Admin();
    private static final double TOL = 0.001;

    @Test
    public void testAccessors() {
        assertEquals("ACCESSOR: default values admin name", "John Doe", testAdmin.getName());
        assertEquals("ACCESSOR: default values admin username", "12345678", testAdmin.getUserName());
        assertEquals("ACCESSOR: default values admin email", "johnDoe@curtin.edu.au", testAdmin.getEmail());
        assertEquals("ACCESSOR: default values admin country", "AUSTRALIA", testAdmin.getCountry());
    }

    @Test
    public void testMutators() {
        String newName = "Tawana Kwaramba";
        String newUserName = "283690A";
        String newEmail = "tawana.kwaramba@curtin.edu.au";
        String newCountry = "zimbabwe";

        testAdmin.setName(newName);
        testAdmin.setUserName(newUserName);
        testAdmin.setEmail(newEmail);
        testAdmin.setCountry(newCountry);


        //seeing if I can retrieve the correct data from the newly set admin
        assertEquals("Mutator tests: changing admin name", newName, testAdmin.getName());
        assertEquals("Mutator tests: changing admin user name", newUserName, testAdmin.getUserName());
        assertEquals("Mutator tests: changing email", newEmail, testAdmin.getEmail());
        assertEquals("Mutator tests: changing admin country", newCountry, testAdmin.getCountry());
    }

    //testing all the invalid users of mutators
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUserName() {
        testAdmin.setUserName("king slayer");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidName() {
        testAdmin.setName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        testAdmin.setEmail("@curtin.edu.au");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmailTwo() {
        testAdmin.setEmail("tawana@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmailThree() {
        testAdmin.setEmail("not an email address");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCountry() {
        testAdmin.setCountry("");
    }

    //@Test(expected = IllegalArgumentException.class)
    public void testInvalidCounrtyNotFound() {
        /*TODO: to be implemented, once I have figure out how to do the country data base,
        and to store the flags correctly for each country*/
    }

    //testing if the accessors are actually going to return a new memory address
    @Test
    public void testAccessorCopyReturn() {
        //making sure that the accessor are going to return a copy of the data which it was asked to retrieve

        //forcing the current class field to be the default field
        testAdmin = new Admin();
        String newName = "Tawana Kwaramba";
        String newUserName = "283690A";
        String newEmail = "tawana.kwaramba@curtin.edu.au";
        String newCountry = "Zimbabwe";

        //getting the stored strings
        String retName = testAdmin.getName();
        String retUserName = testAdmin.getUserName();
        String retEmail = testAdmin.getEmail();
        String retCountry = testAdmin.getCountry();

        retName = "this is not it mate";
        retUserName = "this is not it mate";
        retEmail = "this is not it mate";
        retCountry = "this is not it mate";

        //seeing if the original set things are going to be at the current memory address
        assertEquals("ACCESSOR: returning a copy an object name", newName, testAdmin.getName());
        assertEquals("ACCESSOR: returning a copy an object user name", newUserName, testAdmin.getUserName());
        assertEquals("ACCESSOR: returning a copy an object email", newEmail, testAdmin.getEmail());
        assertEquals("ACCESSOR: returning a copy an object email", newCountry, testAdmin.getCountry());
    }

    //testing if the alternate constructor is working well
    @Test
    public void testAlteranteConstructor() {
        String newName = "Mark Nguyen";
        String newUserName = "283690A";
        String newEmail = "Mark.Nguyen@curtin.edu.au";
        String newCountry = "vietnam";

        Admin newAdmin = new Admin(newName, newUserName, newEmail, newCountry);

        //testing if the class fields have being set correctly by the alternate constructor
        assertEquals("ALTERNATE CONSTRUCTOR: setting the name", newName, newAdmin.getName());
        assertEquals("ALTERNATE CONSTRUCTOR: setting the userName", newUserName, newAdmin.getUserName());
        assertEquals("ALTERNATE CONSTRUCTOR: setting the email", newEmail, newAdmin.getEmail());
        assertEquals("ALTERNATE CONSTRUCTOR: setting the country", newCountry, newAdmin.getCountry());
    }


    //testing all the interaction with the practical objects
    @Test
    public void addingPracticals()
    {
        String newDescrpt = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In " +
                "consectetur lacinia efficitur. Nullam sed quam in libero auctor mollis sit amet " +
                "nec magna. Donec sodales a ante et mattis. Pellentesque at ligula sed ex maximus " +
                "fermentum vitae sit amet purus. Mauris laoreet hendrerit massa. Proin vitae e" +
                "nim tellus. Maecenas malesuada.";
        String pracOneName = "facebook";
        String pracTwoName = "google";

        Practical newPracOne = new Practical(pracOneName, newDescrpt);
        Practical newPracTwo = new Practical(pracTwoName, newDescrpt);

        testAdmin.addPrac(newPracOne);
        testAdmin.addPrac(newPracTwo);

        //seeing if I can find and retrieve practicals
        Practical retPracTwo = testAdmin.getPrac(pracTwoName);

        //ensuring the returned pracs were the ones which were actually added to the methods
        assertEquals("adding and deleting practical practical two", pracTwoName, retPracTwo.getTitle());
    }

    @Test
    public void deletingPrac()
    {
        String name = "google";
        Practical removedPrac = testAdmin.delPrac(name);
        assertEquals("deleting practical from test Admin", name, removedPrac.getTitle());
    }

}