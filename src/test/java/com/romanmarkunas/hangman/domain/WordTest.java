package com.romanmarkunas.hangman.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    private Word testInstance;


    @Before
    public void setup() {

        testInstance = new Word("mongoose");
    }


    @Test
    public void creation() {

        assertEquals("mongoose", testInstance.getString());
    }

    @Test
    public void testEquals() {

        Word sameWord = new Word("mongoose");
        Word notSameWord = new Word("Mongoose");

        assertTrue(testInstance.equals(sameWord));
        assertFalse(testInstance.equals(notSameWord));
    }

    @Test
    public void testHashCode() {

        Word sameWord = new Word("mongoose");

        assertEquals(testInstance.hashCode(), sameWord.hashCode());
    }
}