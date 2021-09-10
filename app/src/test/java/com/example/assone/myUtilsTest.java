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
}