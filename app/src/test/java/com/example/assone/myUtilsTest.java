package com.example.assone;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import static org.junit.Assert.*;

public class myUtilsTest
{
    @Test
    public void testGetCountry()
    {
        String actualOne =  "au";
        String actualTwo = "br";
        String actualThree = "ar";

        assertEquals("country retrieval for australia", actualOne, myUtils.getCountryCode("australia"));
        assertEquals("country retrieval for brazil", actualTwo, myUtils.getCountryCode("brazil"));
        assertEquals("country retrieval for argentina", actualThree, myUtils.getCountryCode("argentina"));
    }

    @Test
    public void testGetCountryNames()
    {
        //getting the look up table from the class
        Set<String> countries = myUtils.countryLookUp.keySet();
        String [] returnedCountrys = myUtils.getCountryNames();

        for (String key: countries)
        {
            boolean hasKey = Arrays.asList(returnedCountrys).contains(key);
            assertTrue("returend list doesn't have key: "+key, hasKey);
        }
    }

    //I don't think that I can actually test this method herre because it depends on android studio
    /*@Test
    public void testLoadImage()
    {
        myUtils utils = new myUtils();

        ArrayList<Flag> allFLags = utils.loadFlags();
        Set<String> actualFlags = myUtils.countryLookUp.keySet();


        for (Flag currFlag : allFLags)
        {
            boolean hasKey = actualFlags.contains(currFlag.getName());
            assertTrue("laod flags doesn't have flag: " + currFlag, hasKey);
        }

    }*/

}