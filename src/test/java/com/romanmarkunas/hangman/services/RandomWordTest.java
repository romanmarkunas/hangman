package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.Words;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RandomWordTest {

    private RandomWord testInstance;
    private Words wordsMock;


    @Before
    public void setUp() throws Exception {

        wordsMock = mock(Words.class);
        testInstance = new RandomWord(wordsMock);
    }

    @Test
    public void getNextSuccessful() throws Exception {

        when(wordsMock.count()).thenReturn(1);
        when(wordsMock.get(0)).thenReturn("fizzbuzz");

        for (int i = 0; i < 1000; i++) {

            assertTrue("fizzbuzz".equals(testInstance.getNext()));
        }
    }

    @Test(expected=DomainAccessException.class)
    public void getNextEmptyDatabase() throws Exception {

        when(wordsMock.count()).thenReturn(10);
        when(wordsMock.get(10)).thenReturn(null);

        testInstance.getNext();
    }
}