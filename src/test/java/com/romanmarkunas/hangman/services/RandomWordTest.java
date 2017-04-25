package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.Word;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RandomWordTest {

    private RandomWord testInstance;
    private List<Word> wordMockList;


    @Before
    public void setUp() throws Exception {

        // mocks setup
        Word elephant = mock(Word.class);
        when(elephant.getString()).thenReturn("elephant");

        wordMockList = new ArrayList<>(3);
        wordMockList.add(elephant);

        // test instance
        testInstance = new RandomWord(wordMockList);
    }

    @Test
    public void getNextSuccessful() throws Exception {

        for (int i = 0; i < 1000; i++) {

            assertTrue("elephant".equals(testInstance.getNext().getString()));
        }
    }
}