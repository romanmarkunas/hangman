package com.romanmarkunas.hangman.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordsTest {

    private Words testInstance;


    @Before
    public void setup() {

        testInstance = new Words();
    }


    @Test
    public void crud() {

        // test add
        testInstance.add(null);                     // shouldn't be added
        testInstance.add("elephant");               // add at 0 index
        testInstance.add("mOngoOse");               // add at 1 index
        testInstance.add("bingo!");                 // do not add with special characters
        testInstance.add("this is a sentence");     // do not add sentence
        testInstance.add("mongoose");               // do not add already existing

        assertTrue("elephant".equals(testInstance.get(0)));
        assertTrue("mongoose".equals(testInstance.get(1)));
        assertEquals(2, testInstance.count());

        // test update
        testInstance.update("elephant", null);      // do not update
        testInstance.update(null, "mammoth");       // do not update
        testInstance.update("elephant", "mongoose");// do not update since mongoose already present
        testInstance.update("Elephant", "maMMoth"); // update
        testInstance.update("rhino", "fairy");      // do not update since rhino wasn't in list
        testInstance.update("mongoose", "panda*");  // do not update since * is a special char

        assertTrue("mammoth".equals(testInstance.get(0)));
        assertTrue("mongoose".equals(testInstance.get(1)));
        assertEquals(2, testInstance.count());

        // remove
        testInstance.remove(null);
        testInstance.remove("monkey");

        assertEquals(2, testInstance.count());

        testInstance.remove("mAmmotH");

        assertTrue("mongoose".equals(testInstance.get(0)));
        assertEquals(1, testInstance.count());

        // get
        assertEquals(null, testInstance.get(10));
    }
}