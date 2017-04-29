package com.romanmarkunas.hangman.applications.hangmangame;

import com.romanmarkunas.hangman.domain.HangmanGameState;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HangmanGameTest {

    private HangmanGame testGame;
    private String secretWord = "ellipse";


    @Before
    public void setUp() throws Exception {

        testGame = new HangmanGame(secretWord, 5);
    }


    @Test
    public void creation() throws Exception {

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());
    }

    @Test
    public void playerWon() throws Exception {

        testGame.guess('e');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("e_____e", testGame.getRevealedWord());

        testGame.guess('L');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("ell___e", testGame.getRevealedWord());

        testGame.guess('l');
        testGame.guess('l');

        assertEquals(5, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("ell___e", testGame.getRevealedWord());

        testGame.guess('p');
        testGame.guess('s');
        testGame.guess('i');

        assertEquals(5, testGame.getTriesLeft());
        assertTrue(testGame.complete());
        assertTrue(testGame.playerWon());
        assertEquals(secretWord, testGame.getRevealedWord());
    }

    @Test
    public void playerLost() throws Exception {

        testGame.guess('A');

        assertEquals(4, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('a');

        assertEquals(4, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('z');

        assertEquals(3, testGame.getTriesLeft());
        assertFalse(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());

        testGame.guess('k');
        testGame.guess('o');
        testGame.guess('n');

        assertEquals(0, testGame.getTriesLeft());
        assertTrue(testGame.complete());
        assertFalse(testGame.playerWon());
        assertEquals("_______", testGame.getRevealedWord());
    }

    @Test
    public void gameStateInteractions() throws Exception {

        int id = -10;
        String secret = "razzledazzle";
        String revealed = "__zz____zz__";
        int triesLeft = 1;
        String leftChars = "raled";
        LocalDateTime testStartDate = LocalDateTime.now();

        HangmanGameState stateMock = mock(HangmanGameState.class);

        when(stateMock.getId()).thenReturn(id);
        when(stateMock.getSecretWord()).thenReturn(secret);
        when(stateMock.getRevealedWord()).thenReturn(revealed);
        when(stateMock.getTriesLeft()).thenReturn(triesLeft);
        when(stateMock.getNotUsedChars()).thenReturn(leftChars);

        testGame = new HangmanGame(stateMock);

        HangmanGameState resultState = testGame.getGameState();

        assertEquals(id, resultState.getId());
        assertEquals(secret, resultState.getSecretWord());
        assertEquals(revealed, resultState.getRevealedWord());
        assertEquals(triesLeft, resultState.getTriesLeft());
        assertEquals(leftChars, resultState.getNotUsedChars());
        assertTrue(resultState.getExpiryDate().isAfter(testStartDate.plus(29, ChronoUnit.MINUTES)));
    }
}