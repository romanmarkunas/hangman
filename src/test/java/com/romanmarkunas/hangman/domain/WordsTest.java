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
        testInstance.add("mongoose");               // add at 1 index
        testInstance.add("bingo!");                 // do not add with special characters
        testInstance.add("this is a sentence");     // do not add sentence
        testInstance.add("mongoose");               // do not add already existing

        assertTrue("elephant".equals(testInstance.get(0)));
        assertTrue("mongoose".equals(testInstance.get(1)));
        assertEquals(2, testInstance.count());

        // test update
        testInstance.update("elephant", null);
        testInstance.update(null, "mammoth");
        testInstance.update("elephant", "mammoth");
        testInstance.update("rhino", "fairy");
        testInstance.update("mongoose", "panda*");

        assertTrue("mammoth".equals(testInstance.get(0)));
        assertTrue("mongoose".equals(testInstance.get(1)));
        assertEquals(2, testInstance.count());

        // remove
        testInstance.remove(null);
        testInstance.remove("monkey");
        assertEquals(2, testInstance.count());
        testInstance.remove("mammoth");
        assertTrue("mongoose".equals(testInstance.get(0)));
        assertEquals(1, testInstance.count());

        // get
        assertEquals(null, testInstance.get(10));
    }
}